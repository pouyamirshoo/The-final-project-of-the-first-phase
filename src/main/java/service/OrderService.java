package service;

import base.exception.ReturnMethodException;
import base.service.BaseService;
import entity.Order;
import entity.enums.OrderCondition;

import java.util.List;

public interface OrderService extends BaseService<Order,Integer> {
    List<Order> findByCustomerAndCondition(int id, OrderCondition orderCondition) throws ReturnMethodException;
}
