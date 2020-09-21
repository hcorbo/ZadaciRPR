package ba.unsa.etf.rpr.t7;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class SlikeController {
    KorisniciModel model = KorisniciModel.getInstance();
    public Button btnCancel;
    public Button btnSearch;
    public Button btnOk;
    public TextField fldPretraga = null;
    public Button btnSelektovanaSlika = null;
    public TilePane tilePaneSlike;
    public ScrollPane scrollPane;
    private String selektovanaSlikaURL = "resources/slicice/face-smile.png";

    final private String APIKEY = "ZbscMMjQmLP76J2zsaqKzGKKHiM7vKUO";
    public SlikeController(KorisniciModel model) {
        this.model = model;
    }

    public void zatvoriPretraguSlikaAction(ActionEvent actionEvent){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    //Pretraga slika sa giphya
    public void searchAction(ActionEvent actionEvent) {
        scrollPane.setFitToWidth(true);
        Platform.runLater(()-> tilePaneSlike.getChildren().removeAll());
        String adr = "https://api.giphy.com/v1/gifs/search?api_key="  + APIKEY  + "&q=" + fldPretraga.getText().replaceAll("[\\s]", "+");
            try {
                URL url = new URL(adr);
                BufferedReader ulaz = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
                String json = "", line = null;
                while ((line = ulaz.readLine()) != null)
                json = json + line;
                JSONObject obj = new JSONObject(json);
                int n = obj.getJSONArray("data").length();
                for(int i=0; i<n; i++){
                    String imageURL = "http://i.giphy.com/media/" + obj.getJSONArray("data").getJSONObject(i).getString("id") + "/giphy_s.gif";
                    Button button = new Button();
                    //button.setGraphic();
                    button.setStyle("-fx-background-image: url(" + imageURL + ");  -fx-background-size: 150; -fx-min-width: 128; -fx-min-height: 128");
                    button.setOnAction(actionEvent1 -> {
                        btnSelektovanaSlika = button;
                        selektovanaSlikaURL = imageURL;
                    });
                    Platform.runLater(()-> tilePaneSlike.getChildren().add(button));
                }
                ulaz.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public void okAction(ActionEvent actionEvent){
        //Ako nije selektovana slika dobijamo upozorenje
        if(btnSelektovanaSlika == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nijedna slika nije izabrana");
            alert.setHeaderText("Niste izabrali sliku koju zelite");
            alert.setContentText("Unesite a zatim izaberite sliku ili klinite na cancel");
            alert.showAndWait();
        } else {
            //Postavljamo izabranu sliku u bazu za trennutnog korinika
            Korisnik k = model.getTrenutniKorisnik();
            k.setSlika(selektovanaSlikaURL);
            model.izmijeniKorisnika(k);
            Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
        }
    }
}
