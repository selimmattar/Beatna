package comm.example.emirc.beatnafirsttry;

public class Song {
    private String nom;

    public String getNom(){ return nom; }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public Song(String nom)
    {
        this.nom=nom;
    }
}
