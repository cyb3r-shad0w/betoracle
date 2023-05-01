package betoracle.theOracle.app.apifootball.entity;


import java.sql.Timestamp;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name= "FIXTURE", schema="APIFOOTBALL")
public class Fixture {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="FIXTURE_ID")
	private String fixtureId;
	@Column(name="EVENT_DATE")
	private String eventDate;
	@Column(name="TIMEZONE")
	private String timezone;
	@Column(name="REFEREE")
	private String referee;
	@Column(name="TIMESTAMP")
	private String timestamp;
	@Column(name="FIRSTHALF")
	private Timestamp firstHalf;
	@Column(name="SECONDHALF")
	private Timestamp secondHalf;
	@Column(name="ELAPSED")
	private String elapsed;
	@Column(name="FIXTURE_STATUS_SHORT")
	private String fixtureStatusShort;
	@Column(name="FIXTURE_STATUS_LONG")
	private String fixtureStatusLong;
	
	@ManyToOne
	@JoinColumn(name="VENUE")
	private Venue venue;


	@CreatedDate
	private Date createdDate;
	@LastModifiedDate
	private Date lastModifiedDate;

}