package com.customs.bag.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.customs.bag.R
import com.customs.bag.util.DataManger
import com.customs.bag.ui.chat.ChatFragment
import com.customs.bag.ui.postes.AllPosts
import com.customs.bag.ui.tarif.SearchFrag

class HomeFragment : Fragment() {

    var mAct: MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.frag_home, container, false)
        mAct = (activity as MainActivity)
        root.findViewById<View>(R.id.chat).setOnClickListener {
            showDialog(mAct!!)
            // (activity as MainActivity).attachFragment(ChatFragment())
        }
        root.findViewById<View>(R.id.tarifaa).setOnClickListener {
            (activity as MainActivity).attachFragment(SearchFrag())
        }
        root.findViewById<View>(R.id.post).setOnClickListener {
            (activity as MainActivity).attachFragment(AllPosts())
        }


        return root
    }

    private fun showDialog( ctx:Context) {
        val isReg = DataManger.getInstance().isUser
        if (!isReg && mAct != null) {
                object : Dialog(ctx) {
                    override fun onCreate(savedInstanceState: Bundle?) {
                        super.onCreate(savedInstanceState)
                        if (window == null) {
                            return
                        }
                        //val width = (ctx.resources.displayMetrics.widthPixels * 0.90).toInt()

                        setContentView(R.layout.dialog_getname)
                        val getPhone = findViewById<EditText>(R.id.getPhone)
                        val getName = findViewById<EditText>(R.id.getName)
                        findViewById<View>(R.id.startCaht).setOnClickListener {
                            if (getPhone.text.length <= 10) {
                                getPhone.error = "مطلوب"
                            }
                            if (getName.text.length <= 1) {
                                getName.error = "مطلوب"
                            }
                            DataManger.getInstance().setNameAndPhone(getName.text.toString(),getPhone.text.toString());

                            mAct!!.attachFragment(ChatFragment())
                            dismiss()
                    }}
                }.show()

        return
    }
    (activity as MainActivity).attachFragment(ChatFragment())

}

}