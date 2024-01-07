import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class HoneyBee extends Bee{
	private int id;
	private boolean isDanger=false;
	private int num=0;
	private int count=0;
	public HoneyBee(int id,int x, int y, double angle,boolean isAlive,Image img){
		super(id,x,y,angle,isAlive,img);
		this.id = id;
	}
	
	/**�˷�������Ҫ��д�ĺ��Ĵ��룬�۷���۵���Ҫ�����ڴ�������*/
public void search(){
	
		String strVision = BeeFarming.search(id);
		System.out.println(strVision);
		
		
		if (this.isDanger == true) {
			System.out.println("������"+this.isDanger + "������" + this.num);
			if (strVision.startsWith("*")){
				if((strVision.indexOf('S')==1) || (strVision.indexOf('N')==1) || (strVision.indexOf('W')==1) || (strVision.indexOf('E')==1)){
					this.num++;
				}
			}
			
			if ( this.num >= 1 ) {
				if (strVision.indexOf('S')!=-1) angle = 0.0;
				if (strVision.indexOf('N')!=-1) angle = 180.0;
				if (strVision.indexOf('W')!=-1)angle = 90.0;
				if (strVision.indexOf('E')!=-1) angle = 270.0;
				if ((strVision.indexOf('S')!=-1) && (strVision.indexOf('W')!=-1)) angle=315.0;
				if ((strVision.indexOf('S')!=-1) && (strVision.indexOf('E')!=-1)) angle=225.0;
				if ((strVision.indexOf('N')!=-1) && (strVision.indexOf('W')!=-1)) angle=45.0;
				if ((strVision.indexOf('N')!=-1) && (strVision.indexOf('E')!=-1)) angle=135.0;
				if (!strVision.startsWith("*")) {this.isDanger = false;this.num = 0;}
				
				ratoteImage(angle);
				
			}
		}
		else if (strVision.startsWith("*")) {
				String subString1 = strVision.substring(strVision.indexOf('~')+1);
				//ֻ��ǽ��
				if (subString1.equals("")) {
					Random ra = new Random();
					double deltAngle = ra.nextInt(30);
					if((strVision.indexOf('S')==1) || (strVision.indexOf('N')==1)){
						angle = (360-angle+deltAngle)%360;
						//angle = adjustAngle(angle);
						ratoteImage(angle);
					}
					else {
						angle = (((540-angle)%360)+deltAngle)%360;
						//angle = adjustAngle(angle);
						ratoteImage(angle);
					}
				}
				
				//��ǽ�����л�
				else if (subString1.startsWith("-")) {
					String subString2 = subString1.substring(subString1.indexOf('~')+1);
					//�л���û�лƷ���Ȳɻ�
					if (subString2.equals("")) {
						String flowerAngle = subString1.substring(subString1.indexOf(',')+1,subString1.indexOf(')'));
						if (!flowerAngle.equals("ON")) {
							angle = Double.parseDouble(flowerAngle);
							ratoteImage(angle);
						}
					}
					else if (subString2.startsWith("+")){
						int honeyBeeId = Integer.parseInt(subString2.substring(subString2.indexOf('(')+1,subString2.indexOf('(')+2));
						//�����Ʒ��������
						if (honeyBeeId == 9){
							System.out.println("��Σ������");
							this.isDanger = true;
							String hornetAngle = subString2.substring(subString2.lastIndexOf(',')+1,subString2.indexOf(')'));
							angle = Double.parseDouble(hornetAngle);
							angle = angle+90;//��ȡ��ֱ�ڻƷ�ķ�������
							System.out.println("Hornet: "+angle);
							ratoteImage(angle);
						}
						else {
					
						}
					}
					else {
					
					}

				}
				else if (subString1.startsWith("+")) {
					int honeyBeeId = Integer.parseInt(subString1.substring(subString1.indexOf('(')+1,subString1.indexOf('(')+2));
					//�����Ʒ��������
					if (honeyBeeId == 9){
					//	System.out.println("��Σ������");
						this.isDanger = true;
						String hornetAngle = subString1.substring(subString1.lastIndexOf(',')+1,subString1.indexOf(')'));
						angle = Double.parseDouble(hornetAngle);
						angle = angle+90;//��ȡ��ֱ�ڻƷ�ķ�������
						System.out.println("Hornet: "+angle);
						ratoteImage(angle);
					}
					else {
					//	System.out.println("��С�������");
						String honeyBeeAngle = subString1.substring(subString1.lastIndexOf(',')+1,subString1.indexOf(')'));
						angle = Double.parseDouble(honeyBeeAngle) + 120;
						ratoteImage(angle);
					}
				}
			}
		else if (strVision.startsWith("-(")) {
			String subString1 = strVision.substring(strVision.indexOf('~')+1);
			//ֻ�л��Ͳɻ�
			if (subString1.equals("")) {
				String flowerAngle = strVision.substring(strVision.indexOf(',')+1,strVision.indexOf(')'));
				if (!flowerAngle.equals("ON")) {
					angle = Double.parseDouble(flowerAngle);
					ratoteImage(angle);
				}
			}
			else if (subString1.startsWith("+")) {
				int honeyBeeId = Integer.parseInt(subString1.substring(subString1.indexOf('(')+1,subString1.indexOf('(')+2));
				//�����Ʒ��������
				if (honeyBeeId == 9){
					System.out.println("��Σ������");
					this.isDanger = true;
					String hornetAngle = subString1.substring(subString1.lastIndexOf(',')+1,subString1.indexOf(')'));
					angle = Double.parseDouble(hornetAngle);
					angle = angle+90;//��ȡ��ֱ�ڻƷ�ķ�������
					System.out.println("Hornet: "+angle);
					ratoteImage(angle);
				}
				else {
				
				}
			}
			
		}
		else if (strVision.startsWith("+(")) {
			while (strVision.startsWith("+(")) {
				int beeId = Integer.parseInt(strVision.substring(strVision.indexOf('(')+1,strVision.indexOf('(')+2));
				String subString1 = strVision.substring(strVision.indexOf('+'), strVision.indexOf('~'));
				if (beeId == 9) {
					this.isDanger = true;
					String hornetAngle = subString1.substring(subString1.lastIndexOf(',')+1,subString1.indexOf(')'));
					angle = Double.parseDouble(hornetAngle);
					angle = angle+90;//��ȡ��ֱ�ڻƷ�ķ�������
					ratoteImage(angle);
					break;
				}
				else {
					String honeyBeeAngle = subString1.substring(subString1.lastIndexOf(',')+1,subString1.indexOf(')'));
					angle = Double.parseDouble(honeyBeeAngle);
					angle = angle+120;//��ȡ120��ǲ��Էֿ�Ѱ�һ�
					ratoteImage(angle);
				}
				strVision = strVision.substring(strVision.indexOf('~')+1);
				
			}
		}
		else{
			this.count++;
			if (count%2==0) {angle+=30;}
			else {angle=(angle+360-30)%360;}
			ratoteImage(angle);
		}
		
		setXYs(0);
	}
	
	/**�����Ƕȣ����ⴹֱ����**/
	public double adjustAngle(double angle){
		if(angle<10 && angle>=0){
			angle = angle + 30;
		}
		else if(angle<95 && angle>85){
			angle = angle + 40;
		}
		else if(angle<185 && angle>175){
			angle = angle - 40;
		}
		else if(angle<275 && angle>265){
			angle = angle - 40;
		}
		
		return angle;
	}
	
	public boolean getIsDanger()
	{	
		return this.isDanger;
	}
}