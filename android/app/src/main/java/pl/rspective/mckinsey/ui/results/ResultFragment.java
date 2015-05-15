package pl.rspective.mckinsey.ui.results;

import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.db.chart.Tools;
import com.db.chart.listener.OnEntryClickListener;
import com.db.chart.model.Bar;
import com.db.chart.model.BarSet;
import com.db.chart.view.BarChartView;
import com.db.chart.view.XController;
import com.db.chart.view.YController;
import com.db.chart.view.animation.Animation;
import com.db.chart.view.animation.easing.BaseEasingMethod;
import com.db.chart.view.animation.easing.quint.QuintEaseOut;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rspective.mckinsey.R;

public class ResultFragment extends Fragment {

    public static ResultFragment newInstance() {
        return new ResultFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_results, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initBarChart();

        updateBarChart();
    }

    private final TimeInterpolator enterInterpolator = new DecelerateInterpolator(1.5f);
    private final TimeInterpolator exitInterpolator = new AccelerateInterpolator();


    /**
     * Order
     */
    private static float mCurrOverlapFactor;
    private static int[] mCurrOverlapOrder;
    private static float mOldOverlapFactor;
    private static int[] mOldOverlapOrder;


    /**
     * Ease
     */
    private static BaseEasingMethod mCurrEasing;
    private static BaseEasingMethod mOldEasing;


    /**
     * Enter
     */
    private static float mCurrStartX;
    private static float mCurrStartY;
    private static float mOldStartX;
    private static float mOldStartY;


    /**
     * Alpha
     */
    private static int mCurrAlpha;
    private static int mOldAlpha;


    /**
     * Bar
     */
    private final static int BAR_MAX = 10;
    private final static int BAR_MIN = 0;
    private final static String[] barLabels = {"A", "B", "C", "D", "E", "F", "G"};
    private final static float [][] barValues = { {5, 3, 0, 8, 1, 1, 2} };

    @InjectView(R.id.barchart)
    BarChartView mBarChart;
    private Paint mBarGridPaint;
    private TextView mBarTooltip;

    private final OnEntryClickListener barEntryListener = new OnEntryClickListener(){
        @Override
        public void onClick(int setIndex, int entryIndex, Rect rect) {
            if(mBarTooltip == null)
                showBarTooltip(setIndex, entryIndex, rect);
            else
                dismissBarTooltip(setIndex, entryIndex, rect);
        }
    };

    private final View.OnClickListener barClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(mBarTooltip != null)
                dismissBarTooltip(-1, -1, null);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrOverlapFactor = 1;
        mCurrEasing = new QuintEaseOut();
        mCurrStartX = -1;
        mCurrStartY = 0;
        mCurrAlpha = -1;

        mOldOverlapFactor = 1;
        mOldEasing = new QuintEaseOut();
        mOldStartX = -1;
        mOldStartY = 0;
        mOldAlpha = -1;
    }


	/*------------------------------------*
	 *              BARCHART              *
	 *------------------------------------*/

    private void initBarChart(){
        mBarChart.setOnEntryClickListener(barEntryListener);
        mBarChart.setOnClickListener(barClickListener);

        mBarGridPaint = new Paint();
        mBarGridPaint.setColor(this.getResources().getColor(R.color.bar_grid));
        mBarGridPaint.setStyle(Paint.Style.STROKE);
        mBarGridPaint.setAntiAlias(true);
        mBarGridPaint.setStrokeWidth(Tools.fromDpToPx(.75f));
    }


    private void updateBarChart(){

        mBarChart.reset();

        BarSet barSet = new BarSet();
        Bar bar;
        for(int i = 0; i < barLabels.length; i++){
            bar = new Bar(barLabels[i], barValues[0][i]);
            if(i == 4)
                bar.setColor(this.getResources().getColor(R.color.bar_highest));
            else
                bar.setColor(this.getResources().getColor(R.color.bar_fill1));
            barSet.addBar(bar);
        }
        mBarChart.addData(barSet);

        mBarChart.setSetSpacing(Tools.fromDpToPx(3));
        mBarChart.setBarSpacing(Tools.fromDpToPx(14));

        mBarChart.setBorderSpacing(0)
                .setAxisBorderValues(BAR_MIN, BAR_MAX, 2)
                .setGrid(BarChartView.GridType.FULL, mBarGridPaint)
                .setYAxis(false)
                .setXLabels(XController.LabelPosition.OUTSIDE)
                .setYLabels(YController.LabelPosition.NONE)
                .show(getAnimation(true))
        //.show()
        ;
    }


    @SuppressLint("NewApi")
    private void showBarTooltip(int setIndex, int entryIndex, Rect rect){
        mBarTooltip = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.bar_tooltip, null);
        mBarTooltip.setText(Integer.toString((int) barValues[setIndex][entryIndex]));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(rect.width(), rect.height());
        layoutParams.leftMargin = rect.left;
        layoutParams.topMargin = rect.top;
        mBarTooltip.setLayoutParams(layoutParams);

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){
            mBarTooltip.setAlpha(0);
            mBarTooltip.setScaleY(0);
            mBarTooltip.animate()
                    .setDuration(200)
                    .alpha(1)
                    .scaleY(1)
                    .setInterpolator(enterInterpolator);
        }

        mBarChart.showTooltip(mBarTooltip);
    }


    @SuppressLint("NewApi")
    private void dismissBarTooltip(final int setIndex, final int entryIndex, final Rect rect){

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            mBarTooltip.animate()
                    .setDuration(100)
                    .scaleY(0)
                    .alpha(0)
                    .setInterpolator(exitInterpolator).withEndAction(new Runnable(){
                @Override
                public void run() {
                    mBarChart.removeView(mBarTooltip);
                    mBarTooltip = null;
                    if(entryIndex != -1)
                        showBarTooltip(setIndex, entryIndex, rect);
                }
            });
        }else{
            mBarChart.dismissTooltip(mBarTooltip);
            mBarTooltip = null;
            if(entryIndex != -1)
                showBarTooltip(setIndex, entryIndex, rect);
        }
    }


    private void updateValues(BarChartView chartView){

        chartView.updateValues(0, barValues[1]);
        chartView.updateValues(1, barValues[0]);
        chartView.notifyDataUpdate();
    }


	/*------------------------------------*
	 *               GETTERS              *
	 *------------------------------------*/

    private Animation getAnimation(boolean newAnim){
        if(newAnim)
            return new Animation()
                    .setAlpha(mCurrAlpha)
                    .setEasing(mCurrEasing)
                    .setOverlap(mCurrOverlapFactor, mCurrOverlapOrder)
                    .setStartPoint(mCurrStartX, mCurrStartY);
        else
            return new Animation()
                    .setAlpha(mOldAlpha)
                    .setEasing(mOldEasing)
                    .setOverlap(mOldOverlapFactor, mOldOverlapOrder)
                    .setStartPoint(mOldStartX, mOldStartY);
    }
}
