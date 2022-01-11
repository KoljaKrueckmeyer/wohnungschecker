#!/bin/bash
set -ex
CHROME_VERSION="95.0.4638.69"
wget --no-verbose -O /tmp/chrome.deb http://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/google-chrome-stable_${CHROME_VERSION}_amd64.deb
sudo apt install -y /tmp/chrome.deb
sudo rm /tmp/chrome.deb
