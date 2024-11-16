package org.firstinspires.ftc.teamcode.Tele.Config_Const;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.lib.drive.SampleMecanumDrive;

import java.util.Timer;


public class Robot {
    public ElapsedTime timer = new ElapsedTime();
    public Arm cla = new Arm();
    //public boolean SecondDriver = false;
    HardwareMap hardwareMap;
    Pose2d endpose;
    public Chassis c = new Chassis(hardwareMap, endpose);
    //public Visual v = new Visual();
    public Robot(HardwareMap mp, Pose2d endpos)
    {
        this.hardwareMap = mp;
        this.endpose = endpos;
    }

    public void AutoinitRemote(HardwareMap hwm){
        cla.autoInit(hwm);
        timer.reset();
        c.AutoInit(hwm);

    }

    public void Teleinit(HardwareMap hwm){
        c.TeleInit(hwm);
        cla.teleInit(hwm);
        timer.reset();
        //v.apt();
    }


    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

//    public void updatevisualposMT1(){
//        c.posvisual(v.getfieldposeMT1());
//    }
//
//    public void updatevisualposMT2(){
//        c.posvisual(v.getfieldposeMT2());
//    }

}
