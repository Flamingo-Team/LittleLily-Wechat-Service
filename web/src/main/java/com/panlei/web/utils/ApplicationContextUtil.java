package com.panlei.web.utils;

import org.springframework.context.ApplicationContext;

/**
 * Created by yhc on 2017/9/21.
 */
public class ApplicationContextUtil {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

}
