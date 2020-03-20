package com.example.final_project


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass.
 */
class fragment_detail : Fragment() {


    val mRootRef = FirebaseDatabase.getInstance().getReference()
    val mMessagesRef = mRootRef.child("order")
    var image:String ?= null
    var title:String ?= null
    var camera:String? = ""

    fun newInstance(image:String,title:String,description: String): fragment_detail {
        val fragment_detail = fragment_detail()
        val bundle = Bundle()
        bundle.putString("image",image);
        bundle.putString("title",title);
        bundle.putString("camera",title);
        bundle.putString("description",description);
        fragment_detail.setArguments(bundle)
        return fragment_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if(bundle != null){
            this.image = bundle.getString("image").toString()
            this.title = bundle.getString("title").toString()
            this.camera = bundle.getString("title").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_fragment_detail, container, false)
        val image_view : ImageView = view.findViewById(R.id.image_view)
        val title_view : TextView = view.findViewById(R.id.title_view)
        val button: Button = view.findViewById(R.id.success)
        button.setOnClickListener{
            val builder: AlertDialog.Builder =  AlertDialog.Builder(this.context)
            builder.setMessage("คุณลูกค้าต้องการ "+ camera + " หรือป่าวครับ ?")

            builder.setNegativeButton("ยืนยัน",
                DialogInterface.OnClickListener{ dialog, id ->

                    val key = mMessagesRef.push().key
                    val postValues: HashMap<String, Any> = HashMap()
                    postValues["order"] = camera.toString()
                    val childUpdates: MutableMap<String, Any> = HashMap()
                    childUpdates["/messages/$key"] = postValues
                    mMessagesRef.updateChildren(childUpdates)

                    Toast.makeText(this.context,"ขอบคุณสำหรับการอุดหนุนสินค้าครับ", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                })

            builder.setPositiveButton("ยกเลิก",
                DialogInterface.OnClickListener{ dialog, id ->

                })
            builder.show();

        }
        title_view.text = this.title
        Glide.with(activity!!.baseContext).load(image).into(image_view)
        return view
    }




}
