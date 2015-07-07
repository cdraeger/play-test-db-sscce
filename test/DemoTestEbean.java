import models.DomainModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.db.Database;
import play.db.Databases;
import play.inject.guice.GuiceApplicationBuilder;

import static org.junit.Assert.assertNotNull;
import static play.inject.Bindings.bind;
import static play.test.Helpers.running;

/**
 * These tests succeed in this sample project, but not in my main project:
 * {@link javax.persistence.PersistenceException}: Unique index or primary key violation
 */
public class DemoTestEbean {

  private Database database;

  @Before
  public void setUp() {
    database = Databases.inMemory();

    /* with Ebean, this seems not to be necessary */
//    Evolutions.applyEvolutions(database);
  }

  @After
  public void cleanUp() {
    database.shutdown();

    /* with Ebean, this doesn't seem to have any effect at all, so in my main project it's not working
     * as a workaround if the in-memory database persists across tests and new instantiations */
//    Evolutions.cleanupEvolutions(database);
  }

  @Test
  public void test1() {
    final Application application = new GuiceApplicationBuilder().overrides(bind(Database.class).toInstance(database)).build();;
    running(application, () -> {
      final DomainModel model = new DomainModel("unique");
      model.save();

      assertNotNull(model);
      assertNotNull(model.id);
    });
  }

  @Test
  public void test2() {
    final Application application = new GuiceApplicationBuilder().overrides(bind(Database.class).toInstance(database)).build();
    running(application, () -> {
      final DomainModel model = new DomainModel("unique");
      model.save();

      assertNotNull(model);
      assertNotNull(model.id);
    });
  }
}
