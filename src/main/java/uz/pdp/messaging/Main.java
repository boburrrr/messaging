package uz.pdp.messaging;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import uz.pdp.messaging.model.contact.Contact;
import uz.pdp.messaging.model.contact.Status;
import uz.pdp.messaging.model.grup.Grup;
import uz.pdp.messaging.model.message.Message;
import uz.pdp.messaging.model.user.User;
import uz.pdp.messaging.service.contact.ContactService;
import uz.pdp.messaging.service.contact.ContactServiceImpl;
import uz.pdp.messaging.service.grup.GrupService;
import uz.pdp.messaging.service.grup.GrupServiceImpl;
import uz.pdp.messaging.service.message.MessageService;
import uz.pdp.messaging.service.message.MessageServiceImpl;
import uz.pdp.messaging.service.user.UserService;
import uz.pdp.messaging.service.user.UserServiceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import static uz.pdp.messaging.model.message.Status.GRUP_MESSAGE;
import static uz.pdp.messaging.model.message.Status.USER_MESSAGE;
import static uz.pdp.messaging.model.user.Role.*;

public class Main {
    static Scanner scannerStr = new Scanner(System.in);
    static Scanner scannerInt = new Scanner(System.in);
    static UserService userService = new UserServiceImpl();
    static MessageService messageService = new MessageServiceImpl();
    static ContactService contactService = new ContactServiceImpl();
    static GrupService grupService = new GrupServiceImpl();
    public static void main(String[] args) {

        while (true){
            System.out.println("1: SIGN IN     2: SIGN UP     0: EXIT");
            switch (scannerStr.nextLine()){
                case "0" -> {
                    return;
                }
                case "1" -> {
                    singIn();
                }
                case "2" -> {
                    signUp();
                }
            }
        }
    }

    private static void signUp() {
        System.out.print("Enter fullName: ");
        String fullName = scannerStr.nextLine();
        System.out.print("Enter username: ");
        String username = scannerStr.nextLine();
        System.out.print("Enter password: ");
        String password = scannerStr.nextLine();
        LocalDate localDate = LocalDate.now();
        String time = localDate.toString();
        if(userService.add(new User(time,fullName,username,password,USER))==1){
            System.out.println("Welcome create user!");
        }else{
            System.out.println("Error create user?");
        }
    }

    private static void singIn() {
        System.out.print("Enter username: ");
        String username = scannerStr.nextLine();
        System.out.print("Enter password: ");
        String password = scannerStr.nextLine();
        User user = userService.signIn(username,password);
        if(user != null) {
            switch (user.getRole()){
                case USER -> {
                    if(user.getBlock().equals(false)) {
                        userPanel(user);
                    }else{
                        System.out.println("Error user blocked?");
                    }
                }
                case ADMIN -> {
                    if(user.getBlock().equals(false)) {
                        adminPanel(user);
                    }else{
                        System.out.println("Error user blocked?");
                    }
                }
                case SUPER_ADMIN -> {
                    superAdminPanel(user);
                }
            }
        }else{
            System.out.println("Error user not found?");
        }
    }

    private static void superAdminPanel(User user) {
        while (true){
            System.out.println("1: AdminCantrole     2: TestAdminProfile     3: TestUserProfile     4: UpdateProfile     5: Displays     0: LogOut");
            switch (scannerStr.nextLine()){
                case "0" -> {
                    return;
                }
                case "1" -> {
                    adminCantrole();
                }
                case "2" -> {
                    adminPanel(user);
                }
                case "3" -> {
                    userPanel(user);
                }
                case "4" -> {
                    updateProfile(userService.returnUsernameId("super"));
                }
                case "5" -> {
                    displays();
                }
            }
        }
    }

    private static void displays() {
        System.out.println("1.Users/2.Admins/3?.Grups/4.Messages/5.GrupMessages/0.Back");
        switch (scannerStr.nextLine()){
            case "0" -> { return; }
            case "1" -> {
                ArrayList<User> users = userService.userListXlsx(USER);
                if(users.size()!=0) {
                    String path = "D:\\Javac\\arxiv\\messaging\\src\\main\\resources\\xlsxFile\\users.xlsx";
                    xlsxUser(users, path);
                }
            }
            case "2" -> {
                ArrayList<User> users = userService.userListXlsx(ADMIN);
                if(users.size()!=0) {
                    String path = "D:\\Javac\\arxiv\\messaging\\src\\main\\resources\\xlsxFile\\admins.xlsx";
                    xlsxUser(users, path);
                }
            }
            case "3" -> {
                ArrayList<Grup> grups = grupService.grupsList();
                if(grups.size()!=0) {
                    String path = "D:\\Javac\\arxiv\\messaging\\src\\main\\resources\\xlsxFile\\grups.xlsx";
                    XSSFWorkbook workbook = new XSSFWorkbook();
                    XSSFSheet sheet = workbook.createSheet("Grups");
                    XSSFRow row = sheet.createRow(0);

                    XSSFCell cell1 = row.createCell(0, CellType.STRING);
                    cell1.setCellValue("id");

                    XSSFCell cell2 = row.createCell(1, CellType.STRING);
                    cell2.setCellValue("createDate");

                    XSSFCell cell3 = row.createCell(2, CellType.STRING);
                    cell3.setCellValue("createdUsername");

                    XSSFCell cell4 = row.createCell(3, CellType.STRING);
                    cell4.setCellValue("grupName");

                    XSSFCell cell5 = row.createCell(4, CellType.STRING);
                    cell5.setCellValue("updateDate");

                    XSSFCell cell6 = row.createCell(5, CellType.STRING);
                    cell6.setCellValue("blocked");

                    int rowNumber = 1;
                    for (Grup grup : grups) {
                        XSSFRow row1 = sheet.createRow(rowNumber++);

                        String id = grup.getId() + "";
                        XSSFCell cell11 = row1.createCell(0, CellType.STRING);
                        cell11.setCellValue(id);

                        XSSFCell cell12 = row1.createCell(1, CellType.STRING);
                        cell12.setCellValue(grup.getLocalDate());

                        XSSFCell cell13 = row1.createCell(2, CellType.STRING);
                        cell13.setCellValue(userService.getById(grup.getAdminId()).getUsername());

                        XSSFCell cell14 = row1.createCell(3, CellType.STRING);
                        cell14.setCellValue(grup.getGrupName());

                        String update = "-";
                        if (grup.getUpdateLocalDate() != null) {
                            update = grup.getUpdateLocalDate();
                        }
                        XSSFCell cell15 = row1.createCell(4, CellType.STRING);
                        cell15.setCellValue(grup.getUpdateLocalDate());

                        XSSFCell cell16 = row1.createCell(5, CellType.STRING);
                        cell16.setCellValue(grup.getBlocked());
                    }
                    try {
                        workbook.write(new FileOutputStream(new File(path)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            case "4" -> {
                ArrayList<Message> messages = messageService.messageList(USER_MESSAGE);
                if(messages.size()!=0) {
                    String path = "D:\\Javac\\arxiv\\messaging\\src\\main\\resources\\xlsxFile\\user_messages.xlsx";
                    xlsxMessage(messages, path);
                }
            }
            case "5" -> {
                ArrayList<Message> messages = messageService.messageList(GRUP_MESSAGE);
                if(messages.size()!=0) {
                    String path = "D:\\Javac\\arxiv\\messaging\\src\\main\\resources\\xlsxFile\\grup_messages.xlsx";
                    xlsxMessage(messages, path);
                }
            }
        }
    }

    private static void xlsxMessage(ArrayList<Message> messages, String path) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(messages.get(0).getStatus()+"");
        XSSFRow row = sheet.createRow(0);

        XSSFCell cell1 = row.createCell(0, CellType.STRING);
        cell1.setCellValue("id");

        XSSFCell cell2 = row.createCell(1, CellType.STRING);
        cell2.setCellValue("createDate");

        XSSFCell cell3 = row.createCell(2, CellType.STRING);
        cell3.setCellValue("sentUsername");

        XSSFCell cell4 = row.createCell(3, CellType.STRING);
        if(messages.get(0).getStatus().equals(USER_MESSAGE)){
            cell4.setCellValue("seenUsername");
        }else{
            cell4.setCellValue("grupName");
        }

        XSSFCell cell5 = row.createCell(4, CellType.STRING);
        cell5.setCellValue("message");

        XSSFCell cell6 = row.createCell(5, CellType.STRING);
        cell6.setCellValue("updateDate");

        int rowNumber = 1;
        for (Message message: messages) {
            XSSFRow row1 = sheet.createRow(rowNumber++);

            String id = message.getId()+"";
            XSSFCell cell11 = row1.createCell(0,CellType.STRING);
            cell11.setCellValue(id);

            XSSFCell cell12 = row1.createCell(1,CellType.STRING);
            cell12.setCellValue(message.getLocalDate());

            XSSFCell cell13 = row1.createCell(2,CellType.STRING);
            cell13.setCellValue(userService.getById(message.getSentUserId()).getUsername());

            XSSFCell cell14 = row1.createCell(3,CellType.STRING);
            if(messages.get(0).getStatus().equals(USER_MESSAGE)){
                cell14.setCellValue(userService.getById(message.getSeenUserId()).getUsername());
            }else{
                cell14.setCellValue(grupService.getById(message.getSeenUserId()).getGrupName());
            }

            XSSFCell cell15 = row1.createCell(4,CellType.STRING);
            cell15.setCellValue(message.getMessage());

            String updateLocalDate = "-";
            if(message.getUpdateLocalDate()!= null){
                updateLocalDate = message.getUpdateLocalDate();
            }
            XSSFCell cell16 = row1.createCell(5,CellType.STRING);
            cell16.setCellValue(updateLocalDate);

        }
        try {
            workbook.write(new FileOutputStream(new File(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void xlsxUser(ArrayList<User> users, String path) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(users.get(0).getRole()+"");
        XSSFRow row = sheet.createRow(0);

        XSSFCell cell1 = row.createCell(0, CellType.STRING);
        cell1.setCellValue("id");

        XSSFCell cell2 = row.createCell(1, CellType.STRING);
        cell2.setCellValue("createDate");

        XSSFCell cell3 = row.createCell(2, CellType.STRING);
        cell3.setCellValue("fullName");

        XSSFCell cell4 = row.createCell(3, CellType.STRING);
        cell4.setCellValue("username");

        XSSFCell cell5 = row.createCell(4, CellType.STRING);
        cell5.setCellValue("password");

        XSSFCell cell6 = row.createCell(5, CellType.STRING);
        cell6.setCellValue("updateDate");

        XSSFCell cell7 = row.createCell(6, CellType.STRING);
        cell7.setCellValue("blocked");

        XSSFCell cell8 = row.createCell(7, CellType.STRING);
        cell8.setCellValue("block");

        int rowNumber = 1;
        for (User user : users) {
            XSSFRow row1 = sheet.createRow(rowNumber++);

            String id = user.getId()+"";
            XSSFCell cell11 = row1.createCell(0,CellType.STRING);
            cell11.setCellValue(id);

            XSSFCell cell12 = row1.createCell(1,CellType.STRING);
            cell12.setCellValue(user.getLocalDate());

            XSSFCell cell13 = row1.createCell(2,CellType.STRING);
            cell13.setCellValue(user.getFullName());

            XSSFCell cell14 = row1.createCell(3,CellType.STRING);
            cell14.setCellValue(user.getUsername());

            XSSFCell cell15 = row1.createCell(4,CellType.STRING);
            cell15.setCellValue(user.getPassword());

            String updateLocalDate = "-";
            if(user.getUpdateLocalDate()!= null){
                updateLocalDate = user.getUpdateLocalDate();
            }
            XSSFCell cell16 = row1.createCell(5,CellType.STRING);
            cell16.setCellValue(updateLocalDate);

            XSSFCell cell17 = row1.createCell(6,CellType.BOOLEAN);
            cell17.setCellValue(user.getBlocked());

            XSSFCell cell18 = row1.createCell(7,CellType.BOOLEAN);
            cell18.setCellValue(user.getBlock());

        }
        try {
            workbook.write(new FileOutputStream(new File(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void adminCantrole() {
        while (true){
            System.out.println("1: NewAdmin     2: AdminManagement     0:Back");
            switch (scannerStr.nextLine()){
                case "0" -> {
                    return;
                }
                case "1" -> {
                    ArrayList<User> users = userService.userShow(USER);
                    if(users.size()==0){
                        return;
                    }else {
                        userDisplay(users);
                        System.out.print("==(0->Back)>> ");
                        int index = scannerInt.nextInt() - 1;
                        if (index != -1) {
                            if (index > -1 && index < users.size()) {
                                users.get(index).setRole(ADMIN);
                                users.get(index).setBlock(false);
                                userService.updateById(users.get(index));
                            } else {
                                System.out.println("Error entered?");
                            }
                        } else {
                            return;
                        }
                    }
                }
                case "2" -> {
                    adminManagement();
                }
            }
        }
    }

    private static void adminManagement() {
        ArrayList<User> users = userService.userShow(ADMIN);
        while (true){
            System.out.println("1: Block     2: OnBlock     3: delete     0: Back");
            switch (scannerStr.nextLine()){
                case "0" -> {
                    return;
                }
                case "1" -> {
                    adminBlocked(users,true);
                }
                case "2" -> {
                    adminBlocked(users,false);
                }
                case "3" -> {
                    while (true){
                        if(users.size()==0){
                            return;
                        }else {
                            userDisplay(users);
                            System.out.print("==(0->Back)>> ");
                            int index = scannerInt.nextInt() - 1;
                            if (index != -1) {
                                if (index > -1 && index < users.size()) {
                                    users.get(index).setRole(USER);
                                    userService.updateById(users.get(index));
                                } else {
                                    System.out.println("Error entered?");
                                }
                            } else {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private static void adminBlocked(ArrayList<User> users, boolean b) {
        while (true) {
            ArrayList<User> users1 = new ArrayList<>();
            for (User user : users) {
                if (user.getBlock().equals(b)) {
                    users1.add(user);
                }
            }
            if(users1.size()==0){
                return;
            }else {
                userDisplay(users1);
                System.out.print("==(0->Back)>> ");
                int index = scannerInt.nextInt() - 1;
                if (index != -1) {
                    if (index > -1 && index < users1.size()) {
                        users1.get(index).setBlock(!b);
                        userService.updateById(users1.get(index));
                    } else {
                        System.out.println("Error entered?");
                    }
                } else {
                    return;
                }
            }
        }
    }

    private static void adminPanel(User user) {
        while (true){
            System.out.println("1: UserCantrole     2: GrupCantrole     3: TestUserProfile     4: UpdateProfile     0: LogOut");
            String buyruq = scannerStr.nextLine();
            try{
                userService.getById(user.getId());
            }catch (NullPointerException e){
                return;
            }
            switch (buyruq){
                case "0" -> {
                    return;
                }
                case "1" -> {
                    userCantrole();
                }
                case "2" -> {
                    grupCantrole(user.getId());
                }
                case "3" -> {
                    userPanel(user);
                }
                case "4" -> {
                    if(updateProfile(userService.returnUsernameId("super"))==-1){
                        return;
                    }
                }
            }
        }
    }

    private static void grupCantrole(UUID id) {
        while (true){
            System.out.println("1: NewGrup     2: UpdateGrup     0: Break");
            switch (scannerStr.nextLine()){
                case "0" -> {
                    return;
                }
                case "1" -> {
                    System.out.print("Enter grup name: ");
                    String grupName = scannerStr.nextLine();
                    LocalDate localDate = LocalDate.now();
                    String time = localDate.toString();
                    if(grupService.add(new Grup(time,id,grupName))==1){
                        System.out.println("Create new grup!");
                    }else{
                        System.out.println("Error create grup?");
                    }
                }
                case "2" -> {
                    updateGrup(id);
                }
                default -> System.out.println("Error entered?");
            }
        }
    }

    private static void updateGrup(UUID id) {
        while (true) {
            ArrayList<Grup> grups = grupService.grupShow(id);
            if (grups.size() == 0) {
                return;
            } else {
                grupDisplay(grups);
                System.out.print("Enter grup number (0->Back): ");
                int index = scannerInt.nextInt() - 1;
                if (index != -1) {
                    if (index > -1 && index < grups.size()) {
                        System.out.print("Enter UpdateGrupName or (DeleteGrup->1/2->"+(grups.get(index).getBlocked().equals(false)?"Blocked":"AnBlocked")+"/0->Back): ");
                        String amal = scannerStr.nextLine();
                        if(amal.equals("1")){
                            grupService.deleteById(grups.get(index).getId());
                            System.out.println("Delete grup");
                        }else if(amal.equals("2")){
                            grups.get(index).setBlocked(!grups.get(index).getBlocked());
                        }else if(!amal.equals("0") && !amal.equals("")){
                            grups.get(index).setGrupName(amal);
                        }
                        LocalDate localDate = LocalDate.now();
                        String time = localDate.toString();
                        grups.get(index).setUpdateLocalDate(time);
                        grupService.updateById(grups.get(index));
                    } else {
                        System.out.println("Error entered?");
                    }
                } else {
                    return;
                }
            }
        }
    }

    private static void grupDisplay(ArrayList<Grup> grups) {
        int i = 1;
        for (Grup grup : grups) {
            System.out.println(i+++": "+grup.toString());
        }
    }

    private static void userCantrole() {
        System.out.println("1: UserBlockList     2: UserOnBlockList     0: Break");
        ArrayList<User> users;
        boolean amal;
        switch (scannerStr.nextLine()){
            case "0" -> {
                return;
            }
            case "1" -> {
                amal = true;
            }
            case "2" -> {
                amal = false;
            }
            default -> {
                System.out.println("Error entered?");
                return;
            }
        }
        while (true){
            users = userService.blockList(amal);
            if(users.size()==0){
                return;
            }else {
                userDisplay(users);
                System.out.print("Enter user number (0->Back): ");
                int index = scannerInt.nextInt() - 1;
                if (index != -1) {
                    if (index > -1 && index < users.size()) {
                        users.get(index).setBlock(!amal);
                        userService.updateById(users.get(index));
                    } else {
                        System.out.println("Error entered?");
                    }
                } else {
                    return;
                }
            }
        }
    }

    private static void userPanel(User user) {
        while (true){
            System.out.println("1: MyContacts     2: MyGrups     3: Notification     4: NewContact     5: UpdateProfile     0: LogOut");
            switch (scannerStr.nextLine()){
                case "0" -> {
                    return;
                }
                case "1" -> {
                    myContacts(user.getId());
                }
                case "2" -> {
                    myGrups(user.getId());
                }
                case "3" -> {
                    notification(user.getId());
                }
                case "4" -> {
                    newContact(user.getId());
                }
                case "5" -> {
                    if(updateProfile(user.getId())==-1){
                        return;
                    };
                }
            }
        }
    }

    private static void myGrups(UUID id) {
            ArrayList<Contact> myGrups = contactService.getMyGrupContacts(id);
            if(myGrups.size()!=0) {
                contactDisplay(myGrups);
            }
                System.out.print("==(-1->NewGrup/0->Back)>> ");
                int index = scannerInt.nextInt() - 1;
                if (index == -1) {
                    return;
                } else if (index == -2) {
                    System.out.print("Enter grupName: ");
                    String grupName = scannerStr.nextLine();
                    UUID contactUserId = grupService.returnGrupNameId(grupName);
                    if (contactUserId != null) {
                        if (!contactService.checkContact(id, contactUserId)) {
                            System.out.print("Create contact (Y/N): ");
                            String amal = scannerStr.nextLine().toUpperCase();
                            if (amal.equals("N")) {
                                return;
                            } else if (amal.equals("Y")) {
                                LocalDate localDate = LocalDate.now();
                                String time = localDate.toString();
                                if (contactService.add(new Contact(time, id, contactUserId, grupService.getById(contactUserId).getGrupName(), Status.GRUP_CONTACT)) == 1) {
                                    System.out.println("muoffaqqiyatli!");
                                } else {
                                    System.out.println("Enter not found?");
                                }
                            } else {
                                System.out.println("Error enter found?");
                            }
                        } else {
                            System.out.print("bu gruppa sizda mavjud! ");
                        }
                    } else {
                        System.out.println("Error grup not found?");
                    }
                } else if (index < 0 || index >= myGrups.size()) {
                    System.out.println("Enter error number?");
                } else {
                    while (true) {
                        System.out.println(myGrups.get(index).toString());
                        ArrayList<Message> messages = messageService.ShowGrupMessage(id, myGrups.get(index).getContactUserId());
                            messageDisplay(id, messages);
                            System.out.print("message (0->Back): ");
                            String message = scannerStr.nextLine();
                            if (message.equals("0")) {
                                return;
                            } else {
                                LocalDate localDate = LocalDate.now();
                                String time = localDate.toString();
                                messageService.add(new Message(time, id, myGrups.get(index).getContactUserId(), message, GRUP_MESSAGE));
                            }
                    }
                }
    }

    private static void notification(UUID id) {
        ArrayList<Contact> myNoContacts = contactService.myNoShow(id);
        ArrayList<Contact> myContacts = contactService.myShow(id);
        ArrayList<User> myNotUsers = new ArrayList<>();
        ArrayList<User> myUsers = new ArrayList<>();
        for (Contact myContact : myContacts) {
            myUsers.add(userService.getById(myContact.getContactUserId()));
        }
        for (Contact myContact : myNoContacts) {
            for (int i = 0; i < myUsers.size(); i++) {
                if(myUsers.get(i).getId().equals(myContact.getMyId())){
                    break;
                }else if(i==myUsers.size()-1){
                    myNotUsers.add(userService.getById(myContact.getMyId()));
                }
            }
        }
        if(myNoContacts.size()==0){
            return;
        }else {
            userDisplay(myNotUsers);
            System.out.print("==(0->Back)>> ");
            int index = scannerInt.nextInt() - 1;
            if (index == -1) {
                return;
            } else if (index < 0 || index >= myNotUsers.size()) {
                System.out.println("Enter error number?");
            } else {
                while (true) {
                    System.out.println(myNotUsers.get(index).toString());
                    ArrayList<Message> messages = messageService.show(id, myNotUsers.get(index).getId());
                    if (messages.size() == 0) {
                        return;
                    } else {
                        messageDisplay(id, messages);
                        System.out.print("message (0->Back): ");
                        String message = scannerStr.nextLine();
                        if (message.equals("0")) {
                            return;
                        } else {
                            LocalDate localDate = LocalDate.now();
                            String time = localDate.toString();
                            messageService.add(new Message(time, id, myNotUsers.get(index).getId(), message, USER_MESSAGE));
                        }
                    }
                }
            }
        }
    }

    private static void userDisplay(ArrayList<User> myNotUsers) {
        int i = 1;
        for (User user : myNotUsers) {
            System.out.println(i+++": "+user.toString());
        }
    }

    private static int updateProfile(UUID id) {
        User user = userService.getById(id);
        a:
        while (true){
            System.out.print("1.FullName/2.Username/3.Password/4.Blocked"+(!user.getRole().equals(SUPER_ADMIN)?"/5.DeleteAccount":"")+"/0.Back ==> ");
            switch (scannerStr.nextLine()){
                case "0" -> {
                    break a;
                }
                case "1" -> {
                    System.out.print("Enter fullName: ");
                    String fullName = scannerStr.nextLine();
                    user.setFullName(fullName);
                }
                case "2" -> {
                    System.out.print("Enter username: ");
                    String username = scannerStr.nextLine();
                    if(userService.checkUsername(username)){
                        System.out.println("Error entered username");
                    }else {
                        user.setUsername(username);
                    }
                }
                case "3" -> {
                    System.out.print("Enter password: ");
                    String password = scannerStr.nextLine();
                    user.setPassword(password);
                }
                case "4" -> {
                    System.out.print("1.Blocked/2.AnBlocked/0.Break ==> ");
                    String blocked = scannerStr.nextLine();
                    if(blocked.equals("1")){
                        user.setBlocked(true);
                    }else if(blocked.equals("2")){
                        user.setBlocked(false);
                    }else if(!blocked.equals("0")){
                        System.out.println("Error entered number?");
                    }
                }
                case "5" -> {
                    if(user.getRole().equals(USER)) {
                        contactService.deleteUser(user.getId());
                        messageService.deleteUser(user.getId());
                        userService.deleteById(user.getId());
                        System.out.println("Deleted user");
                    }else{
                        user.setRole(USER);
                        System.out.println("Delete admin");
                        LocalDate localDate = LocalDate.now();
                        String time = localDate.toString();
                        user.setUpdateLocalDate(time);
                        userService.updateById(user);
                    }
                    return -1;
                }
                default -> System.out.println("Error entered number?");
            }
        }
        LocalDate localDate = LocalDate.now();
        String time = localDate.toString();
        user.setUpdateLocalDate(time);
        userService.updateById(user);
        return 1;
    }

    private static void newContact(UUID id) {
        System.out.print("Enter friend username: ");
        String username = scannerStr.nextLine();
        UUID contactUserId = userService.returnUsernameId(username);
        if(contactUserId != null && !username.equals(userService.getById(id).getUsername())){
            if(!contactService.checkContact(id,contactUserId)) {
                System.out.print("Create contact (Y/N): ");
                String amal = scannerStr.nextLine().toUpperCase();
                if (amal.equals("N")) {
                    return;
                } else if (amal.equals("Y")) {
                    System.out.print("Enter contact name: ");
                    String contactName = scannerStr.nextLine();
                    LocalDate localDate = LocalDate.now();
                    String time = localDate.toString();
                    if(contactService.add(new Contact(time, id, contactUserId, !contactName.equals("") ? contactName : userService.getById(contactUserId).getUsername(), Status.USER_CONTACT))==1){
                        System.out.println("muoffaqqiyatli!");
                    }else{
                        System.out.println("Enter not found?");
                    }
                }else{
                    System.out.println("Error enter found?");
                }
            }else{
                System.out.print("this contact update (Y/N): ");
                String amal = scannerStr.nextLine().toUpperCase();
                if (amal.equals("N")) {
                    return;
                } else if (amal.equals("Y")) {
                    System.out.print("Enter contact name: ");
                    String contactName = scannerStr.nextLine();
                    LocalDate localDate = LocalDate.now();
                    String time = localDate.toString();
                    Contact contact = contactService.getContact(id,contactUserId);
                    contact.setContactName(contactName);
                    contact.setUpdateLocalDate(time);
                    if(contactService.updateById(contact)){
                        System.out.println("muoffaqqiyatli !!!");
                    }else{
                        System.out.println("Error not found?");
                    }
                }else{
                    System.out.println("Error enter found?");
                }
            }
        }else{
            System.out.println("Error user not found?");
        }
    }

    private static void myContacts(UUID id) {
        while (true) {
            ArrayList<Contact> myContacts = contactService.myShow(id);
            ArrayList<User> users = new ArrayList<>();
            for (Contact myContact : myContacts) {
                users.add(userService.getById(myContact.getContactUserId()));
            }
            if(myContacts.size()==0){
                return;
            }else {
                contactDisplay(myContacts);
                System.out.print("==(0->Back)>> ");
                int index = scannerInt.nextInt() - 1;
                if (index == -1) {
                    return;
                } else if (index < 0 || index >= users.size()) {
                    System.out.println("Enter error number?");
                } else {
                    while (true) {
                        System.out.println(users.get(index).toString());
                        ArrayList<Message> messages = messageService.show(id, users.get(index).getId());
                        messageDisplay(id, messages);
                        System.out.print("message (0->Back): ");
                        String message = scannerStr.nextLine();
                        if (message.equals("0")) {
                            return;
                        } else {
                            LocalDate localDate = LocalDate.now();
                            String time = localDate.toString();
                            messageService.add(new Message(time, id, users.get(index).getId(), message, USER_MESSAGE));
                        }
                    }
                }
            }
        }

    }

    private static void messageDisplay(UUID id, ArrayList<Message> messages) {
        for (Message message : messages) {
            if(message.getSentUserId().equals(id)){
                System.out.println("     "+message.toString());
            }else{
                System.out.println(message.toString());
            }
        }
    }

    private static void contactDisplay(ArrayList<Contact> myContacts) {
        int i = 1;
        for (Contact myContact : myContacts) {
            System.out.println(i+++": "+myContact.toString());
        }
    }
}