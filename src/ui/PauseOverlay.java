package ui;

import static utils.Constants.Assets;
import static utils.Constants.Config;
import static utils.Constants.UI.Pause.*;
import static utils.Constants.UI.DEFAULT_FONT;

import states.GameState;
import states.Play;
import utils.Loader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PauseOverlay {
    private BufferedImage background;
    private int x, y, width, height;
    private SoundButton musicButton, sfxButton;
    private UMButton menu, resume;
    private Font pauseFont, font;
    private Play play;

    public PauseOverlay(Play play) {
        this.play = play;
        init();
    }

    public void init() {
        loadBackground();
        createSoundButtons();
        pauseFont = DEFAULT_FONT.deriveFont(50f);
        font = DEFAULT_FONT.deriveFont(40f);
    }

    private void createSoundButtons() {
        musicButton = new SoundButton(SOUND_B_X, MUSIC_B_Y, SOUND_B_WIDTH , SOUND_B_HEIGHT);
        sfxButton = new SoundButton(SOUND_B_X, SFX_B_Y, SOUND_B_WIDTH, SOUND_B_HEIGHT);
        menu = new UMButton(SOUND_B_X - 100, SFX_B_Y + 100, SOUND_B_WIDTH, SOUND_B_HEIGHT);
        resume = new UMButton(SOUND_B_X - 100, SFX_B_Y + 150, SOUND_B_WIDTH, SOUND_B_HEIGHT);
    }

    private void loadBackground() {
        background = Loader.getAssets(Assets.PAUSE_BG);
        width = (int) (background.getWidth() * Config.SCALE * 3.5) ;
        height = (int) (background.getHeight() * Config.SCALE * 3.5);
        x = Config.WIDTH / 2 - width / 2;
        y = 200;
    }

    public void update() {
        musicButton.update();
        sfxButton.update();
        menu.update();
        resume.update();
    }

    public void render(Graphics g) {
        // semi-transparent background
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, Config.WIDTH, Config.HEIGHT);
        g2d.dispose();

        g.drawImage(background, x, y, width, height, null);

        musicButton.render(g);
        sfxButton.render(g);
        menu.render(g);
        resume.render(g);
        renderFont(g);
    }

    // TODO: Fix this!!!
    public void renderFont(Graphics g) {
        g.setFont(pauseFont);
        g.setColor(Color.BLACK);

        FontMetrics pauseMetrics = g.getFontMetrics(pauseFont);
        int textX = x + (width - pauseMetrics.stringWidth("Pause")) / 2;
        int textY = y + pauseMetrics.getAscent() + 40;
        g.drawString("Pause", textX, textY);

        g.setFont(font);

        int halfWidth = x + (width / 2);

        FontMetrics fontMetrics = g.getFontMetrics(font);
        textX = halfWidth - fontMetrics.stringWidth("Music") - 20;
        textY = musicButton.getY() + (musicButton.getHeight() + fontMetrics.getAscent()) / 2 - 5;
        g.drawString("Music", textX, textY);

        // Render "SFX" next to the SFX button
        textX = halfWidth - fontMetrics.stringWidth("SFX") - 20;
        textY = sfxButton.getY() + (sfxButton.getHeight() + fontMetrics.getAscent()) / 2 - 5;
        g.drawString("SFX", textX, textY);
    }


    public void mouseDragged(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if (isHovering(e, musicButton)) {
            musicButton.setClicked(true);
        } else if (isHovering(e, sfxButton)) {
            sfxButton.setClicked(true);
        } else if (isHovering(e, menu)) {
            menu.setClicked(true);
        } else if (isHovering(e, resume)) {
            resume.setClicked(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isHovering(e, musicButton) && musicButton.isClicked()) {
            musicButton.setMuted(!musicButton.isMuted());
        } else if (isHovering(e, sfxButton) && sfxButton.isClicked()) {
            sfxButton.setMuted(!sfxButton.isMuted());
        } else if (isHovering(e, menu) && menu.isClicked()) {
            GameState.currentState = GameState.MENU;
            play.unpause();
        } else if (isHovering(e, resume) && resume.isClicked()) {
            play.unpause();
        }

        musicButton.reset();
        sfxButton.reset();
        menu.reset();
        resume.reset();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setHovered(isHovering(e, musicButton));
        sfxButton.setHovered(isHovering(e, sfxButton));
        menu.setHovered(isHovering(e, menu));
        resume.setHovered(isHovering(e, resume));
    }

    public void keyPressed(KeyEvent e) {

    }

    private boolean isHovering(MouseEvent e, PauseButton button) {
        return button.getBounds().contains(e.getX(), e.getY());
    }
}
