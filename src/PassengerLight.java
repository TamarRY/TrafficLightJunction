import java.awt.Color;
import javax.swing.JPanel;



class PassengerLight extends Thread
{
	Ramzor ramzor;
	JPanel panel;
	Event64 evRed,evGreen,evShabat, evChol,evOnRed;

	public PassengerLight(Ramzor ramzor, JPanel panel, Event64 evRed, Event64 evGreen, Event64 evShabat, Event64 evChol,
			Event64 evOnRed) {
		this.ramzor = ramzor;
		this.panel = panel;
		this.evRed = evRed;
		this.evGreen = evGreen;
		this.evShabat = evShabat;
		this.evChol = evChol;
		this.evOnRed = evOnRed;
		start();
	}

	enum Out_State  {On_Every_Day,On_Shabat};
	Out_State out_State;
	enum In_State {On_Red, on_Green};
	In_State in_State;
	public PassengerLight( Ramzor ramzor,JPanel panel)
	{
		this.ramzor=ramzor;
		this.panel=panel;
		start();
	}

	public void run()
	{
		out_State=Out_State.On_Every_Day;
		in_State=In_State.On_Red;
		setRed();
		try 
		{
			while(true) {
				switch (out_State) {
				case On_Every_Day:
					while(out_State==Out_State.On_Every_Day) {
						switch (in_State) {
						case On_Red:
							while(true) {
								if(evShabat.arrivedEvent()) {
									//System.out.println("llllllllllll");
									evShabat.waitEvent();
									setOff();
									out_State=Out_State.On_Shabat;
									break;
								}
								else
									if(evGreen.arrivedEvent()) {
										evGreen.waitEvent();
										setGreen();
										in_State=In_State.on_Green;
										break;
									}
									else {
										yield();
									}
							}
							break;
						case on_Green:
							while(true) {
								if(evShabat.arrivedEvent()) {
									evShabat.waitEvent();
									setOff();
									out_State=Out_State.On_Shabat;
									break;
								}
								else
									if(evRed.arrivedEvent()) {
										evRed.waitEvent();
										evOnRed.sendEvent();
										setRed();
										in_State=In_State.On_Red;
										break;
									}
									else {
										yield();
									}
							}
							break;

						default:
							break;
						}
					}

					break;
				case On_Shabat:
					
					while (true) {
						if(evChol.arrivedEvent())
						{
							evChol.waitEvent();
							System.out.println("got chol passenger");
							setRed();
							out_State=Out_State.On_Every_Day;
							in_State=In_State.On_Red;
							break;
						}
						else 
							yield();
					}
					
				default:
					break;
				}
			}


		} catch (Exception e) {}

	}
	private void setGreen() {
		setLight(1,Color.GRAY);
		setLight(2,Color.GREEN);
		
	}

	private void setOff() {
		setLight(1,Color.GRAY);
		setLight(2,Color.GRAY);		
	}

	private void setRed() {
		setLight(1,Color.RED);
		setLight(2,Color.GRAY);		
	}

	public void setLight(int place, Color color)
	{
		ramzor.colorLight[place-1]=color;
		panel.repaint();
	}
}
