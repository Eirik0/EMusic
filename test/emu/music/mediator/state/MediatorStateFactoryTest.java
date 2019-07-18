package emu.music.mediator.state;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import emu.music.Composition;
import emu.music.mediator.ICompositionProperties;
import emu.music.mediator.TestCompositionProperties;

public class MediatorStateFactoryTest {
    @Test
    public void testNewInstance() {
        TestCompositionProperties testCompositionProperties = new TestCompositionProperties();
        MediatorStateFactory.updateReferences(testCompositionProperties);
        TestNoOpPropertiesState state = MediatorStateFactory.newInstance(TestNoOpPropertiesState.class);
        assertEquals(testCompositionProperties, state.properties);
    }

    public static class TestNoOpPropertiesState implements IMediatorState {
        public final ICompositionProperties properties;

        public TestNoOpPropertiesState(ICompositionProperties properties) {
            this.properties = properties;
        }

        @Override
        public void setComposition(Composition composition) {
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
