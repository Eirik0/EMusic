package emu.music.mediator.state;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import emu.music.Song;
import emu.music.mediator.ISongProperties;
import emu.music.mediator.TestSongProperties;

public class MediatorStateFactoryTest {
    @Test
    public void testNewInstance() {
        TestSongProperties testSongProperties = new TestSongProperties();
        MediatorStateFactory.updateReferences(testSongProperties);
        TestNoOpPropertiesState state = MediatorStateFactory.newInstance(TestNoOpPropertiesState.class);
        assertEquals(testSongProperties, state.properties);
    }

    public static class TestNoOpPropertiesState implements IMediatorState {
        public final ISongProperties properties;

        public TestNoOpPropertiesState(ISongProperties properties) {
            this.properties = properties;
        }

        @Override
        public void setSong(Song song) {
        }

        @Override
        public void mousePressed(int button) {
        }

        @Override
        public void mouseReleased() {
        }

        @Override
        public void draw() {
        }

        @Override
        public void finish() {
        }
    }
}
