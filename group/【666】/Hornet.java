import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.lang.*;

public class Hornet extends Bee {

	private int id;
	private boolean dead = false;
	private boolean seen = false;
	//
	public boolean searchright = true; // �� ��ʾ����ת ���ʾ����ת
	public int searchturn = 0; // ��ʾ��ת�ֻص���Ŀ

	private int ratote = 0;
	private int distance_now = 100;

	public Hornet(int id, int x, int y, double angle, boolean isAlive, Image img) {
		super(id, x, y, angle, isAlive, img);
		this.id = id;
		this.ratote = 0;
	}

	/** �˷�������Ҫ��д�ĺ��Ĵ��룬�۷���۵���Ҫ�����ڴ������� */
	public void search() {

		String strVision = BeeFarming.search(id);
	  
		// �������*Ϊ�׵��ַ��������������˱ߣ����������˳ʱ����ת90�����ڵĽǶ�
		if (strVision.isEmpty()) {
			// �������ʱ��û�������κζ��������ʱ��ͽ���������ת �����Լ�����Ұ
			Random ra = new Random();
			if (searchright) {
				if (searchturn < 3) {
					searchturn++;

				} else {
					angle = angle + ra.nextInt(90);
					searchright = false;
					searchturn = 0; // ����Ĳ���
				}

			} else {
				if (searchturn < 5) {
					searchturn++;

				} else {
					angle = angle - ra.nextInt(60);
					searchright = true;
					searchturn = 0; // ����Ĳ���
				}

			}
			ratoteImage(angle);

		} else {

			if (strVision.indexOf('*') == 0) {
				if (ratote == -1)
					angle = angle;
				else if (strVision.indexOf("+(") == -1) {
					if (strVision.indexOf("*N") != -1) {
						angle = 90;
					} else if (strVision.indexOf("*S") != -1) {
						angle = 270;
					} else if (strVision.indexOf("*E") != -1) {
						angle = 180;
					} else {
						angle = 0;
					}
				} else {
					ratote += 1;
				}
				ratoteImage(angle);
			} else
				ratote = 0;

			if (ratote >= 20) {
				angle = angle + 180;
				ratoteImage(angle);
				ratote = -1;
			}
			if (strVision.indexOf("+(") != -1) {
				if (ratote != -1) { // ����׷���Ĳ���ĵ���
					this.angle = tracert(strVision);
					ratoteImage(angle);
				}
			} else {
				distance_now = 100;// ���¼��㵱ǰ���Ӿ�����
			}

		}

		setXYs(0);
	}

	/**
	 * ����Ʒ�ץ�����۷䣬��boolean dead==true���Ʒ���Ը���dead��ֵ�ж��۷�֪��ɱ����
	 * �����������޸ģ���BeeFarming��killBee�����е��۷䱻�Ʒ�����󽫱�����
	 */
	public boolean isCatched() {
		dead = true;
		return dead;
	}

	/**
	 * �����Ӿ��ľ࣬ �������Ҫת�ĵ���
	 * 
	 * @param strVision
	 * @return
	 */
	public double tracert(String strVision) {
		double honey = -1;
		// �����������ĽǶȺͷ��еĽǶ�
		double seeAngle_double = 0;
		double flyAngle_double = 0;
		String seeAngle_double_str = null;
		String seeAngle = null;
		String flyAngle = null;
		String seeAngle_double_str_honey = null;
		String seeAngle_double_str_honey_2 = null;
		double angle_tracert = 0;
		int honeybee_posx_next;
		int honeybee_posy_next;
		int honeybee_posx_now;
		int honeybee_posy_now;
		int hornet_posx_next;
		int hornet_posy_next;
		int distance_honeybee;
		int distance1_honeybee;
		int distance2_honeybee;

		/**
		 * ��ʾҲ����������Ϣ
		 */
		if (strVision.indexOf("-(") != -1) {
			seeAngle_double_str_honey = strVision.substring(strVision
					.indexOf("-("));
			seeAngle_double_str_honey_2 = seeAngle_double_str_honey.substring(
					seeAngle_double_str_honey.indexOf(',') + 1,
					seeAngle_double_str_honey.indexOf(')'));
			if(!seeAngle_double_str_honey_2.equals("ON"))
 {
				honey = Double.valueOf(seeAngle_double_str_honey_2);}
		}
		seeAngle_double_str = strVision.substring(strVision.indexOf("+(") + 4);
		seeAngle = seeAngle_double_str.substring(0,
				seeAngle_double_str.indexOf(','));
		flyAngle = seeAngle_double_str.substring(
				seeAngle_double_str.indexOf(',') + 1,
				seeAngle_double_str.indexOf(')'));
		seeAngle_double = Double.valueOf(seeAngle);
		flyAngle_double = Double.valueOf(flyAngle);
		seeAngle_double = seeAngle_double % 360;
		flyAngle_double = (flyAngle_double + 360) % 360;

		honeybee_posx_now = (int) (distance_now * Math.cos(Math
				.toRadians(seeAngle_double)));
		honeybee_posy_now = (int) (distance_now * Math.sin(Math
				.toRadians(seeAngle_double)));
		distance1_honeybee = (int) (Math
				.pow((0 + 18 * Math.cos(Math.toRadians(flyAngle_double)) + honeybee_posx_now),
						2) + Math
				.pow((0 + 18 * Math.sin(Math.toRadians(flyAngle_double)) + honeybee_posx_now),
						2));
		distance2_honeybee = (int) (Math
				.pow((0 - 18 * Math.cos(Math.toRadians(flyAngle_double)) + honeybee_posx_now),
						2) + Math
				.pow((0 - 18 * Math.sin(Math.toRadians(flyAngle_double)) + honeybee_posx_now),
						2));
		honeybee_posx_next = (int) (distance_now
				* Math.cos(Math.toRadians(seeAngle_double)) + 18 * Math
				.cos(Math.toRadians(flyAngle_double)));
		honeybee_posy_next = (int) (distance_now
				* Math.sin(Math.toRadians(seeAngle_double)) + 18 * Math
				.sin(Math.toRadians(flyAngle_double)));

		distance_honeybee = (int) (Math.pow(honeybee_posx_now, 2) + Math.pow(
				honeybee_posy_now, 2));

		if (distance_honeybee <= Math.pow(50, 2)) {
			if (distance1_honeybee < distance2_honeybee) {
				angle_tracert = seeAngle_double;
			} else
				;
		} else if (honey == seeAngle_double)
			angle_tracert = seeAngle_double;

		angle_tracert = (BeeFarming.getVectorDegree(0, 0, honeybee_posx_next,
				honeybee_posy_next)) % 360;

		hornet_posx_next = (int) (18 * Math.cos(Math.toRadians(angle_tracert)));
		hornet_posy_next = (int) (18 * Math.sin(Math.toRadians(angle_tracert)));

		distance_now = (int) (Math.sqrt((Math.pow(honeybee_posx_next
				- hornet_posx_next, 2))
				+ (Math.pow(honeybee_posy_next - hornet_posy_next, 2))));

		return angle_tracert;
	}
}
