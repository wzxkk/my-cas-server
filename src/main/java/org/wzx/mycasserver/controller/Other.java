package org.wzx.mycasserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author: 鱼头
 * @time: 2021-10-2 14:16
 */
@Slf4j
@Controller
public class Other {
    @GetMapping("succeed")
    public String loginSucceed() {
        return "loginSucceed";
    }

    @GetMapping("loginFailure")
    public String loginFailure() {
        return "loginFailure";
    }

    @GetMapping("src/{c}")
    @ResponseBody
    public String src(@PathVariable("c") String c) {
        return "受保护资源" + c;
    }
}
