package ru.evillcat.view.custom.button;

import javax.swing.*;

public abstract class JButtonWithData<T> extends JButton {

    protected T data;

    public abstract T getData();

}
