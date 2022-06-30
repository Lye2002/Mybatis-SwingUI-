package Client_UI;

import Bean.Goods;
import Service.GoodsService;
import Service.UserDao;
import config.NumDocument;
import config.Postion;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.util.List;

public class Main {
    public static JFrame jf = null;
    public static String userid;
    String password;
    JLabel lb;
    JButton bu1, bu2;
    JPanel pl2;
    List<Goods> list = null;
    JPanel jpz;
    static Page page;
    UserOrderList userOrderList;
    static int row = 2;
    static int col = 4;
    static int total=row*col;
    public static JLabel money;

    public Main(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }

    public void init() throws Exception {
        jf = new JFrame();
        jf.setTitle("当前在线用户ID：" + userid);
        JPanel pl1 = new JPanel();
        JTextField jtf = new JTextField(20);
        JButton but1 = new JButton("搜索");
        JButton but2 = new JButton("修改密码");
        //JButton but3 = new JButton("我的购物车");
        money=new JLabel();
        JButton but4=new JButton("充值");

        pl1.add(jtf);
        pl1.add(but1);
        pl1.add(but2);
        //pl1.add(but3);
        pl1.add(but4);
        money.setText("账户余额:"+ UserDao.getMoney(userid)+"￥");
        pl1.add(money);

        // 中间面板进行设置
        pl2 = new JPanel();
        pl2.setLayout(new GridLayout(row, col));
        // 分页
        page = new Page();
        page.setTotal(GoodsService.getAllGoods().size());
        if (GoodsService.getAllGoods().size() % total == 0) {
            if (GoodsService.getAllGoods().size() == 0) {
                page.setEndPage(1);
            } else {
                page.setEndPage((int) Math.ceil(GoodsService.getAllGoods().size() * 1.0 / total));
            }
        } else {
            page.setEndPage((int) Math.ceil(GoodsService.getAllGoods().size() * 1.0 / total));
        }
        page.setStartPage(1);
        page.setCurrentPage(1);

        list = GoodsService.pageShops(page.getCurrentPage() - 1,total);
        show(0, list);


        // 最下部分面板进行设置
        JPanel pl3 = new JPanel();
        pl3.setLayout(new GridLayout(1, 3));
        bu1 = new JButton("上一页");
        lb = new JLabel("当前页：     " + page.getCurrentPage() + "");
        bu2 = new JButton("下一页");
        pl3.add(bu1);
        pl3.add(lb);
        pl3.add(bu2);

        JPanel pl5 = new JPanel();
        //       JButton bp = new JButton("  评价    ");
      //  JButton bs = new JButton(" 刷新数据 ");
        JButton bd = new JButton("我购买的");

        //pl5.add(bs);

        pl5.add(bd);

        Box vbox = Box.createVerticalBox();
        vbox.add(pl1);
        vbox.add(pl2);
        vbox.add(pl3);
        vbox.add(pl5);

        jpz = new JPanel();
        jpz.add(vbox);

        jf.addWindowListener((WindowListener) new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
//                try {
//                    ManageClientConServerThread.getClientConServerThread(userid).getS().close();
//                    ManageClientConServerThread.delete(userid);
//                } catch (IOException e1) {
//
//                    e1.printStackTrace();
//                }
            }
        });

        jf.add(jpz);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        jf.setSize(1200,800);
        jf.setVisible(true);
        // 我购买的
        bd.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
//                MyBuy mb = new MyBuy(userid);
                if (userOrderList == null) {
                    userOrderList = new UserOrderList(userid);
                } else {
                    userOrderList.init_table();
                }

            }

        });
        // 刷新
//        bs.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//
//                Main mh = new Main(userid, password);
//                try {
//                    mh.init();
//
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//                //jf.repaint();
//                //mh.jf.repaint();
//
//                Main.jf.dispose();
//                JOptionPane.showConfirmDialog(null, "恭喜您，刷新成功", "请确认", 0);
//
//            }
//
//        });

        // 上一页
        bu1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                if (page.getCurrentPage() == 1) {
                    JOptionPane.showMessageDialog(null, "当前页为第一页，不可以再往前", "发生错误", JOptionPane.ERROR_MESSAGE);
                    page.setCurrentPage(page.getCurrentPage());
                    list = GoodsService.pageShops(page.getCurrentPage() - 1,total);
                    try {
                        show(0, list);
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    page.setCurrentPage(page.getCurrentPage() - 1);
                    list = GoodsService.pageShops(page.getCurrentPage() - 1,total);
                }
                lb.setText("当前页：     " + (page.getCurrentPage()) + "");
                list = GoodsService.pageShops(page.getCurrentPage() - 1,total);
                try {
                    show(0, list);
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
            }

        });
        // 下一页
        bu2.addMouseListener(new MouseAdapter() {

            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

                if (page.getCurrentPage() == page.getEndPage()) {

                    JOptionPane.showMessageDialog(null, "当前页为最后一页，不可以再往后", "发生错误", JOptionPane.ERROR_MESSAGE);
                    page.setCurrentPage(page.getCurrentPage());
                    //list = GoodsService.pageShops(page.getCurrentPage(),total);
                    try {
                        show(0, list);
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    //System.out.println("331当前页码:"+page.getCurrentPage());

                    list = GoodsService.pageShops(page.getCurrentPage(),total);
                    page.setCurrentPage(page.getCurrentPage() + 1);
                }
                lb.setText("当前页： " + (page.getCurrentPage()) + "");
                //list=GoodsService.pageShops(page.getCurrentPage());
                try {
                    show(0, list);
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }

            }

        });

        // 修改密码
        but2.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                new ChangePassword(userid);
            }

        });

        // 搜索按钮的监听
        but1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                page = new Page();
                List<Goods> res = GoodsService.searchLikeGoodsByName(jtf.getText());
                page.setTotal(res.size());
                if (res.size() % total == 0) {
                    if (res.size() == 0) {
                        page.setEndPage(1);
                    } else {
                        page.setEndPage(res.size() / total);
                    }
                } else {
                    page.setEndPage(res.size() / total + 1);
                }
                page.setStartPage(1);
                page.setCurrentPage(1);
                list = res;

                try {
                    show(0, list);
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
                jtf.setText("");
            }

        });

        but4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame addmoneyJF=new JFrame("充值");
                JLabel jLabel=new JLabel("充值金额:");

                JTextField jTextField=new JTextField(20);
                //jTextField.addKeyListener(new ControlTheInput());
                jTextField.setDocument(new NumDocument.DoubleOnlyDocument());
                JPanel jPanel=new JPanel();
                JButton jButton=new JButton("确定充值");
                jPanel.add(jLabel);
                jPanel.add(jTextField);
                addmoneyJF.pack();
                jPanel.add(jButton);
                addmoneyJF.add(jPanel);
                jButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Float mon= Float.valueOf((jTextField.getText()));
                        Float sum=mon+UserDao.getMoney(userid);
                        Boolean ok = UserDao.updateMoney(sum, userid);
                        if(ok==true) {
                             JOptionPane.showConfirmDialog(null, "充值成功", "请确认", 0);
                            money.setText("账户余额:"+ UserDao.getMoney(userid)+"￥");
                             addmoneyJF.dispose();
                        }else{
                            JOptionPane.showConfirmDialog(null, "充值失败", "请确认", 0);
                        }
                    }
                });
                Postion.SetPostionCenter(addmoneyJF);
                addmoneyJF.setVisible(true);
                addmoneyJF.pack();


            }
        });
        Postion.SetPostionCenter(jf);
    }

    public void show(int start, List<Goods> list) throws MalformedURLException {
        pl2.removeAll();
        int total_in_page = row * col;
        JPanel jp[] = new JPanel[total_in_page];
        // System.out.println(list.size());
        JPanel empty=new JPanel();
        JLabel empl=new JLabel();
        empty.add(empl);
        JPanel emp=new Panel_main().panel();
        for (int i = 0; i < jp.length; i++) {
            if((list.size()<=jp.length)){
                if(i>=list.size()){
                    pl2.add(emp);
                }else{
                    jp[i] = new Panel_main().panel(list.get(start + i));
                    pl2.add(jp[i]);
                }

            }else{
                jp[i] = new Panel_main().panel(list.get(start + i));
                pl2.add(jp[i]);
            }
            pl2.validate();
            // 重绘组件
            pl2.repaint();
        }

        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws Exception {
        Main mainWindows=  new Main("222","222");
        mainWindows.init();

    }
}
