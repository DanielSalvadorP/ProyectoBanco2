package org.example;

import javax.swing.*;

public class UserSesion {

    /**
     * Opciones del usuario sobre la cuenta bancaria
     * @param isSesionActive
     * @param email
     */
    public static void OpcionClient(boolean isSesionActive, String email){
        System.out.println(email +" " + isSesionActive);
        String[] opciones = {"Opción de cuenta", "Opción de Usuario"};
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
                    AccountOpcion(isSesionActive);
                    break;
                case 1:
                    boolean isSesionAcive = OpcionAccountUser(isSesionActive, email);
                    if (isSesionAcive == false) {
                        System.out.println("fin de sesión");
                        opcion = 4;
                    }
                    break;
                default:
                    System.out.println("No es una opcion valida");
                    break;
            }
        }while(opcion != 4);
    }

    /**
     * Opciones de usuario sobre la cuenta de usuario
     * @param isSesionActive
     * @param email
     * @return
     */
    public static boolean OpcionAccountUser(boolean isSesionActive, String email){
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
                    ImplementDao.UpdatePass(email);
                    break;
                case 1:
                    if (Validation.Confirm()){
                        isSesionActive = ImplementDao.updateStateAccount(email,isSesionActive);
                        opcion = 3;
                    } else {
                        System.out.println("Desactivación cancelada por el usuario");
                    }
                    break;
                case 2:
                    if(Validation.Confirm()) {
                        isSesionActive = Validation.endSesion(isSesionActive);
                        opcion = 3;
                    } else {
                        System.out.println("Cierre de sesión cancelado por el usuario");
                    }
                    break;
                default:
                    System.out.println("Opción no valida, se debe cerrar sesión");
                    break;
            }
        }while(opcion != 3);

        return isSesionActive;
    }

    public static boolean AccountOpcion(boolean isSesionActive){
    String[] options = {"Cuanto tengo","Retirar Dinero", "Ingresar Dinero", "Movimientos", "Volver"};
    String option = (String) JOptionPane.showInputDialog(null,
            "Tu cuenta",
            "Opciones de cuenta",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        System.out.println(option);
    return isSesionActive;
    }
}

