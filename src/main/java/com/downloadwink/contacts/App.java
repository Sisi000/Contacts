package com.downloadwink.contacts;

import controller.ContactFormController;
import view.ContactForm;

import javax.swing.*;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("Contact Form");
        ContactForm contactForm = new ContactForm();
        ContactFormController contactFormController = new ContactFormController(contactForm);
        frame.setContentPane(contactForm.getMain());
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
