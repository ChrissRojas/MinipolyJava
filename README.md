# MinipolyJava

# Minipoly Project

This was a javaFX project which was used to create a basic 2 player monopoly game.<br>
MVC design pattern was used to separate the code between logic, states and views.

## The Program
### The mTile Class
This contains the data for each tile whihc includes data such as the price as well the current owner of the tile.
The individual model for each tile so you can treat it as a dataclass.

### minipolyCLI
The command lines version of the game. Mainly used to test while developing the program.

### mnpolyBoardModel Class
Used to model the board. This is supposed to structure the whole game, each tile is stored in a list. With each tile associated
with a certain set. 

