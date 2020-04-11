package com.imooc.sell.dao;

import com.imooc.sell.dataobject.Flow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowDao extends JpaRepository<Flow,Integer> {
    Flow findByOrderNum(Integer orderId);
}
