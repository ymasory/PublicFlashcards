#!/bin/bash

#fail on error
set -e

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
        echo CONVERTING $FILE
        java -jar $HOME/workspace/Flashup/target/scala_*/flashup-*.min.jar $FILE;
        java -jar $HOME/workspace/Flashup/target/scala_*/flashup-*.min.jar -f $FILE;
        java -jar $HOME/workspace/Flashup/target/scala_*/flashup-*.min.jar -b $FILE;
    done;
    mv *.pdf bin;
    cd ..;
done

echo
echo GIT PUSH ORIGIN MASTER
git push origin master
