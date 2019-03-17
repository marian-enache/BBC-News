package com.example.marian.bbcnews

import android.content.Context
import android.graphics.*
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.marian.bbcnews.ui.NewsAdapter
import java.util.*


abstract class SwipeHelper(context: Context, var recyclerView: RecyclerView) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    private var buttons: MutableList<UnderlayButton>? = null
    private lateinit var gestureDetector: GestureDetector
    var swipedPos = -1
    private var swipeThreshold = 0.5f
    private val buttonsBuffer: MutableMap<Int, MutableList<UnderlayButton>>
    private lateinit var recoverQueue: Queue<Int>
    var isDeactivated: Boolean = false

    private var layerWidth: Int = 0

    val type: Int
        get() = type

    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            return true
        }
    }

    private val onTouchListener = object : View.OnTouchListener {
        override fun onTouch(view: View, e: MotionEvent): Boolean {

            if (swipedPos < 0) return false
            val point = Point(e.rawX.toInt(), e.rawY.toInt())

            val swipedViewHolder = recyclerView.findViewHolderForAdapterPosition(swipedPos)
            val swipedItem = swipedViewHolder?.itemView
            val rect = Rect()
            swipedItem?.getGlobalVisibleRect(rect)

            if (e.action == MotionEvent.ACTION_DOWN || e.action == MotionEvent.ACTION_UP || e.action == MotionEvent.ACTION_MOVE) {
                if (rect.top < point.y && rect.bottom > point.y) {
                    gestureDetector.onTouchEvent(e)
                } else {
                    recoverQueue.add(swipedPos)
                    swipedPos = -1
                    recoverSwipedItem()
                }
            }
            return false
        }
    }

    init {
        layerWidth = Utils.dpToPx(context, 115f) // the width of the image
        this.buttons = ArrayList()
        this.gestureDetector = GestureDetector(context, gestureListener)
        this.recyclerView.setOnTouchListener(onTouchListener)
        buttonsBuffer = HashMap()
        recoverQueue = object : LinkedList<Int>() {
            fun add(o: Int?): Boolean {
                return if (contains(o))
                    false
                else
                    super.add(o!!)
            }
        }

        attachSwipe()
    }


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos = viewHolder.adapterPosition

        if (swipedPos != pos)
            recoverQueue.add(swipedPos)

        swipedPos = pos

        if (buttonsBuffer.containsKey(swipedPos))
            buttons = buttonsBuffer[swipedPos]
        else
            buttons!!.clear()

        buttonsBuffer.clear()
        swipeThreshold = 0.5f * buttons!!.size.toFloat() * layerWidth.toFloat()
        recoverSwipedItem()
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return swipeThreshold
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return 0.1f * defaultValue
    }

    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return 5.0f * defaultValue
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (isDeactivated) {
            return
        }

        val pos = viewHolder.adapterPosition
        var translationX = dX
        val itemView = viewHolder.itemView


        if (pos < 0) {
            swipedPos = pos
            return
        }

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                var buffer: MutableList<UnderlayButton>? = ArrayList()

                if (!buttonsBuffer.containsKey(pos)) {
                    instantiateUnderlayButton(viewHolder, buffer!!)
                    buttonsBuffer[pos] = buffer!!
                } else {
                    buffer = buttonsBuffer[pos]
                }

                translationX = dX * buffer!!.size.toFloat() * layerWidth / itemView.width
                drawButtons(c, itemView, buffer, pos, translationX)
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, translationX, dY, actionState, isCurrentlyActive)
    }

    @Synchronized
    private fun recoverSwipedItem() {
        while (!recoverQueue.isEmpty()) {
            val pos = recoverQueue.poll()
            if (pos > -1) {
                recyclerView.adapter!!.notifyItemChanged(pos)
            }
        }
    }

    private fun drawButtons(c: Canvas, itemView: View, buffer: List<UnderlayButton>, pos: Int, dX: Float) {
        var right = itemView.getRight()
        val dButtonWidth = -1 * dX / buffer.size

        var news = (recyclerView.adapter as? NewsAdapter)?.getItemFromAdapter(pos)
        var url = news?.image?.url?.replace("uk/", "uk/\n")
        val text = String.format("Image Details\nw: %d\nh: %d\n%s",
            news?.image?.width,
            news?.image?.height,
            news?.image?.url?.replace("uk/", "uk/\n")
        )

        for (button in buffer) {
            button.text = text

            val left = right - dButtonWidth
            button.onDraw(
                c,
                RectF(
                    left,
                    itemView.getTop().toFloat(),
                    right.toFloat(),
                    itemView.getBottom().toFloat()
                ),
                pos
            )

            right = left.toInt()
        }
    }

    fun attachSwipe() {
        val itemTouchHelper = ItemTouchHelper(this)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    abstract fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder, underlayButtons: MutableList<UnderlayButton>)

    class UnderlayButton(
        private val context: Context,
        private val color: Int
    ) {

        var text: String = ""

        fun onDraw(c: Canvas, rect: RectF, pos: Int) {
            val p = Paint()

            // Draw background
            p.color = color
            c.drawRect(rect, p)

            // Draw Text
            p.color = Color.WHITE
            p.textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                10f,
                context.resources.displayMetrics
            )

            val r = Rect()
            p.textAlign = Paint.Align.LEFT
            p.getTextBounds(text, 0, text.length, r)

            val x = 20f
            var y = 100f
            for (line in text.split("\n")) {
                c.drawText(line, rect.left + x, rect.top + y, p)
                y += p.descent() - p.ascent()
            }
        }
    }
}