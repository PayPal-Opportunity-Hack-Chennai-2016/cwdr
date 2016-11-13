'use strict'

var db = require('../lib/db');
var content = require('./content');
var Q = require('q');

exports = module.exports = {
	addUser: function addUser(req, callback) {
		var currentTime = new Date().getTime();
		req.body.timeCreated = currentTime;
		req.body.timeLastUpdated = currentTime;

		if (!req.body.password || !req.body.email) {
			callback({error: "User Name or password is not present in the request"}, null);
			return;
		}

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
		if (!req.body.email) {
			callback({error: "Email not present in the request"}, null);
			return;
		}
		var userPromise = Q.ninvoke(db,'searchUser',req.body.email);
		userPromise.then(function(user) {
			var currentTime = new Date().getTime();
			var data = {body: {timeLastUpdated: currentTime,
				fullName: req.body.fullName,
    			address: req.body.address,
    			age: req.body.age,
    			qualification: req.body.qualification,
    			phone: req.body.phone,
    			tags: req.body.preferences,
    			profilePicture: req.body.profilePicture,
    			id: user.id,
    			timeCreated: user.timeCreated,
    			createdBy: user.createdBy,
    			email: user.email,
    			password: user.password,
    			preferences: user.tags}
        	};
			var updatePromise = Q.ninvoke(db, 'addUser', data);
			updatePromise.then(function(response) {
				callback(null, response);
			}).catch(function(err) {
				console.log(err);
				callback({error: "Unable to update user"}, null);
			});
		}).catch(function(err) {
			console.log(err);
			callback({error: "Not able to find the user"}, null);
		});
	},

	getUser: function getUser(req, callback) {
		if (!req.query.email) {
			callback({error: "Email not present in the request"}, null);
			return;
		}
		var userPromise = Q.ninvoke(db,'searchUser',req.query.email);
		userPromise.then(function(response) {
			console.log(response);
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