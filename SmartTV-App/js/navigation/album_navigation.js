//Javascript file
//Navigation page for the album view

//Actual element and previous element.
var element = "#element-3";
var prevElement = "#element-3";

//The last element is the id of the last element inside the carrusel. The actual Menu is the element selected in the carrusel, so it does not get override when we move the focus
//inside the content
var lastElement = "#element-5";
var actualMenu = element;

//Array for the navigation.
var navigationArray;

//Initial index to access the navigation array.
var focusx = 0;
var focusy = 0;

//Max index to access to the navigation array.
var maxx;
var maxy;

//Loading stickers variables
var globalTeams;
var stickersOfActiveTeam;
var stickersDoubled;
var userStickersAjaxRequest;
var numberOfSctickersOfActualTeam = -1;
var show_player_ok = false;


//Scope values. Each scope has different ways of navigation.
ScopeValues = {
	MENU : "MENU",
	CONTENT : "CONTENT",
	PLAYER : "PLAYER"
}

//The actual scope is the carrusel, and the user can navigate.
var actualScope = ScopeValues.MENU;
var canNavigate = true;

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


//Function that initialize the navigation array. In this case, is a Matrix of 6x2 which is simulated in , and each cell represents one button. For each button there are to elements:
	//ID of the elements of the html.
	//URL or action to do when ok is pressed at that element.
//Check the documentation to undrestand how this kind of navigations works.
function initialize_nav_array(){
	
	//Fill the navigation array.
	navigationArray = new Array(6);
	for(var i=0;i<navigationArray.length;i++){
		navigationArray[i] = new Array(2);
		navigationArray[i][0] = new Array(2);
		navigationArray[i][1] = new Array(2);
	}
	
	navigationArray[0][0]["id"] = "#sticker-img-1";
	navigationArray[0][1]["id"] = "#sticker-img-7";
	navigationArray[1][0]["id"] = "#sticker-img-2";
	navigationArray[1][1]["id"] = "#sticker-img-8";
	navigationArray[2][0]["id"] = "#sticker-img-3";
	navigationArray[2][1]["id"] = "#sticker-img-9";
	navigationArray[3][0]["id"] = "#sticker-img-4";
	navigationArray[3][1]["id"] = "#sticker-img-10";
	navigationArray[4][0]["id"] = "#sticker-img-5";
	navigationArray[4][1]["id"] = "#sticker-img-11";
	navigationArray[5][0]["id"] = "#sticker-img-6";
	navigationArray[5][1]["id"] = "#sticker-img-11";
	
	//Define the max index for the matrix.
	maxx = navigationArray.length - 1;
	maxy = navigationArray[0].length - 1;
}

//Function that will be call everytime a key is pressed.
function navigate(e){
	
	switch (actualScope){
		case ScopeValues.MENU:
			//If we are in the carrusel, call navigate_in_menu
			navigate_in_menu(e);
			break;
		case ScopeValues.CONTENT:
			//If we are in the content, call navigate_in_content
			navigate_in_content(e);
			break;
		case ScopeValues.PLAYER:
			//If we are in the player description, call navigate_in_content
			navigateInPlayer(e);
			break;
		default:
			break;
	}
}

//navigate_in_menu function
function navigate_in_menu(e){
	
	if(canNavigate){
	
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
				//Chante to content scope			
				change_scope(ScopeValues.CONTENT);
				e.preventDefault();
				break;
			case VK_LEFT:
				e.preventDefault();
				break;
			default:
				break;	
		}
	}
}

//Function that moves the carrusel up.
function menu_nav_up(){

	//If the actual element is not the last one
	if(element != lastElement){
		//Hide the actual view and show a loading view
		$("#complete-view").css("display","none");
		$("#loading").css("display","block");
		//Change the focus calling change_focus_carrusel
		change_focus_carrusel("up");
		//Move the carrusel up and call change_content after that.
		$('#flag-list').animate({
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

//menu_nav_down function
function menu_nav_down(){
	
	//If the actual element is not the first one
	if(element != "#element-1" ){
		//Hide the actual view and show a loading view
		$("#complete-view").css("display","none");
		$("#loading").css("display","block");
		//Change the focus calling change_focus_carrusel
		change_focus_carrusel("down");
		//Move the carrusel down and call change_content after that.
		$('#flag-list').animate({
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
	
	//Reset the number of stickers of the actual Team.
	numberOfSctickersOfActualTeam = -1;
	//Cancel any AJAX request of the actual content
	userStickersAjaxRequest.abort();
	//Call the function to get the stickers of the new selected team.
	getStickersFromUser();
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
		$(prevElement).removeClass("flag-glow");
		$(element).addClass("flag-glow");
	
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
		$(prevElement).removeClass("flag-glow");
		$(element).addClass("flag-glow");
		
	}else{
		return false;	
	}
	
}

//Function called when the scope is changed bewteen the carrusel and the content.
function change_scope(scope){

	if(scope == ScopeValues.CONTENT){

		//If changing to the content.
		//Remove focus to the graphic elements of the carrusel (rectangule and arrows.)
		$("#element-selected").css("background-color","#3B5D7F");
		$(".arrow-up").css("border-bottom-color","#3B5D7F");
		$(".arrow-down").css("border-top-color","#3B5D7F");

		//save the element in actualMenu, and update the actual element and the previous element.
		prevElement = element;
		actualMenu = element;
		element = "#sticker-img-1";
		//Add the glow to the acual element and finally the scope is updated.
		$("#list-sticker-1").addClass("sticker-glow");
		$("#complete-view").addClass("whitelay");
		actualScope = scope;

	}else if(scope == ScopeValues.MENU){

		//If changing to the carrusel.
		//Add focus to the graphic elements of the carrusel (rectangule and arrows.)
		$("#element-selected").css("background-color","#e74c3c");
		$(".arrow-up").css("border-bottom-color","#e74c3c");
		$(".arrow-down").css("border-top-color","#e74c3c");

		//Update the actual element using the actualMenu that is saved, and the glow is removed from the previous element.
		prevElement = element;
		element = actualMenu;
		$(prevElement).parent().removeClass("sticker-glow");
		$("#complete-view").removeClass("whitelay");

		//Also, the indexes of the navigation array are reseted and finally the scope is updated.
		focusx = 0;
		focusy = 0;
		actualScope = scope;
	}

}

//Navigate in the content (stickers of a team)
function navigate_in_content(e){
	
	//Depending on which key has you pressed, you will change focus or not.

	switch(e.keyCode){
	
		//For the arrow keys, call the functions of navigations to go left, right, up and down in this kind of navigation.	
		case VK_UP:
			nav_content_up();
			e.preventDefault();
			break;
		case VK_DOWN:
			nav_content_down();
			e.preventDefault();
			break;
		case VK_RIGHT:
			nav_content_right();
			e.preventDefault();
			break;
		case VK_LEFT:
			//If left is pressed and the user is in the first column (stickers 1 or 7), change the scope to the carrusel.
			if(element=="#sticker-img-1"||element=="#sticker-img-7"){change_scope(ScopeValues.MENU);}
			//If not, navigate normally.
			else{nav_content_left();}
			e.preventDefault();
			break;
		case VK_ENTER:
			//If ENTER is pressed:
			if($.isArray(navigationArray[focusx][focusy]["url"])){
				//If the element in the url compoenent of the actual element in the navigation array is an array, is a function with their arguments, so call the function.
				window[navigationArray[focusx][focusy]["url"][0]](navigationArray[focusx][focusy]["url"][1]);
			}else if(navigationArray[focusx][focusy]["url"]=="BACK"){
				//If the element in the url compoenent of the actual element is the string "BACK", go to the previous page
				window.history.back();
			}else if(navigationArray[focusx][focusy]["url"].indexOf('.')!=0){
				//If the element in the url compoenent of the actual element is a string not starting with a dot, is a function without argument, so call it.
				window[navigationArray[focusx][focusy]["url"]]();
			}else{
				//In other case, redirect to the url.
				window.location.href = navigationArray[focusx][focusy]["url"];
			}
			e.preventDefault();
		default:
			break;
		
	}
	
}

//Function to navigate down in the overview. Check documentation for more information.
function nav_content_down(){
	if(focusy+1<=maxy){
		for(var i=0;focusy+1+i<=maxy;i++){
			var ty = focusy+1+i;
			if(navigationArray[focusx][ty]["id"]!=undefined && navigationArray[focusx][ty]["id"]!=element){
				prevElement=element;
				element = navigationArray[focusx][ty]["id"];	
				focusy=ty;
				$(prevElement).parent().removeClass("sticker-glow");
				$(element).parent().addClass("sticker-glow");
				break;
			}
		}
	}
}

//Function to navigate up in the overview. Check documentation for more information.
function nav_content_up(){
	if(focusy-1>=0){
		for(var i=0;focusy-1-i>=0;i++){
			var ty = focusy-1-i;
			if(navigationArray[focusx][ty]["id"]!=undefined && navigationArray[focusx][ty]["id"]!=element){
				prevElement=element;
				element = navigationArray[focusx][ty]["id"];	
				focusy=ty;
				$(prevElement).parent().removeClass("sticker-glow");
				$(element).parent().addClass("sticker-glow");
				break;
			}
		}

	}
}

//Function to navigate right in the overview. Check documentation for more information.
function nav_content_right(){
	if(focusx+1<=maxx){
		for(var i=0;focusx+1+i<=maxx;i++){
			var tx = focusx+1+i;
			if(navigationArray[tx][focusy]["id"]!=undefined && navigationArray[tx][focusy]["id"]!=element){
				prevElement=element;
				element = navigationArray[tx][focusy]["id"];	
				focusx=tx;
				$(prevElement).parent().removeClass("sticker-glow");
				$(element).parent().addClass("sticker-glow");
				break;
			}
		}
	}   
}

//Function to navigate left in the overview. Check documentation for more information.
function nav_content_left(){
	if(focusx-1>=0){
		for(var i=0;focusx-1-i>=0;i++){
			var tx = focusx-1-i;
			if(navigationArray[tx][focusy]["id"]!=undefined && navigationArray[tx][focusy]["id"]!=element){
				prevElement=element;
				element = navigationArray[tx][focusy]["id"];	
				focusx=tx;
				$(prevElement).parent().removeClass("sticker-glow");
				$(element).parent().addClass("sticker-glow");
				break;
			}
		}
	}     	
}

//Function that retrieve the teams from the server, only the first time page is loaded.
function getTeamsData(){
	
	trueUrl = "http://145.92.6.118/medialab-api/album/getTeams/";
	fakeUrl = "./JSON_REQUESTS/getAllTeamsStub.json";

	//Make the AJAX request to the getTeams entrypoint.
	call = $.ajax({
		type:"GET",
		url: trueUrl,
		contentType:"application/json",
		dataType:"json",
		success: function(json){
			if(json.response == "SUCCESS"){
				//If the server response with a SUCCESS, save the teams.
				globalTeams = json.teams;
				//Get a player id from the URL
				playerid = $.getUrlVar("playerid");
				//If the playerid exists and is a correct number, is because we want to open a specific player. So we call perpareView
				if(!isNaN(parseFloat(playerid)) && isFinite(playerid)){
					prepareView(playerid);
				}else{
					//If there is no id, we call directly getStickersFromUsers
					getStickersFromUser();
				}

			}else{
				console.log("ERROR GETING TEAMS");
			}		
		}
	});

	//If the ajax function failed to connect to the server, then it loads the content from a stub JSON file. This has been done for testing. At production should show an error message or try the calling again.
	call.fail(function(){

		console.log("Error from server. Loading fake JSON for teams.");

		$.ajax({
			type:"GET",
			url: fakeUrl,
			contentType:"application/json",
			dataType:"json",
			success: function(json){
				if(json.response == "SUCCESS"){
					globalTeams = json.teams;

					playerid = $.getUrlVar("playerid");

					if(!isNaN(parseFloat(playerid)) && isFinite(playerid)){
						prepareView(playerid);
					}else{
						getStickersFromUser();
					}
					
				}else{
					console.log("ERROR GETING TEAMS");
				}		
			}
		});		
	});
}

//It removes the doubled stickers from an array of stickers, and return it.
function remove_doubled(arraystickers){

	temp = new Array();
	id_inside = new Array();

	for(var i=0;i<arraystickers.length;i++){
		if(id_inside.indexOf(arraystickers[i].id)==-1){
			temp.push(arraystickers[i]);
			id_inside.push(arraystickers[i].id);
		}
	}

	return temp;



}

//Function that retrieve from the server the stickers of a specific user and team, it is called perioducally.
function getStickersFromUser(){

	//The user ID
	my_user_id = 2;
	//The Team ID that is actually selected.
	countryID = $(actualMenu).attr("data-country");

	trueUrl = "http://145.92.6.118/medialab-api/user/getUserStickers/"+my_user_id+"/"+countryID+"/";
	fakeUrl = "./JSON_REQUESTS/userstickers.json";

	//If the teams are defined make the AJAX call to the getUserStickers entrypoint.
	if(globalTeams != undefined){
		
		userStickersAjaxRequest = $.ajax({
			type : "GET",
			url: trueUrl,
			contentType:"application/json",
			dataType:"json",
			success: function(json){
				
				if(json.response == "STICKERS_RETRIEVE_SUCESS"){
					//IF the server response is STICKERS_RETRIEVE_SUCESS, we save the sticker list that the user has of that team in stickersOfActiveTeam and call printPage
					countryID = $(actualMenu).attr("data-country");
					stickersDoubled = json.users[0].stickers;
					stickersOfActiveTeam = remove_doubled(json.users[0].stickers);
					printPage(countryID, json.users[0].stickers);
					//Update the number of stickers in each call.
					$("#sticker-count").html(json.users[0].sticker_count);
					//Remove the loading gif
					$("#loading").css("display","none");
					//If we are in the MENU or in the content, we show the complete view of the team.
					if(actualScope==ScopeValues.MENU || actualScope==ScopeValues.CONTENT){ $("#complete-view").css("display","block"); }
					//Enable the navigation.
					canNavigate = true;

					//Show player is true only if we want to show directly a player when we open the page, so we directly change the scope to the content and call showPlayer.
					if(show_player_ok==true){
						show_player_ok = false;
						change_scope(ScopeValues.CONTENT);
						showPlayer(id);
					}

				}else if(json.response == "NO_STICKERS_FOUND"){
					//If no sticker is found, call print_blank_page and hide the loading image.
					countryID = $(actualMenu).attr("data-country");
					$("#sticker-count").html(json.users[0].sticker_count);
					printBlankPage(countryID);
					$("#loading").css("display","none");
					//If we are in the MENU or in the content, we show the complete view of the team.
					if(actualScope==ScopeValues.MENU || actualScope==ScopeValues.CONTENT){ $("#complete-view").css("display","block"); }
					//Enable the navigation.
					canNavigate = true;
				}else{
					console.log("error");
				}

			}
		});
		
		//If the ajax function failed to connect to the server, then it loads the content from a stub JSON file. This has been done for testing. At production should show an error message or try the calling again.
		userStickersAjaxRequest.fail(function(){

			console.log("Error from server. Loading fake JSON for user stickers.");

			userStickersAjaxRequest = $.ajax({
				type : "GET",
				url: fakeUrl,
				contentType:"application/json",
				dataType:"json",
				success: function(json){
					
					console.log(json.response);
					
					if(json.response == "STICKERS_RETRIEVE_SUCESS"){
						countryID = $(actualMenu).attr("data-country");
						stickersOfActiveTeam = remove_doubled(json.users[0].stickers);
						$("#sticker-count").html(json.users[0].sticker_count);
						printPage(countryID, json.users[0].stickers);
						$("#loading").css("display","none");
						if(actualScope==ScopeValues.MENU || actualScope==ScopeValues.CONTENT){ $("#complete-view").css("display","block"); }
						canNavigate = true;
					}else if(json.response == "NO_STICKERS"){
						countryID = $(actualMenu).attr("data-country");
						$("#sticker-count").html(json.users[0].sticker_count);
						printBlankPage(countryID);
						$("#loading").css("display","none");
						if(actualScope==ScopeValues.MENU || actualScope==ScopeValues.CONTENT){ $("#complete-view").css("display","block"); }
						canNavigate = true;

						if(show_player_ok==true){
							show_player_ok = false;
							change_scope(ScopeValues.CONTENT);
							showPlayer(id);
						}

					}else{
						console.log("error");
					}

				}
			});
		});
		
	}
}

//Function that prints all the blank stickers of a team with their name.
function printBlankPage(id){
	
	//Get the index of that team in the globalTeams array.
	accessIndex = getTeamIndex(id);
	
	//For each player in the team.
	for(var i = 0;i<11;i++){
		//Create the ID dinamically
		imgID = "#sticker-img-" + (i+1);
		//Set the url of the image of the sticker with it color.
		if(globalTeams[accessIndex].color!=undefined){
			url = "./img/stickers/stickers/blankStickerNoName_"+ globalTeams[accessIndex].color +".png";
		}else{
			url = "./img/stickers/stickers/blankStickerNoName_orange.png";
		}
		//Set the image with the generated ID.
		$(imgID).attr("src",url);
		//Create the nameID dinamically.
		nameID = "#sticker-name-" + (i+1);
		//Set the name of the sticker and show it.
		$(nameID).html(globalTeams[accessIndex].players[i].name);
		$(nameID).css("display","block");
		
		//Calculate which index of the nav array are the ones to that sticker that is filling at that moment.
		index1NavArray = i%6;
		index2NavArray = Math.floor(i/6);

		//Update the URL atribute of the navigation array, with the url to trade the sticker.
		navigationArray[index1NavArray][index2NavArray]['url'] = "./trade.html?stickerid=" + globalTeams[accessIndex].players[i].id + "&stickername=" + encodeURIComponent(globalTeams[accessIndex].players[i].name);

	}
	//Since the last row has one sticker less, set the last one as the previous one.
	navigationArray[5][1]['url'] = navigationArray[4][1]['url'];
}

//Function that prints all the blank stickers of a team with their name.
function printPage(id, userStickers){
	
	//Get the index of that team in the globalTeams array.
	accessIndex = getTeamIndex(id);
	
	//For each player in the team.
	for(var i = 0;i<11;i++){
		
		//Create the ID dinamically
		imgID = "#sticker-img-" + (i+1);
		//Check if the user has that sticker by calculating its index in the userStickers variable
		indexOfUser = userStickerNumberIndex(globalTeams[accessIndex].players[i].sticker.number, userStickers);
		//Create the nameID dinamically.
		nameID = "#sticker-name-" + (i+1);
		
		//Check the number of stickers after every call, so we can make a sound if it changes (that means a sticker has been added)
		if(numberOfSctickersOfActualTeam != -1 && numberOfSctickersOfActualTeam != userStickers.length){
			numberOfSctickersOfActualTeam = userStickers.length;
			playAudio();
		}else if(numberOfSctickersOfActualTeam == -1){
			numberOfSctickersOfActualTeam = userStickers.length;
		}

		//Calculate which index of the nav array are the ones to that sticker that is filling at that moment.
		index1NavArray = i%6;
		index2NavArray = Math.floor(i/6);

		if(indexOfUser==-1){
			//If the index is -1, the user does not have that sticker, so load a blank sticker.
			//Set the url of the image of the sticker with it color.
			if(globalTeams[accessIndex].color!=undefined){
				url = "./img/stickers/stickers/blankStickerNoName_"+ globalTeams[accessIndex].color +".png";
			}else{
				url = "./img/stickers/stickers/blankStickerNoName_orange.png";
			}
			//Set the image with the generated ID.
			$(imgID).attr("src",url);
			//Set the name of the sticker and show it.
			$(nameID).html(globalTeams[accessIndex].players[i].name);
			$(nameID).css("display","block");
			//Update the URL atribute of the navigation array, with the url to trade the sticker.
			navigationArray[index1NavArray][index2NavArray]['url'] = "./trade.html?stickerid=" + globalTeams[accessIndex].players[i].id + "&stickername=" + encodeURIComponent(globalTeams[accessIndex].players[i].name);	
		}else{
			//If is not -1 then the user has that sticker.
			//Set the image with the generated ID.
			$(imgID).attr("src",userStickers[indexOfUser].image);
			//Not show the name (is already in the sticker)
			$(nameID).css("display","none");
			//Update the navigation array, with the function showPlayer and the ID of the player should be show when pressing OK.
			temp = ["showPlayer" , userStickers[indexOfUser].id];
			navigationArray[index1NavArray][index2NavArray]['url'] = temp;
		}
		
	}
		
}

//Returns the index to access the team with the id passed as attribute.
function getTeamIndex(id){
	
	for(var i = 0;i<globalTeams.length;i++){
		if(globalTeams[i].id == id){
			return i;
		}
	}
}

//Returns the index to access the sticker with the number passed as attribute in the stickers array passed as an attribute.
function userStickerNumberIndex(number, stickers){
	
	for(var i=0;i<stickers.length;i++){
		if(stickers[i].number == number)
			return i;
	}
	
	return -1;
}

//Function that prepares the page to show a player directly.
function prepareView(playerid){

	if(playerid!=undefined){
		//The ID of the player to show
		id = parseInt(playerid);

		//AJAX call to getStickerByID to get the sticker information.
		var getCountry = $.ajax({
			type : "GET",
			url: "http://145.92.6.118/medialab-api/player/getByStickerId/"+id+"/",
			contentType:"application/json",
			dataType:"json",
			success:function(json){
				
				if(json.response == "SUCCESS"){
					//If the server response is SUCCESS, get the id of the country that player belongs to.
					countryid = json.players[0].team.id;

					//Search for that country inside the list of countries using jQuery.
					string = 'li[data-country="' + countryid + '"]';
					activeelement = $("#flag-list").find(string).attr("id");

					//Get the position of the actual country and the one you are looking for to change, and calculate how much is needed to move the country list to select the correct position.
					var i = parseInt(activeelement.replace(/[^\d]+/gi, ''));
					var j = parseInt(actualMenu.replace(/[^\d]+/gi, ''));
					numberOfMoves = j-i;
					pixelsToMove = 106*numberOfMoves;
					cssPropierty = "";
	
					cssPropierty = "+="+pixelsToMove+"px";

					//Move the list if the cssProperty is enabled.
					if(cssPropierty!=""){
						$("#flag-list").css("margin-top",cssPropierty);
					}

					//Remove the glow from the actual element
					$(element).removeClass("flag-glow");
					//Set the new active element and add the glow to it.
					element = "#" + activeelement;
					actualMenu = element;
					$(element).addClass("flag-glow");

					//if there is any request of ajax, abort it.
					if(userStickersAjaxRequest!=undefined){
						userStickersAjaxRequest.abort();
					}
					
					//Set show_player_ok on true, so getStickersFromUser finish the prepareView, and call getStickersFromUser.
					show_player_ok = true;
					getStickersFromUser();

				}else{
					//If something goes woring, don't do anything and select the normal element.
					$("#element-3").addClass("glow");
				}	

			}
		});
	
		//If the ajax function failed to connect to the server, it does not do anything and select the normal element. Could be improve.
		getCountry.fail(function(){
			$("#element-3").addClass("glow");
		});

	}

}

//Function called when the user clicks on a sticker they have or when the user goes directly to a player when opening the album (because is redirected from other page)
function showPlayer(id){

	indexPlayer = -1;
	//Get the index of the player in stickersOfActiveTeam
	for(var i=0;i<stickersOfActiveTeam.length;i++){
		if(stickersOfActiveTeam[i].id == id)
			indexPlayer = i;
	}	

	if(indexPlayer!=-1){
		//If the user has that sticker, change the scope to PLAYER.
		actualScope = ScopeValues.PLAYER;
		//Hide the stickers-view
		$("#stickers-view").css("display","none");
		//Update the image and content of the sticker in the playerView
		$("#player-img-big").attr("src",stickersOfActiveTeam[indexPlayer].image);
		
		content = stickersOfActiveTeam[indexPlayer].content[0];

		$("#team-logo-container img").attr("src",content.clubUrl);
		$("#club-name").html(content.playerClub);
		$("#info-text").html(content.text);
		$("#position-text").html(content.playerPosition);

			//This sets the position of the marker.
		if(content.playerPosition=="MIDDENVELD"){
			$("#marker").css("left","406px");
			$("#marker").css("top","113px");
		}else if(content.playerPosition=="VERDEDIGER"){
			//defense top:146px;
			$("#marker").css("left","406px");
			$("#marker").css("top","146px");
		}else if(content.playerPosition=="AANVALLER"){
			//attacer: top:92px;
			$("#marker").css("left","406px");
			$("#marker").css("top","92px");
		}else if(content.playerPosition=="DOELMAN"){
			//Goalkeeper: top: 178px; left: 406px;
			$("#marker").css("left","406px");
			$("#marker").css("top","178px");
		}

		//Show the info-view
		$("#info-view").css("display","block");

		//If the index player is 0, is the first, so hide the previous button.
		if(indexPlayer==0){
			$("#button-previous-player").hide();
		}else{
			//If the index player is not 0, show the previous button and save which is the previous player id.
			$("#button-previous-player").show();
			idPrevPlayer = stickersOfActiveTeam[indexPlayer-1].id;
		}
		if(stickersOfActiveTeam.length - 1 == indexPlayer){
			//If the index player is the last one hide the next button.
			$("#button-next-player").hide();	
		}else{
			//If the index player is not the last one, show the next button and save which is the next player id.
			$("#button-next-player").show();
			idNextPlayer = stickersOfActiveTeam[indexPlayer+1].id;
		}

		//Remove the glow from the next and previous button and focus the overview button.
		$("#button-next-player").removeClass("button-glow");
		$("#button-previous-player").removeClass("button-glow");
		$("#button-overview").addClass("button-glow");
	}else{
		return false;
	}
	
}

//Navigate in the player content
function navigateInPlayer(e){
	//If the user press up or down, the app don't do anything. Left and right change between buttons "go next player", "overview" and "go before player". Enter activates the button.
	switch(e.keyCode){

		case VK_UP:
			e.preventDefault();
			break;
		case VK_DOWN:
			e.preventDefault();
			break;
		case VK_RIGHT:
			navRightContentNav();
			e.preventDefault();
			break;
		case VK_LEFT:
			navLeftContentNav();
			e.preventDefault();
			break;
		case VK_ENTER:
			navigateBetweenPlayers();
			e.preventDefault();
		default:
			break;
		
	}
}

function navLeftContentNav(){

	if($("#button-overview").hasClass("button-glow") && $("#button-previous-player").is(":visible")){
		//Check if the overview is focused and if the previous player is visible, on that case remove the glow from overview and focus the previous player button.
		$("#button-overview").removeClass("button-glow");
		$("#button-previous-player").addClass("button-glow");
	}else if($("#button-next-player").hasClass("button-glow")){
		//Check if the next player is focused, on that case remove the glow from next player and focus the overview button.
		$("#button-next-player").removeClass("button-glow");
		$("#button-overview").addClass("button-glow");
	}
}

function navRightContentNav(){
	//Check if the overview is focused and if the next player is visible, on that case remove the glow from overview and focus the next player button.
	if($("#button-overview").hasClass("button-glow") && $("#button-next-player").is(":visible")){
		$("#button-overview").removeClass("button-glow");
		$("#button-next-player").addClass("button-glow");
	}else if($("#button-previous-player").hasClass("button-glow")){
		//Check if the previous player is focused, on that case remove the glow from previous player and focus the overview button.
		$("#button-previous-player").removeClass("button-glow");
		$("#button-overview").addClass("button-glow");
	}
}

//Function call when OK is pressed in the player view.
function navigateBetweenPlayers(){
	//If the overview is glow
	if($("#button-overview").hasClass("button-glow")){
		//Hide the info-view and show the stickers-view, change the scope.
		$("#info-view").css("display","none");
		$("#stickers-view").css("display","block");
		actualScope = ScopeValues.CONTENT;
	}else if($("#button-previous-player").hasClass("button-glow")){
		//If the previousplayer button is focused, call showPlayer with the id of the previous player
		showPlayer(idPrevPlayer);
	}else if($("#button-next-player").hasClass("button-glow")){
		//If the nextPlayer button is focused, call showPlayer with the id of the next player
		showPlayer(idNextPlayer);
	}
}

//Function that play the audio of sticker obtainer
function playAudio(){
	audio.play(1);
}

//Function that initilize the url of the audio for stickers obtained.
function initializeAudio(){
	audio.data = "./videos/stickerObtained.mp3";
}

//Set a timer to call getStickersFromUser periodically.
var tid = setInterval(getStickersFromUser,5000);

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