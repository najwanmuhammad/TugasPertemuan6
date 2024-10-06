package com.example.tugaspertemuan6

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tugaspertemuan6.databinding.ActivityMainBinding
import com.example.tugaspertemuan6.databinding.TaskDetailBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var taskDatalist = ArrayList<Array<String>>()

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        result ->
        if (result.resultCode == Activity.RESULT_OK) {
            var data = result.data
            val taskData = data?.getStringArrayExtra("EXTRA_TASK")
            if (taskData != null) {
                if (binding.imgIlustrasi.visibility == View.VISIBLE) {
                    binding.imgIlustrasi.visibility = View.GONE
                }
                taskDatalist.add(taskData)
                addTaskDetail(taskData.get(0), taskData.get(1), taskData.get(2), taskData.get(3))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            addTaskButton.setOnClickListener {
                val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
                launcher.launch(intent)
            }

            if (taskDatalist.size > 0) {

            }

        }
    }

    fun addTaskDetail(title: String, repeat: String, date: String, time: String) {
        val taskBinding = TaskDetailBinding.inflate(layoutInflater)

        taskBinding.taskTitle.text = title
        taskBinding.taskRepeat.text = repeat
        taskBinding.taskDate.text = date
        taskBinding.taskTime.text = time

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            topMargin = 32
        }

        taskBinding.root.layoutParams = layoutParams

        binding.taskContainer.addView(taskBinding.root)

    }

}