using System;
using System.Net;
using System.Text;
using System.Text.Json;
using System.IO;
using System.Threading.Tasks; 
using MySql.Data.MySqlClient;

class Program
{
    static async Task Main()
    {
        Thread.Sleep(2000);
        var listener = new HttpListener();
        listener.Prefixes.Add("http://*:8081/");
        listener.Start();
        Console.WriteLine("Listening on http://*:8081/");

        string connectionString = "Server=my-mysql;Port=3306;Database=mydatabase;User ID=myuser;Password=mypassword;";
        using var dbConnection = new MySqlConnection(connectionString);
        try
        {
            dbConnection.Open();
            Console.WriteLine("Connected to MySQL successfully!");
        }
        catch (Exception ex)
        {
            Console.WriteLine("Failed to connect to MySQL: " + ex.Message);
        }

        while (true)
        {
            var context = await listener.GetContextAsync();
            var request = context.Request;
            var response = context.Response;

            if (request.HttpMethod == "OPTIONS")
            {
                AddCorsHeaders(response);
                response.StatusCode = 200;
                response.Close();
                continue;
            }

            using var reader = new StreamReader(request.InputStream, Encoding.UTF8);
            string requestBody = await reader.ReadToEndAsync();

            Console.WriteLine("Received: " + requestBody);

            var userData = JsonSerializer.Deserialize<UserData>(requestBody, new JsonSerializerOptions { PropertyNameCaseInsensitive = true });


            Console.WriteLine($"Saving User: {userData!.Username}");
            var temp = new { message = userData };
            string jsonResponse = string.Empty;
            if (CheckIfUserExists(dbConnection, userData.Username, userData.Password))
            {
                string query = "SELECT grade FROM users WHERE username = @username AND password = @password";

                using var command = new MySqlCommand(query, dbConnection);
                command.Parameters.AddWithValue("@username", userData.Username);
                command.Parameters.AddWithValue("@password", userData.Password);

                int grade = Convert.ToInt32(command.ExecuteScalar());

                jsonResponse = JsonSerializer.Serialize(new { message = grade });
            }
            else
            {
                jsonResponse = JsonSerializer.Serialize(new { message = -1 });
            }
            AddCorsHeaders(response);


            byte[] responseBytes = Encoding.UTF8.GetBytes(jsonResponse);
            response.ContentType = "application/json";
            response.ContentLength64 = responseBytes.Length;
            response.OutputStream.Write(responseBytes, 0, responseBytes.Length);
            response.Close();
        }
    }

    static bool CheckIfUserExists(MySqlConnection dbConnection, string username, string password)
    {
        try
        {
            string query = "SELECT COUNT(*) FROM users WHERE username = @username AND password = @password";
            using var command = new MySqlCommand(query, dbConnection);
            command.Parameters.AddWithValue("@username", username);
            command.Parameters.AddWithValue("@password", password);


            int count = Convert.ToInt32(command.ExecuteScalar());

            return count > 0;
        }
        catch (Exception ex)
        {
            Console.WriteLine("Error checking if user exists: " + ex.Message);
            return false;
        }
    }

    static void AddCorsHeaders(HttpListenerResponse response)
    {
        response.AddHeader("Access-Control-Allow-Origin", "*");
        response.AddHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.AddHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    class UserData
    {
        public string Username { get; set; }
        public string Password { get; set; }
    }
}
