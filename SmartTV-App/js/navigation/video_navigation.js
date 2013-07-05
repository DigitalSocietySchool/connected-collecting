//Javascript file
//Navigation page for the videos view

//Actual and previous elemented selected.
var element = "#video-1";
var prevElement = "#video-1";

//Max index to access to the navigation array.
var maxx;
var maxy;

//Initial index to access the navigation array.
var focusx = 0;
var focusy = 0;

//Variables requeired for the video show.
var list_videos;
var number_stickers;
var video_to_open;
var actualPage = 1;
var firstTime = true;
var watching_video = false;
var in_menu_video = false;

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

//Array for navigation
var nav_array_overview;

//Function that initialize the navigation array. In this case, is a Matrix of 4x4 which is simulated in , and each cell represents one button. For each button there are to elements:
	//ID of the elements of the html.
	//URL or action to do when ok is pressed at that element.
//Check the documentation to undrestand how this kind of navigations works.
function initialize_nav_array(){
	
	//Fill the navigation array.
	nav_array_overview = new Array(4);
	for(var i=0;i<nav_array_overview.length;i++){
		nav_array_overview[i] = new Array(4);
		nav_array_overview[i][0] = new Array(2);
		nav_array_overview[i][1] = new Array(2);
		nav_array_overview[i][2] = new Array(2);
		nav_array_overview[i][3] = new Array(2);
	}
	
	nav_array_overview[0][0]["id"] = "#video-1";
	nav_array_overview[1][0]["id"] = "#video-2";
	nav_array_overview[2][0]["id"] = "#video-3";
	nav_array_overview[3][0]["id"] = "#video-4";
	nav_array_overview[0][1]["id"] = "#video-5";
	nav_array_overview[1][1]["id"] = "#video-6";
	nav_array_overview[2][1]["id"] = "#video-7";
	nav_array_overview[3][1]["id"] = "#video-8";
	nav_array_overview[0][2]["id"] = "#video-9";
	nav_array_overview[1][2]["id"] = "#video-10";
	nav_array_overview[2][2]["id"] = "#video-11";
	nav_array_overview[3][2]["id"] = "#video-12";
	nav_array_overview[0][3]["id"] = undefined; //prebutton space
	nav_array_overview[1][3]["id"] = undefined; //prebutton space
	nav_array_overview[2][3]["id"] = "#next-button";
	nav_array_overview[3][3]["id"] = "#next-button";

	nav_array_overview[0][3]["url"] = "previous_page"; //prebutton space
	nav_array_overview[1][3]["url"] = "previous_page"; //prebutton space
	nav_array_overview[2][3]["url"] = "next_page";
	nav_array_overview[3][3]["url"] = "next_page";
	
	//Define the max index for the matrix.
	maxx = nav_array_overview.length - 1;
	maxy = nav_array_overview[0].length - 1;
}

//Function that will be call everytime a key is pressed.
function navigate(e){
	
	if(watching_video){
		//If you are inside the video, we want to navigate inside its options.
		navigate_in_video(e);
	}else{
		//If you are not inside the video, navigate in the overview.
		navigate_in_overview(e);
	}
}

//Navigate in the videos overview.
function navigate_in_overview(e){

	//Depending on which key has you pressed, you will change focus in one or other way.
	switch(e.keyCode){
		//For the arrow keys, call the functions of navigations to go up, down, left and right in this kind of navigation.
		case VK_UP:
			nav_overview_up();
			e.preventDefault();
			break;
		case VK_DOWN:
			nav_overview_down();
			e.preventDefault();
			break;
		case VK_RIGHT:
			nav_overview_right();
			e.preventDefault();
			break;
		case VK_LEFT:
			nav_overview_left();
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

//Function to navigate up in the overview. Check documentation for more information.
function nav_overview_up(){

	if(focusy-1>=0){
		for(var i=0;focusy-1-i>=0;i++){
			var ty = focusy-1-i;
			if(nav_array_overview[focusx][ty]["id"]!=undefined && nav_array_overview[focusx][ty]["id"]!=element){
				prevElement=element;
				element = nav_array_overview[focusx][ty]["id"];	
				focusy=ty;
				$(prevElement).parent().removeClass("video-glow");
				$(element).parent().addClass("video-glow");
				break;
			}
		}

	}
}

//Function to navigate down in the overview. Check documentation for more information.
function nav_overview_down(){
	if(focusy+1<=maxy){
		for(var i=0;focusy+1+i<=maxy;i++){
			var ty = focusy+1+i;
			if(nav_array_overview[focusx][ty]["id"]!=undefined && nav_array_overview[focusx][ty]["id"]!=element){
				prevElement=element;
				element = nav_array_overview[focusx][ty]["id"];	
				focusy=ty;
				$(prevElement).parent().removeClass("video-glow");
				$(element).parent().addClass("video-glow");
				break;
			}
		}
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
				$(prevElement).parent().removeClass("video-glow");
				$(element).parent().addClass("video-glow");
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
				$(prevElement).parent().removeClass("video-glow");
				$(element).parent().addClass("video-glow");
				break;
			}
		}
	}     	
}

//Function to load the videos from the server. It will made an AJAX call to the server. For the prototype, we don't do that. We use a stub JSON with the info of the two first videos.
function load_videos(){

	url = "./JSON_REQUESTS/videos.json";

	$.ajax({

		url:url,
		type : "GET",
		contentType:"application/json",
		dataType:"json",
		success: function(json){

			if(json.response=="SUCCESS"){
				//If the server answer correctly, save the list of videos and continue the load of videos by calling number_of_sitckers_for_unlock.
				list_videos = json.videos;
				number_of_stickers_for_unlock();
			}else{
				window.location.href="./index.html";
			}

		}

	});

}

//Function that will check in the server which is the number of stickers that the user has, and start rendering the page.
function number_of_stickers_for_unlock(){

	//ID of the user.
	my_id = 2;

	call = $.ajax({
		url:"http://145.92.6.118/medialab-api/user/getUser/"+my_id+"/",
		type : "GET",
		contentType:"application/json",
		dataType:"json",
		success: function(json){

			if(json.response=="SUCCESS"){
				//If the server answer correctly, save the number of stickers and print the actual page (that the first time will be 1)
				number_stickers = json.users[0].sticker_count;
				print_page(actualPage);
			}else{
				//If the server found a problem, redirect to homepage.
				window.location.href="./index.html";
			}

		}

	});

	//If the ajax function failed to connect to the server, then it loads the content from a stub JSON file. This has been done for testing. At production should show an error message or try the calling again.
	call.fail(function(){

		$.ajax({
		url:"./JSON_REQUESTS/userprofilestub.json",
		type : "GET",
		contentType:"application/json",
		dataType:"json",
		success: function(json){

			if(json.response=="SUCCESS"){
				number_stickers = json.users[0].stickers.length;
				print_page(actualPage);
			}else{
				window.location.href="./index.html";
			}

		}

	});

	});

}

//Function that renders a page of the overview of the videos. This function is hardcode for the prototype. Needs to change for production.
function print_page(page){
	
	//Render all the videos that correspond to the actual page.
	for(var i=0;i<12;i++){
		img = "#video-" + (i + 1);
		$(img).attr("src",list_videos[i+((page-1)*12)].miniature);
		$(img).parent().children().eq(1).html(list_videos[i+((page-1)*12)].title)
	}

	//If the user has more than 10 stickers and is in page 1, render the second video unlocked.
	if(number_stickers>=10 && page==1){
		$("#video-2").attr("src","img/video-miniatures/overview-miniature.png");
		$("#video-2").parent().children().eq(1).html("AFLEVERING 1 - DE KNIE AKKA");
		nav_array_overview[1][0]["url"]=["open_video",list_videos[1].url];
	}else{
		//If not, remove the action from that button.
		nav_array_overview[1][0]["url"]=undefined;
	}


	//Depending which page we are rendering, change navigation array because the "go next" and "go back" buttons are hidden in somepages.
	if(page==1){
		//If page 1, the first video has the option to see the animation video.
		nav_array_overview[0][0]["url"]=["open_video",list_videos[0].url];

		//Delete the previos videos button by declaring undefined that elements on the navigation array.
		nav_array_overview[0][3]["id"] = undefined; //prebutton space
		nav_array_overview[1][3]["id"] = undefined; //prebutton space
		//Add the next videos button by declaring their ID on the navigation array.
		nav_array_overview[2][3]["id"] = "#next-button";
		nav_array_overview[3][3]["id"] = "#next-button";

		//Make glow the correct circle that indicates in which page we are by adding and removing the circle-glow class.
		$("#circle1").addClass("circle-glow");
		$("#circle2").removeClass("circle-glow");
		$("#circle3").removeClass("circle-glow");

		//Make invisible the previous button and visible the next button.
		$("#pre-button").addClass("invi");
		$("#next-button").removeClass("invi");

		//If the page is loaded and is not the first time, is because I have come from the second page. In that case, the previous button is removed, so we need to focus another element of the html (in this case, the next-button).
		if(!firstTime){
			prevElement=element;
			element = "#next-button";	
			focusx=2;
			$(prevElement).parent().removeClass("video-glow");
			$(element).parent().addClass("video-glow");	
		}else{
			firstTime=false;
		}
		
	}else if(page==2){
		//If page 2, the first video is not enabled.
		nav_array_overview[0][0]["url"]=undefined;

		//Add the next videos and previous videos button by declaring their ID on the navigation array.
		nav_array_overview[0][3]["id"] = "#pre-button";
		nav_array_overview[1][3]["id"] = "#pre-button";
		nav_array_overview[2][3]["id"] = "#next-button";
		nav_array_overview[3][3]["id"] = "#next-button";

		//Make glow the correct circle that indicates in which page we are by adding and removing the circle-glow class.
		$("#circle1").removeClass("circle-glow");
		$("#circle2").addClass("circle-glow");
		$("#circle3").removeClass("circle-glow");

		//Make visible the previous button and the next button.
		$("#pre-button").removeClass("invi");
		$("#next-button").removeClass("invi");

	}else if(page==3){
		//If page 3, the first video is not enabled.
		nav_array_overview[0][0]["url"]=undefined;

		//Add the previous videos button by declaring their ID on the navigation array.
		nav_array_overview[0][3]["id"] = "#pre-button"; //prebutton space
		nav_array_overview[1][3]["id"] = "#pre-button"; //prebutton space
		//Delete the next videos button by declaring undefined that elements on the navigation array.
		nav_array_overview[2][3]["id"] = undefined;
		nav_array_overview[3][3]["id"] = undefined;

		//Make glow the correct circle that indicates in which page we are by adding and removing the circle-glow class.
		$("#circle1").removeClass("circle-glow");
		$("#circle2").removeClass("circle-glow");
		$("#circle3").addClass("circle-glow");

		//Make visible the previous button and the next button.
		$("#pre-button").removeClass("invi");
		$("#next-button").addClass("invi");

		//If we are in page 3 (last one of prototype) the next button is going to be hidden, so we need to focus another element (in this case, the previous button).
		prevElement=element;
		element = "#pre-button";	
		focusx=1;
		$(prevElement).parent().removeClass("video-glow");
		$(element).parent().addClass("video-glow");


	}

}

//Function to change page when the previous button videos is selected.
function previous_page(){
	//Substract 1 to the actual page.
	actualPage = actualPage - 1;
	//Check it is good.
	if(actualPage<1) actualPage = 1;
	//Call the number_of_stickers_for_unlock to reload the number of stickers that the user has and print the videos page.
	number_of_stickers_for_unlock();
}

function next_page(){
	//Add 1 to the actual page.
	actualPage = actualPage + 1;
	//Check it is good.
	if(actualPage>3) actualPage = 3;
	//Call the number_of_stickers_for_unlock to reload the number of stickers that the user has and print the videos page.
	number_of_stickers_for_unlock();
}

//Function to open the video, it saves the url of the video and call goToVideo();
function open_video(url){
	video_to_open = url;
	goToVideo();
}

//Function goToVideo will opeb the video object and will start playing it using the mediaobject_functions.js functions.
function goToVideo(){
				
			//Stablish the url of the video to load.
			video.data = video_to_open;
			
			//Put true the variable of watching video, started playing and show the vide object and the loading gif. The mediaobject_function.js will take care of the other thigs (take out the loading gif, pausing it, ...)
			watching_video = true;
			video.play(1);
			$("#mediaobject").show();
			//$("#loadingImage").show();
}

//Everytime the user press a key, call the function navigate.
document.addEventListener("keydown", navigate, true);