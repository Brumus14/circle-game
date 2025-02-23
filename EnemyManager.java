import java.util.*;

public class EnemyManager {
    private List<Player> players = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private GameArena arena;

    public EnemyManager(GameArena arena, Player playerOne) {
        this.arena = arena;
        // the enemy manager requires at least one player to function
        players.add(playerOne);
    }

    private Player getNearestPlayer(Enemy enemy) {
        // assume nearest player is first player
        Player nearestPlayer = players.getFirst();

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
    public void moveEnemies() {
        Enemy curEnemy;
        Player nearestPlayer;
        for (Enemy enemy : enemies) {
            curEnemy = enemy;
            nearestPlayer = getNearestPlayer(curEnemy);
            curEnemy.move(nearestPlayer);
        }
    }

    public void checkCollisions() {
        boolean willMove = true;
        for (Enemy enemy : enemies) {
            willMove = true;
            for (Player player : players) {
                if (enemy.checkCollision(player)) {
                    willMove = false;
                }
            }
            enemy.setMoving(willMove);
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
    public void createEnemy(int xMax, int yMax, int distance, double diameter) {
        int[] enemyLoc =
            createEnemyLocation(xMax, yMax, distance, (int)diameter / 2);
        Enemy newEnemy = new Enemy(enemyLoc[0], enemyLoc[1], diameter);
        enemies.add(newEnemy);
        arena.addBall(newEnemy.getSprite());
    }

    public void addPlayer(Player newPlayer) {
        players.add(newPlayer);
    }
}
