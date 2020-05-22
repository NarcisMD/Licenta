import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FereastraDp extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DatePartajate dp = DatePartajate.getInstance();
	private JTextField textNumeDisciplina;
	private JTextField textSubgrupa;
	private JTextField textField3;
	private JTextField textGrupa;
	
	private String ValoareAn = "I";
	private String ValoareSectie = "-";
	private String sAn = "I";
	protected String sSectie;
	
	
	String[] aniFacultate = { "I", "II", "III", "m1", "m2" };
	String[] sectiiFacultate = { "-","IR", "IE", "IA"};
	
	public FereastraDp(JFrame parent) {
		super(parent,"Fereastra dp", true);
		constructGUI();
		this.setPreferredSize(new Dimension(500,500));
		pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		
	}
	private void constructGUI() {
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
		
		JLabel lblSectie = new JLabel("Sectia:");
		lblSectie.setBounds(30, 150, 50, 20);
		contentPane.add(lblSectie);
		
		JLabel lblNumeDisciplina = new JLabel("Disciplina:");
		lblNumeDisciplina.setBounds(30, 200, 50, 20);
		contentPane.add(lblNumeDisciplina);
		

		textNumeDisciplina = new JTextField();
		textNumeDisciplina.setBounds(100, 200, 100, 30);
		contentPane.add(textNumeDisciplina);
		textNumeDisciplina.setColumns(10);
		
		
		JComboBox anFacultate = new JComboBox(aniFacultate);
		JLabel lblText = new JLabel();
		anFacultate.setSelectedIndex(0);
		anFacultate.setBounds(100, 100, 100, 20);
		contentPane.add(anFacultate);

		
		anFacultate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(e.getSource() == anFacultate) {
						JComboBox Anul = (JComboBox)e.getSource();
						ValoareAn = (String)Anul.getSelectedItem();
						sAn =(String) Anul.getSelectedItem();
						dp.setsAn(sAn);	
					}
					
				}catch (NumberFormatException err) {

					System.out.println("Dimesion must be number");
				}
				
			}
		});
		
		JComboBox sectieFacultate = new JComboBox(sectiiFacultate);
		sectieFacultate.setSelectedIndex(0);
		sectieFacultate.setBounds(100, 150, 100, 20);
		contentPane.add(sectieFacultate);
		
		sectieFacultate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(e.getSource() == sectieFacultate) {
						JComboBox sectia = (JComboBox)e.getSource();
						ValoareSectie = (String)sectia.getSelectedItem();
						sSectie =(String) sectia.getSelectedItem();
						dp.setSectie(sSectie);	
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

		JButton btnGo = new JButton("Orar Disciplina");
		btnGo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					sAn = ValoareAn;
					sSectie = ValoareSectie;
					
					String sNumeDisciplina = textNumeDisciplina.getText();
					dp.setNumeDisciplina(sNumeDisciplina);
					
					System.out.println("Anul : " + ValoareAn + " Sectia :" + ValoareSectie + " Nume Disciplina : " + sNumeDisciplina);

					OrarDisciplina od = new OrarDisciplina((JFrame)FereastraDp.this.getParent());
					//System.out.println(dim + " " + tip+ " "  + " " + prod);
					//faci automobil bagi in lista
				} catch (NumberFormatException err) {

					System.out.println("Dimesion must be number");
				} catch (InvalidFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGo.setBounds(200, 350, 89, 23);
		contentPane.add(btnGo);
	}





}