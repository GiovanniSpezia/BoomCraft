package it.giovannispezia.boomCraft.enums;

public enum ExplosionType {
    NORMAL,
    FIRE,
    SHOCKWAVE;

    public static ExplosionType fromString(String input) {
        try {
            return ExplosionType.valueOf(input.toUpperCase());
        } catch (Exception e) {
            return NORMAL;
        }
    }
}