
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PdfSharpCore.Pdf.IO;
using PdfSharpCore.Pdf;

namespace PROYECTOPDFVFINAL.Controllers
{
    public class PdfController : Controller
    {
        private readonly ILogger<PdfController> _logger;

        public PdfController(ILogger<PdfController> logger)
        {
            _logger = logger;
        }

        // GET: Pdf/OperacionesPDF
        public IActionResult OperacionesPDF()
        {
            return View();
        }

        // GET: Pdf/FusionarPDF
        public IActionResult FusionarPDF()
        {
            return View();
        }

        // GET: Pdf/CortarPDF
        public IActionResult CortarPDF()
        {
            return View();
        }

        // POST: Pdf/CortarPDF
        [HttpPost]
        public IActionResult CortarPDF(IFormFile pdf, int startPage, int endPage)
        {
            if (pdf == null || startPage <= 0 || endPage <= 0 || startPage > endPage)
            {
                _logger.LogWarning("Datos no válidos para cortar el PDF.");
                TempData["ErrorMessage"] = "Por favor selecciona un archivo PDF y páginas válidas.";
                return RedirectToAction("CortarPDF");
            }

            try
            {
                var tempFilePath = Path.Combine(Path.GetTempPath(), "CroppedPDF.pdf");

                using (var inputDocument = PdfReader.Open(pdf.OpenReadStream(), PdfDocumentOpenMode.Import))
                {
                    using (var outputDocument = new PdfDocument())
                    {
                        for (int i = startPage - 1; i < endPage && i < inputDocument.PageCount; i++)
                        {
                            outputDocument.AddPage(inputDocument.Pages[i]);
                        }

                        outputDocument.Save(tempFilePath);
                    }
                }

                TempData["CroppedPdfPath"] = tempFilePath;
                return RedirectToAction("CortarPDF");
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error al cortar el archivo PDF.");
                TempData["ErrorMessage"] = "Ocurrió un error al cortar el archivo PDF. Inténtalo nuevamente.";
                return RedirectToAction("CortarPDF");
            }
        }

        // GET: Pdf/DescargarCortadoPDF
        public IActionResult DescargarCortadoPDF()
        {
            var tempFilePath = TempData["CroppedPdfPath"] as string;
            if (string.IsNullOrEmpty(tempFilePath) || !System.IO.File.Exists(tempFilePath))
            {
                TempData["ErrorMessage"] = "No se encontró el archivo PDF cortado.";
                return RedirectToAction("CortarPDF");
            }

            var fileBytes = System.IO.File.ReadAllBytes(tempFilePath);
            TempData.Remove("CroppedPdfPath"); // Remove temp file path from TempData after use
            return File(fileBytes, "application/pdf", "CroppedPDF.pdf");
        }
    }
}
