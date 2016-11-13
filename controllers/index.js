'use strict';

var IndexModel = require('../models/index');
var db = require('../lib/db');
var loginController = require('./login');
var content = require('../lib/content');

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
        db.addUser(req, function(error, result) {
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

    router.post('/answer', function (req, res) {
        content.updateContent(req, function(err, result){
            if (err) {
                res.json(err);
            } else {
                res.json(result);
            }
        });
        
    });

    router.post('/comment', function (req, res) {
        content.updateContent(req, function(err, result){
            if (err) {
                res.json(err);
            } else {
                res.json(result);
            }
        });
    });

    router.get('/adminLogin', function(req, res){
        res.render('index');
    });

    router.get('/adminEntry', function(req, res){
        res.render('adminEntry');
    });
};
