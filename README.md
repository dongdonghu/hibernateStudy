The project is used as personal usage for hibernate study and do the experiment. The build tool is gradle.
Please refer to www.gradle.org how to install gradle.

Here is some basic useful command you may need
gradle eclpse
gradle build 
gradle test
gradle tasks

Before you try to use the project, you have to modify the hibernate.cfg.xml under directory src/main/resources. Change the database setup to your own.
The basic stragey is simple. clean every data in database before one testcase running. It also take advantage of the hibernate feature to drop/create database automatically. So every time you run the unit test, the database will reset.
