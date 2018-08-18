package br.com.fiap.roupas.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.Setter;

@Setter
public class TextPDFBuilder {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss-SSS");

	private String text;

	public byte[] build() {
		File file = new File(String.format("./temp/%s.pdf", DATE_TIME_FORMATTER.format(LocalDateTime.now())));
		file.getParentFile().mkdirs();
		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			
			Font font = new Font(FontFamily.COURIER, 8);

			document.add(new Paragraph(text, font));

			document.addSubject("Cupom Fiscal (CCF)");

			document.addKeywords("Ferramentas utilitárias");

			document.addCreator("FIAP");
			document.addCreator("30SCJ");

			document.addAuthor("André");
			document.addAuthor("Eduardo");
			document.addAuthor("Fernando");
			document.addAuthor("Marcos");
		} catch (FileNotFoundException | DocumentException e) {
			throw new RuntimeException(e);
		} finally {
			document.close();
		}
		return null;
	}

}
