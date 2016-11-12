'use strict';

var db = require('../lib/db');
var content = require('../lib/content');
var Q = require('q');

exports = module.exports = {
	login: function login(req, callback) {
		if (!req.body.password || !req.body.email) {
			callback({error: "User Name or password is not correct"}, null);
		}
		var loginPromise = Q.ninvoke(db,'searchUser',req.body.email);
		loginPromise.then(function(response) {
			if (response.password && response.password === req.body.password) {
				var contentPromise = Q.ninvoke(content,'getContentForUser',response);
				contentPromise.then(function(result) {
					console.log('got content');
					callback(null, result);

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