//Javascript for all the files


//This function access to the back-end and retrieve the information about the user.
//The my_id variable is the id of the user

function ini_count_stickers(){

	my_id = 2;

	//Made the ajax call to the server to the getUser entrypoint.
	call = $.ajax({
		url:"http://145.92.6.118/medialab-api/user/getUser/"+my_id+"/",
		type : "GET",
		contentType:"application/json",
		dataType:"json",
		success: function(json){

			if(json.response=="SUCCESS"){

				//If the server response with success put the sticker count and name of the user inside the html.
				$("#sticker-count").html(json.users[0].sticker_count);
				$("#user-name").html(json.users[0].name);
				
				//If ini_last_video is a defined function call it (it is defined on the index-navigation.js, will control the last video section).
				if(typeof(ini_last_video)=="function"){
					ini_last_video();
				}

				//If market_news is a defined function  call it (it is defined on the market-navigation.js and control the amount of stickers that is show in the news section).
				if(typeof(market_news)=="function"){
					market_news(json.users[0].sticker_count);
				}
			}else{
				//If something goes wrong with the server, redirect to the homepage.
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
				$("#sticker-count").html(json.users[0].stickers.length);
				if(typeof(ini_last_video)=="function"){
					ini_last_video();
				}
				if(typeof(market_news)=="function"){
					market_news(json.users[0].stickers.length);
				}
			}else{
				window.location.href="./index.html";
			}

		}

	});

	});

}