package org.example;

import javax.swing.*;
import java.sql.SQLException;

public class Service {

    public static void createAccount() throws SQLException {
        String name = UserSesion.inputAndValidate("Ingresa tu nombre", "Nombre");
        String lastName = UserSesion.inputAndValidate("Ingresa tu apellido", "Apellido");
        int cedula = Validation.idIsValid();
        String email = Validation.emailIsValid();
        String pass = Validation.passIsValid();
       ImplementDao.create(name,lastName,cedula,email,pass);
       /*
       1. Validar que no sea null la entrada de nombre y aprellido con un metodo recursivo
       2. Validarque el correo no haya exista
       3. manejo de excepciones
        */
    }

    public static void accesAccount(){
        ImplementDao.AccessUser();
    }

    public static void updateAccount(){

    }

    public static void deleteAccount(){

    }



}
