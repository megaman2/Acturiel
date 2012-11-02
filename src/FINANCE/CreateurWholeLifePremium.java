package FINANCE;

import IHM.Fenetre;
import ToolBox.Calcul;

public class CreateurWholeLifePremium extends Contrat {

	public CreateurWholeLifePremium(int amount, int age, int term, int technicalRate,
			int payment, String contrat){
		super(amount, age, term, technicalRate, payment, contrat);
	}

@Override
public void createurPanelJTable(Fenetre fenetre) {
	String[] entetes = {"age",fenetre.getChoixTable().getSelectedItem().toString(),"qx","dx", " ","term","tpx","v^t","tEx", "  ","term","v^h","h-1/1 q x","h-1/ A x" };
	String[][] donnees = new String[fenetre.getListMortality().get(0).getValeur().length][17];
	int indice = fenetre.getChoixTable().getSelectedIndex();
	System.out.println("la taille est"+fenetre.getListMortality().get(0).getValeur().length+" "+donnees[0].length);
	for( int i=0; i < donnees.length ; i++){
		donnees[i][0]=i+"";
	}
	for( int i=0; i < donnees.length ; i++){
		donnees[i][1]=fenetre.getListMortality().get(indice).getValeur()[i]+"";
	}
	for( int i=0; i < donnees.length -1 ; i++){
		donnees[i][2]=Calcul.qx(fenetre.getListMortality().get(indice).getValeur()[i],fenetre.getListMortality().get(indice).getValeur()[i+1])+"";
	}
	for( int i=0; i < donnees.length -1; i++){
		donnees[i][3]=Calcul.dx(fenetre.getListMortality().get(indice).getValeur()[i],fenetre.getListMortality().get(indice).getValeur()[i+1])+"";
	}

	// pour term
	for( int i=0; i < 15; i++){
		//term
		donnees[i][10]=i+"";
		//tpx
		donnees[i][11]=Calcul.npx(age,Integer.parseInt(donnees[i][10]),fenetre.getListMortality().get(indice).getValeur())+"";
		//v^t
		donnees[i][12]=Calcul.techDF(Integer.parseInt(donnees[i][10]),technicalRate)+"";
		//nEx
		donnees[i][13]=Double.parseDouble(donnees[i][11])*Double.parseDouble(donnees[i][12])+"";

	}
	double ax=0.0;
	// pour term 2
	for( int i=0; i < fenetre.getListMortality().get(indice).getValeur().length - age -1 ; i++){
		//term
		donnees[i][5]=i+1+"";
		//v^h
		donnees[i][6]=Calcul.techDF(Integer.parseInt(donnees[i][5]), technicalRate)+"";
		//h-1/1 q x 
		donnees[i][7]=Calcul.n_1qx(age, i, fenetre.getListMortality().get(indice).getValeur())+"";
		//h-1/1 A x
		donnees[i][8]=Double.parseDouble(donnees[i][6])*Double.parseDouble(donnees[i][7])+"";
		ax+=Double.parseDouble(donnees[i][6])*Double.parseDouble(donnees[i][7]);
	}

	int offsetH=10;
	int offsetV=19;
	donnees[offsetV+4][offsetH]="a..m:x   ";
	double somme=0.0;
	for( int i=0; i < 15 ; i++){
		somme+=Double.parseDouble(donnees[i][13]);
	}
	donnees[offsetV+4][offsetH+1]=somme +"";
	//AX
	donnees[offsetV+12][offsetH]="AX   ";
	donnees[offsetV+12][offsetH+1]=ax+"";

	//Single Premiun 
	donnees[offsetV+6][offsetH]="Single Premiun   ";
	donnees[offsetV+6][offsetH+1]=amount*Double.parseDouble(donnees[offsetV+12][offsetH+1])+"";


	// Annual Premium
	donnees[offsetV+9][offsetH]="Annual Premium   ";
	donnees[offsetV+9][offsetH+1]=Double.parseDouble(donnees[offsetV+6][offsetH+1])/Double.parseDouble(donnees[offsetV+4][offsetH+1])+"";



	fenetre.createurPanelJTable(entetes, donnees);
	}

}
