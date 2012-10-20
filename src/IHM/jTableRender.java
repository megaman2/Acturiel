package IHM;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class jTableRender extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		/**
		 * Fixer la couleur de fond de la première colonne en jaune
		 */
		if (column == 0) {
			Color clr = new Color(255, 255, 200);
			component.setBackground(clr);
		} else {
			Color clr = new Color(255, 255, 255);
			component.setBackground(clr);
		}
		/**
		 * Colorier les cellules en orange si le montant est négatif
		 */
		for (int i = 0; i < column; i++) {
			Object o = table.getValueAt(row, i);
			if (o != null && component instanceof JLabel) {
				JLabel label = (JLabel) component;
				if (label.getText().contains("- ")) {
					Color clr = new Color(255, 226, 198);
					component.setBackground(clr);
				}else if (label.getText().contains("valide")) {
					Color clr = new Color(255, 0, 0);
					component.setBackground(clr);
				}else if (label.getText().contains("   ")) {
					Color clr = new Color(102,204 , 204);
					component.setBackground(clr);
				}else if (label.getText().contains("OK")) {
					Color clr = new Color(51,254 , 51);
					component.setBackground(clr);
				}else if (label.getText().contains("ERREUR")) {
					Color clr = new Color(255,0 ,0);
					component.setBackground(clr);
				}else if (label.getText().length() <= 2  && label.getText().length() != 0 && column > 3) {
					Color clr = new Color(51,204 ,0);
					component.setBackground(clr);
				}
			}
		}
			/**
			 * Center le texte pour la colonne 0 et aligner le texte à droite pour les autres colonnes
			 */
			//            if (column == 0) {
			//                label.setHorizontalAlignment(CENTER);
			//            } else {
			//                label.setHorizontalAlignment(RIGHT);
			//            }
			//        }
			return component;
		}
	}