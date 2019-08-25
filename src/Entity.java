import java.awt.Image;
import javax.swing.ImageIcon;

public class Entity {
	int x, y;
	Image tmp;
	ImageIcon img;
	Entity(int x1,int y1, ImageIcon image){
		x = x1;
		y = y1;
		tmp = image.getImage();
		Image newimg = tmp.getScaledInstance(66, 64,  java.awt.Image.SCALE_SMOOTH);
		img = new ImageIcon(newimg);
	}
}
