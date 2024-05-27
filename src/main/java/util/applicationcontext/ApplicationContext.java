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

    static {
        SESSION_FACTORY = SessionFactorySingleton.getInstance();

        CUSTOMER_REPOSITORY = new CustomerRepositoryImpl(SESSION_FACTORY);
        DUTY_REPOSITORY = new DutyRepositoryImpl(SESSION_FACTORY);
        EXPERT_REPOSITORY = new ExpertRepositoryImpl(SESSION_FACTORY);
        OFFER_REPOSITORY = new OfferRepositoryImpl(SESSION_FACTORY);
        ORDER_REPOSITORY = new OrderRepositoryImpl(SESSION_FACTORY);
        SUB_DUTY_REPOSITORY = new SubDutyRepositoryImpl(SESSION_FACTORY);

        CUSTOMER_SERVICE = new CustomerServiceImpl(CUSTOMER_REPOSITORY, SESSION_FACTORY);
        DUTY_SERVICE = new DutyServiceImpl(DUTY_REPOSITORY, SESSION_FACTORY);
        EXPERT_SERVICE = new ExpertServiceImpl(EXPERT_REPOSITORY, SESSION_FACTORY);
        OFFER_SERVICE = new OfferServiceImpl(OFFER_REPOSITORY, SESSION_FACTORY);
        ORDER_SERVICE = new OrderServiceImpl(ORDER_REPOSITORY,SESSION_FACTORY);
        SUB_DUTY_SERVICE = new SubDutyServiceImpl(SUB_DUTY_REPOSITORY,SESSION_FACTORY);
    }

    public static CustomerService getStudentService() {
        return CUSTOMER_SERVICE;
    }

    public static DutyService getEducationLoanService() {
        return DUTY_SERVICE;
    }

    public static ExpertService getMortgageService() {
        return EXPERT_SERVICE;
    }
    public static OfferService getPaidInstallmentsService(){return OFFER_SERVICE;}
    public static OrderService getTuitionLoanService(){return ORDER_SERVICE;}
    public static SubDutyService getUnpaidInstallmentsService(){return SUB_DUTY_SERVICE;}

}
