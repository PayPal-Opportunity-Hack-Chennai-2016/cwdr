var elasticsearch = require('elasticsearch');
var client = new elasticsearch.Client({
  host: 'localhost:9200',
  log: 'trace'
});

exports = module.exports = {
  search: function search(query, callback) {
    client.search({
      index: 'contentindex',
      "body": {
        "query": {
          "multi_match": {
            "query": query,
            "fields": [ "title", "data", "tags" ]
          }
        }
      }
    }).then(function (body) {
      var hits = body.hits.hits;
      callback(null,hits);
    }, function (error) {
      console.trace(error.message);
    });
  },

  getContent: function search(query, callback) {
    client.search({
      q: 'y'
    }).then(function (body) {
      var hits = body.hits.hits;
      callback(null,hits);
    }, function (error) {
      console.trace(error.message);
    });
  },

  getContentById: function search(id, callback) {
    client.search({
      id: id
    }).then(function (body) {
      var hits = body.hits.hits;
      callback(null,hits);
    }, function (error) {
      console.trace(error.message);
    });
  },

  addContent: function addContent(data, callback) {
    client.index({
      index: 'contentindex',
      type: data.type,
      body: {
        title: data.title,
        tags: data.tags,
        data: data.content,
        published: true,
        published_time: data.publish_time
      }
    }, function (error, response) {
        if (error) {
          callback(error, null);
        } else {
          callback(null,{});
        }
      });
  },

  addUser: function addUser(req, callback) {
    client.index({
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

  searchUser: function searchUser(userName, callback) {
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
          if (response && response.hits && response.hits.total == 1) {
            callback(null,response.hits.hits[0]._source);
          } else {
            callback({error: "User Name or password is not correct"}, null);
          }
        }
        
    });
  }
}
