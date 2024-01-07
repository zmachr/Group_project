import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class Hornet extends Bee{
	private int id;
	private boolean dead=false;
	private boolean seen=false;
	private boolean isMissing = false;
	private int TIMER = 5;
	public Hornet(int id,int x, int y, double angle,boolean isAlive,Image img){
		super(id,x,y,angle,isAlive,img);
		this.id = id;
	}
	
	/**�˷�������Ҫ��д�ĺ��Ĵ��룬�۷���۵���Ҫ�����ڴ�������*/
	public void search(){
		
		ArrayList<FlowerSimple> flowerList = new ArrayList<>();
		ArrayList<BeeSimple> beeList = new ArrayList<>();
		
		ArrayList<FlowerSimple> flowerBeeList = new ArrayList<>();
		ArrayList<BeeSimple> beeBeeList = new ArrayList<>();
		
		ArrayList<FlowerSimple> flowerTemp = new ArrayList<>();
		ArrayList<BeeSimple> beeTemp = new ArrayList<>();
		
		BeeSimple beeSimple = null;
		
		String strVision = BeeFarming.search(id);	
		for (int i = 1; i < 3; i++) {
			String strTemp = BeeFarming.search(id);
			getInformation(flowerTemp, beeTemp, strTemp);
			for (int j = 0; j < beeTemp.size(); j++) {
				if (beeTemp.get(j).id == id) {
					isMissing = true;
				}
			}
		}
		
		//�������*Ϊ�׵��ַ��������������˱ߣ����������˳ʱ����ת90�����ڵĽǶ�
		/*if(strVision.indexOf('*')==0)
		{			
			Random ra = new Random();
			angle += ra.nextInt(90);
			if (angle == 180 || angle == 90 || angle == 0) {
				if ((angle+30)<360) {
					angle=angle+30;
				}
			}
			ratoteImage(angle);
		}*/
		//�����������Ϣ�����ӡ����
		if(strVision.length()>0)
			System.out.println(strVision);	
		
		getInformation(flowerList, beeList, strVision);
		
		if (TIMER != 0) {
			TIMER--;
		}
		
		if (!beeList.isEmpty() && strVision.indexOf('*')==0) {
			//���۷�������ײǽ���
			super.ratoteImage(beeList.get(beeList.size() - 1).angleBetwenTwoBee);
			setXYs(0);
		}else if(strVision.indexOf('*')==0){
			//���۷�ʱײǽ���
			Random ra = new Random();
			angle += ra.nextInt(90);
			ratoteImage(angle);
			setXYs(0);
		}else if (!beeList.isEmpty()) {
			//��ͨ����»Ʒ�׷���㷨
			isMissing = true;
			seen = false;
			String strBee = BeeFarming.search(beeList.get(beeList.size() - 1).id);
			
			getInformation(flowerBeeList, beeBeeList, strBee);
			for (int i = 0; i < beeBeeList.size(); i++) {
				if (beeBeeList.get(i).id == id) {
					//beeSimple = beeBeeList.get(i);
					seen = true;
				}
			}
			beeSimple = beeList.get(beeList.size() - 1);
			if (seen) {
				//�����۷�ʱ
				if (beeSimple.angleOfSeenbee < beeList.get(beeList.size() - 1).angleBetwenTwoBee - 90&& beeSimple.angleOfSeenbee > beeList.get(beeList.size() - 1).angleBetwenTwoBee -180 && TIMER == 0) {
					super.ratoteImage(beeList.get(beeList.size() - 1).angleBetwenTwoBee - 5);
					setXYs(0);
					TIMER = 5;
				}else if(TIMER == 0){
					super.ratoteImage(beeList.get(beeList.size() - 1).angleBetwenTwoBee + 5);
					setXYs(0);
					TIMER = 5;
				}else {
					super.ratoteImage(beeList.get(beeList.size() - 1).angleBetwenTwoBee);
					setXYs(0);
				}
				
				
			}else {
				//����׷��
				if (beeList.get(beeList.size() - 1).angleBetwenTwoBee < 270) {
					if (beeSimple.angleOfSeenbee < beeList.get(beeList.size() - 1).angleBetwenTwoBee + 90&& beeSimple.angleOfSeenbee > beeList.get(beeList.size() - 1).angleBetwenTwoBee&& TIMER == 0) {
						super.ratoteImage(beeList.get(beeList.size() - 1).angleBetwenTwoBee + 5);
						setXYs(0);
						TIMER = 5;
					}else if (TIMER == 0) {
						super.ratoteImage(beeList.get(beeList.size() - 1).angleBetwenTwoBee - 5);
						setXYs(0);
						TIMER = 5;
					}else{
						super.ratoteImage(beeList.get(beeList.size() - 1).angleBetwenTwoBee);
						setXYs(0);
					}
				}else {
					super.ratoteImage(beeList.get(beeList.size() - 1).angleBetwenTwoBee);
					setXYs(0);
				}
				
			}
			/*super.ratoteImage(beeList.get(beeList.size() - 1).angleBetwenTwoBee);
			setXYs(0);*/
		}else if (isMissing) {
			isMissing = false;
			super.ratoteImage(angle + 160);
			setXYs(0);
		}
		else{
			//��������
			setXYs(0);
		}
		
		/*if (beeList.isEmpty()) {
			setXYs(0);	
		} else if (beeList.size() != 1) {
			
		} else {
			super.ratoteImage(beeList.get(0).angleBetwenTwoBee);
			setXYs(0);	
		}*/
		
		
	}
	/**����Ʒ�ץ�����۷䣬��boolean dead==true���Ʒ���Ը���dead��ֵ�ж��۷�֪��ɱ����
	�����������޸ģ���BeeFarming��killBee�����е��۷䱻�Ʒ�����󽫱�����*/
	public boolean isCatched(){
	    dead = true;
	    return dead;
	}
	
	/**�Ի�õ��ַ������мӹ������Ӧ��Ϣ
	 * @param flowerList
	 * @param beeList
	 * @param strVision
	 */
	private void getInformation(ArrayList <FlowerSimple> flowerList, ArrayList <BeeSimple> beeList, String strVision) {
		
		FlowerSimple flowerSimple;
		BeeSimple beeSimple;
		
		String[] strings = strVision.split("~");
		for (int i = 0; i < strings.length; i++) {
			if (strings[i].startsWith("+")) {
				beeSimple = new BeeSimple();
				String beeString = strings[i].split("\\(")[1];
				String idString = beeString.split("\\,")[0];
				String angleBetwenTwoBeeString = beeString.split("\\,")[1];
				String angleOfSeenbee = beeString.split("\\,")[2].split("\\)")[0];
				beeSimple.id = Integer.parseInt(idString);
				beeSimple.angleBetwenTwoBee = Double.valueOf(angleBetwenTwoBeeString);
				beeSimple.angleOfSeenbee = Double.valueOf(angleOfSeenbee);
				
				beeList.add(beeSimple);
				
			}else if (strings[i].startsWith("-")) {
				flowerSimple = new FlowerSimple();
				String flowerString = strings[i].split("\\(")[1];
				String volumnString = flowerString.split("\\,")[0];
				String angleString = flowerString.split("\\,")[1].split("\\)")[0];
				if (angleString.equals("ON")) {
					flowerSimple.angleOfFlower = super.angle;
				}else {
					flowerSimple.angleOfFlower = Double.valueOf(angleString);
				}
				
				flowerSimple.volumn = Integer.parseInt(volumnString);
				
				flowerList.add(flowerSimple);
			}
		}
	}
	  
	private class FlowerSimple{
		int volumn;
		double angleOfFlower;
	}

	private class BeeSimple{
		int id;
		double angleBetwenTwoBee;
		double angleOfSeenbee;
	}
	
}

/*class FlowerSimple{
	int volumn;
	double angleOfFlower;
}

class BeeSimple{
	int id;
	double angleBetwenTwoBee;
	double angleOfSeenbee;
}*/