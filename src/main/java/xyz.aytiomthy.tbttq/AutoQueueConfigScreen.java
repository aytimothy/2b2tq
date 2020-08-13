package xyz.aytiomthy.tbttq;

import com.google.common.base.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.IOException;
import java.time.*;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class AutoQueueConfigScreen extends GuiScreen {
    private GuiTextField server;
    private GuiTextField hour;
    private GuiTextField minute;
    private GuiTextField second;
    private GuiTextField day;
    private GuiTextField month;
    private GuiTextField year;
    private GuiTextField timeout;
    private GuiButton enable;
    private GuiButton disable;
    private GuiButton confirm;
    private GuiButton cancel;

    private GuiScreen previousScreen;

    private int baseY;

    public Predicate<String> NUMERIC_VALIDATOR = new Predicate<String>() {
        @Override
        public boolean apply(@Nullable String input) {
            return isNumeric(input);
        }
    };

    public AutoQueueConfigScreen (GuiScreen previousScreen) {
        mc = Minecraft.getMinecraft();
        fontRenderer = mc.fontRenderer;
        this.previousScreen = previousScreen;
    }

    @Override
    protected void actionPerformed (GuiButton b) {
        switch (b.id) {
            case 0:
                enableButton_OnClick();
                break;
            case 1:
                disableButton_OnClick();
                break;
            case 2:
                confirmButton_OnClick();
                break;
            case 3:
                cancelButton_OnClick();
                break;
        }
    }

    private void enableButton_OnClick() {
        AutoQueueConfig.enabled = true;
        enable.enabled = false;
        disable.enabled = true;
    }

    private void disableButton_OnClick() {
        AutoQueueConfig.enabled = false;
        enable.enabled = true;
        disable.enabled = false;
    }

    private void confirmButton_OnClick() {
        AutoQueueConfig.server = server.getText();
        LocalDateTime timeBackup = AutoQueueConfig.timestamp;
        try {
            int _hour = Integer.parseInt(hour.getText());
            int _minute = Integer.parseInt(minute.getText());
            int _second = Integer.parseInt(second.getText());
            int _day = Integer.parseInt(day.getText());
            int _month = Integer.parseInt(month.getText());
            int _year = Integer.parseInt(year.getText());
            int _timeout = Integer.parseInt(timeout.getText());
            AutoQueueConfig.afkTimeout = _timeout;
            AutoQueueConfig.timestamp = LocalDateTime.of(_year, _month, _day, _hour, _minute, _second);
        }
        catch (Exception ex) {
            AutoQueueConfig.timestamp = timeBackup;
            // todo: Display an error.
            return;
        }
        AutoQueueConfig.afkTimeout = Integer.parseInt(timeout.getText());
        AutoQueueConfig.SaveToDisk();
        mc.displayGuiScreen(previousScreen);
    }

    private void cancelButton_OnClick() {
        AutoQueueConfig.LoadFromDisk();
        mc.displayGuiScreen(previousScreen);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        drawCenteredString(fontRenderer, "Server:", width / 2, baseY, Color.WHITE.getRGB());
        drawCenteredString(fontRenderer, "Target Time:", width / 2 - 60, baseY + 45, Color.WHITE.getRGB());
        drawCenteredString(fontRenderer, "Timeout:", width / 2 + 120, baseY + 45, Color.WHITE.getRGB());

        server.drawTextBox();
        hour.drawTextBox();
        minute.drawTextBox();
        second.drawTextBox();
        day.drawTextBox();
        month.drawTextBox();
        year.drawTextBox();
        timeout.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        server.drawTextBox();
        hour.drawTextBox();
        minute.drawTextBox();
        second.drawTextBox();
        day.drawTextBox();
        month.drawTextBox();
        year.drawTextBox();
        timeout.drawTextBox();
    }

    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);

        baseY = height / 2 - 110 / 2;

        server = new GuiTextField(100, fontRenderer, width / 2 - 155, baseY + 15, 310, 20);
        server.setMaxStringLength(512);
        server.setText(AutoQueueConfig.server);
        server.setFocused(true);

        hour = new GuiTextField(101, fontRenderer, width / 2 - 155, baseY + 60, 20, 20);
        hour.setValidator(NUMERIC_VALIDATOR);
        hour.setText(Integer.toString(AutoQueueConfig.timestamp.getHour()));

        minute = new GuiTextField(102, fontRenderer, width / 2 - 130, baseY + 60, 20, 20);
        minute.setValidator(NUMERIC_VALIDATOR);
        minute.setText(Integer.toString(AutoQueueConfig.timestamp.getMinute()));

        second = new GuiTextField(103, fontRenderer, width / 2 - 105, baseY + 60, 20, 20);
        second.setValidator(NUMERIC_VALIDATOR);
        second.setText(Integer.toString(AutoQueueConfig.timestamp.getSecond()));

        day = new GuiTextField(104, fontRenderer, width / 2 - 60, baseY + 60, 20, 20);
        day.setValidator(NUMERIC_VALIDATOR);
        day.setText(Integer.toString(AutoQueueConfig.timestamp.getDayOfMonth()));

        month = new GuiTextField(105, fontRenderer, width / 2 - 35, baseY + 60, 20, 20);
        month.setValidator(NUMERIC_VALIDATOR);
        month.setText(Integer.toString(AutoQueueConfig.timestamp.getMonth().getValue()));

        year = new GuiTextField(106, fontRenderer, width / 2 - 10, baseY + 60, 40, 20);
        year.setValidator(NUMERIC_VALIDATOR);
        year.setText(Integer.toString(AutoQueueConfig.timestamp.getYear()));

        timeout = new GuiTextField(107, fontRenderer, width / 2 + 90, baseY + 60, 60, 20);
        timeout.setValidator(NUMERIC_VALIDATOR);
        timeout.setText(Integer.toString(AutoQueueConfig.afkTimeout));

        enable = new GuiButton(0, width / 2 - 170, baseY + 105, 80, 20, "Enable");
        enable.enabled = !AutoQueueConfig.enabled;

        disable = new GuiButton(1, width / 2 - 85, baseY + 105, 80, 20, "Disable");
        disable.enabled = AutoQueueConfig.enabled;

        confirm = new GuiButton(2, width / 2, baseY + 105, 80, 20, "Confirm");

        cancel = new GuiButton(3, width / 2 + 85, baseY + 105, 80, 20, "Cancel");

        buttonList.add(enable);
        buttonList.add(disable);
        buttonList.add(confirm);
        buttonList.add(cancel);
    }

    @Override
    protected void mouseClicked(int x, int y, int b) throws IOException {
        super.mouseClicked(x, y, b);
        server.mouseClicked(x, y, b);
        hour.mouseClicked(x, y, b);
        minute.mouseClicked(x, y, b);
        second.mouseClicked(x, y, b);
        day.mouseClicked(x, y, b);
        month.mouseClicked(x, y, b);
        year.mouseClicked(x, y, b);
        timeout.mouseClicked(x, y, b);
    }

    @Override
    protected void keyTyped(char c, int k) throws IOException {
        super.keyTyped(c, k);
        server.textboxKeyTyped(c, k);
        hour.textboxKeyTyped(c, k);
        minute.textboxKeyTyped(c, k);
        second.textboxKeyTyped(c, k);
        day.textboxKeyTyped(c, k);
        month.textboxKeyTyped(c, k);
        year.textboxKeyTyped(c, k);
        timeout.textboxKeyTyped(c, k);

        // todo: Program what happens when you press the tab key.

        confirm.enabled = checkTimestampFields();

        if (k == Keyboard.KEY_RETURN) {
            confirmButton_OnClick();
        }
    }

    private boolean checkTimestampFields() {
        boolean valid = true;
        int _hour = Integer.parseInt(hour.getText());
        if (_hour < 0 || _hour > 24)
            valid = false;
        if (_hour == 24)
            hour.setText("0");
        int _minute = Integer.parseInt(minute.getText());
        if (_minute < 0 || _minute > 60)
            valid = false;
        if (_minute == 60)
            minute.setText("0");
        int _second = Integer.parseInt(second.getText());
        if (_second < 0 || _second > 60)
            valid = false;
        if (_second == 60)
            second.setText("0");
        int _day = Integer.parseInt(day.getText());
        if (_day <= 0)
            valid = false;
        if (_day > 31)
            valid = false;
        int _month = Integer.parseInt(month.getText());
        if (_month <= 0 || _month > 12)
            valid = false;
        int _year = Integer.parseInt(year.getText());
        if (_day > YearMonth.of(_year, _month).lengthOfMonth())
            valid = false;
        return valid;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }
}
