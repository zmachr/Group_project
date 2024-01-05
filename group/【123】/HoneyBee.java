//angle�ľ��庬�壺����0������90������180������270��
//����ratoteImage(angle)�������з���ĵ�����
//result�����ǹؼ������������Ұ���������Ϣ������ͨ���ж�result��һ���ַ���ֵ���ж���Ұ�ھ�����ʲô��
//����result��ȡ�Ƕȵ�ʱ����Ҫע��λ����ѡ��Ҫ�������е�����������ҵ�.��λ�ã�ǰ���ȡ�����������ȡһλС��
//Ҫ������Ұ���ж�仨�����

import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class HoneyBee extends Bee{
	
	private int id;
	private int dangerBeeId = -1;
	private boolean danger = false;
	private int num = 0;
	
	int turnCount = 0;
	
	public HoneyBee(int id ,int x, int y, double angle, boolean isAlive, Image img) {
		super(id, x, y, angle, isAlive, img);
		this.id = id;
	}


	/**�˷�������Ҫ��д�ĺ��Ĵ��룬�۷���۵���Ҫ�����ڴ�������*/
	public void search(){ 
		
		//�۷��ȡ�Ʒ����Ұ
		String hornetVision = BeeFarming.search(9); 
		//�۷��Լ�����Ұ
		String honeyBeeVision = BeeFarming.search(this.id); 
		
		//���Ʒ����Ұ�г������۷䣬�ö�Ӧid���۷��������ѷ�Ӧ
		if(hornetVision.indexOf("+(") != -1) {

			String dangerBeeStr = hornetVision.substring(hornetVision.lastIndexOf("+(")); 
			dangerBeeId = Integer.parseInt(dangerBeeStr.substring(2, 3));
			
			if(this.id == dangerBeeId) {
				this.danger = true;
			}
			else {
				this.danger = false;
				turnCount = 0;
			}
			
			if(danger == true) {		
				if(turnCount == 0){
					angle = (angle + 100) % 360;
					turnCount = 1;
				}
				if(honeyBeeVision.indexOf('-') == 0) {
					angle = (angle + 120) % 360;
				}
			}
			
		} 
		else {
			this.danger = false;
		}
		
		//���۷����Ұ������˻Ʒ�������ͷ
		if(honeyBeeVision.indexOf("+(9") != -1 && danger == false) {
			angle = (angle + 180) % 360;	
		}
		
		//���۷䲻Σ������Ұ���л�
		if(danger == false && honeyBeeVision.indexOf('-') != -1 && honeyBeeVision.contains("ON") == false) {
			
			String getFlowerStr = honeyBeeVision.substring(honeyBeeVision.indexOf("-("));
			int pointLocation = getFlowerStr.indexOf('.');
			String flowerStr = getFlowerStr.substring(5, pointLocation + 2);
			angle = Double.parseDouble(flowerStr) % 360;
				
		}
		
		//����������жϣ��ڶ���������ʾ�ɲ�΢����Ļ���
        if(honeyBeeVision.indexOf('*') == 0 && honeyBeeVision.indexOf('-') == -1) {

        	Random ra = new Random();
        	
        	if(honeyBeeVision.indexOf('N') != -1) {	
        		if(angle <= 360 && angle > 270) angle = (angle + 70 + ra.nextInt(40)) % 360;
        		else angle = (angle - 70 - ra.nextInt(40)) % 360;
        	}
        	if(honeyBeeVision.indexOf('S') != -1) {	
        		if(angle < 90 && angle >= 0) angle = (angle + 250 + ra.nextInt(20)) % 360;
        		else angle = (angle + 70 + ra.nextInt(40)) % 360;
        	}
        	if(honeyBeeVision.indexOf('W') != -1) {	
        		if(angle < 180 && angle > 90) angle = (angle - 70 - ra.nextInt(20)) % 360;
        		else angle = (angle + 70 + ra.nextInt(40)) % 360;
        	}
        	if(honeyBeeVision.indexOf('E') != -1) {	
        		if(angle < 90 && angle >= 0) angle = (angle + 70 + ra.nextInt(40)) % 360;
        		else angle = (angle - 70 - ra.nextInt(20)) % 360;
        	}
        		
        	
        }
		
        ratoteImage(angle); 
		setXYs(0);

	}
}	
		
		
