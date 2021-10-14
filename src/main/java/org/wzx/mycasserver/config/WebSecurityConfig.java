package org.wzx.mycasserver.config;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.wzx.constant.ConstString;
import org.wzx.mycasserver.core.MyUserDetailsService;

import javax.servlet.http.Cookie;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: 鱼头(韦忠幸)
 * @since: 2021-10-8 15:51
 * @version: 0.0.1
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //  启用方法级别的权限认证
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //这个是保存已登录的浏览器信息的，按理说应该用redis,但我实在不想在折腾依赖别的环境了
    //如果你觉得不爽可以自己处理存储到你喜欢的地方
    public volatile static Map<String, Object> MY_LOGIN_IDS = new ConcurrentHashMap<>();
    public volatile static Map<String, String> MY_TICKET_IDS = new ConcurrentHashMap<>();

    //    @Autowired
//    private MyProvider myProvider;
//    @Autowired
//  private   TokenInterceptor tokenInterceptor;
//    @Autowired
//    private MyConfig myConfig;
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 注册token拦截器
//        registry.addInterceptor(tokenInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/**/swagger-ui/**")
//                .excludePathPatterns("/**/swagger-resources/**")
//                .excludePathPatterns("/**/v3/**")
//        ;
//    }
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    /**
     * 没有passwordEncoder会抛java.lang.IllegalArgumentException:
     * There is no PasswordEncoder mapped for the id "null"
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Override
    protected void configure(
            AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(myProvider); //注册自定义的provider
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("root").password(new BCryptPasswordEncoder().encode("123")).roles("ADMIN", "DBA")
//                .and()
//                .withUser("admin").password(new BCryptPasswordEncoder().encode("123")).roles("ADMIN", "USER")
//                .and()
//                .withUser("wzx").password(new BCryptPasswordEncoder().encode("123")).roles("USER");
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .authorizeRequests()
                .antMatchers("/resources/static/**", "/favicon.ico", "/public/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/public/login")//如果连不指定则默认是login,如果指定则会访问相对应的controller,此时没有controller将报错
                .loginProcessingUrl("/dologin")//如果指定必须同此内容一致，如果不指定那表单的action就跟loginPage内容一致
                .permitAll()
                .successHandler((request, response, authentication) -> {
                    String service = request.getParameter("service");
                    log.debug("登录成功，初始访问路径：" + service);
                    //生成票据，并以此作为登录信息，如果觉得不爽可以自行设置另外的值
                    String browerId = IdUtil.simpleUUID();
                    MY_LOGIN_IDS.put(browerId, authentication.getPrincipal());
                    Cookie cookie = new Cookie(ConstString.CASTGC, browerId);
                    //设置cookie，此cookie只对登录口有效，关闭浏览器即失效
                    cookie.setPath("/public/login");
                    cookie.setHttpOnly(true);
                    response.addCookie(cookie);
                    if (service == null || service.trim().isEmpty()) {
                        response.sendRedirect("/public/succeed");//没有路径那只能跳到成功页
                    } else {
                        if (service.startsWith("http")) {
                            log.debug("http的跳转");
                            String ticket = IdUtil.simpleUUID();
                            MY_TICKET_IDS.put(ticket, browerId);
                            response.sendRedirect(service + "?ticket=" + ticket);
                        } else {
                            log.debug("本地的跳转");
                            response.sendRedirect(service);
                        }
                    }
                })
                //.defaultSuccessUrl("/succeed")//如果是直接打开登录页，则登录成功会默认跳转到此页,作为sso系统这个选项不能配置
                .failureHandler((request, response, e) -> {
                    log.debug("登录失败，初始访问路径：" + request.getRequestURI());
                    response.sendRedirect("/public/loginFailure");
                })
                .and()
                .exceptionHandling().authenticationEntryPoint((request, response, e) -> {
            log.debug("尚未登录，初始访问路径：" + request.getRequestURI());
            response.sendRedirect("/public/login?service=" + request.getRequestURI());
        })
                .and()
                .rememberMe()//记得
                .and()
                .csrf().disable()
                .sessionManagement()
                .maximumSessions(1);//最多只能有一个session,如果同一账号在另一个地方登录，之前登录的将被迫下线
    }
}
