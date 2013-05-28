/*******************************************************************************
 * @author Reika
 * 
 * This code is the property of and owned and copyrighted by Reika.
 * This code is provided under a modified visible-source license that is as follows:
 * 
 * Any and all users are permitted to use the source for educational purposes, or to create other mods that call
 * parts of this code and use DragonAPI as a dependency.
 * 
 * Unless given explicit written permission - electronic writing is acceptable - no user may redistribute this
 * source code nor any derivative works. These pre-approved works must prominently contain this copyright notice.
 * 
 * Additionally, no attempt may be made to achieve monetary gain from this code by anyone except the original author.
 * In the case of pre-approved derivative works, any monetary gains made will be shared between the original author
 * and the other developer(s), proportional to the ratio of derived to original code.
 * 
 * Finally, any and all displays, duplicates or derivatives of this code must be prominently marked as such, and must
 * contain attribution to the original author, including a link to the original source. Any attempts to claim credit
 * for this code will be treated as intentional theft.
 * 
 * Due to the Mojang and Minecraft Mod Terms of Service and Licensing Restrictions, compiled versions of this code
 * must be provided for free. However, with the exception of pre-approved derivative works, only the original author
 * may distribute compiled binary versions of this code.
 * 
 * Failure to comply with these restrictions is a violation of copyright law and will be dealt with accordingly.
 ******************************************************************************/
package Reika.DragonAPI.IO;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import Reika.DragonAPI.Libraries.ReikaJavaLibrary;

public final class ReikaPNGLoader {

	private ReikaPNGLoader() {throw new RuntimeException("The class "+this.getClass()+" cannot be instantiated!");}

	public static int textureMap;
	public static BufferedImage missingtex = new BufferedImage(64, 64, 2);

    /** Returns a BufferedImage read off the provided filepath, or, failing that, a backup hard-coded path.
     * Args: Root class, filepath, Backup Direct FilePath (include C:/ or other letter drive) */
    public static BufferedImage readTextureImage(Class root, String name, String back)
    {
    	InputStream inputfile = root.getResourceAsStream(name);
    	InputStream inputback = root.getResourceAsStream(back);
    	setMissingTex();
    	if (inputfile == null && inputback == null) {
    		ReikaJavaLibrary.pConsole("Neither default image filepath at "+name+" or backup at "+back+" found. Loading \"MissingTexture\".");
    		return missingtex;
    	}
    	if (inputfile == null && inputback != null) {
    		ReikaJavaLibrary.pConsole("Default image filepath at "+name+" does not exist. Switching to backup at "+back+".");
			try {
				return ImageIO.read(inputback);
			} catch (IOException e1) {
				ReikaJavaLibrary.pConsole("Backup image filepath at "+back+" not found. Loading \"MissingTexture\".");
				//e1.printStackTrace();
			}
			return missingtex;
    	}
        BufferedImage bufferedimage = null;
		try {
			return ImageIO.read(inputfile);
		}
		catch (IOException e) {
			if (back == null) {
				ReikaJavaLibrary.pConsole("Backup image filepath at "+back+" does not exist. Loading \"MissingTexture\".");
				return missingtex;
			}
			ReikaJavaLibrary.pConsole("Default image filepath at "+name+" not found. Switching to backup at "+back+".");
			try {
				return ImageIO.read(inputback);
			} catch (IOException e1) {
				ReikaJavaLibrary.pConsole("Backup image filepath at "+back+" not found. Loading \"MissingTexture\".");
				//e1.printStackTrace();
				return missingtex;
			}
		}
    }

    public static boolean imageFileExists(Class root, String name) {
    	InputStream inputfile = root.getResourceAsStream(name);
    	if (inputfile == null) {
    		return false;
    	}
        BufferedImage bufferedimage = null;
		try {
			bufferedimage = ImageIO.read(inputfile);
		}
		catch (IOException e) {
				return false;
		}
    	return true;
    }

    public static BufferedImage getMissingTex() {
    	setMissingTex();
    	return missingtex;
    }

    private static void setMissingTex() {
        Graphics graphics = missingtex.getGraphics();
        graphics.setColor(Color.decode("0x2F0044"));
        graphics.fillRect(0, 0, 64, 64);
        graphics.setColor(Color.decode("0x7F6A00"));
        int i = 10;
        int j = 0;
        while (i < 64) {
            String s = j++ % 2 == 0 ? "missing" : "texture";
            graphics.drawString(s, 1, i);
            i += graphics.getFont().getSize();
            if (j % 2 == 0)
                i += 5;
        }
        graphics.dispose();
    }
}