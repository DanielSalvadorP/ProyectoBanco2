package org.example;

import javax.swing.*;

public class Validation {
    private static final int VALUE_MIN_PASS = 8;

    public static int idIsValid(){
        int idUser = 0;
        boolean isValid = false;
        while(!isValid){
            /*Intentará hacer que el usuario ingrese una cedula valida,
            * Cada vez que no lo haga, mostrará un fallo y lo intentará de nuevo
            */
            try{
                String input = JOptionPane.showInputDialog(null, "Ingresa tu cedula", "Cedula", JOptionPane.QUESTION_MESSAGE);
                if (input == null){
                    JOptionPane.showMessageDialog(null, "Vacio rey ¿qué pasó?", "Error",JOptionPane.WARNING_MESSAGE);
                }
                idUser= Integer.parseInt(input);

                //Validación de si entra en una cedula valida
                if (idUser >80000000 && idUser <1200000000){
                    System.out.println("Id valida");
                    isValid = true;
                } else{
                    JOptionPane.showMessageDialog(null,"Papi, tu cedula", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }catch (NumberFormatException e){
                System.out.println("Error: " + e);
            }
        }
        return idUser;
    }

    /***
     * Recibe el correo y valida sea valido revisando que haya solo un arroba.
     * @return
     */
    public static String emailIsValid(){
        String emailValid = "";
        int cont=0;
        boolean isValid = false;
        while(!isValid){
            emailValid = JOptionPane.showInputDialog(null, "Ingresa tu email", "Email",  JOptionPane.INFORMATION_MESSAGE);
            for(int i = 0; i<emailValid.length(); i++){
                System.out.println(i);
                if(emailValid.charAt(i) == '@'){
                    System.out.println(emailValid.charAt(i));
                    cont+=1;
                }
            }if(cont ==1){
                    isValid=true;
            }else{
                System.out.println(cont);
                   JOptionPane.showMessageDialog(null, "papi, un correo te pido", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
        return emailValid;
    }

    public static String passIsValid(){
        String pass = "";
        String confirmPass;
        boolean isValid = false;
        while(!isValid) {
            pass = JOptionPane.showInputDialog(null,
                    "Ingresa una clave para tu cuenta",
                    "Clave",
                    JOptionPane.INFORMATION_MESSAGE);
            confirmPass = JOptionPane.showInputDialog(null,
                    "Confirma la clave para tu cuenta",
                    "Confirmación de clave",
                    JOptionPane.INFORMATION_MESSAGE);


            if (pass.length() >= VALUE_MIN_PASS && pass.equals(confirmPass)) {
                if(isPasswordValid(pass) == true) {
                    isValid=true;
                    System.out.println("Clave valida");
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Tu contraseña debe incluir: \n- Al menos una mayúscula\n- Al menos una minúscula\n- Al menos un número\n- Al menos un carácter especial\n- No debe contener espacios",
                            "Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(null, "papi, que tenga más de 8 digitos", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
        return pass;
    }

    public static boolean isPasswordValid(String password){
        boolean contSpace=false;
        boolean contUpper=false;
        boolean contLower = false;
        boolean contNum = false;
        boolean contSim = false;

        for (char C : password.toCharArray()) {
            if (Character.isUpperCase(C)) {
                contUpper = true;
            }
            if (Character.isLowerCase(C)) {
                contLower = true;
            }
            if (Character.isDigit(C)) {
                contNum = true;
            }
            if (!Character.isLetterOrDigit(C) && !Character.isWhitespace(C)) {
                contSim = true;
            }
            if (Character.isWhitespace(C)) {
                contSpace = true;
            }
        }
        return contUpper && contNum && contLower && contSim && !contSpace;    }
}
