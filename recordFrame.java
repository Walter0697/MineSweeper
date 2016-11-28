import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class recordFrame extends JFrame
{
   recordList smallList;
   recordList middleList;
   recordList bigList;
   recordList customeList;

   JPanel buttonPanel;
   JButton exit;
   ArrayList<MineNode> list = new ArrayList<MineNode>();
   
   JButton smallButton;
   JButton middleButton;
   JButton bigButton;
   JButton customeButton;
   
   JLabel smallLabel;
   JLabel middleLabel;
   JLabel bigLabel;
   JLabel customeLabel;
   
   JPanel smallPanel;
   JPanel middlePanel;
   JPanel bigPanel;
   JPanel customePanel;
   
   JPanel recordPanel;
   
   String NoRecord = " No record yet ";
   
   public recordFrame()
   {
      setTitle("Personal Record");
      setSize(300, 400);
      setLayout(new BorderLayout());
      
      exit = new JButton("EXIT");
      exit.addActionListener(new buttonListener());
      exit.setPreferredSize(new Dimension(300, 50));
      add(exit, BorderLayout.SOUTH);
      
      try { readFile(); } catch(IOException ex) {}
      
      smallList = new recordList(list, 1);
      middleList = new recordList(list, 2);
      bigList = new recordList(list, 3);
      customeList = new recordList(list, 4);
      
      smallPanel = new JPanel();
      middlePanel = new JPanel();
      bigPanel = new JPanel();
      customePanel = new JPanel();
      
      smallPanel.setBorder(BorderFactory.createTitledBorder("Best of 9x9:"));
      middlePanel.setBorder(BorderFactory.createTitledBorder("Best of 16x16:"));
      bigPanel.setBorder(BorderFactory.createTitledBorder("Best of 16x30:"));
      customePanel.setBorder(BorderFactory.createTitledBorder("Best of Custome:"));
      
      smallPanel.setLayout(new BorderLayout());
      middlePanel.setLayout(new BorderLayout());
      bigPanel.setLayout(new BorderLayout());
      customePanel.setLayout(new BorderLayout());
      
      smallLabel = new JLabel();
      middleLabel = new JLabel();
      bigLabel = new JLabel();
      customeLabel = new JLabel();
      
      smallLabel.setText(NoRecord);
      middleLabel.setText(NoRecord);
      bigLabel.setText(NoRecord);
      customeLabel.setText(NoRecord);
      
      smallButton = new JButton("more");
      middleButton = new JButton("more");
      bigButton = new JButton("more");
      customeButton = new JButton("more");
      
      smallButton.addActionListener(new moreListener());
      middleButton.addActionListener(new moreListener());
      bigButton.addActionListener(new moreListener());
      customeButton.addActionListener(new moreListener());
      
      setLabelLikeButton(smallButton);
      setLabelLikeButton(middleButton);
      setLabelLikeButton(bigButton);
      setLabelLikeButton(customeButton);
      
      smallPanel.add(smallLabel, BorderLayout.CENTER);
      middlePanel.add(middleLabel, BorderLayout.CENTER);
      bigPanel.add(bigLabel, BorderLayout.CENTER);
      customePanel.add(customeLabel, BorderLayout.CENTER);
      
      smallPanel.add(smallButton, BorderLayout.EAST);
      middlePanel.add(middleButton, BorderLayout.EAST);
      bigPanel.add(bigButton, BorderLayout.EAST);
      customePanel.add(customeButton, BorderLayout.EAST);
      
      recordPanel = new JPanel();
      recordPanel.setLayout(new GridLayout(4, 1));
      recordPanel.add(smallPanel);
      recordPanel.add(middlePanel);
      recordPanel.add(bigPanel);
      recordPanel.add(customePanel);
      add(recordPanel, BorderLayout.CENTER);
      
      setVisible(false);
   }
   
   public void updateText()
   {
      //list = bubbleSort(list);
      MineNode node = findRecord(1);
      smallLabel.setText(node == null ? NoRecord : NodeToString(node));
      node = findRecord(2);
      middleLabel.setText(node == null ? NoRecord : NodeToString(node));
      node = findRecord(3);
      bigLabel.setText(node == null ? NoRecord : NodeToString(node));
      node = findRecord(4);
      customeLabel.setText(node == null ? NoRecord : NodeToString(node));
   }
   
   public ArrayList<MineNode> bubbleSort(ArrayList<MineNode> mine)
   {
      MineNode temp;
      for (int i = 0; i < mine.size()-1; i++)
      {
         for (int j = 1; j < mine.size()-i; j++)
         {
            if (mine.get(j-1).time > mine.get(j).time)
            {
               temp = mine.get(j-1);
               mine.set(j, temp);
               mine.set(j-1, mine.get(j));
            }
         }
      }
      return mine;
   }
   
   
   
   public MineNode findRecord(int type)
   {
      if (type != 4)
      {
         if (type == 1)
            return findRecord(9, 9, 10);
         else if (type == 2)
            return findRecord(16, 16, 40);
         else if (type == 3)
            return findRecord(16, 30, 99);
      }
      else
      {
         MineNode highest = null;
         for (int i = 0; i < list.size(); i++)
         {
            if (!(list.get(i).sizex == 9 && list.get(i).sizey == 9 && list.get(i).numOfMine == 10))
               if (!(list.get(i).sizex == 16 && list.get(i).sizey == 16 && list.get(i).numOfMine == 40))
                  if (!(list.get(i).sizex == 16 && list.get(i).sizey == 30 && list.get(i).numOfMine == 99))
                     if (highest == null)
                     {
                        highest = list.get(i);
                     }
                     else
                     {
                        double averageOfH = ((double) highest.numOfMine / highest.realTime);
                        double averageOfC = ((double) list.get(i).numOfMine / list.get(i).realTime);
                        if (averageOfC < averageOfH)
                           highest = list.get(i);
                     }
         }
         return highest;         
      }
      return null;
   }
   
   public MineNode findRecord(int x, int y, int num)
   {
      MineNode highest = null;
      for (int i = 0; i < list.size(); i++)
      {
         if (list.get(i).sizex == x && list.get(i).sizey == y && list.get(i).numOfMine == num)
         {
            if (highest == null)
            {
               highest = list.get(i);
            }
            else
            {
               if (list.get(i).realTime < highest.realTime)
               {
                  highest = list.get(i);
               }
            }
         }
      }
      return highest;
   }
   
   public String NodeToString(MineNode node)
   {
      if (node.sizex == 9 && node.sizey == 9 && node.numOfMine == 10)
      {
         return "Best of small : " + node.time +"s by " + node.name;
      }
      if (node.sizex == 16 && node.sizey == 16 && node.numOfMine == 40)
      {
         return "Best of middle : " + node.time +"s by " + node.name;
      }
      if (node.sizex == 16 && node.sizey == 30 && node.numOfMine == 99)
      {
         return "Best of big : " + node.time +"s by " + node.name;
      }
      return "" + node.sizex + "x" + node.sizey + " (Mines : " + 
             node.numOfMine + ") : " + node.time +"s by " + node.name;
   }
   
   public void addNode(String n, int x, int y, int m, double t) 
   {
      list.add(new MineNode(n, x, y, m, t));
      try
      {
         writeFile();
      }
      catch(IOException ex)
      {}
      updateText();
      updateList();
   }
   
   public void updateList()
   {
      smallList.sorting(list);
      middleList.sorting(list);
      bigList.sorting(list);
      customeList.sorting(list);
      
      smallList.updateString();
      middleList.updateString();
      bigList.updateString();
      customeList.updateString();
   }
   
   public void writeFile() throws IOException
   {
      PrintWriter outfile;
      outfile = new PrintWriter(new FileWriter("DONOTOPEN.txt"));  
      for (int i = 0; i < list.size(); i++)
      {
         outfile.println(list.get(i).name);
         outfile.println(list.get(i).sizex);
         outfile.println(list.get(i).sizey);
         outfile.println(list.get(i).numOfMine);
         outfile.println(list.get(i).realTime);
      } 
      outfile.close();
   }
   
   public void clearFile() throws IOException
   {
      PrintWriter outfile;
      outfile = new PrintWriter(new FileWriter("DONOTOPEN.txt"));
      outfile.close();
      updateText();
      updateList();
   }
   
   public void readFile() throws IOException
   {
      ArrayList<MineNode> temp = new ArrayList<MineNode>();
      BufferedReader reader;
      reader = new BufferedReader(new FileReader("DONOTOPEN.txt"));
      for (String line = reader.readLine(); line != null; line = reader.readLine())
      {
         MineNode mine = new MineNode();
         mine.name = line;
         line = reader.readLine();
         mine.sizex = Integer.parseInt(line);
         line = reader.readLine();
         mine.sizey = Integer.parseInt(line);
         line = reader.readLine();
         mine.numOfMine = Integer.parseInt(line);
         line = reader.readLine();
         mine.realTime = Double.parseDouble(line);
         mine.time = mine.roundUp(mine.realTime);
         temp.add(mine);
      }
      list = temp;
   }
   
   public void setLabelLikeButton(JButton button)
   {
      button.setFocusPainted(false);
      button.setMargin(new Insets(0, 0, 0, 0));
      button.setContentAreaFilled(false);
      button.setBorderPainted(false);
      button.setOpaque(false);
   }
   
   public class buttonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         if (e.getSource() == exit)
         {
            setVisible(false);
         }
      }
   }
   
   public class moreListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         if (e.getSource() == smallButton)
         {
            smallList.setVisible(true);
         }
         else if (e.getSource() == middleButton)
         {
            middleList.setVisible(true);
         }
         else if (e.getSource() == bigButton)
         {
            bigList.setVisible(true);
         }
         else if (e.getSource() == customeButton)
         {
            customeList.setVisible(true);
         }
      }
   }
}