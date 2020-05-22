import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;



public class OrarDisciplina extends JDialog {

	private DatePartajate dp = DatePartajate.getInstance();
	private String Orar_Sali_Path = dp.getPathOrar();
	private Workbook workbookOD;
	private DataFormatter dataFormatterOD;
	private Sheet paginaOD = null;
	static int primaColoana = 0;
	static int ultimaColoana = 0;
	static int primulRand = 0;
	static int ultimulRand = 0;
	Cell indexPtOra = null;
	Cell indexPtZiua = null;
	Path path = Paths.get(Orar_Sali_Path);
	String directory = path.getParent().toString();

	protected enum tipOrar{
		Licenta,Master;
	}

	private static Integer getIndexIfCellIsInMergedCells(Sheet sheet, Row randOD, Cell coloanaOD) {
		int numberOfMergedRegions = sheet.getNumMergedRegions();
		for (int i = 0; i < numberOfMergedRegions; i++) {
			CellRangeAddress mergedCell = sheet.getMergedRegion(i);
			primaColoana = mergedCell.getFirstColumn();        
			ultimaColoana= mergedCell.getLastColumn();        
			if (mergedCell.isInRange(coloanaOD)) {
				return i;

			}
		}

		return null;
	}

	private static String readContentFromMergedCells(Sheet sheet, CellRangeAddress mergedCells) {
		if (mergedCells.getFirstRow() != mergedCells.getLastRow()) {
			primulRand = mergedCells.getFirstRow();
			ultimulRand = mergedCells.getLastRow();
		}

		return sheet.getRow(mergedCells.getFirstRow()).getCell(mergedCells.getFirstColumn()).getRichStringCellValue().getString();
	}

	private void AfisareDateColoana(Sheet sheet,Cell coloanaDorita , Row randDePlecare) throws IOException
	{
		int ok = 0;
		int k = 0;
		int indexZi = 0;
		boolean afisatOdata = false;
		String ValoareMergeCell = null;
		String ValoareNuNeIntereseaza = null;
		//Cell coloanaGrupa =null;

		
		String fileName = "testOD.txt";
		File dir = new File (directory);
		File actualFile = new File (dir, fileName);
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		Iterator<Row> iteratorRandOD = paginaOD.rowIterator();
		while (iteratorRandOD.hasNext()) {
			Row randOD =null;
			randOD = iteratorRandOD.next();
			Iterator<Cell> iteratorColoanaOD = randOD.cellIterator();						
			if(randOD.getRowNum() >= 6)
			{
				while (iteratorColoanaOD.hasNext()) {
					Cell coloanaOD =null;
					coloanaOD = iteratorColoanaOD.next();
					Integer mergedCellIndex = getIndexIfCellIsInMergedCells(paginaOD, randOD, coloanaOD);
						if (mergedCellIndex != null) {
							if(ok == 0) {
								ValoareMergeCell = readContentFromMergedCells(paginaOD, paginaOD.getMergedRegion(mergedCellIndex));
								ok = 1;
							}
							else {
								ValoareNuNeIntereseaza = readContentFromMergedCells(paginaOD, paginaOD.getMergedRegion(mergedCellIndex));
								if(ValoareMergeCell == ValoareNuNeIntereseaza || ValoareNuNeIntereseaza == null || ValoareNuNeIntereseaza == " ")
								{
									continue;
								}
								else {
								    
									ValoareMergeCell = ValoareNuNeIntereseaza;
									String celulaOD = readContentFromMergedCells(paginaOD, paginaOD.getMergedRegion(mergedCellIndex));
									if(afisatOdata ==false)
									{
										writer.write(zileOrar[k]+"\n");
										System.out.println("Ziua: "+ zileOrar[k]);
										afisatOdata = true;
									}
									if(randOD.getRowNum() < matriceRandInceputZile[indexZi] || randOD.getRowNum() > matriceRandSfarsitZile[indexZi])
									{
										writer.write(zileOrar[k+1]+"\n");
										System.out.println("Ziua: "+ zileOrar[k+1]);
										k++;
										indexZi++;
									}	
									String[] wordscelulaOP = celulaOD.split("\\W+-|-");
									String[] wordsnumeDisciplina = dp.getNumeDisciplina().split("\\W+");
									for(int i =0;i<wordscelulaOP.length;i++)
									{
										for(int j = 0;j< wordsnumeDisciplina.length;j++)
										{
											if(wordscelulaOP[i].contains(wordsnumeDisciplina[j]))
											{
												String celulaOpPtOra = dataFormatterOD.formatCellValue(randOD.getCell(indexPtOra.getColumnIndex()));

												System.out.println("Ora la care este: "+ celulaOpPtOra);
												writer.write(celulaOpPtOra+"\t");
												//System.out.println("Cell is in a merged cell");
												System.out.println(
														readContentFromMergedCells(paginaOD, paginaOD.getMergedRegion(mergedCellIndex)));
												writer.write(celulaOD);
												writer.write("\n");
												break;
											}
										}

									}
									
//									String celulaOsPtOra = dataFormatterOD.formatCellValue(randOD.getCell(indexPtOra.getColumnIndex()));
//									
//									System.out.println("Ora la care este: "+ celulaOsPtOra);
//									writer.write(celulaOsPtOra+"\t");
//									//System.out.println("Cell is in a merged cell");
//									System.out.println(
//											readContentFromMergedCells(paginaOD, paginaOD.getMergedRegion(mergedCellIndex)));
//									writer.write(celulaOD);
//									writer.write("\n");

								}
							}
						}
						else {
							String valoareCelulaOD = dataFormatterOD.formatCellValue(coloanaOD);
							if (valoareCelulaOD.isEmpty()) {
								continue;
							}
							else {
								
//								String celulaOsPtOra = dataFormatterOD.formatCellValue(indexPtOra);
//								System.out.println("Ora la care este: "+ celulaOsPtOra);
//								writer.write(celulaOsPtOra +"\t");
//								//System.out.print("Cell is an individual cell");
//								System.out.print(valoareCelulaOD + "\t");
//								writer.write(valoareCelulaOD);
//								writer.write("\n");
								String[] wordscelulaOD = valoareCelulaOD.split("\\W+");
								String[] wordsnumeDisciplina = dp.getNumeDisciplina().split("\\W+");
								for(int i =0;i<wordscelulaOD.length;i++)
								{
									for(int j = 0;j< wordsnumeDisciplina.length;j++)
									{
									if(wordscelulaOD[i].contains(wordsnumeDisciplina[j]))
									{
										String celulaOpPtOra = dataFormatterOD.formatCellValue(randOD.getCell(indexPtOra.getColumnIndex()));

										System.out.println("Ora la care este: "+ celulaOpPtOra);
										writer.write(celulaOpPtOra+"\t");
										//System.out.println("Cell is in a merged cell");
										System.out.println("Materia: "+ valoareCelulaOD);
										writer.write(valoareCelulaOD);
										writer.write("\n");
									}
									}
									
								}

							}
						}
				}
			}
		}
		writer.close();
	}


	String[] zileOrar = { "Luni", "Mar.i", "Miercuri", "Joi", "Vineri", " " };
	int[] matriceRandInceputZile= {0,0,0,0,0};
	int[] matriceRandSfarsitZile= {0,0,0,0,0};
	public OrarDisciplina(JFrame parent)  throws IOException, InvalidFormatException {
		super(parent,"OrarDisciplina", true);
		workbookOD = WorkbookFactory.create(new File(Orar_Sali_Path));
		paginaOD = workbookOD.getSheetAt(0);  
		dataFormatterOD = new DataFormatter();

		System.out.println("WorkbookOD are " + workbookOD.getNumberOfSheets() + "pagini : ");
		Iterator<Sheet> iteratorPagOD = workbookOD.sheetIterator();
		System.out.println("Extragere nume pagina");
		

		tipOrar tip;
		String descriereSectie = " ";

		if(dp.getSectie() =="IR")
			descriereSectie = "ROMANA";  	
		if(dp.getSectie() =="IA")
			descriereSectie = "APLICATA";
		if(dp.getSectie() =="IE")
			descriereSectie = "ENGLEZA";
		if(dp.getSectie() !="-")
		{
			tip = tipOrar.Licenta; 
		}
		else
		{
			tip = tipOrar.Master; 
		}

		boolean requiredSheet = false;
		if(tip == tipOrar.Licenta)
		{
			while (iteratorPagOD.hasNext()) {
				//Aici aflam anul bun din toate paginile
				Sheet paginaCautata = iteratorPagOD.next();       
				Iterator<Row> iteratorRandOD = paginaCautata.rowIterator();
				if(requiredSheet == false)
				{
					while (iteratorRandOD.hasNext()) {
						Row randOD = iteratorRandOD.next();
						Iterator<Cell> iteratorColoanaOD = randOD.cellIterator();
						while (iteratorColoanaOD.hasNext()) {
							Cell coloanaOD = iteratorColoanaOD.next();
							String valoareCelulaOD = dataFormatterOD.formatCellValue(coloanaOD);
							if(valoareCelulaOD.contains("Anul"))
							{
								Iterator<Cell> it = randOD.cellIterator();
								while (it.hasNext()) {
									String s = dataFormatterOD.formatCellValue(it.next());
									if(descriereSectie.equals("ROMANA")){
										System.out.println("s= "+s+" descriere="+descriereSectie);
										if (s.contains("INFORMATICA") && !s.contains("ENGLEZA") && !s.contains("APLICATA")) {
											String[] words = s.split("\\W+");
											for(int i =0;i<words.length;i++)
											{
												if(dp.getsAn().contentEquals(words[i])) {
													requiredSheet = true;
													System.out.print(s + "\t");
													System.out.print(paginaCautata.getSheetName() + "\n");
													paginaOD = paginaCautata;
													break;
												}
											}
										}
									}
									if(descriereSectie.equals("APLICATA")){
										System.out.println("s= "+s+" descriere="+descriereSectie);
										if (s.contains("INFORMATICA") && s.contains("APLICATA")) {
											String[] words = s.split("\\W+");
											for(int i =0;i<words.length;i++)
											{
												if(dp.getsAn().contentEquals(words[i])) {
													requiredSheet = true;
													System.out.print(s + "\t");
													System.out.print(paginaCautata.getSheetName() + "\n");
													paginaOD = paginaCautata;
													break;
												}
											}
										}
									}
									if(descriereSectie.equals("ENGLEZA")){
										System.out.println("s= "+s+" descriere="+descriereSectie);
										if (s.contains("INFORMATIC") && s.contains("ENGLEZA")) {
											String[] words = s.split("\\W+");
											for(int i =0;i<words.length;i++)
											{
												if(dp.getsAn().contentEquals(words[i])) {
													requiredSheet = true;
													System.out.print(s + "\t");
													System.out.print(paginaCautata.getSheetName() + "\n");
													paginaOD = paginaCautata;
													break;
												}
											}
										}
									}
								}
							}						
						}
					}
				}
			}
		}
		else // Pentru master
		{

		}

		int ok = 0;
			
		String ValoareMergeCell = null;
		String ValoareNuNeIntereseaza = null;
		Cell coloanaDorita = null;
		Row randDePlecare =null;
		Iterator<Row> iteratorRandOD = paginaOD.rowIterator();
		while (iteratorRandOD.hasNext()) {
			Row randOD =null;
			randOD = iteratorRandOD.next();
			Iterator<Cell> iteratorColoanaOD = randOD.cellIterator();
			while (iteratorColoanaOD.hasNext()) {
				Cell coloanaOD =null;
				coloanaOD = iteratorColoanaOD.next();
				Integer mergedCellIndex = getIndexIfCellIsInMergedCells(paginaOD, randOD, coloanaOD);
				if (mergedCellIndex != null) {
					if(ok == 0) {
						ValoareMergeCell = readContentFromMergedCells(paginaOD, paginaOD.getMergedRegion(mergedCellIndex));
						ok = 1;
					}
					else {
						ValoareNuNeIntereseaza = readContentFromMergedCells(paginaOD, paginaOD.getMergedRegion(mergedCellIndex));
						if(ValoareMergeCell == ValoareNuNeIntereseaza || ValoareNuNeIntereseaza == null || ValoareNuNeIntereseaza == " " || ValoareNuNeIntereseaza.isEmpty() || ValoareNuNeIntereseaza.isBlank()  )
						{
							continue;
						}
						else {
							ValoareMergeCell = ValoareNuNeIntereseaza;
							String celulaOD = readContentFromMergedCells(paginaOD, paginaOD.getMergedRegion(mergedCellIndex));
							String[] wordsCelulaOD = celulaOD.split("\\s+");
							
							for(int i=0;i<wordsCelulaOD.length;i++)
							{
							
//								System.out.println("Valoare celula ziua pentru index -------------" + wordsCelulaOD[i] +"\n");
								//afisare ora corecta+ ziua
								for(int indexZileOrar = 0;indexZileOrar < zileOrar.length;indexZileOrar++)
								{
//									if(wordsCelulaOD[i].startsWith("Mar"))
//									{
//										//variabila.matches("Mar.i")
////										boolean equal = Arrays.equals(wordsCelulaOD[i].getBytes("UTF-8"), zileOrar[indexZileOrar].getBytes("UTF-8"));
//										boolean equal = wordsCelulaOD[i].matches("Mar.i");
//										System.out.println("--------------------" + wordsCelulaOD[i] + "-" +equal+ "-" + zileOrar[indexZileOrar] + "-" + wordsCelulaOD[i].contentEquals(zileOrar[indexZileOrar]) +"\n");
//									}
									
									if(wordsCelulaOD[i].matches(zileOrar[indexZileOrar]))
									{
										indexPtZiua = coloanaOD;	
										System.out.println("Valoare celula ziua pentru index " + celulaOD +"\n");
										System.out.print(coloanaOD.getColumnIndex() + "\t");
										System.out.println("Primul rand :" + primulRand +"\n");
										matriceRandInceputZile[indexZileOrar]= primulRand;
										matriceRandSfarsitZile[indexZileOrar] = ultimulRand;
										System.out.println("Ultimul rand :" + ultimulRand+"\n");
									}
								}
								if(wordsCelulaOD[i].startsWith("8.00"))
								{
									// retin celula unde gaseste 8.00 de unde se poate extrage 
									indexPtOra = coloanaOD;	
									System.out.println("Valoare celula ora pentru index " + celulaOD +"\n");
									System.out.print(coloanaOD.getColumnIndex() + "\t");
								}

							}
						}
					}

				}

				else {
					String valoareCelulaOD = dataFormatterOD.formatCellValue(coloanaOD);
					if (valoareCelulaOD.isEmpty()) {
						continue;
					}
					else {
						//Pentru celule individuale gasire subgrupa buna
						String[] wordsCelulaOD = valoareCelulaOD.split("\\s+");
						for(int i=0;i<wordsCelulaOD.length;i++)
						{
							//System.out.println("Valoare celula ziua pentru index " + valoareCelulaOD +"\n");
							//afisare ora corecta+ ziua
							if(valoareCelulaOD.contains(zileOrar[0]))
							{
								indexPtZiua = coloanaOD;		
								System.out.println("Valoare celula ziua pentru index " + valoareCelulaOD +"\n");
								System.out.print(coloanaOD.getColumnIndex() + "\t");
							}
							if(valoareCelulaOD.contains("8.00"))
							{
								indexPtOra = coloanaOD;								
							}

						}
					}
				}
			}
		}
	      for (int i = 0; i < matriceRandInceputZile.length; i++) 
	      {
	    	  System.out.print(matriceRandInceputZile[i] + " "); 
	    	  System.out.print(matriceRandSfarsitZile[i] + " "); 
	    	  System.out.print("\n");
	      }
        AfisareDateColoana(paginaOD,coloanaDorita , randDePlecare);
		
	}
	
	
}