//Index navigation
//The navigation variables. Actual element selected and an array with all the elements.
var actual_element = "#sticker-album-button";
var navigation_array = new Array(4);

//Variables to control the last video playing. Watching video will be true if the video is on, and in_menu_video will be on if the vide is pause and the popup is shown.
//Video_url will have the video url that needs to be show as the last video.
var watching_video = false;
var in_menu_video = false;
var video_url;

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

//Function that will be call everytime a key is pressed.
function navigate(e){

	if(watching_video){
		//If you are inside the video, we want to navigate inside its options.
		navigate_in_video(e);
	}else{
		//If you are not inside the video, navigate in the homepage.
		navigate_home_menu(e);
	}
}

//Function that fill the navigation array. In each button of the homepage, the user could press UP, DOWN, LEFT, RIGHT and OK. We defined an action for each
//of this situations. Since the buttons are in a 2x2 table, I only need to stablish two of the actions and the destiny if they press OK.
function ini_nav_array(){

	//Fill array with the info. The index of the array is the ID of the button in the HTML, and the content of the array is to what element is going the user if they press
	//an arrow key, or the action if it press OK on that button (url in this case).
	navigation_array["#sticker-album-button"] = new Array(5);
	navigation_array["#sticker-album-button"]["down"] = "#sticker-market-button";
	navigation_array["#sticker-album-button"]["right"] = "#last-video-button";
	navigation_array["#sticker-album-button"]["url"] = "./album.html";

	navigation_array["#sticker-market-button"] = new Array(5);
	navigation_array["#sticker-market-button"]["up"] = "#sticker-album-button";
	navigation_array["#sticker-market-button"]["right"] = "#videos-button";
	navigation_array["#sticker-market-button"]["url"] = "./market.html";

	navigation_array["#last-video-button"] = new Array(5);
	navigation_array["#last-video-button"]["down"] = "#videos-button";
	navigation_array["#last-video-button"]["left"] = "#sticker-album-button";
	navigation_array["#last-video-button"]["url"] = "goToVideo";

	navigation_array["#videos-button"] = new Array(5);
	navigation_array["#videos-button"]["up"] = "#last-video-button";
	navigation_array["#videos-button"]["left"] = "#sticker-market-button";
	navigation_array["#videos-button"]["url"] = "./videos.html";

	//Also, since we have a video, we need to say which function will take care of its status changes. That function is defined on the mediaobject_functions.js file, because
	//is the same for all the pages.
	video.onPlayStateChange=checkPlayState;

}

//Navigate in the homemenu.
function navigate_home_menu(e){

	//Depending on which key has you pressed, you will change focus or not.
	switch (e.keyCode){
		//For the arrow keys, just check the next-element is defined and change to it. If it is not defined, then is because there is no button, so don't do anything.
		case VK_UP:
			next_element = navigation_array[actual_element]["up"];
			if(next_element!=undefined){
				change_focus(next_element);
			}
			e.preventDefault();
			break;
		case VK_DOWN:
			next_element = navigation_array[actual_element]["down"];
			if(next_element!=undefined){
				change_focus(next_element);
			}
			e.preventDefault();
			break;
		case VK_LEFT:
			next_element = navigation_array[actual_element]["left"];
			if(next_element!=undefined){
				change_focus(next_element);
			}
			e.preventDefault();
			break;
		case VK_RIGHT:
			next_element = navigation_array[actual_element]["right"];
			if(next_element!=undefined){
				change_focus(next_element);
			}
			e.preventDefault();
			break;
		case VK_ENTER:
			url = navigation_array[actual_element]["url"];

			if(url.indexOf('.')!=0){
				//If the url does not begin with a dot, is a function that needs to be call. So call it.
				window[url]();
			}else{
				//If it started with dot, is a link so redirect the user to it.
				window.location.href = url;
			}	
		default:
			break;
	}
}

//Function that change the focus of the buttons on the homepage. 
function change_focus(new_element){

	//Remove the glow class ftom the actual element.
	$(actual_element).removeClass("glow");
	//Change the actual element to the new one.
	actual_element = new_element;
	//Add the glow class to the new actual element.
	$(actual_element).addClass("glow");

}

//Function that initialize what video to show. For the prototype, is it hardcode checking the amount of stickers the user has.
//Since it uses the number of stickers, this function is called from the main.js file after the app has retrieve that information.
function ini_last_video(){

	//Check the number of stickers
	if(parseInt($("#sticker-count").html())>=10){
		//If the user has more than 10 stickers, load second video in the url, image and title.
		video_url = "http://145.92.6.118/smarttv/videos/try1.mp4";
		$("#video-miniature").attr("src","img/video-miniatures/our-video-miniature.png");
		$("#video-desc").html("AFLEVERING 1 - DE KNIE AKKA");
	}else{
		//If the user has less than 10 stickers, load first video in the url, image and title.
		video_url = "http://145.92.6.118/smarttv/videos/animation.mp4";
		$("#video-miniature").attr("src","img/video-miniatures/animation-miniature.png");
		$("#video-desc").html("Animation");
	}
}

//Everytime the user press a key, call the function navigate.
document.addEventListener("keydown", navigate, true);

//Function that started the video. It is needed by the mediaobject_functions.js.
function goToVideo(){
		
	//Stablish the url of the video to load.
	video.data = video_url;
	
	//Put true the variable of watching video, started playing and show the vide object and the loading gif. The mediaobject_function.js will take care of the other thigs (take out the loading gif, pausing it, ...)
	watching_video = true;
	video.play(1);
	$("#mediaobject").show();
	$("#loadingImage").show();

}

/*Get the user last requests to show them in the notifications*/
function get_user_requests(){

	my_user_id = 2;

	trueUrl = "http://145.92.6.118/medialab-api/trade/getLastTrades/"+my_user_id+"/0";
	fakeUrl = "./JSON_REQUESTS/tradesrequests.json";

	//Make the ajax call to the server entrypoint getLastTrades.
	call = $.ajax({
		type : "GET",
		url: trueUrl,
		contentType:"application/json",
		dataType:"json",
		success: function(json){

			if(json.response=="SUCCESS"){
				//If the server answer success, show the trades by callint the function draw_notifications
				draw_notifications(json.trades);
			}else{
				//IF there is an error on the answer, reload the page.
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
					draw_notifications(json.trades);
				}else if(json.response=="NO_TRADES_FOUND"){
					
				}else{
					window.location.href="./index.html";
				}

			}
		});

	});

}

//This function will draw the notifications at the screen.
function draw_notifications(trades){

	//ID of the user
	my_user_id = 2;

	//If the user has more than 5 trades, we started filling frmo the first element on the html page.
	if(trades.length>=5){
		element5 = "#trading-sticker-1";

		//Depending of the status, it will have a different image status (tick, clock or error) and a different class to change the color bacground.
		if(trades[4].status=="WAITING"){
			$(element5).children().eq(1).attr("src","img/UI-elements/home-ui/trade-timer-1.png");
			$(element5).addClass("sticker-trade-wait");	
		}

		if(trades[4].status=="ACCEPTED"){
			$(element5).children().eq(1).attr("src","img/UI-elements/home-ui/trade-ok-ui.png");
			$(element5).addClass("sticker-trade-ok");	
		}

		if(trades[4].status=="REFUSED" || trades[4].status=="TIMEOUT"){
			$(element5).children().eq(1).attr("src","img/UI-elements/home-ui/trade-fail-ui.png");
			$(element5).addClass("sticker-trade-fail");	
		}


		//Check if the user has made the request, or another user has made it, so we take the ID of the sticker you are getting.
		if(trades[4].initiator.id==my_user_id){
			//If the user is the initiator, he is getting the sticker of the respondent.
			urlpart = trades[4].respondentSticker.id;
		}else{
			//If the user is not the initiator, he is getting the sticker of the initiator.
			urlpart = trades[4].initiatorSticker.id;
		}

		//Call to the server to get the information about that stickerID (could be done better if the getLastTrades also gives this information, so we don't have to do this call)
		call = $.ajax({
			type : "GET",
			url: "http://145.92.6.118/medialab-api/player/getByStickerId/"+urlpart+"/",
			contentType:"application/json",
			dataType:"json",
			success: function(json){

				if(json.response=="SUCCESS"){
					//If the retrieve is success, put the name of the player on the space in the html.
					$(element5).children().eq(0).html(json.players[0].name);
				}else{
					//If there is something wrong, don't show that notification.
					$(element5).css("display","none");		
				}

			}
		});
	}else{
		//If there are less than 5 trades, not show the first row of notifications.
		element5 = "#trading-sticker-1";
		$(element5).css("display","none");		
	}

	//The next code is exactly the same for the previous one, but for the other notifications rows of the html. It could be probably refactor all of them to just one loop.
	if(trades.length>=4){
		element4 = "#trading-sticker-2";
		if(trades[3].status=="WAITING"){
			$(element4).children().eq(1).attr("src","img/UI-elements/home-ui/trade-timer-1.png");
			$(element4).addClass("sticker-trade-wait");	
		}

		if(trades[3].status=="ACCEPTED"){
			$(element4).children().eq(1).attr("src","img/UI-elements/home-ui/trade-ok-ui.png");
			$(element4).addClass("sticker-trade-ok");	
		}

		if(trades[3].status=="REFUSED" || trades[3].status=="TIMEOUT"){
			$(element4).children().eq(1).attr("src","img/UI-elements/home-ui/trade-fail-ui.png");
			$(element4).addClass("sticker-trade-fail");	
		}

		if(trades[3].initiator.id==my_user_id){
			urlpart = trades[3].respondentSticker.id;
		}else{
			urlpart = trades[3].initiatorSticker.id;
		}

		call = $.ajax({
			type : "GET",
			url: "http://145.92.6.118/medialab-api/player/getByStickerId/"+urlpart+"/",
			contentType:"application/json",
			dataType:"json",
			success: function(json){

				if(json.response=="SUCCESS"){
					$(element4).children().eq(0).html(json.players[0].name);
				}else{
					$(element4).css("display","none");		
				}

			}
		});
	}else{
		element4 = "#trading-sticker-2";
		$(element4).css("display","none");		
	}

	if(trades.length>=3){
		element3 = "#trading-sticker-3";

		if(trades[2].status=="WAITING"){
			$(element3).children().eq(1).attr("src","img/UI-elements/home-ui/trade-timer-1.png");
			$(element3).addClass("sticker-trade-wait");	
		}

		if(trades[2].status=="ACCEPTED"){
			$(element3).children().eq(1).attr("src","img/UI-elements/home-ui/trade-ok-ui.png");
			$(element3).addClass("sticker-trade-ok");	
		}

		if(trades[2].status=="REFUSED" || trades[2].status=="TIMEOUT"){
			$(element3).children().eq(1).attr("src","img/UI-elements/home-ui/trade-fail-ui.png");
			$(element3).addClass("sticker-trade-fail");	
		}

		if(trades[2].initiator.id==my_user_id){
			urlpart = trades[2].respondentSticker.id;
		}else{
			urlpart = trades[2].initiatorSticker.id;
		}

		call = $.ajax({
			type : "GET",
			url: "http://145.92.6.118/medialab-api/player/getByStickerId/"+urlpart+"/",
			contentType:"application/json",
			dataType:"json",
			success: function(json){

				if(json.response=="SUCCESS"){
					$(element3).children().eq(0).html(json.players[0].name);
				}else{
					$(element3).css("display","none");		
				}

			}
		});
	}else{
		element3 = "#trading-sticker-3";
		$(element3).css("display","none");		
	}

	if(trades.length>=2){
		element2 = "#trading-sticker-4";
		
		if(trades[1].status=="WAITING"){
			$(element2).children().eq(1).attr("src","img/UI-elements/home-ui/trade-timer-1.png");
			$(element2).addClass("sticker-trade-wait");	
		}

		if(trades[1].status=="ACCEPTED"){
			$(element2).children().eq(1).attr("src","img/UI-elements/home-ui/trade-ok-ui.png");
			$(element2).addClass("sticker-trade-ok");	
		}

		if(trades[1].status=="REFUSED" || trades[1].status=="TIMEOUT"){
			$(element2).children().eq(1).attr("src","img/UI-elements/home-ui/trade-fail-ui.png");
			$(element2).addClass("sticker-trade-fail");	
		}

		if(trades[1].initiator.id==my_user_id){
			urlpart = trades[1].respondentSticker.id;
		}else{
			urlpart = trades[1].initiatorSticker.id;
		}
		call = $.ajax({
			type : "GET",
			url: "http://145.92.6.118/medialab-api/player/getByStickerId/"+urlpart+"/",
			contentType:"application/json",
			dataType:"json",
			success: function(json){

				if(json.response=="SUCCESS"){
					$(element2).children().eq(0).html(json.players[0].name);
				}else{
					$(element2).css("display","none");		
				}

			}
		});
	}else{
		element2 = "#trading-sticker-4";
		$(element2).css("display","none");		
	}

	if(trades.length>=1){
		element1 = "#trading-sticker-5";
		
		if(trades[0].status=="WAITING"){
			$(element1).children().eq(1).attr("src","img/UI-elements/home-ui/trade-timer-1.png");
			$(element1).addClass("sticker-trade-wait");	
		}

		if(trades[0].status=="ACCEPTED"){
			$(element1).children().eq(1).attr("src","img/UI-elements/home-ui/trade-ok-ui.png");
			$(element1).addClass("sticker-trade-ok");	
		}

		if(trades[0].status=="REFUSED" || trades[0].status=="TIMEOUT"){
			$(element1).children().eq(1).attr("src","img/UI-elements/home-ui/trade-fail-ui.png");
			$(element1).addClass("sticker-trade-fail");	
		}

		if(trades[0].initiator.id==my_user_id){
			urlpart = trades[0].respondentSticker.id;
		}else{
			urlpart = trades[0].initiatorSticker.id;
		}
		call = $.ajax({
			type : "GET",
			url: "http://145.92.6.118/medialab-api/player/getByStickerId/"+urlpart+"/",
			contentType:"application/json",
			dataType:"json",
			success: function(json){

				if(json.response=="SUCCESS"){
					$(element1).children().eq(0).html(json.players[0].name);
				}else{
					$(element1).css("display","none");		
				}

			}
		});
	}else{
		element1 = "#trading-sticker-5";
		$(element1).css("display","none");		
	}

}