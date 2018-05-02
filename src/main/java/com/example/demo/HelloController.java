package com.example.demo;

import com.example.demo.dto.Album;
import com.example.demo.dto.Singer;
import com.google.cloud.spanner.*;
import com.google.spanner.admin.database.v1.CreateDatabaseMetadata;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class HelloController {

    //Variables
    String project = "helical-sled-202720",
            instance = "test-instance",
            database = "demo-db2";
    SpannerOptions options = SpannerOptions.newBuilder().build();
    Spanner spanner = options.getService();
    DatabaseClient databaseClient = spanner.getDatabaseClient(DatabaseId.of(project,instance,database));
    static final List<Singer> SINGERS =
            Arrays.asList(
                    new Singer(1, "Marc", "Richards"),
                    new Singer(2, "Catalina", "Smith"),
                    new Singer(3, "Alice", "Trentor"),
                    new Singer(4, "Lea", "Martin"),
                    new Singer(5, "David", "Lomond"));

    static final List<Album> ALBUMS =
            Arrays.asList(
                    new Album(1, 1, "Total Junk"),
                    new Album(1, 2, "Go, Go, Go"),
                    new Album(2, 1, "Green"),
                    new Album(2, 2, "Forever Hold Your Peace"),
                    new Album(2, 3, "Terrified"));



    @RequestMapping("/")
    public String index() {
//        writeExampleData(databaseClient);
       // query(databaseClient);
       // read(databaseClient);
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/readAlbums")
    public String read() {
        ResultSet resultSet =
                databaseClient
                        .singleUse()
                        .read("Albums",
                                KeySet.all(),
                                Arrays.asList("SingerId", "AlbumId", "AlbumTitle"));
        String result = "";
        while (resultSet.next()) {
            System.out.printf(
                    "%d %d %s\n", resultSet.getLong(0), resultSet.getLong(1), resultSet.getString(2));
            result = result.concat( "" + resultSet.getLong(0) + " " + resultSet.getLong(1) + " " + resultSet.getString(2) + "\n");
        }
        return result;

    }//@RequestParam("singerId") int singerid, @RequestParam("albumId") int albumId, @RequestParam("albumTitle") String albumTitle) {

    @RequestMapping("/queryAlbums")
    public String query(@RequestParam("singerId") long singerid, @RequestParam("albumId") long albumId) {
        // singleUse() can be used to execute a single read or query against Cloud Spanner.
        ResultSet resultSet =
                databaseClient
                        .singleUse()
                        .executeQuery(Statement.of("SELECT SingerId, AlbumId, AlbumTitle FROM Albums WHERE SingerId=" + singerid + " AND AlbumId=" + albumId));
        String result = "";
        while (resultSet.next()) {
            System.out.printf(
                    "%d %d %s\n", resultSet.getLong(0), resultSet.getLong(1), resultSet.getString(2));
            result = result.concat( "" + resultSet.getLong(0) + " " + resultSet.getLong(1) + " " + resultSet.getString(2) + "\n");
        }
        return result;
    }

    @RequestMapping(value = "/writeAlbum", method = RequestMethod.POST)
    public String writeAlbumToDB(@RequestBody Album album){
        List<Mutation> mutations = new ArrayList<>();
        mutations.add(
                Mutation.newInsertBuilder("Albums")
                        .set("SingerId")
                        .to(album.getSingerId())
                        .set("AlbumId")
                        .to(album.getAlbumId())
                        .set("AlbumTitle")
                        .to(album.getTitle())
                        .build());

        try{
            databaseClient.write(mutations);
            return "Album Added to the DB";
        } catch(Exception ex){
            return "There was an error and the album was not added to the DB. The error is: " + ex.getMessage();
        }

    }

    /**
     * Writes the sample list of singers and albums to the DB
     * @param dbClient
     */
    private void writeExampleData(DatabaseClient dbClient) {
        List<Mutation> mutations = new ArrayList<>();
        for (Singer singer : SINGERS) {
            mutations.add(
                    Mutation.newInsertBuilder("Singers")
                            .set("SingerId")
                            .to(singer.getId())
                            .set("FirstName")
                            .to(singer.getFirstName())
                            .set("LastName")
                            .to(singer.getLastName())
                            .build());
        }
        for (Album album : ALBUMS) {
            mutations.add(
                    Mutation.newInsertBuilder("Albums")
                            .set("SingerId")
                            .to(album.getSingerId())
                            .set("AlbumId")
                            .to(album.getAlbumId())
                            .set("AlbumTitle")
                            .to(album.getTitle())
                            .build());
        }
        dbClient.write(mutations);
    }

    //Todo you can remove this method or you can modify it to receive a name of the database and react properly in case one of the parameters is not correct and the DB is not created
    private void createDatabase() {

        String project = "helical-sled-202720",
                instance = "test-instance",
                database = "demo-db2";


        SpannerOptions options = SpannerOptions.newBuilder().build();
        Spanner spanner = options.getService();

        DatabaseAdminClient dbAdminClient = spanner.getDatabaseAdminClient();

        Operation<Database, CreateDatabaseMetadata> op = dbAdminClient.createDatabase(
                instance,
                database,
                Arrays.asList(
                        "CREATE TABLE Singers (\n"
                                + "  SingerId   INT64 NOT NULL,\n"
                                + "  FirstName  STRING(1024),\n"
                                + "  LastName   STRING(1024),\n"
                                + "  SingerInfo BYTES(MAX)\n"
                                + ") PRIMARY KEY (SingerId)",
                        "CREATE TABLE Albums (\n"
                                + "  SingerId     INT64 NOT NULL,\n"
                                + "  AlbumId      INT64 NOT NULL,\n"
                                + "  AlbumTitle   STRING(MAX)\n"
                                + ") PRIMARY KEY (SingerId, AlbumId),\n"
                                + "  INTERLEAVE IN PARENT Singers ON DELETE CASCADE"));
        Database db = op.waitFor().getResult();

        System.out.println("Created database [" + db.getId() + "]");
    }


}