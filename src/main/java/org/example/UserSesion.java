package org.example;

import javax.swing.*;

public class UserSesion {

    public static void OpcionCliente(boolean isSesionActive, String email){
        System.out.println(email +" " + isSesionActive);
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
            if(opcion == -1){
                JOptionPane.showMessageDialog(null,"Debes cerrar sesión en opciones de usuario", "Error", JOptionPane.INFORMATION_MESSAGE);
            }

            switch (opcion){
                case 0:

                    break;
                case 1:

                    break;
                case 2:
                    boolean isSesionAcive = UserSesionActive(isSesionActive, email);
                    if(isSesionAcive == false){
                        System.out.println("fin de sesión");
                        opcion = 4;
                    }
                    break;
                case 3:

                    break;
                default:
                    System.out.println("No es una opcion valida");
                    break;
            }
        }while(opcion != 4);
    }

    public static boolean UserSesionActive(boolean isSesionActive, String email){
        //System.out.println(email + " " + pass);

        String[] opciones = {"Cambiar clave", "Desactivar cuenta","Cerrar Sesión","Regresar"};
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
                    ImplementDao.UpdatePass();
                    break;
                case 1:

                    break;
                case 2:
                    JOptionPane.showMessageDialog(null,"¡Hasta luego!", "Cerrando sesión", JOptionPane.INFORMATION_MESSAGE);
                    isSesionActive = false;
                    opcion = 3;
                    break;
                default:
                    System.out.println("regresando");
                    break;
            }
        }while(opcion != 3);

        return isSesionActive;
    }


}

