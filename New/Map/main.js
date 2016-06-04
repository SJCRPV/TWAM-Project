/*!
 * OS.js - JavaScript Cloud/Web Desktop Platform
 *
 * Copyright (c) 2011-2016, Anders Evenrud <andersevenrud@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * @author  Anders Evenrud <andersevenrud@gmail.com>
 * @licence Simplified BSD License (function(API, Utils, DialogWindow) {
 */
(function(Application, Window, Utils, API, VFS, GUI) {
  'use strict';

  /////////////////////////////////////////////////////////////////////////////
  // WINDOWS
  /////////////////////////////////////////////////////////////////////////////

  function ApplicationMapWindow(app, metadata, scheme) {
    Window.apply(this, ['ApplicationMapWindow', {
      icon: metadata.icon,
      title: metadata.name,
      width: 400,
      height: 200
    }, app, scheme]);
  }

  ApplicationMapWindow.prototype = Object.create(Window.prototype);
  ApplicationMapWindow.constructor = Window.prototype;

  ApplicationMapWindow.prototype.init = function(wmRef, app, scheme) {
    var root = Window.prototype.init.apply(this, arguments);
    var self = this;

    // Load and set up scheme (GUI) here
    scheme.render(this, 'MapWindow', root);

	
	this._find('Mbone').son('hover', this, this.onMyButtonClick);
	
    return root;
  };

  ApplicationMapWindow.prototype.destroy = function() {
    Window.prototype.destroy.apply(this, arguments);
  };

  /////////////////////////////////////////////////////////////////////////////
  // APPLICATION
  /////////////////////////////////////////////////////////////////////////////

  function ApplicationMap(args, metadata) {
    Application.apply(this, ['ApplicationMap', args, metadata]);
  }

  ApplicationMap.prototype = Object.create(Application.prototype);
  ApplicationMap.constructor = Application;

  ApplicationMap.prototype.destroy = function() {
    return Application.prototype.destroy.apply(this, arguments);
  };

  ApplicationMap.prototype.init = function(settings, metadata) {
    Application.prototype.init.apply(this, arguments);

    var self = this;
    var url = API.getApplicationResource(this, './scheme.html');
    var scheme = GUI.createScheme(url);
    scheme.load(function(error, result) {
      self._addWindow(new ApplicationMapWindow(self, metadata, scheme));
    });

    this._setScheme(scheme);
  };
  
	console.log("Yo!");
  
	function AlertDialog(args, callback) {
		args = Utils.argumentDefaults(args, {});
		DialogWindow.apply(this, ['AlertDialog', {
		  title: args.title || API._('DIALOG_ALERT_TITLE'),
		  icon: 'status/dialog-warning.png',
		  width: 400,
		  height: 100
		}, args, callback]);
	};
	
	
	
	ApplicationMapWindow.prototype.onMyButtonClick = function(el, ev) {
			console.log('CLICKED');
			API.createDialog('Alert',{
				message: 'Foo'
				}, function(){
					console.log('closed');
			});
			
			API.error('Error','Fuck the police','23');
			
			
		};
	
	
  /////////////////////////////////////////////////////////////////////////////
  // EXPORTS
  /////////////////////////////////////////////////////////////////////////////


  OSjs.Applications = OSjs.Applications || {};
  OSjs.Applications.ApplicationMap = OSjs.Applications.ApplicationMap || {};
  OSjs.Applications.ApplicationMap.Class = ApplicationMap;

})(OSjs.Core.Application, OSjs.Core.Window, OSjs.Utils, OSjs.API, OSjs.VFS, OSjs.GUI);




  



