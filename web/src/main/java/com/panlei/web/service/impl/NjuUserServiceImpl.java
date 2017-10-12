package com.panlei.web.service.impl;

import com.panlei.web.dao.UserNjuMapper;
import com.panlei.web.model.UserNju;
import com.panlei.web.service.NjuUserService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by 361pa on 2017/7/21.
 */

@Service("njuUserService")
public class NjuUserServiceImpl implements NjuUserService {
    // 注意 itemsCustomMapper 已通过包自动扫描的方式注入到 IoC 容器中，
    // 所以此处可以通过 Autowired 自动注入

    private UserNjuMapper userNjuMapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setUserNjuMapper(UserNjuMapper userNjuMapper){
        this.userNjuMapper = userNjuMapper;
    }


    public String createUser(UserNju userNju)throws Exception {
        String cookie = loginBBS(userNju.getUserName(), userNju.getPasswd());
        if (cookie.equals("failure")){
            return "failure";
        }else {
            userNju.setCookie(cookie);
            userNjuMapper.insert(userNju);
            return "success";
        }
    }

    //成功：返回cookie
    //失败：返回failure
    public String loginBBS(String id, String pw){
        String sendCookie = null;
        try {
            Connection con = Jsoup.connect("http://bbs.nju.edu.cn/vd55599/bbslogin?type=2");
            con.userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");
            con.data("id",id);
            con.data("pw",pw);
            con.data("lasturl","");
            con.timeout(5000);
            Document doc = con.post(); //将获取到的内容打印出来
            String CookieString = doc.head().getElementsByIndexEquals(3).toString();
            System.out.println(CookieString);
            if (CookieString.length() > 0){
                int start = CookieString.indexOf("('");
                int end = CookieString.indexOf("')");
                CookieString = CookieString.substring(start + 2, end);
                int indexN = CookieString.indexOf("N");
                int indexP = CookieString.indexOf("+");
                int u_num = Integer.parseInt(CookieString.substring(0, indexN)) + 2;
                String u_id = CookieString.substring(indexN + 1, indexP );
                int u_key = Integer.parseInt(CookieString.substring(indexP + 1)) - 2;
                String footkey = "217872412";
                sendCookie="_U_NUM=" + u_num + ";_U_UID=" + u_id + ";_U_KEY=" + u_key + ";FOOTKEY=" + footkey;
                System.out.println(sendCookie);
            }else {
                System.out.println("failure");
                return "failure";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sendCookie;
    }
    public String getUserBind(UserNju userNju) throws Exception{
        UserNju userNjuReturn = userNjuMapper.selectUserByWebchatId(userNju.getWebchatID());
        if (userNjuReturn != null && userNjuReturn.getUserName() != null){
            return userNjuReturn.getUserName();
        }else {
            return "0";
        }
    }
    public String deleteUserBind(UserNju userNju) throws Exception{
        userNjuMapper.deleteUserByWebchatId(userNju.getWebchatID());
        return "succes";
    }
}
