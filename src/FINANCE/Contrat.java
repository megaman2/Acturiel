package FINANCE;

import IHM.Fenetre;

public abstract class Contrat {
	protected int amount;
	protected int age;
	protected int term;
	protected int technicalRate;
	protected int payment;
	protected String contrat;
	

	public Contrat(int amount, int age, int term, int technicalRate,
			int payment, String contrat) {
		super();
		this.amount = amount;
		this.age = age;
		this.term = term;
		this.technicalRate = technicalRate;
		this.payment = payment;
		this.contrat = contrat;
	}


	public boolean equals(String s1, String s2){
		double a = Double.parseDouble(s1);
		double b = Double.parseDouble(s2);		
		return (a*100000-b*100000 < 1) && (a*100000-b*100000 > -1);	
	}

	public abstract void createurPanelJTable(Fenetre f);
	
	
}
