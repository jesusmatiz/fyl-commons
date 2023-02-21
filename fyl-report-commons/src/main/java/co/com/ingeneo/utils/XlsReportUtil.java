package co.com.ingeneo.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/*Librer�as para trabajar con archivos excel*/
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;




public class XlsReportUtil {	
	
	
	
	public byte[] createFile(ResultSet querySet, String head) throws IOException {
				
		String fileName = BusinessConstants.TEMPORARY_NAME_REPORT + System.currentTimeMillis();
        File fileXLS = new File(fileName);
        
        Workbook book = new HSSFWorkbook();
        FileOutputStream file = null;
		try {
			file = new FileOutputStream(fileXLS);
				
        Sheet sheet = book.createSheet(fileName);    	

		String[] headReport = head.split(";");
		int columnCount = headReport.length;	
    	//se crea el encabezado
		Row rowHeader = sheet.createRow(0);
    	for (int i = 0; i < columnCount; i++) {
    		  Cell cell = rowHeader.createCell(i);
    		  cell.setCellValue(headReport[i]);		
		}
    	
    	List<String> queryRows = getQueryRows(querySet);
    	//se adicionan las filas,que vienen de la consulta
    	for (int j = 1; j <= queryRows.size(); j++) {
			String[] rowData = queryRows.get(j -1).split(";");
			Row row = sheet.createRow(j);
			for (int i = 0; i < rowData.length; i++) {
				Cell cell = row.createCell(i);				
				if(rowData.length < columnCount){
					if( i == rowData.length){						
						break;
					}					
				}
				cell.setCellValue(rowData[i]);
			}			
		}} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	book.write(file);
    	file.close();
    	byte[] reportFile = null;
    	reportFile = convertDocToByteArray(fileName);
    	deleteFile(fileName);
    	
    	
    	return reportFile;

		
	}	
	
	
	public byte[] createFile(List<String> queryRows, String head) throws IOException {
		
		String fileName = BusinessConstants.TEMPORARY_NAME_REPORT + System.currentTimeMillis();
        File fileXLS = new File(fileName);
        
        Workbook book = new HSSFWorkbook();
        FileOutputStream file = null;
		try {
			file = new FileOutputStream(fileXLS);
				
        Sheet sheet = book.createSheet(fileName);    	

		String[] headReport = head.split(";");
		int columnCount = headReport.length;	
    	//se crea el encabezado
		Row rowHeader = sheet.createRow(0);
    	for (int i = 0; i < columnCount; i++) {
    		  Cell cell = rowHeader.createCell(i);
    		  cell.setCellValue(headReport[i]);		
		}
   
    	//se adicionan las filas,que vienen de la consulta
    	for (int j = 1; j <= queryRows.size(); j++) {
			String[] rowData = queryRows.get(j -1).split(";");
			Row row = sheet.createRow(j);
			for (int i = 0; i < rowData.length; i++) {
				Cell cell = row.createCell(i);				
				if(rowData.length < columnCount){
					if( i == rowData.length){						
						break;
					}					
				}
				cell.setCellValue(rowData[i]);
			}			
		}} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	book.write(file);
    	file.close();
    	byte[] reportFile = null;
    	reportFile = convertDocToByteArray(fileName);
    	deleteFile(fileName);
    	
    	
    	return reportFile;

		
	}	
	
		
	public List<String> getQueryRows(ResultSet querySet) throws SQLException{
		
		List<String> rows = new ArrayList<String>();
		ResultSetMetaData metaData = querySet.getMetaData();
		int columnCount = metaData.getColumnCount();
		
		while (querySet.next()) 
		{
			for (int i = 1; i <= columnCount; i++) 
			{
				String row = querySet.getObject(i).toString();
				rows.add(row);				
			}			
		}
				
		return rows;		
	}
	
	/**
	 * Generate and download a EXCEL file from ResultSet.
	 * 
	 * @param querySet
	 *            Result set.
	 * @param head
	 *            Header information.
	 * @param response
	 *            HttpServletResponse
	 * @param fileName
	 *            File name *
	 */
	public void downloadFile(ResultSet querySet, String head,
			HttpServletResponse response, String fileName) {

		response.setContentType("application/vnd.ms-excel");

		if (fileName == null) {
			fileName = BusinessConstants.TEMPORARY_NAME_REPORT
					+ System.currentTimeMillis() + ".xls";

		}

		response.setHeader("Content-Disposition", "attachment; filename="
				+ fileName);

		try {

			/* Create the WorkBook */
			Workbook book = new HSSFWorkbook();

			/* Create the sheet */
			Sheet sheet = book.createSheet(fileName);

			String[] headReport = head.split(";");
			int columnCount = headReport.length;

			/* Create the header at row 0 */
			Row rowHeader = sheet.createRow(0);
			for (int i = 0; i < columnCount; i++) {
				Cell cell = rowHeader.createCell(i);
				cell.setCellValue(headReport[i]);
			}

			int contRows = 1;
			/* Iterate the result set to generate the create the rows and cells */
			while (querySet.next()) {
				String[] rowData = querySet.getString(1).split(";", -1);
				/* Create the cell */
				Row row = sheet.createRow(contRows);
				for (int i = 0; i < rowData.length; i++) {
					/* Create the cell */
					Cell cell = row.createCell(i);
					cell.setCellValue(rowData[i]);
				}
				contRows++;
			}

			/* Write the report */
			OutputStream out = response.getOutputStream();
			book.write(out);
			out.close();

		} catch (Exception e) {
			System.out.println("Error generating excel file");
			e.printStackTrace();
		}

	}
	
	public byte[] convertDocToByteArray(String sourcePath) throws IOException {

		   
	      InputStream inputStream = null;
	      ByteArrayOutputStream baos = new ByteArrayOutputStream();
	      
	      inputStream = new FileInputStream(sourcePath);
	      byte[] buffer = new byte[1024];
	      int bytesRead;
	      
	      while ((bytesRead = inputStream.read(buffer)) != -1)
	      {
	    	  baos.write(buffer, 0, bytesRead);	    	  
	      }
	      
	      inputStream.close();
	      
	      return baos.toByteArray();
	      
	}
	
	public void deleteFile(String path){
		
		File file = new File(path);
		file.delete();
		
	}
	
	
}


