/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.viewmodel;

import data.dao.ArticuloDAO;
import data.jasper.JasperArticulo;
import data.model.Articulo;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author tobias
 */
public class ArticuloViewModel {
   private final ArticuloDAO articuloDAO;
   private final JasperArticulo jArticulo;

   
   public ArticuloViewModel(){
        this.articuloDAO = new ArticuloDAO();
        this.jArticulo = new JasperArticulo();

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
   
   public void jasperArticuloPorCodigo() throws JRException, SQLException{
       jArticulo.jListadoPorCodigo();
   }
   
   public void jasperArticuloEntreCodigos(String codigoUno, String codigoDos) throws JRException, SQLException{
       Map<String, Object> parametros = new HashMap<>();
       
       int cod1 = Integer.parseInt(codigoUno);
       int cod2 = Integer.parseInt(codigoDos);
       
       parametros.put("codigo", cod1);
       parametros.put("codigo2", cod2);

       
       jArticulo.jEntreCodigos(parametros);
   }
   
}