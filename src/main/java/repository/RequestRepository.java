package repository;

import base.repository.BaseRepository;
import entity.Requested;

import java.util.Optional;

public interface RequestRepository extends BaseRepository<Requested, Integer> {
    Optional<Requested> findExpertRequests(int id) throws NullPointerException;
}
