//COntrolador pruebas
package Controlador;

import Clases.Cliente;
import Clases.Producto;
import Clases.Carrito;
import Clases.Compra;
import Clases.DetalleCompra;
import Clases.Pago;
import ConexionBD.Fecha;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.swing.JOptionPane;

@MultipartConfig
public class Controlador extends HttpServlet {

    Pago pago = new Pago();
    Cliente cl = new Cliente();
    ClienteDAO cldao = new ClienteDAO();
    ComprasDAO cdao = new ComprasDAO();
    ProductoDAO pdao = new ProductoDAO();
    Cliente c = new Cliente();
    Producto p = new Producto();
    int item = 0;
    int cantidad = 1;
    double subtotal = 0.0;
    double totalPagar = 0.0;

    List<Carrito> listaTienda = new ArrayList<>();
    List tiendas = new ArrayList();
    List clientes = new ArrayList();

    String logueo = "Iniciar Sesion";
    String correo = "Iniciar Sesion";

    int idcompra;
    int idpago;
    double montopagar;
    int idProducto = 0;
    int idCliente = 0;

    Carrito car = new Carrito();

    Fecha fechaSistem = new Fecha();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, InterruptedException {

        tiendas = pdao.listar();
        clientes = cldao.listar();
        String accion = request.getParameter("accion");//Obteniendo un
        Metodos met = new Metodos ();
        switch (accion) {
            case "carrito":
                totalPagar = 0.0;
                request.setAttribute("Carrito", listaTienda);
                totalPagar = met.calcularTotal(listaTienda);
                Thread.sleep(5000);
                request.setAttribute("Carrito", listaTienda);
                request.setAttribute("totalPagar", totalPagar);
                request.getRequestDispatcher("vistas/carrito.jsp").forward(request, response);
                break;
            case "Comprar":
                listaTienda = (List<Carrito>) met.agregarCarrito(Integer.parseInt(request.getParameter("id")),listaTienda);
                request.getRequestDispatcher("Controlador?accion=carrito").forward(request, response);
                break;
            case "AgregarCarrito":
                listaTienda = (List<Carrito>) met.agregarCarrito(Integer.parseInt(request.getParameter("id")),listaTienda);
                request.setAttribute("cont", listaTienda.size());
                request.getRequestDispatcher("Controlador?accion=home").forward(request, response);
                break;
            case "AgregarCarritoGamer":
                listaTienda = (List<Carrito>) met.agregarCarrito(Integer.parseInt(request.getParameter("id")),listaTienda);
                request.setAttribute("cont", listaTienda.size());
                request.getRequestDispatcher("Controlador?accion=CategoriaGamer").forward(request, response);
                break;
            case "AgregarCarritoOfimatica":
                listaTienda = (List<Carrito>) met.agregarCarrito(Integer.parseInt(request.getParameter("id")),listaTienda);
                request.setAttribute("cont", listaTienda.size());
                request.getRequestDispatcher("Controlador?accion=CategoriaOfimatica").forward(request, response);
                break;
            case "AgregarCarritoEdicion":
                listaTienda = (List<Carrito>) met.agregarCarrito(Integer.parseInt(request.getParameter("id")),listaTienda);
                request.setAttribute("cont", listaTienda.size());
                request.getRequestDispatcher("Controlador?accion=CategoriaEdicion").forward(request, response);
                break;
            case "desactivarPro":
                int idproduc = Integer.parseInt(request.getParameter("id"));
                pdao.DesactivarProducto(idproduc);
                request.getRequestDispatcher("Controlador?accion=NuevoProducto").forward(request, response);
                break;
            case "ActivarPro":
                int idpfro = Integer.parseInt(request.getParameter("id"));
                pdao.ActivarProducto(idpfro);
                request.getRequestDispatcher("Controlador?accion=NuevoProducto").forward(request, response);
                break;
            case "eliminar":
                int idpro = Integer.parseInt(request.getParameter("id"));
                pdao.EliminarProducto(idpro);
                request.getRequestDispatcher("Controlador?accion=NuevoProducto").forward(request, response);
                break;
            case "existeproducto":
                boolean existe = false;
                int id = Integer.parseInt(request.getParameter("id"));
                existe = cdao.existeProducto(id);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{ \"existe\": " + existe + " }");
                break;
            case "editar":
                int idedi = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("productos", tiendas);
                p = pdao.BuscarProducto(idedi);
                if (p.getNombres() != null) {
                    request.setAttribute("Nombres", p.getNombres());
                    request.setAttribute("Descripcion", p.getDescripcion());
                    request.setAttribute("Precio", p.getPrecio());
                    request.setAttribute("Stock", p.getStock());
                    request.setAttribute("categoria", p.getCategoria());
                    request.getRequestDispatcher("vistas/eddProducto.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("Controlador?accion=NuevoProducto").forward(request, response);
                }
                break;
            case "ClienteEliminar":
                int idcli = Integer.parseInt(request.getParameter("id"));
                cldao.EliminarCliente(idcli);
                request.getRequestDispatcher("Controlador?accion=NuevoCliente").forward(request, response);
                break;
            case "existecliente"://13  
                boolean existec = false;
                int idc = Integer.parseInt(request.getParameter("id"));
                existe = cdao.existeCliente(idc);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{ \"existe\": " + existe + " }");
                break;
            case "ClientEditar":
                idCliente = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("clientes", clientes);
                cl = cldao.BuscarCliente(idCliente);
                if (cl.getNombres() != null) {
                    request.setAttribute("Dni", cl.getDni());
                    request.setAttribute("Nombres", cl.getNombres());
                    request.setAttribute("Direccion", cl.getDireccion());
                    request.setAttribute("Email", cl.getEmail());
                    request.setAttribute("Pass", cl.getPass());
                    request.getRequestDispatcher("vistas/eddCliente.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("Controlador?accion=NuevoCliente").forward(request, response);
                }
                break;
            case "ConfirmarCliente":
                String nombes = request.getParameter("txtNombes");
                String dniss = request.getParameter("txtDnis");
                String dire = request.getParameter("txtDirec");
                String ema = request.getParameter("txtEmaill");
                String passww = request.getParameter("txtPasswr");
                c.setId(idCliente);
                c.setNombres(nombes);
                c.setDni(dniss);
                c.setDireccion(dire);
                c.setEmail(ema);
                c.setPass(passww);
                cldao.EditarCliente(c);
                request.getRequestDispatcher("Controlador?accion=NuevoCliente").forward(request, response);
                break;
            case "CancelarCliente":
                request.getRequestDispatcher("Controlador?accion=NuevoCliente").forward(request, response);
                break;
            case "Confirmar":
                String nombe = request.getParameter("txtNombree");
                String desce = request.getParameter("txtDescripcione");
                double precie = Double.parseDouble(request.getParameter("txtPrecioe"));
                int stocke = Integer.parseInt(request.getParameter("txtStocke"));
                String cate = request.getParameter("txtcategoria");
                p.setNombres(nombe);
                p.setPrecio(precie);
                p.setStock(stocke);
                p.setCategoria(cate);
                p.setDescripcion(desce);
                pdao.EditarProducto(p);
                request.getRequestDispatcher("Controlador?accion=NuevoProducto").forward(request, response);
                break;
            case "Cancelar":
                request.getRequestDispatcher("Controlador?accion=NuevoProducto").forward(request, response);
                break;
            case "updateCantidad":
                idProducto = Integer.parseInt(request.getParameter("id"));
                int cant = Integer.parseInt(request.getParameter("cantidad"));
                for (int j = 0; j < listaTienda.size(); j++) {
                    if (listaTienda.get(j).getIdProducto() == idProducto) {
                        listaTienda.get(j).setCantidad(cant);
                        listaTienda.get(j).setSubTotal(listaTienda.get(j).getPrecioCompra() * cant);
                    }
                }
                break;
            case "Validar":
                String email = request.getParameter("txtemail");
                String pass = request.getParameter("txtpass");
                if (email.equals("admin@gmail.com") && pass.equals("admin")) {
                    HttpSession session = request.getSession();
                    session.setAttribute("perfil", "admin");
                    logueo = "Admin";
                    correo = "admin@gmail.com";
                    session.setAttribute("logueo", logueo);
                    session.setAttribute("correo", correo);
                } else {
                    cl = cldao.Validar(email, pass);
                    if (cl.getId() != 0) {
                        HttpSession session = request.getSession();
                        session.setAttribute("email", "pass");
                        logueo = cl.getNombres();
                        correo = cl.getEmail();
                        session.setAttribute("logueo", logueo);
                        session.setAttribute("correo", correo);
                    } else {
                        request.setAttribute("error", "El usuario no existe o los datos son incorrectos. Inténtalo de nuevo.");
                    }
                }
                request.getRequestDispatcher("Controlador?accion=home").forward(request, response);
                break;
            case "Registrar":
                String nom = request.getParameter("txtnom");
                String dni = request.getParameter("txtdni");
                String em = request.getParameter("txtemail");
                String pas = request.getParameter("txtpass");
                String dir = request.getParameter("txtdire");
                cl.setNombres(nom);
                cl.setDni(dni);
                cl.setEmail(em);
                cl.setPass(pas);
                cl.setDireccion(dir);
                cldao.AgregarCliente(cl);
                request.setAttribute("cuenta", "Cuenta creada exitosamente");
                request.getRequestDispatcher("Controlador?accion=home").forward(request, response);
                break;
            case "Nuevo":
                listaTienda = new ArrayList();
                request.getRequestDispatcher("Controlador?accion=home").forward(request, response);
                break;
            ///Prepatado
            case "Buscar":
                String nombre = request.getParameter("txtbuscar");
                tiendas = pdao.buscar(nombre);
                request.setAttribute("cont", listaTienda.size());
                request.setAttribute("productos", tiendas);
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
            case "RealizarPago":
                montopagar = totalPagar;
                if (cl.getId() != 0 && montopagar > 0) {
                    cdao.Pagar(montopagar);
                    request.setAttribute("car", "Pago realizado exitosamente.");

                    request.getRequestDispatcher("Controlador?accion=carrito").forward(request, response);

                } else {
                    montopagar = 0;
                    request.setAttribute("car", "No se pudo realizar el pago.");
                    request.getRequestDispatcher("Controlador?accion=carrito").forward(request, response);

                }
                break;
            case "GenerarCompra":
                idpago = cdao.IdPago();
                if (cl.getId() != 0 && listaTienda.size() != 0 && montopagar > 0) {
                    if (totalPagar > 0.0) {
                        Compra co = new Compra();
                        co.setIdCliente(cl.getId());
                        co.setFecha(fechaSistem.FechaBD());
                        co.setMonto(totalPagar);
                        co.setIdPago(idpago);
                        co.setEstado("Pagado");
                        cdao.guardarCompra(co);
                        montopagar = 0;
                        idcompra = cdao.IdCompra();
                        for (int i = 0; i < listaTienda.size(); i++) {
                            DetalleCompra dc = new DetalleCompra();
                            dc.setIdcompra(idcompra);
                            dc.setIdproducto(listaTienda.get(i).getIdProducto());
                            dc.setCantidad(listaTienda.get(i).getCantidad());
                            dc.setPrecioCompra(listaTienda.get(i).getPrecioCompra());
                            cdao.guardarDetalleCompra(dc);
                        }
                        listaTienda = new ArrayList<>();
                        List compra = cdao.misCompras(cl.getId());
                        request.setAttribute("myCompras", compra);
                        request.getRequestDispatcher("vistas/compras.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Controlador?accion=home").forward(request, response);
                    }
                } else {
                    request.getRequestDispatcher("Controlador?accion=carrito").forward(request, response);
                }
                break;
            //26
            case "MisCompras":
                if (cl.getId() != 0) {
                    List compra = cdao.misCompras(cl.getId());
                    request.setAttribute("myCompras", compra);
                    request.getRequestDispatcher("vistas/compras.jsp").forward(request, response);
                } else if (listaTienda.size() > 0) {
                    request.getRequestDispatcher("Controlador?accion=carrito").forward(request, response);
                } else {
                    request.getRequestDispatcher("Controlador?accion=home").forward(request, response);
                }
                break;
            case "verDetalle":
                totalPagar = 0.0;
                int idcompras = Integer.parseInt(request.getParameter("idcompra"));
                List<DetalleCompra> detalle = cdao.Detalle(idcompras);
                request.setAttribute("myDetalle", detalle);
                for (int i = 0; i < detalle.size(); i++) {
                    totalPagar = totalPagar + (detalle.get(i).getPrecioCompra() * detalle.get(i).getCantidad());
                }
                request.setAttribute("montoPagar", totalPagar);
                request.getRequestDispatcher("vistas/DetalleCompra.jsp").forward(request, response);
                break;
            case "NuevoProducto":
                request.setAttribute("productos", tiendas);
                request.getRequestDispatcher("vistas/addProducto.jsp").forward(request, response);
                break;
            case "NuevoCliente":
                request.setAttribute("clientes", clientes);
                request.getRequestDispatcher("vistas/addCliente.jsp").forward(request, response);
                break;
            case "Asesorias":
                request.setAttribute("productos", tiendas);
                request.getRequestDispatcher("vistas/Asesoria.jsp").forward(request, response);
                break;
            case "CategoriaGamer":
                request.setAttribute("productos", tiendas);
                request.getRequestDispatcher("vistas/gamer.jsp").forward(request, response);
                break;
            case "CategoriaOfimatica":
                request.setAttribute("productos", tiendas);
                request.getRequestDispatcher("vistas/ofimatica.jsp").forward(request, response);
                break;
            case "CategoriaEdicion":
                request.setAttribute("productos", tiendas);
                request.getRequestDispatcher("vistas/edicion.jsp").forward(request, response);
                break;
            case "GuardarProducto":
                String nomb = request.getParameter("txtNombre");
                String desc = request.getParameter("txtDescripcion");
                double preci = Double.parseDouble(request.getParameter("txtPrecio"));
                int stock = Integer.parseInt(request.getParameter("txtStock"));
                String categ = request.getParameter("txtcategoria");
                Part part = request.getPart("txtFoto");
                InputStream inputStream = part.getInputStream();
                p.setNombres(nomb);
                p.setPrecio(preci);
                p.setStock(stock);
                p.setCategoria(categ);
                p.setFoto(inputStream);
                p.setDescripcion(desc);
                p.setEstado("ACTIVO");
                pdao.AgregarNuevoProducto(p);
                request.getRequestDispatcher("Controlador?accion=NuevoProducto").forward(request, response);
                break;
            case "GuardarCliente":
                String dnis = request.getParameter("txtDni");
                String nombs = request.getParameter("txtNombres");
                String direc = request.getParameter("txtDireccion");
                String emai = request.getParameter("txtEmail");
                String passwd = request.getParameter("txtPassword");
                c.setDni(dnis);
                c.setNombres(nombs);
                c.setDireccion(direc);
                c.setEmail(emai);
                c.setPass(passwd);
                cldao.AgregarCliente(c);
                request.getRequestDispatcher("Controlador?accion=NuevoCliente").forward(request, response);
                break;
            case "Salir":
                listaTienda = new ArrayList();
                cl = new Cliente();
                HttpSession session = request.getSession();
                session.invalidate();
                logueo = "Iniciar Sesion";
                correo = "Iniciar Sesion";
                request.getRequestDispatcher("Controlador?accion=Salirr").forward(request, response);
                break;
            case "deleteProducto":
                int idw = Integer.parseInt(request.getParameter("id"));
                eliminar_Lista(idw);

                break;
            default:
                request.setAttribute("cont", listaTienda.size());
                request.setAttribute("clientes", tiendas);
                request.setAttribute("productos", tiendas);
                request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


    public void eliminar_Lista(int id) {
        Carrito car = new Carrito();
        for (int i = 0; i < listaTienda.size(); i++) {
            if (listaTienda.get(i).getIdProducto() == id) {
                car = listaTienda.get(i);
                break;
            }
        }
        listaTienda.remove(car);

    }
}
