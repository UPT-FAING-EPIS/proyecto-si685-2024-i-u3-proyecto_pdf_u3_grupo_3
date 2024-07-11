
package Servicio;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 *
 * @author Mario
 */
public class CortadorPDF {
public String cortarPDF(InputStream archivoPDF, int inicio, int fin, ByteArrayOutputStream outputStream) {
        Document document = null;
        try {
            PdfReader reader = new PdfReader(archivoPDF);
            int numeroPaginas = reader.getNumberOfPages();
            if (inicio < 1 || fin > numeroPaginas || inicio > fin) {
                return "Rango de páginas inválido.";
            }

            document = new Document();
            PdfCopy copy = new PdfCopy(document, outputStream);
            document.open();
            for (int i = inicio; i <= fin; i++) {
                copy.addPage(copy.getImportedPage(reader, i));
            }
            reader.close();
            return "PDF cortado exitosamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al cortar PDF: " + e.getMessage();
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }
}
