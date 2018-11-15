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
					board[a][b] = new Tile(a, b, fullColors);
				else				//make a space with the appropriate color
				{
					if (fullColors.contains(color))
						throw new Exception("There cannot be three of the same color!");
					else if (usedColors.contains(color))
						fullColors.add(color);
					else
						usedColors.add(color);
					board[a][b] = new Tile(a, b, color);
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
		return !constraintsViolated();
	}
	
	//checks the entire board for any tile that violates constraints.
	public boolean constraintsViolated()
	{
		for (int a = 0; a < height; a++)
		{
			for (int b = 0; b < width; b++)
			{
				if (tileViolates(b, a))	//b is width, and a is height
					return true;
			}
		}
		//none of the tiles violated any contraints!
		return false;
	}
	
	//No need to check the whole board when you change one thing!
	//when called with an x and a y, we will only check the tile and the four adjacent to it.
	public boolean constraintsViolated (int x, int y)
	{
		return tileViolates(x, y) || tileViolates(x+1, y) || tileViolates(x-1, y) || tileViolates(x, y+1) || tileViolates(x, y-1);
	}
	
	//exclusively for use in constraintsViolated
	private boolean tileViolates(int x, int y)
	{
		if (board[y][x].getColor() == '_')
			return false;									//blank tiles can never be invalid
		if (x < 0 || y < 0 || x >= width || y >= height)	//if this is called on a space which is out of bounds, it's fine.
			return false;
		
		Tile tile = board[y][x];
		//keep us from going out of bounds.
		//This will make the logic statements for the constraints compare a tile to itself, evaluating to false, effectively skipping to the next ||
		int xPlus, xMinus, yPlus, yMinus;
		if (tile.getX() + 1 > width)
			xPlus = tile.getX();
		else
			xPlus = tile.getX() + 1;
		if (tile.getY() + 1 > height)
			yPlus = tile.getY();
		else
			yPlus = tile.getY() + 1;
		if (tile.getX() - 1 < 0)
			xMinus = tile.getX();
		else
			xMinus = tile.getX() - 1;
		if (tile.getY() - 1 < 0)
			yMinus = tile.getY();
		else
			yMinus = tile.getY() - 1;
		
		//the constraints get pretty ugly. :(
		//Read the report for a better explanation of what's going on here. It's even color-coded!
		if (tile.isSource())
		{
			//make sure at least three adjacent tiles do not match the color
			return !((tile.getColor() != board[yPlus][tile.getX()].getColor() && tile.getColor() != board[yMinus][tile.getX()].getColor()
					&& tile.getColor() != board[tile.getY()][xMinus].getColor())
			|| (tile.getColor() != board[tile.getY()][xPlus].getColor() && tile.getColor() != board[yMinus][tile.getX()].getColor()
					&& tile.getColor() != board[tile.getY()][xMinus].getColor())
			|| (tile.getColor() != board[tile.getY()][xPlus].getColor() && tile.getColor() != board[yPlus][tile.getX()].getColor()
					&& tile.getColor() != board[tile.getY()][xMinus].getColor())
			|| (tile.getColor() != board[tile.getY()][xPlus].getColor() && tile.getColor() != board[yPlus][tile.getX()].getColor()
					&& tile.getColor() != board[yMinus][tile.getX()].getColor()));
		}
		else
		{
			//make sure at least two adjacent tiles do not match the color
			return !((tile.getColor() != board[yPlus][tile.getX()].getColor() && tile.getColor() != board[yMinus][tile.getX()].getColor())
					|| (tile.getColor() != board[yPlus][tile.getX()].getColor() && tile.getColor() != board[tile.getY()][xPlus].getColor())
					|| (tile.getColor() != board[yPlus][tile.getX()].getColor() && tile.getColor() != board[tile.getY()][xMinus].getColor())
					|| (tile.getColor() != board[yMinus][tile.getX()].getColor() && tile.getColor() != board[tile.getY()][xPlus].getColor())
					|| (tile.getColor() != board[yMinus][tile.getX()].getColor() && tile.getColor() != board[tile.getY()][xMinus].getColor())
					|| (tile.getColor() != board[tile.getY()][xPlus].getColor() && tile.getColor() != board[tile.getY()][xMinus].getColor()));
		}
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
