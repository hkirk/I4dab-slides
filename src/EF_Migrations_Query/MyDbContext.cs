using Microsoft.EntityFrameworkCore;

namespace EF_Migrations_Query;

public class MyDbContext : DbContext
{
    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder) =>
        optionsBuilder.UseSqlServer(@"Data Source=127.0.0.1,1433;Database=book_demo;User ID=SA;Password=Password1;");

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Book>().HasIndex(b => b.Isbn).IsUnique();
    }
}