<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="permitAll">
<nav class="navbar navbar-inverse">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-npi">
				<span class="sr-only">NPI</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#"><span class="fa fa-group"></span> Gestão NPI</a>
		</div>
		
		<div class="collapse navbar-collapse" id="navbar-npi">

			<ul class="nav navbar-nav">
				<li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li>
			</ul>
		
			<sec:authorize access="hasRole('ROLE_COORDENADOR')">
				<ul class="nav navbar-nav">
					<li id="projetos"><a href="<c:url value="/coordenador/projetos" />">Projetos <span class="fa fa-folder"></span></a></li>

					<li id="periodos"><a href="<c:url value="/coordenador/periodos" />">Periodos <span class="fa fa-calendar"></span></a></li>

					<li id="turmas"><a href="<c:url value="/turma/minhas-turmas" />">Minhas Turmas <span class="fa fa-th-list"></span></a></li>

					<li id="estagiarios"><a href="<c:url value="/coordenador/estagiarios" />">Estagiários <span class="fa fa-user"></span></a></li>

					<li id="menu-frequencias"><a href="<c:url value="/frequencia/frequencias" />">Frequência <span class="fa fa-check-square-o"></span></a></li>

					<li id="estagiarios"><a href="<c:url value="/frequencia/reposicao" />">Reposições <span class="fa fa-cogs"></span></a></li>
				</ul>
			</sec:authorize>

			<sec:authorize access="hasRole('ROLE_ESTAGIARIO')">
				<ul class="nav navbar-nav">
					<li id="editar-dados"><a href="<c:url value="/estagiario/editar-estagiario" />">Meus Dados<span class="fa fa-th-list"></span></a></li>

					<li id="minha-presenca"><a href="<c:url value="/frequencia/minha-presenca" />">Minha Presença <span class="fa fa-th-list"></span></a></li>

					<li id="meu-projeto"><a href="<c:url value="/estagiario/meu-projeto" />">Meu Projeto <span class="fa fa-folder"></span></a></li>

					<li id="documentos"><a href="<c:url value="/estagiario/documentos" />">Documentos <span class="fa fa-th-list"></span></a></li>
					
					<li id="avaliacao"><a href="<c:url value="/estagiario/avaliacao" />">Avaliação <span class="fa fa-check"></span></a></li>
				</ul>
			</sec:authorize>

			<ul class="nav navbar-right navbar-nav">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-user"></i> <i class="glyphicon glyphicon-chevron-down"></i></a>
					<ul class="dropdown-menu">
						<li><a href="#"><i class="glyphicon glyphicon-user"></i> Perfil</a></li>
						<li><a href="<c:url value="/j_spring_security_logout" />"><i class="glyphicon glyphicon-off"></i> Sair</a></li>
						<li class="divider"></li>
						<li><a href="#"><i class="glyphicon glyphicon-info-sign"></i> Sobre</a></li>
					</ul>
				</li>  
			</ul>
		</div>
	</div>
</nav>
</sec:authorize>