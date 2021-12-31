package pl.comp.model;

public enum DifficultyLevel {

    Easy(20),
    Normal(35),
    Hard(50);

    private final int fieldsToDelete;

    DifficultyLevel(int fieldsToDelete) {
        this.fieldsToDelete = fieldsToDelete;
    }

    public int getFieldsToDelete() {
        return this.fieldsToDelete;
    }
}
