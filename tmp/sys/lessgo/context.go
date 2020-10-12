package lessgo

import (
	"bytes"
	"encoding/json"
	"encoding/xml"
	"fmt"
	"io"
	"io/ioutil"
	"mime"
	"mime/multipart"
	"net"
	"net/http"
	"net/http/httputil"
	"net/url"
	"os"
	"path"
	"path/filepath"
	"strings"
	"sync"
	"time"

	"github.com/henrylee2cn/lessgo/logs"
	"github.com/henrylee2cn/lessgo/markdown"
	"github.com/henrylee2cn/lessgo/session"
	"github.com/henrylee2cn/lessgo/utils"
	"github.com/henrylee2cn/lessgo/websocket"
)

type (
	Context struct {
		request        *http.Request
		response       *Response
		path           string
		realRemoteAddr string
		query          url.Values
		form           url.Values
		pkeys          []string
		pvalues        []string
		store          store
		cruSession     session.Store
		socket         *websocket.Conn
		failureHandler FailureHandlerFunc
	}

	store map[string]interface{}

	// Common message format of JSON and JSONP.
	CommJSON Result

	ReverseProxys struct {
		list map[string]*httputil.ReverseProxy
		sync.RWMutex
	}
)

var (
	_ http.ResponseWriter = new(Context)

	// 默认页面文件
	indexPage = "index.html"

	// 文件上传默认内存缓存大小，默认值是64MB。
	MaxMemory int64 = 64 * MB

	reverseProxys = &ReverseProxys{
		list: map[string]*httputil.ReverseProxy{},
	}
)

func (c *Context) Request() *http.Request {
	return c.request
}

func (c *Context) SetRequestBody(reader io.Reader) {
	c.request.Body = ioutil.NopCloser(reader)
}

func (c *Context) ResponseWriter() http.ResponseWriter {
	return c
}

func (c *Context) Response() *Response {
	return c.response
}

func (c *Context) SetResponse(resp *Response) {
	c.response = resp
}

func (c *Context) IsTLS() bool {
	return c.request.TLS != nil
}

func (c *Context) Scheme() string {
	if c.IsTLS() {
		return "https"
	}
	return "http"
}

// 获取客户端真实IP
func (c *Context) RealRemoteAddr() string {
	if len(c.realRemoteAddr) > 0 {
		return c.realRemoteAddr
	}
	var ip string
	if ip = c.request.Header.Get(HeaderXRealIP); len(ip) == 0 {
		if ip = c.request.Header.Get(HeaderXForwardedFor); len(ip) == 0 {
			ip, _, _ = net.SplitHostPort(c.request.RemoteAddr)
		}
	}
	c.realRemoteAddr = ip
	return ip
}

// Path returns the registered path for the handler.
func (c *Context) Path() string {
	return c.path
}

// SetPath sets the registered path for the handler.
func (c *Context) SetPath(p string) {
	c.path = p
}

// PathParamKeys returns path param keys.
func (c *Context) PathParamKeys() []string {
	return c.pkeys
}

// PathParamValues returns path param values.
func (c *Context) PathParamValues() []string {
	return c.pvalues
}

// PathParam returns path param by key.
func (c *Context) PathParam(key string) string {
	l := len(c.pkeys)
	for i, n := range c.pkeys {
		if n == key && i < l {
			return c.pvalues[i]
		}
	}
	return ""
}

// PathParamByIndex returns path param by index.
func (c *Context) PathParamByIndex(i int) string {
	l := len(c.pkeys)
	if i < l {
		return c.pvalues[i]
	}
	return ""
}

// SetPathParam sets path param.
func (c *Context) SetPathParam(key, value string) {
	l := len(c.pkeys)
	for i, n := range c.pkeys {
		if n == key && i < l {
			c.pvalues[i] = value
			return
		}
	}
	c.pkeys = append(c.pkeys, key)
	if len(c.pvalues) > l {
		c.pvalues[l] = value
	} else {
		c.pvalues = append(c.pvalues, value)
	}
}

// DelPathParam  deletes the values associated with key.
// func (c *Context) DelPathParam(key string) {
// 	l := len(c.pkeys)
// 	for i, n := range c.pkeys {
// 		if n == key && i < l {
// 			c.pkeys = append(c.pkeys[:i], c.pkeys[i+1:]...)
// 			c.pvalues = append(c.pvalues[:i], c.pvalues[i+1:]...)
// 			return
// 		}
// 	}
// }

// QueryValues returns all query params.
func (c *Context) QueryValues() url.Values {
	if c.query == nil {
		c.query = c.request.URL.Query()
	}
	return c.query
}

// QueryParams returns the query param with "[]string".
func (c *Context) QueryParams(key string) []string {
	if c.query == nil {
		c.query = c.request.URL.Query()
	}
	return c.query[key]
}

// QueryParam returns the query param for the provided key.
func (c *Context) QueryParam(key string) string {
	if c.query == nil {
		c.query = c.request.URL.Query()
	}
	return c.query.Get(key)
}

// SetQueryParam sets the query param. It replaces any existing
// values.
func (c *Context) SetQueryParam(key string, value string) {
	if c.query == nil {
		c.query = c.request.URL.Query()
	}
	c.query.Set(key, value)
}

// AddQueryParam adds the the query param. It appends to any existing
// values associated with key.
func (c *Context) AddQueryParam(key string, value string) {
	if c.query == nil {
		c.query = c.request.URL.Query()
	}
	c.query.Add(key, value)
}

// DelQueryParam deletes the values associated with key.
// func (c *Context) DelQueryParam(key string) {
// 	if c.query == nil {
// 		c.query = c.request.URL.Query()
// 	}
// 	c.query.Del(key)
// }

// HeaderValues returns the request header.
func (c *Context) HeaderValues() http.Header {
	return c.request.Header
}

// HeaderParams returns request header value with "[]string" for the provided key.
func (c *Context) HeaderParams(key string) []string {
	return c.request.Header[key]
}

// HeaderParam returns request header value for the provided key.
func (c *Context) HeaderParam(key string) string {
	return c.request.Header.Get(key)
}

// SetHeaderParam sets request header. It replaces any existing values.
func (c *Context) SetHeaderParam(key string, value string) {
	c.request.Header.Set(key, value)
}

// AddHeaderParam sets request header. It appends to any existing
// values associated with key.
func (c *Context) AddHeaderParam(key string, value string) {
	c.request.Header.Add(key, value)
}

// DelHeaderParam deletes the values associated with key for request.
// func (c *Context) DelHeaderParam(key string) {
// 	c.request.Header.Del(key)
// }

// FormValues returns the form params as url.Values.
func (c *Context) FormValues() url.Values {
	c.parseForm()
	return c.form
}

// FormParams returns the form field value with "[]string" for the provided key.
func (c *Context) FormParams(key string) []string {
	c.parseForm()
	if vs := c.form[key]; len(vs) > 0 {
		return vs
	}
	return []string{}
}

// FormParam returns the form field value for the provided key.
func (c *Context) FormParam(key string) string {
	c.parseForm()
	if vs := c.form[key]; len(vs) > 0 {
		return vs[0]
	}
	return ""
}

// SetFormParam sets the form param. It replaces any existing values.
func (c *Context) SetFormParam(key string, value string) {
	c.parseForm()
	c.form.Set(key, value)
}

// AddFormParam adds the form param. It appends to any existing
// values associated with key.
func (c *Context) AddFormParam(key string, value string) {
	c.parseForm()
	c.form.Add(key, value)
}

// DelFormParam deletes the values associated with key.
// func (c *Context) DelFormParam(key string) {
// 	c.parseForm()
// 	c.form.Del(key)
// }

// FormFile returns the multipart form file for the provided key.
func (c *Context) FormFile(key string) (multipart.File, *multipart.FileHeader, error) {
	c.parseForm()
	return c.request.FormFile(key)
}

// SaveFile saves the file *Context.FormFile to UPLOADS_DIR,
// character "?" indicates that the original file name.
// for example newfname="a/?" -> UPLOADS_DIR/a/fname.
func (c *Context) SaveFile(key string, cover bool, newfname ...string) (fileUrl string, size int64, err error) {
	f, fh, err := c.FormFile(key)
	if err != nil {
		return
	}
	defer func() {
		err2 := f.Close()
		if err2 != nil && err == nil {
			err = err2
		}
	}()

	// Sets the full file name
	var fullname string
	if len(newfname) == 0 {
		fullname = filepath.Join(UPLOADS_DIR, fh.Filename)
	} else {
		if strings.Contains(newfname[0], "?") {
			fullname = filepath.Join(UPLOADS_DIR, strings.Replace(newfname[0], "?", fh.Filename, -1))
		} else {
			fname := strings.TrimRight(newfname[0], ".")
			if filepath.Ext(fname) == "" {
				fullname = filepath.Join(UPLOADS_DIR, fname+filepath.Ext(fh.Filename))
			} else {
				fullname = filepath.Join(UPLOADS_DIR, fname)
			}
		}
	}

	// Create the completion file path
	p, _ := filepath.Split(fullname)
	err = os.MkdirAll(p, 0777)
	if err != nil {
		return
	}

	// If the file with the same name exists, add the suffix of the serial number
	idx := strings.LastIndex(fullname, filepath.Ext(fullname))
	_fullname := fullname
	for i := 2; utils.FileExists(_fullname) && !cover; i++ {
		_fullname = fmt.Sprintf("%s(%d)%s", fullname[:idx], i, fullname[idx:])
	}
	fullname = _fullname

	// Create the URL of the file
	fileUrl = "/" + strings.Replace(fullname, `\`, `/`, -1)

	// Save the file to local
	f2, err := os.OpenFile(fullname, os.O_CREATE|os.O_WRONLY, 0644)
	if err != nil {
		return
	}
	size, err = io.Copy(f2, f)
	err3 := f2.Close()
	if err3 != nil && err == nil {
		err = err3
	}
	return
}

// CookieParams returns the HTTP cookies sent with the request.
func (c *Context) CookieParams() []*http.Cookie {
	return c.request.Cookies()
}

// CookieParam returns the named cookie provided in the request.
func (c *Context) CookieParam(key string) *http.Cookie {
	cookie, _ := c.request.Cookie(key)
	return cookie
}

// AddCookieParam adds a cookie to the request.
func (c *Context) AddCookieParam(cookie *http.Cookie) {
	c.request.AddCookie(cookie)
}

// Bind binds the request body into provided type `container`. The default binder
// does it based on Content-Type header.
func (c *Context) Bind(container interface{}) error {
	return app.binder.Bind(container, c)
}

// Header returns the response header.
func (c *Context) Header() http.Header {
	return c.response.Header()
}

// SetHeader sets header for response. It replaces any existing values.
func (c *Context) SetHeader(key string, value string) {
	c.response.Header().Set(key, value)
}

// AddHeader sets header for response. It appends to any existing
// values associated with key.
func (c *Context) AddHeader(key string, value string) {
	c.response.Header().Add(key, value)
}

// DelHeader deletes the values associated with key for response.
func (c *Context) DelHeader(key string) {
	c.response.Header().Del(key)
}

// AddCookie adds cookie for response.
// The provided cookie must have a valid Name. Invalid cookies may be
// silently dropped.
func (c *Context) AddCookie(cookie *http.Cookie) {
	c.response.Header().Add("Set-Cookie", cookie.String())
}

// SetCookie sets cookie for response.
func (c *Context) SetCookie(cookie *http.Cookie) {
	c.response.Header().Set("Set-Cookie", cookie.String())
}

// DelCookie deletes cookie for response.
func (c *Context) DelCookie() {
	c.response.Header().Del("Set-Cookie")
}

// Write writes the data to the connection as part of an HTTP reply.
// If WriteHeader has not yet been called, Write calls WriteHeader(http.StatusOK)
// before writing the data.  If the Header does not contain a
// Content-Type line, Write adds a Content-Type set to the result of passing
// the initial 512 bytes of written data to DetectContentType.
func (c *Context) Write(b []byte) (int, error) {
	return c.response.writer.Write(b)
}

// WriteHeader sends an HTTP response header with status code.
// If WriteHeader is not called explicitly, the first call to Write
// will trigger an implicit WriteHeader(http.StatusOK).
// Thus explicit calls to WriteHeader are mainly used to
// send error codes.
func (c *Context) WriteHeader(code int) {
	if c.response.committed {
		Log.Warn("response already committed")
		return
	}
	c.freeSession()
	c.response.status = code
	c.response.writer.WriteHeader(code)
	c.response.committed = true
}

// Render renders a template with data and sends a text/html response with status
// code. Templates can be registered using `App.SetRenderer()`.
func (c *Context) Render(code int, name string, data interface{}) error {
	if app.renderer == nil {
		return ErrRendererNotRegistered
	}
	buf := new(bytes.Buffer)
	var err error
	if err = app.renderer.Render(buf, name, data, c); err != nil {
		return err
	}
	c.response.Header().Set(HeaderContentType, MIMETextHTMLCharsetUTF8)
	c.WriteHeader(code)
	_, err = c.response.Write(buf.Bytes())
	return nil
}

// HTML sends an HTTP response with status code.
func (c *Context) HTML(code int, html string) error {
	c.response.Header().Set(HeaderContentType, MIMETextHTMLCharsetUTF8)
	c.WriteHeader(code)
	_, err := c.response.Write(utils.String2Bytes(html))
	return err
}

// String sends a string response with status code.
func (c *Context) String(code int, s string) error {
	c.response.Header().Set(HeaderContentType, MIMETextPlainCharsetUTF8)
	c.WriteHeader(code)
	_, err := c.response.Write(utils.String2Bytes(s))
	return err
}

// JSON sends a JSON response with status code.
func (c *Context) JSON(code int, i interface{}) error {
	var (
		b   []byte
		err error
	)

	if Debug() {
		b, err = json.MarshalIndent(i, "", "  ")
	} else {
		b, err = json.Marshal(i)
	}
	if err != nil {
		return err
	}
	return c.JSONBlob(code, b)
}

// JSON with default format.
func (c *Context) JSONMsg(code int, msgcode int, info interface{}) error {
	var (
		b   []byte
		err error
	)
	i := CommJSON{
		Code: msgcode,
		Info: info,
	}
	if Debug() {
		b, err = json.MarshalIndent(i, "", "  ")
	} else {
		b, err = json.Marshal(i)
	}
	if err != nil {
		return err
	}

	return c.JSONBlob(code, b)
}

// JSONBlob sends a JSON blob response with status code.
func (c *Context) JSONBlob(code int, b []byte) error {
	c.response.Header().Set(HeaderContentType, MIMEApplicationJSONCharsetUTF8)
	c.WriteHeader(code)
	_, err := c.response.Write(b)
	return err
}

// JSONP sends a JSONP response with status code. It uses `callback` to construct
// the JSONP payload.
func (c *Context) JSONP(code int, callback string, i interface{}) error {
	var (
		b   []byte
		err error
	)
	if Debug() {
		b, err = json.MarshalIndent(i, "", "  ")
	} else {
		b, err = json.Marshal(i)
	}
	if err != nil {
		return err
	}
	c.response.Header().Set(HeaderContentType, MIMEApplicationJavaScriptCharsetUTF8)
	c.WriteHeader(code)
	if _, err = c.response.Write(utils.String2Bytes(callback + "(")); err != nil {
		return err
	}
	if _, err = c.response.Write(b); err != nil {
		return err
	}
	_, err = c.response.Write(utils.String2Bytes(");"))
	return err
}

// JSONP with default format.
func (c *Context) JSONPMsg(code int, callback string, msgcode int, info interface{}) error {
	var (
		b   []byte
		err error
	)
	i := CommJSON{
		Code: msgcode,
		Info: info,
	}
	if Debug() {
		b, err = json.MarshalIndent(i, "", "  ")
	} else {
		b, err = json.Marshal(i)
	}
	if err != nil {
		return err
	}
	c.response.Header().Set(HeaderContentType, MIMEApplicationJavaScriptCharsetUTF8)
	c.WriteHeader(code)
	if _, err = c.response.Write(utils.String2Bytes(callback + "(")); err != nil {
		return err
	}
	if _, err = c.response.Write(b); err != nil {
		return err
	}
	_, err = c.response.Write(utils.String2Bytes(");"))
	return err
}

// XML sends an XML response with status code.
func (c *Context) XML(code int, i interface{}) error {
	b, err := xml.Marshal(i)
	if Debug() {
		b, err = xml.MarshalIndent(i, "", "  ")
	}
	if err != nil {
		return err
	}
	return c.XMLBlob(code, b)
}

// XMLBlob sends a XML blob response with status code.
func (c *Context) XMLBlob(code int, b []byte) error {
	var err error
	c.response.Header().Set(HeaderContentType, MIMEApplicationXMLCharsetUTF8)
	c.WriteHeader(code)
	if _, err = c.response.Write(utils.String2Bytes(xml.Header)); err != nil {
		return err
	}
	_, err = c.response.Write(b)
	return err
}

// File sends a response with the content of the file.
func (c *Context) File(file string) error {
	if app.CanMemoryCache() {
		b, fi, exist := app.memoryCache.GetCacheFile(file)
		if !exist {
			return c.Failure(404, nil)
		}
		return c.ServeContent(bytes.NewReader(b), fi.Name(), fi.ModTime())
	}
	f, err := os.Open(file)
	if err != nil {
		return c.Failure(404, nil)
	}
	defer f.Close()

	fi, _ := f.Stat()
	if fi.IsDir() {
		file = filepath.Join(file, indexPage)
		f, err = os.Open(file)
		if err != nil {
			return c.Failure(404, nil)
		}
		fi, _ = f.Stat()
	}
	return c.ServeContent(f, fi.Name(), fi.ModTime())
}

// Markdown parses markdown file and generates html in github style
func (c *Context) Markdown(file string, hasCatalog ...bool) error {
	var catalog bool
	if len(hasCatalog) > 0 {
		catalog = hasCatalog[0]
	}
	if app.CanMemoryCache() {
		b, fi, exist := app.memoryCache.GetCacheFile(file)
		if !exist {
			return c.Failure(404, nil)
		}
		if c.isModified(fi.Name(), fi.ModTime()) {
			c.WriteHeader(http.StatusOK)
			return markdown.GithubMarkdown(b, c.response, catalog)
		}
		return c.NoContent(http.StatusNotModified)
	}
	f, err := os.Open(file)
	if err != nil {
		return c.Failure(404, nil)
	}
	defer f.Close()

	fi, _ := f.Stat()
	if fi.IsDir() {
		file = filepath.Join(file, indexPage)
		f, err = os.Open(file)
		if err != nil {
			return c.Failure(404, nil)
		}
		fi, _ = f.Stat()
	}
	if c.isModified(fi.Name(), fi.ModTime()) {
		buf, err := ioutil.ReadAll(f)
		if err != nil {
			return c.Failure(404, nil)
		}
		c.WriteHeader(http.StatusOK)
		return markdown.GithubMarkdown(buf, c.response, catalog)
	}
	return c.NoContent(http.StatusNotModified)
}

// Attachment sends a response from `io.ReaderSeeker` as attachment, prompting
// client to save the file.
func (c *Context) Attachment(r io.ReadSeeker, name string) error {
	c.response.Header().Set(HeaderContentType, ContentTypeByExtension(name))
	c.response.Header().Set(HeaderContentDisposition, "attachment; filename="+name)

	c.WriteHeader(http.StatusOK)
	_, err := io.Copy(c.response, r)
	return err
}

// ServeContent sends static content from `io.ReadSeeker` and handles caching
// via `If-Modified-Since` request header. It automatically sets `Content-Type`
// and `Last-Modified` response headers.
func (c *Context) ServeContent(content io.ReadSeeker, name string, modtime time.Time) error {
	http.ServeContent(c.response, c.request, name, modtime, content)
	return nil
}

func (c *Context) isModified(name string, modtime time.Time) bool {
	req := c.request
	head := c.response.Header()
	if t, err := time.Parse(http.TimeFormat, req.Header.Get(HeaderIfModifiedSince)); err == nil && modtime.Before(t.Add(1*time.Second)) {
		head.Del(HeaderContentType)
		head.Del(HeaderContentLength)
		return false
	}
	c.response.Header().Set(HeaderContentType, ContentTypeByExtension(name))
	head.Set(HeaderLastModified, modtime.UTC().Format(http.TimeFormat))
	return true
}

// NoContent sends a response with no body and a status code.
func (c *Context) NoContent(code int) error {
	c.WriteHeader(code)
	return nil
}

// Redirect redirects the request with status code.
func (c *Context) Redirect(code int, url string) error {
	if code < http.StatusMultipleChoices || code > http.StatusTemporaryRedirect {
		return ErrInvalidRedirectCode
	}
	c.response.Header().Set(HeaderLocation, url)
	c.WriteHeader(code)
	return nil
}

// ReverseProxy routes URLs to the scheme, host, and base path provided in targetUrlBase.
// If pathAppend is "true" and the targetUrlBase's path is "/base" and the incoming request was for "/dir",
// the target request will be for /base/dir.
func (c *Context) ReverseProxy(targetUrlBase string, pathAppend bool) error {
	var rp *httputil.ReverseProxy
	reverseProxys.RLock()
	rp = reverseProxys.list[targetUrlBase]
	reverseProxys.RUnlock()
	if rp == nil {
		reverseProxys.Lock()
		rp = reverseProxys.list[targetUrlBase]
		if rp == nil {
			target, err := url.Parse(targetUrlBase)
			if err != nil {
				reverseProxys.Unlock()
				return err
			}
			targetQuery := target.RawQuery
			rp = &httputil.ReverseProxy{
				Director: func(req *http.Request) {
					req.Host = target.Host
					req.URL.Scheme = target.Scheme
					req.URL.Host = target.Host
					req.URL.Path = path.Join(target.Path, req.URL.Path)
					if targetQuery == "" || req.URL.RawQuery == "" {
						req.URL.RawQuery = targetQuery + req.URL.RawQuery
					} else {
						req.URL.RawQuery = targetQuery + "&" + req.URL.RawQuery
					}
				},
			}
			reverseProxys.list[targetUrlBase] = rp
		}
		reverseProxys.Unlock()
	}
	if !pathAppend {
		c.request.URL.Path = ""
	}
	rp.ServeHTTP(c, c.request)
	return nil
}

// Failure writes http failure status, example 403, 404, 405, 500 and so on.
func (c *Context) Failure(code int, err error) error {
	if err == nil {
		return c.failureHandler(c, code, "")
	}
	return c.failureHandler(c, code, err.Error())
}

// CruSession returns session data info.
func (c *Context) CruSession() session.Store {
	return c.cruSession
}

// SetSession puts value into session.
func (c *Context) SetSession(key interface{}, value interface{}) {
	if c.cruSession == nil {
		return
	}
	c.cruSession.Set(key, value)
}

// GetSession gets value from session.
func (c *Context) GetSession(key interface{}) interface{} {
	if c.cruSession == nil {
		return nil
	}
	return c.cruSession.Get(key)
}

// DelSession removes value from session.
func (c *Context) DelSession(key interface{}) {
	if c.cruSession == nil {
		return
	}
	c.cruSession.Delete(key)
}

// SessionRegenerateID regenerates session id for this session.
// the session data have no changes.
func (c *Context) SessionRegenerateID() {
	if c.cruSession == nil {
		return
	}
	c.cruSession.SessionRelease(c.response)
	c.cruSession = app.sessions.SessionRegenerateID(c.response, c.request)
}

// DestroySession cleans session data and session cookie.
func (c *Context) DestroySession() {
	if c.cruSession == nil {
		return
	}
	c.cruSession.Flush()
	c.cruSession = nil
	app.sessions.SessionDestroy(c.response, c.request)
}

// 获取websocket实例
func (c *Context) Ws() *websocket.Conn {
	return c.socket
}

// 设置websocket实例
func (c *Context) SetWs(conn *websocket.Conn) {
	c.socket = conn
}

// 关闭websocket
func (c *Context) WsClose() error {
	return c.socket.Close()
}

// 接收JSON格式的websocket信息
func (c *Context) WsRecvJSON(v interface{}) error {
	return websocket.JSON.Receive(c.socket, v)
}

// 发送JSON格式的websocket信息
func (c *Context) WsSendJSON(v interface{}) (int, error) {
	return websocket.JSON.Send(c.socket, v)
}

// 接收string格式的websocket信息
func (c *Context) WsRecvString(v *string) error {
	return websocket.Message.Receive(c.socket, v)
}

// 发送string格式的websocket信息
func (c *Context) WsSendString(v string) (int, error) {
	return websocket.Message.Send(c.socket, v)
}

// Get retrieves data from the context.
func (c *Context) Set(key string, val interface{}) {
	if c.store == nil {
		c.store = make(store)
	}
	c.store[key] = val
}

// Set saves data in the context.
func (c *Context) Get(key string) interface{} {
	return c.store[key]
}

// Del deletes data from the context.
func (c *Context) Del(key string) {
	delete(c.store, key)
}

// Contains checks if the key exists in the context.
func (c *Context) Contains(key string) bool {
	_, ok := c.store[key]
	return ok
}

// Log returns the `Logger` instance.
func (c *Context) Log() logs.Logger {
	return Log
}

func (c *Context) parseForm() {
	if c.form != nil {
		return
	}
	c.request.ParseMultipartForm(MaxMemory)
	c.form = c.request.PostForm
	if c.request.MultipartForm != nil {
		for k, v := range c.request.MultipartForm.Value {
			if _, ok := c.form[k]; ok {
				c.form[k] = append(c.form[k], v...)
			} else {
				c.form[k] = v
			}
		}
	}
}

func (c *Context) freeSession() {
	if c.cruSession != nil {
		c.cruSession.SessionRelease(c.response)
		c.cruSession = nil
	}
}

func (c *Context) init(rw http.ResponseWriter, req *http.Request) error {
	var err error
	c.pkeys = c.pkeys[:0]
	c.pvalues = c.pvalues[:0]
	if app.sessions != nil {
		c.cruSession, err = app.sessions.SessionStart(rw, req)
		if err != nil {
			c.NoContent(503)
			return err
		}
	}
	c.request = req
	c.response.init(rw)
	c.store = make(store)
	return err
}

func (c *Context) free() {
	c.freeSession()
	c.socket = nil
	c.store = nil
	c.realRemoteAddr = ""
	c.query = nil
	c.form = nil
	c.response.free()
}

// ContentTypeByExtension returns the MIME type associated with the file based on
// its extension. It returns `application/octet-stream` incase MIME type is not
// found.
func ContentTypeByExtension(name string) string {
	if t := mime.TypeByExtension(filepath.Ext(name)); t != "" {
		return t
	}
	return MIMEOctetStream
}
