/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectoswing;

import gui.Login;
import java.sql.Connection;
import java.sql.SQLException;
import utils.DatabaseConnection;


/**
 *
 * @author Alex Alvarado
 */
public class ProyectoSwing {

    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos.");
            e.printStackTrace();
            return;
        }
        Login login = new Login();
        login.show();
    }
}
