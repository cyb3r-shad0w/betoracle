package betoracle.theOracle.app.repository;

import betoracle.theOracle.app.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionRepository  extends JpaRepository<Prediction,Integer> {
}
