using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace EF_Migrations_Query.Migrations
{
    public partial class Nullables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Review_Voter_VoterId",
                table: "Review");

            migrationBuilder.DropIndex(
                name: "IX_Book_Isbn",
                table: "Book");

            migrationBuilder.AlterColumn<string>(
                name: "Username",
                table: "Voter",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)");

            migrationBuilder.AlterColumn<int>(
                name: "VoterId",
                table: "Review",
                type: "int",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<string>(
                name: "Title",
                table: "Book",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)");

            migrationBuilder.AlterColumn<string>(
                name: "Isbn",
                table: "Book",
                type: "nvarchar(450)",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(450)");

            migrationBuilder.AlterColumn<string>(
                name: "LastName",
                table: "Author",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)");

            migrationBuilder.AlterColumn<string>(
                name: "FirstName",
                table: "Author",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)");

            migrationBuilder.CreateIndex(
                name: "IX_Book_Isbn",
                table: "Book",
                column: "Isbn",
                unique: true,
                filter: "[Isbn] IS NOT NULL");

            migrationBuilder.AddForeignKey(
                name: "FK_Review_Voter_VoterId",
                table: "Review",
                column: "VoterId",
                principalTable: "Voter",
                principalColumn: "VoterId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Review_Voter_VoterId",
                table: "Review");

            migrationBuilder.DropIndex(
                name: "IX_Book_Isbn",
                table: "Book");

            migrationBuilder.AlterColumn<string>(
                name: "Username",
                table: "Voter",
                type: "nvarchar(max)",
                nullable: false,
                defaultValue: "",
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "VoterId",
                table: "Review",
                type: "int",
                nullable: false,
                defaultValue: 0,
                oldClrType: typeof(int),
                oldType: "int",
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "Title",
                table: "Book",
                type: "nvarchar(max)",
                nullable: false,
                defaultValue: "",
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "Isbn",
                table: "Book",
                type: "nvarchar(450)",
                nullable: false,
                defaultValue: "",
                oldClrType: typeof(string),
                oldType: "nvarchar(450)",
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "LastName",
                table: "Author",
                type: "nvarchar(max)",
                nullable: false,
                defaultValue: "",
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "FirstName",
                table: "Author",
                type: "nvarchar(max)",
                nullable: false,
                defaultValue: "",
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_Book_Isbn",
                table: "Book",
                column: "Isbn",
                unique: true);

            migrationBuilder.AddForeignKey(
                name: "FK_Review_Voter_VoterId",
                table: "Review",
                column: "VoterId",
                principalTable: "Voter",
                principalColumn: "VoterId",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
