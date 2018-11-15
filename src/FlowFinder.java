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
		long startTime = System.currentTimeMillis();
		stupidSolution = notGreat.solve();
		long stopTime = System.currentTimeMillis();
		System.out.println("Finished StupidSearch in " + (stopTime-startTime) + "ms!");
		System.out.println("It took " + notGreat.getAttemptedSolutions() + " tries to find the solution.");
		System.out.println(notGreat.getAssignmentsMade() + " color assignments were made to get here.");
		System.out.println("We detected " + notGreat.getDeadEnds() + " dead end branches along the way.");
		
		for (int a = 0; a < stupidSolution.getHeight(); a++)
		{
			for (int b = 0; b < stupidSolution.getWidth(); b++)
			{
				System.out.print(stupidSolution.board[a][b].getColor());
			}
			System.out.println();
		}
		
		//try to free up some memory real quick
		notGreat = null;
		System.gc();
		System.out.println();
		
		CleverSearch prettyGreat = new CleverSearch(startingBoard);
		startTime = System.currentTimeMillis();
		cleverSolution = prettyGreat.solve();
		stopTime = System.currentTimeMillis();
		System.out.println("Finished CleverSearch in " + (stopTime-startTime) + "ms!");
		System.out.println("It took " + prettyGreat.getAttemptedSolutions() + " tries to find the solution.");
		System.out.println(prettyGreat.getAssignmentsMade() + " color assignments were made to get here.");
		System.out.println("We detected " + prettyGreat.getDeadEnds() + " dead end branches along the way.");
		
		for (int a = 0; a < cleverSolution.getHeight(); a++)
		{
			for (int b = 0; b < cleverSolution.getWidth(); b++)
			{
				System.out.print(cleverSolution.board[a][b].getColor());
			}
			System.out.println();
		}
	}
}
