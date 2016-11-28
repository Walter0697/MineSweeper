import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class Menu 
{
   private JFrame frame;
   private StartGame s;
   
   private JMenuBar menuBar;
   private JMenu setting;
   
   private JMenu size;
   private JCheckBoxMenuItem big, medium, small;
   private JCheckBoxMenuItem custome;
   
   private JMenuItem flag;
   private JMenuItem restart;
   
   private JMenu record;
   private JMenuItem pRecord;
   private JMenuItem clearRecord;
   
   private FlagChange flagFrame;
   private ChangeSize sizeFrame;
   private recordFrame recordF;
   
   public Menu(StartGame s) throws IOException
   {
      this.s = s;
      this.frame = s.getFrame();
            
      menuBar = new JMenuBar();
      
      //Setting
      setting = new JMenu("Setting");
      setting.setMnemonic(KeyEvent.VK_S);
      menuBar.add(setting);
      
      //record
      recordF = new recordFrame();
      try {recordF.readFile();} catch(IOException ex) {}
      
      record = new JMenu("Record");
      record.setMnemonic(KeyEvent.VK_R);
      pRecord = new JMenuItem("Personal Record");
      pRecord.addActionListener(new ButtonListener());
      record.add(pRecord);
      clearRecord = new JMenuItem("Clear Record");
      clearRecord.addActionListener(new ButtonListener());
      record.add(clearRecord);
      menuBar.add(record);
      
      recordF.updateText();
   
      //size Option
      size = new JMenu("Size");
      big = new JCheckBoxMenuItem("BIG");
      medium = new JCheckBoxMenuItem("MediuM");
      small = new JCheckBoxMenuItem("small");
      custome = new JCheckBoxMenuItem("CuStOmE");
      ButtonGroup sizeGroup = new ButtonGroup();
      sizeGroup.add(big);
      sizeGroup.add(medium);
      sizeGroup.add(small);
      sizeGroup.add(custome);
      sizeGroup.setSelected(small.getModel(), true);
      //adding Listener
      MenuListener m = new MenuListener();      
      big.addItemListener(m);
      medium.addItemListener(m);
      small.addItemListener(m);
      custome.addItemListener(m);
      //adding it into menu
      size.add(big);
      size.add(medium);
      size.add(small);   
      size.add(custome);
      setting.add(size);
      
      //size Option
      sizeFrame = new ChangeSize(this);
      
      //flag Option
      flag = new JMenuItem("Flag");
      flag.addActionListener(new ButtonListener());
      setting.add(flag);
      flagFrame = new FlagChange(this);
      
      //restart Option
      restart = new JMenuItem("Restart");
      restart.addActionListener(new ButtonListener());
      setting.add(restart);
      
      //setMenuBar
      frame.setJMenuBar(menuBar);
      
      //setVisible
      this.frame.setVisible(true);
   }
   
   public class MenuListener implements ItemListener
   {
      public void itemStateChanged(ItemEvent e)
      {
         if (e.getStateChange() == ItemEvent.SELECTED)
         {
            if (e.getItem() == big)
            {
               s.changeSize(16, 30, 99, 45);
            }
            else if (e.getItem() == medium)
            {
               s.changeSize(16, 16, 40, 50);
            }
            else if (e.getItem() == small)
            {
               s.changeSize(9, 9, 10, 50);
            }
            else if (e.getItem() == custome)
            {
               sizeFrame.setVisible(true);
            }
         }
      }
   }
   
   public void changeSize(int x, int y, int m)
   {
      if (x > 30 || y > 30)
      {
         s.changeSize(x, y, m, 45);
      }
      else
      {
         s.changeSize(x, y, m, 50);
      }
   }
   
   public class ButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         if (e.getSource() == flag)
         {
            flagFrame.setVisible(true);
         }
         else if (e.getSource() == restart)
         {
            s.reStart();
         }
         else if (e.getSource() == pRecord)
         {
            recordF.setVisible(true);
         }
         else if (e.getSource() == clearRecord)
         {
            int reply = JOptionPane.showConfirmDialog(null, "Do you want to clear the record?", "Are you sure?", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION)
            {
               try { recordF.clearFile(); } catch (IOException ex) {}
            }
         }
      }
   }
   
   public void addData(String n, int x, int y, int m, double t)
   {
      recordF.addNode(n, x, y, m, t);
   }
   
   public void changeFlag(ImageIcon image)
   {
      s.changeFlag(image);
   }
   
   public void changeAllFlag(ImageIcon image)
   {
      s.changeAllFlag(image);
   }
}