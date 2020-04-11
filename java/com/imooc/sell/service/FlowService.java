package com.imooc.sell.service;


import com.imooc.sell.dataobject.Flow;

public interface FlowService {
    Flow findone(Integer id);
    Flow save(Flow flow);
    Flow findByOrderNum(Integer prodcutId);
}
