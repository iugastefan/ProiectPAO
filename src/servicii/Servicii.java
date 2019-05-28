package servicii;

import schimb_valutar.Date;
import schimb_valutar.Valuta;

import java.sql.ResultSet;
import java.util.Optional;

final public class Servicii {
    private static ServiciuDB db;
    private static ServiciuCursIstoric istoric;
    private static ServiciuCursActual actual;
    private static ServiciuClienti clienti;
    private static ServiciuCasieri casieri;
    private static Servicii instance = new Servicii();

    public static Servicii getInstance() {
        return instance;
    }

    private Servicii() {
        db = ServiciuDB.getInstance();
        istoric = ServiciuCursIstoric.getInstance();
        actual = ServiciuCursActual.getInstance();
        clienti = ServiciuClienti.getInstance();
        casieri = ServiciuCasieri.getInstance();
    }

    public void adaugaValuta(Valuta v) {
        actual.adaugaValuta(v);
    }

    public Optional<Valuta> getInfoValutaActual(String v) {
        return actual.getInfoValutaActual(v);
    }

    public void updateValutaActualSiFisier(Valuta v) throws Exception {
        actual.updateValutaActualSiFisier(v);
    }

    public void getValutaLaData(Date date, String nume) throws Exception {
        istoric.getValutaLaData(date, nume);
    }

    public void printIstoric() {
        istoric.printIstoric();
    }

    public void addClient(String nume, String prenume, long cnp, String serie_id, int numar_id) {
        clienti.addClient(nume, prenume, cnp, serie_id, numar_id);
    }

    public void addCasier(String nume, String prenume) {
        casieri.addCasier(nume, prenume);
    }

    void addValutaInIstoric(Date date, Valuta valuta) {
        istoric.addValuta(date, valuta);
    }

    public void putActualInIstoric() {
        actual.putActualInIstoric();
    }

    public void printClienti() {
        clienti.printClienti();
    }

    public void printCasieri() {
        casieri.printCasieri();
    }

    ResultSet executeQuery(String query) {
        return db.executeQuery(query);
    }
}
