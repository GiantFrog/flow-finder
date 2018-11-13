import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;

public class FlowFinder
{
	public static void main (String[] args)
	{
		File file;
		ArrayList<String> fileContents = new ArrayList<>();
		Scanner scanThatFile;
		Gameboard startingBoard= null;
		
		//let's find a file to load!
		if (args.length < 1)
		{
			System.out.println("Please name the file to load.");
			Scanner in = new Scanner(System.in);
			file = new File(in.nextLine());
			in.close();
		}
		else
			file = new File(args[0]);
		
		//now try to actually load it
		try
		{
			scanThatFile = new Scanner(file);
			
			while (scanThatFile.hasNextLine())
				fileContents.add(scanThatFile.nextLine());
			scanThatFile.close();
		}
		catch (FileNotFoundException eh)
		{
			System.out.println("That file could not be found!");
			exit(1);
		}
		
		try
		{
			//making the board will validate that every color in the input file is used exactly twice
			startingBoard = new Gameboard(fileContents);
		}
		catch (Exception badInput)
		{
			System.out.println(badInput);
			exit(1);
		}
		System.out.println("Gameboard loaded!");
	}
	
	public boolean isSolved (Gameboard solution)
	{
		Tile tile;
		for (int a = 0; a < solution.getHeight(); a++)
		{
			for (int b = 0; b < solution.getWidth(); b++)
			{
				tile = solution.board[a][b];
				
				if (!tile.isOccupied())
					return false;	//every tile must be occupied
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
	
	public Gameboard stupidSearch (Gameboard startingBoard)
	{
		//TODO dumb backtracking search with no heuristics
		return startingBoard;
	}
	
	public Gameboard cleverSearch (Gameboard startingBoard)
	{
		//TODO smart search that uses forward checking, and maybe some other stuff to prune off bad branches
		return startingBoard;
	}
}
