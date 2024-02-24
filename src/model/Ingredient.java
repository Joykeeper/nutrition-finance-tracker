package model;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private final String name;
    public Ingredient(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
