var smsClient = require('msg91-sms');

exports = module.exports = {
	sendSMSNotification: function sendSMSNotification(req, callback) {
		var smsConfig = req.app.kraken.get('smsConfig');
		var authkey = smsConfig.authKey;
		var number = req.phoneNumber;
		var message = req.messageContent; 
		var senderid = smsConfig.senderId;
		var route = smsConfig.route;
		var dialCode = smsConfig.dialCode;

		smsClient.sendOne(authkey, number, message, senderid, route, dialCode, function(response) {
			callback(null,response);
		});
	}
}