package Bataille;


import java.util.ArrayList;

import java.util.List;



import org.newdawn.slick.geom.Vector2f;



/**

 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)

 * 

 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>

 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+

 */

public class PathAnimation {



	public enum Mode {

		ONCE, LOOP

	}



	private static class AnimationListenerPoint {



		private int time;

		private AnimationListener listener;



		public AnimationListenerPoint(int time, AnimationListener listener) {

			this.time = time;

			this.listener = listener;

		}



		public int getTime() {

			return time;

		}



		public AnimationListener getListener() {

			return listener;

		}



	}



	private Path path;

	private int duration;

	private int time;

	private boolean running = false;

	private Mode mode = Mode.ONCE;

	private List<AnimationListenerPoint> listeners = new ArrayList<AnimationListenerPoint>();



	public PathAnimation(Path path, int duration) {

		this.path = path;

		this.duration = duration;

	}



	public Vector2f currentLocation() {

		return path.pointAt((float) time / duration);

	}



	public void update(int delta) {

		if (running) {

			updateListeners(delta);

			time += delta;

			switch (mode) {

			case ONCE:

				if (time > duration) {

					time = duration;

					running = false;

				}

				break;

			case LOOP:

				if (time > duration) {

					time %= duration;

				}

				break;

			default:

				throw new RuntimeException("Not yet Implemented");

			}

		}

	}



	private void updateListeners(int delta) {

		for (AnimationListenerPoint pt : listeners) {

			if (time <= pt.getTime() && pt.getTime() < time + delta) {

				pt.getListener().on();

			}

		}

	}



	public void start() {

		time = 0;

		running = true;

	}



	public void start(Mode mode) {

		this.mode = mode;

		start();

	}



	public void addListener(int time, AnimationListener listener) {

		this.listeners.add(new AnimationListenerPoint(time, listener));

	}



}
