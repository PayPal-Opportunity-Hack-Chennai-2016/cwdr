'use strict'

var db = require('./db');

exports = module.exports = {
	getContent: function getContent(req, callback) {
		var query = {user : req.body.userName};
		db.getContent(query, function(error, response){
			if(response) {
				callback(null, response);
			}
		});
	}
}