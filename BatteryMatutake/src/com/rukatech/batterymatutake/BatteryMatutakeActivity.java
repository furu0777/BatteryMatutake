package com.rukatech.batterymatutake;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;

public class BatteryMatutakeActivity extends Activity {
	private Bitmap bitmap[];
	private Matrix matrix;
	private Matrix bgMatrix;
	private Bitmap tmp;
	private Bitmap bg;
	private int level;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new BitmapView(this));
	}
	
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	IntentFilter filter = new IntentFilter();
    	
    	filter.addAction(Intent.ACTION_BATTERY_CHANGED);
    	registerReceiver(mBroadcastReceiver, filter);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	
    	unregisterReceiver(mBroadcastReceiver);
    }
    
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
    	public void onReceive(Context context, Intent intent) {
    		String action = intent.getAction();
    		if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
    			level = intent.getIntExtra("level", 0);
    		}
		}
    };
 
    class BitmapView extends View {
    	public BitmapView(Context context) {
    		super(context);
    	}			
    	
    	@SuppressLint("DrawAllocation")
    	public void onDraw(Canvas canvas) {
    		Paint paint = new Paint();
    		String text = new String();
    		String percentage = new String();
    		
    		bitmap = new Bitmap[2];
    		
    		// ���\�[�X����Bitmap���擾
    		bitmap[0] = BitmapFactory.decodeResource(getResources(),R.drawable.matutake);
    		bitmap[1] = BitmapFactory.decodeResource(getResources(),R.drawable.matsu);
    		
    		// Bitmap�̃T�C�Y�̎擾
    		int width = bitmap[0].getWidth();
    		int height = bitmap[0].getHeight();
    		int bg_width = bitmap[1].getWidth();
    		int bg_height = bitmap[1].getHeight();
    		
    		// ��]������Bitmap�̍쐬
    		matrix = new Matrix();
    		bgMatrix = new Matrix();
    		matrix.postRotate(140 - (float)level, width/2, height); //�p�x�w��@140��=0%, 40��=100%
    		
    		tmp = Bitmap.createBitmap(bitmap[0], 0, 0, width, height, matrix, true);
    		bg = Bitmap.createBitmap(bitmap[1], 0, 0, bg_width, bg_height, bgMatrix, true);
    					
    		// Bitmap�`��
    		int canvasWidth = canvas.getWidth();
    		int canvasHeight = canvas.getHeight();
    		canvas.drawBitmap(bg, 0, 0 , paint);
    		canvas.drawBitmap(tmp, (canvasWidth / 10) * 2, (canvasHeight/10) * 3  , paint);
    					
    		// �e�L�X�g�`��
    		paint.setAntiAlias(true);
    		if(level == 100){	// �[�d��100%
    			text = "���CMAX�I";
    			paint.setColor(Color.rgb(0, 255, 0));
    			paint.setTextSize(36);
    			canvas.drawText(text, (canvasWidth / 6) * 3, (canvasHeight /10) * 1, paint);
    			percentage = level + "%";
    			paint.setColor(Color.rgb(0, 255, 0));
    			paint.setTextSize(72);
    			canvas.drawText(percentage, (canvasWidth / 6) * 3, (canvasHeight /10) * 2, paint);
    		} else if(level < 100 && level > 70) {	// �[�d��99%�`71%
    			text = "���C����";
    			paint.setColor(Color.rgb(0, 255, 0));
    			paint.setTextSize(36);
    			canvas.drawText(text, (canvasWidth / 6) * 3, (canvasHeight /10) * 1, paint);
    			percentage = level + "%";
    			paint.setColor(Color.rgb(0, 255, 0));
    			paint.setTextSize(72);
    			canvas.drawText(percentage, (canvasWidth / 6) * 3, (canvasHeight /10) * 2, paint);
    		} else if(level <= 70 && level > 30) {	// �[�d��70%�`31%
    			text = "�܂��܂�����";
    			paint.setColor(Color.rgb(225, 215, 0));
    			paint.setTextSize(30);
    			canvas.drawText(text, (canvasWidth / 6) * 3, (canvasHeight /10) * 1, paint);
    			percentage = level + "%";
    			paint.setColor(Color.rgb(225, 215, 0));
    			paint.setTextSize(72);
    			canvas.drawText(percentage, (canvasWidth / 6) * 3, (canvasHeight /10) * 2, paint);
    		} else if(level <= 30 && level > 10) {	// �[�d��30%�`11%
    			text = "���C�Ȃ��Ȃ��Ă���";
    			paint.setColor(Color.rgb(225, 0, 0));
    			paint.setTextSize(24);
    			canvas.drawText(text, (canvasWidth / 6) * 3, (canvasHeight /10) * 1, paint);
    			percentage = level + "%";
    			paint.setColor(Color.rgb(225, 0, 0));
    			paint.setTextSize(72);
    			canvas.drawText(percentage, (canvasWidth / 6) * 3, (canvasHeight /10) * 2, paint);
    		} else {	// �[�d��10%�ȉ�
    			text = "�����_���E�E�E";
    			paint.setColor(Color.rgb(225, 0, 0));
    			paint.setTextSize(36);
    			canvas.drawText(text, (canvasWidth / 6) * 3, (canvasHeight /10) * 1, paint);
    			percentage = level + "%";
    			paint.setColor(Color.rgb(225, 0, 0));
    			paint.setTextSize(72);
    			canvas.drawText(percentage, (canvasWidth / 6) * 3, (canvasHeight /10) * 2, paint);
    		}
    	}
	}
}
