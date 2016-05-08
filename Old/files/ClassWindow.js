class Window {
  constructor(name, height, width, icon, bgColour, fgColour) {
    var windowHeight = height;
    var windowWidth = width;
	var windowName = name;
	var windowIcon = icon;
	var bgColour = bgColour;
	var fgColour = fgColour;
	this.html = ' \
		<div id="'+ windowName + '" class="window" data-role="draggable" style="width: ' + windowWidth + 'px; position: relative; z-index: 1; top: 0%; left: 0%"> \
			<div class="window-caption">\
				<span class="window-caption-icon">\
					<span class="' + windowIcon + '"></span>\
				</span>\
				<span class="window-caption-title" style="float: left;">' + windowName + '</span>\
				<span class="btn-min" style="float:right;"></span>\
				<span class="btn-max" style="float:right;" ></span>\
				<span class="btn-close" style="float:right;" onclick="this.parentNode.parentNode.style.visibility=&quot;hidden&quot;"></span>\
			</div>\
			<div class="window-content bg-' + bgColour + ' fg-' + fgColour + '" style="height: ' + windowHeight + 'px;">\
				<p style="float: left;"></p>\
			</div>\
		</div>';
		
	this.vis = function()
	{
	 document.getElementById(windowName).style.visibility='visible';
	}
  }
  
  windowHtml()
  {
	  return this.html;
  }
  
  
}