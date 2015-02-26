$(document).ready(function() {
	$("#cadastrar").hide();
	$(".cpf").mask("99999999999");

	$("#cadastre-se").click(function(){
		$("#login-form").hide();
		$("#cadastrar").show();
		$("#title").text("Cadastre-se");
	});

	$("#logar").click(function(){
		$("#title").text("Faça seu login");
		$("#login-form").show();
		$("#cadastrar").hide();
	});
	
});
