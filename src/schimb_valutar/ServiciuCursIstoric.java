package schimb_valutar;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class ServiciuCursIstoric {
    private Map<Date, List<Valuta>> istoric;
    private static ServiciuCursIstoric instance;

    static ServiciuCursIstoric getInstance() {
        if (instance == null)
            instance = new ServiciuCursIstoric();
        return instance;
    }

    private ServiciuCursIstoric() {
        //TODO: de citit din fisier
        istoric = new TreeMap<>(Collections.reverseOrder());
        actualizeazaIstoric();
    }

    private void actualizeazaIstoric() {
        //TODO: scris in fisier
        ServiciuCursActual actual = ServiciuCursActual.getInstance();
        if (!istoric.containsKey(actual.getDataActuala()))
            istoric.put(actual.getDataActuala(), actual.getListaValuteActuale());
    }

    void getValutaLaData(Date date, String nume) throws Exception {
        List<Valuta> lista = istoric.get(date);
        if (lista == null) {
            throw new Exception("Data " + date + " nu exista in istoric");
        }
        boolean ex = false;
        for (Valuta x : lista) {
            if (x.getNume().equals(nume) || x.getPrescurtare().equals(nume)) {
                System.out.println(x);
                ex = true;
            }
        }
        if (!ex)
            throw new Exception("Valuta" + nume + " nu exista la data " + date);
    }

    void printIstoric() {
        for (Map.Entry<Date, List<Valuta>> dateListEntry : istoric.entrySet()) {
            System.out.println(dateListEntry.getKey() + ": ");
            for (Valuta y : dateListEntry.getValue()) {
                System.out.println("\t" + y.getNume() + " " + y.getCurs());
            }
        }
    }
}
