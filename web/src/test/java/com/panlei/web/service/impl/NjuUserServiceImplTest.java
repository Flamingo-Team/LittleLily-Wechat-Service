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
public class NjuUserServiceImplTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void loginBBS() throws Exception {
        try {
            //Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
            Connection con = Jsoup.connect("http://bbs.nju.edu.cn/vd55599/bbslogin?type=2").userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0").timeout(3000);
            con.data("id","Jeson");
            con.data("pw","panlei110");
            con.data("lasturl","");
            Document doc = con.post(); //将获取到的内容打印出来
            String CookieString = doc.head().getElementsByIndexEquals(3).toString();
            System.out.println(CookieString);
            if (CookieString.length() > 0){
                int start = CookieString.indexOf("('");
                int end = CookieString.indexOf("')");
                CookieString = CookieString.substring(start + 2, end);
                System.out.println(CookieString);
                int indexN = CookieString.indexOf("N");
                int indexP = CookieString.indexOf("+");
                String u_num = CookieString.substring(0, indexN);
                String u_id = CookieString.substring(indexN + 1, indexP );
                String u_key = CookieString.substring(indexP+1);
                String footkey = "217872412";
                String sendCookie="_U_NUM=" + u_num + ";_U_UID=" + u_id + ";_U_KEY=" + u_key + ";FOOTKEY=" + footkey;
                System.out.println(sendCookie);
            }else {
                System.out.println("failure");
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("hll");

    }

}