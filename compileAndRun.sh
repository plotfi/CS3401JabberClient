#!/bin/bash
export CLASSPATH=./smack.jar:./smackx.jar:./
javac *.java
java JabberClient
