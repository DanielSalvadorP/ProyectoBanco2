package org.example;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class bdConecction {

    private static final String url = "jdbc:mysql://localhost:3306/SmtaBanco";
    private static final String usuario = "root";
    private static final String clave = "";

  public static Connection getConnection() throws SQLException{
      return DriverManager.getConnection(url, usuario,clave);
  }

}
