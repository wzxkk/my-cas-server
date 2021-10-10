package org.wzx.mycasserver.controller.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wzx.mycasserver.entity.base.Result;

/**
 * @description:
 * @author: 鱼头
 * @time: 2021-10-10 10:59
 */
@Slf4j
@RestController
@RequestMapping("clientEntry")
public class ClientEntry {
    //客户端启动后注册
    @PostMapping("register")
    public Result register(String localName){
return null;

    }
}
