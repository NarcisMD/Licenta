import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerListModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class FereastraOP extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DatePartajate dp = DatePartajate.getInstance();
	
	private JTextField textNumeProfesor;
	private	String sZi = "Luni";
	private String sOraStart;
	private String sOraEnd ;

	
	String[] zileOrar = { "Luni", "Marți", "Miercuri", "Joi", "Vineri" };
	String[] oreStartOrar = { "8.00", "9.40", "11.20", "13.00", "14.40", "16.20", "18.00" , "19.40" };
	String[] oreEndOrar = { "9.30", "11.10", "12.50", "14.30", "16.10", "17.50" , "19.30" , "21.10" };
	double[] oreStartOrarD = { 8.00, 9.40, 11.20, 13.00, 14.40, 16.20, 18.00 , 19.40};
	double[] oreEndOrarD = { 9.30, 11.10, 12.50, 14.30, 16.10, 17.50 , 19.30 , 21.10 };

	public FereastraOP(JFrame parent)  {
		super(parent, "FereastraOP", true);
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
		
		JLabel lblZiua = new JLabel("Ziua:");
		lblZiua.setBounds(30, 100, 50, 20);
		contentPane.add(lblZiua);
		
		JLabel lblOraStart = new JLabel("De la ora:");
		lblOraStart.setBounds(30, 150, 100, 20);
		contentPane.add(lblOraStart);

		JLabel lblOraEnd = new JLabel("Pana la ora:");
		lblOraEnd.setBounds(30, 200, 100, 20);
		contentPane.add(lblOraEnd);

		JLabel lblNumeProfesor = new JLabel("Profesor:");
		lblNumeProfesor.setBounds(30, 250, 100, 20);
		contentPane.add(lblNumeProfesor);
		
		JComboBox ziuaOrar = new JComboBox(zileOrar);
		JLabel lblText = new JLabel();
		ziuaOrar.setSelectedIndex(0);
		ziuaOrar.setBounds(100, 100, 100, 20);
		contentPane.add(ziuaOrar);
		
		
//		Date dateStart = new Date();
//		JSpinner intervalStart = new JSpinner();
//		intervalStart.setBounds(100, 150, 100, 30);
//		intervalStart.setModel(new SpinnerDateModel(dateStart, null, null, Calendar.HOUR_OF_DAY));
//		JSpinner.DateEditor dateEditorStart = new JSpinner.DateEditor(intervalStart, "HH:mm");
//		intervalStart.setEditor(dateEditorStart);
//		contentPane.add(intervalStart);
		
		JSpinner intervalStart = new JSpinner();
		SpinnerListModel oraStart = new SpinnerListModel(oreStartOrar);
		intervalStart.setBounds(100, 150, 100, 30);
		intervalStart.setModel(oraStart);
		contentPane.add(intervalStart);
		
		JSpinner intervalEnd = new JSpinner();
		SpinnerListModel oraEnd = new SpinnerListModel(oreEndOrar);
		intervalEnd.setBounds(100, 200, 100, 30);
		intervalEnd.setModel(oraEnd);
		contentPane.add(intervalEnd);
		
		textNumeProfesor = new JTextField();
		textNumeProfesor.setBounds(100, 250, 100, 30);
		contentPane.add(textNumeProfesor);
		textNumeProfesor.setColumns(10);
		

		intervalStart.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				
				double oraCurentaStart = 0 , oraCurentaEnd = 0;
				for(int i = 0; i<oreStartOrar.length;i++)
				{
					if( oraStart.getValue().equals(oreStartOrar[i]))
					{
						oraCurentaStart = Double.parseDouble(oreStartOrar[i]);

					}
					if( oraEnd.getValue().equals(oreEndOrar[i]))
					{
						oraCurentaEnd = Double.parseDouble(oreEndOrar[i]);

					}
				}
				if(oraCurentaStart > oraCurentaEnd)
				{
					oraEnd.setValue(oraEnd.getNextValue());
				}
				//oraUrmatoare = oraStart.getNextValue(oraStart.getPreviousValue());
//				if(oraStart.getValue() == oraStart.getNextValue())
//				{
//					oraEnd.setValue(oraEnd.getNextValue());
//				}
//				if(oraStart.getValue() == oraStart.getPreviousValue())
//				{
//					oraEnd.setValue(oraEnd.getPreviousValue());
//				}
				
				
			}
			
		});
		
		
		intervalEnd.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				
				double oraCurentaStart = 0 , oraCurentaEnd = 0;
				for(int i = 0; i<oreEndOrar.length;i++)
				{
					if( oraStart.getValue().equals(oreStartOrar[i]))
					{
						oraCurentaStart = Double.parseDouble(oreStartOrar[i]);

					}
					if( oraEnd.getValue().equals(oreEndOrar[i]))
					{
						oraCurentaEnd = Double.parseDouble(oreEndOrar[i]);

					}
				}
				if(oraCurentaEnd < oraCurentaStart)
				{
					oraStart.setValue(oraStart.getPreviousValue());
					
				}
				
				
			}
			
		});
		
		
		ziuaOrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(e.getSource() == ziuaOrar) {
						JComboBox ziua = (JComboBox)e.getSource();
						sZi= (String)ziua.getSelectedItem();
							
					}
					
				}catch (NumberFormatException err) {

					System.out.println("Dimesion must be number");
				}
				
			}
		});
		
		
		
		
		JButton btnGo = new JButton("Orar Profesor");
		btnGo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String sZiua = sZi;
					dp.setZiua(sZiua);
					
					sOraStart = (String) intervalStart.getValue();	
					dp.setOraStart(sOraStart);
					
					sOraEnd = (String) intervalEnd.getValue();
					dp.setOraEnd(sOraEnd);
					
					String sNumeProfesor = textNumeProfesor.getText();
					dp.setNumeProfesor(sNumeProfesor);
										
					
					OrarProfesor op = new OrarProfesor((JFrame)FereastraOP.this.getParent());
					
//					LocalDateTime myDateObj = LocalDateTime.now();
//					DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");
//					String formattedDate = myDateObj.format(myFormatObj);

					System.out.println("Ziua : " + sZiua + " De la ora :" + sOraStart + " Pana la ora : " + sOraEnd + " Profesor : " + sNumeProfesor);
					

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