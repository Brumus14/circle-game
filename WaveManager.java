import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WaveManager {
    private EnemyManager enemMan;
    private final int BASIC = 0;
    private final int ASSASSIN = 1;
    private final int TANK = 2;
    private Spawn basics = new Spawn(Arrays.asList(
        Arrays.asList(BASIC, BASIC, BASIC, TANK), Arrays.asList(BASIC, ASSASSIN, ASSASSIN)),
            Arrays.asList(2, 2),
            Arrays.asList(6000, 3000));

    private float completion;
    private int waveSize;
    private Rectangle waveCompletion;
    float healthLength;

    private long[] testWave = {BASIC, 2, 1000};
    private List<Spawn> wave1 = new ArrayList<>(Arrays.asList(basics));
    private final List<List<Spawn>> waves = new ArrayList<>();
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
        List<Spawn> waveToSpawn = waves.get(wave);
        for (Spawn curSpawn:waveToSpawn) {
            for(int i = 0; i < curSpawn.types.size(); i++){
                waveSize += curSpawn.types.get(i).size() * curSpawn.repeats.get(i);
            }
        }
        enemMan.enemiesKilled = 0;
        new Thread(() -> {
            spawnWave(waveToSpawn);
        }).start();
        if(wave <= waves.size() - 1){
            wave++;
        }
        else{
            // edit the wave to continously get faster
        }
    }

    private void spawnWave(List<Spawn> waveSpawns){
        for(Spawn wave : waveSpawns){
            for(int i = 0; i < wave.repeats.size(); i++){
                for(int times = 0; times < wave.repeats.get(i); times++) {
                    for(int type : wave.types.get(i)) {
                        switch (type){
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
                    }
                    try {
                        Thread.sleep(wave.timings.get(i));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }
        /*
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
        }*/
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
