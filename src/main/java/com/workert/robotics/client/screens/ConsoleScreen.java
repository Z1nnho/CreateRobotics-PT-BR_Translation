package com.workert.robotics.client.screens;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.widget.IconButton;
import com.simibubi.create.foundation.utility.Components;
import com.workert.robotics.roboscript.ingame.IConsoleOutputProvider;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.network.chat.Component;

public class ConsoleScreen extends AbstractSimiScreen {
	private MultiLineEditBox consoleOutput;
	private IconButton closeButton;
	private IconButton runningStateButton;

	private IConsoleOutputProvider consoleOutputProvider;

	public ConsoleScreen(IConsoleOutputProvider consoleOutputProvider) {
		super(Component.literal("Edit Signal Name"));
		this.consoleOutputProvider = consoleOutputProvider;
	}


	@Override
	public void tick() {
		super.tick();

		if (this.consoleOutputProvider.getRunningState().equals(IConsoleOutputProvider.RunningState.RUNNING)) {
			this.runningStateButton.setIcon(AllIcons.I_PLAY);
			this.runningStateButton.setToolTip(Component.literal("Currently running"));
		} else if (this.consoleOutputProvider.getRunningState().equals(IConsoleOutputProvider.RunningState.STOPPED)) {
			this.runningStateButton.setIcon(AllIcons.I_STOP);
			this.runningStateButton.setToolTip(Component.literal("Currently stopped"));
		} else if (this.consoleOutputProvider.getRunningState()
				.equals(IConsoleOutputProvider.RunningState.ENERGY_REQUIREMENT_NOT_MET)) {
			this.runningStateButton.setIcon(AllIcons.I_PAUSE);
			this.runningStateButton.setToolTip(
					Component.literal("The Energy Requirement is currently not met.\nThe program has been stopped."));
		}
		this.consoleOutput.setValue(this.consoleOutputProvider.getConsoleOutput());
	}

	@Override
	protected void init() {
		int width = 380;
		int height = 210;
		this.setWindowSize(width, height);
		super.init();
		int x = this.guiLeft;
		int y = this.guiTop;

		this.runningStateButton = new IconButton(x - 20, y, AllIcons.I_STOP);
		this.runningStateButton.withCallback(() -> {
		});
		this.addRenderableWidget(this.runningStateButton);

		this.closeButton = new IconButton(x - 20, y + 20, AllIcons.I_MTD_CLOSE);
		this.closeButton.withCallback(this::onClose);
		this.closeButton.setToolTip(Component.literal("Close"));
		this.addRenderableWidget(this.closeButton);

		this.consoleOutput = new MultiLineEditBox(this.font, x, y, width, 210, Components.immutableEmpty(),
				Components.immutableEmpty()) {
			@Override
			public boolean isFocused() {
				return false;
			}
		};
		this.addRenderableWidget(this.consoleOutput);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		return false;
	}

	@Override
	protected void renderWindow(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
	}
}
