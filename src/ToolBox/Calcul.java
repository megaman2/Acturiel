package ToolBox;

public abstract class Calcul {


	public static double qx(int a, int b){
		return (a-b)/(a*1.0);
	}

	public static int dx(int a, int b){
		return a-b;
	}

	public static int lx(int[] tab, int a){
		return tab[a];	
	}
	public static double n_1qx( int x, int n, int[] tab){
		return (lx(tab, x+n)-lx(tab,x+n+1)/(lx(tab,x)*1.0));

	}

	public static double npx(int amount, int age, int[] tab){
		return lx(tab,amount+age)/((lx(tab,age)*1.0));
	}

	// pour v^n
	public static double techDF(int term, int techincalRate){
		return 1 / (Math.pow((1 + techincalRate) , term));
	}


	// nEx faire v^n * npx
	public static double nEx(int term, int techincalRate,int amount, int age, int[] tab){
		return techDF(term, techincalRate)*npx(amount, age, tab);
	}


	public static double SinglePremiumPE(int x ,int n ,double techRate,double benefit){
		return 1.0; // nEx(x, n, techRate)* benefit;
	}


	public static double AnnualPremium(double singlePremium, int x, int n, double techRate){
		return singlePremium / annuityFactor(x, n, techRate);
	}

	private static double annuityFactor(int x, int n, double techRate) {
		// TODO Auto-generated method stub
		return 0;
	}
}