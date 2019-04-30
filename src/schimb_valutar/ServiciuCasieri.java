package schimb_valutar;

import java.io.IOException;
import java.util.*;

class ServiciuCasieri {
    private List<Casier> casieri;
    private static ServiciuCasieri instance;

    private ServiciuCasieri() {
        casieri = new ArrayList<>();
        Optional<List<String>> optionalList = Optional.empty();
        try {
            optionalList = ServiciuFisiere.citesteFisier("date", "casieri.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        optionalList.ifPresent(strings -> strings
                .stream()
                .map(x -> Arrays.asList(x.split(",")))
                .forEach(x -> casieri.add(new Casier(x.get(0).strip(), x.get(1).strip())))
        );
    }

    static ServiciuCasieri getInstance() {
        if (instance == null)
            instance = new ServiciuCasieri();
        return instance;
    }

    void addCasier(String nume, String prenume) {
        Casier x = new Casier(nume, prenume);
        casieri.add(x);
        try {
            ServiciuFisiere.scrieFisierAppend("date", "casieri.csv", Collections.singletonList(nume + "," + prenume));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void printCasieri() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return casieri
                .stream()
                .map(Casier::toString)
                .reduce((x, y) -> x + "\n\t" + y)
                .map(s -> "<ListaCasieri>\n\t" + s
                        + "\n</ListaCasieri>")
                .orElse("<ListaCasieri></ListaCasieri>");
    }
}
