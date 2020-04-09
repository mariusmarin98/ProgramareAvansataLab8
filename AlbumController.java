package lab8;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlbumController implements DAO<Album>{
    private List<Album> albums = new ArrayList<>();
    private Statement stmt = null;

    AlbumController(Statement stmt){
        this.stmt = stmt;
    }

    public void create(String name, int artistId, int releaseYear) throws SQLException {
        this.stmt.executeUpdate("INSERT INTO albums (name, artist_id, release_year) " +
                "VALUES ('" + name + "', " + artistId + ", " + releaseYear + ")");
        ResultSet rs = stmt.executeQuery("SELECT count(*) FROM albums");
        int id = 0;
        if ( rs.next() ) {
            id = rs.getInt(1);
        }
        this.save(new Album(id, name, artistId, releaseYear));
    }

    public Album findByArtist(int artistId) throws SQLException {
        Album album = null;

        ResultSet rs = stmt.executeQuery("SELECT * FROM albums WHERE artist_id = " + artistId);
        if ( rs.next() ) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int artist_id = rs.getInt(3);
            int release_year = rs.getInt(4);
            album = new Album(id, name, artist_id, release_year);
        }
        return album;
    }

    public void createTable() throws SQLException {
        this.stmt.executeUpdate("create table albums(\n" +
                " id serial PRIMARY KEY,\n" +
                " name VARCHAR (100)  NOT NULL,\n" +
                " artist_id INTEGER REFERENCES artists(id),\n" +
                " release_year INTEGER)");
    }

    @Override
    public Album get(long id) {
        return null;
    }

    @Override
    public List<Album> getAll() throws SQLException {
        updateFromDB();
        return albums;
    }

    public int getSize() throws SQLException {
        updateFromDB();
        return this.albums.size();
    }

    public void deleteFromDB() throws SQLException {
        this.stmt.executeUpdate("DELETE from albums");
        this.stmt.executeUpdate("ALTER SEQUENCE albums_id_seq RESTART WITH 1;");
    }

    public void updateFromDB() throws SQLException {
        this.albums.clear();
        ResultSet rs = stmt.executeQuery("SELECT * FROM albums");
        while ( rs.next() ) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int artist_id = rs.getInt(3);
            int release_year = rs.getInt(4);
            this.albums.add(new Album(id, name, artist_id, release_year));
        }
    }

    @Override
    public void save(Album album) {
        this.albums.add(album);
    }

    @Override
    public void update(Album album, String[] params) {

    }

    @Override
    public void delete(Album album) {
        this.albums.remove(album);
    }
}
