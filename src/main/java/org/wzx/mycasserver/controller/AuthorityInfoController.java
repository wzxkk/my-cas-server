package org.wzx.mycasserver.controller;

import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.wzx.mycasserver.entity.AuthorityInfo;
import org.wzx.mycasserver.service.impl.AuthorityInfoServiceImpl;
import org.wzx.mycasserver.controller.base.BaseController;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: 鱼头
 * @description: 角色信息表API接口
 * @since: 2021-10-08
 */
@RestController
@RequestMapping("api/my-cas-server/authorityInfo" )
@Api(tags = "角色信息表API接口" )
@Slf4j
@CrossOrigin
@ApiResponses({
        @ApiResponse(code = 204, message = "请求角色信息表成功,但没有内容返回" ),
        @ApiResponse(code = 400, message = "请求角色信息表的参数有误" ),
        @ApiResponse(code = 401, message = "请求角色信息表需要通过HTTP认证" ),
        @ApiResponse(code = 403, message = "服务器拒绝访问角色信息表" ),
        @ApiResponse(code = 404, message = "请求角色信息表的路径不对" ),
        @ApiResponse(code = 500, message = "请求角色信息表时服务器出现错误" )
})
public class AuthorityInfoController extends BaseController<AuthorityInfoServiceImpl, AuthorityInfo>{}
