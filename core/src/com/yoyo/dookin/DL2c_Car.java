package com.yoyo.dookin;

import java.util.ArrayList;

import java.util.Arrays;

//import org.jbox2d.collision.shapes.PolygonShape;
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
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import com.badlogic.gdx.physics.box2d.World;
/*
 import com.badlogic.gdx.physics.box2d.Body;
 import com.badlogic.gdx.physics.box2d.BodyDef;
 import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
 import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
 import com.badlogic.gdx.physics.box2d.ChainShape;
 import com.badlogic.gdx.physics.box2d.Contact;
 import com.badlogic.gdx.physics.box2d.ContactImpulse;
 import com.badlogic.gdx.physics.box2d.ContactListener;
 import com.badlogic.gdx.physics.box2d.Fixture;
 import com.badlogic.gdx.physics.box2d.FixtureDef;
 import com.badlogic.gdx.physics.box2d.Manifold;
 import com.badlogic.gdx.physics.box2d.PolygonShape;
 import com.badlogic.gdx.physics.box2d.Shape;
 import com.badlogic.gdx.physics.box2d.World;
 */
//import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ShortArray;

public class DL2c_Car extends Game implements Screen, GestureListener {
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private SpriteBatch batch;
	private SpriteBatch hudBatch;
	Sprite bgSprite1 = new Sprite(new Texture("bg.png"));
	Sprite fgSprite2 = new Sprite(new Texture("foreground2.png"));
	Sprite fgSprite3 = new Sprite(new Texture("foreground2.png"));
	Sprite bodyType = new Sprite(new Texture("dynamicDown.png"));
	Sprite shapeType = new Sprite(new Texture("light.png"));
	Sprite gasPedal = new Sprite(new Texture("exitUp.png"));
	Sprite brakePedal = new Sprite(new Texture("brakeUp.png"));
	Sprite fuel = new Sprite(new Texture("fuel.png"));
	Sprite menuPlace = new Sprite(new Texture("menuPlace.png"));
	Sprite restart = new Sprite(new Texture("rightUp.png"));
	Sprite unPause = new Sprite(new Texture("leftUp.png"));
	Sprite pause = new Sprite(new Texture("pause.png"));
	Sprite toMenu = new Sprite(new Texture("toMenuUp.png"));
	public int screenChanger = 0;
	public boolean isJoint = false;
	public boolean isStatic = true;
	public boolean isDynamic = false;
	private OrthographicCamera camera;
	float lastPointx = 0;
	float lastPointy = 0;
	Body leftWheel2;
	float direction = 1;
	Vector2[] tmp;
	Fixture ground;
	int amplitude = 3;
	Game mygame;
	PolygonSprite poly;
	PolygonSpriteBatch polyBatch = new PolygonSpriteBatch(); // To assign at the
																// beginning
	Texture textureSolid;
	EarClippingTriangulator delp = new EarClippingTriangulator();
	Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
	FixtureDef fixtureDef = new FixtureDef(), wheelFixtureDef = new FixtureDef();
	Sprite splash = new Sprite(new Texture("coin.png"));
	Sprite coin = new Sprite(new Texture("coin.png"));
	PolygonRegion polyReg;
	private final float TIMESTEP = 1 / 60f;
	public float scaleFactor = (50 * Gdx.graphics.getWidth()) / 1080f;
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
	private ArrayList<com.badlogic.gdx.math.Rectangle> spriteBoxes = new ArrayList<com.badlogic.gdx.math.Rectangle>();
	private com.badlogic.gdx.math.Rectangle carBox;
	private Rectangle gas;
	private Rectangle brake;
	private Rectangle reverseIcon;
	private int fpScore = 0;
	private float tOD = 10000;
	private float colorChanger = 0;
	private int airTime = 0;
	private String currentStunt = "";
	private BitmapFont stuntFont;
	private boolean gassing = false;
	private boolean breaking = false;
	private boolean noFuel = false;
	private Rectangle pauseButton;
	public boolean showMenu = false;
	private Rectangle restartButton;
	private Game myGame;
	private GestureDetector gd;
	private InputMultiplexer inputMultiplexer;
	private boolean goToMenu = false;
	private String menuMsg = "";
	private Screen tmpScreen;
	private boolean drawing = false;
	private ChainShape currentShape;
	private Body doop;
	private FixtureDef fixtureDef2;
	private Vector2[] drawPoints;
	private TextureRegion texRegion;
	private boolean coinsGenerated = false;
	private boolean justStarted = true;
	private ArrayList<Body> drawnBodies = new ArrayList<Body>();
	private ArrayList<Vector2[]> pointsofBodies = new ArrayList<Vector2[]>();
	// private ArrayList<Vector2> originalPoints = new ArrayList<Vector2>();
	private ArrayList<Vector2> originalPoints;
	private boolean isSquare;
	private boolean isMisc = true;
	private Vector2 delphi = new Vector2(0, 0);
	ArrayList<Vector2> updatedPoints = new ArrayList<Vector2>();
	private boolean first;
	private float prevMargin = 0;
	private boolean isHeavy = false;
	private ShapeRendererF shapeRendererF;
	private FPSLogger fps;
	private Sound sound;

	public DL2c_Car(Game game) {
		this.mygame = game;
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		if (!Gdx.input.isTouched(2)) {
			//camera.position.set(car.getChassis().getPosition().x , car.getChassis().getPosition().y, 0);
			//camera.update();
		}
		handleInput();
		hudBatch.begin();
		hudBatch.draw(bgSprite1, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// hudBatch.draw(fgSprite2,fgParallax,(720-camera.unproject(new
		// Vector3(car.getChassis().getPosition(),0)).y)-600); //720/2-300
		hudBatch.draw(fgSprite2, fgParallax, fgParallay - 100, fgSprite2.getWidth() * 1.5f, fgSprite2.getHeight() * 1.5f);
	//	hudBatch.draw(fgSprite3, fgParallax + (fgSprite2.getWidth() * 1.5f), fgParallay - 100, fgSprite2.getWidth() * 1.5f, fgSprite2.getHeight() * 1.5f);

		hudBatch.end();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (int i = 0; i < sprites.size(); i++) {
			sprites.get(i).draw(batch);
		}
		batch.end();

		

	

		rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.updateAndRender();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		world.getBodies(tmpBodies);
		for (Body body : tmpBodies){
			if (body.getUserData() instanceof Sprite && !body.equals(car.chassis) && !body.equals(car.rightWheel) && !body.equals(car.leftWheel)) {
				Sprite sprite = (Sprite) body.getUserData();
				sprite.setPosition(

				body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
				sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
				sprite.draw(batch);

			}
	
		
		}
		debugRenderer.setDrawJoints(false);
		font.setScale(0.1f);
		
		font.setScale(1);

		batch.end();
		debugRenderer.render(world, camera.combined);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (Body body : car.carParts) {

			if (body.getUserData() instanceof Sprite) {
				Sprite sprite = (Sprite) body.getUserData();
				sprite.setPosition(

				body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
				sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
				sprite.draw(batch);

			}
		}
		batch.end();
		
		/*if (tmp.length != 0) { // terrain drawing.
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Line);

			for (int i = 0; i < tmp.length - 1; i++) {
				shapeRenderer.setColor(Color.BLUE);
				shapeRenderer.line(tmp[i].x, tmp[i].y, tmp[i + 1].x, tmp[i + 1].y);
			}

		}
		shapeRenderer.end();*/

		debugRenderer.setDrawBodies(true);
		update();
		

		// shapeDebugger.setProjectionMatrix(camera.combined);
		Color doop = new Color(Color.CYAN);

		shapeDebugger.setColor(doop);
		shapeDebugger.begin(ShapeType.Filled);

		// shapeDebugger.rect(Gdx.graphics.getWidth()/2-(car.jumpjuice * 3)/2,
		// 0, car.jumpjuice * 3, 10 * 4);
		shapeDebugger.setColor(Color.TEAL);
		
		// shapeDebugger.rect(0, Gdx.graphics.getHeight()-200,3*50,3*50);
		shapeDebugger.end();

		hudBatch.begin();
		gasPedal.draw(hudBatch);
		bodyType.draw(hudBatch);
		coin.draw(hudBatch);
	
		//fuel.draw(hudBatch);
		pause.draw(hudBatch);
		font.setScale(1.9f);
		//font.draw(hudBatch, "Ink:         ", 500, 50);
		font.setScale(3);
		font.draw(hudBatch, "Coins: " + fpScore, 10, Gdx.graphics.getHeight() - 30);
		font.setScale(1.9f);
		font.setColor(Color.WHITE);
		font.setColor(Color.WHITE);
		font.setScale(1f);
		hudBatch.end();
		camera.update();

		

		if (Gdx.input.isTouched()) {
		
			Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			worldCoordinates = camera.unproject(worldCoordinates);
			//car.getChassis().setTransform(worldCoordinates.x, worldCoordinates.y, 0);
			//Vector2 tempCoords = new Vector2(car.getChassis().getPosition());
			//car.getChassis().setLinearVelocity(Gdx.input.getDeltaX() / 5, -Gdx.input.getDeltaY() / 5);
			if(isMisc)
			{
				car.prevW();
			
			}
			else
			{
				car.prevS();
			}
		}
		else
		{
			car.prevKeyUp();
		}

	}

	private void handleInput() {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();

		}
		

	}

	private void update() {
	//	world.setGravity(new Vector2(-14.81f*MathUtils.cosDeg(Gdx.input.getAzimuth()+90),-14.81f*MathUtils.sinDeg(Gdx.input.getAzimuth()+90)));
	//System.out.println(world.getGravity());
		
		if (Gdx.input.isKeyPressed(Keys.W)) {
			car.prevW(); //on android it won't matter but for debugging it's okay.
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			car.getChassis().setTransform(car.getChassis().getPosition().x,car.getChassis().getPosition().y+1, 0); //on android it won't matter but for debugging it's okay.
		}
		shapeRenderer.setProjectionMatrix(camera.combined); // rendering for
		optimizeOffScreen();													// static shapes
		//fps.log(); // while drawing
					// //change
					// everything back
					// to shapeRenderer
					// from
					// shapeRendererF is
					// things go wrong.
		shapeRenderer.begin(ShapeType.Line);
		if (drawPoints != null) {
			for (int x = 0; x < drawPoints.length - 1; x++) {
				shapeRenderer.setColor(1, 1, 0, 1);
				shapeRenderer.line(drawPoints[x].x, drawPoints[x].y, drawPoints[x + 1].x, drawPoints[x + 1].y);
			}
		}
		shapeRenderer.end();
		shapeRendererF.setProjectionMatrix(camera.combined); // rendering for
																// static shapes
																// while drawing

		shapeRendererF.setProjectionMatrix(camera.combined);
		shapeRendererF.begin(ShapeRendererF.ShapeType.Filled);

		if (tmpBodies.size > 1) // && drawnBodies.size() > 0
			for (int i = 0; i < tmpBodies.size; i++) {
				if (tmpBodies.get(i).getType().equals(BodyType.DynamicBody) && !tmpBodies.get(i).equals(terrain)
						&& (tmpBodies.get(i).getFixtureList().get(0).getType().equals(Shape.Type.Chain)) && tmpBodies.get(i).getFixtureList().size > 0) {
					shapeRendererF.set(ShapeRendererF.ShapeType.Filled);
					ChainShape tempShape = new ChainShape();
					ArrayList<Vector2> bodyPoints = new ArrayList<Vector2>();

					ArrayList<Vector2> testicle = new ArrayList<Vector2>();
					updatedPoints = bodyPoints;
					tempShape = (ChainShape) tmpBodies.get(i).getFixtureList().get(0).getShape();
					float[] verticies = new float[tempShape.getVertexCount() * 2];
					int counter = 0;
					for (int q = 0; q < tempShape.getVertexCount(); q++) {
						Vector2 linePoints = new Vector2();
						Vector2 linePoints2 = new Vector2();
						tempShape.getVertex(q, linePoints);

						Vector2 newLinePoints = tmpBodies.get(i).getWorldPoint(linePoints);

						bodyPoints.add(newLinePoints);

						verticies[counter] = newLinePoints.x;
						verticies[counter + 1] = newLinePoints.y;
						counter += 2;
						shapeRendererF.setColor(1, 1, 0, 1);

					}

					camera.update();

					if (verticies.length > 4) {
						shapeRendererF.polygon(verticies);
					}
				} else if (tmpBodies.get(i).getType().equals(BodyType.DynamicBody) && !tmpBodies.get(i).equals(terrain) && !tmpBodies.get(i).equals(car.chassis)
						&& (tmpBodies.get(i).getFixtureList().get(0).getType().equals(Shape.Type.Polygon))) // tmpBodies.get(i).getFixtureList().get(0).getShape().equals(Type.Chain)
				{
					if (isHeavy == true) {

						/*
						 * shapeRendererF.set(ShapeRendererF.ShapeType.Filled);
						 * PolygonShape tempShape = new PolygonShape();
						 * ArrayList<Vector2> bodyPoints = new
						 * ArrayList<Vector2>();
						 * 
						 * ArrayList<Vector2> testicle = new
						 * ArrayList<Vector2>(); updatedPoints = bodyPoints; for
						 * (int a = 0; a <
						 * tmpBodies.get(i).getFixtureList().size; a++) {
						 * 
						 * tempShape = (PolygonShape)
						 * tmpBodies.get(i).getFixtureList().get(a).getShape();
						 * float[] verticies = new
						 * float[tempShape.getVertexCount() * 2]; int counter =
						 * 0; for (int q = 0; q < tempShape.getVertexCount();
						 * q++) { Vector2 linePoints = new Vector2(); Vector2
						 * linePoints2 = new Vector2(); tempShape.getVertex(q,
						 * linePoints); Vector2 newLinePoints =
						 * tmpBodies.get(i).getWorldPoint(linePoints);
						 * bodyPoints.add(newLinePoints); verticies[counter] =
						 * newLinePoints.x; verticies[counter + 1] =
						 * newLinePoints.y; counter += 2;
						 * shapeRendererF.setColor(1, 1, 0, 1);
						 * 
						 * } if (verticies.length > 4) {
						 * shapeRendererF.polygon(verticies); } }
						 */

						int sizeOf = 0;
						int counter = 0;
						shapeRendererF.set(ShapeRendererF.ShapeType.Filled);
						for (int x = 0; x < tmpBodies.get(i).getFixtureList().size; x++) {
							PolygonShape tempShape = (PolygonShape) tmpBodies.get(i).getFixtureList().get(x).getShape();
							sizeOf += tempShape.getVertexCount() * 2;
						}
						float[] tempArr = new float[sizeOf];
						for (int z = 0; z < tmpBodies.get(i).getFixtureList().size; z++) {
							PolygonShape tempShape = (PolygonShape) tmpBodies.get(i).getFixtureList().get(z).getShape();

							for (int a = 0; a < tempShape.getVertexCount(); a++) {
								Vector2 linePoints = new Vector2();
								tempShape.getVertex(a, linePoints);
								Vector2 newLinePoints = tmpBodies.get(i).getWorldPoint(linePoints);

								// tempArr[counter] = newLinePoints.x;
								// tempArr[counter + 1] = newLinePoints.y;
								tempArr[counter] = newLinePoints.x;
								tempArr[counter + 1] = newLinePoints.y;
								counter += 2;
							}
						}

						int counter3 = 0;
						Vector2[] recievedPoints = new Vector2[tempArr.length / 2];
						for (int r = 0; r < recievedPoints.length; r++) {
							recievedPoints[r] = new Vector2(tempArr[counter3], tempArr[counter3 + 1]);
							counter3 += 2;
						}

						ArrayList<Vector2> temp = toArrayList(recievedPoints);

						// temp = removeDuplicates(temp);

						// System.out.println(Arrays.toString(recievedPoints));
						temp = removeDuplicates(temp);

						recievedPoints = toArrayList(temp);
						// System.out.println(Arrays.toString(recievedPoints));
						// recievedPoints = removeDuplicates(recievedPoints);
						int[] userData;
						if (tmpBodies.get(i).getUserData() != null) {
							userData = (int[]) tmpBodies.get(i).getUserData();
							Vector2[] finalPoints = new Vector2[recievedPoints.length];// used
																						// to
																						// be
																						// userData.length
							for (int k = 0; k < userData.length; k++) {
								try {
									finalPoints[userData[k]] = recievedPoints[k]; // the
																					// problem
																					// is
																					// here
								} catch (ArrayIndexOutOfBoundsException e) {
									continue;
								}
							}
							recievedPoints = finalPoints;
						}


						shapeRendererF.setColor(Color.BLUE);
						// shapeRendererF.polygon(tempArr);
						ArrayList<Vector2> verticies = toArrayList(recievedPoints);
						float[] fVerticies = new float[verticies.size() * 2];
						int cIndex = 0; // the corrected index
						for (int s = 0; s < verticies.size(); s++) {
							fVerticies[cIndex] = verticies.get(s).x;
							fVerticies[cIndex + 1] = verticies.get(s).y;
							cIndex += 2;

						}
						// System.out.println(Arrays.toString(fVerticies));
						if (fVerticies.length > 4) {
							// shapeRendererF.polygon(fVerticies,0,16);

							ShortArray why;
							why = delp.computeTriangles(fVerticies); //triangulation O(n) algorithm: not mine
							short[] why2 = new short[why.size];
							why2 = why.toArray();
				            
				           
							polyReg = new PolygonRegion(texRegion, fVerticies, why2);
							poly = new PolygonSprite(polyReg);
							
						//	poly.set(polyReg); //tmpBodies.get(i).getPosition().x, tmpBodies.get(i).getPosition().y
							poly.setOrigin(tmpBodies.get(i).getWorldCenter().x, tmpBodies.get(i).getWorldCenter().y);
						//	poly.setScale(.5f, .5f);
							
							
							polyBatch.setProjectionMatrix(camera.combined);
							
							polyBatch.begin();
							poly.draw(polyBatch);
							//polyBatch.draw(polyReg,tmpBodies.get(i).getWorldCenter().x/50, tmpBodies.get(i).getWorldCenter().y/50,100,100);
							polyBatch.end();
							
							//poly = null;
							//polyReg = null;
							

						}

						// shapeRendererF.line(tempArr[0],tempArr[1],tempArr[tempArr.length-2],tempArr[tempArr.length-1]);

						// Gdx.app.exit();

					} else {
						int sizeOf = 0;
						int counter = 0;
						shapeRendererF.set(ShapeRendererF.ShapeType.Filled);
						for (int x = 0; x < tmpBodies.get(i).getFixtureList().size; x++) {
							PolygonShape tempShape = (PolygonShape) tmpBodies.get(i).getFixtureList().get(x).getShape();
							sizeOf += tempShape.getVertexCount() * 2;
						}
						float[] tempArr = new float[sizeOf];
						for (int z = 0; z < tmpBodies.get(i).getFixtureList().size; z++) {
							PolygonShape tempShape = (PolygonShape) tmpBodies.get(i).getFixtureList().get(z).getShape();

							for (int a = 0; a < tempShape.getVertexCount(); a++) {
								Vector2 linePoints = new Vector2();
								tempShape.getVertex(a, linePoints);
								Vector2 newLinePoints = tmpBodies.get(i).getWorldPoint(linePoints);

								// tempArr[counter] = newLinePoints.x;
								// tempArr[counter + 1] = newLinePoints.y;
								tempArr[counter] = newLinePoints.x;
								tempArr[counter + 1] = newLinePoints.y;
								counter += 2;
							}
						}

						int counter3 = 0;
						Vector2[] recievedPoints = new Vector2[tempArr.length / 2];
						for (int r = 0; r < recievedPoints.length; r++) {
							recievedPoints[r] = new Vector2(tempArr[counter3], tempArr[counter3 + 1]);
							counter3 += 2;
						}

						ArrayList<Vector2> temp = toArrayList(recievedPoints);

						// temp = removeDuplicates(temp);

						// System.out.println();

						// System.out.println(Arrays.toString(recievedPoints));
						temp = removeDuplicates(temp);

						recievedPoints = toArrayList(temp);
						// System.out.println(Arrays.toString(recievedPoints));
						// recievedPoints = removeDuplicates(recievedPoints);
						int[] userData;
						if (tmpBodies.get(i).getUserData() != null) {
							userData = (int[]) tmpBodies.get(i).getUserData();
							Vector2[] finalPoints = new Vector2[recievedPoints.length];// used
																						// to
																						// be
																						// userData.length
							for (int k = 0; k < userData.length; k++) {
								try {
									finalPoints[userData[k]] = recievedPoints[k]; // the
																					// problem
																					// is
																					// here
								} catch (ArrayIndexOutOfBoundsException e) {
									continue;
								}
							}
							recievedPoints = finalPoints;
						}

						// System.out.println(tmpBodies.get(i).getWorldPoint(recievedPoints[0]));
						// System.out.println(tmpBodies.get(i).getWorldPoint(recievedPoints[5]));
						// System.out.println(Arrays.toString(recievedPoints));

						// System.out.println(Arrays.toString(recievedPoints));
						// System.out.println(Arrays.toString((int[])tmpBodies.get(i).getUserData()));
						// Gdx.app.exit();

						shapeRendererF.setColor(Color.RED);
						// shapeRendererF.polygon(tempArr);
						for (int s = 0; s < recievedPoints.length - 1; s++) {

							// shapeRendererF.line(originalPoints[s],originalPoints[s+1]);
							if (recievedPoints[s] == null && recievedPoints[s + 1] != null) {
								shapeRendererF.line(recievedPoints[s - 1], recievedPoints[s + 1]);
							}else if (recievedPoints[s] != null && recievedPoints[s + 1] != null) {
								shapeRendererF.line(recievedPoints[s], recievedPoints[s + 1]);
							}else if (s < recievedPoints.length && recievedPoints[s] != null && recievedPoints[s + 1] == null ) {
								
								try{
									//System.out.println(recievedPoints[s + 2]);
								shapeRendererF.line(recievedPoints[s], recievedPoints[s + 2]);}
								catch(ArrayIndexOutOfBoundsException e)
								{
									
								}
								
							} 
						}
						// shapeRendererF.line(tempArr[0],tempArr[1],tempArr[tempArr.length-2],tempArr[tempArr.length-1]);

						// Gdx.app.exit();

					}
				}

				camera.update();

				
			}
		shapeRendererF.set(ShapeRendererF.ShapeType.Line);
		for (int i = 0; i < drawnBodies.size(); i++) { // rendering for static
														// shapes-convex or
														// concave after drawing
			if (drawnBodies.get(i).getType().equals(BodyType.StaticBody) && drawnBodies.get(i).getFixtureList().size > 0
					&& drawnBodies.get(i).getFixtureList().get(0).getType().equals(Shape.Type.Chain)) {
				for (int x = 0; x < pointsofBodies.get(i).length - 1; x++) {
					shapeRendererF.setColor(Color.WHITE);
					shapeRendererF.line(pointsofBodies.get(i)[x], pointsofBodies.get(i)[x + 1]);
				}
			}
		}
		shapeRendererF.end();

		if (car.getChassis().getLinearVelocity().x > 5 && car.getChassis().getLinearVelocity().x > 0) {
			fgParallax -= 0.3f;
		} else if (car.getChassis().getLinearVelocity().x < -5 && car.getChassis().getLinearVelocity().x < 0) {
			fgParallax += 0.3f;
		}
		if (car.getChassis().getLinearVelocity().y >= 1 && car.getChassis().getLinearVelocity().y > 0) {
			fgParallay -= 0.3f;
		} else if (car.getChassis().getLinearVelocity().y <= -1 && car.getChassis().getLinearVelocity().y < 0) {
			fgParallay += 0.3f;
		}
		if (showMenu) {
			carLight.setActive(false);

		}
		//if (car.rightWheel.getPosition().y > car.leftWheel.getPosition().y + 2) {
		//	car.prevA();
		//} else if (car.leftWheel.getPosition().y > car.rightWheel.getPosition().y + 2) {
		//	car.prevD();
	//	}
		
		if (car.getChassis().getPosition().y < -310) {
			showMenu = true;
			noFuel = true;
			sCounter2 = 0;
		}

		if (goToMenu) {
			backToMenu();
		}

	/*	if (Gdx.input.isTouched() && !drawing && !noFuel && isSquare && car.fuel>0 && !bodyType.getBoundingRectangle().contains(Gdx.input.getX(), 1080 - Gdx.input.getY())) {

			FixtureDef fixtureDef2 = new FixtureDef();
			coinsGenerated = false;
			drawing = true;
			sound.play();
			Vector3 mouseCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

			drawPoints = new Vector2[1];
			Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			worldCoordinates = camera.unproject(worldCoordinates);
			// System.out.println(camera.unproject(worldCoordinates).y);
			// System.out.println(Gdx.input.getX()-(camera.viewportWidth+camera.position.x));

			drawPoints[0] = new Vector2(worldCoordinates.x, worldCoordinates.y);
			// drawPoints[0] = new
			// Vector2(camera.unproject(mouseCoords).x,camera.unproject(mouseCoords).y);
			// drawPoints[1] = new
			// Vector2(camera.unproject(mouseCoords).x+0.01f,camera.unproject(mouseCoords).y+0.01f);
			// currentShape.createChain(drawPoints);

		} else if (Gdx.input.isTouched() && drawing && isSquare && car.fuel>0 && !bodyType.getBoundingRectangle().contains(Gdx.input.getX(), 1080 - Gdx.input.getY())) { //!gassing

			Vector3 mouseCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			if (first) {
				delphi = new Vector2(mouseCoords.x, mouseCoords.y);
				first = false;
			} else {
				first = true;
			}
			if ((!(delphi.x == mouseCoords.x && delphi.y == mouseCoords.y) || !(delphi.dst(mouseCoords.x, mouseCoords.y) <= 10f))) {
				Vector2[] tempPoints = new Vector2[drawPoints.length + 1];

				for (int i = 0; i < drawPoints.length; i++) {
					tempPoints[i] = drawPoints[i];
				}
				drawPoints = tempPoints;
				Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
				worldCoordinates = camera.unproject(worldCoordinates);
				drawPoints[drawPoints.length - 1] = new Vector2(worldCoordinates.x, worldCoordinates.y);
				car.fuel-=0.5f;
			
			}
		} else if (Gdx.input.isTouched() && !drawing && !noFuel && car.fuel>0 && !bodyType.getBoundingRectangle().contains(Gdx.input.getX(), 1080 - Gdx.input.getY())) {
			// FixtureDef fixtureDef2 = new FixtureDef();
			coinsGenerated = false;
			drawing = true;
			sound.play();
			Vector3 mouseCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			drawPoints = new Vector2[1];
			Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			worldCoordinates = camera.unproject(worldCoordinates);
			// System.out.println(camera.unproject(worldCoordinates).y);
			// System.out.println(Gdx.input.getX()-(camera.viewportWidth+camera.position.x));

			drawPoints[0] = new Vector2(worldCoordinates.x, worldCoordinates.y);
			delphi = new Vector2(mouseCoords.x, mouseCoords.y);
			fixtureDef.friction = .5f;
			fixtureDef.restitution = 0;
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.StaticBody;
			doop = world.createBody(bodyDef);
           
		} else if (Gdx.input.isTouched() && drawing && !noFuel && car.fuel>0) {

			Vector3 mouseCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			car.fuel-=0.5f;
			if (first) {
				delphi = new Vector2(mouseCoords.x, mouseCoords.y);
				first = false;
			} else {
				first = true;
			}
			Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			worldCoordinates = camera.unproject(worldCoordinates);
			if (!(delphi.x == mouseCoords.x && delphi.y == mouseCoords.y) || !(delphi.dst(mouseCoords.x, mouseCoords.y) <= 10f)) {
				Vector2[] tempPoints = new Vector2[drawPoints.length + 1];
				// System.out.println("skdshkjhsdkjhsfjhdlkjhsdlkfhjkhlkshlshjdhdskhjsfklhjsfklhdsflsfdhkshjkl");
				BodyDef bodyDef = new BodyDef();
				bodyDef.type = BodyType.StaticBody;
				world.destroyBody(doop);

				doop = world.createBody(bodyDef);
				// FixtureDef fixtureDef3 = new FixtureDef();

				for (int i = 0; i < drawPoints.length; i++) {
					tempPoints[i] = drawPoints[i];
				}
				drawPoints = tempPoints;

				drawPoints[drawPoints.length - 1] = new Vector2(worldCoordinates.x, worldCoordinates.y);

				currentShape = new ChainShape();
				currentShape.createChain(drawPoints);

				fixtureDef.shape = currentShape;
				// PolygonShape chassisShape = new PolygonShape();
				// chassisShape.set(drawPoints);
				fixtureDef.shape = currentShape;
				fixtureDef.friction = .5f;
				fixtureDef.restitution = 0;
				car.fuel -= 0.20f;

				doop = world.createBody(bodyDef);
				doop.createFixture(fixtureDef);
			}

		}
		
		if (!Gdx.input.isTouched()) {
			sound.stop();
			if (drawPoints != null && drawPoints.length > 1 && !coinsGenerated) {
				
				
				coinsGenerated = true;
				if (isSquare && drawPoints.length > 3) {
					
					PolygonShape nelf = new PolygonShape();
					PolygonShape nelf2 = new PolygonShape();
					// originalPoints = drawPoints;
					Vector2[] tmep = new Vector2[drawPoints.length];
					for (int i = drawPoints.length - 1; i >= 0; i--) {
						tmep[drawPoints.length - i - 1] = drawPoints[i];
					}
					// ///////////////////////////////////////////////////////////////////////

					for (int i = 0; i < tmep.length - 1; i++) {
						if (tmep[i].dst2(tmep[i + 1]) <= 0.01f || tmep[i].isOnLine(tmep[i + 1]) || tmep[i].isCollinear(tmep[i + 1])) {

							Vector2[] marginalized = new Vector2[tmep.length - 1];
							for (int x = 0; x < marginalized.length; x++) {
								if (x < i) {
									marginalized[x] = tmep[x];
								} else {
									marginalized[x] = tmep[x + 1];
								}
							}
							tmep = marginalized;

							float marginalizer = MathUtils.random(0.03f, 0.2f);

							tmep[i] = new Vector2(tmep[i].add(marginalizer, 0.01f));
							i--;

							// tmep[i+1] = new Vector2(tmep[i+1].add(1,1));
							System.out.println(tmep.length);
						}
					}
					tmep = removeDuplicates(tmep);
					// System.out.println(Arrays.toString(tmep));
					// Gdx.app.exit();
					drawPoints = tmep;
					ArrayList<Vector2[]> terrainSplittr = new ArrayList<Vector2[]>();
					BodyDef bodyDef = new BodyDef();
					bodyDef.type = BodyType.DynamicBody;
					Body testPolygonShape1 = world.createBody(bodyDef);

					
					for (int x = 0; x < drawPoints.length - 3; x += 3) { 

						Vector2[] test = new Vector2[4]; // was 5
						if(x+3>=drawPoints.length-3 && isHeavy)
						{
							test = new Vector2[5];
							test[0] = drawPoints[x];
							test[1] = drawPoints[x + 1];
							test[2] = drawPoints[x + 2];
							test[3] = drawPoints[x + 3];
							test[4] = drawPoints[0];
							
						}
						else if(x+3>=drawPoints.length-3)
						{
							test = new Vector2[drawPoints.length-x];
							for(int q = 0;q<test.length;q++)
							{
								test[q] = drawPoints[x+q];
							}
						}
						else
						{
						test[0] = drawPoints[x];
						test[1] = drawPoints[x + 1];
						test[2] = drawPoints[x + 2];
						test[3] = drawPoints[x + 3];
						}
						terrainSplittr.add(test);

					}
				
					originalPoints = new ArrayList<Vector2>();

					// Gdx.app.exit();
					int counter = 0;
					for (int special = 0; special < terrainSplittr.size(); special++) {
						for (int q = 0; q < terrainSplittr.get(special).length; q++) {
							originalPoints.add(terrainSplittr.get(special)[q]);
							// counter++;
						}
						// originalPoints[special] =
						// terrainSplittr.get(special);
					}

					originalPoints = removeDuplicates(originalPoints);
					// System.out.println((originalPoints));
					ArrayList<Body> semiFinished = new ArrayList<Body>();
					for (int x = 0; x < terrainSplittr.size(); x++) {

						nelf.set(terrainSplittr.get(x));
						// nelf2.set(terrainSplittr.get(x + 1));

						fixtureDef.shape = nelf;
						fixtureDef.friction = .5f;
						fixtureDef.restitution = 0;
						bodyDef.position.set(0, 0);

						// bodyDef.fixedRotation = false;

						testPolygonShape1.createFixture(fixtureDef);

					

					}
					doop = testPolygonShape1;
					
					ArrayList<Vector2> recievedPoints = new ArrayList<Vector2>();

					for (int i = 0; i < doop.getFixtureList().size; i++) {
						PolygonShape tempShape = (PolygonShape) doop.getFixtureList().get(i).getShape();
						for (int x = 0; x < tempShape.getVertexCount(); x++) {
							Vector2 d = new Vector2();
							tempShape.getVertex(x, d);
							// d= doop.getWorldPoint(d);
							recievedPoints.add(d);
						}
					}

					// System.out.println(recievedPoints);
					recievedPoints = removeDuplicates(recievedPoints);

					// originalPoints = removeDuplicates(originalPoints);

					while (recievedPoints.size() != originalPoints.size()) {
						for (int x = 0; x < originalPoints.size(); x++) {
							if (indexOfVector2(recievedPoints, originalPoints.get(x)) == -1) {
								recievedPoints.add(originalPoints.get(x));
								// Gdx.app.exit();
							}

						}
						// originalPoints.remove(originalPoints.size()-1);

					}
					int[] userData = new int[recievedPoints.size()];
					for (int i = 0; i < userData.length; i++) {

						userData[i] = indexOfVector2(originalPoints, recievedPoints.get(i));
					}
					doop.setUserData(userData);

					// System.out.println(Arrays.toString(userData));

				}

				if (isDynamic && !isSquare) {
					doop.setType(BodyType.DynamicBody);
				} else if (isStatic && !isSquare) {
					doop.setType(BodyType.StaticBody);
				}
				Body body = doop;

				if (!isSquare) {
					drawnBodies.add(body);
					pointsofBodies.add(drawPoints);
				}

				drawPoints = null;
			}
			drawing = false;
		}
		// System.out.println(car.fuel);
		
		*/

		if (!Gdx.input.isTouched()) {
			if (gassing) {
				gasPedal.setTexture(new Texture("exitUp.png"));
				gassing = false;
			}
			if (breaking) {
				brakePedal.setTexture(new Texture("brakeUp.png"));
				breaking = false;
			}
		}
		if (showMenu == true) {
			setupMenu();
		}

	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void resize(int width, int height) {

		camera.viewportWidth = 1920/63f; // to make things
		camera.viewportHeight = 1080f/63f;  //height / scaleFactor;

	}

	@Override
	public void show() {
		
		world = new World(new Vector2(0, -14.81f), true);
		camera = new OrthographicCamera();
		debugRenderer = new Box2DDebugRenderer();
		shapeRenderer = new ShapeRenderer();
		shapeRendererF = new ShapeRendererF();
		rayHandler = new RayHandler(world);
		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		polyBatch = new PolygonSpriteBatch();
		sound = Gdx.audio.newSound(Gdx.files.internal("drawingS.mp3"));
		Texture bgRepeat = new Texture("foreground2.png");
		bgRepeat.setWrap(TextureWrap.MirroredRepeat, TextureWrap.MirroredRepeat);
		fgSprite2 = new Sprite(bgRepeat);
		shapeRendererF.setAutoShapeType(true);
		mp3Music = Gdx.audio.newMusic(Gdx.files.internal("Cipher2.mp3"));
		pix.setColor(Color.BLUE); // DE is red, AD is green and BE is blue.
		pix.fill();
		textureSolid = new Texture(pix); //pix
		Texture test = new Texture("medres.png");
		
		test.setWrap(TextureWrap.MirroredRepeat, TextureWrap.MirroredRepeat);
		texRegion = new TextureRegion(test);
	
		
		// mp3Music.play();
		createCollisionListener();

		coin.setPosition(320, Gdx.graphics.getHeight() - coin.getHeight() - 30);
		

		BodyDef bodyDef = new BodyDef();

		// car
		fixtureDef.density = 5;
		fixtureDef.friction = .4f;
		fixtureDef.restitution = .3f;

		wheelFixtureDef.density = fixtureDef.density * 1.5f;
		wheelFixtureDef.friction = .9f;
		wheelFixtureDef.restitution = .4f;

		bodyDef.type = BodyType.DynamicBody;

		car = new DroidCar(world, fixtureDef, wheelFixtureDef, 0f, 3, 6, 1.25f, 1, true); // 1
		carLight = new PointLight(rayHandler, 1000, Color.CYAN, 0, 30, 40); // 30,30
		PointLight mainLight = new PointLight(rayHandler, 1000,Color.ORANGE,25,0,1);
		mainLight.setXray(true);
		//PointLight mainLight2 = new PointLight(rayHandler, 1000,Color.BLUE,15,0,-1);
	//	PointLight mainLight3 = new PointLight(rayHandler, 3000,Color.BLUE,30,0,-5);
		headLight = new ConeLight(rayHandler, 1000, Color.BLUE, 20, 30, 800, 20, 20); // these
																		headLight.setActive(false);				// numbers
																						// don't
																						// matter
																						// cause
																						// it's
																						// attached
																						// to
		// the chassis.
		headLight.attachToBody(car.getChassis());
		headLight.setXray(false);
		carLight2 = new PointLight(rayHandler, 1000, Color.YELLOW, 0, car.getChassis().getPosition().x, car.getChassis().getPosition().y + 5);
		carLight2.setActive(false);
		carLight2.setSoft(true);
		carLight2.setSoftnessLength(10);
		// GROUND
		// body definition
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, 0);
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, 0);

		// ground shape
		ChainShape groundShape = new ChainShape();
		
		// ground shape
		groundShape = new ChainShape();
		groundShape.createChain(new Vector2[] { new Vector2(-15, 8), new Vector2(15, 8) });
		car.getChassis().setSleepingAllowed(false);
		fixtureDef.shape = groundShape;
		fixtureDef.friction = .9f;
		fixtureDef.restitution = 0;
		terrain = world.createBody(bodyDef);
		ground = terrain.createFixture(fixtureDef);
		groundShape.dispose();
		groundShape = new ChainShape();
		groundShape.createChain(new Vector2[] { new Vector2(-15, -8), new Vector2(15, -8)});
		car.getChassis().setSleepingAllowed(false);
		fixtureDef.shape = groundShape;
		fixtureDef.friction = .9f;
		fixtureDef.restitution = 0;
		terrain = world.createBody(bodyDef);
		ground = terrain.createFixture(fixtureDef);
		groundShape.dispose();
		groundShape = new ChainShape();
		groundShape.createChain(new Vector2[] { new Vector2(-10, -5), new Vector2(10, 5)});
		car.getChassis().setSleepingAllowed(false);
		fixtureDef.shape = groundShape;
		fixtureDef.friction = .9f;
		fixtureDef.restitution = 0;
		terrain = world.createBody(bodyDef);
		ground = terrain.createFixture(fixtureDef);
		groundShape.dispose();
		groundShape = new ChainShape();
		groundShape.createChain(new Vector2[] { new Vector2(-8, -2), new Vector2(-8, 10)});
		car.getChassis().setSleepingAllowed(false);
		fixtureDef.shape = groundShape;
		fixtureDef.friction = .9f;
		fixtureDef.restitution = 0;
		terrain = world.createBody(bodyDef);
		ground = terrain.createFixture(fixtureDef);
		groundShape.dispose();
		groundShape = new ChainShape();
		groundShape.createChain(new Vector2[] { new Vector2(-15, -10), new Vector2(-15,10)});
		car.getChassis().setSleepingAllowed(false);
		fixtureDef.shape = groundShape;
		fixtureDef.friction = .9f;
		fixtureDef.restitution = 0;
		terrain = world.createBody(bodyDef);
		ground = terrain.createFixture(fixtureDef);
		groundShape.dispose();
		groundShape = new ChainShape();
		groundShape.createChain(new Vector2[] { new Vector2(15, -10), new Vector2(15,10)});
		car.getChassis().setSleepingAllowed(false);
		fixtureDef.shape = groundShape;
		fixtureDef.friction = .9f;
		fixtureDef.restitution = 0;
		terrain = world.createBody(bodyDef);
		ground = terrain.createFixture(fixtureDef);
		
		
		
		
		// world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape.dispose();

		font = new BitmapFont();
		stuntFont = new BitmapFont();
		font.setColor(Color.CYAN);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		font = generator.generateFont(parameter); // font size 12 pixels
		stuntFont = generator.generateFont(parameter);
		generator.dispose();
		carBox = new com.badlogic.gdx.math.Rectangle(car.getChassis().getPosition().x - 2, car.getChassis().getPosition().y + 3, 3, 12);
		Vector3 tempCoords = camera.unproject(new Vector3(10, 50, 0));
	//	setUpLevel();

		brakePedal.setPosition(Gdx.graphics.getWidth() / 2 + 100 + brakePedal.getWidth(), 0);
		gasPedal.setPosition(Gdx.graphics.getWidth() / 2 - gasPedal.getWidth() / 2, 1080 - 100 - gasPedal.getHeight());
		gasPedal.setScale(3);
		pause.setPosition(Gdx.graphics.getWidth() - pause.getWidth() - 10, Gdx.graphics.getHeight() - pause.getHeight() - 10);
		// reverseIcon = new
		// Rectangle(0,100,reverseIco.getWidth()*3,reverseIco.getHeight()*3);
		pauseButton = new Rectangle(pause.getX(), 10, pause.getWidth() * 2, pause.getHeight() * 2);
		toMenu.setScale(2);
		toMenu.setPosition(1920 / 2 - toMenu.getWidth() / 2, 1080 - 350);
		// gas = new Rectangle(camera.unproject(new Vector3(100, 0,
		// 0)).x,camera.unproject(new Vector3(0, 1080, 0)).y, 10,10);
		menuPlace.setScale(4, 16);
		restart.setScale(3);
		menuPlace.setPosition(Gdx.graphics.getWidth() / 2 - menuPlace.getWidth() / 2, Gdx.graphics.getHeight() / 2 - menuPlace.getHeight() / 2);

		restart.setPosition(1920 / 2 - restart.getWidth() / 2, 1080 - 600);
		restartButton = new Rectangle(restart.getX(), 600 - restart.getHeight(), restart.getWidth() * 2, restart.getHeight() * 2);
		unPause.setPosition(1920 / 2 - restart.getWidth() / 2, 1080 - 900);
		unPause.setScale(3);
		gas = new Rectangle(gasPedal.getX(), Gdx.graphics.getHeight() - gasPedal.getY() - gasPedal.getHeight(), gasPedal.getWidth(), gasPedal.getHeight());
		brake = new Rectangle(brakePedal.getX(), Gdx.graphics.getHeight() - brakePedal.getY() - brakePedal.getHeight(), brakePedal.getWidth(), brakePedal.getHeight());
		bodyType.setScale(2);
		bodyType.setPosition(bodyType.getWidth() + 10, bodyType.getHeight() + 10);
		bodyType.rotate(90);
		//shapeType.setScale(0.5f);
		//shapeType.setPosition(bodyType.getWidth() * 2 + 10, bodyType.getHeight() - 80); // 2f
																						// *
		// bodyType.getHeight()
		// +
		// 100

		gd = new GestureDetector(this);
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(gd);
		inputMultiplexer.addProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {

			@Override
			public void onUp() {
				car.prevSpace();
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

	}

	public void putCoins() {
		Sprite coin1 = new Sprite(splash.getTexture());
		Sprite coin2 = new Sprite(splash.getTexture());
		Sprite fuel3 = new Sprite(fuel.getTexture());
		int temp = sprites.size();
		sprites.add(coin1);
		sprites.add(coin2);
		int random = MathUtils.random(0, 5);
		if (random == 3) {
			sprites.add(fuel3);
		}
		// sprites.add(splash);
		for (int i = temp; i < sprites.size(); i++) {
			int index = MathUtils.random(0, drawPoints.length - 1);
			Vector2 doop = drawPoints[index];
			sprites.get(i).setPosition(doop.x, doop.y);
			sprites.get(i).setSize(1, 1);
			// using both rectangles
			com.badlogic.gdx.math.Rectangle delp = new com.badlogic.gdx.math.Rectangle(doop.x, doop.y, sprites.get(i).getWidth(), sprites.get(i).getHeight());
			spriteBoxes.add(delp);

		}
		coinsGenerated = true;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		if (gasPedal.getBoundingRectangle().contains(x, 1080 - y) && Gdx.input.isTouched()) {
			gassing = true;
			gasPedal.setTexture(new Texture("exit.png"));
			car.freeze();
			car.getChassis().setLinearVelocity(0,0);
			car.rightWheel.setTransform(new Vector2(-12,-6),0);
			car.leftWheel.setTransform(new Vector2(-12,-6),0);
			world.setGravity(new Vector2(0,-14.81f));
			car.getChassis().setTransform(new Vector2(-12,-6), 0);
			car.unfreeze();
			
			//car.getChassis().applyLinearImpulse(new Vector2(-50, 0), car.getChassis().getPosition(), true);
		}
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		if (pauseButton.contains(x, y) && !noFuel) {
			showMenu = true;
			car.freeze();
			menuMsg = "Paused";
		}

		else if (unPause.getBoundingRectangle().contains(x, 1080 - y) && showMenu) {

			unPause.setTexture(new Texture("leftDown.png"));
			carLight.setActive(true);
			noFuel = false;
			showMenu = false;
			unPause.setTexture(new Texture("leftUp.png"));
			car.unfreeze();
		}
		if (bodyType.getBoundingRectangle().contains(x, 1080 - y)) {

			/*
			 * if (isStatic) { bodyType.setTexture(new
			 * Texture("dynamicDown.png")); isStatic = false; isDynamic = true;
			 * } else if (isDynamic) { isStatic = true; isDynamic = false;
			 * bodyType.setTexture(new Texture("dynamicUp.png")); }
			 */
			if (isMisc) {
				bodyType.setTexture(new Texture("dynamicUp.png"));
				isSquare = true;
				isMisc = false;
			} else if (isSquare) {
				isMisc = true;
				isSquare = false;
				bodyType.setTexture(new Texture("dynamicDown.png"));
			}
		}
		 else if (toMenu.getBoundingRectangle().contains(x, 1080 - y) && showMenu) {

			toMenu.setTexture(new Texture("toMenuDown.png"));
			tmpScreen = new DroidMenuScreen(mygame);
			goToMenu = true;
			backToMenu();

		} else if (restart.getBoundingRectangle().contains(x, y) && showMenu) { // restartButton.contains(x,y)
			goToMenu = true;
			restart.setTexture(new Texture("rightDown.png"));
			tmpScreen = new DemoLevel2(mygame);
			backToMenu();

			// tmp = new Vector2[2];

		}

		return true;
	}

	public void backToMenu() {
		if (screenChanger == 30) {
			mygame.setScreen(tmpScreen);
			mp3Music.dispose();
			screenChanger = 0;
		}
		screenChanger++;

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

		/*
		 * if((int)camera.zoom>0 && (int)camera.zoom<4){ camera.zoom +=
		 * -(distance/50-initialDistance/50)/300;}
		 * 
		 * else if(camera.zoom<1) { camera.zoom = 1.01f; } else
		 * if(camera.zoom>3) { camera.zoom = 3.99f; }
		 */
		return true;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {

		return false;

	}

	private void createCollisionListener() {
		world.setContactListener(new ContactListener() {

			@Override
			public void beginContact(Contact contact) {
				Fixture fixtureA = contact.getFixtureA();
				Fixture fixtureB = contact.getFixtureB();
				if((car.carParts.contains(fixtureA.getBody()) && !car.carParts.contains(fixtureB.getBody())))
				{
				
					
				}
				else if(car.carParts.contains(fixtureB.getBody()) && !car.carParts.contains(fixtureA.getBody()))
				{
	ArrayList<Vector2> bodyPoints = new ArrayList<Vector2>();

					
					ChainShape tempShape = (ChainShape) fixtureA.getShape();
					float[] verticies = new float[tempShape.getVertexCount() * 2];
					int counter = 0;
					for (int q = 0; q < tempShape.getVertexCount(); q++) {
						Vector2 linePoints = new Vector2();
						Vector2 linePoints2 = new Vector2(); //I FOUND A FUCKING BUG: FUCK THIS SHIT YO FUCK WHOEVER MADE THIS SHIT. I CAN'T FUCKING DECOMPILE YOUR DLL YOU FUCKTARD SO YOU REALLY FUCKED ME OVER HERE
						tempShape.getVertex(q, linePoints);

						Vector2 newLinePoints = fixtureA.getBody().getWorldPoint(linePoints);

						bodyPoints.add(newLinePoints);

						verticies[counter] = newLinePoints.x;
						verticies[counter + 1] = newLinePoints.y;
						counter += 2;
				}
					System.out.println(Arrays.toString(verticies));
					counter = 0;
					for(int i = 0;i<bodyPoints.size();i++)
					{
						Vector2 temp = new Vector2(verticies[counter],verticies[counter+1]);
						counter+=2;
						bodyPoints.set(i, temp);
					}
					System.out.println(bodyPoints);
					//System.exit(1);
					  Vector2 closest1 = new Vector2(0,0);
					  Vector2 closest2 = new Vector2(0,0);
					  float distance = 1000;
					  int index = 0;
					for(int i = 0;i<bodyPoints.size();i++)
					{
						
				    if(bodyPoints.get(i).dst(fixtureB.getBody().getPosition())<distance)
				    {
				    	distance = bodyPoints.get(i).dst(fixtureB.getBody().getPosition());
				    	
				    	closest1 = new Vector2(bodyPoints.get(i));
				    	
				    	index = i;
				    	
				    }
				  
						
					}
					if(index+1<bodyPoints.size())
					{
						closest2 = bodyPoints.get(index+1);
					}
					else if(index-1>=0)
					{
						closest2 = bodyPoints.get(index-1);
					}
					double slope = (closest1.y-closest2.y)/(closest1.x-closest2.x);
					float theta = (float)Math.atan(slope);
					
					theta = theta*MathUtils.radiansToDegrees;
					System.out.println(closest2 + " " + closest1 + "slope" + " " + slope + "grav " + world.getGravity());
					 //Gravity = -14.81
					
					
					
					//	world.setGravity(new Vector2((14.81f)*MathUtils.sinDeg(theta),(-14.81f)*MathUtils.cosDeg(theta))); //idk y -x yet
					if(car.getChassis().getPosition().y<closest2.y && slope == 0)
					{
					world.setGravity(new Vector2(0f,14.81f));
					}
					else if(slope == Double.NEGATIVE_INFINITY && car.getChassis().getPosition().x<=closest1.x)
					{
						world.setGravity(new Vector2(14.81f,0));
					}
					else if(slope == Double.POSITIVE_INFINITY && car.getChassis().getPosition().x>=closest1.x)
					{
						world.setGravity(new Vector2(-14.81f,0));
					}
					else
					{
						Vector2 mdpt = new Vector2((closest1.x+closest2.x)/2f,(closest1.y+closest2.y)/2f); //big D's patented algo
						
							//y-y1 = m(x-x1)
							if(car.getChassis().getPosition().y>(slope*(car.getChassis().getPosition().x-closest2.x)+closest2.y))
							{
								world.setGravity(new Vector2(14.81f*MathUtils.sinDeg(theta),-14.81f*MathUtils.cosDeg(theta)));
							}
						
						
							else
						{
							world.setGravity(new Vector2(-14.81f*MathUtils.sinDeg(theta),14.81f*MathUtils.cosDeg(theta)));
						}
						
					}
					
					
				}

			}

			@Override
			public void endContact(Contact contact) {
				Fixture fixtureA = contact.getFixtureA();
				Fixture fixtureB = contact.getFixtureB();

			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}

		});
	}

	

	public void coolText(String text) {
		if (stuntFont.getScaleX() < 10) {
			stuntFont.setScale(stuntFont.getScaleX() + .1f, stuntFont.getScaleY() + .1f);
		} else if (stuntFont.getScaleX() > 10) {
			stuntFont.setScale(1);
		}
		hudBatch.begin();
		if (text.equals(currentStunt)) {
			stuntFont.setColor(Color.WHITE);
			stuntFont.draw(hudBatch, text, Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2);
		} else {
			stuntFont.setColor(Color.RED);
			stuntFont.draw(hudBatch, text, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 100);
		}
		hudBatch.end();

	}

	public void setupMenu() {
		hudBatch.begin();
		noFuel = true;

		if (sCounter2 < .95) {
			sCounter2 += 0.01f;
		} else {
			sCounter2 = 1;
		}
		menuPlace.setAlpha(sCounter2);

		menuPlace.draw(hudBatch);
		float tempScaleX = font.getScaleX();
		float tempScaleY = font.getScaleY();
		font.setScale(8);
		font.draw(hudBatch, menuMsg, menuPlace.getX() - font.getBounds(menuMsg).width / 2 + 110, Gdx.graphics.getHeight() - 50);
		font.setScale(tempScaleX, tempScaleY);
		restart.draw(hudBatch);
		toMenu.draw(hudBatch);
		if (car.fuel > 0) {
			unPause.draw(hudBatch);
		}
		
		hudBatch.end();
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
	}

	public void setUpLevel() {
		int terrainBits = 3;
		for (int x = 0; x < terrainBits; x++) {
			Vector2[] temp = new Vector2[tmp.length + MathUtils.random(20, 40)];
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
			int sinOrCos = MathUtils.random(0, 1);
			// sinOrCos = 1; //keep for debugging purposes
			amplitude = MathUtils.random(1, 5);

			if (sinOrCos == 1) {

				while (Math.abs(tmp[tmp.length - 1].y / amplitude) > 1) {
					amplitude = MathUtils.random(1, 5);
				}
				float j = (float) Math.acos(tmp[tmp.length - 1].y / amplitude);
				j += 0.1f;
				Vector2 testPoint = new Vector2(-1, amplitude * (MathUtils.cos(j)));

				

				if (Math.abs(testPoint.y - tmp[tmp.length - 1].y) * Math.abs(testPoint.y - tmp[tmp.length - 1].y) <= (.005f * .005f)) {
					System.out.println("dslskjfksjslkjlsdjsfdfsjkdjlsjsdsfds");
					amplitude *= 1.45f;
					j += 0.2f;
				}
				// lastPointx++;

				for (int i = tmp.length; i < temp.length; i++) {
					// System.out.println(j);
					Vector2 siny = new Vector2(lastPointx, amplitude * (MathUtils.cos(j)));

					temp[i] = siny;
					lastPointx++;
					j += 0.1f;
				}
			} else {
				// lastPointx++;
				while (Math.abs(tmp[tmp.length - 1].y / amplitude) > 1) {
					amplitude = MathUtils.random(1, 5);
				}
				float j = (float) Math.asin(tmp[tmp.length - 1].y / amplitude);
				j += 0.1f;
				Vector2 testPoint = new Vector2(-1, amplitude * (MathUtils.sin(j)));
				if (Math.abs(testPoint.y - tmp[tmp.length - 1].y) * Math.abs(testPoint.y - tmp[tmp.length - 1].y) <= (.005f * .005f)) {
					amplitude *= 1.45f;
					j += 0.2f;
					System.out.println("dslskjfksjslkjlsdjsfdfsjkdjlsjsdsfds");
				}

				// amplitude = MathUtils.random(2, 5);
				for (int i = tmp.length; i < temp.length; i++) {
					// System.out.println(j);
					Vector2 siny = new Vector2(lastPointx, amplitude * (MathUtils.sin(j)));

					temp[i] = siny;
					lastPointx++;
					j += 0.1f;
				}
			}
			tmp = temp;

			// world.destroyBody(terrain);
			// ChainShape groundShape2 = new ChainShape();
			// groundShape2.createChain(tmp);
			//
			// fixtureDef.shape = groundShape2;
			// fixtureDef.friction = .5f;
			// fixtureDef.restitution = 0;
			// BodyDef bodyDef = new BodyDef();
			//
			// terrain = world.createBody(bodyDef);
			// ground = terrain.createFixture(fixtureDef);

			lastPointx = tmp[tmp.length - 1].x;
			lastPointy = tmp[tmp.length - 1].y;
		}
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, 0);
		PolygonShape groundShape = new PolygonShape();
		Vector2[] tmep = new Vector2[tmp.length];
		for (int i = tmp.length - 1; i >= 0; i--) {
			tmep[tmp.length - i - 1] = tmp[i];
		}
		tmp = tmep;
		ArrayList<Vector2[]> terrainSplittr = new ArrayList<Vector2[]>();
		for (int x = 0; x < tmp.length - 5; x++) {
			if (x == 0 || x % 5 == 0) {
				Vector2[] test = new Vector2[6];
				test[0] = tmp[x];
				test[1] = tmp[x + 1];
				test[2] = tmp[x + 2];
				test[3] = tmp[x + 3];
				test[4] = tmp[x + 4];
				test[5] = tmp[x + 5];
				terrainSplittr.add(test);
			}
		}

		for (int x = 0; x < terrainSplittr.size(); x++) {
			groundShape.set(terrainSplittr.get(x));
			fixtureDef.shape = groundShape;
			fixtureDef.friction = .5f;
			fixtureDef.restitution = 0;
			world.createBody(bodyDef).createFixture(fixtureDef);
			// groundShape.dispose();
		}
	}

	public void freeze() // call when pausing game
	{
		for (int i = 0; i < tmpBodies.size; i++) {
			tmpBodies.get(i).setActive(false);
		}
		car.freeze();
	}

	public void unFreeze() // call when unpausing game
	{
		for (int i = 0; i < tmpBodies.size; i++) {
			tmpBodies.get(i).setActive(true);
		}
		car.unfreeze();

	}

	public static Vector2[] removeDuplicates(Vector2[] arr) {
		ArrayList<Vector2> temporary = new ArrayList<Vector2>();
		for (int i = 0; i < arr.length; i++) {
			temporary.add(arr[i]);
		}
		for (int i = 0; i < temporary.size(); i++) {
			for (int k = 0; k < temporary.size(); k++) {
				if (k != i) {
					if (temporary.get(i).equals(temporary.get(k))) {
						temporary.remove(k);
						if (i != 0) {
							i--;
							// break;
						}
						break;

					}
				}
			}
		}
		arr = new Vector2[temporary.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = temporary.get(i);
		}
		return arr;

		
	}

	public static ArrayList<Vector2> removeDuplicates(ArrayList<Vector2> arr) {
		ArrayList<Vector2> temporary = new ArrayList<Vector2>();
		for (int i = 0; i < arr.size(); i++) {
			temporary.add(arr.get(i));
		}
		for (int i = 0; i < temporary.size(); i++) {
			for (int k = 0; k < temporary.size(); k++) {
				if (k != i) {
					if (temporary.get(i).equals(temporary.get(k))) {
						temporary.remove(k);
						if (i != 0) {
							i--;
							// break;
						}
						break;

					}
				}
			}
		}
		return temporary;

		/*
		 * ArrayList<Vector2> temporary = new ArrayList<Vector2>(); for(int i =
		 * 0;i<arr.size();i++) { temporary.add(arr.get(i)); } for(int i =0
		 * ;i<temporary.size()-1;i++) {
		 * if(temporary.get(i).equals(temporary.get(i+1))) {
		 * temporary.remove(i+1); } } return temporary;
		 */
	}

	public static int indexOfVector2(Vector2[] arr, Vector2 element) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(element)) {
				return i;
			}
		}
		return -1;
	}

	public static int indexOfVector2(ArrayList<Vector2> arr, Vector2 element) {
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).equals(element)) {
				return i;
			}
		}

		return -1;
	}

	public static ArrayList<Vector2> toArrayList(Vector2[] arr) {
		ArrayList<Vector2> newArr = new ArrayList<Vector2>();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null) // take this away if stuff goes wrong.
			{
				newArr.add(arr[i]);
			}
		}
		return newArr;
	}

	public static Vector2[] toArrayList(ArrayList<Vector2> arr) {
		Vector2[] newArr = new Vector2[arr.size()];
		for (int i = 0; i < arr.size(); i++) {
			newArr[i] = (arr.get(i));
		}
		return newArr;
	}
	public void optimizeOffScreen()
	{
		Vector3 mouseCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

		
		Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		worldCoordinates = camera.unproject(worldCoordinates);
		for (Body body : tmpBodies){
			com.badlogic.gdx.math.Rectangle rect = new com.badlogic.gdx.math.Rectangle(body.getPosition().x-3,body.getPosition().y-3,10,10);
			if(rect.contains(worldCoordinates.x,worldCoordinates.y) && Gdx.input.isKeyPressed(Keys.Q) && body.getType().equals(BodyType.DynamicBody) && body.equals(car.getChassis()) )
			{
				body.setTransform(worldCoordinates.x, worldCoordinates.y, 0);

				
			}
	}
	}

}