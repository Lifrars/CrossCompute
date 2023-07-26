
package Clases;

import java.io.InputStream;

public class Carrito {
    int item;
    int idProducto;
    String nombres;
    InputStream foto;
    String descripcion;
    double precioCompra;
    int cantidad;
    double subTotal;
    int stock; 
        

    public Carrito() {
    }

    public Carrito(int item, int idProducto, String nombres, InputStream foto, String descripcion, double precioCompra, int cantidad, double subTotal , int stock) {
        this.item = item;
        this.idProducto = idProducto;
        this.nombres = nombres;
        this.foto = foto;
        this.descripcion = descripcion;
        this.precioCompra = precioCompra;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
        this.stock = stock;
    }



    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public InputStream getFoto() {
        return foto;
    }

    public void setFoto(InputStream foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public void setSubTotal() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String toString() {
        return "Carrito{" + "item=" + item + ", idProducto=" + idProducto + ", nombres=" + nombres + ", foto=" + foto + ", descripcion=" + descripcion + ", precioCompra=" + precioCompra + ", cantidad=" + cantidad + ", subTotal=" + subTotal + '}';
    }

    
    
}
