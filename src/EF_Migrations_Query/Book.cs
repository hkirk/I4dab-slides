using Microsoft.EntityFrameworkCore;

namespace EF_Migrations_Query;

public class Book
{
    public int BookId { get; set; }
    
    public string Isbn { get; set; }
    public string Title { get; set; }
    public Author Author { get; set; }
    public List<Review> Reviews { get; set; }
}

public class Author
{
    public int AuthorId { get; set; }
    public string FirstName { get; set; }
    public string LastName { get; set; }
    
    public int BookId { get; set; }
    public Book Book { get; set; }
}

public class Review
{
    public int ReviewId { get; set; }
    public int Rating { get; set; }
    
    public int BookId { get; set; }
    public Book Book { get; set; }

    public Voter Voter { get; set; }
}

public class Voter
{
    public int VoterId { get; set; }
    public string Username { get; set; }
}
