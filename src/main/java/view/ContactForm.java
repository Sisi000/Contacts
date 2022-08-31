package view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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
    private JPanel enterPanel;
    private JButton clearAllButton;
    private JButton resetButton;

    private JScrollPane table1;


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

    public JPanel getMain() {
        return Main;
    }

    public JButton getAddNewButton() {
        return addNewButton;
    }

    public void setAddNewButton(JButton addNewButton) {
        this.addNewButton = addNewButton;
    }

    public JPanel getEnterPanel() {
        return enterPanel;
    }

    public JTextField getTxtFirstName() {
        return txtFirstName;
    }

    public JTextField getTxtLastName() {
        return txtLastName;
    }

    public JTextField getTxtPhoneNumber() {
        return txtPhoneNumber;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public ContactForm() throws SQLException {
        getConnection();
        table_load();

        //Add new button
        addNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterPanel.setVisible(true);
                deleteButton.setVisible(false);
                updateButton.setVisible(false);
                txtFirstName.setText("");
                txtLastName.setText("");
                txtPhoneNumber.setText("");
                txtFirstName.requestFocus();
            }
        });

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
                        enterPanel.setVisible(false);
                        saveButton.setVisible(false);
                        clearAllButton.setVisible(false);
                        addNewButton.setVisible(true);
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
                    saveButton.setVisible(false);
                    addNewButton.setVisible(true);
                    deleteButton.setVisible(false);
                    updateButton.setVisible(false);
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
                    String lastNameSearch = textField1.getText();
                    String phoneNumberSearch = textField1.getText();

                    pst = getConnection().prepareStatement("select * from contacts where firstName = ? || lastName = ? || phoneNumber = ?");
                    pst.setString(1, firstNameSearch);
                    pst.setString(2, lastNameSearch);
                    pst.setString(3, phoneNumberSearch);
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
                enterPanel.setVisible(true);
                addNewButton.setVisible(true);
                updateButton.setVisible(true);
                deleteButton.setVisible(true);
                saveButton.setVisible(false);
                int i = table_1.getSelectedRow();
                TableModel model = table_1.getModel();
//                textField1.setText(model.getValueAt(i, 1).toString() + model.getValueAt(i, 2).toString() + model.getValueAt(i, 3).toString());
                txtFirstName.setText(model.getValueAt(i, 1).toString());
                txtLastName.setText(model.getValueAt(i, 2).toString());
                txtPhoneNumber.setText(model.getValueAt(i, 3).toString());
            }
        });

        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtFirstName.setText("");
                txtLastName.setText("");
                txtPhoneNumber.setText("");
                txtFirstName.requestFocus();
            }
        });


        KeyAdapter listener = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                clearAllButton.setVisible(true);
                saveButton.setVisible(true);
                addNewButton.setVisible(false);
            }
        };
        txtFirstName.addKeyListener(listener);
        txtLastName.addKeyListener(listener);
        txtPhoneNumber.addKeyListener(listener);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    table_load();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                txtFirstName.setText("");
                txtLastName.setText("");
                txtPhoneNumber.setText("");
                textField1.setText("");
                txtFirstName.requestFocus();
                enterPanel.setVisible(false);
                addNewButton.setVisible(true);
                updateButton.setVisible(false);
                deleteButton.setVisible(false);
                saveButton.setVisible(false);
                clearAllButton.setVisible(false);
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
        Main.setAlignmentX(0.5f);
        Main.setAlignmentY(0.5f);
        Main.setAutoscrolls(false);
        Font MainFont = this.$$$getFont$$$(null, -1, -1, Main.getFont());
        if (MainFont != null) Main.setFont(MainFont);
        Main.setPreferredSize(new Dimension(450, 622));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setEnabled(true);
        scrollPane1.setMaximumSize(new Dimension(32767, 32767));
        scrollPane1.setMinimumSize(new Dimension(200, 21));
        scrollPane1.setPreferredSize(new Dimension(370, 328));
        scrollPane1.setVisible(true);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 9;
        gbc.gridwidth = 16;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipadx = 40;
        Main.add(scrollPane1, gbc);
        table_1 = new JTable();
        table_1.setMinimumSize(new Dimension(30, 50));
        scrollPane1.setViewportView(table_1);
        enterPanel = new JPanel();
        enterPanel.setLayout(new GridLayoutManager(6, 2, new Insets(5, 5, 5, 5), -1, -1));
        enterPanel.setMaximumSize(new Dimension(400, 2147483647));
        enterPanel.setMinimumSize(new Dimension(400, 50));
        enterPanel.setVisible(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 15;
        gbc.fill = GridBagConstraints.BOTH;
        Main.add(enterPanel, gbc);
        enterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        txtFirstName = new JTextField();
        enterPanel.add(txtFirstName, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtLastName = new JTextField();
        txtLastName.setHorizontalAlignment(10);
        enterPanel.add(txtLastName, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtPhoneNumber = new JTextField();
        txtPhoneNumber.setEditable(true);
        enterPanel.add(txtPhoneNumber, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, -1, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("First Name");
        enterPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Last Name");
        enterPanel.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Phone Number");
        enterPanel.add(label3, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        enterPanel.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        enterPanel.add(spacer2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        enterPanel.add(spacer3, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 80;
        Main.add(spacer4, gbc);
        textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(40, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.gridwidth = 12;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Main.add(textField1, gbc);
        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.setVisible(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 15;
        gbc.gridy = 5;
        Main.add(saveButton, gbc);
        searchButton = new JButton();
        searchButton.setText("Search");
        gbc = new GridBagConstraints();
        gbc.gridx = 15;
        gbc.gridy = 7;
        Main.add(searchButton, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipady = 30;
        Main.add(spacer5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 11;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        Main.add(spacer6, gbc);
        updateButton = new JButton();
        updateButton.setEnabled(true);
        updateButton.setText("Update");
        updateButton.setVisible(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 13;
        gbc.gridy = 5;
        Main.add(updateButton, gbc);
        deleteButton = new JButton();
        deleteButton.setEnabled(true);
        deleteButton.setText("Delete");
        deleteButton.setVisible(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 11;
        gbc.gridy = 5;
        Main.add(deleteButton, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 13;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        Main.add(spacer7, gbc);
        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.VERTICAL;
        Main.add(spacer8, gbc);
        final JPanel spacer9 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.VERTICAL;
        Main.add(spacer9, gbc);
        addNewButton = new JButton();
        addNewButton.setEnabled(true);
        addNewButton.setText("Add new");
        addNewButton.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Main.add(addNewButton, gbc);
        clearAllButton = new JButton();
        clearAllButton.setText("Clear All");
        clearAllButton.setVisible(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 5;
        Main.add(clearAllButton, gbc);
        final JPanel spacer10 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Main.add(spacer10, gbc);
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$(null, -1, 22, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("Contacts");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        Main.add(label4, gbc);
        resetButton = new JButton();
        resetButton.setText("Reset");
        gbc = new GridBagConstraints();
        gbc.gridx = 17;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Main.add(resetButton, gbc);
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


