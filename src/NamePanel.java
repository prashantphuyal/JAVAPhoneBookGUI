import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class NamePanel extends JPanel implements AppLayout{

    private JTable dataTable;
    private DefaultTableModel model;

    public NamePanel(){
        setBorder(BorderFactory.createTitledBorder("Name"));
        model = new DefaultTableModel();
        String[] header = {"First Name", "Last Name","Phone","Status"};
        model.setColumnIdentifiers(header);
        dataTable = new JTable(model);
        dataTable.setSelectionBackground(Color.GRAY);
    }
    public void getTableData(){
        for (int row = 0; row < model.getRowCount(); row++) {
            String firstName = model.getValueAt(row, 0).toString();
            String secondName = model.getValueAt(row, 1).toString();
            String phone = model.getValueAt(row, 2).toString();
            String status = model.getValueAt(row, 3).toString();
            model.addRow(new Object[] {firstName,secondName,phone,status});
        }
    }

    @Override
    public JPanel panelUI() {
        dataTable.setShowGrid(true);
        JScrollPane scrollable = new JScrollPane(dataTable);
        add(scrollable);
        return this;
    }

    public DefaultTableModel getModel() {
        return (DefaultTableModel) getTable().getModel();
    }
    public JTable getTable() {
        return dataTable;
    }
}