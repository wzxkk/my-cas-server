package org.wzx.mycasserver.controller;

import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.wzx.mycasserver.entity.UserAuthority;
import org.wzx.mycasserver.service.impl.UserAuthorityServiceImpl;
import org.wzx.mycasserver.controller.base.BaseController;
import lombok.extern.slf4j.Slf4j;

/**

 * @description: 用户与角色关系信息表API接口
 * @author: 鱼头(韦忠幸)
 * @since: 2021-10-13
 * @version: 0.0.1
 */
@RestController
@RequestMapping("api/my-cas-server/userAuthority" )
@Api(tags = "用户与角色关系信息表API接口" )
@Slf4j
@CrossOrigin
@ApiResponses({
        @ApiResponse(code = 204, message = "请求用户与角色关系信息表成功,但没有内容返回" ),
        @ApiResponse(code = 400, message = "请求用户与角色关系信息表的参数有误" ),
        @ApiResponse(code = 401, message = "请求用户与角色关系信息表需要通过HTTP认证" ),
        @ApiResponse(code = 403, message = "服务器拒绝访问用户与角色关系信息表" ),
        @ApiResponse(code = 404, message = "请求用户与角色关系信息表的路径不对" ),
        @ApiResponse(code = 500, message = "请求用户与角色关系信息表时服务器出现错误" )
})
public class UserAuthorityController extends BaseController<UserAuthorityServiceImpl, UserAuthority>{}
