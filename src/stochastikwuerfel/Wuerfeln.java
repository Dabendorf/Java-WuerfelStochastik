package stochastikwuerfel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

/**
 * Dies ist die einzige Klasse des Wuerfelstochastikprogramms, welches sowohl interne Algorithmen als auch die Grafik steuert.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Wuerfeln {
	
	private JFrame frame1 = new JFrame("Stochastik mit Würfeln");
	private NumberFormat format1 = NumberFormat.getInstance(); 
	private NumberFormat format2 = NumberFormat.getInstance();
	private NumberFormat format3 = NumberFormat.getInstance();
    private NumberFormatter formatter1 = new NumberFormatter(format1); 
    private NumberFormatter formatter2 = new NumberFormatter(format2);
    private NumberFormatter formatter3 = new NumberFormatter(format3);
    private JFormattedTextField tfanzwuerfe = new JFormattedTextField(formatter1);
    private JFormattedTextField tfwuerfelgroesse = new JFormattedTextField(formatter2);
    private JFormattedTextField tfgesuchtezahl = new JFormattedTextField(formatter3);
    private JLabel labelAnzahlWuerfe = new JLabel("Wurfzahl:");
    private JLabel labelGroesse = new JLabel("Würfelgröße:");
    private JLabel labelSuche = new JLabel("Gesuchte Zahl:");
    private JLabel labelOptimalwert = new JLabel("Optimalwert:");
    private JLabel labelHaeufigkeit = new JLabel("Häufigkeit:");
    private JLabel labelAbweichung = new JLabel("Abweichung:");
    private JLabel labelOptimal = new JLabel();
    private JLabel labelMenge = new JLabel();
    private JLabel labelAbweichungWert = new JLabel();
	private JButton buttonWuerfeln = new JButton("Würfeln");
	private JList<Integer> wurfliste = new JList<Integer>();
    private DefaultListModel<Integer> wurflisteModel = new DefaultListModel<Integer>();
    private JScrollPane wurflisteScrollPane = new JScrollPane(wurfliste);
    private JSeparator separator1 = new JSeparator(SwingConstants.VERTICAL);
    private JSeparator separator2 = new JSeparator(SwingConstants.VERTICAL);
    private int anzahl;
    private int wuerfelgroesse;
    private int gesuchtezahl;
	
	public Wuerfeln() {
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(550,115);
		frame1.setResizable(false);
		Container cp = frame1.getContentPane();
		cp.setLayout(null);
		
		labelAnzahlWuerfe.setBounds(10, 10, 100, 20);
		cp.add(labelAnzahlWuerfe);
		labelGroesse.setBounds(10, 35, 100, 20);
		cp.add(labelGroesse);
		labelSuche.setBounds(10, 60, 100, 20);
		cp.add(labelSuche);
		tfanzwuerfe.setBounds(110, 10, 80, 20);
		tfanzwuerfe.setHorizontalAlignment(SwingConstants.RIGHT);
		tfanzwuerfe.setText("1000");
		cp.add(tfanzwuerfe);
		tfwuerfelgroesse.setBounds(110, 35, 80, 20);
		tfwuerfelgroesse.setHorizontalAlignment(SwingConstants.RIGHT);
		tfwuerfelgroesse.setText("6");
		cp.add(tfwuerfelgroesse);
		tfgesuchtezahl.setBounds(110, 60, 80, 20);
		tfgesuchtezahl.setHorizontalAlignment(SwingConstants.RIGHT);
		tfgesuchtezahl.setText("6");
		cp.add(tfgesuchtezahl);
		buttonWuerfeln.setBounds(190, 10, 80, 70);
		buttonWuerfeln.setMargin(new Insets(2, 2, 2, 2));
		buttonWuerfeln.setVisible(true);
		buttonWuerfeln.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				wuerfeln();
			}
		});
		cp.add(buttonWuerfeln);
		separator1.setBounds(270, 5, 20, 80);
		separator1.setBackground(Color.black);
	    cp.add(separator1);
	    labelOptimalwert.setBounds(290, 10, 100, 20);
		cp.add(labelOptimalwert);
		labelHaeufigkeit.setBounds(290, 35, 100, 20);
		cp.add(labelHaeufigkeit);
		labelAbweichung.setBounds(290, 60, 100, 20);
		cp.add(labelAbweichung);
		labelOptimal.setBounds(350, 10, 100, 20);
		labelOptimal.setHorizontalAlignment(SwingConstants.RIGHT);
		cp.add(labelOptimal);
		labelMenge.setBounds(350, 35, 100, 20);
		labelMenge.setHorizontalAlignment(SwingConstants.RIGHT);
		cp.add(labelMenge);
		labelAbweichungWert.setBounds(350, 60, 100, 20);
		labelAbweichungWert.setHorizontalAlignment(SwingConstants.RIGHT);
		cp.add(labelAbweichungWert);
		separator2.setBounds(450, 5, 20, 80);
		separator2.setBackground(Color.black);
	    cp.add(separator2);
	    wurfliste.setModel(wurflisteModel);
	    wurflisteScrollPane.setBounds(465, 10, 80, 75);
	    cp.add(wurflisteScrollPane);
		
	    format1.setGroupingUsed(false);
		format1.setMaximumIntegerDigits(7);
		formatter1.setAllowsInvalid(false);
		format2.setGroupingUsed(false);
		format2.setMaximumIntegerDigits(9);
		formatter2.setAllowsInvalid(false);
		format3.setGroupingUsed(false);
		format3.setMaximumIntegerDigits(9);
		formatter3.setAllowsInvalid(false);
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
	}
	
	/**
	 * Diese Methode liest die Zahlen ein und leitet in die Berechnungsmethode weiter.
	 */
	private void wuerfeln() {
		try {
			wurflisteModel.removeAllElements();
			anzahl = Integer.parseInt(tfanzwuerfe.getText());
			wuerfelgroesse = Integer.parseInt(tfwuerfelgroesse.getText());
			gesuchtezahl = Integer.parseInt(tfgesuchtezahl.getText());
			if(wuerfelgroesse > 0) {
				if(anzahl > 0) {
					if(gesuchtezahl > 0) {
						if(gesuchtezahl <= wuerfelgroesse) {
							berechnen();
						} else {
							JOptionPane.showMessageDialog(null, gesuchtezahl+" ist nicht Teil eines "+wuerfelgroesse+"-seitigen Würfels.\nBitte wähle eine andere Zahl aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Bitte gib eine positive gesuchte Würfelzahl ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Bitte wähle eine positive Anzahl von Würfen aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, wuerfelgroesse+" Seiten sind zu klein für einen Würfel.\nBitte wähle mehr als eine Seite.", "Fehler", JOptionPane.ERROR_MESSAGE);
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Du hast falsche Werte eingetragen."+System.getProperty("line.separator")+"Wenn Du dies nicht korrigierst"+System.getProperty("line.separator")+"bekommst Du kein Ergebnis!", "Falscheingabe", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * Diese Methode wuerfelt mit der angegebenen Groesse x mal.<br>
	 * Dann gibt sie aus wie oft die gesuchte Zahl vorkommt und wie gross die statistische Abweichung ist.<br>
	 * Abschliessend schreibt sie alle Werte in die Liste.
	 */
	private void berechnen() {
		Random wuerfel = new Random();
		int zaehler = 0;
		for(int n=0;n<anzahl;n++) {
			int zahl = wuerfel.nextInt(wuerfelgroesse)+1;
			wurflisteModel.addElement(zahl);
			if(zahl == gesuchtezahl) {
				zaehler++;
			}
		}
		
		double statistik = anzahl*1.00/wuerfelgroesse*1.00;
		statistik = Math.round(1000.0*statistik)/1000.0;
		if(statistik%1==0) {
			int statistikint = (int)statistik;
			labelOptimal.setText(statistikint+"");
		} else {
			labelOptimal.setText(statistik+"");
		}
		labelMenge.setText(zaehler+"");
		
		double abweichung = 100-(100*(zaehler*1.00/statistik*1.00));
		if(abweichung < 0.0) {
			abweichung = -abweichung;
		}
		abweichung = Math.round(100.0*abweichung)/100.0;
		labelAbweichungWert.setText(abweichung+"%");
	}

	public static void main(String[] args) {
		new Wuerfeln();
	}
}