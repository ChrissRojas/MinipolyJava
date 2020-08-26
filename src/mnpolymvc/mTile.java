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
public class mTile {
    private final String tileName;
    private final double propCost;
    private final double baseRent;
    private final double setOwnedRent;
    private boolean owned;
    private final int setID;
    private mpolyPlayerModel ownedBy;
    private final int indexPos;
    private final int col;
    private final int row;
    
    public mTile(String tN, double pcost,double bRent,double setRent, int p,int stID, int c,int r){
        this.tileName = tN;
        this.propCost = pcost;
        this.baseRent= bRent;
        this.setOwnedRent= setRent;
        this.setID= stID;
        this.indexPos = p;
        this.col = c;
        this.row = r;
        startTileState();
    }
    public int getSetID(){
        return this.setID;
    }
    public String getTileName(){
        return this.tileName;
    }
    public double getPropCost(){
        return this.propCost;
    }
    public int getTilePos(){
        return this.indexPos;
    }
    public int getC(){
        return this.col;
    }
    public int getR(){
        return this.row;
    }
    public double getBaseRent(){
        return this.baseRent;
    }
    /**
     * @return setOwnedRent
     */
    public double getSetOwnedRent(){
        return this.setOwnedRent;
    }
    /**
     * @return owned
     */
    public boolean getOwnedStatus(){
       return this.owned;
    }
    /**
     * @return ownedBy
     */
    public mpolyPlayerModel getOwner(){
        return this.ownedBy;
    }
    
    /**
     * @pre player !=null;
     * @post set owned to true;
     * @post set ownedBy with current player object.
     * @param player 
     */
    public void setOwner(mpolyPlayerModel player){
        assert player != null;
        this.ownedBy = player;
        this.owned = true;
    }
 
    public void startTileState() {
        this.owned = false;
    }
}
