nameAgeApp for recruitment in Vector Smart Data
--

---
Java Version: 21

Spring Boot: 3.2.6

Type of database: text file

---

Initially _database.path_ has to be specified in _application.properties_. Database will be created under this path.

---
No spring profile used:

* Initially empty database will be realised in path given in _application.properties_ under _database.path_

Spring profile **db_populated** used:

* Database with pre-initialized records from _/resources/database/db_populated.txt_ will be realised in path given in
  _application.properties_ under _database.path_.

Spring profile **test** is used only by unit and integration tests.


---

**If under** _database.path_ **the same file exists it will be used as a database.** (Application resistant to restart)



---

By default application runs on address: http://localhost:8080/nameAgeApp (defined in _application.properties_)



---
Postman collection available in provided file _nameAgeApp.postman_collection.json_.

---

Application with preinitialized database:

`java -jar C:\path\to\jar\nameageapp-1.0.0.jar --spring.profiles.active=db_populated `

or wthout:

`java -jar C:\path\to\jar\nameageapp-1.0.0.jar`




---