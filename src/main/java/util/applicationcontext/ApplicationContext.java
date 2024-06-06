package util.applicationcontext;

import connection.SessionFactorySingleton;
import org.hibernate.SessionFactory;
import repository.*;
import service.*;


public class ApplicationContext {
    static final SessionFactory SESSION_FACTORY;

    private static final CustomerRepository CUSTOMER_REPOSITORY;
    private static final CustomerService CUSTOMER_SERVICE;

    private static final DutyRepository DUTY_REPOSITORY;
    private static final DutyService DUTY_SERVICE;

    private static final ExpertRepository EXPERT_REPOSITORY;
    private static final ExpertService EXPERT_SERVICE;

    private static final OfferRepository OFFER_REPOSITORY;
    private static final OfferService OFFER_SERVICE;

    private static final OrderRepository ORDER_REPOSITORY;
    private static final OrderService ORDER_SERVICE;

    private static final SubDutyRepository SUB_DUTY_REPOSITORY;
    private static final SubDutyService SUB_DUTY_SERVICE;

    private static final CommentsRepository COMMENTS_REPOSITORY;
    private static final CommentsService COMMENTS_SERVICE;

    private static final AdminRepository ADMIN_REPOSITORY;
    private static final AdminService ADMIN_SERVICE;

    private static final RequestRepository REQUEST_REPOSITORY;
    private static final RequestService REQUEST_SERVICE;

    static {
        SESSION_FACTORY = SessionFactorySingleton.getInstance();

        CUSTOMER_REPOSITORY = new CustomerRepositoryImpl(SESSION_FACTORY);
        DUTY_REPOSITORY = new DutyRepositoryImpl(SESSION_FACTORY);
        EXPERT_REPOSITORY = new ExpertRepositoryImpl(SESSION_FACTORY);
        OFFER_REPOSITORY = new OfferRepositoryImpl(SESSION_FACTORY);
        ORDER_REPOSITORY = new OrderRepositoryImpl(SESSION_FACTORY);
        SUB_DUTY_REPOSITORY = new SubDutyRepositoryImpl(SESSION_FACTORY);
        COMMENTS_REPOSITORY = new CommentsRepositoryImpl(SESSION_FACTORY);
        ADMIN_REPOSITORY = new AdminRepositoryImpl(SESSION_FACTORY);
        REQUEST_REPOSITORY = new RequestRepositoryImpl(SESSION_FACTORY);

        CUSTOMER_SERVICE = new CustomerServiceImpl(CUSTOMER_REPOSITORY, SESSION_FACTORY);
        DUTY_SERVICE = new DutyServiceImpl(DUTY_REPOSITORY, SESSION_FACTORY);
        EXPERT_SERVICE = new ExpertServiceImpl(EXPERT_REPOSITORY, SESSION_FACTORY);
        OFFER_SERVICE = new OfferServiceImpl(OFFER_REPOSITORY, SESSION_FACTORY);
        ORDER_SERVICE = new OrderServiceImpl(ORDER_REPOSITORY,SESSION_FACTORY);
        SUB_DUTY_SERVICE = new SubDutyServiceImpl(SUB_DUTY_REPOSITORY,SESSION_FACTORY);
        COMMENTS_SERVICE = new CommentsServiceImpl(COMMENTS_REPOSITORY,SESSION_FACTORY);
        ADMIN_SERVICE = new AdminServiceImpl(ADMIN_REPOSITORY,SESSION_FACTORY);
        REQUEST_SERVICE = new RequestServiceImpl(REQUEST_REPOSITORY,SESSION_FACTORY);
    }

    public static CustomerService getCustomerService() {
        return CUSTOMER_SERVICE;
    }

    public static DutyService getDutyService() {
        return DUTY_SERVICE;
    }

    public static ExpertService getExpertService() {
        return EXPERT_SERVICE;
    }
    public static OfferService getOfferService(){return OFFER_SERVICE;}
    public static OrderService getOrderService(){return ORDER_SERVICE;}
    public static SubDutyService getSubDutyService(){return SUB_DUTY_SERVICE;}
    public static CommentsService getCommentsService(){return COMMENTS_SERVICE;}
    public static AdminService getAdminService(){return ADMIN_SERVICE;}
    public static RequestService getRequestService(){return REQUEST_SERVICE;}

}
