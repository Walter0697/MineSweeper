import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class recordList extends JFrame
{
   JList display;
   ArrayList<MineNode> list = new ArrayList<MineNode>();
   ArrayList<String> allString = new ArrayList<String>();
   int type;
   
   public recordList(ArrayList<MineNode> arr, int type)
   {
      setTitle("more record");
      setSize(200, 300);
      
      display = new JList(allString.toArray());
      this.type = type;
      sorting(arr); 
      updateString();
      
      add(display);
      setVisible(false); 
   }
   
   public void sorting(ArrayList<MineNode> arr)
   {
      if (type != 4)
      {
         if (type == 1) sorting(arr, 9, 9, 10);
         else if (type == 2) sorting(arr, 16, 16, 40);
         else if (type == 3) sorting(arr, 16, 30, 99); 
      }
      else
      {
         ArrayList<MineNode> temp = new ArrayList<MineNode>();
         while (arr.size() != 0)
         {
            int j = -1;
            for (int i = 0; i < arr.size(); i++)
            {
               if (!(arr.get(i).sizex == 9 && arr.get(i).sizey == 9 && arr.get(i).numOfMine == 10))
                  if (!(arr.get(i).sizex == 16 && arr.get(i).sizey == 16 && arr.get(i).numOfMine == 40))
                     if (!(arr.get(i).sizex == 16 && arr.get(i).sizey == 30 && arr.get(i).numOfMine == 99))
                        {
                           if (j == -1) j = i;
                           else 
                           {
                              double averageOfH = ((double) arr.get(j).numOfMine / arr.get(j).realTime);
                              double averageOfC = ((double) arr.get(i).numOfMine / arr.get(i).realTime);
                              if (averageOfC < averageOfH) j = i;
                           }
                        }           
            }
            if (j == -1) break;
            temp.add(arr.remove(j));
         }
         list = temp;
      }
   }
   
   public void sorting(ArrayList<MineNode> arr, int x, int y, int num)
   {
      ArrayList<MineNode> temp = new ArrayList<MineNode>();
      while (arr.size() != 0)
      {
         int j = -1;
         for (int i = 0; i < arr.size(); i++)
         {
            if (arr.get(i).sizex == x && arr.get(i).sizey == y && arr.get(i).numOfMine == num)
            {
               if (j == -1)
                  j = i;
               else
               {
                  if (arr.get(j).realTime > arr.get(i).realTime)
                     j = i;
               }
            }
         }
         if (j == -1)
            break;
         temp.add(arr.remove(j));
      }
      list = temp;
   }
   
   public void updateString()
   {
      ArrayList<String> temp = new ArrayList<String>();
      for (int i = 0; i < list.size(); i++)
      {
         String temps = list.get(i).sizex + "x" + list.get(i).sizey + " : " + list.get(i).time + "s by " + list.get(i).name;
         temp.add(temps);
      }
      allString = temp;
      display.setListData(allString.toArray());
   }
}