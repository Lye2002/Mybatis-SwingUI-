package config;

import javax.swing.*;
import java.awt.*;

public class Postion {
    public static void SetPostionCenter(JFrame jf){
        int windowWidth = jf.getWidth(); //获得窗口宽
        int windowHeight = jf.getHeight(); //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        jf.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示
    }
}
