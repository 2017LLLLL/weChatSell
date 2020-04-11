package com.imooc.sell.service.impl;

import com.imooc.sell.dao.FlowDao;
import com.imooc.sell.dataobject.Flow;
import com.imooc.sell.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: sell
 * @description: 流水订单服务层
 * @author: flj
 * @create: 2020-03-04 12:31
 **/
@Service
public class FlowImpl implements FlowService {

    @Autowired
    private FlowDao flowDao;

    @Override
    public Flow findone(Integer id) {
        return flowDao.findOne(id);
    }

    @Override
    public Flow save(Flow flow) {
        return flowDao.save(flow);
    }

    @Override
    public Flow findByOrderNum(Integer orderId) {
        return flowDao.findByOrderNum(orderId);
    }
}
