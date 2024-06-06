package service;

import base.exception.ReturnMethodException;
import base.service.BaseService;
import entity.Expert;
import entity.enums.ExpertCondition;

import java.util.List;

public interface ExpertService extends BaseService<Expert, Integer> {
    Expert findByUsernameAndPassword(String username, String password);

    List<Expert> findByCondition(ExpertCondition expertCondition) throws ReturnMethodException;

    List<Expert> allExperts() throws ReturnMethodException;
}
