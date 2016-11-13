'use strict'

var db = require('../lib/db');
var Q = require('q');
var notification = require('./notification');

exports = module.exports = {
	getContentForUser: function getContent(req, callback) {
		var query = req.body && req.body.preferences && req.body.preferences[0] || 'education';
		var contentPromise = Q.ninvoke(db, 'search', query);
		
		contentPromise.then(function(response){
			callback(null, response);
		}).catch(function(err) {
			console.log(err);
			callback({error: "Unable to get content"}, null);
		});
	},

	getContent: function getContent(req, callback) {
		var query = {"type": req.query.type, "term" : req.query.searchText};
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
		var data = {createdBy : req.body.email,
					title: req.body.title,
					tags: req.body.tags,
					timeCreated: currentTime,
					content: req.body.content,
					url: req.body.url,
					type: req.body.type,
					timeLastUpdated: currentTime,
					lastUpdatedBy: req.body.email,
					description: req.body.description || req.body.title
					};
		var contentPromise = Q.ninvoke(db, 'addContent', data);
		
		contentPromise.then(function(response){
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
		contentPromise.then(function(result){
			var record = result._source;
			var comments = record.comments;
			var likes = record.likes;
			var operation = req.body.operation || 'update';
			if (operation === 'like') {
				if (!likes) likes = 0;
				likes += 1;
			} else if (operation === 'comment' && req.body.comment) {
				comments.push(req.body.comment);
			}
			var data = {
						id: req.body.id,
						tags: req.body.tags || record.tags,
						content: req.body.content,
						lastUpdatedBy: req.body.email,
						timeLastUpdated: new Date().getTime(),
						comments: comments,
						type: result._type,
						createdBy: record.createdBy,
						timeCreated: record.timeCreated,
						likes: likes,
						title: record.title,
						description: req.body.description || record.description || record.title,
						url: req.body.url || record.url
						};
			
			var updatePromise = Q.ninvoke(db, 'updateContent', data);
			
			updatePromise.then(function(response){
				console.log('content updated');
				var res = {status: "Success", "Message": "Content updated successfully"};
				if (operation === 'answer') {
					var userPromise = Q.ninvoke(db, 'searchUser', data.createdBy);
					userPromise.then(function(user) {
						console.log('got user data');
						req.phoneNumber = user.phone;
						req.messageContent = 'Your question: ' + data.title + ' has been answered, Please check.';
						console.log('calling send sms');
						var smsPromise = Q.ninvoke(notification, 'sendSMS', req);
						smsPromise.then(function(result) {
							console.log('sms sent successfully');
							callback(null, res);
							return;
						}).catch(function(err) {
							console.log(err);
							callback(null, res);
							return;
						});
					}).catch(function(err) {
						console.log(err);
						callback(null, res);
						return;
					});
				} else {
					callback(null,res);
				}
			}).catch(function(err) {
				console.log(err);
				callback({error: "Unable to update content"}, null);
				return;
			});
		}).catch(function(err) {
			console.log(err);
			callback({error: "Unable to update content"}, null);
			return;
		});

	},

	deleteContent: function deleteContent(req, callback) {
		var deletePromise = Q.ninvoke(db, 'deleteContent', req);
		deletePromise.then(function(response){
			callback(null, response);
		}).catch(function(err) {
			console.log(err);
			callback({error: "Unable to delete content"}, null);
		});
	},
}