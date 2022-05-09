#!/bin/bash

#########################################################################################
# A simple OpenSSL script to create self signed client encryption public and private keys
#########################################################################################

rm -rf keys
mkdir -p keys
cd keys
set -e

#
# Point to the OpenSSL configuration file for macOS or Windows
#
case "$(uname -s)" in

  Darwin)
    export OPENSSL_CONF='/System/Library/OpenSSL/openssl.cnf'
 	;;

  MINGW64*)
    export OPENSSL_CONF='C:/Program Files/Git/usr/ssl/openssl.cnf';
    export MSYS_NO_PATHCONV=1;
	;;
esac

#
# Create the private and public keypair
#
openssl genrsa -out client.key 2048

#
# Create the public key to be deployed to the Identity Server
#
openssl rsa -in client.key -out client.pub -pubout -outform PEM

#
# Create a certificate signing request
#
openssl req -new -key client.key -out client.csr -subj "/CN=client-encryption"

#
# Create a self signed certificate
#
openssl x509 -req -days 180 -in client.csr -signkey client.key -out client.pem -sha256 

#
# Export to a password protected keystore
#
openssl pkcs12 -export -inkey client.key -in client.pem -name 'Encryption Key' -out client.p12 -passout pass:Password1
