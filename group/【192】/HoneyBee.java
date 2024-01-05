import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class HoneyBee extends Bee{
	private int id;
	private boolean isDanger=false;
	private int num=0;
	private static int dir = 1;
	
	private static boolean dangersignal[] = {false,false,false,false};
	
	public HoneyBee(int id,int x, int y, double angle,boolean isAlive,Image img){
		super(id,x,y,angle,isAlive,img);
		this.id = id;
	}
	
	/**�˷�������Ҫ��д�ĺ��Ĵ��룬�۷���۵���Ҫ�����ڴ�������*/
	public void search(){
		String strVision = BeeFarming.search(id);
		if(strVision.indexOf('*')!=-1){
			if(strVision.indexOf('+') ==-1){
				if(strVision.indexOf('-') != -1)
					Pickhoney(strVision);
				Random ra = new Random();
				angle += ra.nextInt(90);
				ratoteImage(angle);
			}
			else
			{
				dangersignal[id] = true;
				Avoidhornet(strVision);
				new Timetochange(20,id);		//�����Ʒ䣬20�벻�������ۣ�����
			}
		}
		
		else{
			if(strVision.indexOf("+(9") !=-1)
			{
				dangersignal[id] = true;
				Avoidhornet(strVision);
				new Timetochange(20,id);
			}
			if(strVision.indexOf("+(9") ==-1 && strVision.indexOf('-')!= -1 && dangersignal[id]==false )
			{	
				if(strVision.indexOf("+(1") != -1 ||strVision.indexOf("+(2") != -1
						||strVision.indexOf("+(2") != -1)		//������ֻ�۷���������bug
				{
					Random ra = new Random();
		        	angle = angle + ra.nextInt(90);
		        	ratoteImage(angle);
				}
				else
					Pickhoney(strVision);
			}
			if(strVision.indexOf("+(9") ==-1 && strVision.indexOf('-')== -1)
			{
				Random ra = new Random();
	        	angle = angle + ra.nextInt(25) * dir;
				
	            ratoteImage(angle);
	            dir = dir*(-1);			//ʵ�����Ұڶ�������ڶ�25�����ڣ�25Ϊ��β��Եó�
			}
			
			
		}

		setXYs(0);
	}
	
	
		public void Pickhoney(String strVision){	//�������۴�
			if(strVision.indexOf('-') != -1){
				System.out.println(strVision);
				int flag = strVision.indexOf('-');
				int flag1 = strVision.indexOf('(',flag+1);
				int flag2 = strVision.indexOf(',',flag1+1);
				int flag3 = strVision.indexOf(')',flag2+1);
				
				String anglestring = strVision.substring(flag2+1,flag3);
				if(!anglestring.equals("ON")){
				angle =  (Double.parseDouble(anglestring)+360)%360;
				ratoteImage(angle);
				}
			}
		}
	
		public void Avoidhornet(String strVision){	//ײ���Ʒ�ʱת�򷽰�
			
		if(strVision.indexOf('+') !=-1){
			
			int flag = strVision.indexOf('+');
			int flag1 = strVision.indexOf('(',flag+1);
			int flag2 = strVision.indexOf(',',flag1+1);
			int flag3 = strVision.indexOf(',',flag2+1);
			
			String idstring = strVision.substring(flag1+1,flag2);
			String astring = strVision.substring(flag2+1,flag3);
			
			double hornet = (Double.parseDouble(astring)+360) % 360;
			if(Integer.parseInt(idstring) == 9)
			{
				
				if(hornet>=0 && hornet<90)
					angle = 180;
				if(hornet>=90 && hornet<180)
					angle = 270;
				if(hornet>=180 && hornet<270)
					angle = 0;
				if(hornet>=270 && hornet<360)
					angle = 90;
				ratoteImage(angle);
			}
			setXYs(0);
		}
		
		}
		
		class Timetochange{		//��ʱ��
			Timer timer;
			
			public Timetochange(int seconds,int id){
				timer = new Timer();
		        timer.schedule(new RemindTask(), seconds*1000);
			}
			class RemindTask extends TimerTask {
		        public void run() {
		           
		            dangersignal[id] = false;
		            timer.cancel(); //Terminate the timer thread
		        }
		    }
			
		}
		
}