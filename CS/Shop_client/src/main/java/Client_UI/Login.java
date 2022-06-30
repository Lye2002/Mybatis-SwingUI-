package Client_UI;

import Bean.UserLogin;
import Service.UserDao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    JFrame frame;
    private  void CreatGUI()
    {
        frame= new JFrame("用户登陆界面");
        JPanel rootPane=new JPanel();
        frame.setContentPane(rootPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(320, 200);
        frame.setLocationRelativeTo(rootPane);
        frame.setVisible(true);
        //JFrame.setDefaultLookAndFeelDecorated(true);
        placeComponents(rootPane);

    }
    private  void placeComponents(JPanel panel) {
        panel.setLayout(null);
        JLabel userLabel = new JLabel("用户名:");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);


        JButton loginButton = new JButton("登录");
        loginButton.setBounds(350/2-40, 200/2-12, 80, 25);
        panel.add(loginButton);


        ActionListener listener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserLogin user = UserDao.findUser(userText.getText(), passwordText.getText());
                System.out.println(user);
                if(user!=null) {
                    Main mainWindows=  new Main(user.getUsername(),user.getPassword());
                    System.out.println(mainWindows);
                    frame.dispose();
                    try {
                        mainWindows.init();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    System.out.print("成功 OK!\r\n");
                }else{
                    System.out.println("失败");
                }
            }
        };

        loginButton.addActionListener(listener);

    }

    public static void main(String[] args) {
        Login login=new Login();
        login.CreatGUI();
    }
}
