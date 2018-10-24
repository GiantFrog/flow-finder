import java.util.ArrayList;

public class Gameboard
{
	Tile[][] board;
	ArrayList<Character> usedColors, fullColors;
	
	public Gameboard (ArrayList<String> text) throws Exception
	{
		board = new Tile[text.get(0).length()][text.size()];
		usedColors = new ArrayList<>();
		fullColors = new ArrayList<>();
		
		//scan through all the input map text and create the initial gameboard.
		int b = 0;
		for (String line : text)
		{
			for (int a = 0; a < line.length(); a++)
			{
				char color = line.charAt(a);
				if (color == '_')	//make an empty space
					board[a][b] = new Tile();
				else						//make a space with the appropriate color
				{
					if (fullColors.contains(color))
						throw new Exception("There cannot be three of the same color!");
					else if (usedColors.contains(color))
						fullColors.add(color);
					else
						usedColors.add(color);
					board[a][b] = new Tile(color);
				}
			}
			b++;
		}
		
		//make sure every color has been used twice
		for (char color : usedColors)
		{
			if (!fullColors.contains(color))
				throw new Exception("Evey color needs two spaces!");
		}
	}
}
