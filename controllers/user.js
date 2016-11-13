'use strict'

var db = require('../lib/db');
var content = require('./content');
var Q = require('q');

exports = module.exports = {
	addUser: function addUser(req, callback) {
		var currentTime = new Date().getTime();
		req.body.timeCreated = currentTime;
		req.body.timeLastUpdated = currentTime;
		var userPromise = Q.ninvoke(db, 'addUser', req);
		userPromise.then(function(response){
			var contentPromise = Q.ninvoke(content, 'getContentForUser', req);
			contentPromise.then(function(response) {
				callback(null, response);
			}).catch(function(err) {
				console.log(err);
				callback(null,{status: "Success", "Message" : "User created, Unable to get content"});
			});
		}).catch(function(err) {
			console.log(err);
			callback({error: "Unable to create user"}, null);
		});
	},

	updateUser: function addUser(req, callback) {
		var currentTime = new Date().getTime();
		var data = {timeLastUpdated: currentTime,
					fullName: req.body.fullName,
        			address: req.body.address,
        			age: req.body.age,
        			qualification: req.body.qualification,
        			phone: req.body.phone,
        			tags: req.body.preferences,
        			profilePicture: req.body.profilePicture
        		};
		var updatePromise = Q.ninvoke(db, 'updateUser', data);
		updatePromise.then(function(response) {
			callback(null, response);
		}).catch(function(err) {
			console.log(err);
			callback({error: "Unable to update user"}, null);
		});
	},

	getUser: function getUser(req, callback) {
		if (!req.body.email) {
			callback({error: "Email not present in the request"}, null);
			return;
		}
		var userPromise = Q.ninvoke(db,'searchUser',req.body.email);
		userPromise.then(function(response) {
			var data = {fullName: response.fullName,
						address: response.address,
						age: response.age,
						qualification: response.qualification,
						email: response.email,
						phone: response.phone,
						tags: response.tags};			
			callback(null, data);
		}).catch(function(err) {
			callback({error: "Not able to get user data"}, null);
		});
	},
}