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
 * @licence Simplified BSD License
 */
(function(Application, Window, Utils, API, VFS, GUI) {
  'use strict';

  /////////////////////////////////////////////////////////////////////////////
  // WINDOWS
  /////////////////////////////////////////////////////////////////////////////

  function ApplicationIPTracerWindow(app, metadata, scheme) {
    Window.apply(this, ['ApplicationIPTracerWindow', {
      icon: metadata.icon,
      title: metadata.name,
      width: 400,
      height: 200
    }, app, scheme]);
  }

  ApplicationIPTracerWindow.prototype = Object.create(Window.prototype);
  ApplicationIPTracerWindow.constructor = Window.prototype;

  ApplicationIPTracerWindow.prototype.init = function(wmRef, app, scheme) {
    var root = Window.prototype.init.apply(this, arguments);
    var self = this;

    // Load and set up scheme (GUI) here
    scheme.render(this, 'IPTracerWindow', root);
	
	// Bind button to call server API
  this._find('Button').on('click', function() { // Expects you to have a button with data-id="Button"
    var methodName = 'MyServerMethod';
    var methodArgs = {'Argument': 'Some Value'};

    app._api(methodName, methodArgs, function(error, result) { // or `this._app`
      if ( error ) {
        console.log('An error occured: ' + error);
        return;
      }

      // Or else do something with 'result'
      // In this example it should return {foo: bar}
      alert(result.foo);
    });
  });
	
	//this._find('information').son('click', this, this.sendInfo)
	
	// this._find('information').son('click', function() {
    // var methodName = 'fetchInformation';
    // //var methodArgs = {'Argument': 'Some Value'};

    // app._api(methodName, function(error, result) { // or `this._app`
      // if ( error ) {
        // alert('An error occured: ' + error);
        // return;
      // }

      // // Or else do something with 'result'
      // // In this example it should return {foo: bar}
      // //alert(result.foo);
    // });
  // });

	
	return root;
  };
  
  ApplicationIPTracerWindow.prototype.destroy = function() {
    Window.prototype.destroy.apply(this, arguments);
  };

  /////////////////////////////////////////////////////////////////////////////
  // APPLICATION
  /////////////////////////////////////////////////////////////////////////////

  function ApplicationIPTracer(args, metadata) {
    Application.apply(this, ['ApplicationIPTracer', args, metadata]);
  }

  ApplicationIPTracer.prototype = Object.create(Application.prototype);
  ApplicationIPTracer.constructor = Application;

  ApplicationIPTracer.prototype.destroy = function() {
    return Application.prototype.destroy.apply(this, arguments);
  };

  ApplicationIPTracer.prototype.init = function(settings, metadata) {
    Application.prototype.init.apply(this, arguments);

    var self = this;
    var url = API.getApplicationResource(this, './scheme.html');
    var scheme = GUI.createScheme(url);
    scheme.load(function(error, result) {
      self._addWindow(new ApplicationIPTracerWindow(self, metadata, scheme));
    });

    this._setScheme(scheme);
  };
  
  // ApplicationIPTracerWindow.prototype.sendInfo = function()
  // {
	// API.curl(
	// {
		// url: 'http://95.93.234.88:8080/fetchInformation.php',
		// method: 'POST',
		// query: 
		// {
			// foo: "bar"
		// }
	// },  function(error, response) 
		// {
			// console.log(response); // What you get back
		// });
  // }
  
  // ApplicationIPTracerWindow.prototype.sendInfo = function()
  // {
	// var methodName = 'fetchInformation';
    // var methodArgs = {'Argument': 'Some Value'};

    // this._app(methodName, methodArgs, function(error, result) { 
      // if ( error ) {
        // alert('An error occured: ' + error);
        // return;
      // }

      // // Or else do something with 'result'
      // // In this example it should return {foo: bar}
      // //alert(result.foo);
    // });
  // }

  /////////////////////////////////////////////////////////////////////////////
  // EXPORTS
  /////////////////////////////////////////////////////////////////////////////

  OSjs.Applications = OSjs.Applications || {};
  OSjs.Applications.ApplicationIPTracer = OSjs.Applications.ApplicationIPTracer || {};
  OSjs.Applications.ApplicationIPTracer.Class = ApplicationIPTracer;

})(OSjs.Core.Application, OSjs.Core.Window, OSjs.Utils, OSjs.API, OSjs.VFS, OSjs.GUI);
