package com.panlei.web.controller;

import com.panlei.web.model.User;
import com.panlei.web.model.UserNju;
import com.panlei.web.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Zhangxq on 2016/7/15.
 */

@Controller
@RequestMapping("/bbs")
public class UserController {

    private Logger log = Logger.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @Autowired
    AuthInfoService authInfoService;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    NjuUserService njuUserService;

    @Autowired
    MsgInfoService msgInfoService;


    @RequestMapping("/showUser")
    public String showUser(HttpServletRequest request, Model model){
        log.info("查询所有用户信息");
        List<User> userList = userService.getAllUser();
        model.addAttribute("userList",userList);
        return "showUser";
    }

    @RequestMapping(value = "/listUser", method = RequestMethod.GET)
    @ResponseBody
    public Map showUserList(){
        log.info("查询所有用户信息");
        List<User> userList = userService.getAllUser();
        Map map = new HashMap();
        map.put("list", userList);
        return map;
    }

    @RequestMapping(value = "/user/bind",method = RequestMethod.POST)
    @ResponseBody
    public String bindUser(@RequestBody UserNju userNju) throws Exception {
        System.out.print(userNju.getUserName());


        return njuUserService.createUser(userNju);
    }


    @RequestMapping(value = "/user/bind/get",method = RequestMethod.POST)
    @ResponseBody
    public String getUserBind(@RequestBody UserNju userNju) throws Exception {

        return njuUserService.getUserBind(userNju);
    }

    @RequestMapping(value = "/user/bind/delete",method = RequestMethod.POST)
    @ResponseBody
    public String deleteUserBind(@RequestBody UserNju userNju) throws Exception {

        return njuUserService.deleteUserBind(userNju);
    }

    @RequestMapping(value = "/user/auth",method = RequestMethod.POST)
    @ResponseBody
    public Map getDecodeUserInfo(@RequestBody Map<String, String> requestUrl){
        System.out.print(requestUrl.values());
        return authInfoService.getDecodeUserInfo(requestUrl.get("encryptedData"),requestUrl.get("iv"),requestUrl.get("code"));
    }

    /**
     * 文件上传功能
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/upload",method=RequestMethod.POST)
    @ResponseBody
    public String upload(MultipartFile file, HttpServletRequest request) throws Exception {
        Map<String, String> requestMap =new HashMap<String, String>();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        requestMap.put("webchatID", request.getParameter("webchatID"));
        requestMap.put("boardName", request.getParameter("boardName"));
        requestMap.put("number", request.getParameter("number"));
        request.setCharacterEncoding("UTF-8");

        String fileName = file.getOriginalFilename();
        File dir;
        Properties props=System.getProperties(); //获得系统属性集
        String osName = props.getProperty("os.name"); //操作系统名称
        if (osName.indexOf("Windows") > -1){
            dir = new File("d:/" + fileName);
        }else {
            dir = new File("./img/upload/" + fileName);
        }
        if(!dir.exists()){
            dir.mkdirs();
        }
        //MultipartFile自带的解析方法
        file.transferTo(dir);

        //requestMap.put("url", fileUploadService.uploadFile(dir.toString(), requestMap));
        return fileUploadService.uploadFile(dir.toString(), requestMap);
    }

    /**
     * 文件下载功能
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/down")
    public void down(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //模拟文件，myfile.txt为需要下载的文件
        String fileName = request.getSession().getServletContext().getRealPath("upload")+"/myfile.txt";
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
        //假如以中文名下载的话
        String filename = "下载文件.txt";
        //转码，免得文件名中文乱码
        filename = URLEncoder.encode(filename,"UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        out.close();
    }

    /**
     * 储存formId
     */

    @RequestMapping(value = "/msginfo",method = RequestMethod.POST)
    @ResponseBody
    public String saveMsgInfo(@RequestBody Map<String, String> requestUrl){
        System.out.print(requestUrl.values());
        return msgInfoService.saveMsgInfo(requestUrl.get("openid"),requestUrl.get("formid"));
    }
}
