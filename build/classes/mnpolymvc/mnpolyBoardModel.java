/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnpolymvc;

/**
 *
 * @author chris_000
 */
public class mnpolyBoardModel{
    private final int totalSets = 8;
    private final int[] setsIDList = new int[]{1,2,3,4,5,6,7,8};
    private mTile[] tileList;
    
    public mnpolyBoardModel(mTile[] p){
        this.tileList =p;
    }
    
    public int[] getSetsIDList(){
        return this.setsIDList;
    } 
    public mTile[] getTileList(){
        return this.tileList;
    }
    public int gettotalSets(){
        return this.totalSets;
    }
    public mTile getTile(int i){
        assert i >= 0 && i <=39;
        return tileList[i];
    }
    
}
