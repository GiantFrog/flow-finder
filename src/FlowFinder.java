import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

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
	
	public Gameboard stupidSearch (Gameboard state)
	{
		//TODO dumb backtracking search with no heuristics
		Stack<Tile> frontier = new Stack<>();
		ArrayList<Tile> closed = new ArrayList<>();
		
		for (int a = 0; a < state.getHeight(); a++)
		{
			for (int b = 0; b < state.getWidth(); b++)
			{
				if (state.board[a][b].isOccupied())		//add each tile adjacent to a source to the frontier
				{	//TODO also must be unoccupied; not on the frontier
					if (a+1 < state.getHeight())		//but only if it isn't off the edge of the board!
						frontier.add(state.board[a+1][b]);
					if (a-1 >= 0)
						frontier.add(state.board[a-1][b]);
					if (b+1 < state.getWidth())
						frontier.add(state.board[a][b+1]);
					if (b-1 >= 0)
						frontier.add(state.board[a][b-1]);
				}
			}
		}
		return state;
	}
	
	public Gameboard cleverSearch (Gameboard state)
	{
		//TODO smart search that uses forward checking, and maybe some other stuff to prune off bad branches
		return state;
	}
}
