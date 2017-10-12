package com.panlei.web.service.impl;

import com.panlei.web.dao.UserDao;
import com.panlei.web.model.User;
import com.panlei.web.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Zhangxq on 2016/7/15.
 */

@Service("UserService")
public class UserServiceImpl implements UserService {
    
    @Resource
    private UserDao userDao;

    public User getUserById(Long userId) {
        return userDao.selectUserById(userId);
    }
    
    public User getUserByPhoneOrEmail(String emailOrPhone, Short state) {
        return userDao.selectUserByPhoneOrEmail(emailOrPhone,state);
    }
    
    public List<User> getAllUser() {
        return userDao.selectAllUser();
    }

//    public Map getDecodeUserInfo(String encryptedData, String iv, String code){
//        Map<String, Object> result = new HashMap<String, Object>();
//
//        if (code == null || code.length() == 0) {
//            //result.put("status", 0);
//            //result.put("msg", "code 不能为空");
//            return result;
//        }
//
//        //小程序唯一标识   (在微信小程序管理后台获取)
//        String wxspAppid = "wxa7ff67769340f01d";
//        //小程序的 app secret (在微信小程序管理后台获取)
//        String wxspSecret = "f1f8f6a1250d935f0ba24b51a4f0c507";
//        //授权（必填）
//        String grant_type = "authorization_code";
//
//        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
//        //请求参数
//        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
//        //发送请求
//        //发送函数自己写了一个
//        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
//
//        //解析相应内容（转换成json对象）
//        JSONObject json = JSONObject.parseObject(sr);
//        if(json.containsKey("errcode")){
//            return result;
//        }
//
//        //获取会话密钥（session_key）
//        String session_key = json.getString("session_key");
//
//        //用户的唯一标识（openid）
//        String openid = (String) json.get("openid");
//
//        //解密
//        try {
//            String rs = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
//            if (null == rs || rs.length() <= 0) {
//                return result;
//            }
//            AuthInfo authInfo = new AuthInfo();
//            JSONObject userInfoJSON = JSONObject.parseObject(rs);
//            //具体不知道会不会返回null值，等稳定了再封装测试中...
//            if(userInfoJSON.get("openId") != null){
//                authInfo.setOpenId(userInfoJSON.get("openId").toString());
//            }
//            if(userInfoJSON.get("nickName") != null){
//                authInfo.setNickName(userInfoJSON.get("nickName").toString());
//            }
//            if(userInfoJSON.get("gender") != null){
//                authInfo.setGender(userInfoJSON.get("gender").toString());
//            }
//            if(userInfoJSON.get("city") != null){
//                authInfo.setCity(userInfoJSON.get("city").toString());
//            }
//            if(userInfoJSON.get("province") != null){
//                authInfo.setProvince(userInfoJSON.get("province").toString());
//            }
//            if(userInfoJSON.get("country") != null){
//                authInfo.setCountry(userInfoJSON.get("country").toString());
//            }
//            if(userInfoJSON.get("avatarUrl") != null){
//                authInfo.setAvatarUrl(userInfoJSON.get("avatarUrl").toString());
//            }
//            if(userInfoJSON.get("language") != null){
//                authInfo.setLanguage(userInfoJSON.get("language").toString());
//            }
//            result.put("authInfo", authInfo);
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return result;
//        }
//    }
}
