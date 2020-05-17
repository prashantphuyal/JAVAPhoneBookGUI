package Helpers;

import java.sql.*;

public class DbPhoneBookConnection {
    private Connection conn;

    public DbPhoneBookConnection(){
        try {
            conn = DBUtils.getDbConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ResultSet get(){
        try {
            String selectData = "SELECT * FROM phonebook";
            PreparedStatement statement = conn.prepareStatement(selectData);
            return statement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void insert( String firstName, String secondName, String phone, String status) {
        try {
            String addToSql = "INSERT INTO phonebook ( firstName, secondName, phone, status) VALUES (?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(addToSql);

            statement.setString(1, firstName);
            statement.setString(2, secondName);
            statement.setString(3, phone);
            statement.setString(4, status);

            statement.executeUpdate();
            statement.close();
            System.out.println("Data Added to DataBase successfully");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void removeData(int id){
        try {
            String remData = "DELETE FROM phonebook WHERE ID = ?";
            PreparedStatement statement = conn.prepareStatement(remData);

            statement.setInt(1,id);
            statement.executeUpdate();
            statement.close();
            System.out.println("Data Removed from DataBase successfully");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void updateData(int id,String fName, String  sName, String phone, String status){
        try {
            String upData = "UPDATE phonebook SET firstName = ?, secondName = ?, status = ?,  phone = ? WHERE ID = ?";
            PreparedStatement statement = conn.prepareStatement(upData);

            statement.setString(1,fName);
            statement.setString(2,sName);
            statement.setString(3,status);
            statement.setString(4,phone);
            statement.setInt(5,id);

            statement.executeUpdate();

            System.out.println("Data updated in DataBase successfully");
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
