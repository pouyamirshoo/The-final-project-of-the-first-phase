package menu;


import base.exception.NotFoundException;
import base.exception.ReturnMethodException;
import entity.*;
import entity.enums.BestTime;
import entity.enums.OfferCondition;
import entity.enums.OrderCondition;
import jakarta.persistence.PreUpdate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.*;
import util.applicationcontext.ApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Menu {

    private final Scanner sc = new Scanner(System.in);

    CustomerService customerService = ApplicationContext.getCustomerService();
    DutyService dutyService = ApplicationContext.getDutyService();
    SubDutyService subDutyService = ApplicationContext.getSubDutyService();
    OrderService orderService = ApplicationContext.getOrderService();
    OfferService offerService = ApplicationContext.getOfferService();
    AdminService adminService = ApplicationContext.getAdminService();

    private final String RESET = "\u001B[0m";
    private final String YELLOW = "\u001B[33m";
    private final String RED = "\u001B[31m";
    private final String GREEN = "\u001B[32m";
    private final String BLUE = "\u001B[34m";
    private final String CYAN = "\u001B[46m";

    private final Logger logger = LoggerFactory.getLogger(Menu.class);

    public int getNumberFromUser() {
        int num = 0;
        try {
            num = sc.nextInt();
        } catch (InputMismatchException e) {
            logger.error(RED + "wrong input type" + RESET);
        } finally {
            sc.nextLine();
        }
        return num;
    }

    public String getStringFromUser() {
        logger.info(GREEN + "get String input method is running" + RESET);
        String input = null;
        try {
            input = sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        return input;
    }

    public String getFirstName() {
        System.out.println("please enter your first name");
        return getStringFromUser();
    }

    public String getLastName() {
        System.out.println("please enter your last name");
        return getStringFromUser();
    }

    public String getNationalCode() {
        System.out.println("please enter your National Code");
        return getStringFromUser();
    }

    public String getPhoneNumber() {
        System.out.println("please enter your Phone Number");
        return getStringFromUser();
    }

    public String getUsername() {
        System.out.println("please enter your username");
        return getStringFromUser();
    }
    public String getPassword() {
        System.out.println("please enter your username");
        System.out.println(CYAN + "HINT:password has to be 8 size and must contain at least 1 lower and upper case and 1 digit and 1 char" + RESET);
        return getStringFromUser();
    }

    public String getEmail() {
        System.out.println("please enter your email");
        return getStringFromUser();
    }

    public String getCity() {
        System.out.println("please enter your city");
        return getStringFromUser();
    }

    public String getAddress() {
        System.out.println("please enter your address");
        return getStringFromUser();
    }

    public String getPostalCode() {
        System.out.println("please enter your postalCode");
        return getStringFromUser();
    }
    public String getTitle(){
        System.out.println("please enter the name");
        return getStringFromUser();
    }
    public int getPrice(){
        System.out.println("please enter price");
        return getNumberFromUser();
    }
    public String getDescription(){
        System.out.println("please enter description");
        return getStringFromUser();
    }

    public Date getOrderLimit() {
        System.out.println("please enter the order limit max 30 days");
        System.out.println("it is the time that order can receive offers by day");
        int input = getNumberFromUser();
        DateTime current = new DateTime(new Date(System.currentTimeMillis()));
        String limitDate = current.plusDays(input).toString();
        return creatDate(limitDate);
    }

    public void startMenu() {
        System.out.println(CYAN + "*****WELCOME*****" + RESET);
        System.out.println("press 1 for customer menu");
        System.out.println("press 2 for expert menu");
        System.out.println("press 3 for admin menu");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> customerMenu();
            case 2 -> System.out.println("2");
            case 3 -> signInAdmin();
            default -> startMenu();
        }
    }
    public void signInAdmin(){
        String username = getUsername();
        String password = getPassword();
        Admin admin;
        try {
            admin = adminService.findByUsernameAndPassword(username,password);
            logger.info(GREEN + "admin founded" + RESET);
            adminMenu(admin);
        } catch (NotFoundException e){
            logger.error(RED + "no admin founded and return to startMenu method" + RESET);
            System.out.println("wrong username or password");
            startMenu();
        }
    }
    public void adminMenu(Admin admin){
        System.out.println(CYAN + "WELCOME " + admin.getFirstname() + RESET);
        System.out.println("press 1 for CRUD duty");
        System.out.println("press 2 for CRUD subDuty");
        System.out.println("press 3 for check experts");
        System.out.println("press 4 for EXIT");
        int choose = getNumberFromUser();
        switch (choose){
            case 1 -> crudDuty(admin);
            case 2 -> crudSubDuty(admin);
            case 3 -> System.out.println("expert");
            default -> startMenu();
        }
    }
    public void crudDuty(Admin admin){
        System.out.println("press 1 for add duty");
        System.out.println("press 2 for rename duty");
        System.out.println("press 3 for remove duty");
        System.out.println("press 4 for show all duties duty");
        System.out.println("press 5 for EXIT");
        int choose = getNumberFromUser();
        switch (choose){
            case 1 -> addDuty(admin);
            case 2 -> renameDuty(admin);
            case 3 -> removeDuty(admin);
            case 4 -> showAllDuties();
            case 5 -> adminMenu(admin);
            default -> crudDuty(admin);
        }
    }
    public void crudSubDuty(Admin admin){
        Duty duty = selectDuty(admin);
        System.out.println("press 1 for add SubDuty");
        System.out.println("press 2 for edit SubDuty");
        System.out.println("press 3 for remove SubDuty");
        System.out.println("press 4 for show all SubDuties");
        System.out.println("press 5 for EXIT");
        int choose = getNumberFromUser();
        switch (choose){
            case 1 -> addSubDuty(admin,duty);
            case 2 -> editSubDuty(admin,duty);
            case 3 -> removeSubDuty(admin,duty);
            case 4 -> showAllSubDuties(duty.getId());
            case 5 -> adminMenu(admin);
            default -> crudSubDuty(admin);
        }
    }
    public Duty selectDuty(Admin admin){
        showAllDuties();
        System.out.println("please enter the id of duty");
        int choose = getNumberFromUser();
        Duty duty = null;
        try {
            duty = dutyService.findById(choose);
            logger.info(GREEN + "duty founded" + RESET);
        } catch (NullPointerException e){
            logger.error(RED + "wrong Id input and return" + RESET);
            System.out.println("wrong id entered");
            crudSubDuty(admin);
        }
        return duty;
    }
    public void addSubDuty(Admin admin,Duty duty){
        SubDuty subDuty = SubDuty.builder()
                .subDutyName(getTitle())
                .price(getPrice())
                .description(getDescription())
                .duty(duty)
                .build();
        if (subDutyService.validate(subDuty)){
            try {
                subDutyService.saveOrUpdate(subDuty);
                logger.info(GREEN + "SubDuty saved" + RESET);
                crudSubDuty(admin);
            } catch (IllegalStateException e){
                logger.error(RED + "can not save SubDuty" + RESET);
                System.out.println("duplicate information entered");
                crudSubDuty(admin);
            }
        } else {
            logger.warn(YELLOW + "wrong input format" + RESET);
            addSubDuty(admin,duty);
        }
    }
    public void editSubDuty(Admin admin,Duty duty){
        System.out.println("press 1 for edit name");
        System.out.println("press 2 for edit price");
        System.out.println("press 3 for edit description");
        System.out.println("press 4 for change duty");
        System.out.println("press 5 for show all subDuties");
        System.out.println("press 6 for EXIT");
        int choose = getNumberFromUser();
        switch (choose){
            case 1 -> editSubDutyName(admin,duty);
            case 2 -> editSubDutyPrice(admin,duty);
            case 3 -> editSubDutyDescription(admin,duty);
            case 4 -> changeDuty(admin,duty);
            case 5 -> showAllSubDuties(duty.getId());
            case 6 -> crudSubDuty(admin);
            default -> editSubDuty(admin,duty);
        }
    }
    public SubDuty selectSubDuty(Admin admin,Duty duty){
        showAllSubDuties(duty.getId());
        System.out.println("please enter the id of SubDuty");
        int choose = getNumberFromUser();
        SubDuty subDuty = null;
        try {
            subDuty = subDutyService.findById(choose);
            logger.info(GREEN + "subDuty founded" + RESET);
        } catch (NullPointerException e){
            logger.error(RED + "wrong Id input and return " + RESET);
            System.out.println("wrong id entered");
            crudSubDuty(admin);
        }
        return subDuty;
    }
    public void editSubDutyName(Admin admin,Duty duty){
        SubDuty subDuty = selectSubDuty(admin,duty);
        String newTitle = getTitle();
        assert subDuty != null;
        subDuty.setSubDutyName(newTitle);
        try {
            subDutyService.saveOrUpdate(subDuty);
            logger.info(GREEN + "subDuty updated" + RESET);
            crudSubDuty(admin);
        } catch (IllegalStateException e){
            logger.error(RED + "can not update subDuty" + RESET);
            System.out.println("something wrong try again");
            crudSubDuty(admin);
        }
    }

    public void editSubDutyPrice(Admin admin,Duty duty){
        SubDuty subDuty = selectSubDuty(admin,duty);
        int newPrice = getPrice();
        assert subDuty != null;
        subDuty.setPrice(newPrice);
        try {
            subDutyService.saveOrUpdate(subDuty);
            logger.info(GREEN + "subDuty updated" + RESET);
            crudSubDuty(admin);
        } catch (IllegalStateException e){
            logger.error(RED + "can not update subDuty" + RESET);
            System.out.println("something wrong try again");
            crudSubDuty(admin);
        }
    }
    public void changeDuty(Admin admin,Duty duty){
        SubDuty subDuty = selectSubDuty(admin,duty);
        Duty newDuty = selectDuty(admin);
        assert subDuty != null;
        subDuty.setDuty(newDuty);
        try {
            subDutyService.saveOrUpdate(subDuty);
            logger.info(GREEN + "subDuty updated" + RESET);
            crudSubDuty(admin);
        } catch (IllegalStateException e){
            logger.error(RED + "can not update subDuty" + RESET);
            System.out.println("something wrong try again");
            crudSubDuty(admin);
        }
    }
    public void editSubDutyDescription(Admin admin,Duty duty){
        SubDuty subDuty = selectSubDuty(admin,duty);
        String newDescription = getTitle();
        assert subDuty != null;
        subDuty.setDescription(newDescription);
        try {
            subDutyService.saveOrUpdate(subDuty);
            logger.info(GREEN + "subDuty updated" + RESET);
            crudSubDuty(admin);
        } catch (IllegalStateException e){
            logger.error(RED + "can not update subDuty" + RESET);
            System.out.println("something wrong try again");
            crudSubDuty(admin);
        }
    }
    public void removeSubDuty(Admin admin,Duty duty){
        showAllSubDuties(duty.getId());
        System.out.println("please enter the id of SubDuty for delete");
        int choose = getNumberFromUser();
        SubDuty subDuty = null;
        try {
            subDuty = subDutyService.findById(choose);
            logger.info(GREEN + "subDuty founded" + RESET);
        } catch (NullPointerException e){
            logger.error(RED + "wrong Id input and return removeSubDuty method" + RESET);
            System.out.println("wrong id entered");
            crudSubDuty(admin);
        }
        try {
            subDutyService.delete(subDuty);
            logger.info(GREEN + "SubDuty deleted" + RESET);
            crudSubDuty(admin);
        } catch (IllegalStateException e){
            logger.error(RED + "can not delete SubDuty" + RESET);
            System.out.println("something wrong try again");
            crudSubDuty(admin);
        }
    }
    public void addDuty(Admin admin){
        Duty duty = Duty.builder()
                .dutyName(getTitle())
                .build();
        if (dutyService.validate(duty)){
            try {
                dutyService.saveOrUpdate(duty);
                logger.info(GREEN + "duty saved" + RESET);
                crudDuty(admin);
            } catch (IllegalStateException e){
                logger.error(RED + "can not save duty" + RESET);
                System.out.println("duplicate information entered");
                crudDuty(admin);
            }
        }
    }
    public void renameDuty(Admin admin){
        showAllDuties();
        System.out.println("please enter the id of duty for rename");
        int choose = getNumberFromUser();
        Duty duty = null;
        try {
            duty = dutyService.findById(choose);
            logger.info(GREEN + "duty founded" + RESET);
        } catch (NullPointerException e){
            logger.error(RED + "wrong Id input and return renameDuty method" + RESET);
            System.out.println("wrong id entered");
            crudDuty(admin);
        }
        String newTitle = getTitle();
        assert duty != null;
        duty.setDutyName(newTitle);
        try {
            dutyService.saveOrUpdate(duty);
            logger.info(GREEN + "duty updated" + RESET);
            crudDuty(admin);
        } catch (IllegalStateException e){
            logger.error(RED + "can not update duty" + RESET);
            System.out.println("something wrong try again");
            crudDuty(admin);
        }
    }
    public void removeDuty(Admin admin){
        showAllDuties();
        System.out.println("please enter the id of duty for delete");
        int choose = getNumberFromUser();
        Duty duty = null;
        try {
            duty = dutyService.findById(choose);
            logger.info(GREEN + "duty founded" + RESET);
        } catch (NullPointerException e){
            logger.error(RED + "wrong Id input and return removeDuty method" + RESET);
            System.out.println("wrong id entered");
            crudDuty(admin);
        }
        try {
            dutyService.delete(duty);
            logger.info(GREEN + "duty deleted" + RESET);
            crudDuty(admin);
        } catch (IllegalStateException e){
            logger.error(RED + "can not delete duty" + RESET);
            System.out.println("something wrong try again");
            crudDuty(admin);
        }
    }

    public void customerMenu() {
        System.out.println("press 1 for sign up");
        System.out.println("press 2 for sign in");
        System.out.println("press 3 for EXIT");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> signUpCustomer();
            case 2 -> signInCustomer();
            case 3 -> startMenu();
            default -> customerMenu();
        }
    }

    public void showSignUpInfo(Customer customer) {
        System.out.println("this is your password please write it down it will disappear after 5 second");
        System.out.println(customer.getPassword());
    }

    public void signUpCustomer() {
        Customer customer = Customer.builder()
                .firstname(getFirstName())
                .lastname(getLastName())
                .username(getUsername())
                .password(getPassword())
                .email(getEmail())
                .phoneNumber(getPhoneNumber())
                .city(getCity())
                .address(getAddress())
                .postalCode(getPostalCode())
                .build();
        if (customerService.validate(customer)) {
            try {
                customerService.saveOrUpdate(customer);
                logger.info(GREEN + "customer saved" + RESET);
            } catch (IllegalStateException e) {
                logger.error(RED + "can not save customer" + RESET);
                System.out.println("duplicate information entered");
                customerMenu();
            }
            showSignUpInfo(customer);
            try {
                logger.info(GREEN + "stop the running ap for wait to save password by user" + RESET);
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            customerMenu();
        } else {
            logger.warn(YELLOW + "some information is not valid" + RESET);
            System.out.println("wrong input entered");
            customerMenu();
        }
    }

    public void signInCustomer() {
        String username = getUsername();
        String password = getPassword();
        Customer customer;
        try {
            customer = customerService.findByUsernameAndPassword(username, password);
            logger.info(GREEN + "sign in complete" + RESET);
            customerSecondMenu(customer);
        } catch (NotFoundException e) {
            logger.error(RED + "this customer is not exist in table" + RESET);
            System.out.println("wrong username or password");
            customerMenu();
        }
    }

    public void customerSecondMenu(Customer customer) {
        System.out.println("press 1 for make an order");
        System.out.println("press 2 for check your orders");
        System.out.println("press 3 to add Balance");
        System.out.println("press 4 to edit your profile");
        System.out.println("press 5 to EXIT");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> makeAnOrder(customer);
            case 2 -> checkOrdersCustomer(customer);
            case 3 -> System.out.println("add balance is under construction!!!:-)");
            case 4 -> System.out.println("edit");
            default -> startMenu();
        }
    }

    public void makeAnOrder(Customer customer) {
        showAllDuties();
        System.out.println("please enter the the duty id");
        int dutyId = getNumberFromUser();
        Duty duty = null;
        try {
            duty = dutyService.findById(dutyId);
            logger.info(GREEN + "a duty selected now showing sub duties" + RESET);
        } catch (NullPointerException e) {
            logger.error(RED + "no duty founded now return to customerSecondMenu method" + RESET);
            customerSecondMenu(customer);
        }
        assert duty != null;
        showAllSubDuties(duty.getId());
        System.out.println("please enter the the duty id");
        int subDutyId = getNumberFromUser();
        SubDuty subDuty = null;
        try {
            subDuty = subDutyService.findById(subDutyId);
            logger.info(GREEN + "a sub duty selected now creating order" + RESET);
        } catch (ReturnMethodException e) {
            logger.error(RED + "no sub duty founded now return to customerSecondMenu method" + RESET);
            customerSecondMenu(customer);
        }
        creatOrder(customer, subDuty);
    }

    public void showAllDuties() {
        List<Duty> duties = dutyService.duties();
        if (duties.size()== 0){
            System.out.println(YELLOW + "no duty founded");
        } else {
            System.out.println(CYAN + "all of duties" + RESET);
            duties.forEach(System.out::println);
        }
    }

    public void showAllSubDuties(int id) {
        List<SubDuty> subDuties = subDutyService.subDuties(id);
        if (subDuties.size()== 0){
            System.out.println(YELLOW + "no subDuty founded");
        } else {
            System.out.println(CYAN + "all of sub duties" + RESET);
            subDuties.forEach(System.out::println);
        }
    }

    public void creatOrder(Customer customer, SubDuty subDuty) {
        Date current = new Date(System.currentTimeMillis());
        String dateCurrent = current.toString();
        Order order = Order.builder()
                .customer(customer)
                .subDuty(subDuty)
                .dateCreatOrder(creatDate(dateCurrent))
                .needExpert(creatDate(takeExpertNeedDate()))
                .bestTime(chooseBestOrderTime())
                .takeOfferLimit(getOrderLimit())
                .build();
        if (orderService.validate(order)) {
            try {
                orderService.saveOrUpdate(order);
                logger.info(GREEN + "customer saved" + RESET);
                customerSecondMenu(customer);
            } catch (IllegalStateException e) {
                logger.error(RED + "can not save order" + RESET);
                System.out.println("some thing wrong entered try again");
                customerSecondMenu(customer);
            }
        } else {
            logger.warn(YELLOW + "some information is not valid" + RESET);
            System.out.println("wrong input entered");
            customerSecondMenu(customer);
        }
    }

    public String takeExpertNeedDate() {
        System.out.println("please enter the date that you need expert");
        System.out.println("the input format must be yyyy-MM-dd");
        return getStringFromUser();
    }

    public BestTime chooseBestOrderTime() {
        System.out.println("press 1 to morning(9-12 a.m)");
        System.out.println("press 1 to noon(12-3 9.m)");
        System.out.println("press 1 to afternoon(3-6 p.m)");
        System.out.println("press 1 to night(6-9 p.m)");
        int choose = getNumberFromUser();
        BestTime bestTime = null;
        switch (choose) {
            case 1 -> bestTime = BestTime.MORNING;
            case 2 -> bestTime = BestTime.NOON;
            case 3 -> bestTime = BestTime.AFTERNOON;
            case 4 -> bestTime = BestTime.NIGHT;
            default -> chooseBestOrderTime();
        }
        return bestTime;
    }

    public Date creatDate(String date) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            System.out.println("wrong pattern input");
            customerMenu();
        }
        return date1;
    }
    public void waitingCostumerOrders(Customer customer){
        List<Order> orders = orderService.findByCustomerAndCondition(customer.getId()
                ,OrderCondition.WAIT_FOR_ACCEPT);
        if (orders.size() > 0){
            System.out.println(BLUE + "there is some orders that need to be accept please check" + RESET);
        }
    }

    public void checkOrdersCustomer(Customer customer) {
        waitingCostumerOrders(customer);
        System.out.println("press 1 for see ongoing orders");
        System.out.println("press 2 for see waiting for accept orders");
        System.out.println("press 3 for see orders that accepted and waiting for expert");
        System.out.println("press 4 for see ongoing orders");
        System.out.println("press 5 for see done orders");
        System.out.println("press 6 for EXIT");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> checkOngoingCustomerOrder(customer);
            case 2 -> System.out.println("waiting orders");
            case 3 -> System.out.println("accepted orders");
            case 4 -> System.out.println("ongoing orders");
            case 5 -> System.out.println("done orders");
            case 6 -> customerSecondMenu(customer);
            default -> checkOrdersCustomer(customer);
        }
    }
@PreUpdate
    public void changeOrderCondition(){
        String current = new Date(System.currentTimeMillis()).toString();
        String patternCurrent = creatDate(current).toString();
        List<Order> orders = null;
        try {
            orders = orderService.allOrders();
            logger.info(GREEN + "all orders founded" + RESET);
        } catch (ReturnMethodException e) {
            logger.error(RED + "no order founded" + RESET);
        }
        assert orders != null;
        for (Order order : orders){
            String limitDate = order.getTakeOfferLimit().toString();
            if (patternCurrent.equals(limitDate)){
                order.setOrderCondition(OrderCondition.WAIT_FOR_ACCEPT);
                orderService.saveOrUpdate(order);
            }
        }
        logger.info(GREEN + "all orders in table updated" + RESET);
    }

    public void checkOngoingCustomerOrder(Customer customer) {
        OrderCondition orderCondition = OrderCondition.RECEIVING_OFFERS;
        List<Order> orders = null;
        try {
            orders = orderService.findByCustomerAndCondition(customer.getId(), orderCondition);
            logger.info(GREEN + "ongoing orders founded" + RESET);
        } catch (ReturnMethodException e) {
            logger.error(RED + "no ongoing orders founded and return to costumerSecondMenu" + RESET);
            customerSecondMenu(customer);
        }
        System.out.println(CYAN + "ongoing orders" + RESET);
        assert orders != null;
        orders.forEach(System.out::println);
        System.out.println("please enter the order id you want to check");
        int orderId = getNumberFromUser();
        Order order = null;
        try {
            order = orderService.findById(orderId);
            logger.info(GREEN + "order founded" + RESET);
        }catch (NullPointerException e){
            logger.error(RED + "no order founded and return to customerSecondMenu" + RESET);
            System.out.println("wrong id input try again");
            customerSecondMenu(customer);
        }
        assert order != null;
        System.out.println(order);
        acceptOfferForAnOrder(customer,order);
    }
    public void acceptOfferForAnOrder(Customer customer,Order order){
        System.out.println("do you want to see this order's offers?(y/n)");
        String choose = getStringFromUser();
        if(!choose.equals("y")){
            checkOrdersCustomer(customer);
        } else {
            List<Offer> offers = findAllOffersOfOrder(customer,order);
            System.out.println(BLUE + "all offers" + RESET);
            offers.forEach(System.out::println);
            System.out.println("please enter the id of offer you want to accept");
            int chooseOffer = getNumberFromUser();
            Offer offer = null;
            try {
                offer = offerService.findById(chooseOffer);
                logger.info(GREEN + "offer founded" + RESET);
            } catch (ReturnMethodException e){
                logger.error(RED + "no offer founded and return to customerSecondMenu" + RESET);
                System.out.println("wrong id entered");
                customerSecondMenu(customer);
            }
            System.out.println(BLUE + "you chosen this offer are you sure?(y/n)" + RESET);
            System.out.println(offer);
            String chooseSec = getStringFromUser();
            if (!chooseSec.equals("y")){
                acceptOfferForAnOrder(customer,order);
            } else {
                assert offer != null;
                makeOfferAccepted(offer,order,customer);
                makeRestOffersRejected(offers,offer);
                System.out.println("offer accepted");
                customerSecondMenu(customer);
            }
        }
    }
    public List<Offer> findAllOffersOfOrder(Customer customer,Order order){
        List<Offer> offers = null;
        try {
            offers = offerService.findByOrderId(order.getId());
            logger.info(GREEN + "all offers founded" + RESET);
        } catch (ReturnMethodException e){
            logger.error(RED + "no offer founded and return to costumerSecondMenu method" + RESET);
            System.out.println("there is no offer for this order yet");
            customerSecondMenu(customer);
        }
        return offers;
    }
    public void makeOfferAccepted(Offer offer,Order order,Customer customer){
        order.setOrderCondition(OrderCondition.ACCEPTED);
        offer.setOfferCondition(OfferCondition.ACCEPTED);
        try {
            orderService.saveOrUpdate(order);
            offerService.saveOrUpdate(offer);
            logger.info(GREEN + "order and offer condition changed to accepted" + RESET);
        } catch (ReturnMethodException e){
            logger.error(RED + "can not update order or offer and return to customerSecondMenu" + RESET);
            System.out.println("can not accept this offer please try again");
            customerSecondMenu(customer);
        }
    }
    public void makeRestOffersRejected(List<Offer> offers, Offer offer){
        for (Offer tempOffer : offers){
            if(!Objects.equals(tempOffer.getId(), offer.getId())){
                tempOffer.setOfferCondition(OfferCondition.REJECTED);
                try {
                    offerService.saveOrUpdate(tempOffer);
                }catch (ReturnMethodException e){
                    logger.error(RED + "can not update other offers" + RESET);
                }
            }
        }
    }
}
