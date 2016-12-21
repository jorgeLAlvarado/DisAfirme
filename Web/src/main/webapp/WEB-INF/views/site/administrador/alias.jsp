<%--<%@ include file="/WEB-INF/views/base/include/include.jsp"%>
<link href='<c:url value="/resources/css/login.css"/>' rel="stylesheet" type="text/css">
<%@ include file="/WEB-INF/views/base/include/header.jsp"%>
<script src='<c:url value="/resources/js/loginServices.js"/>' type="text/javascript"></script>
--%>
<script type="text/javascript">
$(document).ready(function () {

	preventDoubleSubmit($('#alias'), $('#continuar'));
	preventDoubleSubmit($('#aliasConf'), $('#continuar'));
	
	showMessage('informacion', '<spring:message code="afirmenet.etiqueta.conf.alias.tooltip.mensaje"/>', '');

	$('#continuar').click(function() {
		submitted = true;
		if ($('#alias').val() == "") {
			showMessage('advertencia', '<spring:message code="afirmenet.alias.aliasRequerido" />', '');
			$('#alias').css('border-color', 'red');
			submitted = false;
		} else if ($('#aliasConf').val() == "") {
			showMessage('advertencia', '<spring:message code="afirmenet.alias.confirmacionRequerido" />', '');
			$('#alias').css('border-color', '');
			$('#aliasConf').css('border-color', 'red');
			submitted = false;
		} else if ($('#alias').val() != $('#aliasConf').val()) {
			showMessage('advertencia', '<spring:message code="afirmenet.alias.errorCoincide" />', '');
			$('#alias').css('border-color', '');
			$('#aliasConf').css('border-color', '');
			submitted = false;
		} else {
			submitFormAnimate($('#login').attr('id'), $('#login').attr('action'));
		}
	});
});
</script>

<form:form method="POST" commandName="login" action="controlAcceso/alias.htm">
<form:hidden path="contrato" />
<form:hidden path="serialToken" />

  <div class="aviso"><div class="container">
	
    <h3 align="center">
   	<c:choose>
   	<c:when test="${not empty activacion}">
   		<input type="hidden" name="activacion" value="true" />
   		<span class="op50"><spring:message code="afirmenet.activacion" /></span><br />
		<span class="op50"><spring:message code="afirmenet.pasos.paso5de" /><spring:message code="afirmenet.pasos.totalActivacion" /></span><br />
   	</c:when>
   	<c:when test="${empty activacion}">
   		<span class="op50"><spring:message code="afirmenet.alias" /></span><br/>
    	<span class="op50"><spring:message code="afirmenet.pasos.paso1de" /><spring:message code="afirmenet.pasos.totalAlias" /></span><br/>
   	</c:when>
   	</c:choose>
    	<strong><spring:message code="afirmenet.alias.paso1" /></strong>
    </h3>

    <div class="login">

      <div class="campo">
        <div class="label"><label class="gris"><spring:message code="afirmenet.alias.alias" /></label></div>
        <div class="input"><input class="bgbco light req" maxlength="30" id="alias" name="alias" type="text" placeholder="Máximo 30 caracteres"></div>
      </div>

      <div class="campo">
        <div class="label"><label class="gris"><spring:message code="afirmenet.alias.confirma" /></label></div>
        <div class="input"><input class="bgbco light req" maxlength="30" id="aliasConf" name="aliasConf" type="text" placeholder="Máximo 30 caracteres"></div>
      </div>

      <div align="center" class="flex pt10">
        <a href="${context}/portal.htm" class="btn sec mr20"><spring:message code="afirmenet.cancelar" /></a>
        <button id="continuar" onclick="return false;" class="btn verde" type="button"><spring:message code="afirmenet.botones.continuar" /></button>
      </div>
      
    </div>

  </div></div>
  
</form:form>
<%--
 <%@ include file="/WEB-INF/views/base/include/footer.jsp"%> --%>