import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

public class ChangeSize extends JFrame
{
   private Menu m;
   private JPanel buttonPanel;
   private JButton exit, apply;
   
   private JTextField sizex;
   private JTextField sizey;
   private JTextField mineNum;
   
   private JLabel labelx;
   private JLabel labely;
   private JLabel labelNum;
   
   private int numX = 1;
   private int numY = 1;
   private int numMine = 1;
   
   private JPanel typePanel;
   
   public ChangeSize(Menu m)
   {
      setTitle("Change Size");
      setSize(300, 400);
      setLayout(new BorderLayout());
      
      buttonPanel = new JPanel();
      buttonPanel.setLayout(new GridLayout(1, 2));
      buttonPanel.setPreferredSize(new Dimension(300, 50));
      exit = new JButton("EXIT");
      apply = new JButton("APPLY");
      exit.addActionListener(new ExitListener());
      apply.addActionListener(new ApplyListener());
      buttonPanel.add(apply);
      buttonPanel.add(exit);
      add(buttonPanel, BorderLayout.SOUTH);
      
      sizex = new JTextField(3);
      sizey = new JTextField(3);
      mineNum = new JTextField(3);
      labelx = new JLabel("Width:");
      labely = new JLabel("Height:");
      labelNum = new JLabel("Number of Mine:");
      
      typePanel = new JPanel();
      typePanel.setLayout(new GridLayout(6, 1));
      typePanel.add(labelx);
      typePanel.add(sizex);
      typePanel.add(labely);
      typePanel.add(sizey);
      typePanel.add(labelNum);
      typePanel.add(mineNum);
      typePanel.setBorder(BorderFactory.createTitledBorder("Change the size:"));
      
      add(typePanel, BorderLayout.CENTER);
      
      this.m = m;
      
      setVisible(false);
   }
   
   public class ExitListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         if (e.getSource() == exit)
         {
            setVisible(false);
         }
      }
   }
   
   public class ApplyListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         if (e.getSource() == apply)
         {
            int tempx = Integer.parseInt(sizex.getText());
            int tempy = Integer.parseInt(sizey.getText());
            int tempNum = Integer.parseInt(mineNum.getText());
            if (tempx <= 1 || tempx > 99)
            {
               JOptionPane.showMessageDialog(null, "Invalid size value!");
            }
            else if (tempy <= 1 || tempx > 99)
            {
               JOptionPane.showMessageDialog(null, "Invalid size value!");
            }
            else if (tempx * tempy < tempNum)
            {
               JOptionPane.showMessageDialog(null, "Invalid Mine Number");
            }
            else
            {  
               m.changeSize(tempx, tempy, tempNum);
            }
         }
      }   
   }
}