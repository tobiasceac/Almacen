/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.viewmodel;

import data.dao.ArticuloDAO;
import data.jasper.JasperBase;
import data.model.Articulo;
import excepciones.ArticuloAlreadyExistsException;
import excepciones.ArticuloNotFoundException;
import excepciones.DataAccessException;
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
   private final JasperBase jArticulo;

   private final String rutaPorCodigo ="src/main/java/resources/jasper/ListadoPorCodigoArticulos.jasper";
   private final String rutaPrintPorCodigo ="src/main/java/resources/PDF/Listados Por Codigos Articulos.pdf";

   private final String rutaEntreCodigo ="src/main/java/resources/jasper/EntreCodigosArticulos.jasper";
   private final String rutaPrintEntreCodigo ="src/main/java/resources/PDF/Entre Codigos Articulos.pdf";

   private final String rutaGrafico ="src/main/java/resources/jasper/GraficoArticulo.jasper";
   private final String rutaPrintGrafico ="src/main/java/resources/PDF/Grafico Articulo.pdf";
   
   private final String rutaFactura = "src/main/java/resources/jasper/FacturaCliente.jasper" ;
   private final String rutaPrintFactura = "src/main/java/resources/PDF/Factura Cliente.pdf" ;

   public ArticuloViewModel(){
        this.articuloDAO = new ArticuloDAO();
        this.jArticulo = new JasperBase();

   }
   
   public void altaArticulo (
           String codigo, 
           String descripcion, 
           float stock, 
           float stockMinimo, 
           float precioCompra, 
           float precioVenta
   ) throws 
            ArticuloAlreadyExistsException, DataAccessException {
       
       Articulo articulo = new Articulo(codigo, descripcion, stock, stockMinimo, precioCompra, precioVenta);
       articuloDAO.insertar(articulo);
   }
   
    public Articulo consultaPorCodigo(String codigo)throws ArticuloNotFoundException, DataAccessException {
       return articuloDAO.buscarPorCodigo(codigo);
   }
   
   public void bajaArticulo(String codigo) throws ArticuloNotFoundException, ArticuloNotFoundException, DataAccessException {
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
            DataAccessException, ArticuloNotFoundException{
       
       Articulo articulo = new Articulo(codigo, descripcion, stock, stockMinimo, precioCompra, precioVenta);
       articuloDAO.actualizar(articulo);
   }
   
   
   public void jasperArticuloPorCodigo() throws JRException, SQLException, DataAccessException{
       jArticulo.jListadoPorCodigo(rutaPorCodigo, rutaPrintPorCodigo);
   }
   
   public void jasperArticuloEntreCodigos(String codigoUno, String codigoDos) throws JRException, SQLException, DataAccessException{
       Map<String, Object> parametros = new HashMap<>();
       
       int cod1 = Integer.parseInt(codigoUno);
       int cod2 = Integer.parseInt(codigoDos);
       
       parametros.put("codigo", cod1);
       parametros.put("codigo2", cod2);

       
       jArticulo.jEntreCodigos(parametros, rutaEntreCodigo, rutaPrintEntreCodigo);
   }
   
   public void jasperArticuloGrafico() throws JRException, SQLException, DataAccessException{
       jArticulo.jGrafico(rutaGrafico, rutaPrintGrafico);
   }  
   
      public void jasperFactura(String codigoUno, int codigoDos) throws JRException, SQLException, DataAccessException{
       Map<String, Object> parametros = new HashMap<>();
       
       int cod1 = Integer.parseInt(codigoUno);
       
       parametros.put("CODIGO", cod1);
       parametros.put("NUM_FACTURA", codigoDos);

       
       jArticulo.jEntreCodigos(parametros, rutaEntreCodigo, rutaPrintEntreCodigo);
   }
   
}