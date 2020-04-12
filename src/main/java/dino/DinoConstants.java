package dino;

public class DinoConstants {

    public static final boolean IN_DEBUG_MODE = Boolean.FALSE;
    public static final int GAME_CANVAS_WIDTH = 500;
    public static final int SPEED_UNAVAILABLE = 0;
    public static final int DINO_X_AXIS = 78;
    public static final int DINO_Y_AXIS = 154;
    public static final int CLUSTERED_CACTUS_SIZE = 35;
    public static final int JUMP_SAFE_DISTANCE = 179;

    public static void printDebugMessage(String message) {
        if (DinoConstants.IN_DEBUG_MODE) {
            System.out.println(message);
        }
    }

    /*
     * Runner.instance_.currentSpeed;
     * Runner.instance_.config.GRAVITY
     *
     */
}
