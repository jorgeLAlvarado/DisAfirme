<div class="btop2 p20-0" id="paginacion">
	<div class="pag">
		<ul>
			<li><a
				href="${context}/inversiones/ahorrafirme/pagina.htm?page=1"
				class="
			<c:choose><c:when test="${paginacion.paginaActual eq 1}">disabled</c:when>
			<c:otherwise>f-verde</c:otherwise></c:choose>
			">|<span
					class="icon icon-left"></span> <span class="hide-xs">PRIMERA</span></a></li>
			<li><a
				href="${context}/inversiones/ahorrafirme/pagina.htm?page=${paginacion.paginaActual-1}"
				class="
			<c:choose><c:when test="${paginacion.paginaActual eq 1}">disabled</c:when>
			<c:otherwise>f-verde</c:otherwise></c:choose>
			"><span
					class="icon icon-left"></span></a></li>

			<c:forEach var="pagina" items="${paginacion.lstPaginas}">
				<c:if test="${pagina <= paginacion.paginaActual+5}">
					<li><a
						href="${context}/inversiones/ahorrafirme/pagina.htm?page=${pagina}"
						class="
				<c:if test="${paginacion.paginaActual eq pagina}">act</c:if>">${pagina}
					</a></li>
				</c:if>
			</c:forEach>

			<li><a
				href="${context}/inversiones/ahorrafirme/pagina.htm?page=${paginacion.paginaActual+1}"
				class="
			<c:choose><c:when test="${paginacion.paginaActual >= paginacion.paginas}">disabled</c:when>
			<c:otherwise>f-verde</c:otherwise></c:choose>">
					<span class="icon icon-right"></span>
			</a></li>
			<li><a
				href="${context}/inversiones/ahorrafirme/pagina.htm?page=${paginacion.paginas}"
				class="
			<c:choose><c:when test="${paginacion.paginaActual >= paginacion.paginas}">disabled</c:when>
			<c:otherwise>f-verde</c:otherwise></c:choose>
			"><span
					class="hide-xs">ÚLTIMA</span> <span class="icon icon-right"></span>|</a></li>
		</ul>
	</div>
</div>
<input type="hidden" id="actionPaginacion" value="${actionPaginacion}" />
<input type="hidden" id="paginas" value="${consulta.paginas}" />
<form:form method="POST" commandName="consulta" action="#">
	<form:hidden path="paginaActual" />
	<form:hidden path="cuenta" />
	<form:hidden path="cuentaDesc" />
	<form:hidden path="cuentaMoneda" />
	<form:hidden path="cuentaNombre" />
	<form:hidden path="cuentaClabe" />
	<form:hidden path="clave" />
	<form:hidden path="fechaInicio" />
</form:form>
<script type="text/javascript">
	var actionPaginacion = $("#actionPaginacion").val();
	function cambiaPagina(pagina) {
		if (pagina == $('#paginaActual').val()) {
			return;
		}
		if (pagina < 1) {
			return;
		}
		if (pagina > $('#paginas').val()) {
			return;
		}
		$('#paginaActual').val(pagina);
		$('#consulta').attr('action', actionPaginacion);
		$('body').append('<div id="loading"></div>');
		$('#loading').fadeIn(1, function() {
			$('#consulta').submit();
		});
		return;
	}
	function mesAnterior() {
		$('#consulta').attr('action', actionPaginacion);
		$('#paginaActual').val('1');
		$('body').append('<div id="loading"></div>');
		$('#loading').fadeIn(1, function() {
			$('#consulta').submit();
		});
		return;
	}
	function mesActual() {
		$('#consulta').attr('action', actionPaginacion);
		$('#paginaActual').val('1');
		$('body').append('<div id="loading"></div>');
		$('#loading').fadeIn(1, function() {
			$('#consulta').submit();
		});
		return;
	}
	$('.disabled').click(function(e) {
		e.preventDefault();
	});
</script>