// See https://aka.ms/new-console-template for more information

using System.Net;
using EF_Migrations_Query;
using Microsoft.EntityFrameworkCore;

Console.WriteLine("Hello, World!");


using (var context = new MyDbContext())
{
    var poulChiusano = new Author()
    {
        FirstName = "Poul",
        LastName = "Chiusano",
    };
    
    var book = CreateBook(context, "123456", "Functional Programming in Scala", poulChiusano);

    InitData(context);

    var theArtQuery1 = context.Book.Where(b => b.Title.StartsWith("The Art"));
    var theArtBooks1 = theArtQuery1.ToList();

    var theArtBook2 = from b in context.Book
        where b.Title.StartsWith("The Art")
        select b;


    var review = new Review()
    {
        Book = book,
        Rating = 3,
    };
    context.Add(review);
    await context.SaveChangesAsync();
    var reviews = await context.Review.ToListAsync();

    var books1 = await ReadAllBooksWithAuthorExplicit(context);
    var books2 = await ReadAllBooksEager(context);
    var books3 = await ReadAllBooksEagerMultipleLevels(context);
    var books4 = await ReadAllBooksSelect(context);
    

    UpdateBook(context, "Functional Programming in Scala", "9781617290657");
    DeleteData(context, "9781617290657");
}

void InsertReview(MyDbContext context,  Book book, int rating, Voter voter)
{
    context.Add(new Review()
    {
        Rating = rating,
        Book = book,
        Voter = voter
    });
}

Book CreateBook(MyDbContext context, string isbn,
                        string title, Author? author)
{
    var book = new Book()
    {
        Isbn = isbn,
        Title = title,
        Author = author
    };

    context.Add(book);
    context.SaveChanges();
    return book;
}

void UpdateBook(MyDbContext context, string title, string isbn)
{
    var book = context.Book.Single(p => p.Title == title);
    book.Isbn = isbn;

    context.SaveChanges();
}

void DeleteData(MyDbContext context, string isbn)
{
    var book = context.Book.Single(b => b.Isbn == isbn);
    context.Remove(book);

    context.SaveChanges();
}

async Task<IEnumerable<Book>> ReadAllBooksWithAuthorExplicit(MyDbContext myDbContext)
{
    var books = await myDbContext.Book.ToListAsync();
    foreach (var book in books)
    {
        await myDbContext.Entry(book).Reference(b => b.Author).LoadAsync();
    }

    return books;
}

async Task<IEnumerable<Book>> ReadAllBooksEager(MyDbContext myDbContext)
{
    return await myDbContext.Book
        .Include(b => b.Author)
        .Include(b => b.Reviews)
        .ToListAsync();
}

async Task<IEnumerable<Book>> ReadAllBooksEagerMultipleLevels(MyDbContext myDbContext)
{
    return await myDbContext.Book
        .Include(b => b.Author)
        .Include(b => b.Reviews)
         .ThenInclude(r => r.Voter)
        .ToListAsync();
}

async Task<IEnumerable<object>> ReadAllBooksSelect(MyDbContext myDbContext) {
    return await myDbContext.Book
        .Select(b => new {
            b.Title,
            b.Isbn,
            NumReview = b.Reviews.Count
    }).ToListAsync();
}

void InitData(MyDbContext myDbContext)
{
    var donaldKnuth = new Author()
    {
        FirstName = "Donald E.",
        LastName = "Knuth"
    };
    CreateBook(myDbContext, "0201896834", "The Art of Computer Programming: Volume 1: Fundamental Algorithms",
        donaldKnuth);
    var concreteMath = CreateBook(myDbContext, "B08F5H9DYM", "Concrete Mathematics: A Foundation for Computer Science",
        donaldKnuth);
    CreateBook(myDbContext, "0201896842", "Art of Computer Programming, Volume 2: Seminumerical Algorithms",
        donaldKnuth);
    var vol3 = CreateBook(myDbContext, "0201896850", "The Art of Computer Programming: Volume 3: Sorting and Searching",
        donaldKnuth);
    CreateBook(myDbContext, "978-0062316097", "Sapiens: A Brief History of Humankind", null);

    var voter1 = new Voter()
    {
        Username = "hkirk",
    };
    var voter2 = new Voter()
    {
        Username = "leneH"
    };
    var voter3 = new Voter()
    {
        Username = "Hugo"
    };

    InsertReview(myDbContext, vol3, 5, voter1);
    InsertReview(myDbContext, vol3, 4, voter2);
    InsertReview(myDbContext, concreteMath, 5, voter3);
    myDbContext.SaveChanges();
}
