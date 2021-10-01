# Website with Encrypted ID Tokens

A code example showing a method for protecting Personally Identifiable Information (PII) in ID tokens.

## Overview

A Kotlin Spring Boot website application receives ID tokens that are encrypted using JSON Web Encryption (JWE).\
The app then uses the [jose4j library](https://github.com/RbkGh/Jose4j) to perform decryption of a Nested JWT. 

## Create Encryption Keys

Run a script that uses OpenSSL to create some development encryption keys:

```bash
./create-keys.sh
```

## Configure the Curity Identity Server

Follow the [Code Example Article](https://curity.io/resources/learn/website-using-encrypted-id-tokens) instructions to configure your instance of the Curity Identity Server.  

## Run the App

Browse to http://localhost:8080 to sign in, then use details from the decrypted ID token in the standard way.

## More Information

Please visit [curity.io](https://curity.io/) for more information about the Curity Identity Server.
