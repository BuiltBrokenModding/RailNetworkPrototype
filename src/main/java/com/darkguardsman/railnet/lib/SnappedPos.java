package com.darkguardsman.railnet.lib;

import java.awt.Point;

import com.darkguardsman.railnet.api.RailHeading;
import com.darkguardsman.railnet.api.math.IPos;
import com.darkguardsman.railnet.api.math.IPosM;
public class SnappedPos extends AbstractPos<SnappedPos> {
	private float ox;
	private float oy;
	private float oz;
	
	public enum SNAP_VECTORS{
		ALL(new Point(1, 0), new Point(0, 1), new Point(-1, 0),new Point(0, -1), new Point(1, -1), new Point(1, 1), new Point(-1, 1), new Point(-1, -1)),
		NS(new Point(1, 0), new Point(-1, 0)),
		EW(new Point(0, 1), new Point(0, -1)),
		NE_SW_NW_SE(new Point(1, 1), new Point(-1, -1),new Point(1, -1), new Point(-1, 1));
		private final Point[] vectors;
		SNAP_VECTORS(Point... points){
			vectors = points;
		}
		public int length() {
			return vectors.length;
		}
		public Point get(int i) {
			return vectors[i];
		}		
		public static SNAP_VECTORS getFromAngle(int angle) {
			switch (angle) {
			case 45:
			case 135:
			case 225:
			case 315:
				return NE_SW_NW_SE;
			case 90:
			case 270:
				return EW;
			}
			return NS;
		}
	}

	public SnappedPos(float x, float y, float z,SNAP_VECTORS vectors) {
		this.ox = x;
		this.oy = y;
		this.oz = z;
		Pos snap = getClosestSnapPoint(origin(),vectors);
		this.x = snap.x;
		this.y = snap.y;
		this.z = snap.z;
	}
	
	public RailHeading[] possibleHeadings() {
		return RailHeading.getPossibleHeadings((int)x,(int)z);
	}
	
	public SnappedPos(float x, float y, float z) {
		this(x,y,z,SNAP_VECTORS.ALL);
	}
	public SnappedPos(IPosM pos,SNAP_VECTORS vectors) {
		this(pos.x(),pos.y(),pos.z(),vectors);
	}
	public SnappedPos(IPosM pos) {
		this(pos,SNAP_VECTORS.ALL);
	}
	public Pos origin() {
		return new Pos(ox,oy,oz);
	}

	@Override
	public SnappedPos newCopyAtPosition(float x, float y, float z) {
		return new SnappedPos(x,y,z);
	}
	/**
	 * Gets the position of the dimension relative to the snapping grid
	 * 
	 * @param i
	 * @return
	 */
	public static int gridPoint(int i) {
		return (Math.abs(i) + 1) % 2;
	}

	/**
	 * Snaps a given position to the nearest valid position
	 * 
	 * @param pos
	 * @return
	 */
	private static Pos getClosestSnapPoint(IPosM pos, SNAP_VECTORS vectors) {
		int x = Math.round(pos.x());
		int y = Math.round(pos.y());
		int z = Math.round(pos.z());
		int xoffset = gridPoint(x);
		int zoffset = gridPoint(z);
		
			Point closestSnapPoint = null;
			double shortestDistance = 2d;
					for (int i = 0; i < vectors.length(); i++) {
						Point snapPoint = new Point(x + (xoffset * (x>0?1:-1)) + vectors.get(i).x, z  + (zoffset * (z>0?1:-1)) + vectors.get(i).y);
						double testDistance = snapPoint.distance(pos.x(), pos.z());
						if (testDistance < shortestDistance) {
							shortestDistance = testDistance;
							closestSnapPoint = snapPoint;
						}
					}
				
			x = closestSnapPoint.x;
			z = closestSnapPoint.y;
		return new Pos(x, y, z);
	}

	
}
