antlr4 JavadocLexer.g4
antlr4 JavadocParser.g4
javac -cp /home/bizmailov/opt/antlr/antlr-4.4-complete.jar *.java
cd ../../../../../..
grun com.puppycrawl.tools.checkstyle.grammars.javadoc.Javadoc javadoc ~/test.java -tokens -gui