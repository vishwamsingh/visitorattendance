Visitor registration and attendance application

Application Overview - 
The application is a web application capable of capturing visitor details like phone, name, email address and photograph as part of registration process.
The application allows registered users to be search, logged in and log out if already logged in to capture attendance.
The application is written in springboot/Thymeleaf/Ajax and uses MongoDB to store visitor and attendance records.


Pre-requisites - 
1. MongoDB Database installed. In case if it is not installed follow steps provided in Install MongoDB section


Start application - 
1. After cloning repository, update mongodb properties to connect in src\main\resources\application.properties
2. Execute mvn package to create executable jar file in target directory
3. Open powershell or cmd prompt inside target directory to execute java -jar visitorattendance<version>.jar

Operate application Visitor registration and capture attendance process - 
1. After starting application open https://<ipaddress-of-machine>:8443/register will bring up registration page
2. Enter details and click photo of the visitor
3. Login visitor after registration
4. Return to registration screen for registering other visitors
5. Search an already registered visitor by phone number, if visitor is still inside, you will get a logout button.
6. In case if visitor wants to logout, search visitor by phone number and logout.

Operate application admin processes - 
1. After starting application open https://<ipaddress-of-machine>:8443/admin
2. There are 2 options presented - Generate visitor specific attendance report and generate full attendance register
3. For visitor specific attendance report - enter visitor id which is same as registered phone number and click generate report
4. For full attendance register download - click on download full attendance register

Install MongoDB

mongod --remove
mongod --directoryperdb --dbpath "D:\Program Files\MongoDB\Server\4.2\data\db" --logpath "D:\Program Files\MongoDB\Server\4.2\log\mongo.log" --install
net start MongoDB
mongo
> show dbs
> use mydb
> show collections

> db.createCollection('visitors') (to create DB not required for this application)