mongod --remove
mongod --directoryperdb --dbpath "D:\Program Files\MongoDB\Server\4.2\data\db" --logpath "D:\Program Files\MongoDB\Server\4.2\log\mongo.log" --install
net start MongoDB
mongo
> show dbs
> use mydb
> show collections

> db.createCollection('visitors') (to create DB not required for this application)