import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;


public class Fereastra_Principala extends JFrame{
	
    DatePartajate dp;
	private Fereastra_Principala() {
		super();
		dp = DatePartajate.getInstance();
		
		
		realizareMeniu();
		
		this.setPreferredSize(new Dimension(500, 500));//modificat
		this.pack();//modificat
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Gestiune orar");
		this.setVisible(true);
		
	}
	
	private void realizareMeniu(){
		JMenuBar BaraJava = new JMenuBar();
		this.setJMenuBar(BaraJava);
		//BaraJava.setPreferredSize(new Dimension(100, 25));
		
		JMenu file = new JMenu("File");
		file.setPreferredSize(new Dimension(60, 25));
		BaraJava.add(file);
		
		JMenu optiuni = new JMenu("Optiuni");
		optiuni.setPreferredSize(new Dimension(60, 25));
		BaraJava.add(optiuni);
		
		JMenuItem orarProfesor = new JMenuItem("Orar Profesor");
		optiuni.add(orarProfesor);
		
		JMenuItem orarStudent = new JMenuItem("Orar Student");
		optiuni.add(orarStudent);
		
		JMenuItem orarDisciplina = new JMenuItem("Orar Disciplina");
		optiuni.add(orarDisciplina);
		
		JMenuItem open = new JMenuItem("Open File..");
		file.add(open);

		open.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(".");
				Component parent = null;
				int returnVal = chooser.showOpenDialog(parent);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		        	
	               System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
	               dp.setPathOrar(chooser.getSelectedFile().getAbsolutePath());
	            } 

			}
			
		});
		
		orarStudent.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
//				FereastraOS fereastraStudenti = new FereastraOS(this);
//				fereastraStudenti.FereastraNoua();
				 new FereastraOS(Fereastra_Principala.this);
			}
			
		});
		
		orarProfesor.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
//				FereastraOP fereastraProfesori = new FereastraOP();
//				fereastraProfesori.FereastraNoua();
				 new FereastraOP(Fereastra_Principala.this);
			}
			
		});
		
		orarDisciplina.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
//				FereastraDp fereastraDiscipline = new FereastraDp();
//				fereastraDiscipline.FereastraNoua();
				 new FereastraDp(Fereastra_Principala.this);
			}
			
		});
		
		JMenuItem exit = new JMenuItem("Exit");
		file.add(exit);
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				 new Fereastra_Principala();
				
			}
		});	
	}
}
