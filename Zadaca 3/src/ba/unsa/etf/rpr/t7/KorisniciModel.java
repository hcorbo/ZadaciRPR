package ba.unsa.etf.rpr.t7;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Scanner;

public class KorisniciModel {
    private static KorisniciModel instance;
    private Connection conn;
    private ObservableList<Korisnik> korisnici = FXCollections.observableArrayList();
    private SimpleObjectProperty<Korisnik> trenutniKorisnik = new SimpleObjectProperty<>();

    private PreparedStatement korisniciUpit, izmijeniKorisnikaUpit, obrisiKorisnikaUpit, dodajKorisnikaUpit, odrediIdUpit;
    public KorisniciModel() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:korisnici.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            korisniciUpit = conn.prepareStatement("SELECT id, ime, prezime, email, korisnicko_ime, lozinka, slika FROM korisnik");
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                korisniciUpit = conn.prepareStatement("SELECT id, ime, prezime, email, korisnicko_ime, lozinka, slika FROM korisnik");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        try {
            izmijeniKorisnikaUpit = conn.prepareStatement("UPDATE korisnik SET ime=?, prezime=?, email=?, korisnicko_ime=?, lozinka=?, slika=? where id=?");
            obrisiKorisnikaUpit = conn.prepareStatement("DELETE FROM korisnik WHERE id=?");
            dodajKorisnikaUpit = conn.prepareStatement("INSERT INTO korisnik VALUES (?,?,?,?,?,?,?)");
            odrediIdUpit = conn.prepareStatement("SELECT max(id)+1 FROM korisnik");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static KorisniciModel getInstance(){
        if(instance == null) instance = new KorisniciModel();
        return instance;
    }

    public static void diskonektuj() {
        if (instance != null) {
            try {
                instance.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        instance = null;
    }

    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("korisnici.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.length() > 1 && sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ne postoji SQL datoteka, nastavljam sa praznom bazom");
        }
    }

    public void napuni() {
        try {
            ResultSet rs = korisniciUpit.executeQuery();
            while(rs.next()){
                Korisnik k = new Korisnik(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                korisnici.add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        trenutniKorisnik.set(null);
    }

    public ObservableList<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(ObservableList<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    public Korisnik getTrenutniKorisnik() {
        return trenutniKorisnik.get();
    }

    public SimpleObjectProperty<Korisnik> trenutniKorisnikProperty() {
        return trenutniKorisnik;
    }

    public void setTrenutniKorisnik(Korisnik trenutniKorisnik) {
        if(this.getTrenutniKorisnik() != null)
            izmijeniKorisnika(this.getTrenutniKorisnik());
        this.trenutniKorisnik.set(trenutniKorisnik);
    }

    public void setTrenutniKorisnik(int i) {
        izmijeniKorisnika(korisnici.get(i));
        this.trenutniKorisnik.set(korisnici.get(i));
    }

    public void izmijeniKorisnika(Korisnik k){
        try {
            izmijeniKorisnikaUpit.setString(1, k.getIme());
            izmijeniKorisnikaUpit.setString(2, k.getPrezime());
            izmijeniKorisnikaUpit.setString(3, k.getEmail());
            izmijeniKorisnikaUpit.setString(4, k.getUsername());
            izmijeniKorisnikaUpit.setString(5, k.getPassword());
            izmijeniKorisnikaUpit.setString(6, k.getSlika());
            izmijeniKorisnikaUpit.setInt(7, k.getId());
            izmijeniKorisnikaUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void obrisiKorisnika(Korisnik k){
        try {
            obrisiKorisnikaUpit.setInt(1, k.getId());
            obrisiKorisnikaUpit.executeUpdate();
            korisnici.remove(k);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajKorisnika(Korisnik k){
        ResultSet rs = null;
        try {
            rs = odrediIdUpit.executeQuery();
            int id = 1;
            if(rs.next()){
                id = rs.getInt(1);
            }
            dodajKorisnikaUpit.setInt(1, id);
            dodajKorisnikaUpit.setString(2, k.getIme());
            dodajKorisnikaUpit.setString(3, k.getPrezime());
            dodajKorisnikaUpit.setString(4, k.getEmail());
            dodajKorisnikaUpit.setString(5, k.getUsername());
            dodajKorisnikaUpit.setString(6, k.getPassword());
            dodajKorisnikaUpit.setString(7, k.getSlika());
            dodajKorisnikaUpit.executeUpdate();
            korisnici.add(new Korisnik(id, k.getIme(), k.getPrezime(), k.getEmail(), k.getUsername(), k.getPassword(), k.getSlika()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void zapisiDatoteku(File file) {

    }

    public Connection getConn() { return conn; }
}
