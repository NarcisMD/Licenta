import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Fereastra extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textAnul;
	private JTextField textSubgrupa;
	private JTextField textField3;
	private JTextField textGrupa;
	private String ValoareAn = "1";

	public static void FereastraNoua()  {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fereastra frame = new Fereastra();
					frame.setVisible(true);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	
	String[] aniFacultate = { "1", "2", "3", "m1", "m2" };
	
	
	public Fereastra() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		JLabel Descriere = new JLabel("Afisarea orarului ");
		Descriere.setBounds(200, 30, 100, 20);
		contentPane.add(Descriere);
		
		JLabel lblAnul = new JLabel("Anul:");
		lblAnul.setBounds(30, 100, 50, 20);
		contentPane.add(lblAnul);
		
		JLabel lblGrupa = new JLabel("Grupa:");
		lblGrupa.setBounds(30, 150, 50, 20);
		contentPane.add(lblGrupa);

		JLabel lblSubgrupa = new JLabel("Subgrupa:");
		lblSubgrupa.setBounds(30, 200, 50, 20);
		contentPane.add(lblSubgrupa);

//		JLabel lblProducator = new JLabel("Producator:");
//		lblProducator.setBounds(10, 107, 69, 14);
//		contentPane.add(lblProducator);

		
//		textAnul = new JTextField();
//		textAnul.setBounds(100, 100, 100, 30);
//		contentPane.add(textAnul);
//		textAnul.setColumns(10);
		
		textGrupa = new JTextField();
		
		textGrupa.setBounds(100, 150, 100, 30);
		contentPane.add(textGrupa);
		textGrupa.setColumns(10);

		textSubgrupa = new JTextField();
		textSubgrupa.setBounds(100, 200, 100, 30);
		contentPane.add(textSubgrupa);
		textSubgrupa.setColumns(10);
		
		
		JComboBox anFacultate = new JComboBox(aniFacultate);
		JLabel lblText = new JLabel();
		anFacultate.setSelectedIndex(0);
		anFacultate.setBounds(100, 100, 100, 20);
		contentPane.add(anFacultate);
		//add(anFacultate);
		//add(lblText);
		
		anFacultate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(e.getSource() == anFacultate) {
						JComboBox Anul = (JComboBox)e.getSource();
						ValoareAn = (String)Anul.getSelectedItem();
							
					}
					
				}catch (NumberFormatException err) {

					System.out.println("Dimesion must be number");
				}
				
			}
		});
		
//		JSpinner spinner2 = new JSpinner();
//		spinner2.setBounds(100, 250, 29, 20);
//		spinner2.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
//		contentPane.add(spinner2);

		JButton btnGo = new JButton("GO");
		btnGo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//String sAn = textAnul.getText();
					//int pret = (int) spinner2.getValue();
					String sGrupa= textGrupa.getText();
					String sSubGrupa = textSubgrupa.getText();
					
					String sAn  = ValoareAn;
					System.out.println("Anul : " + ValoareAn + " Grupa :" + sGrupa + " Subgrupa : " + sSubGrupa);
					
					//System.out.println(dim + " " + tip+ " "  + " " + prod);
					//faci automobil bagi in lista
				} catch (NumberFormatException err) {

					System.out.println("Dimesion must be number");
				}
			}
		});
		btnGo.setBounds(200, 350, 89, 23);
		contentPane.add(btnGo);
	}





}
