package org.example;

import jdk.dynalink.linker.LinkerServices;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String[] opciones = {"Crear cuenta","Acceder","Actualizar datos","Eliminar cuenta", "Finalizar"};
        int opcion = -1;
        do{
           opcion = JOptionPane.showConfirmDialog(null,"¿Qué quieres hacer hoy?",
           "Bienvenido",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

           switch (opcion){
               case 0:
                   Service.createAccount();
                   break;
               case 1:
                   break;
               case 2:
                   break;
               case 3:
                   break;
               case 4:
                   break;
               default:
                   break;
           }

        }while(opcion !=5);
    }
}