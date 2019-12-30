import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;

/*
 * Created on Tevet 5770 
 */

/**
 * @author לויאן
 */


public class MyActionListener implements ActionListener
{
	Event64 evShabat, evChol, evPadesstrian;

	public MyActionListener(Event64 evShabat, Event64 evChol, Event64 evPadesstrian) {
		super();
		this.evShabat = evShabat;
		this.evChol = evChol;
		this.evPadesstrian = evPadesstrian;
	}

	public void actionPerformed(ActionEvent e) 
	{
		JRadioButton butt=(JRadioButton)e.getSource();
		if(butt.getName().equals("16")) {
			if(butt.isSelected()) {
				evShabat.sendEvent();
				System.out.println("kkk");
			}
			else
			{
				evChol.sendEvent();
				System.out.println("send chol");
			}
		}
		else
			evPadesstrian.sendEvent(butt.getName());
		System.out.println(butt.getName());
		//		butt.setEnabled(false);
		//		butt.setSelected(false);
	}

}
