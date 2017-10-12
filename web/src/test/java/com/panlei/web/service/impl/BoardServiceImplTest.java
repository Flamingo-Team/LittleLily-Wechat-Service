package com.panlei.web.service.impl;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by 361pa on 2017/7/22.
 */
public class BoardServiceImplTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void postWritePost() throws Exception {

        String sendCookie = "_U_NUM=460;_U_UID=Jeson;_U_KEY=67212179;FOOTKEY=217872412";
        try {
            Connection con = Jsoup.connect("http://bbs.nju.edu.cn/vd36389/bbssnd?board=Pictures");
            con.userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");
            con.header("Host", "bbs.nju.edu.cn");
            con.header("Connection", "keep-alive");
            con.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            con.header("Accept-Encoding", "gzip,deflate,sdch");
            con.header("Accept-Language", "zh-CN,zh;q=0.8");
            con.header("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
            con.header("Cookie", sendCookie);
            con.data("title", "test1");
            con.data("pid", "0");
            con.data("reid", "0");
            con.data("signature", "1");
            con.data("autocr", "on");
            con.data("text", "testbody");

            con.timeout(5000);
            Document doc = con.post(); //将获取到的内容打印出来
            String CookieString = doc.toString();
            System.out.println(CookieString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}