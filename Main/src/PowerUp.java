
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


    public void activate() {
        startTime = System.currentTimeMillis();
    }


    public boolean isActive() {
        return System.currentTimeMillis() - startTime < durationMs;
    }


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
