package service;

import base.service.BaseServiceImpl;
import entity.Comments;
import org.hibernate.SessionFactory;
import repository.CommentsRepository;

public class CommentsServiceImpl extends BaseServiceImpl<Comments,Integer, CommentsRepository>
        implements CommentsService {
    public CommentsServiceImpl(CommentsRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }
}
