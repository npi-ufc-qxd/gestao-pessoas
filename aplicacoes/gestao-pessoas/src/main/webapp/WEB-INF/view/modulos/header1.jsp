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
				<a class="navbar-brand" href="<c:url value="/estagiario/inicio" />"><span class="fa fa-group"></span> Gestão NPI</a>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_SUPERVISOR', 'DOCENTE')">
				<a class="navbar-brand" href="<c:url value="/supervisor/inicio" />"><span class="fa fa-group"></span> Gestão NPI</a>
			</sec:authorize>
		</div>
		
		<div class="collapse navbar-collapse" id="navbar-npi">

			<sec:authorize access="hasAnyRole('ROLE_SUPERVISOR', 'DOCENTE')">
				<ul class="nav navbar-nav menu">
					<li id="periodos"><a href="<c:url value="/supervisor/periodos" />"><span class="fa fa-calendar"></span> Periodos</a></li>

					<li id="turmas"><a href="<c:url value="/supervisor/minhas-turmas" />"><span class="fa fa-th-list"></span> Minhas Turmas</a></li>

					<li id="projetos"><a href="<c:url value="/supervisor/projetos" />"><span class="fa fa-briefcase"></span> Projetos</a></li>

					<li id="estagiarios"><a href="<c:url value="/supervisor/estagiarios" />"><span class="fa fa-user"></span> Estagiários</a></li>

					<li id="menu-frequencias"><a href="<c:url value="/supervisor/frequencias" />"><span class="fa fa-calendar-check-o"></span> Frequência</a></li>

					<li id="menu-reposicao"><a href="<c:url value="/frequencia/reposicao" />">Reposições</span></a></li>
				</ul>
			</sec:authorize>

			<sec:authorize access="hasRole('ROLE_ESTAGIARIO_NPI')">
				<ul class="nav navbar-nav menu">
					<li id="minha-presenca"><a href="<c:url value="/estagiario/minha-presenca" />"><span class="fa fa-calendar-check-o"></span> Minha Presença</a></li>

					<li id="meu-projeto"><a href="<c:url value="/estagiario/meu-projeto" />"><span class="fa fa-briefcase"></span> Meu Projeto</a></li>

<%-- 					<li id="documentos"><a href="<c:url value="/estagiario/documentos" />"><span class="fa fa-folder-open"></span> Documentos</a></li> --%>
					
<%-- 					<li id="avaliacao"><a href="<c:url value="/estagiario/avaliacao" />"><span class="fa fa-check-square-o"></span> Avaliação</a></li> --%>
				</ul>
			</sec:authorize>

			<ul class="nav navbar-right navbar-nav">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-user"></i> <i class="glyphicon glyphicon-chevron-down"></i></a>
					<ul class="dropdown-menu">
						<sec:authorize access="hasRole('ROLE_ESTAGIARIO_NPI')">
							<li><a href="<c:url value="/estagiario/meus-dados" />"><i class="glyphicon glyphicon-user"></i> Meus Dados</a></li>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('ROLE_SUPERVISOR', 'DOCENTE')">
							<li><a href="<c:url value="#/supervisor/meus-dados" />"><i class="glyphicon glyphicon-user"></i> Meus Dados</a></li>
						</sec:authorize>
						<li><a href="<c:url value="/j_spring_security_logout" />"><i class="glyphicon glyphicon-off"></i> Sair</a></li>
						<li class="divider"></li>
						<li><a href="<c:url value="#/home/sobre" />"><i class="glyphicon glyphicon-info-sign"></i> Sobre</a></li>
					</ul>
				</li>  
			</ul>
		</div>
	</div>
</nav>
</sec:authorize>
<br><br><br>