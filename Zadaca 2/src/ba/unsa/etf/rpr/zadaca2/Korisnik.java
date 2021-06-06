package ba.unsa.etf.rpr.zadaca2;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Korisnik {
    private SimpleStringProperty ime, prezime, email, username, password;
    private SimpleIntegerProperty godinaRodjenja =  new SimpleIntegerProperty(2000);

    public Korisnik(String ime, String prezime, String email, String username, String password) {
        this.ime = new SimpleStringProperty(ime);
        this.prezime = new SimpleStringProperty(prezime);
        this.email = new SimpleStringProperty(email);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
    }
    @Override
    public String toString() {
        return prezime.get() + " " + ime.get();
    }

    public String getIme() {
        return ime.get();
    }

    public SimpleStringProperty imeProperty() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime.set(ime);
    }

    public String getPrezime() {
        return prezime.get();
    }

    public SimpleStringProperty prezimeProperty() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime.set(prezime);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public int getGodinaRodjenja() {
        return godinaRodjenja.get();
    }

    public SimpleIntegerProperty godinaRodjenjaProperty() {
        return godinaRodjenja;
    }

    public void setGodinaRodjenja(int godinaRodjenja) {
        this.godinaRodjenja.set(godinaRodjenja);
    }

    public boolean checkPassword() {
        String passwordRegex =  "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{1,}$";
        Pattern pat = Pattern.compile(passwordRegex);
        if (password.get() == null)
            return false;
        return (pat.matcher(password.get()).matches());
    }
}
