/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.model;

import java.util.Objects;

/**
 *
 * @author tobias
 */
public class Cliente {
    private String codigo;
    private String nif;
    private String apellidos;
    private String nombre;
    private String domicilio;
    private String codigoPostal;
    private String localidad;
    private String telefono;
    private String movil;
    private String fax;
    private String email;
    private float total;
    
    
    public Cliente(){}
    
    public Cliente(String codigo, String nif, String apellidos, String nombre, String domicilio, String codigoPostal, String localidad, String telefono, String movil, String fax, String email, float total) {
        setCodigo(codigo);
        setNif(nif);
        setApellidos(apellidos);
        setNombre(nombre);
        setDomicilio(domicilio);
        setCodigoPostal(codigoPostal);
        setLocalidad(localidad);
        setTelefono(telefono);
        setMovil(movil);
        setFax(fax);
        setEmail(email);
        setTotal(total);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código no puede ser nulo o vacío");
        }
        if (codigo. length() > 6) {
            throw new IllegalArgumentException("El código no puede tener más de 6 caracteres");
        }
        this.codigo = codigo. trim();
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        if (nif == null || nif.isBlank()) {
            throw new IllegalArgumentException("El NIF no puede ser nulo o vacío");
        }
        
        if (!nif.matches("^[0-9]{8}[A-Za-z]$")) {
            throw new IllegalArgumentException("El NIF debe tener 8 dígitos seguidos de una letra");
        }
        this.nif = nif.trim().toUpperCase();
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        if (apellidos == null || apellidos.isBlank()) {
            throw new IllegalArgumentException("Los apellidos no pueden ser nulos o vacíos");
        }
        if (apellidos.length() > 35) {
            throw new IllegalArgumentException("Los apellidos no pueden tener más de 35 caracteres");
        }
        if (!apellidos.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new IllegalArgumentException("Los apellidos solo pueden contener letras y espacios");
        }
        this.apellidos = apellidos.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        if (nombre.length() > 15) {
            throw new IllegalArgumentException("El nombre no puede tener más de 15 caracteres");
        }
        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras y espacios");
        }
        this.nombre = nombre.trim();
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        if (domicilio == null || domicilio.isBlank()) {
            throw new IllegalArgumentException("El domicilio no puede ser nulo o vacío");
        }
        if (domicilio.length() > 40) {
            throw new IllegalArgumentException("El domicilio no puede tener más de 40 caracteres");
        }
        this. domicilio = domicilio. trim();
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        if (codigoPostal == null || codigoPostal.isBlank()) {
            throw new IllegalArgumentException("El código postal no puede ser nulo o vacío");
        }
        if (codigoPostal. length() > 5) {
            throw new IllegalArgumentException("El código postal no puede tener más de 5 caracteres");
        }
        if (!codigoPostal.matches("^[0-9]{5}$")) {
            throw new IllegalArgumentException("El código postal debe tener exactamente 5 dígitos");
        }
        this.codigoPostal = codigoPostal.trim();
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        if (localidad == null || localidad.isBlank()) {
            throw new IllegalArgumentException("La localidad no puede ser nula o vacía");
        }
        if (localidad.length() > 20) {
            throw new IllegalArgumentException("La localidad no puede tener más de 20 caracteres");
        }
        this.localidad = localidad. trim();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || telefono.isBlank()) {
            this.telefono = null;
            return;
        }

        telefono = telefono.trim();

        if (!telefono.matches("\\d{9}")) {
            throw new IllegalArgumentException("El teléfono debe tener exactamente 9 dígitos");
        }

        this.telefono = telefono;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        if (movil == null || movil.isBlank()) {
            this.movil = null;
            return;
        }

        movil = movil.trim();
        
        if (!movil.matches("^[0-9]{9}$")) {
            throw new IllegalArgumentException("El móvil debe tener exactamente 9 dígitos");
        }
        this.movil = movil.trim();
    }

    public String getFax() {
        return fax;
    }

   public void setFax(String fax) {
        if (movil == null || movil.isBlank()) {
            this.movil = null;
            return;
        }

        movil = movil.trim();
    
        if (!fax.matches("\\d{9}")) {
            throw new IllegalArgumentException("El fax debe tener exactamente 9 dígitos");
        }
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (movil == null || movil.isBlank()) {
            this.movil = null;
            return;
        }

        movil = movil.trim();
        if (!email.matches("^(?=.{1,20}$)[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("El email debe ser válido y como máximo 20 caracteres");
        }
        this.email = email.trim().toLowerCase();
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        if (total < 0) {
            throw new IllegalArgumentException("El total no puede ser negativo");
        }
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        return Objects.equals(this.codigo, other.codigo);
    }

    @Override
    public String toString() {
        return "Cliente{" + "codigo=" + codigo + ", nif=" + nif + ", apellidos=" + apellidos + ", nombre=" + nombre + ", domicilio=" + domicilio + ", codigoPostal=" + codigoPostal + ", localidad=" + localidad + ", telefono=" + telefono + ", movil=" + movil + ", fax=" + fax + ", email=" + email + ", total=" + total + '}';
    }
    
    
    
}
