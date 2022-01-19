package com.innowise.main.java;

import com.innowise.main.java.filesworkers.FileWorker;
import com.innowise.main.java.helpers.UserHelper;
import com.innowise.main.java.parser.DomParser;
import com.innowise.main.java.user.User;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean isExit = false;
        while (!isExit) {
            FileWorker fileWorker = new FileWorker();
            File pathFile = new File("src/com/innowise/main/resources/paths/usersFilePath.txt");
            String file = fileWorker.read(pathFile);
            if (file.isEmpty()) {
                System.out.println("Please, write a path to xml data file in 'src/com/innowise/main/resources/paths/usersFilePath.txt' and restart app");
                break;
            }

            File xmlFile = new File(file);

            if (fileWorker.isFileExists(xmlFile)) {
                System.out.println("1-Add user");
                System.out.println("2-Edit user");
                System.out.println("3-Delete user");
                System.out.println("4-Search user by name");
                System.out.println("5-Show all users");
                System.out.println("6-Exit");
                int choose=UserHelper.inputInRange(1,6);
                switch (choose) {
                    case 1: {
                        DomParser.addElement(xmlFile, UserHelper.getUserFromConsole());
                        break;
                    }
                    case 2: {
                        List<User> userList = DomParser.parse(xmlFile);
                        UserHelper.showUsers(userList);
                        System.out.println("Select user");
                        int user = UserHelper.inputInRange(1,userList.size()+1);

                        DomParser.editElement(xmlFile, UserHelper.getUserFromConsole(), userList.get(user-1).getId());
                        break;
                    }
                    case 3: {
                        List<User> userList = DomParser.parse(xmlFile);
                        UserHelper.showUsers(userList);
                        System.out.println("Select user");
                        int user = UserHelper.inputInRange(1,userList.size()+1);
                        DomParser.deleteElement(xmlFile, userList.get(user-1).getId());

                        break;
                    }
                    case 4: {
                        String name=UserHelper.stringInput("Enter name to search");

                        List<User> userList = DomParser.findUserByName(xmlFile, name);
                        if (userList.isEmpty()) {
                            System.out.println("No such user");
                        } else {
                            UserHelper.showUsers(userList);
                        }
                        break;
                    }
                    case 5: {
                        List<User> userList = DomParser.parse(xmlFile);
                        UserHelper.showUsers(userList);
                        break;
                    }
                    case 6: {
                        isExit = true;
                        break;
                    }
                }
            } else {
                System.out.println("Error. Can' load file");
                isExit = true;
            }
        }
    }
}
