import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfoPanel extends JPanel implements AppLayout{

    private JTextField firstName;
    private JTextField secondName;
    private JTextField phone;
    private JCheckBox cPrivate;
    private JLabel checkLabel;

    /*
     * 4x2 Grid layout is used for dealing with app layout
     * ToolTips are used for shortcut keys and for better friendly environment
     * Method of Interface AppLayout is implemented for layout purpose*/
    public InfoPanel(){
        setBorder(BorderFactory.createTitledBorder("Info:"));
        setLayout(new GridLayout(4,2,6,6));
        firstName = new JTextField(20);
        secondName = new JTextField(20);
        phone = new JTextField(10);
        cPrivate = new JCheckBox("Private",true);

        checkLabel = new JLabel("Private");
        checkBox();
    }

    @Override
    public JPanel panelUI() {

        add(new JLabel("First Name"));
        add(firstName);
        firstName.setToolTipText("Enter First Name");

        add(new JLabel("Last Name"));
        add(secondName);
        secondName.setToolTipText("Enter Last Name");

        add(new JLabel("Phone"));
        add(phone);
        phone.setToolTipText("Enter Phone Number");

        add(cPrivate);

        return this; //return this is used so that if this method is called through another one than it returns everything inside panelUI
    }
    /*
    * checkBox() method is for setting the state of label when check box is clicked or unClicked
    * In initial phase checkbox state is clicked. so, it returns 1 and when 1 it changes the state of label text to private
      and when not 1 it changes it text to Not Private*/
    private void checkBox(){
        cPrivate.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                checkLabel.setText(e.getStateChange()==1?"Private":"Not Private");
            }
        });
    }
    /*This method "isValid" is for phone validation, when certain numbers are entered in Text field it takes as string and pass to this method
    * This is a regex method where it checks the pattern of the phone and sends a boolean value i.e either true or false and the
      action is performed accordingly */
    public boolean isValid(String s)
    {
        /*
         * 9 in the beginning denotes the number should start with number 9
         * [7-8]{1} denotes the 2nd phone number is one between 7 or 8
         * [0-9]{8} denotes that rest 8 numbers should be from 0 to 9*/
        Pattern p = Pattern.compile("(9)[7-8]{1}[0-9]{8}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    /*
     * Below getter method is for action listener to perform certain operation*/
    public JTextField getFirstName() {
        return firstName;
    }

    public JTextField getSecondName() {
        return secondName;
    }

    public JTextField getPhone() {
        return phone;
    }

    public JLabel getCheck() {
        return checkLabel;
    }
    public JCheckBox getPrivate(){
        return cPrivate;
    }

}

