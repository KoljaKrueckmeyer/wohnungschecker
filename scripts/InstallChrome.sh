#!/bin/bash
set -ex
CHROME_VERSION="94.0.4606.113-1"
wget --no-verbose -O /tmp/chrome.deb http://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/google-chrome-stable_${CHROME_VERSION}_amd64.deb
sudo apt install -y /tmp/chrome.deb
sudo rm /tmp/chrome.deb
