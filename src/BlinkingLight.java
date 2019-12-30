import java.awt.Color;

import javax.swing.JPanel;
class BlinkingLight extends Thread
{
	Ramzor ramzor;
	JPanel panel;
	public BlinkingLight( Ramzor ramzor,JPanel panel)
	{
		this.ramzor=ramzor;
		this.panel=panel;
		start();
	}
	enum State {On, Off};
	State state;

	public void run()
	{
		try 
		{
			state=State.On;
			setOn();
			while (true) {
				switch (state) {
				case On:
					sleep(250);
					setOff();
					state=State.Off;
					break;
				case Off:
					sleep(250);
					setOn();
					state=State.On;
					break;
				default:
					break;
				}
			}
		} catch (InterruptedException e) {}

	}
	public void setOn() {
		setLight(1,Color.YELLOW);
	}
	public void setOff() {
		setLight(1,Color.GRAY);
	}
	public void setLight(int place, Color color)
	{
		ramzor.colorLight[place-1]=color;
		panel.repaint();
	}
}
