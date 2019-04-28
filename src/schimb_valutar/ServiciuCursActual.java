package schimb_valutar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class ServiciuCursActual {
    private static ServiciuCursActual instance;
    private List<Valuta> listaValuteActuale;
    private Date dataActuala;

    static ServiciuCursActual getInstance() {
        if (instance == null)
            instance = new ServiciuCursActual();
        return instance;
    }

    private ServiciuCursActual() {
        listaValuteActuale = new ArrayList<>();
        LocalDate date = LocalDate.now();
        dataActuala = new Date(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
    }

    void adaugaValuta(Valuta v) {
        listaValuteActuale.add(v);
    }

    Optional<Valuta> getInfoValutaActual(String v) {
        return listaValuteActuale.stream().filter(x -> x.getNume().equals(v) || x.getPrescurtare().equals(v)).findFirst();
    }

    void updateValutaActual(Valuta v) throws Exception {
        if (listaValuteActuale.stream().anyMatch(o -> o.getNume().equals(v.getNume())))
            listaValuteActuale = listaValuteActuale.stream().map(o -> o.getNume().equals(v.getNume()) ? v : o).collect(Collectors.toList());
        else
            throw new Exception("Valuta cu numele " + v.getNume() + " nu exista in actual!");
    }

    Date getDataActuala() {
        return dataActuala;
    }

    List<Valuta> getListaValuteActuale() {
        return listaValuteActuale;
    }
}
