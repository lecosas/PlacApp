package br.com.fiap.placapp.ui.game

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import br.com.fiap.placapp.R
import br.com.fiap.placapp.ui.game.event.EventFragment
import br.com.fiap.placapp.ui.score.ScoreActivity
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    private var eventName = ""
    private var homeTeam = ""
    private var awayTeam = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        showEventFragment()

        registerBroadcastReceiver()

        ivBack.setOnClickListener{
            onBackPressed()
        }
    }

    private fun registerBroadcastReceiver() {
        val intentFilter = IntentFilter("FILTER_EVENT")
        intentFilter.addAction("FILTER_HOME_TEAM")
        intentFilter.addAction("FILTER_AWAY_TEAM")
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter)
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.hasExtra("event_name")) {
                eventName = intent.getStringExtra("event_name")
            }

            if (intent.hasExtra("home_team")) {
                homeTeam = intent.getStringExtra("home_team")
            }

            if (intent.hasExtra("away_team")) {
                awayTeam = intent.getStringExtra("away_team")
                val nextScreen = Intent(this@GameActivity, ScoreActivity::class.java)
                nextScreen.putExtra("eventName", eventName)
                nextScreen.putExtra("homeTeam", homeTeam)
                nextScreen.putExtra("awayTeam", awayTeam)
                startActivity(nextScreen)
                finish()
            }
        }
    }


    private fun showEventFragment() {
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.containerGame, EventFragment())
        ft.commit()
    }

}