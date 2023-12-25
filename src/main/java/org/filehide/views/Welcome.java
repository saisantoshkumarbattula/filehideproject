package org.filehide.views;

import org.filehide.dao.UserDAO;
import org.filehide.db.MyConnection;
import org.filehide.model.User;
import org.filehide.service.GenerateOTP;
import org.filehide.service.SendOTPService;
import org.filehide.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Welcome {
    public void welcomeScreen(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the app");
        System.out.println("press\n1. login\n2. signup\n0. exit");
        int choice;
        try {
            choice = Integer.parseInt(bufferedReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException();
        }
        switch (choice){
            case 1 -> login();
            case 2 -> signUp();
            case 0 -> {
                System.err.println("Thank you for using file hide system");
                MyConnection.closeConnection();
                System.exit(0);
            }
        }
    }

    private void login() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter email to login : ");
        String email = s.nextLine();
        try {
            if (UserDAO.isExists(email)) {
                String generatedOTP = GenerateOTP.getOTP();
                SendOTPService.sendOTP(email, generatedOTP, "login");
                System.out.println("Enter otp :");
                String otp = s.nextLine();
                if(otp.equals(generatedOTP)){
                    System.out.println("Welcome");
                    new UserView(email).home();
                }else{
                    System.err.println("Wrong Otp");
                }
            }else{
                System.err.println("User not found");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void signUp() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter name : ");
        String name = s.nextLine();
        System.out.println("Enter email : ");
        String email = s.nextLine();
        String generatedOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email, generatedOTP, "signup");
        System.out.println("Enter otp : ");
        String otp = s.nextLine();
        if(otp.equals(generatedOTP)){
            try {
                if(UserDAO.isExists(email)){
                    System.err.println("User already exists, please login");
                    return;
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                throw new RuntimeException(e);
            }
            User user = new User(name, email);
            int response = UserService.saveUser(user);
            switch (response){
                case 0 -> System.out.println("User already exists");
                case 1 -> System.out.println("User saved");
            }
        }else{
            System.err.println("Wrong Otp");
        }
    }
}
