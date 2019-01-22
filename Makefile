compile: FileSystemApp.java
	javac FileSystemApp.java

clean :
	rm *.class

run: compile
	java FileSystemApp