package servicii;

import schimb_valutar.Date;
import schimb_valutar.Valuta;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

class ServiciuCursActual {
    private static ServiciuCursActual instance;
    private List<Valuta> listaValuteActuale;
    private schimb_valutar.Date dataActuala;

    static ServiciuCursActual getInstance() {
        if (instance == null)
            instance = new ServiciuCursActual();
        return instance;
    }

    private ServiciuCursActual() {
        listaValuteActuale = new ArrayList<>();
        LocalDate date = LocalDate.now();
        dataActuala = new schimb_valutar.Date(date.getDayOfMonth(), date.getMonthValue(), date.getYear());

    }

    private void ServiciuCursActualFromFisier() {
        listaValuteActuale = new ArrayList<>();
        LocalDate date = LocalDate.now();
        dataActuala = new schimb_valutar.Date(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        Optional<List<String>> linii = Optional.empty();
        try {
            linii = ServiciuFisiere.citesteFisier("date", "curs_actual.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        linii.ifPresent(strings ->
                strings
                        .stream()
                        .map(x -> Arrays.asList(x.split(","))).forEach(x -> listaValuteActuale.add(
                        new Valuta(
                                x.get(1), x.get(2), Double.parseDouble(x.get(3)),
                                Double.parseDouble(x.get(4)), Double.parseDouble(x.get(5)))
                        )
                ));
        //TODO: de luat de pe net in mod automat curs actual odata pe zi
    }

    void adaugaValuta(Valuta v) {
        listaValuteActuale.add(v);
//        try {
//            ServiciuFisiere.scrieFisierAppend("date", "curs_actual.csv", Collections.singletonList(dataActuala + "," + v.toString()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    Optional<Valuta> getInfoValutaActual(String v) {
        return listaValuteActuale.stream().filter(x -> x.getNume().equals(v) || x.getPrescurtare().equals(v)).findFirst();
    }

    void updateValutaActualSiFisier(Valuta v) throws Exception {
        if (listaValuteActuale.stream().anyMatch(o -> o.getNume().equals(v.getNume()))) {
            listaValuteActuale = listaValuteActuale
                    .stream()
                    .map(o -> o.getNume().equals(v.getNume()) ? v : o)
                    .collect(Collectors.toList());
            try {
                ServiciuFisiere.scrieFisierOverwrite("date", "curs_actual.csv",
                        listaValuteActuale.stream().map(o -> dataActuala.toString() + "," + o.toString()).collect(Collectors.toList())
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            throw new Exception("Valuta cu numele " + v.getNume() + " nu exista in actual!");
    }

    void putActualInIstoric() {
        Servicii s = Servicii.getInstance();
        listaValuteActuale.forEach(x -> s.addValutaInIstoric(dataActuala, x));
    }

    Date getDataActuala() {
        return dataActuala;
    }

    List<Valuta> getListaValuteActuale() {
        return listaValuteActuale;
    }
}
