 
            <div class="col6 xs-break xs-mb20">
                <p class="h6 mb5 f-gris6">
                <c:choose>
				    <c:when test="${sessionScope.afirmeNetUser.basicoSinToken}"><spring:message code="afirmenet.datosAcceso.password"/></c:when>
				    <c:otherwise><spring:message code="afirmenet.token.clave"/></p></c:otherwise>
				</c:choose>                
                <div class="relative input">
                  <input autocomplete="off" name="token" id="token" maxlength="
	                <c:choose>
					    <c:when test="${sessionScope.afirmeNetUser.basicoSinToken}">10</c:when>
					    <c:otherwise>6</c:otherwise>
					</c:choose>" 
					placeholder="
					<c:choose>
					    <c:when test="${sessionScope.afirmeNetUser.basicoSinToken}">Contraseña</c:when>
					    <c:otherwise>Solo números</p></c:otherwise>
					</c:choose>
					" type="password" class='	               
					<c:choose>
					    <c:when test="${sessionScope.afirmeNetUser.basicoSinToken}"></c:when>
					    <c:otherwise>onlyNum </p></c:otherwise>
					</c:choose>
					 req' />
                  <a class="ayuda tooltip t-serv"><spring:message code="afirmenet.ayuda.icon"/>
                  	<span>
                  	 <c:choose>
					    <c:when test="${sessionScope.afirmeNetUser.basicoSinToken}"><spring:message code="afirmenet.tooltip.login.contraseña"/></span></c:when>
					    <c:otherwise><spring:message code="afirmenet.tooltip.trans.token"/></span></c:otherwise>
					</c:choose> 
                  	
                  </a> 
                </div>
            </div>