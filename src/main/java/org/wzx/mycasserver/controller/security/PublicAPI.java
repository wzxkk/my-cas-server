package org.wzx.mycasserver.controller.security;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wzx.constant.ConstString;
import org.wzx.model.Result;
import org.wzx.mycasserver.config.WebSecurityConfig;
import org.wzx.mycasserver.entity.Clients;
import org.wzx.mycasserver.service.IClientsService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 公共接口类，无需登录，可直接调用
 * @author: 鱼头(韦忠幸)
 * @time: 2021-10-10 11:52
 */
@Slf4j
@Controller
@RequestMapping("public")
public class PublicAPI {
    @Autowired
    private IClientsService clientsService;

    /**
     * @param request
     * @param response
     * @param service
     * @param modelMap
     * @return: java.lang.String
     * @description: 认证中心登录接口
     * @author: 鱼头(韦忠幸)
     * @time: 2021-10-13 14:32
     * @version: 0.0.1
     */
    @GetMapping("login")
    public String login(HttpServletRequest request, HttpServletResponse response,
                        String service, ModelMap modelMap) throws IOException {
        log.info("登录功能的原始路径：" + service);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ConstString.CASTGC.equals(cookie.getName())) {
                    Object object = WebSecurityConfig.MY_LOGIN_IDS.get(cookie.getValue());
                    if (object == null) {
                        modelMap.put("service", service);
                        return "casLogin";
                    } else {
                        if (service == null || service.trim().isEmpty()) {
                            response.sendRedirect("/public/succeed");//没有路径那只能跳到成功页
                            return null;
                        } else {
                            if (service.startsWith("http")) {
                                String ticket = IdUtil.simpleUUID();
                                WebSecurityConfig.MY_TICKET_IDS.put(ticket, cookie.getValue());
                                response.sendRedirect(service + "?ticket=" + ticket);
                                return null;
                            } else {
                                response.sendRedirect(service);
                                return null;
                            }
                        }
                    }
                }
            }
        }
        modelMap.put("service", service);
        return "casLogin";
    }

    /**
     * @param ticket 票据
     * @description: 验证客户端的票据正常合法
     * @return: 合法则返回用户信息
     * @author: 鱼头(韦忠幸)
     * @since: 2021-10-13 15:04
     * @version: 0.0.1
     */
    @GetMapping("validate")
    @ResponseBody
    public Result validate(String ticket) {
        if (WebSecurityConfig.MY_TICKET_IDS.containsKey(ticket)) {
            Object object = WebSecurityConfig.MY_LOGIN_IDS.get(WebSecurityConfig.MY_TICKET_IDS.remove(ticket));
            if (object == null) {
                log.info("用户已退出登录");
                return new Result(null, "验证ticket失败,已返回用户信息", -1);
            } else {
                log.debug("验证票根:" + ticket + "成功,已返回用户信息");
                return new Result(object, "验证ticket成功,已返回用户信息", 0);
            }
        } else {
            log.warn("非法票根:" + ticket + "，验证失败！！！！！！");
            return new Result(null, "非法票根:" + ticket + "，验证失败！！！！！！", -1);
        }
    }

    /**
     * @param
     * @return: java.lang.String
     * @description: 登录失败页跳转
     * @author: 鱼头(韦忠幸)
     * @time: 2021-10-13 14:33
     * @version: 0.0.1
     */
    @GetMapping("loginFailure")
    public String loginFailure() {
        return "loginFailure";
    }

    /**
     * @param protocol
     * @param hostname
     * @param port
     * @param state
     * @return: org.wzx.model.Result
     * @description: 客户端启动后注册
     * @author: 鱼头(韦忠幸)
     * @time: 2021-10-13 14:33
     * @version: 0.0.1
     */
    @PostMapping("clientRegister")
    @ResponseBody
    public Result clientRegister(String protocol, String hostname, String port, String state) {
        Clients clients = Clients.builder()
                .protocol(protocol)
                .hostname(hostname)
                .port(port)
                .clientState(state)
                .build();
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq(true, "protocol", clients.getProtocol());
        updateWrapper.eq(true, "hostname", clients.getHostname());
        updateWrapper.eq(true, "port", clients.getPort());
        clientsService.saveOrUpdate(clients, updateWrapper);
        return new Result(null, "注册成功", 0);
    }
}
