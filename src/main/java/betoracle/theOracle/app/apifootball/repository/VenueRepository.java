package betoracle.theOracle.app.apifootball.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import betoracle.theOracle.app.apifootball.entity.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Integer>{

	Optional<Venue> findByVenueId(int venueId);

}
