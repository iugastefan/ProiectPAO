package schimb_valutar;

public class Valuta {
    private String nume;
    private String prescurtare;
    private double curs;
    private double comision_cumparare;
    private double comision_vanzare;

    @Override
    public String toString() {
        return "<Valuta nume=\""+nume + "\" prescurtare=\"" + prescurtare + "\" curs=\"" +curs + "\" comision_cumparare=\"" + comision_cumparare + "\" comision_vanzare=\"" + comision_vanzare+"\"/>";
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

    String getPrescurtare() {
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
}

