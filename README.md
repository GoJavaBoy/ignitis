# Blog API  - Ignitis test task

## Overview
____
###The main task: 

Develop backend part of the "Blog" service where users can
register, log in, create a simple blog(only text ant title), and delete them.
____

###Solution:

Provided 2 types of users: ***admin*** and ***regular*** user
- ***Regular*** user can:
  - create a post
  - update/delete his own posts 
  - manage profile, delete or update it

- ***Admin*** can:
    - create a post
    - update only his own posts (for security reasons)
    - delete any post in the system
    - get all posts in the system
    - get posts for particular user
    - manage all user profiles, delete or update them

The Blog API is organized around REST, covered with 47 JUnit tests and has
predictable resource-oriented URLs,
accepts JSON-encoded request bodies and returns
JSON-encoded responses, uses standard HTTP response codes and
authentication.
____

Use Swagger UI for Api Documentation **_\**/swagger-ui.html_**

_Credentials for testing:_
- admin@gmail.com - admin
- user@gmail.com - password
- user1@gmail.com - password
- user2@gmail.com - password
____

To run this project you have to:
- Clone it
- Configure Tomcat Server
- Configure data source (default PostgreSQL), you can do it simple
by editing _/resources/db/db.properties_ file.
  
This project uses Liquibase by default, so your database needs to support it.
Also you can run this project without Liquibase,
just edit _/resources/db/db.properties_ and _/resources/db/initDB.sql_ files.

____

###Tech stack:
- Spring Data JPA
- Spring Security
- Spring Web MVC
- Hibernate
- Liquibase
- PostgreSQL
- Maven
- Java 8 Stream API
- Jackson
- JUnit 5


