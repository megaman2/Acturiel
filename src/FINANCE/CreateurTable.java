package FINANCE;

import IHM.Fenetre;


public class CreateurTable extends Contrat{

	public CreateurTable(int amount, int age, int term, int technicalRate,
	int payment, String contrat){
		super(amount, age, term, technicalRate, payment, contrat);
	}

	@Override
	public void createurPanelJTable(Fenetre fenetre) {
		
			String[] entetes = new String[fenetre.getListMortality().size()+1];
			entetes[0]="age";
			for( int i=1; i< entetes.length ; i++){
				entetes[i] = fenetre.getListMortality().get(i-1).getNom();
			}
			String[][] donnees = new String[fenetre.getListMortality().get(0).getValeur().length][fenetre.getListMortality().size()+1];
			for(int j=0;  j < donnees.length ; j++){
				donnees[j][0]=j+"";
			}
			for( int i=0; i < donnees.length ; i++){
				for(int j=0;  j < donnees[i].length -1 ; j++){
					donnees[i][j+1] = fenetre.getListMortality().get(j).getValeur()[i]+"";
				}
			}
			fenetre.createurPanelJTable(entetes, donnees);
		}
		
	}
