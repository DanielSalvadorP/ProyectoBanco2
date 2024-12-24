package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        String createUser = "INSERT INTO user (name_user, lastname_user, cedula, correo, clave) VALUES (?,?,?,?,?);";
        String estadoUser = "INSERT INTO estado (estado_user, correo) VALUES (?,?)";

        try(Connection conection = bdConecction.getConnection()){
            conection.setAutoCommit(false);

            try(PreparedStatement psUser = conection.prepareStatement(createUser);
                PreparedStatement psEstado = conection.prepareStatement(estadoUser)){

                psUser.setString(1,name);
                psUser.setString(2,lastName);
                psUser.setInt(3,cedula);
                psUser.setString(4,email);
                psUser.setString(5,pass);
                psUser.executeUpdate();

                psEstado.setString(1,"Activo");
                psEstado.setString(2,email);
                psEstado.executeUpdate();

                conection.commit();

        }

            System.out.println("usuario creado");
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("falló");
        }


    }
}
