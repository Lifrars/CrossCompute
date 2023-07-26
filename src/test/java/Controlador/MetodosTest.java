package Controlador;

import Clases.Carrito;
import Clases.Cliente;
import Clases.Compra;
import Clases.DetalleCompra;
import Clases.Producto;
import ConexionBD.Conexion;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MetodosTest {

    @Test
    public void obtenerParte1Unidades() {
        Metodos m = new Metodos();
        String ecuacion1 = m.hola();

        assertEquals("hola", ecuacion1);
    }

    @Test
    public void testCalcularTotal() {
        //Arrange
        Metodos m = new Metodos();
        List<Carrito> listaTienda = new ArrayList<>();
        Carrito carrito = new Carrito();
        carrito.setItem(1);
        carrito.setPrecioCompra(10.00);
        carrito.setCantidad(2);
        carrito.setSubTotal(20.0);
        listaTienda.add(carrito);

        //Act
        double resultadoEsperado = 20.00;
        double resultadoObtenido = m.calcularTotal(listaTienda);

        //Assert
        assertEquals(resultadoEsperado, resultadoObtenido, 0.01);
    }

    @Test
    public void testCalcularTotal_MultiplesElementos() {
        //Arrange
        Metodos m = new Metodos();
        List<Carrito> listaTienda = new ArrayList<>();
        Carrito carrito1 = new Carrito();
        carrito1.setItem(1);
        carrito1.setPrecioCompra(10.00);
        carrito1.setCantidad(2);
        carrito1.setSubTotal(15.0);
        listaTienda.add(carrito1);
        //Act
        Carrito carrito2 = new Carrito();
        carrito2.setItem(2);
        carrito2.setPrecioCompra(5.00);
        carrito2.setCantidad(3);
        carrito2.setSubTotal(20.0);
        listaTienda.add(carrito2);

        double resultadoEsperado = 35.00;
        double resultadoObtenido = m.calcularTotal(listaTienda);

        assertEquals(resultadoEsperado, resultadoObtenido, 0.01);
    }

    @Test
    public void testCalcularTotal_ListaVacia() {
        Metodos m = new Metodos();
        List<Carrito> listaTienda = new ArrayList<>();

        double resultadoEsperado = 0.00;
        double resultadoObtenido = m.calcularTotal(listaTienda);

        assertEquals(resultadoEsperado, resultadoObtenido, 0.01);
    }

    @Test
    public void testAgregarCarrito_ProductoNuevo() {
        int idProducto = 1;
        List<Carrito> listaCarrito = new ArrayList<>();
        Metodos m = new Metodos();

        Producto producto = new Producto();
        producto.setId(idProducto);
        producto.setNombres("Producto 1");
        producto.setDescripcion("Descripción del producto 1");
        producto.setPrecio(10.00);

        ProductoDAO productoDAO = Mockito.mock(ProductoDAO.class);
        Mockito.when(productoDAO.listarId(idProducto)).thenReturn(producto);

        List<Carrito> resultadoEsperado = new ArrayList<>();
        Carrito carritoEsperado = new Carrito();
        carritoEsperado.setIdProducto(producto.getId());
        carritoEsperado.setNombres(producto.getNombres());
        carritoEsperado.setDescripcion(producto.getDescripcion());
        carritoEsperado.setPrecioCompra(producto.getPrecio());
        carritoEsperado.setCantidad(1);
        carritoEsperado.setSubTotal(producto.getPrecio());
        resultadoEsperado.add(carritoEsperado);

        List<Carrito> resultadoObtenido = m.agregarCarrito(idProducto, listaCarrito);

        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void cuandoAgregarCarritoConProductoExistente_entoncesAumentaCantidad() {
        // Arrange
        Metodos m = new Metodos();
        Carrito carrito = new Carrito();
        carrito.setIdProducto(1);
        carrito.setNombres("Producto de prueba");
        carrito.setDescripcion("Descripción de prueba");
        carrito.setPrecioCompra(10.0);
        carrito.setCantidad(1);
        carrito.setSubTotal(10.0);

        List<Carrito> listaCarrito = new ArrayList<>();
        listaCarrito.add(carrito);

        int idProducto = 1;

        Carrito carritoEsperado = new Carrito();
        carritoEsperado.setIdProducto(1);
        carritoEsperado.setNombres("Producto de prueba");
        carritoEsperado.setDescripcion("Descripción de prueba");
        carritoEsperado.setPrecioCompra(10.0);
        carritoEsperado.setCantidad(2);
        carritoEsperado.setSubTotal(20.0);

        // Act
        Carrito carritoObtenido = new Carrito();
        List<Carrito> listaCarritoActualizada = m.agregarCarrito(idProducto, listaCarrito);

        // Assert
        assertEquals(1, listaCarritoActualizada.size());
        Carrito carritoActualizado = listaCarritoActualizada.get(0);
        assertEquals(carritoEsperado.getIdProducto(), carritoActualizado.getIdProducto());
        assertEquals(carritoEsperado.getNombres(), carritoActualizado.getNombres());
        assertEquals(carritoEsperado.getDescripcion(), carritoActualizado.getDescripcion());
        assertEquals(carritoEsperado.getPrecioCompra(), carritoActualizado.getPrecioCompra(), 0.01);
        assertEquals(carritoEsperado.getCantidad(), carritoActualizado.getCantidad());
        assertEquals(carritoEsperado.getSubTotal(), carritoActualizado.getSubTotal(), 0.01);
    }

    @Test
    public void testDesactivarProducto() throws Exception {
        int idCliente = 1;
        Connection con = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ProductoDAO cldao = new ProductoDAO();
        cldao.cn = Mockito.mock(Conexion.class);
        Mockito.when(cldao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);

        // Act
        cldao.DesactivarProducto(idCliente);

        // Assert
        Mockito.verify(ps, Mockito.times(1)).executeUpdate();
    }

    @Test
    public void testActivarProducto() throws Exception {
        int id = 1;
        Connection con = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ProductoDAO cldao = new ProductoDAO();
        cldao.cn = Mockito.mock(Conexion.class);
        Mockito.when(cldao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);

        // Act
        cldao.ActivarProducto(id);

        // Assert
        Mockito.verify(ps, Mockito.times(1)).executeUpdate();
    }

    @Test
    public void testActivarProductoInexistente() throws Exception {
        int id = 800;
        Connection con = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ProductoDAO cldao = new ProductoDAO();
        cldao.cn = Mockito.mock(Conexion.class);
        Mockito.when(cldao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);

        // Act
        cldao.ActivarProducto(id);
        // Assert

        Mockito.verify(ps, Mockito.times(1)).executeUpdate();
    }

    public void testCalcularTotal_MultiplesElementos2() {
        Metodos m = new Metodos();
        List<Carrito> listaTienda = new ArrayList<>();
        Carrito carrito1 = new Carrito();
        carrito1.setItem(1);
        carrito1.setPrecioCompra(10.00);
        carrito1.setCantidad(2);
        carrito1.setSubTotal(18.0);
        listaTienda.add(carrito1);

        Carrito carrito2 = new Carrito();
        carrito2.setItem(2);
        carrito2.setPrecioCompra(5.00);
        carrito2.setCantidad(3);
        carrito2.setSubTotal(20.0);
        listaTienda.add(carrito2);

        double resultadoEsperado = 35.00;
        double resultadoObtenido = m.calcularTotal(listaTienda);

        assertEquals(resultadoEsperado, resultadoObtenido, 0.01);
    }

    @Test
    public void testCalcularTotalEr() {
        Metodos m = new Metodos();

        List<Carrito> listaTienda = new ArrayList<>();
        Carrito carrito = new Carrito();
        carrito.setItem(1);
        carrito.setPrecioCompra(10.00);
        carrito.setCantidad(2);
        carrito.setSubTotal(21.0);
        listaTienda.add(carrito);

        double resultadoEsperado = 20.00;
        double resultadoObtenido = m.calcularTotal(listaTienda);

        assertEquals(resultadoEsperado, resultadoObtenido, 0.01);
    }

    @Test
    public void testEliminarProducto() throws Exception {
        // Arrange
        int id = 1;
        Connection con = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ProductoDAO cldao = new ProductoDAO();
        cldao.cn = Mockito.mock(Conexion.class);
        Mockito.when(cldao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);

        // Act
        cldao.EliminarProducto(id);

        // Assert
        Mockito.verify(ps, Mockito.times(1)).executeUpdate();
    }

    @Test
    public void testExisteProducto() {
        // Prueba 1 - El producto existe en la tabla detalle_compras
        // Arrange
        int idProducto1 = 39;
        ComprasDAO cl = new ComprasDAO();
        // Act
        boolean resultado = cl.existeProducto(idProducto1);
        // Assert
        assertTrue(resultado);

    }

    @Test
    public void testNoExisteProducto() {
        // Prueba 1 - El producto existe en la tabla detalle_compras
        // Arrange
        int idProducto1 = 800;
        ComprasDAO cl = new ComprasDAO();
        // Act
        boolean resultado = cl.existeProducto(idProducto1);
        // Assert
        assertFalse(resultado);

    }

    @Test
    public void testDalExisteProducto() {
        // Arrange
        int idProducto3 = -1;
        ComprasDAO cl = new ComprasDAO();
        // Act
        boolean resultado = cl.existeProducto(idProducto3);
        // Assert
        assertFalse(resultado);

    }

    @Test
    public void testEditar() throws SQLException {
        // Arrange
        ClienteDAO cl = new ClienteDAO();
        int idCliente = 21;
        Connection con = mock(Connection.class);
        Conexion cn = mock(Conexion.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        when(cn.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(1);
        when(rs.getString(2)).thenReturn("12345678");
        when(rs.getString(3)).thenReturn("Juan Perez");
        when(rs.getString(4)).thenReturn("Calle 123");
        when(rs.getString(5)).thenReturn("juan.perez@example.com");
        when(rs.getString(6)).thenReturn("password");
// Act
        Cliente resultado = cl.BuscarCliente(idCliente);
// Assert
        assertEquals(21, resultado.getId());
        assertEquals("1020102989", resultado.getDni());
        assertEquals("juan", resultado.getNombres());
        assertEquals("La Estrella", resultado.getDireccion());
        assertEquals("juan@examples", resultado.getEmail());
        assertEquals("123", resultado.getPass());
    }

    @Test
    public void testClienteNoExisteEditar() throws SQLException {
        // Arrange
        int idCliente = 1;
        ClienteDAO cl = new ClienteDAO();

        Connection con = mock(Connection.class);
        Conexion cn = mock(Conexion.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        when(cn.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);
        // Act
        Cliente resultado = cl.BuscarCliente(idCliente);
        // Assert
        assertNull(resultado);

    }

    @Test
    public void testvalorInvalidoEditarCliente() throws SQLException {
        // Arrange
        int idCliente = -1; // Supongamos que el ID del cliente es un valor inválido
        ClienteDAO cl = new ClienteDAO();
        Conexion cn = mock(Conexion.class);
        when(cn.getConnection()).thenThrow(SQLException.class);
        // Act
        Cliente resultado = cl.BuscarCliente(idCliente);
        // Assert
        assertNull(resultado);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testguardarCompra() throws Exception, SQLException, SQLException, SQLException {
        // Arrange
        Compra co = new Compra(1, 1, "2023-04-18", 10.0, 1, "Pagada");
        Connection con = mock(Connection.class);
        Conexion cn = mock(Conexion.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        when(cn.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);

        ComprasDAO codao = new ComprasDAO();

        // Act
        int result = codao.guardarCompra(co);

        // Assert
        verify(ps, times(0)).executeUpdate();
        verify(ps, times(1)).setInt(1, 1);
        verify(ps, times(1)).setInt(2, 1);
        verify(ps, times(1)).setString(3, "2023-04-18");
        verify(ps, times(1)).setDouble(4, 10.0);
        verify(ps, times(1)).setString(5, "Pagada");
        assertEquals(1, result);
    }

    @Test
    public void testGuardarCompra() throws Exception {
        // Arrange
        ComprasDAO codao = new ComprasDAO();
        Compra compra = new Compra(1, 1, "2023-04-18", 10.0, 1, "Pagada");
        Connection con = mock(Connection.class);
        Conexion cn = mock(Conexion.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(cn.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        // Act
        int resultado = codao.guardarCompra(compra);

        // Assert
        assertEquals(1, resultado);
        verify(ps).setInt(1, compra.getIdCliente());
        verify(ps).setInt(2, compra.getIdPago());
        verify(ps).setString(3, compra.getFecha());
        verify(ps).setDouble(4, compra.getMonto());
        verify(ps).setString(5, compra.getEstado());
        verify(ps).executeUpdate();
    }

    //Buscar producto1
    @Test
    public void testBuscarProducto() throws Exception {
        // Arrange
        int id = 1;
        String nombres = "producto prueba";
        InputStream foto = null;
        String descripcion = "Descripcion";
        double precio = 2000.0;
        int stock = 10;
        String categoria = "Edicion";
        ResultSet rs = Mockito.mock(ResultSet.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        Connection con = Mockito.mock(Connection.class);
        ProductoDAO pdao = new ProductoDAO();
        pdao.cn = Mockito.mock(Conexion.class);
        Mockito.when(pdao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);
        Mockito.when(ps.executeQuery()).thenReturn(rs);
        Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
        Mockito.when(rs.getInt(1)).thenReturn(id);
        Mockito.when(rs.getString(2)).thenReturn(nombres);
        Mockito.when(rs.getBinaryStream(3)).thenReturn(foto);
        Mockito.when(rs.getString(4)).thenReturn(descripcion);
        Mockito.when(rs.getDouble(5)).thenReturn(precio);
        Mockito.when(rs.getInt(6)).thenReturn(stock);
        Mockito.when(rs.getString(7)).thenReturn(categoria);
        // Act
        List<Producto> productos = pdao.buscar(nombres);
        Producto producto = productos.get(0);
        // Assert
        assertEquals(id, producto.getId());
        assertEquals(nombres, producto.getNombres());
        assertEquals(foto, producto.getFoto());
        assertEquals(descripcion, producto.getDescripcion());
        assertEquals(precio, producto.getPrecio(), 0.001);
        assertEquals(stock, producto.getStock());
        assertEquals(categoria, producto.getCategoria());
    }

    //Buscar producto2
    @Test
    public void testBuscarProducto2() throws Exception {
        // Arrange
        int id = 1;
        String nombres = "producto prueba";
        InputStream foto = null;
        String descripcion = "Descripcion";
        double precio = 2000.0;
        int stock = 10;
        String categoria = "Edicion";
        String estado = "Activo";
        ResultSet rs = Mockito.mock(ResultSet.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        Connection con = Mockito.mock(Connection.class);
        ProductoDAO pdao = new ProductoDAO();
        pdao.cn = Mockito.mock(Conexion.class);
        Mockito.when(pdao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);
        Mockito.when(ps.executeQuery()).thenReturn(rs);
        Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
        Mockito.when(rs.getInt(1)).thenReturn(id);
        Mockito.when(rs.getString(2)).thenReturn(nombres);
        Mockito.when(rs.getBinaryStream(3)).thenReturn(foto);
        Mockito.when(rs.getString(4)).thenReturn(descripcion);
        Mockito.when(rs.getDouble(5)).thenReturn(precio);
        Mockito.when(rs.getInt(6)).thenReturn(stock);
        Mockito.when(rs.getString(7)).thenReturn(categoria);
        Mockito.when(rs.getString(8)).thenReturn(estado);
        // Act
        List<Producto> productos = pdao.buscar(nombres);
        Producto producto = productos.get(0);
        // Assert
        assertEquals(id, producto.getId());
        assertEquals(nombres, producto.getNombres());
        assertEquals(foto, producto.getFoto());
        assertEquals(descripcion, producto.getDescripcion());
        assertEquals(precio, producto.getPrecio(), 0.001);
        assertEquals(stock, producto.getStock());
        assertEquals(categoria, producto.getCategoria());
        assertEquals(estado, producto.getEstado());
    }

    //Buscar Realizar Pago 1
    @Test
    public void testRealizarPago() {
        // Arrange
        Metodos m = new Metodos();
        int id = 1;
        double totalPagar = 3000.0;
        // Act
        String result = m.realizarpago(id, totalPagar);
        // Assert
        assertEquals("Pago realizado exitosamente.", result);
    }

    //Realizar Pago 2
    @Test
    public void testRealizarPago2() {
        // Arrange
        Metodos m = new Metodos();
        int id = 0;
        double totalPagar = 0;
        // Act
        String result = m.realizarpago(id, totalPagar);
        // Assert
        assertEquals("Pago realizado exitosamente.", result);
    }

    //Guardar compra 1
    @Test
    public void testguardarCompra1() throws SQLException  {
        // Arrange
        Compra co = new Compra(1, 1, "2023-04-18", 10.0, 1, "Pagado");
        Connection con = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ComprasDAO codao = new ComprasDAO();
        codao.cn = Mockito.mock(Conexion.class);

        Mockito.when(codao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);

        // Act
        int result = codao.guardarCompra(co);

        // Assert
        Mockito.verify(ps, Mockito.times(1)).setInt(1, 1);
        Mockito.verify(ps, Mockito.times(1)).setInt(2, 1);
        Mockito.verify(ps, Mockito.times(1)).setString(3, "2023-04-18");
        Mockito.verify(ps, Mockito.times(1)).setDouble(4, 10.0);
        Mockito.verify(ps, Mockito.times(1)).setString(5, "Pagado");
        assertEquals(1, result);
    }

    //Guardar compra 2
    @Test
    public void testguardarCompra2() throws Exception {
        // Arrange
        Compra co = new Compra(1, 1, "2023-04-18", 10.0, 1, "Pagado");
        Connection con = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ComprasDAO codao = new ComprasDAO();
        codao.cn = Mockito.mock(Conexion.class);

        Mockito.when(codao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);

        // Act
        int result = codao.guardarCompra(co);

        // Assert
        Mockito.verify(ps, Mockito.times(1)).setInt(1, 1);
        Mockito.verify(ps, Mockito.times(1)).setInt(2, 1);
        Mockito.verify(ps, Mockito.times(1)).setString(3, "2023-04-8");
        Mockito.verify(ps, Mockito.times(1)).setDouble(4, 10.0);
        Mockito.verify(ps, Mockito.times(1)).setString(5, "Pagado");
        assertEquals(1, result);
    }

    //Guardar Detalle Compra 1
    @Test
    public void testGuardarDetalleCompra() throws Exception {
        // Arrange

        DetalleCompra dc = new DetalleCompra(1, 1, 1, 2, 10.0, new Producto());
        Connection con = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ComprasDAO codao = new ComprasDAO();
        codao.cn = Mockito.mock(Conexion.class);
        Mockito.when(codao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);
        // Act

        int result = codao.guardarDetalleCompra(dc);
        // Assert

        Mockito.verify(ps, Mockito.times(1)).setInt(1, 1);
        Mockito.verify(ps, Mockito.times(1)).setInt(2, 1);
        Mockito.verify(ps, Mockito.times(1)).setInt(3, 2);
        Mockito.verify(ps, Mockito.times(1)).setDouble(4, 10.0);
        assertEquals(1, result);
    }

    //Guardar Detalle Compra 2
    @Test
    public void testGuardarDetalleCompra2() throws Exception {
        // Arrange

        DetalleCompra dc = new DetalleCompra(1, 1, 1, 2, 10.0, new Producto());
        Connection con = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ComprasDAO codao = new ComprasDAO();
        codao.cn = Mockito.mock(Conexion.class);
        Mockito.when(codao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);
        // Act

        int result = codao.guardarDetalleCompra(dc);
        // Assert

        Mockito.verify(ps, Mockito.times(1)).setInt(1, 1);
        Mockito.verify(ps, Mockito.times(1)).setInt(2, 1);
        Mockito.verify(ps, Mockito.times(1)).setInt(3, 2);
        Mockito.verify(ps, Mockito.times(1)).setDouble(4, 10.0 - 1);
        assertEquals(1, result);
    }

    //Mis compras 1
    @Test
    public void testMisCompras() throws Exception {
        // Arrange
        int idCliente = 1;
        Connection con = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ResultSet rs = Mockito.mock(ResultSet.class);
        Compra com = new Compra(1, 1, "2023-04-18", 10.0, 1, "Pagado");
        List<Compra> listaesperada = new ArrayList<>();
        listaesperada.add(com);
        ComprasDAO codao = new ComprasDAO();
        codao.cn = Mockito.mock(Conexion.class);

        Mockito.when(codao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);
        Mockito.when(ps.executeQuery()).thenReturn(rs);
        Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
        Mockito.when(rs.getInt(1)).thenReturn(com.getId());
        Mockito.when(rs.getInt(2)).thenReturn(com.getIdCliente());
        Mockito.when(rs.getInt(3)).thenReturn(com.getIdPago());
        Mockito.when(rs.getString(4)).thenReturn(com.getFecha());
        Mockito.when(rs.getDouble(5)).thenReturn(com.getMonto());
        Mockito.when(rs.getString(6)).thenReturn(com.getEstado());
        // Act
        List<Compra> resultList = codao.misCompras(idCliente);
        // Assert
        Mockito.verify(ps, Mockito.times(1)).executeQuery();
        assertEquals(listaesperada.size(), resultList.size());
        assertEquals(listaesperada.get(0).getId(), resultList.get(0).getId());
        assertEquals(listaesperada.get(0).getIdCliente(), resultList.get(0).getIdCliente());
        assertEquals(listaesperada.get(0).getIdPago(), resultList.get(0).getIdPago());
        assertEquals(listaesperada.get(0).getFecha(), resultList.get(0).getFecha());
        assertEquals(listaesperada.get(0).getMonto(), resultList.get(0).getMonto(), 0.001);
        assertEquals(listaesperada.get(0).getEstado(), resultList.get(0).getEstado());
    }
    //Mis compras 1

    @Test
    public void testMisCompras2() throws Exception {
        // Arrange
        int idCliente = 1;
        Connection con = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ResultSet rs = Mockito.mock(ResultSet.class);
        Compra com = new Compra(1, 1, "2023-04-18", 10.0, 1, "Pagado");
        List<Compra> listaesperada = new ArrayList<>();
        listaesperada.add(com);
        ComprasDAO codao = new ComprasDAO();
        codao.cn = Mockito.mock(Conexion.class);

        Mockito.when(codao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);
        Mockito.when(ps.executeQuery()).thenReturn(rs);
        Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
        Mockito.when(rs.getInt(1)).thenReturn(com.getId());
        Mockito.when(rs.getInt(2)).thenReturn(com.getIdCliente());
        Mockito.when(rs.getInt(3)).thenReturn(com.getIdPago());
        Mockito.when(rs.getString(4)).thenReturn(com.getFecha());
        Mockito.when(rs.getDouble(5)).thenReturn(com.getMonto());
        Mockito.when(rs.getString(6)).thenReturn(com.getEstado());
        // Act
        List<Compra> resultList = codao.misCompras(idCliente);
        // Assert
        Mockito.verify(ps, Mockito.times(1)).executeQuery();
        assertEquals(listaesperada.size(), resultList.size());
        assertEquals(listaesperada.get(0).getId(), resultList.get(0).getId());
        assertEquals(listaesperada.get(0).getIdCliente(), resultList.get(0).getIdCliente());
        assertEquals(listaesperada.get(0).getIdPago(), resultList.get(0).getIdPago() - 1);
        assertEquals(listaesperada.get(0).getFecha(), resultList.get(0).getFecha());
        assertEquals(listaesperada.get(0).getMonto(), resultList.get(0).getMonto(), 0.001);
        assertEquals(listaesperada.get(0).getEstado(), resultList.get(0).getEstado());
    }

    //Agregar producto
    @Test
    public void testAgregarNuevoProducto() throws Exception {
        // Arrange
        Producto p = new Producto();
        p.setId(1);
        p.setNombres("Producto de prueba");
        p.setDescripcion("Descripción del producto de prueba");
        p.setPrecio(10.0);
        p.setStock(100);
        p.setCategoria("Categoría del producto de prueba");
        p.setEstado("Activo");
        p.setFoto(new ByteArrayInputStream(new byte[]{1, 2, 3}));
        Connection con = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ProductoDAO pdao = new ProductoDAO();
        pdao.cn = Mockito.mock(Conexion.class);
        Mockito.when(pdao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);

        // Act
        int result = pdao.AgregarNuevoProducto(p);

        // Assert
        Mockito.verify(ps, Mockito.times(1)).setString(1, p.getNombres());
        //Mockito.verify(ps, Mockito.times(1)).setObject(2, p.getFoto());
        Mockito.verify(ps, Mockito.times(1)).setString(3, p.getDescripcion());
        Mockito.verify(ps, Mockito.times(1)).setDouble(4, p.getPrecio());
        Mockito.verify(ps, Mockito.times(1)).setInt(5, p.getStock());
        Mockito.verify(ps, Mockito.times(1)).setString(6, p.getCategoria());
        Mockito.verify(ps, Mockito.times(1)).setString(7, p.getEstado());
        Mockito.verify(ps, Mockito.times(1)).executeUpdate();
        assertEquals(1, result);
    }

    //Agregar producto 2
    @Test
    public void testAgregarNuevoProducto2() throws Exception {
        // Arrange
        Producto p = new Producto();
        p.setId(1);
        p.setNombres("Producto de prueba");
        p.setDescripcion("Descripción del producto de prueba");
        p.setPrecio(10.0);
        p.setStock(100);
        p.setCategoria("Categoría del producto de prueba");
        p.setEstado("Activo");
        p.setFoto(new ByteArrayInputStream(new byte[]{1, 2, 3}));
        Connection con = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ProductoDAO pdao = new ProductoDAO();
        pdao.cn = Mockito.mock(Conexion.class);
        Mockito.when(pdao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);

        // Act
        int result = pdao.AgregarNuevoProducto(p);

        // Assert
        Mockito.verify(ps, Mockito.times(1)).setString(1, p.getNombres());
        //Mockito.verify(ps, Mockito.times(1)).setObject(2, p.getFoto());
        Mockito.verify(ps, Mockito.times(1)).setString(3, p.getDescripcion() + "hola");
        Mockito.verify(ps, Mockito.times(1)).setDouble(4, p.getPrecio());
        Mockito.verify(ps, Mockito.times(1)).setInt(5, p.getStock());
        Mockito.verify(ps, Mockito.times(1)).setString(6, p.getCategoria());
        Mockito.verify(ps, Mockito.times(1)).setString(7, p.getEstado());
        Mockito.verify(ps, Mockito.times(1)).executeUpdate();
        assertEquals(1, result);
    }

    //Agregar cliente 
    @Test
    public void testAgregarCliente() throws Exception {
        // Arrange
        Cliente c = new Cliente();
        c.setDni("12345678");
        c.setNombres("Juan Pérez");
        c.setDireccion("Calle Falsa 123");
        c.setEmail("juan.perez@mail.com");
        c.setPass("password");
        Connection con = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ClienteDAO cdao = new ClienteDAO();
        cdao.cn = Mockito.mock(Conexion.class);
        Mockito.when(cdao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);

        // Act
        int result = cdao.AgregarCliente(c);

        // Assert
        Mockito.verify(ps, Mockito.times(1)).setString(1, c.getDni());
        Mockito.verify(ps, Mockito.times(1)).setString(2, c.getNombres());
        Mockito.verify(ps, Mockito.times(1)).setString(3, c.getDireccion());
        Mockito.verify(ps, Mockito.times(1)).setString(4, c.getEmail());
        Mockito.verify(ps, Mockito.times(1)).setString(5, c.getPass());
        Mockito.verify(ps, Mockito.times(1)).executeUpdate();
        assertEquals(1, result);
    }
    //Agregar cliente 2

    @Test
    public void testAgregarCliente2() throws Exception {
        // Arrange
        Cliente c = new Cliente();
        c.setDni("12345678");
        c.setNombres("Juan Pérez");
        c.setDireccion("Calle Falsa 123");
        c.setEmail("juan.perez@mail.com");
        c.setPass("password");
        Connection con = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ClienteDAO cdao = new ClienteDAO();
        cdao.cn = Mockito.mock(Conexion.class);
        Mockito.when(cdao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);

        // Act
        int result = cdao.AgregarCliente(c);

        // Assert
        Mockito.verify(ps, Mockito.times(1)).setString(1, c.getDni());
        Mockito.verify(ps, Mockito.times(1)).setString(2, c.getNombres() + " Fallo");
        Mockito.verify(ps, Mockito.times(1)).setString(3, c.getDireccion());
        Mockito.verify(ps, Mockito.times(1)).setString(4, c.getEmail());
        Mockito.verify(ps, Mockito.times(1)).setString(5, c.getPass());
        Mockito.verify(ps, Mockito.times(1)).executeUpdate();
        assertEquals(1, result);
    }

    //Eliminar cliente
    @Test
    public void testEliminarCliente() throws Exception {
        // Arrange
        int id = 1;
        Connection con = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ClienteDAO cldao = new ClienteDAO();
        cldao.cn = Mockito.mock(Conexion.class);
        Mockito.when(cldao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);

        // Act
        cldao.EliminarCliente(id);

        // Assert
        Mockito.verify(ps, Mockito.times(1)).executeUpdate();

    }

    //Eliminar cliente 2
    @Test
    public void testEliminarCliente2() throws Exception {
        // Arrange
        int id = 1;
        Connection con = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ClienteDAO cldao = new ClienteDAO();
        cldao.cn = Mockito.mock(Conexion.class);
        Mockito.when(cldao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);

        // Act
        cldao.EliminarCliente(id);

        // Assert
        Mockito.verify(ps, Mockito.times(1)).setString(1, "valor esperado");

    }


    //Eliminar cliente fallo
    @Test
    public void testEliminarClienteFallo() throws Exception {
        // Arrange
        int idCliente = 1;
        Connection con = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ClienteDAO cldao = new ClienteDAO();
        cldao.cn = Mockito.mock(Conexion.class);
        Mockito.when(cldao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);

        // Act
        cldao.EliminarCliente(idCliente);

        // Assert
        Mockito.verify(ps, Mockito.times(2)).executeUpdate();
    }

    //Cliente Editar
    @Test
    public void testBuscarCliente() throws Exception {
        // Arrange
        int idCliente = 1;
        String dni = "12345678";
        String nombres = "Juan Perez";
        String direccion = "Av. Los Conquistadores 123";
        String email = "juan.perez@gmail.com";
        String pass = "password123";
        ResultSet rs = Mockito.mock(ResultSet.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        Connection con = Mockito.mock(Connection.class);
        ClienteDAO cldao = new ClienteDAO();
        cldao.cn = Mockito.mock(Conexion.class);
        Mockito.when(cldao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);
        Mockito.when(ps.executeQuery()).thenReturn(rs);
        Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
        Mockito.when(rs.getInt(1)).thenReturn(idCliente);
        Mockito.when(rs.getString(2)).thenReturn(dni);
        Mockito.when(rs.getString(3)).thenReturn(nombres);
        Mockito.when(rs.getString(4)).thenReturn(direccion);
        Mockito.when(rs.getString(5)).thenReturn(email);
        Mockito.when(rs.getString(6)).thenReturn(pass);

        // Act
        Cliente result = cldao.BuscarCliente(idCliente);

        // Assert
        assertNotNull(result);
        assertEquals(result.getId(), idCliente);
        assertEquals(result.getDni(), dni);
        assertEquals(result.getNombres(), nombres);
        assertEquals(result.getDireccion(), direccion);
        assertEquals(result.getEmail(), email);
        assertEquals(result.getPass(), pass);
    }

    //Confirmar cliente
    @Test
    public void testEditarCliente() throws Exception {
        // Arrange
        ClienteDAO cldao = new ClienteDAO();
        cldao.cn = Mockito.mock(Conexion.class);
        Connection con = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        Cliente c = new Cliente();
        c.setId(1);
        c.setNombres("Juan Perez");
        c.setDni("12345678");
        c.setDireccion("Calle 123");
        c.setEmail("juan.perez@gmail.com");
        c.setPass("password");

        Mockito.when(cldao.cn.getConnection()).thenReturn(con);
        Mockito.when(con.prepareStatement(Mockito.anyString())).thenReturn(ps);

        // Act
        cldao.EditarCliente(c);

        // Assert
        Mockito.verify(ps, Mockito.times(1)).executeUpdate();
        Mockito.verify(ps, Mockito.times(1)).setString(1, c.getNombres());
        Mockito.verify(ps, Mockito.times(1)).setString(2, c.getDni());
        Mockito.verify(ps, Mockito.times(1)).setString(3, c.getDireccion());
        Mockito.verify(ps, Mockito.times(1)).setString(4, c.getEmail());
        Mockito.verify(ps, Mockito.times(1)).setString(5, c.getPass());
        Mockito.verify(ps, Mockito.times(1)).setInt(6, c.getId());
    }

    //Confirmar
    @Test
    public void EditarCliente() {
        // Arrange
        Cliente c = new Cliente("12345678", "Juan Perez", "Av. Siempreviva 123", "juan.perez@gmail.com", "contraseña");
        ClienteDAO dao = new ClienteDAO();

        // Act
        int resultado = dao.AgregarCliente(c);

        // Assert
        assertEquals(1, resultado);
    }


}
