package edu.sjsu.cs175.pw01;

import java.io.Serializable;

public class Question implements Serializable {
    private String text;
    private String stime;
    private String etime;
    
    public Question() {
    }
    
    public Question(String text, String stime, String etime) {
        this.text = text;
        this.stime = stime;
        this.etime = etime;
    }


    
    
    public String getText() {
        return text;
    }
    public String getSTime() {
        return stime;
    }
    public String getETime() {
        return etime;
    }

}
