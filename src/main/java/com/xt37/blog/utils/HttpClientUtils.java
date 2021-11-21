package com.xt37.blog.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * HttpClientUtils
 */
@SuppressWarnings("deprecation")
public class HttpClientUtils {
    //默认连接超时时间，单位ms
    public static final int CONNTIMEOUT = 60 * 1000;
    //默认读取响应超时时间，单位ms
    public static final int READTIMEOUT = 60 * 1000;
    //默认字符编码
    public static final String CHARSET = "UTF-8";
    //默认mime类型
    public static final String MIMETYPE_URLENCODE = "application/x-www-form-urlencoded";
    public static final String MIMETYPE_JSON = "application/json";
    //全局HttpClient
    private static HttpClient client = null;

    //静态初始化httpclient
    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(128);
        cm.setDefaultMaxPerRoute(128);
        client = HttpClients.custom().setConnectionManager(cm).build();
    }

    /**
     * 描述: http or https post请求（body体参数）
     * 参数：(参数列表)
     *
     * @param url--请求链接
     * @param parameterStr--body体参数json的string格式
     * @return
     * @throws ConnectTimeoutException
     * @throws SocketTimeoutException
     * @throws Exception
     */
    public static String sendPost(String url, String parameterStr) throws ConnectTimeoutException, SocketTimeoutException, Exception {
        return post(url, parameterStr, MIMETYPE_JSON, CHARSET, null, CONNTIMEOUT, READTIMEOUT);
    }

    /**
     * 描述: http or https post请求（body体参数）
     * 参数：(参数列表)
     *
     * @param url--请求链接
     * @param parameterStr--body体参数json的string格式
     * @param mimeType--消息类型
     * @return
     * @throws ConnectTimeoutException
     * @throws SocketTimeoutException
     * @throws Exception
     */
    public static String sendPost(String url, String parameterStr, String mimeType) throws ConnectTimeoutException, SocketTimeoutException, Exception {
        return post(url, parameterStr, mimeType, CHARSET, null, CONNTIMEOUT, READTIMEOUT);
    }

    /**
     * 描述: http or https post请求（body体参数）
     * 参数：(参数列表)
     *
     * @param url--请求链接
     * @param parameterStr--body体参数json的string格式
     * @param mimeType--消息类型
     * @param charset--字符编码
     * @param headers--请求头部参数
     * @param connTimeout--连接超时时间
     * @param readTimeout--响应超时时间
     * @return
     * @throws ConnectTimeoutException
     * @throws SocketTimeoutException
     * @throws Exception
     */
    public static String sendPost(String url, String parameterStr, String mimeType, String charset, Map<String, String> headers, Integer connTimeout, Integer readTimeout) throws ConnectTimeoutException, SocketTimeoutException, Exception {
        return post(url, parameterStr, mimeType, charset, headers, connTimeout, readTimeout);
    }

    /**
     * 描述: http or https post请求（form表单）
     * 参数：(参数列表)
     *
     * @param url--请求链接
     * @param params--form表单参数
     * @return
     * @throws ConnectTimeoutException
     * @throws SocketTimeoutException
     * @throws Exception
     */
    public static String sendPost(String url, Map<String, String> params) throws ConnectTimeoutException,
            SocketTimeoutException, Exception {
        return postForm(url, params, null, CONNTIMEOUT, READTIMEOUT);
    }

    /**
     * 描述: http or https post请求（form表单）
     * 参数：(参数列表)
     *
     * @param url--请求链接
     * @param params--form表单参数
     * @param headers--请求头部参数
     * @return
     * @throws ConnectTimeoutException
     * @throws SocketTimeoutException
     * @throws Exception
     */
    public static String sendPost(String url, Map<String, String> params, Map<String, String> headers) throws ConnectTimeoutException,
            SocketTimeoutException, Exception {
        return postForm(url, params, headers, CONNTIMEOUT, READTIMEOUT);
    }

    /**
     * 描述: http or https post请求（form表单）
     * 参数：(参数列表)
     *
     * @param url--请求链接
     * @param params--form表单参数
     * @param headers--请求头部参数
     * @param connTimeout--连接超时时间
     * @param readTimeout--响应超时时间
     * @return
     * @throws ConnectTimeoutException
     * @throws SocketTimeoutException
     * @throws Exception
     */
    public static String sendPost(String url, Map<String, String> params, Map<String, String> headers, Integer connTimeout, Integer readTimeout) throws ConnectTimeoutException,
            SocketTimeoutException, Exception {
        return postForm(url, params, headers, connTimeout, readTimeout);
    }

    /**
     * 描述: http or https get请求（不指定字符编码）
     * 参数：(参数列表)
     *
     * @param url--请求链接
     * @return
     * @throws Exception
     */
    public static String sendGet(String url) throws Exception {
        return get(url, CHARSET, null, null, null);
    }

    /**
     * 描述: http or https get请求（指定字符编码）
     * 参数：(参数列表)
     *
     * @param url--请求链接
     * @param charset--字符编码
     * @return
     * @throws Exception
     */
    public static String sendGet(String url, String charset) throws Exception {
        return get(url, charset, null, CONNTIMEOUT, READTIMEOUT);
    }

    /**
     * 描述: http or https get请求 (指定请求头)
     * 参数：(参数列表)
     *
     * @param url--请求链接
     * @param charset--字符编码
     * @param headers--请求头键值对
     * @return
     * @throws Exception
     */
    public static String sendGet(String url, String charset, Map<String, String> headers) throws Exception {
        return get(url, charset, headers, CONNTIMEOUT, READTIMEOUT);
    }

    /**
     * 描述: http or https get请求（指定字符编码和超时等待时间）
     * 参数：(参数列表)
     *
     * @param url--请求链接
     * @param charset--字符编码
     * @param headers--请求头键值对
     * @param connTimeout--连接超时时间
     * @param readTimeout--响应超时时间
     * @return
     * @throws Exception
     */
    public static String sendGet(String url, String charset, Map<String, String> headers, Integer connTimeout, Integer readTimeout) throws Exception {
        return get(url, charset, headers, connTimeout, readTimeout);
    }

    /**
     * 描述: http or https post请求处理方法(requestbody传参)
     * 参数：(参数列表)
     *
     * @param url--请求链接
     * @param body--RequestBody
     * @param mimeType--消息类型：例如     application/xml "application/x-www-form-urlencoded"
     * @param charset--字符编码
     * @param headers--请求头键值对
     * @param connTimeout--连接超时时间
     * @param readTimeout--读取响应超时时间
     * @return
     * @throws ConnectTimeoutException
     * @throws SocketTimeoutException
     * @throws Exception
     */
    public static String post(String url, String body, String mimeType, String charset, Map<String, String> headers, Integer connTimeout, Integer readTimeout) {
        HttpClient client = createClient(url);
        HttpPost post = new HttpPost(url);
        String result = "";
        try {
            if (StringUtils.isNotBlank(body)) {
                HttpEntity entity = new StringEntity(body, ContentType.create(mimeType, charset));
                post.setEntity(entity);
            }
            if (headers != null && !headers.isEmpty()) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    post.addHeader(entry.getKey(), entry.getValue());
                }
            }
            //设置配置参数
            Builder customReqConf = RequestConfig.custom();
            if (connTimeout != null) {
                customReqConf.setConnectTimeout(connTimeout);
            }
            if (readTimeout != null) {
                customReqConf.setSocketTimeout(readTimeout);
            }
            post.setConfig(customReqConf.build());
            result = responseText(client.execute(post));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            post.releaseConnection();
            if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
                try {
                    ((CloseableHttpClient) client).close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 描述: http or https post请求处理方法(form表单传参)
     * 参数：(参数列表)
     *
     * @param url--请求链接
     * @param params--参数键值对
     * @param headers--请求头键值对
     * @param connTimeout--连接超时时间
     * @param readTimeout--响应超时时间
     * @return
     * @throws ConnectTimeoutException
     * @throws SocketTimeoutException
     * @throws Exception
     */
    public static String postForm(String url, Map<String, String> params, Map<String, String> headers, Integer connTimeout, Integer readTimeout) {
        String result = "";
        HttpClient client = createClient(url);
        HttpPost post = new HttpPost(url);
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> formParams = new ArrayList<NameValuePair>();
                Set<Entry<String, String>> entrySet = params.entrySet();
                for (Entry<String, String> entry : entrySet) {
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
                post.setEntity(entity);
            }

            if (headers != null && !headers.isEmpty()) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    post.addHeader(entry.getKey(), entry.getValue());
                }
            }
            // 设置参数
            Builder customReqConf = RequestConfig.custom();
            if (connTimeout != null) {
                customReqConf.setConnectTimeout(connTimeout);
            }
            if (readTimeout != null) {
                customReqConf.setSocketTimeout(readTimeout);
            }
            post.setConfig(customReqConf.build());
            result = responseText(client.execute(post));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            post.releaseConnection();
            if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
                try {
                    ((CloseableHttpClient) client).close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 描述: http or https get请求处理方法
     * 参数：(参数列表)
     *
     * @param url--请求链接
     * @param charset--字符编码
     * @param headers--请求头键值对
     * @param connTimeout--连接超时时间
     * @param readTimeout--响应超时时间
     * @return
     * @throws ConnectTimeoutException
     * @throws SocketTimeoutException
     * @throws Exception
     */
    public static String get(String url, String charset, Map<String, String> headers, Integer connTimeout, Integer readTimeout) {

        HttpClient client = createClient(url);
        HttpGet get = new HttpGet(url);
        String result = "";
        if (headers != null && !headers.isEmpty()) {
            for (Entry<String, String> entry : headers.entrySet()) {
                get.addHeader(entry.getKey(), entry.getValue());

            }
        }
        try {
            // 设置参数
            Builder customReqConf = RequestConfig.custom();
            if (connTimeout != null) {
                customReqConf.setConnectTimeout(connTimeout);
            }
            if (readTimeout != null) {
                customReqConf.setSocketTimeout(readTimeout);
            }
            get.setConfig(customReqConf.build());

            result = responseText(client.execute(get));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            get.releaseConnection();
            if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
                try {
                    ((CloseableHttpClient) client).close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String doGet(String url, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClientUtils.createSSLClientDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "utf-8");

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }


    public static CloseableHttpClient createSSLClientDefault() {
        try {
            //使用 loadTrustMaterial() 方法实现一个信任策略，信任所有证书
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            //NoopHostnameVerifier类:  作为主机名验证工具，实质上关闭了主机名验证，它接受任何
            //有效的SSL会话并匹配到目标主机。
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();

    }


    /**
     * 描述: http or https 文件中转上传
     * 参数：(参数列表)
     *
     * @param serverUrl
     * @param file
     * @param uploadFieldName
     * @param params
     * @return
     */
    public static String httpClientUpload(String serverUrl, MultipartFile file, String uploadFieldName, Map<String, String> params) {
        String result = "";
        HttpClient client = createClient(serverUrl);
        // 请求处理url
        HttpPost post = new HttpPost(serverUrl);
        try {
            // 创建待处理的文件
            String fileName = file.getOriginalFilename();
            ContentBody files = new ByteArrayBody(file.getBytes(), fileName);
            // 对请求的表单域进行填充
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart(uploadFieldName, files);
            if (params != null && !params.isEmpty()) {
                for (Entry<String, String> item : params.entrySet()) {
                    reqEntity.addPart(
                            item.getKey(),
                            new StringBody(URLEncoder.encode(
                                    String.valueOf(item.getValue()), CHARSET)));
                }
            }
            // 设置请求
            post.setEntity(reqEntity);
            result = responseText(client.execute(post));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            post.releaseConnection();
            if (serverUrl.startsWith("https") && client != null
                    && client instanceof CloseableHttpClient) {
                try {
                    ((CloseableHttpClient) client).close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 描述: 创建 SSL连接（绕过证书）
     * 参数：(参数列表)
     *
     * @return
     * @throws GeneralSecurityException
     */
    private static CloseableHttpClient createSSLInsecureClient() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslContext, new X509HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                @Override
                public void verify(String host, SSLSocket ssl)
                        throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert)
                        throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns,
                                   String[] subjectAlts) throws SSLException {
                }

            });

            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 描述: get请求url参数拼接
     * 参数：(参数列表)
     *
     * @param url
     * @param params
     * @return
     */
    public static String concatGetUrl(String url, Map<String, Object> params) {
        StringBuffer buf = new StringBuffer(url);
        if (params != null) {
            if (!url.contains("?")) {
                buf.append("?");
            }
            try {
                for (Entry<String, Object> item : params.entrySet()) {
                    buf.append("&" + item.getKey() + "=" + URLEncoder.encode(String.valueOf(item.getValue()), "UTF-8"));
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        return buf.toString();
    }

    /**
     * 描述: http响应
     * 参数：(参数列表)
     *
     * @param res
     * @return
     */
    public static String responseText(HttpResponse res) {
        try {
            if (HttpStatus.SC_OK == res.getStatusLine().getStatusCode()) {
                HttpEntity entity = res.getEntity();
                return EntityUtils.toString(entity, CHARSET);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 描述: 创建httpclient
     * 参数：(参数列表)
     *
     * @param url
     * @return
     */
    public static HttpClient createClient(String url) {
        if (url.startsWith("https")) {
            // 执行 Https 请求.
            return createSSLInsecureClient();
        } else {
            // 执行 Http 请求.
            return HttpClientUtils.client;
        }
    }

}
