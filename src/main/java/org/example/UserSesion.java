package org.example;

import javax.swing.*;

public class UserSesion {

    public static void OpcionCliente(String email, String pass){
        System.out.println(email + " " + pass);
        //Cambiar clave", "Desactivar cuenta","Cerrar sesión"
        String[] opciones = {"Retirar Dinero", "Ingresar Dinero", "Opción de Usuario"};
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
                case 3:

                    break;
                default:
                    System.out.println("Esta no es una opcion valida");
                    break;
            }
        }while(opcion != 4);
    }

    public static void OpcionUsuario(String email, String pass){

    }
}

