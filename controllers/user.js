'use strict'

var db = require('../lib/db');
var content = require('./content');
var Q = require('q');

exports = module.exports = {
	addUser: function addUser(req, callback) {
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
	}
}