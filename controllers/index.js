'use strict';

var IndexModel = require('../models/index');

var db = require('../lib/db');
var loginController = require('./login');

module.exports = function (router) {

    var model = new IndexModel();

    router.get('/', function (req, res) {
        
        res.send('<code><pre>' + JSON.stringify(model, null, 2) + '</pre></code>');
        
    });

    // router.get('/getContentForUser', function (req, res) {
        
    //     res.send('<code><pre>' + JSON.stringify(model, null, 2) + '</pre></code>');
        
    // });
    
    router.get('/search', function (req, res) {
        db.search(req, function(error, result) {
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

    router.get('/addUser', function (req, res) {
        db.addUser(req, function(error, result) {
            if (error) {
                res.json(error);
            } else {
                res.json(result);
            }
        });
        
    });
    
 //    router.post('/updateUser', function (req, res) {
        
 //        res.send('<code><pre>' + JSON.stringify(model, null, 2) + '</pre></code>');
        
 //    });
    
    router.post('/addQuestion', function (req, res) {
        
        res.send('<code><pre>' + JSON.stringify(model, null, 2) + '</pre></code>');
        
    });

 //    router.post('/postComment', function (req, res) {
        
 //        res.send('<code><pre>' + JSON.stringify(model, null, 2) + '</pre></code>');
        
 //    });
    
 //    router.post('/postBlog', function (req, res) {
        
 //        res.send('<code><pre>' + JSON.stringify(model, null, 2) + '</pre></code>');
        
 //    });

    // Admin Controls

    router.get('/addContent', function (req, res) {
        db.addContent(req, function(err, result){
            res.json({status: "Success"});
        });
        
    });

    // router.post('/postAnswer', function (req, res) {
        
    //     res.send('<code><pre>' + JSON.stringify(model, null, 2) + '</pre></code>');
        
    // });

};
