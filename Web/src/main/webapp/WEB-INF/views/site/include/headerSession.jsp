
<header>
	<div class="container">
		<div class="head-izq">
			<%--logotipo patrimonial o normal, según flag en sesión S = si, N = no--%>			
			<c:if test="${afirmeNetUser.patrimonial == 'N'}">
	   			<a id="logo" href='${context}/home.htm'><img src='<c:url value="/resources/img/icons/logotipo.png"/>'  alt="Afirme"></a>
			</c:if>
			<c:if test="${afirmeNetUser.patrimonial == 'S'}">
	   			<a id="logo" href='${context}/home.htm'><img src='<c:url value="/resources/img/icons/logotipo_p.png"/>'  alt="Afirme"></a>
			</c:if>
							
			 
			<a class="btn-menu"> 
				<span class="linea"></span>
				<span class="linea"></span>
				<span class="linea"></span> 
			</a>
		</div>

		<div class="head-der">

			<div class="head-btns">
				<a class="avatar"> <img src='<c:url value="${sessionScope.afirmeNetUser.avatar}"/>'
					height="36" width="36" alt="${sessionScope.afirmeNetUser.nombreCorto}"> 
<%-- 				<span class="notificaciones">${afirmeNetUser.notificaciones}</span> --%>
				</a> 
				<a href="#" class="btn" onclick="submitFormAnimate('menu','login/logout.htm')">
<%-- 				<a href="${context}/modaLogout.htm" data-fancybox-type="iframe" class="modal btn"> --%>
				<span class="txt"><spring:message code="afirmenet.header.cerrarSession"/></span><span
					class="icon icon-exit"></span></a>
							
			</div>
			<div class="head-datos">
				<p> <spring:message code="afirmenet.header.saludo"/> <strong>${sessionScope.afirmeNetUser.nombreLargo}</strong>
				</p>
				<p class="f12">
					<spring:message code="afirmenet.header.contrato"/> <strong>${sessionScope.afirmeNetUser.contrato}</strong> 
					<spring:message code="afirmenet.header.ultimoAcceso"/>  <strong>${sessionScope.afirmeNetUser.ultimoAccesoStr}</strong>
				</p>
			</div>

		</div>

	</div>

</header>