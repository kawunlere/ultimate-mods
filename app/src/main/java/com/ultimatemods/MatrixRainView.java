package com.ultimatemods;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.Random;

public class MatrixRainView extends View {

    private Paint paint;
    private Random random;
    private int width, height;
    private int fontSize = 16;
    private int columnCount;
    private int[] drops;
    private String matrixChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#$%^&*";

    public MatrixRainView(Context context) {
        super(context);
        init();
    }

    public MatrixRainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setTextSize(fontSize);
        paint.setAntiAlias(true);
        random = new Random();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        columnCount = width / fontSize;
        drops = new int[columnCount];
        for (int i = 0; i < columnCount; i++) {
            drops[i] = random.nextInt(height / fontSize);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Fade effect for trail
        paint.setColor(Color.argb(40, 0, 0, 0));
        canvas.drawRect(0, 0, width, height, paint);

        paint.setTextSize(fontSize);

        for (int i = 0; i < drops.length; i++) {
            // First char is brighter (white-green)
            paint.setColor(Color.argb(220, 180, 255, 180));
            char firstChar = matrixChars.charAt(random.nextInt(matrixChars.length()));
            canvas.drawText(String.valueOf(firstChar), i * fontSize,
                drops[i] * fontSize, paint);

            // Rest of the column is green
            paint.setColor(Color.argb(150, 0, 255, 70));
            for (int j = 1; j < 8; j++) {
                if (drops[i] - j > 0) {
                    char c = matrixChars.charAt(random.nextInt(matrixChars.length()));
                    canvas.drawText(String.valueOf(c), i * fontSize,
                        (drops[i] - j) * fontSize, paint);
                }
            }

            // Reset column randomly
            if (drops[i] * fontSize > height && random.nextFloat() > 0.975) {
                drops[i] = 0;
            }

            drops[i]++;
        }

        // Redraw every frame for animation
        postInvalidateOnAnimation();
    }
}
