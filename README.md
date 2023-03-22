# Website with Encrypted ID Tokens

A code example showing a method for protecting Personally Identifiable Information (PII) in ID tokens.

## Overview

A website application receives ID tokens that are encrypted using JSON Web Encryption (JWE).\
The example shows how to use the [jose4j library](https://github.com/RbkGh/Jose4j) in Spring Boot to perform decryption of a Nested JWT. 

## Create Encryption Keys

Run a script that uses OpenSSL to create some development encryption keys:

```bash
./create-keys.sh
```

## Build the Website

Ensure that Java 8 or later is installed, along with Maven, then build and run the app:

```bash
mnv package
java -jar target/example-website-0.0.1-SNAPSHOT.jar
```

## Use the Website

Browse to http://localhost:8080 to sign a user in.\
The app will then decrypt ID tokens and the UI will render user name claims.

## Further Information

- See the [Website Tutorial](https://curity.io/resources/learn/website-using-encrypted-id-tokens) for further information on the setup.
- Please visit [curity.io](https://curity.io/) for more information about the Curity Identity Server.
