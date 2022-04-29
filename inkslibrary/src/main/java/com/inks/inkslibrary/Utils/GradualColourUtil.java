package com.inks.inkslibrary.Utils;

public class GradualColourUtil {
    private int startColour;
    private int endColour;

    public GradualColourUtil(int startColour, int endColour) {
        this.startColour = startColour;
        this.endColour = endColour;
    }

    public int getColour(float per) {
        int R = (int) ((endColour - startColour) / 65536 % 256 * per);
        int G = (int) ((endColour - startColour) / 256 % 256 * per);
        int B = (int) ((endColour - startColour) % 256 * per);
        int returnR = startColour / 65536 + R;
        int returnG = startColour / 256 % 256 + G;
        int returnB = startColour % 256 + B;
        return returnR * 65536 + returnG * 256 + returnB +0XFF000000;
    }
}
