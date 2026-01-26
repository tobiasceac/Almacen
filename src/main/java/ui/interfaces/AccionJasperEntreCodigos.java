/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.interfaces;

import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;

/**
 * Interfaz funcional que define una acción para generar reportes JasperReports
 * filtrados por un rango de códigos.
 * 
 * <p>Esta interfaz implementa el patrón Strategy/Command, permitiendo que diferentes
 * implementaciones definan cómo generar reportes (clientes, artículos, proveedores, etc.)
 * sin que el formulario {@link ui.screens.EntreCodigos} necesite conocer los detalles
 * específicos de cada tipo de reporte.</p>
 * 
 * <p>Ejemplo de uso con expresión lambda:</p>
 * <pre>
 * AccionJasperEntreCodigos accion = (codigo1, codigo2) -> {
 *     viewModel.jasperClienteEntreCodigos(codigo1, codigo2);
 * };
 * </pre>
 * 
 * @author tobias
 * @see ui.screens.EntreCodigos
 */
public interface AccionJasperEntreCodigos {
    
    /**
     * Ejecuta la generación de un reporte JasperReports filtrado por un rango de códigos.
     * 
     * <p>Este método es invocado cuando el usuario confirma los códigos inicial y final
     * en el formulario {@link ui.screens.EntreCodigos}. La implementación concreta debe
     * generar el reporte correspondiente (PDF u otro formato) basándose en el rango
     * de códigos proporcionado.</p>
     * 
     * @param codigoUno El código inicial del rango (inclusive). Debe ser un código válido
     *                  de 6 dígitos numéricos.
     * @param codigoDos El código final del rango (inclusive). Debe ser un código válido
     *                  de 6 dígitos numéricos y mayor o igual que codigoUno.
     * @throws JRException Si ocurre un error durante la generación del reporte JasperReports,
     *                     como problemas de compilación del archivo .jasper o errores
     *                     en el procesamiento de datos.
     * @throws SQLException Si ocurre un error al acceder a la base de datos para obtener
     *                      los registros dentro del rango especificado.
     */
    void ejecutar(String codigoUno, String codigoDos) throws JRException, SQLException;
}
