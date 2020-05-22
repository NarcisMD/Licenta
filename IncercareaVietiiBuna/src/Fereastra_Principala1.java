import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Fereastra_Principala1 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame frame;


	public static void main(String[] args) {
		Fereastra_Principala1 frame = new Fereastra_Principala1();
		frame.setVisible(true);
		frame.pack();
	}

	public Fereastra_Principala1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		JPopupMenu BaraJava = new JPopupMenu();
		contentPane.setComponentPopupMenu(BaraJava);
		BaraJava.setPreferredSize(new Dimension(100, 25));
		
		JMenu file = new JMenu("File");
		file.setPreferredSize(new Dimension(60, 25));
		BaraJava.add(file);
		
		JMenu optiuni = new JMenu("Optiuni");
		optiuni.setPreferredSize(new Dimension(60, 25));
		BaraJava.add(optiuni);
		
		//this.setMenuBar(BaraJava);
		
		JMenuItem orarProfesor = new JMenuItem("OrarProfesor");
		optiuni.add(orarProfesor);
		JMenuItem orarStudent = new JMenuItem("OrarStudent");
		optiuni.add(orarStudent);
		
		JMenuItem open = new JMenuItem("Open File..");
		file.add(open);

		open.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				Component parent = null;
				int returnVal = chooser.showOpenDialog(parent);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
	               System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
	            } 

			}
			
		});
		
		orarProfesor.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Fereastra fereastraStudenti = new Fereastra();
				
			}
			
		});
		
		JMenuItem exit = new JMenuItem("Exit");
		file.add(exit);
	}

}