package org.firstinspires.ftc.teamcode.lib;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {
    public DcMotorEx VtLeft = null;
    public DcMotorEx VtRight = null;
    public DcMotorEx hzFront = null;
    public DcMotorEx intakeMotor = null;
    public Servo speClaw = null;
    public Servo inArmLeft = null;
    public Servo inArmRight = null;
    public Servo outArmLeft = null;
    public Servo outArmRight = null;
    public boolean reverseIntake = false;
    public boolean getIntake = false;
    public int frontArmPos = 0;


    public void autoInit(HardwareMap hwm) {

        intakeMotor = hwm.get(DcMotorEx.class, "frSpIntake_mt");
        VtLeft = hwm.get(DcMotorEx.class, "vtSlider_lfMt");
        VtRight = hwm.get(DcMotorEx.class, "vtSlider_rtMt");
        hzFront = hwm.get(DcMotorEx.class, "hzSlider_mt");
        speClaw = hwm.get(Servo.class, "bkSpClaw_sv");
        inArmLeft = hwm.get(Servo.class, "frSpFlipper_lfSv");
        inArmRight = hwm.get(Servo.class, "frSpFlipper_rtSv");
        outArmLeft = hwm.get(Servo.class, "bkSpFlipper_lfSv");
        outArmRight = hwm.get(Servo.class, "bkSpFlipper_rtSv");
        VtLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        VtLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        VtLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        VtRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        VtRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        VtRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        hzFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        hzFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hzFront.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        intakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        intakeMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        //basketBack();
        //inArmTrans();
        //closeClaw();
    }

    public void teleInit(HardwareMap hwm) {
        intakeMotor = hwm.get(DcMotorEx.class, "frSpIntake_mt");
        VtLeft = hwm.get(DcMotorEx.class, "vtSlider_lfMt");
        VtRight = hwm.get(DcMotorEx.class, "vtSlider_rtMt");
        hzFront = hwm.get(DcMotorEx.class, "hzSlider_mt");
        speClaw = hwm.get(Servo.class, "bkSpClaw_sv");
        inArmLeft = hwm.get(Servo.class, "frSpFlipper_lfSv");
        inArmRight = hwm.get(Servo.class, "frSpFlipper_rtSv");
        outArmLeft = hwm.get(Servo.class, "bkSpFlipper_lfSv");
        outArmRight = hwm.get(Servo.class, "bkSpFlipper_rtSv");
        VtLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        //VtLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        VtLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        VtRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        //VtRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        VtRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        hzFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        //hzFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hzFront.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        intakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        intakeMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        basketBack();

        inArmTrans();
        speClaw.setPosition(0.37);
        HzArmSet(5);
    }

    public void VtArmSet(int pos) {
        VtLeft.setTargetPosition(pos);
        VtLeft.setPower(1);
        VtLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        VtRight.setPower(1);
        VtRight.setTargetPosition(-pos);
        VtRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void HzArmSet(int pos) {
        hzFront.setPower(0.7);
        frontArmPos = pos;
        hzFront.setTargetPosition(-frontArmPos);
        hzFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void closeClaw() {//夹样本
        //爪子夹样本的位置
        speClaw.setPosition(0.15);
    }

    public void dropSpe() {//放下样本松手
        int k = 500;    //向下移动多少
        if(VtLeft.getCurrentPosition() > 800) {
            VtArmSet(VtLeft.getCurrentPosition() - k);
            sleep(500);
        }

        speClaw.setPosition(0.37);//松手舵机位置

    }

    //mudheadcar
    public void HzArmVel(double power) {
        // 控制目标点
        frontArmPos += 10 * power;
        if (frontArmPos > 1450) {
            HzArmSet(1450);
        } else if (frontArmPos < 5) {
            HzArmSet(5);
        } else {
            HzArmSet(frontArmPos);
        }

    }


    public void frontIntake() {//放下前面arm开始吸
        // 把两个 servo 放下去
        // 把滚吸过放下去
        inArmLeft.setPosition(0.8157);//左arm位置
        inArmRight.setPosition(0.2843);//右arm位置
        //gathering = true;
    }


    public void intakeMupdate() {
        // rev 是吐出来
        // getIntake 是正着吸
        if (reverseIntake) {
            intakeMotor.setPower(0.8);//滚吸反转功率
        } else if (getIntake) {
            intakeMotor.setPower(-1);//滚吸功率
        } else {
            intakeMotor.setPower(0);
        }
    }

    public void inArmTrans() {//收回arm并反转
        // 滚吸收回来
        // getIntake false 不再吸了
        inArmLeft.setPosition(0.1);//左arm位置
        inArmRight.setPosition(1);//右arm位置
        getIntake = false;
    }

    public void frontArmBack() {//收回arm并反转
        // 快捷键收回横着的滑轨
        HzArmSet(5);
    }


    public void highBasket() {
        VtArmSet(2755);//高框arm位置
    }

    public void lowBasket() {
        VtArmSet(1242);//低框arm位置
    }

    public void highBar() {
        VtArmSet(1500);//高杆arm位置
    }

    public void lowBar() {
        VtArmSet(1000);//低杆arm位置
    }

    public void takeSpePos() {
        // 场边的 specimen 的位置
        if(frontArmPos <= 100  && VtLeft.getCurrentPosition() > 200) {
            HzArmSet(200);
            // set 到 200 避免冲突
        }
        VtArmSet(147);//夹取样本位置

    }

    public void VtBack() {
        //
        if (frontArmPos <= 100 && VtLeft.getCurrentPosition() > 200) {
            HzArmSet(200);

            // set 到 200 避免冲突
        }

        VtArmSet(0); // 竖着
        if (VtLeft.getCurrentPosition() < 5){
            VtLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            VtRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    }

    public void basketOut() {//倒到框里
        double d = 0.61 - 0.2767;
        outArmLeft.setPosition(0.61 - d);//左arm位置
        outArmRight.setPosition(0.5 + d);//右arm位置
    }

    public void basketBack() {//框里收回来
        double d= 0.89 - 0.61;
        if(frontArmPos < 200 && VtLeft.getCurrentPosition() < 200) {
            // 如果竖着的杆太低了，横着的杆又收的抬回来了。
            HzArmSet(200);
        }
        outArmLeft.setPosition(0.61 + d);//左arm位置
        outArmRight.setPosition(0.5 - d);//右arm位置
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
