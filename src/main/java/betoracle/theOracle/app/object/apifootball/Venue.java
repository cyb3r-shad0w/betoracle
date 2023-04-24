package betoracle.theOracle.app.object.apifootball;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Venue { // venue = sede dove si disputa la partita

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String city;
	private String name;
	
}
