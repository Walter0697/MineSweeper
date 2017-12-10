import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.util.Random;
import java.io.*;

public class StartGame
{
	//set up menu and stopwatch
   private Menu m;
   private Stopwatch s;

   //set up the mine number
   private final int MINE = 9;
   
   //set up jframe and jpanel
   private JFrame frame;
   private JPanel panel;
   private int n;
   
   //set up the game board
   private JButton[][] button;
   private boolean[][] isClicked;
   private boolean[][] isFlaged;
   private int[][] table;
   
   //set up the size of the button
   private int SizeOfButton = 50;
   
   //set up the length of the game board
   private int lengthx = 9, lengthy = 9;
   private int Sizex = lengthx * SizeOfButton, Sizey = lengthy * SizeOfButton;
   private int numOfMine = 10;
   private int numOfFlag = 0;
   
   //set up the image 
   private ImageIcon FLAG;
   private ImageIcon LOSS;
   private ImageIcon BLACK;
   
   //For timing
   private boolean isStart = false;
   
   public StartGame()
   {
	   //set up everything
      s = new Stopwatch();
      frame = new JFrame();
      panel = new JPanel();
      
      frame.setTitle("MineSweeper");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(Sizey, Sizex);
      
      panel.setLayout(new GridLayout(lengthx, lengthy));
      
      button = new JButton[lengthx][lengthy];
      isClicked = new boolean[lengthx][lengthy];
      isFlaged = new boolean[lengthx][lengthy];
      table = new int[lengthx][lengthy];
      FLAG = new ImageIcon("CANADA.png");
      LOSS = new ImageIcon("LOSS.png");
      BLACK = new ImageIcon("BLACK.png");
         
      setUp();
      setMine();
      setTable();
      printTable();
      
      frame.add(panel);
      //frame.setVisible(true);
   }
   
   //change the gameboard if the player loss
   public void lossMenu()
   {
      for (int i = 0; i < button.length; i++)
      {
         for (int j = 0; j < button[i].length; j++)
         {
            if (!isClicked[i][j])
            {
               if (table[i][j] == 9 && !isFlaged[i][j])
               {
                  button[i][j].setIcon(BLACK);
               }
               else if (table[i][j] != 9 && isFlaged[i][j])
               {
                  button[i][j].setIcon(LOSS);
               }
            }
         }
      }
   }
   
   //setter for the menu
   public void setMenu(Menu m)
   {
      this.m = m;  
   }
   
   //change the flag to the input image
   public void changeFlag(ImageIcon image)
   {
      FLAG = image;
   }
   
   //change all the flag to the input image
   public void changeAllFlag(ImageIcon image)
   {
      changeFlag(image);
      for (int i = 0; i < button.length; i++)
      {
         for (int j = 0; j < button[i].length; j++)
         {
            if (isFlaged[i][j])
            {
               button[i][j].setIcon(image);
            }
         }
      }
   }
   
   //if the player decides to change the size of the game
   public void changeSize(int i, int j, int m, int size)
   {
      //Reset the basic setting
      lengthx = i;
      lengthy = j;
      SizeOfButton = size;
      Sizex = lengthx * SizeOfButton;
      Sizey = lengthy * SizeOfButton;
      frame.setSize(Sizey, Sizex);
      numOfMine = m;
      panel.removeAll();
      panel.setLayout(new GridLayout(lengthx, lengthy));
      
      JButton[][] newButton = new JButton[lengthx][lengthy];
      boolean[][] newClicked = new boolean[lengthx][lengthy];
      boolean[][] newFlaged = new boolean[lengthx][lengthy];
      int[][] newtable = new int[lengthx][lengthy];
               
      button = newButton;
      isClicked = newClicked;
      isFlaged = newFlaged;
      table = newtable;
      
      setUp();
      setMine();
      setTable();
      printTable();
      isStart = false;
   }
   
   //change the size of the game
   public void changeSize(int i, int j, int m)
   {
      changeSize(i, j, m, 50);
   }
   
   //set up the game
   public void setUp() 
   {
      for (int i = 0; i < button.length; i++)
      {
         for (int j = 0; j < button[i].length; j++)
         {
            button[i][j] = new JButton();
            button[i][j].setPreferredSize(new Dimension(SizeOfButton, SizeOfButton));
            //button[i][j].addActionListener(new ButtonListener());
            setMouseListener(i, j);
            isClicked[i][j] = false;
            isFlaged[i][j] = false;
            table[i][j] = 0;
            
            panel.add(button[i][j]);
         }
      }  
   }
   
   //restart the game
   public void reStart()
   {
      numOfFlag = 0;
      for (int i = 0; i < button.length; i++)
      {
         for (int j = 0; j < button[i].length; j++)
         {
            isClicked[i][j] = false;
            isFlaged[i][j] = false;
            table[i][j] = 0;
            button[i][j].setIcon(null);
            button[i][j].setEnabled(true);
            button[i][j].setText("");
         }
      }
      setMine();
      setTable();
      printTable();
      isStart = false;
   }
   
   //check if the player win
   public boolean checkWin()
   {
      for (int i = 0; i < button.length; i++)
      {
         for (int j = 0; j < button[i].length; j++)
         {
            if (table[i][j] == MINE)
            {
               if (isFlaged[i][j] == false)
               return false;
            }
            else
            {
               if (isFlaged[i][j] == true)
               return false;
            }
         }
      }
      return true;
   }
     
	// set up the mouse listener
   public void setMouseListener(int i, int j) 
   {
      button[i][j].addMouseListener(new MouseAdapter()
      {
         public void mouseClicked(MouseEvent e)
         {
            if (isStart == false)
            {
               isStart = true;
               s.start();
            }
            if (e.getButton() == 1)
            {
               if (!isFlaged[i][j])
               {
                  if (table[i][j] == MINE)
                  {
                     lossMenu();
                     JOptionPane.showMessageDialog(null, "YOU LOSS!");
                     reStart();
                  }
                  else
                  {
                     ButtonClicked(i, j);
                  }
               }
            }
            else if (e.getButton() == 3)
            {
               if (!isClicked[i][j])
               {
                  if (isFlaged[i][j])
                  {
                     isFlaged[i][j] = false;
                     button[i][j].setIcon(null);
                     numOfFlag--;
                  }
                  else 
                  {
                     if (numOfFlag < numOfMine)
                     {
                        isFlaged[i][j] = true;
                        button[i][j].setIcon(FLAG);
                        numOfFlag++;
                        if (checkWin())
                        {
                           s.stop();
                           JOptionPane.showMessageDialog(null, "You win!");
                           String name = JOptionPane.showInputDialog(null, "What is your name?");
                           m.addData(name, lengthx, lengthy, numOfMine, s.elapsedSeconds());
                           reStart();
                        }
                     }
                  }
               }
            }
         }
      }
      );
   }
   
   //set mine to the random position in the beginning
   public void setMine()
   {
      Random randomNumbers = new Random();

      int tempx;
      int tempy;
      for (int i = 0; i < numOfMine; i++)
      {
         tempx = randomNumbers.nextInt(table.length);
         tempy = randomNumbers.nextInt(table[0].length);
         System.out.println(tempx + " " + tempy);
         while (table[tempx][tempy] == MINE)
         {
            tempx = randomNumbers.nextInt(table.length);
            tempy = randomNumbers.nextInt(table[0].length);
         }
         table[tempx][tempy] = MINE;
      }
   }
   
   //set up the gameboard
   public void setTable()
   {
      for (int i = 0; i < table.length; i++)
      {
         for (int j = 0; j < table[i].length; j++)
         {
            SetNumber(i, j);
         }
      }
   }
   
   //set the number according to the mine nearby
   public void SetNumber(int x, int y)
   {
      int sum = 0;
      //top left
      if (x == 0 && y == 0)
      {
         if (table[1][0] == MINE) sum++;
         if (table[0][1] == MINE) sum++;
         if (table[1][1] == MINE) sum++;
      }
      //bottom right
      else if (x == table.length - 1 && y == table[0].length - 1)
      {
         if (table[table.length-2][table[0].length-1] == MINE) sum++;
         if (table[table.length-1][table[0].length-2] == MINE) sum++;
         if (table[table.length-2][table[0].length-2] == MINE) sum++;
      }
      //bottom left
      else if (x == 0 && y == table[0].length - 1)
      {
         if (table[1][table[0].length-1] == MINE) sum++;
         if (table[0][table[0].length-2] == MINE) sum++;
         if (table[1][table[0].length-2] == MINE) sum++;
      }
      //top right
      else if (x == table.length - 1 && y == 0)
      {
         if (table[table.length-2][0] == MINE) sum++;
         if (table[table.length-1][1] == MINE) sum++;
         if (table[table.length-2][1] == MINE) sum++;
      }
      //left
      else if (x == 0)
      {
         if (table[0][y-1] == MINE) sum++;
         if (table[0][y+1] == MINE) sum++;
         if (table[1][y] == MINE) sum++;
         if (table[1][y-1] == MINE) sum++;
         if (table[1][y+1] == MINE) sum++;
      }
      //right
      else if (x == table.length - 1)
      {
         if (table[table.length-1][y-1] == MINE) sum++;
         if (table[table.length-1][y+1] == MINE) sum++;
         if (table[table.length-2][y] == MINE) sum++;
         if (table[table.length-2][y-1] == MINE) sum++;
         if (table[table.length-2][y+1] == MINE) sum++;
      }
      //top
      else if (y == 0)
      {
         if (table[x-1][0] == MINE) sum++;
         if (table[x+1][0] == MINE) sum++;
         if (table[x][1] == MINE) sum++;
         if (table[x-1][1] == MINE) sum++;
         if (table[x+1][1] == MINE) sum++;
      }
      //bottom
      else if (y == table[0].length-1)
      {
         if (table[x-1][table[0].length-1] == MINE) sum++;
         if (table[x+1][table[0].length-1] == MINE) sum++;
         if (table[x][table[0].length-2] == MINE) sum++;
         if (table[x-1][table[0].length-2] == MINE) sum++;
         if (table[x+1][table[0].length-2] == MINE) sum++;
      }
      //center
      else 
      {
         if (table[x-1][y-1] == MINE) sum++;
         if (table[x][y-1] == MINE) sum++;
         if (table[x+1][y-1] == MINE) sum++;
         if (table[x-1][y] == MINE) sum++;
         if (table[x+1][y] == MINE) sum++;
         if (table[x-1][y+1] == MINE) sum++;
         if (table[x][y+1] == MINE) sum++;
         if (table[x+1][y+1] == MINE) sum++;
      }

      if (table[x][y] == MINE)
      {
         sum = MINE;
      }
      table[x][y] = sum;
   }
   
   //print out the table to the console
   public void printTable()
   {
      for (int i = 0; i < table.length; i++)
      {
         for (int j = 0; j < table[i].length; j++)
         {
            System.out.print("[" + table[i][j] + "]");
         }
         System.out.println();
      }
   }
   /*
   public class ButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         for (int i = 0; i < button.length; i++)
         {
            for (int j = 0; j < button.length; j++)
            {
                  if (e.getSource() == button[i][j])
                  {
                     if (table[i][j] == MINE)
                     {
                        JOptionPane.showMessageDialog(null, "YOU LOSS!");
                     }
                     else
                     {
                        ButtonClicked(i, j);
                     }
                  }
            }
         }
      }
   }
   */
   //set up for the button click
   public void ButtonClicked(int i, int j)
   {
      button[i][j].setEnabled(false);
      button[i][j].setText(table[i][j] == 0 ? "" : "" + table[i][j]);
      isClicked[i][j] = true;
      isFlaged[i][j] = false;
      button[i][j].setIcon(null);
      if (table[i][j] == 0)
      {
		  //check nine nearby position to see if that triggered
         if (i != 0 && j != 0) if (table[i-1][j-1] != MINE) if (!isClicked[i-1][j-1]) ButtonClicked(i-1, j-1);
         if (i != 0 && j != table[0].length-1) if (table[i-1][j+1] != MINE) if (!isClicked[i-1][j+1]) ButtonClicked(i-1, j+1);
         if (i != table.length-1 && j != 0) if (table[i+1][j-1] != MINE) if (!isClicked[i+1][j-1]) ButtonClicked(i+1, j-1);
         if (i != table.length-1 && j != table[0].length-1) if (table[i+1][j+1] != MINE) if (!isClicked[i+1][j+1]) ButtonClicked(i+1, j+1);
         if (i != 0) if (table[i-1][j] != MINE) if (!isClicked[i-1][j]) ButtonClicked(i-1, j);
         if (j != 0) if (table[i][j-1] != MINE) if (!isClicked[i][j-1]) ButtonClicked(i, j-1);
         if (i != table.length-1) if (table[i+1][j] != MINE) if (!isClicked[i+1][j]) ButtonClicked(i+1, j);
         if (j != table[0].length-1) if (table[i][j+1] != MINE) if (!isClicked[i][j+1]) ButtonClicked(i, j+1);
      }
   }
   
   public JFrame getFrame()
   {
      return frame;
   }
}