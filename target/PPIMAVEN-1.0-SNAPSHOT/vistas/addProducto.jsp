<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript">
    (function(c,l,a,r,i,t,y){
        c[a]=c[a]||function(){(c[a].q=c[a].q||[]).push(arguments)};
        t=l.createElement(r);t.async=1;t.src="https://www.clarity.ms/tag/"+i;
        y=l.getElementsByTagName(r)[0];y.parentNode.insertBefore(t,y);
    })(window, document, "clarity", "script", "golzus5hs4");
</script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
        <link href="css/estilos.css" rel="stylesheet" type="text/css"/>        
        <title>Cross Computer</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <a class="navbar-brand" href="#"><i>Agregar Producto</i></a>
            <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                    <li class="nav-item active">
                        <a class="nav-link" href="./Controlador?accion=home"><i class="fas fa-home"></i>Menú<span class="sr-only">(current)</span></a>
                    </li>                   
                </ul>                                           
                <ul class="navbar-nav btn-group my-2 my-lg-0" role="group">
                    <a style="color: white; cursor: pointer" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fas fa-user-tie"></i> ${logueo}</a>
                    <div class="dropdown-menu text-center dropdown-menu-right">
                        <a class="dropdown-item" href="#"><img src="img/user.png" alt="60" height="60"/></a>                        
                        <a class="dropdown-item" href="#">${user}</a>
                        <a class="dropdown-item" href="#" data-toggle="modal" data-target="#myModal">${correo}</a>
                        <a class="dropdown-item" href="./Controlador?accion=Salir"> <i class="fas fa-arrow-right"> Salir</i></a>                        
                    </div>
                </ul>     
            </div>
        </nav>
        <div class="container mt-3">            
            <div class="row">
                <div class="col-sm-4">
                    <div class="card">
                        <div class="card-header">
                            <h3>Agregar Producto</h3>
                        </div>                
                        <div class="card-body">
                            <form action="Controlador?accion=GuardarProducto" method="POST" enctype="multipart/form-data">
                                <div class="form-group">
                                    <label>Nombres:</label>
                                    <input type="text" name="txtNombre" class="form-control" required>
                                </div>                     
                                <div class="form-group">
                                    <label>Descripción</label>
                                    <textarea name="txtDescripcion" class="form-control" required></textarea>
                                </div>
                                <div class="form-group">
                                    <label>Precio</label>
                                    <input type="text" name="txtPrecio" class="form-control" required>
                                </div>
                                <div class="form-group">
                                    <label>Stock</label>
                                    <input type="text" name="txtStock" class="form-control" required>
                                </div>
                                <div class="form-group">
                                    <label>Categoría</label>
                                    <input type="text" name="txtcategoria" class="form-control" required>
                                </div>
                                <div class="form-group">                            
                                    <input type="file" name="txtFoto" placeholder="sad" required>
                                </div>
                                <div class="form-group">
                                    <button class="btn btn-danger" name="accion" value="GuardarProducto">Guardar</button>
                                </div>
                            </form>
                        </div>               
                    </div>
                </div> 
                <div class="col-sm-8">
                    <table class="table table-responsive">
                        <thead class="">
                            <tr class="text-center">
                                <th>ID</th>
                                <th>NOMBRE</th>
                                <th>DESCRIPCION</th>                               
                                <th>PRECIO</th>
                                <th>STOCK</th>
                                <th>CATEGORIA</th>
                                <th>ACCIONES</th>
                            </tr>
                        </thead>
                        <tbody>

                            <c:forEach var="p" items="${productos}">
                                <tr class="text-center">
                                    <td>${p.getId()}</td>
                                    <td>${p.getNombres()}
                                        <img src="ControladorImg?id=${p.getId()}" width="30" height="30">
                                    </td>
                                    <td>${p.getDescripcion()}</td>                                    
                                    <td>${p.getPrecio()}</td>
                                    <td>${p.getStock()}</td>
                                    <td>${p.getCategoria()}</td>
                                    <td class="d-flex">
                                        <form action="Controlador" class="">
                                            <input type="hidden" name="id" value="${p.getId()}">
                                            <button type="submit" class="btn btn-warning"name="accion" value="editar">Editar</button>

                                            <button type="submit" class="btn btn-danger eliminarpro" value="${p.getId()}">Eliminar</button>
                                            <c:if test="${p.getEstado()=='ACTIVO'}">
                                                <button type="submit" class="btn btn-danger" name="accion" value="desactivarPro">Descontinuar</button>
                                            </c:if>
                                            <c:if test="${p.getEstado()=='DESACTIVO'}">
                                                <button type="submit" class="btn btn-info" name="accion" value="ActivarPro">Activar</button>
                                            </c:if>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>                           
                        </tbody>
                    </table>

                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="js/Eliminarpro.js" type="text/javascript"></script>
    </body>
</html>