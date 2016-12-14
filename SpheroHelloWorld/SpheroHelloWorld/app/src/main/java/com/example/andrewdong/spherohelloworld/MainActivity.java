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
    private static final float ROBOT_VELOCITY = 0.3f;

    private ConvenienceRobot mRobot;

    private Button mBtn0;
    private Button btnRotate;
    private Button btnSetTail;
    private Button btnStop;
    private Button btnStopMotion;
    private Button btnRight;
    private Button btnLeft;
    private Button btnUp;
    private Button btnDown;
    private Button btnPlay2;
    private Button btnSphero1_3;
    private Button btnSphero2_1;
    private Button btnSphero2_2;
    private Button btnChorus;
    private Button btnChorus2;
    private Button btnEnding;
    private Button btnEnding2;
    private Button btnSphero2_3;
    private int tail = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DualStackDiscoveryAgent.getInstance().addRobotStateListener(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
                Log.e("Sphero", "Location permission has not already been granted");
                List<String> permissions = new ArrayList<String>();
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
                requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_LOCATION_PERMISSION);
            } else {
                Log.d("Sphero", "Location permission already granted");
            }
        }

        initViews();
    }


    private void initViews() {
        mBtn0 = (Button) findViewById(R.id.btn_0);
        mBtn0.setOnClickListener(this);

        btnRotate = (Button) findViewById(R.id.btnRotate);
        btnRotate.setOnClickListener(this);

        btnSetTail = (Button) findViewById(R.id.btnSet);
        btnSetTail.setOnClickListener(this);

        btnStop = (Button) findViewById(R.id.buttonStop);
        btnStop.setOnClickListener(this);

        btnStopMotion =(Button) findViewById(R.id.buttonStopMove);
        btnStopMotion.setOnClickListener(this);

        btnRight = (Button) findViewById(R.id.buttonRight);
        btnRight.setOnClickListener(this);

        btnLeft = (Button) findViewById(R.id.buttonLeft);
        btnLeft.setOnClickListener(this);

        btnUp = (Button) findViewById(R.id.buttonUp);
        btnUp.setOnClickListener(this);

        btnDown = (Button) findViewById(R.id.buttonDown);
        btnDown.setOnClickListener(this);

        btnPlay2 = (Button) findViewById(R.id.buttonPlay2);
        btnPlay2.setOnClickListener(this);

        btnSphero1_3 = (Button) findViewById(R.id.buttonSphero1_3);
        btnSphero1_3.setOnClickListener(this);

        btnSphero2_1 = (Button) findViewById(R.id.buttonSphero2_1);
        btnSphero2_1.setOnClickListener(this);

        btnSphero2_2 = (Button) findViewById(R.id.buttonSphero2_2);
        btnSphero2_2.setOnClickListener(this);

        /*btnChorus = (Button) findViewById(R.id.buttonChorus);
        btnChorus.setOnClickListener(this);*/

        btnChorus2 = (Button) findViewById(R.id.buttonChorus2);
        btnChorus2.setOnClickListener(this);

        btnEnding = (Button) findViewById(R.id.buttonEnding);
        btnEnding.setOnClickListener(this);

        btnEnding2 = (Button) findViewById(R.id.buttonEnding2);
        btnEnding2.setOnClickListener(this);

        btnSphero2_3 = (Button) findViewById(R.id.buttonSphero2_3);
        btnSphero2_3.setOnClickListener(this);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_LOCATION_PERMISSION: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        startDiscovery();
                        Log.d("Permissions", "Permission Granted: " + permissions[i]);
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        Log.d("Permissions", "Permission Denied: " + permissions[i]);
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

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startDiscovery();
        }
    }

    private void startDiscovery() {
        //If the DiscoveryAgent is not already looking for robots, start discovery.
        if (!DualStackDiscoveryAgent.getInstance().isDiscovering()) {
            try {
                DualStackDiscoveryAgent.getInstance().startDiscovery(this);
            } catch (DiscoveryException e) {
                Log.e("Sphero", "DiscoveryException: " + e.getMessage());
            }
        }
    }

    @Override
    protected void onStop() {
        //If the DiscoveryAgent is in discovery mode, stop it.
        if (DualStackDiscoveryAgent.getInstance().isDiscovering()) {
            DualStackDiscoveryAgent.getInstance().stopDiscovery();
        }

        //If a robot is connected to the device, disconnect it
        if (mRobot != null) {
            mRobot.disconnect();
            mRobot = null;
        }

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DualStackDiscoveryAgent.getInstance().addRobotStateListener(null);
    }

    @Override
    public void handleRobotChangedState(Robot robot, RobotChangedStateNotificationType type) {
        switch (type) {
            case Online: {

                //If robot uses Bluetooth LE, Developer Mode can be turned on.
                //This turns off DOS protection. This generally isn't required.
                if (robot instanceof RobotLE) {
                    ((RobotLE) robot).setDeveloperMode(true);
                }

                //Save the robot as a ConvenienceRobot for additional utility methods
                mRobot = new ConvenienceRobot(robot);
                //Start blinking the robot's LED
                initialize();
                break;
            }
        }
    }

    //Initializing the Sphero ro make sure it is connected
    private void initialize()
    {
        mRobot.setLed(0,0,1);
        mRobot.setBackLedBrightness(1);
    }

    private void stopMacro()
    {
        mRobot.sendCommand(new AbortMacroCommand());
        initialize();
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
                // The Set of macros that will be played when we hit the play button. Spheros will follow the
                // set of macros until the very end.
                MacroObject macro = new MacroObject();

                // Spin Movement
                //can you hear my heartbeat
                macro.addCommand( new LoopStart( 8)); //four seconds
                macro.addCommand( new RotateOverTime( 180, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );

                // Move Right
                //Set the robot LED to blue
                // RGB (Red,Green,Blue,DELAY)
                macro.addCommand(new LoopStart(4));
                //macro.addCommand( new RGB( 0, 0, 255, 255 ) );
                //Move the robot to the right
                macro.addCommand( new Roll( 0.3f, 90, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );

                macro.addCommand( new RotateOverTime( 360, 500));
                macro.addCommand( new Delay( ( 500  ) ) );

                //Move Left
                //Move the robot to the left
                macro.addCommand( new Roll( 0.3f, 270, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500 ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand(new LoopEnd());

                //another spin ccw
                macro.addCommand( new LoopStart( 5));
                macro.addCommand( new RotateOverTime( 360, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );

                //victors part
                macro.addCommand( new LoopStart( 30));
                macro.addCommand( new RotateOverTime( 360, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                //you set my heart on fire
                macro.addCommand(new Roll( 1.5f, 0, 0 ));
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 0, 255 ) );
                macro.addCommand( new Delay( ( 500  ) ) );


                
                //Send the macro to the robot and play
                macro.setMode(MacroObject.MacroObjectMode.Normal);
                macro.setRobot(mRobot.getRobot());
                macro.playMacro();
                break;
            }

            case R.id.buttonPlay2:{

                // The Set of macros that will be played when we hit the play button. Spheros will follow the
                // set of macros until the very end.
                MacroObject macro = new MacroObject();

                //split the parts
                //another spin ccw
                macro.addCommand( new LoopStart( 3));
                macro.addCommand( new RotateOverTime( 720, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );

                macro.addCommand( new LoopStart( 12));
                macro.addCommand( new RotateOverTime( 540, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );

                macro.addCommand( new LoopStart( 12 ));
                macro.addCommand( new RotateOverTime( 720, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );

                //yes we were born to make history
                // Shake
                macro.addCommand( new LoopStart(10));
                macro.addCommand( new RotateOverTime( 1080, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand(new Roll( 1.5f, 0, 0 ));
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 0, 255 ) );
                macro.addCommand( new Delay( ( 500  ) ) );
                //don't stop us now
                // Shake
                macro.addCommand( new LoopStart(10));
                macro.addCommand( new RotateOverTime( 1080, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                //Move the robot to the back
                macro.addCommand( new Roll( 0.5f, 180, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500 ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );
                //Move the robot to the right
                macro.addCommand( new Roll( 0.5f, 90, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );
                //don't stop us now II
                macro.addCommand( new LoopStart( 30));
                macro.addCommand( new RotateOverTime( 540, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand( new Delay ((500)));
                //trumpets
                //Move the robot to the left
                macro.addCommand( new Roll( 0.8f, 270, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay((500) ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand( new Delay( ( 500 ) ) );
                //Move the robot to the left
                macro.addCommand( new Roll( 0.8f, 90, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay((500) ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );
                macro.addCommand( new Delay( ( 500 ) ) );

                //Send the macro to the robot and play
                macro.setMode(MacroObject.MacroObjectMode.Normal);
                macro.setRobot(mRobot.getRobot());
                macro.playMacro();
                break;
            }

            case R.id.buttonSphero1_3:{
                // The Set of macros that will be played when we hit the play button. Spheros will follow the
                // set of macros until the very end.
                MacroObject macro = new MacroObject();

                // Move Right
                //Set the robot LED to blue
                // RGB (Red,Green,Blue,DELAY)
                macro.addCommand(new LoopStart(4));
                //macro.addCommand( new RGB( 0, 0, 255, 255 ) );
                //Move the robot to the right
                macro.addCommand( new Roll( 0.3f, 90, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );

                macro.addCommand( new RotateOverTime( 360, 500));
                macro.addCommand( new Delay( ( 500  ) ) );

                //Move Left
                //Move the robot to the left
                macro.addCommand( new Roll( 0.3f, 270, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500 ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand(new LoopEnd());

                //another spin ccw
                macro.addCommand( new LoopStart( 5));
                macro.addCommand( new RotateOverTime( 360, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );

                //victors part
                macro.addCommand( new LoopStart( 30));
                macro.addCommand( new RotateOverTime( 360, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                //you set my heart on fire
                macro.addCommand(new Roll( 1.5f, 0, 0 ));
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 0, 255 ) );
                macro.addCommand( new Delay( ( 500  ) ) );



                //Send the macro to the robot and play
                macro.setMode(MacroObject.MacroObjectMode.Normal);
                macro.setRobot(mRobot.getRobot());
                macro.playMacro();

                break;
            }

            case R.id.buttonSphero2_1:{
                // The Set of macros that will be played when we hit the play button. Spheros will follow the
                // set of macros until the very end.
                MacroObject macro = new MacroObject();

                //victors part
                macro.addCommand( new LoopStart(3));
                //Move the robot to the left
                macro.addCommand( new Roll( 0.8f, 270, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500 ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand( new Delay( ( 500 ) ) );
                //Move the robot to the right
                macro.addCommand( new Roll( 1.0f, 90, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );
                macro.addCommand( new LoopEnd());
                //another spin ccw
                macro.addCommand( new LoopStart( 14));
                macro.addCommand( new RotateOverTime( 360, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );

                //you set my heart on fire
                macro.addCommand(new Roll( 1.5f, 180, 0 ));
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 0, 255 ) );
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand(new Roll( 1.5f, 0, 0 ));
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 0, 255 ) );
                macro.addCommand( new Delay( ( 500  ) ) );

                //Send the macro to the robot and play
                macro.setMode(MacroObject.MacroObjectMode.Normal);
                macro.setRobot(mRobot.getRobot());
                macro.playMacro();
                break;
            }

            // Chorus
            case R.id.buttonSphero2_2:{
                // The Set of macros that will be played when we hit the play button. Spheros will follow the
                // set of macros until the very end.
                MacroObject macro = new MacroObject();

                //split the parts
                //another spin ccw
                macro.addCommand( new LoopStart( 3));
                macro.addCommand( new RotateOverTime( 720, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );

                macro.addCommand( new LoopStart( 12));
                macro.addCommand( new RotateOverTime( 540, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );

                macro.addCommand( new LoopStart( 12 ));
                macro.addCommand( new RotateOverTime( 720, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );

                //yes we were born to make history
                // Shake
                macro.addCommand( new LoopStart(10));
                macro.addCommand( new RotateOverTime( 1080, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand(new Roll( 1.5f, 180, 0 ));
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 0, 255 ) );
                macro.addCommand( new Delay( ( 500  ) ) );


                //don't stop us now
                // Shake
                macro.addCommand( new LoopStart(10));
                macro.addCommand( new RotateOverTime( 1080, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                //Move the robot to the back
                macro.addCommand( new Roll( 0.5f, 180, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500 ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );
                //Move the robot to the right
                macro.addCommand( new Roll( 0.5f, 90, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );
                //don't stop us now II
                macro.addCommand( new LoopStart( 30));
                macro.addCommand( new RotateOverTime( 540, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand( new Delay ((500)));
                //trumpets
                macro.addCommand(new LoopStart(4));
                //Move the robot to the left
                macro.addCommand( new Roll( 0.8f, 270, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay((500) ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand( new Delay( ( 500 ) ) );
                //shake
                macro.addCommand( new LoopStart(5));
                macro.addCommand( new RotateOverTime( 1080, 500));
                macro.addCommand( new Delay( (500) ) );
                macro.addCommand ( new LoopEnd());
                macro.addCommand(new Delay(( 500)));
                macro.addCommand( new LoopEnd());

                //Move the robot to the left
                macro.addCommand( new Roll( 0.8f, 90, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay((500) ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );
                macro.addCommand( new Delay( ( 500 ) ) );


                //Send the macro to the robot and play
                macro.setMode(MacroObject.MacroObjectMode.Normal);
                macro.setRobot(mRobot.getRobot());
                macro.playMacro();

                break;
            }

            case R.id.buttonSphero2_3:
            {

                // The Set of macros that will be played when we hit the play button. Spheros will follow the
                // set of macros until the very end.
                MacroObject macro = new MacroObject();

// Move Right
                //Set the robot LED to blue
                // RGB (Red,Green,Blue,DELAY)
                macro.addCommand(new LoopStart(4));
                //macro.addCommand( new RGB( 0, 0, 255, 255 ) );
                //Move the robot to the right
                macro.addCommand( new Roll( 0.3f, 90, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );

                macro.addCommand( new RotateOverTime( 360, 500));
                macro.addCommand( new Delay( ( 500  ) ) );

                //Move Left
                //Move the robot to the left
                macro.addCommand( new Roll( 0.3f, 270, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500 ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand(new LoopEnd());

                //another spin ccw
                macro.addCommand( new LoopStart( 5));
                macro.addCommand( new RotateOverTime( 360, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );

                //victors part
                macro.addCommand( new LoopStart( 30));
                macro.addCommand( new RotateOverTime( 360, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                //you set my heart on fire
                macro.addCommand(new Roll( 1.5f, 0, 0 ));
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 0, 255 ) );
                macro.addCommand( new Delay( ( 500  ) ) );

                //split the parts
                //another spin ccw
                macro.addCommand( new LoopStart( 3));
                macro.addCommand( new RotateOverTime( 720, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );

                macro.addCommand( new LoopStart( 12));
                macro.addCommand( new RotateOverTime( 540, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );

                macro.addCommand( new LoopStart( 12 ));
                macro.addCommand( new RotateOverTime( 720, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );

                //yes we were born to make history
                // Shake
                macro.addCommand( new LoopStart(10));
                macro.addCommand( new RotateOverTime( 1080, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand(new Roll( 1.5f, 0, 0 ));
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 0, 255 ) );
                macro.addCommand( new Delay( ( 500  ) ) );
                //don't stop us now
                // Shake
                macro.addCommand( new LoopStart(10));
                macro.addCommand( new RotateOverTime( 1080, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                //Move the robot to the back
                macro.addCommand( new Roll( 0.5f, 180, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500 ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );
                //Move the robot to the right
                macro.addCommand( new Roll( 0.5f, 90, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );
                //don't stop us now II
                macro.addCommand( new LoopStart( 30));
                macro.addCommand( new RotateOverTime( 540, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand( new Delay ((500)));
                //trumpets
                //Move the robot to the left
                macro.addCommand( new Roll( 0.8f, 270, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay((500) ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand( new Delay( ( 500 ) ) );
                //Move the robot to the left
                macro.addCommand( new Roll( 0.8f, 90, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay((500) ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );
                macro.addCommand( new Delay( ( 500 ) ) );
                //Send the macro to the robot and play
                macro.setMode(MacroObject.MacroObjectMode.Normal);
                macro.setRobot(mRobot.getRobot());
                macro.playMacro();

                break;
            }

            // Sphero2_1
            /*case R.id.buttonChorus:{
                // The Set of macros that will be played when we hit the play button. Spheros will follow the
                // set of macros until the very end.
                MacroObject macro = new MacroObject();

                // Move Right
                //Set the robot LED to blue
                // RGB (Red,Green,Blue,DELAY)
                macro.addCommand(new LoopStart(4));
                //macro.addCommand( new RGB( 0, 0, 255, 255 ) );
                //Move the robot to the right
                macro.addCommand( new Roll( 0.3f, 90, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );

                macro.addCommand( new RotateOverTime( 360, 500));
                macro.addCommand( new Delay( ( 500  ) ) );

                //Move Left
                //Move the robot to the left
                macro.addCommand( new Roll( 0.3f, 270, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500 ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand(new LoopEnd());

                //another spin ccw
                macro.addCommand( new LoopStart( 5));
                macro.addCommand( new RotateOverTime( 360, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );

                //victors part
                macro.addCommand( new LoopStart( 30));
                macro.addCommand( new RotateOverTime( 360, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                //you set my heart on fire
                macro.addCommand(new Roll( 1.5f, 0, 0 ));
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 0, 255 ) );
                macro.addCommand( new Delay( ( 500  ) ) );

                //split the parts
                //another spin ccw
                macro.addCommand( new LoopStart( 3));
                macro.addCommand( new RotateOverTime( 720, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );

                macro.addCommand( new LoopStart( 12));
                macro.addCommand( new RotateOverTime( 540, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );

                macro.addCommand( new LoopStart( 12 ));
                macro.addCommand( new RotateOverTime( 720, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );

                //yes we were born to make history
                // Shake
                macro.addCommand( new LoopStart(10));
                macro.addCommand( new RotateOverTime( 1080, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand(new Roll( 1.5f, 0, 0 ));
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 0, 255 ) );
                macro.addCommand( new Delay( ( 500  ) ) );
                //don't stop us now
                // Shake
                macro.addCommand( new LoopStart(10));
                macro.addCommand( new RotateOverTime( 1080, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                //Move the robot to the back
                macro.addCommand( new Roll( 0.5f, 180, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500 ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );
                //Move the robot to the right
                macro.addCommand( new Roll( 0.5f, 90, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );
                //don't stop us now II
                macro.addCommand( new LoopStart( 30));
                macro.addCommand( new RotateOverTime( 540, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand( new Delay ((500)));
                //trumpets
                //Move the robot to the left
                macro.addCommand( new Roll( 0.8f, 270, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay((500) ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand( new Delay( ( 500 ) ) );
                //Move the robot to the left
                macro.addCommand( new Roll( 0.8f, 90, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay((500) ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );
                macro.addCommand( new Delay( ( 500 ) ) );



                //Send the macro to the robot and play
                macro.setMode(MacroObject.MacroObjectMode.Normal);
                macro.setRobot(mRobot.getRobot());
                macro.playMacro();
                break;
            }*/

            case R.id.buttonChorus2:{
                // The Set of macros that will be played when we hit the play button. Spheros will follow the
                // set of macros until the very end.
                MacroObject macro = new MacroObject();

                //another spin ccw
                macro.addCommand( new LoopStart( 3));
                macro.addCommand( new RotateOverTime( 720, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );

                macro.addCommand( new LoopStart( 12));
                macro.addCommand( new RotateOverTime( 540, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );

                macro.addCommand( new LoopStart( 12 ));
                macro.addCommand( new RotateOverTime( 720, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );

                //yes we were born to make history
                // Shake
                macro.addCommand( new LoopStart(10));
                macro.addCommand( new RotateOverTime( 1080, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand(new Roll( 1.5f, 0, 0 ));
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 0, 255 ) );
                macro.addCommand( new Delay( ( 500  ) ) );


                //don't stop us now
                // Shake
                macro.addCommand( new LoopStart(10));
                macro.addCommand( new RotateOverTime( 1080, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                //Move the robot to the back
                macro.addCommand( new Roll( 0.5f, 180, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500 ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );
                //Move the robot to the right
                macro.addCommand( new Roll( 0.5f, 90, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );
                //don't stop us now II
                macro.addCommand( new LoopStart( 30));
                macro.addCommand( new RotateOverTime( 540, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand( new Delay ((500)));
                //another spin ccw
                macro.addCommand( new LoopStart( 8));
                macro.addCommand( new RotateOverTime( 360, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );

                //another spin ccw
                macro.addCommand( new LoopStart( 8));
                macro.addCommand( new RotateOverTime( 720, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );

                //another spin ccw
                macro.addCommand( new LoopStart( 10));
                macro.addCommand( new RotateOverTime( 1080, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                macro.addCommand( new Delay( ( 500 ) ) );

                //Send the macro to the robot and play
                macro.setMode(MacroObject.MacroObjectMode.Normal);
                macro.setRobot(mRobot.getRobot());
                macro.playMacro();
                break;
            }

            case R.id.buttonEnding:{
                // The Set of macros that will be played when we hit the play button. Spheros will follow the
                // set of macros until the very end.
                MacroObject macro = new MacroObject();

                //END PART1:
                //another spin ccw
                macro.addCommand( new LoopStart( 3));
                macro.addCommand( new RotateOverTime( 720, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );

                macro.addCommand( new LoopStart( 12));
                macro.addCommand( new RotateOverTime( 540, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );

                macro.addCommand( new LoopStart( 12 ));
                macro.addCommand( new RotateOverTime( 720, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );

                //yes we were born to make history
                // Shake
                macro.addCommand( new LoopStart(10));
                macro.addCommand( new RotateOverTime( 1080, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand(new Roll( 1.5f, 0, 0 ));
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 0, 255 ) );
                macro.addCommand( new Delay( ( 500  ) ) );


                //don't stop us now
                // Shake
                macro.addCommand( new LoopStart(10));
                macro.addCommand( new RotateOverTime( 1080, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                //Move the robot to the back
                macro.addCommand( new Roll( 0.5f, 180, 0 ) );
                //Wait until the robot should stop moving
                macro.addCommand( new Delay( 500 ) );
                //Stop
                macro.addCommand( new Roll( 0.0f, 90, 255 ) );
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

            case R.id.buttonEnding2:{
                // The Set of macros that will be played when we hit the play button. Spheros will follow the
                // set of macros until the very end.
                MacroObject macro = new MacroObject();

                //END PART2:

                //don't stop us now II
                macro.addCommand( new LoopStart( 30));
                macro.addCommand( new RotateOverTime( 540, 800));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                //Set the robot to roll left
                macro.addCommand( new Roll( 0.0f, 270, 255 ) );
                macro.addCommand( new Delay ((500)));
                //another spin ccw
                macro.addCommand( new LoopStart( 8));
                macro.addCommand( new RotateOverTime( 360, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );

                //another spin ccw
                macro.addCommand( new LoopStart( 8));
                macro.addCommand( new RotateOverTime( 720, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );

                //another spin ccw
                macro.addCommand( new LoopStart( 10));
                macro.addCommand( new RotateOverTime( 1080, 500));
                macro.addCommand( new Delay( ( 500  ) ) );
                macro.addCommand( new LoopEnd() );
                macro.addCommand( new Delay( ( 500 ) ) );

                //Send the macro to the robot and play
                macro.setMode(MacroObject.MacroObjectMode.Normal);
                macro.setRobot(mRobot.getRobot());
                macro.playMacro();
                break;
            }

            case R.id.btnRotate: {
                tail = tail + 10;
                //mRobot.drive(tail,0);
                mRobot.rotate((float) tail);

                break;
            }

            case R.id.btnSet: {
                mRobot.setZeroHeading();
                break;
            }

            case R.id.buttonStop:
            {
                stopMacro();
                break;
            }

            case R.id.buttonRight:
            {
                mRobot.drive( 90.0f, ROBOT_VELOCITY );
                break;
            }

            case R.id.buttonLeft:
            {
                mRobot.drive( 270.0f, ROBOT_VELOCITY );
                break;
            }

            case R.id.buttonUp:
            {
                mRobot.drive( 0.0f, ROBOT_VELOCITY );
                break;
            }

            case R.id.buttonDown:
            {
                mRobot.drive( 180.0f, ROBOT_VELOCITY );
                break;
            }

            case R.id.buttonStopMove:
            {
                mRobot.stop();
                break;
            }




        }
    }




}
