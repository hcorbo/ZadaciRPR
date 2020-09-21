package ba.unsa.etf.rpr.t7;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.io.File;
import java.util.*;

public class KorisnikController {
    public TextField fldIme;
    public TextField fldPrezime;
    public TextField fldEmail;
    public TextField fldUsername;
    public ListView<Korisnik> listKorisnici;
    public PasswordField fldPassword;
    public  ImageView imageViewSlika;
    public Image img;

    private KorisniciModel model = KorisniciModel.getInstance();
    public KorisnikController(KorisniciModel model) {
        this.model = model;
    }


    @FXML
    public void initialize() {
            File file1 = new File("resources/slicice/face-smile.png");
            Image image1 = new Image(file1.toURI().toString());
            imageViewSlika.setImage(image1);

        listKorisnici.setItems(model.getKorisnici());
        listKorisnici.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            model.setTrenutniKorisnik(newKorisnik);

            //Postavljam sliku u ImageView, uzimam URL iz baze
            String imageUrl = model.getTrenutniKorisnik().getSlika();
            if(imageUrl.equals("resources/slicice/face-smile.png")) {
                File file = new File(imageUrl);
                Image image = new Image(file.toURI().toString());
                imageViewSlika.setImage(image);
            }
            else {
                Image image = new Image(imageUrl);
                imageViewSlika.setImage(image);
            }
            imageViewSlika.maxWidth(128);
            imageViewSlika.minHeight(128);
            imageViewSlika.setFitHeight(128);
            imageViewSlika.setFitWidth(128);

            listKorisnici.refresh();
         });

        model.trenutniKorisnikProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if (oldKorisnik != null) {
                fldIme.textProperty().unbindBidirectional(oldKorisnik.imeProperty() );
                fldPrezime.textProperty().unbindBidirectional(oldKorisnik.prezimeProperty() );
                fldEmail.textProperty().unbindBidirectional(oldKorisnik.emailProperty() );
                fldUsername.textProperty().unbindBidirectional(oldKorisnik.usernameProperty() );
                fldPassword.textProperty().unbindBidirectional(oldKorisnik.passwordProperty() );
            }
            if (newKorisnik == null) {
                fldIme.setText("");
                fldPrezime.setText("");
                fldEmail.setText("");
                fldUsername.setText("");
                fldPassword.setText("");
            }
            else {
                fldIme.textProperty().bindBidirectional( newKorisnik.imeProperty() );
                fldPrezime.textProperty().bindBidirectional( newKorisnik.prezimeProperty() );
                fldEmail.textProperty().bindBidirectional( newKorisnik.emailProperty() );
                fldUsername.textProperty().bindBidirectional( newKorisnik.usernameProperty() );
                fldPassword.textProperty().bindBidirectional( newKorisnik.passwordProperty() );
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
        });

        fldPrezime.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldPrezime.getStyleClass().removeAll("poljeNijeIspravno");
                fldPrezime.getStyleClass().add("poljeIspravno");
            } else {
                fldPrezime.getStyleClass().removeAll("poljeIspravno");
                fldPrezime.getStyleClass().add("poljeNijeIspravno");
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
        });

        fldUsername.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            } else {
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPassword.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
            } else {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
            }
        });

        //menuItemSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        //menuOptionExit.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
    }

    public void dodajAction(ActionEvent actionEvent) {
        Korisnik k = new Korisnik("", "", "", "", "");
        model.dodajKorisnika(k);
        listKorisnici.setItems(model.getKorisnici());
        listKorisnici.getSelectionModel().selectLast();
    }

    public void krajAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void obrisiAction(ActionEvent actionEvent) {
        Korisnik korisnik = listKorisnici.getSelectionModel().getSelectedItem();
        if(korisnik == null) return;
        model.obrisiKorisnika(korisnik);
        listKorisnici.refresh();
    }

    public void zatvoriProgramAction(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void aboutAppAction(ActionEvent actionEvent) {
        Image image = new Image("/slicice/maca.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacije o aplikaciji");
        alert.setHeaderText(null);
        alert.setGraphic(imageView);
        alert.setContentText("Verzija 2.0\nGithub repozitorij: https://github.com/RPR-2019\nAutor: Harun Čorbo");
        alert.showAndWait();
    }

    public void snimiDatotekuAction(){
        TextInputDialog dialog = new TextInputDialog("passwd");
        dialog.setTitle("Odabir datoteke");
        dialog.setHeaderText("Datoteka za upis korisnika");
        dialog.setContentText("Upišite ime datoteke za upis korinika:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Ime datoteke za upis: " + result.get());
        }
        //Nije zavrseno
    }

    public void printReportAction (ActionEvent actionEvent) {
        try {
            new PrintReport().showReport(model.getConn());
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public void postaviBosanskiAction (ActionEvent actionEvent) {
        Locale.setDefault(new Locale("bs", "BA"));
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        reloadScene();
    }

    public void postaviEngleskiAction (ActionEvent actionEvent) {
        Locale.setDefault(new Locale("en_US", "US"));
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        reloadScene();
    }

    private void reloadScene(){
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Scene scene = listKorisnici.getScene();
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/korisnici.fxml" ), bundle);
        loader.setController(this);
        try {
            scene.setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Prozor za pretragu slika
    public void otvoriProzorAction (ActionEvent actionEvent) {
        if(listKorisnici.getSelectionModel().getSelectedItem()!=null){
            try {
                SlikeController ctrl = new SlikeController(model);
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/fxml/slike.fxml" ), bundle);
                fxmlLoader.setController(ctrl);
                Parent root1 = null;
                root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Pretraga slike");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
