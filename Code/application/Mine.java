package application;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class Mine implements Initializable {
	
	@FXML
	AnchorPane main;
	@FXML
	Button klick;//potez ili zastavice
	@FXML
	Button start;
	@FXML
	Button help;
	@FXML
	TextField tf;//polje za mine
	@FXML
	TextField tf_help;
	@FXML
	Label lab_win;
	@FXML
	Label lab_loss;
	
	Button btn[][]=new Button[20][20];//dugme polja
	Label lab[][]=new Label[20][20];//polja ispod dugmeta
	GridPane gp=new GridPane();//sluzi za grupisanje
	int count=0;//broji otvorena polja
	int br=0;//broj mina
	int help_count=0;//proj poteza za pomoc
	
	boolean mby=false;//da li stavljamo zastavice
	boolean play=true;//da li igra jos traje
	Random R=new Random();//potreban za dugme pomoc
	
	Polje P;//za inicijalizaciju matrice
	int PO[][];//matrica sa poljima mina i brojeva
	
	
	public void handle1(ActionEvent e)//menjamo stanje da stisnemo ili stavljamo zastavice
	{
		if(mby)
		{
			mby=false;
			klick.setText("MARK");
		}
		else
		{
			mby=true;
			klick.setText("CLICK");
		}
	}
	
	
	public void handle3(ActionEvent e)//kada stisnemo na dugme help, trazimo polje koje sme da se otvori
	{
		help_count--;
		tf_help.setText(help_count+"");
		if(help_count==0)//ako nemamo vise poteza za pitanje, iskljucujemo dugme
			help.setDisable(true);
		int a=R.nextInt(PO.length);
		int b=R.nextInt(PO.length);
		while(PO[a][b]<0)//ako je polje mina ili otvoreno, nastavljamo petlju
			{
			a=R.nextInt(PO.length);
			b=R.nextInt(PO.length);
			}
		btn[a][b].setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;"
				+"-fx-border-color:black;-fx-background-color:rgb(50,250,80);-fx-text-fill:rgb(50,250,80)");
		btn[a][b].setText("3");//da ne bi mogli da stavljamo zastavicu
	}
	
	
	public void handle2(ActionEvent e)
	{
		help.setDisable(false);//ukljucujemo dugme za pomoc
		start.setDisable(true);//iskljucujemo dugme za novu igru dok ne zavrsimo trenutnu
		tf.setDisable(true);//iskljucimo polje za unos broj mina
		lab_win.setVisible(false);//iskljucujemo poruke
		lab_loss.setVisible(false);//iskljucujemo poruke
		count=0;//postavljamo brojac na nula otvorena polja
		br=Polje.broj(tf.getText().toCharArray());//proveravamo i vadimo broj mina
		int n=10,m=20;
		if(br<10)//min broj mina je 10
			br=10;
		if(br>100)//max broj mina je 100
			br=100;
		tf.setText(br+"");
		if(br<=25)//podesavanja za 10x10
			{
			n=20;//n*2 velicina polja
			m=10;//dimenzija matrice
			}
		else//podesavanja za 20x20
			{
			n=10;//n*2 velicina polja
			m=20;//dimenzija matrice
			}
		help_count=br/5;
		tf_help.setText(help_count+"");
		P=new Polje(m,br);//pravimo novo polje sa [m][m] matricom i brojem mina br
		PO=P.getP();
		play=true;//omogucavamo igru
		for(int i=0;i<20;i++)
			for(int j=0;j<20;j++)//podesavamo velicinu i vidljivost
			{
				btn[i][j].setVisible(false);
				lab[i][j].setVisible(false);
				btn[i][j].setMinSize(n+n,n+n);
				btn[i][j].setMaxSize(n+n,n+n);
				lab[i][j].setMinSize(n+n,n+n);
				lab[i][j].setMaxSize(n+n,n+n);
			}
		for(int i=0;i<m;i++)
			for(int j=0;j<m;j++)//podesavanja za igru za [m][m] matricu
			{
				btn[i][j].setVisible(true);
				lab[i][j].setVisible(false);
				if(PO[i][j]==-1)//postavljamo labelu za minu
					lab[i][j].setText("X");
				else if(PO[i][j]==0)//postavljamo labelu za prazno polje
					lab[i][j].setText("");
				else//postavljamo labelu na broj mina u blizini
					lab[i][j].setText(PO[i][j]+"");
				btn[i][j].setText("");//postavljamo prazan text,zbog zastavica
				btn[i][j].setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;"
						+"-fx-border-color:black;-fx-background-color:gray;-fx-text-fill:gray;");
				lab[i][j].setStyle("-fx-background-color:rgb(244,244,244);" +
						"-fx-font-size:15px;-fx-border-color:rgb(220,220,220);");
			}
	}
	

	public void initialize(URL arg0, ResourceBundle arg1) {
	
	for(int i=0;i<20;i++)//moramo da napravimo 400polja da ne bi komplikovali kod
		for(int j=0;j<20;j++)
		{
			final int I = i;//potrebno da bi mogli ubaciti index u okviru funkcije
			final int J = j;//potrebno da bi mogli ubaciti index u okviru funkcije
			btn[i][j]=new Button();
			lab[i][j]=new Label();
			btn[i][j].setVisible(false);
			btn[i][j].setOnAction(new EventHandler<ActionEvent>()
				{
				
				public void handle(ActionEvent t)
				{
					//System.out.println(PO.length*PO.length-br); //debug
					if(mby)//ako smo status za zastavice
					{
						if(btn[I][J].getText().compareTo("2")==0)//ako je zastavice, skidamo je
							{
							btn[I][J].setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;"
									+"-fx-border-color:black;-fx-background-color:gray;-fx-text-fill:gray;");
							btn[I][J].setText("");
							}
						else if(btn[I][J].getText().compareTo("1")==0)
						{
						btn[I][J].setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;"
								+"-fx-border-color:black;-fx-background-color:rgb(153,217,234);-fx-text-fill:rgb(153,217,234);");
						btn[I][J].setText("2");
						}
						else if(btn[I][J].getText().compareTo("")==0)
						{
						btn[I][J].setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;"
								+"-fx-border-color:black;-fx-background-color:yellow;-fx-text-fill:yellow;");
						btn[I][J].setText("1");
						}
					}
					else if(btn[I][J].getText().compareTo("1")==0 || btn[I][J].getText().compareTo("2")==0 || !play)//kada je status klik ili ako smo zavrsili igru izlazimo
						return;
					else//ako smo uspeli stisnuti
					{
					lab[I][J].setVisible(true);//polje iza dugmeta ce biti vidljivo
					btn[I][J].setVisible(false);//dugme ce nestati
					if(PO[I][J]==0)//ako je polje prazno, onda otvaramo sva moguca susedna
						{
						Polje.Provera(PO, I, J);//postavljamo zastavice
						for(int i=0;i<PO.length;i++)
							for(int j=0;j<PO.length;j++)
								if(PO[i][j]==-2)//otvaramo sve kod kojih je postavljena zastavica
									{
									PO[i][j]=-3;
									count++;
									lab[i][j].setVisible(true);
									btn[i][j].setVisible(false);
									//System.out.println(count+"");//debug
									}
						}
					else if(PO[I][J]==-1)//ako je bila mina, zavrsavamo igru
						{
						play=false;//moramo postaviti da ne bi mogli polja otvarati
						lab[I][J].setStyle("-fx-background-color:red;");
						lab_loss.setVisible(true);//informacija da ste izgubili igru
						start.setDisable(false);//aktiviramo dugme za pokretanje nove igre
						tf.setDisable(false);//aktiviramo polje za unos broja mina
						help.setDisable(true);//iskljucujemo dugme za pomoc
						//System.out.println("Broj poteza je:"+count); //debug
						}
					else//postavljamo vrednost na otvoreno polje
						{
						PO[I][J]=-3;
						count++;
						//System.out.println(count+"");//debug
						}
					}
				if(count==(PO.length*PO.length-br))//provera da li smo sva polja otvorili
					{
					for(int i=0;i<PO.length;i++)
						for(int j=0;j<PO.length;j++)
						{
							if(PO[i][j]==-1)//otvaramo sve mine i bojimo u plavo
							{
								lab[i][j].setVisible(true);
								lab[i][j].setStyle("-fx-background-color:rgb(0,100,200);");
								btn[i][j].setVisible(false);
							}
						}
					lab_win.setVisible(true);//poruka o pobedi igre
					start.setDisable(false);//ukljucujemo dugme za novu igru
					tf.setDisable(false);//ukljucujemo polje za unos broja mina
					help.setDisable(true);//iskljucujemo dugme za pomoc
					}
				}
				});
			lab[i][j].setAlignment(Pos.CENTER);
			lab[i][j].setVisible(false);
			GridPane.setHgrow(btn[i][j], Priority.ALWAYS);
			GridPane.setVgrow(btn[i][j], Priority.ALWAYS);
			gp.add(btn[i][j],j,i);
			GridPane.setHgrow(lab[i][j], Priority.ALWAYS);
			GridPane.setVgrow(lab[i][j], Priority.ALWAYS);
			gp.add(lab[i][j],j,i);
		}
	main.getChildren().add(gp);
	}

}
