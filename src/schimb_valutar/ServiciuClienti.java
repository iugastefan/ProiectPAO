package schimb_valutar;

import java.io.IOException;
import java.util.*;

class ServiciuClienti {
    private List<Client> clienti;
    private static ServiciuClienti instance;

    private ServiciuClienti() {
        clienti = new ArrayList<>();
        Optional<List<String>> optionalList = Optional.empty();
        try {
            optionalList = ServiciuFisiere.citesteFisier("date", "clienti.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        optionalList.ifPresent(strings -> strings
                .stream()
                .map(x -> Arrays.asList(x.split(",")))
                .forEach(x -> clienti.add(new Client(x.get(0).strip(), x.get(1).strip(), Long.parseLong(x.get(2).strip()),
                        x.get(3).strip(), Integer.parseInt(x.get(4).strip()))))
        );
    }

    static ServiciuClienti getInstance() {
        if (instance == null)
            instance = new ServiciuClienti();
        return instance;
    }

    void addClient(String nume, String prenume, long cnp, String serie_id, int numar_id) {
        Client x = new Client(nume, prenume, cnp, serie_id, numar_id);
        clienti.add(x);
        try {
            ServiciuFisiere.scrieFisierAppend("date", "clienti.csv",
                    Collections.singletonList(nume + "," + prenume + "," + cnp + "," + serie_id + "," + numar_id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void printClienti() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return clienti
                .stream()
                .map(Client::toString)
                .reduce((x, y) -> x + "\n\t" + y)
                .map(s -> "<ListaClienti>\n\t" + s
                        + "\n</ListaClienti>")
                .orElse("<ListaClienti></ListaClienti>");
    }
}
