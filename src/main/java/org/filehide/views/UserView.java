package org.filehide.views;

import org.filehide.dao.DataDAO;
import org.filehide.db.MyConnection;
import org.filehide.model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private final String email;

    UserView(String email) {
        this.email = email;
    }

    public void home() {
        while (true){
            System.out.println("Welcome " + email.split("@")[0]);
            System.out.println("press 1 : to show hidden files");
            System.out.println("press 2 : to hide a new file");
            System.out.println("press 3 : to un hide a file");
            System.out.println("press 0 : to exit");
            Scanner sc = new Scanner(System.in);
            int ch = Integer.parseInt(sc.nextLine());
            switch (ch) {
                case 0 -> {
                    MyConnection.closeConnection();
                    System.err.println("Thanks for using our file hide system");
                    System.exit(0);
                }
                case 1 -> {
                    try {
                        List<Data> files = DataDAO.getAllFiles(this.email);
                        if(files.isEmpty()) {
                            System.err.println("No Hidden files found");
                            continue;
                        }
                        System.out.println("ID - File Name");
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 2 -> {
                    System.out.println("Enter file path : ");
                    String path = sc.nextLine();
                    File f = new File(path);
                    Data file = new Data(0, f.getName(), path, this.email);
                    try {
                        int response = DataDAO.hideFile(file);
                        System.err.println(response != 0 ? "file hidden successfully" : "file hidden failed");
                    } catch (SQLException | IOException e) {
                        System.err.println(e.getMessage());
                    }
                }
                case 3 -> {
                    List<Data> files;
                    try {
                        files = DataDAO.getAllFiles(this.email);
                        System.out.println("ID - File Name");
                        for (Data data : files) {
                            System.out.println(data.getId() + " : " + data.getFileName());
                        }
                        System.out.println("Enter the id of file to un hide it");
                        int id = Integer.parseInt(sc.nextLine());
                        boolean isValidId = false;
                        for (Data file : files) {
                            if (file.getId() == id) {
                                isValidId = true;
                                break;
                            }
                        }
                        if (isValidId)
                            DataDAO.unHideFile(id);
                        else
                            System.err.println("Wrong id");
                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
