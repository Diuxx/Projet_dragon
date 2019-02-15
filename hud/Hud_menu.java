package hud;


import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import sys.MenuItem;

import java.util.ArrayList;

/**
 * class Hud_menu
 *
 * @author: Diuxx
 */
public class Hud_menu {

    private int hud_x = 0;
    private int hud_y = 0;
    private int hud_w = 0;
    private int hud_h = 0;
    private static final Color UHD_COLOR = new Color(255, 255, 255);
    private boolean showing;

    private Button exit_button;
    private Button back_button;

    private ArrayList<Button> lesBoutons;
    private int currentButton;

    // --
    // public static boolean test = false;
    public long s = 0;

    public Hud_menu() {
    }

    /**
     *
     * @throws SlickException
     */
    public void init(GameContainer gc) {
        hud_w = gc.getWidth() / 2;
        hud_h = gc.getHeight() / 2;
        hud_x = (gc.getWidth() / 2) - (hud_w / 2);
        hud_y = 20;
        showing = false;

        this.exit_button = new Button("Quitter",
                new Rectangle(hud_x + 10, hud_y + 15, (hud_w / 4), 27), MenuItem.EXITGAME);
        this.exit_button.setSelected(true);

        this.back_button = new Button("Retour au jeu",
                new Rectangle(hud_x + 10, hud_y + 15 + 35, (hud_w / 4), 27), MenuItem.BACK);

        lesBoutons = new ArrayList<>();
        lesBoutons.add(exit_button);
        lesBoutons.add(back_button);

        this.currentButton = 0;
    }

    /**
     *
     * @param// g
     */
    public MenuItem render(GameContainer gc, Graphics g) throws SlickException {
        g.resetTransform();
        g.setColor(UHD_COLOR);
        g.fillRect(hud_x, hud_y, hud_w, hud_h);

        //if(this.test == false)
        //    this.test = true;

        menuUp(gc);
        if(gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
            lesBoutons.get(this.currentButton).setSelected(false);
            this.currentButton = (this.currentButton + 1) % lesBoutons.size();
            System.out.println(this.currentButton);
            lesBoutons.get(this.currentButton).setSelected(true);
        }

        // affichage
        for(Button b : this.lesBoutons)
            b.render(gc, g);

        if(gc.getInput().isKeyDown(Input.KEY_ENTER)) {

            this.s = System.currentTimeMillis();

            System.out.println(lesBoutons.get(this.currentButton).getMenuItem());
            return lesBoutons.get(this.currentButton).getMenuItem();
        }

        return MenuItem.NONE;
    }

    private void menuUp(GameContainer gc) {
        if(gc.getInput().isKeyPressed(Input.KEY_UP)) {
            int nouvellePosition = ((this.currentButton - 1) < 0) ? (this.currentButton - 1) * -1 : (this.currentButton - 1);
            lesBoutons.get(this.currentButton).setSelected(false);
            this.currentButton = (nouvellePosition) % lesBoutons.size();
            System.out.println(this.currentButton);
            lesBoutons.get(this.currentButton).setSelected(true);
        }
    }

    public long getDiff() {
        return System.currentTimeMillis() - this.s;
    }

    public boolean isShowing() {
        return showing;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
    }

    private class Button {
        private String text;
        private Shape rect;

        private boolean selected;
        private boolean mouseOver;

        private MenuItem menuItem;

        public Button(String text, Rectangle rect) {
            this.text = text;
            this.rect = rect;
            this.mouseOver = false;
            this.selected = false;

            this.menuItem = MenuItem.NONE;
        }

        public Button(String text, Rectangle rect, MenuItem item) {
            this.text = text;
            this.rect = rect;
            this.mouseOver = false;
            this.selected = false;

            this.menuItem = item;
        }

        public void render(GameContainer gc, Graphics g) throws SlickException {

            int x = gc.getInput().getMouseX();
            int y = gc.getInput().getMouseY();

            g.setColor(Color.white);
            g.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

            if(rect.intersects(new Rectangle(x, y, 1, 1))) {
                setMouseOver(true);
                g.setColor(Color.black);
                g.drawLine(rect.getX() + rect.getWidth(), rect.getY(),
                        rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight());
                // System.out.println(this.text);

                g.setColor(Color.red);
            } else {
                if(isMouseOver())
                    setMouseOver(false);

                g.setColor(Color.black);
            }
            if(isSelected()) {
                g.setColor(Color.black);
                g.drawLine(rect.getX() + rect.getWidth(), rect.getY(),
                        rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight());
                g.setColor(Color.red);
            }
            g.drawString(this.text, rect.getX(), rect.getY());
        }

        public boolean isMouseOver() {
            return mouseOver;
        }

        public void setMouseOver(boolean mouseOver) {
            this.mouseOver = mouseOver;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public MenuItem getMenuItem() {
            return menuItem;
        }

        public void setMenuItem(MenuItem menuItem) {
            this.menuItem = menuItem;
        }
    }
}
