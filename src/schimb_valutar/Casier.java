package schimb_valutar;

public class Casier extends Persoana {
    // TODO: de luat din DB ultimul id folosit
    static private int nextId = 1;

    private int id;

    public int getId() {
        return id;
    }


    public Casier(String nume, String prenume) {
        this.nume = nume;
        this.prenume = prenume;
        this.id = nextId++;
    }

    public Casier(int id, String nume, String prenume) {
        this.nume = nume;
        this.prenume = prenume;
        this.id = id;
        Casier.nextId = (Casier.nextId >= id) ? Casier.nextId + 1 : id + 1;
    }

    @Override
    public String toString() {
        return "<Casier id=\"" + id + "\" nume=\"" + nume + "\" prenume=\"" + prenume + "\"/>";
    }
}
