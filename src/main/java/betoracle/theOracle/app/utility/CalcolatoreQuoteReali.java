package betoracle.theOracle.app.utility;

import betoracle.theOracle.app.object.QuoteReali;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CalcolatoreQuoteReali {

    final static int ULTIME_5 = 5;
    double marketValueB1, marketValueB2, marketValueBX;// percentuali bookmaker
    double fairProbability1, fairProbability2, fairProbabilityX;// percentuali reali
    double fairvalueR1, fairvalueR2, fairvalueRX;// quote reali
    double aggio;// allibramento bookmaker
    double percPartiteTot1, percPartiteTot2, percPartiteTotX;
    double percUltime51, percUltime52, percUltime5X;// percentuali Ultime 5 partite disputate
    double percgarecatra1, percgarecatra2, percgarecatraX;// percentuali gare solo in casa e trasferta
    double percultime5catra1, percultime5catra2, percultime5catraX;
    double valueBet1, valueBet2, valueBetX;
    double investimento;

    public void probabilitaImplicita(QuoteReali quoteReali) {// PASSO 1
        marketValueB1 = 100 / quoteReali.getQuotaB1();
        marketValueB2 = 100 / quoteReali.getQuotaB2();
        marketValueBX = 100 / quoteReali.getQuotaBX();
        aggio = (marketValueB1 + marketValueB2 + marketValueBX) - 100;
        log.info("Il valore delle PERCENTUALI dato dal BOOKMAKER è:");
        log.info("La probabilità per la squadra di CASA di vincere secondo i BM è: " + marketValueB1 + "%");
        log.info("La probabilità per la squadra in TRASFERTA di vincere secondo i BM è: " + marketValueB2 + "%");
        log.info("La probabilità di PAREGGIARE secondo i BM è: " + marketValueBX + "%");
        log.info("L'allibramento sulla seguente partita è del " + aggio + "%");
    }

    public void probabilitaPartiteTotali(QuoteReali quoteReali) {// PASSO 2
        double partitetotali = quoteReali.getPartiteTotCasa() + quoteReali.getPartiteTotOspite();
        percPartiteTot1 = ((quoteReali.getVittorieTotaliCasa() + quoteReali.getSconfitteTotaliOspite()) / partitetotali) * 100;
        percPartiteTot2 = ((quoteReali.getVittorieTotaliOspite() + quoteReali.getSconfitteTotaliCasa()) / partitetotali) * 100;
        percPartiteTotX = 100 - (percPartiteTot1 + percPartiteTot2);
        log.info("Il valore dato dal calcolo delle PARTITE TOTALI è:");
        log.info("La probabilità per la squadra di CASA è: " + percPartiteTot1 + "%");
        log.info("La probabilità per la squadra in TRASFERTA è: " + percPartiteTot2 + "%");
        log.info("La probabilità di PAREGGIARE è: " + percPartiteTotX + "%");
    }

    public void probabilitaUltime5(QuoteReali quoteReali) {// PASSO 3
        double partitetotali = ULTIME_5 * 2;
        percUltime51 = ((quoteReali.getVittUltime5TotCasa() + quoteReali.getSconfitteUltime5Ospite()) / partitetotali) * 100;
        percUltime52 = ((quoteReali.getVittUltime5inTrasfOspite() + quoteReali.getSconfUltime5Casa()) / partitetotali) * 100;
        percUltime5X = 100 - (percUltime51 + percUltime52);
        log.info("Il valore dato dal calcolo delle ULTIME 5 PARTITE è:");
        log.info("La probabilità per la squadra di CASA è: " + percUltime51 + "%");
        log.info("La probabilità per la squadra in TRASFERTA è: " + percUltime52 + "%");
        log.info("La probabilità di PAREGGIARE è: " + percUltime5X + "%");
    }

    public void percentualiSoloGareCasaTrasferta(QuoteReali quoteReali) {// PASSO 4
        double partitetotali = quoteReali.getTotatliPartiteCasaInCasa() + quoteReali.getTotaliPartiteOspiteInTrasferta();
        percgarecatra1 = ((quoteReali.getVittCasaTot() + quoteReali.getSconfTrasTot()) / partitetotali) * 100;
        percgarecatra2 = ((quoteReali.getVittTrasTot() + quoteReali.getSconfCasaTot()) / partitetotali) * 100;
        percgarecatraX = 100 - (percgarecatra1 + percgarecatra2);
        log.info("Il valore dato dal calcolo delle sole VITTORIE/SCONFITTE IN CASA/TRASFERTA è:");
        log.info("La probabilità per la squadra di CASA è: " + percgarecatra1 + "%");
        log.info("La probabilità per la squadra in TRASFERTA è: " + percgarecatra2 + "%");
        log.info("La probabilità di PAREGGIARE è: " + percgarecatraX + "%");
    }

    public void percentualiUltime5InCasaTrasferta(QuoteReali quoteReali) {// PASSO 5
        int partitetotali = ULTIME_5 * 2;
        percultime5catra1 = ((quoteReali.getVittUltime5inCasa() + quoteReali.getSconfUlt5inTraOspite()) / partitetotali) * 100;
        percultime5catra2 = ((quoteReali.getVittUltime5inTrasfOspite() + quoteReali.getSconfUlt5inCasa()) / partitetotali) * 100;
        percultime5catraX = 100 - (percultime5catra1 + percultime5catra2);
        log.info("Il valore dato dal calcolo delle sole ULTIME 5 VITTORIE/SCONFITTE IN CASA/TRASFERTA è:");
        log.info("La probabilità per la squadra di CASA è: " + percultime5catra1 + "%");
        log.info("La probabilità per la squadra in TRASFERTA è: " + percultime5catra2 + "%");
        log.info("La probabilità di PAREGGIARE è: " + percultime5catraX + "%");
    }

    public void percentualiReali() {// PASSO 6
        fairProbability1 = (percPartiteTot1 + percUltime51 + percgarecatra1 + percultime5catra1) / 4.0;
        fairProbability2 = (percPartiteTot2 + percUltime52 + percgarecatra2 + percultime5catra2) / 4.0;
        fairProbabilityX = (percPartiteTotX + percUltime5X + percgarecatraX + percultime5catraX) / 4.0;
        log.info(
                "Il valore delle PERCENTUALI REALI,dato dall'anilisi statistica solo sulle vittorie/sconfitte, è:");
        log.info("La probabilità reale per la squadra di CASA di vincere è: " + fairProbability1 + "%");
        log.info("La probabilità reale per la squadra in TRASFERTA di vincere è: " + fairProbability2 + "%");
        log.info("La probabilità reale di PAREGGIARE è: " + fairProbabilityX + "%");
    }

    public void quoteReali() {// PASSO 7
        fairvalueR1 = 100 / fairProbability1;
        fairvalueR2 = 100 / fairProbability2;
        fairvalueRX = 100 / fairProbabilityX;
        log.info("Le QUOTE REALI risultano quindi essere:");
        log.info("Quota CASA : " + fairvalueR1);
        log.info("Quota TRASFERTA : " + fairvalueR2);
        log.info("Quota PAREGGIO : " + fairvalueRX);
    }

    public void tipologiaDiPartita(QuoteReali quoteReali) {// PASSO 8
        double differenzaquota1 = fairvalueR1 - quoteReali.getQuotaB1();
        double differenzaquota2 = fairvalueR2 - quoteReali.getQuotaB2();
        double differenzaquotaX = fairvalueRX - quoteReali.getQuotaBX();
        log.info("Differenza QUOTA REALE CASA - QUOTA BM = " + differenzaquota1);
        log.info("Differenza QUOTA REALE TRASFERTA - QUOTA BM = " + differenzaquota2);
        log.info("Differenza QUOTA REALE X - QUOTA BM = " + differenzaquotaX);
        double percquota1 = Math.abs((differenzaquota1 / fairvalueR1) * 100);
        double percquota2 = Math.abs((differenzaquota2 / fairvalueR2) * 100);
        double percquotaX = Math.abs((differenzaquotaX / fairvalueRX) * 100);
        double totalediff = percquota1 + percquota2 + percquotaX;
        log.info("La % di differenza tra quotaReale1-quotaBM1 = " + percquota1 + "%");
        log.info("La % di differenza tra quotaReale2-quotaBM2 = " + percquota2 + "%");
        log.info("La % di differenza tra quotaRealeX-quotaBMX = " + percquotaX + "%");
        if (totalediff <= 17) {
            log.info(
                    "Analizzando la differenza tra Percentuale Reale e Percentuale BM questa partita risulta essere una PARTITA LINEARE(STATISTICA)!");
            log.info(
                    "CONSIGLIO: Si possono studiare tutti i tipi di mercato, però se una delle differenze risulta essere negativa bisogna valutare anche una doppia chance");
            log.info("INOLTRE: ");
            if ((fairvalueR1 < fairvalueRX && fairvalueRX < fairvalueR2)
                    || (fairvalueR2 < fairvalueRX && fairvalueRX < fairvalueR1)) {
                log.info(
                        "Poichè  quota1<quotaX<quota2 oppure quota2<quotaX<quota1 POSSO giocare il RISULTATO ESATTO");
            }
            if (((fairvalueR1 < fairvalueRX && fairvalueR1 < fairvalueR2) && fairvalueRX > fairvalueR2)
                    || ((fairvalueR1 < fairvalueRX && fairvalueR1 > fairvalueR2) && fairvalueRX > fairvalueR2)) {
                log.info(
                        "Poichè  quota1<quota2<quotaX oppure quota2<quota1<quotaX NON POSSO giocare il RISULTATO ESATTO");

            } else {
                log.info("NIENTE");
            }
        }
        if (totalediff > 17 && totalediff < 30) {
            log.info(
                    "Analizzando la differenza tra Percentuale Reale e Percentuale BM questa partita risulta essere una PARTITA NON LINEARE(NON STATISTICA)!");
            log.info(
                    "CONSIGLIO: Si possono studiare tutti i mercati dei GOL-NOGOL,UNDER-OVER,MULTIGOL dopo uno studio preventivo dell'andamento STATISTICO DEI GOL possibilmente con la Formula di POISSON");
        }

        if (totalediff >= 30) {
            log.info(
                    "Analizzando la differenza tra Percentuale Reale e Percentuale BM questa partita risulta essere una PARTITA CON UNA FORTE FAVORITA(NON LINEARE CASA/TRASFERTA)!");
            log.info("CONSIGLIO: Si possono giocare i mercati dell' 1-X-2, quindi risultato fisso ");
        }

    }

    public void valueBet() {// PASSO 9
        log.info("VALUE BET:");
        log.info("La VALUE BET è una scommessa in cui le probabilità di ottenere un dato risultato" + "/n"
                + " è maggiore di quanto mostrino le quote offerte.");
        double vb1 = marketValueB1 - fairProbability1;
        double vb2 = marketValueB2 - fairProbability2;
        double vbX = marketValueBX - fairProbabilityX;
        double vbtotal = vb1 + vb2 + vbX;
        log.info("ValueBet 1= " + vb1 + "%");
        log.info("ValueBet 2= " + vb2 + "%");
        log.info("ValueBet X= " + vbX + "%");
        log.info("La valueBet con valore POSITIVO ci mostra una papabile quota su cui giocare");
        log.info(
                "La ValueBet con maggior valore NEGATIVO ci mostra dove il BM sta cercando di fare più guadagno e quindi l'evento che molto probabilmente NON si verificherà!");
        log.info("ValueBet Totale= " + vbtotal + "%" + " = Allibramento = " + aggio + "%");

    }

    public void expectedValue() {// PASSO 10
        log.info("EXPECTED VALUE:");
        log.info("Il VALORE ATTESO(EXPECTED VALUE) viene usato per valutare quale opzione scegliere" + "/n"
                + "affinchè possano essere massimizzati i profitti e minimizzate le perdite." + "/n"
                + "Può essere positivo o negativo consentendo così di determinare quale investimento è proficuo e quale no");
//		double possibileVincita=%vincinta sulla quota scelta X valoreQuotaBM su cui scommetto
//		double possibilePerdita=somma %delle altre due quote che non gioco X investimento
//		double ev=investimento+(possibileVincita - possibilePerdita);
//		log.info("EXPECTED VALUE:" + ev);
    }

    /* calcolaQuoteReali si occuperà di avviare il calcolo delle quote reali a partire dalle quoteBookmaker
     * */
    public void calcolaQuoteReali(QuoteReali quoteReali) {

        log.info("Avvio calcolo di : Picchetto tecnico,Value Bet e Expected Value della partita selezionata");

        log.info("-----------------PASSO 1/10----------------");
        log.info("Calcolo le probabilità date dal BookMaker:");
        probabilitaImplicita(quoteReali);
        log.info("-----------------PASSO 2/10----------------");
        log.info("Calcolo il valore delle percentuali sulle PARTITE TOTALI:");
        probabilitaPartiteTotali(quoteReali);
        log.info("-----------------PASSO 3/10----------------");
        log.info("Calcolo il valore delle percentuali sulle ULTIME 5 PARTITE:");
        probabilitaUltime5(quoteReali);
        log.info("-----------------PASSO 4/10----------------");
        log.info("Calcolo il valore delle percentuali sulle PARTITE TOTALI:");
        percentualiSoloGareCasaTrasferta(quoteReali);
        log.info("-----------------PASSO 5/10----------------");
        log.info("Calcolo il valore delle percentuali sulle ULTIME 5 PARTITE:");
        percentualiUltime5InCasaTrasferta(quoteReali);
        log.info("-----------------PASSO 6/10----------------");
        percentualiReali();
        log.info("-----------------PASSO 7/10----------------");
        quoteReali();
        log.info("-----------------PASSO 8/10----------------");
        tipologiaDiPartita(quoteReali);
        log.info("-----------------PASSO 9/10----------------");
        valueBet();
        log.info("-----------------PASSO 10/10----------------");
        log.info("Calcola l'EXPECTED VALUE!");
        log.info("-----------------COMING SOON---------------");

    }


}
