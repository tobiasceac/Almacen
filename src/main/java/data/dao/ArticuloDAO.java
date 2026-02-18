/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.dao;

import data.database.ConexionDB;
import data.model.Articulo;
import excepciones.ArticuloAlreadyExistsException;
import excepciones.ArticuloNotFoundException;
import excepciones.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author tobias
 */
public class ArticuloDAO {
    
    private ConexionDB conn;
    
    public ArticuloDAO(){
        this.conn = new ConexionDB();
    }
    
    public void insertar(Articulo articulo) throws ArticuloAlreadyExistsException, DataAccessException {
        if (articulo == null) {
            throw new IllegalArgumentException("El articulo no puede ser nulo");
        }
        
        String sql = "INSERT INTO articulos(codigo, descripcion, stock, stock_minimo, precio_compra, precio_venta) VALUES(?, ?, ?, ?, ?, ?)";
        
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
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                throw new ArticuloAlreadyExistsException("Artiuclo Duplicado, ya existe en la Base de datos", e);
            }
            throw new DataAccessException("Error al insertar artículo en la Base de datos", e);
        }
    }
    
    public Articulo buscarPorCodigo(String codigo) throws ArticuloNotFoundException, DataAccessException {
        if (codigo == null || codigo.isBlank()){
            throw new IllegalArgumentException("El nif es nulo o está vacío");
        }
        
        String sql = "SELECT descripcion, stock, stock_minimo, precio_compra, precio_venta FROM articulos WHERE codigo = ?";
        Articulo articulo = new Articulo();
        
        try (PreparedStatement stm = conn.connectDataBase().prepareStatement(sql)){
            stm.setString(1, codigo);
            
            try (ResultSet rs = stm.executeQuery()){
               
                if (!rs.next()) {
                    throw new ArticuloNotFoundException("No existe ningún articulo con ese código");
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
            throw new DataAccessException("Ha ocurrido un error al acceder a la BdD ", e);
        }
        return articulo;
    }
    
    
    public void borrar(String codigo) throws ArticuloNotFoundException, DataAccessException{
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
            throw new DataAccessException("Ha ocurrido un error al acceder a la BdD ", e);
        }
    }
    
    public void actualizar(Articulo articulo) throws DataAccessException, ArticuloNotFoundException {
         if (articulo == null) {
            throw new IllegalArgumentException("El articulo no puede ser nulo");
        }
        
        buscarPorCodigo(articulo.getCodigo());
        
        String sql = "UPDATE articulos SET descripcion = ?, stock = ?, stock_minimo = ?, precio_compra = ?, precio_venta = ? WHERE codigo = ?";
        try(Connection connection = conn.connectDataBase();
            PreparedStatement stm = connection.prepareStatement(sql)){
            
            stm.setString(1, articulo.getDescripcion());
            stm.setFloat(2, articulo.getStock());
            stm.setFloat(3, articulo.getStockMinimo());
            stm.setFloat(4, articulo.getPrecioCompra());
            stm.setFloat(5, articulo.getPrecioVenta());
            stm.setString(6, articulo.getCodigo());

            stm.executeUpdate();
            
        } catch (SQLException e) {
            throw new DataAccessException("Ha ocurrido un error al acceder a la BdD ", e);

        }
    }
    
    public void descontarStock(String codigoArticulo, float unidades) throws ArticuloNotFoundException, DataAccessException {

        if (codigoArticulo == null || codigoArticulo.isBlank()) {
            throw new IllegalArgumentException("El código del artículo no puede ser nulo o vacío");
        }
        if (unidades <= 0) {
            throw new IllegalArgumentException("Las unidades deben ser mayores que 0");
        }

        buscarPorCodigo(codigoArticulo);

        String sql = "UPDATE articulos SET stock = stock - ? WHERE codigo = ?";

        try (Connection conexion = conn.connectDataBase();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setFloat(1, unidades);
            stm.setString(2, codigoArticulo);
            stm.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Ha ocurrido un error al acceder a la BdD ", e);
        }
    }
    
    public void sumarStock(String codigoArticulo, float unidades) throws ArticuloNotFoundException, DataAccessException {

        if (codigoArticulo == null || codigoArticulo.isBlank()) {
            throw new IllegalArgumentException("El código del artículo no puede ser nulo o vacío");
        }
        if (unidades <= 0) {
            throw new IllegalArgumentException("Las unidades deben ser mayores que 0");
        }

        buscarPorCodigo(codigoArticulo);

        String sql = "UPDATE articulos SET stock = stock + ? WHERE codigo = ?";

        try (Connection conexion = conn.connectDataBase();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setFloat(1, unidades);
            stm.setString(2, codigoArticulo);
            stm.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Ha ocurrido un error al acceder a la BdD ", e);
        }
    }
    
    public void insertarPedidoCliente(String codigoCliente, String codigoArticulo, float unidades) throws DataAccessException {

        if (codigoCliente == null || codigoCliente.isBlank()) {
            throw new IllegalArgumentException("El código del cliente no puede ser nulo o vacío");
        }
        if (codigoArticulo == null || codigoArticulo.isBlank()) {
            throw new IllegalArgumentException("El código del artículo no puede ser nulo o vacío");
        }
        if (unidades <= 0) {
            throw new IllegalArgumentException("Las unidades deben ser mayores que 0");
        }

        String sql = "INSERT INTO registros (codigo_cliente, codigo_proveedor, codigo_articulo, unidades, fecha_pedido) "
                   + "VALUES (?,?,?,?,?)";

        try (Connection conexion = conn.connectDataBase();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setString(1, codigoCliente); // Cliente
            stm.setNull(2, java.sql.Types.VARCHAR); // Proveedor
            stm.setString(3, codigoArticulo);
            stm.setFloat(4, unidades);
            stm.setDate(5, java.sql.Date.valueOf(LocalDate.now()));

            stm.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Ha ocurrido un error al acceder a la BdD ", e);
        }
    }
    
    
    public void insertarPedidoProveedor(String codigoProovedor, String codigoArticulo, float unidades) throws DataAccessException {

        if (codigoProovedor == null || codigoProovedor.isBlank()) {
            throw new IllegalArgumentException("El código del cliente no puede ser nulo o vacío");
        }
        if (codigoArticulo == null || codigoArticulo.isBlank()) {
            throw new IllegalArgumentException("El código del artículo no puede ser nulo o vacío");
        }
        if (unidades <= 0) {
            throw new IllegalArgumentException("Las unidades deben ser mayores que 0");
        }

        String sql = "INSERT INTO registros (codigo_cliente, codigo_proveedor, codigo_articulo, unidades, fecha_pedido) "
                   + "VALUES (?,?,?,?,?)";

        try (Connection conexion = conn.connectDataBase();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setNull(1, java.sql.Types.VARCHAR); // Cliente
            stm.setString(2, codigoProovedor); // Proveedor
            stm.setString(3, codigoArticulo);
            stm.setFloat(4, unidades);
            stm.setDate(5, java.sql.Date.valueOf(LocalDate.now()));

            stm.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Ha ocurrido un error al acceder a la BdD ", e);
        }
    }
    
}
