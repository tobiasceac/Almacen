/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.interfaces;

import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author tobias
 */
public interface AccionJasperEntreCodigos {
    void ejecutar(String codigoUno, String codigoDos) throws JRException, SQLException;
}
