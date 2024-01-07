import java.awt.*;
import java.awt.image.*;
import java.util.*;
public class Hornet extends Bee{
	private int id;
	private boolean dead=false;
	private boolean seen=false;
	private int num=0;
	private boolean isDanger=false;
	public Hornet(int id,int x, int y, double angle,boolean isAlive,Image img){
		super(id,x,y,angle,isAlive,img);
		this.id = id;
	}
	
	/**�˷�������Ҫ��д�ĺ��Ĵ��룬�۷���۵���Ҫ�����ڴ�������*/
	public void search(){
		String strVision = BeeFarming.search(id);	

		if (strVision.startsWith("*")) {
			this.num++;
			System.out.println("�Ʒ俴��ǽ��"+strVision);
			Random ra = new Random();
			double deltAngle = 0.0;
			if((strVision.indexOf('S')==1) || (strVision.indexOf('N')==1)){
				angle = (360-angle+deltAngle)%360;
				System.out.println(angle);
				ratoteImage(angle);
			}
			else {
				angle = (((540-angle)%360)+deltAngle)%360;
				ratoteImage(angle);
			}
			strVision = strVision.substring(strVision.indexOf('~')+1);
		}
		else {
			this.num = 0;
		}
		
		while (strVision.startsWith("-(")) {
			strVision = strVision.substring(strVision.indexOf('~')+1);
		}
	
		if (strVision.startsWith("+(")) {
			System.out.println("�Ʒ俴���۷䣺"+strVision);
			while (strVision.indexOf('+') != strVision.lastIndexOf('+')) {
				strVision = strVision.substring(strVision.indexOf('~')+1);
			}
			String beeAngle = strVision.substring(strVision.indexOf(',')+1,strVision.lastIndexOf(','));
			angle = Double.parseDouble(beeAngle);
			ratoteImage(angle);
		}
		
		setXYs(0);	
	}
	
	/**����Ʒ�ץ�����۷䣬��boolean dead==true���Ʒ���Ը���dead��ֵ�ж��۷�֪��ɱ����
	�����������޸ģ���BeeFarming��killBee�����е��۷䱻�Ʒ�����󽫱�����*/
	public boolean isCatched(){
	    dead = true;
	    return dead;
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
	  
}