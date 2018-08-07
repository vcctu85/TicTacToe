import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity {
    static String[] board= {"O","1" ,"X","X","4" ,"X", "6" ,"O","O"};
    static String humanPlayer = "X";
    static String aiPlayer = "O";
    static Move desiredMove;

    public static void main(String[] arg) {
        minimax(board, aiPlayer);
        System.out.println(desiredMove.score);
    }

    public static ArrayList<String> emptyBoxes(String[] board) {
        ArrayList<String> newBoard = new ArrayList<>();
        for (String x : board) {
            if (!x.equals("X") && !x.equals("O")) {
                newBoard.add(x);
            }
        }
        return newBoard;
    }

    public static boolean checkWins(String[] board, String player) {
        if ((board[0] == player && board[1] == player && board[2] == player) ||
                (board[3] == player && board[4] == player && board[5] == player)||
                (board[6] == player && board[7] == player && board[8] == player)||
                (board[0] == player && board[3] == player && board[6] == player)||
                (board[1] == player && board[4] == player && board[7] == player)||
                (board[2] == player && board[5] == player && board[8] == player) ||
                (board[0] == player && board[4] == player && board[8] == player)||
                (board[2] == player && board[4] == player && board[6] == player)) {
            return true;
        }
        return false;
    }

    public static int minimax(String[] newBoard, String currentPlayer) {
        ArrayList<String> possibleMoves = emptyBoxes(newBoard);

        if (checkWins(newBoard, humanPlayer)) {
            //game is over
            return -10;
        }
        if (checkWins(newBoard, aiPlayer)) {
            //game is over
            return 10;
        }
        if (possibleMoves.size() == 0) {
            //tie
            return 0;
        }
        //given the current state, here are the possible moves
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < possibleMoves.size(); i++) {
            Move newMove = new Move();
            newMove.index = newBoard[Integer.parseInt(possibleMoves.get(i))];
            newBoard[Integer.parseInt(possibleMoves.get(i))] = currentPlayer;

            int result;
            if (currentPlayer == aiPlayer) {
                result = minimax(newBoard, humanPlayer);
            } else {
                result = minimax(newBoard, aiPlayer);
            }
            newMove.score = result;

            //reset the spot to empty
            newBoard[Integer.parseInt(possibleMoves.get(i))] = newMove.index;

            moves.add(newMove);
        }
        //now we have a list of moves, determine the highest score
        Move bestMove = new Move();
        if (currentPlayer == aiPlayer) {
            int bestScore = -1000000;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).getScore() > bestScore) {
                    bestScore = moves.get(i).score;
                    bestMove = moves.get(i);
                }
            }
        } else {
            int bestScore = 1000000;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).getScore() < bestScore) {
                    bestScore = moves.get(i).score;
                    bestMove = moves.get(i);
                }
            }
        }
        desiredMove = bestMove;
        return 0;
    }
}
