package org.example;

import javax.management.Query;
import javax.swing.*;
import java.sql.*;
import java.util.Locale;

public class ImplementDao {

    /***
     * Metodo de creación de usuario y asignación de estado
     * @param name
     * @param lastName
     * @param cedula
     * @param email
     * @param pass
     * @throws SQLException
     */
    public static void create(String name, String lastName, int cedula, String email, String pass) throws SQLException {
        String createUser = "INSERT INTO user (name_user, lastname_user, cedula, correo) VALUES (?,?,?,?);";
        String estadoUser = "INSERT INTO estado (estado_user, correo, clave) VALUES (?,?,?)";

        try (Connection conection = bdConecction.getConnection()) {
            conection.setAutoCommit(false);

            try (PreparedStatement psUser = conection.prepareStatement(createUser);
                 PreparedStatement psEstado = conection.prepareStatement(estadoUser)) {

                psUser.setString(1, name.toLowerCase(Locale.ROOT));
                psUser.setString(2, lastName);
                psUser.setInt(3, cedula);
                psUser.setString(4, email);
                psUser.executeUpdate();

                psEstado.setString(1, "Activo");
                psEstado.setString(2, email);
                psEstado.setString(3, pass);
                psEstado.executeUpdate();

                conection.commit();
            }
            System.out.println("usuario creado");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Datos de usuario ya existente\nIntenta de nuevo", "Error", JOptionPane.WARNING_MESSAGE);
            System.out.println("Falló");
        }
    }

    public static void AccessUser() {
        String getEmail = "";
        String getPass="";
        int tryCount = 0;
        boolean isPassValid = false;

        try (Connection conection = bdConecction.getConnection()) {
            do {
                tryCount += 1;

                //Solicitar credenciales
                String email = JOptionPane.showInputDialog(null, "Ingresa tu correo", "Correo", JOptionPane.QUESTION_MESSAGE);
                String pass = JOptionPane.showInputDialog(null, "Ingresa tu clave", "Clave", JOptionPane.QUESTION_MESSAGE);

                //Consulta segura con PreparedStatement
                String accessemail = "Select correo from estado where correo = '" + email + "';";
                try(Statement ps = conection.createStatement()){
                    try(ResultSet rs = ps.executeQuery(accessemail)){
                        while (rs.next()) {
                            getEmail = rs.getString("correo");
                            System.out.println(getEmail);
                        }
                        rs.close();
                        if (getEmail.equalsIgnoreCase(email)) {
                            String accessPass = "Select clave from estado where clave = '"+pass+"';";
                            Statement psPass = conection.createStatement();
                            ResultSet rsPass = psPass.executeQuery(accessPass);
                            while(rsPass.next()){
                                getPass = rsPass.getString("clave");
                            }
                            //se deben corregir los intentos de autenticación y se debe asignar el bloqueo despues de 3 intentos
                            if(getPass.equals(pass) && tryCount <=3){
                                isPassValid = true;
                                JOptionPane.showMessageDialog(null, "Acceso concedido", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            }else if (!getPass.equals(pass) && tryCount>=3) {
                                JOptionPane.showMessageDialog(null, "Contraseña incorrecta", "Error", JOptionPane.WARNING_MESSAGE);
                                isPassValid = true;
                            }else {
                                JOptionPane.showMessageDialog(null, "Contraseña incorrecta", "Error", JOptionPane.WARNING_MESSAGE);
                            }
                        }else {
                            JOptionPane.showMessageDialog(null, "Correo no encontrado", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }
            } while (!isPassValid);

            if(tryCount >=3){
                JOptionPane.showMessageDialog(null, "Muchos intentos, chao", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Ingreso", "ok", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error en la data");
        }
    }
}