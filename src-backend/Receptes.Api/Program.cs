using Microsoft.EntityFrameworkCore;
using NSwag;
using Receptes.Dal;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddDbContext<ReceptesDbContext>(options => 
    options.UseInMemoryDatabase("ReceptesDb"));

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerDocument();

var app = builder.Build();

app.UseOpenApi(a => {
    a.PostProcess = (document, _) => {
        document.Schemes = new[] { OpenApiSchema.Https, OpenApiSchema.Http };
    };
});
app.UseSwaggerUi3();

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();