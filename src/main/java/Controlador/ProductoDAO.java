package Controlador;

import Clases.Producto;
import ConexionBD.Conexion;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

public class ProductoDAO extends Conexion {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r = 0;

    public List buscar(String nombre) {
        List list = new ArrayList();
        String sql = "select * from producto where Nombres like '%" + nombre + "%'";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt(1));
                p.setNombres(rs.getString(2));
                p.setFoto(rs.getBinaryStream(3));
                p.setDescripcion(rs.getString(4));
                p.setPrecio(rs.getDouble(5));
                p.setStock(rs.getInt(6));
                p.setCategoria(rs.getString(7));
                list.add(p);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public Producto listarId(int id) {
        Producto p = new Producto();
        String sql = "select * from producto where IdProducto=" + id;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                p.setId(rs.getInt(1));
                p.setNombres(rs.getString(2));
                p.setFoto(rs.getBinaryStream(3));
                p.setDescripcion(rs.getString(4));
                p.setPrecio(rs.getDouble(5));
                p.setStock(rs.getInt(6));
                p.setCategoria(rs.getString(7));
                p.setEstado(rs.getString(8));
            }
        } catch (Exception e) {
        }
        return p;
    }

    public List listar() {
        List lista = new ArrayList();
        String sql = "select * from producto";
        try {
            ps = getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt(1));
                p.setNombres(rs.getString(2));
                p.setFoto(rs.getBinaryStream(3));
                p.setDescripcion(rs.getString(4));
                p.setPrecio(rs.getDouble(5));
                p.setStock(rs.getInt(6));
                p.setCategoria(rs.getString(7));
                p.setEstado(rs.getString(8));
                lista.add(p);
            }
        } catch (Exception e) {
        }
        return lista;
    }

    public void listarImg(int id, HttpServletResponse response) {
        String sql = "select * from producto where idProducto=" + id;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            outputStream = response.getOutputStream();
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                inputStream = rs.getBinaryStream("Foto");
            }
            bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            int i = 0;
            while ((i = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(i);
            }
        } catch (Exception e) {
        }
    }

    public int AgregarNuevoProducto(Producto p) {
        String sql = "insert into producto(Nombres,Foto,Descripcion,Precio,Stock,categoria,Estado)values(?,?,?,?,?,?,?)";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, p.getNombres());
            ps.setBlob(2, p.getFoto());
            ps.setString(3, p.getDescripcion());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getCategoria());
            ps.setString(7, p.getEstado());
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }

    public void EliminarProducto(int id) {
        String sql = "delete from producto where idProducto=" + id;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public Producto BuscarProducto(int id) {
        String sql = "select * from producto where idProducto=" + id;
        try {

            ps = getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt(1));
                p.setNombres(rs.getString(2));
                p.setFoto(rs.getBinaryStream(3));
                p.setDescripcion(rs.getString(4));
                p.setPrecio(rs.getDouble(5));
                p.setStock(rs.getInt(6));
                p.setCategoria(rs.getString(7));
                return p;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void EditarProducto(Producto p) {
        String sql = "update producto set Nombres=?,Descripcion=?,Precio=?,Stock=?,categoria=? where idProducto=?";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, p.getNombres());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getCategoria());
            ps.setInt(6, p.getId());
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void DesactivarProducto(int id)  {
        String sql = "UPDATE producto SET Estado='DESACTIVO' WHERE idProducto="+id;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
            
        }
    }

    public void ActivarProducto(int id)  {
        String sql = "update producto set Estado='ACTIVO' where idProducto=?";
        try {
            con = cn.getConnection();
            
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e.toString());
            
        }
    }

}
