import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class FereastraOS extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textAnul;
	private JTextField textSubgrupa;
	private JTextField textField3;
	private JTextField textGrupa;
	private String sGrupa;
	private String sSubGrupa;
	protected String sSectie;
	private String sAn = "I";
	private String ValoareAn = "I";
	protected String ValoareSectie = "-";
	protected boolean cerereOrar = false;
	
	private DatePartajate dp = DatePartajate.getInstance();

	String[] aniFacultate = { "I", "II", "III", "m1", "m2" };
	String[] sectiiFacultate = { "-","IR", "IE", "IA"};
	public FereastraOS(JFrame parent)  {
		super(parent, "FereastraOS", true);
		constructGUI();
		this.setPreferredSize(new Dimension(500,500));
		this.pack();
		this.setVisible(true);
		
	}
	private void constructGUI() {
				
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

		JLabel lblSectie = new JLabel("Sectia:");
		lblSectie.setBounds(30, 250, 50, 20);
		contentPane.add(lblSectie);

		
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
						System.out.println("apasa buton an Falculate: "+sAn);	
							
					}
					
				}catch (NumberFormatException err) {

					System.out.println("Dimesion must be number");
				}
				
			}
		});
		
		
		JComboBox sectieFacultate = new JComboBox(sectiiFacultate);
		sectieFacultate.setSelectedIndex(0);
		sectieFacultate.setBounds(100, 250, 100, 20);
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
						System.out.println("apasa buton sectie Falculate: "+sSectie);	
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

		JButton orarGrupa = new JButton("Orar Grupa");
		orarGrupa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					sGrupa= textGrupa.getText();
					dp.setsGrupa(sGrupa);

					sSubGrupa = textSubgrupa.getText();
					dp.setsSubgrupa(sSubGrupa);
					
					sSectie = ValoareSectie;
					sAn  = ValoareAn;
					
					cerereOrar = false; //orar grupa false
					dp.setcerereOrar(cerereOrar);
					
					OrarStudent os = new OrarStudent((JFrame)FereastraOS.this.getParent());
					
					
					System.out.println("Anul : " + sAn + " Grupa :" + sGrupa + " Subgrupa : " + sSubGrupa + "Sectia :"+ sSectie);
					
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
		orarGrupa.setBounds(100, 350, 125, 23);
		contentPane.add(orarGrupa);
		
		JButton orarSubGrupa = new JButton("Orar Subgrupa");
		orarSubGrupa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					sGrupa= textGrupa.getText();
					dp.setsGrupa(sGrupa);
					
					sSubGrupa = textSubgrupa.getText();
					dp.setsSubgrupa(sSubGrupa);
					
					sSectie = ValoareSectie;
					sAn  = ValoareAn;
					
					cerereOrar = true; //orarSubgrupa true
					dp.setcerereOrar(cerereOrar);
					
					OrarStudent os = new OrarStudent((JFrame)FereastraOS.this.getParent());
					
					System.out.println("Anul : " + sAn + " Grupa :" + sGrupa + " Subgrupa : " + sSubGrupa + "Sectia :"+ sSectie);
							
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
		orarSubGrupa.setBounds(300, 350, 125, 23);
		contentPane.add(orarSubGrupa);
	}

//
//	protected String getsGrupa() {
//		return sGrupa;
//	}
//
//
//	protected void setsGrupa(String sGrupa) {
//		this.sGrupa = sGrupa;
//	}
//
//
//	public String getsSectie() {
//		return sSectie;
//	}
//
//
//	protected void setsSectie(String sSectie) {
//		this.sSectie = sSectie;
//	}
//
//
//	public String getsAn() {
//		return sAn;
//	}
//
//
//	protected void setsAn(String sAn) {
//		this.sAn = sAn;
//	}





}
