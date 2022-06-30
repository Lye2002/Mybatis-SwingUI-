package Client_UI;

import Service.UserDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ChangePassword extends JFrame{
    private JPanel contentPanel = new JPanel();
    // Label标签存放背景图片
    private JLabel label;
    public static String username;
    // 设置按钮组件
    private JButton ok = new JButton("确定修改");

    private JLabel jlb1 = new JLabel("原密码:"), jlb2 = new JLabel("密码:"), jlb3 = new JLabel("确认密码:"),
            jlbtitle = new JLabel("修改密码界面");
    // 设置文本框组件
    private JTextField oldpassword = new JTextField(), password1 = new JTextField(), password2 = new JTextField();

    public ChangePassword(String username) {
        this.init(username);
        this.addListener();
    }

    private void init(String username) {
        this.username=username;
        this.setTitle("修改密码");
        this.setSize(500, 350);

        ImageIcon image1 = new ImageIcon("837878.jpg"); // 界面背景图片
        JLabel backLabel = new JLabel();
        backLabel.setIcon(image1);
        label = new JLabel(image1);
        label.setBounds(0, 0, 1000, 400);
        // 在LayeredPane最底层上添加两个带图片的标签，并且label2在label上方
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        // 将内容面板设置为透明，就能够看见添加在LayeredPane上的背景。
        ((JPanel) this.getContentPane()).setOpaque(false);

        /*
         * 添加组件到contentPanel容器中 布局方式为自由布局。
         */
        contentPanel.setLayout(null);
        add(oldpassword);
        add(password1);
        add(password2);
        add(ok);
        add(jlb1);
        add(jlb2);
        add(jlb3);
        add(jlbtitle);

        /*
         * 组件绝对位置
         */
        jlb1.setBounds(40, 130, 90, 25);
        jlb1.setForeground(Color.BLACK);
        oldpassword.setBounds(95, 130, 300, 25);

        password1.setBounds(95, 154, 300, 25);
        jlb2.setBounds(40, 154, 90, 25);
        jlb2.setForeground(Color.BLACK);

        password2.setBounds(95, 178, 300, 25);
        jlb3.setBounds(40, 178, 90, 25);
        jlb3.setForeground(Color.BLACK);

        ok.setBounds(195, 225, 90, 20);


        jlbtitle.setBounds(180, 45, 200, 50);
        Font f = new Font("微软雅黑", Font.BOLD, 30);
        jlbtitle.setFont(f);
        jlbtitle.setForeground(Color.BLUE);

        /*
         * 组件透明化
         */
        oldpassword.setOpaque(true);
        password1.setOpaque(true);
        password2.setOpaque(true);
        contentPanel.setOpaque(false);
        getContentPane().add(contentPanel);

        int windowWidth = this.getWidth(); //获得窗口宽
        int windowHeight = this.getHeight(); //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        this.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示
        this.setVisible(true);

    }



    /*
     * 事件监听
     */
    private void addListener() {
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Change(username,oldpassword.getText(), password1.getText(), password2.getText());
            }
        });

    }

    public void Change(String username,String oldpassword, String pwd1, String pwd2) {
        System.out.println(UserDao.findUser(username, oldpassword));
        if((UserDao.findUser(username, oldpassword)!=null)&&pwd1.equals(pwd2)==true) {
            if(!((pwd1.isEmpty())&&(pwd1.isEmpty()))) {
                Boolean aBoolean = UserDao.changePassword(username, pwd1);
                if(aBoolean){
                    JOptionPane.showConfirmDialog(null, "修改成功!", "请确认", 0);
                }else{
                    JOptionPane.showConfirmDialog(null, "修改失败!", "请确认", 0);
                }
            }else{
                JOptionPane.showConfirmDialog(null, "请输入新密码!", "请确认", 0);
            }
        }else if(UserDao.findUser(username, oldpassword)==null){
            JOptionPane.showConfirmDialog(null, "账号不存在或者密码错误!", "请确认", 0);
        }else{
            JOptionPane.showConfirmDialog(null, "两次输入的密码不同,清重新输入!", "请确认", 0);
        }
    }

    public static void main(String[] args) {
        new ChangePassword("111").setVisible(true);
    }
}
