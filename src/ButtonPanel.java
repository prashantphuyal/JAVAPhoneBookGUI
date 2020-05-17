import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class ButtonPanel extends JPanel implements AppLayout {
    /*
     * Declaring all JButtons used below in program */
    private JButton clear;
    private JButton update;
    private JButton add;
    private JButton remove;

    public ButtonPanel(){
        /*
         * 2x2 Grid layout is used for dealing with app layout
         * ToolTips and Mnemonic are used for shortcut keys and for better friendly environment*/
        setLayout(new GridLayout(2,2));
        setPreferredSize(new Dimension(100, 100));
        clear = new JButton("Clear");
        clear.setToolTipText("Clear all fields [alt + 1]");
        clear.setMnemonic(KeyEvent.VK_1);

        update = new JButton("Update");
        update.setToolTipText("Update data in table [alt + 2]");
        update.setMnemonic(KeyEvent.VK_2);

        add = new JButton("Add");
        add.setToolTipText("Add data entered in the field [alt + 3]");
        add.setMnemonic(KeyEvent.VK_3);

        remove = new JButton("Remove");
        remove.setToolTipText("Add selected data from table [alt + 4]");
        remove.setMnemonic(KeyEvent.VK_4);
    }

    @Override
    public JPanel panelUI() {
        /*
         * Implementing interface AppLayout
         * adding all buttons in panelUI so that we can call this method while doing mainLayout*/
        add(clear);
        add(update);
        add(add);
        add(remove);
        return this; //return this is used so that if this method is called through another one than it returns everything inside panelUI
    }

    /*
     * Below getter method is for action listener to perform certain operation*/
    public JButton getClearBtn(){
        return clear;
    }
    public JButton getUpdateBtn(){
        return update;
    }
    public JButton getAddBtn(){
        return add;
    }
    public JButton getRemoveBtn(){
        return remove;
    }
}
