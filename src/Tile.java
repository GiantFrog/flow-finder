import java.util.ArrayList;

public class Tile
{
	private boolean source;
	private char color;
	private int x, y, adjacentTiles;
	private ArrayList<Character> possibleColors;
	
	public Tile (int x, int y, ArrayList<Character> usedCharacters)
	{
		this.x = x;
		this.y = y;
		color = '_';
		source = false;
		possibleColors = new ArrayList<>(usedCharacters);
	}
	
	public Tile (int x, int y, char color)	//a new tile with a color is always a source space
	{
		this.x = x;
		this.y = y;
		this.color = color;
		source = true;
		possibleColors = new ArrayList<>();
	}
	
	public Tile (Tile toCopy)
	{
		x = toCopy.getX();
		y = toCopy.getY();
		color = toCopy.getColor();
		source = toCopy.isSource();
		possibleColors = new ArrayList<>(toCopy.getPossibleColors());
	}
	
	public boolean isOccupied ()	//true if the color isn't blank.
	{
		return color != '_';
	}
	
	public boolean isSource ()
	{
		return source;
	}
	public char getColor ()
	{
		return color;
	}
	public int getX ()
	{
		return x;
	}
	public int getY ()
	{
		return y;
	}
	public int getAdjacentTiles()
	{
		return adjacentTiles;
	}
	public void setColor (char color)
	{
		this.color = color;
	}
	public ArrayList<Character> getPossibleColors()
	{
		return possibleColors;
	}
}
