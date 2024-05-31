package repository;

import base.exception.NotFoundException;
import base.repository.BaseRepository;
import entity.Customer;

public interface CustomerRepository extends BaseRepository<Customer,Integer> {
    Customer findByUsernameAndPassword(String username,String password) throws NotFoundException;
}
