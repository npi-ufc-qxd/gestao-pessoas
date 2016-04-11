<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="permitAll">
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-npi">
				<span class="sr-only">NPI</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<sec:authorize access="hasRole('ROLE_ESTAGIARIO_NPI')">
				<a class="navbar-brand" href="<c:url value="/estagiario/" />"><span class="fa fa-group"></span> Gestão de Pessoas</a>
			</sec:authorize>
			<sec:authorize access="hasRole('DISCENTE')">
				<a class="navbar-brand" href="<c:url value="/home/meu-cadastro/" />"><span class="fa fa-group"></span> Gestão de Pessoas</a>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_SUPERVISOR', 'DOCENTE')">
				<a class="navbar-brand" href="<c:url value="/supervisor/" />"><span class="fa fa-group"></span> Gestão de Pessoas</a>
			</sec:authorize>
		</div>
		
		<div class="collapse navbar-collapse" id="navbar-npi">

			<sec:authorize access="hasAnyRole('ROLE_SUPERVISOR', 'DOCENTE')">
				<ul class="nav navbar-nav menu">
					<li id="turmas"><a href="<c:url value="/supervisor/turmas" />"><span class="fa fa-folder-open"></span> Minhas Turmas</a></li>
				</ul>
			</sec:authorize>

			<sec:authorize access="hasRole('ROLE_ESTAGIARIO_NPI')">

				<ul class="nav navbar-nav menu">
					<li id="turmas"><a href="<c:url value="/estagiario/turmas" />"><span class="fa fa-folder-open"></span> Minhas Turmas</a></li>
				</ul>
			</sec:authorize>
			
			
			<ul class="nav navbar-right navbar-nav">
				<li class="dropdown">
					
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"><b><strong> ${usuario.nome}&nbsp </strong></b> <i class="glyphicon glyphicon-user"></i><i class="glyphicon glyphicon-chevron-down"></i></a>
					<ul class="dropdown-menu">
						
						<sec:authorize access="hasAnyRole('ROLE_ESTAGIARIO_NPI')">
							
							<li><a href="<c:url value="/estagiario/meus-dados" />"><i class="glyphicon glyphicon-user"></i> Meus Dados</a></li>
							<li class="divider"></li>
						</sec:authorize>

						<sec:authorize access="hasAnyRole('ROLE_SUPERVISOR', 'DOCENTE')">
<%-- 							<li><a href="<c:url value="#/supervisor/meus-dados" />"><i class="glyphicon glyphicon-user"></i> Meus Dados</a></li> --%>
						</sec:authorize>
<%-- 						<li><a href="<c:url value="#/home/sobre" />"><i class="glyphicon glyphicon-info-sign"></i> Sobre</a></li> --%>
						<li><a href="<c:url value="/j_spring_security_logout" />"><i class="glyphicon glyphicon-off"></i> Sair</a></li>
					</ul>
				</li>  
			</ul>
		</div>
	</div>
</nav>
</sec:authorize>
<br><br><br>