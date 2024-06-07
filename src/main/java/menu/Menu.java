package menu;


import base.exception.NotFoundException;
import base.exception.ReturnMethodException;
import entity.*;
import entity.enums.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.*;
import util.applicationcontext.ApplicationContext;
import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.io.File;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Menu {

    private final Scanner sc = new Scanner(System.in);

    CustomerService customerService = ApplicationContext.getCustomerService();
    DutyService dutyService = ApplicationContext.getDutyService();
    SubDutyService subDutyService = ApplicationContext.getSubDutyService();
    OrderService orderService = ApplicationContext.getOrderService();
    OfferService offerService = ApplicationContext.getOfferService();
    AdminService adminService = ApplicationContext.getAdminService();
    ExpertService expertService = ApplicationContext.getExpertService();
    CommentsService commentsService = ApplicationContext.getCommentsService();
    RequestService requestService = ApplicationContext.getRequestService();

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

    public String getTitle() {
        System.out.println("please enter the name");
        return getStringFromUser();
    }

    public int getPrice() {
        System.out.println("please enter price");
        return getNumberFromUser();
    }

    public String getDescription() {
        System.out.println("please enter description");
        return getStringFromUser();
    }

    public String getImageFilePath() {
        System.out.println("please enter your image file path");
        return getStringFromUser();
    }

    public int getTakeLong() {
        System.out.println("please enter how long can you finish this order by days");
        return getNumberFromUser();
    }

    public Date getOrderLimit() {
        System.out.println("please enter the order limit max 30 days");
        System.out.println("it is the time that order can receive offers by day");
        int input = getNumberFromUser();
        DateTime current = new DateTime(new Date(System.currentTimeMillis()));
        String limitDate = current.plusDays(input).toString();
        return creatDate(limitDate);
    }

    public String getRejectReason() {
        System.out.println("please enter the reason for rejection of this expert");
        return getStringFromUser();
    }

    public int getRate() {
        System.out.println("please enter the rate between 0-5 for this order Process");
        return getNumberFromUser();
    }

    public String getAdditionalComments() {
        System.out.println("please read if you have something to say about this order process");
        return getStringFromUser();
    }

    public void startMenu() {
        System.out.println(CYAN + "*****WELCOME*****" + RESET);
        System.out.println("press 1 for customer menu");
        System.out.println("press 2 for expert menu");
        System.out.println("press 3 for admin menu");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> customerMenu();
            case 2 -> expertMenu();
            case 3 -> signInAdmin();
            default -> startMenu();
        }
    }

    public void signInAdmin() {
        String username = getUsername();
        String password = getPassword();
        Admin admin;
        try {
            admin = adminService.findByUsernameAndPassword(username, password);
            logger.info(GREEN + "admin founded" + RESET);
            adminMenu(admin);
        } catch (NotFoundException e) {
            logger.error(RED + "no admin founded and return to startMenu method" + RESET);
            System.out.println("wrong username or password");
            startMenu();
        }
    }

    public void adminMenu(Admin admin) {
        System.out.println(CYAN + "WELCOME " + admin.getFirstname() + RESET);
        System.out.println("press 1 for CRUD duty");
        System.out.println("press 2 for CRUD subDuty");
        System.out.println("press 3 for check experts");
        System.out.println("press 4 for EXIT");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> crudDuty(admin);
            case 2 -> crudSubDuty(admin);
            case 3 -> checkExpert(admin);
            default -> startMenu();
        }
    }

    public void crudDuty(Admin admin) {
        System.out.println("press 1 for add duty");
        System.out.println("press 2 for rename duty");
        System.out.println("press 3 for remove duty");
        System.out.println("press 4 for show all duties");
        System.out.println("press 5 for EXIT");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> addDuty(admin);
            case 2 -> renameDuty(admin);
            case 3 -> removeDuty(admin);
            case 4 -> showAllDuties();
            case 5 -> adminMenu(admin);
            default -> crudDuty(admin);
        }
    }

    public void crudSubDuty(Admin admin) {
        Duty duty = selectDuty(admin);
        System.out.println("press 1 for add SubDuty");
        System.out.println("press 2 for edit SubDuty");
        System.out.println("press 3 for remove SubDuty");
        System.out.println("press 4 for show all SubDuties");
        System.out.println("press 5 for EXIT");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> addSubDuty(admin, duty);
            case 2 -> editSubDuty(admin, duty);
            case 3 -> removeSubDuty(admin, duty);
            case 4 -> showAllSubDuties(duty.getId());
            case 5 -> adminMenu(admin);
            default -> crudSubDuty(admin);
        }
    }

    public Duty selectDuty(Admin admin) {
        showAllDuties();
        System.out.println("please enter the id of duty");
        int choose = getNumberFromUser();
        Duty duty = null;
        try {
            duty = dutyService.findById(choose);
            logger.info(GREEN + "duty founded" + RESET);
        } catch (NullPointerException e) {
            logger.error(RED + "wrong Id input and return" + RESET);
            System.out.println("wrong id entered");
            crudSubDuty(admin);
        }
        return duty;
    }

    public void addSubDuty(Admin admin, Duty duty) {
        SubDuty subDuty = SubDuty.builder()
                .subDutyName(getTitle())
                .price(getPrice())
                .description(getDescription())
                .duty(duty)
                .build();
        if (subDutyService.validate(subDuty)) {
            try {
                subDutyService.saveOrUpdate(subDuty);
                logger.info(GREEN + "SubDuty saved" + RESET);
                crudSubDuty(admin);
            } catch (IllegalStateException e) {
                logger.error(RED + "can not save SubDuty" + RESET);
                System.out.println("duplicate information entered");
                crudSubDuty(admin);
            }
        } else {
            logger.warn(YELLOW + "wrong input format" + RESET);
            addSubDuty(admin, duty);
        }
    }

    public void editSubDuty(Admin admin, Duty duty) {
        System.out.println("press 1 for edit name");
        System.out.println("press 2 for edit price");
        System.out.println("press 3 for edit description");
        System.out.println("press 4 for change duty");
        System.out.println("press 5 for show all subDuties");
        System.out.println("press 6 for EXIT");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> editSubDutyName(admin, duty);
            case 2 -> editSubDutyPrice(admin, duty);
            case 3 -> editSubDutyDescription(admin, duty);
            case 4 -> changeDuty(admin, duty);
            case 5 -> showAllSubDuties(duty.getId());
            case 6 -> crudSubDuty(admin);
            default -> editSubDuty(admin, duty);
        }
    }

    public SubDuty selectSubDuty(Admin admin, Duty duty) {
        showAllSubDuties(duty.getId());
        System.out.println("please enter the id of SubDuty");
        int choose = getNumberFromUser();
        SubDuty subDuty = null;
        try {
            subDuty = subDutyService.findById(choose);
            logger.info(GREEN + "subDuty founded" + RESET);
        } catch (NullPointerException e) {
            logger.error(RED + "wrong Id input and return " + RESET);
            System.out.println("wrong id entered");
            crudSubDuty(admin);
        }
        return subDuty;
    }

    public void editSubDutyName(Admin admin, Duty duty) {
        SubDuty subDuty = selectSubDuty(admin, duty);
        String newTitle = getTitle();
        assert subDuty != null;
        subDuty.setSubDutyName(newTitle);
        try {
            subDutyService.saveOrUpdate(subDuty);
            logger.info(GREEN + "subDuty updated" + RESET);
            crudSubDuty(admin);
        } catch (IllegalStateException e) {
            logger.error(RED + "can not update subDuty" + RESET);
            System.out.println("something wrong try again");
            crudSubDuty(admin);
        }
    }

    public void editSubDutyPrice(Admin admin, Duty duty) {
        SubDuty subDuty = selectSubDuty(admin, duty);
        int newPrice = getPrice();
        assert subDuty != null;
        subDuty.setPrice(newPrice);
        try {
            subDutyService.saveOrUpdate(subDuty);
            logger.info(GREEN + "subDuty updated" + RESET);
            crudSubDuty(admin);
        } catch (IllegalStateException e) {
            logger.error(RED + "can not update subDuty" + RESET);
            System.out.println("something wrong try again");
            crudSubDuty(admin);
        }
    }

    public void changeDuty(Admin admin, Duty duty) {
        SubDuty subDuty = selectSubDuty(admin, duty);
        Duty newDuty = selectDuty(admin);
        assert subDuty != null;
        subDuty.setDuty(newDuty);
        try {
            subDutyService.saveOrUpdate(subDuty);
            logger.info(GREEN + "subDuty updated" + RESET);
            crudSubDuty(admin);
        } catch (IllegalStateException e) {
            logger.error(RED + "can not update subDuty" + RESET);
            System.out.println("something wrong try again");
            crudSubDuty(admin);
        }
    }

    public void editSubDutyDescription(Admin admin, Duty duty) {
        SubDuty subDuty = selectSubDuty(admin, duty);
        String newDescription = getTitle();
        assert subDuty != null;
        subDuty.setDescription(newDescription);
        try {
            subDutyService.saveOrUpdate(subDuty);
            logger.info(GREEN + "subDuty updated" + RESET);
            crudSubDuty(admin);
        } catch (IllegalStateException e) {
            logger.error(RED + "can not update subDuty" + RESET);
            System.out.println("something wrong try again");
            crudSubDuty(admin);
        }
    }

    public void removeSubDuty(Admin admin, Duty duty) {
        showAllSubDuties(duty.getId());
        System.out.println("please enter the id of SubDuty for delete");
        int choose = getNumberFromUser();
        SubDuty subDuty = null;
        try {
            subDuty = subDutyService.findById(choose);
            logger.info(GREEN + "subDuty founded" + RESET);
        } catch (NullPointerException e) {
            logger.error(RED + "wrong Id input and return crudSubDuty method" + RESET);
            System.out.println("wrong id entered");
            crudSubDuty(admin);
        }
        try {
            subDutyService.delete(subDuty);
            logger.info(GREEN + "SubDuty deleted" + RESET);
            crudSubDuty(admin);
        } catch (IllegalStateException e) {
            logger.error(RED + "can not delete SubDuty" + RESET);
            System.out.println("something wrong try again");
            crudSubDuty(admin);
        }
    }

    public void addDuty(Admin admin) {
        Duty duty = Duty.builder()
                .dutyName(getTitle())
                .build();
        if (dutyService.validate(duty)) {
            try {
                dutyService.saveOrUpdate(duty);
                logger.info(GREEN + "duty saved" + RESET);
                crudDuty(admin);
            } catch (IllegalStateException e) {
                logger.error(RED + "can not save duty" + RESET);
                System.out.println("duplicate information entered");
                crudDuty(admin);
            }
        }
    }

    public void renameDuty(Admin admin) {
        showAllDuties();
        System.out.println("please enter the id of duty for rename");
        int choose = getNumberFromUser();
        Duty duty = null;
        try {
            duty = dutyService.findById(choose);
            logger.info(GREEN + "duty founded" + RESET);
        } catch (NullPointerException e) {
            logger.error(RED + "wrong Id input and return crudDuty method" + RESET);
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
        } catch (IllegalStateException e) {
            logger.error(RED + "can not update duty" + RESET);
            System.out.println("something wrong try again");
            crudDuty(admin);
        }
    }

    public void removeDuty(Admin admin) {
        showAllDuties();
        System.out.println("please enter the id of duty for delete");
        int choose = getNumberFromUser();
        Duty duty = null;
        try {
            duty = dutyService.findById(choose);
            logger.info(GREEN + "duty founded" + RESET);
        } catch (NullPointerException e) {
            logger.error(RED + "wrong Id input and return crudDuty method" + RESET);
            System.out.println("wrong id entered");
            crudDuty(admin);
        }
        try {
            dutyService.delete(duty);
            logger.info(GREEN + "duty deleted" + RESET);
            crudDuty(admin);
        } catch (IllegalStateException e) {
            logger.error(RED + "can not delete duty" + RESET);
            System.out.println("something wrong try again");
            crudDuty(admin);
        }
    }

    public void checkExpert(Admin admin) {
        newExpertNotification();
        System.out.println("press 1 to check Awaiting experts");
        System.out.println("press 2 to check updated experts");
        System.out.println("press 3 to edit duties and subDuties of experts");
        System.out.println("press 4 for EXIT");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> checkAwaitingExperts(admin);
            case 2 -> checkUpdatedExperts(admin);
            case 3 -> editExpertSubDuties(admin);
            case 4 -> adminMenu(admin);
        }
    }

    public void checkAwaitingExperts(Admin admin) {
        List<Expert> waitingExperts = waitingExperts();
        waitingExperts.forEach(System.out::println);
        System.out.println("please enter the id of expert");
        int expertId = getNumberFromUser();
        Expert expert;
        try {
            expert = expertService.findById(expertId);
            logger.info(GREEN + "expert founded" + RESET);
            setExpertCondition(expert, admin);
        } catch (NullPointerException e) {
            logger.error(RED + "no expert founded and return" + RESET);
            System.out.println("wrong id input");
            checkExpert(admin);
        }
    }

    public void checkUpdatedExperts(Admin admin) {
        List<Expert> updatedExperts = updatedExperts();
        updatedExperts.forEach(System.out::println);
        System.out.println("please enter the id of expert");
        int expertId = getNumberFromUser();
        Expert expert;
        try {
            expert = expertService.findById(expertId);
            logger.info(GREEN + "expert founded" + RESET);
            setExpertCondition(expert, admin);
        } catch (NullPointerException e) {
            logger.error(RED + "no expert founded and return" + RESET);
            System.out.println("wrong id input");
            checkExpert(admin);
        }
    }

    public void editExpertSubDuties(Admin admin) {
        System.out.println("press 1 for change duty of an expert");
        System.out.println("press 2 for remove one subDuty of an expert");
        System.out.println("press 3 for add one subDuty for an expert");
        System.out.println("press 4 for EXIT");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> changeExpertDuty(admin);
            case 2 -> removeOneSubDuty(admin);
            case 3 -> addOneSubDutyToExpert(admin);
            case 4 -> checkExpert(admin);
            default -> editExpertSubDuties(admin);
        }
    }

    public void changeExpertDuty(Admin admin) {
        Expert expert = selectOneExpert(admin);
        removeExpertAllSubDuties(admin, expert);
        setExpertNewSubDuties(admin, expert);
    }

    public void removeExpertAllSubDuties(Admin admin, Expert expert) {
        expert.setSubDuties(null);
        try {
            expertService.saveOrUpdate(expert);
            logger.info(GREEN + "expert subDuties removed" + RESET);
        } catch (IllegalStateException e) {
            logger.error(RED + "can not remove expert subDuties, try again" + RESET);
            checkExpert(admin);
        }
    }

    public void setExpertNewSubDuties(Admin admin, Expert expert) {
        showAllDuties();
        Duty duty = selectDuty(admin);
        boolean flag = true;
        List<SubDuty> subDuties = duty.getSubDuties();
        List<SubDuty> expertNewSubDuties = new ArrayList<>();
        while (flag) {
            subDuties.forEach(System.out::println);
            System.out.println("please enter the id of subDuty");
            int subDutyId = getNumberFromUser();
            SubDuty subDuty = null;
            try {
                subDuty = subDutyService.findById(subDutyId);
                logger.info(GREEN + "subDuty founded" + RESET);
            } catch (NullPointerException e) {
                logger.error(RED + "no subDuty founded and return" + RESET);
                System.out.println("wrong id input");
            }
            expertNewSubDuties.add(subDuty);
            System.out.println("do you want more subDuties?(y/n)");
            String wantMoreSubDuties = getStringFromUser();
            if (!wantMoreSubDuties.equals("y")) {
                expert.setSubDuties(expertNewSubDuties);
                try {
                    expertService.saveOrUpdate(expert);
                    logger.info(GREEN + "expert new subDuties set" + RESET);
                } catch (IllegalStateException e) {
                    logger.error(RED + "can not set new expert subDuties and return" + RESET);
                    checkExpert(admin);
                }
                flag = false;
            }
        }
    }

    public Expert selectOneExpert(Admin admin) {
        List<Expert> experts = null;
        try {
            experts = expertService.allExperts();
            logger.info(GREEN + "all experts founded" + RESET);
        } catch (ReturnMethodException e) {
            logger.error(RED + "no expert founded and return" + RESET);
            checkExpert(admin);
        }
        assert experts != null;
        experts.forEach(System.out::println);
        System.out.println("please enter the id of expert");
        int expertId = getNumberFromUser();
        Expert expert = null;
        try {
            expert = expertService.findById(expertId);
            logger.info(GREEN + "expert founded" + RESET);
        } catch (IllegalStateException e) {
            logger.error(RED + "no expert founded and return" + RESET);
            System.out.println("wrong id input");
            checkExpert(admin);
        }
        return expert;
    }

    public void removeOneSubDuty(Admin admin) {
        Expert expert = selectOneExpert(admin);
        List<SubDuty> subDuties = expert.getSubDuties();
        Duty expertDuty = subDuties.get(0).getDuty();
        SubDuty subDuty = selectSubDuty(admin, expertDuty);
        List<SubDuty> newSubDuties = new ArrayList<>();
        for (SubDuty tempSubDuty : subDuties) {
            if (!Objects.equals(tempSubDuty.getId(), subDuty.getId())) {
                newSubDuties.add(tempSubDuty);
            }
        }
        expert.setSubDuties(newSubDuties);
        try {
            expertService.saveOrUpdate(expert);
            logger.info(GREEN + "subDuty removed" + RESET);
        } catch (IllegalStateException e) {
            logger.error(RED + "can not update expert and return" + RESET);
            checkExpert(admin);
        }
    }

    public void addOneSubDutyToExpert(Admin admin) {
        boolean flag = true;
        Expert expert = selectOneExpert(admin);
        List<SubDuty> subDuties = expert.getSubDuties();
        Duty expertDuty = subDuties.get(0).getDuty();
        SubDuty subDuty = selectSubDuty(admin, expertDuty);
        for (SubDuty tempSubDuty : subDuties) {
            if (Objects.equals(tempSubDuty.getId(), subDuty.getId())) {
                logger.warn(YELLOW + "duplicate subDuty selected" + RESET);
                flag = false;
                break;
            }
        }
        if (flag) {
            subDuties.add(subDuty);
            expert.setSubDuties(subDuties);
            try {
                expertService.saveOrUpdate(expert);
                logger.info(GREEN + "subDuty added" + RESET);
            } catch (IllegalStateException e) {
                logger.error(RED + "can not add subDuty and return" + RESET);
                checkExpert(admin);
            }
        } else {
            System.out.println("expert already selected this subDuty");
            checkExpert(admin);
        }
    }

    public void setExpertCondition(Expert expert, Admin admin) {
        System.out.println(expert);
        saveExpertImageToHDD(expert);
        System.out.println("the image saved in F:\\Maktab\\FinalProjectFirstPhase\\src\\main\\java\\images\\save from dataBase");
        System.out.println("you can see the image from there");
        System.out.println(BLUE + "is this expert acceptable?(y/n)" + RESET);
        String choose = getStringFromUser();
        if (choose.equals("y")) {
            expert.setExpertCondition(ExpertCondition.ACCEPTED);
            expert.setSubDuties(makeExpertSubDuties(admin, expert));
            try {
                expertService.saveOrUpdate(expert);
                logger.info(GREEN + "expert accepted" + RESET);
                checkExpert(admin);
            } catch (IllegalStateException e) {
                logger.error(RED + "can not accept expert try again" + RESET);
                checkExpert(admin);
            }
        } else {
            expert.setExpertCondition(ExpertCondition.REJECTED);
            expert.setRejectReason(getRejectReason());
            try {
                expertService.saveOrUpdate(expert);
                logger.info(GREEN + "expert rejected" + RESET);
                checkExpert(admin);
            } catch (IllegalStateException e) {
                logger.error(RED + "can not reject expert try again" + RESET);
                checkExpert(admin);
            }
        }
    }

    public List<SubDuty> makeExpertSubDuties(Admin admin, Expert expert) {
        boolean flag = true;
        List<SubDuty> subDuties = new ArrayList<>();
        SubDuty subDuty;
        Requested requested = null;
        try {
            requested = requestService.findExpertRequests(expert.getId());
            logger.info(GREEN + "all requests founded" + RESET);
        } catch (NotFoundException e) {
            logger.error(RED + "no request founded and return" + RESET);
        }
        while (flag) {
            assert requested != null;
            List<SubDuty> reqSubDuties = requested.getSubDuties();
            for (SubDuty subDutyTemp : reqSubDuties) {
                System.out.println(subDutyTemp);
            }
            System.out.println("please enter the id of subDuty you want add expert to it");
            int subDutyId = getNumberFromUser();
            try {
                subDuty = subDutyService.findById(subDutyId);
                subDuties.add(subDuty);
                reqSubDuties.remove(subDuty);
            } catch (NullPointerException e) {
                logger.error(RED + "no subDuty founded" + RESET);
                checkExpert(admin);
            }
            if (reqSubDuties.size() > 0) {
                System.out.println("do you want to continue");
                String choose = getStringFromUser();
                if (!choose.equals("y")) {
                    flag = false;
                }
            } else {
                flag = false;
            }
        }
        return subDuties;
    }

    public void saveExpertImageToHDD(Expert expert) {
        byte[] expertImage = expert.getExpertImage();
        String path = "F:\\Maktab\\FinalProjectFirstPhase\\src\\main\\java\\images\\save from dataBase\\" + expert.getFirstname() + " " + expert.getLastname() + ".jpg";
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(expertImage);
        } catch (IOException e) {
            logger.error(RED + "someThing wrong during savin image" + RESET);
        }
    }

    public void newExpertNotification() {
        List<Expert> waitingExperts = waitingExperts();
        List<Expert> updatedExperts = updatedExperts();
        if (waitingExperts.size() > 0) {
            System.out.println(BLUE + "**ATTENTION** " + waitingExperts.size() + " experts are Awaiting to be accepted" + RESET);
        }
        if (updatedExperts.size() > 0) {
            System.out.println(YELLOW + "**ATTENTION** " + updatedExperts.size() + " Updated experts are Awaiting to be accepted" + RESET);
        }
    }

    public List<Expert> waitingExperts() {
        List<Expert> experts = null;
        try {
            experts = expertService.findByCondition(ExpertCondition.AWAITING);
            logger.info(GREEN + "all awaiting experts founded" + RESET);
        } catch (ReturnMethodException e) {
            logger.warn(YELLOW + "no waiting experts founded");
        }
        return experts;
    }

    public List<Expert> updatedExperts() {
        List<Expert> experts = null;
        try {
            experts = expertService.findByCondition(ExpertCondition.UPDATED);
            logger.info(GREEN + "all updated experts founded" + RESET);
        } catch (ReturnMethodException e) {
            logger.warn(YELLOW + "no updated experts founded");
        }
        return experts;
    }

    public void expertMenu() {
        System.out.println("press 1 for sign up");
        System.out.println("press 2 for sign in");
        System.out.println("press 3 for EXIT");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> signUpExpert();
            case 2 -> signInExpert();
            case 3 -> startMenu();
            default -> expertMenu();
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

    public byte[] expertImage() {
        byte[] image = new byte[0];
        File inputImage = new File(getImageFilePath());
        String mimetype= new MimetypesFileTypeMap().getContentType(inputImage);
        String type = mimetype.split("/")[0];
        if(type.equals("image")) {
            logger.info(GREEN + "current image type" + RESET);
            try {
                image = Files.readAllBytes(inputImage.toPath());
            } catch (IOException e) {
                logger.error(RED + "wrong file input try again" + RESET);
                expertImage();
            }
        }
        else {
            logger.error(RED + "wrong file input try again" + RESET);
        }
        return image;
    }

    public Duty chooseExpertDuty() {
        showAllDuties();
        System.out.println("please enter the id of duty");
        int choose = getNumberFromUser();
        Duty duty = null;
        try {
            duty = dutyService.findById(choose);
            logger.info(GREEN + "duty founded" + RESET);
        } catch (NullPointerException e) {
            logger.error(RED + "wrong Id input and return" + RESET);
            System.out.println("wrong id entered");
            expertMenu();
        }
        return duty;
    }

    public List<SubDuty> chooseExpertSubDuties(Duty duty) {
        SubDuty subDuty;
        boolean flag = true;
        List<SubDuty> subDuties = new ArrayList<>();
        while (flag) {
            showAllSubDuties(duty.getId());
            System.out.println("please enter the subDuty id you want work in it");
            int choose = getNumberFromUser();
            try {
                subDuty = subDutyService.findById(choose);
                subDuties.add(subDuty);
                logger.info(GREEN + "subDuty chosen" + RESET);
            } catch (NullPointerException e) {
                logger.error(RED + "no subDuty founded" + RESET);
                System.out.println("wrong id input");
                chooseExpertSubDuties(duty);
            }
            System.out.println(BLUE + "do you want to add more subDuties?(y/n)" + RESET);
            String wantToContinue = getStringFromUser();
            if (!wantToContinue.equals("y")) {
                flag = false;
            }
        }
        return subDuties;
    }

    public void signUpExpert() {
        Expert expert = Expert.builder()
                .firstname(getFirstName())
                .lastname(getLastName())
                .username(getUsername())
                .password(getPassword())
                .nationalCode(getNationalCode())
                .email(getEmail())
                .phoneNumber(getPhoneNumber())
                .city(getCity())
                .address(getAddress())
                .postalCode(getPostalCode())
                .expertImage(expertImage())
                .build();
        if (expertService.validate(expert)) {
            try {
                expertService.saveOrUpdate(expert);
                logger.info(GREEN + "expert saved" + RESET);
            } catch (IllegalStateException e) {
                logger.error(RED + "can not save expert" + RESET);
                System.out.println("duplicate information entered");
                expertMenu();
            }
        } else {
            logger.warn(YELLOW + "some information is not valid" + RESET);
            System.out.println("wrong input entered");
            expertMenu();
        }
        Requested requested = Requested.builder()
                .expert(expert)
                .subDuties(chooseExpertSubDuties(chooseExpertDuty()))
                .build();
        if (requestService.validate(requested)) {
            try {
                requestService.saveOrUpdate(requested);
                logger.info(GREEN + "your request saved" + RESET);
                expertMenu();
            } catch (IllegalStateException e) {
                logger.error(RED + "can not save request" + RESET);
                expertMenu();
            }
        } else {
            logger.warn(YELLOW + "some information is not valid" + RESET);
            System.out.println("wrong input entered");
            expertMenu();
        }

    }

    public void signInExpert() {
        String username = getUsername();
        String password = getPassword();
        Expert expert;
        try {
            expert = expertService.findByUsernameAndPassword(username, password);
            logger.info(GREEN + "sign in complete" + RESET);
            checkExpertCondition(expert);
        } catch (NotFoundException e) {
            logger.error(RED + "this expert is not exist in table" + RESET);
            System.out.println("wrong username or password");
            customerMenu();
        }
    }

    public void checkExpertCondition(Expert expert) {
        ExpertCondition expertCondition = expert.getExpertCondition();
        if (expertCondition == ExpertCondition.ACCEPTED) {
            expertSecondMenu(expert);
        } else if (expertCondition == ExpertCondition.REJECTED) {
            System.out.println(RED + "SORRY YOUR REQUEST HAS BEEN REJECTED PLEASE READ THE REASONS AND FIX THEM THEN TRY AGAIN" + RESET);
            System.out.println(expert.getRejectReason());
            customerMenu();
        } else {
            System.out.println(YELLOW + "YOUR REQUEST IS CHECKING THANKS FOR YOUR PATIENT" + RESET);
            startMenu();
        }
    }

    public void expertSecondMenu(Expert expert) {
        System.out.println("press 1 to see available orders and send Offers");
        System.out.println("press 2 to see your Time schedule");
        System.out.println("press 3 to see your Financial situation");
        System.out.println("press 4 to all of your offers");
        System.out.println("press 5 to edit your profile");
        System.out.println("press 6 to EXIT");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> showAllAvailableOrders(expert);
            case 2 -> showTimeSchedule(expert);
            case 3 -> showExpertFinancialSituation();
            case 4 -> showAllOffers(expert);
            case 5 -> checkExpertOffers(expert);
            default -> startMenu();
        }
    }

    public void showAllAvailableOrders(Expert expert) {
        SubDuty subDuty = selectOneSubDuty(expert);
        Order order = selectOneOrder(expert, subDuty);
        System.out.println("do you want to send an offer for this order?(y/n)");
        String choose = getStringFromUser();
        if (!choose.equals("y")) {
            showAllAvailableOrders(expert);
        } else {
            if (checkOrderDate(expert, order)) {
                List<Offer> offers = order.getOffers();
                if (offers.size() > 0) {
                    for (Offer offer : offers) {
                        if (Objects.equals(offer.getExpert().getId(), expert.getId())) {
                            System.out.println(YELLOW + "can not send offer for an order twice");
                            System.out.println(offer);
                            break;
                        } else {
                            makeAnOffer(expert, subDuty, order);
                        }
                    }
                } else {
                    makeAnOffer(expert, subDuty, order);
                }
            }
        }
    }

    public void showTimeSchedule(Expert expert) {
        List<Offer> offers = allExpertOffers(expert);
        for (Offer offer : offers) {
            if (offer.getOfferCondition() == OfferCondition.ACCEPTED || offer.getOfferCondition() == OfferCondition.ONGOING) {
                System.out.println(CYAN + offer + RESET);
            }
        }
        expertSecondMenu(expert);
    }

    public void showExpertFinancialSituation() {
        System.out.println("under maintenance");
    }

    public void showAllOffers(Expert expert) {
        List<Offer> offers = allExpertOffers(expert);
        offers.forEach(System.out::println);
        expertSecondMenu(expert);
    }

    public void editExpertProfile(Expert expert) {
        System.out.println("press 1 for edit username");
        System.out.println("press 2 for edit password");
        System.out.println("press 3 for edit email");
        System.out.println("press 4 for edit phoneNumber");
        System.out.println("press 5 for edit address");
        System.out.println("press 6 for EXIT");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> editExpertUsername(expert);
            case 2 -> editExpertPassword(expert);
            case 3 -> editExpertEmail(expert);
            case 4 -> editExpertPhoneNumber(expert);
            case 5 -> editExpertAddress(expert);
            case 6 -> expertSecondMenu(expert);
            default -> editExpertProfile(expert);
        }
    }

    public void checkExpertOffers(Expert expert) {
        boolean flag = true;
        List<Offer> offers = allExpertOffers(expert);
        for (Offer offer : offers) {
            if (offer.getOfferCondition() != OfferCondition.REJECTED && offer.getOfferCondition() != OfferCondition.DONE) {
                flag = false;
                break;
            }
        }
        if (flag) {
            editExpertProfile(expert);
        } else {
            System.out.println(YELLOW + "you have active offers and can not change your profile" + RESET);
            expertSecondMenu(expert);
        }
    }

    public void editExpertUsername(Expert expert) {
        String newUsername = getUsername();
        expert.setUsername(newUsername);
        expert.setExpertCondition(ExpertCondition.UPDATED);
        expert.setUpdatedField(UpdatedField.USERNAME);
        if (expertService.validate(expert)) {
            try {
                expertService.saveOrUpdate(expert);
                logger.info(GREEN + "expert username updated" + RESET);
                System.out.println(YELLOW + "your profile is changed and now you need to wait for admin to active your account again" + RESET);
                startMenu();
            } catch (IllegalStateException e) {
                logger.error(RED + "username can not updated for duplicate or null" + RESET);
                editExpertProfile(expert);
            }
        } else {
            logger.warn(YELLOW + "some information is not valid" + RESET);
            System.out.println("wrong input entered");
            editExpertProfile(expert);
        }
    }

    public void editExpertPassword(Expert expert) {
        String newPassword = getPassword();
        expert.setPassword(newPassword);
        expert.setExpertCondition(ExpertCondition.UPDATED);
        expert.setUpdatedField(UpdatedField.PASSWORD);
        if (expertService.validate(expert)) {
            try {
                expertService.saveOrUpdate(expert);
                logger.info(GREEN + "expert Password updated" + RESET);
                System.out.println(YELLOW + "your profile is changed and now you need to wait for admin to active your account again" + RESET);
                startMenu();
            } catch (IllegalStateException e) {
                logger.error(RED + "Password can not updated for null" + RESET);
                editExpertProfile(expert);
            }
        } else {
            logger.warn(YELLOW + "some information is not valid" + RESET);
            System.out.println("wrong input entered");
            editExpertProfile(expert);
        }
    }

    public void editExpertEmail(Expert expert) {
        String newEmail = getEmail();
        expert.setEmail(newEmail);
        expert.setExpertCondition(ExpertCondition.UPDATED);
        expert.setUpdatedField(UpdatedField.EMAIL);
        if (expertService.validate(expert)) {
            try {
                expertService.saveOrUpdate(expert);
                logger.info(GREEN + "expert Email updated" + RESET);
                System.out.println(YELLOW + "your profile is changed and now you need to wait for admin to active your account again" + RESET);
                startMenu();
            } catch (IllegalStateException e) {
                logger.error(RED + "email can not updated for duplicate or null" + RESET);
                editExpertProfile(expert);
            }
        } else {
            logger.warn(YELLOW + "some information is not valid" + RESET);
            System.out.println("wrong input entered");
            editExpertProfile(expert);
        }
    }

    public void editExpertPhoneNumber(Expert expert) {
        String newPhoneNumber = getPhoneNumber();
        expert.setPhoneNumber(newPhoneNumber);
        expert.setExpertCondition(ExpertCondition.UPDATED);
        expert.setUpdatedField(UpdatedField.PHONE_NUMBER);
        if (expertService.validate(expert)) {
            try {
                expertService.saveOrUpdate(expert);
                logger.info(GREEN + "expert PhoneNumber updated" + RESET);
                System.out.println(YELLOW + "your profile is changed and now you need to wait for admin to active your account again" + RESET);
                startMenu();
            } catch (IllegalStateException e) {
                logger.error(RED + "PhoneNumber can not updated for duplicate or null" + RESET);
                editExpertProfile(expert);
            }
        } else {
            logger.warn(YELLOW + "some information is not valid" + RESET);
            System.out.println("wrong input entered");
            editExpertProfile(expert);
        }
    }

    public void editExpertAddress(Expert expert) {
        String newCity = getCity();
        String newAddress = getAddress();
        String newPostalCode = getPostalCode();
        if (newCity != null) {
            expert.setCity(newCity);
            expert.setExpertCondition(ExpertCondition.UPDATED);
            expert.setUpdatedField(UpdatedField.ADDRESS);
        }
        if (newAddress != null) {
            expert.setAddress(newAddress);
            expert.setExpertCondition(ExpertCondition.UPDATED);
            expert.setUpdatedField(UpdatedField.ADDRESS);
        }
        if (newPostalCode != null) {
            expert.setPostalCode(newPostalCode);
            expert.setExpertCondition(ExpertCondition.UPDATED);
            expert.setUpdatedField(UpdatedField.ADDRESS);
        }
        if (expertService.validate(expert)) {
            try {
                expertService.saveOrUpdate(expert);
                logger.info(GREEN + "expert address updated" + RESET);
                System.out.println(YELLOW + "your profile is changed and now you need to wait for admin to active your account again" + RESET);
                startMenu();
            } catch (IllegalStateException e) {
                logger.error(RED + "address can not updated for duplicate or null" + RESET);
                editExpertProfile(expert);
            }
        } else {
            logger.warn(YELLOW + "some information is not valid" + RESET);
            System.out.println("wrong input entered");
            editExpertProfile(expert);
        }
    }

    public SubDuty selectOneSubDuty(Expert expert) {
        List<SubDuty> subDuties = expert.getSubDuties();
        subDuties.forEach(System.out::println);
        System.out.println("please enter the id of subDuty");
        int subDutyId = getNumberFromUser();
        SubDuty subDuty = null;
        try {
            subDuty = subDutyService.findById(subDutyId);
            logger.info(GREEN + "subDuty founded" + RESET);
        } catch (NullPointerException e) {
            logger.error(RED + "no subDuty founded and return" + RESET);
            expertSecondMenu(expert);
        }
        return subDuty;
    }

    public Order selectOneOrder(Expert expert, SubDuty subDuty) {
        List<Order> orders = subDuty.getOrders();

        for (Order tempOrder : orders) {
            if (tempOrder.getOrderCondition() == OrderCondition.RECEIVING_OFFERS) {
                System.out.println(tempOrder);
            }
        }
        System.out.println("please enter the id of order");
        int orderId = getNumberFromUser();
        Order order = null;
        try {
            order = orderService.findById(orderId);
            logger.info(GREEN + "order founded" + RESET);
        } catch (NullPointerException e) {
            logger.error(RED + "no order founded and return" + RESET);
            System.out.println("there is no order for this subDuty yet");
            expertSecondMenu(expert);
        }
        return order;
    }

    public void makeAnOffer(Expert expert, SubDuty subDuty, Order order) {
        String date = new DateTime(new Date(System.currentTimeMillis())).toString();
        Offer offer = Offer.builder()
                .offerPrice(getOfferPrice(subDuty))
                .takeLong(getTakeLong())
                .expert(expert)
                .order(order)
                .creatOfferDate(creatDate(date))
                .build();
        if (offerService.validate(offer)) {
            try {
                offerService.saveOrUpdate(offer);
                logger.info(GREEN + "offer saved" + RESET);
                expertSecondMenu(expert);
            } catch (IllegalStateException e) {
                logger.error(RED + "can not save offer and return" + RESET);
                showAllAvailableOrders(expert);
            }
        } else {
            logger.warn(YELLOW + "wrong input and return" + RESET);
            makeAnOffer(expert, subDuty, order);
        }
    }

    public int getOfferPrice(SubDuty subDuty) {
        boolean flag = true;
        int offerPrice = 0;
        while (flag) {
            System.out.println("default price is: " + subDuty.getPrice());
            offerPrice = getPrice();
            if (offerPrice < subDuty.getPrice()) {
                System.out.println("price can not be less than default price");
            } else {
                flag = false;
            }
        }
        return offerPrice;
    }

    public List<Offer> allExpertOffers(Expert expert) {
        List<Offer> offers = null;
        try {
            offers = offerService.findAllExpertOffers(expert.getId());
            logger.info(GREEN + "all offers founded" + RESET);
        } catch (ReturnMethodException e) {
            logger.error(RED + "no offer founded and return" + RESET);
        }
        return offers;
    }

    public boolean checkOrderDate(Expert expert, Order order) {
        boolean flag = true;
        Date orderNeedExpert = order.getNeedExpert();
        BestTime bestTime = order.getBestTime();
        List<Offer> offers = allExpertOffers(expert);
        for (Offer offer : offers) {
            Order tempOrder = offer.getOrder();
            if (offer.getOfferCondition() == OfferCondition.ACCEPTED
                    && tempOrder.getNeedExpert().toString().equals(orderNeedExpert.toString())
                    && bestTime.equals(tempOrder.getBestTime())) {
                System.out.println(YELLOW + "you have accepted job in this time " + RESET);
                System.out.println(offer);
                flag = false;
                break;
            }
        }
        return flag;
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
                customerMenu();
            } catch (IllegalStateException e) {
                logger.error(RED + "can not save customer" + RESET);
                System.out.println("duplicate information entered");
                customerMenu();
            }
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
        changeOrderCondition();
        waitingCostumerOrders(customer);
        ongoingOrderNotification(makeOrderOngoing(customer));
        doneOrderNotification(makeOrderDone(customer));
        delayedOrderNotification(makeDelayedOrderDone(customer));
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
            case 4 -> editCustomerProfile(customer);
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
        if (duties.size() == 0) {
            System.out.println(YELLOW + "no duty founded" + RESET);
        } else {
            System.out.println(CYAN + "all of duties" + RESET);
            duties.forEach(System.out::println);
        }
    }

    public void showAllSubDuties(int id) {
        List<SubDuty> subDuties = subDutyService.subDuties(id);
        if (subDuties.size() == 0) {
            System.out.println(YELLOW + "no subDuty founded");
        } else {
            System.out.println(CYAN + "all of sub duties" + RESET);
            subDuties.forEach(System.out::println);
        }
    }

    public void creatOrder(Customer customer, SubDuty subDuty) {
        DateTime current = new DateTime(new Date(System.currentTimeMillis()));
        String dateCurrent = current.toString();
        Order order = Order.builder()
                .customer(customer)
                .subDuty(subDuty)
                .dateCreatOrder(creatDate(dateCurrent))
                .needExpert(creatDate(takeExpertNeedDate()))
                .orderPrice(getOfferPrice(subDuty))
                .description(getDescription())
                .bestTime(chooseBestOrderTime())
                .takeOfferLimit(getOrderLimit())
                .build();
        if (orderService.validate(order)) {
            try {
                orderService.saveOrUpdate(order);
                logger.info(GREEN + "order saved" + RESET);
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
        System.out.println(new DateTime(new Date(System.currentTimeMillis())));
        System.out.println("the input format must be yyyy-MM-dd");
        return getStringFromUser();
    }

    public BestTime chooseBestOrderTime() {
        System.out.println("press 1 to morning(9-12 a.m)");
        System.out.println("press 2 to noon(12-3 p.m)");
        System.out.println("press 3 to afternoon(3-6 p.m)");
        System.out.println("press 4 to night(6-9 p.m)");
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

    public void waitingCostumerOrders(Customer customer) {
        List<Order> orders = orderService.findByCustomerAndCondition(customer.getId()
                , OrderCondition.WAIT_FOR_ACCEPT);
        if (orders.size() > 0) {
            System.out.println(BLUE + "there is some orders that need to be accept please check" + RESET);
        }
    }

    public List<Order> findByCondition(OrderCondition orderCondition, Customer customer) {
        List<Order> orders = null;
        try {
            orders = orderService.findByCustomerAndCondition(customer.getId(), orderCondition);
            logger.info(GREEN + "accepted orders founded" + RESET);
        } catch (ReturnMethodException e) {
            logger.error(RED + "no accepted orders founded and return to costumerSecondMenu" + RESET);
        }
        return orders;
    }

    public List<Order> makeOrderOngoing(Customer customer) {
        List<Order> waitToChangeOrders = new ArrayList<>();
        DateTime currentDate = new DateTime(new Date(System.currentTimeMillis()));
        OrderCondition orderCondition = OrderCondition.ACCEPTED;
        List<Order> orders = findByCondition(orderCondition, customer);
        for (Order order : orders) {
            DateTime tempDate = new DateTime(order.getNeedExpert());
            if (tempDate.withTimeAtStartOfDay().isEqual(currentDate.withTimeAtStartOfDay())) {
                waitToChangeOrders.add(order);
            }
        }
        return waitToChangeOrders;
    }

    public List<Order> makeOrderDone(Customer customer) {
        List<Order> waitToDoneOrders = new ArrayList<>();
        DateTime current = new DateTime(new Date(System.currentTimeMillis()));
        OrderCondition orderCondition = OrderCondition.ONGOING;
        List<Order> orders = findByCondition(orderCondition, customer);
        for (Order order : orders) {
            List<Offer> offers = findAllOffersOfOrder(customer, order);
            for (Offer offer : offers) {
                if (offer.getOfferCondition() == OfferCondition.ONGOING) {
                    DateTime dateTime1 = new DateTime(order.getNeedExpert());
                    DateTime dateTime2 = dateTime1.plusDays(offer.getTakeLong());
                    if (dateTime2.withTimeAtStartOfDay().isEqual(current.withTimeAtStartOfDay())) {
                        waitToDoneOrders.add(order);
                    }
                }
            }
        }
        return waitToDoneOrders;
    }

    public List<Order> makeDelayedOrderDone(Customer customer) {
        List<Order> waitToDoneDelayedOrders = new ArrayList<>();
        OrderCondition orderCondition = OrderCondition.DELAYED;
        List<Order> orders = findByCondition(orderCondition, customer);
        for (Order order : orders) {
            if (order.getOrderCondition() == OrderCondition.DELAYED)
                waitToDoneDelayedOrders.add(order);
        }
        return waitToDoneDelayedOrders;
    }

    public void ongoingOrderNotification(List<Order> orders) {
        if (orders.size() > 0) {
            System.out.println(BLUE + "you accepted an offer for today please check it" + RESET);
            System.out.println("you can set it on check your order");
        }
    }

    public void doneOrderNotification(List<Order> orders) {
        if (orders.size() > 0) {
            System.out.println(BLUE + "you have an order that must be end today please check it" + RESET);
            System.out.println("you can set it on check your order");
        }
    }

    public void delayedOrderNotification(List<Order> orders) {
        if (orders.size() > 0) {
            System.out.println(BLUE + "you have a delayed order if its done check it" + RESET);
            System.out.println("you can set it on check your order");
        }
    }

    public void checkOrdersCustomer(Customer customer) {
        System.out.println("press 1 for see taking offers orders");
        System.out.println("press 2 for see waiting for accept orders");
        System.out.println("press 3 for see orders that accepted and waiting for expert");
        System.out.println("press 4 for see ongoing orders");
        System.out.println("press 5 for see done orders");
        System.out.println("press 6 for set order ongoing");
        System.out.println("press 7 for set done or delay orders");
        System.out.println("press 8 for set done or delay orders");
        System.out.println("press 9 for EXIT");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> checkTakingOffersCustomerOrder(customer);
            case 2 -> checkWaitingForAcceptOrders(customer);
            case 3 -> checkAcceptedOrders(customer);
            case 4 -> checkOngoingOrders(customer);
            case 5 -> checkDoneOrders(customer);
            case 6 -> setOrderOngoing(customer);
            case 7 -> setOrderDoneOrDelayed(customer);
            case 8 -> setDelayedOrderDone(customer);
            case 9 -> customerSecondMenu(customer);
            default -> checkOrdersCustomer(customer);
        }
    }

    public void changeOrderCondition() {
        DateTime dateTime = new DateTime(new Date(System.currentTimeMillis()));
        List<Order> orders = null;
        try {
            orders = orderService.allOrders();
            logger.info(GREEN + "all orders founded" + RESET);
        } catch (ReturnMethodException e) {
            logger.error(RED + "no order founded" + RESET);
        }
        assert orders != null;
        for (Order order : orders) {
            DateTime limitDate = new DateTime(order.getTakeOfferLimit());
            if (limitDate.withTimeAtStartOfDay().isEqual(dateTime.withTimeAtStartOfDay())
                    && order.getOrderCondition() == OrderCondition.RECEIVING_OFFERS) {
                order.setOrderCondition(OrderCondition.WAIT_FOR_ACCEPT);
                orderService.saveOrUpdate(order);
            }
        }
        logger.info(GREEN + "all orders in table updated" + RESET);
    }

    public void checkTakingOffersCustomerOrder(Customer customer) {
        OrderCondition orderCondition = OrderCondition.RECEIVING_OFFERS;
        List<Order> orders = null;
        try {
            orders = orderService.findByCustomerAndCondition(customer.getId(), orderCondition);
            logger.info(GREEN + "taking offers orders founded" + RESET);
        } catch (ReturnMethodException e) {
            logger.error(RED + "no taking offers orders founded and return to costumerSecondMenu" + RESET);
            customerSecondMenu(customer);
        }
        System.out.println(CYAN + "taking offers orders" + RESET);
        assert orders != null;
        orders.forEach(System.out::println);
        Order order = findById(customer);
        System.out.println(order);
        acceptOfferForAnOrder(customer, order);
    }

    public void acceptOfferForAnOrder(Customer customer, Order order) {
        System.out.println("do you want to see this order's offers?(y/n)");
        String choose = getStringFromUser();
        if (!choose.equals("y")) {
            checkOrdersCustomer(customer);
        } else {
            List<Offer> offers = findAllOffersOfOrder(customer, order);
            System.out.println(BLUE + "all offers" + RESET);
            offers.forEach(System.out::println);
            System.out.println("please enter the id of offer you want to accept");
            int chooseOffer = getNumberFromUser();
            Offer offer = null;
            try {
                offer = offerService.findById(chooseOffer);
                logger.info(GREEN + "offer founded" + RESET);
            } catch (NullPointerException e) {
                logger.error(RED + "no offer founded and return to customerSecondMenu" + RESET);
                System.out.println("wrong id entered");
                customerSecondMenu(customer);
            }
            assert offer != null;
            saveExpertImageToHDD(offer.getExpert());
            System.out.println("the expert image saved in F:\\Maktab\\FinalProjectFirstPhase\\src\\main\\java\\images\\save from dataBase");
            System.out.println(BLUE + "you chosen this offer are you sure?(y/n)" + RESET);
            System.out.println(offer);
            String chooseSec = getStringFromUser();
            if (!chooseSec.equals("y")) {
                acceptOfferForAnOrder(customer, order);
            } else {
                makeOfferAccepted(offer, order, customer);
                makeRestOffersRejected(order,offer);
                System.out.println("offer accepted");
                customerSecondMenu(customer);
            }
        }
    }

    public List<Offer> findAllOffersOfOrder(Customer customer, Order order) {
        List<Offer> offers = null;
        try {
            offers = offerService.findAllOfOneOrderOffers(order.getId());
            logger.info(GREEN + "all offers founded" + RESET);
        } catch (ReturnMethodException e) {
            logger.error(RED + "no offer founded and return to costumerSecondMenu method" + RESET);
            System.out.println("there is no offer for this order yet");
            customerSecondMenu(customer);
        }
        return offers;
    }

    public void makeOfferAccepted(Offer offer, Order order, Customer customer) {
        order.setOrderCondition(OrderCondition.ACCEPTED);
        offer.setOfferCondition(OfferCondition.ACCEPTED);
        try {
            orderService.saveOrUpdate(order);
            offerService.saveOrUpdate(offer);
            logger.info(GREEN + "order and offer condition changed to accepted" + RESET);
            customerSecondMenu(customer);
        } catch (ReturnMethodException e) {
            logger.error(RED + "can not update order or offer and return to customerSecondMenu" + RESET);
            System.out.println("can not accept this offer please try again");
            customerSecondMenu(customer);
        }
    }

    public void makeRestOffersRejected(Order order,Offer offer) {
      List<Offer> offers = order.getOffers();
      for (Offer tempOffer : offers){
          if (!Objects.equals(tempOffer.getId(), offer.getId())){
              tempOffer.setOfferCondition(OfferCondition.REJECTED);
              try {
                  offerService.saveOrUpdate(tempOffer);
              } catch (IllegalStateException e){
                  System.out.println("na");
              }
          } else {
              logger.info("jumped");
          }
      }
      logger.info(GREEN + "all other offers rejected" + RESET);
    }

    public void checkWaitingForAcceptOrders(Customer customer) {
        OrderCondition orderCondition = OrderCondition.WAIT_FOR_ACCEPT;
        List<Order> orders = null;
        try {
            orders = orderService.findByCustomerAndCondition(customer.getId(), orderCondition);
            logger.info(GREEN + "waiting for accept orders founded" + RESET);
        } catch (ReturnMethodException e) {
            logger.error(RED + "no waiting for accept orders founded and return to costumerSecondMenu" + RESET);
            customerSecondMenu(customer);
        }
        System.out.println(CYAN + "waiting for accept orders" + RESET);
        assert orders != null;
        orders.forEach(System.out::println);
        Order order = findById(customer);
        System.out.println(order);
        acceptOfferForAnOrder(customer, order);
    }

    public void checkAcceptedOrders(Customer customer) {
        OrderCondition orderCondition = OrderCondition.ACCEPTED;
        List<Order> orders = null;
        try {
            orders = orderService.findByCustomerAndCondition(customer.getId(), orderCondition);
            logger.info(GREEN + "accepted orders founded" + RESET);
        } catch (ReturnMethodException e) {
            logger.error(RED + "no accepted orders founded and return to costumerSecondMenu" + RESET);
            customerSecondMenu(customer);
        }
        System.out.println(CYAN + "accepted orders" + RESET);
        assert orders != null;
        orders.forEach(System.out::println);
        Order order = findById(customer);
        System.out.println(order);
        customerSecondMenu(customer);
    }

    public void checkOngoingOrders(Customer customer) {
        OrderCondition orderCondition = OrderCondition.ONGOING;
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
        Order order = findById(customer);
        System.out.println(order);
        customerSecondMenu(customer);
    }

    public void checkDoneOrders(Customer customer) {
        OrderCondition orderCondition = OrderCondition.DONE;
        List<Order> orders = null;
        try {
            orders = orderService.findByCustomerAndCondition(customer.getId(), orderCondition);
            logger.info(GREEN + "done orders founded" + RESET);
        } catch (ReturnMethodException e) {
            logger.error(RED + "no done orders founded and return to costumerSecondMenu" + RESET);
            customerSecondMenu(customer);
        }
        System.out.println(CYAN + "done orders" + RESET);
        assert orders != null;
        orders.forEach(System.out::println);
        Order order = findById(customer);
        System.out.println(order);
        customerSecondMenu(customer);
    }

    public void setOrderOngoing(Customer customer) {
        List<Order> orders = makeOrderOngoing(customer);
        orders.forEach(System.out::println);
        Order order = findById(customer);
        Offer offer = findAcceptedOffer(order);
        System.out.println(YELLOW + "Has the specialist referred to you?(y/n)" + RESET);
        String choose = getStringFromUser();
        if (choose.equals("y")) {
            setOngoingCondition(customer, order, offer);
        } else {
            System.out.println(BLUE + "sorry, we will call the expert immediately" + RESET);
            customerSecondMenu(customer);
        }
    }

    public Offer findAcceptedOffer(Order order) {
        Offer offer = null;
        List<Offer> offers = order.getOffers();
        for (Offer tempOffer : offers) {
            if (tempOffer.getOfferCondition() == OfferCondition.ACCEPTED) {
                offer = tempOffer;
                break;
            }
        }
        return offer;
    }

    public void setOngoingCondition(Customer customer, Order order, Offer offer) {
        offer.setOfferCondition(OfferCondition.ONGOING);
        order.setOrderCondition(OrderCondition.ONGOING);
        try {
            offerService.saveOrUpdate(offer);
            orderService.saveOrUpdate(order);
            logger.info(GREEN + "order and offer updated to ongoing" + RESET);
        } catch (IllegalStateException e) {
            logger.error(RED + "can not update order or offer and return" + RESET);
            customerSecondMenu(customer);
        }
    }

    public Order findById(Customer customer) {
        System.out.println("please enter the order id");
        int orderId = getNumberFromUser();
        Order order = null;
        try {
            order = orderService.findById(orderId);
            logger.info(GREEN + "order founded" + RESET);
        } catch (NullPointerException e) {
            logger.error(RED + "no order founded and return" + RESET);
            System.out.println("wrong id entered");
            customerSecondMenu(customer);
        }
        return order;
    }

    public Offer findOngoingOffer(Order order) {
        Offer offer = null;
        List<Offer> offers = order.getOffers();
        for (Offer tempOffer : offers) {
            if (tempOffer.getOfferCondition() == OfferCondition.ONGOING) {
                offer = tempOffer;
                break;
            }
        }
        return offer;
    }

    public void setDoneCondition(Customer customer, Order order, Offer offer) {
        offer.setOfferCondition(OfferCondition.DONE);
        order.setOrderCondition(OrderCondition.DONE);
        try {
            offerService.saveOrUpdate(offer);
            orderService.saveOrUpdate(order);
            logger.info(GREEN + "order and offer updated to Done" + RESET);
            makeComment(order);
        } catch (IllegalStateException e) {
            logger.error(RED + "can not update order or offer and return" + RESET);
            customerSecondMenu(customer);
        }
    }

    public void setDelayDays(Customer customer, Order order, Offer offer) {
        DateTime current = new DateTime(new Date(System.currentTimeMillis()));
        DateTime start = new DateTime((order.getNeedExpert()));
        DateTime dateMustEnd = start.plusDays(offer.getTakeLong());
        int delay = 0;
        if (order.getOrderCondition() == OrderCondition.DELAYED) {
            delay = current.getDayOfWeek() - dateMustEnd.getDayOfWeek();
        }
        offer.setDelayDays(delay);
        try {
            offerService.saveOrUpdate(offer);
            logger.info(GREEN + "offer days of delay set" + RESET);
            customerSecondMenu(customer);
        } catch (IllegalStateException e) {
            logger.error(RED + "can not set days of delay" + RESET);
            customerSecondMenu(customer);
        }
    }

    public void setDelayCondition(Customer customer, Order order, Offer offer) {
        offer.setOfferCondition(OfferCondition.DELAYED);
        order.setOrderCondition(OrderCondition.DELAYED);
        setDelayDays(customer, order, offer);
        try {
            offerService.saveOrUpdate(offer);
            orderService.saveOrUpdate(order);
            logger.info(GREEN + "order and offer updated to DELAYED" + RESET);
            customerSecondMenu(customer);
        } catch (IllegalStateException e) {
            logger.error(RED + "can not update order or offer and return" + RESET);
            customerSecondMenu(customer);
        }
    }

    public void setOrderDoneOrDelayed(Customer customer) {
        List<Order> orders = makeOrderDone(customer);
        orders.forEach(System.out::println);
        Order order = findById(customer);
        Offer offer = findOngoingOffer(order);
        System.out.println(YELLOW + "Has the specialist finish the work?(y/n)" + RESET);
        String choose = getStringFromUser();
        if (choose.equals("y")) {
            setDoneCondition(customer, order, offer);
        } else {
            System.out.println(BLUE + "sorry for the delay, we will call the expert immediately" + RESET);
            setDelayCondition(customer, order, offer);
        }
    }

    public void setDelayedOrderDone(Customer customer) {
        List<Order> orders = makeDelayedOrderDone(customer);
        orders.forEach(System.out::println);
        Order order = findById(customer);
        Offer offer = findDelayedOffer(order);
        System.out.println(YELLOW + "Has the specialist referred to you?(y/n)" + RESET);
        String choose = getStringFromUser();
        if (choose.equals("y")) {
            setDelayCondition(customer, order, offer);
        } else {
            System.out.println(BLUE + "sorry, we will call the expert immediately" + RESET);
            customerSecondMenu(customer);
        }
    }

    public Offer findDelayedOffer(Order order) {
        Offer offer = null;
        List<Offer> offers = order.getOffers();
        for (Offer tempOffer : offers) {
            if (tempOffer.getOfferCondition() == OfferCondition.DELAYED) {
                offer = tempOffer;
                break;
            }
        }
        return offer;
    }

    public void makeComment(Order order) {
        System.out.println("do you want to send comment for this order?(y/n)");
        String choose = getStringFromUser();
        if (choose.equals("y")) {
            Comments comment = Comments.builder()
                    .rate(getRate())
                    .additionalComments(getAdditionalComments())
                    .order(order)
                    .build();
            if (commentsService.validate(comment)) {
                try {
                    commentsService.saveOrUpdate(comment);
                    logger.info(GREEN + "comment saved" + RESET);
                } catch (IllegalStateException e) {
                    logger.error(RED + "can not save comment" + RESET);
                }
            }
        }
    }

    public void editCustomerProfile(Customer customer) {
        System.out.println("press 1 for edit username");
        System.out.println("press 2 for edit password");
        System.out.println("press 3 for edit email");
        System.out.println("press 4 for edit phoneNumber");
        System.out.println("press 5 for edit address");
        System.out.println("press 6 for EXIT");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> editCustomerUsername(customer);
            case 2 -> editCustomerPassword(customer);
            case 3 -> editCustomerEmail(customer);
            case 4 -> editCustomerPhoneNumber(customer);
            case 5 -> editCustomerAddress(customer);
            case 6 -> customerSecondMenu(customer);
            default -> editCustomerProfile(customer);
        }
    }

    public void editCustomerUsername(Customer customer) {
        String newUsername = getUsername();
        customer.setUsername(newUsername);
        if (customerService.validate(customer)) {
            try {
                customerService.saveOrUpdate(customer);
                logger.info(GREEN + "customer username updated" + RESET);
            } catch (IllegalStateException e) {
                logger.error(RED + "username can not updated for duplicate or null" + RESET);
                editCustomerProfile(customer);
            }
        } else {
            logger.warn(YELLOW + "some information is not valid" + RESET);
            System.out.println("wrong input entered");
            editCustomerProfile(customer);
        }
    }

    public void editCustomerPassword(Customer customer) {
        String newPassword = getPassword();
        customer.setPassword(newPassword);
        if (customerService.validate(customer)) {
            try {
                customerService.saveOrUpdate(customer);
                logger.info(GREEN + "customer Password updated" + RESET);
            } catch (IllegalStateException e) {
                logger.error(RED + "Password can not updated for null" + RESET);
                editCustomerProfile(customer);
            }
        } else {
            logger.warn(YELLOW + "some information is not valid" + RESET);
            System.out.println("wrong input entered");
            editCustomerProfile(customer);
        }
    }


    public void editCustomerEmail(Customer customer) {
        String newEmail = getEmail();
        customer.setEmail(newEmail);
        if (customerService.validate(customer)) {
            try {
                customerService.saveOrUpdate(customer);
                logger.info(GREEN + "customer Email updated" + RESET);
            } catch (IllegalStateException e) {
                logger.error(RED + "email can not updated for duplicate or null" + RESET);
                editCustomerProfile(customer);
            }
        } else {
            logger.warn(YELLOW + "some information is not valid" + RESET);
            System.out.println("wrong input entered");
            editCustomerProfile(customer);
        }
    }

    public void editCustomerPhoneNumber(Customer customer) {
        String newPhoneNumber = getPhoneNumber();
        customer.setPhoneNumber(newPhoneNumber);
        if (customerService.validate(customer)) {
            try {
                customerService.saveOrUpdate(customer);
                logger.info(GREEN + "customer Phone Number updated" + RESET);
            } catch (IllegalStateException e) {
                logger.error(RED + "Phone Number can not updated for duplicate or null" + RESET);
                editCustomerProfile(customer);
            }
        } else {
            logger.warn(YELLOW + "some information is not valid" + RESET);
            System.out.println("wrong input entered");
            editCustomerProfile(customer);
        }
    }

    public void editCustomerAddress(Customer customer) {
        String newCity = getCity();
        String newAddress = getAddress();
        String newPostalCode = getPostalCode();
        if (newCity != null) {
            customer.setCity(newCity);
        }
        if (newAddress != null) {
            customer.setAddress(newAddress);
        }
        if (newPostalCode != null) {
            customer.setPostalCode(newPostalCode);
        }
        if (customerService.validate(customer)) {
            try {
                customerService.saveOrUpdate(customer);
                logger.info(GREEN + "customer address updated" + RESET);
            } catch (IllegalStateException e) {
                logger.error(RED + "address can not updated for duplicate or null" + RESET);
                editCustomerProfile(customer);
            }
        } else {
            logger.warn(YELLOW + "some information is not valid" + RESET);
            System.out.println("wrong input entered");
            editCustomerProfile(customer);
        }
    }
}
