package Controlador;

import Clases.Carrito;
import Clases.Producto;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author santi
 */
public class Metodos {

    public String hola() {

        return "hola";

    }
    
    public String realizarpago(int id, double totalPagar) {
        ComprasDAO cdao = new ComprasDAO();
        if (id != 0 && totalPagar > 0) {
            cdao.Pagar(totalPagar);
            return "Pago realizado exitosamente.";
        } else {
            return "No se pudo realizar el pago.";
        }
    }
    ///Test del metodo de Calcular el total
    public static double calcularTotal(List<Carrito> listaTienda) {
        double totalPagar = 0.0;
        for (int i = 0; i < listaTienda.size(); i++) {
            totalPagar = totalPagar + listaTienda.get(i).getSubTotal();
            listaTienda.get(i).setItem(i + 1);
        }
        return totalPagar;
    }

    public List<Carrito> agregarCarrito(int idProducto, List<Carrito> listaCarrito) {
        ProductoDAO productoDAO = new ProductoDAO();
        Producto producto = productoDAO.listarId(idProducto);
        if (producto == null) {
            return listaCarrito;
        }
        int cantidad = 1;
        Carrito carrito = new Carrito();
        for (Carrito item : listaCarrito) {
            if (item.getIdProducto() == idProducto) {
                cantidad = item.getCantidad() + cantidad;
                item.setCantidad(cantidad);
                item.setSubTotal(cantidad * item.getPrecioCompra());
                return listaCarrito;
            }
        }
        carrito.setIdProducto(producto.getId());
        carrito.setNombres(producto.getNombres());
        carrito.setDescripcion(producto.getDescripcion());
        carrito.setPrecioCompra(producto.getPrecio());
        carrito.setCantidad(cantidad);
        carrito.setStock(producto.getStock());
        carrito.setSubTotal(cantidad * producto.getPrecio());
        listaCarrito.add(carrito);
        return listaCarrito;
    }
    //Test de agregar al carrito
}
