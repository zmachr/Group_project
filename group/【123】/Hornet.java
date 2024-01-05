import java.awt.*;
import java.util.*;

public class Hornet extends Bee{
 
	private static final long serialVersionUID = 1L;
	
	private int id;
    private boolean dead = false;
 
    private int honeyBeeDist = 100; 
 
  //构造方法不做太多说明
    public Hornet(int id, int x, int y, double angle, boolean isAlive, Image img){
        super(id, x, y, angle, isAlive, img);
        this.id = id;
    }
     
    /**如果黄蜂抓到了蜜蜂，则boolean dead==true，黄蜂可以根据dead的值判断蜜蜂知否被杀死。
	本方法可以修改，在BeeFarming的killBee方法中当蜜蜂被黄蜂消灭后将被调用*/
    public boolean isCatched(){
        dead = true;
        return dead;
    }
    
    /**此方法是需要重写的核心代码，蜜蜂采蜜的主要个性在此类体现*/
    public void search() {
 
    	//strVision包含了视野内物体的信息
    	//视野内有花："-("开头；
    	//视野内有蜜蜂："+("开头；
    	//视野内有墙："*"开头；
        String strVision = BeeFarming.search(id);
        
        //如果碰到*为首的字符串，代表遇到了边
        if(strVision.indexOf('*') == 0 && strVision.indexOf("+(") == -1) {
     
        	//strVision.indexOf("+(") == -1代表视野里没有蜜蜂
        	//若碰到了边且视野里没有蜜蜂，则变换飞行方向
        	//此处有一个需要解决的问题：怎么让黄蜂尽量不碰边界
        	Random ra = new Random();
        	if(strVision.indexOf('N') == 1) angle = ra.nextInt(120) + 30;			
        	else if(strVision.indexOf('S') == 1) angle = ra.nextInt(120) + 210;
        	else if(strVision.indexOf('W') == 1) angle = ra.nextInt(60);
        	else if(strVision.indexOf('E') == 1) angle = ra.nextInt(60) + 180;
        	else ;
        	ratoteImage(angle); 
        	
        }
 
        //代表视野中有蜜蜂
        if(strVision.indexOf("+(") != -1) {
    
       //  	System.out.println(strVision);
        	this.angle = nextAngleCalculator(strVision); 
        	ratoteImage(angle);        
        	
        }
 
        else honeyBeeDist = 100;
         
        setXYs(0);
     
    }
 
    
    public double nextAngleCalculator(String strVision){
 
    	System.out.println(strVision);
    	
    	//方法返回的黄蜂下一个应该飞行追逐的方向
    	double nextAngle;
    	
    	//黄蜂看见蜜蜂的方向（即黄蜂中心点与蜜蜂中心店矢量的方向）
    	double seeBeeDir;
    	String seeBeeDirStr;
    	seeBeeDirStr = strVision.substring(strVision.lastIndexOf("+(")+4, strVision.lastIndexOf(','));
    	seeBeeDir = Double.parseDouble(seeBeeDirStr);
    	
    	//蜜蜂进入视野时飞行的方向（即蜜蜂本身的angle）
    	double beeFlyDir;
    	String beeFlyDirStr;
    	beeFlyDirStr = strVision.substring(strVision.lastIndexOf(',')+1,strVision.lastIndexOf(')'));
    	beeFlyDir = Double.parseDouble(beeFlyDirStr);
    	
    	//黄蜂下一个时刻飞到的点
    	int nextHornetX;
    	int nextHornetY;
    	
    	//蜜蜂此时相对于黄蜂的位置以及蜜蜂下一个时刻飞到的点
    	int honeyBeeX;
    	int honeyBeeY;
    	int nextHoneyBeeX;
    	int nextHoneyBeeY;
    	
    	honeyBeeX = (int)(honeyBeeDist * Math.cos(Math.toRadians(seeBeeDir)));
    	honeyBeeY = (int)(honeyBeeDist * Math.sin(Math.toRadians(seeBeeDir)));
    	nextHoneyBeeX = (int)(honeyBeeDist * Math.cos(Math.toRadians(seeBeeDir)) + 18 * Math.cos(Math.toRadians(beeFlyDir)));
    	nextHoneyBeeY = (int)(honeyBeeDist * Math.sin(Math.toRadians(seeBeeDir)) + 18 * Math.sin(Math.toRadians(beeFlyDir)));
    	
    	
    	//以黄蜂中心为原点建立动态坐标系计算蜜蜂下一个飞行点相对于黄蜂的角度nextAngle，作为黄蜂飞行的下一个方向。
    	//注意getVectorDegree方法传入的参数是int型，故需对计算结果进行强制类型转换。
    	nextAngle = BeeFarming.getVectorDegree(0, 0, nextHoneyBeeX, nextHoneyBeeY);
    	
    	nextHornetX =  (int)(18 * Math.cos(Math.toRadians(nextAngle)));
    	nextHornetY =  (int)(18 * Math.sin(Math.toRadians(nextAngle)));
    	
    	honeyBeeDist = (int)(Math.sqrt((Math.pow(nextHoneyBeeX - nextHornetX, 2)) 
    						+ (Math.pow(nextHoneyBeeY - nextHornetY,2))));
    	
    	return nextAngle;
    	
    }
 
}