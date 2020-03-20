package com.example.final_project


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.facebook.*
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * A simple [Fragment] subclass.
 */
class fragment_login : Fragment() {

    private var username: String? = null
    private var pass: String? = null

    var user : FirebaseUser? = null
    lateinit var facebookSignInButton : LoginButton
    var callbackManager : CallbackManager? = null
    // Firebase Auth Object.
    var firebaseAuth: FirebaseAuth? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_fragment_login, container, false)

        callbackManager = CallbackManager.Factory.create()
        facebookSignInButton  = view.findViewById(R.id.login_button) as LoginButton
        firebaseAuth = FirebaseAuth.getInstance()
        facebookSignInButton.setReadPermissions("email")

        // If using in a fragment
        facebookSignInButton.setFragment(this)

        val token: AccessToken?
        token = AccessToken.getCurrentAccessToken()

        if (token != null) { //Means user is not logged in
            LoginManager.getInstance().logOut()
        }

        // Callback registration
        facebookSignInButton.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) { // App code

                handleFacebookAccessToken(loginResult!!.accessToken)

            }
            override fun onCancel() { // App code
            }
            override fun onError(exception: FacebookException) { // App code
            }
        })

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) { // App code

                    handleFacebookAccessToken(loginResult!!.accessToken)

                }

                override fun onCancel() { // App code
                }

                override fun onError(exception: FacebookException) { // App code
                }
            })



        val button : Button = view.findViewById(R.id.button_login)
        button.setOnClickListener {
            var username: EditText = view.findViewById(R.id.username)
            var password: EditText = view.findViewById(R.id.password)

            this.username = username.text.toString()
            this.pass = password.text.toString()


            if(this.username.toString().isEmpty()){
                Toast.makeText(context,"Please Enter your Username ", Toast.LENGTH_LONG).show()
            }else if(this.pass.toString().isEmpty()){
                Toast.makeText(context,"Please Enter your Password ", Toast.LENGTH_LONG).show()
            }else if(this.username.toString() == "60160353" || this.pass.toString() == "60160353"){
                Toast.makeText(context,"Login Success ", Toast.LENGTH_LONG).show()

                val listview = Recycler_view()
                val menu = fragment_menu()
                val fm = fragmentManager
                val transaction: FragmentTransaction = fm!!.beginTransaction()
                transaction.replace(R.id.layout, menu, "listview")
                transaction.addToBackStack("listview")
                transaction.commit()
            }else{
                Toast.makeText(context,"Please Check Your Username or Password ", Toast.LENGTH_LONG).show()
            }


        }

        return view
    }

    fun Check_space():Boolean{

        if(this.username.toString().isEmpty() || this.pass.toString().isEmpty()){
            Toast.makeText(context,"Login Fail ", Toast.LENGTH_LONG).show()
            return false
        }else{
            Toast.makeText(context,"Login Success ", Toast.LENGTH_LONG).show()
            return true
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FacebookSdk.sdkInitialize(getApplicationContext())
        AppEventsLogger.activateApp(activity!!.baseContext)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token : AccessToken) {


        Log.d(TAG, "handleFacebookAccessToken:" + token)
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth!!.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")

                user = firebaseAuth!!.currentUser

                val profile = Recycler_view()
                val fm = fragmentManager
                val transaction : FragmentTransaction = fm!!.beginTransaction()
                transaction.replace(R.id.layout, profile,"fragment_profile")
                transaction.addToBackStack("fragment_profile")
                transaction.commit()

            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithCredential:failure", task.getException())
                Toast.makeText(activity!!.baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }




}
