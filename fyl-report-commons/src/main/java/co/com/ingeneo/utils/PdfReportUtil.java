package co.com.ingeneo.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfReportUtil {

	
	
	public PdfPTable createTable(ResultSet querySet, String head) {
		
		try {
			
			String[] headReport = head.split(";");
			int columnCount = headReport.length;				
			
			PdfPTable table = new PdfPTable(columnCount);			
			table.setHeaderRows(1);
			table.setWidthPercentage(100);
			PdfPCell cell;
			Font fontHeader = new Font();
			fontHeader.setSize(10);
			fontHeader.setStyle(Font.BOLD);		
		
			for (int i = 0; i < columnCount; i++) {
				cell = new PdfPCell(new Phrase(headReport[i],fontHeader));
				cell.setColspan(1);
				cell.setHorizontalAlignment(1);				
				
				table.addCell(cell);				
			}		
			
			List<String> queryRows = getQueryRows(querySet);
			Font fontBody = new Font();
			fontHeader.setSize(8);
			for (String string : queryRows) {
				String[] row = string.split(";", -1);
				
				for (int i = 0; i < headReport.length; i++) {
					cell = new PdfPCell(new Phrase(row[i], fontBody));
					table.addCell(cell);
					
					/*if(row.length < columnCount){
						if(i >= row.length){
							cell = new PdfPCell(new Phrase("",fontBody));
							table.addCell(cell);						
						}else{
							cell = new PdfPCell(new Phrase(row[i],fontBody));
							table.addCell(cell);							
						}						
					}else{
						cell = new PdfPCell(new Phrase(row[i],fontBody));						
						table.addCell(cell);						
					}*/				
				}
				
			}			

			return table;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public PdfPTable createTable(List<String> queryRows, String head) {
		
		try {
			
			String[] headReport = head.split(";");
			int columnCount = headReport.length;				
			
			PdfPTable table = new PdfPTable(columnCount);			
			table.setHeaderRows(1);
			table.setWidthPercentage(100);
			PdfPCell cell;	
			Font fontHeader = new Font();
			fontHeader.setSize(10);
			fontHeader.setStyle(Font.BOLD);		
		
			for (int i = 0; i < columnCount; i++) {
				cell = new PdfPCell(new Phrase(headReport[i],fontHeader));
				cell.setColspan(1);
				cell.setHorizontalAlignment(1);				
				
				table.addCell(cell);				
			}		
			
			//List<String> queryRows = getQueryRows(querySet);
			Font fontBody = new Font();
			fontHeader.setSize(8);
			for (String string : queryRows) {
				String[] row = string.split(";");
				
				for (int i = 0; i < columnCount; i++) {
					
					if(row.length < columnCount){
						if(i >= row.length){
							cell = new PdfPCell(new Phrase("",fontBody));
							table.addCell(cell);						
						}else{
							cell = new PdfPCell(new Phrase(row[i],fontBody));
							table.addCell(cell);							
						}						
					}else{
						cell = new PdfPCell(new Phrase(row[i],fontBody));						
						table.addCell(cell);						
					}					
				}
				
			}			

			return table;
		} catch (Exception e) {
			return null;
		}
	}
	
	public byte[] generatePdfReport(ResultSet querySet, String head) throws IOException, DocumentException {
		
		byte[] reportFile = null;
		Document pdfDocument;
		
		if(head.split(";").length > 8){
			pdfDocument = new Document(PageSize.A2.rotate());
		}else{
			pdfDocument = new Document(PageSize.A4);
		}		
		String fileName = BusinessConstants.TEMPORARY_NAME_REPORT + System.currentTimeMillis();
		FileOutputStream reportPDF = new FileOutputStream(fileName);
				
		PdfWriter.getInstance(pdfDocument, reportPDF);
		pdfDocument.open();
		pdfDocument.add(createTable(querySet,head));

		pdfDocument.close();
		
		reportFile = convertDocToByteArray(fileName);	
		deleteFile(fileName);
		
		return reportFile;
		
	}
	
	
	public byte[] generatePdfReport(List<String> list, String head) throws DocumentException, IOException{
		
		byte[] reportFile = null;
		Document pdfDocument;
		
		if(head.split(";").length > 8){
			pdfDocument = new Document(PageSize.A2.rotate());			
		}else{
			pdfDocument = new Document(PageSize.A4);
		}		
		String fileName = BusinessConstants.TEMPORARY_NAME_REPORT + System.currentTimeMillis();
		FileOutputStream reportPDF = new FileOutputStream(fileName);
				
		PdfWriter.getInstance(pdfDocument, reportPDF);
		pdfDocument.open();
		pdfDocument.add(createTable(list,head));

		pdfDocument.close();
		
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
			String row = "";
			for (int i = 1; i <= columnCount; i++) 
			{
				if(querySet.getObject(i) == null){
					 row += "";
					
				}else{
					 row += querySet.getObject(i).toString() ;					
				}
				rows.add(row);				
			}			
		}
				
		return rows;		
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
  
	/**
	 * Generate and download a PDF file from ResultSet.
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
	public void downlaodFile(ResultSet querySet, String head,
			HttpServletResponse response, String fileName) {

		response.setContentType("application/pdf");

		if (fileName == null) {
			fileName = BusinessConstants.TEMPORARY_NAME_REPORT
					+ System.currentTimeMillis() + ".pdf";
		}

		response.setHeader("Content-Disposition", "attachment; filename="
				+ fileName);

		try {

			String[] headReport = head.split(";");

			Document pdfDocument;

			/* Setting size */
			if (head.split(";").length > 8) {
				pdfDocument = new Document(PageSize.A2.rotate());
			} else {
				pdfDocument = new Document(PageSize.A4);
			}

			PdfWriter.getInstance(pdfDocument, response.getOutputStream());

			int columnCount = headReport.length;

			/* Configure table */
			PdfPTable table = new PdfPTable(columnCount);
			table.setHeaderRows(1);
			table.setWidthPercentage(100);

			/* Font header */
			Font fontHeader = new Font();
			fontHeader.setSize(10);
			fontHeader.setStyle(Font.BOLD);

			/* Font body */
			Font fontBody = new Font();
			fontHeader.setSize(8);

			/* Cell var */
			PdfPCell cell;

			/* Header */
			for (int i = 0; i < columnCount; i++) {
				cell = new PdfPCell(new Phrase(headReport[i], fontHeader));
				cell.setColspan(1);
				cell.setHorizontalAlignment(1);

				table.addCell(cell);
			}

			/* Iterate the result to create the cells */
			while (querySet.next()) {
				String[] rowData = querySet.getString(1).split(";",-1);
				/* Create the cell */

				for (int i = 0; i < rowData.length; i++) {
					/* Create the cell */
					cell = new PdfPCell(new Phrase(rowData[i], fontBody));
					table.addCell(cell);
				}
			}

			pdfDocument.open();
			pdfDocument.add(table);
			pdfDocument.close();
		} catch (Exception e) {
			System.out.println("Error generating PDF file");
			e.printStackTrace();
		}
	}
	
}
