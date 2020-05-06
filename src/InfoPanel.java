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

        return this;
    }

    private void checkBox(){
        cPrivate.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                checkLabel.setText(e.getStateChange()==1?"Private":"Not Private");
            }
        });
    }

    public boolean isValid(String s)
    {
        Pattern p = Pattern.compile("(9)[7-8]{1}[0-9]{8}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

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

