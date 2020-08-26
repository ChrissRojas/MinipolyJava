/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnpolymvc;
import java.util.Scanner;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
/**
 *
 * @author chris
 */

public class minipolyCLI {
    
    public static void addtoSetList(mnpolyBoardModel board, mpolyPlayerModel current){
        int[] setsCompare = board.getSetsIDList();
        ArrayList<mTile>playerProperties = current.getPropertiesList();
        int count =0;
        if(playerProperties.size()!= 0){
            for(int i =0;i < setsCompare.length; i++){
                for (mTile tiles : playerProperties) {
                    if(tiles.getSetID()==setsCompare[i]){
                        count++;
                        if(count==3){
                            count=0;
                            current.addSetOwned(i);
                        }
                    }
                }
            }
        }
    }
    
    public static void addToPropList(mTile tile, mpolyPlayerModel current){
        if(tile.getOwnedStatus()==false && current.getBalance() > tile.getPropCost()){
            double deduct = current.getBalance() - tile.getPropCost();
            tile.setOwner(current);
            current.setBalance(deduct); 
        }
    }
       
    public static double checkRent(mTile tile){
        double theRent = tile.getBaseRent();//base rent as default.
        for(Integer setsOwned : tile.getOwner().getSetsOwned()) {
            if(setsOwned == tile.getSetID()){
                theRent = tile.getSetOwnedRent();
            }
        }
        return theRent;
    }
    public static void payRent(mpolyPlayerModel current, mTile tile){
        if(tile.getOwnedStatus()==true && tile.getOwner()!= current){ //if the tile is owned AND the tile owner is not the current player
            double rentToPay = checkRent(tile);// either rent is 10% or 20%
            double plBalance = current.getBalance() - rentToPay; //deduction
            double ownerBal = tile.getOwner().getBalance(); // get the tile owner balance.
            current.setBalance(plBalance);//deduct the balance
            tile.getOwner().setBalance(ownerBal + rentToPay);//add rent 
        }
    }
    
    public static mpolyPlayerModel currentPlayer(mpolyPlayerModel a, mpolyPlayerModel b){
        mpolyPlayerModel p;
        if(a.getPlayerTurn() == true){
            p = a;
        }
        else{
            p=b;
        }
        return p;
    }
    public static void updateTurn(mpolyPlayerModel current,mpolyPlayerModel a, mpolyPlayerModel b){
        if(current == a){
            current.setTurn(false);
            a.setTurn(true);
        }
        else{
            current.setTurn(false);
            b.setTurn(true);
        }
    }
    
    public static mTile getTile(mnpolyBoardModel m,mpolyPlayerModel current){
        return m.getTile(current.getPos());
    }
    
    public static void updatePos(mpolyPlayerModel current){
        int diceNum = rollDice();
        int oldPos = current.getPos();
        int newPos = oldPos + diceNum;
        if(newPos>39){
            int diffBetween = 39 - oldPos;
            int remaining = diceNum - diffBetween;
            newPos = 0 + remaining;
        }
        else if(newPos == 20){
            newPos = 0;
        }
        System.out.println("The new pos for player is "+newPos);
        current.setPos(newPos);
    }
    
    public static int rollDice(){
        Random rand = new Random();
        int diceCount = 0;
        int total = 0;
        while(diceCount <2){
            total+=rand.nextInt(6)+1;
            diceCount++;
        }
        return total;
    }
    public static void displayProperties(mpolyPlayerModel player){
        if(!player.getPropertiesList().isEmpty()){
           for (mTile propertiesList : player.getPropertiesList()){
            System.out.println(propertiesList.getTileName());
           }
        }
        else{
            System.out.println("No properties in the owned");
        }
    }
    
    public static void minipolyMenu(){
        System.out.println("Choose an option");
        System.out.println("1: Roll Dice and Move");
        System.out.println("2: Buy Property");
        System.out.println("3: Check Balance for Both Players");
        System.out.println("4: Current Player Tile Position -> True value = current player");
        System.out.println("5: Current Player turn");
        System.out.println("6: Show Owned Properties for each Player");
    }
    
    public static mTile[] addTiles() throws FileNotFoundException{
        mTile[] tiles = new mTile[40];
        FileInputStream inFile = new FileInputStream("src/boardObjs.csv");
        Scanner scan = new Scanner(inFile);
        int tileCount=0;
        
        while(scan.hasNextLine()){
            String tlName = scan.nextLine();
            Scanner sc = new Scanner(tlName);
            sc.useDelimiter(",");
            String n = sc.next();
            double prCost = sc.nextDouble();
            double bsRent = sc.nextDouble();
            double setRent = sc.nextDouble();
            int p = sc.nextInt();
            int setID = sc.nextInt();
            int col = sc.nextInt();
            int row= sc.nextInt();
            mTile nTile = new mTile(n,prCost,bsRent,setRent,p,setID,col,row);
            tiles[tileCount] =nTile;
            tileCount++;
        }
        return tiles;
    }
        
    public static void main(String[] args) throws FileNotFoundException {
        mpolyPlayerModel pla1 = new mpolyPlayerModel();
        mpolyPlayerModel pla2 = new mpolyPlayerModel();
        mnpolyBoardModel gameboard = new mnpolyBoardModel(addTiles());
        Scanner kbInput = new Scanner(System.in);
        
        int opt =1;
        while(opt!=0){
            minipolyMenu();
            opt = kbInput.nextInt();
            switch(opt){
                case 1:
                    updatePos(currentPlayer(pla1,pla2));
                    payRent(currentPlayer(pla1,pla2),getTile(gameboard,currentPlayer(pla1,pla2)));
                    updateTurn(currentPlayer(pla1,pla2),pla1,pla2);
                    break;
                case 2:
                    addToPropList(getTile(gameboard,currentPlayer(pla1,pla2)),currentPlayer(pla1,pla2));
                    addtoSetList(gameboard,currentPlayer(pla1,pla2));
                    break;
                case 3:
                    System.out.println("Player 1 Balance: " + pla1.getBalance());
                    System.out.println("Player 2 Balance: " + pla2.getBalance());
                    break;
                case 4:
                    System.out.println("Player 1: "+pla1.getPlayerTurn()+" "+"Player 2: "+pla2.getPlayerTurn());
                    System.out.println("Current Player Position: " + getTile(gameboard,currentPlayer(pla1,pla2)).getTileName());
                    break;
                case 5:
                    System.out.println("Player1: " +pla1.getPlayerTurn()+" "+"Player 2: "+pla2.getPlayerTurn());
                    break;
                case 6:
                    System.out.println("Player 1 Properties:");
                    displayProperties(pla1);
                    System.out.println("\n");
                    System.out.println("Player 2 Properties:");
                    displayProperties(pla2);
                break;
                case 7:
                    break;
                case 0:
                    opt =0;
                    break;
            }
            
        }
    }
    
}
