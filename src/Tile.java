import java.util.ArrayList;

public class Tile
{
	private boolean source;
	private char color;
	private int x, y, emptyAdjacentTiles;
	private ArrayList<Character> possibleColors;
	
	public Tile (int x, int y)
	{
		this.x = x;
		this.y = y;
		color = '_';
		source = false;
		possibleColors = new ArrayList<>();
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
		possibleColors = new ArrayList<>();
		possibleColors.addAll(toCopy.getPossibleColors());
		this.emptyAdjacentTiles = toCopy.getEmptyAdjacentTiles();
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
	public int getEmptyAdjacentTiles()
	{
		return emptyAdjacentTiles;
	}
	public void resetEmptyAdjacentTiles()
	{
		emptyAdjacentTiles = 0;
	}
	public void addEmptyAdjacentTile()
	{
		emptyAdjacentTiles++;
	}
	public void setColor (char color)
	{
		this.color = color;
	}
	public ArrayList<Character> getPossibleColors()
	{
		return possibleColors;
	}
	public void setPossibleColors (ArrayList<Character> usedColors)
	{
		possibleColors.addAll(usedColors);
	}
	public void removePossibleColor (char color)
	{
		possibleColors.remove(color);
	}
}
