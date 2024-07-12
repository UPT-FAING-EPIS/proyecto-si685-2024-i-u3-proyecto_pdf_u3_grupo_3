using Microsoft.AspNetCore.Mvc;
using PROYECTOPDFVFINAL.Models;
using System.Linq;
using Microsoft.AspNetCore.Http;

namespace PROYECTOPDFVFINAL.Controllers
{
    public class AccountController : Controller
    {
        private readonly ApplicationDbContext _context;
        private readonly ILogger<AccountController> _logger;

        public AccountController(ApplicationDbContext context, ILogger<AccountController> logger)
        {
            _context = context;
            _logger = logger;
        }

        // GET: Account/Register
        public IActionResult Register()
        {
            return View();
        }

        // POST: Account/Register
        [HttpPost]
        [ValidateAntiForgeryToken]
        public IActionResult Register(RegisterViewModel model)
        {
            if (ModelState.IsValid)
            {
                _logger.LogInformation("Registrando usuario con correo: {Correo}", model.Correo);

                var usuario = new Usuario
                {
                    Nombre = model.Nombre,
                    Correo = model.Correo,
                    Contraseña = model.Contraseña,
                };

                _context.Usuarios.Add(usuario);
                _context.SaveChanges();

                // Asignar suscripción básica
                var suscripcion = new DetalleSubscripcion
                {
                    IdUsuario = usuario.IdUsuario,
                    TipoSubscripcion = "BASICO",
                    OperacionesRealizadas = 0,
                    Precio = 0,
                    FechaInicio = DateTime.Now,
                    FechaFinal = DateTime.Now.AddYears(1)
                };
                _context.DetallesSubscripcion.Add(suscripcion);
                _context.SaveChanges();

                _logger.LogInformation("Usuario registrado con éxito.");
                return RedirectToAction("Login", "Account");
            }
            else
            {
                _logger.LogWarning("Error en el modelo de registro de usuario.");
                foreach (var error in ModelState)
                {
                    _logger.LogWarning($"{error.Key}: {string.Join(", ", error.Value.Errors.Select(e => e.ErrorMessage))}");
                }
            }
            return View(model);
        }

        // GET: Account/Login
        public IActionResult Login()
        {
            return View();
        }

        // POST: Account/Login
        [HttpPost]
        [ValidateAntiForgeryToken]
        public IActionResult Login(LoginViewModel model)
        {
            if (ModelState.IsValid)
            {
                _logger.LogInformation("Intentando iniciar sesión con correo: {Correo}", model.Correo);
                var usuario = _context.Usuarios.FirstOrDefault(u => u.Correo == model.Correo && u.Contraseña == model.Contraseña);

                if (usuario != null)
                {
                    _logger.LogInformation("Usuario encontrado, iniciando sesión.");
                    HttpContext.Session.SetInt32("UserId", usuario.IdUsuario);
                    return RedirectToAction("Dashboard", "Account");
                }
                else
                {
                    _logger.LogWarning("Correo o contraseña inválidos para el correo: {Correo}. Contraseña ingresada: {Contraseña}", model.Correo, model.Contraseña);
                    ModelState.AddModelError("", "Correo o contraseña inválidos");
                }
            }
            else
            {
                _logger.LogWarning("Modelo de inicio de sesión no es válido.");
            }
            return View(model);
        }

        // GET: Account/Dashboard
        public IActionResult Dashboard()
        {
            var userId = HttpContext.Session.GetInt32("UserId");
            if (userId == null)
            {
                _logger.LogWarning("Usuario no logueado, redirigiendo a la página de login.");
                return RedirectToAction("Login", "Account");
            }

            var usuario = _context.Usuarios
                .Where(u => u.IdUsuario == userId)
                .Select(u => new DashboardViewModel
                {
                    Nombre = u.Nombre,
                    TipoSubscripcion = u.DetallesSubscripcion.FirstOrDefault() != null ? u.DetallesSubscripcion.FirstOrDefault().TipoSubscripcion : "No tiene suscripción",
                    OperacionesRealizadas = u.DetallesSubscripcion.FirstOrDefault() != null ? u.DetallesSubscripcion.FirstOrDefault().OperacionesRealizadas : 0
                }).FirstOrDefault();

            if (usuario == null)
            {
                _logger.LogError("Usuario no encontrado en la base de datos.");
                return RedirectToAction("Login", "Account");
            }

            _logger.LogInformation("Cargando dashboard para el usuario: {Nombre}", usuario.Nombre);
            return View(usuario);
        }

        public IActionResult Logout()
        {
            HttpContext.Session.Clear();
            _logger.LogInformation("Usuario deslogueado.");
            return RedirectToAction("Login", "Account");
        }
        public IActionResult ComprarPremium()
        {
            var userId = HttpContext.Session.GetInt32("UserId");
            if (userId == null)
            {
                _logger.LogWarning("Usuario no logueado, redirigiendo a la página de login.");
                return RedirectToAction("Login", "Account");
            }

            var model = new CompraPremiumViewModel
            {
                Precio = 25,
                FechaInicio = DateTime.Now,
                FechaFinal = DateTime.Now.AddDays(30)
            };

            return View(model);
        }

        [HttpPost]
        public IActionResult ConfirmarCompraPremium()
        {
            var userId = HttpContext.Session.GetInt32("UserId");
            if (userId == null)
            {
                _logger.LogWarning("Usuario no logueado, redirigiendo a la página de login.");
                return RedirectToAction("Login", "Account");
            }

            var suscripcion = _context.DetallesSubscripcion.FirstOrDefault(ds => ds.IdUsuario == userId);
            if (suscripcion != null)
            {
                suscripcion.TipoSubscripcion = "PREMIUM";
                suscripcion.Precio = 25;
                suscripcion.FechaInicio = DateTime.Now;
                suscripcion.FechaFinal = DateTime.Now.AddDays(30);

                _context.DetallesSubscripcion.Update(suscripcion);
                _context.SaveChanges();

                _logger.LogInformation("Suscripción actualizada a Premium para el usuario con ID: {UserId}", userId);
            }
            return RedirectToAction("Dashboard");
        }
    }
}

