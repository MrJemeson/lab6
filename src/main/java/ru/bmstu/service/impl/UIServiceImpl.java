//package ru.bmstu.service.impl;
//
//import com.opencsv.exceptions.CsvValidationException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import ru.bmstu.Render;
//import ru.bmstu.aspect.RoleCheck;
//import ru.bmstu.object.User;
//import ru.bmstu.service.UserService;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Scanner;
//
//@Service
//public class UIServiceImpl {
//    private final UserService userService;
//    private final Scanner scanner;
//
//    @Autowired
//    public UIServiceImpl(UserService userService) {
//        this.userService = userService;
//        this.scanner = new Scanner(System.in);
//    }
//
//    public void start() throws CsvValidationException, IOException {
//        Render.displayStartAuthorisation();
//        List<User> users = userService.getUsers();
//        User user;
//        String name;
//        while (true) {
//            if(scanner.hasNext()) {
//                name = scanner.nextLine();
//                if (!users.stream().map(User::getFULL_NAME).toList().contains(name)) {
//                    Render.displayNoSuchUserMessage();
//                } else {
//                    break;
//                }
//            }
//        }
//        String finalName = name;
//        if (users.stream().filter(x -> x.getFULL_NAME().equals(finalName)).toList().size() > 1) {
//            int numOfUsers = Render.displayUserList(users.stream().filter(x -> x.getFULL_NAME().equals(finalName)).toList());
//            Render.displayEnteringProfile();
//            int input;
//            while (true) {
//                if (scanner.hasNextInt()) {
//                    input = scanner.nextInt();
//                    if (input > 0 && input <= numOfUsers) {
//                        break;
//                    }
//                }
//                Render.displayWrongInput();
//            }
//            user = users.stream().filter(x -> x.getFULL_NAME().equals(finalName)).toList().get(input);
//        } else user = users.stream().filter(x -> x.getFULL_NAME().equals(finalName)).toList().get(0);
//        Render.displayWelcomeMessage(user);
//        RoleCheck.setCurrentRole(user.getROLE());
//        mainMenu();
//    }
//
//    public void mainMenu() throws CsvValidationException, IOException {
//        int intInput;
//        String input;
//        outerLoop:
//        while (true) {
//            Render.displayMainMenu();
//            if (scanner.hasNextInt()) {
//                intInput = scanner.nextInt();
//                if (!RoleCheck.isTeacher() && intInput>=2) intInput++;
//                switch (intInput) {
//                    case 1: {
//                        addUser();
//                        continue;
//                    }
//                    case 2: {
//                        if (RoleCheck.isTeacher()) {
//                            deleteStudent();
//                            continue;
//                        }
//                    }
//                    case 3: {
//                        updateStudent();
//                        continue;
//                    }
//                    case 4: {
//                        Render.displayUserList(userService.getUsers().stream().filter(x -> x.getROLE().equals("Student")).toList());
//                        continue;
//                    }
//                    case 5: {
//                        break outerLoop;
//                    }
//                    default:
//                        Render.displayWrongInput();
//                }
//            } else {
//                input = scanner.next();
//                if (input.equals("end")) {
//                    return;
//                } else {
//                    Render.displayWrongInput();
//                }
//
//            }
//        }
//        start();
//    }
//
//    private void addUser() {
//        try {
//            Render.displayEnterNameRequest();
//            String name;
//            while (true) {
//                if(scanner.hasNext()) {
//                    name = scanner.nextLine();
//                    if (name.contains(" ") && Arrays.stream(name.split(" ")).filter(x -> !Character.isDigit(x.charAt(0))).toList().size() == 2) {
//                        break;
//                    } else {
//                        Render.displayWrongInput();
//                    }
//                }
//            }
//            String role = "Student";
//            if (RoleCheck.isTeacher()) {
//                Render.displayEnterRoleRequest();
//                while (true) {
//                    if(scanner.hasNext()) {
//                        role = scanner.nextLine();
//                        if (Arrays.asList("Student", "Teacher").contains(role)) {
//                            break;
//                        } else {
//                            Render.displayWrongInput();
//                        }
//                    }
//                }
//            }
//
//            userService.addUser(name, role);
//        } catch (Exception e) {
//            Render.displayAddingUserError(e.getMessage());
//        }
//    }
//
//    private void deleteStudent() {
//        try {
//            Render.displayUserList(userService.getUsers().stream().filter(x -> x.getROLE().equals("Student")).toList());
//            Render.displayEnterIdRequest();
//            int id;
//            while (true) {
//                if (scanner.hasNextInt()) {
//                    id = scanner.nextInt();
//                    if (userService.getUsers().stream().filter(x -> x.getROLE().equals("Student")).map(User::getID).toList().contains(id)) {
//                        userService.deleteUser(id);
//                        break;
//                    }
//                }
//                Render.displayWrongInput();
//            }
//        } catch (Exception e) {
//            Render.displayDeletingUserError(e.getMessage());
//        }
//    }
//
//    private void updateStudent() {
//        try {
//            Render.displayUserList(userService.getUsers().stream().filter(x -> x.getROLE().equals("Student")).toList());
//            Render.displayEnterIdRequest();
//            int id;
//            while (true) {
//                if (scanner.hasNextInt()) {
//                    id = scanner.nextInt();
//                    if (userService.getUsers().stream().filter(x -> x.getROLE().equals("Student")).map(User::getID).toList().contains(id)) {
//                        break;
//                    }
//                }
//                Render.displayWrongInput();
//            }
//            int finalId = id;
//            Render.displayEnterTokenChangeRequest();
//            int change;
//            while (true) {
//                if (scanner.hasNextInt()) {
//                    change = scanner.nextInt();
//                    if (change * (-1) < userService.getUsers().stream().filter(x -> x.getID() == finalId).toList().get(0).getTokens()) {
//                        userService.updateUser(finalId, change);
//                        break;
//                    }
//                }
//                Render.displayWrongInput();
//            }
//        } catch (Exception e) {
//            Render.displayUpdatingUserError(e.getMessage());
//        }
//    }
//}
//
