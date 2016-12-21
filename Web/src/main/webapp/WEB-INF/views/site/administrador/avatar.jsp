<%--<%@ include file="/WEB-INF/views/base/include/include.jsp"%>
<link href='<c:url value="/resources/css/login.css"/>' rel="stylesheet" type="text/css">
<%@ include file="/WEB-INF/views/base/include/header.jsp"%>
<script src='<c:url value="/resources/js/loginServices.js"/>' type="text/javascript"></script>
--%>
<script>
$(document).ready(function() {

	$('.op-avatar').each(function() {
		var img = $(this).data('img');
		$(this).css('background-image','url('+ img +')');
	});
	
	$('.op-avatar').click(function() {
		if ($(this).hasClass('sel')) {
			$('.op-avatar').removeClass('sel op50');
			$('#avatar').val('');
		} else {
			$(this).removeClass('op50').addClass('sel');
			$('#avatar').val(this.id);
			$('.op-avatar').not(this).removeClass('sel').addClass('op50');
		}
	});
	
	$('#continuar').click(function() {
		if ($('#avatar').val() != "") {
			submitFormAnimate($('#login').attr('id'), $('#login').attr('action'));
		} else {
			showMessage('advertencia', 'Por favor seleccione una de las imagenes como su avatar.', '');
		}
	});

	$('.login').css('width', '100%');
	$('.clearfix').css('margin', '0px auto');
	
	resizeDiv($(window));
	
	$(window).resize(function() {
		resizeDiv(this);
	});
});

function resizeDiv(element) {
	if ($(element).width() < 550) {
		$('.clearfix').css('width', '100%');
	} else {
		$('.clearfix').css('width', '65%');
	}
}
</script>

<form:form method="POST" commandName="login" action="controlAcceso/aliasConfirma.htm">
<form:hidden path="contrato"/>
<form:hidden path="alias"/>
<form:hidden path="serialToken" />

<input type="hidden" name="avatar" id="avatar" />

 <div class="aviso"><div class="container">

    <h3 align="center">
    <c:choose>
   	<c:when test="${not empty activacion}">
   		<input type="hidden" name="activacion" value="true" />
   		<span class="op50"><spring:message code="afirmenet.activacion" /></span><br />
		<span class="op50"><spring:message code="afirmenet.pasos.paso6de" /><spring:message code="afirmenet.pasos.totalActivacion" /></span><br />
   	</c:when>
   	<c:when test="${empty activacion}">
   		<span class="op50"><spring:message code="afirmenet.alias" /></span><br/>
    	<span class="op50"><spring:message code="afirmenet.pasos.paso2de" /><spring:message code="afirmenet.pasos.totalAlias" /></span><br/>
   	</c:when>
   	</c:choose>
    	<strong><spring:message code="afirmenet.alias.paso2" /></strong>
    </h3>
    
    <h6 align="center"><strong><spring:message code="afirmenet.alias.avatar" /></strong></h6>

    <div class="login">

      <div class="clearfix">
		<c:forEach var="avatar" items="${lstAvatar}">
			<a class="op-avatar" data-img="<c:url value="${pathAvatar}${avatar.avatar}.jpg"/>" id="${avatar.avatar}"></a>
		</c:forEach>
      </div>

      <div align="center" class="flex pt10">
        <a href="${context}/portal.htm" class="btn sec mr20"><spring:message code="afirmenet.cancelar" /></a>
        <button id="continuar" onclick="return false;" class="btn verde"><spring:message code="afirmenet.botones.continuar" /></button>
      </div>
      
    </div>

  </div></div>
</form:form>
<%--
<%@ include file="/WEB-INF/views/base/include/footer.jsp"%> --%>