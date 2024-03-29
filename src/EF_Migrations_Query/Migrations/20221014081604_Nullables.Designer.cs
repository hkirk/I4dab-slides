﻿// <auto-generated />
using System;
using EF_Migrations_Query;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

#nullable disable

namespace EF_Migrations_Query.Migrations
{
    [DbContext(typeof(MyDbContext))]
    [Migration("20221014081604_Nullables")]
    partial class Nullables
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "6.0.10")
                .HasAnnotation("Relational:MaxIdentifierLength", 128);

            SqlServerModelBuilderExtensions.UseIdentityColumns(modelBuilder, 1L, 1);

            modelBuilder.Entity("EF_Migrations_Query.Author", b =>
                {
                    b.Property<int>("AuthorId")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    SqlServerPropertyBuilderExtensions.UseIdentityColumn(b.Property<int>("AuthorId"), 1L, 1);

                    b.Property<int>("BookId")
                        .HasColumnType("int");

                    b.Property<string>("FirstName")
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("LastName")
                        .HasColumnType("nvarchar(max)");

                    b.HasKey("AuthorId");

                    b.HasIndex("BookId")
                        .IsUnique();

                    b.ToTable("Author");
                });

            modelBuilder.Entity("EF_Migrations_Query.Book", b =>
                {
                    b.Property<int>("BookId")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    SqlServerPropertyBuilderExtensions.UseIdentityColumn(b.Property<int>("BookId"), 1L, 1);

                    b.Property<string>("Isbn")
                        .HasColumnType("nvarchar(450)");

                    b.Property<string>("Title")
                        .HasColumnType("nvarchar(max)");

                    b.HasKey("BookId");

                    b.HasIndex("Isbn")
                        .IsUnique()
                        .HasFilter("[Isbn] IS NOT NULL");

                    b.ToTable("Book");
                });

            modelBuilder.Entity("EF_Migrations_Query.Review", b =>
                {
                    b.Property<int>("ReviewId")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    SqlServerPropertyBuilderExtensions.UseIdentityColumn(b.Property<int>("ReviewId"), 1L, 1);

                    b.Property<int>("BookId")
                        .HasColumnType("int");

                    b.Property<int>("Rating")
                        .HasColumnType("int");

                    b.Property<int?>("VoterId")
                        .HasColumnType("int");

                    b.HasKey("ReviewId");

                    b.HasIndex("BookId");

                    b.HasIndex("VoterId");

                    b.ToTable("Review");
                });

            modelBuilder.Entity("EF_Migrations_Query.Voter", b =>
                {
                    b.Property<int>("VoterId")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    SqlServerPropertyBuilderExtensions.UseIdentityColumn(b.Property<int>("VoterId"), 1L, 1);

                    b.Property<string>("Username")
                        .HasColumnType("nvarchar(max)");

                    b.HasKey("VoterId");

                    b.ToTable("Voter");
                });

            modelBuilder.Entity("EF_Migrations_Query.Author", b =>
                {
                    b.HasOne("EF_Migrations_Query.Book", "Book")
                        .WithOne("Author")
                        .HasForeignKey("EF_Migrations_Query.Author", "BookId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Book");
                });

            modelBuilder.Entity("EF_Migrations_Query.Review", b =>
                {
                    b.HasOne("EF_Migrations_Query.Book", "Book")
                        .WithMany("Reviews")
                        .HasForeignKey("BookId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("EF_Migrations_Query.Voter", "Voter")
                        .WithMany()
                        .HasForeignKey("VoterId");

                    b.Navigation("Book");

                    b.Navigation("Voter");
                });

            modelBuilder.Entity("EF_Migrations_Query.Book", b =>
                {
                    b.Navigation("Author");

                    b.Navigation("Reviews");
                });
#pragma warning restore 612, 618
        }
    }
}
