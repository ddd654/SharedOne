package com.project.tobe.service;

import com.project.tobe.dto.OrderH;
import com.project.tobe.dto.OrderSearchDTO;

import java.util.List;

public interface OrderService {

    List<OrderH> getOrder(OrderSearchDTO criteria); //조건조회
    double getPrice(int formcustomer); //주문등록 판매가 가져오기

}
