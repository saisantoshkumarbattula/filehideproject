package org.filehide.dao;

import org.filehide.db.MyConnection;
import org.filehide.model.Data;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class DataDAO {
    public static List<Data> getAllFiles(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from data where email = ?");
        statement.setString(1, email);
        ResultSet rs = statement.executeQuery();
        List<Data> files = new ArrayList<>();
        while (rs.next()) {
            files.add(new Data(rs.getInt(1), rs.getString(2), rs.getString(3), email));
        }
        return files;
    }

    public static int hideFile(Data file) throws SQLException, IOException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("insert into data values(default, ?, ?, ?, ?, ?)");
        statement.setString(1, file.getFileName());
        statement.setString(2, file.getPath());
        statement.setString(3, file.getEmail());
        File f = new File(file.getPath());
        FileReader fr = new FileReader(f);
        statement.setCharacterStream(4, fr, f.length());
        statement.setString(5, new SimpleDateFormat("dd-MM-yyyy | hh:mm:ss a").format(new Date()));
        int response = statement.executeUpdate();
        fr.close();
        boolean status = f.delete();
        return status ? response : 0;
    }

    public static void unHideFile(int id) throws SQLException, IOException{
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("select path, bin_data from data where id = ?");
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        rs.next();
        String path = rs.getString("path");
        Clob clob = rs.getClob("bin_data");
        Reader reader = clob.getCharacterStream();
        FileWriter fw = new FileWriter(path);
        int i;
        while ((i = reader.read()) != -1) {
            fw.write((char) i);
        }
        fw.close();
        statement = connection.prepareStatement("delete from data where id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
        System.out.println("Successfully file unhidden");
    }
}
