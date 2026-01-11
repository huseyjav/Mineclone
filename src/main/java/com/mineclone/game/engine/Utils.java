package com.mineclone.game.engine;

import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Utils {
    public static ByteBuffer loadImage(String path, int[] width, int[] height, int[] channels) {
        stbi_set_flip_vertically_on_load(true); // Optional (recommended for OpenGL)

        try (MemoryStack stack = stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer c = stack.mallocInt(1);

            ByteBuffer image = stbi_load(path, w, h, c, 0);
            if (image == null) {
                throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
            }

            width[0] = w.get(0);
            height[0] = h.get(0);
            channels[0] = c.get(0);
            return image;
        }
    }

}
