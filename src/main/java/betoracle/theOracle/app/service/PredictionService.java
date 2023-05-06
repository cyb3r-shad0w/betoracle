package betoracle.theOracle.app.service;

import betoracle.theOracle.app.object.QuoteReali;
import betoracle.theOracle.app.repository.PredictionRepository;
import betoracle.theOracle.app.utility.CalcolatoreQuoteReali;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PredictionService {

    //TODO questo service si occuperà di:
    // - fare la prediction dopo aver consultato tutto ciò che gli serve (CalcolatoreQuoteReali,Apifootball, etc)
    // - scrivere la prediction sul db
    // - ritornare la prediction al controller che la manderà sulla pagina web dove sarà visualizzata


    @Autowired
    CalcolatoreQuoteReali calcolatoreQuoteReali;
    @Autowired
    PredictionRepository predictionRepository;

    public QuoteReali calcolatoreQuoteReali(){
        return null;
    }

}
