package controller;

import model.Contact;
import view.ContactForm;

import javax.swing.*;

public class ContactFormController {
    private ContactForm view;
    private Contact contact;

    public ContactForm getView() {
        return view;
    }

    public ContactFormController(ContactForm view) {
        this.view = view;
//        this.contact = contact;

        JButton addNewButton = view.getAddNewButton();
        addNewButton.addActionListener(e -> {
//            view.getAddNewButton().setText("Save");
            view.getEnterPanel().setVisible(true);
                view.getDeleteButton().setVisible(false);
                view.getUpdateButton().setVisible(false);
                view.getTxtFirstName().setText("");
                view.getTxtLastName().setText("");
                view.getTxtPhoneNumber().setText("");
//            view.getTxtFirstName.requestFocus();
        });

    }
}
