<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<html>
<head>
	<title>Administraçã GRH</title>
	<jsp:include page="../modulos/header-estrutura.jsp" />
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	
	<div class="container">
	<ul class="nav nav-tabs">
	  <li class=""><a href="#home" data-toggle="tab" aria-expanded="false">Home</a></li>
	  <li class="active"><a href="#profile" data-toggle="tab" aria-expanded="true">Profile</a></li>
	</ul>
	<div id="myTabContent" class="tab-content">
	  <div class="tab-pane fade" id="home">
	    <p>Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua, retro synth master cleanse. Mustache cliche tempor, williamsburg carles vegan helvetica. Reprehenderit butcher retro keffiyeh dreamcatcher synth. Cosby sweater eu banh mi, qui irure terry richardson ex squid. Aliquip placeat salvia cillum iphone. Seitan aliquip quis cardigan american apparel, butcher voluptate nisi qui.</p>
	  </div>
	  <div class="tab-pane fade active in" id="profile">
	    <p>Food truck fixie locavore, accusamus mcsweeney's marfa nulla single-origin coffee squid. Exercitation +1 labore velit, blog sartorial PBR leggings next level wes anderson artisan four loko farm-to-table craft beer twee. Qui photo booth letterpress, commodo enim craft beer mlkshk aliquip jean shorts ullamco ad vinyl cillum PBR. Homo nostrud organic, assumenda labore aesthetic magna delectus mollit.</p>
	  </div>
	</div>	
	
	</div>

	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>