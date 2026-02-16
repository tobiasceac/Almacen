/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.model;


public class Pedido {
    String codigoArticulo;
    int unidades;
    float stock;
    float precioUnidad;
    float precioCantidad;

    public Pedido() {
    }

    public Pedido(String codigoArticulo, int unidades, float stock, float precioUnidad, float precioCantidad) {
        this.codigoArticulo = codigoArticulo;
        this.unidades = unidades;
        this.stock = stock;
        this.precioUnidad = precioUnidad;
        this.precioCantidad = precioCantidad;
    }
 
    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }

    public float getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(float precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public float getPrecioCantidad() {
        return precioCantidad;
    }

    public void setPrecioCantidad(float precioCantidad) {
        this.precioCantidad = precioCantidad;
    }
    
}
