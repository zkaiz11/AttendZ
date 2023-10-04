package com.example.attendz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.attendz.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
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

    }
    private fun showFragment(fragment: Fragment) {
        fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.lytFragment, fragment).commit()
    }
}