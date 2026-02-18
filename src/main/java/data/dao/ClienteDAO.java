/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.dao;

import data.database.ConexionDB;
import data.model.Cliente;
import excepciones.ClienteAlreadyExistsException;
import excepciones.ClienteNotFoundException;
import excepciones.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author tobias
 */
public class ClienteDAO {
    
    private ConexionDB conn;
    
    public ClienteDAO(){
        this.conn = new ConexionDB();
    }
    
    public void insertar(Cliente cliente) throws ClienteAlreadyExistsException, DataAccessException {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        
        String sql = "INSERT INTO clientes(codigo, nif, apellidos, nombre, domicilio, codigo_postal, localidad, telefono, movil, fax, email, total_ventas) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try(Connection connection = conn.connectDataBase();
            PreparedStatement stm = connection.prepareStatement(sql)){
            
            stm.setString(1, cliente.getCodigo());
            stm.setString(2, cliente.getNif());
            stm.setString(3, cliente.getApellidos());
            stm.setString(4, cliente.getNombre());
            stm.setString(5, cliente.getDomicilio());
            stm.setString(6, cliente.getCodigoPostal());
            stm.setString(7, cliente.getLocalidad());
            stm.setString(8, cliente.getTelefono());
            stm.setString(9, cliente.getMovil());
            stm.setString(10, cliente.getFax());
            stm.setString(11, cliente.getEmail());
            stm.setFloat(12, cliente.getTotal());
            
            stm.execute();
            
            
        } catch (SQLException e) {
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                throw new ClienteAlreadyExistsException("Cliente Duplicado, ya existe en la base de datos", e);
            }
            throw new DataAccessException(e.getMessage(), e);
        }
    }
    
    public Cliente buscarPorCodigo(String codigo) throws ClienteNotFoundException, DataAccessException {
        if (codigo == null || codigo.isBlank()){
            throw new IllegalArgumentException("El nif es nulo o está vacío");
        }
        
        String sql = "SELECT nif, apellidos, nombre, domicilio, codigo_postal, localidad, telefono, movil, fax, email, total_ventas FROM clientes WHERE codigo = ?";
        Cliente c = new Cliente();
        
        try (PreparedStatement stm = conn.connectDataBase().prepareStatement(sql)){
            stm.setString(1, codigo);
            
            try (ResultSet rs = stm.executeQuery()){
               
                if (!rs.next()) {
                    throw new ClienteNotFoundException("No existe ningún cliente con ese código");
                }
                
                String nifQuery = rs.getString("nif");
                String apellidosQuery = rs.getString("apellidos");
                String nombreQuery = rs.getString("nombre");
                String domicilioQuery = rs.getString("domicilio");
                String codigo_postalQuery = rs.getString("codigo_postal");
                String localidadQuery = rs.getString("localidad");
                String telefonoQuery = rs.getString("telefono");
                String movilQuery = rs.getString("movil");
                String faxQuery = rs.getString("fax");
                String emailQuery = rs.getString("email");
                Float totalQuery = rs.getFloat("total_ventas");                
                c.setNif(nifQuery);
                c.setApellidos(apellidosQuery);
                c.setNombre(nombreQuery);
                c.setDomicilio(domicilioQuery);
                c.setCodigoPostal(codigo_postalQuery);
                c.setLocalidad(localidadQuery);
                c.setTelefono(telefonoQuery);
                c.setMovil(movilQuery);
                c.setFax(faxQuery);
                c.setEmail(emailQuery);
                c.setTotal(totalQuery);
            }
            
        } catch (SQLException e) {
            throw new DataAccessException("Ha ocurrido un error al acceder a la BdD ", e);
        }
        return c;
    }
    
    
    public void borrar(String codigo) throws DataAccessException, ClienteNotFoundException {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El codigo no puede ser nulo o estar vacio");
        }
        
        buscarPorCodigo(codigo);
        
        String sql = "DELETE FROM clientes WHERE codigo = ?";
        try (Connection connection = conn.connectDataBase();
             PreparedStatement stm = connection.prepareStatement(sql)){
            
            stm.setString(1, codigo);
            stm.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Ha ocurrido un error al acceder a la BdD ", e);
        }
    }
    
    public void actualizar(Cliente cliente) throws ClienteNotFoundException, DataAccessException {
         if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        
        buscarPorCodigo(cliente.getCodigo());
        
        String sql = "UPDATE clientes SET nif = ?, apellidos = ?, nombre = ?, domicilio = ?, codigo_postal = ?, localidad = ?, telefono = ?, movil = ?, fax = ?, email = ? WHERE codigo = ?";
        try(Connection connection = conn.connectDataBase();
            PreparedStatement stm = connection.prepareStatement(sql)){
            
            stm.setString(1, cliente.getNif());
            stm.setString(2, cliente.getApellidos());
            stm.setString(3, cliente.getNombre());
            stm.setString(4, cliente.getDomicilio());
            stm.setString(5, cliente.getCodigoPostal());
            stm.setString(6, cliente.getLocalidad());
            stm.setString(7, cliente.getTelefono());
            stm.setString(8, cliente.getMovil());
            stm.setString(9, cliente.getFax());
            stm.setString(10, cliente.getEmail());
            stm.setString(11, cliente.getCodigo());
            stm.executeUpdate();
            
        } catch (SQLException e) {
            throw new DataAccessException("Ha ocurrido un error al acceder a la BdD ", e);            
        }
    }
    
    public void incrementarTotalVentas(String codigoCliente, float incremento) throws ClienteNotFoundException, DataAccessException {

        if (codigoCliente == null || codigoCliente.isBlank()) {
            throw new IllegalArgumentException("El código del cliente no puede ser nulo o vacío");
        }

        buscarPorCodigo(codigoCliente);

        String sql = "UPDATE clientes SET total_ventas = total_ventas + ? WHERE codigo = ?";

        try (Connection connection = conn.connectDataBase();
             PreparedStatement stm = connection.prepareStatement(sql)) {

            stm.setFloat(1, incremento);
            stm.setString(2, codigoCliente);
            stm.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Ha ocurrido un error al acceder a la BdD ", e);
        }
    }
    
 
}
