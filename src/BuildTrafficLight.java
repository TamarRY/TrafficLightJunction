import javax.swing.JRadioButton;


public class BuildTrafficLight
{

	public static void main(String[] args) 
	{
		final int numOfLights=4+12+1;
		Event64[] evGreen= new Event64[numOfLights-1],evRed=new Event64[numOfLights-1],evShabat=new Event64[numOfLights-1],evChol=new Event64[numOfLights-1],evOnRed=new Event64[numOfLights-1];//to all 5 kinds of events
		for(int i =0; i<evRed.length;i++) {
			evRed[i]=new Event64();
		}
		for(int i =0; i<evGreen.length;i++) {
			evGreen[i]=new Event64();
		}
		for (int i = 0; i < evOnRed.length; i++) {
			evOnRed[i]=new Event64();
		}
		for (int i = 0; i < evShabat.length; i++) {
			evShabat[i]= new Event64();
		}
		for (int i = 0; i < evChol.length; i++) {
			evChol[i]= new Event64();
		}
		
		Event64 evShabatPressed, evCholPressed, evPadesstrianPressed;
		evCholPressed=new Event64();
		evPadesstrianPressed=new Event64();
		evShabatPressed=new Event64();
		Ramzor ramzorim[]=new Ramzor[numOfLights];
		ramzorim[0]=new Ramzor(3,40,430,110,472,110,514,110);
		ramzorim[1]=new Ramzor(3,40,450,310,450,352,450,394);
		ramzorim[2]=new Ramzor(3,40,310,630,280,605,250,580);
		ramzorim[3]=new Ramzor(3,40,350,350,308,350,266,350);

		ramzorim[4]=new Ramzor(2,20,600,18,600,40);
		ramzorim[5]=new Ramzor(2,20,600,227,600,205);
		ramzorim[6]=new Ramzor(2,20,600,255,600,277);
		ramzorim[7]=new Ramzor(2,20,600,455,600,433);
		ramzorim[8]=new Ramzor(2,20,575,475,553,475);
		ramzorim[9]=new Ramzor(2,20,140,608,150,590);
		ramzorim[10]=new Ramzor(2,20,205,475,193,490);
		ramzorim[11]=new Ramzor(2,20,230,475,250,475);
		ramzorim[12]=new Ramzor(2,20,200,453,200,433);
		ramzorim[13]=new Ramzor(2,20,200,255,200,277);
		ramzorim[14]=new Ramzor(2,20,200,227,200,205);
		ramzorim[15]=new Ramzor(2,20,200,18,200,40);

		ramzorim[16]=new Ramzor(1,30,555,645);

		TrafficLightFrame tlf=new TrafficLightFrame("installation of traffic lights",ramzorim);
		

		new CarLight(ramzorim[0],tlf.myPanel,1,true,1,evRed[0],evGreen[0],evShabat[0],evChol[0],evOnRed[0]);
		new CarLight(ramzorim[1],tlf.myPanel,2,true,2,evRed[1],evGreen[1],evShabat[1],evChol[1],evOnRed[1]);
		new CarLight(ramzorim[2],tlf.myPanel,3,true,3,evRed[2],evGreen[2],evShabat[2],evChol[2],evOnRed[2]);
		new CarLight(ramzorim[3],tlf.myPanel,4,true,4,evRed[3],evGreen[3],evShabat[3],evChol[3],evOnRed[3]);
		
		for (int i = 4; i <= 15; i++) {
			new PassengerLight(ramzorim[i],tlf.myPanel,evRed[i],evGreen[i],evShabat[i],evChol[i],evOnRed[i]);
		}

	

		new BlinkingLight(ramzorim[16],tlf.myPanel);

		MyActionListener myListener=new MyActionListener(evShabatPressed, evCholPressed, evPadesstrianPressed);
		
		
		
		Controller controller= new Controller(evGreen,evRed,evShabat,evChol,evOnRed,evCholPressed,evShabatPressed,evPadesstrianPressed);//+the other 3 event arrays

		JRadioButton butt[]=new JRadioButton[13]; 

		for (int i=0;i<butt.length-1;i++) 
		{
			butt[i]  =new JRadioButton();
			butt[i].setName(Integer.toString(i+4));
			butt[i].setOpaque(false);
			butt[i].addActionListener(myListener);
			tlf.myPanel.add(butt[i]);
		}
		butt[0].setBounds(620, 30, 18, 18);
		butt[1].setBounds(620, 218, 18, 18);
		butt[2].setBounds(620, 267, 18, 18);
		butt[3].setBounds(620, 447, 18, 18);
		butt[4].setBounds(566, 495, 18, 18);
		butt[5].setBounds(162,608, 18, 18);
		butt[6].setBounds(213,495, 18, 18);
		butt[7].setBounds(240,457, 18, 18);
		butt[8].setBounds(220,443, 18, 18);
		butt[9].setBounds(220,267, 18, 18);
		butt[10].setBounds(220,218, 18, 18);
		butt[11].setBounds(220,30, 18, 18);

		butt[12]  =new JRadioButton();
		butt[12].setName(Integer.toString(16));
		butt[12].setBounds(50,30, 80, 22);
		butt[12].setText("shabat");
		butt[12].setOpaque(false);
		butt[12].addActionListener(myListener);
		tlf.myPanel.add(butt[12]);
	}
}
