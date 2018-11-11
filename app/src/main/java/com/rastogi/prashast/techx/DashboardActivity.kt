package com.rastogi.prashast.techx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rastogi.prashast.techx.ui.main.MainFragment

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

}
