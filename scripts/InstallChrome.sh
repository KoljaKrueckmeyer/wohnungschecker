#!/bin/bash
set -ex
sudo apt install -y wget
wget -o ./google-chrome-stable_current_amd64.deb https://mirror.cs.uchicago.edu/google-chrome/pool/main/g/google-chrome-stable/google-chrome-stable_133.0.6943.98-1_amd64.deb
sudo dpkg -i -y ./google-chrome-stable_current_amd64.deb
