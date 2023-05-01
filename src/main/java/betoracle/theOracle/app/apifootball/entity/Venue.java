package betoracle.theOracle.app.apifootball.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name= "VENUE", schema="APIFOOTBALL")
public class Venue { // venue = sede dove si disputa la partita

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "VENUE_ID")
	private int venueId;
	@Column(name = "VENUE_CITY")
	private String venueCity;
	@Column(name = "VENUE_NAME")
	private String venueName;
	
	@OneToMany( mappedBy = "venue")
	private List<Fixture> fixtureList;
	
	@CreatedDate
	private Date createdDate;
	@LastModifiedDate
	private Date lastModifiedDate;

}
