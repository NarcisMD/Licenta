import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
public class ReadOrar {
	
	public static final String Orar_Sali_Path = "./Orar_Sali.xls";
	public static final String Orar_Cadru_Didactic_Path = "C:\\Users\\narci\\eclipse-workspace\\IncercareaVietii2\\Orar_Cadru_Didactic.xlsx";
	
	private static MissingCellPolicy Row;
	
	private static Integer getIndexIfCellIsInMergedCells(Sheet sheet, Row randOCD, Cell coloanaOCD) {
	    int numberOfMergedRegions = sheet.getNumMergedRegions();

	    for (int i = 0; i < numberOfMergedRegions; i++) {
	        CellRangeAddress mergedCell = sheet.getMergedRegion(i);

	        if (mergedCell.isInRange(coloanaOCD)) {
	            return i;
	        }
	    }

	    return null;
	}
	
	private static String readContentFromMergedCells(Sheet sheet, CellRangeAddress mergedCells) {

	    if (mergedCells.getFirstRow() != mergedCells.getLastRow()) {
	        return null;
	    }

	    return sheet.getRow(mergedCells.getFirstRow()).getCell(mergedCells.getFirstColumn()).getStringCellValue();
	}
	
	
	public static void main(String[] args) throws IOException, InvalidFormatException{
		Workbook workbookOS = WorkbookFactory.create(new File(Orar_Sali_Path));
		Workbook workbookOCD = WorkbookFactory.create(new File(Orar_Cadru_Didactic_Path));
		
		
		
		/* ********************************************************* */
		// Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatterOS = new DataFormatter();
        
		System.out.println("WorkbookOS are " + workbookOS.getNumberOfSheets() + "pagini : ");
		
		
        Iterator<Sheet> iteratorPagOS = workbookOS.sheetIterator();
        System.out.println("Extragere nume pagina");
        while (iteratorPagOS.hasNext()) {
            Sheet paginaOS = iteratorPagOS.next();
            System.out.println("Pagina:" + paginaOS.getSheetName());
        }
        
        
        Sheet paginaOS = workbookOS.getSheetAt(0);
        
        Iterator<Row> iteratorRandOS = paginaOS.rowIterator();
        while (iteratorRandOS.hasNext()) {
            Row randOS = iteratorRandOS.next();

            // Now let's iterate over the columns of the current row
            Iterator<Cell> iteratorColoanaOS = randOS.cellIterator();

            while (iteratorColoanaOS.hasNext()) {
                Cell coloanaOS = iteratorColoanaOS.next();
                String valoareCelulaOS = dataFormatterOS.formatCellValue(coloanaOS);
                System.out.print(valoareCelulaOS + "\t");
            }
            System.out.println();
        }
		
        
        /* ***************************************************************** */
        DataFormatter dataFormatterOCD = new DataFormatter();
        
		System.out.println("WorkbookOCD are" + workbookOCD.getNumberOfSheets() + " pagini : ");
		
        Iterator<Sheet> iteratorPagOCD = workbookOCD.sheetIterator();
        System.out.println("Extragere nume pagina");
        while (iteratorPagOCD.hasNext()) {
            Sheet paginaOCD = iteratorPagOCD.next();
            System.out.println("Pagina:" + paginaOCD.getSheetName());
        }
        
        Sheet paginaOCD = workbookOCD.getSheetAt(0);

        // 1. You can obtain a rowIterator and columnIterator and iterate over them
        System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
        
        Iterator<Row> iteratorRandOCD = paginaOCD.rowIterator();
        while (iteratorRandOCD.hasNext()) {
            Row randOCD = iteratorRandOCD.next();

            int lastColumn = randOCD.getLastCellNum();
            
            // Now let's iterate over the columns of the current row
            Iterator<Cell> iteratorColoanaOCD = randOCD.cellIterator();

            while (iteratorColoanaOCD.hasNext()) {
                Cell coloanaOCD = iteratorColoanaOCD.next();
                Integer mergedCellIndex = getIndexIfCellIsInMergedCells(paginaOCD, randOCD, coloanaOCD);
//                if(coloanaOCD.getColumnIndex()==3)
//                {
//                    String valoareCelulaOCD = dataFormatterOCD.formatCellValue(coloanaOCD);
//                    System.out.print(valoareCelulaOCD + "\t");
//                }
                if (mergedCellIndex != null) {
                    // If it is in a merged cell
                    // then get it
                    CellRangeAddress cell = paginaOCD.getMergedRegion(mergedCellIndex);

                    // Do your logic here
                    System.out.println("Cell is in a merged cell");
                    System.out.println("Content is "+
                            readContentFromMergedCells(paginaOCD, paginaOCD.getMergedRegion(mergedCellIndex)));
                //String valoareCelulaOCD = dataFormatterOCD.formatCellValue(coloanaOCD);
                //System.out.print(valoareCelulaOCD + "\t");
            }
                else {
                    // If it is not in a merged cell
                    // hence, an "individual" cell
                    String valoareCelulaOCD = dataFormatterOCD.formatCellValue(coloanaOCD);
                    
                    if (valoareCelulaOCD == null) {
                        continue;
                    }

                    // Then you can simply do your logic
                    // and continue to the next loop
                    System.out.print("Cell is an individual cell");
                    System.out.print(valoareCelulaOCD + "\t");
                    
            System.out.println();
        }
        

     
	}
	

	
        }
 }
}

