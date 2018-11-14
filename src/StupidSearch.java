public class StupidSearch
{
	private int attemptedSolutions, assignmentsMade;
	private Gameboard initialState;
	
	public StupidSearch (Gameboard startingBoard)
	{
		initialState = startingBoard;
	}
	
	//resets the tracker values and starts the recursion off.
	public Gameboard solve()
	{
		attemptedSolutions = assignmentsMade = 0;
		return solve(new Gameboard(initialState), 0, 0);	//make a new gameboard so we don't muck up the one in the main class!
	}
	
	//iterates over every tile in the board, assigning random colors until it works, skipping the source tiles.
	private Gameboard solve (Gameboard state, int x, int y)
	{
		//go down to the next row once we've completed one.
		//x is not checked to be in bounds when we call solve, so we should start by doing that.
		if (x >= state.getWidth())
		{
			x -= state.getWidth();
			y++;
		}
		
		if (y >= state.getHeight())
		{
			//we've completed the list! One last (redundant at this point) check to make sure we have a valid solution.
			attemptedSolutions += 1;
			if (state.isSolved())
				return state;
			else
				return null;
		}
		
		//just skip over any source tiles, we can reuse the untouched state.
		if (state.board[y][x].isSource())
			return solve(state, x+1, y);
		else
		{
			//for each color used in the board, start a new branch where our tile is set to that color.
			Gameboard result;
			for (char color : state.getColors())
			{
				assignmentsMade += 1;
				state.board[y][x].setColor(color);
				
				if (state.constraintsViolated())	//see if we have violated our constraints, and ditch the branch if so.
					return null;
				
				//and thus begins the next level of recursion
				result = solve(new Gameboard(state), x+1, y);
				
				if (result != null)		//if we get back a solution, we are done looping!
					return result;		//if not, well, let's do it again with another color.
			}
		}
		return null;	//if we've finished trying all the colors, this branch is dead. backtracking time.
	}
	
	public int getAttemptedSolutions()
	{
		return attemptedSolutions;
	}
}
