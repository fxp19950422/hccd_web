package cn.healthdata.webapp.hospital.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

public class SpringUtils {
    public static ApplicationContext getApplicationContext() {
        return ContextsHolder.getApplicationContext();
    }

    public static ServletContext getServletContext() {
        return ContextsHolder.context;
    }

    public static Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }

    public static <T> T getBean(Class<T> beanType) {
        return getApplicationContext().getBean(beanType);
    }
}

@Component
class ContextsHolder {
    static ServletContext context;
    static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        if (ContextsHolder.applicationContext == null && ContextsHolder.context != null) {
            ContextsHolder.applicationContext = WebApplicationContextUtils.getWebApplicationContext(ContextsHolder.context);
        }
        return ContextsHolder.applicationContext;
    }

    @Autowired
    public void hook(ServletContext context) {
        ContextsHolder.context = context;
    }
}
