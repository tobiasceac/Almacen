/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.viewmodel;

import data.dao.ClienteDAO;
import data.jasper.JasperBase;
import data.model.Cliente;
import excepciones.ClienteAlreadyExistsException;
import excepciones.ClienteNotFoundException;
import excepciones.DataAccessException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author tobias
 */
public class ClienteViewModel {
  
   private final ClienteDAO clienteDAO;
   private final JasperBase jCliente;
   
   private final String rutaPorCodigo ="src/main/java/resources/jasper/ListadoPorCodigoClientes.jasper";
   private final String rutaPrintPorCodigo ="src/main/java/resources/PDF/Listados Por Codigos Clientes.pdf";

   private final String rutaEntreCodigo ="src/main/java/resources/jasper/EntreCodigosClientes.jasper";
   private final String rutaPrintEntreCodigo ="src/main/java/resources/PDF/Entre Codigos Clientes.pdf";

   private final String rutaGrafico ="src/main/java/resources/jasper/GraficoCliente.jasper";
   private final String rutaPrintGrafico ="src/main/java/resources/PDF/Grafico Cliente.pdf";
   
   
   public ClienteViewModel(){
        this.clienteDAO = new ClienteDAO();
        this.jCliente = new JasperBase();
   }
   
   /**
    * Registra un nuevo cliente en la base de datos.
    * 
    * <p>Este método crea una instancia de Cliente con los datos proporcionados
    * y la inserta en la base de datos mediante el DAO. Las validaciones de formato
    * (longitud, caracteres permitidos, etc.) deben realizarse previamente en la
    * capa de vista antes de invocar este método.</p>
    * 
    * @param codigo El código único de 6 dígitos del cliente
    * @param nif El NIF completo del cliente (8 dígitos + letra)
    * @param apellidos Los apellidos del cliente (máximo 35 caracteres)
    * @param nombre El nombre del cliente (máximo 15 caracteres)
    * @param domicilio La dirección del cliente (máximo 40 caracteres)
    * @param codigoPostal El código postal de 5 dígitos
    * @param localidad La localidad del cliente
    * @param telefono El número de teléfono fijo (9 dígitos, opcional)
    * @param movil El número de teléfono móvil (9 dígitos, opcional)
    * @param fax El número de fax (9 dígitos, opcional)
    * @param email La dirección de correo electrónico (máximo 20 caracteres, opcional)
    * @param total El total de ventas del cliente (generalmente 0 para nuevos clientes)
    * @throws ClienteAlreadyExistsException Si ya existe un cliente con el mismo código
    * @throws DataAccessException Si ocurre un error al acceder a la base de datos
    */
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
            ClienteAlreadyExistsException, DataAccessException {
       
       Cliente cliente = new Cliente(codigo, nif, apellidos, nombre, domicilio, codigoPostal, localidad, telefono, movil, fax, email, total);
       clienteDAO.insertar(cliente);
   }
   
   /**
    * Busca y retorna un cliente por su código único.
    * 
    * <p>Este método consulta la base de datos para obtener todos los datos
    * de un cliente específico identificado por su código de 6 dígitos.</p>
    * 
    * @param codigo El código de 6 dígitos del cliente a buscar
    * @return El objeto Cliente con todos sus datos
    * @throws ClienteNotFoundException Si no existe ningún cliente con el código especificado
    * @throws DataAccessException Si ocurre un error al acceder a la base de datos
    */
   public Cliente consultaPorCodigo(String codigo)throws ClienteNotFoundException, DataAccessException {
       return clienteDAO.buscarPorCodigo(codigo);
   }
   
   /**
    * Elimina un cliente de la base de datos.
    * 
    * <p>Este método borra permanentemente el registro del cliente identificado
    * por el código proporcionado. Esta operación no se puede deshacer.</p>
    * 
    * @param codigo El código de 6 dígitos del cliente a eliminar
    * @throws ClienteNotFoundException Si no existe ningún cliente con el código especificado
    * @throws DataAccessException Si ocurre un error al acceder a la base de datos
    */
   public void bajaCliente(String codigo) throws DataAccessException, ClienteNotFoundException {
       clienteDAO.borrar(codigo);
   }
   
   /**
    * Actualiza los datos de un cliente existente en la base de datos.
    * 
    * <p>Este método modifica todos los campos del cliente excepto el código
    * (que es inmutable) y el total de ventas (que se gestiona mediante otras operaciones).
    * El cliente debe existir previamente en la base de datos.</p>
    * 
    * <p><strong>Nota:</strong> Este método no modifica el total de ventas del cliente,
    * ya que ese campo se actualiza mediante operaciones de facturación.</p>
    * 
    * @param codigo El código del cliente a modificar (no se puede cambiar)
    * @param nif El nuevo NIF completo (8 dígitos + letra)
    * @param apellidos Los nuevos apellidos
    * @param nombre El nuevo nombre
    * @param domicilio La nueva dirección
    * @param codigoPostal El nuevo código postal
    * @param localidad La nueva localidad
    * @param telefono El nuevo teléfono fijo (opcional)
    * @param movil El nuevo teléfono móvil (opcional)
    * @param fax El nuevo número de fax (opcional)
    * @param email La nueva dirección de correo electrónico (opcional)
    * @throws ClienteNotFoundException Si no existe el cliente con el código especificado
    * @throws DataAccessException Si ocurre un error al acceder a la base de datos
    */
   public void modificarCliente(
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
           String email
   ) throws 
            ClienteNotFoundException, DataAccessException{
       
       Cliente cliente = new Cliente(codigo, nif, apellidos, nombre, domicilio, codigoPostal, localidad, telefono, movil, fax, email);
       clienteDAO.actualizar(cliente);
   }
   
   /**
    * Genera un reporte PDF con el listado completo de clientes ordenado por código.
    * 
    * <p>Este método crea un archivo PDF con todos los clientes de la base de datos,
    * ordenados ascendentemente por su código. El archivo se guarda en la ruta
    * especificada en {@code rutaPrintPorCodigo}.</p>
    * 
    * <p><strong>Ruta de salida:</strong> src/main/java/resources/PDF/Listados Por Codigos Clientes.pdf</p>
    * 
    * @throws JRException Si ocurre un error durante la generación del reporte JasperReports
    * @throws SQLException Si ocurre un error al consultar los datos de la base de datos
    * @throws DataAccessException Si ocurre un error al acceder a la base de datos
    */
   public void jasperClientePorCodigo() throws JRException, SQLException, DataAccessException{
       jCliente.jListadoPorCodigo(rutaPorCodigo, rutaPrintPorCodigo);
   }
   
   /**
    * Genera un reporte PDF filtrado por un rango de códigos de cliente.
    * 
    * <p>Este método crea un archivo PDF que incluye únicamente los clientes
    * cuyos códigos se encuentran dentro del rango especificado (ambos límites inclusivos).
    * Los códigos se pasan como String pero se convierten a Integer para ser procesados
    * por el reporte JasperReports.</p>
    * 
    * <p><strong>Ruta de salida:</strong> src/main/java/resources/PDF/Entre Codigos Clientes.pdf</p>
    * 
    * @param codigoUno El código inicial del rango (inclusive), como String de 6 dígitos
    * @param codigoDos El código final del rango (inclusive), como String de 6 dígitos
    * @throws JRException Si ocurre un error durante la generación del reporte JasperReports
    * @throws SQLException Si ocurre un error al consultar los datos de la base de datos
    * @throws DataAccessException Si ocurre un error al acceder a la base de datos
    */
   public void jasperClienteEntreCodigos(String codigoUno, String codigoDos) throws JRException, SQLException, DataAccessException{
       Map<String, Object> parametros = new HashMap<>();
       
       // Conversión de String a Integer para los parámetros del reporte Jasper
       int cod1 = Integer.parseInt(codigoUno);
       int cod2 = Integer.parseInt(codigoDos);
       
       parametros.put("codigo", cod1);
       parametros.put("codigo2", cod2);

       
       jCliente.jEntreCodigos(parametros, rutaEntreCodigo, rutaPrintEntreCodigo);
   }
   
   /**
    * Genera un reporte PDF con gráfico estadístico de clientes.
    * 
    * <p>Este método crea un archivo PDF que contiene representaciones gráficas
    * con estadísticas e información visual sobre los clientes (por ejemplo,
    * distribución por localidad, totales de ventas, etc.).</p>
    * 
    * <p><strong>Ruta de salida:</strong> src/main/java/resources/PDF/Grafico Cliente.pdf</p>
    * 
    * @throws JRException Si ocurre un error durante la generación del reporte JasperReports
    * @throws SQLException Si ocurre un error al consultar los datos de la base de datos
    * @throws DataAccessException Si ocurre un error al acceder a la base de datos
    */
   public void jasperClienteGrafico() throws JRException, SQLException, DataAccessException{
       jCliente.jGrafico(rutaGrafico, rutaPrintGrafico);
   }
}
