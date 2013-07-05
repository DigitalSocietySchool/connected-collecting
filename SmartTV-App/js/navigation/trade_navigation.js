//Javascript file
//Navigation page for the trade view

//Actual element and previous element.
var element = "#element-1";
var prevElement = "#element-1";

//The last element is the id of the last element inside the carrusel. The actual Menu is the element selected in the carrusel, so it does not get override when we move the focus
//inside the content
var lastElement = "#element-3";
var actualMenu = element;

//Scope values. Each scope has different ways of navigation.
ScopeValues = {
	MENU : "MENU",
	STICKERS : "STICKERS",
	SUCCESS_MENU : "SUCCESS_MENU",
	ERROR_MENU : "ERROR_MENU",
	GO_BACK : "GO_BACK"
}

//Max index to access to the navigation array.
var maxx;
var maxy;

//Initial index to access the navigation array.
var focusx = 0;
var focusy = 0;

//The actual scope is the MENU (carrusel) and the user can not go to the content until it is loaded.
var actualScope = ScopeValues.MENU;
var go_back = false;
var block_go_right = true;

//Variables for the list of users
var list_users;

//This variable are to simulate the controller. IF they are not defined, simulate the arrow keys of the controller with the keyboard arrow keys and the ENTER key for OK.
var VK_DOWN;
var VK_LEFT;
var VK_RIGHT;
var VK_UP;
var VK_ENTER;

if(VK_DOWN 	=== undefined) 	{ 	VK_DOWN 	= 40;	}
if(VK_LEFT 	=== undefined) 	{	VK_LEFT 	= 37;	}
if(VK_RIGHT === undefined)	{	VK_RIGHT 	= 39;	}
if(VK_UP 	=== undefined)	{	VK_UP 		= 38;	}
if(VK_ENTER === undefined)	{	VK_ENTER 	= 13;	}

//Function that initialize the navigation array. In this case, is a Matrix of 5x1 which is simulated in , and each cell represents one button. For each button there are to elements:
	//ID of the elements of the html.
	//URL or action to do when ok is pressed at that element.
//Check the documentation to undrestand how this kind of navigations works.
function initialize_nav_array(){
	
	//Fill the navigation array.
	nav_array_overview = new Array(5);
	for(var i=0;i<nav_array_overview.length;i++){
		nav_array_overview[i] = new Array(2);
		nav_array_overview[i][0] = new Array(2);
		nav_array_overview[i][1] = new Array(2);
	}
	
	nav_array_overview[0][0]["id"] = "#trade-req-img-1";
	nav_array_overview[1][0]["id"] = "#trade-req-img-2";
	nav_array_overview[2][0]["id"] = "#trade-req-img-3";
	nav_array_overview[3][0]["id"] = "#trade-req-img-4";
	nav_array_overview[4][0]["id"] = "#trade-req-img-5";
	
	//Define the max index for the matrix.
	maxx = nav_array_overview.length - 1;
	maxy = nav_array_overview[0].length - 1;
}


//Function that will be call everytime a key is pressed.
function navigate(e){
	
	//Depending on the scope, the navigation will change.
	switch (actualScope){
		case ScopeValues.MENU:
			//If we are in the carrusel, call navigate_in_menu
			navigate_in_menu(e);
			break;
		case ScopeValues.STICKERS:
			//If we are in the content, call navigate_in_stickers
			navigate_in_stickers(e);
			break;
		case ScopeValues.SUCCESS_MENU:
			//If we are in the success popup, there is just one button that takes you back when pressing enter.
			if(e.keyCode==VK_ENTER){
				window.history.back();
			}else{
				e.preventDefault();
			}
			break;
		case ScopeValues.ERROR_MENU:
			if(e.keyCode==VK_ENTER){
				//If we are in the error popup, there is just one button that reloads the page when pressing enter.
				window.location.reload();
			}else{
				e.preventDefault();
			}
		case ScopeValues.GO_BACK:
			//If we are in the scope were there are no users to trade with, there is just one button that takes you back when pressing enter.
			if(e.keyCode==VK_ENTER){
				window.history.back();
			}else{
				e.preventDefault();
			}
			break;
		default:
			break;
	}
}

//Navigate in the menu carrusel
function navigate_in_menu(e){
	
	//For the arrow keys, call the functions of navigations to go up, down and right in this kind of navigation. Left does not do anything.
	switch(e.keyCode){
		case VK_DOWN:
			menu_nav_up();
			e.preventDefault();
			break;
		case VK_UP:
			menu_nav_down();
			e.preventDefault();
			break;
		case VK_RIGHT:
			//Only do this if there is content
			if(!block_go_right){
				change_scope(ScopeValues.STICKERS);
			}
			e.preventDefault();
			break;
		case VK_LEFT:
			e.preventDefault();
			break;
		default:
			break;	
	}
}

//Function that moves the carrusel up.
function menu_nav_up(){

	//If the actual element is not the last one
	if(element != lastElement){
		//Change the focus calling change_focus_carrusel
		change_focus_carrusel("up");
		//Move the carrusel up and call change_content after that.
		$('#option-list').animate({
			"margin-top": '-=106',
		}, 200, change_content);
		//If now element is the lastElement, remove the navigation arrow.
		if(element==lastElement){
			$(".arrow-down").css("display","none");
		}
		//Show the other navigation arrow.
		$(".arrow-up").css("display","block")
	}
}

//Function that moves the carrusel down.
function menu_nav_down(){
	
	//If the actual element is not the first one
	if(element != "#element-1" ){
		//Change the focus calling change_focus_carrusel
		change_focus_carrusel("down");
		//Move the carrusel down and call change_content after that.
		$('#option-list').animate({
			"margin-top": '+=106',
		}, 200, change_content);
		//If now element is the firstElement, remove the navigation arrow.
		if(element=="#element-1"){
			$(".arrow-up").css("display","none");
		}
		//Show the other navigation arrow.
		$(".arrow-down").css("display","block")
	}
}

//Function that changes the glow classes of the elements.
function change_focus_carrusel(movement){

	if(movement=="up"){
		
		//Look for the next element of the list and put it in the element variable.
		prevElement = element;
		var patt = /\d+/g;
		var result = patt.exec(element);
		var numberPage = parseInt(result) + 1;
		element = "#element-" + numberPage;
		//Change tha ctualMenu to the element founded.
		actualMenu = element;
		
		//Remove the glow class from the previous element and add it to the actual element.
		$(prevElement).removeClass("option-glow");
		$(element).addClass("option-glow");
	
	}else if(movement=="down"){

		//Look for the next element of the list and put it in the element variable.
		prevElement = element;
		var patt = /\d+/g;
		var result = patt.exec(element);
		var numberPage = parseInt(result) - 1;
		element = "#element-" + numberPage;
		//Change tha ctualMenu to the element founded.
		actualMenu = element;
		
		//Remove the glow class from the previous element and add it to the actual element.
		$(prevElement).removeClass("option-glow");
		$(element).addClass("option-glow");
		
	}else{
		return false;	
	}
	
}

//Function called when the carrusel is moved.
function change_content(){
	
	//It hides the actual content and show a loading image.
	$("#sticker-to-give-container").hide();
	$("#no-stickers").hide();
	$("#loading").show();

	//Call the function that will load the new content.
	draw_stickers_to_offer();

}

//Function called when the scope is changed bewteen the carrusel and the content.
function change_scope(scope){

	if(scope==ScopeValues.MENU){
		//If changing to the carrusel.
		//Add focus to the graphic elements of the carrusel (rectangule and arrows.)
		$("#element-selected").css("background-color","#e74c3c");
		$(".arrow-up").css("border-bottom-color","#e74c3c");
		$(".arrow-down").css("border-top-color","#e74c3c");


		if(actualScope==ScopeValues.STICKERS){
			//If I am coming from the content I update the actual element using the actualMenu that is saved, and the glow is removed from the previous element.
			prevElement = element;
			element = actualMenu;
			$(prevElement).parent().removeClass("sticker-glow");
			$("#stickers-trade-requests").removeClass("whitelay");
			//Also, the indexes of the navigation array are reseted and finally the scope is updated.
			focusx = 0;
			focusy = 0;
			actualScope = scope;
		}

	}else if(scope==ScopeValues.STICKERS){

		//If changing to the content.
		//Remove focus to the graphic elements of the carrusel (rectangule and arrows.)
		$("#element-selected").css("background-color","#3B5D7F");
		$(".arrow-up").css("border-bottom-color","#3B5D7F");
		$(".arrow-down").css("border-top-color","#3B5D7F");

		//save the element in actualMenu, and update the actual element and the previous element.
		prevElement = element;
		actualMenu = element;
		element = "#trade-req-img-1";
		//Add the glow to the acual element and finally the scope is updated.
		$("#trade-req-img-1").parent().addClass("sticker-glow");
		$("#stickers-trade-requests").addClass("whitelay");
		actualScope = scope;

	}
}

//Navigate in the content (stickers to trade)
function navigate_in_stickers(e){

	//Depending on which key has you pressed, you will change focus or not.
	switch(e.keyCode){
		//For the arrow keys, call the functions of navigations to go left and right in this kind of navigation, no up and down since we only have one row of content.
		case VK_UP:
			e.preventDefault();
			break;
		case VK_DOWN:
			e.preventDefault();
			break;
		case VK_RIGHT:
			nav_sticker_right();
			e.preventDefault();
			break;
		case VK_LEFT:
			//If the user is in the first element and press left, then it goes to the carrusel. Otherwise, just change focus inside content.
			if(element=="#trade-req-img-1"){change_scope(ScopeValues.MENU);}
			else{nav_sticker_left();}
			e.preventDefault();
			break;
		case VK_ENTER:
			//If ENTER is pressed:
			if($.isArray(nav_array_overview[focusx][focusy]["url"])){
				//If the element in the url compoenent of the actual element in the navigation array is an array, is a function with their arguments, so call the function.
				window[nav_array_overview[focusx][focusy]["url"][0]](nav_array_overview[focusx][focusy]["url"][1]);
			}else if(nav_array_overview[focusx][focusy]["url"]=="BACK"){
				//If the element in the url compoenent of the actual element is the string "BACK", go to the previous page
				window.history.back();
			}else if(nav_array_overview[focusx][focusy]["url"].indexOf('.')!=0){
				//If the element in the url compoenent of the actual element is a string not starting with a dot, is a function without argument, so call it.
				window[nav_array_overview[focusx][focusy]["url"]]();
			}else{
				//In other case, redirect to the url.
				window.location.href = nav_array_overview[focusx][focusy]["url"];
			}
			e.preventDefault();
		default:
			break;
	}

}

//Function to navigate right in the overview. Check documentation for more information.
function nav_sticker_right(){

	if(focusx+1<=maxx){
		for(var i=0;focusx+1+i<=maxx;i++){
			var tx = focusx+1+i;
			if(nav_array_overview[tx][focusy]["id"]!=undefined && nav_array_overview[tx][focusy]["id"]!=element){
				prevElement=element;
				element = nav_array_overview[tx][focusy]["id"];	
				focusx=tx;
				$(prevElement).parent().removeClass("sticker-glow");
				$(element).parent().addClass("sticker-glow");
				break;
			}
		}
	}   
}

//Function to navigate left in the overview. Check documentation for more information.
function nav_sticker_left(){
	if(focusx-1>=0){
		for(var i=0;focusx-1-i>=0;i++){
			var tx = focusx-1-i;
			if(nav_array_overview[tx][focusy]["id"]!=undefined && nav_array_overview[tx][focusy]["id"]!=element){
				prevElement=element;
				element = nav_array_overview[tx][focusy]["id"];	
				focusx=tx;
				$(prevElement).parent().removeClass("sticker-glow");
				$(element).parent().addClass("sticker-glow");
				break;
			}
		}
	}     	
}

//FUnction that retrieves from the stickers the list of stickers to show
function get_users_to_trade(){

	//Get the ID of the sticker the user want, that is in the URL
	stickerid = $.getUrlVar("stickerid");
	//ID of the user
	my_user_id = 2;
	//Take the name of the sticker to write it in the html, is in the URL.
	stickername = $.getUrlVar("stickername");
	$("#sticker-name").html(decodeURIComponent(stickername));
	$("#sticker-name-2").html(decodeURIComponent(stickername));

	//If the sticker ID is a valid number
	if(!isNaN(parseFloat(stickerid)) && isFinite(stickerid)){
		
		trueUrl = "http://145.92.6.118/medialab-api/user/getUsersForTrade/"+my_user_id+"/"+stickerid+"/";
		fakeUrl = "./JSON_REQUESTS/tradeusers.json";

		//Make ajax call to the getUsersForTrade entrypoing to get the uers that can trade with the user the sticker he want
		call = $.ajax({
			type : "GET",
			url: trueUrl,
			contentType:"application/json",
			dataType:"json",
			success: function(json){

				if(json.response=="NO_USERS_FOUND"){
					//If there is no user, call draw_no_users
					draw_no_users(); 
				}else if (json.response=="SUCCESS"){
					//If the answer is SUCCESS and there is at least 1 user
					if(json.users.length>0){
						//Save the user list
						list_users = json.users;
						//Draw the user list calling draw_user_list and call after call the function that draw the stickers you can trade to the first user in the list.
						draw_user_list();
						draw_stickers_to_offer();
						//Hide loading gif and show content.
						$("#first-loading").hide();
						$("#content-after-load").show();
					}else{
						//If there is no user, call draw_no_users
						draw_no_users(); 
					}
				}else{
					window.location.href="./index.html";
				}

			}
		});

		//If the ajax function failed to connect to the server, then it loads the content from a stub JSON file. This has been done for testing. At production should show an error message or try the calling again.
		call.fail(function(){
			$.ajax({
				type : "GET",
				url: fakeUrl,
				contentType:"application/json",
				dataType:"json",
				success: function(json){

					if(json.response=="NO_USERS_FOUND"){
						draw_no_users(); 
					}else if (json.response=="SUCCESS"){
						if(json.users.length>0){
							list_users = json.users;
							draw_user_list();
							draw_stickers_to_offer();
							$("#first-loading").hide();
							$("#content-after-load").show();
						}else{
							draw_no_users(); 
						}
					}else{
						window.location.href="./index.html";
					}

				}
			});
		});

	}else{
		window.location.href="./index.html";
	}

}

//Function that hide the loading and show the div for no users. Also change the scope to GO_BACK.
function draw_no_users(){
	$("#first-loading").hide();
	$("#no-users").show();
	actualScope = ScopeValues.GO_BACK;
}

//Function that will render the user_list
function draw_user_list(){

	//Variables for the dynamic generating of the user list
	id_ini = "element-";
	id_final = "";

	for(var i=0;i<list_users.length;i++){

		//Create the li element
		li_element = $("<li/>");
		//Create the id of the li element
		id_final = id_ini + (i+1);

		//Add to the li element the ID, the ID of the user and the index of the user in the list. Also add the name of the user to the element.
		li_element.attr("id",id_final);
		li_element.attr("data-id",list_users[i].id);
		li_element.attr("data-index",i);
		li_element.html(list_users[i].name);

		if(i==0){
			//To the first element, add the glow class because is the one selected.
			li_element.addClass("option-glow");
		}

		//Add the li element to the user list
		$("#option-list").append(li_element);
	}

	//If there is only one user, hide the down arrow (the up is already hidden)
	if(list_users.length==1){
		$(".arrow-down").css("display","none");
	}

	//Save the id of the last element of the user carrusel in JQuery format.
	lastElement = "#" + id_final;

}

//Function that draws the sticker you can change for each user everytime you move the carrusel.
function draw_stickers_to_offer(){

	//The user ID of the user in the list
	userID = $(element).attr("data-id");
	//Your user ID.
	my_user_id = 2;

	trueUrl = "http://145.92.6.118/medialab-api/user/getDoublesForAnotherUser/"+my_user_id+"/"+userID+"/";
	fakeUrl = "./JSON_REQUESTS/stickersfortrade.json";

	//Ajax call tot he server to get the stickers you can trade with that user.
	call = $.ajax({
		type : "GET",
		url: trueUrl,
		contentType:"application/json",
		dataType:"json",
		success: function(json){

			if(json.response=="SUCCESS"){
				if(json.stickers.length>0){
					//If the server reponse is SUCCESS and there is at least 1 sticker, call draw_sticker_list, and after hide the loading image and show the stickers.
					draw_sticker_list(json.stickers);
					$("#loading").hide();
					$("#sticker-to-give-container").show();
				}else{
					//If there is no stickers for trade, call function draw_no_stickers
					draw_no_stickers();
				}
			}else if(json.response=="NO_STICKERS_FOUND"){
				//If there is no stickers for trade, call function draw_no_stickers
				draw_no_stickers(); 
			}else{
				window.location.href="./index.html";
			}

		}
	});

	//If the ajax function failed to connect to the server, then it loads the content from a stub JSON file. This has been done for testing. At production should show an error message or try the calling again.
	call.fail(function(){
		$.ajax({
		type : "GET",
		url: fakeUrl,
		contentType:"application/json",
		dataType:"json",
		success: function(json){
				if(json.response=="SUCCESS"){
					if(json.stickers.length>0){
						draw_sticker_list(json.stickers);
						$("#loading").hide();
						$("#sticker-to-give-container").show();
					}else{
						draw_no_stickers();	
					}
				}else if(json.response=="NO_STICKERS_FOUND"){
					draw_no_stickers();
				}else{
					window.location.href="./index.html";
				}

			}
		});
	});

}

//Function that hide the loading and show the div for no stickers. Also blocks the user go to the content, since there is no content.
function draw_no_stickers(){
	$("#loading").hide();
	$("#no-stickers").show();
	block_go_right = true;
}

//Function that render the stickers in the HTML.
function draw_sticker_list(stickers){

	//It will show 5 max stickers.
	for(var i=5;i>0;i--){

		//If we have enough stickers to trade (from 5 to 0)
		if(stickers.length>=i){
			//Update the HTML with the image, make it visible, and upload the navigation array.
			$("#trade-req-img-"+i).attr("src",stickers[i-1].image);
			$("#trade-req-img-"+i).parent().show();
			nav_array_overview[i-1][0]["id"] = "#trade-req-img-"+i;
			nav_array_overview[i-1][0]["url"] = ["make_request",stickers[i-1].id];
		}else{
			//If there is no enough stickers, hide the ones that does not have content.
			$("#trade-req-img-"+i).parent().hide();
			nav_array_overview[i-1][0]["id"] = undefined;
		}
	}

	//Write the name if the user you are selecting in the HTML
	$("#name-user").html($(element).html());
	//Let the user go to the content
	block_go_right = false;
}

//Function to make a request for a trade
function make_request(stickerIgive){

	//THe user ID
	my_user_id = 2;
	//THe user ID you want to trade
	userID = $(actualMenu).attr("data-id");
	//The sticker you want
	stickerIwant = $.getUrlVar("stickerid");

	trueUrl = "http://145.92.6.118/medialab-api/trade/createTrade/"+my_user_id+"/"+userID+"/"+stickerIgive+"/"+stickerIwant+"/";
	fakeUrl = "./JSON_REQUESTS/createtrade.json";

	//Make the AJAX call to the createTrade entrypoint of the server.
	call = $.ajax({
		type : "POST",
		url: trueUrl,
		contentType:"application/json",
		dataType:"json",
		success: function(json){

			if(json.response=="SUCCESS"){
				//If the server response is SUCCESS, show success popup and change the scope to SUCCESS_MENU.
				$("#request-success").modal("show");
				actualScope = ScopeValues.SUCCESS_MENU;
			}else{
				//If the server shows an error, show error popup and change the scope to ERROR_MENU.
				$("#request-error").modal("show");
				actualScope = ScopeValues.ERROR_MENU;
			}

		}
	});

	//If the ajax function failed to connect to the server, then it loads the content from a stub JSON file. This has been done for testing. At production should show an error message or try the calling again.
	call.fail(function(){

		call = $.ajax({
			type : "POST",
			url: fakeUrl,
			contentType:"application/json",
			dataType:"json",
			success: function(json){

				console.log(json.response);

				if(json.response=="SUCCESS"){
					$("#request-success").modal("show");
					actualScope = ScopeValues.SUCCESS_MENU;
				}else{
					$("#request-error").modal("show");
					actualScope = ScopeValues.ERROR_MENU;
				}

			}
		});

	});
}

//Everytime the user press a key, call the function navigate.
document.addEventListener("keydown", navigate, true);

//jQuery plugin to retireve data from the URL
$.extend({
  getUrlVars: function(){
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
      hash = hashes[i].split('=');
      vars.push(hash[0]);
      vars[hash[0]] = hash[1];
    }
    return vars;
  },
  getUrlVar: function(name){
    return $.getUrlVars()[name];
  }
});