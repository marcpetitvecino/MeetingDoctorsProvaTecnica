package com.example.meetingdoctorsprovatecnica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream
import java.util.*

private const val FIRST_FILE_NAME = "alice29.txt"
private const val SECOND_FILE_NAME = "Nombres.txt"
private const val THIRD_FILE_NAME = "plrabn12.txt"

class MainActivity : AppCompatActivity() {

    private lateinit var loadButton: AppCompatButton
    private lateinit var list: RecyclerView
    private lateinit var adapter: Adapter

    private var firstFileContent: String? = null
    private var secondFileContent: String? = null
    private var thirdFileContent: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        GlobalScope.launch {
            adapter = Adapter(getFilesContent().toList())
        }
    }

    private fun initViews() {
        loadButton = findViewById(R.id.loadBtn)
        list = findViewById(R.id.list)
    }

    private fun getFilesContent(): LinkedList<String?> {
        firstFileContent = MainActivity::class.java.getResource(FIRST_FILE_NAME)?.readText()
        secondFileContent = MainActivity::class.java.getResource(SECOND_FILE_NAME)?.readText()
        thirdFileContent = MainActivity::class.java.getResource(THIRD_FILE_NAME)?.readText()
        val list = LinkedList<String?>()
        list.add(firstFileContent)
        list.add(secondFileContent)
        list.add(thirdFileContent)
        return list
    }

}