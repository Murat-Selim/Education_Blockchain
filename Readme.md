# Education Blockchain

A JavaFX learning application exploring blockchain concepts such as blocks, transactions, wallets, and smart-contract representations. It is an educational local implementation, not a deployed blockchain network.

## Stack and structure

- Java 17 and Maven.
- JavaFX controls/FXML, ControlsFX, and BootstrapFX.
- Gson, Commons Codec, H2, SQLite JDBC, and Apache POI dependencies.
- `src/main/java/com/user/education_blockchain/blockchain/`: blockchain-domain examples.
- `src/main/java/com/user/education_blockchain/controller/`: application controllers.
- `src/main/resources/`: FXML and styles.
- `tutorials_blockchain/`: explanatory notes.

## Setup and build

Install JDK 17 and Maven, then:

```sh
git clone https://github.com/Murat-Selim/Education_Blockchain.git
cd Education_Blockchain
mvn clean compile
```

The repository also includes Maven wrappers: use `.\mvnw.cmd` on Windows or `sh mvnw` on macOS/Linux in place of `mvn`.

## Launching the JavaFX application

The application source is `src/main/java/com/user/education_blockchain/HelloApplication.java`. Open the project in a Java IDE with JavaFX support and run this entry point with the required module dependencies.

The `javafx-maven-plugin` configuration currently names `com.user.education_blockchain/user.education_blockchain.HelloApplication`, which differs from the source package. Correct the module/main-class configuration against `module-info.java` before relying on `mvn javafx:run`. The POM's default goal also references Spring Boot even though this is a JavaFX project; use explicit Maven goals rather than bare `mvn`.

## Validation

```sh
mvn test
```

JUnit dependencies are declared, but this does not establish complete test coverage. GUI behavior should also be checked manually after launch configuration is resolved.

## License

No repository-wide license file is currently declared.
