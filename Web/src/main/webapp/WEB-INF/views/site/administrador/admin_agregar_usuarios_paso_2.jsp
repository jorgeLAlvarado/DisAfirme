<!DOCTYPE html>

<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<head>
  <link rel="icon" href="favicon.ico" type="image/vnd.microsoft.icon" /> 
  <link rel="shortcut icon" href="favicon.ico" type="image/vnd.microsoft.icon" /> 
  <link rel="apple-touch-icon" href="apple-touch-icon.png" />
  <link rel="apple-touch-icon" sizes="72x72" href="apple-touch-icon-72x72-precomposed.png" />
  <link rel="apple-touch-icon" sizes="114x114" href="apple-touch-icon-114x114-precomposed.png" />
  <link rel="apple-touch-icon" sizes="144x144" href="apple-touch-icon-144x144-precomposed.png" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta http-equiv="Content-Language" content="es-MX" />
  <meta name="title" content="" /> 
  <meta name="author" content="" />
  <meta name="copyright" content="" />
  <meta name="Language" content="Spanish" />
  <meta name="apple-mobile-web-app-capable" content="yes" />
  <meta name="robots" content="all | index | follow" />
  <meta name="description" content="" />
  <meta name="keywords" content="" />
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
  <title>Afirme | Agregar / Modificar Usuarios</title>
  
  <link href="assets/css/main.css" rel="stylesheet" type="text/css" />
  <link href="assets/css/dropkick.css" rel="stylesheet" type="text/css" />
  <link href="assets/css/transaccion.css" rel="stylesheet" type="text/css" />
  <script src="assets/js/vendor/modernizr-2.6.2.min.js"></script>
</head>
<body id="administrador">

  <header><div class="container">

    <div class="head-izq">
      <a id="logo" href="HOME_home-comercial.html"><img src="assets/img/icons/logotipo.png" alt="Afirme"></a>

      <a class="btn-menu">
        <span class="linea"></span><span class="linea"></span><span class="linea"></span>
        <span class="notificaciones">10</span>
      </a>
    </div>

    <div class="head-der">

      <div class="head-btns">
        <a class="avatar">
          <img src="assets/img/photos/avatar2.png" height="36" width="36" alt="Jessica Arévalo">
          <span class="notificaciones">10</span>
        </a>
        <a href="#" class="btn"><span class="txt">CERRAR SESIÓN</span><span class="icon icon-exit"></span></a>
      </div>
      <div class="head-datos">
        <p>¡Bienvenida! <strong>Ing. Jessica Andrea Lissette Arévalo Betancur</strong></p>
        <p class="f12">Contrato: <strong>1234567890</strong> Último acceso: <strong>25-nov-14, 16:30 hrs.</strong></p>
      </div>

    </div>

  </div></header>

  <section><div class="container">

    <article>

      <div class="col-der relative">

        <div class="contenido">

          <div class="p20">

            <div class="mb10">
              <h3 class="titulo">Agregar / Modificar Usuarios</h3>
            </div>

            <a href="#" class="cerrar2"></a>
            <div class="mb30"><div class="pasos">
              <span class="paso activo"><span>1 <small class="hide-xs">Datos del Usuario</small></span></span>
              <span class="paso activo"><span>2 <small class="hide-xs">Permisos de Usuario</small></span></span>
              <span class="paso"><span>3 <small class="hide-xs">Editar Contraseña</small></span></span>
              <span class="paso"><span>4 <small class="hide-xs">Confirmación</small></span></span>
            </div></div>

            <div class="clearfix mb20">
              <h3 class="fL f-verdeO m0 mr20">Permisos de Usuario</h3>
              <h6 class="fL m0 mt7"><span class="op50">Campo Obligatorio</span><span class="f-verdeO">*</span></h6>
            </div>

            <div class="bbot2 mb20"><div class="row mb20">

              <div class="col6 xs-col12">

                <fieldset>
                  <div class="form-label w100"><label for="importe"><strong>Tipo Usuario</strong><span class="f-verdeO mr10">*</span><a class="ayuda">?</a></label></div>
                  <div class="form-input w100 mt10 clearfix">
                    <div class="fL mr20"><input type="radio" id="radio01" name="radio" class="radio" checked /><label for="radio01">Operador</label></div>
                    <div class="fL mr20"><input type="radio" id="radio02" name="radio" class="radio" /><label for="radio02">Supervisor</label></div>
                    <div class="fL mr20"><input type="radio" id="radio03" name="radio" class="radio" /><label for="radio03">Ambos</label></div>
                  </div>
                </fieldset>

                <fieldset>
                  <div class="form-label w100"><label for="importe"><strong>Alta de Cuentas</strong><span class="f-verdeO mr10">*</span></label></div>
                  <div class="form-input w100 mt10 clearfix">
                    <div class="fL mr20"><input type="radio" id="radio201" name="radio2" class="radio" checked /><label for="radio201">Permitir</label></div>
                    <div class="fL mr20"><input type="radio" id="radio202" name="radio2" class="radio" /><label for="radio202">No Permitir</label></div>
                  </div>
                </fieldset>

                <fieldset class="mb20">
                  <div class="form-label w100"><label for="importe"><strong>Activar Cuentas</strong><span class="f-verdeO mr10">*</span></label></div>
                  <div class="form-input w100 mt10 clearfix">
                    <div class="fL mr20"><input type="radio" id="radio301" name="radio3" class="radio" checked /><label for="radio301">Permitir</label></div>
                    <div class="fL mr20"><input type="radio" id="radio302" name="radio3" class="radio" /><label for="radio302">No Permitir</label></div>
                  </div>
                </fieldset>

                <fieldset>
                  <div class="form-label w100"><label for="importe">Autorización Alta de Beneficiarios<span class="f-verdeO mr10">*</span></label></div>
                  <div class="form-input w100 mt10 clearfix">
                    <select class="drop">
                      <option>Seleccionar</option>
                      <option>Requiere 1 autorización</option>
                    </select>
                  </div>
                </fieldset>
                
              </div>

              <div class="col4 off2 xs-col12">

                <div class="bgf1 p20 mr-20">

                  <div class="mb10">
                    <h6 class="m0"><strong>Usuario</strong></h6>
                    <p class="h6">DEFINE</p>          
                  </div>

                  <div class="mb10">
                    <h6 class="m0"><strong>Nombre</strong></h6>
                    <p class="h6">Luis Ricardo Pérez González</p>          
                  </div>

                  <div class="mb10">
                    <h6 class="m0"><strong>Título</strong></h6>
                    <p class="h6">Contador</p>          
                  </div>

                  <div class="mb10">
                    <h6 class="m0"><strong>E-mail</strong></h6>
                    <p class="h6">luis@afirme.com</p>          
                  </div>

                  <div class="mb10">
                    <h6 class="m0"><strong>Estatus</strong></h6>
                    <p class="h6">Activo</p>          
                  </div>

                  <div class="mb10">
                    <h6 class="m0"><strong>Token</strong></h6>
                    <p class="h6">116877652</p>          
                  </div>

                </div>
                
              </div>

            </div></div>

            <div class="acciones" align="right">
              <a href="#" class="btn atras mr10">Atrás</a>
              <a href="ADMIN_Agregar-usuarios-paso-3.html" class="btn verde">Continuar</a>
            </div>

          </div>

        </div>

      </div>

    </article>

  </div></section>

  <footer><div class="container"><img src="assets/img/photos/globo.png" id="globo">

    <div class="row mb40 flex">

      <div class="col6">
        <img src="assets/img/icons/logotipo-20.png" alt="20 años">
      </div>

      <div class="col5 pt15" align="right">
        <div class="dib">
          <h6>Atención telefónica<br><a href="tel:018002234763">01 800 22 34 763</a></h6>
        </div>
        <a href="#" class="btn btn-lg lima">AYUDA</a>
      </div>

    </div>

    <div class="row">

      <div class="col6">
        <strong>Derechos Reservados Afirme Grupo Financiero 2015.</strong><br>
        <a href="#">Aviso de Privacidad</a> | <a href="#">Términos Legales</a>
      </div>

      <div class="col5" align="right">
        <p class="h6">Sellos de Seguridad</p>
        <img class="ml20" src="assets/img/icons/verisign.jpg" alt="Verisign">
        <img class="ml20" src="assets/img/icons/amipci.jpg" alt="AMIPCI">
      </div>

    </div>

  </div></footer>

  <script>window.jQuery || document.write('<script src="assets/js/jquery-1.11.0.min.js"><\/script>')</script>
  <script type="text/javascript" src="assets/js/main.js"></script>
  <script type="text/javascript" src="assets/js/vendor/dropkick.js"></script>
  <script type="text/javascript">
    $(document).ready(function(){

      // Dropdown
      $('.drop').dropkick({ 
        mobile: true,
        change: function(){ 
          $(this.data.elem).find('.dk-selected').addClass('f-verde');
        }
      }); 

    });
  </script>

</body>
</html>