package ba.unsa.etf.rpr.zadaca2;

import java.util.regex.Pattern;

public class Administrator extends Korisnik {

    public Administrator(String ime, String prezime, String email, String username, String password) {
        super(ime, prezime, email, username, password);
    }

    @Override
    public boolean checkPassword() {
        String emailRegex =  "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-/`:])[a-zA-Z0-9!@#$%^&*_=+-/`:]{1,}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (this.getPassword() == null)
            return false;
        return (pat.matcher(this.getPassword()).matches());
    }
}
