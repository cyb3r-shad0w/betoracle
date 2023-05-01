package betoracle.theOracle.app.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import betoracle.theOracle.app.apifootball.entity.Fixture;
import betoracle.theOracle.app.apifootball.entity.Venue;
import betoracle.theOracle.app.apifootball.object.Status;
import betoracle.theOracle.app.apifootball.repository.FixtureRepository;
import betoracle.theOracle.app.apifootball.repository.VenueRepository;

@RestController(value = "/apifootballv1")
public class RestControllerAPIFootball {

	private Logger log = LoggerFactory.getLogger(RestControllerAPIFootball.class);

	@Autowired
	private FixtureRepository fixtureRepository;
	@Autowired
	private VenueRepository venueRepository;

	/*
	 * Get predictions about a fixture.
	 * 
	 * The predictions are made using several algorithms including the poisson
	 * distribution, comparison of team statistics, last matches, players etc…
	 * 
	 * Bookmakers odds are not used to make these predictions
	 * 
	 * Also provides some comparative statistics between teams
	 * 
	 * fixtureNumber is required
	 */
	@GetMapping("/apifootballv1/predictions/{fixtureNumber}")
	@ResponseBody
	String predictions(@PathVariable String fixtureNumber) {

		HttpResponse<String> response = null;
		JSONObject responseBodyJSON = null;
		JSONArray responseArray = null;
		Status status = getStatus();

		if (status == null)
			return "error, status is null";

		try {

			log.info("Sto preparando la richiesta predictions.....");
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://v3.football.api-sports.io/predictions?fixture=" + fixtureNumber))// fixture
																												// di
																												// test
																												// =
																												// 198772
					.header("content-type", "application/octet-stream")
					.header("x-apisports-key", "35e5c3a7dac05ca511bb23dc06f41525")
					.method("GET", HttpRequest.BodyPublishers.noBody()).build();

			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

			log.info("Richiesta andata a buon fine.....");

			log.info("Inizializzo i JSONObject....");

			responseBodyJSON = new JSONObject(response.body());

			responseArray = responseBodyJSON.getJSONArray("response"); // get response which is a JSON object inside
																		// response

			for (int i = 0; i < responseArray.length(); i++) { // iterate over array to get inner JSON objects and
																// extract values inside
				JSONObject record = responseArray.getJSONObject(i); // each item of Array is a JSON object
				if (record.has("comparison")) {
					log.info("comparisonObject: " + record.getJSONObject("comparison").toString() + "\n");
					JSONObject comparisonJSON = record.getJSONObject("comparison");
				}
				if (record.has("teams")) {
					JSONObject teamsJSON = record.getJSONObject("teams");
				}
			}

			log.info("Inizializzazione andata a buon fine.....");

		} catch (IOException e) {
			log.error("IOException:errore nel getStatus()");
			e.printStackTrace();
		} catch (InterruptedException e) {
			log.error("InterruptedException:errore nel getStatus()");
			e.printStackTrace();
		}

		return responseBodyJSON != null ? responseBodyJSON.toString() : "there was an error";

	}

	/**
	 * Available fixtures status SHORT LONG TYPE DESCRIPTION: TBD Time To Be Defined
	 * Scheduled Scheduled but date and time are not known NS Not Started Scheduled
	 * 1H First Half, Kick Off In Play First half in play HT Halftime In Play
	 * Finished in the regular time 2H Second Half, 2nd Half Started In Play Second
	 * half in play ET Extra Time In Play Extra time in play BT Break Time In Play
	 * Break during extra time P Penalty In Progress In Play Penaly played after
	 * extra time SUSP Match Suspended In Play Suspended by referee's decision, may
	 * be rescheduled another day INT Match Interrupted In Play Interrupted by
	 * referee's decision, should resume in a few minutes FT Match Finished Finished
	 * Finished in the regular time AET Match Finished After Extra Time Finished
	 * Finished after extra time without going to the penalty shootout PEN Match
	 * Finished After Penalty Finished Finished after the penalty shootout PST Match
	 * Postponed Postponed Postponed to another day, once the new date and time is
	 * known the status will change to Not Started CANC Match Cancelled Cancelled
	 * Cancelled, match will not be played ABD Match Abandoned Abandoned Abandoned
	 * for various reasons (Bad Weather, Safety, Floodlights, Playing Staff Or
	 * Referees), Can be rescheduled or not, it depends on the competition AWD
	 * Technical Loss Not Played WO WalkOver Not Played Victory by forfeit or
	 * absence of competitor LIVE In Progress In Play Used in very rare cases. It
	 * indicates a fixture in progress but the data indicating the half-time or
	 * elapsed time are not available
	 * 
	 * Fixtures with the status TBD may indicate an incorrect fixture date or time
	 * because the fixture date or time is not yet known or final. Fixtures with
	 * this status are checked and updated daily. The same applies to fixtures with
	 * the status PST, CANC.
	 * 
	 * The fixtures ids are unique and specific to each fixture. In no case an ID
	 * will change.
	 * 
	 * Although the data is updated every 15 seconds, depending on the competition
	 * there may be a delay between reality and the availability of data in the API.
	 * 
	 * Update Frequency : This endpoint is updated every 15 seconds.
	 * 
	 * Recommended Calls : 1 call per minute for the leagues, teams, fixtures who
	 * have at least one fixture in progress otherwise 1 call per day.
	 * 
	 * @throws Exception
	 **/
	@GetMapping("/apifootballv1/fixturesByStatus/{league}/{season}/{fixtureStatus}")
	@ResponseBody
	String fixturesByStatus(@PathVariable String league, @PathVariable String season,
			@PathVariable String fixtureStatus) throws Exception {

		HttpRequest request = null;
		HttpResponse<String> response;
		JSONObject responseBodyJSON = null;
		JSONArray responseArray = null;
		LinkedHashMap<String, Object> fixtureNumbersMap = new LinkedHashMap<>();// key = fixtureNumber, Object = all the
																				// info for that fixture
		Status status = getStatus();

		if (status == null) {
			return "{error, status is null}";
		}

		try {

			log.info("Sto preparando la richiesta fixturesByStatus.....");
			if (fixtureStatus.equals("NS") || season == null || season.isBlank()) {
				// NS Not Started but Scheduled, this status is only valid for the current year,
				// season must be the current year
				season = String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - 1);// in football if we are in the
																						// 2023 we are still in the
																						// season 2022

				if (season != null && !season.isBlank()) {
					if (Integer.parseInt(season) > Calendar.getInstance().get(Calendar.YEAR) - 1) {
						return "{error, season must be lower than the current season}";
					}
				}

			}

			URI uri = new URIBuilder(URI.create("https://v3.football.api-sports.io/fixtures?"))
					.addParameter("league", league).addParameter("season", season).addParameter("status", fixtureStatus)
					.build();

			request = HttpRequest.newBuilder(uri).header("content-type", "application/octet-stream")
					.header("x-apisports-key", "35e5c3a7dac05ca511bb23dc06f41525")
					.method("GET", HttpRequest.BodyPublishers.noBody()).build();

			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			log.info("Richiesta andata a buon fine.....");

			log.info("Inizializzo i JSONObject....");

			responseBodyJSON = new JSONObject(response.body());

			System.out.println(responseBodyJSON.toString());

			responseArray = responseBodyJSON.getJSONArray("response"); // get response which is a JSON object inside
																		// response

			for (int i = 0; i < responseArray.length(); i++) { // iterate over array to get inner JSON objects and
																// extract values inside
				JSONObject record = responseArray.getJSONObject(i); // each item of Array is a JSON object
				String fixtureId = null;

				log.info("Avvio inizializzazione Fixture.....");

				if (record.has("fixture")) {

					Fixture fixture = null;
					Venue venue;

					log.info("fixtureObject: " + record.getJSONObject("fixture").toString() + "\n");

					if (record.has("fixture")) {

						JSONObject fixtureJSON = record.getJSONObject("fixture");
						fixtureId = String.valueOf(fixtureJSON.getInt("id"));

						Optional<Fixture> fixtureDb = fixtureRepository.findByFixtureId(fixtureId);

						if (fixtureDb.isPresent()) {

							log.info("Fixture gia' presente nel db......");
							//log.info("Aggiorno la Fixture con i nuovi valori ricevuti......");
							//a questo punto controllo se la data di inserimento della fixture è maggiore di 24h e lo status non è NS allora
							//aggiorno i campi dello score e se è in corso aggiorno i goals, questa parte andrà impementata in un punto diverso, qui siamo sicuramente in una fixture con status NS
							//e devo anche settare il campo lastModifiedDate se modifico qualcosa nella tabella
						} else {

							log.info(".....Inizializzo i valori della nuova Fixture.....");

							fixture = new Fixture();
							fixture.setCreatedDate(new Date());
							fixture.setFixtureId(fixtureId);
							fixture.setEventDate(fixtureJSON.get("date").equals(null) ? "" : fixtureJSON.getString("date"));
							fixture.setTimezone(
									fixtureJSON.get("timezone").equals(null) ? "" : fixtureJSON.getString("timezone"));
							fixture.setReferee(
									fixtureJSON.get("referee").equals(null) ? "" : fixtureJSON.getString("referee"));
							fixture.setTimestamp(fixtureJSON.get("timestamp").equals(null) ? ""
									: String.valueOf(fixtureJSON.getInt("timestamp")));

							if (fixtureJSON.has("periods")) {

								JSONObject periodsJSON = fixtureJSON.getJSONObject("periods");
								fixture.setFirstHalf(periodsJSON.get("first").equals(null) ? null
										: (Timestamp) periodsJSON.get("first"));
								fixture.setFirstHalf(periodsJSON.get("second").equals(null) ? null
										: (Timestamp) periodsJSON.get("second"));

							}
							if (fixtureJSON.has("status")) {

								JSONObject statusJSON = fixtureJSON.getJSONObject("status");
								fixture.setElapsed(statusJSON.get("elapsed").equals(null) ? null
										: statusJSON.getString("elapsed"));
								fixture.setFixtureStatusShort(
										statusJSON.get("short").equals(null) ? null : statusJSON.getString("short"));
								fixture.setFixtureStatusLong(
										statusJSON.get("long").equals(null) ? null : statusJSON.getString("long"));

							}
							if (fixtureJSON.has("venue")) {

								JSONObject venueJSON = fixtureJSON.getJSONObject("venue");

								if (venueJSON.get("id") != null) {
									Optional<Venue> venueDb = venueRepository.findByVenueId(venueJSON.getInt("id"));
									if (venueDb.isPresent()) {
										fixture.setVenue(venueDb.get());
									} else {
										venue = new Venue();
										venue.setCreatedDate(new Date());
										venue.setVenueId(venueJSON.getInt("id"));
										venue.setVenueCity(
												venueJSON.get("city").equals(null) ? "" : venueJSON.getString("city"));
										venue.setVenueName(
												venueJSON.get("name").equals(null) ? "" : venueJSON.getString("name"));
										fixture.setVenue(venue);
										venueRepository.save(venue);
									}
								} else {
									log.warn("L' ID dello stadio è null, stadio non caricato!");
								}

							}

							log.info("Salvo la fixture nel db......");
							try {
								fixtureRepository.save(fixture);
							} catch (Exception e) {
								log.error("Errore durante il salvataggio a db della fixture");
								e.printStackTrace();
							}
							log.info("Salvataggio andato a buon fine....");

						}

						fixtureNumbersMap.put(fixtureId + "-F", fixtureJSON);
						log.info("Inizializzazione Fixture terminata correttamente.....");
						
					} else {
						log.error("La Fixture è vuota!");
						throw new Exception();
					}

				}

				log.info("Avvio inizializzazione League.....");

				if (record.has("league")) {
					log.info("leagueObject: " + record.getJSONObject("league").toString() + "\n");
					JSONObject leagueJSON = record.getJSONObject("league");

					fixtureNumbersMap.put(fixtureId + "-L", leagueJSON);
				}

				log.info("Inizializzazione League terminata correttamente.....");

				log.info("Avvio inizializzazione Teams.....");

				if (record.has("teams")) {
					log.info("teamsObject: " + record.getJSONObject("teams").toString() + "\n");
					JSONObject teamsJSON = record.getJSONObject("teams");

					fixtureNumbersMap.put(fixtureId + "-T", teamsJSON);
				}

				log.info("Inizializzazione Teams terminata correttamente.....");

				log.info("Avvio inizializzazione Goals.....");

				if (record.has("goals")) {
					log.info("goalsObject: " + record.getJSONObject("league").toString() + "\n");
					JSONObject goalsJSON = record.getJSONObject("league");

					fixtureNumbersMap.put(fixtureId + "-G", goalsJSON);
				}
				log.info("Inizializzazione Goals terminata correttamente.....");

				log.info("Avvio inizializzazione Score.....");
				if (record.has("score")) {
					log.info("scoreObject: " + record.getJSONObject("score").toString() + "\n");
					JSONObject scoreJSON = record.getJSONObject("score");

					fixtureNumbersMap.put(fixtureId + "-S", scoreJSON);
				}

				log.info("Inizializzazione Score terminata correttamente.....");
			}

			log.info("Inizializzazione di tutti gli oggetti andata a buon fine.....");

		} catch (IOException e) {
			log.error("IOException:errore nel getStatus()");
			e.printStackTrace();
		} catch (InterruptedException e) {
			log.error("InterruptedException:errore nel getStatus()");
			e.printStackTrace();
		} catch (URISyntaxException e) {
			log.error("URISyntaxException:errore nel getStatus()");
			e.printStackTrace();
		}

		return fixtureNumbersMap != null ? fixtureNumbersMap.toString() : null;

	}

	/**
	 * The status endpoint allows you to: To follow your consumption in real time
	 * Manage your subscription and change it if necessary Check the status of our
	 * servers Test all endpoints without writing a line of code. You can also
	 * consult all this information directly through the API by calling the endpoint
	 * status.
	 * 
	 * This call does not count against the daily quota.
	 * 
	 * @return consumption in real time
	 */
	@GetMapping("/apifootballv1/status")
	@ResponseBody
	Status getStatus() {

		HttpResponse<String> response = null;
		JSONObject responseBodyJSON = null;
		Status status = null;

		try {

			log.info("Sto preparando la richiesta status.....");
			HttpRequest request = HttpRequest.newBuilder()
					// rapidapi
					// .uri(URI.create("https://api-football-v1.p.rapidapi.com/status"))
					// .header("X-RapidAPI-Key",
					// "38446dcaecmsh727d79551bbd0cdp1b2df3jsnb547de9aa069")
					// .header("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
					// api-footbal
					.uri(URI.create("https://v3.football.api-sports.io/status"))
					.header("x-apisports-key", "35e5c3a7dac05ca511bb23dc06f41525")
					.method("GET", HttpRequest.BodyPublishers.noBody()).build();

			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

			log.info("Richiesta andata a buon fine.....");

			log.info("Inizializzo i JSONObject....");

			responseBodyJSON = new JSONObject(response.body());

			JSONObject tmp = responseBodyJSON.getJSONObject("response"); // get response which is a JSON object inside
																			// response

			JSONObject content = tmp.getJSONObject("requests"); // get requests which is JSON object inside response

			status = new Status();

			status.setCurrent(Integer.parseInt(content.get("current").toString()));
			status.setLimitDay(Integer.parseInt(content.get("limit_day").toString()));

			log.info("current: " + status.getCurrent());
			log.info("limit_day: " + status.getLimitDay());

			log.info("Inizializzazione andata a buon fine.....");

			log.info("Controllo se si e' raggiunto il limite giornaliero di chiamate.....");
			if (status.getCurrent() > (status.getLimitDay() - 10)) {
				log.info("sono rimaste solo 10 chiamate, vuoi farle lo stesso?");
				return null;
			}
			log.info("Chiamate effettuate : " + status.getCurrent() + "/" + status.getLimitDay());
			log.info("Chiamate rimanenti : " + (status.getLimitDay() - status.getCurrent()));

		} catch (IOException e) {
			log.error("IOException:errore nel getStatus()");
			e.printStackTrace();
		} catch (InterruptedException e) {
			log.error("InterruptedException:errore nel getStatus()");
			e.printStackTrace();
		}

		return status != null ? status : null;
	}

	/*
	 * @GetMapping("/{endpoint}")
	 * 
	 * @ResponseBody public String getEmployeesById(@PathVariable String endpoint) {
	 * return "ID: " +endpoint; }
	 */

}
