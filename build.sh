javac -Xlint:unchecked -d .  ./src/org/startinglineup/*.java ./src/org/startinglineup/component/*.java ./src/org/startinglineup/controller/*.java ./src/org/startinglineup/data/*.java ./src/org/startinglineup/event/*.java ./src/org/startinglineup/league/*.java ./src/org/startinglineup/simulator/*.java ./src/org/startinglineup/utils/*.java 

jar cfe startinglineup.jar org.startinglineup.Driver ./org/startinglineup/* ./conf/* ./data/*

