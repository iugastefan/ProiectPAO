package servicii;

import schimb_valutar.Valuta;

import java.util.HashMap;
import java.util.Map;

final public class ServiciuCont {
    private Map<String, Double> casa;
    private static ServiciuCont instance;

    private ServiciuCont() {
        casa = new HashMap<>();
    }

    static ServiciuCont getInstance() {
        if (instance == null)
            instance = new ServiciuCont();
        return instance;
    }

    void adaugaBani(String valuta, Double suma) {
        if (!casa.containsKey(valuta))
            casa.put(valuta, suma);
        else casa.put(valuta, casa.get(valuta) + suma);
    }

    void cumparaValuta(Valuta valuta_veche, Valuta valuta_noua, Double suma_de_cumparat) throws Exception {
        if (casa.get(valuta_noua.getPrescurtare()) >= suma_de_cumparat) {
            Double var = casa.get(valuta_noua.getPrescurtare());
            casa.replace(valuta_noua.getPrescurtare(), var - suma_de_cumparat);
            var = suma_de_cumparat / valuta_veche.getCurs() *
                    (valuta_noua.getCurs() + valuta_noua.getComision_cumparare());
            casa.replace(valuta_veche.getPrescurtare(), casa.get(valuta_veche.getPrescurtare()) + var);
        } else {
            throw new Exception("Nu ai destui bani din valuta " + valuta_noua.getNume());
        }
    }

    @Override
    public String toString() {
        return "ServiciuCont{" +
                "casa=" + casa +
                '}';
    }
}
