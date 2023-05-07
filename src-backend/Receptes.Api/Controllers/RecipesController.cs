using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Receptes.Api.Dtos;
using Receptes.Dal;
using Receptes.Domain.Entities;

namespace Receptes.Api.Controllers
{
    [Route("api/recipes")]
    [ApiController]
    public class RecipesController : ControllerBase
    {
        private readonly ReceptesDbContext _context;

        public RecipesController(ReceptesDbContext context)
        {
            _context = context;
        }

        [HttpGet("all/{userName}")]
        public async Task<List<Recipe>> GetAllRecipes(string userName)
        {
            return await _context.Recipes
                .Where(x => x.OwnerUsername == userName)
                .ToListAsync();
        }

        [HttpGet("single/{recipeId}")]
        public async Task<Recipe> GetRecipe(int recipeId)
        {
            return await _context.Recipes.SingleAsync(x => x.Id == recipeId);
        }

        [HttpPost]
        public async Task<Recipe> CreateRecipe(CreateRecipeDto recipeDto)
        {
            var recipe = new Recipe
            {
                Name = recipeDto.Name,
                Description = recipeDto.Description,
                OwnerUsername = recipeDto.OwnerUsername
            };

            _context.Recipes.Add(recipe);

            await _context.SaveChangesAsync();

            return recipe;
        }

        [HttpPut]
        public async Task<Recipe> UpdateRecipe(Recipe recipeToUpdate)
        {
            var recipe = await _context.Recipes.SingleAsync(x => x.Id == recipeToUpdate.Id && x.OwnerUsername == recipeToUpdate.OwnerUsername);

            recipe.Name = recipeToUpdate.Name;
            recipe.Description = recipeToUpdate.Description;

            _context.Recipes.Update(recipe);

            await _context.SaveChangesAsync();

            return recipe;
        }

        [HttpDelete("delete/{recipeId}")]
        public async Task DeleteRecipe(int recipeId)
        {
            var recipe = await _context.Recipes.SingleAsync(x => x.Id == recipeId);

            _context.Remove(recipe);

            await _context.SaveChangesAsync();
        }

        [HttpDelete("/clear-db")]
        public async Task ClearDb()
        {
            var recipes = await _context.Recipes.ToListAsync();

            _context.RemoveRange(recipes);

            await _context.SaveChangesAsync();
        }
    }
}
