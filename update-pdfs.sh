#!/bin/bash

for DIR in *;
do cd $DIR;

for FILE in *.flashup;
do java -jar $HOME/workspace/Flashup/target/scala_*/flashup-*.min.jar $FILE;
java -jar $HOME/workspace/Flashup/target/scala_*/flashup-*.min.jar -f $FILE;
java -jar $HOME/workspace/Flashup/target/scala_*/flashup-*.min.jar -b $FILE;
done;

mv *.pdf bin;
cd ..;


done;
