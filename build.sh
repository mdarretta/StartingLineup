SRC_PATH=./src/org/startinglineup

#Clean generated directories
rm -rf doc src

javac -Xlint:unchecked -d .  $SRC_PATH/*.java $SRC_PATH/component/*.java $SRC_PATH/controller/*.java $SRC_PATH/data/*.java $SRC_PATH/event/*.java $SRC_PATH/league/*.java $SRC_PATH/simulator/*.java $SRC_PATH/utils/*.java 

jar cfe startinglineup.jar org.startinglineup.Driver ./org/startinglineup/* ./conf/* ./data/* ./doc/*

javadoc -sourcepath $SRC_PATH/Driver.java $SRC_PATH/*.java $SRC_PATH/component/*.java $SRC_PATH/controller/*.java $SRC_PATH/data/*.java $SRC_PATH/event/*.java $SRC_PATH/league/*java $SRC_PATH/simulator/*.java $SRC_PATH/utils/*.java -d doc
