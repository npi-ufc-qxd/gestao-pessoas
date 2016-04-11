<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<html>
	<head>
		<jsp:include page="../modulos/header-estrutura.jsp" />
		<title>Informações do Estagiario</title>
	</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

<div class="container">
	<div class="row">
	
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h2 class="titulo-panels"><span class="fa fa-group"></span> Dados do Estagiário </h2>
			
			<div class="pull-right">
				<a title="Voltar" class="btn btn-default back"><span class="fa fa-arrow-left"></span> Voltar</a>
			</div>
		</div>
				
			<div class="panel-body">	
				<h4 class="secao-form-h4">Dados Pessoais</h4>
				
				<div class="form-group">
				
					<div class="form-item col-sm-4">
						<label class="text-view-info"><strong>Nome Completo: </strong></label> <label class="text-view-info">${estagiario.nomeCompleto}</label>
					</div>
					
					<div class="form-item col-sm-4">
							<label class="text-view-info"><strong>Telefone: </strong></label><label class="text-view-info">${estagiario.telefone}</label>
					</div>
					
					<fmt:formatDate value="${estagiario.dataNascimento}" var="formattedDate" type="date" pattern="dd/MM/yyyy" />
					<div class="form-item col-sm-4">
						<label class="text-view-info"><strong>Data de Nascimento: </strong></label> <label class="text-view-info">${formattedDate}</label>
					</div>
					
				</div>
					
					<div class="form-group">
					
						<div class="form-item col-sm-5">
							<label class="col-sm-4 text-view-info"><strong>Nome da Mãe: </strong></label><label class="text-view-info">${estagiario.nomeMae}</label>
						</div>
						
					</div>
			</div>
			
			<div class="panel-body">
				<h4 class="secao-form-h4">Informações do Curso</h4>
		
				<div class="form-group">
				
					<div class="form-item col-sm-4">
						<label class="text-view-info"><strong>Matrícula: </strong></label><label class="text-view-info">${estagiario.matricula}</label>
					</div>
					
					<div class="form-item col-sm-4">
						<label class="text-view-info"><strong>Curso: </strong></label><label class="text-view-info">${estagiario.curso}</label>
					</div>
					
					<div class="form-item col-sm-4">
						<label class="text-view-info"><strong>Semestre: </strong></label><label class="text-view-info">${estagiario.semestre}</label>
					</div>
					
				</div>
			</div>
			
			<div class="panel-body">
				<h4 class="secao-form-h4">Contas</h4>
				
				<div class="form-group">
				
					<div class="form-item col-sm-12">
						<label class="text-view-info"><strong>Usuário github: </label><label class="text-view-info">${estagiario.usuarioGithub}</label>
					</div>
					
					<div class="form-item col-sm-12">
						<label class="text-view-info"><strong>E-mail: </label><label class="text-view-info">${estagiario.email}</label>		
					</div>
					
				</div>
			</div>
			<div class="panel-body">
				<h4 class="secao-form-h4">Endereço</h4>
		
				<div class="form-group">
				
					<div class="form-item col-sm-4">
						<label class="text-view-info"><strong>Endereço: </label><label class="text-view-info">${estagiario.endereco}</label>
					</div>
					
					<div class="form-item col-sm-4">
						<label class="text-view-info"><strong>CEP: </strong></label><label class="text-view-info">${estagiario.cep}</label>
					</div>
					
					<div class="form-item col-sm-4">
						<label class="text-view-info"><strong>Cidade/UF: </strong></label><label class="text-view-info">${estagiario.cidade} / ${estagiario.uf}</label>
					</div>
					
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="../modulos/footer.jsp" />
	
    <script type="text/javascript">
		$('.menu #turmas').addClass('active');
	</script>

</body>
</html>