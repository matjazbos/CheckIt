package com.mbostic.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mbostic.gameObjects.AbstractObject;
import com.mbostic.gameObjects.Button;
import com.mbostic.gameObjects.CheckBox;
import com.mbostic.gameObjects.Icon;
import com.mbostic.gameObjects.RadioButton;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class CheckItMain extends InputAdapter implements ApplicationListener {
	private static final String TAG = CheckItMain.class.getName();
	SpriteBatch batch;
	public static int rBN;
	public static int cBN;
	public static int buttonN; //št gumba
	public static Button button;
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
	Button resetBestTime;
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
	static final int VIEWPORT_WIDTH = 720;
	static final int VIEWPORT_HEIGHT = 1500;
	//	private OrthographicCamera cam;
	private Viewport viewport;
	private Camera camera;
	@Override
	public void create () {
//		midX = Gdx.graphics.getWidth()/2;
//		midY = Gdx.graphics.getHeight()/2;
		midX = VIEWPORT_WIDTH/2;
		midY = VIEWPORT_HEIGHT/2;
//		Gdx.app.log("MyTag", "my informative message w: " + Gdx.graphics.getWidth() + " h: " + Gdx.graphics.getHeight());
//
//		float w = Gdx.graphics.getWidth();
//		float h = Gdx.graphics.getHeight();
//
//		float scale = (float)PREFERRED_HEIGHT / h;
//
//		camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
//		camera.setToOrtho(true, PREFERRED_WIDTH, PREFERRED_HEIGHT);

		camera = new PerspectiveCamera();
		viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, camera);
		// Set the initial position of the camera
		camera.position.set((float) VIEWPORT_WIDTH / 2, (float) VIEWPORT_HEIGHT / 2, 0);
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.input.setInputProcessor(this);
		batch = new SpriteBatch();
		Assets.instance.init(new AssetManager());
		init();
		buttons();
	}

	public void buttons(){
		smallReplay = new Icon(2*midX-64-30, 2*midY-64-20, 64, "replay");
		replay = new Icon(midX-128, 500, 256, "replay");
		play = new Icon(midX-128, 500, 256, "play");
		settings = new Icon(4*midX/3-90, 200, 180, "settings");
		exit = new Icon(2*midX/3-90, 200, 180, "exit");
		home = new Icon(4*midX/3-90, 200, 180, "home");
	}

	public void init (){
		delay =0;
		rBN = -1;
		cBN = 0;
		buttonN = 0;
		time = 0;
		setGamePositions();
		resetBestTime = new Button(midX + 150, midY );

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
		if (time < Assets.getBestTime()){
			end = "New high score!\n" + shortTime;
			Assets.setBestTime((float) Math.round(time * 10f) / 10f);
		}
		init();
		buttonN = -1;
	}


	@Override
	public void render () {
//		viewport.apply();

		deltaTime = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		if (!test) {
//welcome screen
			if (buttonN == 1) {
				Assets.instance.robotoBig.draw(batch, "Check it!", 120, 2 * midY - 100);

				if(!(Assets.getBestTime()==300))
					Assets.instance.roboto.draw(batch,"Best time: " + Assets.getBestTime(), 130,  2 * midY - 300);
				play.render(batch);
				settings.render(batch);
				exit.render(batch);
				if (Gdx.input.justTouched()) {
					if (play.tap(getX(), getY())) {
						button = new Button(randx(), randy());
					} else if (exit.tap(getX(), getY())) {
						Gdx.app.exit();
					}
					else if (settings.tap(getX(), getY())) {

						buttonN = -2;
					}
				}
			}
//napiše čas
			if (buttonN != 1 && buttonN != -1 && buttonN != -2) {
				time += deltaTime;
				Assets.instance.roboto.draw(batch, Float.toString((float) Math.round(time * 10f) / 10f), midX - 50, 2 * midY - 30);
				smallReplay.render(batch);// replay
				if (Gdx.input.justTouched())
					if (smallReplay.tap(getX(), getY())) {
						init();
						buttonN = 1;
						button = new Button(randx(), randy());
					}
			}
//gumb
			if (buttonN != -1 && buttonN != 1 && buttonN != -2 && buttonN != gamePositions[0] &&
					buttonN != gamePositions[1] && buttonN != gamePositions[2] && buttonN != gamePositions[3]) {
				if (Gdx.input.justTouched()) button.tap(getX(), getY());
				button.render(batch);
			}
//checkboxi
			if (buttonN == gamePositions[0]) {
				checkBoxCheck(cB);
				for (CheckBox chb : cB) chb.render(batch);
			}
//premikajoči checkboxi 1
			if (buttonN == gamePositions[1]) {
				checkBox1();
				for (CheckBox c : cb1) c.render(batch);

			}
//premikajoči checkboxi 2
			if (buttonN == gamePositions[2]) {
				checkBox2();
				for (CheckBox c : cb2) c.render(batch);
			}
//radio button
			if (buttonN == gamePositions[3]) {
				radioButtonCheck();
				for (RadioButton rdb : rB) rdb.render(batch);
			}
//konec
			if (buttonN >= 30) {
				gameOver(true);
			}
			if (buttonN == -1) {
				replay.render(batch);
				home.render(batch);
				exit.render(batch);
				Assets.instance.roboto.draw(batch, end, 200, 2 * midY - 100);
				if (delay < 0.5f) delay += deltaTime;
				else {

					if (Gdx.input.justTouched()) {
						if (home.tap(getX(), getY())) {
							buttonN = 1;
							delay = 0;
						} else if (replay.tap(getX(), getY())) {
							buttonN = 1;
							delay = 0;
							button = new Button(randx(), randy());
						} else if (exit.tap(getX(), getY())) {
							Gdx.app.exit();
						}
					}
				}
			}
//nastavitve
			if (buttonN == -2) {
				home.render(batch);
				resetBestTime.render(batch);
				Assets.instance.roboto.draw(batch, "reset best time", 10, midY+50);
				if (delay < 0.5f) delay += deltaTime;
				else {
					if (Gdx.input.justTouched()) {
						{
							if (home.tap(getX(), getY())) {
								buttonN = 1;
								delay = 0;
							}
							else if (resetBestTime.tap(getX(), getY())) {
								Assets.setBestTime(300);
								buttonN = -2;
							}
						}
					}
				}
			}
		}
		else {
			checkBox2();
			for (CheckBox c : cb2) c.render(batch);
		}
		batch.end();

	}
	public static int getY() {return VIEWPORT_HEIGHT / (Gdx.graphics.getHeight() - Gdx.input.getY());}
	public static int getX() {return VIEWPORT_WIDTH / Gdx.input.getX();}
	public static int randx() {return MathUtils.random(10, VIEWPORT_WIDTH - 100);}
	public static int randy() {return MathUtils.random(10, VIEWPORT_HEIGHT - 200);}

	int r;

	public void setGamePositions() {
		for(int j=0; j<4; j++){
			r = MathUtils.random(2,29);

			for (int i=0;i<4;i++){
				if(gamePositions[i] == r){r=MathUtils.random(2, 29); i=0;} //preveri če je pozicije že uporabljena
			}
			gamePositions[j]=r;
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
				chb.tap(getX(), getY());
			}
		}
		allChecked = true;
		for (AbstractObject chb : objects) {
			if (!chb.checked) {
				allChecked = false;
				break;}
		}
		if (allChecked) {button = new Button(randx(), randy());}
	}

	public void radioButtonCheck() {
		if (Gdx.input.justTouched()) {
			for (RadioButton rb : rB) {
				if (rb.tap(getX(), getY())) {
					for (int i = 0; i < rB.length; i++) {
						if (i == rb.id) continue;
						rB[i].checked = false;
					}
					if (rb.id == 5) button = new Button(randx(), randy());
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
		viewport.update(width, height, true);
//		camera.update();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

}
