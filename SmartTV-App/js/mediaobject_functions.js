//Javascript file
//Add the functionality to the video player.
//How to use:
	//Discomment the html part of mediaobject and loading layer
	//Create an bool var (watching_video) that will contain if the user is in the video or not.
	//Add to the onload body this javacript code: video.onPlayStateChange=checkPlayState;
	//Navigate normal when the var is false, and call navigate_in_video(e) when is true.
	//Create the function that will set the video and will start it. Exmaple:
		/*function goToVideo(){
				
			video.data = "http://145.92.6.118/smarttv/videos/try1.mp4";
			
			watching_video = true;
			video.play(1);
			$("#mediaobject").show();
			$("#loadingImage").show();

		}*/

function checkPlayState()
{

	console.log(video.playstate);

	switch (video.playState)
	{
	  	case 5: // finished
			endOfFile();
				break;
		case 0: // stopped
			break;
		case 6: // error
			break;
		case 1: // playing
			beginPlay();
			break;
		case 2: // paused
			//pause();
			break;
		case 3: // connecting
			break;
		case 4: // buffering
			buffering();
			break;
		default:
		  // do nothing
			break;
	}
}

function buffering(){
	if($("#loadingImage").css("display") != "block"){
		$("#loadingImage").css("display","block");
	}
}

function beginPlay(){

	console.log("playing");

	if($("#loadingImage").css("display") != "none"){
		$("#loadingImage").css("display","none");
	}
	
	if($("#mediaobject").css("display") != "block"){
		$("#mediaobject").css("display","block");
	}
}

function endOfFile(){
	if($("#mediaobject").css("display") != "none"){
		$("#mediaobject").css("display","none");
	}	

	if($("#loadingImage").css("display") != "none"){
		$("#loadingImage").css("display","none");
	}

	watching_video = false;
}

function navigate_in_video(e){

	switch(e.keyCode){
		case VK_UP:
			if(in_menu_video){
				$("#resume-video a").eq(0).addClass("button-glow");
				$("#resume-video a").eq(1).removeClass("button-glow");
			}
			e.preventDefault();
			break;
		case VK_DOWN:
			if(in_menu_video){
				$("#resume-video a").eq(1).addClass("button-glow");
				$("#resume-video a").eq(0).removeClass("button-glow");
			}
			e.preventDefault();
			break;
		case VK_RIGHT:
			e.preventDefault();
			break;
		case VK_LEFT:
			e.preventDefault();
			break;
		case VK_ENTER:
			if(in_menu_video){
				if($("#resume-video a").eq(0).hasClass("button-glow")){
					$("#resume-video").modal("hide");
					in_menu_video = false;
					video.play(1);
				}else{
					video.stop();
					$("#resume-video").modal("hide");
					endOfFile();
					$("#resume-video a").eq(1).removeClass("button-glow");
					in_menu_video = false;
				}
			}else{
				video.play(0);
				$("#resume-video").modal("show");
				$("#resume-video a").eq(0).addClass("button-glow");
				in_menu_video = true;
			}
			e.preventDefault();
		default:
			break;	
	}
}