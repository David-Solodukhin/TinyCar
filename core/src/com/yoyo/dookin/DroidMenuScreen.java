package com.yoyo.dookin;


import java.util.ArrayList;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class DroidMenuScreen extends Game implements Screen, GestureListener{
	private BitmapFont font;
	private BitmapFont font2;
	private SpriteBatch menu;
	Car car;
	SpriteBatch batch;
	String[] menuItems;
	private World world;
	RayHandler rayHandler;
	private final float TIMESTEP = 1 / 60f;
public Game mygame;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;

	private Box2DDebugRenderer debugRenderer;
	public FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	private OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	private int currentItem = -1;
	FixtureDef fixtureDef = new FixtureDef(),
			wheelFixtureDef = new FixtureDef();
	private SpriteBatch hudBatch;
	private PointLight moonLight;
	private Sprite bgSprite1 = new Sprite(new Texture("bg.png"));
	private ArrayList<Rectangle> menuboxes = new ArrayList<Rectangle>();
	private InputMultiplexer inputMultiplexer;

	public DroidMenuScreen(Game transfr)
	{
		mygame = transfr;
	}
	@Override
	public void show(){
		hudBatch = new SpriteBatch();
		font= new BitmapFont();
		font.setColor(Color.CYAN);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		
		parameter.size = 12;
		font = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose();
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
        car = new Car(world, fixtureDef, wheelFixtureDef, 1280, 500, 50, 10.25f, 1,true); // 1
		//rayHandler.setGammaCorrection(true);
		moonLight = new PointLight(rayHandler, 1000, Color.CYAN, 1200, 830, 720-180);
		moonLight.setSoft(true);
		moonLight.setSoftnessLength(30);
		menu = new SpriteBatch();
		menuItems = new String[] {
		"Quit",
		"HOW TO PLAY",
		"Levels(Coming Soon)",
		"Vehicles(Coming Soon)",
		"PLAY"
		};
		for(int i = 0;i<menuItems.length;i++)
		{
			float width = font.getBounds(menuItems[i]).width;
			float height  = font.getBounds(menuItems[i]).height;
			Vector3 convertedCoords = new Vector3((Gdx.graphics.getWidth()-width)/2f,(float)(i*100)+100f,0);
			convertedCoords = camera.unproject(convertedCoords);
			menuboxes.add(new Rectangle((Gdx.graphics.getWidth()/2-width),(i*100)+50,width*5,height*4));
		}
		
		camera.translate(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		camera.update();
		
		GestureDetector gd = new GestureDetector(this);

		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(gd);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
		
	
 
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		hudBatch.begin();
		hudBatch.draw(bgSprite1 ,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		hudBatch.end();
		
		
		rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.updateAndRender();
		debugRenderer.render(world, camera.combined);
		menu.begin();
		System.out.println(Gdx.input.getX() + " " + menuboxes.get(0).width);
		for(int i = 0;i<menuItems.length;i++)
		{
			float width = font.getBounds(menuItems[i]).width;
			float height  = font.getBounds(menuItems[i]).height;
			if(i==currentItem)
			{
				font.setColor(Color.BLUE);
			}
			
			font.draw(menu,menuItems[i],(Gdx.graphics.getWidth()-width)/2,(i*100)+100);   //figured it out, it actually stays resolution independent because of configuration settings in desktop launcher.
			font.setColor(Color.WHITE);
		}
		font.setScale(Gdx.graphics.getWidth()*.007f);
		float titleWidth = font.getBounds("tinyCar").width;
		
		font.setColor(Color.GREEN);
	     font.draw(menu, "tinyCar", (Gdx.graphics.getWidth()-titleWidth)/2, Gdx.graphics.getHeight()-font.getBounds("T").height);
	     font.setColor(Color.WHITE);
		font.setScale(Gdx.graphics.getWidth()*.002f);
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
			world.destroyBody(car.getChassis());
			camera = null;
			moonLight.dispose();
			
			world.dispose();
			rayHandler.dispose();
			debugRenderer.dispose();
			dispose();
			hudBatch.dispose();
			menu.dispose();
			font.dispose();
			font2.dispose();
			System.gc();
			((Game) Gdx.app.getApplicationListener())
			.setScreen(new FreePlay(mygame));
		
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

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		Vector3 worldCoordinates = new Vector3(Gdx.input.getX(),
				Gdx.input.getY(), 0);
		worldCoordinates = camera.unproject(worldCoordinates);
		for(int i = 0;i<menuboxes.size();i++)
		{
			if(menuboxes.get(i).contains(worldCoordinates.x,worldCoordinates.y))
			{
				if(menuItems[i].equals("PLAY"))
				{
					currentItem = i;
					world.destroyBody(car.getChassis());
					car = null;
					
					camera = null;
				//	moonLight.dispose();
					parameter = null;
					bgSprite1 = null;
					world.dispose();
					rayHandler.dispose();
					debugRenderer.dispose();
					inputMultiplexer.clear();
					inputMultiplexer = null;
					
					dispose();
					hudBatch.dispose();
					menu.dispose();
					System.gc();
				dispose();
				((Game) Gdx.app.getApplicationListener())
				.setScreen(new GameModes(mygame));
				break;
				}
				else if(menuItems[i].contains("Quit"))
				{
					currentItem = i;
					Gdx.app.exit();
					
				}
			}
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}


	

	

	
	
	

}
