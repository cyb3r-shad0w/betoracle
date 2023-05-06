package betoracle.theOracle.app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "PREDICTION")
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
}
