package org.wzx.mycasserver.controller.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.wzx.model.Result;
import org.wzx.mycasserver.config.WebSecurityConfig;

/**
 * @description: 登录后的接口
 * @author: 鱼头(韦忠幸)
 * @time: 2021-10-13 14:38
 * @version: 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserAPI {

    @GetMapping("logout")
    public Result logout(String username) {
        log.debug("认证中心开始注销" + username);
        log.debug("认证中心注销" + username + "完成。。。");
        return null;
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
    public Result validate(String ticket) {
        if (WebSecurityConfig.MY_TICKET_IDS.containsKey(ticket)) {
            Object object = WebSecurityConfig.MY_LOGIN_IDS.get(WebSecurityConfig.MY_TICKET_IDS.remove(ticket));
            if (object == null) {
                log.info("用户已退出登录");
                return new Result(null, "验证ticket失败,已返回用户信息", -1);
            } else {
                log.debug("验证票根:" + ticket + "成功，原始路径");
                return new Result(object, "验证ticket成功,已返回用户信息", 0);
            }
        } else {
            log.warn("非法票根:" + ticket + "，验证失败！！！！！！");
            return new Result(null, "非法票根:" + ticket + "，验证失败！！！！！！", -1);
        }
    }

    @GetMapping("succeed")
    public String loginSucceed() {
        return "loginSucceed";
    }
}
