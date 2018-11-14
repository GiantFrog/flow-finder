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
		Gameboard stupidSolution, cleverSolution, startingBoard = null;
		
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
		
		StupidSearch notGreat = new StupidSearch(startingBoard);
		stupidSolution = notGreat.solve();
		System.out.println("It took " + notGreat.getAttemptedSolutions() + " tries to find the solution.");
		
		for (int a = 0; a < stupidSolution.getHeight(); a++)
		{
			for (int b = 0; b < stupidSolution.getWidth(); b++)
			{
				System.out.print(stupidSolution.board[a][b].getColor());
			}
			System.out.println();
		}
	}
	
	
	public Gameboard cleverSearch (Gameboard state)
	{
		//TODO smart search that uses forward checking, and maybe some other stuff to prune off bad branches
		return state;
	}
}
