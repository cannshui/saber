# Personal Blog saber v2.0.1

Author: [Nen Den](https://github.com/cannshui)

Designer: [Lenny](http://weibo.com/Lennynan)

## Features

 1. List and Upload articles.
 2. Search articles by type(translation and personal) and tag(Linux, Unix, Java, etc).
 3. Show article detail.
 4. Add comments to article.
 5. Message board.

## Main Dependencies

 1. Java 1.8.
 2. Spring 4.3.24.
 3. SQLite 3.7.
 4. Maven 3.5(for build).
 5. ...

## Dev & Run

    $ git clone https://github.com/cannshui/saber.git
    $ cd saber
    # exec scripts/sqlite3-saber.sql to init database
    $ touch src/main/resources/private.properties
    # custom jdbc properties in private.properties
    # after custom, run with maven
    $ mvn tomcat7:run

## Build & Deploy

After clone and custom, build saber.war:

    $ mvn clean package -DskipTests

Deploy to lastest tomcat(7, 8, 9) servlet container is recommended.

## MIT License

**All source codes and articles** are released under **MIT license**.
