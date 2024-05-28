package menu;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    private final Scanner sc = new Scanner(System.in);
    private final String RESET = "\u001B[0m";
    private final String YELLOW = "\u001B[33m";
    private final String RED = "\u001B[31m";
    private final String GREEN = "\u001B[32m";
    private final String BLUE = "\u001B[34m";

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

}
