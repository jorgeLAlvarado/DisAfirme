<%@ include file="/WEB-INF/views/base/include/include.jsp"%>
<%@ include file="/WEB-INF/views/base/include/headerSession.jsp"%>

<link href="<c:url value="/resources/css/transaccion.css"/>" rel="stylesheet" type="text/css" />

<script type="text/javascript">

	$(document).ready(function(){
		
		$('a.continuar').click(function() {
			submitFormAnimate($('#correoCuentaAfirme').attr('id'), 'configuraciones/Configuraciones.htm');
		});
	});
 
</script>

<section>
	<div class="container">
		<article>
			<div class="col-izq" id="menu-wrap">
				<%@ include file="/WEB-INF/views/site/comun/menu.jsp"%> 
			</div>
				
			<div class="col-der relative">
				<div class="contenido">
				<form:form commandName="correoCuentaAfirme" method="POST" action="">

					<div class="p20">
					
						<c:if test="${!correoCuentaAfirme.esReimpresion}">
							<c:choose>
	    						<c:when test="${empty errMsg}">
									<script type="text/javascript">
										mostrarNotificacionExitosa('<spring:message code="afirmenet.etiqueta.general.notificacion.exitosa.mensaje"/>', '<spring:message code="afirmenet.etiqueta.general.notificacion.exitosa"/>');
									</script>
								</c:when>    
	    						<c:otherwise>
	    							<script type="text/javascript">
										mostrarNotificacionError('${errMsg}', '<spring:message code="afirmenet.etiqueta.general.notificacion.error"/>');
									</script>
	          					</c:otherwise>
							</c:choose>
						</c:if>
          
						<div class="mb20">
			              <h3 class="titulo"><spring:message code="afirmenet.etiqueta.conf.correo.titulo"/></h3>   
			            </div>
			
			           <a href="#" class="cerrar"></a>
			            <div class="mb30 hide-print ovhidden"><div class="pasos">
			              <span class="paso activo"><span><spring:message code="afirmenet.etiqueta.general.wizard.numero.paso1"/> <small class="hide-xs"><spring:message code="afirmenet.etiqueta.conf.alias.paso1.cabecera"/></small></span></span>
			              <span class="paso activo"><span><spring:message code="afirmenet.etiqueta.general.wizard.numero.paso2"/> <small class="hide-xs"><spring:message code="afirmenet.etiqueta.conf.alias.paso2.cabecera"/></small></span></span>
			              <span class="paso activo"><span><spring:message code="afirmenet.etiqueta.general.wizard.numero.paso3"/> <small class="hide-xs"><spring:message code="afirmenet.etiqueta.conf.alias.paso3.cabecera"/></small></span></span>
			              <span class="paso activo"><span><spring:message code="afirmenet.etiqueta.general.wizard.numero.paso4"/> <small class="hide-xs"><spring:message code="afirmenet.etiqueta.conf.alias.paso4.cabecera"/></small></span></span>
			            </div></div>
			            
<!-- area de impresion - div id contenido -->   
<input type="hidden" name="contenidoHTML" id="contenidoHTML"/>
<div id="contenido">
	<div class="header-print"><div class="row">
            		<div class="col6"><img src="<c:url value="/resources/img/icons/logotipo.png"/>" alt="Afirme"/></div>
            		<div class="col6" align="right">
              	<h3 class="f-verdeO"><spring:message code="afirmenet.comprobantes.print.titulo"/></h3>
              	<%-- <p><strong><spring:message code="afirmenet.comprobantes.folioAfirme"/></strong> xxxxxxxx</p> --%>
              	<p><strong><spring:message code="afirmenet.comprobantes.fechaejecucion"/></strong> ${correoCuentaAfirme.fechaOperacionddMMYY} ${correoCuentaAfirme.horaOperacionHHmm} </p>
           	</div>
          </div></div>  
			            
			            <%-- resultado de la operacion --%>
			            <div class="row mb15">
              				<h3 class="f-verdeO col8 xs-col6"><spring:message code="afirmenet.comprobantes.resultado"/></h3>
              			</div>
			            <div class="bbot2 mb15">
			            	<div class="row">
              					<div class="col6 xs-break">
                					<div class="mb15">
	                					<h6 class="m0"><strong><spring:message code="afirmenet.etiqueta.general.comprobante.estatus.de.la.operacion"/></strong></h6>
	                					<p class="h6 f-verdeO"><spring:message code="afirmenet.etiqueta.general.comprobante.procesada"/></p>   
                					</div>
                					<div class="mb15">
                   						<h6 class="m0"><strong><spring:message code="afirmenet.comprobantes.fechasolicitud"/></strong></h6>
                      					<p class="h6 f-verdeO">
	                      					<spring:message code="afirmenet.comprobantes.dia"/> ${correoCuentaAfirme.fechaOperacionddMMYY}<br/>                      
		                  					<spring:message code="afirmenet.comprobantes.hora"/> ${correoCuentaAfirme.horaOperacionHHmm}
	    			  					</p>
                 					</div>
                				</div>
                				
                				<div class="col6 xs-break">
                					<div class="mb15">
                   						<h6 class="m0"><strong><spring:message code="afirmenet.etiqueta.conf.correo.comprobante.fecha.de.operacion.del.cambio"/></strong></h6>
                      					<p class="h6 f-verdeO">
	                      					<spring:message code="afirmenet.comprobantes.dia"/> ${correoCuentaAfirme.fechaActivacionddMMYY}<br/>                      
		                  					<spring:message code="afirmenet.comprobantes.hora"/> ${correoCuentaAfirme.horaActivacionHHmm}
	    			  					</p>
                 					</div> 
                				</div>
                				
                			</div>
                		</div>
			            
			            <%-- datos de la operacion    --%>
            			<div class="row mb15"><h3 class="f-verdeO col12 xs-col6"><spring:message code="afirmenet.comprobantes.datos"/></h3></div>
			            <div class="bbot2 mb15">
			            	<div class="row">
              					<div class="col6 xs-break">
              						<div class="mb15">
                  						<h6 class="m0"><strong><spring:message text="Code 809" code="afirmenet.comprobantes.tipooperacion"/></strong></h6>
                  						<p class="h6 f-verdeO"><spring:message code="afirmenet.etiqueta.conf.correo.titulo"/></p>
                					</div>
              						<div class="mb15">
                  						<h6 class="m0"><strong><spring:message code="afirmenet.etiqueta.general.comprobante.comision"/></strong></h6>
                  						<p class="h6 f-verdeO"><spring:message code="afirmenet.etiqueta.conf.contrasena.confirmacion.comision.sin.costo"/></p>
                					</div>
                					
              					</div>
              					<div class="col6 xs-break">
              						<div class="mb15">
                  						<h6 class="m0"><strong><spring:message code="afirmenet.etiqueta.conf.correo.comprobante.correo.anterior"/></strong></h6>
                  						<p class="h6 f-verdeO">${correoCuentaAfirme.correoActual}</p>
                					</div>
              						<div class="mb15">
                  						<h6 class="m0"><strong><spring:message code="afirmenet.etiqueta.conf.correo.comprobante.correo.nuevo"/></strong></h6>
                  						<p class="h6 f-verdeO">${correoCuentaAfirme.correoNuevo}</p>
                					</div>
                					
              					</div>
              				</div>
              			</div>
			            
			            
			            <%-- informacion de cuentas       --%>
            			<div class="row mb15">
            				<h3 class="f-verdeO col12 xs-col6"><spring:message code="afirmenet.comprobantes.info"/></h3>
            			</div>
           				<div class="row">
							<div class="col6 xs-break">
              					<div class="mb15">
                					<h6 class="m0"><strong><spring:message code="afirmenet.contrato"/></strong></h6>
                					<p class="h6 f-verdeO">${correoCuentaAfirme.contrato}</p>
              					</div>
               				</div>
               			</div>
			            
		                
		                <%-- <p> <span class="f-verdeO"><spring:message code="afirmenet.etiqueta.conf.correo.comprobante.nota.cambio.realizara.en.24.horas"/></span></p> --%>
		                <p> <span class="f-verdeO">${correoCuentaAfirme.mensajeUsuario24Horas}</span></p>
		                
</div> <!--  // area de impresion - div id contenido -->
            					                
		                <div class="bbot2 btop2 p15-10 hide-print"><div class="row">
			              <div class="col6 xs-break xs-center xs-mb20"><p class="f21 m0"><spring:message code="afirmenet.comprobantes.impresion"/></p></div>             
			              <div class="col6 hide-xs"><a href="#" class="h6 m0 f-verde" id="imprimir"><span class="icon icon-print f21 mr10"></span> <spring:message code="afirmenet.comprobantes.imprimir"/></a></div>
			            </div></div>
		                
		                <c:if test="${!correoCuentaAfirme.esReimpresion}">
	            			<div class="p15-20 xs-p100" align="right">
								<div class="acciones mt10 flex">
				              		<a href="#" class="btn verde continuar"><spring:message code="afirmenet.botones.continuar"/></a>
				              	</div>
				            </div>
						</c:if>
			            
					</div> <!-- div p20 -->
					</form:form>
				</div>
			</div>
		</article>
	</div>
</section>


<%@ include file="/WEB-INF/views/base/include/footerSession.jsp"%>