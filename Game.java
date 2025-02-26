public class Game {
    public static void main(String[] args) {
        GameArena arena = new GameArena(1600, 900);

        Player p = new Player(arena, 0, 0, 5, 100);
        WaveManager waveMan = new WaveManager(arena, p, 1600, 900);
        int frames = 0;

        waveMan.nextWave();
        while (true) {


            arena.pause();

            p.update();
            waveMan.update();
        }
    }
}
