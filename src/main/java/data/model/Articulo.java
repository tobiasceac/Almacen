/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.model;

/**
 *
 * @author tobias
 */
public class Articulo {
    private String codigo;
    private String descripcion;
    private Float stockMinimo;
    private Float precioCompra;
    private Float precioVenta;

    public Articulo(String codigo, String descripcion, Float stockMinimo, Float precioCompra, Float precioVenta) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.stockMinimo = stockMinimo;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Float stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Float getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Float precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Float precioVenta) {
        this.precioVenta = precioVenta;
    }
    
    
          
}
