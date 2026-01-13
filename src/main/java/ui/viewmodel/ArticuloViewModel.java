/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.viewmodel;

import data.dao.ArticuloDAO;
import data.model.Articulo;
import java.sql.SQLException;

/**
 *
 * @author tobias
 */
public class ArticuloViewModel {
  private ArticuloDAO articuloDAO;
   
   public ArticuloViewModel(){
        this.articuloDAO = new ArticuloDAO();
   }
   
   public void altaArticulo (
           String codigo, 
           String descripcion, 
           float stock, 
           float stockMinimo, 
           float precioCompra, 
           float precioVenta
   ) throws 
            SQLException {
       
       Articulo articulo = new Articulo(codigo, descripcion, stock, stockMinimo, precioCompra, precioVenta);
       articuloDAO.insertar(articulo);
   }
   
    public Articulo consultaPorCodigo(String codigo)throws SQLException {
       return articuloDAO.buscarPorCodigo(codigo);
   }
   
   public void bajaArticulo(String codigo) throws SQLException {
       articuloDAO.borrar(codigo);
   }
   
   public void modificarArticulo(
           String codigo, 
           String descripcion, 
           float stock, 
           float stockMinimo, 
           float precioCompra, 
           float precioVenta
   ) throws 
            SQLException{
       
       Articulo articulo = new Articulo(codigo, descripcion, stock, stockMinimo, precioCompra, precioVenta);
       articuloDAO.actualizar(articulo);
   }
   
   public Articulo consultaEntreCodigos(String codigoX, String codigoZ) throws SQLException {
       return articuloDAO.buscarEntreCodigos(codigoX, codigoZ);
    }
   
}