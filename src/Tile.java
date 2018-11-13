public class Tile
{
	private boolean source;
	private char color;
	
	public Tile()
	{
		color = '_';
		source = false;
	}
	public Tile (char color)	//a new tile with a color is always a source space
	{
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
}
