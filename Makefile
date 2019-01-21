compile: FileSystemApp.java
	javac FileSystemApp.java

clean :
	rm *.class

run: fileSystemApp
	java FileSystemApp