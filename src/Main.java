import schimb_valutar.Date;
import schimb_valutar.Valuta;
import servicii.Servicii;

import java.time.LocalDate;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Servicii servicii = Servicii.getInstance();
        Valuta v = new Valuta("Euro", "EUR", 4.7, 0.02, 0.03);
        Valuta v2 = new Valuta("Dollar", "USD", 4.1, 0.02, 0.03);
        servicii.adaugaValuta(v);
        servicii.adaugaValuta(v2);
        servicii.adaugaBani(v.getPrescurtare(), 500.0);
        servicii.adaugaBani(v2.getPrescurtare(), 600.0);
        System.out.println(servicii.afisCont());
        try {
            servicii.cumparaValuta(v, v2, 300.0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(servicii.afisCont());
        Optional<Valuta> v3 = servicii.getInfoValutaActual("USD");
        if (v3.isPresent())
            System.out.println(v3.get().getCurs());
        else {
            System.out.println("Valuta USD nu exista");
        }
        Optional<Valuta> v4 = servicii.getInfoValutaActual("RON");
        if (v4.isPresent())
            System.out.println(v4.get().getNume());
        else {
            System.out.println("Valuta RON nu exista");
        }

//        Valuta v6 = new Valuta("Dollar", "USD", 4.4, 0.02, 0.03);
//        try {
//            servicii.updateValutaActual(v6);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
        Optional<Valuta> v5 = servicii.getInfoValutaActual("USD");
        if (v5.isPresent())
            System.out.println(v5.get().getCurs());
        else {
            System.out.println("Valuta nu exista");
        }

        servicii.putActualInIstoric();
        System.out.println("Istoric: ");
        servicii.printIstoric();

//        servicii.addCasier("Iuga", "Stefan");
//        servicii.addCasier("Popescu", "Ionescu");
        servicii.printCasieri();

//        servicii.addClient("Gigi", "Gigel", 123456789123L, "RK", 1234);
//        servicii.addClient("Gigica", "Gigelica", 911923841234L, "RD", 1221);
        servicii.printClienti();

        LocalDate date = LocalDate.now();
        Date mydate = new Date(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        try {
            servicii.getValutaLaData(mydate, "USD");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
