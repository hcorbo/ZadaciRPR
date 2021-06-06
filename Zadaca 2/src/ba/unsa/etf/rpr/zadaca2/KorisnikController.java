package ba.unsa.etf.rpr.zadaca2;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.lang.model.SourceVersion.isName;

public class KorisnikController {
    public TextField fldIme;
    public TextField fldPrezime;
    public TextField fldEmail;
    public TextField fldUsername;
    public ListView<Korisnik> listKorisnici;
    public PasswordField fldPassword;
    public PasswordField fldPasswordRepeat;
    public Slider sliderGodinaRodjenja;
    public CheckBox cbAdmin;

    private KorisniciModel model;
    public KorisnikController(KorisniciModel model) {
        this.model = model;
    }


    @FXML
    public void initialize() {
        listKorisnici.setItems(model.getKorisnici());
        listKorisnici.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if (cbAdmin.isSelected()) {
                model.setTrenutniKorisnik(new Administrator(newKorisnik.getIme(), newKorisnik.getPrezime(), newKorisnik.getEmail(), newKorisnik.getUsername(), newKorisnik.getPassword()));
            }else {
            model.setTrenutniKorisnik(newKorisnik); }
            listKorisnici.refresh();
         });

        model.trenutniKorisnikProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            fldPasswordRepeat.setText(model.getTrenutniKorisnik().getPassword());
            if (oldKorisnik != null) {
                fldIme.textProperty().unbindBidirectional(oldKorisnik.imeProperty() );
                fldPrezime.textProperty().unbindBidirectional(oldKorisnik.prezimeProperty() );
                fldEmail.textProperty().unbindBidirectional(oldKorisnik.emailProperty() );
                fldUsername.textProperty().unbindBidirectional(oldKorisnik.usernameProperty() );
                fldPassword.textProperty().unbindBidirectional(oldKorisnik.passwordProperty() );
                sliderGodinaRodjenja.valueProperty().unbindBidirectional(model.getTrenutniKorisnik().godinaRodjenjaProperty());
            }
            if (newKorisnik == null) {
                fldIme.setText("");
                fldPrezime.setText("");
                fldEmail.setText("");
                fldUsername.setText("");
                fldPassword.setText("");
                sliderGodinaRodjenja.setValue(2000);
            }
            else {
                fldIme.textProperty().bindBidirectional( newKorisnik.imeProperty() );
                fldPrezime.textProperty().bindBidirectional( newKorisnik.prezimeProperty() );
                fldEmail.textProperty().bindBidirectional( newKorisnik.emailProperty() );
                fldUsername.textProperty().bindBidirectional( newKorisnik.usernameProperty() );
                fldPassword.textProperty().bindBidirectional( newKorisnik.passwordProperty() );
                sliderGodinaRodjenja.valueProperty().bindBidirectional(model.getTrenutniKorisnik().godinaRodjenjaProperty());
            }
        });

        fldIme.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldIme.getStyleClass().removeAll("poljeNijeIspravno");
                fldIme.getStyleClass().add("poljeIspravno");
            } else {
                fldIme.getStyleClass().removeAll("poljeIspravno");
                fldIme.getStyleClass().add("poljeNijeIspravno");
            }
            if(!onlyLettersSpaces(newIme) || newIme.length()<3){
                fldIme.getStyleClass().removeAll("poljeIspravno");
                fldIme.getStyleClass().add("poljeNijeIspravno");
            }else{
                fldIme.getStyleClass().removeAll("poljeNijeIspravno");
                fldIme.getStyleClass().add("poljeIspravno");
            }
        });

        fldPrezime.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldPrezime.getStyleClass().removeAll("poljeNijeIspravno");
                fldPrezime.getStyleClass().add("poljeIspravno");
            } else {
                fldPrezime.getStyleClass().removeAll("poljeIspravno");
                fldPrezime.getStyleClass().add("poljeNijeIspravno");
            }
            if(!onlyLettersSpaces(newIme) || newIme.length()<3){
                fldPrezime.getStyleClass().removeAll("poljeIspravno");
                fldPrezime.getStyleClass().add("poljeNijeIspravno");
            }else{
                fldPrezime.getStyleClass().removeAll("poljeNijeIspravno");
                fldPrezime.getStyleClass().add("poljeIspravno");
            }
        });

        fldEmail.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldEmail.getStyleClass().removeAll("poljeNijeIspravno");
                fldEmail.getStyleClass().add("poljeIspravno");
            } else {
                fldEmail.getStyleClass().removeAll("poljeIspravno");
                fldEmail.getStyleClass().add("poljeNijeIspravno");
            }
            if(!isEmail(newIme)){
                fldEmail.getStyleClass().removeAll("poljeIspravno");
                fldEmail.getStyleClass().add("poljeNijeIspravno");
            }else{
                fldEmail.getStyleClass().removeAll("poljeNijeIspravno");
                fldEmail.getStyleClass().add("poljeIspravno");
            }
        });

        fldUsername.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            } else {
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
            }
            if(!isName(newIme) || newIme.length()>16){
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
            }else{
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            }
        });

        fldPassword.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                if(model.getTrenutniKorisnik() != null) {
                    model.getTrenutniKorisnik().setPassword(newIme);
                }

                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
                if(!fldPasswordRepeat.getText().equals(fldPassword.getText()) ||  fldPasswordRepeat.getText().isEmpty() || fldPassword.getText().isEmpty()){
                    fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
                    fldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
                    fldPassword.getStyleClass().removeAll("poljeIspravno");
                    fldPassword.getStyleClass().add("poljeNijeIspravno");
                } else if(model.getTrenutniKorisnik() != null && !model.getTrenutniKorisnik().checkPassword()) {
                    fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
                    fldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
                    fldPassword.getStyleClass().removeAll("poljeIspravno");
                    fldPassword.getStyleClass().add("poljeNijeIspravno");
                } else {
                    fldPasswordRepeat.getStyleClass().removeAll("poljeNijeIspravno");
                    fldPasswordRepeat.getStyleClass().add("poljeIspravno");
                    fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                    fldPassword.getStyleClass().add("poljeIspravno");
                }
            } else {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPasswordRepeat.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                if(model.getTrenutniKorisnik() != null)
                fldPasswordRepeat.setText(newIme);
                fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
                fldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
                if(!fldPasswordRepeat.getText().equals(fldPassword.getText()) || fldPasswordRepeat.getText().isEmpty() || fldPassword.getText().isEmpty()){
                    fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
                    fldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
                    fldPassword.getStyleClass().removeAll("poljeIspravno");
                    fldPassword.getStyleClass().add("poljeNijeIspravno");
                } else if(model.getTrenutniKorisnik() != null && !model.getTrenutniKorisnik().checkPassword()) {
                    fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
                    fldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
                    fldPassword.getStyleClass().removeAll("poljeIspravno");
                    fldPassword.getStyleClass().add("poljeNijeIspravno");
                } else {
                    fldPasswordRepeat.getStyleClass().removeAll("poljeNijeIspravno");
                    fldPasswordRepeat.getStyleClass().add("poljeIspravno");
                    fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                    fldPassword.getStyleClass().add("poljeIspravno");
                }
            } else {
                fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
                fldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }

    public void administratorAction(ActionEvent actionEvent) {
        if(model.getTrenutniKorisnik() != null) {
            if (cbAdmin.isSelected())
                model.setTrenutniKorisnik(new Administrator(model.getTrenutniKorisnik().getIme(), model.getTrenutniKorisnik().getPrezime(), model.getTrenutniKorisnik().getEmail(), model.getTrenutniKorisnik().getUsername(), model.getTrenutniKorisnik().getPassword()));
            else model.setTrenutniKorisnik(new Korisnik(model.getTrenutniKorisnik().getIme(), model.getTrenutniKorisnik().getPrezime(), model.getTrenutniKorisnik().getEmail(), model.getTrenutniKorisnik().getUsername(), model.getTrenutniKorisnik().getPassword()));
        }
    }

    public void dodajAction(ActionEvent actionEvent) {
        model.getKorisnici().add(new Korisnik("", "", "", "", ""));
        listKorisnici.getSelectionModel().selectLast();
    }

    public void obrisiAction(ActionEvent actionEvent) {
        model.getKorisnici().remove(model.getTrenutniKorisnik());
    }

    public void generisiAction(ActionEvent actionEvent) {
        if(cbAdmin.isSelected()){
            generisiPasswordAdmin();
        }
        else{
            generisiPassword();
        }
        String trenutnoIme = model.getTrenutniKorisnik().getIme();
        String trenutnoPrezime = model.getTrenutniKorisnik().getPrezime();
        if(trenutnoIme.isEmpty()) return;
        trenutnoIme = zamijeniSlova(trenutnoIme);
        trenutnoPrezime = zamijeniSlova(trenutnoPrezime);
        String rezultat = trenutnoIme.substring(0, 1);
        rezultat = rezultat + trenutnoPrezime;
        model.getTrenutniKorisnik().setUsername(rezultat);
    }

    public void generisiPassword(){
        String randomPassword = "";
        while(!model.getTrenutniKorisnik().checkPassword()) {
            for (int i = 0; i < 8; i++) {
                int izbor = (int) (Math.random() * 3) + 1;
                if (izbor == 1) {
                    char maloSlovo = (char) ('a' + Math.random() * ('z' - 'a'));
                    randomPassword = randomPassword + maloSlovo;
                } else if (izbor == 2) {
                    char velikoSlovo = (char) ('A' + Math.random() * ('Z' - 'A'));
                    randomPassword = randomPassword + velikoSlovo;
                } else {
                    int broj = (int) (Math.random() * 9);
                    randomPassword = randomPassword + broj;
                }
            }
            model.getTrenutniKorisnik().setPassword(randomPassword);
            fldPasswordRepeat.setText(model.getTrenutniKorisnik().getPassword());
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Infromacija");
        alert.setHeaderText("Vasa lozinka glasi: ");
        alert.setContentText(model.getTrenutniKorisnik().getPassword());
        alert.show();

    }

    public void generisiPasswordAdmin(){
        if(cbAdmin.isSelected()){
            model.setTrenutniKorisnik(new Administrator(model.getTrenutniKorisnik().getIme(), model.getTrenutniKorisnik().getPrezime(), model.getTrenutniKorisnik().getEmail(), model.getTrenutniKorisnik().getUsername(), model.getTrenutniKorisnik().getPassword()));
        }
        String randomPassword = "";
        String specijalniKarakteri = "!@#$%^&*_=+-/`:";
        while(!model.getTrenutniKorisnik().checkPassword()) {
            for (int i = 0; i < 8; i++) {
                int izbor = (int) (Math.random() * 4) + 1;
                if (izbor == 1) {
                    char maloSlovo = (char) ('a' + Math.random() * ('z' - 'a'));
                    randomPassword = randomPassword + maloSlovo;
                } else if (izbor == 2) {
                    char velikoSlovo = (char) ('A' + Math.random() * ('Z' - 'A'));
                    randomPassword = randomPassword + velikoSlovo;
                }
                else if (izbor == 3) {
                    int izborKaraktera = (int) (Math.random() * 13);
                    randomPassword = randomPassword + specijalniKarakteri.charAt(izborKaraktera);
                } else {
                    int broj = (int) (Math.random() * 9);
                    randomPassword = randomPassword + broj;
                }
            }
            model.getTrenutniKorisnik().setPassword(randomPassword);
            fldPasswordRepeat.setText(model.getTrenutniKorisnik().getPassword());
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Infromacija");
        alert.setHeaderText("Vasa lozinka glasi: ");
        alert.setContentText(model.getTrenutniKorisnik().getPassword());
        alert.show();
    }

    public String zamijeniSlova(String rijec){
        rijec = rijec.toLowerCase();
        rijec = rijec.replace("č", "c");
        rijec = rijec.replace("ć", "c");
        rijec = rijec.replace("š", "s");
        rijec = rijec.replace("đ", "d");
        rijec = rijec.replace("ž", "z");
        return rijec;
    }

    public void krajAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public boolean onlyLettersSpaces(String s){
        for(int i=0; i<s.length() ;i++){
            char ch = s.charAt(i);
            if (Character.isLetter(ch) || ch == ' ' || ch == '-') {
                continue;
            }
            return false;
        }
        return true;
    }

    public boolean isEmail(String email){
        String emailRegex =
                "[a-zA-Z0-9_+&*-.]+@" +
                "[a-zA-Z0-9_+&*-.]+";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
