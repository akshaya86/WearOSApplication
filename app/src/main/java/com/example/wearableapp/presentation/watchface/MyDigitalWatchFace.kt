package com.example.wearableapp.presentation.watchface

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.*
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.wearable.watchface.CanvasWatchFaceService
import android.support.wearable.watchface.WatchFaceService
import android.support.wearable.watchface.WatchFaceStyle
import android.view.SurfaceHolder
import android.view.WindowInsets
import androidx.core.content.ContextCompat
import com.example.wearableapp.R
import com.example.wearableapp.presentation.ui.MainActivity
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class MyDigitalWatchFace : CanvasWatchFaceService() {

    companion object {
        //private val NORMAL_TYPEFACE = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)

        /**
         * Updates rate in milliseconds for interactive mode. We update once a second since seconds
         * are displayed in interactive mode.
         */
        private const val INTERACTIVE_UPDATE_RATE_MS = 1000

        /**
         * Handler message id for updating the time periodically in interactive mode.
         */
        private const val MSG_UPDATE_TIME = 0
    }

    override fun onCreateEngine(): Engine {
        return Engine()
    }

    private class EngineHandler(reference: MyDigitalWatchFace.Engine) : Handler() {
        private val mWeakReference: WeakReference<MyDigitalWatchFace.Engine> = WeakReference(reference)

        override fun handleMessage(msg: Message) {
            val engine = mWeakReference.get()
            if (engine != null) {
                when (msg.what) {
                    MSG_UPDATE_TIME -> engine.handleUpdateTimeMessage()
                }
            }
        }
    }

    inner class Engine : CanvasWatchFaceService.Engine() {

        private lateinit var mCalendar: Calendar

        private var mRegisteredTimeZoneReceiver = false

        private var mXOffset: Float = 0F
        private var mYOffset: Float = 0F

        private lateinit var mBackgroundPaint: Paint
        private lateinit var mTextPaint: Paint

        /**
         * Whether the display supports fewer bits for each color in ambient mode. When true, we
         * disable anti-aliasing in ambient mode.
         */
        private var mLowBitAmbient: Boolean = false
        private var mBurnInProtection: Boolean = false
        private var mAmbient: Boolean = false

        private val mUpdateTimeHandler: Handler = EngineHandler(this)

        private val mTimeZoneReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                mCalendar.timeZone = TimeZone.getDefault()
                invalidate()
            }
        }

        override fun onCreate(holder: SurfaceHolder) {
            super.onCreate(holder)

            setWatchFaceStyle(
                WatchFaceStyle.Builder(this@MyDigitalWatchFace)
                    .setAcceptsTapEvents(true)
                    .build()
            )

            mCalendar = Calendar.getInstance()

            val resources = this@MyDigitalWatchFace.resources
            mYOffset = resources.getDimension(R.dimen.digital_y_offset)

            // Initializes background.
            mBackgroundPaint = Paint().apply {
                color = ContextCompat.getColor(applicationContext, R.color.background)
            }
            val tf = Typeface.create(Typeface.createFromAsset(applicationContext.assets,"roboto_regular.ttf"),Typeface.NORMAL)

            // Initializes Watch Face.
            mTextPaint = Paint().apply {
                typeface = tf
                isAntiAlias = true
                color = ContextCompat.getColor(
                    applicationContext,
                    R.color.color_berry
                )
            }
        }

        override fun onDestroy() {
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME)
            super.onDestroy()
        }

        override fun onPropertiesChanged(properties: Bundle) {
            super.onPropertiesChanged(properties)
            mLowBitAmbient = properties.getBoolean(
                WatchFaceService.PROPERTY_LOW_BIT_AMBIENT, false
            )
            mBurnInProtection = properties.getBoolean(
                WatchFaceService.PROPERTY_BURN_IN_PROTECTION, false
            )
        }

        override fun onTimeTick() {
            super.onTimeTick()
            invalidate()
        }

        override fun onAmbientModeChanged(inAmbientMode: Boolean) {
            super.onAmbientModeChanged(inAmbientMode)
            mAmbient = inAmbientMode

            if (mLowBitAmbient) {
                mTextPaint.isAntiAlias = !inAmbientMode
            }

            // Whether the timer should be running depends on whether we"re visible (as well as
            // whether we"re in ambient mode), so we may need to start or stop the timer.
            updateTimer()
        }

        /**
         * Captures tap event (and tap type) and toggles the background color if the user finishes
         * a tap.
         */
        override fun onTapCommand(tapType: Int, x: Int, y: Int, eventTime: Long) {
            when (tapType) {
                WatchFaceService.TAP_TYPE_TOUCH -> {
                    // The user has started touching the screen.
                }
                WatchFaceService.TAP_TYPE_TOUCH_CANCEL -> {
                    // The user has started a different gesture or otherwise cancelled the tap.
                }
                WatchFaceService.TAP_TYPE_TAP -> {
                    // The user has completed the tap gesture.
                    startActivity(Intent(this@MyDigitalWatchFace,MainActivity::class.java))
                }

            }
            invalidate()
        }

        override fun onDraw(canvas: Canvas, bounds: Rect) {
            changeBackground(bounds)
           /* val bitmap =
                BitmapFactory.decodeResource(resources, R.drawable.watchafce_digital_circular)
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap,canvas.width,canvas.height,true)
            canvas.drawBitmap(scaledBitmap, 0f, 0f, null)*/

            // Draw HH:MM in ambient mode or H:MM:SS in interactive mode.
            val now = System.currentTimeMillis()
            mCalendar.timeInMillis = now


            val timeFormat = "h:mm a"
            val nowTime = LocalTime.now().format(DateTimeFormatter.ofPattern(timeFormat))

            val dateFormat = "MMM dd yyyy"

            val formatedDate = SimpleDateFormat(
                dateFormat,
                Locale.getDefault()
            ).format(Calendar.getInstance().time)


            val tf = Typeface.create(Typeface.createFromAsset(applicationContext.assets,
                "roboto_regular.ttf"),Typeface.NORMAL)

           // mYOffset = bitmap.height - resources.getDimension(R.dimen.dimens_140dp)
            mXOffset = (canvas.width / 4.99).toFloat()
            canvas.drawText(nowTime, mXOffset, mYOffset, mTextPaint)
            val textColor = Paint().apply {
                typeface = tf
                isAntiAlias = true
                color = ContextCompat.getColor(
                    applicationContext,
                    R.color.color_berry
                )
            }
            textColor.textSize = resources.getDimension(R.dimen.font_14sp)
            canvas.drawText(formatedDate, mXOffset + resources.getDimension(R.dimen.dimens_50dp),
                mYOffset + resources.getDimension(R.dimen.font_14sp), textColor)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)

            if (visible) {
                registerReceiver()

                // Update time zone in case it changed while we weren"t visible.
                mCalendar.timeZone = TimeZone.getDefault()
                invalidate()
            } else {
                unregisterReceiver()
            }

            // Whether the timer should be running depends on whether we"re visible (as well as
            // whether we"re in ambient mode), so we may need to start or stop the timer.
            updateTimer()
        }

        private fun registerReceiver() {
            if (mRegisteredTimeZoneReceiver) {
                return
            }
            mRegisteredTimeZoneReceiver = true
            val filter = IntentFilter(Intent.ACTION_TIMEZONE_CHANGED)
            this@MyDigitalWatchFace.registerReceiver(mTimeZoneReceiver, filter)
        }

        private fun unregisterReceiver() {
            if (!mRegisteredTimeZoneReceiver) {
                return
            }
            mRegisteredTimeZoneReceiver = false
            this@MyDigitalWatchFace.unregisterReceiver(mTimeZoneReceiver)
        }

        override fun onApplyWindowInsets(insets: WindowInsets) {
            super.onApplyWindowInsets(insets)

            // Load resources that have alternate values for round watches.
            val resources = this@MyDigitalWatchFace.resources
            val isRound = insets.isRound
            mXOffset = resources.getDimension(
                if (isRound)
                    R.dimen.digital_x_offset_round
                else
                    R.dimen.digital_x_offset
            )

            val textSize = resources.getDimension(
                if (isRound)
                    R.dimen.digital_text_size_round
                else
                    R.dimen.digital_text_size
            )

            mTextPaint.textSize = textSize
        }

        /**
         * Starts the [.mUpdateTimeHandler] timer if it should be running and isn"t currently
         * or stops it if it shouldn"t be running but currently is.
         */
        private fun updateTimer() {
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME)
            if (shouldTimerBeRunning()) {
                mUpdateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME)
            }
        }

        /**
         * Returns whether the [.mUpdateTimeHandler] timer should be running. The timer should
         * only run when we"re visible and in interactive mode.
         */
        private fun shouldTimerBeRunning(): Boolean {
            return isVisible && !isInAmbientMode
        }

        /**
         * Handle updating the time periodically in interactive mode.
         */
        fun handleUpdateTimeMessage() {
            invalidate()
            if (shouldTimerBeRunning()) {
                val timeMs = System.currentTimeMillis()
                val delayMs = INTERACTIVE_UPDATE_RATE_MS - timeMs % INTERACTIVE_UPDATE_RATE_MS
                mUpdateTimeHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, delayMs)
            }
        }
    }

    private fun changeBackground(bounds: Rect) {
        val mainPaint = Paint()
        val colors = intArrayOf( Color.BLACK, Color.BLACK, applicationContext.getColor(R.color.color_berry), Color.BLACK, )
        val backgroundColor = SweepGradient( bounds.exactCenterX(), bounds.exactCenterY(), colors, null )

        val matrix = Matrix()
        matrix.preRotate(27f, bounds.exactCenterX(), bounds.exactCenterY())
        backgroundColor.setLocalMatrix(matrix)
        mainPaint.shader = backgroundColor
    }
}