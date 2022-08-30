package com.downloadwink.contacts;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.TableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.Locale;

public class ContactForm {
    private JPanel Main;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtPhoneNumber;
    private JButton saveButton;
    private JTable table_1;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton searchButton;

    private JButton addNewButton;
    private JTextField textField1;

    private JScrollPane table1;


    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("Contact Form");
        frame.setContentPane(new ContactForm().Main);
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    PreparedStatement pst;

    Connection getConnection() throws SQLException {
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/contactsnew", "root", "password");
//        System.out.println("Connected");
        return connect;
    }

    void table_load() throws SQLException {

        try {
            pst = getConnection().prepareStatement("select * from contacts");
            ResultSet rs = pst.executeQuery();
            table_1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ContactForm() throws SQLException {
        getConnection();
        table_load();

        //Save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String firstName, lastName, phoneNumber;
                firstName = txtFirstName.getText();
                lastName = txtLastName.getText();
                phoneNumber = txtPhoneNumber.getText();

                if (txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() || txtPhoneNumber.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields");
                } else {

                    try {
                        pst = getConnection().prepareStatement("insert into contacts (firstName, lastName, phoneNumber)values(?,?,?)");
                        pst.setString(1, firstName);
                        pst.setString(2, lastName);
                        pst.setString(3, phoneNumber);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Record Added");
                        table_load();
                        txtFirstName.setText("");
                        txtLastName.setText("");
                        txtPhoneNumber.setText("");
                        txtFirstName.requestFocus();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });


        //Update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = textField1.getText();
                String firstName = txtFirstName.getText();
                String lastName = txtLastName.getText();
                String phoneNumber = txtPhoneNumber.getText();

                try {

                    pst = getConnection().prepareStatement("update contacts set firstName = ?, lastName = ?, phoneNumber = ? where id = ?");
                    pst.setString(1, firstName);
                    pst.setString(2, lastName);
                    pst.setString(3, phoneNumber);
                    pst.setString(4, id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated");
                    table_load();
                    txtFirstName.setText("");
                    txtLastName.setText("");
                    txtPhoneNumber.setText("");
                    txtFirstName.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        });

        //Delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int i = table_1.getSelectedRow();
                String id = table_1.getValueAt(i, 0).toString();


                try {
                    pst = getConnection().prepareStatement("delete from contacts where id = ?");
                    pst.setString(1, id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted");
                    table_load();
                    txtFirstName.setText("");
                    txtLastName.setText("");
                    txtPhoneNumber.setText("");
                    txtFirstName.requestFocus();
                    saveButton.setVisible(true);
                    addNewButton.setVisible(false);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //Search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String firstNameSearch = textField1.getText();

                    pst = getConnection().prepareStatement("select * from contacts where firstName = ?");
                    pst.setString(1, firstNameSearch);
                    ResultSet rs = pst.executeQuery();
                    table_1.setModel(DbUtils.resultSetToTableModel(rs));
                    addNewButton.setVisible(true);

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }


            }
        });

        table_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                addNewButton.setVisible(true);
                updateButton.setVisible(true);
                deleteButton.setVisible(true);
                saveButton.setVisible(false);
                int i = table_1.getSelectedRow();
                TableModel model = table_1.getModel();
                textField1.setText(model.getValueAt(i, 0).toString());
                txtFirstName.setText(model.getValueAt(i, 1).toString());
                txtLastName.setText(model.getValueAt(i, 2).toString());
                txtPhoneNumber.setText(model.getValueAt(i, 3).toString());
            }
        });
        addNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtFirstName.setText("");
                txtLastName.setText("");
                txtPhoneNumber.setText("");
                txtFirstName.requestFocus();
            }
        });
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        Main = new JPanel();
        Main.setLayout(new GridBagLayout());
        final JPanel spacer1 = new JPanel();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Main.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        Main.add(spacer2, gbc);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, -1, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("First Name");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        Main.add(label1, gbc);
        txtFirstName = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 200;
        Main.add(txtFirstName, gbc);
        txtLastName = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 7;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Main.add(txtLastName, gbc);
        txtPhoneNumber = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 9;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Main.add(txtPhoneNumber, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Last Name");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        Main.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Phone Number");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.EAST;
        Main.add(label3, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setEnabled(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 3;
        gbc.gridheight = 9;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipadx = 350;
        Main.add(scrollPane1, gbc);
        table_1 = new JTable();
        scrollPane1.setViewportView(table_1);
        textField1 = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 13;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 60;
        Main.add(textField1, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Main.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.VERTICAL;
        Main.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.VERTICAL;
        Main.add(spacer5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.VERTICAL;
        Main.add(spacer6, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 12;
        gbc.fill = GridBagConstraints.VERTICAL;
        Main.add(spacer7, gbc);
        saveButton = new JButton();
        saveButton.setText("Save");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Main.add(saveButton, gbc);
        updateButton = new JButton();
        updateButton.setEnabled(true);
        updateButton.setText("Update");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Main.add(updateButton, gbc);
        deleteButton = new JButton();
        deleteButton.setEnabled(true);
        deleteButton.setText("Delete");
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Main.add(deleteButton, gbc);
        searchButton = new JButton();
        searchButton.setText("Search");
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 13;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Main.add(searchButton, gbc);
        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Main.add(spacer8, gbc);
        final JPanel spacer9 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Main.add(spacer9, gbc);
        final JPanel spacer10 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipady = 20;
        Main.add(spacer10, gbc);
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$(null, -1, 22, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("Contacts");
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        Main.add(label4, gbc);
        final JPanel spacer11 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        Main.add(spacer11, gbc);
        addNewButton = new JButton();
        addNewButton.setEnabled(true);
        addNewButton.setText("Add new");
        addNewButton.setVisible(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Main.add(addNewButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return Main;
    }


}


