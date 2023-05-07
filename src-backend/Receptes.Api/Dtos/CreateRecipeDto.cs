namespace Receptes.Api.Dtos
{
    public class CreateRecipeDto
    {
        public string OwnerUsername { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
    }
}
