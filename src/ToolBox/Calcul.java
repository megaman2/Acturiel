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

	public static double n_1qx( int age, int term, int[] tab){
		return ((lx(tab, age+term)-lx(tab,age+term+1))/(lx(tab,age)*1.0));
	}

	public static double npx(int age, int term, int[] tab){
		return lx(tab,term+age)/((lx(tab,age)*1.0));
	}

	// pour v^n
	public static double techDF(int term, double techRate){
		return 1 / (Math.pow((1 + techRate/100.0) , term));
	}


	// nEx faire v^n * npx
	public static double nEx(int term, double techRate, int age, int[] tab){
		return npx(age, term, tab)*techDF(term, techRate);
	}
	
	public static double nAx(int age, int term, int techRate, int[] tab){
		double res=0.0;
		for (int i = 0; i < term; i++) {
			res+=n_1qx(age, i, tab)*techDF(i+1, techRate);
		}
		return res;
	}

	public static double SinglePremiumPE(int age, int term, int techRate, int amount, int[] tab){
		return nEx(term, techRate, age, tab)*amount;
	}
	
	
	public static double SinglePremiumTA(int age, int term, int techRate, int benefit, int[] tab) {
		return nAx(age, term, techRate,tab)*benefit;
	}	
	
	public static double SinglePremiumEnd(int age, int payment, int term, int techRate , int amount, int[] tab){
		
		return SinglePremiumTA(age, term, techRate, amount, tab )+ SinglePremiumPE(age, term, techRate, amount, tab );
	}
	
	
	/*tout est bon jusqu'ici*/


	public static double annuityFactor(int age,int payments ,int techRate, int amount, int[] tab){
		double somme = 0;
		for (int h = 0; h < payments; h++) {
			somme +=  nEx(h, techRate, age, tab);
		}
		return somme;
	}

	public static double SinglePremium(int age, int payments,int term ,int techRate, int amount,int[] tab,String contract){
		if(contract.equals("Term Assurance")){
			System.out.println("on calcule le Term Assurance");
			return SinglePremiumTA(age, term, techRate, amount,tab);
		}else if(contract.equals("Pure Endowment")){
			System.out.println("on calcule le Pure Endowment");
			return SinglePremiumPE(age,term, techRate, amount,tab);
		}else {
			System.out.println("on calcule le END");
			return SinglePremiumEnd(age, payments,term, techRate, amount,tab);
		}  
	}

	public static double AnnualPremium(int age, int payments,int term,int techRate, int amount,int[] tab,String contract){
		if(contract.equals("Term Assurance")){
			return SinglePremiumTA(age, term, techRate, amount,tab)/annuityFactor(age,payments, techRate, amount,tab);
		}else if(contract.equals("Pure Endowment")){
			return SinglePremiumPE(age,term, techRate, amount,tab)/annuityFactor(age,payments, techRate, amount,tab);
		}else {
			return SinglePremiumEnd(age, payments, term, techRate, amount,tab)/annuityFactor(age,payments, techRate, amount,tab);
		}  
	}

	


}











/*




	Public Function ExStressTest(x As Integer, n As Integer, m As Integer, techRate As Double, Benefit As Double, MortalityTable As Range, ContractType As String, discRate As Double) As Double

	Dim DouMortTable() As Double
	Dim newMortalityTable() As Double
	Dim FairPremium As Double
	Dim RealPremium As Double


	ReDim DouMortTable(MortalityTable.Rows.Count)
	ReDim newMortalityTable(MortalityTable.Rows.Count)

	DouMortTable() = ConvertRangeToDouble(MortalityTable)

	newMortalityTable() = DiscountMortTable(DouMortTable(), discRate)

	FairPremium = AnnualPremium_(x, n, m, techRate, Benefit, DouMortTable, ContractType)
	RealPremium = AnnualPremium_(x, n, m, techRate, Benefit, newMortalityTable, ContractType)
	ExStressTest = FairPremium - RealPremium

	End Function









	Public Function AnnualPremium(SinglePremium As Double, x As Integer, m As Integer, techRate As Double) As Double

	AnnualPremium = SinglePremium / AnnuityFactor(x, m, techRate)
	Dim i As Integer
	i = 0

	End Function


	Option Explicit
	Option Base 0   'default value = 0 -> Redim


	Public Function ExAnnualPremium_(x As Integer, n As Integer, m As Integer, techRate As Double, Benefit As Double, MortalityTable As Range, ContractType As String) As Double

	Dim DouMortTable() As Double

	DouMortTable() = ConvertRangeToDouble(MortalityTable)

	ExAnnualPremium_ = AnnualPremium_(x, n, m, techRate, Benefit, DouMortTable, ContractType)

	End Function




	Public Function SinglePremiumTA_(x As Integer, _
	                                n As Integer, _
	                                techRate As Double, _
	                                Benefit As Double, _
	                                MortalityTable() As Double) As Double

	Dim ActDF As Double

	ActDF = nAx_(x, n, techRate, MortalityTable())
	SinglePremiumTA_ = ActDF * Benefit

	End Function

	'''''''''''''''''''''''''''''''''''''''''''''PE = Pure Endowment'************************************




------------------------- a voir -------------------

	Public Function DiscountMortTable(MortalityTable() As Double, DiscountRate As Double) As Double()

	Dim MortCurve() As Double
	Dim discMortCurve() As Double
	Dim newMortalityTable() As Double
	Dim n As Integer
	Dim i As Integer

	n = UBound(MortalityTable, 1)

	ReDim MortCurve(n)
	ReDim newMortalityTable(n)
	ReDim discMortCurve(n)

	newMortalityTable(0) = MortalityTable(0)

	For i = 0 To n - 1
	    MortCurve(i + 1) = (MortalityTable(i) - MortalityTable(i + 1)) / MortalityTable(i)
	    discMortCurve(i + 1) = MortCurve(i + 1) * (1 - DiscountRate)
	    newMortalityTable(i + 1) = newMortalityTable(i) * (1 - discMortCurve(i + 1))
	Next i

	DiscountMortTable = newMortalityTable()

	End Function

		Public Function ConvertRangeToVariant(matrix() As Double) As Variant 'cause excel : input range output variant

	Dim i As Integer
	Dim n As Integer
	Dim VariantMatrix() As Variant
	n = UBound(matrix, 1)

	ReDim VariantMatrix(n, 0)

	For i = 0 To n
	    VariantMatrix(i, 0) = matrix(i)
	Next i

	ConvertRangeToVariant = VariantMatrix

	End Function

	Public Function ExDiscountMortTable(MortalityTable As Range, discRate As Double) As Variant

	Dim DouMortTable() As Double
	Dim newMortalityTable() As Double
	ReDim DouMortTable(MortalityTable.Rows.Count)
	ReDim newMortalityTable(MortalityTable.Rows.Count)

	DouMortTable() = ConvertRangeToDouble(MortalityTable)
	newMortalityTable() = DiscountMortTable(DouMortTable(), discRate)
	ExDiscountMortTable = ConvertRangeToVariant(newMortalityTable())

	End Function

//*/










