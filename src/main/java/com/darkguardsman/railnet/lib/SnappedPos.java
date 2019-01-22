package com.darkguardsman.railnet.lib;

import java.awt.Point;

import com.darkguardsman.railnet.api.RailHeading;
import com.darkguardsman.railnet.api.math.IPos;
import com.darkguardsman.railnet.api.math.IPosM;

public class SnappedPos extends AbstractPos<SnappedPos> {
	private float ox;
	private float oy;
	private float oz;

	public SnappedPos(IPosM pos) {
		this(pos.x(), pos.y(), pos.z());
	}

	public SnappedPos(float x, float y, float z) {
		this.ox = x;
		this.oy = y;
		this.oz = z;
		Pos snap = getClosestSnapPoint(origin());
		this.x = snap.x;
		this.y = snap.y;
		this.z = snap.z;
	}

	/**
	 * Sets the origin as the snapped location
	 * 
	 * @return
	 */
	public SnappedPos clearOrigin() {
		this.ox = x;
		this.oy = y;
		this.oz = z;
		return this;
	}

	public RailHeading[] possibleHeadings() {
		return RailHeading.getPossibleHeadings((int) x, (int) z);
	}

	public Pos origin() {
		return new Pos(ox, oy, oz);
	}

	@Override
	public SnappedPos newCopyAtPosition(float x, float y, float z) {
		return new SnappedPos(x, y, z);
	}

	/**
	 * Gets the position of the dimension relative to the snapping grid
	 * 
	 * @param i
	 * @return
	 */
	public static int gridPoint(int i) {
		return (Math.abs(i)) % 2;
	}

	/**
	 * Snaps a given position to the nearest valid position
	 * 
	 * @param pos
	 * @return
	 */
	private static Pos getClosestSnapPoint(IPosM pos) {
		// Snap to the closest centre first
		int x = Math.round(pos.x());
		int y = Math.round(pos.y());
		int z = Math.round(pos.z());
		return new Pos(x, y, z);
	}

}
