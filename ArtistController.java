package lab8;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArtistController implements DAO<Artist>{
    private List<Artist> artists = new ArrayList<>();
    private Statement stmt = null;

    ArtistController(Statement stmt)
    {
       this.stmt = stmt;
    }

    public void create(String name, String country) throws SQLException {
        this.stmt.executeUpdate("INSERT INTO artists (name, country) VALUES ('" + name + "', '" + country + "')");
        ResultSet rs = stmt.executeQuery("SELECT count(*) FROM artists");
        int id = 0;
        if ( rs.next() ) {
            id = rs.getInt(1);
        }
        this.save(new Artist(id, name, country));
    }

    public Artist findByName(String artistName) throws SQLException {
        Artist artist = null;

        ResultSet rs = stmt.executeQuery("SELECT * FROM artist WHERE name = '" + artistName + "'");
        if ( rs.next() ) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String country = rs.getString(3);
            artist = new Artist(id, name, country);
        }
        return artist;
    }

    public void createTable() throws SQLException {
        this.stmt.executeUpdate("CREATE TABLE artists(\n" +
                "   id serial PRIMARY KEY,\n" +
                "   name VARCHAR (100)  NOT NULL,\n" +
                "   country VARCHAR (100));");
    }

    @Override
    public Artist get(long id) {
        return null;
    }

    @Override
    public List<Artist> getAll() throws SQLException {
        updateFromDB();
        return this.artists;
    }

    public int getSize() throws SQLException {
        updateFromDB();
        return this.artists.size();
    }

    public void deleteFromDB() throws SQLException {
        this.stmt.executeUpdate("DELETE from artists");
        this.stmt.executeUpdate("ALTER SEQUENCE artists_id_seq RESTART WITH 1;");
    }

    public void updateFromDB() throws SQLException {
        this.artists.clear();
        ResultSet rs = stmt.executeQuery("SELECT * FROM artists");
        while ( rs.next() ) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String country = rs.getString(3);
            this.artists.add(new Artist(id, name, country));
        }
    }

    @Override
    public void save(Artist artist) {
        this.artists.add(artist);
    }

    @Override
    public void update(Artist artist, String[] params) {

    }

    @Override
    public void delete(Artist artist) {
        this.artists.remove(artist);
    }
}
