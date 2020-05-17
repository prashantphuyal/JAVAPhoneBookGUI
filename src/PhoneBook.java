public class PhoneBook {
    private String id,firstName,secondName, phone, status;

    public PhoneBook(String ID, String firstName,String secondName, String phone, String  status){
        this.id = ID;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = phone;
        this.status = status;
    }
    public  String getId(){
        return this.id;
    }
    public  String getFirstName(){
        return this.firstName;
    }
    public  String getSecondName(){
        return this.secondName;
    }
    public  String getPhone(){
        return this.phone;
    }
    public  String getStatus(){
        return this.status;
    }
}
