Basic Griffon JavaFX/Java project
---------------------------------

You have just created a basic Griffon application with JavaFX as UI toolkit
and Java as main language. The project has the following file structure

    .
    ├── build.gradle
    ├── griffon-app
    │   ├── conf
    │   ├── controllers
    │   ├── i18n
    │   ├── lifecycle
    │   ├── models
    │   ├── resources
    │   ├── services
    │   └── views
    ├── pom.xml
    └── src
        ├── functional-test
        │   └── java
        ├── integration-test
        │   └── java
        ├── main
        │   ├── java
        │   │   ├── com.anhe3d.domain   domain objects for three dimensional model
        │   │   └── com.anhe3d.util     for reading and slicing files
        │   └── resources
        └── test
            ├── java
            │   └── com.anhe3d.GDATO3DControllerTest
            │           Test for Serial and Printer Connectivity
            └── resources

Simply add your source files to `src/main/java`, your test cases to
`src/test/java` and then you will be able to build your project with

    gradle build
    gradle test
    gradle run

Don't forget to add any extra JAR dependencies to `build.gradle`!

* griffon-app/conf/Config:
For MVC Group, template as following
``` java
    ...
    .e("[Group Name]", map()
        .e("model", "com.anhe3d.[Group Name]Model")
        .e("view", "com.anhe3d.[Group Name]View")
        .e("controller", "com.anhe3d.[Group Name]Controller")
    ...
```
