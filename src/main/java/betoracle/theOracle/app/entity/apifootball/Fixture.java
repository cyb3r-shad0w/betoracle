package betoracle.theOracle.app.entity.apifootball;

import java.sql.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import betoracle.theOracle.app.object.apifootball.Venue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Fixture {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	private Date date;
	//@ManyToOne
	private Venue venue;
	private String timezone;
	private Periods periods;
	private String referee;
	private String timestamp;
	private FixtureStatus status;
	
	capire che tabelle creare e che relazioni devono avere
	
	@CreatedDate
	private Date createdDate;
	@LastModifiedDate
	private Date lastModifiedDate;

}

//example of fixture get from fixturesByStatus
/*"fixture": {
	"date": "2023-04-24T18:45:00+00:00",
	"venue": {
		"city": "Bergamo",
		"name": "Gewiss Stadium",
		"id": 879
	},
	"timezone": "UTC",
	"periods": {
		"first": null,
		"second": null
	},
	"id": 882080,
	"referee": "M. Irrati",
	"timestamp": 1682361900,
	"status": {
		"elapsed": null,
		"short": "NS",
		"long": "Not Started"
	}
}*/
