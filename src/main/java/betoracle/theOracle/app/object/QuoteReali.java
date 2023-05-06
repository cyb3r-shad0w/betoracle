package betoracle.theOracle.app.object;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class QuoteReali {

    //TODO: questo oggetto sarà richiamato dal service Prediction che si preoccuperà di inizializzarlo dopo aver estratto e preparato
    // i dati che servono dalle entity Squadra

    // quote bookmaker
    double quotaB1, quotaB2, quotaBX;
    // sono le vittorie totali in campionato
    double vittorieTotaliCasa, vittorieTotaliOspite;
    // sono le sconfitte totali in campionato
    double sconfitteTotaliCasa, sconfitteTotaliOspite;
    // vittorie ultime 5 partite in casa per l'ospitante e le ultime 5 vitt in trasferta dell'ospite
    double vittUltime5inCasa, vittUltime5inTrasfOspite;
    // sconfitte ultime 5 partite in casa ospitante e sconfitte ultime 5 trasferte ospite
    double sconfUlt5inCasa, sconfUlt5inTraOspite;
    // vittorie negli ultimi 5 match
    double vittUltime5TotCasa, vittUltime5TotOspite;
    // sconfitte negli utlimi 5 match
    double sconfUltime5Casa, sconfitteUltime5Ospite;
    // vittorie solo in casa squadra ospitante e vitt solo in trasferta squadra ospite
    double vittCasaTot, vittTrasTot;
    // sconfitte solo casa e solo in trasferta
    double sconfCasaTot, sconfTrasTot;
    // partite totali in campionato
    double partiteTotCasa, partiteTotOspite;
    // numero di PARTITE TOTALI IN CASA nel campionato(e volendo anche quelle fuori dal campionato) giocate dalla squadra di CASA in casa
    // numero di PARTITE TOTALI IN TRASFERTA nel campionato giocate dalla squadra OSPITE in trasfersta
    double totatliPartiteCasaInCasa, totaliPartiteOspiteInTrasferta;

    //come se fosse una sorta di costruttore personalizzato
    public QuoteReali(Double[] quoteBookmaker, int vittorieTotaliCasa, int sconfitteTotaliCasa,
                                                      int vittorieTotaliOspite, int sconfitteTotaliOspite, int partiteTotCasa,
                                                      int partiteTotOspite, int vittUltime5TotCasa, int sconfitteUltime5Ospite,
                                                      int vittUltime5inTrasfOspite, int sconfUltime5Casa, int vittCasaTot,
                                                      int sconfTrasTot, int vittTrasTot, int sconfCasaTot,
                                                      int totatliPartiteCasaInCasa, int totaliPartiteOspiteInTrasferta,
                                                      int vittUltime5inCasa, int sconfUlt5inTraOspite, int sconfUlt5inCasa) {

        //TODO: tutti questi dati devono essere poi settati dal servizio che li estrarrà dalle Squadre che riceve in input

        if (quoteBookmaker.length < 3 || quoteBookmaker.length > 3) {
            log.error("Ci sono troppe quote! necessarie solo 3 quote, 1, X, 2");
            return;
        }

        log.info("-----------------PASSO 1/10----------------");
        log.info("Setto le Quote Del BookMaker:");
        log.info("Quota 1 :");
        this.quotaB1 = quoteBookmaker[0];
        log.info("Quota X");
        this.quotaBX = quoteBookmaker[1];
        log.info("Quota 2");
        this.quotaB2 = quoteBookmaker[2];
        log.info("-----------------PASSO 2/10----------------");
        log.info("Setto le VITTORIE TOTALI fatte in campionato dalla squadra di CASA:");
        this.vittorieTotaliCasa = vittorieTotaliCasa;
        log.info("Setto le SCONFITTE TOTALI fatte in campionato dalla squadra OSPITE");
        this.sconfitteTotaliOspite = sconfitteTotaliOspite;
        log.info("Setto le VITTORIE TOTALI fatte in campionato dalla squadra OSPITE:");
        this.vittorieTotaliOspite = vittorieTotaliOspite;
        log.info("Setto le SCONFITTE TOTALI fatte in campionato dalla squadra di CASA");
        this.sconfitteTotaliCasa = sconfitteTotaliCasa;
        log.info("Setto il numero di PARTITE TOTALI in campionato(e volendo anche quelle fuori dal campionato) giocate dalla squadra in CASA:");
        this.partiteTotCasa = partiteTotCasa;
        log.info("Setto il numero di PARTITE TOTALI in campionato(e volendo anche quelle fuori dal campionato) giocate dalla squadra OSPITE:");
        this.partiteTotOspite = partiteTotOspite;
        log.info("-----------------PASSO 3/10----------------");
        log.info("Setto le VITTORIE NELLE ULTIME 5 PARTITE fatte in campionato dalla squadra di CASA:");
        this.vittUltime5TotCasa = vittUltime5TotCasa;
        log.info("Setto le SCONFITTE NELLE ULTIME 5 PARTITE fatte in campionato dalla squadra OSPITE:");
        this.sconfitteUltime5Ospite = sconfitteUltime5Ospite;
        log.info("Setto le VITTORIE NELLE ULTIME 5 PARTITE fatte in campionato dalla squadra OSPITE:");
        this.vittUltime5inTrasfOspite = vittUltime5inTrasfOspite;
        log.info("Setto le SCONFITTE NELLE ULTIME 5 PARTITE fatte in campionato dalla squadra di CASA:");
        this.sconfUltime5Casa = sconfUltime5Casa;
        log.info("-----------------PASSO 4/10----------------");
        log.info("Setto le VITTORIE TOTALI fatte in CASA nel campionato dalla squadra di CASA:");
        this.vittCasaTot = vittCasaTot;
        log.info("Setto le SCONFITTE TOTALI fatte in TRASFERTA nel campionato dalla squadra OSPITE");
        this.sconfTrasTot = sconfTrasTot;
        log.info("Setto le VITTORIE TOTALI fatte in TRASFERTA nel campionato dalla squadra OSPITE:");
        this.vittTrasTot = vittTrasTot;
        log.info("Setto le SCONFITTE TOTALI fatte in CASA nel campionato dalla squadra di CASA");
        this.sconfCasaTot = sconfCasaTot;
        log.info("Setto il numero di PARTITE TOTALI IN CASA nel campionato(e volendo anche quelle fuori dal campionato) giocate dalla squadra in CASA:");
        this.totatliPartiteCasaInCasa = totatliPartiteCasaInCasa;
        log.info("Setto il numero di PARTITE TOTALI IN TRASFERTA nel campionato(e volendo anche quelle fuori dal campionato) giocate dalla squadra OSPITE:");
        this.totaliPartiteOspiteInTrasferta = totaliPartiteOspiteInTrasferta;
        log.info("-----------------PASSO 5/10----------------");
        log.info("Setto le VITTORIE NELLE ULTIME 5 PARTITE fatte in CASA nel campionato dalla squadra di CASA:");
        this.vittUltime5inCasa = vittUltime5inCasa;
        log.info("Setto le SCONFITTE NELLE ULTIME 5 PARTITE fatte in TRASFERTA nel campionato dalla squadra OSPITE:");
        this.sconfUlt5inTraOspite = sconfUlt5inTraOspite;
        log.info("Setto le VITTORIE NELLE ULTIME 5 PARTITE fatte in TRASFERTA nel campionato dalla squadra OSPITE:");
        this.vittUltime5inTrasfOspite = vittUltime5inTrasfOspite;
        log.info("Setto le SCONFITTE NELLE ULTIME 5 PARTITE fatte in CASA nel campionato dalla squadra di CASA:");
        this.sconfUlt5inCasa = sconfUlt5inCasa;

    }

}
