package Controlador;

import Clases.Cliente;
import Clases.Producto;
import ConexionBD.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ClienteDAO {

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
                p.setEstado(rs.getString(8));
                list.add(p);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public Cliente Validar(String email, String pass) {
        String sql = "select * from cliente where Email=? and Password=?";
        Cliente c = new Cliente();
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            while (rs.next()) {
                c.setId(rs.getInt(1));
                c.setDni(rs.getString(2));
                c.setNombres(rs.getString(3));
                c.setDireccion(rs.getString(4));
                c.setEmail(rs.getString(5));
                c.setPass(rs.getString(6));
            }
        } catch (Exception e) {
        }
        return c;
    }

    public int AgregarCliente(Cliente c) {
        String sql = "INSERT INTO cliente (Dni, Nombres, Direccion, Email, Password)values(?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getDni());
            ps.setString(2, c.getNombres());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getPass());
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return 1;
    }

    public List listar() {
        List lista = new ArrayList();
        String sql = "select * from cliente";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt(1));
                c.setDni(rs.getString(2));
                c.setNombres(rs.getString(3));
                c.setDireccion(rs.getString(4));
                c.setEmail(rs.getString(5));
                c.setPass(rs.getString(6));
                lista.add(c);
            }
        } catch (Exception e) {
        }
        return lista;
    }

    public Cliente listarId(int id) {
        Cliente c = new Cliente();
        String sql = "select * from cliente where IdCliente=" + id;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                c.setId(rs.getInt(1));
                c.setDni(rs.getString(2));
                c.setNombres(rs.getString(3));
                c.setDireccion(rs.getString(3));
                c.setEmail(rs.getString(4));
                c.setPass(rs.getString(5));
            }
        } catch (Exception e) {
        }
        return c;
    }

    public Cliente BuscarCliente(int id) {
        String sql = "select * from cliente where idCliente=" + id;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt(1));
                c.setDni(rs.getString(2));
                c.setNombres(rs.getString(3));
                c.setDireccion(rs.getString(4));
                c.setEmail(rs.getString(5));
                c.setPass(rs.getString(6));
                return c;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void EliminarCliente(int id) {
        String sql = "delete from cliente where idCliente=" + id;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Este usuario ha comprado en la aplicacion");
        }
    }

    public void EditarCliente(Cliente c) {
        String sql = "update cliente set Nombres=?,Dni=?,Direccion=?,Email=?,Password=? where idCliente=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNombres());
            ps.setString(2, c.getDni());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getPass());
            ps.setInt(6, c.getId());
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
}
