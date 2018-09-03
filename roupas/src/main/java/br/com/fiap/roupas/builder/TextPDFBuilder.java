package br.com.fiap.roupas.builder;

import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Setter
public class TextPDFBuilder {

    private String text;

    private File file;


    public Optional<byte[]> build() {
        generateFilePDF(file);
        return extractBinaryFile(file);
    }

    private Optional<byte[]> extractBinaryFile(File file) {
        if (!file.exists() || !file.isFile()) {
            return Optional.empty();
        }

        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] binary = IOUtils.toByteArray(inputStream);
            return Optional.of(binary);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateFilePDF(File file) {
        file.getParentFile().mkdirs();

        int totalOfLines = StringUtils.countMatches(text, "\n") + 1;
        float height = (float) (6 + (totalOfLines * 7.5));
        Rectangle pageSize = new Rectangle(190, height);
        Document document = new Document(pageSize, 3, 3, 3, 3);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font font = new Font(FontFamily.COURIER, 5);

            Paragraph paragraph = new Paragraph(text, font);
            document.add(paragraph);

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
    }

}
