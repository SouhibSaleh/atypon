FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /app

COPY src/Authentication.csproj ./ 
RUN dotnet restore

COPY src/. ./ 
RUN dotnet publish -c Release -o out

FROM mcr.microsoft.com/dotnet/runtime:8.0 AS runtime
WORKDIR /app

COPY --from=build /app/out ./

EXPOSE 8080


CMD ["dotnet", "Authentication.dll"]
