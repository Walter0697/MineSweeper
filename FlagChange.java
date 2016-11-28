import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

public class FlagChange extends JFrame
{
   private Menu m;
   
   private JPanel panel;
      
   private JPanel buttonPanel;
   private JButton apply;
   private JButton exit;
   
   private JList list;
   private String[] selections = {"Canada", "Hong Kong", "Japan", "America"
                                 , "British", "Korean", "Swedish", "French"
                                 , "Russia"};
   private JScrollPane scrollPane;
   private JPanel listPanel;
   private JButton browseButton;
   private int choice = 0;
   
   private JPanel currentFlag;
   private ImageIcon FLAG = getImage(choice);
   private JLabel label;
   
   private JPanel rightPanel;
   private JCheckBox box;
   private boolean isBox = false;
   
   public FlagChange(Menu m)
   {
      this.m = m;
      
      panel = new JPanel();
      panel.setLayout(new BorderLayout());
      
      //normal setting
      setTitle("Flag change");
      setSize(600, 400);
      
      //exit and apply
      buttonPanel = new JPanel();
      buttonPanel.setLayout(new GridLayout(1, 2));
      exit = new JButton("EXIT");
      exit.addActionListener(new closeListener());
      apply = new JButton("APPLY");
      apply.addActionListener(new ListSelection());
      buttonPanel.add(apply);
      buttonPanel.add(exit);
      panel.add(buttonPanel, BorderLayout.SOUTH);
      
      //JList
      list = new JList(selections);
      listPanel = new JPanel();
      listPanel.setLayout(new BorderLayout());
      list.setSelectedIndex(choice);
      list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      list.addListSelectionListener(new ListSelection());
      scrollPane = new JScrollPane(list);
      scrollPane.setPreferredSize(new Dimension(350, 250));
      listPanel.setBorder(BorderFactory.createTitledBorder("Change the Flag:"));
      listPanel.add(scrollPane, BorderLayout.CENTER);
      
      //Browse Button
      browseButton = new JButton("Get your own!");
      browseButton.addActionListener(new ListSelection());
      listPanel.add(browseButton, BorderLayout.SOUTH);
      
      panel.add(listPanel, BorderLayout.CENTER);
      
      //FlagPanel
      label = new JLabel(resizeImage(FLAG));
      currentFlag = new JPanel();
      currentFlag.setLayout(new BoxLayout(currentFlag, BoxLayout.X_AXIS));
      currentFlag.add(Box.createHorizontalGlue());
      currentFlag.add(label);    
      currentFlag.add(Box.createHorizontalGlue());  
      
      //rightPanel
      rightPanel = new JPanel();
      rightPanel.setPreferredSize(new Dimension(200, 300));
      rightPanel.setLayout(new BorderLayout());
      rightPanel.setBorder(BorderFactory.createTitledBorder("Show flag:"));
      rightPanel.add(currentFlag, BorderLayout.CENTER);
      
      //JCheckBox
      box = new JCheckBox("Keep Flag");
      box.addItemListener(new ItemListen());
      rightPanel.add(box, BorderLayout.SOUTH);
      
      panel.add(rightPanel, BorderLayout.EAST);
      add(panel);
      setVisible(false);
   }
   
   public class closeListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         if (e.getSource() == exit)
         {
            setVisible(false);
         }
      }
   }
   
   public class ListSelection implements ListSelectionListener, ActionListener
   {
      public void valueChanged(ListSelectionEvent e)
      {
         choice = list.getSelectedIndex();
         label.setIcon(resizeImage(getImage(choice)));
      }
      
      public void actionPerformed(ActionEvent e)
      {
         if (e.getSource() == apply)
         {
            if (isBox)
            {
               m.changeFlag(getImage(choice));
            }
            else
            {
               m.changeAllFlag(getImage(choice));
            }
         }
         else if (e.getSource() == browseButton)
         {
            JFileChooser c = new JFileChooser();
            int rVal = c.showOpenDialog(FlagChange.this);
            if (rVal == JFileChooser.APPROVE_OPTION)
            {
               try
               {
                  ImageIcon temp = new ImageIcon(c.getSelectedFile().getName());
                  label.setIcon(resizeImage(temp));
                  if (isBox)
                  {
                     m.changeFlag(temp);
                  }
                  else
                  {
                     m.changeAllFlag(temp);
                  }
               }
               catch(Exception ex){
                  JOptionPane.showMessageDialog(null, "Not Working!");
               }
            }
         }
      }
   }
   
   public class ItemListen implements ItemListener
   {
      public void itemStateChanged(ItemEvent e)
      {
         isBox = box.isSelected();
      }
   }
   
   public ImageIcon getImage(int i)
   {
      switch(i)
      {
         case 0:
            FLAG = new ImageIcon("CANADA.png");
            break;
         case 1:
            FLAG = new ImageIcon("HONGKONG.png");
            break;
         case 2:
            FLAG = new ImageIcon("JAPAN.png");
            break;
         case 3:
            FLAG = new ImageIcon("AMERICA.png");
            break;
         case 4:
            FLAG = new ImageIcon("BRITISH.png");
            break;
         case 5:
            FLAG = new ImageIcon("KOREAN.png");
            break;
          case 6:
            FLAG = new ImageIcon("SWEDISH.png");
            break;
          case 7:
            FLAG = new ImageIcon("FRENCH.png");
            break;
          case 8:
            FLAG = new ImageIcon("RUSSIA.png");
            break;
      }
      return FLAG;
   }
   
   public ImageIcon resizeImage(ImageIcon image)
   {
      Image oldimage = image.getImage();
      Image newimage = oldimage.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
      return new ImageIcon(newimage);
   }
}