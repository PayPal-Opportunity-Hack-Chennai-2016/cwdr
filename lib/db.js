var elasticsearch = require('elasticsearch');
var client = new elasticsearch.Client({
  host: 'localhost:9200',
  log: 'trace'
});

exports = module.exports = {
  search: function search(query, callback) {
    console.log(query);
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

  getContentById: function search(id, callback) {
    client.search({
      "body": {
        "query": {
          "term": {
            "_id": id
          }
        }
      }
    }).then(function (body) {
      var hits = body.hits.hits[0];
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
        timeCreated: data.timeCreated,
        timeLastUpdated: data.timeLastUpdated,
        createdBy: data.createdBy,
        lastUpdatedBy: data.lastUpdatedBy,
        likes: 0,
        comments: []
      }
    }, function (error, response) {
        if (error) {
          callback(error, null);
        } else {
          callback(null,{});
        }
      });
  },

  updateContent: function updateContent(data, callback) {
    console.log(data);
    client.index({
      index: 'contentindex',
      type: data.type,
      id: data.id,
      body: {
        title: data.title,
        tags: data.tags,
        data: data.content,
        published: true,
        timeCreated: data.timeCreated,
        createdBy: data.createdBy,
        timeLastUpdated: data.timeLastUpdated,
        lastUpdatedBy: data.lastUpdatedBy,
        comments: data.comments,
        likes: data.likes
      }
    }, function (error, response) {
        console.log(error);
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
      id: req.body.email,
      body: {
        "fullName": req.body.fullName,
        "address":req.body.address,
        "age":req.body.age,
        "qualification":req.body.qualification,
        "email":req.body.email,
        "password":req.body.password,
        "phone":req.body.phone,
        "tags":req.body.preferences
      }
    }, function (error, response) {
        if (error) {
          callback(error, null);
        } else {
          callback(null, {status: "Success"});
        }
        
    });
  },

  searchUser: function searchUser(email, callback) {
    client.search({
      index: 'userindex',
      type: 'user',
      "body": {
        "query": {
          "term": {
            "_id": email
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
