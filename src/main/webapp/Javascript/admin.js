if(window.localStorage.getItem("rol") != "admin"){
	$(".contentAdmin").hide();
	alert("U heeft geen toegang tot deze pagina. Logt u aub in!");
	window.location.replace("Login.html");
}