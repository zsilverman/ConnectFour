# Connect Four AI
Algorithm to defeat any opponent using MiniMax or Alpha-Beta Pruning.

### How my algorithm works:
It takes into account all possible four-in-a rows from each position of the board. My algorithm prioritizes stopping an opponent rather than winning and waits to win so that it can beat the opponent in more than one way. The algorithm looks in all directions, horizontal, vertical and diagonal. Pieces on the board are assigned a score based on a series of conditions. The 4-piece path in horizontal, vertical, and diagonal direction counts the number of your pieces for each player and assigns a value based on the board.

#### To compile:
```
javac *.java
```

#### To play against my algorithm:
```
java Main -p1 alphabeta
```

#### To watch my algorithm play:
```
java Main -p1 MonteCarloAI -p2 alphabeta
```
