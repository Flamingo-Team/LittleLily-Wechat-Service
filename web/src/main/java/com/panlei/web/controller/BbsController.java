package com.panlei.web.controller;


import com.panlei.web.model.Upvote;
import com.panlei.web.service.BbsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 361pa on 2017/6/20.
 */
@Controller
@RequestMapping("/bbs")
public class BbsController {

    @Autowired
    BbsService bbsService;

    @RequestMapping(value = "/top10", method = RequestMethod.GET,  produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map getTop10() {
        return bbsService.getTop10();
    }

    @RequestMapping(value = "/context", method = RequestMethod.POST)
    @ResponseBody
    public Map getBbsContext(@RequestBody Map<String, String> requestUrl) throws Exception {
        System.out.print(requestUrl.values());
        return bbsService.getBbsContext(requestUrl.get("url"), requestUrl.get("webchatID"));
    }

    @RequestMapping(value = "/topall", method = RequestMethod.GET)
    @ResponseBody
    public Map getTopAll() {
        return bbsService.getTopAll();
    }

    @RequestMapping(value = "/topall/{number}", method = RequestMethod.GET)
    @ResponseBody
    public Map getTopAll(@PathVariable String number) {
        return bbsService.getTopAllByNumber(number);
    }

    @RequestMapping("/file/{cateogry.rp}")
    public void getIcon(@PathVariable("cateogry.rp") String cateogry,
                        HttpServletRequest request,
                        HttpServletResponse response) throws IOException {

        if(StringUtils.isEmpty(cateogry)) {
            cateogry = "";
        }

        //获取当前系统信息
        Properties props=System.getProperties(); //获得系统属性集
        String osName = props.getProperty("os.name"); //操作系统名称

        File file;
        if (osName.indexOf("Windows") > -1){
            file = new File("d:/" + cateogry);
        }else {
            file = new File("./img/" + cateogry);
        }

        //File file = new File(fileName);

        //判断文件是否存在如果不存在就返回默认图标
        if(!(file.exists() && file.canRead())) {
            file = new File("./img/"  + "time.gif");
        }

        FileInputStream inputStream = new FileInputStream(file);
        byte[] data = new byte[(int)file.length()];
        int length = inputStream.read(data);
        inputStream.close();

        response.setContentType("image/png");

        OutputStream stream = response.getOutputStream();
        stream.write(data);
        stream.flush();
        stream.close();
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map getUserInfo(@RequestParam String userid){
        return bbsService.getUserInfo(userid);
    }
    
    @RequestMapping(value = "/user/info", method = RequestMethod.POST)
    @ResponseBody
    public Map getUserInfo(@RequestBody Map<String, String> userId){
        return bbsService.getUserInfo(userId.get("ID"));
    }

    @RequestMapping(value = "/userinfo/wx", method = RequestMethod.GET)
    @ResponseBody
    public Map getUserInfoByWxId(@RequestParam String wxid){
        return bbsService.getUserInfoByWxId(wxid);
    }

    @RequestMapping(value = "/upvote/do", method = RequestMethod.POST)
    @ResponseBody
    public Map upvoteAdd(@RequestBody Upvote upvoteRequest){
        return bbsService.addUpvote(upvoteRequest);
    }


}
