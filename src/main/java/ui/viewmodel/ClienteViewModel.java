/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.viewmodel;

import data.dao.ClienteDAO;
import data.model.Cliente;
import java.sql.SQLException;

/**
 *
 * @author tobias
 */
public class ClienteViewModel {
   
   private ClienteDAO clienteDAO;
   
   public ClienteViewModel(){
        this.clienteDAO = new ClienteDAO();
   }
   
   public void altaCLiente (
           String codigo, 
           String nif, 
           String apellidos, 
           String nombre, 
           String domicilio, 
           String codigoPostal, 
           String localidad, 
           String telefono, 
           String movil, 
           String fax, 
           String email, 
           float total
   ) throws 
           SQLException {
       
       Cliente cliente = new Cliente(codigo, nif, apellidos, nombre, domicilio, codigoPostal, localidad, telefono, movil, fax, email, total);
       clienteDAO.altaCliente(cliente);
       
   }
}
