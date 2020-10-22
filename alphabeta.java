import java.util.*;

public class alphabeta extends AIModule
{
	int player;
	int opponent;
	int maxDepth = 5;
	int bestMoveSeen;

	public void getNextMove(final GameStateModule game)
	{
        player = game.getActivePlayer();
        opponent = (game.getActivePlayer() == 1?2:1);

        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

		//begin recursion
		while(!terminate){
			alphabeta(game, 0, player, alpha, beta);
            if(!terminate)
				chosenMove = bestMoveSeen;
		}
		if(game.canMakeMove(chosenMove))
			game.makeMove(chosenMove);
	}

	private int alphabeta(final GameStateModule state, int depth, int playerID, int alpha, int beta) {
        if (terminate)
            return 0;
        if (depth == maxDepth) {
            return eval(state,playerID);
        }
        depth++;
        int value = 0;
        //max's turn
        int bestVal = Integer.MIN_VALUE;
        if(playerID == player){
            value = Integer.MIN_VALUE + 1;
            for(int i = 0; i < state.getWidth(); i++){
                if(state.canMakeMove(i)) {
                    state.makeMove(i);
                    value = Math.max(value, alphabeta(state, depth, opponent, alpha, beta));
                    state.unMakeMove();

                    if (value > bestVal){
                        bestVal = value;
                        if (depth == 1) { //top of recursion, make our move choice
                            bestMoveSeen = i;
                        }
                    }

                    if(value >= beta) //irrationality test
                        return value;
                    alpha = Math.max(alpha,value); //update alpha
                }
            }
            return value;
        }

        else { //min's turn
            value = Integer.MAX_VALUE;
            for(int i = 0; i < state.getWidth(); i++) {
                if (state.canMakeMove(i)) {
                    state.makeMove(i);
                    value = Math.min(value, alphabeta(state, depth, player, alpha, beta));
                    state.unMakeMove();

                    if(value <= alpha) //irrationality test
                        return value;
                    beta = Math.min(beta,value); //update beta
                }
            }
            return value;
        }
    }

    private int sumValues (int player_piece, int opp_piece){

        if(player_piece == 4 && opp_piece == 0)
            return 10000;
        else if(player_piece == 0 && opp_piece == 4)
            return -100000;
        else if(player_piece == 3 && opp_piece == 0)
            return 50;
        else if(player_piece == 0 && opp_piece == 3)
            return -50;
        else if(player_piece == 2 && opp_piece == 0)
            return 10;
        else if(player_piece == 0 && opp_piece == 2)
            return -10;
        else if(player_piece == 1 && opp_piece == 0)
            return 1;
        else if(player_piece == 0 && opp_piece == 1)
            return -1;
        else
            return 0;
    }

	private int eval(final GameStateModule state, int playerID){
        int numCols = state.getWidth();
        int numRows = state.getHeight();
        opponent = playerID;
        player = opponent == 1?2:1;

        int board_sum = 0; 
        int piece_score = 0;
        int player_piece = 0;
        int opp_piece = 0;
        int counter = 0; //pieces we've seen

        for(int i=0; i < numRows; i++){
            for(int j=0; j < numCols; j++){
                //right
                player_piece = 0; opp_piece = 0; piece_score = 0;
                counter = 0;
                while((j+counter) < numCols && counter <= 3) {
                    int currPlayer = state.getAt(j+counter,i);
                    if(currPlayer == player) {
                        player_piece++;
                    }
                    else if(currPlayer == opponent){
                        opp_piece++;
                    }
                    counter++;
                }
                if(counter == 4)
                    piece_score += sumValues(player_piece,opp_piece);

                //left
                player_piece = 0; opp_piece = 0; counter = 0;
                while((j-counter) > 0 && counter <= 3) {
                    int currPlayer = state.getAt(j-counter,i);
                    if(currPlayer == player) {
                        player_piece++;
                    }
                    else if(currPlayer == opponent){
                        opp_piece++;
                    }
                    counter++;
                }
                if(counter == 4)
                    piece_score += sumValues(player_piece,opp_piece);

                //vertical up
                player_piece = 0; opp_piece = 0; counter = 0;
                while((i+counter) < numRows && counter <= 3) {
                    int currPlayer = state.getAt(j,i+counter);
                    if(currPlayer == player) {
                        player_piece++;
                    }
                    else if(currPlayer == opponent){
                        opp_piece++;
                    }
                    counter++;
                }
                if(counter == 4)
                    piece_score += sumValues(player_piece,opp_piece);

                //vertical down
                player_piece = 0; opp_piece = 0; counter = 0;
                while((i-counter) > 0 && counter <= 3) {
                    int currPlayer = state.getAt(j,i-counter);
                    if(currPlayer == player) {
                        player_piece++;
                    }
                    else if(currPlayer == opponent){
                        opp_piece++;
                    }
                    counter++;
                }

                if(counter == 4)
                    piece_score += sumValues(player_piece,opp_piece);

                //right diagonal right
                player_piece = 0; opp_piece = 0; counter = 0;
                while((i+counter) < numRows && (j+counter) <= numCols && counter <= 3) {
                    int currPlayer = state.getAt(j+counter,i+counter);
                    if(currPlayer == player) {
                        player_piece++;
                    }
                    else if(currPlayer == opponent){
                        opp_piece++;
                    }
                    counter++;
                }
                if(counter == 4)
                    piece_score += sumValues(player_piece,opp_piece);

                //right diagonal left
                player_piece = 0; opp_piece = 0; counter = 0;
                while((i-counter) > 0 && (j-counter) > 0 && counter <= 3) {
                    int currPlayer = state.getAt(j-counter,i-counter);
                    if(currPlayer == player) {
                        player_piece++;
                    }
                    else if(currPlayer == opponent){
                        opp_piece++;
                    }
                    counter++;
                }
                if(counter == 4)
                    piece_score += sumValues(player_piece,opp_piece);

                //left diagonal up
                player_piece = 0; opp_piece = 0; counter = 0;
                while((i+counter) < numRows && (j-counter) > 0 && counter <= 3) {
                    int currPlayer = state.getAt(j-counter,i+counter);
                    if(currPlayer == player) {
                        player_piece++;
                    }
                    else if(currPlayer == opponent){
                        opp_piece++;
                    }
                    counter++;
                }
                if(counter == 4)
                    piece_score += sumValues(player_piece,opp_piece);

                //left diagonal down
                player_piece = 0; opp_piece = 0; counter = 0;
                while((i-counter) > 0 && (j+counter) < numCols && counter <= 3) {
                    int currPlayer = state.getAt(j+counter,i-counter);
                    if(currPlayer == player) {
                        player_piece++;
                    }
                    else if(currPlayer == opponent){
                        opp_piece++;
                    }
                    counter++;
                }
                if(counter == 4)
                    piece_score += sumValues(player_piece,opp_piece);
                
                board_sum += piece_score;
            }
        }

        return board_sum;
	}


}