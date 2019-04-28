package schimb_valutar;

import java.util.ArrayList;
import java.util.List;

class ServiciuClienti {
    private List<Client> clienti;
    private static ServiciuClienti instance;

    private ServiciuClienti() {
        clienti = new ArrayList<>();
    }

    static ServiciuClienti getInstance() {
        if (instance == null)
            instance = new ServiciuClienti();
        return instance;
    }

    void addClient(String nume, String prenume, long cnp, String serie_id, int numar_id) {
        Client x = new Client(nume, prenume, cnp, serie_id, numar_id);
        clienti.add(x);
    }

    void printClienti() {
        clienti.forEach(System.out::println);
    }

}
