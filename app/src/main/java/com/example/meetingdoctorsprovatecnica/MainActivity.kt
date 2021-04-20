package com.example.meetingdoctorsprovatecnica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.util.*

private const val FIRST_FILE_NAME = "com\\example\\meetingdoctorsprovatecnica\\files\\alice29.txt"
private const val SECOND_FILE_NAME = "com\\example\\meetingdoctorsprovatecnica\\files\\Nombres.txt"
private const val THIRD_FILE_NAME = "com\\example\\meetingdoctorsprovatecnica\\files\\plrabn12.txt"

class MainActivity : AppCompatActivity() {

    private lateinit var loadButton: AppCompatButton
    private lateinit var list: RecyclerView
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
        list = findViewById(R.id.list)
    }


    private fun setListeners() {
        loadButton.setOnClickListener {
            if (!running) loadList()
        }
    }

    private fun loadList() {
        running = true
        GlobalScope.launch {
            adapter = Adapter(getFilesContent().toList())
            list.adapter = adapter
        }
        adapter = Adapter(listOf("Loading..."))
        list.adapter = adapter
        running = false
    }


    private fun getFilesContent(): LinkedList<String?> {
        var bufferedReader: BufferedReader = File(FIRST_FILE_NAME).bufferedReader()
        val list = LinkedList<String?>()

        GlobalScope.launch {
            bufferedReader.useLines {
                firstFileContent = it.toString()
                list.add(firstFileContent)
                }
            }

        GlobalScope.launch {
            bufferedReader = File(SECOND_FILE_NAME).bufferedReader()
            bufferedReader.useLines {
                    secondFileContent = it.toString()
                    list.add(secondFileContent)
            }
        }

        GlobalScope.launch {
            bufferedReader = File(THIRD_FILE_NAME).bufferedReader()
            bufferedReader.useLines {
                thirdFileContent = it.toString()
                list.add(thirdFileContent)
            }
        }

        return list
    }

}