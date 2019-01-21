import java.util.ArrayList;
import java.util.Scanner;

public class FileSystemApp {

    public static Directory actualDirectory;

    public static void main(String[] args) {

        Directory rootDir = new Directory("rootDir", null); // root directory
        actualDirectory = rootDir;

        mainMenu();

    }

    public static void mainMenu() {
        int option;

        do {

            displayMenuOptions();

            Scanner optionInput = new Scanner(System.in);

            option = optionInput.nextInt();

            switch (option) {
                case 1:
                    listContent();
                    break;
                case 2:
                    if (actualDirectory.isWritable()) {
                        createNewFile();
                    } else {
                        System.out.println("You don't have write permission in this directory");
                    }
                    break;
                case 3:
                    if (actualDirectory.isWritable()) {
                        createNewDirectory();
                    } else {
                        System.out.println("You don't have write permission in this directory");
                    }
                    break;
                case 4:
                    goToDirectory();
                    break;
                case 5:
                    editActualDirectoryPermissions();
                    break;
                case 6:
                    editFileDirectoryPermissions();
                    break;
                case 7:
                    fileMenuOptions();
                    break;
                case 0:
                    break;

                default:
                    System.out.println("\nInvalid option!\n");
                    break;
            }
            
        } while (option != 0);
    }


    public static void displayMenuOptions(){

        System.out.printf("\n--------------%s---------------\n", actualDirectory.name);
        System.out.printf("1 - List Content\n");
        System.out.printf("2 - Create File\n");
        System.out.printf("3 - Create Directory\n");
        System.out.printf("4 - Go to Directory...\n");
        System.out.printf("5 - Edit permissions...\n");
        System.out.printf("6 - Edit file/directory permissions...\n");
        System.out.printf("7 - File tools...\n");

        System.out.printf("0 - Logout\n");
        System.out.printf("-------------------------------\n", actualDirectory.name);

    }

    /**
     * Method that lists the content of the actual directory
     */
    public static void listContent() {
       ArrayList<Entry> contentList = actualDirectory.getContents();

        if (contentList.size() == 0) {
            System.out.println("The directory is empty");
        } else {

            System.out.println("\nContent:");
            for (Entry entry : contentList) {

                char w = entry.isWritable() ? 'w' : '-';
                char r = entry.isReadable() ? 'r' : '-';
                System.out.printf("%s %c%c %s\n", entry.getClass().getName().charAt(0),  r, w, entry.name);
            }
        }

    }

    /**
     * Method that creates a new file inside of the actual directory
     */
    public static void createNewFile() {
        System.out.printf("Write file's name:\n");

        boolean validName = false;
        String fileName;

        do {

            Scanner fileNameInput = new Scanner(System.in);
            fileName = fileNameInput.nextLine();

            if(getEntryByName(fileName.trim()) == null){ // file with provided name doesn´t exist
                validName = true;
            } else {
                System.out.println("Name already exists. Set a different name:");
            }
            
        } while (!validName);
        

        File file = new File(fileName, actualDirectory, 0);

        actualDirectory.addEntry(file);

        System.out.printf("File %s created!\n", file.name);
    }

    /**
     * 
     * @param nameToCheck
     * @return
     */
    public static Entry getEntryByName(String nameToCheck) {
        ArrayList<Entry> contentList = actualDirectory.getContents();

        Entry entry = null;

        for (Entry ent : contentList) {
            if (nameToCheck.equals(ent.name)) {
                entry = ent;
                break;
            }
        }
        return entry;
    }

    /**
     * Method that creates a new directory inside of the actual directory
     */
    public static void createNewDirectory() {
        System.out.printf("Write directory's name:\n");

        boolean validName = false;
        String directoryName;

        do {

            Scanner directoryNameInput = new Scanner(System.in);
            directoryName = directoryNameInput.nextLine();

            if(getEntryByName(directoryName.trim()) == null){ // dirextory with provided name doesn´t exist
                validName = true;
            } else {
                System.out.println("Name already exists. Set a different name:");
            }
            
        } while (!validName);

        Directory dir = new Directory(directoryName, actualDirectory);
        actualDirectory.addEntry(dir);

        System.out.printf("Directory %s created!\n", dir.name);
    }

    /**
     * Method that allows the user to go into an existing directory
     */
    public static void goToDirectory() {
        System.out.printf("Directory's name: \n");
        Scanner directoryNameInput = new Scanner(System.in);
        String directoryName = directoryNameInput.nextLine();

        ArrayList<Entry> contentList = actualDirectory.getContents();

        Directory dir = getDirectoryByNameFromActualDirectory(directoryName);

        if (dir != null) {

            if (dir.isReadable()) {
                actualDirectory = dir;
            } else {
                System.out.println("You don't have permission to read this directory.");
            }
        } else {
            System.out.println("Directory not found!");
        }
    }

    /**
     * Method that search and return a directory by its name
     * @param dirName directory's name to be found
     * @return if the directory exists it will be returned otherwise returns null
     */
    public static Directory getDirectoryByNameFromActualDirectory(String dirName){
        ArrayList<Entry> contentList = actualDirectory.getContents();

        Directory dir = null;

        for (Entry entry : contentList) {
            if (entry.getClass().getName().equalsIgnoreCase("directory")) {
                if (entry.getName().equals(dirName)) {
                    dir = (Directory) entry;
                    break;
                }
            }
        }
        return dir;
    }

    /**
     * Method that search and return a directory by its name
     * @param dirName directory's name to be found
     * @return if the directory exists it will be returned otherwise returns null
     */
    public static File getFileByNameFromActualDirectory(String fileName){
        ArrayList<Entry> contentList = actualDirectory.getContents();

        File file = null;

        for (Entry entry : contentList) {
            if (entry.getClass().getName().equalsIgnoreCase("file")) {
                if (fileName.equals(entry.getName())) {
                    file = (File) entry;
                    break;
                }
            }
        }
        return file;
    }

    /**
     * Method for editing file/directory permissions
     */
    public static void editActualDirectoryPermissions() {

        editPermissions(actualDirectory);
    }

    public static void editPermissions(Entry entry){
        displayMenuPermissions();

        Scanner optionInput = new Scanner(System.in);

        int option = optionInput.nextInt();

        switch (option) {
        case 1:
            switchEntryReadablePermission(entry);
            break;
        case 2:
            switchEntryWritablePermission(entry);
            break;
        case 0:
            break;

        default:
            System.out.println("\nInvalid option!\n");
            break;
        }
    }

    /**
     * Method that changes file/directory permissions
     */
    public static void editFileDirectoryPermissions() {
        System.out.printf("Name of file/directory: \n");
        Scanner entryNameInput = new Scanner(System.in);
        String entryName = entryNameInput.nextLine();

        Entry entry = getEntryByName(entryName.trim());

        if (entry == null) {
            System.out.printf("File/directory not found!\n");
        } else {
            editPermissions(entry);
        }
    }

    /**
     * Method for changing read permission of an entry
     * @param entry entry to be edited
     */
    public static void switchEntryReadablePermission(Entry entry) {
        if (entry.isReadable()) {
            entry.readable = false;
        } else {
            entry.readable = true;
        }
    }

    /**
     * Method for changing wirte permission of an entry
     * @param entry entry to be edited
     */
    public static void switchEntryWritablePermission(Entry entry) {
        if (entry.isWritable()) {
            entry.writable = false;
        } else {
            entry.writable = true;
        }
    }

    /**
     * Menu that displays permissions options
     */
    public static void displayMenuPermissions(){
        System.out.printf("\n----------Permissions---------\n");
        System.out.printf("1 - Read/Unread\n");
        System.out.printf("2 - Write/Unwrite\n");
        System.out.printf("0 - Back\n");

        System.out.printf("-------------------------------\n");
    }

    /**
     * File's menu that allows the user to view or edit the file's contents
     * System checks user's permissions
     */
    public static void fileMenuOptions() {

        System.out.printf("File's name: \n");
        Scanner fileNameInput = new Scanner(System.in);
        String fileName = fileNameInput.nextLine();

        ArrayList<Entry> contentList = actualDirectory.getContents();

        File file = getFileByNameFromActualDirectory(fileName);

        if (file == null) {
            System.out.println("File not found!");
        } else {
            displayFileMenuOptions();

            Scanner optionInput = new Scanner(System.in);

            int option = optionInput.nextInt();

            switch (option) {
                case 1:
                    readFileContent(file);
                    break;
                case 2:
                    editFileContent(file);
                    break;
                case 0:
                    break;

                default:
                    System.out.println("\nInvalid option!\n");
                    break;
            }
        }

    }

    /**
     * Menu that displays permissions options
     */
    public static void displayFileMenuOptions(){
        System.out.printf("\n----------File Options---------\n");
        System.out.printf("1 - Read\n");
        System.out.printf("2 - Write\n");
        System.out.printf("0 - Back\n");

        System.out.printf("-------------------------------\n");
    }
   
    /**
     * Method for editing file content
     */
    public static void editFileContent(File file) {
        if (!file.isWritable()) {
            System.out.println("You don't have permission to edit this file.");
        } else {            
            System.out.printf("Content: (click Enter to end the input)\n");
            Scanner contentInput = new Scanner(System.in);
            String content = contentInput.nextLine();
            file.setContents(content);
        }
    }

    /**
     * Method that shows file content
     */
    public static void readFileContent(File file) {
        if (!file.isReadable()) {
            System.out.println("You don't have permission to read this file.");
        } else {            
            System.out.printf("Content:\n");
            System.out.println(file.getContents());
        }
    }

}