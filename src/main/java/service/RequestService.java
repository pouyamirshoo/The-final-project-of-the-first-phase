package service;


import base.service.BaseService;
import entity.Requested;


public interface RequestService extends BaseService<Requested, Integer> {
    Requested findExpertRequests(int id);
}
