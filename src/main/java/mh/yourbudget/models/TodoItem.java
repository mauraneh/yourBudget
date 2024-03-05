package mh.yourbudget.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TodoItem {

    private final StringProperty text = new SimpleStringProperty();
    private final BooleanProperty completed = new SimpleBooleanProperty();

    public TodoItem(String text, boolean completed) {
        this.text.set(text);
        this.completed.set(completed);
    }

    public StringProperty textProperty() {
        return text;
    }

    public BooleanProperty completedProperty() {
        return completed;
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public boolean isCompleted() {
        return completed.get();
    }

    public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }

    @Override
    public String toString() {
        return getText();
    }
}

