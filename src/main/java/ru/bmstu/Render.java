//package ru.bmstu;
//
//import ru.bmstu.aspect.RoleCheck;
//import ru.bmstu.object.User;
//
//import java.util.List;
//
//public class Render {
//    public static int displayUserList(List<User> users) {
//        int i = 1;
//        for (User user: users) {
//            System.out.println(i++ + ") ID: "+ user.getID() + " "  + user.getFULL_NAME() + ". " + user.getROLE() +
//                    ((user.getROLE().equals("Student"))?(". Tokens: " + user.getTokens()):(".")));
//        }
//        return i;
//    }
//
//    public static void displayWrongInput() {
//        System.out.print("Incorrect input. Try again:");
//    }
//
//    public static void displayEnteringProfile(){
//        System.out.print("Enter your profile:");
//    }
//
//    public static void displayNoSuchUserMessage(){
//        System.out.print("No such user. Try again: ");
//    }
//
//    public static void displayWelcomeMessage(User user) {
//        System.out.println("Welcome " + user.getROLE() + ": " + user.getFULL_NAME());
//    }
//
//    public static void displayMainMenu(){
//        int i = 1;
//        System.out.println("Main Menu\n" +
//                i++ + ") Add User\n" +
//                ((RoleCheck.isTeacher())?(i++ + ") Delete Student\n"):("")) +
//                i++ + ") Update Student Token\n" +
//                i++ + ") Show all Students\n" +
//                i + ") Log off");
//    }
//
//    public static void displayStartAuthorisation(){
//        System.out.println("\nStudent Management System");
//        System.out.print("Enter your full name: ");
//    }
//
//    public static void displayEnterNameRequest() {
//        System.out.print("Enter full name: ");
//    }
//
//    public static void displayEnterIdRequest() {
//        System.out.print("Enter student ID: ");
//    }
//
//    public static void displayEnterRoleRequest() {
//        System.out.print("Enter role: ");
//    }
//
//    public static void displayEnterTokenChangeRequest() {
//        System.out.print("Enter token change: ");
//    }
//
//    public static void displayAddingUserError(String message) {
//        System.err.println("Error while adding user: " + message);
//    }
//
//    public static void displayDeletingUserError(String message) {
//        System.err.println("Error while deleting student: " + message);
//    }
//
//    public static void displayUpdatingUserError(String message) {
//        System.err.println("Error while updating student: " + message);
//    }
//}
