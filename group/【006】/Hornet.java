import java.awt.*;
import java.awt.image.*;
import java.util.*;
public class Hornet extends Bee{
	private int id;
	private boolean dead=false;
	private boolean seen=false;
	private static FlyingStatus[] status = new FlyingStatus[10];//ÿֻ�۷䵱ǰ����״̬����
		private final static int[] RANGE = {50,50,50,50,50,50,50,50,50,100};
	public Hornet(int id,int x, int y, double angle,boolean isAlive,Image img){
		super(id,x,y,angle,isAlive,img);
		this.id = id;
	}
	  
	  //��strVision��ȡ����Ч����
	   public static String[] substringBetween(String str, String open, String close)
  {
  	int i=0,k=0,start,end;
  	String[] strs = new String[10];
  	String op = open;
  	String cl = close;
  	if ((str == null) || (open == null) || (close == null)) {
      return null;
       }
  	for(i=0;((str!=null)||(str.length()==1))&&(str.indexOf(op)!=-1)&&(str.indexOf(cl)!=-1);i++){
        start = str.indexOf(op);
        end = str.indexOf(cl, start + op.length());
        strs[i] = str.substring(start + op.length(), end);
        str = str.substring(end + 1);
   }
    return strs;
 }
 
    //��һ���۲⵽���۷�ĽǶ�
    public  double finalAngle (double x1,double x2)
    {       double x3,x4,x5;
            x3=90-x2+x1;
            x4=90-x1+x2;    
           while(x3<0)
           {
            x3=x3+360;
           }
            while(x3>360)
           {
            x3=x3-360;
           }      
            while(x4<0)
           {
            x4=x4+360;
           }
            while(x4>360)
           {
            x4=x4-360; 
           }
           while(x2>360)
           {
            x2=x2-360;
           }
           x5=x3+60*Math.cos(Math.toRadians(90-x4+x3))-90+x2;
           return x5;
    }

	/**�˷�������Ҫ��д�ĺ��Ĵ��룬�۷���۵���Ҫ�����ڴ�������*/
	public void search(){
     boolean seen1=false;
     boolean seen2=false;
     boolean seen3=false;
		 String strVision = BeeFarming.search(id);
		 String[] str = substringBetween(strVision,"(",")");
		
		 int k=0;
		 String[][] key = new String[10][3] ;
		 if(str[0] == null && str[1] == null &&str[2] == null){
		 	 for (int i = 0; i < 10; i++)
       {
          for (int j = 0; j < 3; j++)
           {
              key[i][j]= null;
           }
       }
		 	}
		 for(int i = 0;i<10/**str[i] != null*/;i++){
		 	 if(str[i] == null){
		 	 	for(int n=0;n<3;n++){
		 	 		key[i][n] = null;
		 	 	   	}
		 	 		}
		 	 		else{
		 	   String[] ll  = str[i].split(",");
		 	   if(ll.length == 2){                //�۷����������۷��ڻ���
		 	   	key[i][0] = ll[0];
		 	   	key[i][1] = ll[1];
		 	   	key[i][2] = null; 
		 	   	}
		 	   	else {
		 	   		key[i] = ll;
		 	   		}
		 	   	}
		 	}  
      /////�������������жϻƷ��Ƿ��������۷䣬���ǣ���ʼ��׽
     if(key[0][2] != null || key[1][2] != null || key[2][2] != null ){
          //System.out.println("�����۷䣬׽��ing");
          if(key[0][2] != null){
                    seen1=true;
                    if((seen2!=true)&&(seen3!=true)){
                    int num = Integer.parseInt(key[0][0]);                                   //ȡ��id
             	   	  Double tt= Double.parseDouble(key[0][1]);
                    Double kk= Double.parseDouble(key[0][2]);
                    Double ff=finalAngle(tt,kk);
             	   	  //System.out.println(num+"��С�۷䣬Ī�ܣ������I(^��^)�J");
             	   	  ratoteImage(tt);
                   }
          }
          else if(key[1][2] != null){
                    seen2=true;
                    if((seen1!=true)&&(seen3!=true)){
             	   	  Double tt= Double.parseDouble(key[1][1]);
                    Double kk= Double.parseDouble(key[1][2]);
             	   	  int num = Integer.parseInt(key[1][0]);
                    Double ff=finalAngle(tt,kk);
             	   	  //System.out.println(num+"��С�۷䣬Ī�ܣ������I(^��^)�J");
             	   	  ratoteImage(tt);
                    }
          }
          else if(key[2][2] != null){
                    seen3=true;
                    if((seen2!=true)&&(seen1!=true)){
             	   	  int num = Integer.parseInt(key[2][0]);
             	   	  //System.out.println(num+"��С�۷䣬Ī�ܣ������I(^��^)�J");
             	   	  Double tt= Double.parseDouble(key[2][1]);
                    Double kk= Double.parseDouble(key[2][2]);
                    Double ff=finalAngle(tt,kk);
             	   	  ratoteImage(tt);
                }
          }
      }
             	   ///// �ж��Ƿ�������ǽ�ڣ����ǣ������һ���Ƕ�����
	    else if((strVision.indexOf('*')==0)&&strVision.length()<15){
	    	              Random ra = new Random();
                      angle += ra.nextInt(90);
                      //System.out.println("�Ʒ�ײǽ");
                      angle += 180;
                      ratoteImage(angle);
      }
      //�Ʒ��ڻ��ϵ��ж� 
      else if((key[0][1] != null && key[0][1].equals("ON"))||(key[1][1] != null && key[1][1].equals("ON"))||(key[2][1] != null && key[2][1].equals("ON"))){
            /** Random ra = new Random();
             angle += ra.nextInt(180);
             ratoteImage(angle);**/ 
      }
      //�Ʒ���������ж�
	    /**else if ((seen1!=true)&&(seen2!=true)&&(seen3!=true)) {                   //�һƷ䲻��׷���۷�ʱ
          if(key[0][2] == null && key[0][1] != null){
                         Double tt= Double.parseDouble(key[0][1]);
                         //System.out.println("�۷����");
                         ratoteImage(tt);
          }
          else if(key[1][2] == null && key[1][1] != null ){
                         Double tt= Double.parseDouble(key[1][1]);
                         //System.out.println("�۷����");
                         ratoteImage(tt);
          }
          else if(key[2][2] == null && key[2][1] != null){
                         Double tt= Double.parseDouble(key[2][1]);
                         //System.out.println("�۷����");
                         ratoteImage(tt);
          }
    } 
    **/
		setXYs(0);	
	}
	/**����Ʒ�ץ�����۷䣬��boolean dead==true���Ʒ���Ը���dead��ֵ�ж��۷�֪��ɱ����
	�����������޸ģ���BeeFarming��killBee�����е��۷䱻�Ʒ�����󽫱�����*/
	   public boolean isCatched(){                                                                                                                                     
       dead = true;                  
       return dead;                  
   }   
 }
                   