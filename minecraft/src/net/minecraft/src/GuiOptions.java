package net.minecraft.src;

public class GuiOptions extends GuiScreen {
	private GuiScreen parentScreen;
	protected String screenTitle = "Options";
	public static boolean isFullBright = false;
	public static boolean isFog = true;
	private GameSettings options;
	private static EnumOptions[] field_22135_k = new EnumOptions[]{EnumOptions.MUSIC, EnumOptions.SOUND, EnumOptions.INVERT_MOUSE, EnumOptions.SENSITIVITY, EnumOptions.DIFFICULTY, EnumOptions.FOV};

	public GuiOptions(GuiScreen var1, GameSettings var2) {
		this.parentScreen = var1;
		this.options = var2;
	}

	public void initGui() {
		StringTranslate var1 = StringTranslate.getInstance();
		this.screenTitle = var1.translateKey("options.title");
		int var2 = 0;
		EnumOptions[] eOptions = field_22135_k;
		int len = eOptions.length;

		for(int i = 0; i < len; ++i) {
			EnumOptions option = eOptions[i];
			if(!option.getEnumFloat()) {
				this.controlList.add(new GuiSmallButton(option.returnEnumOrdinal(), this.width / 2 - 155 + var2 % 2 * 160, this.height / 6 + 24 * (var2 >> 1), option, this.options.getKeyBinding(option)));
			} else {
				this.controlList.add(new GuiSlider(option.returnEnumOrdinal(), this.width / 2 - 155 + var2 % 2 * 160, this.height / 6 + 24 * (var2 >> 1), option, this.options.getKeyBinding(option), this.options.getOptionFloatValue(option)));
			}

			++var2;
		}

		this.controlList.add(new GuiButton(101, this.width / 2 - 100, this.height / 6 + 96 + 12, var1.translateKey("options.video")));
		this.controlList.add(new GuiButton(100, this.width / 2 - 100, this.height / 6 + 120 + 12, var1.translateKey("options.controls")));
		this.controlList.add(new GuiButton(201, this.width / 2 - 100, this.height / 6 + 144 + 12, "Fulbright: " + (this.isFullBright ? "ON" : "OFF")));
		this.controlList.add(new GuiButton(202, this.width / 2 - 100, this.height / 6 + 168 + 12, "Fog: " + (this.isFog ? "ON" : "OFF")));
		this.controlList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 212, var1.translateKey("gui.done")));
	}

	


	protected void actionPerformed(GuiButton var1) {
		if(var1.enabled) {
			if(var1.id < 100 && var1 instanceof GuiSmallButton) {
				this.options.setOptionValue(((GuiSmallButton)var1).returnEnumOptions(), 1);
				var1.displayString = this.options.getKeyBinding(EnumOptions.getEnumOptions(var1.id));
			}

			if(var1.id == 101) {
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(new GuiVideoSettings(this, this.options));
			}

			if(var1.id == 100) {
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(new GuiControls(this, this.options));
			}

			if(var1.id == 200) {
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(this.parentScreen);
			}

			if (var1.id == 201) {
		        this.isFullBright = !this.isFullBright;
		        var1.displayString = "Fullbright: " + (this.isFullBright ? "ON" : "OFF");
			}

			if (var1.id == 202) {
		        this.isFog = !this.isFog;
		        var1.displayString = "Fog: " + (this.isFog ? "ON" : "OFF");
			}
			

		}
	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
		super.drawScreen(var1, var2, var3);
	}
}
