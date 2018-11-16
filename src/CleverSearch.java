import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

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
		initialState.doesNotViolate();	//count up empty adjacent tiles before we start
		return solve(new Gameboard(initialState));
	}
	
	private Gameboard solve (Gameboard state)
	{
		//first, we need to choose which tile to assign a color!
		ArrayList<Tile> mostConstrained = new ArrayList<>();
		int valuesLeft = 2000;
		
		//get a list of all the most constrained tiles
		for (Tile[] row : state.board)
		{
			for (Tile tile : row)
			{
				if (!tile.isOccupied())
				{
					if (tile.getPossibleColors().size() < valuesLeft)
					{
						valuesLeft = tile.getPossibleColors().size();
						mostConstrained.clear();
						mostConstrained.add(tile);
					}
					else if (tile.getPossibleColors().size() == valuesLeft)
						mostConstrained.add(tile);
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
		if (mostConstrained.size() > 1)
		{
			//pick a tile at random from the ones bordering the most empty tiles
			int mostEmptyTiles = 0;
			ArrayList<Tile> degree = new ArrayList<>();
			for (Tile tile : mostConstrained)
			{
				if (tile.getEmptyAdjacentTiles() > mostEmptyTiles)
				{
					mostEmptyTiles = tile.getEmptyAdjacentTiles();
					degree.clear();
					degree.add(tile);
				}
				else if (tile.getEmptyAdjacentTiles() == mostEmptyTiles)
					degree.add(tile);
			}
			current = degree.get((int)(Math.random()*degree.size()));
		}
		else
			current = mostConstrained.get(0);
		
		/*
		for each color,
			set the tile to that color
			for each adjacent tile,
				for each possible color the adjacent tile can be,
					set the adjacent tile to the possible color.
					check to see if this makes things impossible, and tally if so
					reset the color
		compare the total tallies per color set on this tile and put the colors in order
		 */
		HashMap<Character, Integer> bestColors = new HashMap<>();
		for (char color : current.getPossibleColors())
		{
			current.setColor(color);
			Tile adjTile;
			int constrainedTally = 0;
			for (int z = 0; z < 4; z++)
			{
				try
				{
					switch (z)
					{
						case 0:		//below
							adjTile = state.board[current.getY() + 1][current.getX()];
							if (adjTile.isOccupied())
								throw new Exception();
							break;
						case 1:		//above
							adjTile = state.board[current.getY() - 1][current.getX()];
							if (adjTile.isOccupied())
								throw new Exception();
							break;
						case 2:		//right
							adjTile = state.board[current.getY()][current.getX() + 1];
							if (adjTile.isOccupied())
								throw new Exception();
							break;
						default:	//left
							adjTile = state.board[current.getY()][current.getX() - 1];
							if (adjTile.isOccupied())
								throw new Exception();
							break;
					}
					for (char adjColor : adjTile.getPossibleColors())
					{
						adjTile.setColor(adjColor);
						if (!state.doesNotViolate(adjTile.getX(), adjTile.getY()))
							constrainedTally++;
						adjTile.setColor('_');
					}
				}
				catch (Exception e)
				{
					//the switch statement might try to reference an occupied tile or a tile past an edge.
					//we can just skip over any of these tiles and let it loop again.
				}
			}
			bestColors.put(color, constrainedTally);
		}
		
		//iterate through all possible colors in the order of least constraining
		Gameboard result;
		while (!bestColors.isEmpty())
		{
			int lowestValue = 2000;
			char bestColor = '_';
			for (char color : bestColors.keySet())
			{
				if (bestColors.get(color) < lowestValue)
				{
					lowestValue = bestColors.get(color);
					bestColor = color;
				}
			}
			bestColors.remove(bestColor);
			
			assignmentsMade++;
			current.setColor(bestColor);
			
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
