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
		System.out.println("je fais "+ lx(tab, x+n)+ " - "+lx(tab,x+n+1) +"/ "+lx(tab,x));
		System.out.println();
		return ((lx(tab, x+n)-lx(tab,x+n+1))/(lx(tab,x)*1.0));
	}

	public static double npx(int age, int term, int[] tab){
		return lx(tab,term+age)/((lx(tab,age)*1.0));
	}

	// pour v^n
	public static double techDF(int term, double techRate){
		return 1 / (Math.pow((1 + techRate/100.0) , term));
	}


	// nEx faire v^n * npx
	public static double nEx(int term, double techRate,int amount, int age, int[] tab){
		return techDF(term, techRate)*npx(amount, age, tab);
	}


	public static double SinglePremiumPE(int x ,int n ,double techRate,double benefit, int amount, int age, int[] tab){
		return nEx(x, techRate, amount, age, tab)*techDF(n, techRate);
	}


	public static double AnnualPremium(double singlePremium, int x, int n, double techRate, int amount, int age, int[] tab){
		return singlePremium / annuityFactor(x, n, techRate, amount, age, tab);
	}

	private static double annuityFactor(int x, int m, double techRate, int amount, int age, int[] tab) {
		double somme = 0;
		for (int h = 0; h < m; h++) {
			somme += nEx(x, techRate, amount, h, tab);
		}
		return somme;
	}
}