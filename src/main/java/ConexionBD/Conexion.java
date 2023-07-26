package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    Connection con;

    String url = "jdbc:mysql://bha3cjth0aj19wtwcnjk-mysql.services.clever-cloud.com:3306/bha3cjth0aj19wtwcnjk";
    
    
    String user = "u26khxhokdgc4xv5";
    String pass = "74QgpinYoTf5lz1PRUre";
    
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("NO SE CONECTO");
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
}
