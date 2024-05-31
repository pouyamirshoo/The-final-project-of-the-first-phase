package base.service;

import base.entity.BaseEntity;
import base.exception.NotFoundException;
import base.exception.ReturnMethodException;
import base.repository.BaseRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.Set;

@RequiredArgsConstructor
public class BaseServiceImpl<T extends BaseEntity<ID>, ID extends Serializable
        , R extends BaseRepository<T, ID>> implements BaseService<T, ID> {

    public final R repository;
    private final SessionFactory sessionFactory;
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();


    @Override
    public void saveOrUpdate(T entity) throws IllegalStateException {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            repository.saveOrUpdate(entity);
            transaction.commit();
        } catch (Exception e) {
            assert transaction != null;
            transaction.rollback();
        }
    }

    @Override
    public T findById(ID id) throws IllegalStateException {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            T foundEntity = repository.findById(id).orElseThrow(
                    () -> new NotFoundException(String.format("entity with %s not found", id)));
            session.getTransaction().commit();
            return foundEntity;
        } catch (NotFoundException e) {
            throw new ReturnMethodException("");
        }
    }

    @Override
    public void delete(T t) throws IllegalStateException {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            repository.delete(t);
            transaction.commit();
        } catch (Exception e) {
            assert transaction != null;
            transaction.rollback();
        }
    }

    public boolean validate(T entity) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (violations.isEmpty()) {
            return true;
        } else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<T> violation : violations) {
                System.out.println(violation.getMessage());
            }
        }
        return false;
    }
}
