package com.ilya.busyElevator.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.ilya.busyElevator.game.Objects.AbstractButton;
import com.ilya.busyElevator.game.Objects.People;
import com.ilya.busyElevator.game.Objects.PlayButton;
import com.ilya.busyElevator.game.Objects.ToggleButton;
import com.ilya.busyElevator.game.miscObjects.AbstractMiscObjects;
import com.ilya.busyElevator.game.miscObjects.EndGameBanner;
import com.ilya.busyElevator.game.miscObjects.TutorialArrowDown;
import com.ilya.busyElevator.game.miscObjects.TutorialArrowLeftRight;
import com.ilya.busyElevator.game.miscObjects.TutorialArrowUp;
import com.ilya.busyElevator.util.Assets;
import com.ilya.busyElevator.util.CameraHelper;
import com.ilya.busyElevator.util.Constants;
import com.ilya.busyElevator.util.GamePreferences;

public class WorldRenderer implements Disposable {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    public static OrthographicCamera cameraGUI;
    public boolean showGUI;
    private WorldController worldController;
    private StretchViewport viewportGUI;
    private StretchViewport viewport;
    private ShapeRenderer shapeRenderer;
    protected static CameraHelper cameraHelper;
    private BitmapFont scoreFonts;
    private BitmapFont endgameScoreFonts;
    private TextureRegion bottomBg;
    private float alpha;
    private BitmapFont highscoreFont;
    private EndGameBanner endGameBanner;
    float fontx = 0;
    private TutorialArrowDown tutorialArrowDown;
    private TutorialArrowUp tutorialArrowUp;
    private TutorialArrowLeftRight tutorialArrowLeftRight;
    public boolean showTutorial = false;
    private int currentPreview = 0;
    float highscoreX = 30;




    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        showGUI = false;
        init();
    }

    private void init() {
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        viewport = new StretchViewport(640,960,camera);
        camera.position.set(0, 0, 0);
        camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.update();
        cameraGUI = new OrthographicCamera();
        viewportGUI = new StretchViewport(640,960,cameraGUI);
        viewportGUI.setCamera(cameraGUI);
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.setToOrtho(true, 640, 960);
        cameraGUI.update();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        cameraHelper = new CameraHelper(camera);
        bottomBg = new TextureRegion(com.ilya.busyElevator.util.Assets.instance.bg.bottomBg);
        scoreFonts = Assets.instance.scoreFont;
        endgameScoreFonts = Assets.instance.endgameScoreFonts;
        highscoreFont = Assets.instance.highScoreFonts;
        endGameBanner = new EndGameBanner();
        alpha = 0.2f;
        tutorialArrowDown = new TutorialArrowDown(320/2-50,240);
        tutorialArrowUp = new TutorialArrowUp(320/2-50,720);
        tutorialArrowLeftRight = new TutorialArrowLeftRight(280,480);

    }



    public void render(float delta){
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (worldController.gameOver)
            showGUI=false;

        renderScene(batch);

        if (showGUI) {
            renderGui(batch);
        }
        if (Constants.SHOW_HITBOXES)
            showGUIHitboxes();
        if (worldController.gameOver) {
            showGUI = false;
            gameOver(delta, worldController.listOfButtons.get(worldController.listOfButtons.size - 2),
                    worldController.listOfButtons.get(worldController.listOfButtons.size-1));
        }
        else if (Constants.GAME_STATE== Constants.GAME_STATE.IN_GAME)
            renderScore(batch);

    }


    private void renderGui(SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();

        for (AbstractButton b:worldController.listOfButtons) {
            b.render(batch);
        }

        batch.end();
    }



    private void renderScene(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (AbstractMiscObjects o: worldController.listOfMisc){
            o.render(batch);
        }

        worldController.elevator.render(batch);

        for (People p: WorldController.listOfPeople){
            p.render(batch);
        }
        for (People p: WorldController.listOfPeople){
            p.renderBubble(batch);
        }
        showBottomBg(batch);

        batch.end();
    }

    private void showBottomBg(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.draw(bottomBg, 0, 0, 640, 260);
    }

    private void renderScore(SpriteBatch batch){
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        scoreFonts.draw(batch,""+worldController.score,bottomBg.getRegionWidth()/2-20,150);
        batch.end();
    }

    private void showGUIHitboxes(){
        shapeRenderer.setProjectionMatrix(cameraGUI.combined);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(WorldController.r1.x, WorldController.r1.y, WorldController.r1.width,
                WorldController.r1.height);
        shapeRenderer.rect(WorldController.r2.x, WorldController.r2.y, WorldController.r2.width,
                WorldController.r2.height);
        shapeRenderer.rect(WorldController.r3.x, WorldController.r3.y, WorldController.r3.width,
                WorldController.r3.height);
        shapeRenderer.line(0, 700, 640, 700);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(0,0,cameraGUI.viewportWidth,cameraGUI.viewportHeight);
        shapeRenderer.end();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(-5f, 0.5f, 5f, 0.5f);
        shapeRenderer.end();
    }

    public void gameOver(float delta,AbstractButton adButton,AbstractButton menuButton){

        if (worldController.score>=10)
            fontx = 20;
        if (worldController.score>=100)
            fontx = 40;
        if (worldController.score>=1000)
            fontx = 60;
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        endGameBanner.render(batch);
        endgameScoreFonts.draw(batch,"Score",endGameBanner.getPos().x+endGameBanner.getDimension().x/2-135,
                endGameBanner.getPos().y+100);
        endgameScoreFonts.draw(batch,""+worldController.score,(endGameBanner.getPos().x+endGameBanner.getDimension().x/2-140/2)+40-fontx,
                endGameBanner.getPos().y+190);
        menuButton.render(batch);
        batch.end();

        if (alpha>=1f) return;

        endGameBanner.setPos(endGameBanner.getPos().x,endGameBanner.getPos().y+Interpolation.exp10.apply(alpha)*30);


        menuButton.setPos(endGameBanner.getPos().x+endGameBanner.getDimension().x/2-100,endGameBanner.getPos().y+endGameBanner.getDimension().y/2);

        alpha+=0.01f;

    }

    public void renderTutorialArrows(){
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        tutorialArrowDown.render(batch);
        tutorialArrowUp.render(batch);
        tutorialArrowLeftRight.render(batch);
        batch.end();

    }





    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }

    public void renderBounceScreen(Vector2 animation, float alpha, Vector2 dimension, PlayButton playButton, ToggleButton toggleButton,
                                   TextureRegion miscButtons, TextureRegion miscTouchHand) {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        batch.draw(Assets.instance.misc.logo,viewportGUI.getWorldWidth()/2-240, playButton.getPos().y - 250,500,300);
        playButton.setPos(animation.x + dimension.x / 2-playButton.getHitbox().x/2-25, animation.y + alpha * 650 + dimension.y / 10);
        toggleButton.setPos(playButton.getPos().x+100,playButton.getPos().y+200);
        playButton.render(batch);
//        elevatorsButton.render(batch);
        toggleButton.render(batch);
        batch.draw(miscButtons,toggleButton.getPos().x-128,toggleButton.getPos().y,128,128);
        batch.draw(miscTouchHand,toggleButton.getPos().x+128,toggleButton.getPos().y,128,128);
        if (GamePreferences.instance.score>10&GamePreferences.instance.score<100)
            highscoreX=20;
        if (GamePreferences.instance.score>100)
            highscoreX=10;
        highscoreFont.draw(batch,"HighScore "+GamePreferences.instance.score,viewportGUI.getWorldWidth()/2-230+highscoreX,viewportGUI.getWorldHeight()-160);
        batch.end();

    }


}
