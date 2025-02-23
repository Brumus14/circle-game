import java.util.*;

public class EnemyManager {
    private List<Player> players = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private List<Bullet> bullets = new ArrayList<>();
    private List<Enemy> enemRemove = new ArrayList<>();
    private List<Bullet> bulletRemove = new ArrayList<>();
    private GameArena arena;

    public EnemyManager(GameArena arena, Player playerOne) {
        this.arena = arena;
        // the enemy manager requires at least one player to function
        players.add(playerOne);
    }

    private Player getNearestPlayer(Enemy enemy) {
        // assume nearest player is first player
        Player nearestPlayer = players.get(0);

        // get the distance to this player
        double shortDist = getDistance(nearestPlayer, enemy);

        double dist;
        Player curPlayer;

        // iterate over the remaining players
        for (int i = 1; i < players.size(); i++) {
            curPlayer = players.get(i);
            dist = getDistance(curPlayer, enemy);

            // if this player is closer to the enemy set it as the nearest
            // player
            if (dist < shortDist) {
                nearestPlayer = curPlayer;
                shortDist = dist;
            }
        }
        return nearestPlayer;
    }

    private double getDistance(Player player, Enemy enemy) {
        double playerX = player.getShape().getXPosition();
        double playerY = player.getShape().getYPosition();

        double enemyX = enemy.getEnemyX();
        double enemyY = enemy.getEnemyY();

        // a^2 + b^2 = c^2
        return (Math.sqrt(Math.pow(playerX - enemyX, 2) +
                          Math.pow(playerY - enemyY, 2)));
    }

    // call the move function of each enemy passing in the nearest player to
    // focus
    private void moveEnemies() {
        Enemy curEnemy;
        Player nearestPlayer;
        for (Enemy enemy : enemies) {
            curEnemy = enemy;
            nearestPlayer = getNearestPlayer(curEnemy);
            curEnemy.move(nearestPlayer);
        }
    }

    private void updateBullets(){
        bullets = new ArrayList<>();
        for (Player player : players){
            bullets.addAll(player.getGun().getBullets());
        }
    }

    private void checkCollisions() {
        boolean willMove = true;
        for (Enemy enemy : enemies) {
            willMove = true;
            for (Player player : players) {
                if (enemy.checkCollision(player)) {
                    willMove = false;
                }
            }
            enemy.setMoving(willMove);

            for (Bullet bullet : bullets){
                if (enemy.checkCollision(bullet)) {
                    enemy.health--;
                    if(enemy.health < 0){
                        enemRemove.add(enemy);
                        bulletRemove.add(bullet);
                    }
                }
            }
        }
    }

    private int[] createEnemyLocation(int xMax, int yMax, int distance,
                                      int radius) {
        int xOut = (int)(Math.random() * (xMax + distance * 2)) - distance;
        int yOut;
        if (xOut < 0 || xOut > xMax) {
            yOut = (int)(Math.random() * (yMax + distance * 2)) - distance;
            if (yOut < 0) {
                yOut -= radius;
            }
            if (yOut > yMax) {
                yOut += radius;
            }
        } else {
            yOut = (int)(Math.random() * (distance * 2)) - distance;
            if (yOut > 0) {
                yOut += yMax + radius;
            } else {
                yOut -= radius;
            }
        }
        if (xOut < 0) {
            xOut -= radius;
        }
        if (xOut > xMax) {
            xOut += radius;
        }

        return new int[] {xOut, yOut};
    }

    // create a new enemy within the area provided
    public void createEnemy(int xMax, int yMax, int distance, double diameter, int health) {
        int[] enemyLoc =
            createEnemyLocation(xMax, yMax, distance, (int)diameter / 2);
        Enemy newEnemy = new Enemy(enemyLoc[0], enemyLoc[1], diameter, health);
        enemies.add(newEnemy);
        arena.addBall(newEnemy.getShape());
    }

    public void addPlayer(Player newPlayer) {
        players.add(newPlayer);
    }

    private void removeBalls(){
        for(Enemy enem : enemRemove){
            arena.removeBall(enem.getShape());
            enemies.remove(enem);
        }
        for(Bullet bullet : bulletRemove){
            bullet.destroy();
        }
    }

    public void update(){
        updateBullets();
        moveEnemies();
        checkCollisions();
        removeBalls();
    }
}
