package Client_UI;

import Bean.Goods;
import Service.OrderService;
import Service.UserDao;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static Client_UI.Main.money;

public class Detial {
    JFrame jf;
   public String userid;

    public Detial() {
    }

    public Detial(String userid) {
        this.userid = userid;
        System.out.println(userid);
    }

    public void init(Goods g) {

        System.out.println(userid);
        jf = new JFrame();
        jf.setSize(400, 700);
        jf.setLocationRelativeTo(null);
        jf.setTitle("商品信息");

        if ((g.getImg() == null) ||(g.getImg().length()==0)) {
            g.setImg("src/main/resources/wrong.png");
        }
        JPanel jp19 = new JPanel();
        JLabel l = new JLabel();
        ImageIcon icon = new ImageIcon(g.getImg());
        icon.setImage(icon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
        l.setIcon(icon);
        l.setBounds(0, 0, 10, 10);
        jp19.add(l);

        JPanel jp1 = new JPanel();
        JTextField jtf = new JTextField(30);
        jtf.setText(g.getId());
        jtf.setEditable(false);
        jp1.add(new JLabel("ID   "));
        jp1.add(jtf);

        JPanel jp10 = new JPanel();
        JTextField jtf10 = new JTextField(30);
        jtf10.setText(g.getSellerid());
        jtf10.setEditable(false);
        jp10.add(new JLabel("卖家ID"));
        jp10.add(jtf10);

        JPanel jp2 = new JPanel();
        JTextField jtf1 = new JTextField(30);
        jtf1.setText(g.getName());
        jtf1.setEditable(false);
        jp2.add(new JLabel("商品名"));
        jp2.add(jtf1);

        JPanel jp3 = new JPanel();
        JTextField jtf2 = new JTextField(30);
        jtf2.setText(g.getPrice() + "");
        jtf2.setEditable(false);
        jp3.add(new JLabel("价格"));
        jp3.add(jtf2);

        JPanel jp5 = new JPanel();


        JPanel jp7 = new JPanel();
        JTextField jtf6 = new JTextField(30);
        jtf6.setText(g.getContent() + "");
        jtf6.setEditable(false);
        jp7.add(new JLabel("介绍"));
        jp7.add(jtf6);

        JPanel jp17 = new JPanel();
        jp17.setLayout(new GridLayout(2, 1));


        JPanel jp8 = new JPanel();
        JButton but1 = new JButton("购买");
       // JButton but2 = new JButton("加入购物车");
        but1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int n = JOptionPane.showConfirmDialog(null, "您将购买此商品", "请确认", 0);
                Float MyMoney=UserDao.getMoney(userid);
                if(g.getPrice()< MyMoney&&n == JOptionPane.YES_OPTION){
                    System.out.println("购买成功");
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
                    java.util.Date date = new Date();//获得当前时间
                    String birthday = df.format(date);//将当前时间转换成特定格式的时间字符串，这样便可以插入到数据库中
                    String address=JOptionPane.showInputDialog(null,"请输入地址","收货地址",JOptionPane.INFORMATION_MESSAGE);
                    if(!address.isEmpty()) {
                        OrderService.buyAshop(Integer.valueOf(g.getId()), birthday, userid, address,g.getSellerid());
                        UserDao.updateMoney(MyMoney-g.getPrice(),userid);
                        money.setText("账户余额:"+ UserDao.getMoney(userid)+"￥");
                        int i = JOptionPane.showConfirmDialog(null, "订单生成成功", "请确认", 0);
                        jf.dispose();
                    }else{

                    }
                }else{
                    System.out.println("购买失败");
                    JOptionPane.showMessageDialog(null, "余额不足,订单生成失败~", "通知",JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        jp8.add(but1);
        //jp8.add(but2);

        JPanel jp9 = new JPanel();
//        JTextField jtf14 = new JTextField(30);
//        jtf14.setText(g.getCategory().getCategoryType());
//        jtf14.setEditable(false);
//        jp9.add(new JLabel("商品种类  "));
//        jp9.add(jtf14);

        JPanel jp20 = new JPanel();
        Box vbox = Box.createVerticalBox();
        vbox.add(jp19);
        vbox.add(jp1);
        vbox.add(jp10);
        vbox.add(jp2);
        vbox.add(jp3);
        vbox.add(jp5);
        vbox.add(jp7);
        vbox.add(jp9);
        vbox.add(jp17);
        vbox.add(jp8);
        jp20.add(vbox);
        //JScrollPane jsp = new JScrollPane(jp20, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jf.add(jp20);

        // this.add(button2);

        jf.pack();

        int windowWidth = jf.getWidth(); //获得窗口宽
        int windowHeight = jf.getHeight(); //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        jf.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示
        jf.setVisible(true);

    }

}
