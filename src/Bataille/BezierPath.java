package Bataille;


import org.newdawn.slick.geom.Vector2f;



/**

 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)

 * 

 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>

 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+

 */

public class BezierPath implements Path {



	private Vector2f[] pts;



	public BezierPath(Vector2f... pts) {

		this.pts = pts;

	}



	public BezierPath(float... coords) {

		if (coords.length%2!=0) {

			throw new IllegalStateException("coords must be pair");

		}

		pts = new Vector2f[coords.length / 2];

		for (int i = 0; i < pts.length; i++) {

			pts[i] = new Vector2f(coords[i * 2], coords[i * 2 + 1]);

		}

	}



	@Override

	public Vector2f pointAt(float t) {

		return pointAt(pts, t);

	}



	private Vector2f pointAt(Vector2f[] pts, float t) {

		Vector2f value;

		if (pts.length == 2) {

			value = moy(pts[0], pts[1], t);

		} else {

			Vector2f[] nexts = new Vector2f[pts.length - 1];

			for (int i = 0; i < nexts.length; i++) {

				nexts[i] = moy(pts[i], pts[i + 1], t);

			}

			value = pointAt(nexts, t);

		}

		return value;

	}



	private Vector2f moy(Vector2f v1, Vector2f v2, float t) {

		return v1.copy().scale(1 - t).add(v2.copy().scale(t));

	}



}