import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class HoneyBee extends Bee{
	private int id;
	private boolean isDanger=false;
	private int num=0;
	public HoneyBee(int id,int x, int y, double angle,boolean isAlive,Image img){
		super(id,x,y,angle,isAlive,img);
		this.id = id;
	}
	
	/**�˷�������Ҫ��д�ĺ��Ĵ��룬�۷���۵���Ҫ�����ڴ�������*/
	public void search(){
		String strVision = BeeFarming.search(id); //����ֵΪresult��������
		//System.out.println("ID="+id);
		System.out.println(strVision);
		
		//���Ʒ���Ұ���и��۷�ʱ���۷䲻�����������㿪����һ���ļ�����˦���Ʒ�
		int HorBeeId = getHornetVision();
		//System.out.println("HorBeeId="+HorBeeId);
		if(HorBeeId==id){
			if(strVision.indexOf('-')!=-1){
				strVision="*";
			}
		}
		
		//�������*Ϊ�׵��ַ��������������˱ߣ����������˳ʱ����ת90�����ڵĽǶ�
		if(strVision.indexOf('*')==0)
		{	
			Random ra = new Random();
			angle += ra.nextInt(90);
			//angle+=80;    //�̶�˳ʱ����ת80��
			ratoteImage(angle);
		}
		
		//�������-Ϊ�׵��ַ��������������˻�����׼�Ƕ�ǰ������
		else if(strVision.indexOf('-')==0){
			int inde1 = strVision.indexOf(',');
			int inde2 = strVision.indexOf(')');
			if(strVision.indexOf("ON")==-1){
				angle = Double.valueOf(strVision.substring(inde1+1, inde2)).doubleValue();	//����ȡ��angle����stringת��Ϊdouble
				ratoteImage(angle);
			}
			
		}
		
		//�������+Ϊ�׵��ַ��������������������۷���߻Ʒ䣬��Ҫ�㿪
		else if(strVision.indexOf('+')==0){
			int inde1 = strVision.indexOf(',');
		    int inde2 = strVision.indexOf(',',inde1+1);
			angle = Double.valueOf(strVision.substring(inde1+1, inde2)).doubleValue();
			
			int bee_id = Integer.valueOf(strVision.substring(2, inde1)).intValue();
			if(bee_id==9)  {
				isDanger=handleDanger(angle);
				}
			else {
				//Random ra = new Random();
				//angle += ra.nextInt(90);   
				angle+=90;
				ratoteImage(angle);
			}
		}
	
		setXYs(0);
	}
	
	/** ���Ʒ����۷����Ұ�ڣ������Σ��*/
	public boolean handleDanger(double angle){
		isDanger = true;
		ratoteImage(angle+45);
		return false;
	}
	
	/**αװ�ɻƷ�鿴����Ұ����û���۷�*/
	public int getHornetVision(){
		String HornetVision = BeeFarming.search(9);
		int beeIdHor;
		if(HornetVision.indexOf('+')!=-1)
		{
			int inde1=HornetVision.indexOf("+(");
			int inde2=HornetVision.indexOf(',',inde1+1);
			beeIdHor = Integer.valueOf(HornetVision.substring(inde1+2, inde2)).intValue();	
		}
		else beeIdHor=9;
		
		return beeIdHor;
	}
	
	
}