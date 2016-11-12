'use strict';

var IndexModel = require('../models/index');

var db = require('../lib/db');

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
            res.json(result);
        });
        
    });

	// router.post('/login', function (req, res) {
        
 //        res.send('<code><pre>' + JSON.stringify(model, null, 2) + '</pre></code>');
        
 //    });

 //    router.post('/addUser', function (req, res) {
        
 //        res.send('<code><pre>' + JSON.stringify(model, null, 2) + '</pre></code>');
        
 //    });
    
 //    router.post('/updateUser', function (req, res) {
        
 //        res.send('<code><pre>' + JSON.stringify(model, null, 2) + '</pre></code>');
        
 //    });
    
 //    router.post('/postQuestion', function (req, res) {
        
 //        res.send('<code><pre>' + JSON.stringify(model, null, 2) + '</pre></code>');
        
 //    });

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
