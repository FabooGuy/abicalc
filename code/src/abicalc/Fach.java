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

public class Fach extends Component implements java.io.Serializable{		//Datenstruktur aus Noten, die ein UI-ELement ist
	
	private static final long serialVersionUID = 1L;
	
	String name;
	LinkedList<Note>  notenliste;
	double fachSchnitt;
	
	//Panel mit Noten
	public JPanel panelHaupt = new JPanel();		
	
	//Label mit Fachschnitt
	public JLabel labelFachSchnitt = new JLabel();		
	
	//Label mit Fachname
	JLabel labelFachName = new JLabel();
	
	//Textfeld zum Fachnamen �ndern im JDialog
	JTextField txtFachname = new JTextField();	
	
	public Fach(String s, JPanel jpnl){
		
		//Verkette Liste aus Noten und Name des Fachs mithilfe des �bergebenen Strings
		name = s;
		notenliste = new LinkedList<Note>();		
		
		//komplette UI des einzelnen Fachs wird geladen
		uiLaden(jpnl);
		
	}
	
	public double fachSchnittBerechnen(){		
		//Berechnet den Schnitt eines einzelnen Faches
		
		double schnitt;
		//Summe sind alle Noten mit deren Gewichtungen eingerechnet
		double summe = 0;		
		//Summe der Gewichtungen
		double sGewichtung = 0;		
		
		//f�r jede Note wird deren Wert mit ihrer Gewichtung multipliziert
		for(int i = 0; i < notenliste.size(); i++){		
			summe = summe + (notenliste.get(i).gewichtung * notenliste.get(i).punkte);
			sGewichtung = sGewichtung + notenliste.get(i).gewichtung;
		}
		//eigentliche Durchschnittsberechnung
		schnitt = summe / sGewichtung;	
		return schnitt;
	}
	
	public void uiLaden(JPanel jpnl){
		//Panel mit Fachname und Buttons und Layout einer Tabelle mit 1 Zeile
		JPanel panel = new JPanel();	
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		//Label mit F�chernamen
		labelFachName.setText(name);
		panel.add(labelFachName);
		
		//F�cherdurchschnitt
		fachSchnittAktualisieren();
		panel.add(labelFachSchnitt);
		
		//Button zum Noten bearbeiten
		JButton btnFachname = new JButton("Fachname bearbeiten...");
		panel.add(btnFachname);
				
		//Button-Klick
		btnFachname.addActionListener(new java.awt.event.ActionListener() {		
	        public void actionPerformed(java.awt.event.ActionEvent e) {
	          
	        	//ruft den JDialog zum Bearbeiten des Fachnames auf
	        	dialogFachnameOeffnen();
	        	
	        	//Stellt sicher, dass UI aktualisiert wird
	        	panel.validate();
	    		panel.repaint();
	        }
	    });
		
		
		
		//Button zum Noten bearbeiten
		JButton btnNote = new JButton("Noten bearbeiten...");
		panel.add(btnNote);
		
		//Button-Klick
		btnNote.addActionListener(new java.awt.event.ActionListener() {		
	        public void actionPerformed(java.awt.event.ActionEvent e) {
	          
	        	//ruft den JDialog zum Bearbeiten der Noten auf
	        	dialogNoteOeffnen();		
	        	
	        	//Stellt sicher, dass UI aktualisiert wird
	        	panel.validate();
	    		panel.repaint();
	        }
	    });
			
		
		//Panel wird hinzugef�gt
		jpnl.add(panel);
				
	}
	
	
	
	//Dialog um den Fachnamen zu �ndern
	public void dialogFachnameOeffnen() {
		//Pop-up Fenster
   	 	JDialog fachJDialog = new JDialog();		
        fachJDialog.setTitle(name+": Name bearbeiten");
        fachJDialog.setSize(400,200);
        fachJDialog.setModal(true);
		
        //Panel, das den kompletten Inhalt enth�lt
        JPanel panelDialog = new JPanel();	
        
        //Input f�r Notenname		
        txtFachname.setText("Neuer Fachname...");
        txtFachname.setColumns(10);
        panelDialog.add(txtFachname);
        //Note hinzuf�gen Button
      	JButton buttonSpeichern = new JButton("Speichern");
      	panelDialog.add(buttonSpeichern);
        
      	//Speichern des Namen bei Button-Klick
      	buttonSpeichern.addActionListener(new java.awt.event.ActionListener() {		
	        public void actionPerformed(java.awt.event.ActionEvent e) {
	        	fachNameAktualisieren();
	        }
	    });
      	
      	
        //Sorgt f�r Aktualisierung des Fachschnitts nach Schlie�en des JDialogs
        fachJDialog.addWindowListener(new WindowAdapter() {
        	@Override
    	    public void windowClosed(WindowEvent e) {
    	    	fachNameAktualisieren();
    	    }
    	});
        
        //Alles wird zum fenster hinzugef�gt
        fachJDialog.add(panelDialog);
        
        //UI wird geupdated
        panelDialog.validate();	
        panelDialog.repaint();
        
        //Dialog wird sichtbar gemacht
        fachJDialog.setVisible(true);	
        
	}
	
	
	public void fachNameAktualisieren(){
		//Fachname wird mit String aus JDialog aktualisiert
		this.name=txtFachname.getText();
	}
	
	//Dialog um Noten zu bearbeiten
	public void dialogNoteOeffnen() {
		
		//Pop-up Fenster
   	 	JDialog fachJDialog = new JDialog();		
        fachJDialog.setTitle(name+":Noten bearbeiten");
        fachJDialog.setSize(500,400);
        fachJDialog.setModal(true);
        
        //Panel, das den kompletten Inhalt enth�lt
        JPanel panelDialog = new JPanel();		
        panelDialog.setLayout(new BorderLayout(5, 5));
        fachJDialog.add(panelDialog);
        panelDialog.setBackground(Color.LIGHT_GRAY);
        
        //Panel mit Titel-Label
        JPanel panelTitel = new JPanel();		
        JLabel titel = new JLabel(name+"     ");
        panelTitel.add(titel);
        
        JPanel panelHinzufuegen = new JPanel();
        
        //Input f�r Notenname
        JTextField txtName = new JTextField();			
        txtName.setText("Note");
        panelHinzufuegen.add(txtName);
		txtName.setColumns(10);
		//Input f�r Punktzahl
		JTextField txtNote = new JTextField();			
        txtNote.setText("Punktzahl");
        panelHinzufuegen.add(txtNote);
		txtNote.setColumns(7);
		//Input f�r Gewichtung
		JTextField txtGewichtung = new JTextField();			
        txtGewichtung.setText("Gewichtung");
        panelHinzufuegen.add(txtGewichtung);
		txtGewichtung.setColumns(7);
		//Note hinzuf�gen Button
		JButton buttonPlus = new JButton("+");
		panelHinzufuegen.add(buttonPlus);
		JPanel panelOben = new JPanel();
		panelOben.add(panelTitel);
		
		panelOben.add(panelHinzufuegen);
		
		//Label, Textfelder und Button werden zum oberen Teil des Dialogs hinzugef�gt
		panelDialog.add(panelOben, BorderLayout.PAGE_START);		
        
		//�bercontainer f�r einzelne Noten
        JPanel panelNoten = new JPanel();		
        
        //Container der scrollbar wird und Noten enth�lt
        JScrollPane scrollPane = new JScrollPane (panelHaupt); 
        //bekommt nur Scrollbar, wenn genug Elemente vorhanden sind
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);		
        scrollPane.setPreferredSize(new Dimension(460, 300));
        
        panelNoten.add(scrollPane);
        
        //sorgt f�r vertikale Anordnung der Elemente
        GridLayout gl = new GridLayout(0, 1, 0, 10);
        panelHaupt.setLayout(gl);
		
        //UI-Aktualisierung
        panelDialog.validate();		
        panelDialog.repaint();
        
        //Scrollbarer Bereich f�r einzelne Noten wird hinzugef�gt
        panelDialog.add(panelNoten, BorderLayout.CENTER);	
        
        //Code f�rs Noten hinzuf�gen
        buttonPlus.addActionListener(new java.awt.event.ActionListener() {		
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
        
        //Sorgt f�r Aktualisierung des Fachschnitts nach Schlie�en des JDialogs
        fachJDialog.addWindowListener(new WindowAdapter() {
        	@Override
    	    public void windowClosed(WindowEvent e) {
    	    	fachSchnittAktualisieren();
    	    }
    	});
        
        //UI wird geupdated
        panelDialog.validate();	
        panelDialog.repaint();
        
        //Dialog wird sichtbar gemacht
        fachJDialog.setVisible(true);		
	}

	//Updated die UI mit aktuellem Fachschnitt
		public void fachSchnittAktualisieren(){
			//Fachschnitt wird berechnet und gerundet
			this.fachSchnitt = Abicalc.runden(this.fachSchnittBerechnen());
			//Label wird geupdated
			this.labelFachSchnitt.setText("(Schnitt: "+String.valueOf(fachSchnitt)+" Punkte)");
		}
	
	
}
