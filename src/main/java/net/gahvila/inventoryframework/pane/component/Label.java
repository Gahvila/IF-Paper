package net.gahvila.inventoryframework.pane.component;

import net.gahvila.inventoryframework.gui.InventoryComponent;
import net.gahvila.inventoryframework.gui.type.util.Gui;
import net.gahvila.inventoryframework.gui.GuiItem;
import net.gahvila.inventoryframework.exception.XMLLoadException;
import net.gahvila.inventoryframework.font.util.Font;
import net.gahvila.inventoryframework.pane.*;
import net.gahvila.inventoryframework.pane.util.Slot;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Element;

import java.util.function.BiFunction;

/**
 * A label for displaying text.
 *
 * @since 0.5.0
 */
public class Label extends OutlinePane {

    /**
     * The character set used for displaying the characters in this label
     */
    @NotNull
    private final Font font;

    /**
     * The text to be displayed
     */
    @NotNull
    private String text;

    /**
     * The plugin to be sed for creating items
     */
    @NotNull
    private final Plugin plugin;

    /**
     * Creates a new label
     *
     * @param slot the slot
     * @param length the length
     * @param height the height
     * @param priority the priority
     * @param font the character set
     * @param plugin the plugin that will be the owner for this label's items
     * @see #Label(int, int, int, int, Priority, Font)
     * @since 0.10.8
     */
    public Label(@NotNull Slot slot, int length, int height, @NotNull Priority priority, @NotNull Font font,
                 @NotNull Plugin plugin) {
        super(slot, length, height);

        this.font = font;
        this.text = "";

        this.plugin = plugin;

        setPriority(priority);
    }

    /**
     * Creates a new label
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param length the length
     * @param height the height
     * @param priority the priority
     * @param font the character set
     * @param plugin the plugin that will be the owner for this label's items
     * @see #Label(int, int, int, int, Priority, Font)
     * @since 0.10.8
     */
    public Label(int x, int y, int length, int height, @NotNull Priority priority, @NotNull Font font,
                 @NotNull Plugin plugin) {
        this(Slot.fromXY(x, y), length, height, priority, font, plugin);
    }

    /**
     * Creates a new label
     *
     * @param slot the slot
     * @param length the length
     * @param height the height
     * @param font the character set
     * @param plugin the plugin that will be the owner for this label's items
     * @see #Label(int, int, int, int, Font)
     * @since 0.10.8
     */
    public Label(@NotNull Slot slot, int length, int height, @NotNull Font font, @NotNull Plugin plugin) {
        this(slot, length, height, Priority.NORMAL, font, plugin);
    }

    /**
     * Creates a new label
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param length the length
     * @param height the height
     * @param font the character set
     * @param plugin the plugin that will be the owner for this label's items
     * @see #Label(int, int, int, int, Font)
     * @since 0.10.8
     */
    public Label(int x, int y, int length, int height, @NotNull Font font, @NotNull Plugin plugin) {
        this(x, y, length, height, Priority.NORMAL, font, plugin);
    }

    /**
     * Creates a new label
     *
     * @param length the length
     * @param height the height
     * @param font the character set
     * @param plugin the plugin that will be the owner for this label's items
     * @see #Label(int, int, Font)
     * @since 0.10.8
     */
    public Label(int length, int height, @NotNull Font font, @NotNull Plugin plugin) {
        this(0, 0, length, height, font, plugin);
    }

    /**
     * Creates a new label
     *
     * @param slot the slot
     * @param length the length
     * @param height the height
     * @param priority the priority
     * @param font the character set
     * @since 0.10.8
     */
    public Label(@NotNull Slot slot, int length, int height, @NotNull Priority priority, @NotNull Font font) {
        this(slot, length, height, priority, font, JavaPlugin.getProvidingPlugin(Label.class));
    }

    /**
     * Creates a new label
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param length the length
     * @param height the height
     * @param priority the priority
     * @param font the character set
     * @since 0.5.0
     */
    public Label(int x, int y, int length, int height, @NotNull Priority priority, @NotNull Font font) {
        this(x, y, length, height, priority, font, JavaPlugin.getProvidingPlugin(Label.class));
    }

    /**
     * Creates a new label
     *
     * @param slot the slot
     * @param length the length
     * @param height the height
     * @param font the character set
     * @since 0.10.8
     */
    public Label(@NotNull Slot slot, int length, int height, @NotNull Font font) {
        this(slot, length, height, Priority.NORMAL, font);
    }

    /**
     * Creates a new label
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param length the length
     * @param height the height
     * @param font the character set
     * @since 0.5.0
     */
    public Label(int x, int y, int length, int height, @NotNull Font font) {
        this(x, y, length, height, Priority.NORMAL, font);
    }

    /**
     * Creates a new label
     *
     * @param length the length
     * @param height the height
     * @param font the character set
     * @since 0.5.0
     */
    public Label(int length, int height, @NotNull Font font) {
        this(0, 0, length, height, font);
    }

    /**
     * Sets the text to be displayed in this label. If this label already had text, this text will be overwritten. The
     * specified processor will be called for each character that is part of the specified text. The provided character
     * will be the original character that was attempted to be shown - it is not subject to any transformations that may
     * be applied for finding a valid item corresponding to this character, such as capitalization changes.
     *
     * @param text the new text
     * @param processor processes each character before using them
     * @since 0.10.4
     */
    public void setText(@NotNull String text,
                        @NotNull BiFunction<? super @NotNull Character, ? super @NotNull ItemStack,
                                ? extends @NotNull GuiItem> processor) {
        this.text = text;

        clear();

        for (char character : text.toCharArray()) {
            ItemStack item = font.toItem(character);

            if (item == null) {
                item = font.toItem(Character.toUpperCase(character));
            }

            if (item == null) {
                item = font.toItem(Character.toLowerCase(character));
            }

            if (item == null) {
                item = font.getDefaultItem();
            }

            addItem(processor.apply(character, item.clone()));
        }
    }

    /**
     * Sets the text to be displayed in this label. If this label already had text, this text will be overwritten.
     *
     * @param text the new text
     * @see #setText(String, BiFunction)
     * @since 0.5.0
     */
    public void setText(@NotNull String text) {
        setText(text, (character, item) -> new GuiItem(item, this.plugin));
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public Label copy() {
        Label label = new Label(getSlot(), length, height, getPriority(), font, this.plugin);

        for (GuiItem item : getItems()) {
            label.addItem(item.copy());
        }

        label.setVisible(isVisible());
        label.onClick = onClick;

        label.setOrientation(getOrientation());
        label.setRotation(getRotation());
        label.setGap(getGap());
        label.setRepeat(doesRepeat());
        label.flipHorizontally(isFlippedHorizontally());
        label.flipVertically(isFlippedVertically());
        label.applyMask(getMask());
        label.uuid = uuid;

        label.text = text;

        return label;
    }

    @Override
    public boolean click(@NotNull Gui gui, @NotNull InventoryComponent inventoryComponent,
                         @NotNull InventoryClickEvent event, int slot, int paneOffsetX, int paneOffsetY, int maxLength,
                         int maxHeight) {
        event.setCancelled(true);

        return super.click(gui, inventoryComponent, event, slot, paneOffsetX, paneOffsetY, maxLength, maxHeight);
    }

    /**
     * Gets the text currently displayed in this label
     *
     * @return the text in this label
     * @since 0.5.0
     */
    @Contract(pure = true)
    @NotNull
    public String getText() {
        return text;
    }

    /**
     * Gets the character set currently used for the text in this label
     *
     * @return the character set
     * @since 0.5.0
     */
    @Contract(pure = true)
    @NotNull
    public Font getFont() {
        return font;
    }

    /**
     * Loads a label from a given element
     *
     * @param instance the instance class
     * @param element the element
     * @param plugin the plugin that will be the owner of the underlying items
     * @return the percentage bar
     * @since 0.10.8
     */
    @NotNull
    @Contract(pure = true)
    public static Label load(@NotNull Object instance, @NotNull Element element, @NotNull Plugin plugin) {
        int length;
        int height;

        try {
            length = Integer.parseInt(element.getAttribute("length"));
            height = Integer.parseInt(element.getAttribute("height"));
        } catch (NumberFormatException exception) {
            throw new XMLLoadException(exception);
        }

        Font font = null;

        if (element.hasAttribute("font")) {
            font = Font.fromName(element.getAttribute("font"));
        }

        if (font == null) {
            throw new XMLLoadException("Incorrect font specified for label");
        }

        Label label = new Label(length, height, font, plugin);

        Pane.load(label, instance, element);
        Orientable.load(label, element);
        Flippable.load(label, element);
        Rotatable.load(label, element);

        if (element.hasAttribute("populate")) {
            return label;
        }

        if (element.hasAttribute("text")) {
            label.setText(element.getAttribute("text"));
        }

        return label;
    }

    /**
     * Loads a label from a given element
     *
     * @param instance the instance class
     * @param element the element
     * @return the percentage bar
     * @deprecated this method is no longer used internally and has been superseded by
     *             {@link #load(Object, Element, Plugin)}
     */
    @NotNull
    @Contract(pure = true)
    @Deprecated
    public static Label load(@NotNull Object instance, @NotNull Element element) {
        return load(instance, element, JavaPlugin.getProvidingPlugin(Label.class));
    }
}
