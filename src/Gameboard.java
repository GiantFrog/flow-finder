import java.util.ArrayList;

public class Gameboard
{
	Tile[][] board;
	private ArrayList<Character> usedColors, fullColors;	//for making sure there are exactly 2 of each color to start
	private int height, width;
	
	public Gameboard (ArrayList<String> text) throws Exception
	{
		width = text.get(0).length();	//length of a line
		height = text.size();			//number of lines
		board = new Tile[height][width];
		usedColors = new ArrayList<>();
		fullColors = new ArrayList<>();
		
		//scan through all the input map text and create the initial gameboard.
		int a = 0;
		for (String line : text)
		{
			for (int b = 0; b < width; b++)
			{
				char color = line.charAt(b);
				if (color == '_')	//make an empty space
					board[a][b] = new Tile(b, a, fullColors);
				else				//make a space with the appropriate color
				{
					if (fullColors.contains(color))
						throw new Exception("There cannot be three of the same color!");
					else if (usedColors.contains(color))
						fullColors.add(color);
					else
						usedColors.add(color);
					board[a][b] = new Tile(b, a, color);
				}
			}
			a++;
		}
		
		//make sure every color has been used twice
		for (char color : usedColors)
		{
			if (!fullColors.contains(color))
				throw new Exception("Evey color needs two spaces!");
		}
	}
	
	public Gameboard (Gameboard toCopy)
	{
		height = toCopy.getHeight();
		width = toCopy.getWidth();
		fullColors = toCopy.getColors(); //Should never change, so we can use pointers here.
		board = new Tile[height][width];
		
		for (int a = 0; a < height; a++)
		{
			for (int b = 0; b < width; b++)
			{
				board[a][b] = new Tile(toCopy.board[a][b]);
			}
		}
	}
	
	//checks if a board is a valid final solution
	public boolean isSolved()
	{
		Tile tile;
		for (int a = 0; a < height; a++)
		{
			for (int b = 0; b < width; b++)
			{
				tile = board[a][b];
				
				//every tile must be occupied for a valid solution
				if (!tile.isOccupied())
					return false;
			}
		}
		//since all tiles are occupied, if it violates no constraints, we have a solution!
		return doesNotViolate();
	}
	
	//checks the entire board for any tile that violates constraints.
	public boolean doesNotViolate ()
	{
		for (int a = 0; a < height; a++)
		{
			for (int b = 0; b < width; b++)
			{
				if (!tileDoesNotViolate(b, a))	//b is width, and a is height
					return false;
			}
		}
		//none of the tiles violated any constraints!
		return true;
	}
	
	//No need to check the whole board when you change one thing!
	//when called with an x and a y, we will only check the tile and the four adjacent to it.
	public boolean doesNotViolate (int x, int y)
	{
		return tileDoesNotViolate(x, y) && tileDoesNotViolate(x+1, y) && tileDoesNotViolate(x-1, y) && tileDoesNotViolate(x, y+1) && tileDoesNotViolate(x, y-1);
	}
	
	//returns true if there's at least 2 edges, empty spaces, or color-mismatching spaces next to this tile (3 for sources).
	private boolean tileDoesNotViolate (int x, int y)
	{
		if (x < 0 || y < 0 || x >= width || y >= height)	//if this is called on a space which is out of bounds, it's fine.
			return true;
		if (board[y][x].getColor() == '_')					//blank tiles can never be invalid
			return true;
		
		Tile tile = board[y][x];
		
		//count up the maximum number of color-matching and color-mismatching adjacent tiles
		int maxMatches, maxMismatches, matches = 0, mismatches = 0;
		if (tile.isSource())
		{
			maxMatches = 1;
			maxMismatches = 3;
		}
		else
		{
			maxMatches = 2;
			maxMismatches = 2;
		}
		//lower the amount of allowed mismatches for edge pieces
		/*if (y + 1 >= height)
			maxMismatches--;
		if (y - 1 < 0)
			maxMismatches--;
		if (x + 1 >= width)
			maxMismatches--;
		if (x - 1 < 0)
			maxMismatches--;*/
		//count the matches and mismatches. lower the allowed mismatches for edge pieces.
		try
		{
			if (board[y + 1][x].getColor() != '_')
			{
				if (board[y + 1][x].getColor() == tile.getColor())
					matches++;
				else
					mismatches++;
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			maxMismatches--;		//an edge will throw an out of bounds exception
		}
		try
		{
			if (board[y - 1][x].getColor() != '_')
			{
				if (board[y - 1][x].getColor() == tile.getColor())
					matches++;
				else
					mismatches++;
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			maxMismatches--;		//if against two edges, a non-source pip will have a max mismatch value of 0!
		}
		try
		{
			if (board[y][x + 1].getColor() != '_')
			{
				if (board[y][x + 1].getColor() == tile.getColor())
					matches++;
				else
					mismatches++;
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			maxMismatches--;
		}
		try
		{
			if (board[y][x - 1].getColor() != '_')
			{
				if (board[y][x - 1].getColor() == tile.getColor())
					matches++;
				else
					mismatches++;
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			maxMismatches--;
		}
		return matches <= maxMatches && mismatches <= maxMismatches;
		
		//the constraints get pretty ugly. :(
		//Read the report for a better explanation of what's going on here. It's even color-coded!
		/*if (tile.isSource())
		{
			//make sure at least three adjacent tiles do not match the color
			return (tile.getColor() != board[yPlus][x].getColor() && tile.getColor() != board[yMinus][x].getColor()
					&& tile.getColor() != board[y][xMinus].getColor())
			|| (tile.getColor() != board[y][xPlus].getColor() && tile.getColor() != board[yMinus][x].getColor()
					&& tile.getColor() != board[y][xMinus].getColor())
			|| (tile.getColor() != board[y][xPlus].getColor() && tile.getColor() != board[yPlus][x].getColor()
					&& tile.getColor() != board[y][xMinus].getColor())
			|| (tile.getColor() != board[y][xPlus].getColor() && tile.getColor() != board[yPlus][x].getColor()
					&& tile.getColor() != board[yMinus][x].getColor());
		}
		else
		{
			//make sure at least two adjacent tiles do not match the color
			return (tile.getColor() != board[yPlus][x].getColor() && tile.getColor() != board[yMinus][x].getColor())
					|| (tile.getColor() != board[yPlus][x].getColor() && tile.getColor() != board[y][xPlus].getColor())
					|| (tile.getColor() != board[yPlus][x].getColor() && tile.getColor() != board[y][xMinus].getColor())
					|| (tile.getColor() != board[yMinus][x].getColor() && tile.getColor() != board[y][xPlus].getColor())
					|| (tile.getColor() != board[yMinus][x].getColor() && tile.getColor() != board[y][xMinus].getColor())
					|| (tile.getColor() != board[y][xPlus].getColor() && tile.getColor() != board[y][xMinus].getColor());
		}*/
	}
	
	public int getHeight ()
	{
		return height;
	}
	public int getWidth ()
	{
		return width;
	}
	public ArrayList<Character> getColors()
	{
		return fullColors;
	}
}
