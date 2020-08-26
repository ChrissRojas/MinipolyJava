/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnpolymvc;

import java.util.ArrayList;

/**
 *
 * @author chris_000
 */
public class mpolyPlayerModel{
    private int position;
    private double balance;
    private boolean playerTurn;
    private String pieceColor;
    private ArrayList<Integer> setsOwned = new ArrayList<>();
    private ArrayList<mTile> properties = new ArrayList<>();

    
    public mpolyPlayerModel(){
        startState();
    }
    
    public String getColor(){
        return this.pieceColor;
    }
    
    public boolean getPlayerTurn(){
        return this.playerTurn;
    }
    
    public int getPos(){
        
        return this.position;  
    }
    
    public double getBalance(){
        return this.balance;
    }
    public void addSetOwned(int setO){
        this.setsOwned.add(setO);
    }
    /**
     * @pre m != null;
     * @post m added into properties ArrayList
     * @param m 
     */
    public void addProperty(mTile m){
        assert m != null;
        this.properties.add(m);
    }
    
    public ArrayList<mTile> getPropertiesList(){
        return this.properties;
    }
    public ArrayList<Integer> getSetsOwned(){
        return this.setsOwned;
    }
    /**
     * @pre newPos >= 0 && newPos <=39
     * @post sets new position
     * @param newPos 
     */
    public void setPos(int newPos){ 
        assert newPos >= 0 && newPos <=39;
        this.position = newPos;
    }
    /**
     * @pre newBal >0
     * @post sets a new balance.
     * @param newBal 
     */
    public void setBalance(double newBal){
        assert newBal>=0;
        this.balance = newBal;
    }
    public void setTurn(boolean turn){
        this.playerTurn = turn;
    }
    public void setColor(String color){
        this.pieceColor =color;
    }

    public void startState() {
        
        this.position = 0;
        this.balance = 2000;   
    }
   
}
