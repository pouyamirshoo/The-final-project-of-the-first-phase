package repository;

import base.repository.BaseRepositoryImpl;
import entity.Comments;
import org.hibernate.SessionFactory;

public class CommentsRepositoryImpl extends BaseRepositoryImpl<Comments,Integer>
        implements CommentsRepository{
    public CommentsRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Comments> getEntityClass() {
        return Comments.class;
    }
}
