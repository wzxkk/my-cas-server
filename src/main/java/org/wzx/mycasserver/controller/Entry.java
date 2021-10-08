package org.wzx.mycasserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: 鱼头
 * @time: 2021-10-2 14:16
 */
@Slf4j
@Controller
@RequestMapping("entry")
public class Entry {
    @GetMapping("login")
    public String login(String service, ModelMap modelMap) {
        log.info("登录功能的原始路径：" + service);
        modelMap.put("service", service);
        return "casLogin";
    }

    @GetMapping("validate")
    public void validate(String service, HttpServletResponse response) throws IOException {
        log.info("验证功能的原始路径：" + service);
        response.sendRedirect(service);
    }
}
