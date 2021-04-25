package com.example.meetingdoctorsprovatecnica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val FIRST_FILE_NAME = "alice29.txt"
private const val SECOND_FILE_NAME = "Nombres.txt"
private const val THIRD_FILE_NAME = "plrabn12.txt"

class MainActivity : AppCompatActivity() {

    private lateinit var loadButton: AppCompatButton
    private lateinit var wordList: RecyclerView
    private lateinit var adapter: Adapter
    private var running = false

    private lateinit var totalWordCount: AppCompatTextView
    private lateinit var firstWordCount: AppCompatTextView
    private lateinit var secondWordCount: AppCompatTextView
    private lateinit var thirdWordCount: AppCompatTextView

    private lateinit var firstFileContent: List<String?>
    private lateinit var secondFileContent: List<String?>
    private lateinit var thirdFileContent: List<String?>

    val waitingTime = 200L
    private lateinit var countDownTimer: CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchView = menu?.findItem(R.id.searchView)?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchText ->
                //    countDownTimer = object : CountDownTimer(waitingTime, 500) {

                //       override fun onTick(millisUntilFinished: Long) {
                //           Log.d("Tick", millisUntilFinished.toString())
                //       }

                //       override fun onFinish() {
                //           if (it.length > 1 && !firstFileContent.isNullOrEmpty()) searchWords(newText)
                //           countDownTimer.cancel()
                //       }
                //   }
                    if (searchText.length > 1 && !firstFileContent.isNullOrEmpty()) {
                        loadAdapter(emptyList())
                        searchWords(newText)
                    }
                }
                //countDownTimer.start()
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun searchWords(text: String) {
        loadList(text)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.orderByPosition -> orderByPosition()
            R.id.orderAlphabetically -> orderAlphabetically()
            R.id.orderByApparitions -> orderByApparitions()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun orderByApparitions() {
        Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show()
    }

    private fun orderAlphabetically() {
        loadList(orderType = "alphabetically")
    }

    private fun orderByPosition() {
        loadList(orderType = "position")
    }

    private fun initViews() {
        loadButton = findViewById(R.id.loadBtn)
        wordList = findViewById(R.id.list)
        totalWordCount = findViewById(R.id.totalWordCount)
        firstWordCount = findViewById(R.id.firstListWordCount)
        secondWordCount = findViewById(R.id.secondListWordCount)
        thirdWordCount = findViewById(R.id.thirdListWordCount)
    }


    private fun setListeners() {
        loadButton.setOnClickListener {
            if (!running) loadList()
        }
    }

    private fun loadList(text: String = "", orderType: String = "position") {
        running = true
        loadAdapter(listOf("Loading..."))
        val megaList = getFilesContent()

        var adapterList = when (orderType) {
                "position" -> megaList[0].union(megaList[1].union(megaList[2])).toList()
                "alphabetically" -> megaList[0].union(megaList[1].union(megaList[2])).toList().sortedBy { if (!firstFileContent.isNullOrEmpty() && it != "") it?.get(0).toString() else "" }
                //"apparitions" -> getApparitions(megaList[0].union(megaList[1].union(megaList[2])).toList()) // Not implemented
                else -> megaList[0].union(megaList[1].union(megaList[2])).toList()
            }

        if ("" != text) {
            val adapterListMutable = adapterList.toMutableList()
            adapterList.forEachIndexed { index, s ->
                s?.let {
                    if (!it.toUpperCase().contains(text.toUpperCase())) {
                        try {
                            adapterListMutable.removeAt(index)
                        } catch (exception: IndexOutOfBoundsException) {
                            Log.e("ErrorAdapter", "OUT OF BOUNDS")
                        }
                    }
                }
            }
            adapterList = adapterListMutable.toList()
        }
        // En el search el enfoque logico es correcto pero no se que pasa con la lista

        loadAdapter(adapterList)
        running = false

        totalWordCount.text = "There are a total of ${adapterList.size} words"
        firstWordCount.text = "The file $FIRST_FILE_NAME has a total of ${firstFileContent.size} words"
        secondWordCount.text = "The file $SECOND_FILE_NAME has a total of ${secondFileContent.size} words"
        thirdWordCount.text = "The file $THIRD_FILE_NAME has a total of ${thirdFileContent.size} words"

    }

    private fun getFilesContent(): List<List<String?>> {

        application.assets.open(FIRST_FILE_NAME).bufferedReader().use {
            var text = it.readText()
            text = removeUnwantedChars(text)
            firstFileContent = text.split(" ").distinct()
        }

        application.assets.open(SECOND_FILE_NAME).bufferedReader().use {
            var text = it.readText()
            text = removeUnwantedChars(text)
            secondFileContent = text.split(" ").distinctBy { ti -> ti.toLowerCase(Locale.getDefault()) }
        }

        application.assets.open(THIRD_FILE_NAME).bufferedReader().use {
            var text = it.readText()
            text = removeUnwantedChars(text)
            thirdFileContent = text.split(" ").distinct()
        }

        return listOf(firstFileContent, secondFileContent, thirdFileContent)
    }

    private fun loadAdapter(list: List<String?>) {
        adapter = Adapter(list)
        wordList.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun removeUnwantedChars(text: String): String {
        var newText = text.replace("\n", " ")
        newText = newText.replace(",", "")
        newText = newText.replace("$", "")
        newText = newText.replace("-", "")
        newText = newText.replace(".", "")
        newText = newText.replace("(", "")
        newText = newText.replace(")", "")
        newText = newText.replace(":", "")
        newText = newText.replace(";", "")
        newText = newText.replace("?", "")
        newText = newText.replace("-", "")
        newText = newText.replace("*", "")
        newText = newText.replace("'", "")
        newText = newText.replace("\"", "")
        newText = newText.replace("1", "")
        newText = newText.replace("2", "")
        newText = newText.replace("3", "")
        newText = newText.replace("4", "")
        newText = newText.replace("5", "")
        newText = newText.replace("6", "")
        newText = newText.replace("7", "")
        newText = newText.replace("8", "")
        newText = newText.replace("9", "")
        newText = newText.replace("0", "")
        return newText
    }

}