package com.example.final_project


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction

/**
 * A simple [Fragment] subclass.
 */
class fragment_menu : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_fragment_menu, container, false)
        val button1 : Button = view.findViewById(R.id.btnPrice)
        val button2 : Button = view.findViewById(R.id.btnGrap)
        button1.setOnClickListener {
            val camera = Recycler_view()
            val fm = fragmentManager
            val transaction: FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.layout, camera, "listview")
            transaction.addToBackStack("listview")
            transaction.commit()
        }
//        button2.setOnClickListener {
//            val s = DataRealtime()
//            val fm = fragmentManager
//            val transaction: FragmentTransaction = fm!!.beginTransaction()
//            transaction.replace(R.id.layout, s, "listview")
//            transaction.addToBackStack("listview")
//            transaction.commit()
//        }

        return view
    }

}
