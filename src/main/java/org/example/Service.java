package org.example;

import javax.swing.*;
import java.sql.SQLException;

public class Service {

    /**
     * Solicita los datos de la creación de usuario
     * @throws SQLException
     */
    public static void createAccount() throws SQLException {
        String name = Validation.inputAndValidate("Ingresa tu nombre", "Nombre");
        String lastName = Validation.inputAndValidate("Ingresa tu apellido", "Apellido");
        int cedula = Validation.idIsValid();
        String email = Validation.emailIsValid();
        String pass = Validation.passIsValid();
       ImplementDao.create(name,lastName,cedula,email,pass);
       /*
       2. Validarque el correo no exista (ya, ahora necesito que valide la cedula tambien)
       3. manejo de excepciones
        */
    }

    /**
     * Inicia el servicio de inicio de sesión
     */
    public static void accesAccount(){
        ImplementDao.AccessUser();
    }
}
