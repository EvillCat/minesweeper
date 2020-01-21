package ru.evillcat.view.custom.button;

import javax.swing.*;

public class DataJButton extends JButton {

    private boolean isClosedForDefaultIconPairOperations = false;

    public void closeForDefaultIconPairOperations() {
        isClosedForDefaultIconPairOperations = true;
    }

    public void openForDefaultIconPairOperations() {
        isClosedForDefaultIconPairOperations = false;
    }

    public boolean isClosed() {
        return !isClosedForDefaultIconPairOperations;
    }

}
