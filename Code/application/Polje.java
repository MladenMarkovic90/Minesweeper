package application;

import java.util.Random;

public class Polje {

	int P[][];
	
	Polje(int m,int br)
	{
		P=new int[m][m];
		Random R=new Random();
		for(int k=0;k<br;k++)
		{
			int i=R.nextInt(m);
			int j=R.nextInt(m);
			//System.out.println(i+" "+j);
			if(P[i][j]!=-1)//ako polje nije mina, postavimo na minu
			{//povecavamo vrednost polja oko mine, ako nije mina
				P[i][j]=-1;
				if(i<m-1 && P[i+1][j]!=-1)
					P[i+1][j]++;
				if(i>0 && P[i-1][j]!=-1)
					P[i-1][j]++;
				if(j<m-1 && P[i][j+1]!=-1)
					P[i][j+1]++;
				if(j>0 && P[i][j-1]!=-1)
					P[i][j-1]++;
				if(i<m-1 && j<m-1 && P[i+1][j+1]!=-1)
					P[i+1][j+1]++;
				if(i<m-1 && j>0 && P[i+1][j-1]!=-1)
					P[i+1][j-1]++;
				if(i>0 && j<m-1 && P[i-1][j+1]!=-1)
					P[i-1][j+1]++;
				if(i>0 && j>0 && P[i-1][j-1]!=-1)
					P[i-1][j-1]++;
			}
			else
				k--;//ako nije polje bila mina, moramo naci polje bez mine
		}
		//debug
		/*
		for(int i=0;i<10;i++)
			{
			for(int j=0;j<10;j++)
				System.out.print(P[i][j]+" ");
			System.out.println("");
			}*/
	}
	
	
	int[][] getP()//vracamo matricu polja
	{
		return P;
	}
	
	
	static void Provera(int P[][],int i,int j)//provera koja polja mozemo otvoriti, kada smo otvorili prazno polje
	{
		if(P[i][j]<0)//ako je polje obelezeno ili mina, vracamo se
			return;
		else if(P[i][j]>0)//ako je polje prvi susedni broj praznom polju, postavljamo na obelezeno i vracamo se
			{
			P[i][j]=-2;
			return;
			}
		P[i][j]=-2;//za prazno polje stavljamo obelezeno i pretrazujemo susedna polja
		if(i<P.length-1)
			Provera(P,i+1,j);
		if(i>0)
			Provera(P,i-1,j);
		if(j<P.length-1)
			Provera(P,i,j+1);
		if(j>0)
			Provera(P,i,j-1);
		if(i<P.length-1 && j<P.length-1)
			Provera(P,i+1,j+1);
		if(i<P.length-1 && j>0)
			Provera(P,i+1,j-1);
		if(i>0 && j<P.length-1)
			Provera(P,i-1,j+1);
		if(i>0 && j>0)
			Provera(P,i-1,j-1);
		return;
	}
	
	
	static int broj(char[] c)//provera da li je unos mina dobar
	{
		if(c.length==0)//ako nema unos
			return 10;
		for(int i=0;i<c.length;i++)
			if(c[i]<'0' || c[i]>'9')//ako unso nisu brojevi
				return 10;
		return Integer.parseInt(String.valueOf(c));//ako su unos brojevi, vrati broj
	}
}
