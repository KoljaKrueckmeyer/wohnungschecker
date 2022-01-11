#!/bin/bash
set -ex
CHROME_VERSION="95.0.4638.54-1"
wget --no-verbose -O /tmp/chrome.deb https://dl.google.com/linux/direct/google-chrome-stable_${CHROME_VERSION}_amd64.deb
sudo apt install -y /tmp/chrome.deb
sudo rm /tmp/chrome.deb
