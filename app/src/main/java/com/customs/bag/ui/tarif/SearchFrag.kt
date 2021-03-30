package com.customs.bag.ui.tarif

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.customs.bag.R
import com.customs.bag.data.model.Tarifa
import com.customs.bag.ui.MainActivity
import com.customs.bag.ui.adapters.TarifaAdapter
import com.customs.bag.util.call_back.OnItemClick
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader


class SearchFrag : Fragment() {


    private var adapter: TarifaAdapter? = null

    private var loadDialog: Dialog? = null
    var noValue: TextView? = null;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_blank, container, false)
        // Inflate the layout for this fragment
        val label: TextView = root.findViewById(R.id.label)
        noValue = root.findViewById(R.id.noValue)
        loadDialog = Dialog(context!!)
        loadDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        loadDialog?.setContentView(R.layout.dialog_search)
        val width = (context!!.resources.displayMetrics.widthPixels)
        val height = (context!!.resources.displayMetrics.heightPixels)

        loadDialog?.window!!.setLayout(width, (height * .30).toInt())

        val result = root.findViewById<RecyclerView>(R.id.result)
        result.layoutManager = LinearLayoutManager(context)
        adapter = TarifaAdapter()
        result.adapter = adapter
        val query: EditText = root.findViewById(R.id.getQuery)
        adapter!!.setOnItemClick(onItemClick = object : OnItemClick {
            override fun onClick(obj: Any) {
                showDialog(obj as Tarifa)
            }
        })
        noValue?.visibility = View.GONE

        val labelText =
            "اهلا بكم فى النسخه المحموله من التعريفه يمكنك البحث الان عن بنود التعريفه "
        showDataAsWriting(label, labelText,true)


        val model = (activity as MainActivity).searchViewModel

        model.responseLiveData.observe(viewLifecycleOwner, Observer {
            if (it != "Error")
                decompileJsonToView(it)

            loadDialog?.dismiss()

        })
        root.findViewById<View>(R.id.searchNow).setOnClickListener {
            if (query.text.length < 2) {
                query.error = "برجاء ادخال قيمه صحيحه"
                return@setOnClickListener
            }
            loadDialog?.show()
            noValue?.visibility = View.GONE
            model.searchAboutSomething(query.text.toString())
        }





        return root
    }

    private val tarifaList: MutableList<Tarifa> = ArrayList()

    private fun showDataAsWriting(view: TextView, value: String,repeating:Boolean) {
        object : Thread() {
            override fun run() {
                super.run()
                try {

                    do {
                        activity!!.runOnUiThread {
                            view.text = ""
                        }
                        for (s in value) {
                            sleep(100)
                            activity!!.runOnUiThread {
                                // UI code goes here
                                view.append(s.toString())
                            }
                        }
                        sleep(1000)

                    }
                    while (repeating)

                } catch (e: Exception) {
                    Log.e("Test", "" + e.message)
                }
            }
        }.start()
    }

    //    // حصان
//    private fun sendTo(query: String) {
//        loadDialog?.show()
//        val sr =
//            StringRequest(
//                DataManger.Url+"NewSerch.php?serch=$query",
//                { response: String ->
//                    Log.e(
//                        "HttpClient",
//                        "success! response: $response"
//                    )
//                    try {
//                        val mainArray = JSONArray(response)
//                        for (i in 0 until mainArray.length()) {
//                            val jsonObject = mainArray.getJSONObject(i)
//                            val idtarif: String = jsonObject.getString("idtarif")
//                            val tarifnum: String = jsonObject.getString("tarifnum")
//                            val tarifdes: String = jsonObject.getString("tarifdes")
//                            val tarifsec: String = jsonObject.getString("tarifsec")
//                            val tax1: String = jsonObject.getString("tax1")
//                            val tax2: String = jsonObject.getString("tax2")
//                            val tarifType: String = jsonObject.getString("tarif_type")
//                            val tarif_qty_ehsae: String = jsonObject.getString("tarif_qty_ehsae")
//                            val tarif_qty_custom: String = jsonObject.getString("tarif_qty_custom")
//                            val tarif_view: String = jsonObject.getString("tarif_view")
//                            val tarif_explain: String = jsonObject.getString("tarif_explain")
//                            val tarif_uro1: String = jsonObject.getString("tarif_uro1")
//                            val tarif_number_sharh: String =
//                                jsonObject.getString("tarif_number_sharh")
//                            val tarifa = Tarifa()
//                            tarifa.idtarif = idtarif
//                            tarifa.tarifnum = tarifnum
//                            tarifa.tarifdes = tarifdes
//                            tarifa.tarifsec = tarifsec
//                            tarifa.tax1 = tax1
//                            tarifa.tax2 = tax2
//                            tarifa.tarif_type = tarifType
//                            tarifa.tarif_qty_ehsae = tarif_qty_ehsae
//                            tarifa.tarif_qty_custom = tarif_qty_custom
//                            tarifa.tarif_view = tarif_view
//                            tarifa.tarif_explain = tarif_explain
//                            tarifa.tarif_uro1 = tarif_uro1
//                            tarifa.tarif_number_sharh = tarif_number_sharh
//                            tarifaList.add(tarifa)
//
//                        }
//                        adapter?.setTarifa(tarifaList)
//                        loadDialog?.dismiss()
//
//                    } catch (e: Exception) {
//                        loadDialog?.dismiss()
//                    }
//
//
//                },
//                { error: VolleyError ->
//                    run {
//                        Log.e(
//                            "HttpClient",
//                            "error: $error"
//                        )
//                        loadDialog?.dismiss()
//
//                    }
//                })
//        Volley.newRequestQueue(context).add(sr)
//    }
//
    private fun decompileJsonToView(response: String) {
        Log.e(
            "HttpClient",
            "success! response: $response"
        )
        try {
            tarifaList.clear()
            val mainArray = JSONArray(response)
            for (i in 0 until mainArray.length()) {
                val jsonObject = mainArray.getJSONObject(i)
                val idTarif: String = jsonObject.getString("idtarif")
                val tarifnum: String = jsonObject.getString("tarifnum")
                val tarifdes: String = jsonObject.getString("tarifdes")
                val tarifsec: String = jsonObject.getString("tarifsec")
                val tax1: String = jsonObject.getString("tax1")
                val tax2: String = jsonObject.getString("tax2")
                val tarifType: String = jsonObject.getString("tarif_type")
                val tarif_qty_ehsae: String = jsonObject.getString("tarif_qty_ehsae")
                val tarif_qty_custom: String = jsonObject.getString("tarif_qty_custom")
                val tarif_view: String = jsonObject.getString("tarif_view")
                val tarif_explain: String = jsonObject.getString("tarif_explain")
                val tarif_uro1: String = jsonObject.getString("tarif_uro1")
                val tarif_number_sharh: String =
                    jsonObject.getString("tarif_number_sharh")
                val tarifa = Tarifa()
                tarifa.idtarif = idTarif
                tarifa.tarifnum = tarifnum
                tarifa.tarifdes = tarifdes
                tarifa.tarifsec = tarifsec
                tarifa.tax1 = tax1
                tarifa.tax2 = tax2
                tarifa.tarif_type = tarifType
                tarifa.tarif_qty_ehsae = tarif_qty_ehsae
                tarifa.tarif_qty_custom = tarif_qty_custom
                tarifa.tarif_view = tarif_view
                tarifa.tarif_explain = tarif_explain
                tarifa.tarif_uro1 = tarif_uro1
                tarifa.tarif_number_sharh = tarif_number_sharh
                tarifaList.add(tarifa)

            }
            adapter?.setTarifa(tarifaList)
            loadDialog?.dismiss()

        } catch (e: Exception) {
            loadDialog?.dismiss()
        }
        if (tarifaList.isEmpty()) {
            noValue?.visibility = View.VISIBLE
            showDataAsWriting(noValue!!, "لا يوجد نتائج تطابق بحثك فى قاعده البيانات لدينا ",false)

        }
    }


    fun showDialog(tarifa: Tarifa) {

        val dialog = Dialog(context!!)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.dialog_one_tarifa)

        val tarifnum: TextView = dialog.findViewById(R.id.tarifaNumber)
        val tarifdes: TextView = dialog.findViewById(R.id.tarifaDis)
        val tarifsec: TextView = dialog.findViewById(R.id.tarifsec)
        val tax1: TextView = dialog.findViewById(R.id.tax1)
        val tax2: TextView = dialog.findViewById(R.id.tax2)
        val tarif_type: TextView = dialog.findViewById(R.id.tarif_type)
        val tarif_qty_ehsae: TextView = dialog.findViewById(R.id.tarif_qty_ehsae)
        val tarif_qty_custom: TextView = dialog.findViewById(R.id.tarif_qty_custom)
        val tarif_uro1: TextView = dialog.findViewById(R.id.tarif_uro1)
        val tarif_number_sharh: TextView = dialog.findViewById(R.id.tarif_number_sharh)
        tarifnum.text = tarifa.tarifnum
        tarifdes.text = tarifa.tarifdes
        tarifsec.text = tarifa.tarifsec
        tax1.text = tarifa.tax1
        tax2.text = tarifa.tax2
        tarif_type.text = tarifa.tarif_type
        tarif_qty_ehsae.text = tarifa.tarif_qty_ehsae.toString()
        tarif_qty_custom.text = tarifa.tarif_qty_custom.toString()
        tarif_uro1.text = tarifa.tarif_uro1
        tarif_number_sharh.text = tarifa.tarif_number_sharh


        val width = (context!!.resources.displayMetrics.widthPixels)
        val height = (context!!.resources.displayMetrics.heightPixels)

        openFile("s" + tarifa.tarif_number_sharh)
        tarif_number_sharh.text = sharh

        dialog.window!!.setLayout(width, height)

        dialog.findViewById<View>(R.id.Ok).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }

    private var sharh = ""

    private fun openFile(fileName: String) {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(
                InputStreamReader(context?.assets?.open("$fileName.txt"), "UTF-8")
            )
            var mLine = ""
            while (reader.readLine() != null) {
                mLine += reader.readLine()
            }
            sharh = mLine

        } catch (e: Exception) {
            Log.e("File Ex1", e.message.toString())
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: Exception) {
                    Log.e("File Ex2", e.message.toString())

                }
            }
        }
    }


}