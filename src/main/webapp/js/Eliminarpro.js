/* global swal */

$(document).ready(
        function () {
            $("tr .eliminarpro").click(function (e) {
                e.preventDefault();
                var idp = $(this).val();
                swal({
                    title: "¿Está seguro de eliminar?",
                    text: "¡Una vez eliminado deberá agregar de nuevo!",
                    icon: "warning",
                    buttons: true,
                    dangerMode: true
                }).then((willDelete) => {
                    if (willDelete) {
                        confirmar(idp).then((willDelete) => {
                            parent.location.href = "Controlador?accion=NuevoProducto";
                        });
                    }
                });
            });
            function confirmar(idp) {
                var url = "Controlador?accion=existeproducto&id=" + idp;
                $.ajax({
                    type: 'POST',
                    url: url,
                    async: true,
                    success: function (data) {
                        var existe = data.existe;
                        if (existe) {
                            swal("¡El registro está siendo usado, no se puede eliminar! ", {
                                icon: "warning"
                            });
                        } else {
                            eliminar(idp);
                            swal("¡Oh! ¡Registro Borrado! ", {
                                icon: "success"
                            }).then((willDelete) => {
                                if (willDelete) {
                                    parent.location.href = "Controlador?accion=NuevoProducto";
                                }
                            });
                        }
                    }
                });
            }
            function eliminar(idp) {
                var url = "Controlador?accion=eliminar&id=" + idp;
                $.ajax({
                    type: 'POST',
                    url: url,
                    async: true,
                    success: function (r) {
                    }
                });
            }
        });


