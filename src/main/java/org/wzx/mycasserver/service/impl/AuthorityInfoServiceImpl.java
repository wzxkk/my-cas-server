package org.wzx.mycasserver.service.impl;

import org.wzx.mycasserver.entity.AuthorityInfo;
import org.wzx.mycasserver.mapper.AuthorityInfoMapper;
import org.wzx.mycasserver.service.IAuthorityInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: 鱼头
 * @description: 角色信息表服务实现类
 * @since: 2021-10-08
 */
@Service
public class AuthorityInfoServiceImpl extends ServiceImpl<AuthorityInfoMapper, AuthorityInfo> implements IAuthorityInfoService {
}