'use strict';

var db = require('../lib/db');
var content = require('../lib/content');
var Q = require('q');

exports = module.exports = {
	login: function login(req, callback) {
		if (!req.body.password || !req.body.userName) {
			callback({error: "User Name or password is not correct"}, null);
		}
		var loginPromise = Q.ninvoke(db,'login',req.body.userName);
		loginPromise.then(function(response) {
			if (response.password && response.password === req.body.password) {
				var contentPromise = Q.ninvoke(db,'getContent',req);
				contentPromise.then(function(response) {
					console.log('got content');
					callback(null, response);

				}).catch(function(err) {
					callback({error: "Not able to get the content"}, null);
				});
			} else {
				callback({error: "User Name or password is not correct"}, null);
			}
		}).catch(function(err) {
			callback({error: "User Name or password is not correct"}, null);
		});
	}
}