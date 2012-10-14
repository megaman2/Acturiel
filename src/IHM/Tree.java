package IHM;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import ToolBox.Calcul;


/**
 * Class qui g�n�rera une JTree qui sera afficher en haut � gauche de la Fenetre principale. Elle permettra de naviguer � travers les promo, les ann�es, et les {cours, etudiant, enseignant}
 */
public class Tree extends JTree implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JTree myTree;
	private JScrollPane myPane;
	private Fenetre fenetre;
	private DefaultMutableTreeNode selectNode;
	private ArrayList<DefaultMutableTreeNode> listNode;
	/**
	 * Constructeur permettant de g�n�rer l'arbre pour la navigation
	 * @param fenetre La Fenetre principale
	 */
	public Tree(final Fenetre fenetre)  {
		this.fenetre=fenetre;
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Fili�re"); // il sert a� quoi ?
		JTree monArbre = new JTree(rootNode);
		setSelectNode(rootNode);
		monArbre.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				setSelectNode(node);
				remplitJTable(node);
			}
		});
		listNode = new ArrayList<DefaultMutableTreeNode>();
		// Niveau 1 : les contracts
		listNode.add(new DefaultMutableTreeNode("Table"));
		listNode.add(new DefaultMutableTreeNode("Generic Calc"));
		listNode.add(new DefaultMutableTreeNode("Generic Contract"));
		listNode.add(new DefaultMutableTreeNode("Stress Test"));
		listNode.add(new DefaultMutableTreeNode("Pure Endowment Premium"));
		listNode.add(new DefaultMutableTreeNode("Whole Life Premium"));
		listNode.add(new DefaultMutableTreeNode("Term Assurance"));
		listNode.add(new DefaultMutableTreeNode("Endowment Premium"));
		listNode.add(new DefaultMutableTreeNode("Term Assurance Reserve"));

		for( DefaultMutableTreeNode d :listNode)
			rootNode.add(d);


		rootNode.setUserObject("Contract");
		setMyTree(monArbre);
		setMyPane(new JScrollPane(myTree));
	}

	/**
	 * Getteur pour r�cup�rer l'arbre
	 * @return L'arbre
	 */
	public JTree getMyTree() {
		return myTree;
	}

	/**
	 * Setteur pour modifier l'arbre
	 * @param myTree Un nouvel arbre
	 */
	public void setMyTree(JTree myTree) {
		this.myTree = myTree;
	}

	/**
	 * Getteur qui renvoie le JScrollPane dans lequel est contenu l'arbre
	 * @return Le JScrollPane
	 */
	public JScrollPane getMyPane() {
		return myPane;
	}

	public ArrayList<DefaultMutableTreeNode> getListNode() {
		return listNode;
	}

	public void setListNode(ArrayList<DefaultMutableTreeNode> listNode) {
		this.listNode = listNode;
	}

	/**
	 * Setteur pour changer le JScrollPane dans lequel est contenu l'arbre
	 * @param myPane
	 */
	public void setMyPane(JScrollPane myPane) {
		this.myPane = myPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getSource());
	}

	/**
	 * Getteur pour r�cup�rer les neouds s�lectionn�s
	 * @return
	 */
	public DefaultMutableTreeNode getSelectNode() {
		return selectNode;
	}

	/**
	 * Setteur pour modifier les noeuds s�lectionn�s
	 * @param selectNode
	 */
	public void setSelectNode(DefaultMutableTreeNode selectNode) {
		this.selectNode = selectNode;
	}


	/**
	 * Remplit la JTable avec les donn�es de la BD
	 * @param node
	 */


	public void remplitJTable(DefaultMutableTreeNode node){

		if ( node.toString().equals("Table") ) {
			createurTable();
		}else if(node.toString().equals("Generic Calc")){
			createurGenericCalc();
		}else if(node.toString().equals("Pure Endowment Premium")){
			createurPureEndowmentPremium();
		}else if(node.toString().equals("Whole Life Premium")){
			createurWholeLifePremium();
		}else if(node.toString().equals("Term Assurance")){
			createurTermAssurance();
		}else if(node.toString().equals("Endowment Premium")){
			createurEndowmentPremium();
		}else if(node.toString().equals("Generic Contract")){
			createurGenericContract();
		}
	}


	public void createurTable(){
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


	public void createurGenericContract(){
		int amount =Integer.parseInt(fenetre.getAmount().getText());
		int age=Integer.parseInt(fenetre.getAge().getText());
		int term = Integer.parseInt(fenetre.getTerm().getText());
		int technicalRate=Integer.parseInt(fenetre.getTechnicalRate().getText());
		int payment=Integer.parseInt(fenetre.getPayment().getText());


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
			donnees[i][2]=Calcul.qx(fenetre.getListMortality().get(indice).getValeur()[i],fenetre.getListMortality().get(0).getValeur()[i+1])+"";
		}
		for( int i=0; i < donnees.length -1; i++){
			donnees[i][3]=Calcul.dx(fenetre.getListMortality().get(indice).getValeur()[i],fenetre.getListMortality().get(0).getValeur()[i+1])+"";
		}


		int offsetH=5;
		int offsetV=5;
		// prenser a ajouter 3 espaces pour la coloration !


		//Single Premiun 
		donnees[offsetV][offsetH]="Single Premiun   ";
		donnees[offsetV][offsetH+1]=amount+""; //TODO
		// Annual Premium
		donnees[offsetV+3][offsetH]="Annual Premium   ";
		donnees[offsetV+3][offsetH+1]=amount+""; //TODO
		fenetre.createurPanelJTable(entetes, donnees);
	}

	public void createurGenericCalc(){
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

	public void createurStressTest(){

	}

	public void createurPureEndowmentPremium(){
		int amount =Integer.parseInt(fenetre.getAmount().getText());
		int age=Integer.parseInt(fenetre.getAge().getText());
		int term = Integer.parseInt(fenetre.getTerm().getText());
		int technicalRate=Integer.parseInt(fenetre.getTechnicalRate().getText());
		int payment=Integer.parseInt(fenetre.getPayment().getText());


		String[] entetes = {"age",fenetre.getChoixTable().getSelectedItem().toString(),"qx","dx", " ","term","tpx","v^t","tEx"};
		String[][] donnees = new String[fenetre.getListMortality().get(0).getValeur().length][12];
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

		// pour term
		for( int i=0; i < 10; i++){
			//term
			donnees[i][5]=i+"";
			//tpx
			donnees[i][6]=Calcul.npx(age,Integer.parseInt(donnees[i][5]),fenetre.getListMortality().get(indice).getValeur())+"";
			//v^t
			donnees[i][7]=Calcul.techDF(Integer.parseInt(donnees[i][5]),technicalRate)+"";
			//tEx
			donnees[i][8]=Double.parseDouble(donnees[i][6])*Double.parseDouble(donnees[i][7])+"";

		}

		int offsetH=5;
		int offsetV=15;
		// prenser a ajouter 3 espaces pour la coloration !
		donnees[offsetV][offsetH]="npx   ";
		donnees[offsetV][offsetH+1]=Calcul.npx(age, term ,fenetre.getListMortality().get(indice).getValeur())+"";

		donnees[offsetV+1][offsetH]="v^n   ";
		donnees[offsetV+1][offsetH+1]=Calcul.techDF(term,technicalRate)+"";

		donnees[offsetV+2][offsetH]="nEx   ";
		donnees[offsetV+2][offsetH+1]= Double.parseDouble(donnees[offsetV][offsetH+1])*Double.parseDouble(donnees[offsetV+1][offsetH+1])+"";

		donnees[offsetV+4][offsetH]="a..m:x   ";
		double somme=0.0;
		for( int i=0; i < 10; i++){
			somme+=Double.parseDouble(donnees[i][8]);
		}
		donnees[offsetV+4][offsetH+1]=somme +"";

		//Single Premiun 
		donnees[offsetV+6][offsetH]="Single Premiun   ";
		donnees[offsetV+6][offsetH+1]=amount*Double.parseDouble(donnees[offsetV][offsetH+1])*Double.parseDouble(donnees[offsetV+1][offsetH+1])+"";
		donnees[offsetV+7][offsetH+1]=0.0+""; //TODO
		if(  (Double.parseDouble(donnees[offsetV+6][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+7][offsetH+1])*100000 < 1 && (Double.parseDouble(donnees[offsetV+6][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+7][offsetH+1])*100000 > -1 ))){
			donnees[offsetV+7][offsetH+2]="OK";
		}else{
			donnees[offsetV+7][offsetH+2]="ERREUR";
		}
		// Annual Premium
		donnees[offsetV+9][offsetH]="Annual Premium   ";
		donnees[offsetV+9][offsetH+1]=Double.parseDouble(donnees[offsetV+6][offsetH+1])/Double.parseDouble(donnees[offsetV+4][offsetH+1])+"";
		donnees[offsetV+10][offsetH+1]=(amount*(Double.parseDouble(donnees[offsetV+2][offsetH+1])/Double.parseDouble(donnees[offsetV+4][offsetH+1])))+"";
		if((Double.parseDouble(donnees[offsetV+9][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+10][offsetH+1])*100000 < 1 && (Double.parseDouble(donnees[offsetV+9][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+10][offsetH+1])*100000 > -1 ))){
			donnees[offsetV+10][offsetH+2]="OK";
		}else{
			donnees[offsetV+10][offsetH+2]="ERREUR";
		}
		fenetre.createurPanelJTable(entetes, donnees);
	}

	public void createurWholeLifePremium(){
		int amount =Integer.parseInt(fenetre.getAmount().getText());
		int age=Integer.parseInt(fenetre.getAge().getText());
		int term = Integer.parseInt(fenetre.getTerm().getText());
		int technicalRate=Integer.parseInt(fenetre.getTechnicalRate().getText());
		int payment=Integer.parseInt(fenetre.getPayment().getText());


		String[] entetes = {"age",fenetre.getChoixTable().getSelectedItem().toString(),"qx","dx", " ","term","tpx","v^t","tEx", "  ","term","v^h","h-1/1 q x","h-1/ A x" };
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
			donnees[i][2]=Calcul.qx(fenetre.getListMortality().get(indice).getValeur()[i],fenetre.getListMortality().get(indice).getValeur()[i+1])+"";
		}
		for( int i=0; i < donnees.length -1; i++){
			donnees[i][3]=Calcul.dx(fenetre.getListMortality().get(indice).getValeur()[i],fenetre.getListMortality().get(indice).getValeur()[i+1])+"";
		}

		// pour term
		for( int i=0; i < 16; i++){
			//term
			donnees[i][10]=i+"";
			//tpx
			donnees[i][11]=Calcul.npx(age,Integer.parseInt(donnees[i][10]),fenetre.getListMortality().get(indice).getValeur())+"";
			//v^t
			donnees[i][12]=Calcul.techDF(Integer.parseInt(donnees[i][10]),technicalRate)+"";
			//tEx
			donnees[i][13]=Double.parseDouble(donnees[i][11])*Double.parseDouble(donnees[i][12])+"";

		}

		// pour term 2
		for( int i=0; i < 61; i++){
			//term
			donnees[i][5]=i+1+"";
			//v^h
			donnees[i][6]=100+"";
			//h-1/1 q x 
			donnees[i][7]=100+"";
			//h-1/1 A x
			donnees[i][8]=100+"";

		}

		int offsetH=10;
		int offsetV=19;
		// prenser a ajouter 3 espaces pour la coloration !
		donnees[offsetV][offsetH]="npx   ";
		donnees[offsetV][offsetH+1]=Calcul.npx(age, term ,fenetre.getListMortality().get(indice).getValeur())+"";

		donnees[offsetV+1][offsetH]="v^n   ";
		donnees[offsetV+1][offsetH+1]=Calcul.techDF(term,technicalRate)+"";

		donnees[offsetV+2][offsetH]="nEx   ";
		donnees[offsetV+2][offsetH+1]= Double.parseDouble(donnees[offsetV][offsetH+1])*Double.parseDouble(donnees[offsetV+1][offsetH+1])+"";

		donnees[offsetV+4][offsetH]="a..m:x   ";
		double somme=0.0;
		for( int i=0; i < 10; i++){
			somme+=Double.parseDouble(donnees[i][8]);
		}
		donnees[offsetV+4][offsetH+1]=somme +"";

		//Single Premiun 
		donnees[offsetV+6][offsetH]="Single Premiun   ";
		donnees[offsetV+6][offsetH+1]=amount*Double.parseDouble(donnees[offsetV][offsetH+1])*Double.parseDouble(donnees[offsetV+1][offsetH+1])+"";
		donnees[offsetV+7][offsetH+1]=0.0+""; //TODO
		if(  (Double.parseDouble(donnees[offsetV+6][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+7][offsetH+1])*100000 < 1 && (Double.parseDouble(donnees[offsetV+6][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+7][offsetH+1])*100000 > -1 ))){
			donnees[offsetV+7][offsetH+2]="OK";
		}else{
			donnees[offsetV+7][offsetH+2]="ERREUR";
		}
		// Annual Premium
		donnees[offsetV+9][offsetH]="Annual Premium   ";
		donnees[offsetV+9][offsetH+1]=Double.parseDouble(donnees[offsetV+6][offsetH+1])/Double.parseDouble(donnees[offsetV+4][offsetH+1])+"";
		donnees[offsetV+10][offsetH+1]=(amount*(Double.parseDouble(donnees[offsetV+2][offsetH+1])/Double.parseDouble(donnees[offsetV+4][offsetH+1])))+"";
		if((Double.parseDouble(donnees[offsetV+9][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+10][offsetH+1])*100000 < 1 && (Double.parseDouble(donnees[offsetV+9][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+10][offsetH+1])*100000 > -1 ))){
			donnees[offsetV+10][offsetH+2]="OK";
		}else{
			donnees[offsetV+10][offsetH+2]="ERREUR";
		}
		
		donnees[offsetV+12][offsetH]="AX   ";
		donnees[offsetV+12][offsetH+1]="AX   ";

		
		fenetre.createurPanelJTable(entetes, donnees);
	}

	public void createurTermAssurance(){
		int amount =Integer.parseInt(fenetre.getAmount().getText());
		int age=Integer.parseInt(fenetre.getAge().getText());
		int term = Integer.parseInt(fenetre.getTerm().getText());
		int technicalRate=Integer.parseInt(fenetre.getTechnicalRate().getText());
		int payment=Integer.parseInt(fenetre.getPayment().getText());


		String[] entetes = {"age",fenetre.getChoixTable().getSelectedItem().toString(),"qx","dx", " ","term","tpx","v^t","tEx", "  ","term","v^h","h-1/1 q x","h-1/ A x" };
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
			donnees[i][2]=Calcul.qx(fenetre.getListMortality().get(indice).getValeur()[i],fenetre.getListMortality().get(indice).getValeur()[i+1])+"";
		}
		for( int i=0; i < donnees.length -1; i++){
			donnees[i][3]=Calcul.dx(fenetre.getListMortality().get(indice).getValeur()[i],fenetre.getListMortality().get(indice).getValeur()[i+1])+"";
		}

		// pour term
		for( int i=0; i < 16; i++){
			//term
			donnees[i][10]=i+"";
			//tpx
			donnees[i][11]=Calcul.npx(age,Integer.parseInt(donnees[i][10]),fenetre.getListMortality().get(indice).getValeur())+"";
			//v^t
			donnees[i][12]=Calcul.techDF(Integer.parseInt(donnees[i][10]),technicalRate)+"";
			//tEx
			donnees[i][13]=Double.parseDouble(donnees[i][11])*Double.parseDouble(donnees[i][12])+"";

		}

		// pour term 2
		for( int i=0; i < 20; i++){
			//term
			donnees[i][5]=i+1+"";
			//v^h
			donnees[i][6]=100+"";
			//h-1/1 q x 
			donnees[i][7]=100+"";
			//h-1/1 A x
			donnees[i][8]=100+"";

		}

		int offsetH=10;
		int offsetV=19;
		// prenser a ajouter 3 espaces pour la coloration !
		donnees[offsetV][offsetH]="npx   ";
		donnees[offsetV][offsetH+1]=Calcul.npx(age, term ,fenetre.getListMortality().get(indice).getValeur())+"";

		donnees[offsetV+1][offsetH]="v^n   ";
		donnees[offsetV+1][offsetH+1]=Calcul.techDF(term,technicalRate)+"";

		donnees[offsetV+2][offsetH]="nEx   ";
		donnees[offsetV+2][offsetH+1]= Double.parseDouble(donnees[offsetV][offsetH+1])*Double.parseDouble(donnees[offsetV+1][offsetH+1])+"";

		donnees[offsetV+4][offsetH]="a..m:x   ";
		double somme=0.0;
		for( int i=0; i < 10; i++){
			somme+=Double.parseDouble(donnees[i][8]);
		}
		donnees[offsetV+4][offsetH+1]=somme +"";
		donnees[offsetV+5][offsetH+1]=somme +"";
		if(false){
			donnees[offsetV+5][offsetH+3]="OK";
		}else{
			donnees[offsetV+5][offsetH+3]="ERREUR";
		}
		offsetV++;
		//Single Premiun 
		donnees[offsetV+6][offsetH]="Single Premiun   ";
		donnees[offsetV+6][offsetH+1]=amount*Double.parseDouble(donnees[offsetV][offsetH+1])*Double.parseDouble(donnees[offsetV+1][offsetH+1])+"";
		donnees[offsetV+7][offsetH+1]=0.0+""; //TODO
		donnees[offsetV+7][offsetH+2]=0.0+""; //TODO

		if( false){
			donnees[offsetV+7][offsetH+3]="OK";
		}else{
			donnees[offsetV+7][offsetH+3]="ERREUR";
		}
		// Annual Premium
		donnees[offsetV+9][offsetH]="Annual Premium   ";
		donnees[offsetV+9][offsetH+1]=Double.parseDouble(donnees[offsetV+6][offsetH+1])/Double.parseDouble(donnees[offsetV+4][offsetH+1])+"";
		donnees[offsetV+10][offsetH+1]=amount+"";
		donnees[offsetV+10][offsetH+2]=amount+"";

		if((Double.parseDouble(donnees[offsetV+9][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+10][offsetH+1])*100000 < 1 && (Double.parseDouble(donnees[offsetV+9][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+10][offsetH+1])*100000 > -1 ))){
			donnees[offsetV+10][offsetH+3]="OK";
		}else{
			donnees[offsetV+10][offsetH+3]="ERREUR";
		}
		
		donnees[offsetV+12][offsetH]="nAX   ";
		donnees[offsetV+12][offsetH+1]="calcul";
		donnees[offsetV+13][offsetH+1]="calcul";
		if(false){
			donnees[offsetV+12][offsetH+3]="OK";
		}else{
			donnees[offsetV+12][offsetH+3]="ERREUR";
		}
		fenetre.createurPanelJTable(entetes, donnees);
	}

	public void createurEndowmentPremium(){
		int amount =Integer.parseInt(fenetre.getAmount().getText());
		int age=Integer.parseInt(fenetre.getAge().getText());
		int term = Integer.parseInt(fenetre.getTerm().getText());
		int technicalRate=Integer.parseInt(fenetre.getTechnicalRate().getText());
		int payment=Integer.parseInt(fenetre.getPayment().getText());


		String[] entetes = {"age",fenetre.getChoixTable().getSelectedItem().toString(),"qx","dx", " ","term","tpx","v^t","tEx", "  ","term","v^h","h-1/1 q x","h-1/ A x" };
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

		// pour term
		for( int i=0; i < 5; i++){
			//term
			donnees[i][10]=i+"";
			//tpx
			donnees[i][11]=Calcul.npx(age,Integer.parseInt(donnees[i][10]),fenetre.getListMortality().get(indice).getValeur())+"";
			//v^t
			donnees[i][12]=Calcul.techDF(Integer.parseInt(donnees[i][10]),technicalRate)+"";
			//tEx
			donnees[i][13]=Double.parseDouble(donnees[i][11])*Double.parseDouble(donnees[i][12])+"";

		}

		// pour term 2
		for( int i=0; i < 15; i++){
			//term
			donnees[i][5]=i+1+"";
			//v^h
			donnees[i][6]=100+"";
			//h-1/1 q x 
			donnees[i][7]=100+"";
			//h-1/1 A x
			donnees[i][8]=100+"";

		}

		int offsetH=10;
		int offsetV=19;
		// prenser a ajouter 3 espaces pour la coloration !
		donnees[offsetV][offsetH]="npx   ";
		donnees[offsetV][offsetH+1]=Calcul.npx(age, term ,fenetre.getListMortality().get(indice).getValeur())+"";

		donnees[offsetV+1][offsetH]="v^n   ";
		donnees[offsetV+1][offsetH+1]=Calcul.techDF(term,technicalRate)+"";

		donnees[offsetV+2][offsetH]="nEx   ";
		donnees[offsetV+2][offsetH+1]= Double.parseDouble(donnees[offsetV][offsetH+1])*Double.parseDouble(donnees[offsetV+1][offsetH+1])+"";

		donnees[offsetV+4][offsetH]="a..m:x   ";
		double somme=0.0;
		for( int i=0; i < 10; i++){
			somme+=Double.parseDouble(donnees[i][8]);
		}
		donnees[offsetV+4][offsetH+1]=somme +"";

		//Single Premiun 
		donnees[offsetV+6][offsetH]="Single Premiun   ";
		donnees[offsetV+6][offsetH+1]=amount*Double.parseDouble(donnees[offsetV][offsetH+1])*Double.parseDouble(donnees[offsetV+1][offsetH+1])+"";
		donnees[offsetV+7][offsetH+1]=0.0+""; //TODO
		if(  (Double.parseDouble(donnees[offsetV+6][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+7][offsetH+1])*100000 < 1 && (Double.parseDouble(donnees[offsetV+6][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+7][offsetH+1])*100000 > -1 ))){
			donnees[offsetV+7][offsetH+2]="OK";
		}else{
			donnees[offsetV+7][offsetH+2]="ERREUR";
		}
		// Annual Premium
		donnees[offsetV+9][offsetH]="Annual Premium   ";
		donnees[offsetV+9][offsetH+1]=Double.parseDouble(donnees[offsetV+6][offsetH+1])/Double.parseDouble(donnees[offsetV+4][offsetH+1])+"";
		donnees[offsetV+10][offsetH+1]=(amount*(Double.parseDouble(donnees[offsetV+2][offsetH+1])/Double.parseDouble(donnees[offsetV+4][offsetH+1])))+"";
		if((Double.parseDouble(donnees[offsetV+9][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+10][offsetH+1])*100000 < 1 && (Double.parseDouble(donnees[offsetV+9][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+10][offsetH+1])*100000 > -1 ))){
			donnees[offsetV+10][offsetH+2]="OK";
		}else{
			donnees[offsetV+10][offsetH+2]="ERREUR";
		}
		
		donnees[offsetV+12][offsetH]="nAx   ";
		donnees[offsetV+12][offsetH+1]="1000";
		donnees[offsetV+13][offsetH+1]="1000";
		if((Double.parseDouble(donnees[offsetV+12][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+13][offsetH+1])*100000 < 1 && (Double.parseDouble(donnees[offsetV+12][offsetH+1])*100000 - Double.parseDouble(donnees[offsetV+13][offsetH+1])*100000 > -1 ))){
			donnees[offsetV+13][offsetH+2]="OK";
		}else{
			donnees[offsetV+13][offsetH+2]="ERREUR";
		}
		fenetre.createurPanelJTable(entetes, donnees);
	}

	public void createurTermAssuranceReserve(){

	}
}