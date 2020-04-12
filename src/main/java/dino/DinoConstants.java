package dino;

public class DinoConstants {

    public static final boolean IN_DEBUG_MODE = Boolean.FALSE;
    public static final int GAME_CANVAS_WIDTH = 500;
    public static final int PIXEL_NOT_FOUND = -1;
    public static final int DINO_X_AXIS = 78;
    public static final int DINO_Y_AXIS = 154;
    public static final int CLUSTERED_CACTUS_SIZE = 35;
    public static final int JUMP_SAFE_DISTANCE = 179;
    public static final int GRAY_SCALE_PIXEL_COLOR = 90;
    public static final int PIXELS_BUFFER = 15;

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
