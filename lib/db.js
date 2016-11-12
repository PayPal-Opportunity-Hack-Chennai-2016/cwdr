var elasticsearch = require('elasticsearch');
var client = new elasticsearch.Client({
  host: 'localhost:9200',
  log: 'trace'
});

exports = module.exports = {
  search: function search(request, callback) {
    client.search({
      q: 'y'
    }).then(function (body) {
      var hits = body.hits.hits;
      callback(null,hits);
    }, function (error) {
      console.trace(error.message);
    });
  },

  getContent: function getContent(query, callback){
    client.search({
      q: 'y'
    }).then(function (body) {
      var hits = body.hits.hits;
      callback(null,hits);
    }, function (error) {
      console.trace(error.message);
    });
  },

  addContent: function addContent(req, callback) {
    client.create({
      index: 'myindex',
      type: 'mytype',
      body: {
        title: 'Test 1',
        tags: ['y', 'z'],
        data: "test data",
        published: true,
        published_at: '2013-01-01',
        counter: 1
      }
    }, function (error, response) {
        console.log(error);
        console.log(response);
        callback(null,{});
    });
  },

  addUser: function addUser(req, callback) {
    
    var userName = "admin1";
    var password = "password";
    client.create({
      index: 'userindex',
      type: 'user',
      body: {
        user: userName,
        password: password
      }
    }, function (error, response) {
        console.log(error);
        console.log(response);
        callback(null,{});
    });
  },

  login: function login(userName, callback) {
    console.log(userName);
    client.search({
      index: 'userindex',
      type: 'user',
      "body": {
        "query": {
          "match": {
            "user": userName
          }
        }
      }
    }, function (error, response) {
        if(error) {
          callback(error, null);
        } else {
          if (response && response.hits && response.hits.hits && response.hits.hits[0] && response.hits.hits[0]._source) {
            console.log(response.hits.hits[0]);
            callback(null,response.hits.hits[0]._source);
          } else {
            callback({error: "User Name or password is not correct"}, null);
          }
        }
        
    });
  }
}
