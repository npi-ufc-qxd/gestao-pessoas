$(document).ready(function() {
	$("#cadastrar").hide();
	
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
