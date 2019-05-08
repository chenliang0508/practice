package com.swing;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.db.PackageDAOImpl;
import com.patch.SVNManager;

public class PackLayout extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final int WIDTH=300;
    static final int HEIGHT=210;
    public PackLayout() {
    	setTitle("银商资讯系统打包");
    	init();
        this.setLayout(null);
        this.setSize(WIDTH,HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();  
        this.setLocation(screenSize.width/2-WIDTH/2, screenSize.height/2-HEIGHT/2);
        this.setVisible(true);
        this.setResizable(false);
    }
    
    public void init() {
    	List<String> list = PackageDAOImpl.getInstance().getAllProject();
    	String[] projects = list.toArray(new String[list.size()]);
    	Container container = this.getContentPane();
    	JLabel projectLabel = new JLabel("选择项目:");
    	projectLabel.setBounds(10,20,80,25);
        container.add(projectLabel);
        final JComboBox<String> comboBox = new JComboBox<String>(projects);
        comboBox.setBounds(100,20,165,25);
        container.add(comboBox);
    	
        JLabel startLabel = new JLabel("版本号起:");
        startLabel.setBounds(10,50,80,25);
        container.add(startLabel);
        final JTextField startText = new JTextField(20);
        startText.setBounds(100,50,165,25);
        container.add(startText);
        
        JLabel endLabel = new JLabel("版本号止:");
        endLabel.setBounds(10,80,80,25);
        container.add(endLabel);
        final JTextField endText = new JTextField(20);
        endText.setBounds(100,80,165,25);
        container.add(endText);
     
        JLabel resultLabel = new JLabel("结果:");
        resultLabel.setBounds(10,110,80,25);
        container.add(resultLabel);
     
        final JLabel result = new JLabel();
        result.setBounds(100,110,165,25);
        container.add(result);
        
        JButton button = new JButton("确定");
        button.setBounds(100, 140, 80, 25);
        container.add(button);
        
        button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String project = comboBox.getSelectedItem().toString();
				String verstart = startText.getText();
				String verend = endText.getText()!=null&&!"".equals(endText.getText())?endText.getText():"-1";
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							button.setEnabled(false);
							result.setText("执行中...");
							SVNManager.getFileFromSVN(project,Integer.parseInt(verstart),Integer.parseInt(verend));
							result.setText("成功");
							button.setEnabled(true);
						} catch (NumberFormatException e1) {
							result.setText("请输入数字");
							button.setEnabled(true);
						} catch (Exception e1) {
							e1.printStackTrace();
//							try {
//								PrintWriter pw = new PrintWriter(new File("./error.log"));
//								e1.printStackTrace(pw);
//								pw.flush();
//								pw.close();
//								result.setText("失败");
//								button.setEnabled(true);
//							} catch (IOException e2) {
//								e2.printStackTrace();
//							}
						}
					}
				}).start();
			} 
		});
        
      }
}
