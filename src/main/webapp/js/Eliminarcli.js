$(document).ready(
        function () {
            $("tr .eliminarcli").click(function (e) {
                e.preventDefault();
                var idc = $(this).val();
                swal({
                    title: "¿Está seguro de eliminar el cliente?",
                    text: "¡Una vez eliminado deberá agregarlo de nuevo!",
                    icon: "warning",
                    buttons: true,
                    dangerMode: true
                }).then((willDelete) => {
                    if (willDelete) {
                        confirmar(idc).then((willDelete) => {
                            parent.location.href = "Controlador?accion=NuevoCliente";
                        });
                    }
                });
            });
            function confirmar(idc) {
                var url = "Controlador?accion=existecliente&id=" + idc;
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
                            eliminar(idc);
                            swal("¡Oh! ¡Registro Borrado! ", {
                                icon: "success"
                            }).then((willDelete) => {
                                if (willDelete) {
                                    parent.location.href = "Controlador?accion=NuevoCliente";
                                }
                            });
                        }
                    }
                });
            }
            function eliminar(idc) {
                var url = "Controlador?accion=ClienteEliminar&id=" + idc;
                $.ajax({
                    type: 'POST',
                    url: url,
                    async: true,
                    success: function (r) {
                    }
                });
            }
        });


