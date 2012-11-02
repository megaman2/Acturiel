package FINANCE;

import javax.swing.JOptionPane;

import IHM.Fenetre;
import ToolBox.Calcul;

public class CreateurTermAssuranceReserve extends Contrat {

	public CreateurTermAssuranceReserve(int amount, int age, int term, int technicalRate,
			int payment,int discount, String contrat){
		super(amount, age, term, technicalRate, payment, contrat);
	}






	public void createurPanelJTable(Fenetre fenetre) {
		if(term != payment){
			JOptionPane.showMessageDialog(fenetre, "Attention le nombre de term et de payment doivent etre identique", "avertissement",JOptionPane.WARNING_MESSAGE);
			term=payment;
			fenetre.getTerm().setText(payment+"");
		}

		String[] entetes = new String[6+payment*3+13];
		entetes[0]="age";
		entetes[1]=fenetre.getChoixTable().getSelectedItem().toString();
		entetes[2]="qx";
		entetes[3]="dx";
		for (int i = 4; i < entetes.length; i++) {
			entetes[i]="    ";
		}

		String[][] donnees = new String[fenetre.getListMortality().get(0).getValeur().length][entetes.length];
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

		donnees[2][4]="Reserve";
		donnees[2][5]="Equivalence Principal";
		donnees[3][5]="Recurrence";
		
		donnees[5][5]="n-1 A x+t";
		donnees[6][5]="/m-ta.. x+t";
		
		
		// terme au dessus de la reserve
		for (int i = 0; i < term; i++) {
			donnees[1][i+6]=i+"";
		}
		int positionX= 13;
		int positionY=6;
		for (int i = 0; i < term; i++) {
			donnees[13+i][4]=i+1+"";
		}
		tableau1(donnees, positionX, positionY, term, 0, 0, technicalRate, age,fenetre.getListMortality().get(indice).getValeur());
		
		positionX+=term+9;
		tableau2(donnees, positionX, positionY, term, -1, 0, technicalRate, age,fenetre.getListMortality().get(indice).getValeur());

		fenetre.createurPanelJTable(entetes, donnees);

	}








	public void tableau1(String[][] donnees, int positionX, int positionY, int term,int termDepart, int termHaut, int tech, int age, int[]tab){
		if(term!=termDepart){
			donnees[positionX-2][positionY]=termDepart+"";
			donnees[positionX-1+termDepart][positionY]="v   ";
			donnees[positionX-1+termDepart][positionY+1]="h-1/1 q x+t   ";
			donnees[positionX-1+termDepart][positionY+2]="h-1/1 A x+t   ";
			double somme=0.0;
			for (int i = termDepart; i < term; i++) {
				donnees[i+positionX][positionY]=Calcul.techDF(i+1-termHaut, tech)+"";
				donnees[i+positionX][positionY+1]=Calcul.n_1qx(age+termHaut, i-termDepart,tab)+"";
				donnees[i+positionX][positionY+2]=Double.parseDouble(donnees[i+positionX][positionY])*Double.parseDouble(donnees[i+positionX][positionY+1])+"";
				somme+=Double.parseDouble(donnees[i+positionX][positionY+2]);
			}
			donnees[positionX+term +1][positionY+1]="somme :";
			donnees[positionX+term +1][positionY+2]=somme+"";
			donnees[positionX+term +2][positionY+2]=Calcul.nAx(age+termHaut, term-termHaut, tech, tab)+"";
			if(equals(donnees[positionX+term +1][positionY+2], donnees[positionX+term +2][positionY+2])){
				donnees[positionX+term +2][positionY+1]="OK   ";
				donnees[5][6+termHaut]=donnees[positionX+term +1][positionY+2];
			}else{
				donnees[positionX+term +2][positionY+2]="ERREUR   ";
			}
			
			termDepart++;
			termHaut++;
			tableau1(donnees, positionX, positionY+3, term, termDepart, termHaut, tech, age,tab);
		}

	}
	
	public void tableau2(String[][] donnees, int positionX, int positionY, int term,int termDepart, int termHaut, int tech, int age, int[]tab){
		if(term!=termDepart+1){
			donnees[positionX-3][positionY]=termDepart+1+"";
			donnees[positionX-1+termDepart][positionY]="npx   ";
			donnees[positionX-1+termDepart][positionY+1]="v^h   ";
			donnees[positionX-1+termDepart][positionY+2]="nEx   ";
			double somme=0.0;
			for (int i = termDepart; i < term-1; i++) {
				donnees[i+positionX][positionY]=Calcul.npx(age+termHaut, i-termDepart,tab)+"";
				donnees[i+positionX][positionY+1]=Calcul.techDF(i+1-termHaut, tech)+"";
				donnees[i+positionX][positionY+2]=Double.parseDouble(donnees[i+positionX][positionY])*Double.parseDouble(donnees[i+positionX][positionY+1])+"";
				somme+=Double.parseDouble(donnees[i+positionX][positionY+2]);
			}
			donnees[positionX+term +1][positionY+1]="somme :";
			donnees[positionX+term +1][positionY+2]=somme+"";
			donnees[positionX+term +2][positionY+2]=Calcul.annuityFactor(age+termHaut, term-termDepart-1, technicalRate, amount, tab)+"";
			if(equals(donnees[positionX+term +1][positionY+2], donnees[positionX+term +2][positionY+2])){
				donnees[positionX+term +2][positionY+1]="OK   ";
				donnees[6][6+termHaut]=donnees[positionX+term +1][positionY+2];
			}else{
				donnees[positionX+term +2][positionY+1]="ERREUR   ";
			}
			
			termDepart++;
			termHaut++;
			tableau2(donnees, positionX, positionY+3, term, termDepart, termHaut, tech, age,tab);
		}

	}
	
	
}
