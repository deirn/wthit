package mcp.mobius.waila.gui.widget.value;

import java.util.function.Consumer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class BooleanValue extends ConfigValue<Boolean> {

    private final Button button;

    public BooleanValue(String optionName, boolean value, Consumer<Boolean> save) {
        super(optionName, save);

        this.button = new Button(0, 0, 100, 20, TextComponent.EMPTY, w -> this.value = !this.value);
        this.value = value;

        setMessage();
    }

    @Override
    protected void drawValue(PoseStack matrices, int width, int height, int x, int y, int mouseX, int mouseY, boolean selected, float partialTicks) {
        setMessage();
        button.x = x + width - button.getWidth();
        button.y = y + (height - button.getHeight()) / 2;
        button.render(matrices, mouseX, mouseY, partialTicks);
    }

    private void setMessage() {
        button.setMessage(new TranslatableComponent("config.waila." + value)
            .withStyle(value ? ChatFormatting.GREEN : ChatFormatting.RED));
    }

    @Override
    public GuiEventListener getListener() {
        return button;
    }

}
