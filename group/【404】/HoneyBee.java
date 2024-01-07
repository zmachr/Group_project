import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.math.*;


public class HoneyBee extends Bee{
	private int id;
	private static boolean isDanger[]=new boolean[10];//��¼���۷��Ƿ�������ڱ�׷��
	private static int dangerTime[]=new int[10];//��¼�ӿ�ʼ��׷�������ʱ��
	private int num=0;
	static int mTime=1100;
	private int isOutofrange = 0;
	
	/*���������¼ϵͳ����ϵͳ��ҪĬ���ٶȲ��䣬��дʱ�ٶ�Ϊ18���Ӿ�Ϊ50*/
	private int dx=-1,dy=-1,sx=-1,sy=-1;//��¼�۷�������꣬����ͨ�������߽�ķ������,-1��ʾδ֪��Ԥ����һ���Ӿ�����
	
	private void getXY(String fx,int speed,int RANGE,double tangle)
	{
		double angle=Math.toRadians(tangle);
		if (fx.equals("E")) {
			this.sx=(int) Math.abs(RANGE*Math.cos(angle));
		}
		if (fx.equals("W")) {
			this.dx=(int) Math.abs(RANGE*Math.cos(angle));
		}
		if (fx.equals("N")) {
			this.dy=(int) Math.abs(RANGE*Math.sin(angle));
		}
		if (fx.equals("S")) {
			this.sy=(int) Math.abs(RANGE*Math.sin(angle));
		}
	}
	private void fXY(int flwV,double tangle,int speed) {
		double angle=Math.toRadians(tangle);
		int dist=speed*(10-flwV);
		
		this.dx+=dist*Math.cos(angle);
		this.dy+=dist*Math.sin(angle);
		this.sx-=dist*Math.cos(angle);
		this.sy-=dist*Math.sin(angle);		
	}
	
	/**
	 * 
	 * @author Nanxu
	 * ��������ڻ�����volume = -1
	 * ���û�����߽���range = 'y'
	 * 
	 */		

	public  Hashtable getState(String state) {
		String[] input = state.split("~");
		//System.out.println(input[1].charAt(1));
		ArrayList<String> range = new ArrayList<String>();
		//check range
		//�˴���Ҫ�޸ģ���Ȼ�޷�����ͬʱ���������߽�����
		//BeeFarming.search()�жԵı߽�������ǰ���Ӿ�Զ���Ƿ񳬽硣
//		if (input[0].startsWith("*")) {
//		       range = input[0].substring(1,2);
//		}
		//check flower
		int Flower = 0;
		int isOnFlower = 0;
		ArrayList<Integer> volume = new ArrayList();
		ArrayList<Double> angleofFlower = new ArrayList();
		for (String element : input) {
			//System.out.println(element);
			if (element.startsWith("-")){
				Flower += 1;
				String[] temp = element.split(",");
				volume.add(Integer.parseInt(temp[0].substring(2)));
				if (element.endsWith("ON)"))
				   isOnFlower = 1;
				else 
					angleofFlower.add(Double.parseDouble(temp[1].replace(')', ' ')));
					
			}
			else if(element.startsWith("*")){
				range.add(element.substring(1, 2));
			}
		}
		//check bees
		int isExistBee = 0;
		ArrayList<Integer> idOfbee = new ArrayList();
		ArrayList<Double> angleOfbeeR = new ArrayList();
		ArrayList<Double> angleOfbee = new ArrayList();
		
		for (String element : input) {
			if (element.startsWith("+")) {
				isExistBee = 1;
				String[] temp = element.split(",");
				idOfbee.add(Integer.parseInt(temp[0].substring(2)));
				angleOfbeeR.add(Double.parseDouble(temp[1]));
				angleOfbee.add(Double.parseDouble(temp[2].replace(')', ' ')));
			}
		}
		Hashtable result = new Hashtable(8);
		result.put("isOnFlower", isOnFlower);
		result.put("range", range);
		result.put("volume", volume);
		result.put("angleofflower", angleofFlower);
		result.put("isExistBee", isExistBee);
		result.put("idOfbee", idOfbee);
		result.put("angleOfbeeR", angleOfbeeR);
		result.put("angleOfbee", angleOfbee);
	    return result;
	}	 
		   
	public HoneyBee(int id,int x, int y, double angle, boolean isAlive, Image img){
		super(id,x,y,angle,isAlive,img);
		this.id = id;
	}
	static {
		for (int i=0;i<0;i++){
			isDanger[i]=false;
			dangerTime[i]=0;
		}
	}
	/**�˷�������Ҫ��д�ĺ��Ĵ��룬�۷���۵���Ҫ�����ڴ�������*/
	public void search(){
		mTime--;
		//System.out.println("mTime"+mTime);
		String strVision = BeeFarming.search(id);
		
		Random ra = new Random();
		Hashtable state=getState(strVision);
		if (isDanger[id]) dangerTime[id]++;
		ArrayList<String> tRange=(ArrayList)state.get("range");
		int nR=0;
		//�߽��ж�
		if (!(tRange.isEmpty())) //ͬʱ���������߽�ʱ����ִ��󣡣���
			for (String fx:tRange) {
				getXY(fx,18,50,angle);
				if (dangerTime[id]>1500) {
					dangerTime[id] = 0;
					isDanger[id] = false;
				}
				nR++;
				if (nR>1) {
					angle=angle+180+2;//+2����Ϊת�ǵ�ʱ������1�ȵĿ��࣬���Զ�תһ�ȷ�ֹ���磨��ʵ����תҲ�������
					while (angle>360) angle-=360;
					break;
				}
				double jg=-1,tmp=0;
				boolean jia=true;
				while (angle<0) angle+=360;
				while (angle>360) angle-=360;
				if (fx.equals("E")) {
					jg=0;
					jia=(angle<90);
				}
				if (fx.equals("N")) {
					jg=270;
					jia=((angle-270)>0);
				}
				if (fx.equals("W")) {
					jg=180;
					jia=(angle-180)>0;
				}
				if (fx.equals("S")) {
					jg=90;
					jia=(angle-90)>0;
				}
			
				int tr=ra.nextInt(91);
				
				double ta=angle;
				while (Math.abs(ta+(jia?tr:-tr)-jg)<90) {
					tr=ra.nextInt(91);
				}
				if (jia) angle=ta+tr;
					else angle=ta-tr;
			
				ratoteImage(angle);
				setXYs(0);
				/*
				if (!strVision.isEmpty()) {
					System.out.println("HoneyBee<"+ id + ">: " + strVision);
					System.out.println("dx="+dx+";dy="+dy+";sx="+sx+";sy="+sy);
				}
				*/
				//return;
			}
		//�ޱ߽������tAngleΪ�����Ƕȡ�
		Double tAngle=null;
		//��¼�¼������۵Ļ�������������������о��뼰����
		int flwV=0;
		//���ѡȡһ����Ұ�еĻ�ȥ���ۣ���Ϊ����ʱ���޷����ƣ����Ի��۵��������ٲ���
		if (!((ArrayList)state.get("volume")).isEmpty())
		{
			Iterator it= ((ArrayList)state.get("angleofflower")).iterator();
			Iterator itV=((ArrayList)state.get("volume")).iterator();;
			while (it.hasNext()) {
				if (isDanger[id]) break;
				int dn=0,al=0;
				for (int i=0;i<9;i++)
					{ 
						if (isDanger[i]) dn++;				
					}
				if (dn!=1)
					if (ra.nextInt(2000)<mTime) break;	
				tAngle=(Double)it.next();
				flwV=(int)itV.next();
			}
		}
		if (tAngle!=null) {
			angle=tAngle;
		}
		
		if ((int)state.get("isExistBee")>0){		
			Iterator itb=((ArrayList)state.get("angleOfbee")).iterator();
			Iterator itr=((ArrayList)state.get("angleOfbeeR")).iterator(); 
			Iterator iti=((ArrayList)state.get("idOfbee")).iterator(); 
			while (itb.hasNext()&&iti.hasNext()&&itr.hasNext()) {
				Integer id=(Integer)iti.next();
				Double ba=(Double)itb.next();
				Double ar=(Double)itr.next();
				if (id<9) {
				//	angle=ba+ra.nextInt(90)+90;
					if (Math.abs(ar-ba)>90&&flwV==0) {
						angle=ar+ra.nextInt(90);
					}
				}
				else {//����һֻ�Ʒ�
					//�ȿ����Լ��ڲ��ڻƷ����Ұ��
					boolean safe=false;
					if ((Math.abs(((int)(ar+180)%360)-ba))>90) safe=true;
					if (safe) {
						angle=ar+((ra.nextInt(10)>5)?90:-90);
						
					}
					else {
						angle=ar+89.99D;
						while (angle>360) angle-=360;
						//angle=ar+90;
						for (int i=0;i<9;i++) isDanger[i]=false;
						isDanger[id]=true;						
						dangerTime[id]=0;						
					}
					flwV=0;
				}				
			}
		}
		fXY(flwV,angle,18);
		ratoteImage(angle);
		setXYs(0);
	}
}

