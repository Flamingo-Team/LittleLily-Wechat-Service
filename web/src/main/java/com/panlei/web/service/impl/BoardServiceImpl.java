package com.panlei.web.service.impl;

import com.panlei.web.dao.BoardMapper;
import com.panlei.web.dao.UpvoteMapper;
import com.panlei.web.dao.UserNjuMapper;
import com.panlei.web.model.Board;
import com.panlei.web.model.Dock;
import com.panlei.web.model.UserNju;
import com.panlei.web.service.BoardService;
import com.panlei.web.utils.Utils;


import com.vdurmont.emoji.EmojiParser;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;


import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;


import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 361pa on 2017/6/19.
 */
@Service("boardService")
public class BoardServiceImpl implements BoardService {

    private BoardMapper boardMapper;
    private UpvoteMapper upvoteMapper;
    @Resource
    private UserNjuMapper userNjuMapper;
    @Resource
    private NjuUserServiceImpl njuUserService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setBoardMapper(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setUpvoteMapper(UpvoteMapper upvoteMapper) {
        this.upvoteMapper = upvoteMapper;
    }

    public List<Board> getBoardsBySectionValue(String value) throws Exception {
        return boardMapper.getBoardsBySectionValue(value);
    }

    public List<Board> getAllBoards() throws Exception {
        return boardMapper.getAllBoards();
    }

    public List<Dock> getBoardsByBoardName(String boardName) {

        String urlBoard = "http://bbs.nju.edu.cn/bbstdoc?board=" + boardName;
        Document boardDoc = null;
        try {
            //boardDoc = Jsoup.connect(urlBoard).get();
            boardDoc = Jsoup.parse(new URL(urlBoard).openStream(), "gbk", urlBoard);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("&&&&&" + e.getMessage());
        }
        Elements trs = boardDoc.select("table").select("tr");
        List resultList = new ArrayList<Dock>();
        for (int i = 0; i < trs.size(); i++) {
            Elements tds = trs.get(i).select("td");
            if (i == 0) {
                continue;
            }

            Dock dock = new Dock();
            for (int j = 0; j < tds.size(); j++) {
                String text = tds.get(j).text();
                //text = Utils.convertColorTag(text);
                System.out.print(j + ">" + text);
                switch (j) {
                    case 0:
                        if (text.length() > 0) {
                            dock.setNumber(text);
                        }
                        break;
                    case 1:
                        if (text.length() > 0) {
                            dock.setTop(true);
                        } else {
                            dock.setTop(false);
                        }
                        break;
                    case 2:
                        dock.setAuthor(text);
                        break;
                    case 3:
                        dock.setCreateTime(text);
                        break;
                    case 4:
                        String href = null;
                        String pattern = "href=\"([^\"]*)\"";
                        Pattern pKey = Pattern.compile(pattern, 2 | Pattern.DOTALL);
                        Matcher mKey = pKey.matcher(tds.get(j).toString());
                        if (mKey.find()) {
                            //System.out.print(mKey.group(1).replace(";", "&"));
                            href = "http://bbs.nju.edu.cn/" + mKey.group(1).replace(";", "&");
                            int start = href.indexOf("?");
                            dock.setUrl(href.substring(start + 1));

                        }
                        //System.out.print(dock.getUrl());
                        dock.setTitle(text);
                        break;
                    case 5:
                        if (text.indexOf("/") > 0) {
                            dock.setReplyNumber(text.split("/")[0]);
                            dock.setWatchNumber(text.split("/")[1]);
                        } else {
                            dock.setWatchNumber(text);
                        }
                        //dock.setReplyNumber(text);
                        break;
                }
                //System.out.println(text);
            }
            resultList.add(dock);

        }
        Collections.reverse(resultList);

        System.out.print(resultList);
        return resultList;
    }

    public List<Dock> getBoardsByBoardName(String boardName, String numberFrom) {
        String urlBoard;
        if (numberFrom.equals("latest")) {
            urlBoard = "http://bbs.nju.edu.cn/bbstdoc?board=" + boardName;
        } else {
            urlBoard = "http://bbs.nju.edu.cn/bbstdoc?board=" + boardName + "&start=" + numberFrom;
        }

        Document boardDoc = null;
        try {
            //boardDoc = Jsoup.connect(urlBoard).get();
            //boardDoc = Jsoup.connect(urlBoard).get();
            boardDoc = Jsoup.parse(new URL(urlBoard).openStream(), "gbk", urlBoard);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("&&&&&" + e.getMessage());
        }
        Elements trs = boardDoc.select("table").select("tr");
        List resultList = new ArrayList<Dock>();
        for (int i = 0; i < trs.size(); i++) {
            Elements tds = trs.get(i).select("td");
            if (i == 0) {
                continue;
            }

            Dock dock = new Dock();
            for (int j = 0; j < tds.size(); j++) {
                String text = tds.get(j).text();
                //text = Utils.convertColorTag(text);
                System.out.print(j + ">" + text);
                switch (j) {
                    case 0:
                        if (text.length() > 0) {
                            dock.setNumber(text);
                        }
                        break;
                    case 1:
                        if (text.length() > 0) {
                            dock.setTop(true);
                        } else {
                            dock.setTop(false);
                        }
                        break;
                    case 2:
                        dock.setAuthor(text);
                        break;
                    case 3:
                        dock.setCreateTime(text);
                        break;
                    case 4:
                        String href = null;
                        String pattern = "href=\"([^\"]*)\"";
                        Pattern pKey = Pattern.compile(pattern, 2 | Pattern.DOTALL);
                        Matcher mKey = pKey.matcher(tds.get(j).toString());
                        if (mKey.find()) {
                            //System.out.print(mKey.group(1).replace(";", "&"));
                            href = "http://bbs.nju.edu.cn/" + mKey.group(1).replace(";", "&");
                            int start = href.indexOf("?");
                            dock.setUrl(href.substring(start + 1));

                        }
                        //System.out.print(dock.getUrl());
                        dock.setTitle(text);
                        break;
                    case 5:
                        if (text.indexOf("/") > 0) {
                            dock.setReplyNumber(text.split("/")[0]);
                            dock.setWatchNumber(text.split("/")[1]);
                        } else {
                            dock.setWatchNumber(text);
                        }
                        //dock.setReplyNumber(text);
                        break;
                }
                //System.out.println(text);

            }
            if (dock.getTop() == true) {
                continue;
            }
            dock.setUpvoteNumber(String.valueOf(upvoteMapper.selectUpvoteByTopicID(dock.getUrl()).size()));

            resultList.add(dock);

        }

        Collections.reverse(resultList);
        System.out.print(resultList);
        return resultList;
    }

    public String postWritePost(String boardName, String title, String pid, String reid,
                                String text, String webChatId, String havePictures, boolean isReply) {
        System.out.println("postWritePost():" + "  title->" + title + "  text->" + text);

        UserNju userNju = userNjuMapper.selectUserByWebchatId(webChatId);
        String sendCookie = null;
        if (havePictures.equals("0") && !isReply) {
            sendCookie = njuUserService.loginBBS(userNju.getUserName(), userNju.getPasswd());
            userNjuMapper.updateUserByWebchatId(webChatId,sendCookie);
        } else {
            sendCookie = userNju.getCookie();
        }
        String status = "";
        try {
//            Connection con = Jsoup.connect("http://bbs.nju.edu.cn/vd55599/bbssnd?board=" + boardName);
//            con.userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");
//            con.header("Host", "bbs.nju.edu.cn");
//            con.header("Connection", "keep-alive");
//            con.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//            con.header("Accept-Encoding", "gzip,deflate,sdch");
//            con.header("Accept-Language", "zh-CN,zh;q=0.8");
//            con.header("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
//            con.header("Cookie", sendCookie);
//            EmojiParser emojiParser = new EmojiParser();
//
//            con.data("title", title);
//            con.data("pid", pid);
//            con.data("reid", reid);
//            //con.data("reusr","Jeson");
//            con.data("signature", "1");
//            con.data("autocr", "on");
//
//            text = emojiParser.parseToHtmlHexadecimal(text);
//            con.data("text", text);
//
//            con.timeout(5000);
//            Document doc = con.post("gbk"); //将获取到的内容打印出来
//            String docString = doc.toString();
//            System.out.println(docString);

            /////////////////////////////////////////////////////////用httpClient发送
             List <InetAddress> ipList = Utils.getIps();

             //for (InetAddress address :ipList){
                 //System.out.println("send ip = " + address.getHostAddress());
            //}

            System.out.println("ipList = " + ipList.size());

            InetAddress sendIp = ipList.get(0);
            System.out.println("0");
            CloseableHttpClient httpClient = HttpClients.createDefault();
            System.out.println("1");

            RequestConfig defaultRequestConfig = RequestConfig.custom()//
                    .setSocketTimeout(2000)// socket 超时设置
                    .setConnectTimeout(2000)// 链接超时设置
                    .setLocalAddress(sendIp)
                    .build();
            System.out.println("2");

            //httpClient.getParams().setParameter(ConnRouteParams.LOCAL_ADDRESS,sendIp.getHostAddress());
            EmojiParser emojiParser = new EmojiParser();
            //httpClient.getHostConfiguration().setLocalAddress(sendIp);
            System.out.println("send IP = " + sendIp.getHostAddress());
            String url = "http://bbs.nju.edu.cn/vd55599/bbssnd?board=" + boardName;
            HttpPost httpPost = new HttpPost(url);
            Map<String,String> content = new HashMap<String,String>();
            content.put("title",title);
            content.put("pid",pid);
            content.put("reid",reid);
            content.put("signature","1");
            content.put("autocr","on");
            text = emojiParser.parseToHtmlHexadecimal(text);
            content.put("text",text);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : content.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // JSONObject json = new JSONObject(content);
            //StringEntity entity = new StringEntity();
            //System.out.println(json.toString());
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "gbk"));
            //entity.setContentEncoding("gbk");
            //entity.setContentType("application/x-www-form-urlencoded");
            //httpPost.setEntity(entity);
            httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");
            httpPost.setHeader("Host","bbs.nju.edu.cn");
            httpPost.setHeader("Connection","keep-alive");
            httpPost.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpPost.setHeader("Accept-Encoding","gzip,deflate,sdch");
            httpPost.setHeader("Accept-Language","zh-CN,zh;q=0.8");
            httpPost.setHeader("Accept-Charset","GBK,utf-8;q=0.7,*;q=0.3");
            httpPost.setHeader("Cookie",sendCookie);
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");

            httpPost.setConfig(defaultRequestConfig);

            System.out.println(httpPost.toString() + EntityUtils.toString(httpPost.getEntity(),"UTF-8"));

            HttpResponse res = httpClient.execute(httpPost);

            System.out.println(res.toString());
            HttpEntity he = res.getEntity();
            String respContent = EntityUtils.toString(he,"UTF-8");
            System.out.println("resp  = " + respContent);
           // String docString = res.toString();


            if (respContent.indexOf("javascript:history.go(-1))") > -1) {
                status = "failure";
            } else {
                status = "success";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        return status;
    }

    public String postWriteReply(String boardName,String url, String title, String text, String webChatId, String havePictures) {

        UserNju userNju = userNjuMapper.selectUserByWebchatId(webChatId);
        String sendCookie = null;
        if (havePictures.equals("0")) {
            sendCookie = njuUserService.loginBBS(userNju.getUserName(), userNju.getPasswd());
            userNjuMapper.updateUserByWebchatId(webChatId, sendCookie);
        } else {
            sendCookie = userNju.getCookie();
        }
        int startFileNumber = url.indexOf("M.");
        int endFileNumber = url.indexOf(".A");
        String fileNumber = url.substring(startFileNumber, endFileNumber+2);
        String status = "";
        try {

            String urlstr = "http://bbs.nju.edu.cn/vd55599/bbspst?board=" + boardName +"&file=" + fileNumber;
            URL urltmp = new URL(urlstr);
            HttpURLConnection con = (HttpURLConnection)urltmp.openConnection();
            //con.setRequestMethod("GET");
            //con.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");
            //con.setRequestProperty("Host", "bbs.nju.edu.cn");
            //con.setRequestProperty("Connection", "keep-alive");
            //con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            //con.setRequestProperty("Accept-Encoding", "gzip,deflate");
            //con.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6,en;q=0.4");
            con.setRequestProperty("Cookie", sendCookie);
            Document doc = Jsoup.parse(con.getInputStream(), "gbk", urlstr);

            //Connection con = Jsoup.connect("http://bbs.nju.edu.cn/vd55599/bbspst?board=" + boardName +"&file=" + fileNumber);
            //con.userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");
            //con.header("Host", "bbs.nju.edu.cn");
            //con.header("Connection", "keep-alive");
            //con.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            //con.header("Accept-Encoding", "gzip,deflate,sdch");
            //con.header("Accept-Language", "zh-CN,zh;q=0.8");
            //con.header("Cookie", sendCookie);
            //con.timeout(5000);
            //Document doc = con.get("gb2312"); //将获取到的内容打印出来


            String docString = doc.toString();
            System.out.println(docString);

            Elements inputs = doc.select("input[type]"); //带有href属性的a元素

            title = inputs.get(0).toString();
            int startTitle = title.indexOf("value=");
            title = title.substring(startTitle+7, title.length()-2);

            String pid = inputs.get(1).toString();
            int startPid = pid.indexOf("value=");
            pid = pid.substring(startPid+7, pid.length()-2);

            String reid = inputs.get(2).toString();
            int startReid = reid.indexOf("value=");
            reid = reid.substring(startReid+7, reid.length()-2);

            String reusr = inputs.get(3).toString();
            int startReusr = reusr.indexOf("value=");
            reusr = reusr.substring(startReusr+7, reusr.length()-2);



            if (docString.indexOf("javascript:history.go(-1))") > -1) {
                status = "failure";
            } else {
                status = "success";
            }



            postWritePost(boardName,title,pid,reid,text,webChatId,havePictures, true);
           // http://bbs.nju.edu.cn/vd55507/bbssnd?board=test


        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

}
