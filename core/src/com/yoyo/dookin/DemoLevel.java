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
import com.badlogic.gdx.physics.box2d.Shape.Type;
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
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class DemoLevel extends Game implements Screen, GestureListener {
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private SpriteBatch batch;
	private SpriteBatch hudBatch;
	Sprite bgSprite1 = new Sprite(new Texture("bg.png"));
	Sprite fgSprite2 = new Sprite(new Texture("foreground2.png"));
	Sprite fgSprite3 = new Sprite(new Texture("foreground2.png"));
	Sprite bodyType = new Sprite(new Texture("dynamicUp.png"));
	Sprite shapeType = new Sprite(new Texture("drawType.png"));
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
	FixtureDef fixtureDef = new FixtureDef(),
			wheelFixtureDef = new FixtureDef();
	Sprite splash = new Sprite(new Texture("coin.png"));
	Sprite coin = new Sprite(new Texture("coin.png"));
	private boolean inAir = false;
	private final float TIMESTEP = 1 / 60f;
	public float scaleFactor = (50 * Gdx.graphics.getWidth()) / 1920f;
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
	private boolean coinsGenerated = false;
	private boolean justStarted = true;
	private ArrayList<Body> drawnBodies = new ArrayList<Body>();
	private ArrayList<Vector2[]> pointsofBodies = new ArrayList<Vector2[]>();
	private ArrayList<Vector2> initialPositions = new ArrayList<Vector2>();
	private boolean isSquare;
	private boolean isCircle;
	private boolean isMisc = true;
	private boolean miscShapeDrawing = true;
	private float sizeCounter = 0;
	ArrayList<Vector2> updatedPoints = new ArrayList<Vector2>();
	private boolean rendered = false;

	public DemoLevel(Game game) {
		this.mygame = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		if (!Gdx.input.isTouched(2)) {
			camera.position.set(
					car.getChassis().getPosition().x + 10,
					car.getChassis().getPosition().y + .004f
					* Gdx.graphics.getHeight(), 0);
			camera.update();
		}
		handleInput();
		hudBatch.begin();
		hudBatch.draw(bgSprite1, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		// hudBatch.draw(fgSprite2,fgParallax,(720-camera.unproject(new
		// Vector3(car.getChassis().getPosition(),0)).y)-600); //720/2-300
		hudBatch.draw(fgSprite2, fgParallax, fgParallay - 100,
				fgSprite2.getWidth() * 1.5f, fgSprite2.getHeight() * 1.5f);
		hudBatch.draw(fgSprite3, fgParallax + (fgSprite2.getWidth() * 1.5f),
				fgParallay - 100, fgSprite2.getWidth() * 1.5f,
				fgSprite2.getHeight() * 1.5f);

		hudBatch.end();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (int i = 0; i < sprites.size(); i++) {
			sprites.get(i).draw(batch);
		}
		batch.end();

		if (carLight.getDistance() < 100) {
			carLight.setDistance(carLight.getDistance() + 1);

		}

		if (car != null) {
			// car.prevW();
			float tScale = car.getChassis().getLinearVelocity().y / 50;
			if (camera.viewportHeight + tScale >= Gdx.graphics.getHeight() / 50
					&& camera.viewportHeight + tScale < 1900 / 50) {
				camera.viewportHeight += tScale;
				camera.viewportWidth = (camera.viewportHeight * (Gdx.graphics
						.getWidth() / 50f)) / (Gdx.graphics.getHeight() / 50f);

			}
			carBox.setPosition(car.getChassis().getPosition().x, car
					.getChassis().getPosition().y); // used to be setposition
		}
		for (int i = 0; i < spriteBoxes.size(); i++) {

			if (Intersector.overlaps(carBox, spriteBoxes.get(i))) {
				if (sprites.get(i).getTexture().equals(fuel.getTexture())) {
					if (car.fuel <= 90) {
						car.fuel += 10;
					}
				}
				sprites.remove(i);
				spriteBoxes.remove(i);
				// System.out.println("intersected");
				fpScore += 5;
				if (car.jumpjuice <= 95) {
					car.jumpjuice += 1;
				}
				car.getChassis().applyLinearImpulse(
						new Vector2(190 * car.direction, 0),
						car.getChassis().getPosition(), true);

				break;
			}
		}

		rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.updateAndRender();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		world.getBodies(tmpBodies);
		for (Body body : tmpBodies)
			if (body.getUserData() instanceof Sprite
					&& !body.equals(car.chassis)
					&& !body.equals(car.rightWheel)
					&& !body.equals(car.leftWheel)) {
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
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (Body body : car.carParts) {

			if (body.getUserData() instanceof Sprite) {
				Sprite sprite = (Sprite) body.getUserData();
				sprite.setPosition(

						body.getPosition().x - sprite.getWidth() / 2,
						body.getPosition().y - sprite.getHeight() / 2);
				sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
				sprite.draw(batch);

			}
		}
		batch.end();
		if (tmp.length != 0) {
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Line);

			for (int i = 0; i < tmp.length - 1; i++) {
				shapeRenderer.setColor(Color.BLUE);
				shapeRenderer.line(tmp[i].x, tmp[i].y, tmp[i + 1].x,
						tmp[i + 1].y);
			}

		}
		shapeRenderer.end();

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		if (drawnBodies.size() > 0) {

			for (int i = 0; i < tmpBodies.size; i++) {
				// if( tmpBodies.get(i).getType().equals(BodyType.DynamicBody)
				// &&
				// tmpBodies.get(i).getFixtureList().get(0).getType().equals(Shape.Type.Circle)
				// && !tmpBodies.get(i).equals(car.rightWheel)&&
				// !tmpBodies.get(i).equals(car.leftWheel))
				// {
				// shapeRenderer.circle(tmpBodies.get(i).getPosition().x,
				// tmpBodies.get(i).getPosition().y,
				// tmpBodies.get(i).getFixtureList().get(0).getShape().getRadius(),360);
				// }
			}

		}
		shapeRenderer.end();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		if (updatedPoints.size() != 0) {
			for (int i = 0; i < updatedPoints.size(); i++) {
				shapeRenderer.setColor(1, 1, 0, 1);

				// shapeRenderer.line(bodyPoints.get(z).x,bodyPoints.get(z).y,updatedPoints.get(z+1).x,updatedPoints.get(z+1).y);

				// shapeRenderer.line(car.getChassis().getPosition(),bodyPoints.get(0));
				Gdx.app.log("points2: ", updatedPoints.get(i) + "");
				Gdx.app.log("points: ", updatedPoints.get(0) + " "
						+ updatedPoints.get(1) + "");

			}

		}
		shapeRenderer.end();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		if (drawPoints != null) {
			for (int x = 0; x < drawPoints.length - 1; x++) {
				shapeRenderer.setColor(1, 1, 0, 1);
				shapeRenderer.line(drawPoints[x].x, drawPoints[x].y,
						drawPoints[x + 1].x, drawPoints[x + 1].y);
			}
		}
		shapeRenderer.end();
		debugRenderer.setDrawBodies(false);
		update();
		tOD += 0.1f;
		if (tOD <= 10) {

		} else if (tOD > 100 && tOD < 250) {

			// System.out.println(tOD);
			carLight2.setColor(1f / colorChanger, 1f / colorChanger,
					colorChanger, 1);
			colorChanger += 0.002f;
		} else if (tOD >= 250 && tOD < 500) {
			carLight2.setDistance(carLight2.getDistance() - 0.1f);
		} else if (tOD >= 500 && tOD < 10000) {
			tOD = 0;
			colorChanger = 0;
			carLight2.setColor(Color.ORANGE);
		}

		// shapeDebugger.setProjectionMatrix(camera.combined);
		Color doop = new Color(Color.CYAN);

		shapeDebugger.setColor(doop);
		shapeDebugger.begin(ShapeType.Filled);

		// shapeDebugger.rect(Gdx.graphics.getWidth()/2-(car.jumpjuice * 3)/2,
		// 0, car.jumpjuice * 3, 10 * 4);
		shapeDebugger.setColor(Color.TEAL);
		if (car.fuel > 0) {
			shapeDebugger.rect(
					Gdx.graphics.getWidth() / 2 - (car.fuel * 3) / 2, 0,
					car.fuel * 3, 10 * 4);
		}
		// shapeDebugger.rect(0, Gdx.graphics.getHeight()-200,3*50,3*50);
		shapeDebugger.end();

		shapeRenderer.end();
		hudBatch.begin();
		gasPedal.draw(hudBatch);
		bodyType.draw(hudBatch);
		coin.draw(hudBatch);
		shapeType.draw(hudBatch);
		fuel.draw(hudBatch);
		pause.draw(hudBatch);
		font.setScale(1.9f);
		// font.draw(hudBatch, "Free Play: Meters Driven: "+ (int)
		// car.getChassis().getPosition().x / 25, 0, resY - 23);
		// font.draw(hudBatch,"Speed: "+ (int)
		// ((car.getChassis().getLinearVelocity().x * 3600f) / 1000f)+ "K/H", 0,
		// resY - 53);
		// font.draw(hudBatch, "Terrain bits generated: " + totalTerrain, resX-
		// font.getBounds("Terrain bits generated: 230").width, resY-
		// font.getBounds("Terrain bits generated").height); //
		// camera.viewportHeight+680
		// font.draw(hudBatch, "JumpJuice:         " , 500, 50);
		font.draw(hudBatch, "Ink:         ", 500, 50);
		font.setScale(3);
		font.draw(hudBatch, "Coins: " + fpScore, 10,
				Gdx.graphics.getHeight() - 30);
		font.setScale(1.9f);
		font.setColor(Color.WHITE);
		// font.draw(hudBatch, "Stunt Done: " + currentStunt,
		// (Gdx.graphics.getWidth()/2-font.getBounds("Stunt Done: "+currentStunt).width/2),
		// font.getBounds("T").height+10);
		// font.draw(hudBatch, "Secondary Stunt Done: " + currentStunt2,
		// (Gdx.graphics.getWidth()/2-font.getBounds("Secondary Stunt Done: "+currentStunt2).width/2),
		// font.getBounds("T").height*2+10);
		font.setColor(Color.WHITE);
		font.setScale(1f);

		hudBatch.end();
		camera.update();

		if (car.jumpjuice < 100) {
			car.jumpjuice += 0.09f;
		}
		if (carLight2.getDistance() <= 10) {
			headLight.setDistance(20.0f);
		}
		if (car.getChassis().getPosition().x > 130) {
			if (headLight.getDistance() > 0) {
				headLight.setDistance(headLight.getDistance() - 1);
			}

			carLight2.setPosition(car.getChassis().getPosition().x + 5, car
					.getChassis().getPosition().y + 8);
			// carLight2.setPosition(camera.unproject(new
			// Vector3(875,0,0)).x,camera.unproject(new Vector3(0,250,0)).y);
			carLight.setActive(false);

			// carLight2.setPosition(camera.unproject(new
			// Vector3(875,0,0)).x,camera.unproject(new Vector3(0,22,0)).y);

			// +10 for x, +18 for y

			if (carLight2.getDistance() < 40 && (tOD > 500 || (int) tOD == 0)) {
				carLight2.setActive(true);
				carLight2.setDistance(carLight2.getDistance() + 1);
				tOD = 0;
			}

		}

		if (Gdx.input.isTouched(2)) {
			// System.out.println(Gdx.input.getX()/25 + " " +
			// camera.position.x);
			Vector3 worldCoordinates = new Vector3(Gdx.input.getX(),
					Gdx.input.getY(), 0);
			worldCoordinates = camera.unproject(worldCoordinates);
			// System.out.println(camera.unproject(worldCoordinates).y);
			// System.out.println(Gdx.input.getX()-(camera.viewportWidth+camera.position.x));
			car.getChassis().setTransform(worldCoordinates.x,
					worldCoordinates.y, 0);
			// leftWheel2.setAwake(false);
			// leftWheel2.setLinearVelocity(0, 0);
			Vector2 tempCoords = new Vector2(car.getChassis().getPosition());
			// camera.position.set(0, 0,0);
			// camera.update();
			car.getChassis().setLinearVelocity(Gdx.input.getDeltaX() / 5,
					-Gdx.input.getDeltaY() / 5);
		}

	}

	private void handleInput() {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();

		}
		if (Gdx.input.isTouched(1)) {

		} else if (Gdx.input.isTouched(0)) {
			// car.prevS();
		} else if (!Gdx.input.isTouched()) {
			car.prevKeyUp();
		} else if (car.getChassis().getLinearVelocity().y > 1
				&& Gdx.input.isTouched(1)) {
			Gdx.app.log("hi", (Gdx.input.getPitch() + ""));
			car.prevD();

		} else if (car.getChassis().getLinearVelocity().y > 1
				&& Gdx.input.isTouched(0)) {
			Gdx.app.log("hi", (Gdx.input.getPitch() + ""));
			car.prevA();

		}

	}

	private void update() {
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		if (tmpBodies.size > 2 && drawnBodies.size() > 0)
			for (int i = 0; i < tmpBodies.size; i++) {
				if (tmpBodies.get(i).getType().equals(BodyType.DynamicBody)
						&& !tmpBodies.get(i).equals(terrain)
						&& (tmpBodies.get(i).getFixtureList().get(0).getType()
								.equals(Shape.Type.Chain)) && !rendered) // tmpBodies.get(i).getFixtureList().get(0).getShape().equals(Type.Chain)
				{

					ChainShape tempShape = new ChainShape();
					ArrayList<Vector2> bodyPoints = new ArrayList<Vector2>();

					ArrayList<Vector2> testicle = new ArrayList<Vector2>();
					updatedPoints = bodyPoints;
					tempShape = (ChainShape) tmpBodies.get(i).getFixtureList()
							.get(0).getShape();
					float[] verticies = new float[tempShape.getVertexCount() * 2];
					int counter = 0;
					for (int q = 0; q < tempShape.getVertexCount(); q++) {
						Vector2 linePoints = new Vector2();
						Vector2 linePoints2 = new Vector2();
						tempShape.getVertex(q, linePoints);
						// tempShape.getVertex(q+1, linePoints2);
						Vector2 newLinePoints = tmpBodies.get(i).getWorldPoint(
								linePoints);
						// Vector2 newLinePoints2 =
						// tmpBodies.get(i).getWorldPoint(linePoints2);
						bodyPoints.add(newLinePoints);
						// updatedPoints.add(newLinePoints2);
						// testicle.add(newLinePoints2);
						// Gdx.app.log("points: ", updatedPoints.get(q)+"");
						// Gdx.app.exit();

						// Vector2 newLinePoints2 =
						// tmpBodies.get(i).getWorldPoint(linePoints2);
						// bodyPoints.add(newLinePoints2);

						verticies[counter] = newLinePoints.x;
						verticies[counter + 1] = newLinePoints.y;
						counter += 2;
						shapeRenderer.setColor(1, 1, 0, 1);
						// shapeRenderer.circle(updatedPoints.get(q).x,updatedPoints.get(q).y,0.25f);
						// shapeRenderer.line(bodyPoints.get(q).x,bodyPoints.get(q).y,testicle.get(q).x,testicle.get(q).y);

						// Gdx.app.log("MOUSESHIT",camera.unproject(new
						// Vector3(Gdx.input.getX(),0,0)).x+"" ); //<<--THIS IS
						// FUCKING PROOF THAT IT WORKS BUT I CAN'T FUCKING
						// RENDER IT.

					}

					camera.update();

					// rendered = true;
					if (verticies.length > 4) {
						shapeRenderer.polygon(verticies);
					}
				} else if (tmpBodies.get(i).getType()
						.equals(BodyType.DynamicBody)
						&& !tmpBodies.get(i).equals(terrain)
						&& !tmpBodies.get(i).equals(car.chassis)
						&& (tmpBodies.get(i).getFixtureList().get(0).getType()
								.equals(Shape.Type.Polygon)) && !rendered) // tmpBodies.get(i).getFixtureList().get(0).getShape().equals(Type.Chain)
				{

					PolygonShape tempShape = new PolygonShape();
					ArrayList<Vector2> bodyPoints = new ArrayList<Vector2>();

					ArrayList<Vector2> testicle = new ArrayList<Vector2>();
					updatedPoints = bodyPoints;
					for (int a = 0; a < tmpBodies.get(i).getFixtureList().size; a++) {

						tempShape = (PolygonShape) tmpBodies.get(i)
								.getFixtureList().get(a).getShape();
						float[] verticies = new float[tempShape
						                              .getVertexCount() * 2];
						int counter = 0;
						for (int q = 0; q < tempShape.getVertexCount(); q++) {
							Vector2 linePoints = new Vector2();
							Vector2 linePoints2 = new Vector2();
							tempShape.getVertex(q, linePoints);
							// tempShape.getVertex(q+1, linePoints2);
							Vector2 newLinePoints = tmpBodies.get(i)
									.getWorldPoint(linePoints);
							// Vector2 newLinePoints2 =
							// tmpBodies.get(i).getWorldPoint(linePoints2);
							bodyPoints.add(newLinePoints);
							// updatedPoints.add(newLinePoints2);
							// testicle.add(newLinePoints2);
							// Gdx.app.log("points: ", updatedPoints.get(q)+"");
							// Gdx.app.exit();

							// Vector2 newLinePoints2 =
							// tmpBodies.get(i).getWorldPoint(linePoints2);
							// bodyPoints.add(newLinePoints2);

							verticies[counter] = newLinePoints.x;
							verticies[counter + 1] = newLinePoints.y;
							counter += 2;
							shapeRenderer.setColor(1, 1, 0, 1);
							// shapeRenderer.circle(updatedPoints.get(q).x,updatedPoints.get(q).y,0.25f);
							// shapeRenderer.line(bodyPoints.get(q).x,bodyPoints.get(q).y,testicle.get(q).x,testicle.get(q).y);

							// Gdx.app.log("MOUSESHIT",camera.unproject(new
							// Vector3(Gdx.input.getX(),0,0)).x+"" ); //<<--THIS
							// IS FUCKING PROOF THAT IT WORKS BUT I CAN'T
							// FUCKING RENDER IT.

						}
						if (verticies.length > 4) {
							shapeRenderer.polygon(verticies);
						}
					}
				}

				camera.update();

			}
		for (int i = 0; i < drawnBodies.size(); i++) {
			if (drawnBodies.get(i).getType().equals(BodyType.StaticBody)
					&& !drawnBodies.get(i).getFixtureList().get(0).getType()
					.equals(Shape.Type.Polygon)) {
				for (int x = 0; x < pointsofBodies.get(i).length - 1; x++) {
					shapeRenderer.setColor(Color.WHITE);
					shapeRenderer.line(pointsofBodies.get(i)[x],
							pointsofBodies.get(i)[x + 1]);
				}
			}
		}
		shapeRenderer.end();

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
		if (showMenu) {
			carLight.setActive(false);

		}
		if (car.rightWheel.getPosition().y > car.leftWheel.getPosition().y + 2) {
			car.prevA();
		} else if (car.leftWheel.getPosition().y > car.rightWheel.getPosition().y + 2) {
			car.prevD();
		}
		if (justStarted) {

			Vector3 carCoords = new Vector3(car.getChassis().getPosition(), 0);
			carCoords = camera.unproject(carCoords);
			if (stuntFont.getScaleX() < 20) {
				stuntFont.setScale(stuntFont.getScaleX() + .2f,
						stuntFont.getScaleY() + .2f);
			} else if (stuntFont.getScaleX() > 20) {
				stuntFont.setScale(1);
				sCounter1 += 1;
				if (sCounter1 == 1) {
					stuntFont.setColor(Color.WHITE);
					currentStunt = "Ready?";
				} else if (sCounter1 == 2) {
					currentStunt = "Set";
				} else if (sCounter1 == 3) {
					stuntFont.setColor(Color.GREEN);
					currentStunt = "Go!";
				} else if (sCounter1 == 4) {
					justStarted = false;
					unFreeze();

				}
			}
			hudBatch.begin();
			stuntFont.draw(hudBatch, currentStunt,
					Gdx.graphics.getWidth() / 2 - 100,
					Gdx.graphics.getHeight() / 2);
			hudBatch.end();

		}
		if (car.getChassis().getPosition().y < -310) {
			showMenu = true;
			noFuel = true;
			sCounter2 = 0;
		}

		if (goToMenu) {
			backToMenu();
		}

		if (Gdx.input.isTouched()
				&& !drawing
				&& !noFuel
				&& isSquare
				&& !bodyType.getBoundingRectangle().contains(Gdx.input.getX(),
						1080 - Gdx.input.getY())) {
			FixtureDef fixtureDef2 = new FixtureDef();
			coinsGenerated = false;
			drawing = true;

			Vector3 mouseCoords = new Vector3(Gdx.input.getX(),
					Gdx.input.getY(), 0);
			drawPoints = new Vector2[1];
			Vector3 worldCoordinates = new Vector3(Gdx.input.getX(),
					Gdx.input.getY(), 0);
			worldCoordinates = camera.unproject(worldCoordinates);
			// System.out.println(camera.unproject(worldCoordinates).y);
			// System.out.println(Gdx.input.getX()-(camera.viewportWidth+camera.position.x));

			drawPoints[0] = new Vector2(worldCoordinates.x, worldCoordinates.y);
			// drawPoints[0] = new
			// Vector2(camera.unproject(mouseCoords).x,camera.unproject(mouseCoords).y);
			// drawPoints[1] = new
			// Vector2(camera.unproject(mouseCoords).x+0.01f,camera.unproject(mouseCoords).y+0.01f);
			// currentShape.createChain(drawPoints);

		} else if (Gdx.input.isTouched() && drawing && isSquare && !gassing) {
			Vector2[] tempPoints = new Vector2[drawPoints.length + 1];

			for (int i = 0; i < drawPoints.length; i++) {
				tempPoints[i] = drawPoints[i];
			}
			drawPoints = tempPoints;
			Vector3 worldCoordinates = new Vector3(Gdx.input.getX(),
					Gdx.input.getY(), 0);
			worldCoordinates = camera.unproject(worldCoordinates);
			drawPoints[drawPoints.length - 1] = new Vector2(worldCoordinates.x,
					worldCoordinates.y);

		} else if (Gdx.input.isTouched() && isCircle && !miscShapeDrawing) {

			BodyDef bodyDef = new BodyDef();

			doop = world.createBody(bodyDef);
			CircleShape wheelShape = new CircleShape();
			sizeCounter = 1;
			wheelShape.setRadius(sizeCounter / 50);
			Vector3 mouseCoords = new Vector3(Gdx.input.getX(),
					Gdx.input.getY(), 0);
			Vector3 worldCoordinates = new Vector3(Gdx.input.getX(),
					Gdx.input.getY(), 0);
			worldCoordinates = camera.unproject(worldCoordinates);
			wheelFixtureDef.shape = wheelShape;

			doop.createFixture(wheelFixtureDef);
			doop.setTransform(worldCoordinates.x, worldCoordinates.y, 176);

			miscShapeDrawing = true;
		} else if (Gdx.input.isTouched() && isCircle && miscShapeDrawing) {
			car.fuel -= 0.1f;
			world.destroyBody(doop);
			BodyDef bodyDef = new BodyDef();
			if (isStatic) {
				bodyDef.type = BodyType.StaticBody;
			} else if (isDynamic) {
				bodyDef.type = BodyType.DynamicBody;
			}
			CircleShape wheelShape = new CircleShape();
			wheelShape.setRadius(sizeCounter / 50);
			Vector3 mouseCoords = new Vector3(Gdx.input.getX(),
					Gdx.input.getY(), 0);
			Vector3 worldCoordinates = new Vector3(Gdx.input.getX(),
					Gdx.input.getY(), 0);
			worldCoordinates = camera.unproject(worldCoordinates);
			wheelFixtureDef.shape = wheelShape;
			doop = world.createBody(bodyDef);
			doop.createFixture(wheelFixtureDef);
			doop.setTransform(worldCoordinates.x, worldCoordinates.y, 176);
			sizeCounter++;

			// miscShapeDrawing = false;
		} else if (Gdx.input.isTouched()
				&& !drawing
				&& !noFuel
				&& !gassing
				&& !bodyType.getBoundingRectangle().contains(Gdx.input.getX(),
						1080 - Gdx.input.getY())) {
			// FixtureDef fixtureDef2 = new FixtureDef();
			coinsGenerated = false;
			drawing = true;

			Vector3 mouseCoords = new Vector3(Gdx.input.getX(),
					Gdx.input.getY(), 0);
			drawPoints = new Vector2[1];
			Vector3 worldCoordinates = new Vector3(Gdx.input.getX(),
					Gdx.input.getY(), 0);
			worldCoordinates = camera.unproject(worldCoordinates);
			// System.out.println(camera.unproject(worldCoordinates).y);
			// System.out.println(Gdx.input.getX()-(camera.viewportWidth+camera.position.x));

			drawPoints[0] = new Vector2(worldCoordinates.x, worldCoordinates.y);
			// drawPoints[0] = new
			// Vector2(camera.unproject(mouseCoords).x,camera.unproject(mouseCoords).y);
			// drawPoints[1] = new
			// Vector2(camera.unproject(mouseCoords).x+0.01f,camera.unproject(mouseCoords).y+0.01f);
			// currentShape.createChain(drawPoints);
			fixtureDef.friction = .5f;
			fixtureDef.restitution = 0;
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.StaticBody;
			doop = world.createBody(bodyDef);

		} else if (Gdx.input.isTouched() && drawing && !noFuel && !gassing) {
			Vector2[] tempPoints = new Vector2[drawPoints.length + 1];

			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.StaticBody;
			world.destroyBody(doop);

			doop = world.createBody(bodyDef);
			// FixtureDef fixtureDef3 = new FixtureDef();
			Vector3 mouseCoords = new Vector3(Gdx.input.getX(),
					Gdx.input.getY(), 0);
			for (int i = 0; i < drawPoints.length; i++) {
				tempPoints[i] = drawPoints[i];
			}
			drawPoints = tempPoints;
			Vector3 worldCoordinates = new Vector3(Gdx.input.getX(),
					Gdx.input.getY(), 0);
			worldCoordinates = camera.unproject(worldCoordinates);
			drawPoints[drawPoints.length - 1] = new Vector2(worldCoordinates.x,
					worldCoordinates.y);

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
		if (!Gdx.input.isTouched()) {
			if (drawPoints != null && drawPoints.length > 1 && !coinsGenerated) {

				// doop.setFixedRotation(false);
				//putCoins();
				coinsGenerated = true;
				if (isSquare && drawPoints.length > 3) {
					// currentShape = new ChainShape();
					// currentShape.createChain(drawPoints);
					PolygonShape nelf = new PolygonShape();
					PolygonShape nelf2 = new PolygonShape();

					Vector2[] tmep = new Vector2[drawPoints.length];
					for (int i = drawPoints.length - 1; i >= 0; i--) {
						tmep[drawPoints.length - i - 1] = drawPoints[i];
					}
					drawPoints = tmep;
					ArrayList<Vector2[]> terrainSplittr = new ArrayList<Vector2[]>();
					for (int x = 0; x < drawPoints.length - 4; x++) {
						if (x == 0 || x % 4 == 0) {
							Vector2[] test = new Vector2[5];
							test[0] = drawPoints[x];
							test[1] = drawPoints[x + 1];
							test[2] = drawPoints[x + 2];
							test[3] = drawPoints[x + 3];
							test[4] = drawPoints[x + 4];
							terrainSplittr.add(test);
						}
					}
					ArrayList<Body> semiFinished = new ArrayList<Body>();
					for (int x = 0; x < terrainSplittr.size() - 1; x += 2) {
						BodyDef bodyDef = new BodyDef();
						bodyDef.type = BodyType.DynamicBody;
						nelf.set(terrainSplittr.get(x));
						nelf2.set(terrainSplittr.get(x + 1));

						fixtureDef.shape = nelf;
						fixtureDef.friction = .5f;
						fixtureDef.restitution = 0;
						bodyDef.position.set(0, 0);

						// bodyDef.fixedRotation = false;
						Body testPolygonShape1 = world.createBody(bodyDef);
						testPolygonShape1.createFixture(fixtureDef);

						fixtureDef.shape = nelf2;
						fixtureDef.friction = .5f;
						fixtureDef.restitution = 0;
						Body testPolygonShape2 = world.createBody(bodyDef);
						semiFinished.add(testPolygonShape1);
						// semiFinished.add(testPolygonShape2);
						testPolygonShape2.createFixture(fixtureDef);
						WeldJointDef axisDef = new WeldJointDef();
						testPolygonShape1.setFixedRotation(false);
						testPolygonShape2.setFixedRotation(false);
						axisDef.bodyA = testPolygonShape1;
						axisDef.bodyB = testPolygonShape2;
						axisDef.localAnchorA
						.set(testPolygonShape1
								.getLocalPoint(testPolygonShape2
										.getPosition()));
						axisDef.localAnchorB
						.set(testPolygonShape2
								.getLocalPoint(testPolygonShape1
										.getPosition()));
						axisDef.collideConnected = false;
						// world.createJoint(axisDef);
						testPolygonShape1.createFixture(testPolygonShape2
								.getFixtureList().get(0).getShape(),
								testPolygonShape2.getFixtureList().get(0)
								.getDensity());
						world.destroyBody(testPolygonShape2);

					}
					int i = 0;
					while (semiFinished.size() > 1 && i + 1 < semiFinished.size()) {
						Body testPolygonShape1 = semiFinished.get(i);
						if (i + 1 < semiFinished.size()) {
							Body testPolygonShape2 = semiFinished.get(i + 1);
							for (int x = 0; x < testPolygonShape2.getFixtureList().size; x++) {
								testPolygonShape1.createFixture(testPolygonShape2.getFixtureList().get(x).getShape(),testPolygonShape2.getFixtureList().get(x).getDensity());
							}
							world.destroyBody(semiFinished.get(i+1));
							semiFinished.remove(i+1);
							i++;
						}
					}
					 i = 0;
					while (semiFinished.size() > 1 && i + 1 < semiFinished.size()) {
						Body testPolygonShape1 = semiFinished.get(i);
						if (i + 1 < semiFinished.size()) {
							Body testPolygonShape2 = semiFinished.get(i + 1);
							for (int x = 0; x < testPolygonShape2.getFixtureList().size; x++) {
								testPolygonShape1.createFixture(testPolygonShape2.getFixtureList().get(x).getShape(),testPolygonShape2.getFixtureList().get(x).getDensity());
							}
							world.destroyBody(semiFinished.get(i+1));
							semiFinished.remove(i+1);
							i++;
						}
					}
					 i = 0;
						while (semiFinished.size() > 1 && i + 1 < semiFinished.size()) {
							Body testPolygonShape1 = semiFinished.get(i);
							if (i + 1 < semiFinished.size()) {
								Body testPolygonShape2 = semiFinished.get(i + 1);
								for (int x = 0; x < testPolygonShape2.getFixtureList().size; x++) {
									testPolygonShape1.createFixture(testPolygonShape2.getFixtureList().get(x).getShape(),testPolygonShape2.getFixtureList().get(x).getDensity());
								}
								world.destroyBody(semiFinished.get(i+1));
								semiFinished.remove(i+1);
								i++;
							}
						}

				}
				if (isCircle) {

					miscShapeDrawing = false;

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
		if (car.fuel < 0) {

			currentStunt = "";
			currentStunt2 = "";
			noFuel = true;
			menuMsg = "GAME OVER";

			showMenu = true;

		} else {
			if (gassing) {
				car.fuel -= (float) (Math.abs(car.getChassis()
						.getLinearVelocity().x) * .00791f) + .1f;
			}
		}

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

		camera.viewportWidth = (int) width / scaleFactor; // to make things
		// simpler i'm
		// making everything
		// constant. I've had to
		// guess too much with the
		// previous stuff
		// previously = width/25 etc.
		camera.viewportHeight = (int) height / scaleFactor;

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
		// rayHandler.dispose();
		// camera2 = new OrthographicCamera();
		// camera2.viewportWidth = Gdx.graphics.getWidth()/50;
		// camera2.viewportWidth = Gdx.graphics.getHeight()/50;
		// +camera2.update();
		mp3Music = Gdx.audio.newMusic(Gdx.files.internal("Cipher2.mp3"));
		mp3Music.play();
		createCollisionListener();
		// splash.scale(.005f);
		coin.setPosition(320, Gdx.graphics.getHeight() - coin.getHeight() - 30);
		fuel.setScale(2.5f);
		fuel.setPosition(1200, 24 + 1);
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

		car = new DroidCar(world, fixtureDef, wheelFixtureDef, 30, 3, 6, 1.25f,
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
		// PolygonShape groundShape = new PolygonShape();
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
		// ground shape
		groundShape = new ChainShape();
		groundShape.createChain(new Vector2[] { new Vector2(-5, 10),
				new Vector2(-5, 0), new Vector2(2, 0) });
		car.getChassis().setSleepingAllowed(false);
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
		stuntFont = generator.generateFont(parameter);
		generator.dispose();
		carBox = new com.badlogic.gdx.math.Rectangle(car.getChassis()
				.getPosition().x - 2, car.getChassis().getPosition().y + 3, 3,
				12);
		Vector3 tempCoords = camera.unproject(new Vector3(10, 50, 0));
		setUpLevel();

		brakePedal.setPosition(
				Gdx.graphics.getWidth() / 2 + 100 + brakePedal.getWidth(), 0);
		gasPedal.setPosition(Gdx.graphics.getWidth() / 2 - gasPedal.getWidth()
				/ 2, 1080 - 100 - gasPedal.getHeight());
		gasPedal.setScale(3);
		pause.setPosition(Gdx.graphics.getWidth() - pause.getWidth() - 10,
				Gdx.graphics.getHeight() - pause.getHeight() - 10);
		// reverseIcon = new
		// Rectangle(0,100,reverseIco.getWidth()*3,reverseIco.getHeight()*3);
		pauseButton = new Rectangle(pause.getX(), 10, pause.getWidth() * 2,
				pause.getHeight() * 2);
		toMenu.setScale(2);
		toMenu.setPosition(1920 / 2 - toMenu.getWidth() / 2, 1080 - 350);
		// gas = new Rectangle(camera.unproject(new Vector3(100, 0,
		// 0)).x,camera.unproject(new Vector3(0, 1080, 0)).y, 10,10);
		menuPlace.setScale(4, 16);
		restart.setScale(3);
		menuPlace.setPosition(
				Gdx.graphics.getWidth() / 2 - menuPlace.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - menuPlace.getHeight() / 2);

		restart.setPosition(1920 / 2 - restart.getWidth() / 2, 1080 - 600);
		restartButton = new Rectangle(restart.getX(),
				600 - restart.getHeight(), restart.getWidth() * 2,
				restart.getHeight() * 2);
		unPause.setPosition(1920 / 2 - restart.getWidth() / 2, 1080 - 900);
		unPause.setScale(3);
		gas = new Rectangle(gasPedal.getX(), Gdx.graphics.getHeight()
				- gasPedal.getY() - gasPedal.getHeight(), gasPedal.getWidth(),
				gasPedal.getHeight());
		brake = new Rectangle(brakePedal.getX(), Gdx.graphics.getHeight()
				- brakePedal.getY() - brakePedal.getHeight(),
				brakePedal.getWidth(), brakePedal.getHeight());
		bodyType.setScale(2);
		bodyType.setPosition(bodyType.getWidth() + 10,
				bodyType.getHeight() + 10);
		shapeType.setScale(2);
		shapeType.setPosition(bodyType.getWidth() + 10,
				2f * shapeType.getHeight() + 100);

		gd = new GestureDetector(this);
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(gd);
		inputMultiplexer.addProcessor(new SimpleDirectionGestureDetector(
				new SimpleDirectionGestureDetector.DirectionListener() {

					@Override
					public void onUp() {

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
			com.badlogic.gdx.math.Rectangle delp = new com.badlogic.gdx.math.Rectangle(
					doop.x, doop.y, sprites.get(i).getWidth(), sprites.get(i)
					.getHeight());
			spriteBoxes.add(delp);

		}
		coinsGenerated = true;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		if (gasPedal.getBoundingRectangle().contains(x, 1080 - y)
				&& Gdx.input.isTouched() && !noFuel) {
			gassing = true;
			gasPedal.setTexture(new Texture("exit.png"));
			car.getChassis().applyLinearImpulse(new Vector2(-50, 0),
					car.getChassis().getPosition(), true);
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

		else if (unPause.getBoundingRectangle().contains(x, 1080 - y)
				&& showMenu) {

			unPause.setTexture(new Texture("leftDown.png"));
			carLight.setActive(true);
			noFuel = false;
			showMenu = false;
			unPause.setTexture(new Texture("leftUp.png"));
			car.unfreeze();
		}
		if (bodyType.getBoundingRectangle().contains(x, 1080 - y)) {

			if (isStatic) {
				bodyType.setTexture(new Texture("dynamicDown.png"));
				isStatic = false;
				isDynamic = true;
			} else if (isDynamic) {
				isStatic = true;
				isDynamic = false;
				bodyType.setTexture(new Texture("dynamicUp.png"));
			}
		}
		if (shapeType.getBoundingRectangle().contains(x, 1080 - y)) {

			if (isSquare) {
				shapeType.setTexture(new Texture("circleType.png"));
				isSquare = false;
				isCircle = true;
				isMisc = false;
			} else if (isCircle) {
				isCircle = false;
				isMisc = true;
				isSquare = false;
				shapeType.setTexture(new Texture("drawType.png"));
			} else if (isMisc) {
				isMisc = false;
				isSquare = true;
				isCircle = false;
				shapeType.setTexture(new Texture("squareType.png"));
			}
		} else if (toMenu.getBoundingRectangle().contains(x, 1080 - y)
				&& showMenu) {

			toMenu.setTexture(new Texture("toMenuDown.png"));
			tmpScreen = new DroidMenuScreen(mygame);
			goToMenu = true;
			backToMenu();

		} else if (restart.getBoundingRectangle().contains(x, y) && showMenu) { // restartButton.contains(x,y)
			goToMenu = true;
			restart.setTexture(new Texture("rightDown.png"));
			tmpScreen = new DemoLevel(mygame);
			backToMenu();

			// tmp = new Vector2[2];

		}

		return true;
	}

	public void backToMenu() {
		if (screenChanger == 30) {
			mygame.setScreen(tmpScreen);
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

	public void calcAirTime() {

		airTime++;
		if (airTime > 9) {
			currentStunt = "BIG AIR! +" + airTime;
		}

	}

	public void coolText(String text) {
		if (stuntFont.getScaleX() < 10) {
			stuntFont.setScale(stuntFont.getScaleX() + .1f,
					stuntFont.getScaleY() + .1f);
		} else if (stuntFont.getScaleX() > 10) {
			stuntFont.setScale(1);
		}
		hudBatch.begin();
		if (text.equals(currentStunt)) {
			stuntFont.setColor(Color.WHITE);
			stuntFont.draw(hudBatch, text, Gdx.graphics.getWidth() / 2 - 100,
					Gdx.graphics.getHeight() / 2);
		} else {
			stuntFont.setColor(Color.RED);
			stuntFont.draw(hudBatch, text, Gdx.graphics.getWidth() / 2,
					Gdx.graphics.getHeight() / 2 + 100);
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
		font.draw(hudBatch, menuMsg, menuPlace.getX()
				- font.getBounds(menuMsg).width / 2 + 110,
				Gdx.graphics.getHeight() - 50);
		font.setScale(tempScaleX, tempScaleY);
		restart.draw(hudBatch);
		toMenu.draw(hudBatch);
		if (car.fuel > 0) {
			unPause.draw(hudBatch);
		}
		if (car.fuel < 0) {

			stuntFont.setColor(Color.RED);
			sCounter1 += 0.1f;
			stuntFont.setScale(8 * MathUtils.sin(sCounter1));
			stuntFont.draw(hudBatch, "OUT OF INK!", Gdx.graphics.getWidth() / 2
					- stuntFont.getBounds("SMOKED OUT!").width / 2,
					Gdx.graphics.getHeight() / 2);
			if (stuntFont.getScaleX() < 0) {
				sCounter1 = 0;
			}
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
			int sinOrCos = MathUtils.random((int) 0, (int) 1);
			// sinOrCos = 1; //keep for debugging purposes
			amplitude = MathUtils.random(1, 5);

			System.out.println(amplitude + " ampplitude");
			if (sinOrCos == 1) {
				System.out.println("WHAT THE ACTUAL FUCK I HATE thIS SHIT");
				while (Math.abs(tmp[tmp.length - 1].y / amplitude) > 1) {
					amplitude = MathUtils.random(1, 5);
				}
				float j = (float) Math.acos(tmp[tmp.length - 1].y / amplitude);
				j += 0.1f;
				Vector2 testPoint = new Vector2(-1, amplitude
						* ((float) MathUtils.cos(j)));

				System.out.println(tmp[tmp.length - 1].y / amplitude
						+ "this is the problem? " + j + "<--J" + testPoint.y
						+ "<---TESTPOINT");

				if (Math.abs(testPoint.y - tmp[tmp.length - 1].y)
						* Math.abs(testPoint.y - tmp[tmp.length - 1].y) <= (.005f * .005f)) {
					System.out.println("dslskjfksjslkjlsdjsfdfsjkdjlsjsdsfds");
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
				float j = (float) Math.asin(tmp[tmp.length - 1].y / amplitude);
				j += 0.1f;
				Vector2 testPoint = new Vector2(-1, amplitude
						* ((float) MathUtils.sin(j)));
				if (Math.abs(testPoint.y - tmp[tmp.length - 1].y)
						* Math.abs(testPoint.y - tmp[tmp.length - 1].y) <= (.005f * .005f)) {
					amplitude *= 1.45f;
					j += 0.2f;
					System.out.println("dslskjfksjslkjlsdjsfdfsjkdjlsjsdsfds");
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

}