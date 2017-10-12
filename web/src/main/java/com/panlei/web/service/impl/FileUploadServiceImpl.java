package com.panlei.web.service.impl;


import com.panlei.web.dao.UserNjuMapper;
import com.panlei.web.model.UserNju;
import com.panlei.web.service.FileUploadService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.Map;
import java.util.Set;


/**
 * Created by 361pa on 2017/9/7.
 */
@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private UserNjuMapper userNjuMapper;

    @Resource
    private NjuUserServiceImpl njuUserService;

    /**
     * 上传文件
     *
     * @param localFilePath
     *            本地文件路径
     * @param requestMap
     * @return
     * @throws Exception
     */
    public String uploadFile(String localFilePath, Map<String, String> requestMap)
            throws Exception {

        String imgUlr = null;
        UserNju userNju= userNjuMapper.selectUserByWebchatId(requestMap.get("webchatID"));
        String sendCookie = null;
        if (requestMap.get("number").equals("0")){
            sendCookie = njuUserService.loginBBS(userNju.getUserName(), userNju.getPasswd());
            userNjuMapper.updateUserByWebchatId(requestMap.get("webchatID"), sendCookie);
        }else{
            sendCookie = userNju.getCookie();
        }
        org.apache.http.client.HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost("http://bbs.nju.edu.cn/bbsdoupload");

        httpPost.setHeader("Cookie", sendCookie);
        File file = new File(localFilePath);
        try {
            MultipartEntity entity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);

            entity.addPart("up", new FileBody(file));
            try {
                entity.addPart("exp", new StringBody(""));

                entity.addPart("ptext", new StringBody("text"));
                entity.addPart("board", new StringBody(requestMap.get("boardName")));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            httpPost.setEntity(entity);

            HttpResponse response = null;
            try {
                response = httpClient.execute(httpPost,
                        localContext);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == org.apache.http.HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                InputStream inputStream = null;
                try {
                    inputStream = resEntity.getContent();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                BufferedReader br = null;
                try {
                    br = new BufferedReader(
                            new InputStreamReader(inputStream, "gbk"));
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                // nju_bbs160605153704.jpg
                String line = null;

                StringBuffer sb = new StringBuffer();

                try {
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                        sb.append("\n");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                String result = sb.toString();
                System.out.println("result2:" + result );

                result = result.replaceAll("\n", "");
                // 截取 &file=19068&name=njubbskdsadjkfa.jpg
                int start = result.indexOf("&file=");
                int end = result.indexOf("&exp=");
                result = result.substring(start, end);

                // bbsupload2?board=Pictures&file=2672&name=1.jpg&exp=&ptext=text
                // HTTP/1.1
                String url2 = "http://bbs.nju.edu.cn/bbsupload2?board=" + requestMap.get("boardName")
                        + result + "&exp=&ptext=text";
                System.out.print("url2:" + url2);
                Document doc = null;

                Connection con = Jsoup.connect(url2);
                con.userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");
                con.header("Host", "bbs.nju.edu.cn");
                con.header("Connection", "keep-alive");
                con.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                con.header("Accept-Encoding", "gzip,deflate,sdch");
                con.header("Accept-Language", "zh-CN,zh;q=0.8");
                con.header("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
                con.header("Cookie", sendCookie);

                doc = con.get(); //将获取到的内容打印出来
                Elements elScripts = doc.getElementsByTag("script");

                int imgStart = elScripts.get(1).toString().indexOf("\\n");
                int imgEnd = elScripts.get(1).toString().lastIndexOf("\\n");

                imgUlr = elScripts.get(1).toString().substring(imgStart + 2, imgEnd);
                System.out.println(imgUlr);

            }
        } catch (Exception e) {
            e.printStackTrace();
        };
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return imgUlr;
    }

    /**
     * 设置上传文件时所附带的其他参数
     *
     * @param multipartEntityBuilder
     * @param params
     */
    private void setUploadParams(MultipartEntityBuilder multipartEntityBuilder,
                                 Map<String, String> params) {
        if (params != null && params.size() > 0) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                multipartEntityBuilder
                        .addPart(key, new StringBody(params.get(key),
                                ContentType.TEXT_PLAIN));
            }
        }
    }
    /**
     * 将返回结果转化为String
     *
     * @param entity
     * @return
     * @throws Exception
     */
    private String getRespString(HttpEntity entity) throws Exception {
        if (entity == null) {
            return null;
        }
        InputStream is = entity.getContent();
        StringBuffer strBuf = new StringBuffer();
        byte[] buffer = new byte[40960];
        int r = 0;
        while ((r = is.read(buffer)) > 0) {
            strBuf.append(new String(buffer, 0, r, "UTF-8"));
        }
        return strBuf.toString();
    }

}
