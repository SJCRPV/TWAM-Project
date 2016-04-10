function constructor()
{
	var startMenuFlag = false;
}


function stopTime()
{
	//This function should send a message to the server to stop the time.
	console.log("Time stopped");
}

function startTime($speed)
{
	console.log("Game speed changed to: " + $speed);
	if($speed == 1)
	{
		//This result should send a message to the server to advance time at normal speed
	}
	else if($speed == 2)
	{
		//This result should send a message to the server to advance time at x2 speed (or more)
	}
	else if($speed == 3)
	{
		//This function should send a message to the server to advance time at x3 speed (or more)
	}
	else
	{
		console.log("Failed. You tried to change the game speed with the number: " + $speed);
	}
}

function isPlayerConnected()
{
	//This function requests the $isConnected flag from the server and returns its value.
	console.log("Returning the state of $isConnected");
	//return $isConnected;
}

function toggleStartMenu()
{
	$startMenuFlag != $startMenuFlag;
	console.log("The statement 'Is the start menu open?' is " + $startMenuFlag);
}

if(isPlayerConnected())
{
	$("#currentconnection").toggle()
}