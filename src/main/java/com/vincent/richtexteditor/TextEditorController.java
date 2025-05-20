package com.vincent.richtexteditor;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.MenuItem;

public class TextEditorController {
    @FXML private TextArea textArea;
    @FXML private Label statusLabel;

    private Rope rope;

    @FXML
    public void initialize() {
        rope = new Rope();
    }

    @FXML
    private void handleOpen() {
        statusLabel.setText("File opened");
    }

    @FXML
    private void handleSave() {
        rope = new Rope(textArea.getText());
        statusLabel.setText("File saved");
    }

    @FXML
    private void handleUndo() {
        statusLabel.setText("Undo last action");
    }

}