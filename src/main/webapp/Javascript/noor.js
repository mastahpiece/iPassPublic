$(document).ready(function(){
	window.onload = laadBijeenkomsten1();
	window.onload = laadBoeken();

/** Checken of de gebruiker is ingelogd als user en bepaalde delen van de website unhiden **/
	if (window.sessionStorage.getItem("rol") == "user"){
		$(".formulier").hide();
		$("#table1").hide();
		$("#loguitKnop").show();
		$("#welkomtekstLogin").text("U bent ingelogd");
		$("#loginBar").text("Log uit");

	}
/**Checken of de gebruiker een admin is om zo bepaalde delen van de website te unhiden **/
	if (window.sessionStorage.getItem("rol") == "admin"){
		$("#pdfinvoertabel").show();
		$("#loguitKnop").show();
		$("#loginBar").text("Adminpanel");
		$("#loginBar").attr("href", "admin.html");
	}
	
/** Vanaf hier een reeks click functies **/
    $("#register").click(function(){
        $(".formulier").hide();
        $("#table1").hide();
    });

    $("#register").click(function(){
        $(".registerdiv").show();
    });

    $("#terug").click(function(){
        $(".registerdiv").hide();
        $(".formulier").show();
        $("#table1").show();
    });

    $("#loginKnop").click(function() {
    	logIn();
    });

    $("#terugknop").click(function(){
    	$("#tbody5 tr").remove();
    	$(".inschrijvingen").hide();
    	$(".invoerForm").hide();
    	$("#table1").show();
    	$("#bijinvoerknop").show();
    	$("#terugknop").hide();
    	$("#beschrijving").val("");
    	$("#datum").val("");
    });


    $("#submitBeschr").on("click", function(){
    	$("#tbodyid tr").empty();
    	updateBijeenkomsten();
    	$("#beschrijving").val("");
    	$("#datum").val("");
    	$("#toegangVoor").val("m");
    });

    $("#bijinvoerknop").on("click", function () {
    	$("#tbodyid tr").empty();
    	$(".invoerForm").show();
    	$("#table1").hide();
    	$("#bijinvoerknop").hide();
    	$("#terugknop").show();
    	laadBijeenkomstenAdmin();
    });

    $("#table3").on("click", "#knop1", function () {
    	var uri = "restservices/bijeenkomsten/" + $(this).val();

        $.ajax(uri, {
            type: "delete",
            success: function(response) {
            	alert("Bijeenkomst verwijderd!")
            	$("#tbodyid tr").remove();
            	$("#beschrijving").val("");
            	$("#datum").val("");
            	$("#toegangVoor").val("m");
            	laadBijeenkomstenAdmin();

            },
            error: function(response) {
                console.log("Could not delete bijeenkomst!");
            }
        });
    });

    $("#registerUser").click(function(){
    	registreerUserAccount();
    });

    $("#registerAdmin").click(function(){
    	registreerAdminAccount();
    });

    $("#loguitKnop").click(function(){
    	myUitlog();
    });

    $("#loguit").click(function(){
    	myUitlogAdmin();
    });

    $("#table2").on("click","#melder", function(){
    	var value = parseInt(($(this).val()));
    	var acc_id = parseInt(window.sessionStorage.getItem("id"));

    	var data = { "bid": value, "aid": acc_id};
    	var JSONdata = JSON.stringify(data);
    	$.post("restservices/inschrijvingen", JSONdata, function(response){
    		alert("U bent ingeschreven")
    		laadBijeenkomsten1();
    	});
    });

    $("#zieIn").click(function(){
    	$("#table1").hide();
    	$("#bijinvoerknop").hide();
    	$("#terugknop").show();
    	zieInschrijvingen();
    });

    $("#table4").on("click","#inschrijvingenKnop", function(){
    	laadInschrijvingen($(this).val());
    });

    $("#submitfile").click(function() {
    	var fileInput = document.getElementById('file');
    	var filename = fileInput.files[0].name;
    	var path = "/iPass/pdf/";

    	var data = { "naam" : filename, "path": path+filename}
    	var JSONdata = JSON.stringify(data);

    	$.post("restservices/boeken", JSONdata, function(response){
  /** TOT HIER (de klikfuncties) **/
    	});

    });

});

/** Deze functie wordt gebruikt om de Bijeenkomsten in te laden in de tabel **/
function laadBijeenkomsten1(){
	/** Dit is waar de bijeenkomsten worden opgehaald **/
	$.getJSON("restservices/bijeenkomsten/", function(data1) {
		  $("#tbody tr").remove();
	      var table_body = "";
	      if (data1.length < 1){
	    	  console.log("works");
	    	  $("#table2").hide();
	      }
				/** Hier worden de bijeenkomsten in de tabel gezet**/
	      $.each(data1, function(index, data){
	          table_body += "<tbody id='tbody'><tr><td>" + data.beschr + "</td><td>" + data.toegang +
	          "</td><td>" + data.datum + "</td><td class='aanmeldtd' id='aa"+data.id+"'></td><td id='"+data.id+"'></td></tr></tbody>";
	          aanwezigenTeller(data.id);
	        });
	      $("#table2").append(table_body);
	      $(".label1").hide();
	    });

	/** Hier wordt gecontroleerd of de gebruiker een user/admin is om dan aanmeldknop te vervangen met een tekst **/
	var rol = window.sessionStorage.getItem("rol");
	if (rol == "admin" || rol == "user"){
		$.getJSON("restservices/bijeenkomsten/", function(data1) {
			  $("#tbody tr").remove();
		      var table_body = "";
		      if (data1.length < 1){
		    	  console.log("works");
		    	  $("#table2").hide();
		      }
					/** Hier worden de bijeenkomsten in de tabel gezet**/
		      $.each(data1, function(index, data){
		          table_body += "<tbody id='tbody'><tr><td>" + data.beschr + "</td><td>" + data.toegang +
		          "</td><td>" + data.datum + "</td><td class='aanmeldtd' id='aa"+data.id+"'></td><td class='knoptd' id='"+data.id+"'><button value='" 
		          + data.id +"'id='melder'>meld aanwezig</button></td></tr></tbody>";
		        });
		      $("#table2").append(table_body);
		      $(".label1").hide();
		    });
		
		var login_id = window.sessionStorage.getItem("id");
		/** Kijk of de user in de tabel met inschrijvingen voorkomt **/
		$.getJSON("restservices/inschrijvingen/aid/" + login_id, function(data){
					table_body = "";
					$.each(data, function(index, data1){
						$.getJSON("restservices/bijeenkomsten/" + data1.bid, function(data) {
							$.each(data, function(index, data1){
								$("#"+data1.id+"").html("Aangemeld");
								$(".label1").hide();
								$("#aanmeldColumn").show();
							});
						});
					});
	});
	}
}
/** Loginfunctie **/
function logIn(){
	var gbnm = $("#gbnm").val();
	var ww = $("#ww").val();

/** Spreekt voor zichzelf **/
	if ( gbnm == "" || ww == "" ) {
		alert('gebruikersnaam mag niet leeg zijn')
	}

	else {
		/** Haal account op met invoergebruikersnaam **/
		$.getJSON("restservices/accounts/" + gbnm + "/", function(data) {
			var invoergb = data.gbnaam;
			var invoerww = data.ww;
			var rol = data.rol;
			var id = data.id;

			/** Controleer of de invoer overeenkomt met databasegegevens **/
			if (invoergb == gbnm && invoerww == ww){
							/** Zo ja sla gegevens op in de sessionStorage **/
		    		  window.sessionStorage.setItem('user', invoergb);
		    		  window.sessionStorage.setItem('pass', invoerww);
		    		  window.sessionStorage.setItem("id", id);
		    		  window.sessionStorage.setItem("rol", rol);

		    		  if (rol == 'admin'){
								/** Als role admin is wordt deze verwezen naar adminPanel **/
		    			  console.log("good jobbu admin-des");
		    			  window.location.replace("admin.html");
		    		  }
		    		  else {
								/** Als de role een user is dan naar de normale login pagina **/
		    			  window.location.replace("Login.html");
		    			  console.log('good jobbu user-des')
		    		  }
			}
			else {
				alert('verkeerde combinatie of account bestaat niet!')
			}

		});
	}
}

/** Hier worden bijeenkomsten geladen waarbij de admin bijeenkomsten kan deleten in de tabel **/
function laadBijeenkomstenAdmin(){
	$.getJSON("restservices/bijeenkomsten/", function(data1) {
	      var table_body = "";
	      $.each(data1, function(index, data){
	          table_body += "<tbody id='tbodyid'><tr><td>" + data.beschr + "</td><td>" + data.toegang +
	          "</td><td>" + data.datum + "</td><td><button id='knop1' value='" + data.id + "'>Delete!</button></tr></tbody>";
	        });
	      $("#table3").append(table_body);
	      $(".label1").hide();

	    });
	  };
/** Met deze functie worden bijeenkomsten ingevoerd **/
function updateBijeenkomsten(){
	var data = { "beschr": $("#beschrijving").val(), "toegang": $("#toegangVoor").val(), "datum": $("#datum").val() }
    var JSONdata = JSON.stringify(data);

    $.post("restservices/bijeenkomsten", JSONdata, function(response) {
    	laadBijeenkomstenAdmin();
    });

}

/** Met behulp van deze functie worden useraccounts geregistreerd **/
function registreerUserAccount(){
if(
	$("#gbuser").val() == ""		||
	$("#vnmuser").val() == ""	||
	$("#anmuser").val() == ""	||
	$("#wwuser").val() == ""		||
	$("#mailuser").val() == "") {
	alert("Nope, alle velden invullen graag c:")
}

else {

	var data = { "gbnaam": $("#gbuser").val(), "vnaam": $("#vnmuser").val(), "anaam": $("#anmuser").val()
				, "ww": $("#wwuser").val(), "mail": $("#mailuser").val(), "rol": "user"}

    var JSONdata = JSON.stringify(data);

	$.post("restservices/accounts", JSONdata, function(response){
		alert("Account gecreeerd! U kunt nu inloggen")
		$("#gbuser").val("");
		$("#vnmuser").val("");
		$("#anmuser").val("");
		$("#wwuser").val("");
		$("#mailuser").val("");
		window.location.replace("Login.html");
	});
}
}

/** Met behulp van deze functie worden adminaccounts geregistreerd **/
function registreerAdminAccount(){
	if(
			$("#gbadmin").val() == ""		||
			$("#vnadmin").val() == ""	||
			$("#anmadmin").val() == ""	||
			$("#wwadmin").val() == ""		||
			$("#mailadmin").val() == "") {
			alert("Nope, alle velden invullen graag c:")
		}
	else {
	var data = { "gbnaam": $("#gbadmin").val(), "vnaam": $("#vnmadmin").val(), "anaam": $("#anmadmin").val()
			, "ww": $("#wwadmin").val(), "mail": $("#mailadmin").val(), "rol": "admin"}

	var JSONdata = JSON.stringify(data);

	$.post("restservices/accounts", JSONdata, function(response){
		alert("Account gecreeerd! U kunt nu inloggen")
		$("#gbadmin").val("");
		$("#vnadmin").val("");
		$("#anmadmin").val("");
		$("#wwadmin").val("");
		$("#mailadmin").val("");
		window.location.replace("admin.html");
	});
	}
}
/** Hier wordt de bijeenkomstTabel geladen waarmee de admin inschrijvingen gaat kunnen inzien **/
function zieInschrijvingen(){
	$(".inschrijvingen").show();
	$("#t4body tr").remove();
	var table_body = "";
	$.getJSON("restservices/bijeenkomsten/", function(data) {
		table_body = "";
		$.each(data, function(index, data1){
			table_body += "<tbody id='t4body'><tr><td>" + data1.beschr + "</td><td>" + data1.toegang +
		    "</td><td>" + data1.datum + "</td><td><button value='" + data1.id + "' id='inschrijvingenKnop'>Zie in</button></td></tr></tbody>";
		});
		    $("#table4").append(table_body);
	});
}
/** Met behulp van deze functie kan je met een klik op de bijeenkomst de inschrijvingen van de
		betreffende bijeenkomst inzien **/
function laadInschrijvingen(bid){
	$("#tbody5 tr").remove();
	/** De parameter die een bijeenkomstid is wordt opgezocht in tabel en in de volgende functie
	 		gekoppeld aan een account. Zo itereer het programma met een loop in een loop om combinaties te vinden**/
	$.getJSON("restservices/inschrijvingen/bid/" + bid, function(data){
		var teller = 0;
		$.each(data, function(index, data1){
			teller ++;
			$.getJSON("restservices/accounts/id/" + data1.aid, function(data){
				$.each(data, function(index, data2){
				$("#table5").append("<tbody id='tbody5'><tr><td>" + data2.vnaam + "</td><tr></tbody>");
				});
			});
		});

	});
}

/** Met behulp van deze functie vul ik de pdf download tabel met links **/
function laadBoeken(){
	var table_body = "";
	$.getJSON("restservices/boeken", function(data){
		$.each(data, function(index, data1){
			table_body += "<tr><td>" + data1.naam +
			"<td><a href='"+data1.path+"'>Download</a></td></tr>";
		});
		$("#downloads").append(table_body);
	});
}

/** Uitlogfunctie met removeitems uit de sessionstorage voor users **/
function myUitlog() {
    var txt;
    var r = confirm("Weet u zeker dat u wilt uitloggen?");
    if (r == true) {
    	console.log("uitlogfunctie");
    	window.sessionStorage.removeItem("sessionToken");
   	 	window.sessionStorage.removeItem("user");
   	 	window.sessionStorage.removeItem("pass");
		window.sessionStorage.removeItem("rol");
		window.sessionStorage.removeItem("id");
		window.location.replace("Login.html");
    } else {

    }

}

/** Uitlogfunctie met removeitems uit de sessionstorage voor admins **/
function myUitlogAdmin() {
    var txt;
    var r = confirm("Weet u zeker dat u wilt uitloggen?");
    if (r == true) {
    	window.sessionStorage.removeItem("sessionToken");
   	 	window.sessionStorage.removeItem("user");
	 	window.sessionStorage.removeItem("pass");
		window.sessionStorage.removeItem("rol");
		window.sessionStorage.removeItem("id");
		window.location.replace("Login.html");
    } else {

    }

}

/** Dit is de teller die bij elke tabel wordt gebruikt om het aantal aanwezigen per
 		bijeenkomst te tellen. De teller voegt dan per rij die een bijeenkomst-id heeft
		het getal toe**/
function aanwezigenTeller(bid){
	$("#tbody5 tr").remove();
	$.getJSON("restservices/inschrijvingen/bid/" + bid, function(data){
		var teller = 0;
		if (data != undefined){
		$.each(data, function(index, data1){
			teller ++;
			$.getJSON("restservices/accounts/id/" + data1.aid, function(data){
				$.each(data, function(index, data2){
				});
			});
		});
		$("#aa"+bid).append(teller);
		}
	});

}
