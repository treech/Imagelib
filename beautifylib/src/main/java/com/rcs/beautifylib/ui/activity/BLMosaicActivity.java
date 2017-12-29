package com.rcs.beautifylib.ui.activity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.rcs.beautifylib.R;
import com.rcs.beautifylib.model.BLMosaicParam;
import com.rcs.beautifylib.mosaic.DrawMosaicView;
import com.rcs.beautifylib.mosaic.FileUtils;
import com.rcs.beautifylib.mosaic.MosaicUtil;
import com.rcs.beautifylib.ui.activity.base.BLToolBarActivity;

public class BLMosaicActivity extends BLToolBarActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private BLMosaicParam mParam;
    private Bitmap mSourceBitmap;
    private int mWidth, mHeight;
    private DrawMosaicView mDrawMosaicView;
    private SeekBar mMosaicSizeView;
    private int mPaintSize;

    private TextView mMosaic_Base;
    private TextView mMosaic_Glass;
    private TextView mMosaic_Flower;
    private TextView mMosaic_Eraser;
    private int TXT_NORMAL_COLOR = Color.BLACK;
    private int TXT_SELECTED_COLOR = R.color.camerasdk_text_red;

    private static final int INDEX_MOSAIC_BASE = 0x01;
    private static final int INDEX_MOSAIC_GLASS = 0x02;
    private static final int INDEX_MOSAIC_FLOWER = 0x03;
    private static final int INDEX_MOSAIC_ERASER = 0x04;

    @Override
    protected int getContentLayoutId() {
        return R.layout.bl_activity_mosaic;
    }

    @Override
    protected void customToolBarStyle() {
        mToolbar.setTitle(getResources().getString(R.string.camerasdk_mosaic));
        mToolbar.inflateMenu(R.menu.menu_preview);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.preview_menu) {
                    //涂鸦完成
                    Bitmap bit = mDrawMosaicView.getMosaicBitmap();
                    BLMosaicParam.mBitmap = bit;
                    setResult(RESULT_OK);
                    onBackPressed();
                }
                return false;
            }
        });
    }

    @Override
    protected void initView() {
        mDrawMosaicView = getViewById(R.id.mosaic_draw_view);
        mMosaic_Base = getViewById(R.id.mosaic_paint_base);
        mMosaic_Glass = getViewById(R.id.mosaic_paint_glass);
        mMosaic_Flower = getViewById(R.id.mosaic_paint_flower);
        mMosaic_Eraser = getViewById(R.id.mosaic_paint_eraser);

        mMosaicSizeView = getViewById(R.id.mosaic_paint_size_sb);
    }

    @Override
    protected void otherLogic() {
        mParam = getIntent().getParcelableExtra(BLMosaicParam.KEY);
        mSourceBitmap = BLMosaicParam.mBitmap;
        //背景图片
        mDrawMosaicView.setMosaicBackgroundResource(mSourceBitmap);
        mWidth = mSourceBitmap.getWidth();
        mHeight = mSourceBitmap.getHeight();

        //编辑的图片
        Bitmap bit = MosaicUtil.getMosaic(mSourceBitmap);
        mDrawMosaicView.setMosaicResource(bit);

        mPaintSize = mMosaicSizeView.getProgress();
        mDrawMosaicView.setMosaicBrushWidth(mPaintSize);
    }

    @Override
    protected void setListener() {
        mMosaic_Base.setOnClickListener(this);
        mMosaic_Glass.setOnClickListener(this);
        mMosaic_Flower.setOnClickListener(this);
        mMosaic_Eraser.setOnClickListener(this);
        mMosaicSizeView.setOnSeekBarChangeListener(this);
    }


    @Override
    public void onClick(View v) {
        int resId = v.getId();
        if (resId == R.id.mosaic_paint_base) {
            onClickMosaicBase();
        } else if (resId == R.id.mosaic_paint_glass) {
            onClickMosaicGlass();
        } else if (resId == R.id.mosaic_paint_flower) {
            onClickMosaicFlower();
        } else if (resId == R.id.mosaic_paint_eraser) {
            onClickMosaicEraser();
        }
    }

    //基本色
    private void onClickMosaicBase() {
        setSelectedTextColor(INDEX_MOSAIC_BASE);
        Bitmap bitmapMosaic = MosaicUtil.getMosaic(mSourceBitmap);
        mDrawMosaicView.setMosaicResource(bitmapMosaic);
    }

    //毛玻璃
    private void onClickMosaicGlass() {
        setSelectedTextColor(INDEX_MOSAIC_GLASS);
        Bitmap bitmapBlur = MosaicUtil.getBlur(mSourceBitmap);
        mDrawMosaicView.setMosaicResource(bitmapBlur);
    }

    //花色
    private void onClickMosaicFlower() {
        setSelectedTextColor(INDEX_MOSAIC_FLOWER);
        Bitmap bit = BitmapFactory.decodeResource(getResources(),
                R.drawable.hi4);
        bit = FileUtils.ResizeBitmap(bit, mWidth, mHeight);
        mDrawMosaicView.setMosaicResource(bit);
    }

    //橡皮擦
    private void onClickMosaicEraser() {
        setSelectedTextColor(INDEX_MOSAIC_ERASER);
        mDrawMosaicView.setMosaicType(MosaicUtil.MosaicType.ERASER);
    }

    private void setSelectedTextColor(int index) {
        switch (index) {
            case INDEX_MOSAIC_BASE:
                mMosaic_Base.setTextColor(getResources().getColor(TXT_SELECTED_COLOR));
                mMosaic_Glass.setTextColor(TXT_NORMAL_COLOR);
                mMosaic_Flower.setTextColor(TXT_NORMAL_COLOR);
                mMosaic_Eraser.setTextColor(TXT_NORMAL_COLOR);
                break;
            case INDEX_MOSAIC_GLASS:
                mMosaic_Glass.setTextColor(getResources().getColor(TXT_SELECTED_COLOR));
                mMosaic_Base.setTextColor(TXT_NORMAL_COLOR);
                mMosaic_Flower.setTextColor(TXT_NORMAL_COLOR);
                mMosaic_Eraser.setTextColor(TXT_NORMAL_COLOR);
                break;
            case INDEX_MOSAIC_FLOWER:
                mMosaic_Flower.setTextColor(getResources().getColor(TXT_SELECTED_COLOR));
                mMosaic_Glass.setTextColor(TXT_NORMAL_COLOR);
                mMosaic_Base.setTextColor(TXT_NORMAL_COLOR);
                mMosaic_Eraser.setTextColor(TXT_NORMAL_COLOR);
                break;
            case INDEX_MOSAIC_ERASER:
                mMosaic_Eraser.setTextColor(getResources().getColor(TXT_SELECTED_COLOR));
                mMosaic_Glass.setTextColor(TXT_NORMAL_COLOR);
                mMosaic_Flower.setTextColor(TXT_NORMAL_COLOR);
                mMosaic_Base.setTextColor(TXT_NORMAL_COLOR);
                break;
            default:
                break;
        }
    }

    /**
     * 调整马赛克画笔的大小
     *
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mPaintSize = seekBar.getProgress();
        mDrawMosaicView.setMosaicBrushWidth(mPaintSize);
    }

    private void recycle() {
        if (mSourceBitmap != null) {
            mSourceBitmap.recycle();
            mSourceBitmap = null;
        }
    }
}
