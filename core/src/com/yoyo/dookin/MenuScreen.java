package com.yoyo.dookin;


import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class MenuScreen implements Screen{
	private BitmapFont font;
	private BitmapFont font2;
	private SpriteBatch menu;
	Skin skin;
	Stage stage;
	Car car;
	SpriteBatch batch;
	String[] menuItems;
	private World world;
	RayHandler rayHandler;
	private final float TIMESTEP = 1 / 60f;

	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;

	private Box2DDebugRenderer debugRenderer;
	//public FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	private OrthographicCamera camera = new OrthographicCamera(1280,720);
	private int currentItem = 0;
	FixtureDef fixtureDef = new FixtureDef(),
			wheelFixtureDef = new FixtureDef();
	private SpriteBatch hudBatch;
	private PointLight moonLight;
	private Sprite bgSprite1 = new Sprite(new Texture("bgMain.png"));
	
	@Override
	public void show(){
		
		hudBatch = new SpriteBatch();
		// = new BitmapFont();
		//font.setColor(Color.CYAN);
		//FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		
		//parameter.size = 12;
		//font = generator.generateFont(parameter); // font size 12 pixels
		//generator.dispose();
		world = new World(new Vector2(0, -10.81f), true);
		//camera = new OrthographicCamera();
		debugRenderer = new Box2DDebugRenderer();
		
		rayHandler = new RayHandler(world);
		fixtureDef.density = 5;
		fixtureDef.friction = .4f;
		fixtureDef.restitution = .3f;

		wheelFixtureDef.density = fixtureDef.density * 1.5f;
		wheelFixtureDef.friction = 50;
		wheelFixtureDef.restitution = .4f;
        car = new Car(world, fixtureDef, wheelFixtureDef, 1280/2, 500, 50, 10.25f, 1,true); // 1
		//rayHandler.setGammaCorrection(true);
		moonLight = new PointLight(rayHandler, 8000, Color.CYAN, 1200, 830, 720-180);
		moonLight.setSoft(true);
		moonLight.setSoftnessLength(30);
		menu = new SpriteBatch();
		menuItems = new String[] {
		"Free Play",
		"Vehicles",
		"Quit"
		};
		camera.translate(Gdx.graphics.getWidth()/2, 720/2);
		camera.update();
		
		
		
	}
 
	@Override
	public void render(float delta)
	{
		System.out.println(Gdx.input.getY());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		hudBatch.begin();
		hudBatch.draw(bgSprite1 ,0,0,1280,720);
		hudBatch.end();
		
		
		rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.updateAndRender();
		debugRenderer.render(world, camera.combined);
		menu.begin();
		
		for(int i = 0;i<3;i++)
		{
		//	float width = font.getBounds(menuItems[i]).width;
		//	if(currentItem == i) font.setColor(Color.BLUE);
		//	else font.setColor(Color.WHITE);
		//	font.draw(menu,menuItems[i],(1280-width)/2,180-40*i);   //figured it out, it actually stays resolution independent because of configuration settings in desktop launcher.
			
		}
		//font.setScale(3);
		//float titleWidth = font.getBounds("TinyCar").width;
		
		//font.setColor(Color.GREEN);
	    // font.draw(menu, "TinyCar", (1280-titleWidth)/2, 700);
		//font.setScale(1);
		menu.end();
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
			car.leftWheel.setTransform(car.getChassis().getPosition().x, car.getChassis().getPosition().y,0);
			car.rightWheel.setTransform(car.getChassis().getPosition().x, car.getChassis().getPosition().y,0);
			car.getChassis().applyAngularImpulse(Gdx.input.getDeltaY()*25000, true);
			car.getChassis().setLinearVelocity(Gdx.input.getDeltaX()/5,-Gdx.input.getDeltaY()/5);
		}
		if(Gdx.input.isKeyJustPressed(Keys.DOWN))
		{
			currentItem++;
			if(currentItem>2)
			{
				currentItem=0;
			}
		
		}
		else if(Gdx.input.isKeyJustPressed(Keys.UP))
		{
			currentItem--;
			if(currentItem<0)
			{
				currentItem=2;
			}
		
		}
		else if(Gdx.input.isKeyJustPressed(Keys.ENTER))
		{
			select();
		}
		
		
		
	}

	private void select() {
		if(currentItem==0)
		{
			car = null;
			camera = null;
			world.dispose();
			rayHandler.dispose();
			debugRenderer.dispose();
			dispose();
			((Game) Gdx.app.getApplicationListener())
			.setScreen(new Splash());
		
		}
		else if(currentItem==2)
		{
			System.exit(0);
		
		}
		
	}

	

	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		//camera.viewportHeight = height;
		//camera.viewportWidth = width;
		//camera.update();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	
	}


	

	

	
	
	

}
