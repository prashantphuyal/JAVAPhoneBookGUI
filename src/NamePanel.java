import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class NamePanel extends JPanel implements AppLayout{

    /*
     * DefaultTableModel is simply a table model
     * we should add that model to JTable to store data of table in that model
     * Method of Interface AppLayout is implemented for layout purpose */
    private JTable dataTable;
    private DefaultTableModel model;

    public NamePanel(){
        setBorder(BorderFactory.createTitledBorder("Name"));
        model = new DefaultTableModel();
        String[] header = {"ID","First Name", "Last Name","Phone","Status"};
        model.setColumnIdentifiers(header);
        dataTable = new JTable(model);
        dataTable.setSelectionBackground(Color.GRAY);
    }

    @Override
    public JPanel panelUI() {
        dataTable.setShowGrid(true);
        JScrollPane scrollable = new JScrollPane(dataTable);
        add(scrollable);
        return this;    //return this is used so that if this method is called through another one than it returns everything inside panelUI
    }

    /*
     * Below getter method is for action listener to perform certain operation*/
    public DefaultTableModel getModel() {
        return (DefaultTableModel) getTable().getModel();
    }
    public JTable getTable() {
        return dataTable;
    }
}