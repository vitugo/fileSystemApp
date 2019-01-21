// Entry is superclass for both File and Directory 
public abstract class Entry 
{ 
    protected Directory parent; 
    protected long dateCreated; 
    protected long dateLastUpdate; 
    protected long dateLastAccess; 
    protected String name; 
    protected boolean readable;
    protected boolean writable;
  
    public Entry(String n, Directory p) 
    { 
        name = n; 
        parent = p; 
        dateCreated= System.currentTimeMillis(); 
        dateLastUpdate = System.currentTimeMillis(); 
        dateLastAccess = System.currentTimeMillis(); 
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
    public long getcreationTime() 
    { 
        return dateCreated; 
    } 
    public long getLastUpdatedTime() 
    { 
        return dateLastUpdate; 
    } 
    public long getLastAccessedTime() 
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