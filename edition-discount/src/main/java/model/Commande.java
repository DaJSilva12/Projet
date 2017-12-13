/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Nico1
 */
public class Commande {
    int quantité;
    float Prix;
    String description;
    float fdp;
    float total;
    String dateAchat;
    int orderNum;
    
    public Commande(String de,int quan, float p,float fdp,String DateA,int On){
        this.description=de;
        this.quantité=quan;
        this.Prix=p;
        this.fdp=fdp;
        this.dateAchat=DateA;
        this.orderNum=On;
    }

    public float getprix(){
        return   quantité*Prix+fdp;

    }
    
    public int getquantité(){
        return this.quantité;
    }
    
    public float getfdp(){
        return this.fdp;
    }

     public String getDescription(){
        return this.description;
    }
     public String getdateAchat(){
        return this.dateAchat;
    }
     public int getorderNum(){
        return this.orderNum;
    }
}
