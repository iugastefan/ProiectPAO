package schimb_valutar;

public class Client extends Persoana {
    private long cnp;
    private String serie_id;
    private int numar_id;

    public Client(String nume, String prenume, long cnp, String serie_id, int numar_id) {
        this.nume = nume;
        this.prenume = prenume;
        this.cnp = cnp;
        this.serie_id = serie_id;
        this.numar_id = numar_id;
    }

    public long getCnp() {
        return cnp;
    }

    public String getSerie_id() {
        return serie_id;
    }

    public int getNumar_id() {
        return numar_id;
    }

    @Override
    public String toString() {
        return "<Client cnp=\"" + cnp + "\" serie=\"" + serie_id + "\" numar=\"" + numar_id + "\" nume=\"" + nume + "\" prenume=\"" + prenume+"\"/>";
    }
}
