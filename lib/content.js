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
		var currentTime = new Date().getTime();
		var data = {user : req.body.userName,
					title: req.body.title,
					tags: req.body.tags,
					timeCreated: currentTime,
					content: req.body.content,
					type: req.body.type,
					timeLastUpdated: currentTime,
					lastUpdatedBy: req.body.userName
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
		if (!req.query.id) {
			callback({error: "content Id not present in the request"}, null);
			return;
		}

		var contentPromise = Q.ninvoke(db, 'getContentById', id);

		contentPromise.then(function(response){
			var data = {user : response.userName,
						title: response.title,
						tags: req.body.tags,
						timeCreated: response.timeCreated,
						content: req.body.content,
						type: req.body.type,
						id: req.body.id,
						lastUpdatedBy: req.body.userName,
						timeLastUpdated: new Date().getTime()
						};
			var updatePromise = Q.ninvoke(db, 'updateContent', data);
			
			contentPromise.then(function(response){
				console.log('added content');
				var res = {status: "Success", "Message": "Content added successfully"};
				callback(null,res);
			}).catch(function(err) {
				console.log(err);
				callback({error: "Unable to add content"}, null);
			});
		}).catch(function(err) {
			console.log(err);
			callback({error: "Unable to get content"}, null);
		});

	}
}