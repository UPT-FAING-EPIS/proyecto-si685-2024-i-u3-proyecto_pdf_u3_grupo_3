
package Servicio;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author Mario
 */
public class FusionadorPDF {
 public String fusionarPDFs(List<InputStream> archivosPDF, ByteArrayOutputStream outputStream) {
        Document document = null;
        try {
            document = new Document();
            PdfCopy copy = new PdfCopy(document, outputStream);
            document.open();
            for (InputStream archivo : archivosPDF) {
                PdfReader reader = new PdfReader(archivo);
                copy.addDocument(reader);
                reader.close();
            }
            return "PDF fusionado exitosamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al fusionar PDFs: " + e.getMessage();
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }
}
