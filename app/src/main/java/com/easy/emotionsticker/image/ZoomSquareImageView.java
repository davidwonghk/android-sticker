package com.easy.emotionsticker.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by david.wong on 30/07/2016.
 */
public class ZoomSquareImageView extends SquareImageView {

	//private final static int sizeOfMagnifier = 70;
	private boolean zooming = false;
	private Matrix matrix;

	public ZoomSquareImageView(Context context) {
		super(context);

		this.matrix = new Matrix();

		super.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						zooming = true;
						ZoomSquareImageView.this.invalidate();
						break;
					case MotionEvent.ACTION_UP:
						zooming = false;
						ZoomSquareImageView.this.invalidate();
						break;
				}
				return false;
			}
		});
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (!zooming) {
			buildDrawingCache();
			return;
		}

		Bitmap bitmap = getDrawingCache();
		BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

		Paint paint = new Paint();
		paint.setShader(shader);
		matrix.reset();
		matrix.postTranslate(0, -10);
		paint.getShader().setLocalMatrix(matrix);
		canvas.drawPaint(paint);
	}

}
