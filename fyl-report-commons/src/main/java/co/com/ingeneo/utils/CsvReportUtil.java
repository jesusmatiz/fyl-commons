package co.com.ingeneo.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import co.com.ingeneo.utils.messages.MessageLocale;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CsvReportUtil {

	public byte[] createFile(ResultSet querySet, String head) throws IOException {
		log.trace("[Start][CsvReportUtil][createFile]");
		String fileName = BusinessConstants.TEMPORARY_NAME_REPORT + System.currentTimeMillis();
		
		String encoding = "UTF-8";
		Locale locale =  MessageLocale.getLocale();
		if (locale != null && !locale.getLanguage().startsWith("en")) {
			encoding = "CP1252";
		}

		try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), encoding))){
			pw.println(head.startsWith("ID") ?  head.replaceFirst("ID", "Id") : head);

			List<String> queryRows = getQueryRows(querySet);

			//Se adicionan las filas, que vienen de la consulta
			for (int j = 1; j <= queryRows.size(); j++) {
				pw.println(queryRows.get(j - 1));
			}

		} catch (Exception e) {
			log.error("[Error][CsvReportUtil][createFile]Error", e);
		}

		byte[] reportFile = convertDocToByteArray(fileName);
		deleteFile(fileName);
		log.trace("[End][CsvReportUtil][createFile]");
		return reportFile;

	}

	public List<String> getQueryRows(ResultSet querySet) throws SQLException {
		log.trace("[Start][CsvReportUtil][getQueryRows]");
		List<String> rows = new ArrayList<>();
		ResultSetMetaData metaData = querySet.getMetaData();
		int columnCount = metaData.getColumnCount();

		while (querySet.next()) {
			for (int i = 1; i <= columnCount; i++) {
				String row = querySet.getObject(i).toString();
				rows.add(row);
			}
		}
		log.trace("[End][CsvReportUtil][getQueryRows]");
		return rows;
	}
	
	public void downloadFile(ResultSet querySet, String head, HttpServletResponse response, String fileName) {
		log.trace("[Start][CsvReportUtil][downloadFile]");

		response.setContentType("application/octet-stream");

		if (fileName == null) {
			fileName = BusinessConstants.TEMPORARY_NAME_REPORT + System.currentTimeMillis() + ".csv";
		}

		response.setHeader("Content-Disposition", "attachment; filename="
				+ fileName);
		
		String encoding = "UTF-8";		
		Locale locale =  MessageLocale.getLocale();
		if (locale != null && !locale.getLanguage().startsWith("en")) {
			encoding = "CP1252";
		}

		try (PrintWriter bufOut = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), encoding))){

			bufOut.println(head.startsWith("ID") ?  head.replaceFirst("ID", "Id") : head);		

			while (querySet.next()) {
				bufOut.write(querySet.getString(1) + "\n");
			}
		} catch (Exception e) {
			log.error("[Error][CsvReportUtil][downloadFile]Error generating CSV file", e);
		}
		log.trace("[End][CsvReportUtil][downloadFile]");

	}
	
	public byte[] convertDocToByteArray(String sourcePath) throws IOException {
		log.trace("[Start][CsvReportUtil][convertDocToByteArray]");
		InputStream inputStream;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		inputStream = new FileInputStream(sourcePath);
		byte[] buffer = new byte[1024];
		int bytesRead;

		while ((bytesRead = inputStream.read(buffer)) != -1) {
		  baos.write(buffer, 0, bytesRead);
		}

		inputStream.close();

		log.trace("[End][CsvReportUtil][convertDocToByteArray]");
		return baos.toByteArray();
	}

	public void deleteFile(String path) {
		log.trace("[Start][CsvReportUtil][deleteFile]");
		File file = new File(path);
		file.delete();
		log.trace("[End][CsvReportUtil][deleteFile]");
	}
}
