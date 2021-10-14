package org.wzx.mycasserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @description: 系统配置杂项
 * @author: 鱼头(韦忠幸)
 * @since: 2020/5/19 16:46
 * @version: 0.0.1
 */

@Configuration
public class MyConfig {
    /**
     * @Description: 注入对象ServerEndpointExporter，
     * 这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
