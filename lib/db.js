var elasticsearch = require('elasticsearch');
var client = new elasticsearch.Client({
  host: 'localhost:9200',
  log: 'trace'
});

exports = module.exports = {
  search: function serach(request, callback){
    client.search({
      q: 'y'
    }).then(function (body) {
      console.log(body.hits);
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
      id: '1',
      body: {
        title: 'Test 1',
        tags: ['y', 'z'],
        published: true,
        published_at: '2013-01-01',
        counter: 1
      }
    }, function (error, response) {
        console.log(error);
        console.log(response);
        callback(null,{});
    });
  }
}



