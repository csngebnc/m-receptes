namespace Receptes.Domain.Entities
{
    public class Recipe
    {
        public int Id { get; set; }
        public string OwnerUsername { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
    }
}
