package Admin_UI;

import Bean.Goods;
import Client_UI.Panel_main;
import Service.ShopSellService;
import Service.Upload;
import config.Config;
import config.Postion;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class ShopManage {
    private JTextField s_bookNameText;
    private JTextField s_authorText;
    private static JTable shopTable;
    private static String username;
    private JTextField idText;
    private JTextField nameText;
    private JTextField priceText;
    private JTextField authorText;
    private JTextArea bookDescText;
    private JLabel searchNameLabel;
    private JTextField searchNameText;
    private String hideImgUrl="";
    JFrame jFrame;
    static DefaultTableModel dtm;
    static List<Goods> shopsGoods;

    private JLabel img;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        ShopManage frame = new ShopManage("2");

    }

    /**
     * Create the frame.
     */


    public ShopManage(String username) {
        this.username=username;
        jFrame = new JFrame();

        jFrame.setTitle("商品管理");
        jFrame.setBounds(100, 100, 767, 828);


        JMenuBar jMenuBar=new JMenuBar();                 //实例菜单栏
        JMenu newj=new JMenu("选项内容");                //实例一个菜单项
        JMenuItem shopMange=new JMenuItem("商品管理");         //实例子目录
        JMenuItem ordersMange=new JMenuItem("订单管理");

        newj.add(shopMange);
        newj.add(ordersMange);
        ordersMange.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    OrdersManage(e);
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        jMenuBar.add(newj);//添加菜单项
        jFrame.setJMenuBar(jMenuBar);                        //设置菜单栏



        JScrollPane scrollPane = new JScrollPane();

        JLabel idLabel = new JLabel("编号：");
        idText = new JTextField();
        idText.setColumns(10);
        idText.setEnabled(false);

        JLabel bookNameLabel = new JLabel("商品名称：");
        nameText = new JTextField();
        nameText.setColumns(10);

        searchNameLabel =new JLabel("商品名称:");
        searchNameText =new JTextField();
        searchNameText.setColumns(10);


        JLabel imgLabel=new JLabel("商品预览图:");
        img = new JLabel();
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(Panel_main.class.getClassLoader().getResource("wrong.png")));

        icon.setImage(icon.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT));
        img.setIcon(icon);
        img.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    resetImg(e);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JLabel priceLabel = new JLabel("价格：");

        priceText = new JTextField();
        priceText.setColumns(10);


        JLabel bookDescLabel = new JLabel("商品描述：");

        bookDescText = new JTextArea(5, 15);


        //修改按钮
        JButton modifyBtn = new JButton("修改");
        modifyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyGoodActionPerformed(e);
            }
        });


        //删除按钮
        JButton deleteBtn = new JButton("删除");
        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteBookActionPerformed(e);
            }
        });


        //表格
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
                        "编号", "商品名称","商品价格","商品图片","上架时间","商品描述",
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
        //    shopTable.getColumnModel().getColumn(6).setPreferredWidth(163);

        scrollPane.setViewportView(shopTable);

        JLabel lblL = new JLabel("商品名称：");

        s_bookNameText = new JTextField();
        s_bookNameText.setColumns(10);

        JLabel label = new JLabel("商品作者：");

        s_authorText = new JTextField();
        s_authorText.setColumns(10);

        JButton s_insertBtn = new JButton("增加商品");
        s_insertBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertGoodActionPerformed(e);
            }
        });
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "搜索条件", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.add(s_insertBtn);



        JPanel Root=new JPanel();
        Root.setLayout(new BorderLayout());

        panel.add(s_insertBtn);
        Root.add(panel,BorderLayout.NORTH);

        //panel.setLayout(new GridLayout(1, 3, 0, 0));



        JPanel tablePanel=new JPanel();
        tablePanel.setLayout(new BorderLayout());
        JScrollPane jScrollPanetable=new JScrollPane(shopTable);
        tablePanel.add(shopTable.getTableHeader(),BorderLayout.NORTH);
        tablePanel.add(jScrollPanetable);

        Root.add(panel,BorderLayout.NORTH);

        Root.add(tablePanel,BorderLayout.CENTER);

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

        JPanel l4=new JPanel();
        l4.add(bookDescLabel);
        JScrollPane jScrollPane=new JScrollPane(bookDescText);
        l4.add(jScrollPane);
        oper.add(l4);

        JPanel l5=new JPanel();
        l5.add(imgLabel);
        l5.add(img);
        oper.add(l5);
        l5.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    resetImg(e);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel l6=new JPanel();
        l6.add(modifyBtn);
        l6.add(deleteBtn);
        oper.add(l6);

        Root.add(oper,BorderLayout.SOUTH);

        jFrame.add(Root);
        jFrame.setVisible(true);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Postion.SetPostionCenter(jFrame);
        //初始化表格显示，显示所有的书籍
        fillGoodsTable();
    }

    /**
     * 商品修改事件
     * @param evt
     */
    private void modifyGoodActionPerformed(ActionEvent evt) {
        //获取商品id
        String id = idText.getText();
        //获取商品名称
        String name = nameText.getText();
        //获取商品作者
        // String author=authorText.getText();
        //获取商品价格
        Float price = Float.valueOf(priceText.getText());
        //获取商品id
        //Integer bookTypeId=bookType.getId();

        //获取商品描述
        String Desc = bookDescText.getText();

        String imgurl = img.getText();
        System.out.println(imgurl);


        //判断是否id是否为空
        if (id == null || "".equals(id)) { //为空
            JOptionPane.showMessageDialog(null, "请选中要删除的行！");  //给用户提示
            return;
        }
        //判断商品名称是否为空
        if (name == null || "".equals(name)) { //为空
            JOptionPane.showMessageDialog(null, "商品名称不能为空！");  //给用户提示
            return;
        }

        //判断商品价格是否为空
        if (price == null || "".equals(price)) { //为空
            JOptionPane.showMessageDialog(null, "商品价格不能为空！");  //给用户提示
            return;
        }

        System.out.println("从获取的商品信息创建商品对象:");


        System.out.println(imgurl);
        if(hideImgUrl==""){
            hideImgUrl=imgurl;
        }
        Boolean up = ShopSellService.updateGood(name, price, hideImgUrl, Desc, username,id);
        if (up) {
            JOptionPane.showMessageDialog(null, "商品修改成功n_n");
            //刷新商品表格显示
            fillGoodsTable();
            shopTable.repaint();
            //shopTable.updateUI();

            //重置操作栏
            //resetValue();
        } else { //为0
            JOptionPane.showMessageDialog(null, "商品修改失败u_u");
        }

    }
    /**
     * 商品删除事件
     * @param evt
     */
    private void deleteBookActionPerformed(ActionEvent evt) {
        //获取商品id
        String id=idText.getText();

        if(id==null || "".equals(id)){ //为空
            JOptionPane.showMessageDialog(null, "请选中要删除的行！");  //给用户提示
            return;
        }
        else{
            Integer integer = ShopSellService.delGood(id, username);
            fillGoodsTable();
            shopTable.repaint();
            // shopTable.updateUI();
            if(integer>0){
                JOptionPane.showMessageDialog(null, "删除成功！");  //给用户提示
            }else{
                JOptionPane.showMessageDialog(null, "删除失败！");  //给用户提示
            }
        }

    }
    /**
     * 重置操作栏的所有值
     */
    private void resetValue(){
        idText.setText("");
        nameText.setText("");
        priceText.setText("");
        bookDescText.setText("");
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
        bookDescText.setText((String)shopTable.getValueAt(row, 5));
        String imgurl= (String) shopTable.getValueAt(row, 3);
        ImageIcon icon=null;
        //System.out.println(imgurl);
        if((imgurl!=null)&&!imgurl.isEmpty()) {
            icon = new ImageIcon(new URL(Config.BASE_URL+imgurl));
        } else if(imgurl==null||imgurl.equals("")){
            String url=Config.BASE_URL+"/image/wrong.jpg";
            //System.out.println(url);
            icon = new ImageIcon(new URL(url));
        }
        //icon = new ImageIcon(new URL("http://localhost:8081/image/341421.jpg"));

        icon.setImage(icon.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT));
        img.setIcon(icon);
        img.repaint();
    }


    /**
     * 初始化表格，列出所有的商品
     */
    public static  void fillGoodsTable(){
        //获取表格的模型
        dtm=null;
        dtm=(DefaultTableModel) shopTable.getModel();
        //填充表格时设置成0行（相当于归零处理）
        dtm.setRowCount(0);
        shopsGoods = ShopSellService.getShopsGood(username);
        for(int i=0;i<shopsGoods.size();i++){
            //定义一个集合，由于存储商品信息
            Vector v=new Vector();
            v.add(shopsGoods.get(i).getId());  //添加编号
            v.add(shopsGoods.get(i).getName());
            v.add(shopsGoods.get(i).getPrice());
            v.add(shopsGoods.get(i).getImg());
            String birthday;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
            if(shopsGoods.get(i).getFirsttime()!=null) {
                birthday = df.format(shopsGoods.get(i).getFirsttime());//将当前时间转换成特定格式的时间字符串，这样便可以插入到数据库中
            }else{
                birthday ="";
            }
            v.add(birthday);
            v.add(shopsGoods.get(i).getContent());
            //添加表格新行
            dtm.addRow(v);
        }
        shopTable.setModel(dtm);
        shopTable.repaint();
    }

    private void resetImg(MouseEvent evt) throws IOException {
        JFileChooser jFileChooser=new JFileChooser();
        jFileChooser.setVisible(true);
        jFileChooser.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png)", "jpg", "png"));
        int result =jFileChooser.showOpenDialog(jFrame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());

            try {
                String res=Upload.uploadIMG(selectedFile.getAbsolutePath());
                if(!res.isEmpty()) {

                    String url=res;
                    System.out.println(url);
                    int i = res.lastIndexOf('/');
                    String target=res.substring(i+1);
                    System.out.println(res.substring(i+1));
                    hideImgUrl="/image/"+target;

                    System.out.println(3333);
                    //ImageIcon icon = new ImageIcon(new URL(url));
                    ImageIcon icon = new ImageIcon(url);
                    //JOptionPane.showMessageDialog(null, icon);
                    icon.setImage(icon.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT));
                    img.setIcon(icon);
                    img.repaint();
                    img.updateUI();
                    img.setVisible(true);

//springboot上传图片后bug不能及时回显图片，应该实用应用线程 判断上传完成后再回显数据，能力与时间有限，尚未解决
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void OrdersManage(MouseEvent evt) throws MalformedURLException {
        new OrdersManage(username);
    }

    private void insertGoodActionPerformed(ActionEvent evt) {
        AddGoodFrame addGoodFrame=new AddGoodFrame(username);
        addGoodFrame.init();
        fillGoodsTable();
    }


}