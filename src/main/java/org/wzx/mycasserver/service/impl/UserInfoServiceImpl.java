package org.wzx.mycasserver.service.impl;

import org.wzx.mycasserver.entity.UserInfo;
import org.wzx.mycasserver.mapper.UserInfoMapper;
import org.wzx.mycasserver.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: 鱼头
 * @description: 用户信息表服务实现类
 * @since: 2021-10-08
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
}