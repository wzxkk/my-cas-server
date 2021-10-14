package org.wzx.mycasserver.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: 鱼头(韦忠幸)
 * @time: 2020-10-14 11:36
 */

@Slf4j
@Component
public class SystemInit implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("系统初始化。。。");
    }
}
