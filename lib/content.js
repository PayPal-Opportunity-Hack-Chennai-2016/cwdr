'use strict'

var db = require('./db');
var Q = require('q');

exports = module.exports = {
	getContentForUser: function getContent(req, callback) {
		var query = req.query.searchText;
		var contentPromise = Q.ninvoke(db, 'getContent', query);
		
		contentPromise.then(function(response){
			callback(null, response);
		}).catch(function(err) {
			console.log(err);
			callback({error: "Unable to get content"}, null);
		});
	},

	getContent: function getContent(req, callback) {
		var query = req.query.searchText;
		var contentPromise = Q.ninvoke(db, 'search', query);
		
		contentPromise.then(function(response){
			callback(null, response);
		}).catch(function(err) {
			console.log(err);
			callback({error: "Unable to get content"}, null);
		});
	},

	addContent: function addContent(req, callback) {
		var data = {user : req.body.userName,
					title: req.body.title,
					tags: req.body.tags,
					publish_time: new Date().getTime(),
					content: req.body.content,
					type: req.body.type
					};
		var contentPromise = Q.ninvoke(db, 'addContent', data);
		
		contentPromise.then(function(response){
			console.log('added content');
			var res = {status: "Success", "Message": "Content added successfully"};
			callback(null,res);
		}).catch(function(err) {
			console.log(err);
			callback({error: "Unable to add content"}, null);
		});
	},

	updateContent: function addContent(req, callback) {
		var data = {user : req.body.userName,
					title: req.body.title,
					tags: req.body.tags,
					publish_time: new Date().getTime(),
					content: req.body.content,
					type: req.body.type
					};
		var contentPromise = Q.ninvoke(db, 'addContent', data);
		
		contentPromise.then(function(response){
			console.log('added content');
			var res = {status: "Success", "Message": "Content added successfully"};
			callback(null,res);
		}).catch(function(err) {
			console.log(err);
			callback({error: "Unable to add content"}, null);
		});
	}
}