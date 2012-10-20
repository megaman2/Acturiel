package IHM;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;



import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import FINANCE.MortalityTable;



/**
 * Cette Class permet de generer une fenetre qui est le
 * squelette de notre IHM
 */
public class Fenetre extends JFrame implements ActionListener{
	// serial UID
	private static final long serialVersionUID = -8466580375192983430L;

	// fenetre
	private String windowsName = "Projet Calcul Acturiel";    // nom
	private int widthMinFenetre = 800;        // largeur minimale
	private int heightMinFenetre = 600;        // hauteur minimale
	// panels
	private JPanel container;    // panel contenant les panels : barreMenus, gauche et center

	private JMenuBar barreMenus;// la JMenuBar d'en haut

	private JPanel centre;        // panel contenant les panels : boutonHaut, boutonsBas et affichage
	private JPanel boutonHaut;    // les boutons de navigation en haut
	private JPanel boutonsBas;    // les boutons de navigation en bas
	private JPanel affichage;    // partie ou on affichera la jtable ou autre chose
	private JPanel droite;
	
	private JPanel gauche;        // panel contenant les panels : arbre et sauvegardeVue
	private JScrollPane arbre;        // zone gauche-haut
	//private JPanel sauvegardeVue;// zone gauche-bas

	// La JTable
	private JTable tableau;

	// Pour affichier la Jtable
	private String[] entetes;
	private String[][] donnees;

	// le tree
	private Tree tree;
	// JMENU
	//        FICHIER
	JMenuItem ouvrir;
	JMenuItem enregistrer;
	JMenuItem enregistrerSous;
	JMenuItem resetProp;
	JMenuItem quitter;
	//        EDITION
	JMenuItem toutSelectionner;
	JMenuItem toutDeselectionner;
	JMenuItem ajout;
	JMenuItem detail;
	JMenuItem suppression;
	//        VUE
	JMenuItem charger;
	JMenuItem sauvegarder;
	JMenuItem gestionRenommer;
	JMenuItem gestionSuppression;


	// Choix des tables de mortalité

	private  JComboBox choixTable;
	private ArrayList<MortalityTable> listMortality;
	private boolean saveFormXL;
	private JTextField offset;
	private JLabel titreOffset;
	private JButton valider;
	private JButton remiseAZero;

	private JTextField age;
	private JLabel titreAge;

	private JTextField payment;
	private JLabel titrePayment;

	private JTextField term;
	private JLabel titreTerm;

	private JTextField technicalRate;
	private JLabel titreTechnicalRate;

	private JTextField amount;
	private JLabel titreAmount;

	private JComboBox choixContrat;
	private JLabel titreChoixContrat;

	private JTextField ratio;
	private JLabel titreRatio;
	

	// divers
	private Color colorBg = new Color(230, 230, 255);





	/**
	 * La structure de base de la fenetre avec tous les boutons et les differentes zones.
	 *
	 */
	public void strutureBaseFenetre(){
		saveFormXL = false;
		this.setTitle(windowsName);
		this.setSize(widthMinFenetre, heightMinFenetre);
		this.setMinimumSize(new Dimension(widthMinFenetre, heightMinFenetre));        // taille minimale de la fenetre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // pour fermer
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setVisible(true);

		// la fenetre
		container = new JPanel();
		container.setLayout(new BorderLayout());

		// NORTH : barre de menu (en haut)
		barreMenus = createJMenuBar();
		container.add(barreMenus, BorderLayout.PAGE_START);

		// WEST : partie de gauche avec les vues
		gauche = new JPanel();
		gauche.setPreferredSize(new Dimension(200, 600));            // partie de gauche fixee a 120px
		gauche.setLayout(new BoxLayout(gauche, BoxLayout.Y_AXIS));    // a gauche, ca se stockera de haut en bas
		container.add(gauche, BorderLayout.LINE_START);

		// WEST > ANNEE
		tree = new Tree(this);
		arbre = tree.getMyPane();
		gauche.add(arbre);

		// CENTER
		centre = new JPanel();
		centre.setLayout(new BorderLayout());
		container.add(centre);
		
		// Droite pour les graph
		
		droite = new JPanel();
		droite.setLayout(new GridLayout(2, 1));
		centre.add(droite,BorderLayout.EAST);
		// CENTER > SOUTH
		boutonsBas = new JPanel();
		boutonsBas.setPreferredSize(new Dimension(120, 200));            // partie de basse fixee a 200px
		boutonsBas.setLayout(new BoxLayout(boutonsBas, BoxLayout.X_AXIS));
		boutonsBas.setBackground(colorBg);
		centre.add(boutonsBas, BorderLayout.SOUTH);
		ajoutBoutonsBas();




		// CENTER > NORTH
		boutonHaut = new JPanel();
		boutonHaut.setPreferredSize(new Dimension(120, 100));            // partie de haute fixee a 100px
		boutonHaut.setLayout(new BoxLayout(boutonHaut, BoxLayout.X_AXIS));
		boutonHaut.setBackground(colorBg);


		String[] nom = new String[listMortality.size()];
		for( int i=0; i< nom.length ; i++){
			nom[i] =listMortality.get(i).getNom();
		}
		choixTable = new JComboBox(nom);
		choixTable.addActionListener(this);
		choixTable.setMaximumSize(new Dimension(100, 30));
		boutonHaut.add(choixTable);

		titreOffset = new JLabel("   Offset : ");
		boutonHaut.add(titreOffset);


		offset = new JTextField("0");
		boutonHaut.add(offset);



		valider = new JButton("valider");
		valider.addActionListener(this);
		remiseAZero = new JButton("remise a zero");
		remiseAZero.addActionListener(this);
		boutonHaut.add(valider);
		boutonHaut.add(remiseAZero);
		valider.setMaximumSize(new Dimension(90, 30));
		remiseAZero.setMaximumSize(new Dimension(150, 30));
		offset.setMaximumSize(new Dimension(50, 30));

		centre.add(boutonHaut, BorderLayout.NORTH);

	}

	public void ajoutBoutonsBas(){
		boutonsBas.setLayout(new GridLayout(7, 2));

		titreAge =new  JLabel("age ");
		titreAge.setVisible(true);
		boutonsBas.add(titreAge);
		
		age= new JTextField("20");
		boutonsBas.add(age);
		age.setVisible(true);

		titrePayment = new JLabel("Payment ");
		titrePayment.setVisible(true);
		boutonsBas.add(titrePayment);

		payment = new JTextField("10");
		payment.setVisible(true);
		boutonsBas.add(payment);
		
		titreTerm= new  JLabel("Term ");
		titreTerm.setVisible(true);
		boutonsBas.add(titreTerm);
		
		term = new JTextField("20");
		term.setVisible(true);
		boutonsBas.add(term);
		
		
		titreTechnicalRate=new  JLabel("Technical Rate ");
		titreTechnicalRate.setVisible(true);
		boutonsBas.add(titreTechnicalRate);
		
		technicalRate= new JTextField("1");
		technicalRate.setVisible(true);
		boutonsBas.add(technicalRate);
		
		titreAmount=new  JLabel("Amount ");
		titreAmount.setVisible(true);
		boutonsBas.add(titreAmount);
		
		amount= new JTextField("1000");
		amount.setVisible(true);
		boutonsBas.add(amount);
		
		titreRatio=new  JLabel("Ratio ");
		titreRatio.setVisible(true);
		boutonsBas.add(titreRatio);
		
		
		ratio = new JTextField("15");
		ratio.setVisible(true);
		boutonsBas.add(ratio);
		
		titreChoixContrat= new JLabel("Choix de Contract ");
		titreChoixContrat.setVisible(true);
		boutonsBas.add(titreChoixContrat);
		String[] nomContract = {"Term Assurance","Pure Endowment","Endowment"};
		choixContrat= new JComboBox(nomContract);
		choixContrat.addActionListener(this);
		choixContrat.setVisible(true);
		boutonsBas.add(choixContrat);

	}


	public void clearAffichage(){
		if (affichage != null){
			affichage.removeAll();
			affichage.setOpaque(false);    // resoud un probleme d'affichage lors du resize
			System.out.println("Le Panel affichage a ete nettoye !");
		}
	}

	/**
	 *  Creer et retourne une barre de menu
	 * @return JMenuBar
	 */
	private JMenuBar createJMenuBar(){
		JMenuBar barreMenus = new JMenuBar();

		// on cree les JMenu
		JMenu fichier = new JMenu("Fichier");
		JMenu edition = new JMenu("Edition");
		JMenu vue = new JMenu("Vue");
		JMenu export = new JMenu("Export");
		JMenu aide = new JMenu("?");

		// on les ajoute
		barreMenus.add(fichier);
		barreMenus.add(edition);
		barreMenus.add(vue);
		barreMenus.add(export);
		barreMenus.add(aide);

		// sous-menu de fichier
		ouvrir = new JMenuItem("Ouvrir");
		ouvrir.addActionListener(this);
		enregistrerSous = new JMenuItem("Enregistrer sous..");
		enregistrerSous.addActionListener(this);
		resetProp = new JMenuItem("Effacer les informations de connexion");
		resetProp.addActionListener(this);
		quitter = new JMenuItem("Quitter");
		fichier.add(ouvrir);
		fichier.add(enregistrerSous);
		fichier.add(resetProp);
		fichier.add(quitter);

		// sous-menu de edition
		toutSelectionner = new JMenuItem("Tout Selectionner");
		toutSelectionner.addActionListener(this);
		toutDeselectionner = new JMenuItem("Tout Deselectionner");
		toutDeselectionner.addActionListener(this);
		ajout = new JMenuItem("Ajout");
		detail = new JMenuItem("Detail");
		suppression = new JMenuItem("Suppression");
		edition.add(toutSelectionner);
		edition.add(toutDeselectionner);
		edition.add(ajout);
		edition.add(detail);
		edition.add(suppression);

		// sous-menu de vue
		charger = new JMenuItem("Tout Selectionner") ;
		sauvegarder =new JMenuItem("Tout Deselectionner") ;
		JMenu gestion =new JMenu("Gestion");
		gestionRenommer =new JMenuItem("Renommer") ;
		gestionSuppression =new JMenuItem("Suppression") ;
		vue.add(charger);
		vue.add(sauvegarder);
		vue.add(gestion);
		gestion.add(gestionRenommer);
		gestion.add(gestionSuppression);

		// sous-menu de export
		JMenuItem toExcel =new JMenuItem("Exporter to Excel") ;
		JMenuItem envoieMail =new JMenuItem("Envoie Mail") ;
		export.add(toExcel);
		export.add(envoieMail);

		// sous-menu de help
		JMenuItem docs =new JMenuItem("Document") ;
		JMenuItem credit =new JMenuItem("Credit") ;
		JMenuItem aPropos =new JMenuItem("A propos") ;
		aide.add(docs);
		aide.add(credit);
		aide.add(aPropos);

		return barreMenus;
	}

	/**
	 * Remplace ce qu'il y a au centre par l'affichage de la JTable.
	 */
	public void createurPanelJTable(String[] entetes,String[][] donnees){
		clearAffichage();
		affichage = new JPanel();
		affichage.setBackground(colorBg);
		affichage.setLayout(new BorderLayout());
		System.out.println("la taille est de "+ donnees.length+" sur "+donnees[0].length);
		tableau = new JTable(donnees, entetes);
		System.out.println("test");
		tableau.setAutoCreateRowSorter(true);
		//setColumnSize(tableau);
		JScrollPane pane = new JScrollPane(tableau);
		tableau.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableau.getTableHeader().setReorderingAllowed(false);    // interdit le changement d'ordre des colones
		affichage.add(tableau.getTableHeader(), BorderLayout.NORTH);
		affichage.add(pane, BorderLayout.CENTER);
		tableau.setDefaultRenderer(Object.class, new jTableRender());

		//getTableCellRendererComponent(tableau,entetes,true, true,entetes.length, donnees[0].length);
		
		//setDefaultRenderer(Object.class, new jTableRender());
		centre.add(affichage, BorderLayout.CENTER);
		affichage.revalidate();

	}


	/*   private void createurPanelDetail(ABBLLPP_Etudiant etu){
        clearAffichage();
        Etudiant e = new Etudiant(3, "yoko", "misa", "yoko@gmail.com", "BEP porte-a-porte", "AB negatif", "couou", "", "", "", "");
        // Le grand jpanel affichage
        affichage = new JPanel();
        affichage.setLayout(new BoxLayout(affichage,BoxLayout.X_AXIS));
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        // La partie gauche contiendra : la photo de l'etu, ses info perso, sa filiere, son annee les notes sur ses cours
        JPanel infoGauche = new JPanel();
        infoGauche.setBackground(colorBg);
        infoGauche.setLayout(new BoxLayout(infoGauche,BoxLayout.Y_AXIS));
        infoGauche.setPreferredSize(new Dimension(250, 300));
        infoGauche.setMaximumSize(new Dimension(250, 1000));
        infoGauche.setMinimumSize(new Dimension(250, 300));


        // La partie haut-gauche contiendra : la photo de l'etudiant et ses informations perso
        JPanel infoEtu = new JPanel();
        infoEtu.setBackground(colorBg);
        infoEtu.setLayout(new BoxLayout(infoEtu,BoxLayout.X_AXIS));

        //   la photo
        PhotoPanel laPhoto = new PhotoPanel(75, 100);
        laPhoto.setAlignGauche(20);
        infoEtu.add(laPhoto);

        //   ses informations perso (nom, prenom, sexe, ...)
        JPanel textInfoEtu = new JPanel();
        textInfoEtu.setBackground(colorBg);
        textInfoEtu.setLayout(new BoxLayout(textInfoEtu,BoxLayout.Y_AXIS));
        JLabel nomPrenom = new JLabel("  " + etu.getPrenomEtu() + " " + etu.getNomEtu());
        textInfoEtu.add(nomPrenom);
        JLabel dateNaissance = new JLabel("  " + etu.getDateNaissance());
        textInfoEtu.add(dateNaissance);
        JLabel sexe;
        if(etu.getSexe() == SEXE.M){
            sexe = new JLabel("Homme");
        }else{
            sexe = new JLabel("Femme");
        }
        textInfoEtu.add(sexe);
        JLabel mail = new JLabel("  " + etu.getEmailEtu());
        textInfoEtu.add(mail);
        JLabel origine = new JLabel("  " + etu.getOrigine());

        //
        textInfoEtu.add(origine);
        infoEtu.add(textInfoEtu);
        infoGauche.add(infoEtu);

        // La partie milieux-gauche contiendra : Filiere et Annee
        JPanel textFiliereAnnee = new JPanel();
        textFiliereAnnee.setBackground(colorBg);
        String textFiliere = "Filiere : " + "L2I";
        String textAnnee = "(2008-2009)";
        textFiliereAnnee.add(new JLabel(textFiliere + "   " + textAnnee));
        infoGauche.add(textFiliereAnnee);

        // La partie bas-gauche contiendra : la JTable avec les cours, notes et coefficients
        String[] entetes = {"Cours", "Notes", "Coefficient"};
        ABBLLPP_Filiere filiere = etu.getFiliere();
        ABBLLPP_Promo promo = etu.getPromo();
        JLabel lblFiliere = new JLabel("Filiere : " + filiere.getNomFiliere() + ", "+ etu.getGroup() +
                " (" + promo.getStart() + "-" +    promo.getEnd() + ")");

        List<ABBLLPP_Cours> listCours=coursDao.findByEtuIDAndPromo(etu.getIdEtu(),promo.getPromoId());
        String[][] donnee=new String[listCours.size()][];
        for(int i=0; i<listCours.size(); i++){
            donnee[i]= new String[3];
            donnee[i][0] = listCours.get(i).getNomCours();
            donnee[i][1] = listCours.get(i).getMoyCours()+"";;
            donnee[i][2] = listCours.get(i).getCoef()+"";
        }


        JPanel infoCnc = new JPanel();
        infoCnc.setBackground(colorBg);
        infoCnc.setLayout(new BorderLayout());
        JTable tableau = new JTable(donnee, entetes);
        tableau.setAutoCreateRowSorter(true);
        JScrollPane pane = new JScrollPane(tableau);
        tableau.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        infoCnc.add(tableau.getTableHeader(), BorderLayout.NORTH);
        infoCnc.add(pane, BorderLayout.CENTER);
        infoGauche.add(infoCnc);
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        // La partie droite contiendra : les JFreeChart

        // on ajoute les data d'un etudiant a� mettre dans une methode ...
        JPanel jFreeChartDroite = new JPanel();
        JPanel jFreeChartDroiteHaut = new JPanel();
        jFreeChartDroite.setLayout(new GridLayout(2,1));

        JPanel jFreeChartDroiteBas = new JPanel();
        jFreeChartDroiteBas.setLayout(new GridLayout(1,1));
        jFreeChartDroiteHaut.setLayout(new GridLayout(1,2));
        jFreeChartDroite.add(jFreeChartDroiteHaut);
        jFreeChartDroite.add(jFreeChartDroiteBas);


        // diag sur les note supperieurs a� la moyenne
        int inf=0;
        int sup=0;
        for(int i=0;i<donnee.length;i++){
            if(Double.valueOf(donnee[i][1])<= 10){
                inf++;
            }else{
                sup++;
            }
        }

        DefaultPieDataset data = new DefaultPieDataset();
        data.setValue("Note < 10",(double)inf);
        data.setValue("Note > 10",(double)sup);
      ChartPanel pc = new ChartPanel(ChartFactory.createPieChart( "Note supperieur a la moyenne", data, false/*legend?,true/*tooltips?, false/*URLs?));
        jFreeChartDroiteHaut.add(pc);

        int inf1=0;
        int sup1=0;
        for(int i=0;i<donnee.length;i++){
            if(Double.valueOf(donnee[i][1]) <= 10){
                inf1+=Double.valueOf(donnee[i][1])* Double.valueOf(donnee[i][2]);
            }else{
                sup1+=Double.valueOf(donnee[i][1])*Double.valueOf(donnee[i][2]);
            }
        }

        DefaultPieDataset data1 = new DefaultPieDataset();
        data1.setValue("Note < 10",(double)inf1);
        data1.setValue("Note > 10",(double)sup1);
        jFreeChartDroiteHaut.add(pc1);


        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String s="Profit1";
        for(int i=0;i<donnee.length;i++){
            s = "Profit" + (1+i);
            dataset.setValue(Double.valueOf(donnee[i][1]), s , (String)donnee[i][0]);
        }
        JFreeChart chart = ChartFactory.createBarChart3D( "Graphique des notes",
                "", "Note sur 20", dataset, PlotOrientation.VERTICAL, false, false, false );

        ChartPanel pc3 = new ChartPanel(chart);
        jFreeChartDroiteBas.add(pc3);

        affichage.add(infoGauche);
        affichage.add(jFreeChartDroite);
        centre.add(affichage);
        affichage.revalidate();
    }

	 */
	/**
	 * Cette m�thode permet d'am�liorer la lisibilit� du code. Elle g�n�re les graphiques sur les notes.
	 * @param donnees Un tableau de tableau, qui repr�sente la JTable.
	 * @return Retourne un JPanel qui contiendra 3 JFreeCharts.
	 */
	//public JPanel createurJfreeChart(Object[][] donnees){}
		/*// La partie droite contiendra : les JFreeChart

		// on ajoute les data d'un etudiant a� mettre dans une methode ...
		JPanel jFreeChartDroite = new JPanel();
		JPanel jFreeChartDroiteHaut = new JPanel();
		jFreeChartDroite.setLayout(new GridLayout(2,1));

		JPanel jFreeChartDroiteBas = new JPanel();
		jFreeChartDroiteBas.setLayout(new GridLayout(1,1));
		jFreeChartDroiteHaut.setLayout(new GridLayout(1,2));
		jFreeChartDroite.add(jFreeChartDroiteHaut);
		jFreeChartDroite.add(jFreeChartDroiteBas);


		// diag sur les note supperieurs a� la moyenne
		int inf=0;
		int sup=0;
		for(int i=0;i<donnees.length;i++){
			if( (Integer)donnees[i][1] <= 10){
				inf++;
			}else{
				sup++;
			}
		}

		DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("Note < 10",(double)inf);
		data.setValue("Note > 10",(double)sup);
		ChartPanel pc = new ChartPanel(ChartFactory.createPieChart( "Note supperieur a la moyenne", data, false/*legend?,truetooltips?, falseURLs?*///));
//		jFreeChartDroiteHaut.add(pc);
//
//		int inf1=0;
//		int sup1=0;
//		for(int i=0;i<donnees.length;i++){
//			if( (Integer)donnees[i][1] <= 10){
//				inf1+=(Integer)donnees[i][1]*(Integer)donnees[i][2];
//			}else{
//				sup1+=(Integer)donnees[i][1]*(Integer)donnees[i][2];
//			}
//		}
//
//		DefaultPieDataset data1 = new DefaultPieDataset();
//		data1.setValue("Note < 10",(double)inf1);
//		data1.setValue("Note > 10",(double)sup1);
////		ChartPanel pc1 = new ChartPanel(ChartFactory.createPieChart( "Note supperieur a la moyenne avec coeff", data1, false/*legend?*/,true/*tooltips?*/, false/*URLs?*/));
//		jFreeChartDroiteHaut.add(pc1);
//
//
//		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//		String s="Profit1";
//		for(int i=0;i<donnees.length;i++){
//			s = "Profit" + (1+i);
//			dataset.setValue((Integer)donnees[i][1], s , (String)donnees[i][0]);
//		}
//		JFreeChart chart = ChartFactory.createBarChart3D( "Graphique des notes",
//				"", "Note sur 20", dataset, PlotOrientation.VERTICAL, false, false, false );
//
//		ChartPanel pc3 = new ChartPanel(chart);
//		jFreeChartDroiteBas.add(pc3);
//
//		return jFreeChartDroite;
//	}*/


	/**
	 * Creer un JPanel qui sera afficher dans le JPanel "affichage".
	 * C'est le JPanel info-etudiant qui affichera toutes les informations personnelles sur l'etudiant, ainsi que sur sa scorlarite.
	 * @param listEtu Une liste d'ABBLLPP_Etudiant.
	 */
	/* private void createurPanelEditInfoEtudiant(List<ABBLLPP_Etudiant> listEtu){
        clearAffichage();
        //e = new Etudiant(3, "yoko", "misa", "yoko@gmail.com", "BEP porte-a-porte", "AB positif","lolo");


        int start = Integer.parseInt(tree.getSelectNode().getParent().toString().substring(0, 4));
        int end = Integer.parseInt(tree.getSelectNode().getParent().toString().substring(5));
        ABBLLPP_Promo promo = this.getPromoDao().findByStartYearEndYear(start, end);


        //Collection<ABBLLPP_Etudiant> listEtu = promo.getEtudiant();
        affichage = new JPanel();
        affichage.setLayout(new BoxLayout(affichage,BoxLayout.Y_AXIS));
        JPanel sousAffichage = new JPanel();
        sousAffichage.setLayout(new BoxLayout(sousAffichage,BoxLayout.Y_AXIS));
        Iterator<ABBLLPP_Etudiant> it= listEtu.iterator();
        while(it.hasNext()){
            sousAffichage.add(new StudentPanel((ABBLLPP_Etudiant) it.next(), this.etudiantDao, this.em, this.promoDao));
        }

        JScrollPane jScrollAffichage = new JScrollPane(sousAffichage);
        affichage.add(jScrollAffichage);
        centre.add(affichage, BorderLayout.CENTER);
        affichage.revalidate();
    }*/

	/**
	 * Constructeur utilis�e pour les tests.
	 * @param table 
	 */
	public Fenetre(ArrayList table){
		// on ajoute la structure de la fenetre
		listMortality = table;
		strutureBaseFenetre();
		System.out.println("rofl zedong");
		//createurPanelJTable();    // par defaut, on affiche cette vue la
		this.setContentPane(container);
		repaint();
		validate();
		System.out.println("fenetre cree !");
	}


	/**
	 * Cree une fenetre, et g�re ce qu'il doit etre affich� en son centre.
	 * C'est la fenetre principale, celle que l'utilisateur verra le plus.
	 */
	@SuppressWarnings("unchecked")


	/**
	 * Permet de gerer la largeur des colonnes de la Jtable
	 */
	public void setColumnSize(JTable table){
		FontMetrics fm = table.getFontMetrics(table.getFont());
		for (int i = 0 ; i < table.getColumnCount() ; i++)
		{
			int max = 0;
			for (int j = 0 ; j < table.getRowCount() ; j++)
			{
				int taille = fm.stringWidth((String)table.getValueAt(j,i));

				if (taille > max)
					max = taille;

			}
			String nom = (String)table.getColumnModel().getColumn(i).getIdentifier();
			int taille = fm.stringWidth(nom);
			if (taille > max)
				max = taille;
			table.getColumnModel().getColumn(i).setPreferredWidth(max+10);
		}
	}
	/**
	 * Action faites lorsque l'on clique sur un bouton
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		if(arg0.getSource() == valider){
			System.out.println(Integer.parseInt( offset.getText()));

			for (int i = 0; i < listMortality.size(); i++) {
				System.out.println(listMortality.get(i).getNom() +" " +choixTable.getSelectedItem());

				if(listMortality.get(i).getNom().equals(choixTable.getSelectedItem())){
					System.out.println(Integer.parseInt( offset.getText()));
					listMortality.get(i).appliqueOffset(Integer.parseInt( offset.getText()));
				}
			}

		}else if(arg0.getSource() == remiseAZero){
			for (int i = 0; i < listMortality.size(); i++) {
				if(listMortality.get(i).getNom().equals(choixTable.getSelectedItem())){
					listMortality.get(i).maz();
				}
			}


		}

		// Envoie 1 email a 1 �tudiant


		if(arg0.getSource() == "buttonViewAll"){
			System.out.println("click on buttonViewAll");
			this.createurPanelJTable(entetes,donnees);
		}


		// JMENU
		if(arg0.getSource() == toutSelectionner){
			System.out.println("click on toutSelectionner");
			tableau.setRowSelectionInterval(0, tableau.getRowCount()-1);
		}
		if(arg0.getSource() == toutDeselectionner){
			System.out.println("click on toutDeselectionner");
			tableau.clearSelection();
		}
		//*/


		if(arg0.getSource() == quitter){
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		/* if(tree.getSelectNode().toString().equals("Etudiant")){
                for(int i=0;i<tableau.getRowCount();i++){
                    ABBLLPP_Etudiant etudiant = etudiantDao.findById(Long.parseLong((String) tableau.getValueAt(i, 0)));
                    try{
                        em.getTransaction().begin();
                        etudiant.setNomEtu((String)tableau.getValueAt(i,2));
                        etudiant.setPrenomEtu((String)tableau.getValueAt(i,1));
                        etudiant.setSexe(stringToSexe((String)tableau.getValueAt(i,3)));
                        etudiant.setDateNaissance(stringToDate((String)tableau.getValueAt(i,4)));
                        etudiant.setOrigine((String)tableau.getValueAt(i,7));
                        etudiant.setEmailEtu((String)tableau.getValueAt(i,5));
                        etudiant.setGroup(Integer.parseInt((String)tableau.getValueAt(i,6)));
                        etudiantDao.update(etudiant);
                    }catch(Exception ex){
                        ex.printStackTrace();
                        em.getTransaction().rollback();
                    }finally{
                        em.getTransaction().commit();

                    }
                }
            }else if(tree.getSelectNode().toString().equals("Cours")){
                try{
                    em.getTransaction().begin();
                    for(int i=0;i<tableau.getRowCount();i++){
                        ABBLLPP_Cours cours = coursDao.findById(tableau.getValueAt(i, 0).toString().trim());
                        cours.setCoef(Double.valueOf((String)tableau.getValueAt(i,2)));
                        coursDao.update(cours);
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                    em.getTransaction().rollback();
                }finally{
                    em.getTransaction().commit();
                }
            }else{// update enseignant
                try{
                    em.getTransaction().begin();
                    for(int i=0;i<tableau.getRowCount();i++){
                        ABBLLPP_Enseignant enseignant = enseignantDao.findById(Long.parseLong(tableau.getValueAt(i, 0).toString()));
                        enseignant.setPrenomEns((String)tableau.getValueAt(i,1));
                        enseignant.setNomEns((String)tableau.getValueAt(i,2));
                        enseignant.setEmailEns((String)tableau.getValueAt(i,3));
                        enseignantDao.update(enseignant);
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                    em.getTransaction().rollback();
                }finally{
                    em.getTransaction().commit();
                }
            }
            tree.remplitJTable(tree.getSelectNode());
        }*/
		refrech();
	}


	public void refrech(){
		int indice=0;
		for (int i = 0; i < tree.getListNode().size(); i++) {
			if(tree.getListNode().get(i).equals(tree.getSelectNode())){
				indice=i;
			}
		}
		tree.setSelectNode(tree.getListNode().get(indice+1%tree.getListNode().size()));
		tree.remplitJTable(tree.getSelectNode());
		tree.setSelectNode(tree.getListNode().get(indice));
		tree.remplitJTable(tree.getSelectNode());

	}


	public ArrayList<MortalityTable> getListMortality() {
		return listMortality;
	}

	public void setListMortality(ArrayList<MortalityTable> listMortality) {
		this.listMortality = listMortality;
	}

	public JComboBox getChoixTable() {
		return choixTable;
	}

	public void setChoixTable(JComboBox choixTable) {
		this.choixTable = choixTable;
	}

	public JTextField getAge() {
		return age;
	}

	public void setAge(JTextField age) {
		this.age = age;
	}

	public JTextField getTerm() {
		return term;
	}

	public void setTerm(JTextField term) {
		this.term = term;
	}

	public JTextField getTechnicalRate() {
		return technicalRate;
	}

	public void setTechnicalRate(JTextField technicalRate) {
		this.technicalRate = technicalRate;
	}

	public JTextField getAmount() {
		return amount;
	}

	public void setAmount(JTextField amount) {
		this.amount = amount;
	}

	public JTextField getPayment() {
		return payment;
	}

	public void setPayment(JTextField payment) {
		this.payment = payment;
	}

	public JTextField getRatio() {
		return ratio;
	}

	public void setRatio(JTextField ratio) {
		this.ratio = ratio;
	}

	public JPanel getDroite() {
		return droite;
	}

	public void setDroite(JPanel droite) {
		this.droite = droite;
	}




	/**
	 * Convertit un String en SEXE
	 * @param s la String a convertir
	 * @return le SEXE retourn�
	 */


	/**
	 * Convertit un String en Date.
	 * @param d String avec un format de type "19/11/2011"
	 * @return Un objet Date. En cas de mauvais formatage, on obtiendra Date(0)
	 */





}