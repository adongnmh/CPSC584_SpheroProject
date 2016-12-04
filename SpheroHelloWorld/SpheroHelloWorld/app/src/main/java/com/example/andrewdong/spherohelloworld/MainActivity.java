package com.example.andrewdong.spherohelloworld;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

// Importing Sphero RobotLibrary.jar here:
import com.orbotix.ConvenienceRobot;
import com.orbotix.DualStackDiscoveryAgent;
import com.orbotix.common.DiscoveryException;
import com.orbotix.common.Robot;
import com.orbotix.common.RobotChangedStateListener;
import com.orbotix.le.RobotLE;
import com.orbotix.macro.AbortMacroCommand;
import com.orbotix.macro.MacroObject;
import com.orbotix.macro.cmd.BackLED;
import com.orbotix.macro.cmd.Delay;
import com.orbotix.macro.cmd.Fade;
import com.orbotix.macro.cmd.LoopEnd;
import com.orbotix.macro.cmd.LoopStart;
import com.orbotix.macro.cmd.RGB;
import com.orbotix.macro.cmd.RawMotor;
import com.orbotix.macro.cmd.Roll;
import com.orbotix.macro.cmd.RollSD1;
import com.orbotix.macro.cmd.RollSD1SPD1;
import com.orbotix.macro.cmd.RotateOverTime;
import com.orbotix.macro.cmd.Stabilization;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RobotChangedStateListener, View.OnClickListener {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 42;
    private static final float ROBOT_VELOCITY = 0.6f;

    private ConvenienceRobot mRobot;

    private Button mBtn0;
    private Button btnRotate;
    private int tail = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DualStackDiscoveryAgent.getInstance().addRobotStateListener( this );


        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            int hasLocationPermission = checkSelfPermission( Manifest.permission.ACCESS_COARSE_LOCATION );
            if( hasLocationPermission != PackageManager.PERMISSION_GRANTED ) {
                Log.e( "Sphero", "Location permission has not already been granted" );
                List<String> permissions = new ArrayList<String>();
                permissions.add( Manifest.permission.ACCESS_COARSE_LOCATION);
                requestPermissions(permissions.toArray(new String[permissions.size()] ), REQUEST_CODE_LOCATION_PERMISSION );
            } else {
                Log.d( "Sphero", "Location permission already granted" );
            }
        }

        initViews();
    }


    private void initViews() {
        mBtn0 = (Button) findViewById( R.id.btn_0 );
        mBtn0.setOnClickListener( this );

        btnRotate =(Button) findViewById(R.id.btnRotate);
        btnRotate.setOnClickListener(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch ( requestCode ) {
            case REQUEST_CODE_LOCATION_PERMISSION: {
                for( int i = 0; i < permissions.length; i++ ) {
                    if( grantResults[i] == PackageManager.PERMISSION_GRANTED ) {
                        startDiscovery();
                        Log.d( "Permissions", "Permission Granted: " + permissions[i] );
                    } else if( grantResults[i] == PackageManager.PERMISSION_DENIED ) {
                        Log.d( "Permissions", "Permission Denied: " + permissions[i] );
                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if( Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || checkSelfPermission( Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            startDiscovery();
        }
    }

    private void startDiscovery() {
        //If the DiscoveryAgent is not already looking for robots, start discovery.
        if( !DualStackDiscoveryAgent.getInstance().isDiscovering() ) {
            try {
                DualStackDiscoveryAgent.getInstance().startDiscovery( this );
            } catch (DiscoveryException e) {
                Log.e("Sphero", "DiscoveryException: " + e.getMessage());
            }
        }
    }

    @Override
    protected void onStop() {
        //If the DiscoveryAgent is in discovery mode, stop it.
        if( DualStackDiscoveryAgent.getInstance().isDiscovering() ) {
            DualStackDiscoveryAgent.getInstance().stopDiscovery();
        }

        //If a robot is connected to the device, disconnect it
        if( mRobot != null ) {
            mRobot.disconnect();
            mRobot = null;
        }

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DualStackDiscoveryAgent.getInstance().addRobotStateListener( null );
    }

    @Override
    public void handleRobotChangedState(Robot robot, RobotChangedStateNotificationType type) {
        switch (type) {
            case Online: {

                //If robot uses Bluetooth LE, Developer Mode can be turned on.
                //This turns off DOS protection. This generally isn't required.
                if( robot instanceof RobotLE) {
                    ( (RobotLE) robot ).setDeveloperMode( true );
                }

                //Save the robot as a ConvenienceRobot for additional utility methods
                mRobot = new ConvenienceRobot(robot);
                //Start blinking the robot's LED
                blink( false );
                break;
            }
        }
    }

    //Turn the robot LED on or off every two seconds
    private void blink( final boolean lit ) {
        mRobot.setLed(0,1,0);
        mRobot.setBackLedBrightness(1);
        mRobot.drive(180,0);

    }

    @Override
    public void onClick(View v) {
        //If the robot is null, then it is probably not connected and nothing needs to be done
        if( mRobot == null ) {
            return;
        }

        /*
            When a heading button is pressed, set the robot to drive in that heading.
            All directions are based on the back LED being considered the back of the robot.
            0 moves in the opposite direction of the back LED.
         */
        switch( v.getId() ) {
            case R.id.btn_0: {
                MacroObject macro = new MacroObject();
                macro.addCommand(new LoopStart(9));
                macro.addCommand(new RGB(156,255,0,500));
                macro.addCommand(new Delay(100));
                macro.addCommand(new RGB(255,8,8,500));
                macro.addCommand(new Delay(100));
                macro.addCommand(new LoopEnd());



                //Set the robot LED to blue
                macro.addCommand( new RGB( 0, 0, 255, 255 ) );
                //Move the robot to the right
                macro.addCommand( new Roll( 0.5f, 90, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );

                macro.addCommand( new LoopStart( 5));
                macro.addCommand( new RotateOverTime( 360, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );


                //Set the robot LED to red
                macro.addCommand( new RGB( 255, 0, 0, 255 ) );
                //Move the robot to the left
                macro.addCommand( new Roll( 0.5f, 270, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500 ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );

                //Set the robot LED to red
                macro.addCommand( new RGB( 255, 0, 0, 255 ) );
                //Move the robot to the left
                macro.addCommand( new Roll( 0.5f, 270, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500 ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );

                macro.addCommand( new LoopStart( 5));
                macro.addCommand( new RotateOverTime( 360, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );

                //Set the robot LED to blue
                macro.addCommand( new RGB( 0, 0, 255, 255 ) );
                //Move the robot to the right
                macro.addCommand( new Roll( 0.5f, 90, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );





                //Send the macro to the robot and play
                macro.setMode(MacroObject.MacroObjectMode.Normal);
                macro.setRobot(mRobot.getRobot());
                macro.playMacro();


                break;
            }

            case R.id.btnRotate: {
                tail = tail + 90;
                mRobot.drive(tail,0);
            }

        }
    }




}
