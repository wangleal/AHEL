package wang.leal.ahel.image.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;

public class RoundedCornersTransformation extends BitmapTransformation {

  private static final int VERSION = 1;
  private static final String ID = "rc.platform.image." + VERSION;

  public enum CornerType {
    ALL,
    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
    TOP, BOTTOM, LEFT, RIGHT,
    OTHER_TOP_LEFT, OTHER_TOP_RIGHT, OTHER_BOTTOM_LEFT, OTHER_BOTTOM_RIGHT,
    DIAGONAL_FROM_TOP_LEFT, DIAGONAL_FROM_TOP_RIGHT
  }

  private final int radius;
  private final int diameter;
  private final CornerType cornerType;

  public RoundedCornersTransformation(int radius) {
    this(radius,CornerType.ALL);
  }

  public RoundedCornersTransformation(int radius, CornerType cornerType) {
    this.radius = radius;
    this.diameter = this.radius * 2;
    this.cornerType = cornerType;
  }

  @Override
  protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
                             @NonNull Bitmap toTransform, int outWidth, int outHeight) {
    int width = toTransform.getWidth();
    int height = toTransform.getHeight();

    Bitmap bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
    bitmap.setHasAlpha(true);

    setCanvasBitmapDensity(toTransform, bitmap);

    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setShader(new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
    drawRoundRect(canvas, paint, width, height);
    return bitmap;
  }

  private void drawRoundRect(Canvas canvas, Paint paint, float width, float height) {

    switch (cornerType) {
      case TOP_LEFT:
        drawTopLeftRoundRect(canvas, paint, width, height);
        break;
      case TOP_RIGHT:
        drawTopRightRoundRect(canvas, paint, width, height);
        break;
      case BOTTOM_LEFT:
        drawBottomLeftRoundRect(canvas, paint, width, height);
        break;
      case BOTTOM_RIGHT:
        drawBottomRightRoundRect(canvas, paint, width, height);
        break;
      case TOP:
        drawTopRoundRect(canvas, paint, width, height);
        break;
      case BOTTOM:
        drawBottomRoundRect(canvas, paint, width, height);
        break;
      case LEFT:
        drawLeftRoundRect(canvas, paint, width, height);
        break;
      case RIGHT:
        drawRightRoundRect(canvas, paint, width, height);
        break;
      case OTHER_TOP_LEFT:
        drawOtherTopLeftRoundRect(canvas, paint, width, height);
        break;
      case OTHER_TOP_RIGHT:
        drawOtherTopRightRoundRect(canvas, paint, width, height);
        break;
      case OTHER_BOTTOM_LEFT:
        drawOtherBottomLeftRoundRect(canvas, paint, width, height);
        break;
      case OTHER_BOTTOM_RIGHT:
        drawOtherBottomRightRoundRect(canvas, paint, width, height);
        break;
      case DIAGONAL_FROM_TOP_LEFT:
        drawDiagonalFromTopLeftRoundRect(canvas, paint, width, height);
        break;
      case DIAGONAL_FROM_TOP_RIGHT:
        drawDiagonalFromTopRightRoundRect(canvas, paint, width, height);
        break;
      default:
        canvas.drawRoundRect(new RectF(0, 0, width, height), radius, radius, paint);
        break;
    }
  }

  private void drawTopLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(0, 0, diameter, diameter), radius,
      radius, paint);
    canvas.drawRect(new RectF(0, radius, radius, bottom), paint);
    canvas.drawRect(new RectF(radius, 0, right, bottom), paint);
  }

  private void drawTopRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(right - diameter, 0, right, diameter), radius,
      radius, paint);
    canvas.drawRect(new RectF(0, 0, right - radius, bottom), paint);
    canvas.drawRect(new RectF(right - radius, radius, right, bottom), paint);
  }

  private void drawBottomLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(0, bottom - diameter, diameter, bottom), radius,
      radius, paint);
    canvas.drawRect(new RectF(0, 0, diameter, bottom - radius), paint);
    canvas.drawRect(new RectF(radius, 0, right, bottom), paint);
  }

  private void drawBottomRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(right - diameter, bottom - diameter, right, bottom), radius,
      radius, paint);
    canvas.drawRect(new RectF(0, 0, right - radius, bottom), paint);
    canvas.drawRect(new RectF(right - radius, 0, right, bottom - radius), paint);
  }

  private void drawTopRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(0, 0, right, diameter), radius, radius,
      paint);
    canvas.drawRect(new RectF(0, radius, right, bottom), paint);
  }

  private void drawBottomRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(0, bottom - diameter, right, bottom), radius, radius,
      paint);
    canvas.drawRect(new RectF(0, 0, right, bottom - radius), paint);
  }

  private void drawLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(0, 0, diameter, bottom), radius, radius,
      paint);
    canvas.drawRect(new RectF(radius, 0, right, bottom), paint);
  }

  private void drawRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(right - diameter, 0, right, bottom), radius, radius, paint);
    canvas.drawRect(new RectF(0, 0, right - radius, bottom), paint);
  }

  private void drawOtherTopLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(0, bottom - diameter, right, bottom), radius, radius,
      paint);
    canvas.drawRoundRect(new RectF(right - diameter, 0, right, bottom), radius, radius, paint);
    canvas.drawRect(new RectF(0, 0, right - radius, bottom - radius), paint);
  }

  private void drawOtherTopRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(0, 0, diameter, bottom), radius, radius,
      paint);
    canvas.drawRoundRect(new RectF(0, bottom - diameter, right, bottom), radius, radius,
      paint);
    canvas.drawRect(new RectF(radius, 0, right, bottom - radius), paint);
  }

  private void drawOtherBottomLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(0, 0, right, diameter), radius, radius,
      paint);
    canvas.drawRoundRect(new RectF(right - diameter, 0, right, bottom), radius, radius, paint);
    canvas.drawRect(new RectF(0, radius, right - radius, bottom), paint);
  }

  private void drawOtherBottomRightRoundRect(Canvas canvas, Paint paint, float right,
                                             float bottom) {
    canvas.drawRoundRect(new RectF(0, 0, right, diameter), radius, radius,
      paint);
    canvas.drawRoundRect(new RectF(0, 0, diameter, bottom), radius, radius,
      paint);
    canvas.drawRect(new RectF(radius, radius, right, bottom), paint);
  }

  private void drawDiagonalFromTopLeftRoundRect(Canvas canvas, Paint paint, float right,
                                                float bottom) {
    canvas.drawRoundRect(new RectF(0, 0, diameter, diameter), radius,
      radius, paint);
    canvas.drawRoundRect(new RectF(right - diameter, bottom - diameter, right, bottom), radius,
      radius, paint);
    canvas.drawRect(new RectF(0, radius, right - radius, bottom), paint);
    canvas.drawRect(new RectF(radius, 0, right, bottom - radius), paint);
  }

  private void drawDiagonalFromTopRightRoundRect(Canvas canvas, Paint paint, float right,
                                                 float bottom) {
    canvas.drawRoundRect(new RectF(right - diameter, 0, right, diameter), radius,
      radius, paint);
    canvas.drawRoundRect(new RectF(0, bottom - diameter, diameter, bottom), radius,
      radius, paint);
    canvas.drawRect(new RectF(0, 0, right - radius, bottom - radius), paint);
    canvas.drawRect(new RectF(radius, radius, right, bottom), paint);
  }

  @NotNull
  @Override
  public String toString() {
    return "RoundedTransformation(radius=" + radius + ", diameter="
      + diameter + ", cornerType=" + cornerType.name() + ")";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof RoundedCornersTransformation &&
      ((RoundedCornersTransformation) o).radius == radius &&
      ((RoundedCornersTransformation) o).diameter == diameter &&
      ((RoundedCornersTransformation) o).cornerType == cornerType;
  }

  @Override
  public int hashCode() {
    return ID.hashCode() + radius * 10000 + diameter * 1000 + 100 + cornerType.ordinal() * 10;
  }

  @Override
  public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    messageDigest.update((ID + radius + diameter + cornerType).getBytes(CHARSET));
  }
}
