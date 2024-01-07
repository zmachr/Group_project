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

	/**�˷�������Ҫ��д�ĺ��Ĵ��룬�۷���۵���Ҫ�����ڴ�������*/
	public void search(){
		String strVision = BeeFarming.search(id);
		if(strVision.length()>0) System.out.println(strVision);
		if(strVision.contains("+")==true)//����+Ϊ�׵��ַ���������������HoneyBee
		{
		    int k=strVision.indexOf("+");
            String result=strVision.substring(k+2,strVision.length());
            String a[]=result.split(",");//a[0]Ϊid��a[1]Ϊvector��a[2]Ϊangle
            //���벶ɱ״̬
            try
            {
                angle=Double.valueOf(a[1]).doubleValue();
                ratoteImage(angle);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();  //���������Ϣ
            }

		}
        else//��Ұ��û��HoneyBee,�Ҹ�����û��û������HoneyBee
        {
            for(int i=0;i<3;i++)
            {
                String strHoneyBee=BeeFarming.search(i);
                if(strHoneyBee.contains("+")==true)
                {
                    int j=strHoneyBee.indexOf("+");
                    String result=strHoneyBee.substring(j+2,strHoneyBee.length());
                    String b=result.substring(0,1);
                    if(Integer.parseInt(b)==9) ratoteImage(180);//��HoneyBee����Hornet
                }
            }
            if(strVision.contains("*")==true)//����*Ϊ�׵��ַ��������������˱�
            {
                Random ra = new Random();
                angle += ra.nextInt(90);
                ratoteImage(angle);
            }
            else
            {
                Random ra = new Random();
                angle += ra.nextInt(90);
                angle -= ra.nextInt(90);
                ratoteImage(angle);
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
