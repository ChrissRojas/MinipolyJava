/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnpolymvc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 *
 * @author chris_000
 */
public class mpolyView extends Application {
    
    private final int rowColNum = 6;
    private static final int WINDOW_W = 1200;
    private static final int WINDOW_H = 1000;
    private Label PL1 = new Label("Player 1 Balance");
    private Label PL2 = new Label("Player 2 Balance");
    private TextField player1Bal = new TextField();
    private TextField player2Bal = new TextField();
    private Button rollDice = new Button("Roll Dice");
    private Button startGame = new Button("Intialise");
    private Button buy1 = new Button("Buy Property");
    private Button buy2 = new Button("Buy Property");
    private Button improveProp = new Button("Improve Property");
    private Circle player1Counter = new Circle(5,5,5);
    private Circle player2Counter = new Circle(5,5,5);
    private GridPane minipoly = new GridPane();
    private String labelsRow10[] = new String[]{"B3 £120","B2 £100","B1 £100","A3 £70","A2 £50","A1 £50"};
    private String labelsRow0[] = new String[]{"E1 £250","E2 £250","E3 270","F1 £300","F2 £300","F3 £320"};
    private String labelsCol0[] = new String[]{"D3 £220","D2 £200","D1 £200","C3 £170","C2 £150","C1 £150"};
    private String labelsCol10[] = new String[]{"G1 £350","G2 £350","G3 £370","H1 £400","H2 $400","H3 £420"};
    private int row10AndCol0[] = new int[]{1,2,4,6,7,9};
    private int row0AndCol10[] = new int[]{1,3,4,6,8,9};
    private mpolyPlayerModel pl1Model;
    private mpolyPlayerModel pl2Model;
    private mnpolyBoardModel boardModel;
    private mpolyController controller;
    
    
    public void minipolyBoard(GridPane n){
        n.add(new Label("Go"),10,10);
        for(int i =0; i < 11; i++){
            ColumnConstraints col = new ColumnConstraints(80);
            RowConstraints row = new RowConstraints(80);
            n.getRowConstraints().add(row);
            n.getColumnConstraints().add(col);
        }

        for(int r10 = 0; r10 < rowColNum ; r10++){
            //int posR10[] = new int[]{1,2,4,6,7,9};
            Label lbr10 = new Label(labelsRow10[r10]);
            Label lbc0 = new Label(labelsCol0[r10]);
            //n.add(lbr10,posR10[r10],10);
            n.add(lbr10,row10AndCol0[r10],10);
            n.add(lbc0,0,row10AndCol0[r10]);
            n.setValignment(lbr10,VPos.TOP);
            n.setValignment(lbc0,VPos.TOP);
        }
        
        for(int r0 = 0; r0 < rowColNum ; r0++){
            //int posR0[] = new int[]{1,3,4,6,8,9};
            Label lbr0 = new Label(labelsRow0[r0]);
            Label lbc10 = new Label(labelsCol10[r0]);
            //n.add(lbr0,posR0[r0],0);
            n.add(lbr0,row0AndCol10[r0],0);
            n.add(lbc10,10,row0AndCol10[r0]);
            n.setValignment(lbc10,VPos.TOP);
            n.setValignment(lbr0,VPos.TOP);
        } 
    }
    public void startInitialise(){
        minipoly.add(player1Counter,10,10);
        minipoly.add(player2Counter,10,10);
        minipoly.setHalignment(player1Counter,HPos.LEFT);
        minipoly.setHalignment(player2Counter,HPos.RIGHT);
    }
    
    public Circle setCounter(){
        Circle c;
        if(controller.currentPlayer() == pl1Model){
            c = player1Counter;
            minipoly.setHalignment(c,HPos.LEFT);
        }
        else{
            c = player2Counter;
            minipoly.setHalignment(c,HPos.RIGHT);
        }
        return c;
    }
    
    public GridPane playerControls(){
        GridPane gridControl = new GridPane();
        gridControl.add(PL1,0,0);
        gridControl.add(player1Bal,1,0);
        gridControl.add(rollDice,0,2);
        gridControl.add(buy1,0,3);
        gridControl.add(improveProp,0,4);
        gridControl.add(PL2,0,6);
        gridControl.add(player2Bal,1,6);
        gridControl.add(buy2,0,8);
        gridControl.add(startGame,0,9);
        
        startGame.setOnAction((ActionEvent event) -> {
            controller.initialise();
            startInitialise();
        });
        rollDice.setOnAction((ActionEvent event) -> {
            controller.updatePos();
            controller.payRent();
            updateBalance(pl1Model.getBalance(),pl2Model.getBalance());
            minipoly.getChildren().remove(setCounter());
            minipoly.add(setCounter(),controller.getCol(),controller.getRow());
            controller.updatePlayerTurn();
        });
        
        buy1.setOnAction((ActionEvent event) -> {
            controller.addToPropList();
            updateBalance(pl1Model.getBalance(),pl2Model.getBalance());
        });
        
        buy2.setOnAction((ActionEvent event) ->{
            controller.addToPropList();
            updateBalance(pl1Model.getBalance(),pl2Model.getBalance());
        });
        
        player1Bal.setEditable(false);
        player2Bal.setEditable(false);
        return gridControl;
    }
    public mTile[] addTiles() throws FileNotFoundException{
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
    public void updateBalance(double a,double b){
        player1Bal.setText(String.valueOf(a));
        player2Bal.setText(String.valueOf(b));
    }
    public void enableButtons(boolean a,boolean b){
        buy1.setDisable(a);
        buy2.setDisable(b);
    }
    public void disableRoll(boolean a){
        rollDice.setDisable(a);
    }
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        pl1Model = new mpolyPlayerModel();
        pl2Model = new mpolyPlayerModel();
        mnpolyBoardModel gameBoard = new mnpolyBoardModel(addTiles());
        controller = new mpolyController(pl1Model,pl2Model,gameBoard);
        
        controller.setView(this);
        HBox root = new HBox();
        minipolyBoard(minipoly);
        
        player1Counter.setFill(Color.RED);
        player2Counter.setFill(Color.BLUE);
        
        player1Bal.setText(String.valueOf(pl1Model.getBalance()));
        player2Bal.setText(String.valueOf(pl2Model.getBalance()));
        minipoly.setGridLinesVisible(true);
        minipoly.setBackground(new Background(
                new BackgroundFill(Color.WHITE, null, null)));
        
        root.getChildren().add(playerControls());
        root.getChildren().add(minipoly);
        
        Scene scene = new Scene(root,WINDOW_W,WINDOW_H);
        
        primaryStage.setTitle("Minipoly");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
