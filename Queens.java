/*
 * Name: Justin Senia
 * E-Number: E00851822
 * Date: 10/6/2017
 * Class: COSC 461
 * Project: #1
 */
import java.util.LinkedList;
import java.io.*;
import java.util.*;

//This program solves n-queens problem
public class Queens
{
  //board class (inner class)
  private class Board{
    private char[][] array;  //array
    private int rows;        //filled rows

    //constructor of board class
    private Board(int bRows, int bCols)
    {
      array = new char[bRows][bCols]; //create array based on desired size
      for (int i = 0; i < bRows; i++)
        for (int j = 0; j < bCols; j++)
          array[i][j] = ' ';              //initialize array to blanks

          rows = 0;                       //zero filled rows
    }
  }

  //Declaring private variables for size and file output
  private int boardRows;
  private int boardCols;
  private PrintWriter pW;

  //constructor of queens class
  public Queens(int bRows, int bCols, PrintWriter PWOut)
  {
    this.boardRows = bRows;
    this.boardCols = bCols;
    this.pW = PWOut;
  }

  //method solves queen problem
  public void solve()
  {
    LinkedList<Board> openList = new LinkedList<Board>(); //tracks unused but discovered states
    LinkedList<Board> closedList = new LinkedList<Board>(); //tracks already searched states
    LinkedList<Board> solutionList = new LinkedList<Board>(); //stores solutions

    int solutionNum = 0; //stores # of solutions
    Board randSolutionBoard = new Board(boardRows, boardCols); //finds random solution
    Random rnd = new Random(System.currentTimeMillis()); //new random variable

    Board initialBoard = new Board(boardRows, boardCols); //creating blank board

    openList.addFirst(initialBoard); //adding blank board to openlist

    while (!openList.isEmpty()) //continues as long as there are states left to search
    {
      Board board = openList.removeFirst(); //remove from open list

      closedList.addLast(board); //add removed item to closed list

      //if board is complete and correct then store solution in solutionList
      //also takes first solution, and sets to random board, but after that
      //has a random chance of updating in order to get a random solution
      //each time
      if (complete(board))
      {

        if (solutionNum == 0)
        {
          randSolutionBoard = copy(board);
        }
        else if (solutionNum > 0)
        {
          rnd.setSeed(System.currentTimeMillis());
          if (rnd.nextInt(2) == 1)
          {
            randSolutionBoard = copy(board);
          }
        }

        //adds new solution to solutionList and then continues to look for more solutions
        solutionList.addLast(board);
        solutionNum = solutionNum + 1;

        //file output of solution information
        pW.println("Total solutions: " + solutionNum + " Total stored sols: " + solutionList.size());
      }
      else //if last state searched was not a solution, generate children to look for more solutions
      {
        LinkedList<Board> children = generate(board); //generate children and passed to linked list

        //pops children off LL iteratively (linked list), checks if they already exist in openlist or closedList
        //if they do not, add to open list, otherwise skip
        for (int i = 0; i < children.size(); i++)
        {
          Board child = children.get(i);

          if (!exists(child, openList) && !exists(child, closedList))
            openList.addLast(child);
        }
      }
    }


    //Prints formatted data for all solutions to display and also to external file
    System.out.println("*********************************************");
    System.out.println("*                All Solutions              *");
    System.out.println("*********************************************");

    pW.println("*********************************************");
    pW.println("*                All Solutions              *");
    pW.println("*********************************************");

    displaySolutionList(solutionList);


    //Prints formatted data for the rest of the data to display and also to external file
    System.out.println("\n");
    System.out.print("the number of solutions for a " + boardRows + " x ");
    System.out.println(boardCols + " board are: " + solutionNum);
    System.out.println("*********************************************");
    System.out.println("*               Random Solution             *");
    System.out.println("*********************************************");

    pW.println("\n");
    pW.print("the number of solutions for a " + boardRows + " x ");
    pW.println(boardCols + " board are: " + solutionNum);
    pW.println("*********************************************");
    pW.println("*               Random Solution             *");
    pW.println("*********************************************");

    display(randSolutionBoard);

    System.out.println("");
    pW.println("");

  }

  //Method generates children of a board
  private LinkedList<Board> generate(Board board)
  {
    LinkedList<Board> children = new LinkedList<Board>(); //List to hold children

    for (int i = 0; i < boardCols; i ++)
    {
      Board child = copy(board); //child inherits previous data from passed board

      child.array[child.rows][i] = 'Q'; //tries adding queen to board

      child.rows += 1; //increments rows in board to guide pointer

      if (check(child, child.rows-1, i)) //if child has no conflict, put in children list
        children.addLast(child);
      }

      return children; //return children list for processing
    }

  //Method checks whether queen at a given location causes conflict
  private boolean check(Board board, int x, int y)
  {
    for (int i = 0; i < boardRows; i++)
      for (int j = 0; j < boardCols; j++)
      {
        if (board.array[i][j] == ' ') //go through all locations on board
          ;
        else if (x == i && y == j) //if same location ignore
          ;
        else if (x == i || y == j || x+y == i+j || x-y == i-j)
          return false;      //queen in same row, column, or diagonal causes conflict
      }

      return true; //no conflicts
  }

  //method checks whether board is complete
  private boolean complete(Board board)
  {
    //check number filled rows equals board size
    return (board.rows == boardRows);
  }

  //Method makes copy of board
  private Board copy(Board board)
  {
    Board result = new Board(boardRows, boardCols);

    for (int i = 0; i < boardRows; i++)
      for (int j = 0; j < boardCols; j++)
        result.array[i][j] = board.array[i][j];

    result.rows = board.rows;

    return result;
  }

  //Method decides whether a board exists in a list
  private boolean exists(Board board, LinkedList<Board> list)
  {
    for (int i = 0; i < list.size(); i++)
      if (identical(board, list.get(i)))
        return true;

    return false;
  }

  //Method decides whether two boards are identical
  private boolean identical(Board p, Board q)
  {
    for (int i = 0; i < boardRows; i++)
      for (int j = 0; j < boardCols; j++)
        if (p.array[i][j] != q.array[i][j])
          return false;

    return true;
  }

  //Display full solution list and associated information
  //formatted for both  display and external file
  private void displaySolutionList(LinkedList<Board> dispList)
  {
    pW.println(dispList.size());
    for (int i = 0; i < dispList.size(); i++)
    {
      System.out.println("");
      System.out.println("**************************************");
      System.out.print("* Solution #" + (i+1) + " for a " + boardRows);
      System.out.println(" x " + boardCols + " board");
      System.out.println("**************************************");

      pW.println("");
      pW.println("**************************************");
      pW.print("* Solution #" + (i+1) + " for a " + boardRows);
      pW.println(" x " + boardCols + " board");
      pW.println("**************************************");

      Board board = dispList.get(i);
      display(board);
    }
  }

  //Method displays boards properly formatted on both display and external file
  private void display(Board board)
  {
    for (int j = 0; j < boardCols; j++)
    {
      System.out.print("----");
      pW.print("----");
    }

      System.out.println();
      pW.println();

      for (int i = 0; i < boardRows; i++)
      {
        System.out.print("|");
        pW.print("|");

        for (int j = 0; j < boardCols; j++)
        {
          System.out.print(" " + board.array[i][j] + " |");
          pW.print(" " + board.array[i][j] + " |");
        }

        System.out.println();
        pW.println();

        for (int j = 0; j < boardCols; j++)
        {
          System.out.print("----");
          pW.print("----");
        }

        System.out.println();
        pW.println();
      }
  }
}
