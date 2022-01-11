#!/bin/bash
set -ex
CHROME_VERSION="95.0.4638.54-1"
wget https://dl.google.com/linux/direct/google-chrome-stable_${CHROME_VERSION}_amd64.deb
sudo apt install ./google-chrome-stable_current_amd64.deb
