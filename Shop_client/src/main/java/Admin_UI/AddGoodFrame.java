package Admin_UI;

import Bean.Goods;
import Client_UI.ChangePassword;
import Service.OrderService;
import Service.ShopSellService;
import Service.Upload;
import Service.UserDao;
import config.Postion;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static Admin_UI.ShopManage.fillGoodsTable;
import static Client_UI.Main.money;

public class AddGoodFrame extends JFrame {
    String hideImgUrl;
    JLabel l = new JLabel();
    public static JFrame jf;
    public String userid;

    public AddGoodFrame(Goods g) {
    }

    public AddGoodFrame(String userid) {
        this.userid = userid;
        System.out.println(userid);
    }

    public void init() {

        System.out.println(userid);
        jf = new JFrame();
        jf.setSize(400, 700);
        jf.setLocationRelativeTo(null);
        jf.setTitle("添加商品");


        JPanel jp19 = new JPanel();
        ImageIcon icon = new ImageIcon("src/main/resources/wrong.png");
        icon.setImage(icon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
        l.setIcon(icon);
        l.setBounds(0, 0, 10, 10);
        jp19.add(l);


        l.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    resetImg(e);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });


        JPanel jp2 = new JPanel();
        JTextField jtf1 = new JTextField(30);

        jp2.add(new JLabel("商品名"));
        jp2.add(jtf1);


        JPanel jp3 = new JPanel();
        JTextField jtf2 = new JTextField(30);

        jp3.add(new JLabel("价格"));
        jp3.add(jtf2);

        JPanel jp5 = new JPanel();


        JPanel jp7 = new JPanel();
        JTextField jtf6 = new JTextField(30);

        jp7.add(new JLabel("介绍"));
        jp7.add(jtf6);

        JPanel jp17 = new JPanel();
        jp17.setLayout(new GridLayout(2, 1));


        JPanel jp8 = new JPanel();
        JButton but1 = new JButton("确定增加");
        // JButton but2 = new JButton("加入购物车");
        but1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int n = JOptionPane.showConfirmDialog(null, "您将为商店添加此商品", "请确认", 0);
                if(n==JOptionPane.YES_OPTION){

                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
                    java.util.Date date = new Date();//获得当前时间
                    String birthday = df.format(date);//将当前时间转换成特定格式的时间字符串，这样便可以插入到数据库中
                    ShopSellService.addGood(jtf1.getText(), Float.valueOf(jtf2.getText()),hideImgUrl,birthday,jtf6.getText(),userid);
                    JOptionPane.showMessageDialog(null, "商品添加成功~", "通知",JOptionPane.PLAIN_MESSAGE);
                    jf.dispose();
                    fillGoodsTable();
                    //addGood(String name,Float price,String img,String firsttime,String content,String sellerid)
                }else{
                    JOptionPane.showMessageDialog(null, "商品添加失败~", "通知",JOptionPane.PLAIN_MESSAGE);
                }

            }
        });
        jp8.add(but1);
        //jp8.add(but2);

        JPanel jp9 = new JPanel();


        JPanel jp20 = new JPanel();
        Box vbox = Box.createVerticalBox();
        vbox.add(jp19);
//        vbox.add(jp1);
//        vbox.add(jp10);
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

        Postion.SetPostionCenter(jf);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(HIDE_ON_CLOSE);

    }
    private void resetImg(MouseEvent evt) throws IOException {
        JFileChooser jFileChooser=new JFileChooser();
        jFileChooser.setVisible(true);
        jFileChooser.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png)", "jpg", "png"));
        int result =jFileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            try {
                String res= Upload.uploadIMG(selectedFile.getAbsolutePath());
                if(!res.isEmpty()) {

                    String url=res;
                    System.out.println(url);
                    int i = res.lastIndexOf('/');
                    String target=res.substring(i+1);
                    System.out.println(res.substring(i+1));
                    hideImgUrl="/image/"+target;
                    ImageIcon icon = new ImageIcon(url);
                    icon.setImage(icon.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT));
                    l.setIcon(icon);
                    l.repaint();
                    l.updateUI();
                    l.setVisible(true);
//springboot上传图片后bug不能及时回显图片，应该实用应用线程 判断上传完成后再回显数据，能力与时间有限，尚未解决
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

        public static void main(String[] args) {

            new AddGoodFrame("1").init();


        }
    }

