package menu;


import base.exception.ReturnMethodException;
import entity.Customer;
import entity.Duty;
import entity.Order;
import entity.SubDuty;
import entity.enums.BestTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CustomerService;
import service.DutyService;
import service.OrderService;
import service.SubDutyService;
import util.PasswordGenerator;
import util.applicationcontext.ApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Menu {

    private final Scanner sc = new Scanner(System.in);

    CustomerService customerService = ApplicationContext.getCustomerService();
    DutyService dutyService = ApplicationContext.getDutyService();
    SubDutyService subDutyService = ApplicationContext.getSubDutyService();
    OrderService orderService = ApplicationContext.getOrderService();

    private final String RESET = "\u001B[0m";
    private final String YELLOW = "\u001B[33m";
    private final String RED = "\u001B[31m";
    private final String GREEN = "\u001B[32m";
    private final String BLUE = "\u001B[34m";
    private final String CYAN = "\u001B[46m";

    private final Logger logger = LoggerFactory.getLogger(Menu.class);

    public int getNumberFromUser() {
        logger.info(GREEN + "get int number input method is running" + RESET);
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

    public static String creatPassword() {
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .usePunctuation(true)
                .build();
        return passwordGenerator.generate(8);
    }

    public void startMenu() {
        System.out.println(CYAN + "*****WELCOME*****" + RESET);
        System.out.println("press 1 for customer menu");
        System.out.println("press 2 for expert menu");
        System.out.println("press 3 for admin menu");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> System.out.println("1");
            case 2 -> System.out.println("2");
            case 3 -> System.out.println("3");
            default -> startMenu();
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
                .password(creatPassword())
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
        System.out.println("please enter your password");
        String password = getStringFromUser();
        Customer customer;
        try {
            customer = customerService.findByUsernameAndPassword(username, password);
            logger.info(GREEN + "sign in complete" + RESET);
            customerSecondMenu(customer);
        } catch (ReturnMethodException e) {
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
        } catch (ReturnMethodException e) {
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
        System.out.println(CYAN + "all of duties" + RESET);
        List<Duty> duties = dutyService.duties();
        duties.forEach(System.out::println);
    }

    public void showAllSubDuties(int id) {
        List<SubDuty> subDuties = subDutyService.subDuties(id);
        System.out.println(CYAN + "all of sub duties" + RESET);
        subDuties.forEach(System.out::println);
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
                .build();
        if (orderService.validate(order)) {
            try {
                orderService.saveOrUpdate(order);
                logger.info(GREEN + "customer saved" + RESET);
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
    public void checkOrdersCustomer(Customer customer){
        System.out.println("press 1 for see ongoing orders");
        System.out.println("press 2 for see orders that accepted and waiting for expert");
        System.out.println("press 3 for see done orders");
        System.out.println("press 4 for EXIT");
        int choose = getNumberFromUser();
        switch (choose){
            case 1 -> System.out.println("ongoing orders");
            case 2 -> System.out.println("waiting orders");
            case 3 -> System.out.println("done orders");
            case 4 -> customerSecondMenu(customer);
            default -> checkOrdersCustomer(customer);
        }
    }
}
