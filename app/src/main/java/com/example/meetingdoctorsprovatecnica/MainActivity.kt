package com.example.meetingdoctorsprovatecnica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setListeners()
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

    private  fun loadList() {
        running = true
        adapter = Adapter(listOf("Loading..."))
        wordList.adapter = adapter

        val megaList = getFilesContent()

        val adapterList = megaList[0].union(megaList[1].union(megaList[2])).toList()

        adapter = Adapter(adapterList)
        wordList.adapter = adapter
        adapter.notifyDataSetChanged()
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

    private fun removeUnwantedChars(text: String): String {
        var newText = text.replace("\n", " ")
        newText = newText.replace(",", "")
        newText = newText.replace(".", "")
        newText = newText.replace("(", "")
        newText = newText.replace(")", "")
        newText = newText.replace(":", "")
        newText = newText.replace(";", "")
        newText = newText.replace("?", "")
        newText = newText.replace("-", "")
        newText = newText.replace("1", "")
        newText = newText.replace("2", "")
        newText = newText.replace("3", "")
        newText = newText.replace("4", "")
        newText = newText.replace("5", "")
        newText = newText.replace("6", "")
        newText = newText.replace("7", "")
        newText = newText.replace("8", "")
        newText = newText.replace("9", "")
        newText = newText.replace("10", "")
        return newText
    }

}