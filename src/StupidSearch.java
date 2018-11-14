import java.util.ArrayList;
import java.util.Stack;

public class StupidSearch
{
	private ArrayList<Tile> closed;
	private Stack<Tile> frontier;
	private int attemptedSolutions, endingDepth;
	private Gameboard initialState;
	
	public StupidSearch (Gameboard startingBoard)
	{
		closed = new ArrayList<>();
		frontier = new Stack<>();
		attemptedSolutions = 0;
		initialState = startingBoard;
	}
	
	public Gameboard solve()
	{
		for (int a = 0; a < initialState.getHeight(); a++)
		{
			for (int b = 0; b < initialState.getWidth(); b++)
			{
				if (initialState.board[a][b].isOccupied())	//add each of the four tiles adjacent to a source to the frontier
				{											//it cannot be off the edge of the board, occupied, or already on the frontier
					if (a+1 < initialState.getHeight() && !initialState.board[a+1][b].isOccupied() && !frontier.contains(initialState.board[a+1][b]))
						frontier.add(initialState.board[a+1][b]);
					if (a-1 >= 0 && !initialState.board[a-1][b].isOccupied() && !frontier.contains(initialState.board[a-1][b]))
						frontier.add(initialState.board[a-1][b]);
					if (b+1 < initialState.getWidth() && !initialState.board[a][b+1].isOccupied() && !frontier.contains(initialState.board[a][b+1]))
						frontier.add(initialState.board[a][b+1]);
					if (b-1 >= 0 && !initialState.board[a][b-1].isOccupied() && !frontier.contains(initialState.board[a][b-1]))
						frontier.add(initialState.board[a][b-1]);
				}
			}
		}
		return solve(initialState, 0);
	}
	
	//TODO this is all a terrible way of doing it. Instead, iterate across every open tile, give it a color, and ditch the branch when a conflict arises.
	private Gameboard solve (Gameboard state, int depth)
	{
		//TODO pop from the frontier, do the stuff to it
		while (true)
		{
			Tile current = frontier.pop();
			if (current == null)
				return null;
			
			//TODO check each adjacent space
		}
		if (depth >= endingDepth)
		{
			if (state.isSolved())
				return state;
			else
				return null;
		}
	}
}
