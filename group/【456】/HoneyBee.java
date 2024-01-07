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
		String strVision = BeeFarming.search(id);
		if(strVision.length()>0)System.out.println(strVision);
		if(strVision.contains("*")==true)//����*Ϊ�׵��ַ��������������˱�
        {
            Random ra = new Random();
            angle += ra.nextInt(90);
            ratoteImage(angle);
        }
		if(strVision.contains("+")==true)//����+Ϊ�׵��ַ���������������Bee
		{
		    int i=strVision.indexOf("+");
            String result=strVision.substring(i+2,strVision.length());
            String a[]=result.split(",");//a[0]Ϊid��a[1]Ϊvector��a[2]Ϊangle
            if(a[0].equals("9")==true)//����Hornet
            {
                //�۷�Զ��
                try
				{
                    Double angle=Double.parseDouble(a[1]);
                    ratoteImage(angle+180);
			    }
			    catch(Exception ex)
			    {
			        ex.printStackTrace();  //���������Ϣ
			    }
            }
            else//��������HoneyBee
            {
                if(strVision.indexOf("-")==0)//�һ�
                {
                    result=strVision.substring(2,strVision.length()-5);
                    String b[]=result.split(",");//b[0]Ϊvolumn��b[1]ΪON��vector
                    if(b[1].equals("ON")==false)
                    {
                        try
                        {
                        angle=Double.valueOf(b[1]).doubleValue();
                        ratoteImage(angle);
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();  //���������Ϣ
                        }
                    }
                }
            }
		}
		else//û�п����κ�Bee
        {
            //����Ƿ����ڱ�Hornet׷
            String strHornet=BeeFarming.search(9);
            if(strHornet.contains("+")==true)//Hornet����׷HoneyBee
            {
                int j=strHornet.indexOf("+");
                String result=strHornet.substring(j+2,strHornet.length());
                String c=result.substring(0,1);//c[0]Ϊid��c[1]Ϊvector��c[2]Ϊangle
                if(Integer.parseInt(c)==id)//Hornet����׷�Լ�
                {
                    Random ra = new Random();
                    angle += ra.nextInt(90);
                    angle -= ra.nextInt(90);
                    ratoteImage(angle);//���ܲ���
                }
                else//Hornet��׷����HoneyBee
                {
                    if(strVision.indexOf("-")==0)//�һ�
                    {
                        result=strVision.substring(2,strVision.length()-2);
                        String d[]=result.split(",");//d[0]Ϊvolumn��d[1]ΪON��vector
                        if(d[1].contains("-")==true) d[1]=d[1].substring(0,d[1].indexOf("-")-2);
                        if(d[1].equals("ON")==false)
                        {
                            try
                            {
                                angle=Double.valueOf(d[1]).doubleValue();
                                ratoteImage(angle);
                            }
                            catch(Exception ex)
                            {
                                ex.printStackTrace();  //���������Ϣ
                            }
                        }
                    }
                }
            }
            else//Hornetû����HoneyBee
            {
                if(strVision.contains("-")==true)//�һ�
                {
                    String result=strVision.substring(strVision.indexOf("-")+2,strVision.length()-2);
                    String d[]=result.split(",");//d[0]Ϊvolumn��d[1]ΪON��vector
                    if(d[1].contains("-")==true) d[1]=d[1].substring(0,d[1].indexOf("-")-2);
                    if(d[1].equals("ON")==false)
                    {
                        try
                        {
                            angle=Double.valueOf(d[1]).doubleValue();
                            ratoteImage(angle);
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();  //���������Ϣ
                        }
                    }
                }
            }
        }
        setXYs(0);
	}
}
