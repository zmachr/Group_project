import java.awt.*;
import java.awt.image.*;
import java.util.*;
public class Hornet extends Bee{
	private int id;
	private boolean dead=false;
	private boolean seen=false;
	public Hornet(int id,int x, int y, double angle,boolean isAlive,Image img){
		super(id,x,y,angle,isAlive,img);
		this.id = id;
	}
	int time = 0;
	int ftime = 0;
	boolean choose = false;
	/**�˷�������Ҫ��д�ĺ��Ĵ��룬�۷���۵���Ҫ�����ڴ�������*/
	public void search()
	{
		String strVision = BeeFarming.search(id);
		
		ftime ++;
		//�����۷䣬ȷ���ǶȲ�����
		if(strVision.indexOf('+')!=-1)
		{
			time ++;
			int inde1 = strVision.indexOf('+');
			int inde2 = strVision.lastIndexOf('~');
			String str1 = new String();
			str1 = strVision.substring(inde1+1, inde2);
			    
			//��ֹ����ͬʱ������ֻ�۷��޷�ѡ��Ƕ����
			if(str1.contains("+"))
			{
				int ind1 = str1.indexOf('(');
	            int ind2 = str1.indexOf('~');
	            str1 = str1.substring(ind1, ind2);
			}
			
			//�����Ƕ�׼������
			int inde3 = str1.indexOf(',');
			int inde4 = str1.lastIndexOf(',');
			angle = Double.valueOf(str1.substring(inde3+1, inde4)).doubleValue();
			
			//�Ʒ䲻�ܷ���
			/*if(time == 20)
			{
				angle = - angle ;
				time = 0;
			}*/
			ratoteImage(angle);
		}
   
		//������ʱ�Ʒ��򻨷ɣ���Ѱ���۷�
		else if((strVision.indexOf('-')!=-1)&&(ftime ==5))
		{
			ftime =0;
			String[] str2 = strVision.split("~");
			for(int i = 0; i<str2.length;i++)
			{
				if(str2[i].contains("-"))
				{
					if(str2[i].indexOf("ON")==-1)
					{
					int inde1 = str2[i].indexOf(',');
				    int inde2 = str2[i].indexOf(')');
					angle = Double.valueOf(str2[i].substring(inde1+1, inde2)).doubleValue();
					}
				}
				ratoteImage(angle);
			}
		}
		//û���۷�,û�л���������ǽ
		else if(strVision.indexOf("*")!=-1)
		{
			Random ra = new Random();
			angle += ra.nextInt(90);
			ratoteImage(angle);
		}
		//���۷䣬��ǽ���
	
	 else 
		{
			
				if(choose==false)
				{
					Random ra = new Random();
					angle += ra.nextInt(90);
					ratoteImage(angle);
					choose = true;
				}
				else
				{
					Random ra = new Random();
					angle -= ra.nextInt(90);
					ratoteImage(angle);
					choose = false;
				}
		
		}
		setXYs(0);	
	}
		

	/**����Ʒ�ץ�����۷䣬��boolean dead==true���Ʒ���Ը���dead��ֵ�ж��۷�֪��ɱ����
	�����������޸ģ���BeeFarming��killBee�����е��۷䱻�Ʒ�����󽫱�����*/
	public boolean isCatched(){
	    dead = true;
	    return dead;
	}
}