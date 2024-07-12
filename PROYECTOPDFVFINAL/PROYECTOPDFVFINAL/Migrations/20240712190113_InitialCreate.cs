using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace PROYECTOPDFVFINAL.Migrations
{
    /// <inheritdoc />
    public partial class InitialCreate : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Usuarios",
                columns: table => new
                {
                    IdUsuario = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Nombre = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Correo = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Contraseña = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Usuarios", x => x.IdUsuario);
                });

            migrationBuilder.CreateTable(
                name: "DetallesSubscripcion",
                columns: table => new
                {
                    IdDetalleSubscripcion = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    TipoSubscripcion = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    FechaInicio = table.Column<DateTime>(type: "datetime2", nullable: false),
                    FechaFinal = table.Column<DateTime>(type: "datetime2", nullable: false),
                    Precio = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    OperacionesRealizadas = table.Column<int>(type: "int", nullable: false),
                    IdUsuario = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_DetallesSubscripcion", x => x.IdDetalleSubscripcion);
                    table.ForeignKey(
                        name: "FK_DetallesSubscripcion_Usuarios_IdUsuario",
                        column: x => x.IdUsuario,
                        principalTable: "Usuarios",
                        principalColumn: "IdUsuario",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_DetallesSubscripcion_IdUsuario",
                table: "DetallesSubscripcion",
                column: "IdUsuario");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "DetallesSubscripcion");

            migrationBuilder.DropTable(
                name: "Usuarios");
        }
    }
}
