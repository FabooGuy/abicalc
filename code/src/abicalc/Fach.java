package abicalc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Fach extends Component implements java.io.Serializable{//Datenstruktur aus Noten, die ein UI-ELement ist
	
	private static final long serialVersionUID = 1L;
	
	String name;
	LinkedList<Note>  notenliste;
	double fachSchnitt;
	
	public JPanel panelHaupt = new JPanel();		//Panel mit Noten
	
	public JLabel lbl_fs = new JLabel();		//Label mit Fachschnitt
	
	public Fach(String s, JPanel jpnl){ //Konstruktor
		
		//Verkette Liste aus Noten und Name des Fachs mithilfe des �bergebenen Strings
		name = s;
		notenliste = new LinkedList<Note>();		
		
		uiLaden(jpnl);
		
	}
	
	public double fachSchnittBerechnen(){		//Berechnet den Schnitt eines einzelnen Faches
		double schnitt;
		double summe = 0;		//Summe sind alle Noten mit deren Gewichtungen eingerechnet
		double sGewichtung = 0;		//Summe der Gewichtungen
		for(int i = 0; i < notenliste.size(); i++){		//f�r jede Note wird deren Wert mit ihrer Gewicchtung multipliziert
			summe = summe + (notenliste.get(i).gewichtung * notenliste.get(i).punkte);
			sGewichtung = sGewichtung + notenliste.get(i).gewichtung;
		}
		schnitt = summe / sGewichtung;	
		return schnitt;
	}
	
	public void uiLaden(JPanel jpnl){
		//Panel mit Fachname und Button und Layout einer Tabelle mit 1 Zeile
		JPanel panel = new JPanel();	
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		
		//Label mit F�chernamen
		JLabel lbl_n = new JLabel("   "+name);
		panel.add(lbl_n);
		
		//F�cherdurchschnitt
		//fachSchnitt= Abicalc.runden(fachSchnittBerechnen());
		//lbl_fs.setText(String.valueOf(fachSchnitt));
		fachSchnittAktualisieren();
		panel.add(lbl_fs);
		
		//Button zum Noten bearbeiten
		JButton btn = new JButton("Noten bearbeiten...");
		btn.setBounds(641, 10, 113, 23);
		panel.add(btn);
		
		btn.addActionListener(new java.awt.event.ActionListener() {		//Code f�rs Fach hinzuf�gen
	        public void actionPerformed(java.awt.event.ActionEvent e) {
	          
	        	dialogOeffnen();		//ruft den JDialog zum Bearbeiten der Noten auf
	        	
	        	//Stellt sicher, dass UI aktualisiert wird
	        	panel.validate();
	    		panel.repaint();
	        }
	    });
			
		
		//Panel wird hinzugef�gt
		jpnl.add(panel);
				
	}
	
	public void fachSchnittAktualisieren(){
		this.fachSchnitt = Abicalc.runden(this.fachSchnittBerechnen());
		this.lbl_fs.setText(String.valueOf(fachSchnitt));
	}
	
	
	
	public void dialogOeffnen() {

   	 	JDialog fachJDialog = new JDialog();		//Pop-up Fenster
        fachJDialog.setTitle(name+" bearbeiten");
        fachJDialog.setSize(500,400);
        fachJDialog.setModal(true);
        
        JPanel panelDialog = new JPanel();		//Enth�lt alles
        panelDialog.setLayout(new BorderLayout(5, 5));
        fachJDialog.add(panelDialog);
        panelDialog.setBackground(Color.LIGHT_GRAY);
        
        JPanel panelTitel = new JPanel();		//Panel mit Titel-Label
        JLabel titel = new JLabel(name+"                  ");//Titel-Label
        panelTitel.add(titel);
        
 
        JPanel panelHinzufuegen = new JPanel();
        
        JTextField txtName = new JTextField();			//HJ Input f�r Notenname
        txtName.setText("Note");
        panelHinzufuegen.add(txtName);
		txtName.setColumns(10);
		JTextField txtNote = new JTextField();			//HJ Input f�r Notenname
        txtNote.setText("Punktzahl");
        panelHinzufuegen.add(txtNote);
		txtNote.setColumns(7);
		JTextField txtGewichtung = new JTextField();			//HJ Input f�r Notenname
        txtGewichtung.setText("Gewichtung");
        panelHinzufuegen.add(txtGewichtung);
		txtGewichtung.setColumns(7);
		JButton buttonPlus = new JButton("+");		//HJ Note hinzuf�gen Button
		panelHinzufuegen.add(buttonPlus);
		JPanel panelOben = new JPanel();
		panelOben.add(panelTitel);
		panelOben.add(panelHinzufuegen);
		
		panelDialog.add(panelOben, BorderLayout.PAGE_START);		//Label, Textfelder und Button werden zum oberen Teil des Dialogs hinzugef�gt
        
        
        
        JPanel panelNoten = new JPanel();		//�bercontainer f�r einzelne Noten
        
        
        //Container der scrollbar wird und Noten enth�lt
        JScrollPane scrollPane = new JScrollPane (panelHaupt); 
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);		//bekommt nur Scrollbar, wenn genug Elemente vorhanden sind
        scrollPane.setPreferredSize(new Dimension(460, 300));
        
        panelNoten.add(scrollPane);
        
        GridLayout gl = new GridLayout(0, 1, 0, 10);	//sorgt f�r vertikale Anordnung der Elemente
        panelHaupt.setLayout(gl);
		
        
        panelDialog.validate();		//UI-Aktualisierung
        panelDialog.repaint();
        
        
        panelDialog.add(panelNoten, BorderLayout.CENTER);	//Scrollbarer Bereich f�r einzelne Noten wird hinzugef�gt
        
        
        buttonPlus.addActionListener(new java.awt.event.ActionListener() {		//Code f�rs Noten hinzuf�gen
	        public void actionPerformed(java.awt.event.ActionEvent e) {
	          
	        	//Note wird mithilfe der Daten aus den Textfeldern erzeugt
	        	Note n = new Note(Double.parseDouble(txtGewichtung.getText()), txtName.getText(), Integer.parseInt(txtNote.getText()), panelHaupt);	
	        	//Note wird zur LinkedList hinzugef�gt
	        	notenliste.add(n);
	        	//UI wird aktualisiert
	        	panelDialog.validate();
	        	panelDialog.repaint();
	    		Abicalc.setzeGesamtSchnitt(Abicalc.getGesamtSchnitt(), Abicalc.punkteZuNote(Abicalc.getGesamtSchnitt()));		//Aktualisieren des Gesamtschnitts
	        }
	    });
        
        fachJDialog.addWindowListener(new WindowAdapter() {
        	@Override
    	    public void windowClosed(WindowEvent e) {
    	    	fachSchnittAktualisieren();
    	    }
    	});
        
        panelDialog.validate();	//UI wird geupdated
        panelDialog.repaint();
        fachJDialog.setVisible(true);		//Dialog wird sichtbar gemacht
	}

	
	
	
}
