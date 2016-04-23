//Variables
var isPlayerConnected = false;
var startMenuFlag = false;

//Open program flags
var isNotepadOpen = false;
var isMapOpen = false;
var isScriptsOpen = false;
var isHelpOpen = false;
var isTerminalOpen = false;
var isBrowserOpen = false;
var isTrashOpen = false;
var isSDOpen = false;

//Functions
function stopTime()
{
	//This function should send a message to the server to stop the time.
	console.log("Time stopped");
}

function startTime(speed)
{
	console.log("Game speed changed to: " + speed);
	if(speed == 1)
	{
		//This result should send a message to the server to advance time at normal speed
	}
	else if(speed == 2)
	{
		//This result should send a message to the server to advance time at x2 speed (or more)
	}
	else if(speed == 3)
	{
		//This function should send a message to the server to advance time at x3 speed (or more)
	}
	else
	{
		console.log("Failed. You tried to change the game speed with the number: " + speed);
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
	startMenuFlag = !startMenuFlag;
	if(startMenuFlag)
	{
		$(".startMenu").css('opacity', 0);
		$(".startMenu").css('visibility', 'hidden');
	}
	else
	{
		$(".startMenu").css('opacity', 100);
		$(".startMenu").css('visibility', 'visible');
	}
}

function togglePlayerConnection()
{
	$("#currentconnection").toggle();
}

//Programs
function startNotepad()
{
	if(isNotepadOpen == false)
	{
		console.log("Starting Notepad");
		isNotepadOpen = true;
	}
	else
	{
		console.log("Notepad is already open");
	}
}

function startMap()
{
	if(isMapOpen == false)
	{
		console.log("Starting Map");
		isMapOpen = true;
	}
	else
	{
		console.log("Map is already open");
	}
}

function openScriptsFolder()
{
	if(isScriptsOpen == false)
	{
		console.log("Opening the Scripts folder");
		isScriptsOpen = true;
	}
	else
	{
		console.log("Scripts folder is already open");
	}
}

function startHelp()
{
	if(isHelpOpen == false)
	{
		console.log("Starting Help");
		isHelpOpen = true;
	}
	else
	{
		console.log("Help is already open");
	}
}

function startTerminal()
{
	if(isTerminalOpen == false)
	{
		console.log("Starting Terminal");
		isTerminalOpen = true;
	}
	else
	{
		console.log("Terminal is already open");
	}
}

function startBrowser()
{
	if(isBrowserOpen == false)
	{
		console.log("Starting Browser");
		isBrowserOpen = true;
	}
	else
	{
		console.log("Browser is already open");
	}
}

function openTrash()
{
	if(isTrashOpen == false)
	{
		console.log("Opening the Trash");
		isTrashOpen = true;
	}
	else
	{
		console.log("Trash is already open");
	}
}

function startSelfDestruct()
{
	/*This function should simply ask for confirmation and then play out an animation of it just quickly deleting
	every file in the computer and closing out the game. What is here is simply temporary.*/
	if(isSDOpen == false)
	{
		console.log("Initiating Self Destruct sequence...");
		isSDOpen = true;
	}
	else
	{
		console.log("Self Destruct sequence is already initiated.");
	}
}