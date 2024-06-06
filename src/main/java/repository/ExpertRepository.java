package repository;

import base.exception.NotFoundException;
import base.repository.BaseRepository;
import entity.Expert;
import entity.enums.ExpertCondition;

import java.util.List;
import java.util.Optional;

public interface ExpertRepository extends BaseRepository<Expert,Integer> {
    Optional<Expert> findByUsernameAndPassword(String username, String password) throws NotFoundException;
    List<Expert> findByCondition(ExpertCondition expertCondition) throws NullPointerException;
    List<Expert> allExperts() throws NullPointerException;
}
