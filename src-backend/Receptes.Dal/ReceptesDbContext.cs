using Microsoft.EntityFrameworkCore;
using Receptes.Domain.Entities;

namespace Receptes.Dal
{
    public class ReceptesDbContext : DbContext
    {
        public DbSet<Recipe> Recipes { get; set; }

        public ReceptesDbContext()
        {
        }

        public ReceptesDbContext(DbContextOptions<ReceptesDbContext> options) : base(options)
        {
        }
    }
}
