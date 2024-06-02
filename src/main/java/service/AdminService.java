package service;

import base.service.BaseService;
import entity.Admin;


public interface AdminService extends BaseService<Admin,Integer> {
    Admin findByUsernameAndPassword(String username, String password);
}
