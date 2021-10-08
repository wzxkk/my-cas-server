package org.wzx.mycasserver.service.impl;

import org.wzx.mycasserver.entity.UserAuthority;
import org.wzx.mycasserver.mapper.UserAuthorityMapper;
import org.wzx.mycasserver.service.IUserAuthorityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: 鱼头
 * @description: 用户与角色关系信息表服务实现类
 * @since: 2021-10-08
 */
@Service
public class UserAuthorityServiceImpl extends ServiceImpl<UserAuthorityMapper, UserAuthority> implements IUserAuthorityService {
}