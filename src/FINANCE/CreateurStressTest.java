package FINANCE;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import IHM.Fenetre;
import ToolBox.Calcul;

public class CreateurStressTest extends Contrat{
	private int discount;
	public CreateurStressTest(int amount, int age, int term, int technicalRate,
			int payment,int discount, String contrat){
		super(amount, age, term, technicalRate, payment, contrat);
		this.discount=discount;
	}

@Override
public void createurPanelJTable(Fenetre fenetre) {
	if(discount <= 0 || discount >= 100){
		fenetre.getRatio().setText("15");
		discount=15;
		JOptionPane.showMessageDialog(fenetre, "Attention le ratio droit etre in entier entre 0 et 100", "avertissement",JOptionPane.WARNING_MESSAGE);
	
	}
	
	String[] entetes = {"age",fenetre.getChoixTable().getSelectedItem().toString(),"qx", "qx stressed", "lx stressed","qx stressed", "lx stressed", "check 1", "check 2", "     ", "    ", "    "};
	String[][] donnees = new String[fenetre.getListMortality().get(0).getValeur().length][20];
	int indice = fenetre.getChoixTable().getSelectedIndex();
	for( int i=0; i < donnees.length ; i++){
		donnees[i][0]=i+"";
	}
	for( int i=0; i < donnees.length ; i++){
		donnees[i][1]=fenetre.getListMortality().get(indice).getValeur()[i]+"";
	}

	for( int i=0; i < donnees.length -1; i++){
		donnees[i][2]=Calcul.qx(fenetre.getListMortality().get(indice).getValeur()[i],fenetre.getListMortality().get(indice).getValeur()[i+1])+"";
		
	}
	ArrayList<Double> qxStresssInList = new ArrayList<>();
	
	for( int i=0; i < donnees.length -1 ; i++){
		donnees[i][3]=(Double.parseDouble(donnees[i][2]))*(1-discount/100.0)+"";
		if(Double.parseDouble(donnees[i][2]) == 1 ){
			donnees[i][3]=1+"";
		}
		qxStresssInList.add(Double.parseDouble(donnees[i][3]));
	}
	
	donnees[0][4]=100000+"";
	for( int i=1; i < donnees.length -1; i++){
		donnees[i][4]=Double.parseDouble(donnees[i-1][4])*(1-Double.parseDouble(donnees[i-1][3]))+"";
	}
	for( int i=0; i < donnees.length -2 ; i++){
		donnees[i][5]=1-Double.parseDouble(donnees[i+1][4])/Double.parseDouble(donnees[i][4])+"";
		if(Double.parseDouble(donnees[i][2]) == 1 ){
			donnees[i][5]=1+"";
		}
	}
	donnees[0][6]=100000+"";
	for( int i=1; i < donnees.length -1; i++){
		donnees[i][6]=Double.parseDouble(donnees[i-1][4])*(1-Double.parseDouble(donnees[i-1][3]))+"";
	}
	// test
	int i=0; 
	do{
		if( equals(donnees[i][3],donnees[i][5])){
			donnees[i][7]="OK";
		}else{
			donnees[i][7]="ERREUR";
		}
		i++;
	}while(Double.parseDouble(donnees[i-1][3]) != 1 );
	i=0; 
	do{
		if( equals(donnees[i][4],donnees[i][6])){
			donnees[i][8]="OK";
		}else{
			donnees[i][8]="ERREUR";
		}
		i++;
	}while(Double.parseDouble(donnees[i-1][3]) != 1 );
	
	XYSeries lx =new XYSeries("lx");
	XYSeries lxStressed = new XYSeries("lx Stresse");
	XYSeries qx =new XYSeries("qx");
	XYSeries qxStressed = new XYSeries("qx Stresse");
	
	i=0;
	while( Integer.parseInt(donnees[i][1]) > 0) {
		lx.add(i,Double.parseDouble(donnees[i][1]));
		lxStressed.add(i,Double.parseDouble(donnees[i][4]));
		qxStressed.add(i,Double.parseDouble(donnees[i][3]));
		qx.add(i,Double.parseDouble(donnees[i][2]));
		i++;
	}
	
	
	
	
	XYSeriesCollection dataset = new XYSeriesCollection();
	dataset.addSeries(lx);
	dataset.addSeries(lxStressed);
	JFreeChart graphLx = ChartFactory.createXYLineChart("StressTest Lx", "age", "lx", dataset, PlotOrientation.VERTICAL, true, true, false);
	ChartPanel chartLx = new ChartPanel(graphLx);
	
	
	XYSeriesCollection dataset1 = new XYSeriesCollection();
	dataset1.addSeries(qx);
	dataset1.addSeries(qxStressed);
	JFreeChart graphQx = ChartFactory.createXYLineChart("StressTest Qx", "age", "qx", dataset1, PlotOrientation.VERTICAL, true, true, false);
	ChartPanel chartQx = new ChartPanel(graphQx);
	fenetre.getDroite().setPreferredSize(new Dimension(500, 100));

	fenetre.getDroite().add(chartLx);
	fenetre.getDroite().add(chartQx);
	
	int offsetH=10;
	int offsetV=9;
	
	//Annual Premium
	donnees[offsetV][offsetH]="Annual Premiun   ";
	donnees[offsetV][offsetH+1]=Calcul.AnnualPremium(age, payment,term, technicalRate, amount,fenetre.getListMortality().get(indice).getValeur(), contrat)+"";


	// Annual Premium 2nd order
	donnees[offsetV+1][offsetH]="Annual Premium 2nd order   ";
	double[] qxStressedTab = new double[qxStresssInList.size()];
	for (int j = 0; j < qxStressedTab.length; j++) {
		qxStressedTab[j]= qxStresssInList.get(j);
	}
	donnees[offsetV+1][offsetH+1]=Calcul.AnnualPremium(age, payment,term, technicalRate, amount, fenetre.getListMortality().get(indice).ratio(discount, qxStressedTab), contrat)+"";

	// Implicite Profit
	donnees[offsetV+3][offsetH]="Implicite Profit   ";
	donnees[offsetV+3][offsetH+1]=(Double.parseDouble(donnees[offsetV][offsetH+1])-Double.parseDouble(donnees[offsetV+1][offsetH+1]))+"";
			
	
	
	fenetre.createurPanelJTable(entetes, donnees);
	}

}
