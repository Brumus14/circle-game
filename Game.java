import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Game {
    public static void main(String[] args) {
        GameArena arena = new GameArena(1920, 1080);

        JFrame frame =
            (JFrame)SwingUtilities.getWindowAncestor(arena.getPanel());

        Player p = new Player(arena, 0, 0, 5, 100);

        arena.setSize(200, 200);

        while (true) {
            arena.pause();

            p.update();

            // java.awt.Rectangle bounds = frame.getBounds();
            // arena.setSize(bounds.width, bounds.height);
            arena.setSize(1920, 1080);
        }
    }
}
