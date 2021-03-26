<!-- .slide: data-background="#003d73" -->
## EF Advanced


![AU Logo](./../img/aulogo_uk_var2_white.png "AU Logo") <!-- .element style="width: 200px; position: fixed; bottom: 50px; left: 50px" -->

----

### Agenda

* Existing database
* Transactions
* Disconnected Entities
* Development
    * Data seeding
* Testing

---

## Existing database

* Reverse engineering a database through EF Core can be done with Scaffold tool
    * Visual Studio: 
      `> Scaffold-DbContext 'Data Source=(localdb)\MSSQLLocalDB;Initial Catalog=Chinook' Microsoft.EntityFrameworkCore.SqlServer` <!-- .element style="font-size: 22px;" -->
    * .Net Core CLI: 
      `$ dotnet ef dbcontext scaffold "Data Source=(localdb)\MSSQLLocalDB;Initial Catalog=Chinook" Microsoft.EntityFrameworkCore.SqlServer` <!-- .element style="font-size: 22px;" -->

**Note**: Connection string needs to point to the actual database you want to scaffold

Note:

1. Create project
2. Install Microsoft.EntityFrameworkCore.SqlServer and Microsoft.EntityFrameworkCore.Design
3. Run above command with a correct Connection string

----

### Scaffold options

* Skip tables with `-Tables` / `--tables`
* Preserving names from database `-UseDatabaseNames` / `--use-database-names`
* Fluent API is used by default to change to annotations `-DataAnnotations` / `--data-annotations`
* More configuration to be found in documentations


----

### When scaffold don't work

* Columns types not supported with EF Core
* Inheritance
* Tables without primary key

----

### Afterwards

* The model can be changed afterwards (with migrations) -> So inheritance etc. can be created manually afterwards
* Model is created as partial classes - so you can add extra validation

----

### Partial classes

```csharp
[ModelMetadataType(typeof(UserValidation))]
public partial class User {
  public string Email { get; set; }
}

private class UserValidation {
  [EmailAddress]
  public string Email { get; set; }       
}
```

---

## Transactions

* All changes to be saved with SaveChanges() are applied in a single transaction

```csharp
public class AClass {
 public void AMethod() {
  using (var context = new MyDbContext()) {
   using (var transaction = context.Database.BeginTransaction()) {
     try {
       context.Books.Add(new Book { Title = "First Book" });
       context.SaveChanges();
       // .. Network call which Depends on First Book in DB and Second Book not
       context.Books.Add(new Book { Title = "Second Book" });
       context.SaveChanges();
 
       var books = context.Books.OrderBy(b => b.Title).ToList();
       // Commit transaction if all commands succeed, transaction will auto-rollback
       // when disposed if either commands fails
       transaction.Commit();
     }
     catch (Exception) { // TODO: Handle failure
} } } } }
```
<!-- .element style="font-size: 16px;" -->

----

### When to use Transactions

* Business logic gets complex
* Solutions:

---

### 1. One big methods with all the logic

Problem: Obvious <!-- .element: class="fragment" -->

----

### 2. Smaller methods which are call from overreaching method

```csharp
public void SaveAll() {
    SaveA();
    SaveB();
    SaveC();
}

public void SaveA() {
    context.Add(new A() { ...});
    context.SaveChanges(); // Or call context.SaveChanges in SaveAll?
}
```

Problem: If later parts relies on earlier parts being written, forget to call SaveChanges <!-- .element: class="fragment" -->

----

### 3. Smaller methods and use transaction to run them as one

* Be aware that transactions locks tables for writes (or reads in some cases) - so use with care

---

## Disconnected Entities

* Scenario: Changes are made in a different database context instance

----

### Update Disconnected Entities
* Determine if the entity exists in DB or not.
    * Auto-keys: use `context.Update(entity) // In Core 2.0+`
    * else: `use context.Find(entity.Id) == null`
        * Use `context.Update(entity)`
        * Or `context.Add(newEntity)`

----

### Delete Disconnected Entities

* Same for Graphs
* Handling deletes
    * Harder since objects do not exists, so need check which do not exists in incoming
    * Can be handled with 'soft-deletes'

---

## Development

* From EF Core 2.1 the HasData method is added - part of OnModelCreating
* Migrations is created without connection to DB - have to specify ID manually.
* Data is removed if Primary key is changed

```csharp
public class Context: DbContext {
 public void CreateData(ModelBuilder modelBuilder) {
   modelBuilder.Entity<Book>().HasData(new Book() { Title = "A title", Isbn = 1 });
   modelBuilder.Entity<PriceOffer>().HasData(new PriceOffer{NewPrice = 1.1f, Isbn = 2});
   // Not working !
   // modelBuilder.Entity<Book>().OwnsMany(b => b.Reviews).HasData(
   //     new Review() { Id = 1, BookIsbn = 1, Votername = "V1", NumStars = 1},
   //     new Review() { Id = 2, BookIsbn = 2, Votername = "V2", NumStars = 2}
   // );
   modelBuilder.Entity<Review>().HasData(
   new Review() { Id = 2, BookIsbn = 2, Votername = "V2", NumStars = 2});
   modelBuilder.Entity<Review>().HasData(
   new { Id = 1, BookIsbn = 1, Votername = "V1", NumStars = 1});
} }
```
<!-- .element style="font-size: 16px;" -->

----

### Other solutions

* `InsertData()`, `UpdateData()`, `DeleteData()` on `MigraionBuilder`
[See Custom Migrations operations](https://docs.microsoft.com/en-us/ef/core/managing-schemas/migrations/operations)

----

#### Logging

* LogLevel.Information gives a list of all SQL commands generated by EF Core
* Setup log factory:

```csharp
public class AClass {
  public void Setup() {
    var logs = new List<String>();
    var loggerFactory = context.GetService<ILoggerFactory>();
    loggerFactory.AddProvider(new MyLoggerProvider(logs, LogLevel.Information));
  }
}
```

<!-- .element style="font-size: 16px;" -->

----

### Performance

* EF Core alerts to possible suboptimal LINQ commands by logging a warning of type QueryClientEvaluationWarning.
    * Sometimes EF Core can not translate LINQ expression to SQL
* Configure EF Core to throw an exception
* Analyse SQL queries
    * E.g. Azure Data studio execution plan

```
Run current Query with Actual Plan
```

----

### Keep your application performing

1. Use SELECT loading to load only needed data
2. Use paging/filtering to reduce rows loaded into application from SQL Server
3. Lazy loading will affect performance
4. Use `AsNoTracking` when you load read-only data
5. Using async versions when possible
6. Structure code, so database access code is isolated

---

## Testing

* Create AddDbContext constructor for testing, which allows for all options to come from test methods

```csharp
public class AppDbContext : DbContext {
  public AppDbContext() { }

  public AppDbContext(DbContextOptions<AppDbContext> options)
      : base(options) { }

  protected override void OnConfiguring(DbContextOptionsBuilder options)
  {
    if (!options.IsConfigured)
    {
      options.UseSqlServer("Data Source=....");
    }
  }
}
```


----

* Steps in test method
    1. Create connection to in-memory database
    2. Create DbContextOptions
    3. Initialize AppDbContext
    4. Insert test data
    5. Test business logic
    6. Close connections
    7. Cleanup

* `SQLite` in-memory database or `InMemory` for General purpose database


---

## Exercises :)

<!-- .slide: data-background="./img/make-homework-fun.jpg" -->

----

## References