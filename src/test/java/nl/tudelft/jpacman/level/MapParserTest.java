package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.points.PointCalculatorLoader;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class MapParserTest {

    static PacManSprites pms;
    MapParser mp;
    static GhostFactory gf;
    static LevelFactory lf;


    @BeforeAll
    static void init(){
        pms = new PacManSprites();
        gf = new GhostFactory(pms);
        lf = new LevelFactory(pms, gf, new PointCalculatorLoader().load());
    }

    @BeforeEach
    void setup(){
        mp = new MapParser(lf, new BoardFactory(pms));
    }

    @AfterEach
    void tearDown(){
        mp = null;
    }

    //Load simple map, should load and throw no error
    @Test
    void testLoadSimpleMap() {
        Assertions.assertThatCode(() -> mp.parseMap("/simplemap.txt"))
            .doesNotThrowAnyException();
    }

    //Empty maps are not allowd, should throw error
    @Test
    void testLoadEmptyMap(){
        Assertions.assertThatThrownBy(() -> mp.parseMap("/emptyMap.txt"))
            .isInstanceOf(PacmanConfigurationException.class);
    }

    //A different non-std map, should not error
    @Test
    void testLoadCertainDeathMap() {
        Assertions.assertThatCode(() -> mp.parseMap("/certainDeathMap.txt"))
            .doesNotThrowAnyException();
    }

    //Map doesnt exist, should error.
    @Test
    void testNonExistentMap(){
        Assertions.assertThatThrownBy(() -> mp.parseMap("/IdontExistMap.txt"))
            .isInstanceOf(PacmanConfigurationException.class);
    }

    //Map doesnt have a player should not error.
    @Test
    void testMapMissingPlayer(){
        Assertions.assertThatCode(() -> mp.parseMap("/missingPlayer.txt"))
            .doesNotThrowAnyException();
    }
}

