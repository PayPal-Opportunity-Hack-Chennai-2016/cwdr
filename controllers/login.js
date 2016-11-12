'use strict';

var db = require('../lib/db');
var Q = require('q');

exports = module.exports = {
	login: function login(req, callback) {
		if (!req.body.password || !req.body.email) {
			callback({error: "User Name or password is not correct"}, null);
		}
		var loginPromise = Q.ninvoke(db,'searchUser',req.body.email);
		loginPromise.then(function(response) {
			if (response.password && response.password === req.body.password) {
				var data = {fullName: response.fullName,
							address: response.address,
							age: response.age,
							qualification: response.qualification,
							email: response.email,
							phone: response.phone,
							tags: response.tags};
				callback(null, data);
			} else {
				callback({error: "User Name or password is not correct"}, null);
			}
		}).catch(function(err) {
			callback({error: "User Name or password is not correct"}, null);
		});
	}
}