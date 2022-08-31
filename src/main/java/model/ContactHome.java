package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactHome {
    private static ContactHome instance = new ContactHome();

    public static ContactHome getInstance() {
        return ContactHome.instance;
    }

    //Add contacts
    public Contact addContact(String firstName, String lastName, String phoneNumber) throws SQLException, ClassNotFoundException {

        Connection connect = new DbConnection().getConnection();
        PreparedStatement preparedS = connect.prepareStatement("Insert into contactsnew.contacts (firstName, lastName, phoneNumber) values (?, ?, ?)");
        preparedS.setString(1, firstName);
        preparedS.setString(2, lastName);
        preparedS.setString(3, phoneNumber);
        preparedS.executeUpdate();
        preparedS.close();
        Contact contact = new Contact(firstName, lastName, phoneNumber);
        return contact;


    }
}
