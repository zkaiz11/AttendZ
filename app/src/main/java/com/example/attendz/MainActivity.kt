package com.example.attendz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

import android.content.Intent
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import com.example.attendz.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso;
import com.example.attendz.Home
class MainActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        val auth = Firebase.auth
        val user = auth.currentUser

        if (user != null) {
            showFragment(Home())
//            Picasso.get().load(user.photoUrl).into(imgView);
        } else {
            // Handle the case where the user is not signed in
        }

        binding.bottomNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mnuHome -> showFragment(Home())


                R.id.mnuCreateEvent ->
                    showFragment(CreateEvent())

                R.id.mnuJoinEvent -> showFragment(JoinEvent())


                R.id.mnuSetting -> showFragment(Setting())

                else -> {

                }
            }
            true
        }



        // Inside onCreate() method
//        val sign_out_button = findViewById<Button>(R.id.logout_button)
//        sign_out_button.setOnClickListener {
//            signOutAndStartSignInActivity()
//        }
    }

    private val auth = Firebase.auth
    private val user = auth.currentUser
    private fun showFragment(fragment: Fragment) {
        val bundle = Bundle()
        bundle.putString("name", user?.displayName)
        bundle.putString("imgUrl", user?.photoUrl.toString())
        fragmentManager = supportFragmentManager
        fragmentManager.setFragmentResult("user", bundle)
        fragmentManager.beginTransaction().replace(R.id.lytFragment, fragment).commit()
    }
    private fun signOutAndStartSignInActivity() {
        mAuth.signOut()

        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
            // Optional: Update UI or show a message to the user
            val intent = Intent(this@MainActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}