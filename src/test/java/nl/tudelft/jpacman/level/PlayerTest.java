package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class PlayerTest {

    private Launcher launcher;

    @BeforeEach
    void setup(){
        launcher = new Launcher();
        launcher.launch();
    }

    @AfterEach
    void teardown(){
        launcher.getGame().stop();
        launcher.dispose();
    }

    @Test
    void testIsAlive(){
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0); // Player is always first added to the list
        //Player should be alive at game start
        Assertions.assertThat(player.isAlive()).isTrue();
    }

    @Test
    void testPlayerMove(){


    }


}
