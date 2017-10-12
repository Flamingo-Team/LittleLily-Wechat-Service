package com.panlei.web.service;

import com.panlei.web.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Zhangxq on 2016/7/15.
 */
public interface UserService {

    List<User> getAllUser();

    User getUserByPhoneOrEmail(String emailOrPhone, Short state);

    User getUserById(Long userId);

    //Map getDecodeUserInfo(String encryptedData, String iv, String code);
}
