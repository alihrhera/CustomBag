package com.customs.bag.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.customs.bag.R


class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root=inflater.inflate(R.layout.fragment_splash, container, false)
        val icon=root.findViewById<View>(R.id.logo)
        val an = TranslateAnimation(0, 0f, 0, 0f, 0, -1000f, 0, 0f)
        an.duration=2000
        icon.visibility=View.GONE
        icon.startAnimation(an)
        an.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                icon.visibility=View.VISIBLE

            }
            override fun onAnimationEnd(animation: Animation) {

            }
            override fun onAnimationRepeat(animation: Animation) {}
        })

        val text=root.findViewById<TextView>(R.id.name)

        object : Thread() {
            override fun run() {
                super.run()
                try {
                    sleep(20)

                    val appName="اهلا بكم فى الحقيبه الجمركيه"
                    for (s in appName) {
                        sleep(120)
                        activity!!.runOnUiThread {
                            text.append(s.toString())
                        }
                    }
                    sleep(1000)

                    activity!!.runOnUiThread{
                        (activity as MainActivity).attachFragment(HomeFragment())
                        val  trans = (activity as MainActivity).frManager.beginTransaction();
                        trans.remove(this@SplashFragment);
                        trans.commit();
                    }
                } catch (e: Exception) {
                    Log.e("Test", "" + e.message)

                }
            }
        }.start()

        return root
    }

}