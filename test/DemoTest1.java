import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.db.Database;
import play.db.Databases;
import play.db.evolutions.Evolutions;
import play.inject.guice.GuiceApplicationBuilder;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static play.inject.Bindings.bind;
import static play.test.Helpers.running;

/**
 * These tests demonstrate that an in-memory test db persists in memory, even when
 * it is shut down and a new one is instantiated before using it.
 */
public class DemoTest1 {

  private Database database;

  @Before
  public void setUp() {
    database = Databases.inMemory(); // this does not really have an effect: the database persists in memory, but shouldn't
    Evolutions.applyEvolutions(database);
  }

  @After
  public void cleanUp() {
    database.shutdown(); // this line should shut down the database which it does, but it still persists in memory

    // with this line instead these tests works, but it shouldn't be necessary when I need a new and fresh in-memory database
//    Evolutions.cleanupEvolutions(database);
  }

  @Test
  public void test1() {
    Application application = new GuiceApplicationBuilder().overrides(bind(Database.class).toInstance(database)).build();
    running(application, () -> {
      try {
        Connection connection = database.getConnection();
        connection.prepareStatement("insert into domain_model (id, unique_row) values (1, 'unique')").execute();

        assertTrue(connection.prepareStatement("select * from domain_model where id = 1").executeQuery().next());
      } catch (SQLException e) {
        throw new Error("SQL exception", e);
      }
    });
  }

  @Test
  public void test2() {
    Application application = new GuiceApplicationBuilder().overrides(bind(Database.class).toInstance(database)).build();
    running(application, () -> {
      try {
        Connection connection = database.getConnection();
        connection.prepareStatement("insert into domain_model (id, unique_row) values (1, 'unique')").execute();

        assertTrue(connection.prepareStatement("select * from domain_model where id = 1").executeQuery().next());
      } catch (SQLException e) {
        throw new Error("SQL exception", e);
      }
    });
  }
}
