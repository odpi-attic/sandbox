#!/bin/bash

# Add epe
rpm -qi epel-release 1>/dev/null 2>&1
RC=$?
if [[ $RC -ne 0 ]]; then
    yum -y install epel-release
fi

# Get vim
rpm -qi vim-enhanced 1>/dev/null 2>&1
RC=$?
if [[ $RC -ne 0 ]]; then
    yum -y install vim-enhanced
fi

# Get git
which git 1>/dev/null 2>&1
RC=$?
if [[ $RC -ne 0 ]]; then
    yum -y install git
fi

# Custom vimrc for dark terminals
cat << EOF > ~/.vimrc
set smartindent
set tabstop=4
set shiftwidth=4
set expandtab
set bg=dark
EOF

# Need groovy + jre/jdk
which groovy 1>/dev/null 2>&1
RC=$?
if [[ $RC -ne 0 ]]; then
    yum -y install groovy
fi



