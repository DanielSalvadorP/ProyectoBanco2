package org.example;

import javax.management.Query;
import javax.swing.*;
import java.sql.*;
import java.util.Locale;
import java.util.concurrent.ForkJoinPool;

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

    public static boolean AccessUser() {
        int tryCount = 0;
        boolean isPassValid = false;

        try (Connection conection = bdConecction.getConnection()) {
            String email;
            String inputPass = null;
            do {
                tryCount += 1;

                //Solicitar credenciales
                email = JOptionPane.showInputDialog(null, "Ingresa tu correo", "Correo", JOptionPane.YES_NO_OPTION);

                //Consulta segura con PreparedStatement
                String accessemail = "Select correo, clave from estado where correo = ?;";
                try (PreparedStatement ps = conection.prepareStatement(accessemail)) {
                    ps.setString(1, email);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            String getEmail = rs.getString("correo");
                            String getPass = rs.getString("clave");

                            inputPass = JOptionPane.showInputDialog(null, "Ingresa tu clave", "Clave", JOptionPane.INFORMATION_MESSAGE);

                            if (getPass.equals(inputPass)) {
                                isPassValid = true;
                                JOptionPane.showMessageDialog(null, "Acceso concedido", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                return true;
                            } else {
                                JOptionPane.showMessageDialog(null, "Contraseña incorrecta", "Error", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Correo no encontrado", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }

                if (tryCount >= 3) {
                    JOptionPane.showMessageDialog(null, "Demasiados intentos fallidos. Acceso bloqueado", "Error", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }

            } while (!isPassValid);


        }catch (SQLException e) {
            System.out.println("Error de base de datos: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error en la data", "error", JOptionPane.ERROR_MESSAGE);
        }
        return isPassValid;
    }

    public static void UpdatePass(){
        String email = JOptionPane.showInputDialog(null, "Ingresa tu correo", "correo", JOptionPane.INFORMATION_MESSAGE);
        String passNew = JOptionPane.showInputDialog(null, "Ingresa tu nueva clave", "Clave nueva", JOptionPane.INFORMATION_MESSAGE);

        //Valida que no estén vacios el correo o la clave
        if(email == null || passNew == null || email.isEmpty() || passNew.isEmpty()){
            JOptionPane.showMessageDialog(null, "El correo y la clave no pueden estar vacios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Conección a la BD
        try(Connection connection = bdConecction.getConnection()){
            //Validar que existe el email
            String selectEmail = "SELECT correo from estado where correo = ?;";
            try(PreparedStatement ps = connection.prepareStatement(selectEmail)) {
                ps.setString(1, email);

                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        //Actualizar la contraseña
                        String sentence = "UPDATE estado set clave = ? where correo = ?;";
                        try(PreparedStatement psUpdate = connection.prepareStatement(sentence)){
                            psUpdate.setString(1, passNew);
                            psUpdate.setString(2, email);

                            int rowsAffected = psUpdate.executeUpdate();
                            if(rowsAffected >0) {
                                JOptionPane.showMessageDialog(null, "Cambio realizado", "Exito", JOptionPane.INFORMATION_MESSAGE);
                            } else{
                              JOptionPane.showMessageDialog(null, "No se pudo acctualizar", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "El correo no existe", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }catch (SQLException e){
            System.out.println("Error de base de datos: " + e);
        }
    }
}