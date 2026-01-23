/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.database;

import excepciones.DataAccessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author tobias
 */
public class ConexionDB {
    public String URL = "jdbc:postgresql://localhost:5432/aprendizaje?currentSchema=almacen";
    String USER = "alumno";
    String PWD = "alumno";
    
    public ConexionDB(){
       
    }
    
    public Connection connectDataBase() throws DataAccessException {
        try {
            return DriverManager.getConnection(URL, USER, PWD);
        } catch (SQLException e) {
            throw new DataAccessException("No se ha podido conectar a la base de datos", e);
        }
    }
}
