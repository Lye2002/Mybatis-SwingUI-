package Client_UI;

import Bean.Order;
import Service.OrderService;
import config.Postion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

public class UserOrderList {
    private JFrame jf=new JFrame();
   public static DefaultTableModel model;
   public static JTable  table;
   public static String un;
   public static Vector<Vector<String>> data = new Vector<Vector<String>>();
   public static JScrollPane spTable;
   public static  String t[]={"订单号","商品名称","购买时间","地址"};
   public static  Vector<String> title = new Vector<String>();
   public static  DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public UserOrderList(String username){

        un=username;
        jf.setTitle("用户:"+username+"-订单列表");

        for(int i=0;i<t.length;i++){
            title.add(t[i]);
        }
        model = new DefaultTableModel();
        // 创建表格
        List<Order> userOrderData =OrderService.getUserOrders(username);
        for(int i=0;i<userOrderData.size();i++) {
            // 行数据
            Vector<String> rowdata = new Vector<String>();
            System.out.println(userOrderData.get(i).getGoods().getName());
                rowdata.add(String.valueOf(userOrderData.get(i).getOrderid()));
                rowdata.add(String.valueOf(userOrderData.get(i).getGoods().getName()));
                rowdata.add((String.valueOf(userOrderData.get(i).getBuytime())=="null"?"暂无记录": (String.valueOf(df.format(userOrderData.get(i).getBuytime())))));
                rowdata.add((String.valueOf(userOrderData.get(i).getAddress())=="null"?"暂无记录":String.valueOf(userOrderData.get(i).getAddress())));
                data.add(rowdata);
        }
        System.out.println(data);
        model.setDataVector(data, title);

        table = new JTable(model);
        // 设置表格选择模式为单一选择
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // 创建一个滚动面板，包含表格
        spTable = new JScrollPane(table);
        // 将滚动面板添加到窗体中央


       JButton bthDelete = new JButton("删除");
        //   JButton bthUpdata = new JButton("更新");
        JButton btnFlush = new JButton("刷新");
        // 创建面板
       JPanel pButtons = new JPanel();
        // 将按钮添加到面板中
       // pButtons.add(bthUpdata);
        pButtons.add(bthDelete);
        pButtons.add(btnFlush);
        // 将盛放按钮的面板添加到窗体的南部（下面）
        jf.add(pButtons, BorderLayout.SOUTH);

        bthDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 调用删除数据的方法
                deleteData();
            }
        });
        btnFlush.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 调用显示数据的方法
                showData();
            }
        });


        jf.setSize(700,500);
        jf.add(spTable, BorderLayout.CENTER);
        Postion.SetPostionCenter(jf);


        jf.setVisible(true);
        //jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    public void init_table(){
        jf.setVisible(true);
    }

    public void deleteData() {
        int index;
         index=table.getSelectedRow();

        System.out.println(index);
        if (index == -1) {
            JOptionPane.showMessageDialog(jf, "请选择要删除的记录", "提示",
                    JOptionPane.PLAIN_MESSAGE);
        } else {
            try {
                int k = JOptionPane.showConfirmDialog(jf,
                        "您确定要从数据库中删除所选的数据吗 ？", "删除", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (k == JOptionPane.YES_OPTION) {
                  int col = table.getSelectedColumn();//获取选中的列号
                    int row = table.getSelectedRow();//获取选中的行号
                    DefaultTableModel model1 = (DefaultTableModel) table.getModel();//获取defaulttable
                    Object val = model1.getValueAt(row, 0);//根据行号和列号，获取某个单元格的值

                    System.out.println(val);
                    System.out.println("订单号:"+val);
                    System.out.println("用户名:"+un);

                    String v=val.toString();

                    System.out.println(un+" ++"+v);
                    //System.out.println(OrderService.delOrderUser(un,v));
                        if(OrderService.delOrderUser(un,v)==true){
                            //new goods();
                            System.out.println(1);
                            JOptionPane.showMessageDialog(jf, "删除成功", "成功:",
                                    1);
                                showData();
                          //  jf.repaint();

                        }
                }
            } catch (Exception ee) {
                System.out.println(ee);
                JOptionPane.showMessageDialog(jf, "抱歉!删除数据失败!【系统异常！】", "失败:",
                        0);
            }
        }
    }


    public void showData() {
        List<Order> userOrderData =null;
        userOrderData=OrderService.getUserOrders(un);
        data=new Vector<Vector<String>>();


        for(int i=0;i<userOrderData.size();i++) {
            // 行数据

            Vector<String> rowdata = new Vector<String>();
            rowdata.add(String.valueOf(userOrderData.get(i).getOrderid()));
            rowdata.add(String.valueOf(userOrderData.get(i).getGoods().getName()));
            rowdata.add((String.valueOf(userOrderData.get(i).getBuytime())=="null"?"暂无记录": (String.valueOf(df.format(userOrderData.get(i).getBuytime())))));
            rowdata.add((String.valueOf(userOrderData.get(i).getAddress())=="null"?"暂无记录":String.valueOf(userOrderData.get(i).getAddress())));
            data.add(rowdata);
        }
        System.out.println(data);
        model.setDataVector(data, title);


        // 设置表格选择模式为单一选择
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.repaint();
        table.updateUI();

    }
//    public static void main(String[] args) {
//       new UserOrderList("111");
//
//
//    }
}
