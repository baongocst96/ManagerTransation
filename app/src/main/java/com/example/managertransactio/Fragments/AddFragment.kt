package com.example.managertransactio.Fragments


import android.app.Dialog
import android.app.LauncherActivity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.managertransactio.MainActivity

import com.example.managertransactio.R
import com.example.managertransactio.adapter.SpinnerCustomAdapter
import com.example.managertransactio.data.Constants
import com.example.managertransactio.model.SpinnerTransaction
import com.example.managertransactio.util.Spref
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_total.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class AddFragment : Fragment() {

    val TAG = "add_fragment"
    val types = arrayOf("Invest", "Imcome")
    lateinit var typeSpinner : String
    var flagSwitch:String ="USD"
    lateinit var investUSD :String
    lateinit var imcomeUSD :String
    lateinit var exchange :String

    val database =  FirebaseDatabase.getInstance().reference



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        exchange = Spref().getSharedPreferenceString(requireContext(),Constants().EXCHANGE,"").toString()
        investUSD = Spref().getSharedPreferenceString(requireContext(),Constants().INVEST,"").toString()
        imcomeUSD = Spref().getSharedPreferenceString(requireContext(),Constants().IMCOME,"").toString()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        setSpinner(view)
        val switch = view.findViewById<Switch>(R.id.switch1)
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                switch1.text = "VND"
                flagSwitch = "VND"


            } else {
                // The switch is disabled
                switch1.text = "USD"
                flagSwitch = "USD"}
        }
        val button = view.findViewById<Button>(R.id.button_addtransaction)
        button.setOnClickListener(View.OnClickListener {
            addTransaction(view)
        })


        return view
    }

    fun setSpinner(t :View) {


        val spinner: Spinner = t.findViewById<Spinner>(R.id.spinner2)
//        spinner?.adapter = ArrayAdapter(activity!!.applicationContext, R.layout.support_simple_spinner_dropdown_item, types) as SpinnerCustomAdapter
////        spinner?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
////            override fun onNothingSelected(parent: AdapterView<*>?) {
////                println("erreur")
////            }
////
////            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
////                typeSpinner = parent?.getItemAtPosition(position).toString()
////            }
////        }
        var listType: ArrayList<SpinnerTransaction> = ArrayList()
        listType.add(SpinnerTransaction("Invest", R.drawable.savemoney))
        listType.add(SpinnerTransaction("Imcome", R.drawable.imcome))
        spinner?.adapter = SpinnerCustomAdapter(activity!!.applicationContext,listType)
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("erreur")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var item = parent?.getItemAtPosition(position) as SpinnerTransaction
                typeSpinner = item.type
            }

        }

    }

    fun addTransaction(view : View){
        var amount:String = ""
        var valueNow:String
        if (ed_amount.text!!.isEmpty()) {
            tv_error_amount.visibility = view.visibility
            tv_error_amount.text = "Amount not empty"
        }else{
            if(flagSwitch == "USD"){
                amount = ed_amount.text.toString()

            }else{
                amount = (ed_amount.text.toString().toDouble() / exchange.toDouble()).toString()
            }
            try {
                when(typeSpinner){
                    "Invest" -> {
                        valueNow = (investUSD.toDouble()+amount.toDouble()).toString()
                        database.child("total").child("life").child("invest").setValue(valueNow)
                    }
                    "Imcome" -> {
                        valueNow = (imcomeUSD.toDouble()+amount.toDouble()).toString()
                        database.child("total").child("life").child("imcome").setValue(valueNow)

                    }

                }
                showDialog()
            }catch (e :Exception){
                e.printStackTrace()
            }

        }


    }

    fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.layout_info_dialog)

        val txtResult = dialog.findViewById<TextView>(R.id.txt_result)

        txtResult.text =
            "Yêu cầu của bạn đã được tiếp nhận!\nChúng tôi sẽ gửi thông báo khi có kết quả"
        dialog.show()
        Handler().postDelayed(
            {
                dialog.dismiss()
                fragmentManager?.popBackStack()
            }, 2000
        )
    }


}
