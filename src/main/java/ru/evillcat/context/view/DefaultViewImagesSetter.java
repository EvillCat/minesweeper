package ru.evillcat.context.view;

import ru.evillcat.context.view.image.ButtonImage;
import ru.evillcat.view.GameView;

public class DefaultViewImagesSetter {

    private final GameView view;

    public DefaultViewImagesSetter(GameView view) {
        this.view = view;
    }

    public void setDefaultViewImages() {
        view.setDefaultFlagImage(ButtonImage.getFlagImage());
        view.setDefaultFlagImageName(ButtonImage.FLAG);
        view.setDefaultBlockImage(ButtonImage.getDefaultBlock());
        view.setDefaultBlockImageName(ButtonImage.BLOCK);
    }
}
