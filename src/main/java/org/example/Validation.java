package org.example;

import javax.swing.*;

/**
 * Clase donde se validan los diferentes parametros
 * que se necesiten para el correcto funcionamiento
 */
public class Validation {
    private static final int VALUE_MIN_PASS = 8;

    /**
     * Revisa que el numero de cedula sea valido
     *
     * @return
     */
    public static int idIsValid() {
        int idUser = 0;
        boolean isValid = false;

        while (!isValid) {
            try {
                //pide el numero de cedula y valida que no esté vacío o que no tenga espacios
                String input = inputAndValidate("Ingresa tu cedula", "Cedula");

                idUser = Integer.parseInt(input);
                //Validación de si entra en una cedula valida
                if (idUser > 80000000 && idUser < 1200000000) {
                    System.out.println("Id valida");
                    isValid = true;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Debe escribir su numero de cedula",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: " + e);
            }
        }
        return idUser;
    }

    /***
     * Revisa que el correo sea valido mientras este contenga un solo arroba
     * y lo retorna para que sea registrado en la Bd.
     * @return
     */
    public static String emailIsValid() {
        String emailValid = "";
        int cont = 0;
        boolean isValid = false;
        while (!isValid) {
            emailValid = inputAndValidate("Ingresa tu email", "Email");

            for (int i = 0; i < emailValid.length(); i++) {
                System.out.println(i);
                if (emailValid.charAt(i) == '@') {
                    System.out.println(emailValid.charAt(i));
                    cont += 1;
                }
            }
            if (cont == 1) {
                isValid = true;
            } else {
                System.out.println(cont);
                JOptionPane.showMessageDialog(null, "Debe ser un correo valido", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
        return emailValid;
    }

    /**
     * Apartado para crear la contraseña
     * solo hasta que cumpla los estandares requeridos
     *
     * @return
     */
    public static String passIsValid() {
        String pass = "";
        String confirmPass;

        int countvalid=0;
        boolean isValid = false;
        while (!isValid) {

            System.out.println("countvalid");
            pass = inputAndValidate("Ingresa una clave para tu cuenta",
                    "Clave");
            confirmPass = inputAndValidate("Confirma la clave para tu cuenta",
                    "Confirmación de clave");

            if (pass.length() >= VALUE_MIN_PASS && pass.equals(confirmPass)) {
                if (isPasswordValid(pass) == true) {
                    isValid = true;
                    System.out.println("Clave valida");
                    break;
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Tu contraseña debe incluir: " +
                                    "\n- Al menos una mayúscula" +
                                    "\n- Al menos una minúscula" +
                                    "\n- Al menos un número" +
                                    "\n- Al menos un carácter especial" +
                                    "\n- No debe contener espacios",
                            "Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe tener más de 8 digitos", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
        return pass;
    }

    /**
     * Recibe la contraseña creada por el usuario
     * y valida que cumpla con los estandares recomendados
     *
     * @param password
     * @return
     */
    public static boolean isPasswordValid(String password) {
        boolean contSpace = false;
        boolean contUpper = false;
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
        return contUpper && contNum && contLower && contSim && !contSpace;
    }

    /**
     * Valida que la entrada no sea nula o vacía y que no tenga espacios demas
     *
     * @param message
     * @param title
     * @return
     */
    public static String inputAndValidate(String message, String title) {
        String input;

        do {
            input = JOptionPane.showInputDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Esto fue lo que se imprimio:" + input + "2");

            if (input == null) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas cancelar?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    return null; // Permitir que el método superior maneje la cancelación.
                }
            } else if (input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El campo no debe estar vacio", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                return input.trim();
            }
        } while (true);
    }

    /**
     * Valida que el usuario sí quiera realizar la acción que ha tomado.
     * @return
     */
    public static boolean Confirm(){
        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Seguro?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION);
        return confirm == JOptionPane.YES_OPTION;
    }

    /**
     * Cierra la sesión del usuario
     * @param isSesionActive
     * @return
     */
    public static boolean endSesion(boolean isSesionActive){
        System.out.println("Cerrando sesion");
        JOptionPane.showMessageDialog(null, "¡Hasta luego!", "Cerrando sesión", JOptionPane.INFORMATION_MESSAGE);
        return isSesionActive = false;
    }

}
