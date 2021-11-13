package pl.first.firstjava;

public class SudokuPlayer implements IObserver {

    public String onValueChanged(boolean isValid) {
        String msg = "Wartość pola zmieniona!"
                + "\nWartośc ta jest " + (isValid ? "" : "nie") + "poprawna";
        return msg;
    }
}
