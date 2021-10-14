package org.wzx.mycasserver.controller.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 测试类
 * @author: 鱼头(韦忠幸)
 * @time: 2021-10-13 14:34
 * @version: 0.0.1
 */
@Slf4j
@RestController
public class TestAPI {
    /**
     * @param id
     * @return: java.lang.String
     * @description: 测试登录后是否能访问资源
     * @author: 鱼头(韦忠幸)
     * @time: 2021-10-13 14:34
     * @version: 0.0.1
     */
    @GetMapping("/test/{id}")
    public String src(@PathVariable String id) {
        return "访问到业务资源啦: " + id;
    }
}
