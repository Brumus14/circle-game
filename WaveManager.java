import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.List;

public class WaveManager {
    private EnemyManager enemMan;
    private final int BASIC = 0;
    private final int ASSASSIN = 1;
    private final int TANK = 2;

    private float completion;
    private int waveSize;
    private Rectangle waveCompletion;
    float healthLength;

    private long[] testWave = {BASIC, 2, 1000};
    private long[] wave1 = {BASIC, 5, 1000, ASSASSIN, 2, 500, TANK, 5, 2000, BASIC, 10, 800, ASSASSIN, 5, 100};
    private final List<long[]> waves = new ArrayList<>();
    private int wave = 0;

    public WaveManager(GameArena arena, Player p, int xSize, int ySize){
        this.enemMan = new EnemyManager(arena, p, xSize, ySize);

        //waves.add(testWave);
        waves.add(wave1);

        healthLength = (float)xSize / 3f;
        waveCompletion = new Rectangle((xSize - healthLength) / 2f, 10f, healthLength, (float)ySize / 30f, "RED");
        arena.addRectangle(waveCompletion);
    }

    public void nextWave(){
        waveSize = 0;
        long[] waveToSpawn = waves.get(wave);
        for(int i = 0; i < waveToSpawn.length - 1; i += 3) {
            waveSize += (int)waveToSpawn[i + 1];
        }
        enemMan.enemiesKilled = 0;
        new Thread(() -> {
            spawnWave(waveToSpawn);
        }).start();
        //wave++;
    }

    private void spawnWave(long[] wave){
        for(int i = 0; i < wave.length; i += 3){
            for(int times = 0; times < wave[i + 1]; times++){
                switch ((int)wave[i]){
                    case BASIC:
                        enemMan.createBasic();
                        break;
                    case ASSASSIN:
                        enemMan.createAssassin();
                        break;
                    case TANK:
                        enemMan.createTank();
                        break;
                }
                try {
                    Thread.sleep(wave[i + 2]);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void update(){
        enemMan.update();
        int numEnemies = enemMan.enemiesKilled;
        if(numEnemies == waveSize){
            nextWave();
        }
        completion = 1 - ((float)numEnemies / (float)waveSize);
        waveCompletion.setWidth(healthLength * completion);
    }
}
