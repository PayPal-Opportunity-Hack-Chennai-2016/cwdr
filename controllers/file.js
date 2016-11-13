'use strict';

var fs = require(fs);
var Q = require('q');
var MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB

function isFileValid(file) {
	return file.size < MAX_FILE_SIZE;
}

export = module.exports = {

	storeFile: function storeFile(req, callback) {
		var file = req.body.file;
		if (!isFileValid(file)) {
			callback({error:"File size limit exceeded"}, null);
			return;
		}
		var basePath = req.app.kraken.get('baseFilePath');
		var currentTime = new Date().getTime();
		var path = basePath + currentTime;
		var filePromise = Q.ninvoke(fs, 'writeFile', path);
		filePromise.then(function(data) {
			callback(null, data);
		}).catch(function(err) {
			callback(err, null);
			callback({error: "Unable to upload file"}, null);
		});
	},

	getFile: function getFile(req, callback) {
		var path = req.query.path;
		var filePromise = Q.ninvoke(fs, 'readFile', path);
		filePromise.then(function(data) {
			callback(null, data);
		}).catch(function(err) {
			callback(err, null);
			callback({error: "Unable to get file content"}, null);
		});
	}
}