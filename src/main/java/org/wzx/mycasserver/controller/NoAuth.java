package org.wzx.mycasserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: 鱼头
 * @time: 2021-10-2 14:16
 */
@Slf4j
@RestController
public class NoAuth {
    @GetMapping("noAuth")
    public Object noAuth() {
        return "没有权限限制的资源";
    }
}
