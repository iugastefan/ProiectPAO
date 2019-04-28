package schimb_valutar;

import java.util.Optional;

final public class Servicii {
    private ServiciuCursIstoric istoric;
    private ServiciuCursActual actual;
    private ServiciuClienti clienti;
    private ServiciuCasieri casieri;
    private static Servicii instance = new Servicii();

    public static Servicii getInstance() {
        return instance;
    }

    private Servicii() {
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

    public void updateValutaActual(Valuta v) throws Exception {
        actual.updateValutaActual(v);
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

    public void printClienti() {
        clienti.printClienti();
    }

    public void printCasieri() {
        casieri.printCasieri();
    }

}
