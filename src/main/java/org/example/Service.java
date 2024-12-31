package org.example;

import javax.swing.*;
import java.sql.SQLException;

public class Service {

    public static void createAccount() throws SQLException {
        String name = JOptionPane.showInputDialog(null, "Ingresa tu nombre", "Nombre", JOptionPane.INFORMATION_MESSAGE);
        String lastName = JOptionPane.showInputDialog(null, "Ingresa tu apellido", "Apellido", JOptionPane.INFORMATION_MESSAGE);
        int cedula = Validation.idIsValid();
        String email = Validation.emailIsValid();
        String pass = Validation.passIsValid();
       ImplementDao.create(name,lastName,cedula,email,pass);
    }

    public static void accesAccount(){

    }

    public static void updateAccount(){

    }

    public static void deleteAccount(){

    }
}
