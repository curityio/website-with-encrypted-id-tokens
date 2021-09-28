#!/bin/bash

###############################################################
# A script to create a client encryption key pair as a P12 file
###############################################################

rm -rf certs
mkdir -p certs
cd certs
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

ROOT_CERT_FILE_PREFIX='root'
ROOT_CERT_DESCRIPTION='Client Root CA for Encrypted ID tokens'
ENCRYPTION_CERT_FILE_PREFIX='client'
ENCRYPTION_PKCS12_PASSWORD='Password1'

#
# Create the root CA key
#
openssl genrsa -out $ROOT_CERT_FILE_PREFIX.key 2048
echo '*** Successfully created Root CA key'

#
# Create a root CA with a 10 year lifetime
#
openssl req \
    -x509 \
    -new \
    -nodes \
    -key $ROOT_CERT_FILE_PREFIX.key \
    -out $ROOT_CERT_FILE_PREFIX.pem \
    -subj "/CN=$ROOT_CERT_DESCRIPTION" \
    -reqexts v3_req \
    -extensions v3_ca \
    -sha256 \
    -days 3650
echo '*** Successfully created Root CA'

#
# Create the client's encryption key
#
openssl genrsa -out $ENCRYPTION_CERT_FILE_PREFIX.key 2048
echo '*** Successfully created client encryption key'

#
# Create the client encryption certificate request
#
openssl req \
    -new \
    -key $ENCRYPTION_CERT_FILE_PREFIX.key \
    -out $ENCRYPTION_CERT_FILE_PREFIX.csr \
    -subj "/CN=client-encryption"
echo '*** Successfully created client encryption certificate request'

#
# Create the client encryption certificate and private key with a 6 month lifetime
#
openssl x509 \
    -req \
    -in $ENCRYPTION_CERT_FILE_PREFIX.csr \
    -CA $ROOT_CERT_FILE_PREFIX.pem \
    -CAkey $ROOT_CERT_FILE_PREFIX.key \
    -CAcreateserial \
    -out $ENCRYPTION_CERT_FILE_PREFIX.pem \
    -sha256 \
    -days 180
echo '*** Successfully created client encryption certificate'

#
# Create a password protected PKCS#12 file
#
openssl pkcs12 \
    -export \
    -inkey $ENCRYPTION_CERT_FILE_PREFIX.key \
    -in $ENCRYPTION_CERT_FILE_PREFIX.pem \
    -name $ENCRYPTION_CERT_FILE_PREFIX \
    -out $ENCRYPTION_CERT_FILE_PREFIX.p12 \
    -passout pass:$ENCRYPTION_PKCS12_PASSWORD
echo '*** Successfully exported client encryption public and private key to a PKCS#12 file'


