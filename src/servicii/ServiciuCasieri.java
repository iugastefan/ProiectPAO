package servicii;

import schimb_valutar.Casier;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ServiciuCasieri {
    private List<Casier> casieri;
    private static ServiciuCasieri instance;

    private void ServiciuCasieriDinFisier() {
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

    private ServiciuCasieri() {
        casieri = getCasieriFromDB();
    }

    static ServiciuCasieri getInstance() {
        if (instance == null)
            instance = new ServiciuCasieri();
        return instance;
    }

    void addCasier(String nume, String prenume) {
        Casier x = new Casier(nume, prenume);
        casieri.add(x);
        addCasierToDB(x);
    }

    void addCasierToFisier(String nume, String prenume) {
        Casier x = new Casier(nume, prenume);
        casieri.add(x);
        try {
            ServiciuFisiere.scrieFisierAppend("date", "casieri.csv", Collections.singletonList(nume + "," + prenume));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addCasierToDB(Casier casier) {
        Servicii s = Servicii.getInstance();
        s.executeQuery("insert into casieri values(" + casier.getId() + ",\'" + casier.getNume() + "\',\'" + casier.getPrenume() + "\')");
    }

    private List<Casier> getCasieriFromDB() {
        ServiciuDB s = ServiciuDB.getInstance();
        ResultSet rset = s.executeQuery("select id, nume, prenume from casieri ");
        List<Casier> cas = new ArrayList<>();
        if (rset != null) {
            try {
                while (rset.next()) {
                    Casier tmp = new Casier(rset.getInt("id"), rset.getString("nume"),
                            rset.getString("prenume"));
                    cas.add(tmp);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cas;

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
