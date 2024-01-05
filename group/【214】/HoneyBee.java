
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
public class HoneyBee extends Bee{
	private int id;
	private boolean isDanger=false;
	private int num=0;
	private boolean  multiple=false;
	
	public HoneyBee(int id,int x, int y, double angle,boolean isAlive,Image img){
		super(id,x,y,angle,isAlive,img);
		this.id = id;
	}	
	/**�˷�������Ҫ��д�ĺ��Ĵ��룬�۷���۵���Ҫ�����ڴ�������*/
	public void search(){			
		String strVision = BeeFarming.search(id);
		int i=useBehindVision();
		if(i==2)
		   isDanger=true;
		else{
		    isDanger=false;
		   setagnle(strVision);
       }		   
		ratoteImage(angle);
		setXYs(0);
	}	
  public  int   useBehindVision(){
    BeeFarming.update(new FlyingStatus (id,0,0,angle+180,true,0));
    String strVision = BeeFarming.search(id); 
       		//�۷俴��������
		 if(strVision.indexOf('+')!=-1){
		   int  idx,idx1,idx2,i;
		   String str_temp = strVision.substring(strVision.indexOf('+'));
		   idx1 = str_temp.indexOf(',');
		   idx2 = str_temp.indexOf(',',idx1+1);
		   i = Integer.parseInt(str_temp.substring(str_temp.indexOf('(')+1,idx1)); 
		   String  str_bee =str_temp.substring(idx1+1,idx2-1); 
		   if( i==9) {
		    angle = Double.parseDouble(str_bee)+90;
			//BeeFarming.update(new FlyingStatus(9,0,0,angle,false,0));
			return 2;
		   }
		   else{  	
			   int  x1,x2,id2,x;
			   String str_temp2 = str_temp.substring(str_temp.indexOf(')'));
			   if(str_temp2.indexOf('+')!=-1){
				   x1 = str_temp2.indexOf(',');
				   x2 = str_temp2.indexOf(',',x1+1);
				   id2= Integer.parseInt(str_temp2.substring(str_temp2.indexOf('(')+1,x1)); 
				   String  str_bee2 =str_temp.substring(idx1+1,idx2-1); 
					 if( id2==9) {
						angle = Double.parseDouble(str_bee2)+90;
						//BeeFarming.update(new FlyingStatus(9,0,0,angle,false,0));
						return 2;
			        }                
              }				  
		  }
	   }   
	return 0;
  }
  public void  setagnle(String strVision){
   		//�۷俴��������
		 if(strVision.indexOf('+')!=-1){
		   int  idx,idx1,idx2,i;
		   String str_temp = strVision.substring(strVision.indexOf('+'));
		   idx1 = str_temp.indexOf(',');
		   idx2 = str_temp.indexOf(',',idx1+1);
		   i = Integer.parseInt(str_temp.substring(str_temp.indexOf('(')+1,idx1)); 
		   String  str_bee =str_temp.substring(idx1+1,idx2-1); 
		   if( i==9) {
		    angle = Double.parseDouble(str_bee)+90;
			//BeeFarming.update(new FlyingStatus(9,0,0,angle,false,0));
		   }
		   else{  	
			   int  x1,x2,id2,x;
			   String str_temp2 = str_temp.substring(str_temp.indexOf(')'));
			   if(str_temp2.indexOf('+')!=-1){
				   x1 = str_temp2.indexOf(',');
				   x2 = str_temp2.indexOf(',',x1+1);
				   id2= Integer.parseInt(str_temp2.substring(str_temp2.indexOf('(')+1,x1)); 
				   String  str_bee2 =str_temp.substring(idx1+1,idx2-1); 
					 if( id2==9) {
						angle = Double.parseDouble(str_bee2)+90;
						BeeFarming.update(new FlyingStatus(9,0,0,angle,false,0));
			        }
                    else			   
				     angle = 180+Double.parseDouble(str_bee2);	
              }				  
		  }
	   }   
		//�۷俴����
	    else if(strVision.indexOf('-')==0&&strVision.indexOf("ON")==-1){
		   int idx1=strVision.indexOf(')');
		   String  str_first = strVision.substring(strVision.indexOf(',')+1,strVision.indexOf(')')-1); 
		   String  str_temp=strVision.substring(idx1+1);
		  
		   if(str_temp.indexOf('-')==1){
		       multiple=true;   
			   }
			if(!isDanger)
		       angle =  Double.parseDouble(str_first);	 
		 }
		 //��仨
		 else if(multiple){
		    multiple=false;
			angle+=180;
		 }
		 //�������*Ϊ�׵��ַ��������������˱ߣ����������˳ʱ����ת90�����ڵĽǶ�		
		else if(strVision.indexOf('*')==0){			
			Random ra = new Random();
			angle += ra.nextInt(90);
		}		
		//�۷��ڻ���
		else if(strVision.indexOf("ON")!=-1){
		    //BeeFarming.update(new FlyingStatus(9,1000,1000,angle,false,0));
		}	
		//�������
		else 
		 {  
		        angle+=(int)(Math.random()*20)-10;		
		 }		 
  }
}

     
