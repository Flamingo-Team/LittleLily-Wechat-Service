package com.panlei.web.service;

import com.panlei.web.model.UserNju;

/**
 * Created by 361pa on 2017/7/21.
 */
public interface NjuUserService {
    String createUser(UserNju userNju) throws Exception;
    String getUserBind(UserNju userNju) throws Exception;
    String deleteUserBind(UserNju userNju) throws Exception;
}
