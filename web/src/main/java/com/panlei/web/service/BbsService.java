package com.panlei.web.service;

import com.panlei.web.model.Upvote;

import java.util.Map;

/**
 * Created by 361pa on 2017/6/20.
 */
public interface BbsService {
    Map getTop10();
    Map getBbsContext(String url, String webchatID) throws Exception;
    Map getTopAll();
    Map getTopAllByNumber(String number);
    Map getUserInfo(String userId);
    Map getUserInfoByWxId(String wxId);
    Map addUpvote(Upvote upvoteRequest);
}
