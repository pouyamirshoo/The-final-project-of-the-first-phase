package service;

import base.service.BaseService;
import entity.Customer;

public interface CustomerService extends BaseService<Customer,Integer> {
    Customer findByUsernameAndPassword(String username,String password);
}
