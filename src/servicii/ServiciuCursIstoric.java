package servicii;

import schimb_valutar.Date;
import schimb_valutar.Valuta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

class ServiciuCursIstoric {
    private Map<Date, List<Valuta>> istoric;
    private static ServiciuCursIstoric instance;

    static ServiciuCursIstoric getInstance() {
        if (instance == null)
            instance = new ServiciuCursIstoric();
        return instance;
    }

    private ServiciuCursIstoric() {
        istoric = getIstoricValutaDB();
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

    private Map<Date, List<Valuta>> getIstoricValutaDB() {
        ServiciuDB s = ServiciuDB.getInstance();
        ResultSet rset = s.executeQuery("select * from istoric_valuta");
        istoric = new TreeMap<>(Collections.reverseOrder());
        if (rset != null) {
            try {
                while (rset.next()) {
                    List<Integer> dlist = Arrays.stream(rset.getDate("data").toString().split("-")).
                            map(Integer::parseInt).collect(Collectors.toList());
                    Date date = new Date(dlist.get(2), dlist.get(1), dlist.get(0));
                    if (!istoric.containsKey(date)) {
                        List<Valuta> tmp = new ArrayList<>();
                        istoric.put(date, tmp);
                    }
                    Valuta tmp = new Valuta(
                            rset.getString("nume"),
                            rset.getString("prescurtare"),
                            rset.getDouble("curs"),
                            rset.getDouble("comision_cumparare"),
                            rset.getDouble("comision_vanzare")
                    );
                    istoric.get(date).add(tmp);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return istoric;

    }


    void addValuta(Date date, Valuta valuta) {
        if (!istoric.containsKey(date)) {
            List<Valuta> tmp = new ArrayList<>();
            istoric.put(date, tmp);
        }
        if (!istoric.get(date).contains(valuta)) {
            istoric.get(date).add(valuta);
            addValutaDB(date, valuta);
        }
    }

    private void addValutaDB(Date date, Valuta valuta) {
        Servicii s = Servicii.getInstance();
        s.executeQuery("insert into istoric_valuta values(to_date(\'" + date.toString() + "\',\'dd/mm/yyyy\'),\'" +
                valuta.getNume() + "\',\'" + valuta.getPrescurtare() + "\'," + valuta.getCurs() + "," + valuta.getComision_cumparare() + "," + valuta.getComision_vanzare() + ")");
    }

    private void stergeValutaDB(Date date, Valuta valuta) {
        Servicii s = Servicii.getInstance();
        s.executeQuery("delete from istoric_valuta where data = (to_date(\'" + date.toString() + "\',\'dd/mm/yyyy\') and \'" +
                valuta.getNume() + "\'=nume");
    }

    private void updateValutaDB(Date date, Valuta valuta_veche, Valuta valuta_noua) {
        Servicii s = Servicii.getInstance();
        s.executeQuery("update istoric_valuta set nume=\'" + valuta_noua.getNume() +
                "\', prescurtare=\'" + valuta_noua.getPrescurtare() +
                "\', curs=" + valuta_noua.getCurs() +
                ",comision_cumparare=" + valuta_noua.getComision_cumparare() +
                ",comision_vanzare=" + valuta_noua.getComision_vanzare() +
                " where data = (to_date(\'" + date.toString() + "\',\'dd/mm/yyyy\') and \'" +
                valuta_veche.getNume() + "\'=nume");
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
