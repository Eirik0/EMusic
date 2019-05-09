package gui.properties;

import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;

public class ComponentCreator {
    public static JButton createButton(String text, Consumer<ActionEvent> consumer) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setFocusable(false);
        button.addActionListener(e -> consumer.accept(e));
        return button;
    }

    public static JCheckBox createCheckBox(String text, Consumer<ActionEvent> consumer) {
        JCheckBox checkBox = new JCheckBox(text, true);
        checkBox.setBackground(Color.WHITE);
        checkBox.setFocusable(false);
        checkBox.addActionListener(e -> consumer.accept(e));
        return checkBox;
    }

    public static <T> JComboBox<T> createComboBox(T[] selectable, T defaultValue, Consumer<T> selectionConsumer) {
        JComboBox<T> comboBox = new JComboBox<>(selectable);
        comboBox.setSelectedItem(defaultValue);
        comboBox.setBackground(Color.WHITE);
        comboBox.setFocusable(false);
        comboBox.addActionListener(e -> {
            selectionConsumer.accept(comboBox.getItemAt(comboBox.getSelectedIndex()));
        });
        return comboBox;
    }

    public static JPanel createPanel(LayoutManager layout) {
        JPanel panel = layout == null ? new JPanel() : new JPanel(layout);
        panel.setBackground(Color.WHITE);
        return panel;
    }

    public static JSlider createSlider(int orientation, int minimum, int maximum, int spacing, int defaultValue, Consumer<Integer> consumer) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL);
        slider.setBackground(Color.WHITE);
        slider.setMinimum(minimum);
        slider.setMaximum(maximum);
        slider.setMinorTickSpacing(spacing);
        slider.setPaintTicks(true);
        slider.setValue(defaultValue);
        slider.setFocusable(false);
        slider.addChangeListener(e -> consumer.accept(slider.getValue()));
        return slider;
    }

    public static JSplitPane createSplitPane(int orientation, JComponent leftComponent, JComponent rightComponent) {
        JSplitPane splitPane = new JSplitPane(orientation, leftComponent, rightComponent);
        splitPane.setBackground(Color.WHITE);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setDividerSize(0);
        splitPane.setResizeWeight(0.5);
        return splitPane;
    }
}
