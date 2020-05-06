import javax.swing.*;
import java.awt.*;

public class FileAsPanel extends JPanel implements AppLayout {

    private JRadioButton FsName;
    private JRadioButton SfName;
    private ButtonGroup bgSelect;

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

        add(FsName);
        add(new JLabel());

        add(SfName);
        add(new JLabel());

        return this;
    }

    public JRadioButton getFsName(){
        return FsName;
    }

    public JRadioButton getSfName(){
        return SfName;
    }
}
