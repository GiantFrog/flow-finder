import java.util.ArrayList;

public class Gameboard
{
	Tile[][] board;
	public Gameboard (int length, int height)
	{
		board = new Tile[length][height];
	}
	public Gameboard (ArrayList<String> text)
	{
		board = new Tile[text.get(0).length()][text.size()];
		
		for (String line : text)
		{
			for (char c : line)
			{
				//damn
			}
		}
	}
}
