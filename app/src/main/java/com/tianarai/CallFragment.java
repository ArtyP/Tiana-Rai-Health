package com.tianarai;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallFragment extends Fragment implements View.OnTouchListener {

    private int[] hints = {
            R.string.hint_000,
            R.string.hint_001,
            R.string.hint_002,
            R.string.hint_003,
            R.string.hint_004,
            R.string.hint_005,
            R.string.hint_006,
            R.string.hint_007,
            R.string.hint_008,
            R.string.hint_009,
            R.string.hint_010,
            R.string.hint_011,
            R.string.hint_012
    };

    int currentHint;

    private GestureDetector gestureDetector;
    //OnSwipeTouchListener onSwipeTouchListener;
    TextView tvHint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        gestureDetector = new GestureDetector(getContext(), new GestureListener());
        currentHint = 0;

        return inflater.inflate(R.layout.fragment_call, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_calls, menu);
        tvHint = getView().findViewById(R.id.tvHint);
        tvHint.setText(hints[currentHint]);
        /*tvHint.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));*/

        /*Animation aniSlide = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        tvHint.startAnimation(aniSlide);*/

        getView().setOnTouchListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_call) {
            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT)
                    .show();
        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private void hintUp() {
        if (currentHint < hints.length-1)
            currentHint++;
        else currentHint = 0;
    }

    private void hintDown() {
        if (currentHint > 0)
            currentHint--;
        else currentHint = hints.length-1;
    }

    void onSwipeRight() {
        /*Toast.makeText(context, "Swiped Right", Toast.LENGTH_SHORT).show();
        this.onSwipe.swipeRight();*/
    }
    void onSwipeLeft() {
        /*Toast.makeText(context, "Swiped Left", Toast.LENGTH_SHORT).show();
        this.onSwipe.swipeLeft();*/
    }
    void onSwipeTop() {
        Animation aniSlide = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up_out);
        tvHint.startAnimation(aniSlide);

        tvHint.postDelayed(new Runnable() {
            @Override
            public void run() {
                hintUp();
                tvHint.setText(hints[currentHint]);

                Animation aniSlide = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up_in);
                tvHint.startAnimation(aniSlide);
            }
        }, 300);

    }

    void onSwipeBottom() {
        Animation aniSlide = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down_out);
        tvHint.startAnimation(aniSlide);

        tvHint.postDelayed(new Runnable() {
            @Override
            public void run() {
                hintDown();
                tvHint.setText(hints[currentHint]);

                Animation aniSlide = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down_in);
                tvHint.startAnimation(aniSlide);

            }
        }, 300);
    }

    public class GestureListener extends
            GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }
}

