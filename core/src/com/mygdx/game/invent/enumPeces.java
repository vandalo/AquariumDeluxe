package com.mygdx.game.invent;

public enum enumPeces {
    CRYSTAL_RED("ballBasicBlue"),
    CRYSTAL_BLUE("ballBasicBlue"),
    CRYSTAL_GREEN("ballBasicBlue"),
    CRYSTAL_YELLOW("ballBasicBlue"),
    CRYSTAL_MAGENTA("ballBasicBlue"),
    CRYSTAL_CYAN("ballBasicBlue"),
    CRYSTAL_ORANGE("ballBasicRed");

    private String textureRegion;

    private enumPeces(String textureRegion) {
        this.textureRegion = textureRegion;
    }

    public String getTextureRegion() {
        return textureRegion;
    }
}