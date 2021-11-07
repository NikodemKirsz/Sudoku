package pl.first.firstjava;

public class SudokuPlayer implements IObserver {

    public void onValueChanged(boolean isValid) {
        System.out.println("Wartość pola zmieniona!"
                + "\nWartośc ta jest " + (isValid ? "" : "nie") + "poprawna");
    }
}
