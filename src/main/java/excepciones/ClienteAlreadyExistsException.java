/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author tobias
 */
public class ClienteAlreadyExistsException extends Exception{
    public ClienteAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClienteAlreadyExistsException(String message) {
        super(message);
    }
}
