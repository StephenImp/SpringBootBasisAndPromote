package com.cn.oauth;


import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 自定义登录成功处理器
 *
 *  从request的请求头中拿到Authorization信息，根据clientId获取到secret和请求头中的secret信息做对比，
 *  如果正确，组建一个新的TokenRequest类，然后根据前者和clientDetails创建OAuth2Request对象，然后根据前者和authentication创建OAuth2Authentication对象。
 *  最后通过AuthorizationServerTokenServices和前者前者创建OAuth2AccessToken对象。然后将token返回。
 *
 *  提示：
 *  密码授权，我们在请求token的时候，需要一个包含clientid和clientSecret的请求头还有三个参数。

 */
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功");

        String header = request.getHeader("access_token");


        //获取clientId 和 clientSecret
        String clientId = "demo_client";
        String clientSecret = "demo_secret";

        //获取 ClientDetails
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null){
            throw new UnapprovedClientAuthenticationException("clientId 不存在"+clientId);
            //判断  方言  是否一致
        }else if (!StringUtils.equals(clientDetails.getClientSecret(),clientSecret)){
            throw new UnapprovedClientAuthenticationException("clientSecret 不匹配"+clientId);
        }

        //密码授权 模式, 组建 authentication
        TokenRequest tokenRequest = new TokenRequest(new HashMap<>(),clientId,clientDetails.getScope(),"password");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);

        OAuth2AccessToken token = defaultAuthorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        //判断是json 格式返回 还是 view 格式返回
        //将 authention 信息打包成json格式返回
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString("登陆成功"));
    }


//    /**
//     * 解码请求头
//     */
//    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
//        byte[] base64Token = header.substring(6).getBytes("UTF-8");
//
//        byte[] decoded;
//        try {
//            decoded = Base64.decode(base64Token);
//        } catch (IllegalArgumentException var7) {
//            throw new BadCredentialsException("Failed to decode basic authentication token");
//        }
//
//        String token = new String(decoded, "UTF-8");
//        int delim = token.indexOf(":");
//        if (delim == -1) {
//            throw new BadCredentialsException("Invalid basic authentication token");
//        } else {
//            return new String[]{token.substring(0, delim), token.substring(delim + 1)};
//        }
//    }
}
