package org.example;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.SQLOutput;

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

    /**
     * Opciones del usuario sobre la cuenta en general
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
        String[] opciones = {"Editar Datos", "Desactivar cuenta","Eliminar cuenta","Cerrar Sesión","Regresar"};
        int opcion = -1;

        do{
            opcion = JOptionPane.showOptionDialog(null,
                    "¿Qué quieres hoy?",
                    "Opciones",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            switch (opcion){
                case 0:
                    editDataOpcion(isSesionActive, email);
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
                    System.out.println("La buena carigulectico");
                    Validation.Confirm();

                    break;
                case 3:
                    if(Validation.Confirm()) {
                        isSesionActive = Validation.endSesion(isSesionActive);
                        opcion = 4;
                    } else {
                        System.out.println("Cierre de sesión cancelado por el usuario");
                    }
                    break;
                default:
                    System.out.println("Atrás");
                    break;
            }
        }while(opcion != 4);

        return isSesionActive;
    }

    public static boolean AccountOpcion(boolean isSesionActive){
        String[] options = {"Cuanto tengo","Retirar Dinero",
                "Ingresar Dinero", "Movimientos", "Volver"};
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

    public static boolean editDataOpcion(boolean isSesionActive, String email){
        String[] options = {"Nombre","Apellido", "Correo",
                "Clave", "Volver"};
        boolean isRunning = true;

        do {
            int option = JOptionPane.showOptionDialog(null,
                    "Tu cuenta",
                    "Opciones de cuenta",
                    JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.YES_NO_OPTION,
                    null,
                    options,
                    options[0]);

            switch (option){
                case 1:

                    break;
                case 2:

                    break;
                case 3:
                    ImplementDao.UpdatePass(isSesionActive, email);
                    break;
                case 4:

                    break;
                default:
                    System.out.println("Opción de Default");
                    isRunning = false;
                    break;
            }
        }while (!isRunning);


        return isSesionActive;
    }


}
