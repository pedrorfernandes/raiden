package com.raiden.game;

// fast math atan2 is from
// http://www.java-gaming.org/topics/13-8x-faster-atan2-updated/14647/view.html
public class FastMath {
	
	// cos and sin from
	// http://www.java-gaming.org/index.php?topic=24191.0
	
	public static final float sin(float rad)
	{
		return sin[(int) (rad * radToIndex) & SIN_MASK];
	}

	public static final float cos(float rad)
	{
		return cos[(int) (rad * radToIndex) & SIN_MASK];
	}

	public static final float sinDeg(float deg)
	{
		return sin[(int) (deg * degToIndex) & SIN_MASK];
	}

	public static final float cosDeg(float deg)
	{
		return cos[(int) (deg * degToIndex) & SIN_MASK];
	}

	//private static final float   RAD = (float) Math.PI / 180.0f;
	private static final float   DEG = 180.0f / (float) Math.PI;
	private static final int     SIN_BITS,SIN_MASK,SIN_COUNT;
	private static final float   radFull,radToIndex;
	private static final float   degFull,degToIndex;
	private static final float[] sin, cos;

	static
	{
		SIN_BITS  = 12;
		SIN_MASK  = ~(-1 << SIN_BITS);
		SIN_COUNT = SIN_MASK + 1;

		radFull    = (float) (Math.PI * 2.0);
		degFull    = (float) (360.0);
		radToIndex = SIN_COUNT / radFull;
		degToIndex = SIN_COUNT / degFull;

		sin = new float[SIN_COUNT];
		cos = new float[SIN_COUNT];

		for (int i = 0; i < SIN_COUNT; i++)
		{
			sin[i] = (float) Math.sin((i + 0.5f) / SIN_COUNT * radFull);
			cos[i] = (float) Math.cos((i + 0.5f) / SIN_COUNT * radFull);
		}

		// Four cardinal directions (credits: Nate)
		for (int i = 0; i < 360; i += 90)
		{
			sin[(int)(i * degToIndex) & SIN_MASK] = (float)Math.sin(i * Math.PI / 180.0);
			cos[(int)(i * degToIndex) & SIN_MASK] = (float)Math.cos(i * Math.PI / 180.0);
		}
	}


	private static final int ATAN2_BITS = 7;

	private static final int ATAN2_BITS2 = ATAN2_BITS << 1;
	private static final int ATAN2_MASK = ~(-1 << ATAN2_BITS2);
	private static final int ATAN2_COUNT = ATAN2_MASK + 1;
	private static final int ATAN2_DIM = (int) Math.sqrt(ATAN2_COUNT);

	private static final float INV_ATAN2_DIM_MINUS_1 = 1.0f / (ATAN2_DIM - 1);

	private static final float[] atan2 = new float[ATAN2_COUNT];



	static
	{
		for (int i = 0; i < ATAN2_DIM; i++)
		{
			for (int j = 0; j < ATAN2_DIM; j++)
			{
				float x0 = (float) i / ATAN2_DIM;
				float y0 = (float) j / ATAN2_DIM;

				atan2[j * ATAN2_DIM + i] = (float) Math.atan2(y0, x0);
			}
		}
	}


	/**
	 * ATAN2
	 */

	 public static final float atan2Deg(float y, float x)
	{
		return atan2(y, x) * DEG;
	}

	 public static final float atan2DegStrict(float y, float x)
	 {
		 return (float) Math.atan2(y, x) * DEG;
	 }

	 public static final float atan2(float y, float x)
	 {
		 float add, mul;

		 if (x < 0.0f)
		 {
			 if (y < 0.0f)
			 {
				 x = -x;
				 y = -y;

				 mul = 1.0f;
			 }
			 else
			 {
				 x = -x;
				 mul = -1.0f;
			 }

			 add = -3.141592653f;
		 }
		 else
		 {
			 if (y < 0.0f)
			 {
				 y = -y;
				 mul = -1.0f;
			 }
			 else
			 {
				 mul = 1.0f;
			 }

			 add = 0.0f;
		 }

		 float invDiv = 1.0f / (((x < y) ? y : x) * INV_ATAN2_DIM_MINUS_1);

		 int xi = (int) (x * invDiv);
		 int yi = (int) (y * invDiv);

		 return (atan2[yi * ATAN2_DIM + xi] + add) * mul;
	 }

}
