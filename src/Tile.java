public class Tile
{
	private boolean source;
	private char color;
	private int x, y;
	
	public Tile (int x, int y)
	{
		this.x = x;
		this.y = y;
		color = '_';
		source = false;
	}
	public Tile (int x, int y, char color)	//a new tile with a color is always a source space
	{
		this.x = x;
		this.y = y;
		this.color = color;
		source = true;
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
}
