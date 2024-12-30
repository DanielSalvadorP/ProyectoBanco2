package org.example;

import javax.swing.*;
import java.sql.SQLException;

public class Service {

    public static void createAccount() throws SQLException {
        String name = JOptionPane.showInputDialog(null, "Ingresa tu nombre", "Nombre");
        String lastName = JOptionPane.showInputDialog(null, "Ingresa tu apellido", "Apellido");
        int cedula = Validation.idIsValid();
        String email = Validation.emailIsValid();
        String pass = Validation.passIsValid();

        System.out.println("vamos bien");


        /*name = "daniel";
        lastName = "salvador";
        cedula = 100756;
        email = "dant@gmail.com";
        pass = "1007653kjhhsd";*/

       ImplementDao.create(name,lastName,cedula,email,pass);

    }

    public static void accesAccount(){

    }

    public static void updateAccount(){

    }

    public static void deleteAccount(){

    }
}
