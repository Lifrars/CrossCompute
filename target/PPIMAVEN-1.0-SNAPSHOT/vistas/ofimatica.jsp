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
            <a class="navbar-brand" href="#"><i>Gamer</i></a>
            <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                    <li class="nav-item active">
                        <a class="nav-link" href="Controlador?accion=nuevo"><i class="fas fa-home"></i>Menú<span class="sr-only">(current)</span></a>
                    </li>
                    <%if (session.getAttribute("email") == "pass") {

                    %>
                    <li class="nav-item">
                        <a class="nav-link" href="./Controlador?accion=carrito"><i class="fas fa-cart-plus">(<label style="color: darkorange">${cont}</label>)</i> Carrito</a>
                    </li> 
                    <%}
                    %>
                    <%if (session.getAttribute("perfil") == "admin") {

                    %>
                    <li class="nav-item">
                        <a class="nav-link" href="./Controlador?accion=NuevoProducto">Productos</a>
                    </li>
                    <%}
                    %>
                    <%if (session.getAttribute("email") == "pass") {

                    %>
                    <li class="nav-item">
                        <a class="nav-link" href="./Controlador?accion=Asesorias">Asesoria</a>
                    </li>
                    <%}
                    %>
                </ul>
                <h3 style="color: white; width:700px">Board</h3>
                <ul class="navbar-nav btn-group my-2 my-lg-0" role="group">
                    <a style="color: white; cursor: pointer" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fas fa-user-tie"></i> ${logueo}</a>
                    <div class="dropdown-menu text-center dropdown-menu-right">
                        <a class="dropdown-item" href="#"><img src="img/user.png" alt="60" height="60"/></a>                        
                        <a class="dropdown-item" href="#">${user}</a>
                        <a class="dropdown-item" href="#" data-toggle="modal" data-target="#myModal">${correo}</a>
                        <%if (session.getAttribute("email") == "pass") {


                        %>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="Controlador?accion=MisCompras">Mis Compras</a>
                        <div class="dropdown-divider"></div>
                        <%                            }
                        %>
                        <a class="dropdown-item" href="./Controlador?accion=Salir"> <i class="fas fa-arrow-right"> Salir</i></a>                        
                    </div>
                </ul>     
            </div>
        </nav>
        <div class="container mt-4">
            <div class="row">
                <label>La tarjeta madre, siendo el componente mas importante de todos, ya que es el que hace posible la transitividad de los datos y sus
                    componentes, empezando siempre por este componente, tenemos estas tres opciones de BOARDS gamers que son ideales para un procesamiento
                    de datos y amplia gama de mejora en cuanto a sus componentes que se puedan agregar.</label>
            </div>
        </div>
        <!--1° BOARD -->
        <div class="container mt-4">
            <div class="row">
                <c:forEach var="p" items="${productos}">
                    <c:if test="${p.getEstado() == 'ACTIVO'}">
                        <c:if test="${p.getNombres() == 'Tarjeta Madre'}">
                            <c:if test="${p.getCategoria() == 'OFIMATICA'}">
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <div class="card">
                                            <div class="card-header">
                                                <label class="col-sm-12">${p.getNombres()}</label>                                    
                                            </div>
                                            <div class="card-body text-center d-flex">
                                                <label><i class="fas fa-dollar-sign">${p.getPrecio()}</i></label>
                                                <img src="ControladorImg?id=${p.getId()}" width="200" height="170">
                                            </div>
                                            <div class="card-footer">
                                                <div class="col-sm-12">
                                                    <label>${p.getDescripcion()}</label>                                   
                                                </div>
                                                <%if (session.getAttribute("email") == "pass") {


                                                %>
                                                <div class=" col-sm-12 text-center">                                
                                                    <a href="Controlador?accion=AgregarCarritoOfimatica&id=${p.getId()}" class="btn btn2 btn-outline-primary">Agregar a Carrito<i class="fas fa-cart-plus"></i></a>
                                                    <a href="Controlador?accion=Comprar&id=${p.getId()}" class="btn btn-danger">Comprar</a>
                                                </div>  
                                                <%                                        }
                                                %>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:if>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <!--2° PROCESADOR -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="mx-auto" id="navbarTogglerDemo03">
                <h3 style="color: white; text-align: justify">Procesador</h3>   
            </div>
        </nav>
        <div class="container mt-4">
            <div class="row">
                <label>La tarjeta madre, siendo el componente mas importante de todos, ya que es el que hace posible la transitividad de los datos y sus
                    componentes, empezando siempre por este componente, tenemos estas tres opciones de BOARDS gamers que son ideales para un procesamiento
                    de datos y amplia gama de mejora en cuanto a sus componentes que se puedan agregar.</label>
            </div>
        </div>
        <div class="container mt-4">
            <div class="row">
                <c:forEach var="p" items="${productos}">
                    <c:if test="${p.getEstado() == 'ACTIVO'}">
                        <c:if test="${p.getNombres() == 'Procesador Ofimatica'}">
                            <c:if test="${p.getCategoria() == 'OFIMATICA'}">
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <div class="card">
                                            <div class="card-header">
                                                <label class="col-sm-12">${p.getNombres()}</label>                                    
                                            </div>
                                            <div class="card-body text-center d-flex">
                                                <label><i class="fas fa-dollar-sign">${p.getPrecio()}</i></label>
                                                <img src="ControladorImg?id=${p.getId()}" width="200" height="170">
                                            </div>
                                            <div class="card-footer">
                                                <div class="col-sm-12">
                                                    <label>${p.getDescripcion()}</label>                                   
                                                </div>
                                                <%if (session.getAttribute("email") == "pass") {


                                                %>
                                                <div class=" col-sm-12 text-center">                                
                                                    <a href="Controlador?accion=AgregarCarritoOfimatica&id=${p.getId()}" class="btn btn2 btn-outline-primary">Agregar a Carrito<i class="fas fa-cart-plus"></i></a>
                                                    <a href="Controlador?accion=Comprar&id=${p.getId()}" class="btn btn-danger">Comprar</a>
                                                </div>  
                                                <%                                        }
                                                %>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:if>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <!--3° RAM -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="mx-auto" id="navbarTogglerDemo03">
                <h3 style="color: white">RAM</h3>   
            </div>
        </nav>
        <div class="container mt-4">
            <div class="row">
                <label>La tarjeta madre, siendo el componente mas importante de todos, ya que es el que hace posible la transitividad de los datos y sus
                    componentes, empezando siempre por este componente, tenemos estas tres opciones de BOARDS gamers que son ideales para un procesamiento
                    de datos y amplia gama de mejora en cuanto a sus componentes que se puedan agregar.</label>
            </div>
        </div>
        <div class="container mt-4">
            <div class="row">
                <c:forEach var="p" items="${productos}">
                    <c:if test="${p.getEstado() == 'ACTIVO'}">
                        <c:if test="${p.getNombres() == 'RAM Oficina'}">
                            <c:if test="${p.getCategoria() == 'OFIMATICA'}">
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <div class="card">
                                            <div class="card-header">
                                                <label class="col-sm-12">${p.getNombres()}</label>                                    
                                            </div>
                                            <div class="card-body text-center d-flex">
                                                <label><i class="fas fa-dollar-sign">${p.getPrecio()}</i></label>
                                                <img src="ControladorImg?id=${p.getId()}" width="200" height="170">
                                            </div>
                                            <div class="card-footer">
                                                <div class="col-sm-12">
                                                    <label>${p.getDescripcion()}</label>                                   
                                                </div>
                                                <%if (session.getAttribute("email") == "pass") {


                                                %>
                                                <div class=" col-sm-12 text-center">                                
                                                    <a href="Controlador?accion=AgregarCarritoOfimatica&id=${p.getId()}" class="btn btn2 btn-outline-primary">Agregar a Carrito<i class="fas fa-cart-plus"></i></a>
                                                    <a href="Controlador?accion=Comprar&id=${p.getId()}" class="btn btn-danger">Comprar</a>
                                                </div>  
                                                <%                                        }
                                                %>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:if>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <!--4° DISCO DURO -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="mx-auto" id="navbarTogglerDemo03">
                <h3 style="color: white">Disco Duro</h3>   
            </div>
        </nav>
        <div class="container mt-4">
            <div class="row">
                <label>La tarjeta madre, siendo el componente mas importante de todos, ya que es el que hace posible la transitividad de los datos y sus
                    componentes, empezando siempre por este componente, tenemos estas tres opciones de BOARDS gamers que son ideales para un procesamiento
                    de datos y amplia gama de mejora en cuanto a sus componentes que se puedan agregar.</label>
            </div>
        </div>
        <div class="container mt-4">
            <div class="row">
                <c:forEach var="p" items="${productos}">
                    <c:if test="${p.getEstado() == 'ACTIVO'}">
                        <c:if test="${p.getNombres() == 'Disco HDD'}">
                            <c:if test="${p.getCategoria() == 'OFIMATICA'}">
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <div class="card">
                                            <div class="card-header">
                                                <label class="col-sm-12">${p.getNombres()}</label>                                    
                                            </div>
                                            <div class="card-body text-center d-flex">
                                                <label><i class="fas fa-dollar-sign">${p.getPrecio()}</i></label>
                                                <img src="ControladorImg?id=${p.getId()}" width="200" height="170">
                                            </div>
                                            <div class="card-footer">
                                                <div class="col-sm-12">
                                                    <label>${p.getDescripcion()}</label>                                   
                                                </div>
                                                <%if (session.getAttribute("email") == "pass") {


                                                %>
                                                <div class=" col-sm-12 text-center">                                
                                                    <a href="Controlador?accion=AgregarCarritoOfimatica&id=${p.getId()}" class="btn btn2 btn-outline-primary">Agregar a Carrito<i class="fas fa-cart-plus"></i></a>
                                                    <a href="Controlador?accion=Comprar&id=${p.getId()}" class="btn btn-danger">Comprar</a>
                                                </div>  
                                                <%                                        }
                                                %>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:if>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <!--5° FUENTE DE PODER -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="mx-auto" id="navbarTogglerDemo03">
                <h3 style="color: white">Fuente de Poder</h3>   
            </div>
        </nav>
        <div class="container mt-4">
            <div class="row">
                <label>La tarjeta madre, siendo el componente mas importante de todos, ya que es el que hace posible la transitividad de los datos y sus
                    componentes, empezando siempre por este componente, tenemos estas tres opciones de BOARDS gamers que son ideales para un procesamiento
                    de datos y amplia gama de mejora en cuanto a sus componentes que se puedan agregar.</label>
            </div>
        </div>
        <div class="container mt-4">
            <div class="row">
                <c:forEach var="p" items="${productos}">
                    <c:if test="${p.getEstado() == 'ACTIVO'}">
                        <c:if test="${p.getNombres() == 'Fuente de Poder Oficina'}">
                            <c:if test="${p.getCategoria() == 'OFIMATICA'}">
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <div class="card">
                                            <div class="card-header">
                                                <label class="col-sm-12">${p.getNombres()}</label>                                    
                                            </div>
                                            <div class="card-body text-center d-flex">
                                                <label><i class="fas fa-dollar-sign">${p.getPrecio()}</i></label>
                                                <img src="ControladorImg?id=${p.getId()}" width="200" height="170">
                                            </div>
                                            <div class="card-footer">
                                                <div class="col-sm-12">
                                                    <label>${p.getDescripcion()}</label>                                   
                                                </div>
                                                <%if (session.getAttribute("email") == "pass") {


                                                %>
                                                <div class=" col-sm-12 text-center">                                
                                                    <a href="Controlador?accion=AgregarCarritoOfimatica&id=${p.getId()}" class="btn btn2 btn-outline-primary">Agregar a Carrito<i class="fas fa-cart-plus"></i></a>
                                                    <a href="Controlador?accion=Comprar&id=${p.getId()}" class="btn btn-danger">Comprar</a>
                                                </div>  
                                                <%                                        }
                                                %>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:if>
                    </c:if>
                </c:forEach>
            </div>
        </div>    
        <!--6° CAJA -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="mx-auto" id="navbarTogglerDemo03">
                <h3 style="color: white">Caja</h3>   
            </div>
        </nav>
        <div class="container mt-4">
            <div class="row">
                <label>La tarjeta madre, siendo el componente mas importante de todos, ya que es el que hace posible la transitividad de los datos y sus
                    componentes, empezando siempre por este componente, tenemos estas tres opciones de BOARDS gamers que son ideales para un procesamiento
                    de datos y amplia gama de mejora en cuanto a sus componentes que se puedan agregar.</label>
            </div>
        </div>
        <div class="container mt-4">
            <div class="row">
                <c:forEach var="p" items="${productos}">
                    <c:if test="${p.getEstado() == 'ACTIVO'}">
                        <c:if test="${p.getNombres() == 'TORRE OFICINA'}">
                            <c:if test="${p.getCategoria() == 'OFIMATICA'}">
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <div class="card">
                                            <div class="card-header">
                                                <label class="col-sm-12">${p.getNombres()}</label>                                    
                                            </div>
                                            <div class="card-body text-center d-flex">
                                                <label><i class="fas fa-dollar-sign">${p.getPrecio()}</i></label>
                                                <img src="ControladorImg?id=${p.getId()}" width="200" height="170">
                                            </div>
                                            <div class="card-footer">
                                                <div class="col-sm-12">
                                                    <label>${p.getDescripcion()}</label>                                   
                                                </div>
                                                <%if (session.getAttribute("email") == "pass") {


                                                %>
                                                <div class=" col-sm-12 text-center">                                
                                                    <a href="Controlador?accion=AgregarCarritoOfimatica&id=${p.getId()}" class="btn btn2 btn-outline-primary">Agregar a Carrito<i class="fas fa-cart-plus"></i></a>
                                                    <a href="Controlador?accion=Comprar&id=${p.getId()}" class="btn btn-danger">Comprar</a>
                                                </div>  
                                                <%                                        }
                                                %>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:if>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <!--7° MONITOR -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="mx-auto" id="navbarTogglerDemo03">
                <h3 style="color: white">Monitor</h3>   
            </div>
        </nav>
        <div class="container mt-4">
            <div class="row">
                <label>La tarjeta madre, siendo el componente mas importante de todos, ya que es el que hace posible la transitividad de los datos y sus
                    componentes, empezando siempre por este componente, tenemos estas tres opciones de BOARDS gamers que son ideales para un procesamiento
                    de datos y amplia gama de mejora en cuanto a sus componentes que se puedan agregar.</label>
            </div>
        </div>
        <div class="container mt-4">
            <div class="row">
                <c:forEach var="p" items="${productos}">
                    <c:if test="${p.getEstado() == 'ACTIVO'}">
                        <c:if test="${p.getNombres() == 'Monitor de Oficina'}">
                            <c:if test="${p.getCategoria() == 'OFIMATICA'}">
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <div class="card">
                                            <div class="card-header">
                                                <label class="col-sm-12">${p.getNombres()}</label>                                    
                                            </div>
                                            <div class="card-body text-center d-flex">
                                                <label><i class="fas fa-dollar-sign">${p.getPrecio()}</i></label>
                                                <img src="ControladorImg?id=${p.getId()}" width="200" height="170">
                                            </div>
                                            <div class="card-footer">
                                                <div class="col-sm-12">
                                                    <label>${p.getDescripcion()}</label>                                   
                                                </div>
                                                <%if (session.getAttribute("email") == "pass") {


                                                %>
                                                <div class=" col-sm-12 text-center">                                
                                                    <a href="Controlador?accion=AgregarCarritoOfimatica&id=${p.getId()}" class="btn btn2 btn-outline-primary">Agregar a Carrito<i class="fas fa-cart-plus"></i></a>
                                                    <a href="Controlador?accion=Comprar&id=${p.getId()}" class="btn btn-danger">Comprar</a>
                                                </div>  
                                                <%                                        }
                                                %>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:if>
                    </c:if>
                </c:forEach>
            </div>
        </div>            
        <!-- Modal Iniciar Session o Registrarse -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="container col-lg-9">                   
                    <div class="modal-content">                   
                        <div class="pr-2 pt-1">                         
                            <button type="button" class="close" data-dismiss="modal">
                                <span aria-hidden="true">&times;</span>
                            </button>                    
                        </div>
                        <div class="text-center">                         
                            <img src="img/user.png" width="100" height="100">                         
                        </div>
                        <div class="modal-header text-center">                      
                            <ul class="nav nav-pills">                           
                                <li class="nav-item">
                                    <a class="nav-link active" data-toggle="pill" href="#pills-iniciar">Iniciar Sesion</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" data-toggle="pill" href="#pills-registrar">Registrarse</a>
                                </li>                          
                            </ul>  
                        </div>
                        <div class="modal-body"> 
                            <div class="tab-content" id="pills-tabContent">
                                <!-- Iniciar Session -->
                                <div class="tab-pane fade show active" id="pills-iniciar" role="tabpanel">
                                    <form action="Controlador">
                                        <div class="form-group">
                                            <label>Email address</label>
                                            <input type="email" name="txtemail" class="form-control" placeholder="email@example.com">
                                        </div>
                                        <div class="form-group">
                                            <label>Password</label>
                                            <input type="password" name="txtpass" class="form-control" placeholder="Password">
                                        </div>                                   
                                        <button type="submit" name="accion" value="Validar" class="btn btn-outline-danger btn-block">Iniciar</button>
                                    </form>
                                </div>
                                <!-- Registrarse -->
                                <div class="tab-pane fade" id="pills-registrar" role="tabpanel">
                                    <form action="Controlador">                               
                                        <div class="form-group">
                                            <label>Nombres</label>
                                            <input type="text" name="txtnom" class="form-control" placeholder="Leo Perez" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Dni</label>
                                            <input type="text" maxlength="8" name="txtdni" class="form-control" placeholder="01245678" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Direccion</label>
                                            <input type="text" name="txtdire" class="form-control" placeholder="Chiclayo - La Victoria" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Email address</label>
                                            <input type="email" name="txtemail" class="form-control" placeholder="email@example.com" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Password</label>
                                            <input type="password" name="txtpass" class="form-control" placeholder="Password" required>
                                        </div>                                  
                                        <button type="submit" name="accion" value="Registrar" class="btn btn-outline-danger btn-block">Crear Cuenta</button>
                                    </form>
                                </div>                          
                            </div> 
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="js/index.js" type="text/javascript"></script>
    </body>
</html>
