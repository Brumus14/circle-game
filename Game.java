public class Game {
    public static void main(String[] args) {
        GameArena arena = new GameArena(1920, 1080);

        Player p = new Player(arena, 0, 0, 5, 100);

        while (true) {
            arena.pause();

            p.update();
        }
    }
}
