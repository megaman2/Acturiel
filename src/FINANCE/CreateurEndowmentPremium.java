package FINANCE;

import IHM.Fenetre;
import ToolBox.Calcul;

public class CreateurEndowmentPremium extends Contrat {

	public CreateurEndowmentPremium(int amount, int age, int term, int technicalRate,
			int payment, String contrat){
		super(amount, age, term, technicalRate, payment, contrat);
	}

	@Override
	public void createurPanelJTable(Fenetre fenetre) {
		String[] entetes = {"age",fenetre.getChoixTable().getSelectedItem().toString(),"qx","dx",  "  ","term","v^h","h-1/1 q x","h-1/ A x"," ","term","tpx","v^t","tEx" };
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
			donnees[i][2]=Calcul.qx(fenetre.getListMortality().get(indice).getValeur()[i],fenetre.getListMortality().get(0).getValeur()[i+1])+"";
		}
		for( int i=0; i < donnees.length -1; i++){
			donnees[i][3]=Calcul.dx(fenetre.getListMortality().get(indice).getValeur()[i],fenetre.getListMortality().get(0).getValeur()[i+1])+"";
		}
		double somme=0.0;
		// pour term
		for( int i=0; i < payment; i++){
			//term
			donnees[i][10]=i+"";
			//tpx
			donnees[i][11]=Calcul.npx(age,Integer.parseInt(donnees[i][10]),fenetre.getListMortality().get(indice).getValeur())+"";
			//v^t
			donnees[i][12]=Calcul.techDF(Integer.parseInt(donnees[i][10]),technicalRate)+"";
			//tEx
			donnees[i][13]=Double.parseDouble(donnees[i][11])*Double.parseDouble(donnees[i][12])+"";
			somme+=Double.parseDouble(donnees[i][13]);
		}

		// pour term 2
		double sommeNax=0.0;
		for( int i=0; i < term; i++){
			//term
			donnees[i][5]=i+1+"";
			//v^h
			donnees[i][6]=Calcul.techDF(Integer.parseInt(donnees[i][5]),technicalRate)+"";
			//h-1/1 q x 
			donnees[i][7]=(Calcul.lx(fenetre.getListMortality().get(indice).getValeur(), age+Integer.parseInt(donnees[i][5])-1)-Calcul.lx(fenetre.getListMortality().get(indice).getValeur(), age+Integer.parseInt(donnees[i][5])))/(Calcul.lx(fenetre.getListMortality().get(indice).getValeur(),age)*1.0)+"";
			//h-1/1 A x
			donnees[i][8]=Double.parseDouble(donnees[i][6])*Double.parseDouble(donnees[i][7])+"";
			sommeNax+=Double.parseDouble(donnees[i][6])*Double.parseDouble(donnees[i][7]);
		}
		int offsetH=10;
		int offsetV=19+payment-5;
		// prenser a ajouter 3 espaces pour la coloration !
		donnees[offsetV][offsetH]="npx   ";
		donnees[offsetV][offsetH+1]=Calcul.npx(age, term ,fenetre.getListMortality().get(indice).getValeur())+"";

		donnees[offsetV+1][offsetH]="v^n   ";
		donnees[offsetV+1][offsetH+1]=Calcul.techDF(term,technicalRate)+"";

		donnees[offsetV+2][offsetH]="nEx   ";
		donnees[offsetV+2][offsetH+1]= Double.parseDouble(donnees[offsetV][offsetH+1])*Double.parseDouble(donnees[offsetV+1][offsetH+1])+"";

		donnees[offsetV+12][offsetH]="nAx   ";
		donnees[offsetV+12][offsetH+1]=sommeNax+"";
		donnees[offsetV+13][offsetH+1]=Calcul.nAx(age, term, technicalRate, fenetre.getListMortality().get(indice).getValeur())+"";
		donnees[offsetV+4][offsetH]="a..m:x   ";

		donnees[offsetV+4][offsetH+1]=somme +"";

		//Single Premiun 
		donnees[offsetV+6][offsetH]="Single Premiun   ";
		donnees[offsetV+6][offsetH+1]=amount*(Double.parseDouble(donnees[offsetV+2][offsetH+1])+Double.parseDouble(donnees[offsetV+12][offsetH+1]))+""; //JUSTE
		donnees[offsetV+7][offsetH+1]=Calcul.SinglePremium(age,payment,term, technicalRate, amount, fenetre.getListMortality().get(indice).getValeur(),"")+""; //TODO
		if(  equals(donnees[offsetV+6][offsetH+1],donnees[offsetV+7][offsetH+1])){
			donnees[offsetV+7][offsetH+2]="OK";
		}else{
			donnees[offsetV+7][offsetH+2]="ERREUR";
		}
		// Annual Premium
		donnees[offsetV+9][offsetH]="Annual Premium   ";
		donnees[offsetV+9][offsetH+1]=Double.parseDouble(donnees[offsetV+6][offsetH+1])/Double.parseDouble(donnees[offsetV+4][offsetH+1])+""; //JUSTE
		donnees[offsetV+10][offsetH+1]=Calcul.AnnualPremium(age, payment,term, technicalRate, amount, fenetre.getListMortality().get(indice).getValeur(),"")+"";//TODO erreur
		if(  equals(donnees[offsetV+9][offsetH+1],donnees[offsetV+10][offsetH+1])){
			donnees[offsetV+10][offsetH+2]="OK";
		}else{
			donnees[offsetV+10][offsetH+2]="ERREUR";
		}

		if(equals(donnees[offsetV+12][offsetH+1],donnees[offsetV+13][offsetH+1])){
			donnees[offsetV+13][offsetH+2]="OK";
		}else{
			donnees[offsetV+13][offsetH+2]="ERREUR";
		}
		fenetre.createurPanelJTable(entetes, donnees);
	}
}
