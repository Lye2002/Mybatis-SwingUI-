package Admin_UI;

import Bean.Goods;
import Bean.Order;
import Client_UI.Panel_main;
import Service.OrderService;
import Service.ShopSellService;
import config.Config;
import config.Postion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class OrdersManage {
    private JTextField s_bookNameText;
    private JTextField s_authorText;
    private JTable shopTable;
    private String username;
    private JTextField idText;
    private JTextField nameText;
    private JTextField priceText;
    private JTextArea AddressText;
    private String hideImgUrl="";
    private JLabel img;
    private JTextField timeText;


    public static void main(String[] args) {
       new  OrdersManage("2");
    }
    public OrdersManage(String username){
        this.username=username;
        JFrame jFrame = new JFrame();
        Panel root=new Panel();

        shopTable = new JTable();
        //表格鼠标按下事件
        shopTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    tableMousePressed(e);
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        shopTable.setModel(new DefaultTableModel(
                new Object[][] {
                        {223,"1","1","http://localhost:8081/image/341421.jpg","4","6"}
                },
                new String[] {
                        "订单号", "商品名称","商品价格","商品图片","订单生成时间","地址",
                }
        ) {
            boolean[] columnEditables = new boolean[] {
                    false, false, false, false, false, false, false
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });


        shopTable.getColumnModel().getColumn(0).setPreferredWidth(56);
        shopTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        shopTable.getColumnModel().getColumn(2).setPreferredWidth(63);
        shopTable.getColumnModel().getColumn(3).setPreferredWidth(63);
        shopTable.getColumnModel().getColumn(4).setPreferredWidth(61);
        shopTable.getColumnModel().getColumn(5).setPreferredWidth(94);
        //shopTable.getColumnModel().getColumn(6).setPreferredWidth(163);
        JScrollPane scrollPane = new JScrollPane();
        root.setLayout(new BorderLayout());

        scrollPane.setViewportView(shopTable);
        root.add(scrollPane,BorderLayout.CENTER);

        JLabel idLabel = new JLabel("编号：");
        idText = new JTextField();
        idText.setColumns(10);
        idText.setEnabled(false);

        JLabel bookNameLabel = new JLabel("商品名称：");
        nameText = new JTextField();
        nameText.setColumns(10);
        nameText.setEnabled(false);



        JLabel imgLabel=new JLabel("商品预览图:");
        img = new JLabel();
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(Panel_main.class.getClassLoader().getResource("wrong.png")));

        icon.setImage(icon.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT));
        img.setIcon(icon);

        JLabel priceLabel = new JLabel("价格：");
        priceText = new JTextField();
        priceText.setColumns(10);
        priceText.setEnabled(false);


        JLabel timeLabel = new JLabel("订单生成时间：");
        timeText = new JTextField();
        timeText.setColumns(10);
        timeText.setEnabled(false);



        JLabel bookDescLabel = new JLabel("收货地址：");

        AddressText = new JTextArea(5, 15);


        //修改按钮
        JButton modifyBtn = new JButton("修改");
        modifyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyOrderActionPerformed(e);
            }
        });


        //删除按钮
        JButton deleteBtn = new JButton("删除");
        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteBookActionPerformed(e);
            }
        });

        JPanel oper=new JPanel();
        oper.setLayout(new GridLayout(2, 4, 0, 0));

        JPanel l1=new JPanel();

        l1.add(idLabel);
        l1.add(idText);
        oper.add(l1);

        JPanel l2=new JPanel();
        l2.add(bookNameLabel);
        l2.add(nameText);
        oper.add(l2);

        JPanel l3=new JPanel();
        l3.add(priceLabel);
        l3.add(priceText);
        oper.add(l3);

        JPanel l7=new JPanel();
        l7.add(timeLabel);
        l7.add(timeText);
        oper.add(l7);

        JPanel l4=new JPanel();
        l4.add(bookDescLabel);
        JScrollPane jScrollPane=new JScrollPane(AddressText);
        l4.add(jScrollPane);
        oper.add(l4);

        JPanel l5=new JPanel();
        l5.add(imgLabel);
        l5.add(img);
        oper.add(l5);


        JPanel l6=new JPanel();
        l6.add(modifyBtn);
        l6.add(deleteBtn);
        oper.add(l6);
        fillGoodsTable(new Order());
        root.add(oper,BorderLayout.SOUTH);
        jFrame.add(root);
        jFrame.pack();
        Postion.SetPostionCenter(jFrame);
        jFrame.setVisible(true);

    }


    private void modifyOrderActionPerformed(ActionEvent evt) {
        String address = AddressText.getText();
        String orderid=idText.getText();

        if (address == null || "".equals(address)) { //为空
            JOptionPane.showMessageDialog(null, "收货地址不能为空！");  //给用户提示
            return;
        }


        System.out.println("从获取的商品信息创建商品对象:");

        System.out.println(address);
        Integer integer = OrderService.updateAddress(address, orderid);
        System.out.println("修改影响条数:"+integer);
        if (integer>0) {
            JOptionPane.showMessageDialog(null, "地址修改成功n_n");
            //刷新商品表格显示
            fillGoodsTable(new Order());

//            resetValue();
        } else { //为0
            JOptionPane.showMessageDialog(null, "地址修改失败u_u");
        }
    }


    private void fillGoodsTable(Order order){
        //获取表格的模型
        DefaultTableModel dtm=(DefaultTableModel) shopTable.getModel();
        //填充表格时设置成0行（相当于归零处理）
        dtm.setRowCount(0);

        List<Order> ordersList = OrderService.getSellerOrders(username);
        //System.out.println(ordersList.get(0));
        for(int i=0;i<ordersList.size();i++){
            //定义一个集合，由于存储商品信息
            Vector v=new Vector();
            v.add(ordersList.get(i).getOrderid());  //添加编号
            v.add(ordersList.get(i).getGoods().getName());
            v.add(ordersList.get(i).getGoods().getPrice());
            v.add(ordersList.get(i).getGoods().getImg());
            String birthday;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
            if(ordersList.get(i).getBuytime()!=null) {
                birthday = df.format(ordersList.get(i).getBuytime());//将当前时间转换成特定格式的时间字符串，这样便可以插入到数据库中
            }else{
                birthday ="";
            }
            v.add(birthday);
            v.add(ordersList.get(i).getAddress());
            //添加表格新行
            dtm.addRow(v);
        }
    }


    /**
     * 表格鼠标按下事件处理
     * @param evt
     */
    private void tableMousePressed(MouseEvent evt) throws MalformedURLException {

        int row=shopTable.getSelectedRow();

        idText.setText(String.valueOf(shopTable.getValueAt(row,0))+"");

        nameText.setText((String)shopTable.getValueAt(row, 1));

        priceText.setText(String.valueOf(shopTable.getValueAt(row, 2))+"");

        AddressText.setText((String)shopTable.getValueAt(row, 5));

        String imgurl= (String) shopTable.getValueAt(row, 3);
        ImageIcon icon=null;
        System.out.println(imgurl);
        if((imgurl!=null)&&!imgurl.isEmpty()) {
            icon = new ImageIcon(new URL(Config.BASE_URL+imgurl));
        } else if(imgurl.isEmpty()){
            String url=Config.BASE_URL+"/image/wrong.jpg";
            icon = new ImageIcon(new URL(url));
        }
        icon.setImage(icon.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT));
        img.setIcon(icon);
        img.repaint();
    }

    private void deleteBookActionPerformed(ActionEvent evt) {
        //获取商品id
        String orderid=idText.getText();
        //判断是否id是否为空
        if(orderid==null || "".equals(orderid)){ //为空
            JOptionPane.showMessageDialog(null, "请选中要删除的行！");  //给用户提示
            return;
        }else{
            Integer integer = OrderService.delOrder(orderid);
            if(integer>0){
                JOptionPane.showMessageDialog(null, "删除成功！");  //给用户提示
                fillGoodsTable(new Order());
            }else{
                JOptionPane.showMessageDialog(null, "删除失败！");  //给用户提示
            }
        }

    }
}
