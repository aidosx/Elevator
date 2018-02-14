package com.ilya.busyElevator.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();

    private AssetManager assetManager;

    public AssetButton button;

    public AssetBigRedButton bigRedButton;

    public AssetBackGround bg;

    public AssetElevator elevator;

    public AssetMisc misc;

    public BitmapFont font;

    public BitmapFont scoreFont;

    public AssetEmotionsOfGuy emotion;

    public AssetFonts arielFonts;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontParameter parameter;

    public AssetGuy guy;
    public Array<AtlasRegion> faces;
    public Array<AtlasRegion> previewElevators;
    public Array<AtlasRegion> costumes;

    public BitmapFont endgameScoreFonts;

    public BitmapFont highScoreFonts;

    public Sound sound_pop;
    public Sound buttonPress;
    public Sound elevatorMove;


    //singleton
    private Assets(){}

    public void init(AssetManager assetManager) {

        this.assetManager = assetManager;

        assetManager.setErrorListener(this);

        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);

        assetManager.load(Constants.TEXTURE_ATLAS_FOR_FACE,TextureAtlas.class);

        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);

        TextureAtlas faceAtlas = assetManager.get(Constants.TEXTURE_ATLAS_FOR_FACE);


        faces = new Array<AtlasRegion>();

        previewElevators = new Array<AtlasRegion>();

        costumes = new Array<AtlasRegion>();

        button = new AssetButton(atlas);

        bigRedButton = new AssetBigRedButton(atlas);

        bg = new AssetBackGround(atlas);

        elevator = new AssetElevator(atlas);

        guy = new AssetGuy(atlas,faceAtlas);

        emotion = new AssetEmotionsOfGuy(atlas);

//        fonts = new AssetFonts();
        arielFonts = new AssetFonts();

        misc = new AssetMisc(atlas);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        parameter = new FreeTypeFontParameter();
        parameter.size = 20;

        parameter.flip = false;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;

        font = generator.generateFont(parameter);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        parameter = new FreeTypeFontParameter();
        parameter.size = 80;

        parameter.flip = false;
        scoreFont = generator.generateFont(parameter);

        parameter.flip=true;
        parameter.borderWidth = 1;
        endgameScoreFonts = generator.generateFont(parameter);

        parameter.size = 60;
        parameter.borderWidth = 0;
        parameter.flip = true;

        highScoreFonts = generator.generateFont(parameter);
        generator.dispose();

        sound_pop = Gdx.audio.newSound(Gdx.files.internal("sounds/pop.wav"));
        buttonPress = Gdx.audio.newSound(Gdx.files.internal("sounds/buttonPress.wav"));
        elevatorMove = Gdx.audio.newSound(Gdx.files.internal("sounds/elevatorMove.wav"));

    }

    public class AssetButton {
        public final AtlasRegion buttonUp;
        public final AtlasRegion buttonDown;
        public final AtlasRegion playButtonUp;
        public final AtlasRegion playButtonDown;
        public final AtlasRegion addbuttonUp;
        public final AtlasRegion addbuttonDown;
        public final AtlasRegion slideButtonLeft;
        public final AtlasRegion slideButtonRight;
        public final AtlasRegion menuButton;
        public final AtlasRegion menuButtonPressed;
        public final AtlasRegion elevatorsButton;
        public final AtlasRegion elevatorsButtonPressed;

        public AssetButton(TextureAtlas atlas){
            buttonUp = atlas.findRegion("arrButton");
            buttonDown = atlas.findRegion("arrButtonPressed");
            playButtonUp = atlas.findRegion("playButton");
            playButtonDown = atlas.findRegion("playButtonPressed");
            playButtonUp.flip(false,true);
            playButtonDown.flip(false,true);
            addbuttonUp = atlas.findRegion("addButton");
            addbuttonDown = atlas.findRegion("addButtonPressed");
            slideButtonLeft = atlas.findRegion("toggleLeft");
            slideButtonRight = atlas.findRegion("toggleRight");
            menuButton = atlas.findRegion("menuButton");
            menuButtonPressed = atlas.findRegion("menuButtonPressed");
            menuButton.flip(false,true);
            menuButtonPressed.flip(false,true);
            elevatorsButton = atlas.findRegion("elevatorsButton");
            elevatorsButtonPressed = atlas.findRegion("elevatorsButtonPressed");
            elevatorsButton.flip(false,true);
            elevatorsButtonPressed.flip(false,true);

        }
    }

    public class AssetBigRedButton{
        public final AtlasRegion buttonUp;
        public final AtlasRegion buttonDown;

        public AssetBigRedButton(TextureAtlas atlas) {
            buttonUp = atlas.findRegion("bigRedButton");
            buttonDown = atlas.findRegion("bigRedButtonPressed");
        }

    }

    public class AssetBackGround{
        public final AtlasRegion bg;
        public final AtlasRegion menuBg;
        public final AtlasRegion bottomBg;

        public AssetBackGround(TextureAtlas atlas){
            bg = atlas.findRegion("bg");
            menuBg = atlas.findRegion("backgroundForButtons");
            bottomBg = atlas.findRegion("bottomBg");
        }
    }

    public class AssetElevator {
        public final AtlasRegion elevator;
        public final AtlasRegion elevator2;
        public final AtlasRegion elevator3;

        public AssetElevator(TextureAtlas atlas) {
            elevator = atlas.findRegion("elevator");
            elevator2 = atlas.findRegion("elevator2");
            elevator3 = atlas.findRegion("elevator3");
            previewElevators.add(elevator);
            previewElevators.add(elevator2);
            previewElevators.add(elevator3);
        }
    }

    public class AssetMisc {
        public final AtlasRegion bubble;
        public final AtlasRegion logo;
        public final AtlasRegion miscButtons;
        public final AtlasRegion miscTouchHand;
        public final AtlasRegion miscendGameBanner;
        public final AtlasRegion miscProgressBar;
        public final AtlasRegion peaceOfProgressBar;
        public final AtlasRegion tutorialArrowDown;
        public final AtlasRegion tutorialArrowUp;
        public final AtlasRegion tutorialArrowLeftRight;

        public AssetMisc(TextureAtlas atlas)
        {
            bubble = atlas.findRegion("bubble");
            logo = atlas.findRegion("logo");
            miscendGameBanner = atlas.findRegion("endGameBanner");
            miscButtons = atlas.findRegion("buttons");
            miscTouchHand = atlas.findRegion("touch");
            miscProgressBar = atlas.findRegion("waitBar");
            peaceOfProgressBar = atlas.findRegion("peaceOfProgressBar");
            tutorialArrowDown = atlas.findRegion("tutorialArrowDown");
            tutorialArrowUp = atlas.findRegion("tutorialArrowUp");
            tutorialArrowLeftRight = atlas.findRegion("tutorialArrowLeftRight");
            miscButtons.flip(false,true);
            miscTouchHand.flip(false,true);
            logo.flip(false,true);
        }
    }

    public class AssetFonts {
        public final BitmapFont defaultSmall;
        public final BitmapFont defaultNormal;
        public final BitmapFont defaultBig;

        public AssetFonts(){
            defaultSmall = new BitmapFont(Gdx.files.internal("newFont.fnt"), true);
            defaultNormal = new BitmapFont(Gdx.files.internal("newFont.fnt"), true);
            defaultBig = new BitmapFont(Gdx.files.internal("newFont.fnt"), true);
            // set font sizes
            defaultSmall.getData().scale(0.5f);
            defaultNormal.getData().scale(1.0f);
            defaultBig.getData().scale(2.0f);
        }
    }

    public class AssetGuy{

        public final AtlasRegion shoes;
        public final AtlasRegion shoes1;
        public final AtlasRegion shoes2;

        public AssetGuy(TextureAtlas atlas, TextureAtlas facesAtlas){
            for (int i = 1;i<77;i++) {
                if (facesAtlas.findRegion("p"+ String.valueOf(i))!=null){
                    faces.add(facesAtlas.findRegion("p"+String.valueOf(i)));
                }
            }

            for (int i = 1;i<6;i++){
                if (atlas.findRegion("bus"+String.valueOf(i))!=null){
                    costumes.add(atlas.findRegion("bus"+String.valueOf(i)));
                }

            }
            shoes = atlas.findRegion("shoes");
            shoes1 = atlas.findRegion("shoes1");
            shoes2 = atlas.findRegion("shoes2");
        }
    }

    public class AssetEmotionsOfGuy{
        public final AtlasRegion emotions_angry;
        public final AtlasRegion emotions_closedeyes;
        public final AtlasRegion emotions_pokerface;
        public final AtlasRegion emotions_whistle1;
        public final AtlasRegion emotions_whistle2;
        public final AtlasRegion emotions_weird;

        public AssetEmotionsOfGuy(TextureAtlas atlas) {
            emotions_angry = atlas.findRegion("emotions_angry");
            emotions_closedeyes = atlas.findRegion("emotions_closedeyes");
            emotions_pokerface = atlas.findRegion("emotions_pokerface");
            emotions_whistle1 = atlas.findRegion("emotions_whistle1");
            emotions_whistle2 = atlas.findRegion("emotions_whistle2");
            emotions_weird = atlas.findRegion("emotions_weird");
        }
    }


    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
    }

    @Override
    public void dispose()

    {
        font.dispose();
        assetManager.dispose();
    }
}
