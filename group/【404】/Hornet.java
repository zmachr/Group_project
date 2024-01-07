import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.math.*;

/**
 * 
 * @author Nanxu
 * ��������ڻ�����volume = -1
 * ���û�����߽���range = 'y'
 * 
 */

public class Hornet extends Bee{
	private int id;
	private boolean dead=false;
	private boolean seen=false;
	
	//���ڴ�����0123��������
	private int touched[]=new int[4];
	public Hornet(int id,int x, int y, double angle,boolean isAlive,Image img){
		super(id,x,y,angle,isAlive,img);
		this.id = id;
	}
	int currentstate = 0;
	boolean catching =false;
	int catchid;
    final int SEARCH = 0;
	final int CATCHING = 1;
	final int SUCCESS = 2;
	final int FAILED = 3;
	boolean circuling = false;
	int circlenum = 0;
	//��ȡ��ǰ״̬
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
		   
	boolean isout(Hashtable state) {
		return !((ArrayList<String>)state.get("range")).isEmpty();
	}
	
	boolean isexistbee(Hashtable state) {
		return !((ArrayList<Double>)state.get("angleOfbeeR")).isEmpty();
	}
	
	int numberofFlowers(Hashtable state) {
		if (isExistFlower(state)) {
			return ((ArrayList)state.get("volume")).size();
		}
		else
			return 0;
	}
	void flyback(int currentstate, Hashtable state) {
		{
			double jg=-1;
			String fx=((ArrayList<String>)state.get("range")).get(0);
//			if (fx.equals("E")) jg=0;
//			if (fx.equals("N")) jg=90;
//			if (fx.equals("W")) jg=180;
//			if (fx.equals("S")) jg=270;
			jg=currentstate*90;
			Random ra = new Random();
			int tr=ra.nextInt(180);
			double ta=this.angle;
			while ((ta+tr-jg)>90&&(ta+tr-jg)<-90) {
				tr=ra.nextInt(180);
			}
			this.angle=ta+tr;
			while (this.angle>360) this.angle-=360;
			while (this.angle<0) this.angle+=360;
		}
	}
	boolean isExistFlower(Hashtable state){
		return !((ArrayList)state.get("volume")).isEmpty();
	}

	/**�˷�������Ҫ��д�ĺ��Ĵ��룬�۷���۵���Ҫ�����ڴ�������*/
	public void search(){
		int speed=0;
		String strVision = BeeFarming.search(this.id);
		Hashtable state=getState(strVision);
		ArrayList<String> tRange=(ArrayList)state.get("range");
		//�жϱ߽�
		if (!tRange.isEmpty()) {
			for (String fx:tRange) {
				/*������ת��Ϊ����*/
				int nf=-1;
				if (fx.equals("E")) nf=0;
				if (fx.equals("S")) nf=1;
				if (fx.equals("W")) nf=2;
				if (fx.equals("N")) nf=3;
				if (nf>=0) touched[nf]++;
				if (catching) {
					if (touched[nf]>5) {
						flyback((nf+2)%4,state);
						return;
					}
				} else {
					if (touched[nf]>3) {
						flyback((nf+2)%4,state);
						return;
					}
				}
			}
			
		}
		else {
			for (int i=0;i<4;i++)
				touched[i]=0;
		}
		//ץ���۷�
		if (isexistbee(state)) {
			Iterator itb=((ArrayList)state.get("angleOfbee")).iterator();
			Iterator itr=((ArrayList)state.get("angleOfbeeR")).iterator(); 
			Iterator iti=((ArrayList)state.get("idOfbee")).iterator();  
			while (itb.hasNext()&&iti.hasNext()&&itr.hasNext()) {				
				Integer id=(Integer)iti.next();
				Double ba=(Double)itb.next();
				Double ar=(Double)itr.next();
				if (catching) 
					if (!id.equals(catchid))continue;
				catching =true;catchid=(int)id;
				double rr=Math.abs(ba-ar);
				if (rr>180) rr=360-rr;
				if (rr>106.12762D) {
					//�ɱ����������ε���ʽ��׽
					double x=180-rr;
					if (ar>ba) x=x;else x=-x;
					if (Math.abs(ar-ba)>180) x=-x;
					
					angle=ar+x;
					while (angle>360) angle-=360;
					while (angle<0) angle+=360;
				}
				else {
					//ֻ��β��
					double tana,cosa,x;
					tana=Math.tan(Math.toRadians(rr));
					cosa=Math.cos(Math.toRadians(rr));
					x=1.8*tana/(1.8*cosa+1);//׷����ʽ
					angle=ar+(ar>ba?-x:x);
					while (angle>360) angle-=360;
					while (angle<0) angle+=360;
				}
			}
		}
		//û���۷䣬�����л�
		else {
			catching =false;
			catchid=-1;
			if (isExistFlower(state)){
				Iterator it= ((ArrayList)state.get("angleofflower")).iterator();
				Iterator itV=((ArrayList)state.get("volume")).iterator();;
				while (it.hasNext()) {
					angle=(Double)it.next();
				}
			}
			
		}
		//System.out.println("Hornet<angle:"+angle+"> is catching "+"id <"+catchid+"> "+catching);
		//��������˼·������д��һ��
//		//System.out.println("Hornet<"+ id + ">: " + strVision);
//		int speed = 0;
//		Hashtable state=getState(strVision);
//		if (isout(state)) {
//			flyback(currentstate, state);
//			circuling = false;
//			circlenum = 0;
//		}
//		switch (currentstate) {
//		case SEARCH://��������
//		{	
//			 if (isexistbee(state)) {
//					currentstate = CATCHING;
//					break;
//				}
//			 if (circuling) {
//				 angle -= 10;
//				 circlenum++;
//				 if (circlenum == 50) {
//					 circuling = false;
//					 circlenum = 0;
//				 }
//				 break;
//			 }
//			Random random = new Random();
//			double nextAngle ;
//			if (!isExistFlower(state))
//			{
//				
//				nextAngle = random.nextInt(50);
//		        if (random.nextBoolean())
//					 angle += nextAngle;
//				else
//				     angle -= nextAngle;
//				    
//			}
//			else
//			{
//				
//			   // nextAngle = random.nextInt(180);	
//			   ArrayList<Double> angleofflower = (ArrayList)state.get("angleofflower");
//			   double angle_temp = angleofflower.get(0);
//			   nextAngle = angle_temp + 90;
//			   angle = nextAngle;
////			  if(numberofFlowers(state) > 1)
//			     circuling = true;
//			 
//			}
//			
//		    break;
//		}
//		case CATCHING:
//		{
//
//			Double tAngle=null;//
//			Iterator itR=((ArrayList)state.get("angleOfbeeR")).iterator();
//			Iterator it =((ArrayList)state.get("angleOfbee")).iterator();
//			Double R,b;
//			while (itR.hasNext()&&it.hasNext()) {
//				R=(Double)itR.next();
//				b=(Double)it.next();
//				tAngle=2*R-b+180;//����1:���Ʒ���۷������ʱ�����ɵ��������ν��в�׽
//				while (tAngle<0) tAngle+=360;
//				while (tAngle>=360) tAngle-=360;
//				if (Math.abs(tAngle-angle)>=90||Math.abs(tAngle-angle)>=270) tAngle=R;
//				//tAngle=R;
//				Random ra = new Random();
//				if (ra.nextInt(100)<50) break;	
//			}		
//			if (tAngle!=null) {
//				angle=tAngle;
//			}
//			if (!isexistbee(state)) {
//				currentstate = FAILED;
//			}
//			break;
//		}
//		case FAILED:
//			angle += 45;
//			currentstate = SEARCH;
//			break;
//		}
//		//�߽��ж�
//		if (!((ArrayList<String>)state.get("range")).isEmpty()) {
//			double jg=-1;
//			String fx=((ArrayList<String>)state.get("range")).get(0);
//			if (fx.equals("E")) jg=0;
//			if (fx.equals("N")) jg=90;
//			if (fx.equals("W")) jg=180;
//			if (fx.equals("S")) jg=270;
//			Random ra = new Random();
//			int tr=ra.nextInt(180);
//			double ta=angle;
//			while ((ta+tr-jg)<90&&(ta+tr-jg)>-90) {
//				tr=ra.nextInt(180);
//			}
//			angle=ta+tr;
//			ratoteImage(angle);
//			setXYs(0);
//			if (((ArrayList)state.get("angleOfbee")).isEmpty())return;
//		}
//		//�ޱ߽������tAngleΪ�����Ƕȡ�
//		Double tAngle=null;//
//		Iterator itR=((ArrayList)state.get("angleOfbeeR")).iterator();
//		Iterator it =((ArrayList)state.get("angleOfbee")).iterator();
//		Double R,b;
//		while (itR.hasNext()&&it.hasNext()) {
//			R=(Double)itR.next();
//			b=(Double)it.next();
//			tAngle=2*R-b+180;//����1:���Ʒ���۷������ʱ�����ɵ��������ν��в�׽
//			while (tAngle<0) tAngle+=360;
//			while (tAngle>=360) tAngle-=360;
//			if (Math.abs(tAngle-angle)>=90||Math.abs(tAngle-angle)>=270) tAngle=R;
//			//tAngle=R;
//			Random ra = new Random();
//			if (ra.nextInt(100)<50) break;	
//		}		
//		if (tAngle!=null) {
//			angle=tAngle;
//		}
		//�����������Ϣ�����ӡ����
		//if(strVision.length()>0)
		//	System.out.println(id+"  :  "+strVision+"|");	
		ratoteImage(angle);
		setXYs(0);	
	}
	/**����Ʒ�ץ�����۷䣬��boolean dead==true���Ʒ���Ը���dead��ֵ�ж��۷�֪��ɱ����
	�����������޸ģ���BeeFarming��killBee�����е��۷䱻�Ʒ�����󽫱�����*/
	public boolean isCatched(){
	    dead = true;
	    return dead;
	}
	  
}

