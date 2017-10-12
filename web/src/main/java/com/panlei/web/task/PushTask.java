package com.panlei.web.task;


import com.panlei.web.model.MsgInfo;
import com.panlei.web.model.Top10;
import com.panlei.web.service.BbsService;
import com.panlei.web.service.MsgInfoService;
import com.panlei.web.utils.ApplicationContextUtil;
import com.panlei.web.utils.HttpRequest;
import com.panlei.web.utils.Utils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;



import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lxl on 2017/9/19.
 */
@Component
public class PushTask {
    @Scheduled(cron = "0 0 12 * * ?")
    //@Scheduled(cron="0/20 * * * * ? ")
    public void taskCycle(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
        MsgInfoService msgService = (MsgInfoService) ac.getBean("msgInfoService");
        List<MsgInfo>  msgInfos = msgService.getMsgInfoList();
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        int i = 1;
        List<String> contents = getContent();
        for(MsgInfo msgInfo : msgInfos){
            //System.out.println("sendMsgmInfo  "+ i + "  openid:" + msgInfo.getOpenId() + "  formId:" +  msgInfo.getFormId());
            //i++;
            //String openid =  msgInfo.getOpenId();
            //String formid = msgInfo.getFormId();
            //boolean reuslt = sendMsg(openid,formid,contents);
            //System.out.println("sendMsg result = " + reuslt);
           // if(msgInfo.getOpenId().equals("oz7_90EPbrrtCQ512-eJFILQkL3w") || msgInfo.getOpenId().equals("oz7_90HiI5d2aZ_VhsZmjhJAd-XU") || msgInfo.getOpenId().equals("oz7_90OOM2XVJ3gQ5vIVCHlMXkCE")){
                cachedThreadPool.execute(new sendMsgThread(msgInfo.getOpenId(),msgInfo.getFormId(),contents));
           // }
        }
    }

    public class sendMsgThread implements Runnable{
        String openid;
        String formid;
        List<String> contents;

         sendMsgThread(String openid, String formid, List<String> contents) {
            this.openid = openid;
            this.formid = formid;
            this.contents = contents;
        }

        public void run() {
            System.out.println("sendMsgmInfo  openid:" + openid + "  formId:" +  formid);
            boolean reuslt = sendMsg(openid,formid,contents);
            System.out.println("sendMsg result = " + reuslt);
        }
    }



    public String getAccessToken(){
        //????????????   (???????????????????)
        String wxspAppid = "wxa7ff67769340f01d";
        //??????? app secret (???????????????????)
        String wxspSecret = "f1f8f6a1250d935f0ba24b51a4f0c507";
        //?????????
        String grant_type = "client_credential";
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&grant_type=" + grant_type;
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token", params);
        JSONObject json = new  JSONObject(sr);
        String access_token = json.getString("access_token");
        //int expires_in = json.getInt("expires_in");
        return access_token;
    }

    public List<String> getContent(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
        BbsService bbsService = (BbsService) ac.getBean("bbsService");
        Map<String, Object> results = bbsService.getTop10();
        List resultList;
        resultList = (List) results.get("top10");
        int total = resultList.size();
        String remark = "";
        for (int i = 0; i < total; i++) {
            Top10 top = (Top10) resultList.get(i);
            remark = remark + (i + 1) + ". " + top.getTitle() + "\n";
        }
        List<String> list = new ArrayList<String>() ;
        list.add(remark);
        list.add(total+"");
        return list;
    }

    public boolean sendMsg(String openid,String formid,List<String> contents){
       // BbsService bbsService = (BbsService) ApplicationContextUtil.getBean("bbsService");
        try {
            String token = getAccessToken();
            String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + token;
            Msg msg = new Msg();
            Data data = new Data();
            msg.setTouser(openid);
            msg.setTemplate_id("qUE9QoVSr8IF0Q8vtLpIAJtbI3jMCyj_UlqhE-f8Hao");
            msg.setForm_id(formid);
            Keyword keyword1 = new Keyword();
            keyword1.setValue(Utils.getCurrentDay().toString());
            keyword1.setColor("#000093");
            data.setKeyword1(keyword1);
            Keyword keyword2 = new Keyword();
            keyword2.setValue("今日十大");
            keyword2.setColor("#000093");
            data.setKeyword2(keyword2);

            Keyword keyword3 = new Keyword();
            keyword3.setValue(contents.get(0));
            keyword3.setColor("#000093");
            data.setKeyword3(keyword3);

            Keyword keyword4 = new Keyword();
            keyword4.setValue("热门十大");
            keyword4.setColor("#000093");
            data.setKeyword4(keyword4);

            Keyword keyword5 = new Keyword();
            keyword5.setValue(contents.get(1));
            keyword5.setColor("#000093");
            data.setKeyword5(keyword5);

            msg.setData(data);
            msg.setEmphasis_keyword("");
            JSONObject json1 = new JSONObject(msg);
            System.out.println(json1.toString());
            HttpPost httpPost = new HttpPost(url);
            CloseableHttpClient client = HttpClients.createDefault();

            String respContent = null;
            StringEntity entity = new StringEntity(json1.toString(),"utf-8");
            entity.setContentEncoding("utf-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            HttpResponse resp = client.execute(httpPost);
            //System.out.println(httpPost.getEntity().toString());
            System.out.println(resp.toString());
            System.out.println("sendMsg response =  " + EntityUtils.toString(resp.getEntity(),"UTF-8"));
            if(resp.getStatusLine().getStatusCode() == 200){
                HttpEntity he = resp.getEntity();
                respContent = EntityUtils.toString(he,"UTF-8");
                json1 = new JSONObject(respContent);
                if(json1.optString("errmsg").equals("ok")){
                    return true;
                }
            }
            return false;
        }catch(IOException e){
            e.getStackTrace();
            return false;
        }
    }

}
