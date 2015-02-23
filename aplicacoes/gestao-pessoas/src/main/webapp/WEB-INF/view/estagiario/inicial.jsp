<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<html>
<head>
	<title>Estagiários</title>
	<jsp:include page="../modulos/header-estrutura.jsp" />
</head>
<body>

	<c:if test="${not resultado}"><!-- Tela de Cadastro para Inicio no NPI -->
		<jsp:include page="../modulos/header.jsp" />
	</c:if>
	
	<div class="container">
		<c:if test="${resultado}"><!-- Tela de Cadastro para Inicio no NPI -->
			<div class="temp">
				<jsp:include page="../estagiario/cadastro-npi.jsp" />
			</div>
		</c:if>
	</div>

	<jsp:include page="../modulos/footer.jsp" />
	<script src="<c:url value="/resources/js/bootbox.min.js" />"></script>
	<script src="<c:url value="/resources/js/flippant.min.js" />"></script>
	<script src="<c:url value="/resources/js/fuelux.min.js" />"></script>
	

	<script type="text/javascript">
		$(document).ready(function() {
			function rulesDadosPessoais() {
				$( "#nomeCompleto" ).rules( "add", {
					required: true,
				});
				
				$( "#nomeMae" ).rules( "add", {
					required: true,
				});
				
				$( "#dataNascimento" ).rules( "add", {
					required: true,
				});
				
				$( "#semestre" ).rules( "add", {
					required: true,
				});
				
				$( "#matricula" ).rules( "add", {
					required: true,
				});
				
				$( "#endereco" ).rules( "add", {
					required: true,
				});
				
				$( "#cidade" ).rules( "add", {
					required: true,
				});
				
				$( "#uf" ).rules( "add", {
					required: true,
				});
				
				$( "#telefone" ).rules( "add", {
					required: true,
				});
			}
			
			function rulesContas() {
				$( "#contaRedmine" ).rules( "add", {
					required: true,
				});

				$( "#contaGithub" ).rules( "add", {
					required: true,
				});

				$( "#contaHangout" ).rules( "add", {
					required: true,
				});
				
			}
			
			$('#cadastroNPIWizard').on('actionclicked.fu.wizard', function (event, data) {
				console.log('QUERO MUDAR: ', data);

				var formulario = $( "#meuCadastroNPIEstagiario" );

				if(data.step === 1){
					//rulesDadosPessoais();
				}

				if(data.step === 2){
					//rulesContas();
				}
				
				if(data.step === 3){
					
				}

				if(!formulario.valid()){
				    return event.preventDefault();
				}
			});
			
			$('#cadastroNPIWizard').on('changed.fu.wizard', function (event, data) {
				console.log('PRONTO MUDEI', data);
			});
			
			$('#cadastroNPIWizard').on('finished.fu.wizard', function (event, data) {
				console.log('finished');
				$( "#meuCadastroNPIEstagiario" ).submit();
				$(".temp").empty();
			});
			
			
			$( "#meuCadastroNPIEstagiario" ).validate({
		        rules: {
		            
		        },
		        highlight: function(element) {
		            $(element).closest('.form-item').addClass('has-error');
		            $(element).closest('input').addClass('bola');
		        },
		        unhighlight: function(element) {
		            $(element).closest('.form-item').removeClass('has-error');
		            $(element).closest('input').removeClass('bola');
		        },
		        errorElement: 'span',
		        errorClass: 'help-block',
		        errorPlacement: function(error, element) {
		            error.insertAfter(element.parent().children().last());
		        },
		        messages:{
					nomeCompleto : {
						required : "Campo obrigatório",
					},
					dataNascimento : {
						required : "Campo obrigatório",
					},
					nomeMae : {
						required : "Campo obrigatório",
					},
					semestre : {
						required : "Campo obrigatório",
					},
					matricula : {
						required : "Campo obrigatório",
					},
					endereco : {
						required : "Campo obrigatório",
					},
					cidade : {
						required : "Campo obrigatório",
					},
					uf : {
						required : "Campo obrigatório",
					},
					telefone : {
						required : "Campo obrigatório",
					},
					contaRedmine : {
						required : "Campo obrigatório",
					},
					contaGithub : {
						required : "Campo obrigatório",
					},
					contaHangout : {
						required : "Campo obrigatório",
					},
				}
			});			
		});
	</script>
</body>
</html>