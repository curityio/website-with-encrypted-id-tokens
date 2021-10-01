# Website with Encrypted ID Tokens

A code example showing a method for protecting Personally Identifiable Information (PII) in ID tokens.

## Overview

A website application developed in Kotlin encrypts ID tokens using JSON Web Encryption (JWE).\
The [jose4j library](https://github.com/RbkGh/Jose4j) is used by the web client to perform decryption of the Nested JWT. 

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
