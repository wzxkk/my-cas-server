package org.wzx.mycasserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @description: 自定义其它配置
 * @author: 鱼头
 * @time: 2021-10-8 15:59
 */
@Configuration
public class MyOtherConfig {
    /**
     * @Description: 注入对象ServerEndpointExporter，
     * 这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
