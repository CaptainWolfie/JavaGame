package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {

	public boolean LeftClick = false, RightClick = false, MiddleClick = false;
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			LeftClick = true;
		if (e.getButton() == MouseEvent.BUTTON2)
			MiddleClick = true;
		if (e.getButton() == MouseEvent.BUTTON3)
			RightClick = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			LeftClick = false;
		if (e.getButton() == MouseEvent.BUTTON2)
			MiddleClick = false;
		if (e.getButton() == MouseEvent.BUTTON3)
			RightClick = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
