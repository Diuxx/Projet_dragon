package org.lpdql.dragon;

import org.lpdql.dragon.system.StateGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.io.File;
import java.lang.reflect.Field;

import static org.lpdql.dragon.singleton.InterStateComm.gX;
import static org.lpdql.dragon.singleton.InterStateComm.gY;

public class App {
	public static void main(String[] args) throws SlickException {

	    // add programmaticaly library path.
	    addToJavaLibraryPath(new File("lib/natives-windows"));

	    // launch game app
        new AppGameContainer(new StateGame(), gX, gY, false).start();
    }

    /**
     * Ajoute un nouveau répertoire dans le java.library.path.
     * @param dir Le nouveau répertoire à ajouter. bb
     */
    public static void addToJavaLibraryPath(File dir) {
        final String LIBRARY_PATH = "java.library.path";
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException(dir + " is not a directory.");
        }
        String javaLibraryPath = System.getProperty(LIBRARY_PATH);
        System.setProperty(LIBRARY_PATH, javaLibraryPath + File.pathSeparatorChar + dir.getAbsolutePath());

        resetJavaLibraryPath();
    }

    /**
     * Supprime le cache du "java.library.path".
     * Cela forcera le classloader à revérifier sa valeur lors du prochaine chargement de librairie.
     *
     * Attention : ceci est spécifique à la JVM de Oracle et pourrait ne pas fonctionner
     * sur une autre JVM...
     */
    public static void resetJavaLibraryPath() {
        synchronized(Runtime.getRuntime()) {
            try {
                Field field = ClassLoader.class.getDeclaredField("usr_paths");
                field.setAccessible(true);
                field.set(null, null);

                field = ClassLoader.class.getDeclaredField("sys_paths");
                field.setAccessible(true);
                field.set(null, null);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
