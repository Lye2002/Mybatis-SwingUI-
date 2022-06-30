package Client_UI;


import Bean.Goods;
import config.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class Panel_main {
	String userid;

	public JPanel panel() {

		JPanel pl21 = new JPanel();
		JPanel p1 = new JPanel();
		JPanel o1 = new JPanel();
		JPanel namep = new JPanel();
		JPanel pricep = new JPanel();
		o1.setLayout(new GridLayout(3, 1, 5,5 ));



		JButton bu2 = new JButton("详细信息");
		JLabel l = new JLabel();
		ImageIcon icon = new ImageIcon(Objects.requireNonNull(Panel_main.class.getClassLoader().getResource("wrong.png")));


		icon.setImage(icon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
		l.setIcon(icon);
		l.setBounds(0, 0, 10, 10);
		p1.add(l);

		JLabel namel=new JLabel("商品名称:");
		JLabel pricel=new JLabel("商品价格:");
		JLabel name=new JLabel("暂无数据");
		namep.add(namel);
		namep.add(new JLabel("暂无数据"));
		pricep.add(pricel);
		pricep.add(name);
		o1.add(namep);
		o1.add(pricep);
		o1.add(bu2);
		Box vbox1 = Box.createVerticalBox();
		vbox1.add(p1);
		vbox1.add(o1);
		pl21.add(vbox1);

		pl21.add(vbox1);
		return pl21;
	}

	public JPanel panel(Goods g) throws MalformedURLException {
		this.userid = Main.userid;
		JPanel pl21 = new JPanel();
		JPanel p1 = new JPanel();
		JPanel o1 = new JPanel();
		JPanel namep = new JPanel();
		JPanel pricep = new JPanel();

		JButton bu2 = new JButton("详细信息");

		bu2.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				Detial d = new Detial(userid);
				d.init(g);
			}

		});

		JLabel l = new JLabel();
		JLabel name = new JLabel(g.getName());
		JLabel  price = new JLabel(String.valueOf(g.getPrice()+"￥"));
		ImageIcon icon;

		if ((g.getImg() == null) ||(g.getImg().length()==0)) {

			g.setImg("/image/wrong.jpg");
			//System.out.println("83"+g.getImg());
			icon = new ImageIcon(new URL(Config.BASE_URL+g.getImg()));
//			icon.setImage(icon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
//			l.setIcon(icon);
		}else {
			//icon = new ImageIcon(g.getImg());
			//System.out.println("93"+g.getImg());
			icon = new ImageIcon(new URL(Config.BASE_URL+"/"+g.getImg()));

		}

		//icon = new ImageIcon(new URL("http://localhost:8081/image/341421.jpg"));
		icon.setImage(icon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
		l.setIcon(icon);
		l.setBounds(0, 0, 10, 10);
		l.setSize(170,170);
		p1.add(l);
		o1.setLayout(new GridLayout(3, 1, 5,5 ));

		JLabel namel=new JLabel("商品名称:");
		JLabel pricel=new JLabel("商品价格:");
		namep.add(namel);
		namep.add(name);
		pricep.add(pricel);
		pricep.add(price);
		o1.add(namep);
		o1.add(pricep);
		o1.add(bu2);
		Box vbox1 = Box.createVerticalBox();
		vbox1.add(p1);
		vbox1.add(o1);
		pl21.add(vbox1);
		return pl21;
	}
}