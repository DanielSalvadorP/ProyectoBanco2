package org.example;

import javax.swing.*;

public class Validation {

    public static int idIsValid(){
        int idUser = 0;
        boolean isValid = false;
        while(!isValid){
            /*Intentará hacer que el usuario ingrese una cedula valida,
            * Cada vez que no lo haga, mostrará un fallo y lo intentará de nuevo
            */
            try{
                String input = JOptionPane.showInputDialog(null, "Ingresa tu cedula", "Cedulan ", JOptionPane.QUESTION_MESSAGE);
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

    public static String emailIsValid(){
        String emailValid;
        int cont=0;
        boolean isValid = false;
        while(!isValid){
        String email = JOptionPane.showInputDialog(null, "Ingresa tu email", "Email");
        for(int i = 0; i<email.length(); i++){
            if(email.charAt(i)=='@'){
                cont+=1;
            }
        }if(cont ==1){
                return email;
            }else{
                JOptionPane.showMessageDialog(null, "papi, un correo te pido", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
        return "";
    }

    public static String passIsValid(){
        String pass = JOptionPane.showInputDialog(null, "Ingresa tu clave", "Clave");

        return "";
    }
}
