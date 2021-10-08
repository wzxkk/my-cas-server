package org.wzx.mycasserver.config;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.http.Cookie;
import java.util.UUID;

/**
 * @description:
 * @author: 鱼头
 * @time: 2021-10-8 15:51
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //  启用方法级别的权限认证
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
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
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("root").password(new BCryptPasswordEncoder().encode("123")).roles("ADMIN", "DBA")
                .and()
                .withUser("admin").password(new BCryptPasswordEncoder().encode("123")).roles("ADMIN", "USER")
                .and()
                .withUser("wzx").password(new BCryptPasswordEncoder().encode("123")).roles("USER");
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .authorizeRequests()
                .antMatchers("/resources/static/**", "/favicon.ico", "/swagger-ui/**", "/entry/**", "/noAuth").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/entry/login")//如果连不指定则默认是login,如果指定则会访问相对应的controller,此时没有controller将报错
//                .loginProcessingUrl("/eee")//如果指定必须同此内容一致，如果不指定那表单的action就跟loginPage内容一致
                .permitAll()
                .successHandler((request, response, authentication) -> {
                    String service = request.getParameter("service");
                    log.warn("登录成功，初始访问路径：" + service);
                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    String username = userDetails.getUsername();
                    Cookie cookie = new Cookie("mycasCookie", username);
                    cookie.setMaxAge(3600);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    String ticket = UUID.randomUUID().toString().replace("-", "");
                    HttpUtil.get("http://localhost:11111/setCookie/" + cookie.getName() + "/" + cookie.getValue());
                    HttpUtil.get("http://localhost:22222/setCookie/" + cookie.getName() + "/" + cookie.getValue());
                    if (service == null || service.trim().isEmpty()) {
                        response.sendRedirect("/succeed");//没有路径那只能跳到成功页
                    } else {
                        if (service.startsWith("http")) {
                            log.debug("http的跳转");
//                            response.setStatus(302);
//                            response.setHeader("Location", service + "?ticket=" + ticket);
                            response.sendRedirect(service+ "?ticket=" + ticket);
                        } else {
                            log.debug("本地的跳转");
                            response.sendRedirect(service);
                        }
                    }
                })
                //.defaultSuccessUrl("/succeed")//如果是直接打开登录页，则登录成功会默认跳转到此页,作为sso系统这个选项不能配置
                .failureHandler((request, response, e) -> {
                    log.debug("登录失败，初始访问路径：" + request.getRequestURI());
                    response.sendRedirect("http://localhost:11011/loginFailure");

                })
                .and()
                .exceptionHandling().authenticationEntryPoint((request, response, e) -> {
            log.debug("尚未登录，初始访问路径：" + request.getRequestURI());
//            response.setStatus(302);
//            response.setHeader("Location", "http://localhost:11011/entry/login?service=" + request.getRequestURI());
            response.sendRedirect("/entry/login?service=" + request.getRequestURI());
        })
                .and()
                .csrf().disable()
//                .sessionManagement()
//                .maximumSessions(1)
        ;//最多只能有一个session,如果同一账号在另一个地方登录，之前登录的将被迫下线
    }
}
