import java.sql.*;
import java.util.ArrayList;

public class DataBaseConnection {

    String dbURL = "jdbc:mysql://localhost:3306/phonebook";
    String user = "root";
    String password = "beast10";

    public void addDataToSQL(String firstName, String secondName, String phone, String status) {
        try {
            Connection conn = DriverManager.getConnection(dbURL, user, password);

            String addToSql = "INSERT INTO phonebook (firstName, secondName, phone, status) VALUES (?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(addToSql);

            statement.setString(1, firstName);
            statement.setString(2, secondName);
            statement.setString(3, phone);
            statement.setString(4, status);

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
                String fName = result.getString("firstName");
                String sName = result.getString("secondName");
                String pPhone = result.getString("phone");
                String nStatus = result.getString("status");

                phoneBook = new PhoneBook(fName, sName, pPhone, nStatus);
                data.add(phoneBook);
            }
            System.out.println("Data Added to DataBase successfully");
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public void removeDataSQL(String fName){
        try {
            Connection conn = DriverManager.getConnection(dbURL, user, password);
            String remData = "DELETE FROM phonebook WHERE firstName = ?";
            PreparedStatement statement = conn.prepareStatement(remData);

            statement.setString(1,fName);
            statement.executeUpdate();

            System.out.println("Data Removed from DataBase successfully");
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateDataSQL(String fName, String  sName, String phone, String status){
        try {
            Connection conn = DriverManager.getConnection(dbURL, user, password);
            String upData = "UPDATE phonebook SET firstName = ?, secondName = ?, status = ? WHERE phone=? ";
            PreparedStatement statement = conn.prepareStatement(upData);

            statement.setString(1,fName);
            statement.setString(2,sName);
            statement.setString(3,status);
            statement.setString(4,phone);

            statement.executeUpdate();

            System.out.println("Data updated in DataBase successfully");
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

