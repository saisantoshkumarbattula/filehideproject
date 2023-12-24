package org.filehide;

import org.filehide.db.MyConnection;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        MyConnection.getConnection();
    }
}