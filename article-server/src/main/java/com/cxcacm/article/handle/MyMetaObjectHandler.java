package com.cxcacm.article.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.cxcacm.article.utils.JwtUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.cxcacm.article.constants.ArticleConstants.*;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy",username() , metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", username(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", username(), metaObject);
    }

    private static String username() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null)
            return "胖子";
        HttpServletRequest request = requestAttributes.getRequest();
        String authorization = request.getHeader(AUTH_TOKEN);
        if (authorization == null)
            throw new RuntimeException("需要登录");
        String jwt = authorization.substring(TOKEN_START);
        String username = null;
        try {
            username = (String) JwtUtil.parseJWT(jwt).get(AUTH_USER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }
}