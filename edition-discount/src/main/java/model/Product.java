/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author nbrugie
 */
public class Product {
    
    private String description;
    private float prix;
    private int id;
    
    
    public Product(String description,float p,int id){
        this.description=description;
        this.prix=p;
        this.id=id;
    }
    
    public String getDescription(){
        return this.description;
    }
    public float getprix(){
        return this.prix;
    }
    
    public int getid(){
        return this.id;
    }
}
