package lab8;

import java.sql.SQLException;
import java.sql.Statement;

public class Generator {
    ArtistController artistController = null;
    AlbumController albumController = null;

    String[] names = {
            "Shakira", "Inna", "Alexandra Stan", "Enrique Iglesias", "Fuego", "Maroon 5", "AC/DC", "Madonna", "Eminem",
            "Taylor Swift", "The Beatles", "Rihanna", "Lady Gaga", "Bruno Mars", "The Weeknd", "Beyonce", "Adele",
            "Chris Brown", "Celine Dion", "Margineanu", "Michael Jackson", "Smiley", "Delia", "Carlas Dreams",
            "Elena Gheorghe", "Dan Bitman"
    };

    String[] countries = {
            "Romania", "Moldova", "France", "England", "Scotland", "Spain", "Portugal", "Austria", "Sweeden", "Norway",
            "Germany", "Italy", "Serbia", "Ukraina", "Russia", "Switzerland", "Latvia", "China", "Japan", "SUA", "Canada",
            "Mexico", "Brazil", "Argentina", "Monaco", "Cuba", "India", "Belgium"
    };

    String[] albums = {
            "Falling", "Never back down", "Insanity", "Interstellar", "Insane", "Crazy", "Go away", "Come back",
            "See you again", "Stay", "Keep you", "Do not leave", "Close to me", "Run", "Run away", "Running",
            "Nowhere", "Never let you go", "Let you go", "Rising stars", "Stars"
    };

    Generator(Statement stmt) {
        this.artistController = new ArtistController(stmt);
        this.albumController = new AlbumController(stmt);
    }

    public String getRandomName(){
        int idxName = (int) (Math.random() * names.length);
        return names[idxName];
    }

    public String getRandomCountry(){
        int idxCountries = (int) (Math.random() * countries.length);
        return countries[idxCountries];
    }

    public String getRandomAlbum(){
        int idxAlbum = (int) (Math.random() * albums.length);
        return albums[idxAlbum];
    }

    public int getRandomNumber(int min, int max){
        return (int) (Math.random() * max + min);
    }

    public int getRandomArtistId() throws SQLException {
        return (int) (Math.random() * artistController.getSize() + 1);
    }

    public void generateArtists(int number, boolean createTable) throws SQLException {
        if (createTable){
            this.artistController.createTable();
        }
        for(int i = 0; i < number; i ++)
        {
            String name = getRandomName();
            String country = getRandomCountry();
            this.artistController.create(name, country);
        }
    }

    public void generateAlbums(int number, boolean createTable) throws SQLException {
        if(createTable){
            this.albumController.createTable();
        }
        for(int i = 0; i < number; i++)
        {
            String album = getRandomAlbum();
            int artist_id = getRandomArtistId();
            int year = getRandomNumber(1900, 120);
            this.albumController.create(album, artist_id, year);
        }
    }
}
