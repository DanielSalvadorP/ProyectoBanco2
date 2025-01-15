package org.example;

import javax.management.Query;
import javax.swing.*;
import java.sql.*;
import java.util.Locale;
import java.util.concurrent.ForkJoinPool;

/**
 * Clase que ejecuta los query a la BD
 */
public class ImplementDao {
    private  static final String ESTATE_DESACTIVATE = "Desactivado";
    private  static final String ESTATE_ACTIVATE = "Activo";

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
            JOptionPane.showMessageDialog(null,
                    "Datos de usuario ya existente\nIntenta de nuevo",
                    "Error", JOptionPane.WARNING_MESSAGE);
            System.out.println("Falló");
        }
    }

    /**
     * Inicio de sesión siempre y cuando la cuenta exista
     */
    public static void AccessUser() {
        int tryCount = 0;
        boolean isPassValid = false;
        boolean isSesionActive = false;
        try (Connection conection = bdConecction.getConnection()) {
            String email;
            String inputPass = null;
            do {
                tryCount += 1;

                //Solicitar credenciales
               /* email = JOptionPane.showInputDialog(null,
                        "Ingresa tu correo",
                        "Correo",
                        JOptionPane.INFORMATION_MESSAGE);*/

                email= Validation.inputAndValidate("Ingresa tu correo",
                        "Correo");
                if(email == null){
                    JOptionPane.showMessageDialog(null,
                            "Operación cancelada",
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                }

                //Consulta segura con PreparedStatement
                String accessemail = "SELECT correo, estado_user, clave FROM estado WHERE correo = ?;";
                try (PreparedStatement ps = conection.prepareStatement(accessemail)) {
                    ps.setString(1, email);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {

                            String getEmail = rs.getString("correo");
                            String getPass = rs.getString("clave");
                            String getestado = rs.getString("estado_user");
                            if(getestado.equals(ESTATE_ACTIVATE)){
                                inputPass = Validation.inputAndValidate("Ingresa tu clave",
                                        "Clave");
                                        /*JOptionPane.showInputDialog(null,
                                        "Ingresa tu clave",
                                        "Clave",
                                        JOptionPane.INFORMATION_MESSAGE);*/
                                if(inputPass == null){
                                    JOptionPane.showMessageDialog(null, "Operación cancelada", "Información", JOptionPane.INFORMATION_MESSAGE);
                                    break;
                                }

                                if (getPass.equals(inputPass)) {
                                    isPassValid = true;
                                    JOptionPane.showMessageDialog(null,
                                            "Acceso concedido",
                                            "Éxito",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    UserSesion.OpcionClient(isSesionActive, email);
                                     } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Contraseña incorrecta",
                                            "Error",
                                            JOptionPane.WARNING_MESSAGE);
                                }
                            }else{
                                System.out.println("Usuario inactivo, no permite acceso");
                                int opcion = JOptionPane.showConfirmDialog(null,
                                        "Usuario inactivo ¿Lo quiere activar?",
                                        "Inactivo",
                                        JOptionPane.YES_NO_OPTION);
                                if(opcion == 0){
                                    updateStateAccount(email, isSesionActive);
                                }else{
                                    System.out.println("No actualizar");
                                    JOptionPane.showMessageDialog(null, "Ok");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Correo no encontrado",
                                    "Error",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }

                if (tryCount >= 3) {
                    JOptionPane.showMessageDialog(null,
                            "Demasiados intentos fallidos. Acceso bloqueado",
                            "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                }

            } while (!isPassValid );

        }catch (SQLException e) {
            System.out.println("Error de base de datos: "+e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Error en la data",
                    "error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Actualiza la contraseña cambiandola por una nueva
     * siempre y cuando cumpla con los estandares de seguridad
     * @param email
     */
    public static void UpdatePass(String email){
        //Se trae la contraseña nueva desde el metodo de validación de clave
        String passNew = Validation.passIsValid();

            //Conección a la BD
        try (Connection connection = bdConecction.getConnection()) {
            //Validar que existe el email
            String selectEmail = "SELECT correo FROM estado WHERE correo = ?;";
            try (PreparedStatement ps = connection.prepareStatement(selectEmail)) {
                ps.setString(1, email);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        //Actualizar la contraseña
                        String sentence = "UPDATE estado SET clave = ? WHERE correo = ?;";
                        try (PreparedStatement psUpdate = connection.prepareStatement(sentence)) {
                            psUpdate.setString(1, passNew);
                            psUpdate.setString(2, email);

                            int rowsAffected = psUpdate.executeUpdate();
                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(null,
                                        "Cambio realizado",
                                        "Exito",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "No se pudo acctualizar",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "El correo no existe",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e);
        }
    }

    /**
     * Activa o Desactiva la cuenta de usuario
     * @param email
     * @param isSesionActive
     * @return
     */
    public static boolean updateStateAccount(String email, boolean isSesionActive){
        //Conección a la BD
        String newEstate = ESTATE_ACTIVATE;
        try(Connection connection = bdConecction.getConnection()){
            String selectSentence = "SELECT estado_user, correo FROM estado WHERE correo = ?";
            try(PreparedStatement psSelect = connection.prepareStatement(selectSentence)){
                psSelect.setString(1, email);

                try(ResultSet rs  = psSelect.executeQuery()){
                    if(rs.next()){
                        String emailConfirm = rs.getString("correo");
                        String estadoConfirm = rs.getString("estado_user");

                        if(estadoConfirm.equals(newEstate)){
                            newEstate = ESTATE_DESACTIVATE;
                        }

                        JOptionPane.showMessageDialog(null,
                                "El correo "+emailConfirm+" ha sido "+newEstate);

                        //Sentencia de update de estado
                        String updateSentence = "UPDATE estado SET estado_user = ? WHERE correo = ?;";
                        try(PreparedStatement ps = connection.prepareStatement(updateSentence)){
                            ps.setString(1, newEstate);
                            ps.setString(2, email);
                            ps.executeUpdate();

                        return Validation.endSesion(isSesionActive);
                        }
                    }
                }
            }
        } catch(SQLException e){
            System.out.println("Error de BD:" + e);
            JOptionPane.showMessageDialog(null,
                    "Error en la conección a BD",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        System.out.println(isSesionActive);
        return isSesionActive;
    }
}