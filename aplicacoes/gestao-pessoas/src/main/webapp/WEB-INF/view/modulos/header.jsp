<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style>
	#header {
		width: auto;
		height: 30px;
		background: #89B3D9;
		font-weight: 600;
		color: #fff;
	}
	#header #bem-vindo span {
		vertical-align: sub;
	}

	.navbar-gp-top {
		background: #2C3F52;
		border: none;
		border-radius: 0; 
		font-weight: 600;
		border-bottom: 1px solid #5BC0DE;
	}
	.navbar-default .navbar-nav>li>a:hover {
		color: #2098d1;
		color: #fff;
		font-weight: 600px;
		
	}
</style>

<sec:authorize access="permitAll">
	<div id="header">
		<div class="container">
			<div id="bem-vindo" align="right">
				<span>Seja bem-vindo, ${sessionScope.usuario.nome} ao NPI.</span>
			</div>
		</div>
	</div>
	
	<nav class="navbar navbar-default navbar-gp-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><span class="fa fa-group"></span> Gestão NPI</a>
	        </div>
	
			<div id="navbar" class="navbar-collapse collapse">
				<sec:authorize access="hasRole('ROLE_COORDENADOR')">
					<ul class="nav navbar-nav"><!-- Inicio Menu Cordenador -->
						<li id="projetos"><a class="hvr-sweep-to-top" href="<c:url value="/coordenador/projetos" />">Projetos <span class="fa fa-folder"></span></a></li>
						<li id="periodos"><a class="hvr-sweep-to-top" href="<c:url value="/coordenador/periodos" />">Periodos <span class="fa fa-calendar"></span></a></li>
						<li id="turmas"><a class="hvr-sweep-to-top" href="<c:url value="/turma/minhas-turmas" />">Minhas Turmas <span class="fa fa-th-list"></span></a></li>
						<li id="estagiarios"><a class="hvr-sweep-to-top" href="<c:url value="/coordenador/estagiarios" />">Estagiários <span class="fa fa-user"></span></a></li>
						<li id="menu-frequencias"><a class="hvr-sweep-to-top" href="<c:url value="/frequencia/frequencias" />">Frequência <span class="fa fa-check-square-o"></span></a></li>
						<li id="estagiarios"><a class="hvr-sweep-to-top" href="<c:url value="/coordenador/reposicao" />">Reposições <span class="fa fa-cogs"></span></a></li>
					</ul><!-- Final Menu Cordenador -->
				</sec:authorize>
	
				<sec:authorize access="hasRole('ROLE_ESTAGIARIO')">
					<ul class="nav navbar-nav"><!-- Inicio Menu Estagiario -->
						<li id="minha-presenca"><a class="hvr-sweep-to-top" href="<c:url value="/estagiario/minha-presenca" />">Minha Presença <span class="fa fa-th-list"></span></a></li>
						<li id="meu-projeto"><a class="hvr-sweep-to-top" href="<c:url value="/estagiario/meu-projeto" />">Meu Projeto <span class="fa fa-folder"></span></a></li>
						<li id="documentos"><a class="hvr-sweep-to-top" href="<c:url value="/estagiario/documentos" />">Documentos <span class="fa fa-th-list"></span></a></li>
						<li id="avaliacao"><a class="hvr-sweep-to-top" href="<c:url value="/estagiario/avaliacao" />">Avaliação <span class="fa fa-check"></span></a></li>
					</ul><!-- Final Menu Estagiario -->
				</sec:authorize>
	
	          
				<ul class="nav navbar-nav navbar-right">
					<li><a class="" href="<c:url value="/j_spring_security_logout" />" data-toggle="tooltip" data-placement="bottom" title="Sair"><i class="fa fa-sign-out"></i></a></li>
				</ul>
			
			</div><!--/.nav-collapse -->
		</div>
	</nav>
</sec:authorize>

