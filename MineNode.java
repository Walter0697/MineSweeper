public class MineNode
{
   String name;
   int sizex, sizey, numOfMine;
   double realTime, time;
   
   public MineNode()
   {
      name = "No name";
      sizex = 0;
      sizey = 0;
      numOfMine = 0;
      realTime = 0;
      time = 0;
   }
   
   public MineNode(int x, int y, int m, double t)
   {
      name = "No name";
      sizex = x;
      sizey = y;
      numOfMine = m;
      realTime = t;
      time = roundUp(realTime);
   }
   
   public MineNode(String n, int x, int y, int m, double t)
   {
      this(x ,y, m, t);
      name = n;
   }
   
   public double roundUp(double num)
   { 
      return Math.round(num * 100.0) / 100.0 ;
   }
   
   public MineNode(int size, double t)
   {
      if (size == 1)
      {
         sizex = 9;
         sizey = 9;
         numOfMine = 10;
      }
      else if (size == 2)
      {
         sizex = 16;
         sizey = 16;
         numOfMine = 40;
      }
      else if (size == 3)
      {
         sizex = 16;
         sizey = 30;
         numOfMine = 99;
      }
      time = t;
   }
}