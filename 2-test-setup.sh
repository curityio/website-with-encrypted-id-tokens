#!/bin/bash

###########################################################################
# A script to run NGROK locally and get the URL to configure in OAuth tools
###########################################################################

kill -9 $(pgrep ngrok) 2>/dev/null
ngrok http 8443 -log=stdout &
sleep 5
export NGROK_URL=$(curl -s http://localhost:4040/api/tunnels | jq -r '.tunnels[] | select(.proto == "https") | .public_url')
if [ "$NGROK_URL" == "" ]; then
  echo "Problem encountered getting an NGROK URL"
  exit 1
fi

DISCOVERY_URL="$NGROK_URL/dev/oauth/anonymous/.well-known/openid-configuration"
echo "Configure the following URL in OAuth tools:"
echo $DISCOVERY_URL
