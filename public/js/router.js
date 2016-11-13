define([
	'nougat',
	'jquery',
	'underscore',
	'backbone',
	'BaseView',
    'core/pageView'
], function (nougat, $, _, Backbone, BaseView, ContentView) {
	'use strict';

	var Router = Backbone.Router.extend({
		     view: {}, /* Holds reference to current view loaded */

            _savedViews: {},

		routes: {
			"transactionDetails": "transactionDetails",
			":app/:page(/*data)": "default" // Default catch-all route
		},
		invokereport : function(name, callback){
			var pageName, viewName, pageView, context;

				console.log("inside invoke");
				var pageName = $("body").data("view-name");
				this.loadScripts(pageName, callback);

				context = nougat.getContext();
	            nougat.setContext($(document.body).data());
	            context = nougat.getContext();

	            console.log(JSON.stringify(context));
    			viewName = context.viewName;
				pageView = new ContentView();
		},
		loadScripts :function (scriptName, callback){
				var path=scriptName;
                if (this._savedViews[path]) {
                    this.view = this._savedViews[path];
                    if(callback)
                    {
                        return callback(this.view);
                    }
                    return this.view;
                }

				require(["view/" + scriptName], $.proxy(function (ViewClass) {
					this.view = new ViewClass();
                    this._savedViews[path]= this.view;
					this.view.delegateEvents();

					this.view.afterRender();
                    if (callback) {
                    callback(this.view);
                    }

                }, this));
     	}
	});

	var init = function () {
		var router = new Router;
		router.on("route:transactionDetails", function () {
			require(['transactionDetails'], function (PageView) {
				var view = new PageView();
			});
		});

		router.on("route:default", function (app, page, data) {
			console.log(page.toLowerCase());

			if(page.toLowerCase() == "report") {
				router.invokereport();
			}else {
				require([app.toLowerCase() + '/' + page.toLowerCase()], function (PageView) {
				var view = new PageView();
				//view.render();
			});
			}

			console.log(data+app.toLowerCase());
			// Attempts to load JS that is of the same path and name as the URL
			// /resolutioncenter/addMobile/escalate -> /js/resolutioncenter/addmobile.js

		});



		Backbone.history.start({pushState: true, hashChange: false});
	};

	return {
		init: init
	};
});
