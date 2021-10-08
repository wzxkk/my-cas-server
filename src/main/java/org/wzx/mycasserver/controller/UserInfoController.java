package org.wzx.mycasserver.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wzx.mycasserver.controller.base.BaseController;
import org.wzx.mycasserver.entity.UserInfo;
import org.wzx.mycasserver.service.impl.UserInfoServiceImpl;

/**
 * @author: 鱼头
 * @description: 用户信息表API接口
 * @since: 2021-10-02
 */
@RestController
@RequestMapping("api/mycas/userInfo" )
@Api(tags = "用户信息表API接口" )
@Slf4j
@CrossOrigin
@ApiResponses({
        @ApiResponse(code = 204, message = "请求用户信息表成功,但没有内容返回" ),
        @ApiResponse(code = 400, message = "请求用户信息表的参数有误" ),
        @ApiResponse(code = 401, message = "请求用户信息表需要通过HTTP认证" ),
        @ApiResponse(code = 403, message = "服务器拒绝访问用户信息表" ),
        @ApiResponse(code = 404, message = "请求用户信息表的路径不对" ),
        @ApiResponse(code = 500, message = "请求用户信息表时服务器出现错误" )
})
public class UserInfoController extends BaseController<UserInfoServiceImpl, UserInfo> {}
