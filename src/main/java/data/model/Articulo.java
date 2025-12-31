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
public class Articulo {
    private String codigo;
    private String descripcion;
    private float stock;
    private float stockMinimo;
    private float precioCompra;
    private float precioVenta;

    public Articulo() {
    }

    public Articulo(String codigo, String descripcion, float stock, float stockMinimo, float precioCompra, float precioVenta) {
        setCodigo(codigo);
        setDescripcion(descripcion);
        setStock(stock);
        setStockMinimo(stockMinimo);
        setPrecioCompra(precioCompra);
        setPrecioVenta(precioVenta);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código no puede ser nulo o vacío");
        }
        codigo = codigo.trim();
        if (codigo.length() > 6) {
            throw new IllegalArgumentException("El código no puede tener más de 6 caracteres");
        }
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía");
        }
        descripcion = descripcion.trim();
        if (descripcion.length() > 25) {
            throw new IllegalArgumentException("La descripción no puede tener más de 25 caracteres");
        }
        this.descripcion = descripcion;
    }

    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        this. stock = stock;
    }

    public float getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(float stockMinimo) {
        if (stockMinimo < 0) {
            throw new IllegalArgumentException("El stock mínimo no puede ser negativo");
        }
        this.stockMinimo = stockMinimo;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(float precioCompra) {
        if (precioCompra < 0) {
            throw new IllegalArgumentException("El precio de compra no puede ser negativo");
        }
        this.precioCompra = precioCompra;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        if (precioVenta < 0) {
            throw new IllegalArgumentException("El precio de venta no puede ser negativo");
        }
        this.precioVenta = precioVenta;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects. hashCode(this.codigo);
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
        final Articulo other = (Articulo) obj;
        return Objects.equals(this.codigo, other.codigo);
    }
}