package base.repository;

import base.entity.BaseEntity;

import java.io.Serializable;
import java.util.Optional;

public interface BaseRepository <T extends BaseEntity<ID>,ID extends Serializable> {

    void saveOrUpdate(T entity) throws IllegalStateException;
    Optional<T> findById(ID id);
    void delete(T entity);
}
