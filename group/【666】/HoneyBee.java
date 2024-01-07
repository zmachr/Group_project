import java.awt.*;
import java.awt.image.*;
import java.lang.invoke.SwitchPoint;
import java.util.*;
import java.util.List;

public class HoneyBee extends Bee {
	private int id;
	private int num = 0;
	private int x; // ��ó�ʼ������
	private int y;
	private double angle; // ��ó�ʼ�ĽǶ����ڼ�¼���ڵĽǶ� //�����
	private double time; // ������ȡʱ��
	private int speed = 18;
	// ����Ϊ��ֻ�۷��״̬��ֵ
	public static int FIRST_STATUS = 1; // 1��ʾѰ�һ��۵� 2��ʾΣ��
	public static int SECOND_STATUS = 1;
	public static int THIRD_STATUS = 1;
	// ���ڼ�¼Σ�յ�ʱ��
	public static double FIRST_DANGERTIME = 0;
	public static double SECOND_DANGERTIME = 0;
	public static double THIRD_DANGERTIME = 0;
	//
	public boolean searchright = true; // �� ��ʾ����ת ���ʾ����ת
	public int searchturn = 0; // ��ʾ��ת�ֻص���Ŀ
	//
	public int start = 0; // ��ʾ�ڼ��ֿ�ʼͨ����ֵΪ��
	// ��ǽ���ʱ�����ʾ����Ϣ
	public int timer = 15; // ��׷����ǽ�������ʱ������ݵĲ���
	public int thetimes = 0;
	public boolean mm = false;

	public HoneyBee(int id, int x, int y, double angle, boolean isAlive,
			Image img) {
		super(id, x, y, angle, isAlive, img);
		this.id = id;
		this.x = x;
		this.y = y;
		this.angle = angle;
	}

	/** �˷�������Ҫ��д�ĺ��Ĵ��룬�۷���۵���Ҫ�����ڴ������� */
	public void search() {
		String str = BeeFarming.search(9);
		if (str.indexOf("+") != -1) {//
			Random ra = new Random();
			DeleteDanger(1);
			DeleteDanger(2);
			DeleteDanger(3);
			String[] strVisionStrings = str.split("~");
			for (int i = 0; i < strVisionStrings.length; i++) {
				String tempsString = strVisionStrings[i];
				if (tempsString.startsWith("+")) {// ���������۷�ķ�����Ϣ
					String[] mString = tempsString.split(","); // ���������۷�ķ�����Ϣ
					String beeId = mString[0]
							.substring(mString[0].indexOf("(") + 1); // �����۷��ID����
					String beeflyAngle = mString[2].substring(0,
							mString[2].indexOf(")")); // �����۷�ķ��з���
					String seeAngle = mString[1]; // �����۷�ķ���
					InformInDanger(Integer.parseInt(beeId));
					// if ((this.angle - Double.parseDouble(beeflyAngle)) % 360
					// < 90) {
					// angle += 180;
					// }

				}

			}

		}

		thetimes++;
		String strVision = BeeFarming.search(id);
		switch (getStatus(id)) {
		case 1:
			runInNormal(strVision);
			break;
		case 2:
			runInDanger(strVision);
			break;
		}
		// ÿ�ε��ȵ�����ʱ��Ĳ��Ե���

		caculatePosition(start);
		setXYs(start);
		start = 0;// ÿһ�ֽ���֮��Ҫ���Լ��ĳ��Ե�λ�õ�ֵΪ��
	}

	/**
	 * ���㵱ǰ��λ����Ϣ
	 * 
	 * @param start
	 */
	private void caculatePosition(int start) {
		this.x += (int) (speed * (9 - start) / 9.0 * Math.cos(Math
				.toRadians(angle)));
		this.y += (int) (speed * (9 - start) / 9.0 * Math.sin(Math
				.toRadians(angle)));
	}

	/*
	 * ����������µĲ��Ե���
	 */
	private void runInNormal(String strVion) {
		//

		if (strVion.isEmpty()) {
			// �������ʱ��û�������κζ��������ʱ��ͽ���������ת �����Լ�����Ұ
			Random ra = new Random();
			if (searchright) {
				if (searchturn < 5) {
					searchturn++;

				} else {
					angle = angle + ra.nextInt(60);
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

			String[] strVisionStrings = strVion.split("~");
			for (int i = strVisionStrings.length - 1; i >= 0; i--) { // ������������ĵط��������ȿ��۷ۣ��ٿ������ٿ�ǽ
				String tempsString = strVisionStrings[i];
				if (tempsString.startsWith("*")) { // ����ǽ�ķ�����Ϣ
					Random ra = new Random();
					angle += ra.nextInt(30) + 15; // h
					ratoteImage(angle);
				} else if (tempsString.startsWith("-")) { // ���뻨�ķ�����Ϣ
					String[] mString = tempsString.split(",");

					mString[1] = mString[1].substring(0,
							mString[1].indexOf(")"));
					String honeyleft = mString[0].substring(mString[0]
							.indexOf("(") + 1); // ���ʣ��ķ��ڵ�����
					if (mString[1].equals("ON")) {
						start = Integer.parseInt(honeyleft);
						if (start >= 10) { // �������۵������Ǵ���10
							start = 0;
						}
						ratoteImage(angle);
					} else if (mString[1] != null) {
						double angle = Double.parseDouble(mString[1]);
						ratoteImage(angle);
					}
				} else if (tempsString.startsWith("+")) {// ���������۷�ķ�����Ϣ
					String[] mString = tempsString.split(","); // ���������۷�ķ�����Ϣ
					String beeId = mString[0]
							.substring(mString[0].indexOf("(") + 1); // �����۷��ID����
					String beeflyAngle = mString[2].substring(0,
							mString[2].indexOf(")")); // �����۷�ķ��з���
					String seeAngle = mString[1]; // �����۷�ķ���
					if (beeId.equals("9")) { // ��ʾ���� �Ʒ�
						double beeflyAngle_double = Double
								.parseDouble(beeflyAngle);
						angle += 180;
						InformInDanger(id); // ���Լ�������Σ�յ�״̬
						ratoteImage(angle);
					} else { // ���������ĻƷ䣬���ȼ���Ƿ��Ǵ���Σ�յ�״̬��
						if (getStatus(id) == 2) {// ���߶Է��Լ���
							InformInDanger(Integer.parseInt(beeId));
						}
					}

				}
			}
		}
	}

	/*
	 * Σ�յ�����µĲ��Ե���
	 */
	private void runInDanger(String strVison) {
		String[] strVisionStrings = strVison.split("~");
		// ȡ�������������� �浽��Ӧ��map֮��
		Random ra = new Random();
		for (int i = strVisionStrings.length - 1; i >= 0; i--) {
			String tempsString = strVisionStrings[i];
			if (tempsString.startsWith("*")) { // ����ǽ����Ϣ
				// ���ڴ���Σ�յ�״̬�����߷ɹ� �Ͽ��ӱ�
				// if(angle%360<450){
				angle += ra.nextInt(30) + 15;
				// }else if(angle%360>90)

				ratoteImage(angle);
				// Ϊ�˱�����˲����� ������һ����ʱ�� ÿһ�ֶ���1��5�ֹ��󣬼����Χ�Ļ�����״������ȫ�����֮�£������뵽�����quyu
				timer--;
				if (timer < 0) { // �������ʱ���Ѿ�����֮����
					// ratoteImage(angle + 180);
					timer = 15;
					DeleteDanger(id); // ����Լ���Σ�յ�״̬
				}

			} else if (tempsString.startsWith("-")) { // ���뻨����Ϣ
				String[] mString = tempsString.split(",");
				mString[1] = mString[1].substring(0, mString[1].indexOf(")"));
				if (mString[1].equals("ON")) {
					ratoteImage(angle);
				} else {
					double flowerangle = Double.parseDouble(mString[1]);
					if (this.angle - flowerangle < 10) { // Ϊ�˱���˻�
						angle += 10;
					}
					ratoteImage(angle);
				}
			} else if (tempsString.startsWith("+")) {// ���������۷����Ϣ
				String[] mString = tempsString.split(","); // ���������۷�ķ�����Ϣ
				String beeId = mString[0]
						.substring(mString[0].indexOf("(") + 1); // �����۷��ID����
				String beeflyAngle = mString[2].substring(0,
						mString[2].indexOf(")")); // �����۷�ķ��з���
				String seeAngle = mString[1]; // �����۷�ķ���
				if (beeId.equals("9")) { // ��ʾ���� �Ʒ�
					double beeflyAngle_double = Double
							.parseDouble(beeflyAngle);
					angle += 180;
					InformInDanger(id); // ���Լ�������Σ�յ�״̬
					ratoteImage(angle);
				}
				if (getStatus(Integer.parseInt(beeId)) == 1) {// ���߶Է��Լ���
					// InformInDanger(Integer.parseInt(beeId));
				}
			}
		}

	}

	/**
	 * ����ĳֻ�۷䴦��Σ�յ�״̬
	 * 
	 * @param id
	 */
	public static void InformInDanger(int id) {
		switch (id) {
		case 1:
			FIRST_STATUS = 2;
			FIRST_DANGERTIME = System.currentTimeMillis(); // ��¼Σ�յ�״̬��ʱ��
			break;
		case 2:
			SECOND_STATUS = 2;
			SECOND_DANGERTIME = System.currentTimeMillis();
			break;
		case 3:
			THIRD_STATUS = 2;
			THIRD_DANGERTIME = System.currentTimeMillis();
			break;

		default:
			System.out.println("��������ֻ�۷�");
			break;
		}

	}

	/**
	 * ���ĳֻ�۷��Σ�յ�״̬
	 * 
	 * @param id
	 */
	public static void DeleteDanger(int id) {
		switch (id) {
		case 1:
			FIRST_STATUS = 1;
			break;
		case 2:
			SECOND_STATUS = 1;
			break;
		case 3:
			THIRD_STATUS = 1;
			break;

		default:
			System.out.println("��������ֻ�۷�");
			break;
		}

	}

	/**
	 * ��ȡ״̬
	 * 
	 * @param id
	 * @return
	 */
	public static int getStatus(int id) {

		switch (id) {
		case 1:
			return FIRST_STATUS;
		case 2:
			return SECOND_STATUS;
		case 3:
			return THIRD_STATUS;

		}
		// ��������� �ͷ���������״̬
		return 1;

	}
}