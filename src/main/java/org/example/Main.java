package org.example;

import jdk.dynalink.linker.LinkerServices;

import javax.swing.*;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        String[] opciones = {"Crear cuenta","Acceder","Actualizar datos","Eliminar cuenta", "Finalizar"};
        int opcion = -1;
        do{
           opcion = JOptionPane.showOptionDialog(null,"¿Qué quieres hacer hoy?",
           "Bienvenido",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones,opciones[0]);

           switch (opcion){
               case 0:
                   Service.createAccount();
                   break;
               case 1:
                   ImplementDao.AccessUser();
                   //Service.accesAccount();
                   break;
               case 2:
                   Service.updateAccount();
                   break;
               case 3:
                   Service.deleteAccount();
                   break;
               default:
                   break;
           }
        }while(opcion !=4);
    }
}