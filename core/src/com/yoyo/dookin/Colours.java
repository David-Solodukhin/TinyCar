package com.yoyo.dookin;

import java.util.ArrayList;

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

public class Colours extends Game implements Screen, GestureListener {
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private SpriteBatch batch;
	private SpriteBatch hudBatch;
	Sprite bgSprite1 = new Sprite(new Texture("bg.png"));
	//Sprite ink = new Sprite(new Texture("ink.png"));
	public int screenChanger = 0;
	public boolean isJoint = false;
	public boolean isStatic = false;
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
	//Texture textureSolid;
	EarClippingTriangulator delp = new EarClippingTriangulator();
	//Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
	FixtureDef fixtureDef = new FixtureDef(), wheelFixtureDef = new FixtureDef();
	PolygonRegion polyReg;
	private final float TIMESTEP = 1 / 60f;
	public float scaleFactor = (50 * Gdx.graphics.getWidth()) / 1920f;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
	public DroidCar car;
	private Array<Body> tmpBodies = new Array<Body>();
	RayHandler rayHandler;

	ShapeRenderer shapeDebugger = new ShapeRenderer();
	Body terrain;
	private BitmapFont font;
	
	public boolean showMenu = false;
	
	private boolean drawing = false;
	private ChainShape currentShape;
	private Body doop;
	
	private Vector2[] drawPoints;
	private TextureRegion texRegion;
	
	private ArrayList<Body> drawnBodies = new ArrayList<Body>();
	private ArrayList<Vector2[]> pointsofBodies = new ArrayList<Vector2[]>();
	// private ArrayList<Vector2> originalPoints = new ArrayList<Vector2>();
	private ArrayList<Vector2> originalPoints;
	private boolean isSquare = true;
	
	private Vector2 delphi = new Vector2(0, 0);
	ArrayList<Vector2> updatedPoints = new ArrayList<Vector2>();
	private boolean first;
	
	private boolean isHeavy = true;
	private ShapeRendererF shapeRendererF;
	private FPSLogger fps;
	private ChainShape groundShape;
	private GestureDetector gd;

	public Colours(Game game) {
		this.mygame = game;
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		
	
		
		
		
		
		
		
		rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.updateAndRender();
		hudBatch.begin();
		hudBatch.draw(bgSprite1, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	//	hudBatch.draw(ink, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		hudBatch.end();
		//batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//ink.setSize(ink.getWidth(),ink.getHeight());
		//ink.setScale(100f);
		//ink.draw(batch);
		world.getBodies(tmpBodies);
		for (Body body : tmpBodies){
			if (body.getUserData() instanceof Sprite ) {
				Sprite sprite = (Sprite) body.getUserData();
				sprite.setPosition(

				body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
				sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
				sprite.draw(batch);
				
			}
	
		}
		debugRenderer.setDrawJoints(false);
		font.setScale(0.1f);
		

		batch.end();
		debugRenderer.render(world, camera.combined);
		batch.setProjectionMatrix(camera.combined);
///////////////////////////////////////////////////////////////////////////////////////////TMP IS THE TERRAIN POINTS WHICH ARE BEING TRACED/// HERE/////////////////////////////////		
		/*if (tmp.length != 0) { // terrain drawing.
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Line);

			for (int i = 0; i < tmp.length - 1; i++) {
				shapeRenderer.setColor(Color.BLUE);
				shapeRenderer.line(tmp[i].x, tmp[i].y, tmp[i + 1].x, tmp[i + 1].y);
			}

		}
		shapeRenderer.end();
		*/
////////////////////////////////////////////////////////////////////////////////////////////////////////////////END OF TMP TRACING TERRAIN///////////////////////////////////////////
		//debugRenderer.setDrawBodies(true);
		update();

		

		

	

	}


	private void update() {
		
		for(int i = 0;i<tmpBodies.size;i++)
		{
			System.out.println(tmpBodies.get(i).getPosition());
			if(tmpBodies.get(i).getPosition().y+10<camera.unproject(new Vector3(0,Gdx.graphics.getHeight(),0)).y || tmpBodies.get(i).getPosition().x>30)
			{
				world.destroyBody(tmpBodies.get(i));
				tmpBodies.removeIndex(i);
			}
		}
		shapeRendererF.setProjectionMatrix(camera.combined); 
		
		shapeRendererF.begin(ShapeRendererF.ShapeType.Filled);
		if (drawPoints != null) {
			for (int x = 0; x < drawPoints.length - 1; x+=1) {
				shapeRendererF.setColor(1, 1, 0, 1);
				shapeRendererF.line(drawPoints[x].x, drawPoints[x].y, drawPoints[x + 1].x, drawPoints[x + 1].y);
			}
		}
		shapeRendererF.end();
		

		shapeRendererF.setProjectionMatrix(camera.combined);
		shapeRendererF.begin(ShapeRendererF.ShapeType.Filled);

		if (tmpBodies.size > 1) 
			for (int i = 0; i < tmpBodies.size; i++) {
				if (tmpBodies.get(i).getType().equals(BodyType.DynamicBody) && !tmpBodies.get(i).equals(terrain)
						&& (tmpBodies.get(i).getFixtureList().get(0).getType().equals(Shape.Type.Chain)) && tmpBodies.get(i).getFixtureList().size > 0) {
					shapeRendererF.set(ShapeRendererF.ShapeType.Filled);
					ChainShape tempShape = new ChainShape();
					ArrayList<Vector2> bodyPoints = new ArrayList<Vector2>();

					
					updatedPoints = bodyPoints;
					tempShape = (ChainShape) tmpBodies.get(i).getFixtureList().get(0).getShape();
					float[] verticies = new float[tempShape.getVertexCount() * 2];
					int counter = 0;
					for (int q = 0; q < tempShape.getVertexCount(); q++) {
						Vector2 linePoints = new Vector2();
						
						tempShape.getVertex(q, linePoints);

						Vector2 newLinePoints = tmpBodies.get(i).getWorldPoint(linePoints);

						bodyPoints.add(newLinePoints);

						verticies[counter] = newLinePoints.x;
						verticies[counter + 1] = newLinePoints.y;
						counter += 2;
						shapeRendererF.setColor(1, 1, 0, 1);

					}

				//	camera.update();

					if (verticies.length > 4) {
						shapeRendererF.polygon(verticies);
					}
				} else if (tmpBodies.get(i).getType().equals(BodyType.DynamicBody) && !tmpBodies.get(i).equals(terrain) && !tmpBodies.get(i).equals(car.chassis)
						&& (tmpBodies.get(i).getFixtureList().get(0).getType().equals(Shape.Type.Polygon))) // tmpBodies.get(i).getFixtureList().get(0).getShape().equals(Type.Chain)
				{
					if (isHeavy == true || isHeavy == false) {

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

		


		if (Gdx.input.isTouched() && !drawing && isSquare) {

			
			
			drawing = true;
			
		

			drawPoints = new Vector2[1];
			Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			worldCoordinates = camera.unproject(worldCoordinates);
			

			drawPoints[0] = new Vector2(worldCoordinates.x, worldCoordinates.y);
			

		} else if (Gdx.input.isTouched() && drawing && isSquare) { //!gassing

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
		
			
			}
		} else if (Gdx.input.isTouched() && !drawing ) {
			// FixtureDef fixtureDef2 = new FixtureDef();
			
			drawing = true;
			
			Vector3 mouseCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			drawPoints = new Vector2[1];
			Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			worldCoordinates = camera.unproject(worldCoordinates);
			// System.out.println(camera.unproject(worldCoordinates).y);
			// System.out.println(Gdx.input.getX()-(camera.viewportWidth+camera.position.x));

			drawPoints[0] = new Vector2(worldCoordinates.x, worldCoordinates.y);
			delphi = new Vector2(mouseCoords.x, mouseCoords.y);
			
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.StaticBody;
			doop = world.createBody(bodyDef);
           
		} else if (Gdx.input.isTouched() && drawing) {

			Vector3 mouseCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			
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
				
			//	fixtureDef.shape = currentShape;
			

				doop = world.createBody(bodyDef);
				doop.createFixture(fixtureDef);
			}

		}
		
		if (!Gdx.input.isTouched()) {
			
			if (drawPoints != null && drawPoints.length > 1) {
				
				// doop.setFixedRotation(false);
				// putCoins();
			
				if (isSquare && drawPoints.length > 3) {
					
					PolygonShape nelf = new PolygonShape();
					
					// originalPoints = drawPoints;
					Vector2[] tmep = new Vector2[drawPoints.length];
					for (int i = drawPoints.length - 1; i >= 0; i--) {
						tmep[drawPoints.length - i - 1] = drawPoints[i];
					}
					// ////////////////////////////////////////////////
					tmep = removeDuplicates(tmep);
					// System.out.println(Arrays.toString(tmep));
					// Gdx.app.exit();
					drawPoints = tmep;
					ArrayList<Vector2[]> terrainSplittr = new ArrayList<Vector2[]>();
					BodyDef bodyDef = new BodyDef();
					bodyDef.type = BodyType.DynamicBody;
					Body testPolygonShape1 = world.createBody(bodyDef);

					
					for (int x = 0; x < drawPoints.length - 3; x += 3) { // will
																			// have
																			// to
																			// fix
																			// this
																			// eventually
																			// :I

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
				
					for (int x = 0; x < terrainSplittr.size(); x++) {

						nelf.set(terrainSplittr.get(x));
						// nelf2.set(terrainSplittr.get(x + 1));

						fixtureDef.shape = nelf;
						fixtureDef.friction = .5f;
						fixtureDef.restitution = 0;
						bodyDef.position.set(0, 0);

					

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

			

				drawPoints = null;
			}
			drawing = false;
		}
		// System.out.println(car.fuel);
		
		

	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void resize(int width, int height) {

		camera.viewportWidth = 1920 / scaleFactor; // to make things
	
		camera.viewportHeight = 1080 / scaleFactor;

	}

	@Override
	public void show() {
		
		world = new World(new Vector2(14.81f, 0), true);
		camera = new OrthographicCamera();
		debugRenderer = new Box2DDebugRenderer();
		
		shapeRendererF = new ShapeRendererF();
		rayHandler = new RayHandler(world);
		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		polyBatch = new PolygonSpriteBatch();
	//	sound = Gdx.audio.newSound(Gdx.files.internal("drawingS.mp3"));
		Texture bgRepeat = new Texture("foreground2.png");
		bgRepeat.setWrap(TextureWrap.MirroredRepeat, TextureWrap.MirroredRepeat);
		
		shapeRendererF.setAutoShapeType(true);
		
		//mp3Music = Gdx.audio.newMusic(Gdx.files.internal("Cipher2.mp3"));
		//pix.setColor(Color.BLUE); // DE is red, AD is green and BE is blue.
		//pix.fill();
		//textureSolid = new Texture(pix); //pix
		Texture test = new Texture("medres.png");
		
		test.setWrap(TextureWrap.MirroredRepeat, TextureWrap.MirroredRepeat);
		texRegion = new TextureRegion(test);
	

		createCollisionListener();

		
		BodyDef bodyDef = new BodyDef();

		// car
		//fixtureDef.density = 5;
		//fixtureDef.friction = .4f;
		//fixtureDef.restitution = .3f;

		//bodyDef.type = BodyType.DynamicBody;

		//car = new DroidCar(world, fixtureDef, wheelFixtureDef, 30, 3, 6, 1.25f, 1, true); // 1
	
		//bodyDef.type = BodyType.StaticBody;
		//bodyDef.position.set(0, 0);
		//bodyDef.type = BodyType.StaticBody;
		//bodyDef.position.set(0, 0);

		// ground shape
		
		
		
		
		
	////////////////////////////////////////////////////////////////////////PREVIOUS PLACEHOLDER FOR TERRAIN GENERATION////////////////////////////////////////////////////////
	/*	tmp = new Vector2[100];
		float j = 0;
		for (int i = 0; i < 50; i++) {

			Vector2 temp = new Vector2(i, 3 * (float) Math.sin(j));
			tmp[i] = temp;
			j += 0.1f;
		}
		System.out.println(j);
		j = (float) Math.acos(tmp[49].y / 3);
		System.out.println(j);
		for (int i = 50; i < 100; i++) {
			Vector2 temp = new Vector2(i, 3 * (float) Math.cos(j));
			tmp[i] = temp;
			j += 0.1f;
		}
		System.out.println(Math.acos(tmp[99].y / 3) + "dsfjlksfjlk");
		lastPointx = tmp[tmp.length - 1].x;
		lastPointy = tmp[tmp.length - 1].y;
		setUpLevel();
		*/
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////END OF PLACEHOLDER FOR TERRAIN GENERATION///////////////////////

		font = new BitmapFont();
		
		font.setColor(Color.CYAN);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		font = generator.generateFont(parameter); // font size 12 pixels
		
		generator.dispose();
		
	
		


		gd = new GestureDetector(this);

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

	

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
	


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
	

}