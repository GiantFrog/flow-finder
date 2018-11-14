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
			for (int b = 0; b < line.length(); b++)
			{
				char color = line.charAt(b);
				if (color == '_')	//make an empty space
					board[a][b] = new Tile(a, b);
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
	
	public boolean isSolved()
	{
		Tile tile;
		for (int a = 0; a < height; a++)
		{
			for (int b = 0; b < width; b++)
			{
				tile = board[a][b];
				
				if (!tile.isOccupied())
					return false;	//every tile must be occupied for a valid solution
				if (tile.isSource())
				{
					//TODO make sure one and only one adjacent tile matches the color
				}
				else
				{
					//TODO make sure exactly two adjacent tiles match the color
				}
			}
		}
		return true;
	}
	
	public int getHeight ()
	{
		return height;
	}
	public int getWidth ()
	{
		return width;
	}
}
