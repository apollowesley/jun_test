package com.kiss.form.util.curl;

// Automatically created class with defines extracted from curl.h.
// The curl class is a JNI wrapper for libcurl.
// This is meant as a raw, crude and low-level interface to libcurl.
// this list is up-to-date as of cURL 7.32.0

public class CurlGlue
{
  // start of generated list
  public static final int CURLOPT_FILE = 10001;
  public static final int CURLOPT_URL = 10002;
  public static final int CURLOPT_PORT = 3;
  public static final int CURLOPT_PROXY = 10004;
  public static final int CURLOPT_USERPWD = 10005;
  public static final int CURLOPT_PROXYUSERPWD = 10006;
  public static final int CURLOPT_RANGE = 10007;
  public static final int CURLOPT_INFILE = 10009;
  public static final int CURLOPT_ERRORBUFFER = 10010;
  public static final int CURLOPT_WRITEFUNCTION = 20011;
  public static final int CURLOPT_READFUNCTION = 20012;
  public static final int CURLOPT_TIMEOUT = 13;
  public static final int CURLOPT_INFILESIZE = 14;
  public static final int CURLOPT_POSTFIELDS = 10015;
  public static final int CURLOPT_REFERER = 10016;
  public static final int CURLOPT_FTPPORT = 10017;
  public static final int CURLOPT_USERAGENT = 10018;
  public static final int CURLOPT_LOW_SPEED_LIMIT = 19;
  public static final int CURLOPT_LOW_SPEED_TIME = 20;
  public static final int CURLOPT_RESUME_FROM = 21;
  public static final int CURLOPT_COOKIE = 10022;
  public static final int CURLOPT_HTTPHEADER = 10023;
  public static final int CURLOPT_HTTPPOST = 10024;
  public static final int CURLOPT_SSLCERT = 10025;
  public static final int CURLOPT_KEYPASSWD = 10026;
  public static final int CURLOPT_CRLF = 27;
  public static final int CURLOPT_QUOTE = 10028;
  public static final int CURLOPT_WRITEHEADER = 10029;
  public static final int CURLOPT_COOKIEFILE = 10031;
  public static final int CURLOPT_SSLVERSION = 32;
  public static final int CURLOPT_TIMECONDITION = 33;
  public static final int CURLOPT_TIMEVALUE = 34;
  public static final int CURLOPT_CUSTOMREQUEST = 10036;
  public static final int CURLOPT_STDERR = 10037;
  public static final int CURLOPT_POSTQUOTE = 10039;
  public static final int CURLOPT_WRITEINFO = 10040;
  public static final int CURLOPT_VERBOSE = 41;
  public static final int CURLOPT_HEADER = 42;
  public static final int CURLOPT_NOPROGRESS = 43;
  public static final int CURLOPT_NOBODY = 44;
  public static final int CURLOPT_FAILONERROR = 45;
  public static final int CURLOPT_UPLOAD = 46;
  public static final int CURLOPT_POST = 47;
  public static final int CURLOPT_DIRLISTONLY = 48;
  public static final int CURLOPT_APPEND = 50;
  public static final int CURLOPT_NETRC = 51;
  public static final int CURLOPT_FOLLOWLOCATION = 52;
  public static final int CURLOPT_TRANSFERTEXT = 53;
  public static final int CURLOPT_PUT = 54;
  public static final int CURLOPT_PROGRESSFUNCTION = 20056;
  public static final int CURLOPT_PROGRESSDATA = 10057;
  public static final int CURLOPT_AUTOREFERER = 58;
  public static final int CURLOPT_PROXYPORT = 59;
  public static final int CURLOPT_POSTFIELDSIZE = 60;
  public static final int CURLOPT_HTTPPROXYTUNNEL = 61;
  public static final int CURLOPT_INTERFACE = 10062;
  public static final int CURLOPT_KRBLEVEL = 10063;
  public static final int CURLOPT_SSL_VERIFYPEER = 64;
  public static final int CURLOPT_CAINFO = 10065;
  public static final int CURLOPT_MAXREDIRS = 68;
  public static final int CURLOPT_FILETIME = 69;
  public static final int CURLOPT_TELNETOPTIONS = 10070;
  public static final int CURLOPT_MAXCONNECTS = 71;
  public static final int CURLOPT_CLOSEPOLICY = 72;
  public static final int CURLOPT_FRESH_CONNECT = 74;
  public static final int CURLOPT_FORBID_REUSE = 75;
  public static final int CURLOPT_RANDOM_FILE = 10076;
  public static final int CURLOPT_EGDSOCKET = 10077;
  public static final int CURLOPT_CONNECTTIMEOUT = 78;
  public static final int CURLOPT_HEADERFUNCTION = 20079;
  public static final int CURLOPT_HTTPGET = 80;
  public static final int CURLOPT_SSL_VERIFYHOST = 81;
  public static final int CURLOPT_COOKIEJAR = 10082;
  public static final int CURLOPT_SSL_CIPHER_LIST = 10083;
  public static final int CURLOPT_HTTP_VERSION = 84;
  public static final int CURLOPT_FTP_USE_EPSV = 85;
  public static final int CURLOPT_SSLCERTTYPE = 10086;
  public static final int CURLOPT_SSLKEY = 10087;
  public static final int CURLOPT_SSLKEYTYPE = 10088;
  public static final int CURLOPT_SSLENGINE = 10089;
  public static final int CURLOPT_SSLENGINE_DEFAULT = 90;
  public static final int CURLOPT_DNS_USE_GLOBAL_CACHE = 91;
  public static final int CURLOPT_DNS_CACHE_TIMEOUT = 92;
  public static final int CURLOPT_PREQUOTE = 10093;
  public static final int CURLOPT_DEBUGFUNCTION = 20094;
  public static final int CURLOPT_DEBUGDATA = 10095;
  public static final int CURLOPT_COOKIESESSION = 96;
  public static final int CURLOPT_CAPATH = 10097;
  public static final int CURLOPT_BUFFERSIZE = 98;
  public static final int CURLOPT_NOSIGNAL = 99;
  public static final int CURLOPT_SHARE = 10100;
  public static final int CURLOPT_PROXYTYPE = 101;
  public static final int CURLOPT_ACCEPT_ENCODING = 10102;
  public static final int CURLOPT_PRIVATE = 10103;
  public static final int CURLOPT_UNRESTRICTED_AUTH = 105;
  public static final int CURLOPT_FTP_USE_EPRT = 106;
  public static final int CURLOPT_HTTPAUTH = 107;
  public static final int CURLOPT_SSL_CTX_FUNCTION = 20108;
  public static final int CURLOPT_SSL_CTX_DATA = 10109;
  public static final int CURLOPT_FTP_CREATE_MISSING_DIRS = 110;
  public static final int CURLOPT_PROXYAUTH = 111;
  public static final int CURLOPT_FTP_RESPONSE_TIMEOUT = 112;
  public static final int CURLOPT_IPRESOLVE = 113;
  public static final int CURLOPT_MAXFILESIZE = 114;
  public static final int CURLOPT_INFILESIZE_LARGE = 30115;
  public static final int CURLOPT_RESUME_FROM_LARGE = 30116;
  public static final int CURLOPT_MAXFILESIZE_LARGE = 30117;
  public static final int CURLOPT_NETRC_FILE = 10118;
  public static final int CURLOPT_USE_SSL = 119;
  public static final int CURLOPT_POSTFIELDSIZE_LARGE = 30120;
  public static final int CURLOPT_TCP_NODELAY = 121;
  public static final int CURLOPT_FTPSSLAUTH = 129;
  public static final int CURLOPT_IOCTLFUNCTION = 20130;
  public static final int CURLOPT_IOCTLDATA = 10131;
  public static final int CURLOPT_FTP_ACCOUNT = 10134;
  public static final int CURLOPT_COOKIELIST = 10135;
  public static final int CURLOPT_IGNORE_CONTENT_LENGTH = 136;
  public static final int CURLOPT_FTP_SKIP_PASV_IP = 137;
  public static final int CURLOPT_FTP_FILEMETHOD = 138;
  public static final int CURLOPT_LOCALPORT = 139;
  public static final int CURLOPT_LOCALPORTRANGE = 140;
  public static final int CURLOPT_CONNECT_ONLY = 141;
  public static final int CURLOPT_CONV_FROM_NETWORK_FUNCTION = 20142;
  public static final int CURLOPT_CONV_TO_NETWORK_FUNCTION = 20143;
  public static final int CURLOPT_MAX_SEND_SPEED_LARGE = 30145;
  public static final int CURLOPT_MAX_RECV_SPEED_LARGE = 30146;
  public static final int CURLOPT_FTP_ALTERNATIVE_TO_USER = 10147;
  public static final int CURLOPT_SOCKOPTFUNCTION = 20148;
  public static final int CURLOPT_SOCKOPTDATA = 10149;
  public static final int CURLOPT_SSL_SESSIONID_CACHE = 150;
  public static final int CURLOPT_SSH_AUTH_TYPES = 151;
  public static final int CURLOPT_SSH_PUBLIC_KEYFILE = 10152;
  public static final int CURLOPT_SSH_PRIVATE_KEYFILE = 10153;
  public static final int CURLOPT_FTP_SSL_CCC = 154;
  public static final int CURLOPT_TIMEOUT_MS = 155;
  public static final int CURLOPT_CONNECTTIMEOUT_MS = 156;
  public static final int CURLOPT_HTTP_TRANSFER_DECODING = 157;
  public static final int CURLOPT_HTTP_CONTENT_DECODING = 158;
  public static final int CURLOPT_NEW_FILE_PERMS = 159;
  public static final int CURLOPT_NEW_DIRECTORY_PERMS = 160;
  public static final int CURLOPT_POSTREDIR = 161;
  public static final int CURLOPT_OPENSOCKETFUNCTION = 20163;
  public static final int CURLOPT_OPENSOCKETDATA = 10164;
  public static final int CURLOPT_COPYPOSTFIELDS = 10165;
  public static final int CURLOPT_PROXY_TRANSFER_MODE = 166;
  public static final int CURLOPT_SEEKFUNCTION = 20167;
  public static final int CURLOPT_SEEKDATA = 10168;
  public static final int CURLOPT_CRLFILE = 10169;
  public static final int CURLOPT_ISSUERCERT = 10170;
  public static final int CURLOPT_ADDRESS_SCOPE = 171;
  public static final int CURLOPT_CERTINFO = 172;
  public static final int CURLOPT_USERNAME = 10173;
  public static final int CURLOPT_PASSWORD = 10174;
  public static final int CURLOPT_PROXYUSERNAME = 10175;
  public static final int CURLOPT_PROXYPASSWORD = 10176;
  public static final int CURLOPT_NOPROXY = 10177;
  public static final int CURLOPT_TFTP_BLKSIZE = 178;
  public static final int CURLOPT_PROTOCOLS = 181;
  public static final int CURLOPT_REDIR_PROTOCOLS = 182;
  public static final int CURLOPT_SSH_KNOWNHOSTS = 10183;
  public static final int CURLOPT_SSH_KEYFUNCTION = 20184;
  public static final int CURLOPT_SSH_KEYDATA = 10185;
  public static final int CURLOPT_MAIL_FROM = 10186;
  public static final int CURLOPT_MAIL_RCPT = 10187;
  public static final int CURLOPT_FTP_USE_PRET = 188;
  public static final int CURLOPT_RTSP_REQUEST = 189;
  public static final int CURLOPT_RTSP_SESSION_ID = 10190;
  public static final int CURLOPT_RTSP_STREAM_URI = 10191;
  public static final int CURLOPT_RTSP_TRANSPORT = 10192;
  public static final int CURLOPT_RTSP_CLIENT_CSEQ = 193;
  public static final int CURLOPT_RTSP_SERVER_CSEQ = 194;
  public static final int CURLOPT_INTERLEAVEDATA = 10195;
  public static final int CURLOPT_INTERLEAVEFUNCTION = 20196;
  public static final int CURLOPT_WILDCARDMATCH = 197;
  public static final int CURLOPT_CHUNK_BGN_FUNCTION = 20198;
  public static final int CURLOPT_CHUNK_END_FUNCTION = 20199;
  public static final int CURLOPT_FNMATCH_FUNCTION = 20200;
  public static final int CURLOPT_CHUNK_DATA = 10201;
  public static final int CURLOPT_FNMATCH_DATA = 10202;
  public static final int CURLOPT_RESOLVE = 10203;
  public static final int CURLOPT_TLSAUTH_USERNAME = 10204;
  public static final int CURLOPT_TLSAUTH_PASSWORD = 10205;
  public static final int CURLOPT_TLSAUTH_TYPE = 10206;
  public static final int CURLOPT_TRANSFER_ENCODING = 207;
  public static final int CURLOPT_CLOSESOCKETFUNCTION = 20208;
  public static final int CURLOPT_CLOSESOCKETDATA = 10209;
  public static final int CURLOPT_GSSAPI_DELEGATION = 210;
  public static final int CURLOPT_DNS_SERVERS = 10211;
  public static final int CURLOPT_ACCEPTTIMEOUT_MS = 212;
  public static final int CURLOPT_TCP_KEEPALIVE = 213;
  public static final int CURLOPT_TCP_KEEPIDLE = 214;
  public static final int CURLOPT_TCP_KEEPINTVL = 215;
  public static final int CURLOPT_SSL_OPTIONS = 216;
  public static final int CURLOPT_MAIL_AUTH = 10217;
  public static final int CURLOPT_SASL_IR = 218;
  public static final int CURLOPT_XFERINFOFUNCTION = 20219;
  // end of generated list

  public CurlGlue() {
    try {
      curljava_handle = jni_init();
    } catch (Exception e) {
       e.printStackTrace();
    }
  }

  public void finalize() {
    jni_cleanup(curljava_handle);
  }

  private int curljava_handle;

  // constructor and destructor for the libcurl handle
  private native int jni_init();
  private native void jni_cleanup(int curljava_handle);
  private native synchronized int jni_perform(int curljava_handle);

  // Instead of varargs, we have different functions for each
  // kind of type setopt() can take
  private native int jni_setopt(int libcurl, int option, String value);
  private native int jni_setopt(int libcurl, int option, int value);
  private native int jni_setopt(int libcurl, int option, CurlWrite value);
  private native int jni_setopt(int libcurl, int option, CurlRead value);
  private native int jni_setopt(int libcurl, int option, CurlIO value);

  public native int getinfo();
  public static native String version();

  public int perform() {
    return jni_perform(curljava_handle);
  }
  public int setopt(int option, int value) {
    return jni_setopt(curljava_handle, option, value);
  }
  public int setopt(int option, String value) {
    return jni_setopt(curljava_handle, option, value);
  }
  public int setopt(int option, CurlWrite value) {
    return jni_setopt(curljava_handle, option, value);
  }
  public int setopt(int option, CurlRead value) {
    return jni_setopt(curljava_handle, option, value);
  }
  public int setopt(int option, CurlIO value) {
    return jni_setopt(curljava_handle, option, value);
  }

  static {
    try {
      // Loading up the shared JNI
      System.loadLibrary("curljava");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}

