package org.firstinspires.ftc.teamcode.lib;

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Servo


object ArmKt{
    var isTele : Boolean = true
    val vtSlider_lfMt: DcMotorEx
    val vtSlider_rtMt: DcMotorEx
    val vtSliderMtPower = 0.7

    val hzSlider_mt: DcMotorEx
    val hzSliderMtPower = 0.7

    val frSpIntake_mt: DcMotorEx
    var frSpIntakeState = FrSpIntakeState.STOP

    val bkSpClaw_sv: Servo

    val frSpFlipper_lfSv: Servo
    val frSpFlipper_rtSv: Servo

    val bkSpFlipper_lfSv: Servo
    val bkSpFlipper_rtSv: Servo

    init {
        val hwMap = shLinOp!!.hardwareMap
        frSpIntake_mt = hwMap.get(DcMotorEx::class.java, "frSpIntake_mt")
        vtSlider_lfMt = hwMap.get(DcMotorEx::class.java, "vtSlider_lfMt")
        vtSlider_rtMt = hwMap.get(DcMotorEx::class.java, "vtSlider_rtMt")
        hzSlider_mt = hwMap.get(DcMotorEx::class.java, "hzSlider_mt")
        bkSpClaw_sv = hwMap.get(Servo::class.java, "bkSpClaw_sv")
        frSpFlipper_lfSv = hwMap.get(Servo::class.java, "frSpFlipper_lfSv")
        frSpFlipper_rtSv = hwMap.get(Servo::class.java, "frSpFlipper_rtSv")
        bkSpFlipper_lfSv = hwMap.get(Servo::class.java, "bkSpFlipper_lfSv")
        bkSpFlipper_rtSv = hwMap.get(Servo::class.java, "bkSpFlipper_rtSv")

        vtSlider_lfMt.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        vtSlider_lfMt.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        vtSlider_lfMt.mode = DcMotor.RunMode.RUN_USING_ENCODER

        vtSlider_rtMt.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        vtSlider_rtMt.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        vtSlider_rtMt.mode = DcMotor.RunMode.RUN_USING_ENCODER

        hzSlider_mt.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        hzSlider_mt.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        hzSlider_mt.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        frSpIntake_mt.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        frSpIntake_mt.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        BkSpFlipperState.DOWN.moveTo();
        FrSpFlipperState.UP.moveTo();
    }

    fun teleInit() {
        isTele = true
        BkSpClawState.OPEN.moveTo();
    }

    fun autoInit(){
        isTele = false
        BkSpClawState.CLOSE.moveTo();
    }

    var vtSliderHeiAdj : Int
        get(){
            assert (vtSlider_lfMt.currentPosition == -vtSlider_rtMt.currentPosition)
            return vtSlider_lfMt.currentPosition
        }
        set(value) {
            // constrain the value to the range of the slider
            val adjVal = value.coerceIn(VtSliderHeiState.LOWEST.pos, VtSliderHeiState.HIGHEST.pos)
            vtSlider_lfMt.targetPosition = adjVal
            vtSlider_rtMt.targetPosition = -adjVal
            vtSlider_lfMt.power = vtSliderMtPower
            vtSlider_rtMt.power = vtSliderMtPower
            vtSlider_lfMt.mode = DcMotor.RunMode.RUN_TO_POSITION
            vtSlider_rtMt.mode = DcMotor.RunMode.RUN_TO_POSITION
        }

    var hzSliderLenAdj : Int
        get() = hzSlider_mt.currentPosition
        set(value) {
            val adjVal = value.coerceIn(HzSliderLenState.SHORTEST.pos, HzSliderLenState.LONGEST.pos)
            hzSlider_mt.targetPosition = adjVal
            hzSlider_mt.power = hzSliderMtPower
            hzSlider_mt.mode = DcMotor.RunMode.RUN_TO_POSITION
        }

    enum class HzSliderLenState(val pos : Int) {
        LONGEST(1500),
        BK_FLIPPER_NO_CONFLICT_MIN(100),
        VT_SLIDER_NO_CONFLICT_MIN(100),
        SHORTEST(5);
        fun moveTo(){
            hzSliderLenAdj = pos
        }
    }

    enum class VtSliderHeiState(val pos : Int) {
        HIGHEST(2800), // not implemented
        HIGH_BASKET(2755),
        LOW_BASKET(1242),
        HIGH_BAR(1561),
        LOW_BAR(1000),
        HZ_SLIDER_NO_CONFLICT_MAX(100),
        SIDE_SP(147),
        LOWEST(5){
            override fun moveTo(){
                if (hzSliderLenAdj < HzSliderLenState.VT_SLIDER_NO_CONFLICT_MIN.pos
                    && vtSliderHeiAdj > HZ_SLIDER_NO_CONFLICT_MAX.pos
                    ){
                    HzSliderLenState.VT_SLIDER_NO_CONFLICT_MIN.moveTo()
                }
                moveTo()
            }
        };
        open fun moveTo(){
            vtSliderHeiAdj = pos
        }
    }

    enum class BkSpClawState(val pos : Double) {
        OPEN(0.37),
        CLOSE(0.15);
        fun moveTo(){
            bkSpClaw_sv.position = pos
        }
    }

    enum class FrSpFlipperState(val lPos : Double, val rPos : Double) {
        UP(0.1, 1.0),
        DOWN(0.8157, 0.2843);
        fun moveTo(){
            frSpFlipper_lfSv.position = lPos
            frSpFlipper_rtSv.position = rPos
        }
    }

    enum class FrSpIntakeState(val power : Double) {
        OUT(-1.0),
        IN(1.0),
        STOP(0.0);
        fun moveTo(){
            frSpIntake_mt.power = power
            frSpIntakeState = this
        }
    }

    enum class BkSpFlipperState(val lPos : Double, val rPos : Double) {
        UP(0.2767, 0.8333){
            override fun moveTo(){
                if (hzSliderLenAdj < HzSliderLenState.BK_FLIPPER_NO_CONFLICT_MIN.pos){
                    HzSliderLenState.BK_FLIPPER_NO_CONFLICT_MIN.moveTo()
                }
                moveTo();
            }
        },
        DOWN(0.89, 0.22);
        open fun moveTo() {
            bkSpFlipper_lfSv.position = lPos
            bkSpFlipper_rtSv.position = rPos
        }
    }

    fun dropSpOnClaw() {
        if (vtSlider_lfMt.currentPosition > 1000) {
            vtSliderHeiAdj -= 350;
            sleep(500)
        }
        BkSpClawState.OPEN.moveTo()
    }


    private fun sleep(ms: Long) {
        try {
            Thread.sleep(ms)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }
}
