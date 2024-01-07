import java.awt.*;
import java.util.*;

public class Hornet extends Bee {
    private int id;
    private boolean dead = false;
    private boolean seen = false;

    private int x, y;
    private int num = 0;
    private String strVision, strVision1;
    private String[] info;
    private String[] beeInfos;
    private String[] flwInfos;
    private String flwInfo = new String();
    private String beeInfo = new String();
    private int flwVolumn, beeId;
    private double flwAngle, beeAngle, beeHeading, beeAngle1, beeHeading1;
    private int[] nextX = new int[9];
    private int[] nextY = new int[9];
    private int dx_sum = 0;
    private int dy_sum = 0;
    private int deltaX, deltaY;
    private double newAngle;
    private int maxVolumn;
    private int chasingId = 9;//��¼��ǰĿ��
    private Random ra = new Random();

    public Hornet(int id, int x, int y, double angle, boolean isAlive, Image img) {
        super(id, x, y, angle, isAlive, img);
        this.id = id;
    }

    public void search() {
        strVision = BeeFarming.search(id);
        BeeFarming.update(new FlyingStatus(id, x, y, angle + 180, true, 0));
        strVision1 = BeeFarming.search(id);
        maxVolumn = 0;
        newAngle = 0;
        if ((strVision.length() > 6) || (strVision1.indexOf("+") != -1))
        {
            num = 0;
            beeInfo = beeInfo.substring(beeInfo.length());
            info = strVision.split("~", 0);
            for (int i = 0; i < info.length; i++) {
                if (info[i].indexOf('+') == 0) {
                    beeInfo = beeInfo.concat(info[i]);
                }
            }
            beeInfos = beeInfo.split("[()]");
            if (beeInfos.length > 1) {
                for (int i = 0; i < beeInfos.length; i++) {
                    if ((beeInfos[i].indexOf('+') != 0) && (beeInfos[i].length() > 0)) {

                        beeId = Integer.parseInt(beeInfos[i].substring(0, beeInfos[i].indexOf(',')));
                        //ûĿ��ʱ����������Ŀ��
                        if (seen == false) {
                            chasingId = beeId;
                            seen = true;
                        }
                        //��Ŀ��ʱ��ֻ׷��ǰĿ�����
                        if ((seen == true) && (beeId == chasingId)) {
                            beeAngle = Double.parseDouble(beeInfos[i].substring(beeInfos[i].indexOf(',') + 1, beeInfos[i].indexOf(',', 4)));
                            beeHeading = Double.parseDouble(beeInfos[i].substring(beeInfos[i].indexOf(',', 4) + 1));

                            //��һ���۲⵽���۷�ĽǶ�ֵ
                            beeAngle1 = 90 - angle + beeAngle;
                            beeHeading1 = 90 - angle + beeHeading;
                            while (beeAngle1 < 0) {
                                beeAngle1 = beeAngle1 + 360;
                            }
                            while (beeAngle1 > 360) {
                                beeAngle1 = beeAngle1 - 360;
                            }
                            while (beeHeading1 < 0) {
                                beeHeading1 = beeHeading1 + 360;
                            }
                            while (beeHeading1 > 360) {
                                beeHeading1 = beeHeading1 - 360;
                            }


                            if ((x < 80) || (y < 80) || (x > 740) || (y > 540))
                                angle = beeAngle1 + 60 * Math.cos(Math.toRadians(90 - beeHeading1 + beeAngle1)) - 90 + angle;
                            else
                                angle = beeAngle1 + 30 * Math.cos(Math.toRadians(90 - beeHeading1 + beeAngle1)) - 90 + angle;

                        }
                    }

                }
            }

            else if (strVision1.indexOf("+") != -1) {
                beeInfo = beeInfo.substring(beeInfo.length());
                info = strVision1.split("~", 0);
                for (int i = 0; i < info.length; i++) {
                    if (info[i].indexOf('+') == 0) {
                        beeInfo = beeInfo.concat(info[i]);
                    }
                }
                beeInfos = beeInfo.split("[()]");
                if (beeInfos.length > 1) {
                    //���������Ϣ��׷���۷�
                    for (int i = 0; i < beeInfos.length; i++) {
                        if ((beeInfos[i].indexOf('+') != 0) && (beeInfos[i].length() > 0)) {

                            beeId = Integer.parseInt(beeInfos[i].substring(0, beeInfos[i].indexOf(',')));
                            //ûĿ��ʱ����������Ŀ��
                            if (seen == false) {
                                chasingId = beeId;
                                seen = true;
                            }
                            //��Ŀ��ʱ��ֻ׷��ǰĿ�����
                            if ((seen == true) && (beeId == chasingId)) {
                                beeAngle = Double.parseDouble(beeInfos[i].substring(beeInfos[i].indexOf(',') + 1, beeInfos[i].indexOf(',', 4)));
                                beeHeading = Double.parseDouble(beeInfos[i].substring(beeInfos[i].indexOf(',', 4) + 1));

                                //��һ���۲⵽���۷�ĽǶ�ֵ
                                beeAngle1 = 90 - angle + beeAngle;
                                beeHeading1 = 90 - angle + beeHeading;
                                while (beeAngle1 < 0) {
                                    beeAngle1 = beeAngle1 + 360;
                                }
                                while (beeAngle1 > 360) {
                                    beeAngle1 = beeAngle1 - 360;
                                }
                                while (beeHeading1 < 0) {
                                    beeHeading1 = beeHeading1 + 360;
                                }
                                while (beeHeading1 > 360) {
                                    beeHeading1 = beeHeading1 - 360;
                                }


                                if ((x < 80) || (y < 80) || (x > 740) || (y > 540))
                                    angle = beeAngle1 + 60 * Math.cos(Math.toRadians(90 - beeHeading1 + beeAngle1)) - 90 + angle;
                                else
                                    angle = beeAngle1 + 30 * Math.cos(Math.toRadians(90 - beeHeading1 + beeAngle1)) - 90 + angle;
                            }
                        }

                    }
                }

            }




        }
        else if (strVision.indexOf('*') == 0) {
            num = 0;
            //�������ת��,����������ͷ����ڷ�������
            switch (strVision.charAt(1)) {
                case 'W':
                    if (Math.sin(Math.toRadians(angle)) < 0) {
                        angle = ra.nextInt(90);
                        angle = angle + 270;
                    } else
                        angle = ra.nextInt(90);
                    break;
                case 'N':
                    if (Math.cos(Math.toRadians(angle)) < 0) {
                        angle = ra.nextInt(90);
                        angle = angle + 90;
                    } else
                        angle = ra.nextInt(90);
                    break;
                case 'E':
                    if (Math.sin(Math.toRadians(angle)) < 0) {
                        angle = ra.nextInt(90);
                        angle = angle + 180;
                    } else {
                        angle = ra.nextInt(90);
                        angle = angle + 90;
                    }
                    break;
                case 'S':
                    if (Math.cos(Math.toRadians(angle)) < 0) {
                        angle = ra.nextInt(90);
                        angle = angle + 180;
                    } else {
                        angle = ra.nextInt(90);
                        angle += 270;
                    }
                    break;
                default:
                    break;
            }
        }
        //����10���޻��޷��ޱ߽磬�����ת90���ڵ�һ����
        else {
            if (num == 10) {
                angle += ra.nextInt(180) - 90;
                num = 0;
            } else
                num++;
        }

        //������һʱ������λ��
        dx_sum = 0;
        dy_sum = 0;
        deltaX = (int) (18 * Math.cos(Math.toRadians(angle)));
        deltaY = (int) (18 * Math.sin(Math.toRadians(angle)));
        for (int i = 0; i < 9; i++) {
            nextX[i] = (int) (deltaX * (i + 1) / 9.0) - dx_sum;
            nextY[i] = (int) (deltaY * (i + 1) / 9.0) - dy_sum;
            dx_sum += nextX[i];
            dy_sum += nextY[i];
        }
        x += dx_sum;
        y += dy_sum;


        ratoteImage(angle);
        setXYs(0);
    }

    /**
     * ����Ʒ�ץ�����۷䣬��boolean dead==true���Ʒ���Ը���dead��ֵ�ж��۷�֪��ɱ����
     * �����������޸ģ���BeeFarming��killBee�����е��۷䱻�Ʒ�����󽫱�����
     */
    public boolean isCatched() {
        chasingId = 9;
        seen = false;
        dead = true;
        return dead;
    }

}