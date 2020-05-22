import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import java.text.Normalizer;
import java.util.regex.Pattern;


public class OrarStudent extends JDialog {

	private DatePartajate dp = DatePartajate.getInstance();
	private String Orar_Sali_Path = dp.getPathOrar();
	private Workbook workbookOS;
	private DataFormatter dataFormatterOS;
	private Sheet paginaOS = null;
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
//	public String deAccent(String str) {
//	    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
//	    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
//	    return pattern.matcher(nfdNormalizedString).replaceAll("");
//	}

	private void AfisareDateColoana(Sheet sheet,Cell coloanaDorita , Row randDePlecare ,boolean cerereOrar) throws IOException
	{
		int ok = 0;
		int k = 0;
		int indexZi = 0;
		boolean afisatOdata = false;
		String ValoareMergeCell = null;
		String ValoareNuNeIntereseaza = null;
		//Cell coloanaGrupa =null;
		int randDePlecareGrupaSubGrupa = 0;
		
		String fileName = "test.txt";
		File dir = new File (directory);
		File actualFile = new File (dir, fileName);
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		if(cerereOrar == false)
		{
			randDePlecareGrupaSubGrupa =  randDePlecare.getRowNum()+1;
		}
		else
		{
			randDePlecareGrupaSubGrupa =  randDePlecare.getRowNum();
		}
		Iterator<Row> iteratorRandOS = paginaOS.rowIterator();
		while (iteratorRandOS.hasNext()) {
			Row randOS =null;
			randOS = iteratorRandOS.next();
			Iterator<Cell> iteratorColoanaOS = randOS.cellIterator();						
			if(randOS.getRowNum() >= randDePlecareGrupaSubGrupa)
			{
				while (iteratorColoanaOS.hasNext()) {
					Cell coloanaOS =null;
					coloanaOS = iteratorColoanaOS.next();
					Integer mergedCellIndex = getIndexIfCellIsInMergedCells(paginaOS, randOS, coloanaOS);
					if(coloanaOS.getColumnIndex()==coloanaDorita.getColumnIndex())
					{
						if (mergedCellIndex != null) {
							if(ok == 0) {
								ValoareMergeCell = readContentFromMergedCells(paginaOS, paginaOS.getMergedRegion(mergedCellIndex));
								ok = 1;
							}
							else {
								ValoareNuNeIntereseaza = readContentFromMergedCells(paginaOS, paginaOS.getMergedRegion(mergedCellIndex));
								if(ValoareMergeCell == ValoareNuNeIntereseaza || ValoareNuNeIntereseaza == null || ValoareNuNeIntereseaza == " " || ValoareNuNeIntereseaza.trim().length() == 0)
								{
									continue;
								}
								else {
								    
									ValoareMergeCell = ValoareNuNeIntereseaza;
									String celulaOS = readContentFromMergedCells(paginaOS, paginaOS.getMergedRegion(mergedCellIndex));
									
									//TO DO
									
//									if(randOS.getRowNum() >= matriceRandInceputZile[indexZi] && randOS.getRowNum() <= matriceRandSfarsitZile[indexZi] )
//									{
//										if(afisatOdata == false)
//										{
//										writer.write(zileOrar[k]+"\n");
//										System.out.println("Ziua: "+ zileOrar[k]);
//										afisatOdata = true;
//										}
//									}
//									else
//									{
//										afisatOdata = false;
//										k++;
//										indexZi++;
//									}
									if(afisatOdata ==false)
									{
										writer.write(zileOrar[k]+"\n");
										System.out.println("Ziua: "+ zileOrar[k]);
										afisatOdata = true;
									}
									if(randOS.getRowNum() < matriceRandInceputZile[indexZi] || randOS.getRowNum() > matriceRandSfarsitZile[indexZi])
									{
										writer.write(zileOrar[k+1]+"\n");
										System.out.println("Ziua: "+ zileOrar[k+1]);
										k++;
										indexZi++;
									}
										
									String celulaOsPtOra = dataFormatterOS.formatCellValue(randOS.getCell(indexPtOra.getColumnIndex()));
									
									System.out.println("Ora la care este: "+ celulaOsPtOra);
									writer.write(celulaOsPtOra+"\t");
									//System.out.println("Cell is in a merged cell");
									System.out.println(
											readContentFromMergedCells(paginaOS, paginaOS.getMergedRegion(mergedCellIndex)));
									writer.write(celulaOS);
									writer.write("\n");

								}
							}
						}
						else {
							String valoareCelulaOS = dataFormatterOS.formatCellValue(coloanaOS);
							if (valoareCelulaOS.isEmpty()) {
								continue;
							}
							else {
								
								String celulaOsPtOra = dataFormatterOS.formatCellValue(indexPtOra);
								System.out.println("Ora la care este: "+ celulaOsPtOra);
								writer.write(celulaOsPtOra +"\t");
								//System.out.print("Cell is an individual cell");
								System.out.print(valoareCelulaOS + "\t");
								writer.write(valoareCelulaOS);
								writer.write("\n");

							}
						}
					}
				}
			}
		}
		writer.close();
	}


	String[] zileOrar = { "Luni", "Marti", "Miercuri", "Joi", "Vineri", " " };
	int[] matriceRandInceputZile= {0,14,0,0,0};
	int[] matriceRandSfarsitZile= {0,21,0,0,0};
	public OrarStudent(JFrame parent)  throws IOException, InvalidFormatException {
		super(parent,"OrarStudent", true);
		workbookOS = WorkbookFactory.create(new File(Orar_Sali_Path));
		paginaOS = workbookOS.getSheetAt(0);  
		dataFormatterOS = new DataFormatter();

		System.out.println("WorkbookOS are " + workbookOS.getNumberOfSheets() + "pagini : ");
		Iterator<Sheet> iteratorPagOS = workbookOS.sheetIterator();
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
			while (iteratorPagOS.hasNext()) {
				//Aici aflam anul bun din toate paginile
				Sheet paginaCautata = iteratorPagOS.next();       
				Iterator<Row> iteratorRandOS = paginaCautata.rowIterator();
				if(requiredSheet == false)
				{
					while (iteratorRandOS.hasNext()) {
						Row randOS = iteratorRandOS.next();
						Iterator<Cell> iteratorColoanaOS = randOS.cellIterator();
						while (iteratorColoanaOS.hasNext()) {
							Cell coloanaOS = iteratorColoanaOS.next();
							String valoareCelulaOS = dataFormatterOS.formatCellValue(coloanaOS);
							if(valoareCelulaOS.contains("Anul"))
							{
								Iterator<Cell> it = randOS.cellIterator();
								while (it.hasNext()) {
									String s = dataFormatterOS.formatCellValue(it.next());
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
													paginaOS = paginaCautata;
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
													paginaOS = paginaCautata;
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
													paginaOS = paginaCautata;
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
		Iterator<Row> iteratorRandOS = paginaOS.rowIterator();
		while (iteratorRandOS.hasNext()) {
			Row randOS =null;
			randOS = iteratorRandOS.next();
			Iterator<Cell> iteratorColoanaOS = randOS.cellIterator();
			while (iteratorColoanaOS.hasNext()) {
				Cell coloanaOS =null;
				coloanaOS = iteratorColoanaOS.next();
				Integer mergedCellIndex = getIndexIfCellIsInMergedCells(paginaOS, randOS, coloanaOS);
				if (mergedCellIndex != null) {
					if(ok == 0) {
						ValoareMergeCell = readContentFromMergedCells(paginaOS, paginaOS.getMergedRegion(mergedCellIndex));
						ok = 1;
					}
					else {
						ValoareNuNeIntereseaza = readContentFromMergedCells(paginaOS, paginaOS.getMergedRegion(mergedCellIndex));
						if(ValoareMergeCell == ValoareNuNeIntereseaza || ValoareNuNeIntereseaza == null || ValoareNuNeIntereseaza == " " || ValoareNuNeIntereseaza.isEmpty() || ValoareNuNeIntereseaza.isBlank()  )
						{
							continue;
						}
						else {
							ValoareMergeCell = ValoareNuNeIntereseaza;
							String celulaOS = readContentFromMergedCells(paginaOS, paginaOS.getMergedRegion(mergedCellIndex));
							String[] wordsCelulaOS = celulaOS.split("\\W+");
							
							boolean grupaGasita = false,numarGrupaGasita = false,subgrupaGasita = false,numarsubGrupaGasita = false;
							for(int i=0;i<wordsCelulaOS.length;i++)
							{
								//grupa

								if(wordsCelulaOS[i].contentEquals("GRUPA") || wordsCelulaOS[i].contains("Gr") )
								{
									grupaGasita = true;

								}
								if(wordsCelulaOS[i].contentEquals(dp.getsGrupa()) && grupaGasita == true)
								{
									numarGrupaGasita = true;
								}
								//subgrupa
								if(wordsCelulaOS[i].contentEquals("Subgrupa") || wordsCelulaOS[i].contains("Sg") )
								{
									subgrupaGasita = true;
								}
								if(wordsCelulaOS[i].contentEquals(dp.getsSubgrupa()) && subgrupaGasita == true)
								{
									numarsubGrupaGasita = true;
								}
								//afisare ora corecta+ ziua
								for(int indexZileOrar = 0;indexZileOrar < zileOrar.length;indexZileOrar++)
								{
									if(wordsCelulaOS[i].contains(zileOrar[indexZileOrar]))
									{
										indexPtZiua = coloanaOS;	
										System.out.println("Valoare celula ziua pentru index " + celulaOS +"\n");
										System.out.print(coloanaOS.getColumnIndex() + "\t");
										System.out.println("Primul rand :" + primulRand +"\n");
										matriceRandInceputZile[indexZileOrar]= primulRand;
										matriceRandSfarsitZile[indexZileOrar] = ultimulRand;
										System.out.println("Ultimul rand :" + ultimulRand+"\n");
									}
								}
								if(wordsCelulaOS[i].startsWith("8.00"))
								{
									// retin celula unde gaseste 8.00 de unde se poate extrage 
									indexPtOra = coloanaOS;	
									System.out.println("Valoare celula ora pentru index " + celulaOS +"\n");
									System.out.print(coloanaOS.getColumnIndex() + "\t");
								}

							}
							if(grupaGasita ==true && numarGrupaGasita == true && dp.getcerereOrar() == false)
							{
								System.out.print(randOS.getRowNum() + "\t");
								System.out.print(coloanaOS.getColumnIndex() + "\t");
								randDePlecare = randOS;
								coloanaDorita= coloanaOS;
								System.out.print(celulaOS + "\t"); 
								System.out.println("Prima coloana :" + primaColoana +"\n");
								System.out.println("Ultima Coloana :" + ultimaColoana +"\n");
								System.out.println("Esteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee grupaa");

							}
							if(subgrupaGasita ==true && numarsubGrupaGasita == true && dp.getcerereOrar() == true)
							{
								System.out.print(randOS.getRowNum() + "\t");
								randDePlecare = randOS;
								System.out.print(coloanaOS.getColumnIndex() + "\t");
								coloanaDorita= coloanaOS;
								System.out.print(celulaOS + "\t"); 
								System.out.println("Prima coloana :" + primaColoana +"\n");
								System.out.println("Ultima Coloana :" + ultimaColoana +"\n");
								System.out.println("Esteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee subgrupaa");

							}

						}
					}

				}

				else {
					String valoareCelulaOS = dataFormatterOS.formatCellValue(coloanaOS);
					if (valoareCelulaOS.isEmpty()) {
						continue;
					}
					else {
						//Pentru celule individuale gasire subgrupa buna
						String[] wordsCelulaOS = valoareCelulaOS.split("\\W+");
						boolean grupaGasita = false,numarGrupaGasita = false,subgrupaGasita = false,numarsubGrupaGasita = false;
						for(int i=0;i<wordsCelulaOS.length;i++)
						{
							if(wordsCelulaOS[i].contentEquals("GRUPA") || wordsCelulaOS[i].contains("Gr") )
							{
								grupaGasita = true;

							}
							if(wordsCelulaOS[i].contentEquals(dp.getsGrupa()) && grupaGasita == true)
							{
								numarGrupaGasita = true;
							}
							
							
							if(wordsCelulaOS[i].contentEquals("Subgrupa") || wordsCelulaOS[i].contains("Sg") )
							{
								subgrupaGasita = true;
							}
							if(wordsCelulaOS[i].contentEquals(dp.getsSubgrupa()) && subgrupaGasita == true)
							{
								numarsubGrupaGasita = true;
							}
							//System.out.println("Valoare celula ziua pentru index " + valoareCelulaOS +"\n");
							//afisare ora corecta+ ziua
							if(valoareCelulaOS.contains(zileOrar[0]))
							{
								indexPtZiua = coloanaOS;		
								System.out.println("Valoare celula ziua pentru index " + valoareCelulaOS +"\n");
								System.out.print(coloanaOS.getColumnIndex() + "\t");
							}
							if(valoareCelulaOS.contains("8.00"))
							{
								indexPtOra = coloanaOS;								
							}

						}
						if(grupaGasita ==true && numarGrupaGasita == true && dp.getcerereOrar() == false)
						{
							System.out.print(randOS.getRowNum() + "\t");
							randDePlecare = randOS;
							System.out.print(coloanaOS.getColumnIndex() + "\t");
							coloanaDorita= coloanaOS;
							System.out.print(valoareCelulaOS + "\t"); 
							//prima /ultima Coloana nu functioneaza bine pentru ca e strict pentru merge
//							System.out.println("Prima coloana :" + primaColoana +"\n");
//							System.out.println("Ultima Coloana :" + ultimaColoana +"\n");
							System.out.println("Esteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee grupaa");

						}
						if(subgrupaGasita ==true && numarsubGrupaGasita == true && dp.getcerereOrar() == true)
						{
							System.out.print(randOS.getRowNum() + "\t");
							randDePlecare = randOS;
							System.out.print(coloanaOS.getColumnIndex() + "\t");
							coloanaDorita= coloanaOS;
							System.out.print(valoareCelulaOS + "\t"); 
							//prima /ultima Coloana nu functioneaza bine pentru ca e strict pentru merge
//							System.out.println("Prima coloana :" + primaColoana +"\n");
//							System.out.println("Ultima Coloana :" + ultimaColoana +"\n");
							System.out.println("Esteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee subgrupaa");

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
        AfisareDateColoana(paginaOS,coloanaDorita , randDePlecare, dp.getcerereOrar());
//        ok = 0;
//        ValoareMergeCell = null;
//        ValoareNuNeIntereseaza = null;
//        iteratorRandOS = paginaOS.rowIterator();
//        while (iteratorRandOS.hasNext()) {
//        	Row randOS =null;
//        	randOS = iteratorRandOS.next();
//        	Iterator<Cell> iteratorColoanaOS = randOS.cellIterator();
//        	while (iteratorColoanaOS.hasNext()) {
//        		Cell coloanaOS =null;
//        		coloanaOS = iteratorColoanaOS.next();
//        		Integer mergedCellIndex = getIndexIfCellIsInMergedCells(paginaOS, randOS, coloanaOS);
//        		if(coloanaOS.getColumnIndex()==coloanaGrupa.getColumnIndex())
//        		{
//        			if (mergedCellIndex != null) {
//        				if(ok == 0) {
//        					ValoareMergeCell = readContentFromMergedCells(paginaOS, paginaOS.getMergedRegion(mergedCellIndex));
//        					ok = 1;
//        				}
//        				else {
//        					ValoareNuNeIntereseaza = readContentFromMergedCells(paginaOS, paginaOS.getMergedRegion(mergedCellIndex));
//        					if(ValoareMergeCell == ValoareNuNeIntereseaza || ValoareNuNeIntereseaza == null || ValoareNuNeIntereseaza == " ")
//        					{
//        						continue;
//        					}
//        					else {
//        						ValoareMergeCell = ValoareNuNeIntereseaza;
//        						String celulaOS = readContentFromMergedCells(paginaOS, paginaOS.getMergedRegion(mergedCellIndex));
//        						System.out.println("Cell is in a merged cell");
//        						System.out.println("Content is "+
//        								readContentFromMergedCells(paginaOS, paginaOS.getMergedRegion(mergedCellIndex)));
//
//        					}
//        				}
//        			}
//        			else {
//        				String valoareCelulaOS = dataFormatterOS.formatCellValue(coloanaOS);
//        				if (valoareCelulaOS.isEmpty()) {
//        					continue;
//        				}
//        				else {
//        					System.out.print("Cell is an individual cell");
//        					System.out.print(valoareCelulaOS + "\t");
//        				}
//        			}
//        		}
//        	}
//        }

        
        
        
        
        
        
        
        
        
//        int ok = 0;
//        String ValoareMergeCell = null;
//        String ValoareNuNeIntereseaza = null;
//        //Sheet paginaOS = workbookOS.getSheetAt(0);
//        int rand = 0,coloana = 0;
//        Iterator<Row> iteratorRandOS = paginaOS.rowIterator();
//        while (iteratorRandOS.hasNext()) {
//            Row randOS = iteratorRandOS.next();
//            // Now let's iterate over the columns of the current row
//            Iterator<Cell> iteratorColoanaOS = randOS.cellIterator();
//            while (iteratorColoanaOS.hasNext()) {
//                Cell coloanaOS = iteratorColoanaOS.next();
//                int plecare = coloanaOS.getColumnIndex();
//                Integer mergedCellIndex = getIndexIfCellIsInMergedCells(paginaOS, randOS, coloanaOS);
////                if(coloanaOS.getColumnIndex()==3)
////                {
////                    String valoareCelulaOS = dataFormatterOS.formatCellValue(coloanaOS);
////                    System.out.print(valoareCelulaOS + "\t");
////                }
//                if (mergedCellIndex != null) {
//                    // If it is in a merged cell
//                    // then get it
//                    CellRangeAddress cell = paginaOS.getMergedRegion(mergedCellIndex);
//                    // Do your logic here
////                    System.out.println("Cell is in a merged cell");
////                    System.out.println("Content is "+
////                            readContentFromMergedCells(paginaOS, paginaOS.getMergedRegion(mergedCellIndex)));
//                    if(ok == 0) {
//                    	ValoareMergeCell = readContentFromMergedCells(paginaOS, paginaOS.getMergedRegion(mergedCellIndex));
//                    	ok = 1;
//                    }
//                    else {
//                    	ValoareNuNeIntereseaza = readContentFromMergedCells(paginaOS, paginaOS.getMergedRegion(mergedCellIndex));
//                    	if(ValoareMergeCell == ValoareNuNeIntereseaza || ValoareNuNeIntereseaza == null || ValoareNuNeIntereseaza == " ")
//                        {
//                        	continue;
//                        }
//                        else {
//                        	ValoareMergeCell = ValoareNuNeIntereseaza;
//                        	System.out.println("Cell is in a merged cell");
//                        	System.out.println("Content is "+
//                                    readContentFromMergedCells(paginaOS, paginaOS.getMergedRegion(mergedCellIndex)));
//
//                        }
//                    }
//                    
//                    	
//                    int lastColumnOfThisMergedCell = paginaOS.getMergedRegion(mergedCellIndex).getLastColumn();
//                    //coloanaOS = coloanaOS + lastColumnOfThisMergedCell - 1;
//
//                //String valoareCelulaOS = dataFormatterOS.formatCellValue(coloanaOS);
//                //System.out.print(valoareCelulaOS + "\t");
//                }
//          
//                else {
//                    // If it is not in a merged cell
//                    // hence, an "individual" cell
//                    String valoareCelulaOS = dataFormatterOS.formatCellValue(coloanaOS);
//                    if (valoareCelulaOS.isEmpty()) {
//                        continue;
//                    }
//                    else {
//                        System.out.print("Cell is an individual cell");
//                        System.out.print(valoareCelulaOS + "\t");
//                    }
//                    // Then you can simply do your logic
//                    // and continue to the next loop
//                    //System.out.print("Cell is an individual cell");
//                    //System.out.print(valoareCelulaOS + "\t");
//                    
//                    
//                    System.out.println();
//                	}
//
//            	}
//            System.out.println();
//        }
		
	}
	
	
}
