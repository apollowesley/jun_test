
package cn.backflow.lib.thirdpart;

import cn.backflow.lib.util.StringUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Client;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.StringUtils;
import com.qiniu.util.UrlSafeBase64;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 七牛上传工具类, 请传入不同的命名空间(namespace)实例化调用
 *
 * @author Nandy
 */
public class QiniuUtil {
    private static final Logger LOG = LoggerFactory.getLogger(QiniuUtil.class);

    public static final String QINIU_DOMAIN = "http://backflow.qiniudn.com/";

    private static String namespace = "backflow";

    private static final Auth auth = Auth.create(
            "skngPTWrOlW6AUhzxJcLhVbBhnev6iAA6nUr23GD", // ACCESS_KEY
            "a6fo8X4SJoXUriRH-bVa__nEBL1_mmp67cxVHxGC"  // SECRET_KEY
    );
    private static Configuration configuration = new Configuration(Zone.autoZone());
    private static OperationManager om = new OperationManager(auth, configuration);
    private static BucketManager bm = new BucketManager(auth, configuration);
    private static UploadManager um = new UploadManager(configuration);
    private static Client client = new Client();

    private QiniuUtil() { }

    public static void main(String[] args) throws Exception {

        //        String audio = "http://static.xuehu365.com/app/audio/voice.amr";
        //        String notyfyURL = "http://test.app.xuehu365.com/thirdpart/qiniu/notify";
        //
        //        audioConvert(audio, notyfyURL, "mp3", "m4a");
        final StringMap policy = new StringMap();
        policy.put("callbackUrl", "/thirdpart/qiniu/notify");
        policy.put("callbackBody", "key=$(key)&hash=$(etag)&w=$(imageInfo.width)&h=$(imageInfo.height)");
        String path = "D:\\angela.jpg";
        String key = "admin/angela.jpg";
        String token = QiniuUtil.uptoken(3600L, policy);
        QiniuUtil.executeUpload(() -> um.put(new File(path), key, token));
    }

    public static String buildUrl(Object key) {
        return QINIU_DOMAIN + key;
    }

    public static String uptoken(long expires, StringMap policy) { return auth.uploadToken(namespace, null, expires, policy); }

    // 简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public static String uptoken() { return auth.uploadToken(namespace); }

    public BucketManager getBucketManager() {
        return bm;
    }

    /**
     * 按最大阈值获取图片缩放后的宽高
     *
     * @param width     原始图片宽度
     * @param height    原始图片高度
     * @param threshold 阈值
     * @return 缩放后的宽高数组
     */
    public static int[] getImageDimentionByThreshold(int width, int height, int threshold) {
        if (width <= threshold && height <= threshold)
            return new int[]{width, height};

        double w = width;
        double h = height;

        if (width > height) {
            width = threshold;
            height *= threshold / w;
        } else {
            height = threshold;
            width *= threshold / h;
        }
        return new int[]{width, height};
    }

    public static JSONObject imageinfo(String url) throws IOException, URISyntaxException {
        if (!url.startsWith(QINIU_DOMAIN)) {
            Dimension dimension = dimension(url);
            return new JSONObject()
                    .put("width", dimension.getWidth())
                    .put("height", dimension.getHeight());
        }
        return new JSONObject(client.get(url + "?imageInfo").bodyString());
    }

    public static JSONObject audioinfo(String url) throws QiniuException {
        return new JSONObject(client.get(url + "?avinfo").bodyString());
    }

    public static JSONObject videofo(String url) throws QiniuException {
        String str = client.get(url + "?avinfo").bodyString();
        return new JSONObject(str);
    }

    public static Dimension dimension(String url) throws IOException, URISyntaxException {
        Dimension dimension = new Dimension(227, 227);
        try (ImageInputStream in = ImageIO.createImageInputStream(new URL(url).openStream())) {
            final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                try {
                    reader.setInput(in);
                    dimension = new Dimension(reader.getWidth(0), reader.getHeight(0));
                } finally {
                    reader.dispose();
                }
            }
        }
        return dimension;
    }

    /**
     * 上传文件, 自动生成KEY
     *
     * @param bytes     字节数组
     * @param folder    虚拟文件夹
     * @param extension 文件后缀名, 可为空
     * @return 上传成功, 返回文件key, 否则返回NULL
     */
    public String uploadWithAutoGeneratedKey(final byte[] bytes, String folder, String extension) throws Exception {
        if (folder == null || folder.isEmpty()) {
            throw new Exception("folder must be not null!");
        }
        folder = "/" + folder.replaceFirst("(^/*)|(/*$)", "") + "/";
        if (extension == null) {
            extension = "";
        }
        extension = "." + extension.replaceFirst("\\.", "");
        final String key = folder + StringUtil.reverse_shorten() + extension;
        Response response = executeUpload(() -> um.put(bytes, key, uptoken()));
        return response.isOK() ? key : null;
    }

    /**
     * 根据文件扩展名生成短链接形式的key (按时间顺序从小到大)
     *
     * @param filename 文件名
     * @return 用作上传与访问的key
     */
    public static String buildKey(String filename) throws InterruptedException {
        return StringUtil.reverse_shorten() + "." + FilenameUtils.getExtension(filename);
    }

    public static Response upload(final InputStream in, final String filename) throws InterruptedException {
        String key = buildKey(filename);
        return executeUpload(() -> um.put(in, key, uptoken(), null, null));
    }

    public static Response upload(final byte[] bytes, final String filename) throws InterruptedException {
        String key = buildKey(filename);
        return executeUpload(() -> um.put(bytes, key, uptoken()));
    }

    public static Response upload(final String filepath) throws InterruptedException {
        String key = buildKey(filepath);
        return executeUpload(() -> um.put(filepath, key, uptoken()));
    }

    public static FileListing list(String prefix, String marker, int limit, String delimiter) throws QiniuException {
        return bm.listFiles(namespace, prefix, marker, limit, delimiter);
    }

    /**
     * 根据前缀获取文件列表的迭代器
     *
     * @param prefix    文件名前缀
     * @param limit     每次迭代的长度限制，最大1000，推荐值 100
     * @param delimiter 指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
     * @return FileInfo迭代器
     */
    public static BucketManager.FileListIterator createFileListIterator(String prefix, int limit, String delimiter) {
        return bm.createFileListIterator(namespace, prefix, limit, delimiter);
    }

    public static void delete(final String... keys) {
        if (keys == null) return;
        new Thread(() -> {
            for (String key : keys) {
                try {
                    bm.delete(namespace, key);
                } catch (QiniuException ignore) {
                    // ignore errors
                }
            }
        }).start();
    }

    /**
     * 七牛音频格式持久化转换
     *
     * @param url       源音频
     * @param notifyURL 回调接口地址
     * @param formats   目标音频格式后缀名
     */
    public String audioConvert(String url, String notifyURL, String... formats) {

        if (formats == null || formats.length < 1) {
            throw new IllegalArgumentException("formats can not be null or empty");
        }

        String key = url.replaceFirst(QINIU_DOMAIN, "");
        String name = key.substring(0, key.lastIndexOf("."));


        //设置转码操作参数
        List<String> list = new ArrayList<>();
        for (String f : formats) {
            if (!f.equals(url.substring(url.lastIndexOf(".") + 1))) {
                list.add("avthumb/" + f + "|saveas/" + UrlSafeBase64.encodeToString(namespace + ":" + name + "." + f));
            }
        }

        StringMap params = new StringMap()
                .putNotEmpty("notifyURL", notifyURL) // 回调URL
                .putNotEmpty("pipeline", "audio"); // 设置 pipeline 参数
        try {
            String persistid = om.pfop(namespace, key, StringUtils.join(list, ";"), params);
            // 打印返回的persistid
            LOG.info("persistid:", persistid);
            return persistid;
        } catch (QiniuException e) {
            //捕获异常信息
            Response r = e.response;
            // 请求失败时简单状态信息
            LOG.error("qiniuexception", e);
            try {
                // 响应的文本信息
                LOG.error("qiniuexception body:", r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
        return null;
    }

    /**
     * 模板方法, 用于处理公共异常, 打印日志
     */
    private static Response executeUpload(QiniuUploadCallback<Response> callback) {
        Response res = null;
        try {
            res = callback.doUpload();
            LOG.info(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            LOG.error(r.toString());
            try { //响应的文本信息
                LOG.error(r.bodyString());
            } catch (Exception ignore) {
                // ignore errors
            }
        }
        return res;
    }

    /**
     * 回调接口, 实现该接口处理实际业务逻辑
     */
    private interface QiniuUploadCallback<T> {
        T doUpload() throws QiniuException;
    }
}