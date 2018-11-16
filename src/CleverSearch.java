import java.util.ArrayList;

public class CleverSearch
{
	private int attemptedSolutions, assignmentsMade, deadEnds;
	private Gameboard initialState;
	
	public CleverSearch (Gameboard startingBoard)
	{
		initialState = startingBoard;
	}
	
	//TODO pick the most constrained variable, assign it the least constraining value, forward checking or arc consistency
	public Gameboard solve()
	{
		attemptedSolutions = assignmentsMade = deadEnds = 0;
		return solve(new Gameboard(initialState));
	}
	
	private Gameboard solve (Gameboard state)
	{
		//first, we need to choose which tile to assign a color!
		ArrayList<Tile> mostConstrainted = new ArrayList<>();
		int valuesLeft = 2000;
		
		//get a list of all the most constrained tiles
		for (Tile[] row : state.board)
		{
			for (Tile tile : row)
			{
				if (!tile.isOccupied() && tile.getPossibleColors().size() < valuesLeft)
				{
					valuesLeft = tile.getPossibleColors().size();
					mostConstrainted.clear();
					mostConstrainted.add(tile);
				}
			}
		}
		
		if (valuesLeft == 2000)
		{
			attemptedSolutions++;
			if (state.isSolved())
				return state;
			else
			{
				deadEnds++;
				return null;
			}
		}
		
		//we'll need to run a tiebreaker if we come up with multiple tiles
		Tile current;
		if (mostConstrainted.size() > 1)
		{
			//TODO pick a tile at random from the ones bordering the most empty tiles
			current = mostConstrainted.get(1);
		}
		else
			current = mostConstrainted.get(0);
		
		Gameboard result;
		for (char color : current.getPossibleColors())
		{
			assignmentsMade += 1;
			current.setColor(color);
			
			if (state.doesNotViolate(current.getX(), current.getY()))
			{
				result = solve(new Gameboard(state));
				if (result != null)
					return result;
			}
			
			else
				deadEnds++;
		}
		return null;
	}
	
	public int getAttemptedSolutions ()
	{
		return attemptedSolutions;
	}
	public int getAssignmentsMade ()
	{
		return assignmentsMade;
	}
	public int getDeadEnds ()
	{
		return deadEnds;
	}
}
