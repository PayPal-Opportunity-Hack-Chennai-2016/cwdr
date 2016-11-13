(function() {

	'use strict';

	requirejs.config({
		baseUrl: '/resolutioncenter/js',
		shim: {
			"underscore": {
				exports: "_"
			},
			"dust": {
				exports: "dust"
			},
			"dust-helpers": {
				deps: ["dust"]
			},
			"dust-helpers-supplement": {
				deps: ["dust", "dust-helpers"]
			},
			'backbone': {
				deps: ['underscore', 'jquery'],
				exports: 'Backbone'
			},
			'jquery': {
				exports: 'jQuery'
			},
			'jqueryUI': ['jquery'],
			'cookie': ['jquery'],
			'bootstrap': ['jquery'],
			'ErrorPrompt': ['jquery'],
			'bootstrapAccessibility': ['bootstrap'],
			'opinionLabComponent': ['onlineOpinionPopup', 'opinionLab'],
			'serialize': ['jquery']
		},
		useStrict: true,
		paths: {
			'jquery': 'lib/jquery-1.10.2',
			'hammer': 'lib/hammer.min',
			'jqueryUI': 'lib/jquery-ui-1.10.3',
			'underscore': 'lib/underscore-1.8.3',
			'Datepicker': 'components/Datepicker/bundle',
			'backbone': 'lib/backbone-1.0.0',
			'ErrorPrompt':'lib/errorPrompter',
			'appview': 'appView',
			'dust': 'lib/dust-core-2.0.3',
			'nougat': 'core/nougat',
			'BaseView': 'core/baseView',
			'dust-helpers': 'lib/dust-helpers-1.1.1',
			'dust-helpers-supplement': 'lib/dust-helpers-supplement',
			'bootstrap': 'lib/bootstrap-3.2.0',
			'bootstrapAccessibility': 'lib/bootstrap-accessibility.min',
			'cookie': 'lib/jquery.cookie',
			'opinionLab': '../components/UVLOpinionLab/js/opinionLab',
			'onlineOpinionPopup': '../components/UVLOpinionLab/js/onlineOpinionPopup',
			'opinionLabComponent': '../components/UVLOpinionLab/js/opinionLabComponent',
			'serialize':'lib/serialize'
		}
	});

	//INFO: Uncomment the following line to trigger pisces
	//require(["http://pisces-168854.slc01.dev.ebayc3.com/js/pisces.js"]);
})();