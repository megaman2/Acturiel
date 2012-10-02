package FINANCE;

public class MortalityTable {

		final int max = 100000;
		private int[] valeur;
		private int[] save;

		private String nom;
		public MortalityTable(String s, int[] t1){
			this.nom=s;
			valeur = t1.clone();
			save = t1.clone();
		}
		
		
		public void appliqueOffset(int offset){
			for (int i = 0; i < valeur.length; i++) {
				valeur[i]+=offset;
			}
			
			for (int i = 0; i < valeur.length; i++) {
				if(valeur[i]> max){
					valeur[i]= max;
				}else if(valeur[i] < 0){
					valeur[i]=0;
				}
			}	
		}
		
		public void maz(){
			for (int i = 0; i < valeur.length; i++) {
				valeur[i]=save[i];
			}
		}
		
		
		public int[] getValeur() {
			return valeur;
		}
		public void setValeur(int[] valeur) {
			this.valeur = valeur;
		}
		public String getNom() {
			return nom;
		}
		public void setNom(String nom) {
			this.nom = nom;
		}
}
