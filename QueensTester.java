/*
 * Name: Justin Senia
 * E-Number: E00851822
 * Date: 10/6/2017
 * Class: COSC 461
 * Project: #1
 */
import java.io.*;
import java.util.*;

public class QueensTester
{

    //Main method for testing
    public static void main(String[] args) throws IOException
    {

      //creating buffered reader for getting user input
      java.io.BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));

      //message welcoming to the program/giving instructions
      System.out.println("*****************************************");
      System.out.println("*    Welcome to the Queens program      *");
      System.out.println("*****************************************");
      System.out.println("*  Please enter in a filename to start  *");
      System.out.println("* or type quit to terminate the program *");
      System.out.println("*****************************************");

      //start a loop that continues querying for input as long as user
      //does not enter "quit" (without the quotes)
      while (true)
      {
        System.out.print("Please make your entry now: ");
        String userIn = ""; //used for file entry and/or quitting

        userIn = keyIn.readLine(); //reading user input

        if (userIn.equalsIgnoreCase("quit")) //if user typed quit, stops program
          break;
        else{
              try
              {
                //establishing working directory for file I/O
                String currentDir = System.getProperty("user.dir");
                File fIn = new File(currentDir + '\\' + userIn);

                //using scanner with new input file based on user input
                Scanner scanIn = new Scanner(fIn);

                //creating printwriter for file output
                File fOut = new File("output_" + userIn);
                PrintWriter PWOut = new PrintWriter(fOut, "UTF-8");

                //scanning external file for Board dimensions
                int bRows = scanIn.nextInt();
                int bCols = scanIn.nextInt();

                //Outputting formatted messages with read info
                System.out.println("Output for " + userIn);
                System.out.println("Rows: " + bRows + "  Cols: " + bCols);

                PWOut.println("Output for " + userIn);
                PWOut.println("Rows: " + bRows + "  Cols: " + bCols);

                //creating new queens object, with dimensions and a PrintWriter object
                //passed as parameters
                Queens q = new Queens(bRows, bCols, PWOut);

                //starting solving method for Queens class object
                q.solve();

                //closing I/O objects
                scanIn.close();
                PWOut.close();
                //keyIn.close();
              }
              catch (IOException e)
              {
                ;//catch statement in case of file I/) problems
              }
            }
      }
    }
}
