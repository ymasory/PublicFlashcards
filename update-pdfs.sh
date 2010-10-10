#!/bin/bash

# #fail on error
# set -e

cd `dirname $0`

echo GIT PULL
git pull
echo

for DIR in *; do
    if [ -f $DIR ]
    then
        continue;
    fi
    cd $DIR;
    for FILE in *.flashup; do
        echo converting `pwd`/$FILE
        java -jar $HOME/workspace/Flashup/target/scala_*/flashup-*.min.jar $FILE;
        java -jar $HOME/workspace/Flashup/target/scala_*/flashup-*.min.jar -f $FILE;
        java -jar $HOME/workspace/Flashup/target/scala_*/flashup-*.min.jar -b $FILE;
    done;
    mv *.pdf bin;
    cd ..;
done

echo
echo GIT STATUS
git status
echo GIT PUSH ORIGIN MASTER
git add .
git commit -am "PDF UPDATE SCRIPT"
git push origin master
