package org.wzx.mycasserver.service.impl;

import org.wzx.mycasserver.entity.Clients;
import org.wzx.mycasserver.mapper.ClientsMapper;
import org.wzx.mycasserver.service.IClientsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: 鱼头
 * @description: 客户端信息表服务实现类
 * @since: 2021-10-08
 */
@Service
public class ClientsServiceImpl extends ServiceImpl<ClientsMapper, Clients> implements IClientsService {
}