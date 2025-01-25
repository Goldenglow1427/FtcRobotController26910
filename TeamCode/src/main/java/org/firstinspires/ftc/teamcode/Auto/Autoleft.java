package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ext.roadrunner.trajectorysequence.TrajectorySequence;

@Config
//@Autonomous
@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class Autoleft extends LinearOpMode {
    public static double g1x = -11.705;
    public static double g1y = -38.017;
    public static double g1ang = 3.1415;
    public static double g2x = -11.705;
    public static double g2y = -45.517;
    public static double g2ang = 180;
    public static double g3x = 20.55;
    public static double g3y = -9.15;
    public static double g3ang = 80;

    public Pose2d startPos = new Pose2d(0, 0, 0);
    public Pose2d highBarPos = new Pose2d(-29.7918, 3.8298, 0);////改
    public Pose2d g1Pos = new Pose2d(g1x, g1y, g1ang);
    public Pose2d g2Pos = new Pose2d(g2x, g2y, Math.toRadians(g2ang));//需要调整
    public Pose2d g3Pos = new Pose2d(g3x, g3y, Math.toRadians(g3ang));
    //    //public Pose2d g3Pos = new Pose217.7793, 30.1948, 1.9523);//需要调整

    public Pose2d highBarPos1 = new Pose2d(0.5, -45.5428, Math.toRadians(132.259));//需要调整
    public Pose2d highBarPos2 = new Pose2d(-11.73, 2.51, Math.toRadians(307));//需要调整
    public Pose2d highBarPos3 = new Pose2d(-9.93, 2.5, Math.toRadians(307));//需要调整

    @Override
    public void runOpMode() throws InterruptedException {
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        AutoRobot robot = new AutoRobot(hardwareMap);
        TrajectorySequence FinalAuto1 = robot.chassis.drive.trajectorySequenceBuilder(startPos)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.arm.highBar();//把arm伸上去
                })
                .waitSeconds(0.5)
                .lineToLinearHeading(highBarPos)//走到杆前面

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.arm.dropSpe();//挂上并松手
                })
                .waitSeconds(0.2)//操作等待时间
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.arm.VtBack();//把arm收回来
                })

                .lineToLinearHeading(g1Pos)//准备吸取第一个地上的
                .UNSTABLE_addTemporalMarkerOffset(-1, () -> {
                    robot.arm.HzArmSet(450);
                })
                .UNSTABLE_addTemporalMarkerOffset(-0.5, () -> {
                    robot.arm.frontIntakeDown();
                })
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.arm.frontIntake();
                })
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.arm.inArmTrans();
                })
                .waitSeconds(0.5)//操作时间
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                    robot.arm.HzArmSet(100);
                })
                .lineToLinearHeading(highBarPos1)//夹样本位置
                .UNSTABLE_addTemporalMarkerOffset(-1, () -> {
                    robot.arm.frontIntakeDown();
                    robot.arm.highBasket();
                })
                .waitSeconds(1)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.arm.basketOut();
                })
                .waitSeconds(1)

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.arm.basketBack();

                })
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    robot.arm.VtBack();

                })
                .splineTo(new Vector2d(g2Pos.getX(), g2Pos.getY()), g2Pos.getHeading())
                //.splineTo(g2Pos)//准备吸取第一个地上的
                .UNSTABLE_addTemporalMarkerOffset(-1, () -> {
                    robot.arm.HzArmSet(480);
                })
                .UNSTABLE_addTemporalMarkerOffset(-1, () -> {
                    robot.arm.frontIntakeDown();
                })
                .waitSeconds(1)
                .build();

        TrajectorySequence FinalAuto2 = robot.chassis.drive.trajectorySequenceBuilder(startPos)


                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.arm.frontIntake();
                })
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.arm.inArmTrans();
                })
                .waitSeconds(0.5)//操作时间
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.arm.HzArmSet(100);
                })
                .lineToLinearHeading(highBarPos2)//夹样本位置
                .UNSTABLE_addTemporalMarkerOffset(-1, () -> {
                    robot.arm.frontIntakeDown();
                    robot.arm.highBasket();
                })
                .waitSeconds(1)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.arm.basketOut();
                })
                .waitSeconds(1)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.arm.basketBack();
                    robot.arm.VtBack();
                })
                //.waitSeconds(2)
                .splineTo(new Vector2d(g3Pos.getX(), g3Pos.getY()), g3Pos.getHeading())
                .UNSTABLE_addTemporalMarkerOffset(-1, () -> {
                    robot.arm.HzArmSet(470);
                })
                .UNSTABLE_addTemporalMarkerOffset(-1, () -> {
                    robot.arm.frontIntakeDown();
                })
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.arm.frontIntake();
                })
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.arm.inArmTrans();
                })
                .waitSeconds(0.5)//操作时间
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.arm.HzArmSet(100);
                })
                .lineToLinearHeading(highBarPos3)//夹样本位置
                .UNSTABLE_addTemporalMarkerOffset(-1, () -> {
                    robot.arm.frontIntakeDown();
                    robot.arm.highBasket();
                })
                .waitSeconds(1)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.arm.basketOut();
                })
                .waitSeconds(1)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.arm.basketBack();
                    robot.arm.VtBack();
                    robot.arm.HzArmSet(0);
                })
                .waitSeconds(5)
                .back(1)
                .build();


        robot.Autoinit(hardwareMap);
        waitForStart();
        robot.chassis.drive.setPoseEstimate(startPos);
        //while (!isStopRequested() && opModeIsActive()){
        robot.chassis.drive.followTrajectorySequence(FinalAuto1);
        robot.chassis.drive.setPoseEstimate(new Pose2d(0, 0, 0));
        robot.chassis.drive.followTrajectorySequence(FinalAuto2);


    }

}
