import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.Timer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class Scene extends JFrame implements KeyListener{
	private GridBagConstraints [][] gc = new GridBagConstraints[10][10];
	private JLabel[][] grounds = new JLabel[10][10]; //10x10 maps
	Random ran = new Random();
	
	private ImageIcon ground; 
	private ImageIcon player; 
	private ImageIcon enemy; 
	private ImageIcon sword; 
	private ImageIcon win; 
	private ImageIcon loss;

	private int playerx; //player's x
	private int playery; //plaer's y
	private int enemyx;  //enemy's x
	private int enemyy;  //enemy's y
	private int swordx;  //sword's x
	private int swordy;  //sword's y

	private boolean havesword; //set if player get the sword
	private boolean stepsword; //set if enemy step on sword

	//The way player moves, the way enemy moves, the result of game, did player get the sword 
	Scene(){	
		setLayout(new GridBagLayout());
		playerx = 9;
		playery = 9;
		enemyx = 0;
		enemyy = 0;
		swordx = ran.nextInt(10);
		swordy = ran.nextInt(10);
		
		ground = new ImageIcon(Scene.class.getResource("res/ground.png"));
		sword = new ImageIcon(Scene.class.getResource("res/sword.png"));
		enemy = new ImageIcon(Scene.class.getResource("res/Enemy.png"));
		player = new ImageIcon(Scene.class.getResource("res/Player.png"));
		win = new ImageIcon(Scene.class.getResource("res/Win.png"));
		loss = new ImageIcon(Scene.class.getResource("res/Loss.png"));
		Image image = player.getImage();
		Image newimg = image.getScaledInstance(66, 64,  java.awt.Image.SCALE_SMOOTH);
		player = new ImageIcon(newimg);
		image = win.getImage();
		newimg = image.getScaledInstance(66, 64,  java.awt.Image.SCALE_SMOOTH);
		win = new ImageIcon(newimg);
		image = loss.getImage();
		newimg = image.getScaledInstance(66, 64,  java.awt.Image.SCALE_SMOOTH);
		loss = new ImageIcon(newimg);
		image = sword.getImage();
		newimg = image.getScaledInstance(66, 64,  java.awt.Image.SCALE_SMOOTH); 
		sword = new ImageIcon(newimg);
		
		for(int i = 0; i < 10 ; i++){
			for(int j = 0;j < 10;j++){
				grounds[i][j] = new JLabel();
				gc[i][j] = new GridBagConstraints();
				gc[i][j].gridx = i;
				gc[i][j].gridy = j;
				gc[i][j].gridwidth = 1;
				gc[i][j].gridheight = 1;
				add(grounds[i][j],gc[i][j]);
				grounds[i][j].setIcon(ground);
			}
		}
		
		grounds[playerx][playery].setIcon(player);
		grounds[enemyx][enemyy].setIcon(enemy);
		grounds[swordx][swordy].setIcon(sword);
		addKeyListener(this);
		setVisible(true);
		
        Timer t= new Timer(1000, new enemyMovment());
        t.start();
	}
	public void keyTyped(KeyEvent e) {
		grounds[playerx][playery].setIcon(ground);
        if(e.getKeyChar()=='w'){
        	if(playery != 0) playery--;
        }else if(e.getKeyChar()=='s'){
        	if(playery != 9) playery++;
        }else if(e.getKeyChar()=='a'){
        	if(playerx != 0) playerx--;
        }else if(e.getKeyChar()=='d'){
        	if(playerx != 9) playerx++;
        }
        grounds[playerx][playery].setIcon(player);
        if(playerx == swordx && playery == swordy ){
        	havesword = true;
        }
        Judgement();
    }
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void Judgement(){
		if(playerx == enemyx && playery == enemyy ){
        	if(havesword){
        		grounds[playerx][playery].setIcon(win);
        		JOptionPane.showMessageDialog(this,"win!");
        		this.dispose();
        	}else{
        		grounds[playerx][playery].setIcon(loss);
        		JOptionPane.showMessageDialog(this,"lose!");
        		this.dispose();
        	}
        	System.exit(0);
        }
	}
	class enemyMovment implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(stepsword) grounds[enemyx][enemyy].setIcon(sword);
			else grounds[enemyx][enemyy].setIcon(ground);
			if(enemyx-playerx > 0 && enemyx != 0){
				enemyx --;
			}else if(enemyx-playerx < 0 &&enemyx != 9){
				 enemyx ++;
			}else if(enemyy-playery > 0 && enemyy != 0){
				enemyy --;
			}else if(enemyy-playery < 0 && enemyy != 9){
				 enemyy ++;
			}
			if(swordx == enemyx && swordy == enemyy){
				stepsword = true;
			}else{
				stepsword = false;
			}
			grounds[enemyx][enemyy].setIcon(enemy);
			Judgement();
		}
	}
	
}
