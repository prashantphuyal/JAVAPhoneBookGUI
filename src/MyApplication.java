
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MyApplication extends JFrame {
    JMenuBar menuBar;
    InfoPanel infoPanel;
    FileAsPanel fileAsPanel;
    ButtonPanel buttonPanel;
    NamePanel namePanel;
    DataBaseConnection mySqlConnection;
    MyApplication self = this;

    public MyApplication(){
        setVisible(true);
        setResizable(false);
        setTitle("Phone Book");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuBar = new JMenuBar();
        infoPanel = new InfoPanel();
        fileAsPanel = new FileAsPanel();
        buttonPanel = new ButtonPanel();
        namePanel = new NamePanel();
        mySqlConnection = new DataBaseConnection();
        setJMenuBar(getMenu());
        add(appLayout());
        addToTable();
        clearAll();
        removeData();
        selectionData();
        updateData();
        sortTableData();
        showData();
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel appLayout() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.5;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        mainPanel.add(namePanel.panelUI(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        mainPanel.add(infoPanel.panelUI(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        mainPanel.add(fileAsPanel.panelUI(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        mainPanel.add(buttonPanel.panelUI(), gbc);

        return mainPanel;
    }

    private JMenuBar getMenu(){
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu helpMenu = new JMenu("Help");

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        JMenuItem subExit = new JMenuItem("Exit");
        subExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(subExit);
        subExit.setToolTipText("Click to exit");
        subExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,InputEvent.CTRL_DOWN_MASK));

        JMenuItem subClear = new JMenuItem("Clear");
        subClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.getClearBtn().doClick();
            }
        });
        editMenu.add(subClear);
        subClear.setToolTipText("Clear all fields");
        subClear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_DOWN_MASK));

        JMenuItem subUpdate = new JMenuItem("Update");
        subUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.getUpdateBtn().doClick();
            }
        });
        editMenu.add(subUpdate);
        subUpdate.setToolTipText("Update Data in a Table");
        subUpdate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,InputEvent.CTRL_DOWN_MASK));

        editMenu.addSeparator();

        JMenuItem subAdd = new JMenuItem("Add");
        subAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.getAddBtn().doClick();
            }
        });
        editMenu.add(subAdd);
        subAdd.setToolTipText("Add data entered in the field");
        subAdd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_DOWN_MASK));

        JMenuItem subRemove = new JMenuItem("Remove");
        subRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.getRemoveBtn().doClick();
            }
        });
        editMenu.add(subRemove);
        subRemove.setToolTipText("Add selected data from table");
        subRemove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_DOWN_MASK));

        JMenuItem subAbout = new JMenuItem("About");
        subAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(self,"It's Still in trial version","Warning",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(subAbout);
        subAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,InputEvent.CTRL_DOWN_MASK));

        return menuBar;
    }

    private void addToTable(){
        JButton addBtn = buttonPanel.getAddBtn();
        DefaultTableModel model = namePanel.getModel();
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String firstName = infoPanel.getFirstName().getText().trim();
                String lastName = infoPanel.getSecondName().getText().trim();
                String phone = infoPanel.getPhone().getText().trim();
                String checked = infoPanel.getCheck().getText();

                Boolean validPhone = infoPanel.isValid(phone);

                if(firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()){
                    JOptionPane.showMessageDialog(self,"All field are required", "Warning", JOptionPane.WARNING_MESSAGE);
                }

                else if (!validPhone){
                    JOptionPane.showMessageDialog(self,"Please enter valid phone number");
                }

                else{
                    Object[] data = { firstName, lastName, phone, checked};
                    model.addRow(data);
                    mySqlConnection.addDataToSQL(firstName,lastName, phone, checked);
                    JOptionPane.showMessageDialog(self, "You are registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    buttonPanel.getClearBtn().doClick();
                }
            }
        });
    }

    private void clearAll(){
        JButton clearBtn = buttonPanel.getClearBtn();
        JTable table = namePanel.getTable();
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoPanel.getFirstName().setText("");
                infoPanel.getSecondName().setText("");
                infoPanel.getPhone().setText("");
                infoPanel.getPrivate().setSelected(true);
                table.clearSelection();
            }
        });
    }

    private void removeData(){
        JTable table = namePanel.getTable();
        JButton removeBtn = buttonPanel.getRemoveBtn();
        DefaultTableModel model = namePanel.getModel();
        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dataRow = table.getSelectedRow();
                String fName = table.getModel().getValueAt(dataRow, 0).toString();

                if (dataRow == -1){
                    JOptionPane.showMessageDialog(self, "Please, Select First", "Warning",JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    model.removeRow(dataRow);
                    mySqlConnection.removeDataSQL(fName);
                    buttonPanel.getClearBtn().doClick();
                }}
        });
    }

    private void selectionData(){
        JTable table = namePanel.getTable();
        DefaultTableModel model = namePanel.getModel();
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                infoPanel.getFirstName().setText(model.getValueAt(selectedRow,0).toString());
                infoPanel.getSecondName().setText(model.getValueAt(selectedRow,1).toString());
                infoPanel.getPhone().setText(model.getValueAt(selectedRow,2).toString());

                infoPanel.getCheck().setText(model.getValueAt(selectedRow,3).toString());
                String check = infoPanel.getCheck().getText();
                int state = check.compareTo("Private");
                boolean bool = (state == 0);
                infoPanel.getPrivate().setSelected(bool);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void updateData(){
        JButton updateBtn = buttonPanel.getUpdateBtn();
        JTable table = namePanel.getTable();
        DefaultTableModel model = namePanel.getModel();
        JButton clearBtn = buttonPanel.getClearBtn();
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if(selectedRow == -1){
                    JOptionPane.showMessageDialog(self, "You must select a row in table", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String firstName = infoPanel.getFirstName().getText().trim();
                String lastName = infoPanel.getSecondName().getText().trim();
                String phone = infoPanel.getPhone().getText().trim();
                String checked = infoPanel.getCheck().getText();

                Boolean validPhone = infoPanel.isValid(phone);

                if(firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()){
                    JOptionPane.showMessageDialog(self,"All field are required", "Warning", JOptionPane.WARNING_MESSAGE);
                }

                else if (!validPhone){
                    JOptionPane.showMessageDialog(self,"Please enter valid phone number");
                }

                else{
                    model.setValueAt(firstName,selectedRow,0);
                    model.setValueAt(lastName,selectedRow,1);
                    model.setValueAt(phone,selectedRow,2);
                    model.setValueAt(checked ,selectedRow,3);
                    mySqlConnection.updateDataSQL(firstName, lastName, phone, checked);
                    JOptionPane.showMessageDialog(self, "Data updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearBtn.doClick();
                }
            }
        });
    }

    private void sortTableData(){
        JRadioButton rFsName = fileAsPanel.getFsName();
        JRadioButton rSfName = fileAsPanel.getSfName();
        JTable data = namePanel.getTable();

        rFsName.setEnabled(false);
        rSfName.setEnabled(true);
        rFsName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rFsName.setEnabled(false);
                rSfName.setEnabled(true);
                data.moveColumn(0,1);
            }
        });

        rSfName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rSfName.setEnabled(false);
                rFsName.setEnabled(true);
                data.moveColumn(1,0);
            }
        });
    }

    private void showData(){
        ArrayList<PhoneBook> data = mySqlConnection.dataList();
        DefaultTableModel model = (DefaultTableModel) namePanel.getModel();
        Object[] row = new Object[4];
        for(int i=0; i< data.size(); i++){
            row[0] = data.get(i).getFirstName();
            row[1] = data.get(i).getSecondName();
            row[2] = data.get(i).getPhone();
            row[3] = data.get(i).getStatus();
            model.addRow(row);
        }
    }

    public static void main(String[] args){
        new MyApplication();

    }
}
