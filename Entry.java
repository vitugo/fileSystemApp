import java.util.Calendar;

// Entry is superclass for both File and Directory 
public abstract class Entry 
{ 
    protected Directory parent; 
    protected static Calendar dateCreated; 
    protected static Calendar dateLastUpdate; 
    protected static Calendar dateLastAccess; 
    protected String name; 
    protected boolean readable;
    protected boolean writable;
  
    public Entry(String n, Directory p) 
    { 
        name = n; 
        parent = p; 
        dateCreated= Calendar.getInstance(); 
        dateLastUpdate = Calendar.getInstance(); 
        dateLastAccess = Calendar.getInstance(); 
        readable = true;
        writable = true;
    }
    
    public boolean delete() 
    { 
        if (parent == null) 
            return false; 
        return parent.deleteEntry(this); 
    } 
  
    public abstract int size(); 
  
    /* Getters and setters. */
    public Calendar getcreationTime() 
    { 
        return dateCreated; 
    } 

    public void updateLastDateUpdate(){
        dateLastUpdate = Calendar.getInstance();
    }

    public void updateLastDateAccess(){
        dateLastAccess = Calendar.getInstance();
    }

    private static String convertDateToStringReadable(Calendar date){
        return date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.DAY_OF_MONTH)
                + "_" 
                + date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE) + ":" + date.get(Calendar.SECOND);
    }

    public String getCreationDateReadable(){
        return convertDateToStringReadable(dateCreated);
    }

    public String getLastDateUpdateReadable(){
        return convertDateToStringReadable(dateLastUpdate);
    }

    public String getLastDateAccessReadable(){
        return convertDateToStringReadable(dateLastAccess);
    }

    public Calendar getLastUpdatedTime() 
    { 
        return dateLastUpdate; 
    } 
    public Calendar getLastAccessedTime() 
    { 
        return dateLastAccess; 
    } 
    public void changeName(String n) 
    { 
        name = n; 
    } 
    public String getName() 
    { 
        return name; 
    } 

    public boolean isReadable() {
        return readable;
    }

    public void setReadPermission(boolean state) {
        this.readable = state;
    }

    public void setWritePermission(boolean state) {
        this.writable = state;
    }

    public boolean isWritable() {
        return writable;
    }
} 