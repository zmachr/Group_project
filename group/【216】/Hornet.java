import java.awt.*;
import java.awt.image.*;
import java.util.*;
public class Hornet extends Bee{
    /**�۷䵱ǰ������*/
	private int id;
	private boolean dead=false;
	public Hornet(int id,int x, int y, double angle,boolean isAlive,Image img){
		super(id,x,y,angle,isAlive,img);
		this.id = id;
	}
	/**�˷�������Ҫ��д�ĺ��Ĵ��룬�۷���۵���Ҫ�����ڴ�������*/
	public void search(){
		String strVision = BeeFarming.search(id);
		String[] strs = strVision.split("~");
		for(int i=0;i<strs.length;i++){
			//�������*Ϊ�׵��ַ��������������˱ߣ����������˳ʱ����ת90�����ڵĽǶ�
			if(strs[i].indexOf('*')==0)
			{			
				Random ra = new Random();
				angle += ra.nextInt(45)+45;
				ratoteImage(angle);
			}
			if(strs[i].indexOf('+')==0){
				String strTmp = strs[i];
				int start = strTmp.indexOf('(');
				int end = strTmp.indexOf(',');
				String s = strTmp.substring(start+1,end);
				int id = new Integer(s).intValue();
				strTmp = strTmp.substring(end+1);
				end = strTmp.indexOf(',');
				s = strTmp.substring(0,end);
				{
					double a = new Double(s).doubleValue();
					angle = a;
					ratoteImage(angle);
					break;
					//System.out.println("��ת��"+angle);
				}//else
					//System.out.println("ON flower");

			}			
			else if(strs[i].indexOf('-')==0)
			{
				String strTmp = strs[i];
				int start = strTmp.indexOf('(');
				int end = strTmp.indexOf(',');
				String s = strTmp.substring(start+1,end);
				int honey = new Integer(s).intValue();
				strTmp = strTmp.substring(end+1);
				end = strTmp.indexOf(')');
				s = strTmp.substring(0,end);
				 if(!s.equals("ON")){
					double a = new Double(s).doubleValue();
					angle = a;
					ratoteImage(angle);
					
					//System.out.println("��ת��"+angle);
				}
			}
		}
		//System.out.println(strVision);
		setXYs(0);
		//System.out.println("vx="+fs1.x+",vy="+fs1.y);		
	}
public boolean isCatched(){
	    dead = true;
	    return dead;
	}
	  
}