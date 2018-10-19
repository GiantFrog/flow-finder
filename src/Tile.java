public class Tile
{
	boolean endSpace, occupied;
	char color;
	
	public Tile()
	{
		endSpace = false;
		occupied = false;
	}
	public Tile (char color)	//a new tile with a color is always an end space and always occupied
	{
		this.color = color;
		endSpace = true;
		occupied = true;
	}
}
