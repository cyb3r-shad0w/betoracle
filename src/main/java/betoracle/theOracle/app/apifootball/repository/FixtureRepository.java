package betoracle.theOracle.app.apifootball.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import betoracle.theOracle.app.apifootball.entity.Fixture;

@Repository
public interface FixtureRepository extends JpaRepository<Fixture, Integer>  {

	Optional<Fixture> findByFixtureId(String fixtureId);

}
