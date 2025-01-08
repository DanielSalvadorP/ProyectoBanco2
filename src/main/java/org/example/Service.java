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
        boolean sessionActive = ImplementDao.AccessUser();
        if(sessionActive){
            UserSesionActive(sessionActive);
        }

    }

    public static void updateAccount(){

    }

    public static void deleteAccount(){

    }

    public static void UserSesionActive(boolean sessionActive){
        //System.out.println(email + " " + pass);
        //Cambiar clave", "Desactivar cuenta","Cerrar sesión"
        String[] opciones = {"Retirar Dinero", "Ingresar Dinero", "Opción de Usuario", "Cerrar Sesión"};
        int opcion = -1;

        do{
            opcion = JOptionPane.showOptionDialog(null,
                    "¿Qué quieres hoy?",
                    "Opciones",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            switch (opcion){
                case 0:

                    break;
                case 1:

                    break;
                case 2:
                    ImplementDao.UpdatePass();
                    break;
                default:
                    System.out.println("Hasta luego");
                    break;
            }
        }while(opcion != 3);
    }
}
