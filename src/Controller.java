import java.awt.Color;

import javax.swing.JPanel;





public class Controller extends Thread {
	
	Event64[] evGreen, evRed,evShabat,evChol,evOnRed;
	Event64 evCholPressed, evShabatPressed,evPadesstrianPressed;
	MyTimer72 timer;
	Event64   evTimer;
	int[] group0={0,6,7,9,10,12,13};
	int[] group1= {1,4,5,6,7,9,10,12,13};
	int[] group2= {2,3,4,5,11,8,14,15};


	public Controller(Event64[] evGreen, Event64[] evRed, Event64[] evShabat, Event64[] evChol, Event64[] evOnRed,
			Event64 evCholPressed, Event64 evShabatPressed, Event64 evPadesstrianPressed) {
		super();
		this.evGreen = evGreen;
		this.evRed = evRed;
		this.evShabat = evShabat;
		this.evChol = evChol;
		this.evOnRed = evOnRed;
		this.evCholPressed = evCholPressed;
		this.evShabatPressed = evShabatPressed;
		this.evPadesstrianPressed = evPadesstrianPressed;
		start();
	}

	enum Out_State  {On_Every_Day,On_Shabat};
	Out_State out_State;
	enum In_State {Group0_On_Green,Mid_State0,Group0_On_Red,Group1_On_Green,Mid_State1,Group1_On_Red,Group2_On_Green,Mid_State2,Group2_On_Red,
		C0,C1,C2}
	In_State in_State;
	private Object data;
	private int number; 


	public void run() {
		evTimer=new Event64();
		timer=  new MyTimer72(5000,evTimer);
		out_State=Out_State.On_Every_Day;
		in_State=In_State.Group0_On_Green;
		evGreenGruop0();
		
		
		try {
			while(true) {
				switch (out_State) {
				case On_Every_Day:
					while (out_State==Out_State.On_Every_Day)
					{
						switch (in_State) {
						case Group0_On_Green:
							System.out.println("Group0_On_Green");
							while(true)
							{
								if (evTimer.arrivedEvent())
								{
									evTimer.waitEvent();
									evRedGroup0();
									in_State=In_State.Mid_State0;
									break;
								}
								else if(evShabatPressed.arrivedEvent()) {
									evShabatPressed.waitEvent();
									evShabatToAll();
									out_State=Out_State.On_Shabat;
									break;
								}
								else yield();
							}	

							break;
						case Mid_State0:
							System.out.println("Mid_State0");
							while(true)
							{
								if(evGroup0OnRed()) 
								{
									evTimer=new Event64();
									timer=  new MyTimer72(1000,evTimer);
									in_State=In_State.Group0_On_Red;
									break;
								}
								else if(evShabatPressed.arrivedEvent())
								{
									evShabatPressed.waitEvent();
									evShabatToAll();
									out_State=Out_State.On_Shabat;
									break;
								}
								else yield();
							}
							break;
						case Group0_On_Red:
							System.out.println("Group0_On_Red");
							while(true) 
							{
								if (evTimer.arrivedEvent())
								{
									evTimer.waitEvent();
									evTimer=new Event64();
									timer=  new MyTimer72(5000,evTimer);
									evGreenGroup1();
									in_State=In_State.Group1_On_Green;
									break;
								}
								else if(evPadesstrianPressed.arrivedEvent())
								{
									data=evPadesstrianPressed.waitEvent();
									number=Integer.parseInt((String) data);
									in_State=In_State.C0;
									break;
								}
								else if(evShabatPressed.arrivedEvent()) {
									evShabatPressed.waitEvent();
									evShabatToAll();
									out_State=Out_State.On_Shabat;
									break;
								}
								else yield();
							}
							break;
						case Group1_On_Green:
							System.out.println("Group1_On_Green");
							while (true) {
								if (evTimer.arrivedEvent())
								{
									evTimer.waitEvent();
									evRedGroup1();
									in_State=In_State.Mid_State1;
									break;
								}
								else 
									if(evShabatPressed.arrivedEvent()) {
										evShabatPressed.waitEvent();
										evShabatToAll();
										out_State=Out_State.On_Shabat;
										break;
									}
									else yield();
							}
							break;
						case Mid_State1:
							System.out.println("Mid_State1");
							while(true) {
								if(evGroup1OnRed()) {
									evTimer=new Event64();
									timer=  new MyTimer72(1000,evTimer);
									in_State=In_State.Group1_On_Red;
									break;
								}
								else 
									if(evShabatPressed.arrivedEvent()) {
										evShabatPressed.waitEvent();
										evShabatToAll();
										out_State=Out_State.On_Shabat;
										break;
									}
									else yield();
							}
							break;
						case Group1_On_Red:
							System.out.println("Group1_On_Red");
							while(true) {
								//System.out.println("Group1_On_Red1");
								if (evTimer.arrivedEvent()) {
									evTimer.waitEvent();
									EvGreenGroup2();
									evTimer=new Event64();
									timer=  new MyTimer72(5000,evTimer);
									in_State=In_State.Group2_On_Green;
									break;
									
								}
								else {
									if(evPadesstrianPressed.arrivedEvent()) {
										data=evPadesstrianPressed.waitEvent();
										number=Integer.parseInt((String) data);
										in_State=In_State.C1;
										break;
									}
									else {
										if(evShabatPressed.arrivedEvent()) {
											evShabatPressed.waitEvent();
											evShabatToAll();
											out_State=Out_State.On_Shabat;
											break;
										}
										else yield();
									}
								}
							}
							break;
						case Group2_On_Green:
							System.out.println("Group2_On_Green");
							while(true) 
							{
								if (evTimer.arrivedEvent()) {
									evTimer.waitEvent();
									evRedGroup2();
									in_State=In_State.Mid_State2;
									break;
								}
								else 
									if(evShabatPressed.arrivedEvent()) {
										evShabatPressed.waitEvent();
										evShabatToAll();
										out_State=Out_State.On_Shabat;
										break;
									}
									else yield();
							}
							break;
						case Mid_State2:
							System.out.println("Mid_State2");
							while(true) {
								if(evGroup2OnRed()) {
									evTimer=new Event64();
									timer=  new MyTimer72(1000,evTimer);
									in_State=In_State.Group2_On_Red;
									break;
								}
								else 
									if(evShabatPressed.arrivedEvent()) {
										evShabatPressed.waitEvent();
										evShabatToAll();
										out_State=Out_State.On_Shabat;
										break;
									}
									else yield();
							}
							break;
						case Group2_On_Red:
							System.out.println("Group2_On_Red");
							while (true) {
								if (evTimer.arrivedEvent()) {
									evTimer.waitEvent();
									evGreenGruop0();
									evTimer=new Event64();
									timer=  new MyTimer72(5000,evTimer);
									in_State=In_State.Group0_On_Green;
									break;
								}
								else {
									if(evPadesstrianPressed.arrivedEvent()) {
										data= evPadesstrianPressed.waitEvent();
										number=Integer.parseInt((String) data);
										in_State=In_State.C2;
										break;
									}
									else {
										if(evShabatPressed.arrivedEvent()) {
											evShabatPressed.waitEvent();
											evShabatToAll();
											out_State=Out_State.On_Shabat;
											break;
										}
										else yield();
									}
								}
							}
							break;
						case C0:
							System.out.println("C0");
							while(true) {
								if(in1(number)) {
									evGreenGroup1();
									in_State=In_State.Group1_On_Green;
									break;
								}
								else {
									if (in0(number)) {
										evGreenGruop0();
										in_State=In_State.Group0_On_Green;
										break;
									}
									else
										if(in2(number)) {
											EvGreenGroup2();
											in_State=In_State.Group2_On_Green;
											break;
										}
								}
							}
							break;
						case C1:
							System.out.println("C1");
							while(true) {
								if(in2(number)) {
									EvGreenGroup2();
									in_State=In_State.Group2_On_Green;
									break;
								}
								else {
									if(in0(number)) {
										evGreenGruop0();
										in_State=In_State.Group0_On_Green;
										break;	
									}
									else {
										if(in1(number)) {
											evGreenGroup1();
											in_State=In_State.Group1_On_Green;
											break;
										}
									}
								}
							}

							break;
						case C2:
							System.out.println("C2");
							while(true) {
								if(in0(number)) {
									evGreenGruop0();
									in_State=In_State.Group0_On_Green;
									break;	
								}
								else {
									if(in1(number)) {
										evGreenGroup1();
										in_State=In_State.Group1_On_Green;
										break;
									}
									else if(in2(number)){
										EvGreenGroup2();
										in_State=In_State.Group2_On_Green;
										break;
									}
								}
							}

							break;
						default:
							break;
						}
					}

					break;
				case On_Shabat:
					System.out.println("On_Shabat");
					while(true) {
						if(evCholPressed.arrivedEvent())
						{
							System.out.println("chol pressed");
							evCholPressed.waitEvent();
							sendEvCholToAll();
							evTimer=new Event64();
							timer=  new MyTimer72(5000,evTimer);
							evGreenGruop0();
							out_State=Out_State.On_Every_Day;
							in_State=In_State.Group0_On_Green;
							break;
						}
						else
							yield();
					}
					break;
				default:
					break;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}


	private void sendEvCholToAll() {
		for (int i = 0; i < evChol.length; i++) {
			evChol[i].sendEvent();
		}
	}


	private boolean in2(int n) {
		for(int i:group2) {
			if(i==n)
				return true;
		}
		return false;
	}


	private boolean in1(int n) {
		for(int i:group1) {
			if(i==n)
				return true;
		}
		return false;
	}


	private boolean in0(int n) {
		for(int i:group0) {
			if(i==n)
				return true;
		}
		return false;
	}


	private boolean evGroup2OnRed() {
		boolean flag=true;
		for(int i:group2)
		{
			flag=flag && evOnRed[i].arrivedEvent();
		}
		if(flag) {
			for(int i:group2) {
				evOnRed[i].waitEvent();
			}
		}
		return flag;
	}


	private void evRedGroup2() {
		for (int i : group2) {
			evRed[i].sendEvent();
		}
	}


	private void EvGreenGroup2() {
		for (int i : group2) {
			System.out.println("send green "+i);

			evGreen[i].sendEvent();
		}
	}


	private boolean evGroup1OnRed() {
		boolean flag=true;
		for(int i:group1)
		{
			flag=flag && evOnRed[i].arrivedEvent();
		}
		if(flag) {
			for(int i:group1) {
				evOnRed[i].waitEvent();
			}
		}
		return flag;
	}


	private void evRedGroup1() {
		for (int i : group1) {
			evRed[i].sendEvent();
		}
	}


	private void evGreenGroup1() {
		for (int i : group1) {
			evGreen[i].sendEvent();
		}
	}


	private void evShabatToAll() {
		for (int i = 0; i < evShabat.length; i++) {
			evShabat[i].sendEvent();
		}

	}


	private boolean evGroup0OnRed() { //returns true when all lights in group 0 are red {
		boolean flag=true;
		for(int i:group0)
		{
			flag=flag && evOnRed[i].arrivedEvent();
		}
		if(flag) {
			System.out.println("group0 on red");
			for(int i:group0) {
				evOnRed[i].waitEvent();
			}
		}
		return flag;
	}

	public void evGreenGruop0() {
		System.out.println("send re");
		for (int i : group0) {
			evGreen[i].sendEvent();
		}
	}

	private void evRedGroup0() { //sends evRed to group0{
		for (int i : group0) {
			evRed[i].sendEvent();
			System.out.println("send red to " +i);
		}

	}




}
