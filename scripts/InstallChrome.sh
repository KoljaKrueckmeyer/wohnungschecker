#!/bin/bash
set -ex
sudo apt update
sudo apt upgrade
sudo apt install -y wget
wget -o google-chrome-stable_current_amd64.deb https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
sudo dpkg -i google-chrome-stable_current_amd64.deb
