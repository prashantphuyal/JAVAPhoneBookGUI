import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class ButtonPanel extends JPanel implements AppLayout {

    private JButton clear;
    private JButton update;
    private JButton add;
    private JButton remove;

    public ButtonPanel(){
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
        add(clear);
        add(update);
        add(add);
        add(remove);
        return this;
    }
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
