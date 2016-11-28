import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.io.*;

public class Game
{
   public static void main (String[] args) throws IOException
   {
      StartGame s = new StartGame();
      Menu m = new Menu(s);
      s.setMenu(m);
   }
}