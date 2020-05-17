
import Helpers.DbPhoneBookConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
* Defining all JMenuBar, InfoPanel and other methods outside so that we don't have to right it again and again
* All the methods are called in constructor so that when object is created in main method than this Constructor MyApplication
  is called and program will execute
* And when another object is created inside of constructor(MyApplication) their own constructor is called and so on
* GridBagLayout is used for the main layout where all the panelUI's are managed properly
* pack() method is used for combining all the methods above and to treat as one
* setLocationRelativeTo(null) method is called for starting GUI in the center of screen
* */

public class MyApplication extends JFrame {
    JMenuBar menuBar;
    InfoPanel infoPanel;
    FileAsPanel fileAsPanel;
    ButtonPanel buttonPanel;
    NamePanel namePanel;
    DbPhoneBookConnection dbPhoneBook;
    MyApplication self = this;

    public MyApplication() {
        setVisible(true);
        setResizable(false);
        setTitle("Phone Book");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuBar = new JMenuBar();
        infoPanel = new InfoPanel();
        fileAsPanel = new FileAsPanel();
        buttonPanel = new ButtonPanel();
        namePanel = new NamePanel();
        dbPhoneBook = new DbPhoneBookConnection();
        setJMenuBar(getMenu());
        add(appLayout());
        addToTable();
        clearAll();
        removeData();
        selectionData();
        updateData();
        sortTableData();
        refreshTable();
        pack();
        setLocationRelativeTo(null);
    }

    //    All the panels from interface AppLayout are managed using GridBagLayout
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

        return mainPanel; //return this is used so that if this method is called through another one than it returns everything inside panelUI
    }

    //    * ToolTips and setAccelerator are used for shortcut keys and for better friendly environment
    //    * Likewise, actionListener is used for performing certain operation
    private JMenuBar getMenu() {
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
        subExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));

        JMenuItem subClear = new JMenuItem("Clear");
        subClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.getClearBtn().doClick();
            }
        });
        editMenu.add(subClear);
        subClear.setToolTipText("Clear all fields");
        subClear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));

        JMenuItem subUpdate = new JMenuItem("Update");
        subUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.getUpdateBtn().doClick();
            }
        });
        editMenu.add(subUpdate);
        subUpdate.setToolTipText("Update Data in a Table");
        subUpdate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));

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
        subAdd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));

        JMenuItem subRemove = new JMenuItem("Remove");
        subRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.getRemoveBtn().doClick();
            }
        });
        editMenu.add(subRemove);
        subRemove.setToolTipText("Add selected data from table");
        subRemove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));

        JMenuItem subAbout = new JMenuItem("About");
        subAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(self, "It's Still in trial version", "Warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(subAbout);
        subAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));

        return menuBar;
    }

    //    * private method addToTable is for action listener to perform adding data to table operation
    //    * self in JOptionPane denotes the parent class(JFrame) inside which JOptionPane is to be appreared
    private void addToTable() {
        JButton addBtn = buttonPanel.getAddBtn();

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String firstName = infoPanel.getFirstName().getText().trim();
                String lastName = infoPanel.getSecondName().getText().trim();
                String phone = infoPanel.getPhone().getText().trim();
                String checked = infoPanel.getCheck().getText();

                Boolean validPhone = infoPanel.isValid(phone);


                if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(self, "All field are required", "Warning", JOptionPane.WARNING_MESSAGE);
                } else if (!validPhone) {
                    JOptionPane.showMessageDialog(self, "Please enter valid phone number");
                } else {
                    dbPhoneBook.insert(firstName, lastName, phone, checked);
                    refreshTable();
                    JOptionPane.showMessageDialog(self, "You are registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    buttonPanel.getClearBtn().doClick();
                }
            }
        });
    }

    //      private method clearAll is for action listener to clearing data from text field operation
    //      setText() methods is used to set the text. so, here empty string is assigned
    private void clearAll() {
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

    //      private method removeData is for action listener to removing data from table operation
    //      getSelectedRow() method is used for getting index value of selected row
    private void removeData() {
        JTable table = namePanel.getTable();
        JButton removeBtn = buttonPanel.getRemoveBtn();
        DefaultTableModel model = namePanel.getModel();
        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dataRow = table.getSelectedRow();
                if (dataRow == -1) {
                    JOptionPane.showMessageDialog(self, "Please, Select First", "Warning", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                int id = Integer.parseInt(model.getValueAt(dataRow, 0).toString());
                dbPhoneBook.removeData(id);
                refreshTable();
                buttonPanel.getClearBtn().doClick();
            }
        });
    }

    // selectionData is method is for displaying the data of column in the text field when certain row is selected
    // setText() method is used for setting the that we get from the table to the text field
    private void selectionData() {
        JTable table = namePanel.getTable();
        DefaultTableModel model = namePanel.getModel();
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                infoPanel.getFirstName().setText(model.getValueAt(selectedRow, 1).toString());
                infoPanel.getSecondName().setText(model.getValueAt(selectedRow, 2).toString());
                infoPanel.getPhone().setText(model.getValueAt(selectedRow, 3).toString());

                infoPanel.getCheck().setText(model.getValueAt(selectedRow, 4).toString());
                /*
                *  instead of changing state of checkbox button i preferred to to set the value of label and passed that label here
                *  label text is received when getCheck().getText() is called and stored in check than check is compared to string private
                   which returns int value to variable 0, 1 or -1
                *  Then integer value is converted to boolean and is passed to checkbox and it's state is set
                */
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

    //     * updateData is method is for updating the data of column from the text field after certain row is selected
    private void updateData() {
        JButton updateBtn = buttonPanel.getUpdateBtn();
        JTable table = namePanel.getTable();
        DefaultTableModel model = namePanel.getModel();
        JButton clearBtn = buttonPanel.getClearBtn();
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(self, "You must select a row in table", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String firstName = infoPanel.getFirstName().getText().trim();
                String lastName = infoPanel.getSecondName().getText().trim();
                String phone = infoPanel.getPhone().getText().trim();
                String checked = infoPanel.getCheck().getText();

                int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

                Boolean validPhone = infoPanel.isValid(phone);

                if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(self, "All field are required", "Warning", JOptionPane.WARNING_MESSAGE);
                } else if (!validPhone) {
                    JOptionPane.showMessageDialog(self, "Please enter valid phone number");
                } else {
                    dbPhoneBook.updateData(id, firstName, lastName, phone, checked);
                    refreshTable();
                    JOptionPane.showMessageDialog(self, "Data updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearBtn.doClick();
                }
            }
        });
    }

    //  moveColumn() method is used for changing column in the table
    //   setEnabled() is for radio button to set whether radiobutton is to be enabled or not
    private void sortTableData() {
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
                data.moveColumn(0, 1);
            }
        });

        rSfName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rSfName.setEnabled(false);
                rFsName.setEnabled(true);
                data.moveColumn(1, 0);
            }
        });
    }

    // Retrieving data from the arraylist and adding the data into table row
    private void refreshTable() {
        namePanel.getModel().setRowCount(0);
        try {
            ResultSet result = dbPhoneBook.get();
            while (result.next()) {
                namePanel.getModel().addRow(new Object[]{
                        result.getString("ID"),
                        result.getString("firstName"),
                        result.getString("secondName"),
                        result.getString("phone"),
                        result.getString("status")
                });
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MyApplication();
    }
}
