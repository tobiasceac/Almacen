/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.dao;

import data.database.ConexionDB;
import data.model.Articulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author tobias
 */
public class ArticuloDAO {
    
    private ConexionDB conn;
    
    public ArticuloDAO(){
        this.conn = new ConexionDB();
    }
    
    public void insertar(Articulo articulo) throws SQLException {
        if (articulo == null) {
            throw new IllegalArgumentException("El articulo no puede ser nulo");
        }
        
        String sql = "INSERT INTO articulos(codigo, descripcion, stock, stock_minimo, precio_compra, precio_venta) VALUES(?, ?, ?, ?, ?, ?, ?)";
        
        try(Connection connection = conn.connectDataBase();
            PreparedStatement stm = connection.prepareStatement(sql)){
            
            stm.setString(1, articulo.getCodigo());
            stm.setString(2, articulo.getDescripcion());
            stm.setFloat(3, articulo.getStock());
            stm.setFloat(4, articulo.getStockMinimo());
            stm.setFloat(5, articulo.getPrecioCompra());
            stm.setFloat(6, articulo.getPrecioVenta());
        
            
            stm.execute();
            
            
        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23")) {
                throw new IllegalStateException("Artiuclo Duplicado, ya existe en la base de datos", e);
            }
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
    
    public Articulo buscarPorCodigo(String codigo) throws SQLException {
        if (codigo == null || codigo.isBlank()){
            throw new IllegalArgumentException("El nif es nulo o está vacío");
        }
        
        String sql = "SELECT descripcion, stock, stock_minimo, precio_compra, precio_venta FROM articulos WHERE codigo = ?";
        Articulo articulo = new Articulo();
        
        try (PreparedStatement stm = conn.connectDataBase().prepareStatement(sql)){
            stm.setString(1, codigo);
            
            try (ResultSet rs = stm.executeQuery()){
               
                if (!rs.next()) {
                    throw new IllegalStateException("No existe ningún articulo con ese código");
                }
                
                String descripcionQuery = rs.getString("descripcion");
                Float stockQuery = rs.getFloat("stock");
                Float stockMinimoQuery = rs.getFloat("stock_minimo");
                Float precioMinimoQuery = rs.getFloat("precio_compra");
                Float precioMaximoQuery = rs.getFloat("precio_venta");               
                articulo.setDescripcion(descripcionQuery);
                articulo.setStock(stockQuery);
                articulo.setStockMinimo(stockMinimoQuery);
                articulo.setPrecioCompra(precioMinimoQuery);
                articulo.setPrecioVenta(precioMaximoQuery);
               
            }
            
        } catch (SQLException e) {
            throw new IllegalStateException("Ha ocurrido un error al acceder a la BdD ", e);
        }
        return articulo;
    }
    
    
    public void borrar(String codigo) throws SQLException{
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El codigo no puede ser nulo o estar vacio");
        }
        
        buscarPorCodigo(codigo);
        
        String sql = "DELETE FROM articulos WHERE codigo = ?";
        try (Connection connection = conn.connectDataBase();
             PreparedStatement stm = connection.prepareStatement(sql)){
            
            stm.setString(1, codigo);
            stm.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException("Ha ocurrido un error al acceder a la BdD ", e);
        }
    }
    
    public void actualizar(Articulo articulo) throws SQLException {
         if (articulo == null) {
            throw new IllegalArgumentException("El articulo no puede ser nulo");
        }
        
        buscarPorCodigo(articulo.getCodigo());
        
        String sql = "UPDATE clientes SET descripcion = ?, stock = ?, stock_minimo = ?, precio_compra = ?, precio_venta = ? WHERE codigo = ?";
        try(Connection connection = conn.connectDataBase();
            PreparedStatement stm = connection.prepareStatement(sql)){
            
            stm.setString(1, articulo.getDescripcion());
            stm.setFloat(2, articulo.getStock());
            stm.setFloat(3, articulo.getStockMinimo());
            stm.setFloat(4, articulo.getPrecioCompra());
            stm.setFloat(5, articulo.getPrecioVenta());

            stm.executeUpdate();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(), e);
        }
    }
    
    public Articulo buscarEntreCodigos(String codigoX, String codigoZ) throws SQLException {
        if (codigoX == null || codigoX.isBlank() || codigoZ == null || codigoZ.isBlank()){
            throw new IllegalArgumentException("El nif es nulo o está vacío");
        }
        
        String sql = "SELECT descripcion, stock, stock_minimo, precio_compra, precio_venta FROM articulos BETWEEN codigo = ? AND codigo = ?";
        Articulo articulo = new Articulo();
        
        try (PreparedStatement stm = conn.connectDataBase().prepareStatement(sql)){
            stm.setString(1, codigoX);
            stm.setString(2, codigoZ);
            
            try (ResultSet rs = stm.executeQuery()){
               
                if (!rs.next()) {
                    throw new IllegalStateException("No existe ningún articulo con ese código");
                }
                
                String descripcionQuery = rs.getString("descripcion");
                Float stockQuery = rs.getFloat("stock");
                Float stockMinimoQuery = rs.getFloat("stock_minimo");
                Float precioMinimoQuery = rs.getFloat("precio_compra");
                Float precioMaximoQuery = rs.getFloat("precio_venta");               
                articulo.setDescripcion(descripcionQuery);
                articulo.setStock(stockQuery);
                articulo.setStockMinimo(stockMinimoQuery);
                articulo.setPrecioCompra(precioMinimoQuery);
                articulo.setPrecioVenta(precioMaximoQuery);
            }
            
        } catch (SQLException e) {
            throw new IllegalStateException("Ha ocurrido un error al acceder a la BdD ", e);
        }
        return articulo;
    } 
}
