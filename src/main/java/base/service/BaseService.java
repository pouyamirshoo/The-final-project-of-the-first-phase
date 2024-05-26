package base.service;

import base.entity.BaseEntity;

import java.io.Serializable;

public interface BaseService<T extends BaseEntity<ID>, ID extends Serializable> {
    void saveOrUpdate(T entity) throws IllegalStateException;


    T findById(ID id);

    void delete(T t) throws IllegalStateException;

    boolean validate(T entity);

}
