package com.yoyo.dookin;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
//import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.utils.Array; 
public class DSplash extends Game implements Screen, GestureListener {
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private SpriteBatch batch;
	private SpriteBatch hudBatch;
	Sprite bgSprite1 = new Sprite(new Texture("bg.png"));
	Sprite fgSprite2 = new Sprite(new Texture("foreground2.png"));
	Sprite fgSprite3 = new Sprite(new Texture("foreground2.png"));
	Sprite reverseIco = new Sprite(new Texture("forward.png"));
	Sprite gasPedal = new Sprite(new Texture("gasUp.png"));
	Sprite brakePedal = new Sprite(new Texture("brakeUp.png"));
	Sprite fuel = new Sprite(new Texture("fuel.png"));
	Sprite menuPlace = new Sprite(new Texture("menuPlace.png"));
	Sprite restart = new Sprite(new Texture("resetTapped.png"));
	Sprite pause = new Sprite(new Texture("pause.png"));
	private OrthographicCamera camera;
	float lastPointx = 0;
	float lastPointy = 0;
	Body leftWheel2;
	float direction = 1;
	Vector2[] tmp;
	Fixture ground;
	int amplitude = 3;
	float height = 720;
	Game mygame;
	FixtureDef fixtureDef = new FixtureDef(),
			wheelFixtureDef = new FixtureDef();
	Sprite splash = new Sprite(new Texture("coin.png"));
	Sprite coin = new Sprite(new Texture("coin.png"));
	private boolean inAir = false;
	private final float TIMESTEP = 1 / 60f;
	public float scaleFactor = (50*Gdx.graphics.getWidth())/1920f;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
	public DroidCar car;
	private Array<Body> tmpBodies = new Array<Body>();
	RayHandler rayHandler;
	PointLight carLight;
	PointLight carLight2;
	ConeLight headLight;
	private float sCounter1 = 0;
	private float sCounter2 = 0;
	ShapeRenderer shapeDebugger = new ShapeRenderer();
	Body terrain;
	private BitmapFont font;
	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	private float fgParallax = 0;
	private float fgParallay = 0;
	private Music mp3Music;
	private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private ArrayList< com.badlogic.gdx.math.Rectangle> spriteBoxes = new ArrayList<com.badlogic.gdx.math.Rectangle>();
	private com.badlogic.gdx.math.Rectangle carBox;
	private Rectangle gas;
	private Rectangle brake;
	private Rectangle reverseIcon;
	private int fpScore = 0;
	private float tOD = 10000;
	private float colorChanger = 0;
	private int airTime = 0;
	private String currentStunt = "";
	private String currentStunt2 = "";
	private float initAngle = 0;
	private float flips = 0;
	private BitmapFont stuntFont;
	private boolean gassing = false;
	private boolean breaking = false;
	private boolean noFuel = false;
	private Rectangle pauseButton;
	public boolean showMenu = false;
	private Rectangle restartButton;
	private Game myGame;
	private boolean isCollided = false;
	private GestureDetector gd;
	private InputMultiplexer inputMultiplexer;
	public DSplash(Game game)
	{
	this.mygame = game;
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		if (!Gdx.input.isTouched(2)) {
			camera.position.set(
					car.getChassis().getPosition().x,
					car.getChassis().getPosition().y + .004f
							* Gdx.graphics.getHeight(), 0);
			camera.update();
		}
		handleInput();
		hudBatch.begin();
		hudBatch.draw(bgSprite1, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// hudBatch.draw(fgSprite2,fgParallax,(720-camera.unproject(new
		// Vector3(car.getChassis().getPosition(),0)).y)-600); //720/2-300
		hudBatch.draw(fgSprite2, fgParallax, fgParallay-100,fgSprite2.getWidth() * 1.5f, fgSprite2.getHeight() * 1.5f);
		hudBatch.draw(fgSprite3, fgParallax + (fgSprite2.getWidth() * 1.5f),fgParallay-100, fgSprite2.getWidth() * 1.5f,fgSprite2.getHeight() * 1.5f);
		carLight.setActive(false);
		carLight2.setActive(false);
		hudBatch.end();



		
	

		if (car != null) {
			float tScale = car.getChassis().getLinearVelocity().y / 50;
			if (camera.viewportHeight + tScale >= Gdx.graphics.getHeight() / 50
					&& camera.viewportHeight + tScale < 1900 / 50) {
				camera.viewportHeight += tScale;
				camera.viewportWidth = (camera.viewportHeight * (Gdx.graphics
						.getWidth() / 50f)) / (Gdx.graphics.getHeight() / 50f);

			}
			carBox.setPosition(car.getChassis().getPosition().x,car.getChassis().getPosition().y);  //used to be setposition
		}
		

		rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.updateAndRender();

		

		
		debugRenderer.render(world, camera.combined);
		update();
		

		// shapeDebugger.setProjectionMatrix(camera.combined);
		

		

		

		
	
		if (car.getChassis().getPosition().x > lastPointx - 23) {
			//totalTerrain++;
			int smooth = MathUtils.random(1);
			if (smooth == 0) {
				Vector2[] temp = new Vector2[tmp.length + 4];
				for (int i = 0; i < tmp.length; i++) {
					temp[i] = tmp[i];
				}
				lastPointx++;
				float xc = (float) Math
						.sqrt(16 - (tmp[tmp.length - 1].y * tmp[tmp.length - 1].y));
				for (int i = tmp.length; i < temp.length; i++) {

					Vector2 testPoint = new Vector2(lastPointx,
							(float) Math.sqrt(Math.abs(16 - (xc * xc))));

					if (Float.isNaN(testPoint.y)) {
						System.out.println(16 - (xc * xc));
						testPoint.y = temp[i - 1].y; // 3
						temp[i] = testPoint;

					} else {
						temp[i] = testPoint;
					}
					xc += 0.1f;
					lastPointx++;
				}

				tmp = temp;
				world.destroyBody(terrain);
				ChainShape groundShape = new ChainShape();
				groundShape.createChain(tmp);
				fixtureDef.shape = groundShape;
				fixtureDef.friction = .5f;
				fixtureDef.restitution = 0;
				BodyDef bodyDef = new BodyDef();
				terrain = world.createBody(bodyDef);
				ground = terrain.createFixture(fixtureDef);
				groundShape.dispose();
				lastPointx = tmp[tmp.length - 1].x;
				lastPointy = tmp[tmp.length - 1].y;
			} else if (smooth == 1) {
				// car.getChassis().setTransform(car.getChassis().getPosition().x,car.getChassis().getPosition().y
				// + 1.8f, 0);

				Vector2[] temp = new Vector2[tmp.length
						+ MathUtils.random(20, 40)];
				for (int i = 0; i < tmp.length; i++) {
					temp[i] = tmp[i];
					lastPointx = tmp[i].x;
				}
				lastPointx = lastPointx + 1;
				// float amplitude = MathUtils.random(2,(int)5);
				// lastPointx+=10;
				// Vector2 connector = new
				// Vector2(lastPointx,MathUtils.sin(lastPointx));
				// temp[tmp.length] = connector;
				int sinOrCos = MathUtils.random((int) 0, (int) 1);
				// sinOrCos = 1; //keep for debugging purposes
				amplitude = MathUtils.random(1, 5);

				System.out.println(amplitude + " ampplitude");
				if (sinOrCos == 1) {
					System.out.println("WHAT THE ACTUAL FUCK I HATE thIS SHIT");
					while (Math.abs(tmp[tmp.length - 1].y / amplitude) > 1) {
						amplitude = MathUtils.random(1, 5);
					}
					float j = (float) Math.acos(tmp[tmp.length - 1].y
							/ amplitude);
					j += 0.1f;
					Vector2 testPoint = new Vector2(-1, amplitude
							* ((float) MathUtils.cos(j)));

					System.out.println(tmp[tmp.length - 1].y / amplitude
							+ "this is the problem? " + j + "<--J"
							+ testPoint.y + "<---TESTPOINT");

					if (Math.abs(testPoint.y - tmp[tmp.length - 1].y)
							* Math.abs(testPoint.y - tmp[tmp.length - 1].y) <= (.005f * .005f)) {
						System.out
								.println("dslskjfksjslkjlsdjsfdfsjkdjlsjsdsfds");
						amplitude *= 1.45f;
						j += 0.2f;
					}
					// lastPointx++;

					for (int i = tmp.length; i < temp.length; i++) {
						// System.out.println(j);
						Vector2 siny = new Vector2(lastPointx, amplitude
								* ((float) MathUtils.cos(j)));

						temp[i] = siny;
						lastPointx++;
						j += 0.1f;
					}
				} else {
					// lastPointx++;
					while (Math.abs(tmp[tmp.length - 1].y / amplitude) > 1) {
						amplitude = MathUtils.random(1, 5);
					}
					float j = (float) Math.asin(tmp[tmp.length - 1].y
							/ amplitude);
					j += 0.1f;
					Vector2 testPoint = new Vector2(-1, amplitude
							* ((float) MathUtils.sin(j)));
					if (Math.abs(testPoint.y - tmp[tmp.length - 1].y)
							* Math.abs(testPoint.y - tmp[tmp.length - 1].y) <= (.005f * .005f)) {
						amplitude *= 1.45f;
						j += 0.2f;
						System.out
								.println("dslskjfksjslkjlsdjsfdfsjkdjlsjsdsfds");
					}

					// amplitude = MathUtils.random(2, 5);
					for (int i = tmp.length; i < temp.length; i++) {
						// System.out.println(j);
						Vector2 siny = new Vector2(lastPointx, amplitude
								* ((float) MathUtils.sin(j)));

						temp[i] = siny;
						lastPointx++;
						j += 0.1f;
					}
				}
				tmp = temp;

				world.destroyBody(terrain);
				ChainShape groundShape2 = new ChainShape();
				groundShape2.createChain(tmp);

				fixtureDef.shape = groundShape2;
				fixtureDef.friction = .5f;
				fixtureDef.restitution = 0;
				BodyDef bodyDef = new BodyDef();

				terrain = world.createBody(bodyDef);
			ground = terrain.createFixture(fixtureDef);
				groundShape2.dispose();
				lastPointx = tmp[tmp.length - 1].x;
				lastPointy = tmp[tmp.length - 1].y;
			//	putCoins();
			}

			// System.out.println(tmp[tmp.length - 1].y);
		}
		if (tmp.length >= 500) {
			Vector2[] temp = new Vector2[tmp.length - 300+50];  //used to be tmp.length-300
			for (int i = 50; i < temp.length; i++) { //used to be i = 0;
				temp[i] = tmp[250 + i]; //used to be 300+i
			}
			tmp = temp;
		}
		
		
	}

	private void handleInput() {
		if (Gdx.input.isTouched(1)) {
			
		} else if (Gdx.input.isTouched(0)) {
			//car.prevS();
		} else if (!Gdx.input.isTouched()) {
			car.prevKeyUp();
		} 
	}

	private void update() {
		if (car.getChassis().getLinearVelocity().x > 5
				&& car.getChassis().getLinearVelocity().x > 0) {
			fgParallax -= 0.3f;
		} else if (car.getChassis().getLinearVelocity().x < -5
				&& car.getChassis().getLinearVelocity().x < 0) {
			fgParallax += 0.3f;
		}
		if (car.getChassis().getLinearVelocity().y >= 1
				&& car.getChassis().getLinearVelocity().y > 0) {
			fgParallay -= 0.3f;
		} else if (car.getChassis().getLinearVelocity().y <= -1
				&& car.getChassis().getLinearVelocity().y < 0) {
			fgParallay += 0.3f;
		}
		if(inAir){
			calcAirTime();
			
			}
			float currentAngle = MathUtils.radiansToDegrees*car.getChassis().getAngle();
			 if(Math.abs(currentAngle-initAngle)>300)
			 {
				 flips++;
				 initAngle= MathUtils.radiansToDegrees*car.getChassis().getAngle();
				 currentStunt2= "Flip: " + flips+"x";
				 
			 }
			 if(airTime>100 && inAir)
			 {
				 coolText(currentStunt);
				 if(car.jumpjuice<=95){
						car.jumpjuice+=airTime/120;
						}
			 }
			 if(flips>0)
			 {
				
				coolText(currentStunt2);
				if(car.fuel<=95){
				car.fuel+=(flips)*.01f;
				}
			 }
	
	
	//System.out.println(car.fuel);
	if(car.fuel<0)
	{
		currentStunt = "";
		currentStunt2 = "";
		noFuel  = true;
		stuntFont.setColor(Color.RED);
		hudBatch.begin();
		 menuPlace.setScale(4,16);
			
			menuPlace.setPosition(Gdx.graphics.getWidth()/2-menuPlace.getWidth()/2,Gdx.graphics.getHeight()/2-menuPlace.getHeight()/2);
			restart.setPosition(menuPlace.getX(), menuPlace.getY());
			if(sCounter2<.95)
			{
				sCounter2+=0.01f;
			}
			else
			{
				sCounter2 = 1;
			}
			menuPlace.setAlpha(sCounter2);
			menuPlace.draw(hudBatch);
			restart.draw(hudBatch);
		sCounter1+=0.1f;
		stuntFont.setScale(8*MathUtils.sin(sCounter1));
		stuntFont.draw(hudBatch, "SMOKED OUT!", Gdx.graphics.getWidth()/2-stuntFont.getBounds("SMOKED OUT!").width/2, Gdx.graphics.getHeight()/2);
		if(stuntFont.getScaleX()<0)
		{
			sCounter1 = 0;
		}
		hudBatch.end();
		
	}
	else
	{
		if(gassing)
		{
		car.fuel-=(float)(Math.abs(car.getChassis().getLinearVelocity().x)*.00791f)+.1f;
		}
	}
			 
    if(!Gdx.input.isTouched())
    {
    	if(gassing)
    	{
	gasPedal.setTexture(new Texture("gasUp.png"));
	gassing = false;
    	}
    	if(breaking)
    	{
	brakePedal.setTexture(new Texture("brakeUp.png"));
	breaking = false;
    	}
    }
    if(showMenu ==  true)
    {
		 hudBatch.begin();
		 noFuel = true;
		 menuPlace.setScale(4,16);
			
			menuPlace.setPosition(Gdx.graphics.getWidth()/2-menuPlace.getWidth()/2,Gdx.graphics.getHeight()/2-menuPlace.getHeight()/2);
			restart.setPosition(menuPlace.getX(), menuPlace.getY());
			if(sCounter2<.95)
			{
				sCounter2+=0.01f;
			}
			else
			{
				sCounter2 = 1;
			}
			menuPlace.setAlpha(sCounter2);
			menuPlace.draw(hudBatch);
			restart.draw(hudBatch);
			hudBatch.end();
    }
    	
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void resize(int width, int height) {
		
				
		camera.viewportWidth = width / 50; // to make things simpler i'm
													// making everything
													// constant. I've had to
													// guess too much with the
													// previous stuff
		// previously = width/25 etc.
		camera.viewportHeight =  height / 50;

	}

	@Override
	public void show() {
		world = new World(new Vector2(0, -14.81f), true);
		camera = new OrthographicCamera();
		debugRenderer = new Box2DDebugRenderer();
		shapeRenderer = new ShapeRenderer();
		rayHandler = new RayHandler(world);
		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		// camera2 = new OrthographicCamera();
		// camera2.viewportWidth = Gdx.graphics.getWidth()/50;
		// camera2.viewportWidth = Gdx.graphics.getHeight()/50;
		// +camera2.update();
		//mp3Music = Gdx.audio.newMusic(Gdx.files.internal("Cipher2.mp3"));
		//mp3Music.play();
		createCollisionListener();
		// splash.scale(.005f);
		coin.setPosition(320, Gdx.graphics.getHeight()-coin.getHeight()-30);
		fuel.setScale(2.5f);
		fuel.setPosition(1200, 24+1);
		// debugRenderer.setDrawBodies(false);

		BodyDef bodyDef = new BodyDef();

		// car
		fixtureDef.density = 5;
		fixtureDef.friction = .4f;
		fixtureDef.restitution = .3f;

		wheelFixtureDef.density = fixtureDef.density * 1.5f;
		wheelFixtureDef.friction = 50;
		wheelFixtureDef.restitution = .4f;

		bodyDef.type = BodyType.DynamicBody;
		CircleShape wheelShape2 = new CircleShape();
		wheelShape2.setRadius(1);
		wheelFixtureDef.shape = wheelShape2;
		leftWheel2 = world.createBody(bodyDef);
		leftWheel2.createFixture(wheelFixtureDef);
		leftWheel2.setTransform(-2, 15, 360);

		car = new DroidCar(world, fixtureDef, wheelFixtureDef, 0, 3, 5, 1.25f,
				1, true); // 1

		carLight = new PointLight(rayHandler, 1000, Color.CYAN, 0, 30, 40); // 30,30

		headLight = new ConeLight(rayHandler, 1000, Color.BLUE, 20, 30, 800,
				20, 20); // these numbers don't matter cause it's attached to
							// the chassis.
		headLight.attachToBody(car.getChassis());
		headLight.setXray(false);
		carLight2 = new PointLight(rayHandler, 1000, Color.YELLOW, 0, car
				.getChassis().getPosition().x,
				car.getChassis().getPosition().y + 5);
		carLight2.setActive(false);

		// GROUND
		// body definition
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, 0);

		// ground shape
		ChainShape groundShape = new ChainShape();
		tmp = new Vector2[100];
		float j = 0;
		for (int i = 0; i < 50; i++) {

			Vector2 temp = new Vector2(i, 3 * (float) Math.sin((double) j));
			tmp[i] = temp;
			j += 0.1f;
		}
		System.out.println(j);
		j = (float) Math.acos(tmp[49].y / 3);
		System.out.println(j);
		for (int i = 50; i < 100; i++) {
			Vector2 temp = new Vector2(i, 3 * (float) Math.cos((double) j));
			tmp[i] = temp;
			j += 0.1f;
		}
		System.out.println(Math.acos(tmp[99].y / 3) + "dsfjlksfjlk");
		lastPointx = tmp[tmp.length - 1].x;
		lastPointy = tmp[tmp.length - 1].y;
		groundShape.createChain(tmp);
		fixtureDef.shape = groundShape;
		fixtureDef.friction = .5f;
		fixtureDef.restitution = 0;
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape.dispose();
		groundShape = new ChainShape();
		groundShape.createChain(new Vector2[] { new Vector2(-5, 10),
				new Vector2(-5, 0), new Vector2(1, 0) });
		fixtureDef.shape = groundShape;
		fixtureDef.friction = .5f;
		fixtureDef.restitution = 0;
		terrain = world.createBody(bodyDef);
		ground = terrain.createFixture(fixtureDef);
		// world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape.dispose();

		font = new BitmapFont();
		stuntFont = new BitmapFont();
		font.setColor(Color.CYAN);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				Gdx.files.internal("font.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		font = generator.generateFont(parameter); // font size 12 pixels
		stuntFont =  generator.generateFont(parameter);
		generator.dispose();
		carBox = new  com.badlogic.gdx.math.Rectangle(car.getChassis().getPosition().x - 2, car.getChassis().getPosition().y + 3, 3, 12);
		Vector3 tempCoords = camera.unproject(new Vector3(10, 50, 0));

		
		gasPedal.setPosition(100,0);
		brakePedal.setPosition(Gdx.graphics.getWidth()-100-brakePedal.getWidth(),0);
		pause.setPosition(Gdx.graphics.getWidth()-pause.getWidth()-10,Gdx.graphics.getHeight()-pause.getHeight()-10);
		reverseIcon = new Rectangle(0,100,reverseIco.getWidth()*3,reverseIco.getHeight()*3);
		pauseButton = new Rectangle(pause.getX(),10,pause.getWidth()*2,pause.getHeight()*2);
	//	gas = new Rectangle(camera.unproject(new Vector3(100, 0, 0)).x,camera.unproject(new Vector3(0, 1080, 0)).y, 10,10);
		restart.setScale(3);
		restartButton = new Rectangle(500,500,restart.getWidth()*3,restart.getHeight()*3);
		gas = new Rectangle(gasPedal.getX(),Gdx.graphics.getHeight()-gasPedal.getY()-gasPedal.getHeight(),gasPedal.getWidth(),gasPedal.getHeight());
		brake = new Rectangle(brakePedal.getX(),Gdx.graphics.getHeight()-brakePedal.getY()-brakePedal.getHeight(),brakePedal.getWidth(),brakePedal.getHeight());
		// camera.unproject(new
		// Vector3(car.getChassis().getPosition(),0)).x,camera.unproject(new
		// Vector3(car.getChassis().getPosition(),0)).y
	//	putCoins();

		gd = new GestureDetector(this);
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(gd);
		inputMultiplexer.addProcessor(new SimpleDirectionGestureDetector(
				new SimpleDirectionGestureDetector.DirectionListener() {

					@Override
					public void onUp() {
						if (car.jumpjuice >= 50 && !noFuel) {
							car.chassis.applyLinearImpulse(new Vector2(0, 300),
									car.getChassis().getPosition(), true);
							car.jumpjuice -= 50;
						}
					}

					@Override
					public void onRight() {
						car.prevA();
					}

					@Override
					public void onLeft() {
						car.prevD();
					}

					@Override
					public void onDown() {

					}
				}));
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
		rayHandler.dispose();
		
	}

	public void putCoins() {
		Sprite coin1 = new Sprite(splash.getTexture());
		Sprite coin2 = new Sprite(splash.getTexture());
		Sprite fuel3 = new Sprite(fuel.getTexture());
		int temp = sprites.size();
		sprites.add(coin1);
		sprites.add(coin2);
		int random = MathUtils.random(0,5);
		if(random==3){
		sprites.add(fuel3); } 
		// sprites.add(splash);
		for (int i = temp; i < sprites.size(); i++) {
			int index = MathUtils.random(tmp.length - 20, tmp.length - 1);
			Vector2 doop = tmp[index];
			sprites.get(i).setPosition(doop.x, doop.y);
			sprites.get(i).setSize(1, 1);
//using both rectangles
			 com.badlogic.gdx.math.Rectangle delp = new  com.badlogic.gdx.math.Rectangle(doop.x, doop.y, sprites.get(i)
					.getWidth(), sprites.get(i).getHeight());
			spriteBoxes.add(delp);

		}
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		//if(gas.contains(camera.unproject(new Vector3(Gdx.input.getX(), 0, 0)).x,camera.unproject(new Vector3(0, Gdx.input.getY(), 0)).y))
		if(gas.contains(x,y) && Gdx.input.isTouched() && !noFuel)
		{
			gassing = true;
			gasPedal.setTexture(new Texture("gasDown.png"));
			car.prevW();
		}
		else if(brake.contains(x,y) && Gdx.input.isTouched() && !noFuel)
		{
			breaking  = true;
			brakePedal.setTexture(new Texture("brakeDown.png"));
			car.prevS();
			
		}
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		if (reverseIcon.contains(x,y) && !noFuel) {
			if(direction==1){
			reverseIco.setTexture(new Texture("back.png"));}
			else
			{
				reverseIco.setTexture(new Texture("forward.png"));
			}
			float tempX = car.getChassis().getPosition().x;
			float tempY = car.getChassis().getPosition().y;
			world.destroyBody(car.getChassis());
			world.destroyBody(car.leftWheel);
			world.destroyBody(car.rightWheel);
			car.getChassis().setUserData(null);
			direction *= -1;
			car = null;

			car = new DroidCar(world, fixtureDef, wheelFixtureDef, tempX,
					tempY, 5, 1.25f, direction, false);
			headLight.remove();
			headLight = new ConeLight(rayHandler, 1000, Color.BLUE, 20, 30,
					800, 180, 20); // these numbers don't matter cause it's
									// attached to the chassis.
			System.out.println((direction + 1) * .5f * 180f);
			headLight.attachToBody(car.getChassis(), 0, 0,
					(((direction * -1 + 1) * .5f * 180f)));
			headLight.setXray(true);

		}
		else if(pauseButton.contains(x,y) && !noFuel) {
			showMenu = true;
		}
		else if(restartButton.contains(x,y) && showMenu) {
			
		//((Game) Gdx.app.getApplicationListener()).setScreen(new DroidMenuScreen(mygame));
		mygame.setScreen(new FreePlay(mygame));
		  
		//	tmp = new Vector2[2];
					
		}
	     
		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		
		return false;
		
	
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {

		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {

		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {

		return false;

	}
	 private void createCollisionListener() {
	        world.setContactListener(new ContactListener() {

	            @Override
	            public void beginContact(Contact contact) {
	                Fixture fixtureA = contact.getFixtureA();
	                Fixture fixtureB = contact.getFixtureB();
	                Gdx.app.log("beginContact", "between " + fixtureA.getBody().getPosition().x + " and " + fixtureB.getBody().getPosition().x);
	                if((fixtureB.equals(ground) && fixtureA.equals(car.rWheel)) ||(fixtureB.equals(car.rWheel) && fixtureA.equals(ground)) || (fixtureB.equals(car.lWheel) && fixtureA.equals(ground)) || (fixtureB.equals(ground) && fixtureA.equals(car.lWheel)))
	                {
	                	
	                	stuntFont.setScale(1);
	                		inAir = false;
	                	
	                	airTime = 0;
	                	initAngle = MathUtils.radiansToDegrees*car.getChassis().getAngle();
	                	flips = 0;
	                	
	                }
	                if((fixtureB.equals(car.bodyF) || fixtureA.equals(car.bodyF))  && car.getChassis().getPosition().y<car.leftWheel.getPosition().y && car.getChassis().getPosition().y<car.rightWheel.getPosition().y)
	                		{
	                	inAir = false;
	                	airTime = 0;
	                	flips = 0;
	                	car.fuel-=10;
	                		}
	            }

	            @Override
	            public void endContact(Contact contact) {
	                Fixture fixtureA = contact.getFixtureA();
	                Fixture fixtureB = contact.getFixtureB();
	             //   Gdx.app.log("endContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
	                if((fixtureB.equals(ground) && fixtureA.equals(car.rWheel)) ||(fixtureB.equals(car.rWheel) && fixtureA.equals(ground)) || (fixtureB.equals(car.lWheel) && fixtureA.equals(ground)) || (fixtureB.equals(ground) && fixtureA.equals(car.lWheel)))
	                {
	                	if(car.getChassis().getLinearVelocity().y>2)
	                	{
	                		//airTime++;
	                		//if(airTime>1 )
	                		//{
	                			inAir = true;
	                		//}
	                	}
	                }
	                
	                
	            }

	            @Override
	            public void preSolve(Contact contact, Manifold oldManifold) {
	            }

	            @Override
	            public void postSolve(Contact contact, ContactImpulse impulse) {
	            }

	        });
	    }
	 public void calcAirTime()
	 {
		 
		 
		 airTime++;
		 if(airTime>9)
		 {
			 currentStunt = "BIG AIR! +" + airTime;
		 }
		 
		 
	 }
	 public void coolText(String text)
	 {
		 	if(stuntFont.getScaleX()<10){
			 stuntFont.setScale(stuntFont.getScaleX()+.1f,stuntFont.getScaleY()+.1f);
		 	}
		 	else if(stuntFont.getScaleX()>10)
		 	{
		 		stuntFont.setScale(1);
		 	}
			 hudBatch.begin();
			 if(text.equals(currentStunt))
			 {
				 stuntFont.setColor(Color.WHITE);
			 stuntFont.draw(hudBatch,text, Gdx.graphics.getWidth()/2-100,Gdx.graphics.getHeight()/2);
			 }
			 else
			 {
				 stuntFont.setColor(Color.RED);
				 stuntFont.draw(hudBatch,text, Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2+100);
			 }
			 hudBatch.end();
		 
		 
	 }
	 public void setupMenu()
	 {

	 }
	@Override
	public void create() {
		// TODO Auto-generated method stub
	}

}