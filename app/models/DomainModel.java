package models;

import com.avaje.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import static play.data.validation.Constraints.*;

@Entity
public class DomainModel extends Model {

  @Id
  public Long id;

  @Column(unique = true, nullable = false)
  @Required
  public String uniqueRow;

  public DomainModel(String uniqueRow) {
    this.uniqueRow = uniqueRow;
  }
}
