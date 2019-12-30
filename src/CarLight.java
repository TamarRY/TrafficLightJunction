import java.awt.Color;

import javax.swing.JPanel;




public class CarLight extends Thread
{
	Ramzor ramzor;
	JPanel panel;
	int count; //counter for blinking green
	private boolean stop=true;
	public CarLight( Ramzor ramzor,JPanel panel,int key)
	{
		this.ramzor=ramzor;
		this.panel=panel;
		new CarsMaker(panel,this,key);
		start();
	}
	enum Out_State  {On_Every_Day,On_Shabat};
	Out_State out_State;
	enum In_State_EveryDay {On_Red, On_Yellow_Red, On_Green, Blinking_Green, On_Yellow};
	In_State_EveryDay in_State_EveryDay;
	enum In_State_Shabat{Yellow_On, Yellow_Off };
	In_State_Shabat in_State_Shabat;
	enum In_State_BlinkingGreen{Green_On, Green_Off};
	In_State_BlinkingGreen in_State_BlinkingGreen;
	Event64 evRed,evGreen,evShabat, evChol,evOnRed;


	public CarLight(Ramzor ramzor, JPanel panel, boolean stop, Event64 evRed,
			Event64 evGreen, Event64 evShabat, Event64 evChol, Event64 evOnRed) {
		super();
		this.ramzor = ramzor;
		this.panel = panel;
		this.stop = stop;
		this.evRed = evRed;
		this.evGreen = evGreen;
		this.evShabat = evShabat;
		this.evChol = evChol;
		this.evOnRed = evOnRed;
		start();
	}
	public CarLight(Ramzor ramzor, JPanel panel, int count, boolean stop, int key, Event64 evRed, Event64 evGreen, Event64 evShabat,
			Event64 evChol, Event64 evOnRed) {
		super();
		this.ramzor = ramzor;
		this.panel = panel;
		this.count = count;
		this.stop = stop;
		this.evRed = evRed;
		this.evGreen = evGreen;
		this.evShabat = evShabat;
		this.evChol = evChol;
		this.evOnRed = evOnRed;
		new CarsMaker(panel,this,key);
		start();
	}
	public void run()
	{
		out_State=Out_State.On_Every_Day;
		in_State_EveryDay=In_State_EveryDay.On_Red;
		setRedOn();
		//new CarsMaker(panel,this,1);

		try 
		{
			while(true) {
				switch (out_State) {
				case On_Every_Day:
					while (out_State==Out_State.On_Every_Day) {
						switch (in_State_EveryDay) {
						case On_Red:
							while(true) {
								if(evShabat.arrivedEvent()) {
									evShabat.waitEvent();
									stop=true;
									setRedOff();
									setYellowOn();
									out_State= Out_State.On_Shabat;
									in_State_Shabat= In_State_Shabat.Yellow_On;
									break;
								}
								else if(evGreen.arrivedEvent())
								{
									evGreen.waitEvent();
									stop=true;
									setYellowOn();
									in_State_EveryDay=In_State_EveryDay.On_Yellow_Red;
									break;
								}
								else 
									yield();
							}
							break;
						case On_Yellow_Red:
							while (out_State==Out_State.On_Every_Day) {
								if(evShabat.arrivedEvent()) {
									evShabat.waitEvent();
									stop=true;
									setYellowOn();
									out_State= Out_State.On_Shabat;
									in_State_Shabat= In_State_Shabat.Yellow_On;
									break;
								}
								else 
								{
									sleep(500);
									setYellowOff();
									setRedOff();
									setGreenOn();
									stop=false;
									in_State_EveryDay=In_State_EveryDay.On_Green;
									break;
								}
							}
							break;
						case On_Green:
							while(out_State==Out_State.On_Every_Day) {
								if(evShabat.arrivedEvent()) {
									evShabat.waitEvent();
									stop=true;
									setGreenOff();
									setYellowOn();
									out_State= Out_State.On_Shabat;
									in_State_Shabat= In_State_Shabat.Yellow_On;
									break;
								}
								else if(evRed.arrivedEvent())
								{
									evRed.waitEvent();
									count=0;
									stop=false;
									in_State_EveryDay=In_State_EveryDay.Blinking_Green;
									in_State_BlinkingGreen=In_State_BlinkingGreen.Green_On;
									break;
								}
								else 
									yield();
							}
							break;
						case Blinking_Green:
							while (count<3) {
								switch (in_State_BlinkingGreen) {
								case Green_On:
									if(evShabat.arrivedEvent()) {
										evShabat.waitEvent();
										stop=true;
										setGreenOff();
										setYellowOn();
										out_State= Out_State.On_Shabat;
										in_State_Shabat= In_State_Shabat.Yellow_On;
									}
									else 
									{
										sleep(250);
										setGreenOff();
										in_State_BlinkingGreen=In_State_BlinkingGreen.Green_Off;
									}
									break;
								case Green_Off:
									if(evShabat.arrivedEvent()) {
										evShabat.waitEvent();
										stop=true;
										setYellowOn();
										out_State= Out_State.On_Shabat;
										in_State_Shabat= In_State_Shabat.Yellow_On;
									}
									else 
									{
										sleep(250);
										setGreenOn();
										count++;
										in_State_BlinkingGreen=In_State_BlinkingGreen.Green_On;
									}
									break;
								default:
									break;
								}
							}
							stop=true;
							setGreenOff();
							setYellowOn();
							in_State_EveryDay=In_State_EveryDay.On_Yellow;
							break;
						case On_Yellow:
							if(evShabat.arrivedEvent()) {
								evShabat.waitEvent();
								stop=true;
								setYellowOn();
								out_State= Out_State.On_Shabat;
								in_State_Shabat= In_State_Shabat.Yellow_On;
							}
							else 
							{
								sleep(250);
								stop=true;
								setYellowOff();
								setRedOn();
								evOnRed.sendEvent();
								in_State_EveryDay=In_State_EveryDay.On_Red;
							}
							break;
						default:
							break;
						}
					}
					break;
				case On_Shabat:
					while(out_State==Out_State.On_Shabat) {
						switch (in_State_Shabat) {
						case Yellow_On:
							if(evChol.arrivedEvent()) {
								evChol.waitEvent();
								setYellowOff();
								setRedOn();
								out_State=Out_State.On_Every_Day;
								in_State_EveryDay=In_State_EveryDay.On_Red;
								break;
							}
							else {
								sleep(250);
								setYellowOff();
								in_State_Shabat=In_State_Shabat.Yellow_Off;								
							}
							break;
						case Yellow_Off:
							if(evChol.arrivedEvent()) {
								evChol.waitEvent();
								setRedOn();
								out_State=Out_State.On_Every_Day;
								in_State_EveryDay=In_State_EveryDay.On_Red;
								break;
							}
							else {
								sleep(250);
								setYellowOn();
								in_State_Shabat=In_State_Shabat.Yellow_On;								
							}

							break;

						default:
							break;
						}
					}
					break;
				default:
					break;
				}
			}

		} catch (InterruptedException e) {}

	}
	private void setGreenOff() {
			setLight(3,Color.GRAY);

	}
	private void setGreenOn() {
		setLight(3, Color.GREEN);

	}
	private void setRedOff() {
		setLight(1,Color.GRAY);
	}
	private void setYellowOff() {
		setLight(2,Color.GRAY);
	}
	private void setYellowOn() {
		setLight(2,Color.YELLOW);
	}
	private void setRedOn() {
		setLight(1,Color.RED);
	}
	public void setLight(int place, Color color)
	{
		ramzor.colorLight[place-1]=color;
		panel.repaint();
	}

	public boolean isStop()
	{
		return stop;
	}
}
