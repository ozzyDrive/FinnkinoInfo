package com.finnkinoinfo.finnkinoinfo;

public class movieTheatre {
    String ID;
    String name;
    public movieTheatre(String inputID, String inputName){
        ID=inputID;
        name=inputName;
    }

    public String getName(){
        return name;
    }
    public String getID(){
        return ID;
    }
}
