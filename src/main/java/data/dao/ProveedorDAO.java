/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.dao;

import data.database.ConexionDB;
import data.model.Proveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author tobias
 */
public class ProveedorDAO {
    private ConexionDB conn;
    
    public ProveedorDAO(){
        this.conn = new ConexionDB();
    }
    
    public void insertar(Proveedor proveedor) throws SQLException {
        if (proveedor == null) {
            throw new IllegalArgumentException("El proveedor no puede ser nulo");
        }
        
        String sql = "INSERT INTO proveedores(codigo, nif, apellidos, nombre, domicilio, codigo_postal, localidad, telefono, movil, fax, email, total_compras) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try(Connection connection = conn.connectDataBase();
            PreparedStatement stm = connection.prepareStatement(sql)){
            
            stm.setString(1, proveedor.getCodigo());
            stm.setString(2, proveedor.getNif());
            stm.setString(3, proveedor.getApellidos());
            stm.setString(4, proveedor.getNombre());
            stm.setString(5, proveedor.getDomicilio());
            stm.setString(6, proveedor.getCodigoPostal());
            stm.setString(7, proveedor.getLocalidad());
            stm.setString(8, proveedor.getTelefono());
            stm.setString(9, proveedor.getMovil());
            stm.setString(10, proveedor.getFax());
            stm.setString(11, proveedor.getEmail());
            stm.setFloat(12, proveedor.getTotal());
            
            stm.execute();
            
            
        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23")) {
                throw new IllegalStateException("Proveedor Duplicado, ya existe en la base de datos", e);
            }
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
    
    public Proveedor buscarPorCodigo(String codigo) throws SQLException {
        if (codigo == null || codigo.isBlank()){
            throw new IllegalArgumentException("El nif es nulo o está vacío");
        }
        
        String sql = "SELECT nif, apellidos, nombre, domicilio, codigo_postal, localidad, telefono, movil, fax, email, total_compras FROM proveedores WHERE codigo = ?";
        Proveedor proveedor = new Proveedor();
        
        try (PreparedStatement stm = conn.connectDataBase().prepareStatement(sql)){
            stm.setString(1, codigo);
            
            try (ResultSet rs = stm.executeQuery()){
               
                if (!rs.next()) {
                    throw new IllegalStateException("No existe ningún proveedor con ese código");
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
                Float totalQuery = rs.getFloat("total_compras");                
                proveedor.setNif(nifQuery);
                proveedor.setApellidos(apellidosQuery);
                proveedor.setNombre(nombreQuery);
                proveedor.setDomicilio(domicilioQuery);
                proveedor.setCodigoPostal(codigo_postalQuery);
                proveedor.setLocalidad(localidadQuery);
                proveedor.setTelefono(telefonoQuery);
                proveedor.setMovil(movilQuery);
                proveedor.setFax(faxQuery);
                proveedor.setEmail(emailQuery);
                proveedor.setTotal(totalQuery);
            }
            
        } catch (SQLException e) {
            throw new IllegalStateException("Ha ocurrido un error al acceder a la BdD ", e);
        }
        return proveedor;
    }
    
    
    public void borrar(String codigo) throws SQLException{
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El codigo no puede ser nulo o estar vacio");
        }
        
        buscarPorCodigo(codigo);
        
        String sql = "DELETE FROM proveedores WHERE codigo = ?";
        try (Connection connection = conn.connectDataBase();
             PreparedStatement stm = connection.prepareStatement(sql)){
            
            stm.setString(1, codigo);
            stm.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException("Ha ocurrido un error al acceder a la BdD ", e);
        }
    }
    
    public void actualizar(Proveedor proveedor) throws SQLException {
         if (proveedor == null) {
            throw new IllegalArgumentException("El proveedor no puede ser nulo");
        }
        
        buscarPorCodigo(proveedor.getCodigo());
        
        String sql = "UPDATE proveedores SET nif = ?, apellidos = ?, nombre = ?, domicilio = ?, codigo_postal = ?, localidad = ?, telefono = ?, movil = ?, fax = ?, email = ? WHERE codigo = ?";
        try(Connection connection = conn.connectDataBase();
            PreparedStatement stm = connection.prepareStatement(sql)){
            
            stm.setString(1, proveedor.getNif());
            stm.setString(2, proveedor.getApellidos());
            stm.setString(3, proveedor.getNombre());
            stm.setString(4, proveedor.getDomicilio());
            stm.setString(5, proveedor.getCodigoPostal());
            stm.setString(6, proveedor.getLocalidad());
            stm.setString(7, proveedor.getTelefono());
            stm.setString(8, proveedor.getMovil());
            stm.setString(9, proveedor.getFax());
            stm.setString(10, proveedor.getEmail());
            stm.setString(11, proveedor.getCodigo());
            stm.executeUpdate();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(), e);
        }
    }
    
    public Proveedor buscarEntreCodigos(String codigoX, String codigoZ) throws SQLException {
        if (codigoX == null || codigoX.isBlank() || codigoZ == null || codigoZ.isBlank()){
            throw new IllegalArgumentException("El nif es nulo o está vacío");
        }
        
        String sql = "SELECT nif, apellidos, nombre, domicilio, codigo_postal, localidad, telefono, movil, fax, email, total_compras FROM proveedores BETWEEN codigo = ? AND codigo = ?";
        Proveedor proveedor = new Proveedor();
        
        try (PreparedStatement stm = conn.connectDataBase().prepareStatement(sql)){
            stm.setString(1, codigoX);
            stm.setString(2, codigoZ);
            
            try (ResultSet rs = stm.executeQuery()){
               
                if (!rs.next()) {
                    throw new IllegalStateException("No existe ningún proveedor con ese código");
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
                Float totalQuery = rs.getFloat("total_compras");                
                proveedor.setNif(nifQuery);
                proveedor.setApellidos(apellidosQuery);
                proveedor.setNombre(nombreQuery);
                proveedor.setDomicilio(domicilioQuery);
                proveedor.setCodigoPostal(codigo_postalQuery);
                proveedor.setLocalidad(localidadQuery);
                proveedor.setTelefono(telefonoQuery);
                proveedor.setMovil(movilQuery);
                proveedor.setFax(faxQuery);
                proveedor.setEmail(emailQuery);
                proveedor.setTotal(totalQuery);
            }
            
        } catch (SQLException e) {
            throw new IllegalStateException("Ha ocurrido un error al acceder a la BdD ", e);
        }
        return proveedor;
    } 
}

