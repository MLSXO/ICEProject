/**
 * PowerUp repræsenterer midlertidige effekter i Snake-spillet
 * Bruges af GameEngine og vises i GUI via getActivePowerUp()
 */
public class PowerUp {

    public enum Type {
        GHOST,      // Slangen kan passere sig selv
        SLOW,       // Slangen bevæger sig langsommere
        DOUBLE_SCORE // Dobbelt point
    }

    private Type type;
    private long durationMs;
    private long startTime;

    public PowerUp(Type type, long durationMs) {
        this.type = type;
        this.durationMs = durationMs;
        this.startTime = System.currentTimeMillis();
    }

    /** Starter powerup (resetter tiden) */
    public void activate() {
        startTime = System.currentTimeMillis();
    }

    /** Tjekker om powerup stadig er aktiv */
    public boolean isActive() {
        return System.currentTimeMillis() - startTime < durationMs;
    }

    /** Bruges af GUI */
    public String getName() {
        switch (type) {
            case GHOST:
                return "Ghost Mode";
            case SLOW:
                return "Slow Motion";
            case DOUBLE_SCORE:
                return "Double Score";
            default:
                return "None";
        }
    }

    public Type getType() {
        return type;
    }
}
