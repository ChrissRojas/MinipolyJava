/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnpolymvc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author chris_000
 */
public class mpolyControllerTest {
    
    public mpolyControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }



    /**
     * Test of currentPlayer method, of class mpolyController.
     */
    @Test
    public void testCurrentPlayer() {
       System.out.println("Will it return the current player?");
       // mTile tile1 = new mTile("H3",420,42,84,2,1,10,9);
       // mTile tile2 = new mTile("H2",400,40,80,3,1,10,8);
       // mTile tile3 = new mTile("H1",400,40,80,5,1,10,6);
       // mTile tile4 = new mTile("G3",370,37,74,7,2,10,4);
       // mTile tile5 = new mTile("G2",350,35,70,8,2,10,3);
       // mTile tile6 = new mTile("G1",350,35,70,10,2,10,1);
        mTile[] tiles = new mTile[]{};
               
        mpolyPlayerModel player1 = new mpolyPlayerModel();
        mpolyPlayerModel player2 = new mpolyPlayerModel();
        mnpolyBoardModel gameBoard = new mnpolyBoardModel(tiles);
        mpolyController instance = new mpolyController(player1,player2,gameBoard);
        
        player1.setTurn(true);
        player2.setTurn(false);
        
        
        mpolyPlayerModel expResult = player1;
        mpolyPlayerModel result= instance.currentPlayer();
        assertEquals(expResult, result);
        System.out.println(result);
    }




    /**
     * Test of getTile method, of class mpolyController.
     */
    @Test
    public void testGetTile() {
        System.out.println("will it return the the tile the current player is on");
        
        mTile tile1 = new mTile("H3",420,42,84,2,1,10,9);
        mTile tile2 = new mTile("H2",400,40,80,3,1,10,8);
        mTile tile3 = new mTile("H1",400,40,80,5,1,10,6);
        mTile tile4 = new mTile("G3",370,37,74,7,2,10,4);
        mTile tile5 = new mTile("G2",350,35,70,8,2,10,3);
        mTile tile6 = new mTile("G1",350,35,70,10,2,10,1);
        mTile[] tiles = new mTile[]{tile1,tile2,tile3,tile4,tile5,tile6};
               
        mpolyPlayerModel player1 = new mpolyPlayerModel();
        mpolyPlayerModel player2 = new mpolyPlayerModel();
        mnpolyBoardModel gameBoard = new mnpolyBoardModel(tiles);
        mpolyController instance = new mpolyController(player1,player2,gameBoard);
        
        player1.setTurn(true);
        player2.setTurn(false);
        player1.setPos(3);
        
        String expResult = "G3";
        String result = instance.getTile().getTileName();
        assertEquals(expResult, result);
        System.out.println(result);
    }







    /**
     * Test of payRent method, of class mpolyController.
     * test not working due issues with testing with view.
     */
    /*@Test
    public void testPayRent() {
        System.out.println("Will it deduct the rent?");
        
        mTile tile1 = new mTile("H3",420,42,84,2,1,10,9);
        mTile tile2 = new mTile("H2",400,40,80,3,1,10,8);
        mTile tile3 = new mTile("H1",400,40,80,5,1,10,6);
        mTile tile4 = new mTile("G3",370,37,74,7,2,10,4);
        mTile tile5 = new mTile("G2",350,35,70,8,2,10,3);
        mTile tile6 = new mTile("G1",350,35,70,10,2,10,1);
        mTile[] tiles = new mTile[]{tile1,tile2,tile3,tile4,tile5,tile6};
             
        mpolyPlayerModel player1 = new mpolyPlayerModel();
        mpolyPlayerModel player2 = new mpolyPlayerModel();
        mnpolyBoardModel gameBoard = new mnpolyBoardModel(tiles);
        mpolyView view = new mpolyView();
        mpolyController instance = new mpolyController(player1,player2,gameBoard);
        instance.setView(view);
        player1.setPos(4);
        instance.addToPropList();
        instance.updatePlayerTurn();
        player2.setPos(4);
        instance.payRent();
        double expResult = 1965.0;
        double result = player2.getBalance();
        assertEquals(expResult,result,0);
        System.out.println(result);
    }*/

    /**
     * Test of addToPropList method, of class mpolyController.
     */
    @Test
    public void testAddToPropList() {
        System.out.println("Will it deduct the balance after a player has bought a property");
        mTile tile1 = new mTile("H3",420,42,84,2,1,10,9);
        mTile tile2 = new mTile("H2",400,40,80,3,1,10,8);
        mTile tile3 = new mTile("H1",400,40,80,5,1,10,6);
        mTile tile4 = new mTile("G3",370,37,74,7,2,10,4);
        mTile tile5 = new mTile("G2",350,35,70,8,2,10,3);
        mTile tile6 = new mTile("G1",350,35,70,10,2,10,1);
        mTile[] tiles = new mTile[]{tile1,tile2,tile3,tile4,tile5,tile6};
             
        mpolyPlayerModel player1 = new mpolyPlayerModel();
        mpolyPlayerModel player2 = new mpolyPlayerModel();
        mnpolyBoardModel gameBoard = new mnpolyBoardModel(tiles);
        
        mpolyController instance = new mpolyController(player1,player2,gameBoard);
        
        player1.setPos(2);
        player1.setTurn(true);
        instance.addToPropList();
        
        double expResult = 1600.0;
        double result = player1.getBalance();
        assertEquals(expResult,result,0.0);
        System.out.println(result);
    }



    /**
     * Test of addtoSetList method, of class mpolyController.
     */
    @Test
    public void testBuyMultiProperties() {
        System.out.println("Will buying multiple properties result in the correct deduction of ");
        mTile tile1 = new mTile("H3",420,42,84,2,1,10,9);
        mTile tile2 = new mTile("H2",400,40,80,3,1,10,8);
        mTile tile3 = new mTile("H1",400,40,80,5,1,10,6);
        mTile tile4 = new mTile("G3",370,37,74,7,2,10,4);
        mTile tile5 = new mTile("G2",350,35,70,8,2,10,3);
        mTile tile6 = new mTile("G1",350,35,70,10,2,10,1);
        mTile[] tiles = new mTile[]{tile1,tile2,tile3,tile4,tile5,tile6};
             
        mpolyPlayerModel player1 = new mpolyPlayerModel();
        mpolyPlayerModel player2 = new mpolyPlayerModel();
        mnpolyBoardModel gameBoard = new mnpolyBoardModel(tiles);
        
        mpolyController instance = new mpolyController(player1,player2,gameBoard);
        
        player1.setTurn(true);
        player1.setPos(2);
        instance.addToPropList();
        player1.setPos(1);
        instance.addToPropList();
        player1.setPos(0);
        instance.addToPropList();
        
        double expResult = 780.0;
        double result = player1.getBalance();
        assertEquals(expResult,result,0.0);
        System.out.println(result);
    }



    
}
