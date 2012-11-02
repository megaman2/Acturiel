package FINANCE;

import IHM.Fenetre;
import ToolBox.Calcul;

public class CreateurPureEndowmentPremium extends Contrat {

	public CreateurPureEndowmentPremium(int amount, int age, int term, int technicalRate,
			int payment, String contrat){
		super(amount, age, term, technicalRate, payment, contrat);
	}

@Override
public void createurPanelJTable(Fenetre fenetre) {
	String[] entetes = {"age",fenetre.getChoixTable().getSelectedItem().toString(),"qx","dx", " ","term","tpx","v^t","tEx"};
	String[][] donnees = new String[fenetre.getListMortality().get(0).getValeur().length][12];
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
	for( int i=0; i < 10; i++){
		//term
		donnees[i][5]=i+"";
		//tpx
		donnees[i][6]=Calcul.npx(age,Integer.parseInt(donnees[i][5]),fenetre.getListMortality().get(indice).getValeur())+"";
		//v^t
		donnees[i][7]=Calcul.techDF(Integer.parseInt(donnees[i][5]),technicalRate)+"";
		//tEx
		donnees[i][8]=Double.parseDouble(donnees[i][6])*Double.parseDouble(donnees[i][7])+"";

	}

	int offsetH=5;
	int offsetV=15;
	// prenser a ajouter 3 espaces pour la coloration !
	donnees[offsetV][offsetH]="npx   ";
	donnees[offsetV][offsetH+1]=Calcul.npx(age, term ,fenetre.getListMortality().get(indice).getValeur())+"";

	donnees[offsetV+1][offsetH]="v^n   ";
	donnees[offsetV+1][offsetH+1]=Calcul.techDF(term,technicalRate)+"";

	donnees[offsetV+2][offsetH]="nEx   ";
	donnees[offsetV+2][offsetH+1]= Double.parseDouble(donnees[offsetV][offsetH+1])*Double.parseDouble(donnees[offsetV+1][offsetH+1])+"";

	donnees[offsetV+4][offsetH]="a..m:x   ";
	double somme=0.0;
	for( int i=0; i < 10; i++){
		somme+=Double.parseDouble(donnees[i][8]);
	}
	donnees[offsetV+4][offsetH+1]=somme +"";

	//Single Premiun 
	donnees[offsetV+6][offsetH]="Single Premiun   ";
	donnees[offsetV+6][offsetH+1]=amount*Double.parseDouble(donnees[offsetV][offsetH+1])*Double.parseDouble(donnees[offsetV+1][offsetH+1])+"";
	donnees[offsetV+7][offsetH+1]=Calcul.SinglePremiumPE(age, term, technicalRate, amount, fenetre.getListMortality().get(indice).getValeur())+"";
	if(  (Double.parseDouble(donnees[offsetV+6][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+7][offsetH+1])*100000 < 1 && (Double.parseDouble(donnees[offsetV+6][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+7][offsetH+1])*100000 > -1 ))){
		donnees[offsetV+7][offsetH+2]="OK";
	}else{
		donnees[offsetV+7][offsetH+2]="ERREUR";
	}
	// Annual Premium
	donnees[offsetV+9][offsetH]="Annual Premium   ";
	donnees[offsetV+9][offsetH+1]=Double.parseDouble(donnees[offsetV+6][offsetH+1])/Double.parseDouble(donnees[offsetV+4][offsetH+1])+"";
	donnees[offsetV+10][offsetH+1]=(amount*(Double.parseDouble(donnees[offsetV+2][offsetH+1])/Double.parseDouble(donnees[offsetV+4][offsetH+1])))+"";
	if((Double.parseDouble(donnees[offsetV+9][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+10][offsetH+1])*100000 < 1 && (Double.parseDouble(donnees[offsetV+9][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+10][offsetH+1])*100000 > -1 ))){
		donnees[offsetV+10][offsetH+2]="OK";
	}else{
		donnees[offsetV+10][offsetH+2]="ERREUR";
	}
	fenetre.createurPanelJTable(entetes, donnees);
	}

}
