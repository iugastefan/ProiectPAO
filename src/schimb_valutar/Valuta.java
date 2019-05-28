package schimb_valutar;

import java.util.Objects;

public class Valuta {
    private String nume;
    private String prescurtare;
    private double curs;
    private double comision_cumparare;
    private double comision_vanzare;

    @Override
    public String toString() {
        return nume + "," + prescurtare + "," + curs + "," + comision_cumparare + "," + comision_vanzare;
    }

    public Valuta(String nume, String prescurtare, double curs, double comision_cumparare, double comision_vanzare) {
        this.nume = nume;
        this.prescurtare = prescurtare;
        this.curs = curs;
        this.comision_cumparare = comision_cumparare;
        this.comision_vanzare = comision_vanzare;
    }

    public Valuta(Valuta valutaVeche, double curs, double comision_cumparare, double comision_vanzare) {
        this.nume = valutaVeche.nume;
        this.prescurtare = valutaVeche.prescurtare;
        this.curs = curs;
        this.comision_cumparare = comision_cumparare;
        this.comision_vanzare = comision_vanzare;
    }

    public String getNume() {
        return nume;
    }

    public String getPrescurtare() {
        return prescurtare;
    }

    public double getCurs() {
        return curs;
    }

    public double getComision_cumparare() {
        return comision_cumparare;
    }

    public double getComision_vanzare() {
        return comision_vanzare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Valuta valuta = (Valuta) o;
        return Double.compare(valuta.curs, curs) == 0 &&
                Double.compare(valuta.comision_cumparare, comision_cumparare) == 0 &&
                Double.compare(valuta.comision_vanzare, comision_vanzare) == 0 &&
                nume.equals(valuta.nume) &&
                prescurtare.equals(valuta.prescurtare);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, prescurtare, curs, comision_cumparare, comision_vanzare);
    }
}

