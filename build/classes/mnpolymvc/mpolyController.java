/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnpolymvc;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author chris_000
 */
public class mpolyController {
    private mpolyPlayerModel player1;
    private mpolyPlayerModel player2;
    private final mnpolyBoardModel mBoard;
    private mpolyView view;
    private int[] setsCompare; 
    private ArrayList<mTile> playerProperties;
    private int count;
    private final int emptyListNum = 0;
    private boolean enableDisable;
    
    public mpolyController(mpolyPlayerModel p1, mpolyPlayerModel p2, mnpolyBoardModel b){
        this.player1 = p1;
        this.player2 = p2;
        this.mBoard = b;
    }
    public void setView(mpolyView v){
        this.view = v;
    }
    public void initialise(){
        startGameState();
    }

    /**
     *
     *@post p==player1 || p==player2
     * 
     **/
    public mpolyPlayerModel currentPlayer(){
        mpolyPlayerModel p;
        if(player1.getPlayerTurn() == true){
            p = player1;
        }
        else{
            p = player2;
        }
        assert p==player1 || p==player2; 
        return p;
    }
    
    /**
     * @pre currentPlayer()!=null
     * @post player1 or player 2 turn is set to true
     * 
     */
    //updates which player goes next.
    public void updatePlayerTurn(){
        assert currentPlayer()!=null;
        if(currentPlayer() == player1){
            currentPlayer().setTurn(false);
            player2.setTurn(true);
        }
        else if(currentPlayer()== player2){
            currentPlayer().setTurn(false);
            player1.setTurn(true);
        }
        assert player1.getPlayerTurn()==true || player2.getPlayerTurn()==true;
        changeButtons();
    }
    
    /**
     * @pre currentPlayer() != null;
     * @pre currentPlayer().getPos() >= 0 && currentPlayer().getPos() <= 39
     * @post returns mTile object;
     */
    public mTile getTile(){
        assert currentPlayer() != null;
        assert currentPlayer().getPos() >=0 && currentPlayer().getPos() <= 39;
        return mBoard.getTile(currentPlayer().getPos());
    }
    
    /**
     * @pre currentPlayer() != null;
     * @pre currentPlayer().getPos() >= 0 && currentPlayer().getPos() <=39
     * @post get the tile's row position;
     */
    
    public int getRow(){
        assert currentPlayer() != null;
        assert currentPlayer().getPos() >=0 && currentPlayer().getPos() <= 39;
        return mBoard.getTile(currentPlayer().getPos()).getR();
    }
    /**
     * @pre currentPlayer() !=null
     * @pre currentPlayer().getPos() >= 0 && currentPlayer().getPos() <=39 
     * @post get the tile's column position
     */
    public int getCol(){
        assert currentPlayer() != null;
        assert currentPlayer().getPos() >=0 && currentPlayer().getPos() <= 39;
        return mBoard.getTile(currentPlayer().getPos()).getC();
    }
    /**
     * @pre currentPlayer() !=null
     * @pre 1 <= rollDice() <= 12;
     * @post newPos >=0 && newPos <== 39
     * @post sets new pos for the currentPlayer(); 
     */
    public void updatePos(){
        assert currentPlayer() !=null;
        assert rollDice() >= 1 && rollDice() <= 12;
        mpolyPlayerModel p = currentPlayer();
        int diceNum = rollDice(); // get result from dice roll
        int oldPos = p.getPos();
        int newPos = oldPos + diceNum; // current player position + the d
        if(newPos>39){
            int diffBetween = 39 - oldPos;
            int remaining = diceNum - diffBetween;
            newPos = 0 + remaining;
        }
        else if(newPos == 20){
            newPos = 0;
        }
        assert newPos >= 0 && newPos <=39;
        p.setPos(newPos);
        
    }
    
    /**
     * @pre getTile() != null
     * @post theRent == getTile().getBaseRent || theRent == getTile().getSetOwnedRent()
     */
    public double checkRent(){
        assert getTile() != null;
        double theRent = getTile().getBaseRent();//base rent as default.
        for(Integer setsOwned : getTile().getOwner().getSetsOwned()) { 
            if(setsOwned == getTile().getSetID()){// checks if the Owner owns the set.
                theRent = getTile().getSetOwnedRent(); // changes the rent to 20% of the price of the house.
            }
        }
        assert theRent == getTile().getBaseRent() || theRent==getTile().getSetOwnedRent();
        return theRent;
    }
    /**
     * @pre getTile() !=null
     * @pre currentPlayer() != null
     * @post sets the new balance of both the tile owner and the current player.
     * 
     */
    public void payRent(){
        assert getTile()!=null;
        assert currentPlayer()!=null;
        if(getTile().getOwnedStatus()==true && getTile().getOwner()!= currentPlayer()){ //if the tile is owned AND the tile owner is not the current player
            double rent = checkRent();// either rent is 10% or 20%
            double plBalance = currentPlayer().getBalance() - rent; //deduction
            double ownerBal = getTile().getOwner().getBalance(); // get the tile owner balance.
            currentPlayer().setBalance(plBalance);//deduct the balance
            
            assert currentPlayer().getBalance() == plBalance;// make sure that balance is set correctly
            
            getTile().getOwner().setBalance(ownerBal + rent);//add rent
            bankrupt();
        }
        
        
    }
    /**
     * @pre diceCount =0
     * @post total >=1 && total <=12 
     */
    public int rollDice(){
        Random rand = new Random();
        int diceCount = 0;
        assert diceCount ==0;
        int total = 0;
        while(diceCount <2){ //loops until interation reaches 2.
            total+=rand.nextInt(6)+1; //range of 1 to 6 for dice.
            diceCount++;
        }
        assert total >=1 && total <= 12;
        return total;
    }
    public void changeButtons(){
        enableDisable = false;
        if(player1.getPlayerTurn() == true){
            view.enableButtons(enableDisable, !enableDisable);
        }
        else{
            view.enableButtons(!enableDisable, enableDisable);
        }

    }

    
    public void addToPropList(){
        assert currentPlayer() !=null;
        if(getTile().getOwnedStatus()==false && currentPlayer().getBalance() > getTile().getPropCost()){
            double deduct = currentPlayer().getBalance() - getTile().getPropCost();
            getTile().setOwner(currentPlayer());
            currentPlayer().setBalance(deduct);
            bankrupt();
            addtoSetList();
            
        }
       
    }
    /**
     * @pre balance of players = 0;
     * @post sets the player's turn to false.
     * @post disable buttons.
     */
    public void bankrupt(){
        assert player1.getBalance()==0 || player2.getBalance()==0;
        if(player1.getBalance()==0 || player2.getBalance()==0){
           player1.setTurn(false);
           player2.setTurn(false);
           view.enableButtons(true, true);
           view.disableRoll(true);
           //System.out.println("Game over");
        assert player1.getPlayerTurn()==false;
        assert player2.getPlayerTurn()==false;
       }
    }
    /**
     * @pre currentPlayer() == player1 || currentPlayer() == player2;
     * @post add setID into current player's sets owned list.
     */
    public void addtoSetList(){
        assert currentPlayer() == player1 || currentPlayer() == player2;
        setsCompare = mBoard.getSetsIDList();
        playerProperties = currentPlayer().getPropertiesList();
        count =0;
        if(playerProperties.size()!= emptyListNum){
            for(int i =0;i < setsCompare.length; i++){
                for (mTile tiles : playerProperties) {
                    if(tiles.getSetID()==setsCompare[i]){
                        count++;
                        if(count==3){
                            count=0;
                            currentPlayer().addSetOwned(setsCompare[i]);
                        }
                    }
                }
            }
        }

    }
    public void startGameState(){
        player1.setTurn(true);
        player2.setTurn(false);
        player1.setPos(0);
        player2.setPos(0);
        player1.setColor("RED");
        player2.setColor("BLUE");
        player1.startState();
        player2.startState();
        changeButtons();
    }
}
