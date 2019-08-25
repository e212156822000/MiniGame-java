import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class Scene extends JFrame{
	
	private GridBagConstraints [][] gc = new GridBagConstraints[10][10];
	private JLabel[][] grounds = new JLabel[10][10]; //10x10 maps
	Random ran = new Random();

	private ImageIcon ground; 
	private boolean havesword; //set if player get the sword
	private boolean stepsword; //set if enemy step on sword

	Entity player = new Entity(9,9,new ImageIcon(Scene.class.getResource("res/Player.png")));
	Entity enemy = new Entity(0,0, new ImageIcon(Scene.class.getResource("res/Enemy.png")));
	Entity sword = new Entity(ran.nextInt(10),ran.nextInt(10),new ImageIcon(Scene.class.getResource("res/sword.png")));
	
	//The way player moves, the way enemy moves, the result of game, did player get the sword 
	Scene(){	
		ground = new ImageIcon(Scene.class.getResource("res/ground.png"));
		
		this.setTitle("Minigame");
		this.setSize(670, 670);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLayout(new GridBagLayout());
		
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
		
		this.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				grounds[player.x][player.y].setIcon(ground);
		        if(e.getKeyChar()=='w'){
		        	if(player.y != 0) player.y--;
		        }else if(e.getKeyChar()=='s'){
		        	if(player.y != 9) player.y++;
		        }else if(e.getKeyChar()=='a'){
		        	if(player.x != 0) player.x--;
		        }else if(e.getKeyChar()=='d'){
		        	if(player.x != 9) player.x++;
		        }
		        grounds[player.x][player.y].setIcon(player.img);
		        if(player.x == sword.x && player.y == sword.y ){
		        	havesword = true;
		        }
		        Judgement();
		    }
	      });
		
		grounds[player.x][player.y].setIcon(player.img);
		grounds[enemy.x][enemy.y].setIcon(enemy.img);
		grounds[sword.x][sword.y].setIcon(sword.img);
		
        Timer t= new Timer(1000, new enemyMovment());
        t.start();
	}
	public ImageIcon mkImg(ImageIcon i) {
		Image tmp = i.getImage();
		Image newimg = tmp.getScaledInstance(66, 64,  java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}

	public void Judgement(){
		if(player.x == enemy.x && player.y == enemy.y ){
        	if(havesword){
        		grounds[player.x][player.y].setIcon(this.mkImg(new ImageIcon(Scene.class.getResource("res/Win.png"))));
        		JOptionPane.showMessageDialog(this,"win!");
        		this.dispose();
        	}else{
        		grounds[player.x][player.y].setIcon(this.mkImg(new ImageIcon(Scene.class.getResource("res/Loss.png"))));
        		JOptionPane.showMessageDialog(this,"lose!");
        		this.dispose();
        	}
        	System.exit(0);
        }
	}
	class enemyMovment implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(stepsword) grounds[enemy.x][enemy.y].setIcon(sword.img);
			else grounds[enemy.x][enemy.y].setIcon(ground);
			if(enemy.x-player.x > 0 && enemy.x != 0){
				enemy.x --;
			}else if(enemy.x-player.x < 0 &&enemy.x != 9){
				 enemy.x ++;
			}else if(enemy.y-player.y > 0 && enemy.y != 0){
				enemy.y --;
			}else if(enemy.y-player.y < 0 && enemy.y != 9){
				 enemy.y ++;
			}
			if(sword.x == enemy.x && sword.y == enemy.y){
				stepsword = true;
			}else{
				stepsword = false;
			}
			grounds[enemy.x][enemy.y].setIcon(enemy.img);
			Judgement();
		}
	}	
}