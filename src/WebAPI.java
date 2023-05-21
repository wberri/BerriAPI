import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Scanner;

public class WebAPI {
    public static void getNowPlaying() {
        Scanner scan = new Scanner(System.in);
        String APIkey = "287f7b164fb12c622bde11556219b06"; // your personal API key on TheMovieDatabase
        String queryParameters = "?api_key=" + APIkey;
        String endpoint = "https://api.themoviedb.org/3/movie/popular";
        String url = endpoint + queryParameters;
        String urlResponse = "";
        try {
            URI myUri = URI.create(url); // creates a URI object from the url string
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            urlResponse = response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // when determining HOW to parse the returned JSON data,
        // first, print out the urlResponse, then copy/paste the output
        // into the online JSON parser: https://jsonformatter.org/json-parser
        // use the visual model to help you determine how to parse the data!
        JSONObject jsonObj = new JSONObject(urlResponse);
        JSONArray movieList = jsonObj.getJSONArray("results");
        for (int i = 0; i < movieList.length(); i++) {
            JSONObject movieObj = movieList.getJSONObject(i);
            String movieTitle = movieObj.getString("title");
            int movieID = movieObj.getInt("id");
            String posterPath = movieObj.getString("poster_path");
            String fullPosterPath = "https://image.tmdb.org/t/p/w500" + posterPath;
            System.out.println(movieID + " " + movieTitle + " " + fullPosterPath);
        }

        System.out.print("\nEnter a movie ID to learn more: ");
        int ID = scan.nextInt();

        String ur12 = "https://api.themoviedb.org/3/movie/" + ID + queryParameters;
        String ur1Response2 = "";

        try{
            URI myUri = URI.create(ur12);
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ur1Response2 = response.body();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        JSONObject movie = new JSONObject(ur1Response2);
        String title = movie.getString("title");
        String homePage = movie.getString("homepage");
        String overview = movie.getString("overview");
        String releaseDate = movie.getString("release_date");
        int runtime = movie.getInt("runtime");
        int revenue = movie.getInt("revenue");
        System.out.println("Title: " + title + "\nHomepage: " + homePage + "\nOverview: " + overview + "\nRelease Date: " + releaseDate + "\nRuntime: " + runtime + "\nRevenue: " + revenue);

        System.out.println("Genres: ");
        JSONArray genresList = movie.getJSONArray("genres");
        for(int i = 0; i< genresList.length(); i++){
            JSONObject genreObj = genresList.getJSONObject(i);
            String genre = genreObj.getString("name");
            System.out.println(genre);
        }
    }
}
