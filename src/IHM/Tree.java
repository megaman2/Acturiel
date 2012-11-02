package IHM;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import FINANCE.CreateurEndowmentPremium;
import FINANCE.CreateurGenericCalc;
import FINANCE.CreateurGenericContract;
import FINANCE.CreateurPureEndowmentPremium;
import FINANCE.CreateurStressTest;
import FINANCE.CreateurTable;
import FINANCE.CreateurTermAssurance;
import FINANCE.CreateurTermAssuranceReserve;
import FINANCE.CreateurWholeLifePremium;
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
		fenetre.getDroite().removeAll();
		fenetre.getDroite().setPreferredSize(new Dimension(0,0));
		int amount =Integer.parseInt(fenetre.getAmount().getText());
		int age=Integer.parseInt(fenetre.getAge().getText());
		int term = Integer.parseInt(fenetre.getTerm().getText());
		int technicalRate=Integer.parseInt(fenetre.getTechnicalRate().getText());
		int payment=Integer.parseInt(fenetre.getPayment().getText());
		int discount = Integer.parseInt(fenetre.getRatio().getText());
		String contrat = fenetre.getChoixContrat().getSelectedItem().toString();

		if ( node.toString().equals("Table") ) {
			CreateurTable ct = new CreateurTable(amount, age, term, technicalRate, payment, contrat);
			ct.createurPanelJTable(fenetre);
		}else if(node.toString().equals("Generic Calc")){
			CreateurGenericCalc cgc = new CreateurGenericCalc(amount, age, term, technicalRate, payment, contrat);
			cgc.createurPanelJTable(fenetre);
		}else if(node.toString().equals("Pure Endowment Premium")){
			CreateurPureEndowmentPremium cpep= new CreateurPureEndowmentPremium(amount, age, term, technicalRate, payment, contrat);
			cpep.createurPanelJTable(fenetre);
		}else if(node.toString().equals("Whole Life Premium")){
			CreateurWholeLifePremium cwlp = new CreateurWholeLifePremium(amount, age, term, technicalRate, payment, contrat);
			cwlp.createurPanelJTable(fenetre);
		}else if(node.toString().equals("Term Assurance")){
			CreateurTermAssurance cta = new CreateurTermAssurance(amount, age, term, technicalRate, payment, contrat);
			cta.createurPanelJTable(fenetre);
		}else if(node.toString().equals("Endowment Premium")){
			CreateurEndowmentPremium cep = new CreateurEndowmentPremium(amount, age, term, technicalRate, payment, contrat);
			cep.createurPanelJTable(fenetre);
		}else if(node.toString().equals("Generic Contract")){
			CreateurGenericContract cgc = new CreateurGenericContract(amount, age, term, technicalRate, payment, contrat);
			cgc.createurPanelJTable(fenetre);
		}else if(node.toString().equals("Stress Test")){
			CreateurStressTest cst =new  CreateurStressTest(amount, age, term, technicalRate, payment, discount, contrat);
			cst.createurPanelJTable(fenetre);
		}else if(node.toString().equals("Term Assurance Reserve")){
			CreateurTermAssuranceReserve ctar = new CreateurTermAssuranceReserve(amount, age, term, technicalRate, payment, discount, contrat);
			ctar.createurPanelJTable(fenetre);
		}
	}	
}