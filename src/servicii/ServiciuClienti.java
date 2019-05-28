package servicii;

import schimb_valutar.Client;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ServiciuClienti {
    private List<Client> clienti;
    private static ServiciuClienti instance;

    private ServiciuClienti() {
        clienti = getClientiFromDB();
    }

    private void ServiciuClientiDinFisier() {
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

    void addClientToFisier(String nume, String prenume, long cnp, String serie_id, int numar_id) {
        Client x = new Client(nume, prenume, cnp, serie_id, numar_id);
        clienti.add(x);
        try {
            ServiciuFisiere.scrieFisierAppend("date", "clienti.csv",
                    Collections.singletonList(nume + "," + prenume + "," + cnp + "," + serie_id + "," + numar_id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addClient(String nume, String prenume, long cnp, String serie_id, int numar_id) {
        Client x = new Client(nume, prenume, cnp, serie_id, numar_id);
        clienti.add(x);
        addClientToDB(x);
    }

    private void addClientToDB(Client client) {
        Servicii s = Servicii.getInstance();
        s.executeQuery("insert into clienti values(\'" +
                client.getNume() + "\',\'" + client.getPrenume() + "\'," + client.getCnp() + ",\'" + client.getSerie_id() + "\'," + client.getNumar_id() + ")");
    }

    void printClienti() {
        System.out.println(this.toString());
    }

    private List<Client> getClientiFromDB() {
        ServiciuDB s = ServiciuDB.getInstance();
        ResultSet rset = s.executeQuery("select nume, prenume,cnp,serie_id,numar_id from clienti");
        List<Client> clients = new ArrayList<>();
        if (rset != null) {
            try {
                while (rset.next()) {
                    Client tmp = new Client(
                            rset.getString("nume"),
                            rset.getString("prenume"),
                            rset.getLong("cnp"),
                            rset.getString("serie_id"),
                            rset.getInt("numar_id")
                    );
                    clients.add(tmp);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return clients;

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
