package com.example.meetingdoctorsprovatecnica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

private const val FIRST_FILE_NAME = "alice29.txt"
private const val SECOND_FILE_NAME = "Nombres.txt"
private const val THIRD_FILE_NAME = "plrabn12.txt"

class MainActivity : AppCompatActivity() {

    private lateinit var loadButton: AppCompatButton
    private lateinit var wordList: RecyclerView
    private lateinit var adapter: Adapter
    private var running = false

    private var firstFileContent: String? = null
    private var secondFileContent: String? = null
    private var thirdFileContent: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setListeners()
    }

    private fun initViews() {
        loadButton = findViewById(R.id.loadBtn)
        wordList = findViewById(R.id.list)
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

        adapter = Adapter(getFilesContent())
        wordList.adapter = adapter
    }

    private fun getFilesContent(): LinkedList<String?> {
        val list = LinkedList<String?>()

        application.assets.open(FIRST_FILE_NAME).bufferedReader().use {
            firstFileContent = it.readText()
        }

        application.assets.open(SECOND_FILE_NAME).bufferedReader().use {
            secondFileContent = it.readText()
        }

        application.assets.open(THIRD_FILE_NAME).bufferedReader().use {
            thirdFileContent = it.readText()
        }

        list.add(firstFileContent)
        list.add(secondFileContent)
        list.add(thirdFileContent)
        return list
    }

}