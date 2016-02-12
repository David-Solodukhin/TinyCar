package com.yoyo.dookin;

import java.util.ArrayList;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
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
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
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
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.Array;

public class Splash implements Screen {
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private SpriteBatch batch;
	private SpriteBatch hudBatch;
	Sprite bgSprite1 = new Sprite(new Texture("bg.png"));
	Sprite fgSprite2 = new Sprite(new Texture("foreground3.png"));
	private OrthographicCamera camera;
	float lastPointx = 0;
	float lastPointy = 0;
	Body leftWheel2;
	float direction = 1;
	Vector2[] tmp;
	int amplitude = 3;
	float height = 720;
	private boolean inAir= false;
	FixtureDef fixtureDef = new FixtureDef(),
			wheelFixtureDef = new FixtureDef();
	Fixture ground;
	Sprite splash = new Sprite(new Texture("coin.png"));
	private final float TIMESTEP = 1 / 60f;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
	public Car car;
	private Array<Body> tmpBodies = new Array<Body>();
	RayHandler rayHandler;
	RayHandler rayHandler2;
	PointLight carLight;
	PointLight carLight2;
	ConeLight headLight;
	ShapeRenderer shapeDebugger = new ShapeRenderer();
	Body terrain;
	private BitmapFont font;
	private int totalTerrain = 0;
	private ShapeRenderer shapeRenderer;
	private float fgParallax = 0;
	private float fgParallay = 0;
	private Music mp3Music;
	private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private ArrayList<Rectangle> spriteBoxes = new ArrayList<Rectangle>();
	private Rectangle carBox;
	private float airTime = 0;
	private int fpScore = 0;
	private float tOD = 10000;
	private float colorChanger = 0;
	private String currentStunt = "";
	private String currentStunt2 = "";
	private float initAngle = 0;
	private float flips = 0;
	private float scaleFactor;
	private Body testPolygonShape2;
	private Body testPolygonShape1;
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		if(!Gdx.input.isButtonPressed(0)){
			camera.position.set(car.getChassis().getPosition().x, car.getChassis()
					.getPosition().y+6, 0);
			camera.update();
			}
		handleInput();
		hudBatch.begin();
		hudBatch.draw(bgSprite1,0,0);
		
		//hudBatch.draw(fgSprite2,fgParallax,(720-camera.unproject(new Vector3(car.getChassis().getPosition(),0)).y)-600);  //720/2-300
		hudBatch.draw(fgSprite2,fgParallax,fgParallay);
		hudBatch.end();
		
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(int i = 0;i<sprites.size();i++)
			{
				sprites.get(i).draw(batch);
			}
		batch.end();
		
		if(carLight.getDistance() <100)
		{
			carLight.setDistance(carLight.getDistance()+1);
			
		}
			if(tmp.length!=0){
				shapeRenderer.setProjectionMatrix(camera.combined); 
				 shapeRenderer.begin(ShapeType.Line);
				 
		for(int i = 0;i<tmp.length-1;i++)
		{
		 shapeRenderer.setColor(1, 1, 0, 1);
		 shapeRenderer.line(tmp[i].x, tmp[i].y,tmp[i+1].x,tmp[i+1].y );
		}
	 shapeRenderer.end();
		
			}
			
		if(car!=null){
		float tScale = car.getChassis().getLinearVelocity().y/25;
		if(camera.viewportHeight+tScale>=720/25 && camera.viewportHeight+tScale<1200/25){
		camera.viewportHeight+=tScale;
		camera.viewportWidth = (camera.viewportHeight*(1280f/25f))/(720f/25f);
		
		}
		if((int)car.getChassis().getPosition().y==0)
		{
		//	camera.viewportHeight = 720/25;
		//	camera.viewportWidth = 1280/25;
		}
		carBox.setPosition(car.getChassis().getPosition());}
		for(int i = 0;i<spriteBoxes.size();i++)
		{
		
		if(Intersector.overlaps(carBox, spriteBoxes.get(i)))
		{
			sprites.remove(i);
			spriteBoxes.remove(i);
			System.out.println("intersected");
			fpScore+=10;
			car.getChassis().applyLinearImpulse(new Vector2(190*car.direction,0), car.getChassis().getPosition(), true);
			break;
		}
		}
		
		
		
		rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.updateAndRender();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		world.getBodies(tmpBodies);
		for (Body body : tmpBodies)
			if (body.getUserData() instanceof Sprite) {
				Sprite sprite = (Sprite) body.getUserData();
				sprite.setPosition(
						
						body.getPosition().x - sprite.getWidth() / 2,
						body.getPosition().y - sprite.getHeight() / 2);
				sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
				sprite.draw(batch);
				
			}
		debugRenderer.setDrawJoints(false);
		font.setScale(0.1f);
		font.draw(batch, "START", 10, 10);
		font.setScale(1);
		batch.end();
	    debugRenderer.render(world, camera.combined);
	   // hudBatch.begin();
	//	hudBatch.draw(fgSprite2,0,720/2);
		//hudBatch.end();
		
		
		update();
		tOD+=0.1f;
		if(tOD<=10)
		{
			
		}
		else if(tOD>100 && tOD<250)
		{
			
			carLight2.setColor(1f/colorChanger,1f/colorChanger,colorChanger,1);
			colorChanger+=0.002f;
		}
		else if(tOD>=250 && tOD<500)
		{
			carLight2.setDistance(carLight2.getDistance()-0.1f);
		}
		else if(tOD>=500 && tOD<10000)
		{
			tOD =0;
			colorChanger=0;
		}
		
		
		
		
		//shapeDebugger.setProjectionMatrix(camera.combined);
		Color doop = new Color(Color.CYAN);
		
		shapeDebugger.setColor(doop);
		shapeDebugger.begin(ShapeType.Filled);
		shapeDebugger.rect(10,10,car.jumpjuice*2, 10*2);
		shapeDebugger.setColor(Color.TEAL);
		shapeDebugger.rect(10,50,car.jumpjuice*2, 10*2);
		
		shapeDebugger.end();
		
		
	
		hudBatch.begin();
		font.draw(hudBatch, "Free Play: Meters Driven: " + (int)car.getChassis().getPosition().x/25, 0, Gdx.graphics.getHeight()-23);
		font.draw(hudBatch, (int)((car.getChassis().getLinearVelocity().x*3600f)/1000f)+"K/H", 0, Gdx.graphics.getHeight()-53);
		font.draw(hudBatch, "Terrain bits generated: " + totalTerrain, Gdx.graphics.getWidth()-310, Gdx.graphics.getHeight()-23);  //camera.viewportHeight+680
		font.draw(hudBatch, "All Wheel Drive: " + car.IsallWheelDrive(), Gdx.graphics.getWidth()-310, Gdx.graphics.getHeight()-53);
		font.draw(hudBatch, "JumpJuice: " + (int)car.jumpjuice, 10, 50);
		font.draw(hudBatch, "Coins: " + fpScore, 10, 90);
		font.setColor(Color.WHITE);
		font.draw(hudBatch, "Stunt Done: " + currentStunt,  (Gdx.graphics.getWidth()/2-font.getBounds("Stunt Done: "+currentStunt).width/2), 60);
		font.draw(hudBatch, "Secondary Stunt Done: " + currentStunt2,  (Gdx.graphics.getWidth()/2-font.getBounds("Secondary Stunt Done: "+currentStunt2).width/2), 40);
		font.setColor(Color.WHITE);
		hudBatch.end();
		camera.update();
		
		if (car.jumpjuice < 100) {
			car.jumpjuice += 0.09f;
		}
		if(carLight2.getDistance()<=10)
		{
			headLight.setDistance(20.0f);
		}
		if (car.getChassis().getPosition().x > 130) {
			headLight.setDistance(headLight.getDistance()-1); // TODO: Day night cycles with the lights
			if(Gdx.graphics.getHeight()==720){
		//	carLight2.setPosition(car.getChassis().getPosition().x+10,car.getChassis().getPosition().y+18);
				carLight2.setPosition(camera.unproject(new Vector3(875,0,0)).x,camera.unproject(new Vector3(0,22,0)).y);
			}
			else
			{
				carLight2.setPosition(car.getChassis().getPosition().x+15,car.getChassis().getPosition().y+22);
				
			}
			
			//carLight2.setPosition(camera.unproject(new Vector3(875,0,0)).x,camera.unproject(new Vector3(0,22,0)).y);
			
			//+10 for x, +18 for y
			
			if(carLight2.getDistance()<40 && (tOD>500 || (int)tOD==0))
			{
		//		carLight2.setColor(Color.ORANGE);
		//		carLight2.setActive(true);
		//		carLight2.setDistance(carLight2.getDistance()+1);
		//		tOD=0;
			}
			
			
		}
		if (car.getChassis().getPosition().x > lastPointx - 30) {
			totalTerrain++;
			int smooth = MathUtils.random(1);
			if (smooth == 0) {
				Vector2[] temp = new Vector2[tmp.length + 4];
				for (int i = 0; i < tmp.length; i++) {
					temp[i] = tmp[i];
				}
				lastPointx++;
				float xc = (float) Math
						.sqrt(16- (tmp[tmp.length - 1].y * tmp[tmp.length - 1].y));
				for (int i = tmp.length; i < temp.length; i++) {

					Vector2 testPoint = new Vector2(lastPointx,
							(float) Math.sqrt(Math.abs(16 - (xc * xc))));
					
					if (Float.isNaN(testPoint.y)) {
						System.out.println(16-(xc*xc));
						testPoint.y = temp[i-1].y;  //3
						temp[i]=testPoint;
						

					} else {
						temp[i] = testPoint;
					}
					xc+=0.1f;
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
							* ((float) Math.cos(j)));

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
								* ((float) Math.cos(j)));

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
							* ((float) Math.sin(j)));
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
								* ((float) Math.sin(j)));

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
				//ground = terrain.createFixture(fixtureDef);
				groundShape2.dispose();
				lastPointx = tmp[tmp.length - 1].x;
				lastPointy = tmp[tmp.length - 1].y;
				putCoins();
			}
			

			// System.out.println(tmp[tmp.length - 1].y);
		}
		if(tmp.length>=500)
		{
			Vector2[] temp = new Vector2[tmp.length-300];
			for(int i = 0;i<temp.length;i++)
			{
				temp[i]=tmp[300+i];
			}
			tmp = temp;
		}
		if (Gdx.input.isButtonPressed(0)) {
			// System.out.println(Gdx.input.getX()/25 + " " +
			// camera.position.x);
			Vector3 worldCoordinates = new Vector3(Gdx.input.getX(),
					Gdx.input.getY(), 0);
			worldCoordinates = camera.unproject(worldCoordinates);
			// System.out.println(camera.unproject(worldCoordinates).y);
			// System.out.println(Gdx.input.getX()-(camera.viewportWidth+camera.position.x));
			car.getChassis().setTransform(worldCoordinates.x, worldCoordinates.y, 0);
			//leftWheel2.setAwake(false);
			//leftWheel2.setLinearVelocity(0, 0);
			Vector2 tempCoords = new Vector2(car.getChassis().getPosition());
			//camera.position.set(0, 0,0);
		//	camera.update();
			car.getChassis().setLinearVelocity(Gdx.input.getDeltaX()/5,-Gdx.input.getDeltaY()/5);
		}
		if (car.rightWheel.getPosition().y + 2 < car.getChassis().getPosition().y) {
			// car.rightWheel.setTransform(car.rightWheel.getPosition().x,
			// car.rightWheel.getPosition().y+5,0);
		}
		if (Gdx.input.isKeyJustPressed(Keys.Z)) {
			float tempX = car.getChassis().getPosition().x;
			float tempY = car.getChassis().getPosition().y;
			world.destroyBody(car.getChassis());
			car.getChassis().setUserData(null);
			direction *= -1;
			car = null;
			
			car = new Car(world, fixtureDef, wheelFixtureDef, tempX, tempY, 5, 1.25f,
					direction,false);
			headLight.remove();
			headLight = new ConeLight(rayHandler,5000,Color.BLUE,20,30,800,180,20); //these numbers don't matter cause it's attached to the chassis.
			System.out.println((direction+1)*.5f*180f);
			headLight.attachToBody(car.getChassis(),0,0,(((direction*-1+1)*.5f*180f)));
			headLight.setXray(true);
			Gdx.input.setInputProcessor(new InputMultiplexer(
					new InputAdapter() { // to be used with overriden keydown
											// methods in car.

						@Override
						public boolean keyDown(int keycode) {
							switch (keycode) {
							case Keys.R:
								((Game) Gdx.app.getApplicationListener())
										.setScreen(new Splash());
								mp3Music.dispose();
								break;

							// this is so dumb, i hate libgdx.
							}

							return false;
						}

						@Override
						public boolean scrolled(int amount) {
							camera.zoom += amount / 25f;
							return true;
						}

					}, car));

		}
	}

	private void handleInput() {
	if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
	{
		Gdx.app.exit();
		
		
	}
	
	}
	private void update()
	{
		if(car.getChassis().getLinearVelocity().x>5 && car.getChassis().getLinearVelocity().x>0)
		{
			fgParallax-=0.1f;
		}
		else if(car.getChassis().getLinearVelocity().x<-5 && car.getChassis().getLinearVelocity().x<0)
		{
			fgParallax+=0.1f;
		}
		if(car.getChassis().getLinearVelocity().y>=1 && car.getChassis().getLinearVelocity().y>0)
		{
			fgParallay-=0.1f;
		}
		else if(car.getChassis().getLinearVelocity().y<=-1 && car.getChassis().getLinearVelocity().y<0)
		{
			fgParallay+=0.1f;
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
		
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = (int)width/25;  //to make things simpler i'm making everything constant. I've had to guess too much with the previous stuff
		//previously = width/25 etc.
		camera.viewportHeight = (int) height/25;
		
	}

	@Override
	public void show() {
		world = new World(new Vector2(0, -9.81f), true);
		camera = new OrthographicCamera();
		debugRenderer = new Box2DDebugRenderer();
		shapeRenderer = new ShapeRenderer();
		rayHandler = new RayHandler(world);
		rayHandler2 = new RayHandler(world);
		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
	//	mp3Music = Gdx.audio.newMusic(Gdx.files.internal("cipher2.mp3"));
	//	mp3Music.play();
		createCollisionListener();
		//splash.scale(.005f);
		
	//	debugRenderer.setDrawBodies(false);
		
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

		car = new Car(world, fixtureDef, wheelFixtureDef, 0, 3, 5, 1.25f, 1,true); // 1
		
		carLight = new PointLight(rayHandler, 1000, Color.CYAN, 0, 30,40);  //30,30
		
		headLight = new ConeLight(rayHandler,1000,Color.BLUE,20,30,800,20,20); //these numbers don't matter cause it's attached to the chassis.
		headLight.attachToBody(car.getChassis());
		headLight.setXray(false);
		carLight2 = new PointLight(rayHandler, 1000, Color.YELLOW, 0, car
				.getChassis().getPosition().x,
				car.getChassis().getPosition().y + 5);
		carLight2.setActive(false);
		Gdx.input.setInputProcessor(new InputMultiplexer(new InputAdapter() {

			@Override
			public boolean keyDown(int keycode) {
				switch (keycode) {
				case Keys.R:
					mp3Music.dispose();
					((Game) Gdx.app.getApplicationListener())
							.setScreen(new Splash());
					break;

				}

				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				camera.zoom += amount / 25f;
				return true;
			}

		}, car));

		// GROUND
		// body definition
		bodyDef.type = BodyType.StaticBody;
		
	
		
		// ground shape
		ChainShape groundShape = new ChainShape();
		//PolygonShape groundShape = new PolygonShape();
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
		Vector2[] tmep = new Vector2[tmp.length];
		/*for(int i = tmp.length-1;i>=0;i--)
		{
			tmep[tmp.length-i-1] = tmp[i];
		}
		tmp = tmep;
		ArrayList<Vector2[]> terrainSplittr = new ArrayList<Vector2[]>();
		for(int x = 0;x<tmp.length-5;x++)
		{
			if(x==0 || x%5==0) 
			{
				Vector2[] test = new Vector2[6];
				test[0] = tmp[x];
				test[1] = tmp[x+1];
				test[2] = tmp[x+2];
				test[3] = tmp[x+3];
				test[4] = tmp[x+4];
				test[5] = tmp[x+5];
				terrainSplittr.add(test);
			}
		}
		
		for(int x = 0;x<terrainSplittr.size();x++)
		{
			groundShape.set(terrainSplittr.get(x));
			fixtureDef.shape = groundShape;
			fixtureDef.friction = .5f;
			fixtureDef.restitution = 0;
			
			if(x==terrainSplittr.size()-2){
			 testPolygonShape1 = world.createBody(bodyDef);
			  testPolygonShape1.createFixture(fixtureDef);
			
			}
			else if(x==terrainSplittr.size()-1)
			{
				testPolygonShape2 = world.createBody(bodyDef);
			    testPolygonShape2.createFixture(fixtureDef);
			}
			else
			{
				world.createBody(bodyDef).createFixture(fixtureDef);
			}
			//groundShape.dispose();
		}
		System.out.println("sdjfkjdfkfdjkdfjkfjdfjdkj");
		bodyDef.position.set(0, 0);
		WeldJointDef axisDef = new WeldJointDef();
		testPolygonShape1.setType(BodyType.DynamicBody);
		testPolygonShape2.setType(BodyType.DynamicBody);
	//	testPolygonShape1.setTransform(car.getChassis().getPosition().x,car.getChassis().getPosition().y+5, 0);
		//car.getChassis().setTransform(testPolygonShape1.getPosition(), 0);
		axisDef.bodyA = testPolygonShape1;
		leftWheel2.setTransform(0, 0,42);
		axisDef.bodyB = testPolygonShape2;
		leftWheel2.setTransform(0, 0,19);
		axisDef.localAnchorA.set(testPolygonShape1.getLocalPoint(testPolygonShape2.getPosition()));
		axisDef.localAnchorB.set(testPolygonShape2.getLocalPoint(testPolygonShape1.getPosition()));
	//	world.createJoint(axisDef);
		
		testPolygonShape1.createFixture(testPolygonShape2.getFixtureList().get(0).getShape(),1);
		world.destroyBody(testPolygonShape2);
		
		
		/*
		 * if isSquare then do the 'special polygon'
		 * once drawPoints is done(where putcoins is)
		 */
		
		
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
		terrain.createFixture(fixtureDef);
		 world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape.dispose();

		font = new BitmapFont();
		font.setColor(Color.CYAN);
	//	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
	//	FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	//	parameter.size = 12;
	//	font = generator.generateFont(parameter); // font size 12 pixels
	//	generator.dispose();
		
		
		 
		carBox = new Rectangle(car.getChassis().getPosition().x-2,car.getChassis().getPosition().y+3,3,12);
		//putCoins();
	}

	@Override
	public void hide() {
		dispose();
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
		Gdx.app.exit();
	}
	public void putCoins()
	{
		Sprite coin1 = new Sprite(splash.getTexture());
		Sprite coin2 = new Sprite(splash.getTexture());
		
		int temp = sprites.size();
		sprites.add(coin1);
		sprites.add(coin2);
		//sprites.add(splash);
		for(int i = temp;i<sprites.size();i++)
		{
			int index = MathUtils.random(tmp.length-20,tmp.length-1);
			Vector2 doop =tmp[index];
			sprites.get(i).setPosition(doop.x,doop.y);
			sprites.get(i).setSize(1,1);
			
			Rectangle delp = new Rectangle(doop.x,doop.y,sprites.get(i).getWidth(),sprites.get(i).getHeight());
			spriteBoxes.add(delp);
			
		}
	}
	 private void createCollisionListener() {
	        world.setContactListener(new ContactListener() {

	            @Override
	            public void beginContact(Contact contact) {
	                Fixture fixtureA = contact.getFixtureA();
	                Fixture fixtureB = contact.getFixtureB();
	             //   Gdx.app.log("beginContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
	                if((fixtureB.equals(ground) && fixtureA.equals(car.rWheel)) ||(fixtureB.equals(car.rWheel) && fixtureA.equals(ground)) || (fixtureB.equals(car.lWheel) && fixtureA.equals(ground)) || (fixtureB.equals(ground) && fixtureA.equals(car.lWheel)) )
	                {
	                	
	                	
	                		inAir = false;
	                	
	                	airTime = 0;
	                	initAngle = MathUtils.radiansToDegrees*car.getChassis().getAngle();
	                	flips = 0;
	                	
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
			 currentStunt = "AirTime: " + airTime;
		 }
		 
		 
	 }

}