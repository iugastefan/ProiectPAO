package schimb_valutar;

import java.util.ArrayList;
import java.util.List;

class ServiciuCasieri {
    private List<Casier> casieri;
    private static ServiciuCasieri instance;

    private ServiciuCasieri() {
        casieri = new ArrayList<>();
    }

    static ServiciuCasieri getInstance() {
        if (instance == null)
            instance = new ServiciuCasieri();
        return instance;
    }

    void addCasier(String nume, String prenume) {
        Casier x = new Casier(nume, prenume);
        casieri.add(x);
    }

    void printCasieri() {
        casieri.forEach(System.out::println);
    }
}
