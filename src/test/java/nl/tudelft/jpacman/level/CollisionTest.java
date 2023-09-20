package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.points.PointCalculatorLoader;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CollisionTest {

    //Crash test dummy time.

    Launcher launcher;
    Player pacman;

    @BeforeEach
    void setup(){
        launcher = new Launcher().withMapFile("/collisionMap.txt");
        launcher.launch();
        launcher.getGame().start();
        pacman = launcher.getGame().getPlayers().get(0);
        DefaultPlayerInteractionMap x = new DefaultPlayerInteractionMap(new PointCalculatorLoader().load());
        launcher.getGame().getLevel().addObserver();
    }

    @Test
    void collideCoinThenWallThenGhost(){
        Level l = new Level()
        Level l = launcher.getGame().getLevel();
        l.move(pacman, Direction.WEST); // Should collide with coin
        // Check for things that do coin things
        Assertions.assertThat(pacman.getScore()).isEqualTo(10); // coins add 10 to score.

        Square wall = pacman.squaresAheadOf(1);
        l.move(pacman, Direction.WEST);
        Assertions.assertThat(pacman.squaresAheadOf(1)).isEqualTo(wall); // Square should be the same, it is unchanged.

        //continue marching right, to the ghost,
        ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
        timer.scheduleAtFixedRate(() -> {
            if (pacman.isAlive()){
                l.move(pacman, Direction.EAST);
            } else {
                timer.shutdown();
            }
        }, 0, 100, TimeUnit.MILLISECONDS);

        // This will fail if the task is interrupted.
        Assertions.assertThatCode(() -> {
            if (timer.awaitTermination(1, TimeUnit.SECONDS))
                timer.shutdown();
            }).doesNotThrowAnyException();

        // End of game should be caused by interaction with ghost.
        Assertions.assertThat(pacman.getKiller()).isInstanceOf(Ghost.class);
    }

    @AfterEach
    void tearDown(){
        if(launcher.getGame().isInProgress())
            launcher.getGame().stop();
        launcher.dispose();
    }



}
