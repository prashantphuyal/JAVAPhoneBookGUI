import javax.swing.*;
import java.awt.*;

public class FileAsPanel extends JPanel implements AppLayout {

    private JRadioButton FsName;
    private JRadioButton SfName;
    private ButtonGroup bgSelect;

    /*
     * 4x2 Grid layout is used for dealing with app layout
     * Added 2 radio button in a group called ButtonGroup for checkbox to function accordingly
     * Method of Interface AppLayout is implemented for layout purpose*/

    public FileAsPanel(){
        setBorder(BorderFactory.createTitledBorder("File As:"));
        setLayout(new GridLayout(4,2));
        FsName = new JRadioButton("Forename, Surname");
        SfName = new JRadioButton("Surname, Forename");
        bgSelect = new ButtonGroup();
        bgSelect.add(FsName);
        bgSelect.add(SfName);
        FsName.setSelected(true);
    }

    @Override
    public JPanel panelUI() {
        //adding all buttons and labels in panelUI so to call this method while dealing with mainLayout

        add(FsName);
        add(new JLabel());

        add(SfName);
        add(new JLabel());

        return this;//return this is used so that if this method is called through another one than it returns everything inside panelUI
    }

    /*
     * Below getter method is for action listener to perform certain operation*/
    public JRadioButton getFsName(){
        return FsName;
    }

    public JRadioButton getSfName(){
        return SfName;
    }
}
