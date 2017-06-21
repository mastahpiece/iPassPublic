$(document).ready(function(){
	window.onload = laadBijeenkomsten();
	window.onload = laadBoeken();

	if (window.localStorage.getItem("rol") == "user"){
		$(".formulier").hide();
		$("#table1").hide();
		$("#loguitKnop").show();
		$("#welkomtekstLogin").text("U bent ingelogd");
		$("#loginBar").text("Log uit");

	}

	if (window.localStorage.getItem("rol") == "admin"){
		$("#pdfinvoertabel").show();
		$("#loguitKnop").show();
		$("#loginBar").text("Adminpanel");
		$("#loginBar").attr("href", "admin.html");
	}

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
    
 /**   $("#loginKnop").click(function(event) {
      var data = $(".formulier").serialize();

    	  $.post("restservices/authentication", data, function(response) {
    	    window.sessionStorage.setItem("sessionToken", response);
    	    	logIn(); 
    	  }).fail(function(jqXHR, textStatus, errorThrown) {
    	    console.log(textStatus);
    	    console.log(errorThrown);
    	  });
    	}); **/


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
    	var acc_id = parseInt(window.localStorage.getItem("id"));

    	var data = { "bid": value, "aid": acc_id};
    	var JSONdata = JSON.stringify(data);
    	$.post("restservices/inschrijvingen", JSONdata, function(response){
    		alert("U bent ingeschreven")
    		window.location.replace("Bijeenkomsten.html");
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
    		
    	});
    	
    });
    
});

function laadBijeenkomsten(){
	var storage = window.localStorage.getItem("rol");
	console.log(storage);
	if (storage == "user" || storage == "admin"){
		$("#aanmeldColumn").show();
		$.getJSON("restservices/bijeenkomsten/", function(data) {
		$("#tbody tr").remove();
		var table_body = "";
			if (data.length < 1){
			$("#table2").hide();
		    }

			$.each(data, function(index, data1){
				var login_accid = window.localStorage.getItem("id");
				var acc_id = ""
    	    	$.getJSON("restservices/inschrijvingen/bid/" + data1.id, function(data2){
    			if (data2 != undefined) {
    				$.each(data2, function(index, data3){
    					acc_id = data3.aid;
	    				if (login_accid == acc_id){
	    					console.log("id komt overeen :" + login_accid,",",acc_id);
	    					table_body = "";
	    					table_body += "<tbody id='tbody'><tr><td>" + data1.beschr + "</td><td>" + data1.toegang +
	    				    "</td><td>" + data1.datum + "</td><td class='aanmeldtd' id='aa"+data1.id+"'><td>Aangemeld</td></tr></tbody>"
	    				    $("#table2").append(table_body);
	    					aanwezigenTeller(data1.id);
	    				    $(".label1").hide();
	    					}
	    				else {	
	    					console.log("id komt niet overeen :" + login_accid,",",acc_id);
	    					table_body = "";
	    					table_body += "<tbody id='tbody'><tr><td>" + data1.beschr + "</td><td>" + data1.toegang +
	    				    "</td><td>" + data1.datum + "</td><td class='aanmeldtd' id='aa"+data1.id+"'>" +
	    				    "<td><button value='" + data1.id +"' id='melder'>meld aanwezig</button></td></tr></tbody>"
	    				    $("#table2").append(table_body);
	    					aanwezigenTeller(data1.id);
	    				    $(".label1").hide();
	    					}
    				});
    				}

    			else{
					console.log("id komt niet overeeeeen :" + login_accid,",",acc_id);
					table_body = "";
					table_body += "<tbody id='tbody'><tr><td>" + data1.beschr + "</td><td>" + data1.toegang +
				    "</td><td>" + data1.datum + "</td><td class='aanmeldtd' id='aa"+data1.id+"'>" +
				    "<td><button value='" + data1.id +"' id='melder'>meld aanwezig</button></td></tr></tbody>"
				    $("#table2").append(table_body);
					aanwezigenTeller(data1.id);
				    $(".label1").hide();
					}
				});
			});
		});

	}
	else{
	$.getJSON("restservices/bijeenkomsten/", function(data1) {
	  $("#tbody tr").remove();
      var table_body = "";
      if (data1.length < 1){
    	  console.log("works");
    	  $("#table2").hide();
      }
      $.each(data1, function(index, data){
          table_body += "<tbody id='tbody'><tr><td>" + data.beschr + "</td><td>" + data.toegang +
          "</td><td>" + data.datum + "</td><td class='aanmeldtd' id='aa"+data.id+"'></td></tr></tbody>";
          aanwezigenTeller(data.id);
        });
      $("#table2").append(table_body);
      $(".label1").hide();
    });
	};
  };

function logIn(){
	var gbnm = $("#gbnm").val();
	var ww = $("#ww").val();

	if ( gbnm == "" || ww == "" ) {
		alert('gebruikersnaam mag niet leeg zijn')
	}

	else {
		$.getJSON("restservices/accounts/" + gbnm + "/", function(data) {
			var invoergb = data.gbnaam;
			var invoerww = data.ww;
			var rol = data.rol;
			var id = data.id;

			if (invoergb == gbnm && invoerww == ww){

		    		  window.localStorage.setItem('user', invoergb);
		    		  window.localStorage.setItem('pass', invoerww);
		    		  window.localStorage.setItem("id", id);
		    		  window.localStorage.setItem("rol", rol);

		    		  if (rol == 'admin'){
		    			  console.log("good jobbu admin-des");
		    			  window.location.replace("admin.html");
		    		  }
		    		  else {
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

function updateBijeenkomsten(){
	var data = { "beschr": $("#beschrijving").val(), "toegang": $("#toegangVoor").val(), "datum": $("#datum").val() }
    var JSONdata = JSON.stringify(data);

    $.post("restservices/bijeenkomsten", JSONdata, function(response) {
    	laadBijeenkomstenAdmin();
    });

}

function registreerUserAccount(){
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

function registreerAdminAccount(){
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

function laadInschrijvingen(bid){
	$("#tbody5 tr").remove();
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

function myUitlog() {
    var txt;
    var r = confirm("Weet u zeker dat u wilt uitloggen?");
    if (r == true) {
    	console.log("uitlogfunctie");
    	window.sessionStorage.removeItem("sessionToken");
   	 	window.localStorage.removeItem("user");
   	 	window.localStorage.removeItem("pass");
		window.localStorage.removeItem("rol");
		window.localStorage.removeItem("id");
		window.location.replace("Login.html");
    } else {
    	
    }

}

function myUitlogAdmin() {
    var txt;
    var r = confirm("Weet u zeker dat u wilt uitloggen?");
    if (r == true) {
    	window.sessionStorage.removeItem("sessionToken");
   	 	window.localStorage.removeItem("user");
	 	window.localStorage.removeItem("pass");
		window.localStorage.removeItem("rol");
		window.localStorage.removeItem("id");
		window.location.replace("Login.html");
    } else {
    	
    }

} 

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
