import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;


public class OrarProfesor extends JDialog {


	private DatePartajate dp = DatePartajate.getInstance();
	private String Orar_Sali_Path = dp.getPathOrar();
	private Workbook workbookOP;
	private DataFormatter dataFormatterOP;
	private Sheet paginaOP = null;
	static int primaColoana = 0;
	static int ultimaColoana = 0;
	static int primulRand = 0;
	static int ultimulRand = 0;
	Cell indexPtOra = null;
	Cell indexPtZiua = null;
	Sheet pagina = null;


	Path path = Paths.get(Orar_Sali_Path);
	String directory = path.getParent().toString();
	String fileName = "testOP.txt";
	File dir = new File (directory);
	File actualFile = new File (dir, fileName);
	BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

	private static Integer getIndexIfCellIsInMergedCells(Sheet sheet, Row randOS, Cell coloanaOS) {
		int numberOfMergedRegions = sheet.getNumMergedRegions();
		for (int i = 0; i < numberOfMergedRegions; i++) {
			CellRangeAddress mergedCell = sheet.getMergedRegion(i);
			primaColoana = mergedCell.getFirstColumn();        
			ultimaColoana= mergedCell.getLastColumn();        
			if (mergedCell.isInRange(coloanaOS)) {
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

		return sheet.getRow(mergedCells.getFirstRow()).getCell(mergedCells.getFirstColumn()).getStringCellValue();
	}

	private void AfisareDateColoana(Sheet paginaOP, int indexRandPlecare , int indexRandOprire,int indexPtOraStart,int indexPtOraEnd) throws IOException
	{
		System.out.println("Pagina din functie---------------" + paginaOP.getSheetName());
		
		int ok = 0;
		String ValoareMergeCell = null;
		String ValoareNuNeIntereseaza = null;
		Iterator<Row> iteratorRandOP = paginaOP.rowIterator();
		while (iteratorRandOP.hasNext()) {
			Row randOP =null;
			randOP = iteratorRandOP.next();
			Iterator<Cell> iteratorColoanaOP = randOP.cellIterator();
				while (iteratorColoanaOP.hasNext()) {
					Cell coloanaOP =null;
					coloanaOP = iteratorColoanaOP.next();
					Integer mergedCellIndex = getIndexIfCellIsInMergedCells(paginaOP, randOP, coloanaOP);
					if (mergedCellIndex != null) {
						if(ok == 0) {
							ValoareMergeCell = readContentFromMergedCells(paginaOP, paginaOP.getMergedRegion(mergedCellIndex));
							ok = 1;
						}
						else {
							ValoareNuNeIntereseaza = readContentFromMergedCells(paginaOP, paginaOP.getMergedRegion(mergedCellIndex));
							if(ValoareMergeCell == ValoareNuNeIntereseaza || ValoareNuNeIntereseaza == null || ValoareNuNeIntereseaza == " ")
							{
								continue;
							}
							else {
								ValoareMergeCell = ValoareNuNeIntereseaza;
								if(randOP.getRowNum() >= indexRandPlecare && randOP.getRowNum() <= indexRandOprire)
								{
									if(randOP.getRowNum() >= indexPtOraStart && randOP.getRowNum() <= indexPtOraEnd)
									{	
										String celulaOP = readContentFromMergedCells(paginaOP, paginaOP.getMergedRegion(mergedCellIndex));
										String[] wordscelulaOP = celulaOP.split("\\W+");
										String[] wordsnumeProfesor = dp.getNumeProfesor().split("\\W+");
										for(int i =0;i<wordscelulaOP.length;i++)
										{
											for(int j = 0;j< wordsnumeProfesor.length;j++)
											{
												if(wordscelulaOP[i].contains(wordsnumeProfesor[j]))
												{
													String celulaOpPtOra = dataFormatterOP.formatCellValue(randOP.getCell(indexPtOra.getColumnIndex()));

													System.out.println("Ora la care este: "+ celulaOpPtOra);
													writer.write(celulaOpPtOra+"\t");
													//System.out.println("Cell is in a merged cell");
													System.out.println(
															readContentFromMergedCells(paginaOP, paginaOP.getMergedRegion(mergedCellIndex)));
													writer.write(celulaOP);
													writer.write("\n");
												}
											}

										}
									}
								}

							}
						}
					}

					else {
						String valoareCelulaOP = dataFormatterOP.formatCellValue(coloanaOP);
						if (valoareCelulaOP.isEmpty()) {
							continue;
						}
						else {
							if(randOP.getRowNum() >= indexRandPlecare && randOP.getRowNum() <= indexRandOprire)
							{
								if(randOP.getRowNum() >= indexPtOraStart && randOP.getRowNum() <= indexPtOraEnd)
								{
								String[] wordscelulaOP = valoareCelulaOP.split("\\W+");
								String[] wordsnumeProfesor = dp.getNumeProfesor().split("\\W+");
								for(int i =0;i<wordscelulaOP.length;i++)
								{
									for(int j = 0;j< wordsnumeProfesor.length;j++)
									{
									if(wordscelulaOP[i].contains(wordsnumeProfesor[j]))
									{
										String celulaOpPtOra = dataFormatterOP.formatCellValue(randOP.getCell(indexPtOra.getColumnIndex()));

										System.out.println("Ora la care este: "+ celulaOpPtOra);
										writer.write(celulaOpPtOra+"\t");
										//System.out.println("Cell is in a merged cell");
										System.out.println("Materia: "+ valoareCelulaOP);
										writer.write(valoareCelulaOP);
										writer.write("\n");
									}
									}
									
								}
							
//							String celulaOpPtOra = dataFormatterOP.formatCellValue(randOP.getCell(indexPtOra.getColumnIndex()));
//
//							System.out.println("Ora la care este: "+ celulaOpPtOra);
//							writer.write(celulaOpPtOra+"\t");
//							//System.out.println("Cell is in a merged cell");
//							writer.write(valoareCelulaOP);
//							writer.write("\n");
							}
							}
						}
					}
				}
			}
	}



	String[] zileOrar = { "Luni", "Marti", "Miercuri", "Joi", "Vineri" };
	public OrarProfesor(JFrame parent)  throws IOException, InvalidFormatException {
		super(parent,"OrarProfesor", true);

		workbookOP = WorkbookFactory.create(new File(Orar_Sali_Path));
		dataFormatterOP = new DataFormatter();

		System.out.println("WorkbookOP are " + workbookOP.getNumberOfSheets() + "pagini : ");

		System.out.println("Extragere nume pagina");
		//Sheet paginaOP = workbookOP.getSheetAt(0);
		int indexRandPlecare = 0,indexRandOprire = 0 ,indexPtOraStart = 0 ,indexPtOraEnd = 0;
		int ok = 0;
		String ValoareMergeCell = null;
		String ValoareNuNeIntereseaza = null;
		Iterator<Sheet> iteratorPagOP = workbookOP.sheetIterator();
		while(iteratorPagOP.hasNext())
		{
			Sheet paginaOP = iteratorPagOP.next(); 
			System.out.println("Pagina:" + paginaOP.getSheetName());
			Iterator<Row> iteratorRandOP = paginaOP.rowIterator();
			while (iteratorRandOP.hasNext()) {
				Row randOP =null;
				randOP = iteratorRandOP.next();
				Iterator<Cell> iteratorColoanaOP = randOP.cellIterator();
				while (iteratorColoanaOP.hasNext()) {
					Cell coloanaOP =null;
					coloanaOP = iteratorColoanaOP.next();
					Integer mergedCellIndex = getIndexIfCellIsInMergedCells(paginaOP, randOP, coloanaOP);
					if (mergedCellIndex != null) {
						if(ok == 0) {
							ValoareMergeCell = readContentFromMergedCells(paginaOP, paginaOP.getMergedRegion(mergedCellIndex));
							ok = 1;
						}
						else {
							ValoareNuNeIntereseaza = readContentFromMergedCells(paginaOP, paginaOP.getMergedRegion(mergedCellIndex));
							if(ValoareMergeCell == ValoareNuNeIntereseaza || ValoareNuNeIntereseaza == null || ValoareNuNeIntereseaza == " " || ValoareNuNeIntereseaza.isEmpty() || ValoareNuNeIntereseaza.isBlank()  )
							{
								continue;
							}
							else {
								ValoareMergeCell = ValoareNuNeIntereseaza;
								String celulaOP = readContentFromMergedCells(paginaOP, paginaOP.getMergedRegion(mergedCellIndex));
								String[] wordsCelulaOP = celulaOP.split("\\W+");
								for(int i=0;i<wordsCelulaOP.length;i++)
								{
									//afisare ora corecta+ ziua
									if(wordsCelulaOP[i].contains(dp.getZiua()))
									{
										indexPtZiua = coloanaOP;	
										indexRandPlecare = primulRand;
										indexRandOprire = ultimulRand;
										//									System.out.println("Valoare celula ziua pentru index " + celulaOP +"\n");
										//									System.out.print(coloanaOP.getColumnIndex() + "\t");
										//									System.out.println("Primul rand :" + primulRand +"\n");
										//									//											matriceRandInceputZile[indexZileOrar]= primulRand;
										//									//											matriceRandSfarsitZile[indexZileOrar] = ultimulRand;
										//									System.out.println("Ultimul rand :" + ultimulRand+"\n");
										//									if(dp.getZiua() == "Marti")
										//									{
										//										
										//									}
									}
									
									if(wordsCelulaOP[i].startsWith("8.00"))
									{
										// retin celula unde gaseste 8.00 de unde se poate extrage 
										indexPtOra = coloanaOP;	
									}

								}
								if(randOP.getRowNum() >= indexRandPlecare && randOP.getRowNum() <= indexRandOprire)
								{
									if(celulaOP.startsWith(dp.getOraStart()))
									{
										//retin randul pentru ora de inceput corespuzatoare
										indexPtOraStart = randOP.getRowNum();	
//										System.out.print(randOP.getRowNum() + "\t");
									}
									if(celulaOP.endsWith(dp.getOraEnd()))
									{
										//retin randul pentru ora de sfarsit corespuzatoare
										indexPtOraEnd = randOP.getRowNum();	
//										System.out.print(randOP.getRowNum() + "\t");
									}
								}
							}
						}
					}

					else {
						String valoareCelulaOP = dataFormatterOP.formatCellValue(coloanaOP);
						if (valoareCelulaOP.isEmpty()) {
							continue;
						}
						else {
							//Pentru celule individuale gasire subgrupa buna
							String[] wordsCelulaOP = valoareCelulaOP.split("\\W+");
							boolean grupaGasita = false,numarGrupaGasita = false,subgrupaGasita = false,numarsubGrupaGasita = false;
							for(int i=0;i<wordsCelulaOP.length;i++)
							{
								//afisare ora corecta+ ziua
								if(valoareCelulaOP.contains(dp.getZiua()))
								{
									indexPtZiua = coloanaOP;		
									//								System.out.println("Valoare celula ziua pentru index " + valoareCelulaOP +"\n");
									//								System.out.print(coloanaOP.getColumnIndex() + "\t");
								}

							}
							if(randOP.getRowNum() >= indexRandPlecare && randOP.getRowNum() <= indexRandOprire)
							{
								if(valoareCelulaOP.startsWith(dp.getOraStart()))
								{
									//retin randul pentru ora de inceput corespuzatoare
									indexPtOraStart = randOP.getRowNum();	
//									System.out.print(randOP.getRowNum() + "\t");
								}
								if(valoareCelulaOP.endsWith(dp.getOraEnd()))
								{
									//retin randul pentru ora de sfarsit corespuzatoare
									indexPtOraEnd = randOP.getRowNum();	
//									System.out.print(randOP.getRowNum() + "\t");
								}
							}
							if(valoareCelulaOP.startsWith("8.00"))
							{
								indexPtOra = coloanaOP;	
								//							System.out.println("Valoare celula ora pentru index " + valoareCelulaOP +"\n");
							}
						}
					}
				}
			}
//			System.out.println("Primul rand :" + indexRandPlecare +"\n");
//			System.out.println("Ultimul rand :" + indexRandOprire +"\n");
			pagina = paginaOP;
		AfisareDateColoana(pagina,indexRandPlecare,indexRandOprire,indexPtOraStart,indexPtOraEnd);
		}
		writer.close();
	}
}