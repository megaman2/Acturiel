package FINANCE;

import IHM.Fenetre;
import ToolBox.Calcul;

public class CreateurGenericCalc extends Contrat {

	public CreateurGenericCalc(int amount, int age, int term, int technicalRate,
			int payment, String contrat){
		super(amount, age, term, technicalRate, payment, contrat);
	}

@Override
public void createurPanelJTable(Fenetre fenetre) {
	String[] entetes = {"age",fenetre.getChoixTable().getSelectedItem().toString(),"dx","qx", "qx ", "test"};
	String[][] donnees = new String[fenetre.getListMortality().get(0).getValeur().length][7];
	int indice = fenetre.getChoixTable().getSelectedIndex();
	System.out.println("la taille est"+fenetre.getListMortality().get(0).getValeur().length+" "+donnees[0].length);
	for( int i=0; i < donnees.length ; i++){
		donnees[i][0]=i+"";
	}
	for( int i=0; i < donnees.length ; i++){
		donnees[i][1]=fenetre.getListMortality().get(indice).getValeur()[i]+"";
	}

	for( int i=0; i < donnees.length -1; i++){
		donnees[i][2]=Calcul.dx(fenetre.getListMortality().get(indice).getValeur()[i],fenetre.getListMortality().get(indice).getValeur()[i+1])+"";
	}
	for( int i=0; i < donnees.length -1 ; i++){
		donnees[i][3]=Calcul.qx(fenetre.getListMortality().get(indice).getValeur()[i],fenetre.getListMortality().get(indice).getValeur()[i+1])+"";
	}
	for( int i=0; i < donnees.length -1 ; i++){
		donnees[i][4]=Calcul.n_1qx(i,0,fenetre.getListMortality().get(indice).getValeur())+"";
	}
	for( int i=0; i < donnees.length -1 ; i++){
		if(  (Double.parseDouble(donnees[i][3])*100000 == Double.parseDouble(donnees[i][4])*100000 )){
			donnees[i][5]="OK";
		}else{
			donnees[i][5]="ERREUR";
		}
	}
	fenetre.createurPanelJTable(entetes, donnees);
	}

}
