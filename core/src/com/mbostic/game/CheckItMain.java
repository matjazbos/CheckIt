package com.mbostic.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mbostic.gameObjects.AbstractObject;
import com.mbostic.gameObjects.Button;
import com.mbostic.gameObjects.CheckBox;
import com.mbostic.gameObjects.Icon;
import com.mbostic.gameObjects.RadioButton;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckItMain extends InputAdapter implements ApplicationListener, InputProcessor {
	private static final String TAG = CheckItMain.class.getName();
	SpriteBatch batch;
	public static int rBN;
	public static int cBN;
	public int buttonN; //št gumba
	public Button button;
	float time;
	String end; //tekst na koncu igre
	int midX;//sredina ekrana
	int midY;
	int[] gamePositions = new int[4];
	int checkboxes = 10; //število checkboxov
	CheckBox[] cb1 = new CheckBox[checkboxes];
	CheckBox[] cb2 = new CheckBox[10];
	CheckBox[] cB = new CheckBox[10];
	RadioButton[] rB = new RadioButton[10];
	Button resetBestTime, openPrivacyPolicy;
	boolean test = false;

	float delay;
	float deltaTime;
	boolean allChecked;
	RadioButton radioB;
	Icon play;
	Icon settings;
	Icon replay;
	Icon exit;
	Icon home;
	Icon smallReplay;

	private final OpenUrl openUrl;

	public CheckItMain(OpenUrl openUrl) {
		this.openUrl = openUrl;
	}

	@Override
	public void create () {
		midX = Gdx.graphics.getWidth()/2;
		midY = Gdx.graphics.getHeight()/2;
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		batch = new SpriteBatch();
		Assets.instance.init(new AssetManager());
		buttons();

	}
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.BACK){
			// Do your optional back button handling (show pause menu?)
			if(buttonN == 0){
				Gdx.app.exit();
			} else {
				buttonN = 0;
			}
		}
		return false;
	}
	public void buttons(){
		smallReplay = new Icon(2*midX-64-30, 2*midY-64-20, 64, "replay");
		replay = new Icon(midX-128, 500, 256, "replay");
		play = new Icon(midX-128, 500, 256, "play");
		settings = new Icon(4*midX/3-90, 200, 180, "settings");
		exit = new Icon(2*midX/3-90, 200, 180, "exit");
		home = new Icon(4*midX/3-90, 200, 180, "home");
		resetBestTime = new Button(midX + 220, midY + 200);
		openPrivacyPolicy = new Button(midX + 220, midY);
		button = new Button(randx(), randy());

	}

	public void init (){
		delay =0;
		rBN = -1;
		cBN = 0;
		buttonN = 1;
		button.position.x = randx();
		button.position.y = randy();
		time = 0;
		setGamePositions();

		radioB = new RadioButton(-1,-1);
		if(Assets.getBestTime()==0f)Assets.setBestTime(300f);
		for (int j = 0; j < cb1.length; j++) cb1[j] =
				new CheckBox(midX - 20 + MathUtils.random(-100, 100), midY + MathUtils.random(-100, 100));//checkboxi nastanejo na sredini

		setPositions();
		for (int j = 0; j < cB.length; j++) cB[j] = new CheckBox(positions[0][j], positions[1][j]); //checkboxi nastanejo na random
		setPositions();
		for (int j = 0; j < cb2.length; j++) cb2[j] = new CheckBox(positions[0][j], positions[1][j]); //checkboxi nastanejo na random
		setPositions();
		for (int j = 0; j < rB.length; j++) rB[j] = new RadioButton(positions[0][j], positions[1][j]);

	}

	Float shortTime;
	public void gameOver(boolean finished){
		shortTime = (float) Math.round(time * 10f) / 10f;
		end = finished ? "Your time:\n    " + shortTime : "Try again!";
		if (finished && time < Assets.getBestTime()){
			end = "New high score!\n" + shortTime;
			Assets.setBestTime((float) Math.round(time * 10f) / 10f);
		}
		buttonN = -1;
	}


	@Override
	public void render () {
		deltaTime = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
//		if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
//			if(buttonN == 0){
//				Gdx.app.exit();
//			} else {
//				buttonN = 0;
//			}
//		}
//		if (!test) {
//welcome screen
		if (buttonN == 0) {
			Assets.instance.robotoBig.draw(batch, "Check it!", 120, 2 * midY - 100);

			if(!(Assets.getBestTime()==300))
				Assets.instance.roboto.draw(batch,"Best time: " + Assets.getBestTime(), 130,  2 * midY - 300);
			play.render(batch);
			settings.render(batch);
			exit.render(batch);
			if (Gdx.input.justTouched()) {
				if (play.tap(Gdx.input.getX(), getY())) {
					init();
				} else if (exit.tap(Gdx.input.getX(), getY())) {
					Gdx.app.exit();
				} else if (settings.tap(Gdx.input.getX(), getY())) {
					buttonN = -2;
				}
			}
		}
		if (buttonN > 0) {
//napiše čas
			time += deltaTime;
			Assets.instance.roboto.draw(batch, Float.toString((float) Math.round(time * 10f) / 10f), midX - 50, 2 * midY - 30);
			smallReplay.render(batch);// replay
			if (Gdx.input.justTouched())
				if (smallReplay.tap(Gdx.input.getX(), getY())) {
					init();
				}
//checkboxi
			if (buttonN == gamePositions[0]) {
				checkBoxCheck(cB);
				for (CheckBox chb : cB) chb.render(batch);
			} else if (buttonN == gamePositions[1]) {
//premikajoči checkboxi 1
				checkBox1();
				for (CheckBox c : cb1) c.render(batch);

			} else if (buttonN == gamePositions[2]) {
//premikajoči checkboxi 2
				checkBox2();
				for (CheckBox c : cb2) c.render(batch);
			} else if (buttonN == gamePositions[3]) {
//radio button
				radioButtonCheck();
				for (RadioButton rdb : rB) rdb.render(batch);
			} else if(buttonN <= 30){
//gumb
				if (Gdx.input.justTouched()){
					if(button.tap(Gdx.input.getX(), getY())){
						nextButton();
					}
				}
				button.render(batch);
			} else {
//konec
				gameOver(true);
			}
		}

		if (buttonN == -1) {
			replay.render(batch);
			home.render(batch);
			exit.render(batch);
			Assets.instance.roboto.draw(batch, end, 200, 2 * midY - 100);
			if (delay < 0.5f) delay += deltaTime;
			else {
				if (Gdx.input.justTouched()) {
					if (home.tap(Gdx.input.getX(), getY())) {
						buttonN = 0;
						delay = 0;
					} else if (replay.tap(Gdx.input.getX(), getY())) {
						init();
					} else if (exit.tap(Gdx.input.getX(), getY())) {
						Gdx.app.exit();
					}
				}
			}
		} else if (buttonN == -2) {
//nastavitve
			home.render(batch);
			resetBestTime.render(batch);
			Assets.instance.roboto.draw(batch, "Reset best time", 200, midY+265);
			openPrivacyPolicy.render(batch);
			Assets.instance.roboto.draw(batch, "Privacy policy", 200, midY+65);
			if (delay < 0.5f) delay += deltaTime;
			else {
				if (Gdx.input.justTouched()) {
					if (home.tap(Gdx.input.getX(), getY())) {
						buttonN = 0;
						delay = 0;
					}
					else if (resetBestTime.tap(Gdx.input.getX(), getY())) {
						Assets.setBestTime(300);
						buttonN = -2;
					}
					else if (openPrivacyPolicy.tap(Gdx.input.getX(), getY())) {
						openUrl.openUrl("https://raw.githubusercontent.com/MatjazBostic/CheckIt/master/privacy_policy.txt");

						buttonN = -2;
					}
				}
			}
		}
//		}
//		else {
//			checkBox2();
//			for (CheckBox c : cb2) c.render(batch);
//		}
		batch.end();
	}
	public static int getY() {return Gdx.graphics.getHeight() - Gdx.input.getY();}
	public static int randx() {return MathUtils.random(50, Gdx.graphics.getWidth() - 100);}
	public static int randy() {return MathUtils.random(50, Gdx.graphics.getHeight() - 200);}

	public void setGamePositions() {
		List<Integer> intList = new ArrayList<>();
		for(int i=1; i<=30; i++) {
			intList.add(i);
		}

		Collections.shuffle(intList);

		for(int j=0; j<4; j++){
			gamePositions[j] = intList.get(j);
//			r = MathUtils.random(2,29);
//
//			for (int i=0;i<4;i++){
//				if(gamePositions[i] == r){r=MathUtils.random(2, 29); i=0;} //preveri če je pozicije že uporabljena
//			}
//			gamePositions[j]=r;
		}
	}

	public void checkBox1(){

		checkBoxCheck(cb1);
		for (CheckBox c : cb1) {
			c.position.x += c.speed.x * deltaTime; //premakne vse checkboxe
			c.position.y += c.speed.y * deltaTime;
			if (!c.checked && (c.position.x < -50 || c.position.x > 2 * midX || c.position.y < -50 || c.position.y > 2 * midY))
				gameOver(false);                                   //preveri če je checkbox ušel iz ekrana, če je se igra konča
		}
	}
	Rectangle r1 = new Rectangle();
	Rectangle r2 = new Rectangle();
	int[][] cBIndex = new int[5][2];
	int cBIndexN = 0;
	float[] collisionDetectDelay = new float[5];
	public void checkBox2(){
		checkBoxCheck(cb2);
		for (CheckBox c1 : cb2) {
			c1.position.x += c1.speed.x * deltaTime * 4; //premakne vse checkboxe
			c1.position.y += c1.speed.y * deltaTime * 4;
			if (c1.position.x < 0 || c1.position.x > 2 * midX - 40) c1.speed.x = -c1.speed.x;
			if (c1.position.y < 0 || c1.position.y > 2 * midY - 40) c1.speed.y = -c1.speed.y;//smer se obrne, če zadane rob ekrana
			r1.set(c1.position.x, c1.position.y, 40f, 40f);  //collision detect
			for (CheckBox c2 : cb2) {
				if (c1 == c2) continue;
				r2.set(c2.position.x, c2.position.y, 40f, 40f);
				if (r1.overlaps(r2)) {
					for(int i=0; i < cBIndex.length; i++){
						if(!(((cBIndex[i][0]==c1.index && cBIndex[i][1]==c2.index) || (cBIndex[i][0]==c2.index && cBIndex[i][1]==c1.index))
								&& (collisionDetectDelay[i]<0.2f))) { //preveri, če sta se boxa ze zaletela manj kot 0.2s nazaj, zato da se ne "zapleteta"
							if (Math.abs(c1.position.x - c2.position.x) > Math.abs(c1.position.y - c2.position.y)) {
								c1.speed.x = -c1.speed.x;
								c2.speed.x = -c2.speed.x;
							} else {
								c1.speed.y = -c1.speed.y;
								c2.speed.y = -c2.speed.y;
							}
						}
					}
					cBIndex[cBIndexN][0] = c1.index;
					cBIndex[cBIndexN][1] = c2.index;
					collisionDetectDelay[cBIndexN] = 0;
					cBIndexN++;
					if(cBIndexN == 5) cBIndexN=0;

				}
			}
		}
		for (int i=0; i < collisionDetectDelay.length; i++){
			if (collisionDetectDelay[i]<0.2f)
				collisionDetectDelay[i] += deltaTime;
		}
	}

	public void checkBoxCheck(AbstractObject[] objects){
		if (Gdx.input.justTouched()) {
			for (AbstractObject chb : objects) {
				chb.tap(Gdx.input.getX(), getY());
			}
		}
		allChecked = true;
		for (AbstractObject chb : objects) {
			if (!chb.checked) {
				allChecked = false;
				break;}
		}
		if (allChecked) {nextButton();}
	}

	public void nextButton(){
		buttonN++;
		button.position.x = randx();
		button.position.y = randy();
	}

	public void radioButtonCheck() {
		if (Gdx.input.justTouched()) {
			for (RadioButton rb : rB) {
				if (rb.tap(Gdx.input.getX(), getY())) {
					for (int i = 0; i < rB.length; i++) {
						if (i == rb.id) continue;
						rB[i].checked = false;
					}
					if (rb.id == 5) nextButton();
				}
			}
		}
	}
	int[] randx = new int[10];
	int[] randy = new int[10];
	int[][] positions = new int[2][10];
	int rX;
	int rY;

	public void setPositions (){

		for(int j=0;j<10;j++){
			rX = randx();
			rY = randy();
			for (int i=0;i<10;i++){
				if(Math.abs(randx[i] - rX)<40){rX=randx(); i=0;} //preveri če se objekt prekriva s katerim od prejšnih
			}
			for (int i=0;i<10;i++){
				if(Math.abs(randy[i] - rY)<40){rY=randy(); i=0;}
			}
			randy[j]=rY;
			randx[j]=rX;
		}
		positions[0] = randx;
		positions[1] = randy;
	}

	@Override
	public void dispose() {
		batch.dispose();

	}
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

}