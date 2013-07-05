//Javascript file
//Navigation page for the album view

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
	OVERVIEW : "OVERVIEW",
	YOUR_REQ : "YOUR REQUESTS",
	OTHER_REQ : "OTHER REQUESTS",
	REQ_ACCEPTED : "REQ_ACCEPTED",
	ERROR_REQ : "ERROR_REQ"
}

//Max index to access to the navigation array.
var maxx;
var maxy;

//Initial index to access the navigation array.
var focusx = 0;
var focusy = 1;

//The actual scope is the carrusel, and the user can navigate.
var actualScope = ScopeValues.MENU;

//Variables to manage the trade
var id_actual_trade = -1;
var id_sticker_obtained = -1;

//Variable to block the user to go to the content when there is no content
var block_right = false;

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

//Function that initialize the navigation array. In this case, is a Matrix of 5x2 which is simulated in , and each cell represents one button. For each button there are to elements:
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
	
	
	nav_array_overview[0][1]["id"] = "#overview-req-img-1";
	nav_array_overview[1][1]["id"] = "#overview-req-img-2";
	nav_array_overview[2][1]["id"] = "#overview-req-img-3";
	nav_array_overview[3][1]["id"] = "#overview-req-img-4";
	nav_array_overview[4][1]["id"] = "#overview-req-img-5";
	
	//Define the max index for the matrix.
	maxx = nav_array_overview.length - 1;
	maxy = nav_array_overview[0].length - 1;
}


//Function that will be call everytime a key is pressed.
function navigate(e){
	
	switch (actualScope){
		case ScopeValues.MENU:
			//If we are in the carrusel, call navigate_in_menu
			navigate_in_menu(e);
			break;
		case ScopeValues.OVERVIEW:
			//If we are in the overview, call navigate_in_overview
			navigate_in_overview(e);
			break;
		case ScopeValues.YOUR_REQ:
			//If we are in the user requests, call navigate_in_your_requests
			navigate_in_your_requests(e);
			break;
		case ScopeValues.OTHER_REQ:
			//Uf we are in other people request, call navigate_in_other_requests
			navigate_in_other_requests(e);
			break;
		case ScopeValues.REQ_ACCEPTED:
			//If we are in the accepted request popup, there are three choices.
			if(e.keyCode==VK_UP){
				//If press arrow up, focus the up button.
				$("#request-accepted a:eq(0)").addClass("button-glow");
				$("#request-accepted a:eq(1)").removeClass("button-glow");
			}else if(e.keyCode==VK_DOWN){
				//If press arrow down, focus the down button.
				$("#request-accepted a:eq(1)").addClass("button-glow");
				$("#request-accepted a:eq(0)").removeClass("button-glow");
			}else if(e.keyCode==VK_ENTER){
				//If press OK, chack wich class has the focus.
				if($("#request-accepted a:eq(0)").hasClass("button-glow")){
					//If it is the first, hide popup, move focus to list and change scope to MENU passing thrught OTHER_REQ.
					$("#request-accepted").modal("hide");
					actualScope = ScopeValues.OTHER_REQ;
					change_scope(ScopeValues.MENU);
					//Hide the requests container, show the loading and call load_external_request to render the next trade request.
					$("#people-requests-container").hide();
					$("#loading").show();
					load_external_requests();
				}else{
					//If is the second button, redirect the user to the album with the ID of the player obtained in the URL, but first replace the actual history to "index.html", so when the user 
					//press back it goes to index from the album.
					history.replaceState({},"Index","./index.html");
					window.location.href = "./album.html?playerid=" + id_sticker_obtained;
				}
			}
			e.preventDefault();
			break;
		case ScopeValues.ERROR_REQ:
			//IF we are int the error popup, reload the page when pressing OK.
			if(e.keycode==VK_ENTER){
				window.location.reload();
			}
			e.preventDefault();
			break;
		default:
			break;
	}
}

//navigate_in_menu function
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
			//Only act if there is content on the right side of the page. Depending on what element is the actualMenu, change scope according to that.
			if(!block_right){
				if(actualMenu=="#element-1"){
					change_scope(ScopeValues.OVERVIEW);
				}else if(actualMenu=="#element-2"){
					change_scope(ScopeValues.OTHER_REQ);
				}else if(actualMenu=="#element-3"){
					change_scope(ScopeValues.YOUR_REQ);
				}
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
		//Hide the actual view and show a loading view
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

//Function that change the content when the carrusel is moved.
function change_content(){
	
	//Hide three possible screens.
	$("#people-requests-container").hide();
	$("#market-overview").hide();
	$("#your-requests-container").hide();

	//Show loading page.
	$("#loading").show();

	//Depending on the actual menu, call load_overview, load_external_requests or load_your_requests.
	if(actualMenu=="#element-1"){
		load_overview();
	}else if(actualMenu=="#element-2"){
		load_external_requests();
	}else if(actualMenu=="#element-3"){
		load_your_requests();
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

//Just call get_last_trades
function load_overview(){
	get_last_trades();
}

//Fuction that call get_user_requests, and after that remove the loading image and show the request container
function load_your_requests(){
	get_user_requests();
	$("#loading").hide();
	$("#your-requests-container").show();
}

//Fuction that call get_trades_requests, and after that remove the loading image and show the people request container
function load_external_requests(){
	get_trades_requests();
	$("#loading").hide();
	$("#people-requests-container").show();	
}

//Function called when the scope is changed bewteen the carrusel and the content.
function change_scope(scope){

	if(scope==ScopeValues.MENU){

		//If changing to the carrusel.
		//Add focus to the graphic elements of the carrusel (rectangule and arrows.)
		$("#element-selected").css("background-color","#e74c3c");
		$(".arrow-up").css("border-bottom-color","#e74c3c");
		$(".arrow-down").css("border-top-color","#e74c3c");

		//If actual scope is overview
		if(actualScope==ScopeValues.OVERVIEW){
			//Load the actual menu into element
			prevElement = element;
			element = actualMenu;
			//remove glow in previous element
			$(prevElement).parent().removeClass("sticker-glow");
			$("#stickers-overview-requests").removeClass("whitelay");
			//Reset navigation of overview and finally change scope.
			focusx = 0;
			focusy = 1;
			actualScope = scope;
		//If actual scope is other requets
		}else if(actualScope==ScopeValues.OTHER_REQ){
			//Load the actual menu into element
			prevElement = element;
			element = actualMenu;
			//remove glow in previous element and change scope.
			$("#decline-button").removeClass("glow-button");
			$("#accept-button").removeClass("glow-button");
			$("#request-number").css("background-color","");
			actualScope = scope;
		}

	}else if(scope==ScopeValues.OVERVIEW){

		//If changing to the voerview.
		//Remove focus to the graphic elements of the carrusel (rectangule and arrows.)
		$("#element-selected").css("background-color","#3B5D7F");
		$(".arrow-up").css("border-bottom-color","#3B5D7F");
		$(".arrow-down").css("border-top-color","#3B5D7F");

		//Update element variables and add glow to the stickers in the overview, finally change scope.
		prevElement = element;
		actualMenu = element;
		element = "#overview-req-img-1";
		$("#overview-req-img-1").parent().addClass("sticker-glow");
		$("#stickers-overview-requests").addClass("whitelay");
		actualScope = scope;

	}else if(scope==ScopeValues.OTHER_REQ){
		
		//If changing to the other requests.
		//Remove focus to the graphic elements of the carrusel (rectangule, notification number and arrows.)
		$("#element-selected").css("background-color","#3B5D7F");
		$("#request-number").css("background-color","#3B5D7F");
		$(".arrow-up").css("border-bottom-color","#3B5D7F");
		$(".arrow-down").css("border-top-color","#3B5D7F");

		//Set the glow to the decline button, update the element variables and actualMenu, and finally change the scope.
		$("#decline-button").addClass("glow-button");
		prevElement = element;
		actualMenu = element;
		element = "#decline-button";
		actualScope = scope;

	}
}

//Function to navigate inside the overview view of the market.
function navigate_in_overview(e){

	//Depending on which key has you pressed, you will change focus or not.

	switch(e.keyCode){

		//For the arrow keys, call the functions of navigations to go left, right. Up and down don't do anything because we only have one row.

		case VK_UP:
			e.preventDefault();
			break;
		case VK_DOWN:
			e.preventDefault();
			break;
		case VK_RIGHT:
			nav_overview_right();
			e.preventDefault();
			break;
		case VK_LEFT:
			//If we are in the first column of sticker and press LEFT, change scope to the MENU. Otherwise not.
			if(element=="#overview-new-img-1"||element=="#overview-req-img-1"){change_scope(ScopeValues.MENU);}
			else{nav_overview_left();}
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
				//In other case, redirect to the url replacion market.html to index.html
				history.replaceState({},"Index","./index.html");
				window.location.href = nav_array_overview[focusx][focusy]["url"];
			}
			e.preventDefault();
		default:
			break;
	}

}

//Function to navigate right in the overview. Check documentation for more information.
function nav_overview_right(){

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
function nav_overview_left(){
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

//Function to navigate inside the requests that other people has made to the user
function navigate_in_other_requests(e){

	//Depending on which key has you pressed, you will change focus or not.

	switch(e.keyCode){

		//For the arrow keys, do somehitng when pressed left, right or OK. Up and down don't do anything because we only have one row.

		case VK_UP:
			e.preventDefault();
			break;
		case VK_DOWN:
			e.preventDefault();
			break;
		case VK_RIGHT:
			//If the focus is on the decline button, change it to the accept button.
			if(element=="#decline-button"){
				prevElement = element;
				element = "#accept-button";
				$(prevElement).removeClass("glow-button");
				$(element).addClass("glow-button");
			}
			e.preventDefault();
			break;
		case VK_LEFT:
			//If the focus is on the decline button, change scope to MENU.
			if(element=="#decline-button"){change_scope(ScopeValues.MENU);}
			else{
				//If the focus is on the accept button, change it to the decline button.
					if(element=="#accept-button"){
					prevElement = element;
					element = "#decline-button";
					$(prevElement).removeClass("glow-button");
					$(element).addClass("glow-button");
				}
			}
			e.preventDefault();
			break;
		case VK_ENTER:
			//If the decline button is focused, decline the trade calling decline_trade.
			if(element=="#decline-button"){
				decline_trade();
			}else if(element=="#accept-button"){
				//If the accept button is focused, accept the trade calling accept_trade.
				accept_trade();
			}
			e.preventDefault();
		default:
			break;
	}

}

//Function to get the last 5 trades accepted for the user
function get_last_trades(){

	//The user ID
	my_user_id = 2;

	trueUrl = "http://145.92.6.118/medialab-api/trade/getLastTrades/"+my_user_id+"/ACCEPTED/5/";
	fakeUrl = "./JSON_REQUESTS/lasttrades.json";

	//AJAX to retrieve the trades
	usersToTradeAjaxRequest = $.ajax({
		type : "GET",
		url: trueUrl,
		contentType:"application/json",
		dataType:"json",
		success: function(json){

			if(json.response=="SUCCESS"){
				//If the server response is SUCCESS and it has more than one sitcker
				if(json.trades.length>0){
					//Render the last trades calling draw_last_trades
					draw_last_trades(json.trades);
					//SHow the overview and hide the other screens. Not block right.
					$("#stickers-overview-requests").show();
					$("#no-last-trades").hide();
					$("#loading").hide();
					$("#market-overview").show();
					block_right = false;
				}
				else{
					//If the server response is SUCCESS and it has less than one sitcker
					//SHow the message that there is no accepted trades.
					$("#stickers-overview-requests").hide();
					$("#no-last-trades").show();
					$("#loading").hide();
					$("#market-overview").show();
					block_right = true;
				}
			}else{
				window.location.href="./index.html";
			}

		}
	});

	//If the ajax function failed to connect to the server, then it loads the content from a stub JSON file. This has been done for testing. At production should show an error message or try the calling again.
	usersToTradeAjaxRequest.fail(function(){
		$.ajax({
			type : "GET",
			url: fakeUrl,
			contentType:"application/json",
			dataType:"json",
			success: function(json){

				if(json.response=="SUCCESS"){
					if(json.trades.length>0){
						draw_last_trades(json.trades);
						$("#stickers-overview-requests").show();
						$("#no-last-trades").hide();
						$("#loading").hide();
						$("#market-overview").show();
						block_right = false;
					}
					else{
						$("#stickers-overview-requests").hide();
						$("#no-last-trades").show();
						$("#loading").hide();
						$("#market-overview").show();
						block_right = true;
					}
				}else{
					window.location.href="./index.html";
				}

			}
		});
	})
}

//Render the last trades in the overview of the market
function draw_last_trades(trades){

	//The user ID, to check if he made the trade or he accept the trade.
	var my_user_id = 2;

	//Dinamically add this trades if there are enough retireve from the server.
	for(var i=5;i>0;i--){
		if(trades.length>=i){
			//If the user is the initiator
			if(trades[i-1].initiator.id==my_user_id){
				//Put the image of the respondent sticker and show it.
				$("#overview-req-img-"+i).attr("src",trades[i-1].respondentSticker.image);
				$("#overview-req-img-"+i).parent().show();
				//Update the nav_array_overview to work with that sticker and enable the url to redirect the user to the album is he press OK on it.
				nav_array_overview[i-1][1]["id"] = "#overview-req-img-"+i;
				nav_array_overview[i-1][1]["url"] = "./album.html?playerid="+trades[i-1].respondentSticker.id;
				//If the user is not the initiator
			}else{
				//Put the image of the initiator sticker and show it.
				$("#overview-req-img-"+i).attr("src",trades[i-1].initiatorSticker.image);
				$("#overview-req-img-"+i).parent().show();
				//Update the nav_array_overview to work with that sticker and enable the url to redirect the user to the album is he press OK on it.
				nav_array_overview[i-1][1]["id"] = "#overview-req-img-"+i;
				nav_array_overview[i-1][1]["url"] = "./album.html?playerid="+trades[i-1].initiatorSticker.id;
			}
		}else{
			//If there is no enought trades, hide the element without sticker and modify the nav_array_overview.
			$("#overview-req-img-"+i).parent().hide();
			nav_array_overview[i-1][1]["id"] = undefined;
		}
	}
}

//Function to get the last requests that the user has to accept or decline
function get_trades_requests(){

	//The user ID
	my_user_id = 2;

	trueUrl = "http://145.92.6.118/medialab-api/trade/getRespondentTrade/"+my_user_id+"/WAITING/0";
	fakeUrl = "./JSON_REQUESTS/tradesrequests.json";

	//AJAX to retrieve the trades
	call = $.ajax({
		type : "GET",
		url: trueUrl,
		contentType:"application/json",
		dataType:"json",
		success: function(json){

			if(json.response=="SUCCESS"){
				if(json.trades.length>0){
					//If the server response is SUCCESS and it has more than one sitcker call draw_trades_requests with all the trades.
					draw_trades_requests(json.trades);
					$("#request-form-people").show();
					$("#no-request-from-people").hide();
					block_right = false;
				}else{
					//If the server response is SUCCESS and it has less than one sitcker show the message that the user does not have requets from people and put 0 in the notification number.
					$("#request-number").html(0);
					$("#request-form-people").hide();
					$("#no-request-from-people").show();
					//Block right because there is no content to go.
					block_right = true;
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

				if(json.response=="SUCCESS"){
					if(json.trades.length>0){
						draw_trades_requests(json.trades);
						$("#request-form-people").show();
						$("#no-request-from-people").hide();
						block_right = false;
					}else{

						$("#request-number").html(0);
						$("#request-form-people").hide();
						$("#no-request-from-people").show();
						block_right = true;
					}
				}else{
					window.location.href="./index.html";
				}

			}
		});

	});
}

//Function that render the trade reques one by one. If one is accepted or declined.
function  draw_trades_requests(trades){
	//Update image of initiator
	$("#sticker-to-change").attr("src",trades[0].initiatorSticker.image);
	//Update image of respondent
	$("#sticker-to-get").attr("src",trades[0].respondentSticker.image);
	//Write initiator name on the app
	$("#name-user-trade").html(trades[0].initiator.name);
	//Write the number of notifications they have.
	$("#request-number").html(trades.length);

	//Save the id of the sticker that I'm looking.
	id_actual_trade = trades[0].id;
	id_sticker_obtained = trades[0].initiatorSticker.id;
}

//Function to get the last requests that the user has to accept or decline
function get_user_requests(){

	//The User ID
	my_user_id = 2;

	trueUrl = "http://145.92.6.118/medialab-api/trade/getInitiatorTrade/"+my_user_id+"/WAITING/0";
	fakeUrl = "./JSON_REQUESTS/tradesrequests.json";

	//AJAX call to retrieve the trades that the user has made and is waiting for answer.
	call = $.ajax({
		type : "GET",
		url: trueUrl,
		contentType:"application/json",
		dataType:"json",
		success: function(json){

			if(json.response=="SUCCESS"){
				if(json.trades.length>0){
					//If the server response is SUCCESS and it has more than one sitcker call draw_user_trades_requests with all the trades.
					draw_user_trades_requests(json.trades);
					$("#user-made-request").show();
					$("#no-user-made-requests").hide();
				}else{
					//If the server response is SUCCESS and it has less than one sitcker show the message that the user does not have requets from people and put 0 in the notification number.
					$("#offered-number").html(0);
					$("#user-made-request").hide();
					$("#no-user-made-requests").show();
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

				if(json.response=="SUCCESS"){
					if(json.trades.length>0){
						draw_user_trades_requests(json.trades);
						$("#user-made-request").show();
						$("#no-user-made-requests").hide();
					}else{
						$("#offered-number").html(0);
						$("#user-made-request").hide();
						$("#no-user-made-requests").show();
					}
				}else{
					window.location.href="./index.html";
				}

			}
		});

	});

}

//Function that render the user trade requests with their timer
function draw_user_trades_requests(trades){

	//Write the notification number.
	$("#offered-number").html(trades.length);

	//If there are enough trades retrieve from the server.
	if(trades.length>=5){
		//Take the first element
		element5 = "#sticker-name-1";
		//Calculate how much time is left for that trade and write it in the html.
		hours = 24 - Math.round((new Date().getTime() - trades[4].startDate)/3600000);
		$("#timer1 span").html(hours);

		//Make an AJAX request to get the information about the sticker.
		call = $.ajax({
			type : "GET",
			url: "http://145.92.6.118/medialab-api/player/getByStickerId/"+trades[4].respondentSticker.id+"/",
			contentType:"application/json",
			dataType:"json",
			success: function(json){

				if(json.response=="SUCCESS"){
					//If the information is retrieve correctly, show its name.
					$(element5).html(json.players[0].name);
				}else{
					//If not, hide the request.
					$(element5).parent().css("display","none");		
				}

			}
		});
	}else{
		//If there are not enough trades retrieve, hide the sticker from the html.
		element5 = "#sticker-name-1";
		$(element5).parent().css("display","none");		

	}

	//Next code is the same as the previous one but for each sticker in the trade, up to 4 more. Probably it could be refactor into just one loop.

	if(trades.length>=4){
		element4 = "#sticker-name-2";
		hours = 24 - Math.round((new Date().getTime() - trades[3].startDate)/3600000);
		$("#timer2 span").html(hours);

		call = $.ajax({
			type : "GET",
			url: "http://145.92.6.118/medialab-api/player/getByStickerId/"+trades[3].respondentSticker.id+"/",
			contentType:"application/json",
			dataType:"json",
			success: function(json){

				if(json.response=="SUCCESS"){
					$(element4).html(json.players[0].name);
				}else{
					$(element4).parent().css("display","none");		
				}

			}
		});
	}else{
		element4 = "#sticker-name-2";
		$(element4).parent().css("display","none");		
	}

	if(trades.length>=3){
		element3 = "#sticker-name-3";
		hours = 24 - Math.round((new Date().getTime() - trades[2].startDate)/3600000);
		$("#timer3 span").html(hours);

		call = $.ajax({
			type : "GET",
			url: "http://145.92.6.118/medialab-api/player/getByStickerId/"+trades[2].respondentSticker.id+"/",
			contentType:"application/json",
			dataType:"json",
			success: function(json){

				if(json.response=="SUCCESS"){
					$(element3).html(json.players[0].name);
				}else{
					$(element3).parent().css("display","none");		
				}

			}
		});
	}else{
		element3 = "#sticker-name-3";
		$(element3).parent().css("display","none");		
	}

	if(trades.length>=2){
		element2 = "#sticker-name-4";
		hours = 24 - Math.round((new Date().getTime() - trades[1].startDate)/3600000);
		$("#timer4 span").html(hours);

		call = $.ajax({
			type : "GET",
			url: "http://145.92.6.118/medialab-api/player/getByStickerId/"+trades[1].respondentSticker.id+"/",
			contentType:"application/json",
			dataType:"json",
			success: function(json){

				if(json.response=="SUCCESS"){
					$(element2).html(json.players[0].name);
				}else{
					$(element2).parent().css("display","none");		
				}

			}
		});
	}else{
		element2 = "#sticker-name-4";
		$(element2).parent().css("display","none");		
	}

	if(trades.length>=1){
		element1 = "#sticker-name-5";
		hours = 24 - Math.round((new Date().getTime() - trades[0].startDate)/3600000);
		$("#timer5 span").html(hours);

		call = $.ajax({
			type : "GET",
			url: "http://145.92.6.118/medialab-api/player/getByStickerId/"+trades[0].respondentSticker.id+"/",
			contentType:"application/json",
			dataType:"json",
			success: function(json){

				if(json.response=="SUCCESS"){
					$(element1).html(json.players[0].name);
				}else{
					$(element1).parent().css("display","none");		
				}

			}
		});
	}else{
		element1 = "#sticker-name-5";
		$(element1).parent().css("display","none");		
	}


}

//Function that accepts a trade
function accept_trade(){

	my_user_id = 2;

	trueUrl = "http://145.92.6.118/medialab-api/trade/updateTrade/"+id_actual_trade+"/ACCEPTED/";
	fakeUrl = "./JSON_REQUESTS/acceptrequest.json";

	//AJAX function to the entrypoint updateTrade, making the trade as accepted.
	usersToTradeAjaxRequest = $.ajax({
		type : "PUT",
		url: trueUrl,
		contentType:"application/json",
		dataType:"json",
		success: function(json){

			if(json.response=="SUCCESS"){
				//If the server response is SUCCESS, show the accepted popup and change the scope to REQ_ACCEPTED
				$("#request-accepted").modal("show");
				actualScope = ScopeValues.REQ_ACCEPTED;
			}else{
				//If the server response is not SUCCESS, show the error popup and change the scope to ERROR_REQ
				$("#request-error").modal("show");
				actualScope = ScopeValues.ERROR_REQ;
			}

		}
	});

}

//Function that declines a trade
function decline_trade(){

	my_user_id = 2;

	trueUrl = "http://145.92.6.118/medialab-api/trade/updateTrade/"+id_actual_trade+"/REFUSED/";
	fakeUrl = "./JSON_REQUESTS/acceptrequest.json";

	//AJAX function to the entrypoint updateTrade, making the trade as refused.
	usersToTradeAjaxRequest = $.ajax({
		type : "PUT",
		url: trueUrl,
		contentType:"application/json",
		dataType:"json",
		success: function(json){

			if(json.response=="SUCCESS"){
				//If the server response is SUCCESS, change the scope to the menu and load next trade, hiding also the request container and showing the loading image.
				change_scope(ScopeValues.MENU);
				$("#people-requests-container").hide();
				$("#loading").show();
				load_external_requests();
			}else{
				//If the server response is not SUCCESS, show the error popup and change the scope to ERROR_REQ
				$("#request-error").modal("show");
				actualScope = ScopeValues.ERROR_REQ;
			}

		}
	});
}

//Function that shows the number of stickers in the news section. It is called from the main.js file.
function market_news(number){
	console.log(number);
	$("#number-stickers-news").html(number);
}

//Everytime the user press a key, call the function navigate.
document.addEventListener("keydown", navigate, true);