'use strict';

var IndexModel = require('../models/index');
var loginController = require('./login');
var content = require('./content');
var user = require('./user');

module.exports = function (router) {

    var model = new IndexModel();

    router.get('/', function (req, res) {
        
        res.send('<code><pre>' + JSON.stringify(model, null, 2) + '</pre></code>');
        
    });

    router.get('/search', function (req, res) {
        content.getContent(req, function(error, result) {
            if (error) {
                res.json(error);
            } else {
                res.json(result);
            }
        });
        
    });

    router.post('/login', function (req, res) {
        loginController.login(req, function(error, result) {
            if (error) {
                res.json(error);
            } else {
                res.json(result);
            }
        });
        
    });

    router.post('/user', function (req, res) {
        user.addUser(req, function(error, result) {
            if (error) {
                res.json(error);
            } else {
                res.json(result);
            }
        });
        
    });

    router.post('/content', function (req, res) {
        content.addContent(req, function(err, result){
            if (err) {
                res.json(err);
            } else {
                res.json(result);
            }
        });
        
    });

    router.delete('/content', function (req, res) {
        content.deleteContent(req, function(error, result) {
            if (error) {
                res.json(error);
            } else {
                res.json(result);
            }
        });
        
    });

    router.put('/content', function (req, res) {
        content.updateContent(req, function(err, result){
            if (err) {
                res.json(err);
            } else {
                res.json(result);
            }
        });
    });
};
