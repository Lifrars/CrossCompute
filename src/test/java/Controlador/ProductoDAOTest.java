package Controlador;

import ConexionBD.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ProductoDAOTest {

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


}
