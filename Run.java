public class Run
{
    public static void main(String[] args)
    {
        GameArena arena = new GameArena(1600, 900);
        Player first = new Player(new Ball(250, 150, 40, "GREEN"), 10);
        EnemyManager enemMan = new EnemyManager(arena, first);
        arena.addBall(first.getSprite());
        int frames = 0;
        while(true)
        {
            if(frames == 100){
                int x = (int)(Math.random() * 1600);
                int y = (int)(Math.random() * 900);
                Player newPlayer = new Player(new Ball(x, y, 40, "GREEN"), 10);
                arena.addBall(newPlayer.getSprite());
                enemMan.addPlayer(newPlayer);
                enemMan.createEnemy(1600,900, 50,80);
                frames = 0;
            }
            enemMan.moveEnemies();
            enemMan.checkCollisions();
            frames++;
            arena.pause();
        }
    }
}