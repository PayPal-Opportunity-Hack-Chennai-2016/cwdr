'use strict';

var db = require('../lib/db');
var Q = require('q');

exports = module.exports = {
	login: function login(req, callback) {
		console.log('printing req body');
		console.log(req.body);
		if (!req.body.password || !req.body.email) {
			callback({error: "User Name or password is not correct"}, null);
			return;
		}
		var loginPromise = Q.ninvoke(db,'searchUser',req.body.email);
		loginPromise.then(function(response) {
			if (response.password && response.password === req.body.password) {
				var data = {fullName: response.fullName,
							address: response.address || "",
							age: response.age,
							qualification: response.qualification,
							email: response.email,
							phone: response.phone,
							tags: response.tags};
				console.log('login success. sending user data');
				console.log(data);			
				callback(null, data);
				return;
			} else {
				callback({error: "User Name or password is not correct"}, null);
				return;
			}
		}).catch(function(err) {
			callback({error: "User Name or password is not correct"}, null);
			return;
		});
	}
}