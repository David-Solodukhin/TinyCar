package com.yoyo.dookin;
import java.util.ArrayList;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;

public class DroidCar extends InputAdapter {

	public Body chassis, leftWheel, rightWheel;
	private WheelJoint leftAxis, rightAxis;
	
	public float direction = 0;
	public float motorSpeed = 50;  //175  //180
	public float jumpjuice = 100;
	private float nuff = -1;
	private boolean allWheelDrive = false;
	private WheelJointDef axisDef;
	public PolygonShape chassisShape;
	public float[] bodyShape;
	public PolygonSprite poly;
	public PolygonSpriteBatch polyBatch;
	public Texture textureSolid;
	public Fixture rWheel;
	public Fixture lWheel;
	public Fixture bodyF;
	//public Sprite tinycarcar = new Sprite(new Texture("Car.png"));
	//public Sprite lWheelS = new Sprite(new Texture("wheel.png"));
	//public Sprite rWheelS = new Sprite(new Texture("wheel.png"));
	public ArrayList<Body> carParts = new ArrayList<Body>();
	public float fuel = 100;
//	public float[] chassisPoints = {-width / 2, -height / 2, width / 2, -height / 2, width / 2 * .4f, height / 2, -width / 2 * .8f, height / 2 * .8f};
	public DroidCar(World world, FixtureDef chassisFixtureDef, FixtureDef wheelFixtureDef, float x, float y, float width, float height, float dir, boolean drive) {
		allWheelDrive = drive;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(x, y);
		// chassis
		direction=dir;
		System.out.println(direction);
		chassisShape = new PolygonShape();
		if(dir==1){
			bodyShape = new float[] {-width / 2.6f, -height / 2.5f, width / 2.6f, -height / 2.5f, width / 3f , height / 2.5f, -width / 3f , height / 2.5f };
		chassisShape.set(bodyShape);
				} // counterclockwise order
		
		
		else if(dir==-1)
		{
			bodyShape = new float[] {-width / 2.5f, -height / 2, width / 2.5f, -height / 2, width / 3 , height / 2, -width / 3 , height / 2 };
			chassisShape.set(bodyShape);
		}
		chassisFixtureDef.shape = chassisShape;

		chassis = world.createBody(bodyDef);
		bodyF = chassis.createFixture(chassisFixtureDef);

		// left wheel
		CircleShape wheelShape = new CircleShape();
		wheelShape.setRadius(height / 2.5f);

		wheelFixtureDef.shape = wheelShape;
		leftWheel = world.createBody(bodyDef);
		rWheel = leftWheel.createFixture(wheelFixtureDef);
	//	leftWheel.createFixture(wheelFixtureDef);
		// right wheel
		rightWheel = world.createBody(bodyDef);
		lWheel = rightWheel.createFixture(wheelFixtureDef);

		// left axis
		axisDef = new WheelJointDef();
		axisDef.bodyA = chassis;
		axisDef.bodyB = leftWheel;
		axisDef.localAnchorA.set(-width / 2 * .68f + wheelShape.getRadius(), -height / 2 * 1.675f);
		axisDef.frequencyHz = 3;
		axisDef.localAxisA.set(Vector2.Y);
		axisDef.maxMotorTorque = chassisFixtureDef.density * 20;
		leftAxis = (WheelJoint) world.createJoint(axisDef);
		rightWheel.setAngularDamping(1.1f);
		leftWheel.setAngularDamping(1.1f);
		// right axis
		axisDef.bodyB = rightWheel;
		axisDef.localAnchorA.x *= -1;  //i legitemately don't know what this does

		rightAxis = (WheelJoint) world.createJoint(axisDef);
		//tinycarcar.setScale(0.039f,0.028f);
		//tinycarcar.setScale(0.019f);
		if(direction==-1){
		//	tinycarcar.flip(true, false);
			}
	//	chassis.setUserData(tinycarcar);
		//lWheelS.setScale((rightWheel.getFixtureList().get(0).getShape().getRadius()/50)+0.006f); //0.018f
		//leftWheel.setUserData(lWheelS);
		//rightWheel.setUserData(lWheelS);
		
		carParts.add(rightWheel);
		carParts.add(leftWheel);
		carParts.add(chassis);
	}

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.W:
			leftAxis.enableMotor(true);
			leftAxis.setMotorSpeed(-motorSpeed *direction);
			if(allWheelDrive){
			//rightAxis.enableMotor(true);
			//rightAxis.setMotorSpeed(-motorSpeed*direction);
			}
			break;
		case Keys.S:
			leftAxis.enableMotor(true);
			leftAxis.setMotorSpeed(motorSpeed * direction);
			if(allWheelDrive){
				rightAxis.enableMotor(true);
				rightAxis.setMotorSpeed(motorSpeed*direction);
				}
		case Keys.A:
		//	chassis.applyTorque(2400, true);
			chassis.applyAngularImpulse(50, true);
			break;
		case Keys.D:
		//	chassis.applyTorque(-2000, true);
			chassis.applyAngularImpulse(-50, true);
			break;
		case Keys.SPACE:
			
			
			
			break;
		case Keys.SHIFT_LEFT:
			
			//chassis.setLinearVelocity(10, 0);
				//axisDef.maxMotorTorque+=1000;
				//motorSpeed = 1800;
				//leftWheel.setAngularDamping(0);
			if(direction==1){
			chassis.applyLinearImpulse(100, 0, chassis.getWorldCenter().x, chassis.getWorldCenter().y, true);
			}
			else
			{
				chassis.applyLinearImpulse(-100, 0, chassis.getWorldCenter().x, chassis.getWorldCenter().y, true);
			}
			break;
	}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Keys.W:
			leftAxis.enableMotor(false); //2 wheel drive
			rightAxis.enableMotor(false);
		case Keys.S:
			leftAxis.enableMotor(false); 
			rightAxis.enableMotor(false);
			
		}
		return true;
	}

	public Body getChassis() {
		return chassis;
	}
	public void enableAllWheels()
	{
		allWheelDrive = true;
	}
	public boolean IsallWheelDrive()
	{
		return allWheelDrive;
	}
	public void prevW()
	{
		leftAxis.enableMotor(true);
		leftAxis.setMotorSpeed(-motorSpeed *direction);
		if(allWheelDrive){
		rightAxis.enableMotor(true);
		rightAxis.setMotorSpeed(-motorSpeed*direction);
		}
	}
	public void prevS()
	{
		leftAxis.enableMotor(true);
		leftAxis.setMotorSpeed(motorSpeed * direction);
		if(allWheelDrive){
			rightAxis.enableMotor(true);
			rightAxis.setMotorSpeed(motorSpeed*direction);
			}
	}
	public void prevSpace()
	{
		
		if(rightWheel.getPosition().y<=chassis.getPosition().y)
		{
			chassis.applyLinearImpulse(new Vector2(0,420),chassis.getPosition(),true);
			chassis.setTransform(chassis.getPosition(), chassis.getAngle()+(180*MathUtils.degreesToRadians));
			//tinycarcar.flip(true,false);
			direction*=-1;
		}
		else{
			chassis.applyLinearImpulse(new Vector2(0,-420),chassis.getPosition(),true);
			chassis.setTransform(chassis.getPosition(), chassis.getAngle()+(180*MathUtils.degreesToRadians));
			//tinycarcar.flip(true,false);
			direction*=-1;
		}
	}
	public void prevA()
	{
		chassis.applyAngularImpulse(-250, true);
		
	}
	public void prevD()
	{
		chassis.applyAngularImpulse(250, true);
	}
	public void prevKeyUp() {
		leftAxis.enableMotor(false);
		rightAxis.enableMotor(false);
	}
	public void freeze()
	{
		chassis.setActive(false);
		rightWheel.setActive(false);
		leftWheel.setActive(false);
	}
	public void unfreeze()
	{
		chassis.setActive(true);
		rightWheel.setActive(true);
		leftWheel.setActive(true);
	}
	

}