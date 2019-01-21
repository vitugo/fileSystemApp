// Entry is superclass for both File and Directory 
public abstract class Entry 
{ 
    protected Directory parent; 
    protected long created; 
    protected long lastUpdated; 
    protected long lastAccessed; 
    protected String name; 
    protected boolean readable;
    protected boolean writable;
  
    public Entry(String n, Directory p) 
    { 
        name = n; 
        parent = p; 
        created= System.currentTimeMillis(); 
        lastUpdated = System.currentTimeMillis(); 
        lastAccessed = System.currentTimeMillis(); 
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
        return created; 
    } 
    public long getLastUpdatedTime() 
    { 
        return lastUpdated; 
    } 
    public long getLastAccessedTime() 
    { 
        return lastAccessed; 
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