'use strict'

var db = require('./db');
var Q = require('q');

exports = module.exports = {
	getContentForUser: function getContent(req, callback) {
		var query = req.tags[0];
		var contentPromise = Q.ninvoke(db, 'search', query);
		
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
		var data = {createdBy : req.body.userName,
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
		if (!req.body.id) {
			callback({error: "content Id not present in the request"}, null);
			return;
		}

		var contentPromise = Q.ninvoke(db, 'getContentById', req.body.id);
		contentPromise.then(function(response){
			console.log(response);
			var comments = response._source.comments;
			if (req.body.comment) {
				comments.push(req.body.comment);
			}
			var data = {
						id: req.body.id,
						tags: req.body.tags,
						content: req.body.content,
						lastUpdatedBy: req.body.userName,
						timeLastUpdated: new Date().getTime(),
						comments: comments,
						type: response._type
						};
			var updatePromise = Q.ninvoke(db, 'updateContent', data);
			
			updatePromise.then(function(response){
				console.log('content updated');
				var res = {status: "Success", "Message": "Content updated successfully"};
				callback(null,res);
			}).catch(function(err) {
				console.log(err);
				console.log('error in update');
				callback({error: "Unable to update content"}, null);
			});
		}).catch(function(err) {
			console.log(err);
			callback({error: "Unable to update content"}, null);
		});

	}
}