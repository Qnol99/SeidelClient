package net.minecraft.src;

import org.lwjgl.input.Keyboard;
import java.util.LinkedList;

public class GuiChat extends GuiScreen {
	protected String message = "";
	private int updateCounter = 0;
	private static final String field_20082_i = ChatAllowedCharacters.allowedCharacters;
	protected static LinkedList<String> commandHistory = new LinkedList<String>();
	protected int commandIndex = -1;
	protected String lastMessage = "";

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	public void updateScreen() {
		++this.updateCounter;
	}

	protected void keyTyped(char var1, int var2) {
		if(var2 == 1) { // ESC
			this.mc.displayGuiScreen((GuiScreen)null);
		} else if(var2 == 28) { // RETURN
			String var3 = this.message.trim();
			if(var3.length() > 0) {
				String var4 = this.message.trim();
				if(!this.mc.lineIsCommand(var4)) {
					this.mc.thePlayer.sendChatMessage(var4);
				}
				commandHistory.add(var4);
			}

			this.mc.displayGuiScreen((GuiScreen)null);
		} else if(var2 == 200 && commandHistory.size() != 0) { // UP
			if(this.commandIndex == -1) {
				this.commandIndex = commandHistory.size();
				this.lastMessage = this.message;
			}

			if(this.commandIndex == 0)
				return;

			this.commandIndex--;
			this.message = commandHistory.get(commandIndex);

		} else if(var2 == 208) { // DOWN
			if(this.commandIndex == -1)
				return;
				
			if(this.commandIndex == commandHistory.size() -1) {
				this.commandIndex = -1;
				this.message = lastMessage;
			} else {
				this.commandIndex++;
				this.message = commandHistory.get(commandIndex);
			}
				

		} else { // any character
			if(var2 == 14 && this.message.length() > 0) {
				this.message = this.message.substring(0, this.message.length() - 1);
			}

			if(field_20082_i.indexOf(var1) >= 0 && this.message.length() < 100) {
				this.message = this.message + var1;
			}

		}
	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
		this.drawString(this.fontRenderer, "> " + this.message + (this.updateCounter / 6 % 2 == 0 ? "_" : ""), 4, this.height - 12, 14737632);
		super.drawScreen(var1, var2, var3);
	}

	protected void mouseClicked(int var1, int var2, int var3) {
		if(var3 == 0) {
			if(this.mc.ingameGUI.field_933_a != null) {
				if(this.message.length() > 0 && !this.message.endsWith(" ")) {
					this.message = this.message + " ";
				}

				this.message = this.message + this.mc.ingameGUI.field_933_a;
				byte var4 = 100;
				if(this.message.length() > var4) {
					this.message = this.message.substring(0, var4);
				}
			} else {
				super.mouseClicked(var1, var2, var3);
			}
		}

	}
}
