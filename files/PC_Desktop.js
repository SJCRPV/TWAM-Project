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

//Start Menu
function openMenuFolders()
{
	console.log("Opening file explorer");
}

function openMenuSettings()
{
	console.log("Opening the settings menu");
}

function openMenuPrograms()
{
	console.log("Opening the programs list");
}
//Start Menu END

//Programs
function startNotepad()
{
	if(isNotepadOpen == false)
	{
		console.log("Starting Notepad");
		isNotepadOpen = true;
		var notepadWindow = new Window("Notepad", 300, 250, "mif-file-text");
		var notepad = $("#notepad").html('<span class="title">Notes</span> ' + notepadWindow.windowHtml());
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
		var mapWindow = new Window("Map", 400, 700, "mif-earth2");
		var map = $("#map").html('<span class="title">Map</span> ' + mapWindow.windowHtml());
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
		var scriptsFolderWindow = new Window("Scripts", 400, 500, "mif-folder-open");
		var scripts = $("#scripts").html('<span class="title">Scripts</span> ' + scriptsFolderWindow.windowHtml());
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
		var helpWindow = new Window("Help", 400, 500, "mif-question");
		var help = $("#help").html('<span class="title">Help</span> ' + helpWindow.windowHtml());
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
		var terminalWindow = new Window("Terminal", 200, 500, "mif-embed2", "black", "white");
		var terminal = $("#cmd").html('<span class="title">Terminal</span> ' + terminalWindow.windowHtml());
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
		var browserWindow = new Window("Browser", 400, 600, "mif-squirrel");
		var browser = $("#browser").html('<span class="title">Browser</span> ' + browserWindow.windowHtml());
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
		var trashWindow = new Window("Trash", 200, 500, "mif-bin");
		var trash = $("#trash").html('<span class="title">Trash</span> ' + trashWindow.windowHtml());
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
		var sdWindow = new Window("sd.exe", 200, 500, "mif-fire", "black", "white");
		var sd = $("#selfDestruct").html('<span class="title">sd.exe</span> ' + sdWindow.windowHtml());
	}
	else
	{
		console.log("Self Destruct sequence is already initiated.");
	}
}
//Programs END

//System Icons
function openVolume()
{
	console.log("Opening the volume slider");
}

function openConnection()
{
	console.log("Opening the connection display screen");
}

function openBattery()
{
	console.log("Opening the battery display screen");
}