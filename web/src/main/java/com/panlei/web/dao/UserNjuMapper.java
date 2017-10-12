package com.panlei.web.dao;

import com.panlei.web.model.UserNju;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by 361pa on 2017/6/4.
 */
@Repository
public interface UserNjuMapper {
    int insert(UserNju userNju);

    UserNju selectUserByWebchatId(@Param("webchatID") String webchatId);
    int deleteUserByWebchatId(@Param("webchatID") String webchatId);
    int updateUserByWebchatId(@Param("webchatID") String webchatId, @Param("cookie") String cookie);
}
