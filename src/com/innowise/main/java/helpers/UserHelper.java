package com.innowise.main.java.helpers;

import com.innowise.main.java.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserHelper {
    private static Scanner scanner = new Scanner(System.in);

    private UserHelper() {
    }

    public static User getUserFromConsole() {

        String name = stringInput("Enter name");
        String lastname = stringInput("Enter lastname");
        String email = emailInput();
        List<String> numbers = phoneNumberInput();
//        System.out.println("Enter number");
//        String number1 = scanner.nextLine();
//        numbers.add(number1);
//        while (numbers.size() <= 3) {
//            System.out.println("Enter additional number or 'stop' to skip");
//            String number = scanner.nextLine();
//            if (number.equalsIgnoreCase("stop")) {
//                break;
//            }
//            numbers.add(number);
//        }
        List<String> roles = new ArrayList<>();
        System.out.println("Add role");
        System.out.println("1-SUPER_ADMIN");
        System.out.println("2-No one");
        int role3 = inputInRange(1, 2);
        if (role3 == 1) {
            roles.add("SUPER_ADMIN");
        } else {
            System.out.println("Add role");
            System.out.println("1-USER");
            System.out.println("2-CUSTOMER");
            System.out.println("3-No one from them");
            int role1 = inputInRange(1, 2);
            ;
            if (role1 == 1) {
                roles.add("USER");
            } else if (role1 == 2) {
                roles.add("CUSTOMER");
            }

            System.out.println("Add role");
            System.out.println("1-ADMIN");
            System.out.println("2-PROVIDER");
            System.out.println("3-No one from them");
            int role2 = inputInRange(1, 2);
            if (role2 == 1) {
                roles.add("ADMIN");
            } else if (role2 == 2) {
                roles.add("PROVIDER");
            }
        }

        return new User(name, lastname, email, roles, numbers);
    }

    public static void showUsers(List<User> userList) {
        int counter = 0;
        for (User user : userList) {
            counter++;
            System.out.println("____USER_№_" + counter + "______");
            System.out.println("Name: " + user.getName());
            System.out.println("Lastname:" + user.getLastname());
            System.out.println("Email:" + user.getEmail());
            System.out.println("Roles: " + user.getRoles());
            System.out.println("Numbers: " + user.getNumbers());
        }
    }

    public static boolean validatePhoneNumber(String number) {
        Pattern pattern = Pattern.compile("^375(17|29|33|44)[0-9]{3}[0-9]{2}[0-9]{2}$");
        return pattern.matcher(number).matches() ? true : false;
    }

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(email).matches() ? true : false;
    }

    public static String emailInput() {
        String email;
        do {
            System.out.println("Enter email");
            email = scanner.nextLine();
        } while (!validateEmail(email));

        return email;
    }

    public static List<String> phoneNumberInput() {
        Scanner scanner = new Scanner(System.in);
        List<String> numbers = new ArrayList<>();

        String number1="";
        while (!validatePhoneNumber(number1)){
            System.out.println("Enter number");
            number1 = scanner.nextLine();
        }

        numbers.add(number1);
        while (numbers.size() <= 3) {
            System.out.println("Enter additional number or 'stop' to skip");
            String number = scanner.nextLine();
            if (number.equalsIgnoreCase("stop")) {
                break;
            }
            numbers.add(number);
        }
        return numbers;
    }

    public static String stringInput(String message) {
        String str = "";
        while (str.isEmpty()) {
            System.out.println(message);
            str = scanner.nextLine();
        }
        return str;
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int inputInRange(int min, int max) {
        int choose = 0;
        do {
            String result = stringInput("Please, choose");
            if (isNumeric(result)) {
                choose = Integer.parseInt(result);
            }
        } while (choose > max && choose < min);
        return choose;
    }

}
