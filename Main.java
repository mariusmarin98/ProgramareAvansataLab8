package lab8;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

class Main{
    public static void main(String[] args){
        try {
            Database db = Database.getInstance();

            Connection con = db.getConnection();

            Statement stmt=con.createStatement();

            AlbumController albumController = new AlbumController(stmt);
            ArtistController artistController = new ArtistController(stmt);
            albumController.deleteFromDB();
            artistController.deleteFromDB();

            Generator generator = new Generator(stmt);

            generator.generateArtists(1000, false);
            generator.generateAlbums(1000, false);

            List<Album> albums = albumController.getAll();
            List<Artist> artists = artistController.getAll();

            System.out.println("List of artists: ");
            for (Artist artist : artists )
                System.out.println(artist);

            System.out.println("List of albums: ");
            for (Album album : albums )
                System.out.println(album);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}  