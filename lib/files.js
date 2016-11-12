var fs = require(fs);

export = module.exports = {

	storeFile: function storeFile(req, callback) {
		var basePath = req.app.kraken.get('baseFilePath');
		var currentTime = new Date().getTime();
		callback(null,null);
	},

	getFile: function getFile(req, callback) {
		callback(null,null);
	}
}