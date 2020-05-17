import java.sql.*;
import java.util.ArrayList;

public class DataBaseConnection {

    String dbURL = "jdbc:mysql://localhost:3306/phonebook";
    String user = "root";
    String password = "beast10";

    public void addDataToSQL(String ID, String firstName, String secondName, String phone, String status) {
        try {
            Connection conn = DriverManager.getConnection(dbURL, user, password);

            String addToSql = "INSERT INTO phonebook (ID, firstName, secondName, phone, status) VALUES (?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(addToSql);

            statement.setString(1, ID);
            statement.setString(2, firstName);
            statement.setString(3, secondName);
            statement.setString(4, phone);
            statement.setString(5, status);

            statement.executeUpdate();
            System.out.println("Data Added to DataBase successfully");
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<PhoneBook> dataList() {
        ArrayList<PhoneBook> data = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(dbURL, user, password);
            String selectData = "SELECT * FROM phonebook";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(selectData);
            PhoneBook phoneBook;
            while (result.next()) {
                String id = result.getString("ID");
                String fName = result.getString("firstName");
                String sName = result.getString("secondName");
                String pPhone = result.getString("phone");
                String nStatus = result.getString("status");

                phoneBook = new PhoneBook(id, fName, sName, pPhone, nStatus);
                data.add(phoneBook);
            }
            System.out.println("Data Added to DataBase successfully");
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public void removeDataSQL(String id){
        try {
            Connection conn = DriverManager.getConnection(dbURL, user, password);
            String remData = "DELETE FROM phonebook WHERE ID = ?";
            PreparedStatement statement = conn.prepareStatement(remData);

            statement.setString(1,id);
            statement.executeUpdate();

            System.out.println("Data Removed from DataBase successfully");
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateDataSQL(String id,String fName, String  sName, String phone, String status){
        try {
            Connection conn = DriverManager.getConnection(dbURL, user, password);
            String upData = "UPDATE phonebook SET firstName = ?, secondName = ?, status = ?,  phone = ? WHERE ID = ?";
            PreparedStatement statement = conn.prepareStatement(upData);

            statement.setString(1,fName);
            statement.setString(2,sName);
            statement.setString(3,status);
            statement.setString(4,phone);
            statement.setString(5,id);

            statement.executeUpdate();

            System.out.println("Data updated in DataBase successfully");
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

