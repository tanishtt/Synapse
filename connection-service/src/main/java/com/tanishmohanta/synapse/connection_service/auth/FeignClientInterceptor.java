package com.tanishmohanta.synapse.connection_service.auth;

import com.tanishmohanta.synapse.connection_service.auth.AuthContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        Long userId= AuthContextHolder.getCurrentUserId();
        if(userId!=null){
            template.header("X-User-Id", userId.toString());
        }
    }
}
