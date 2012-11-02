package FINANCE;

import IHM.Fenetre;
import ToolBox.Calcul;

public class CreateurGenericContract extends Contrat {

	public CreateurGenericContract(int amount, int age, int term, int technicalRate,
			int payment, String contrat){
				super(amount, age, term, technicalRate, payment, contrat);
			}

	@Override
	public void createurPanelJTable(Fenetre fenetre) {
		String[] entetes = {"age",fenetre.getChoixTable().getSelectedItem().toString(),"qx","dx", " "," ", " "};
		String[][] donnees = new String[fenetre.getListMortality().get(0).getValeur().length][9];
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


		int offsetH=5;
		int offsetV=5;
		// prenser a ajouter 3 espaces pour la coloration !


		//Single Premiun 							
		donnees[offsetV][offsetH]="Single Premiun   ";
		donnees[offsetV][offsetH+1]=Calcul.SinglePremium(age,payment,term, technicalRate, amount, fenetre.getListMortality().get(indice).getValeur(),contrat)+"";
		
		// Annual Premium
		donnees[offsetV+3][offsetH]="Annual Premium   ";
		donnees[offsetV+3][offsetH+1]=Calcul.AnnualPremium(age, payment,term, technicalRate, amount,fenetre.getListMortality().get(indice).getValeur(), contrat)+"";
		fenetre.createurPanelJTable(entetes, donnees);

	
		
	}


}
