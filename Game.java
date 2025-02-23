public class Game {
    public static void main(String[] args) {
        GameArena arena = new GameArena(1600, 900);

        Player p = new Player(arena, 0, 0, 5, 100);
        EnemyManager enemMan = new EnemyManager(arena, p);
        int frames = 0;

        while (true) {
            if (frames == 100) {
                enemMan.createEnemy(1920, 1080, 50, 80, 1);
                frames = 0;
            }
            arena.pause();

            p.update();
            enemMan.update();
            frames++;
        }
    }
}
