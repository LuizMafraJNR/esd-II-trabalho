package com.univille;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelExporter {

	public static void exportarResultados(String[] headers, Object[][] data) {
		try {
			String timestamp = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(new Date());
			String filename = Paths.get(System.getProperty("user.home"), "Documents", timestamp + ".csv").toString();
			FileWriter writer = new FileWriter(filename);

			// Escrever cabeçalhos (separados por ponto e vírgula)
			for (int i = 0; i < headers.length; i++) {
				writer.append(headers[i]);
				if (i < headers.length - 1) {
					writer.append(";");
				}
			}
			writer.append("\n");

			// Escrever dados
			for (Object[] row : data) {
				for (int i = 0; i < row.length; i++) {
					// Adicionar "ms" ao final dos valores numéricos (exceto a primeira coluna)
					if (row[i] instanceof Number && i > 0) {
						writer.append(row[i].toString()).append(" ms");
					} else {
						writer.append(row[i].toString());
					}
					if (i < row.length - 1) {
						writer.append(";");
					}
				}
				writer.append("\n");
			}

			writer.flush();
			writer.close();
			System.out.println("Arquivo CSV gerado: " + filename);
		} catch (IOException e) {
			System.err.println("Erro ao salvar o arquivo CSV: " + e.getMessage());
		}
	}

}
