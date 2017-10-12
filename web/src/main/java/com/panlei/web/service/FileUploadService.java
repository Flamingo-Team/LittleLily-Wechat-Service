package com.panlei.web.service;

import java.util.Map;

/**
 * Created by 361pa on 2017/9/7.
 */
public interface FileUploadService {
    String uploadFile(String localFilePath, Map<String, String> requestMap)throws Exception;
}
