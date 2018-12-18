package com.akj.quizlocker

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_file_ex.*
import java.io.FileNotFoundException

class FileExActivity : AppCompatActivity() {
    // 데이터 저장에 사용할 파일이름
    val filename = "data.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_ex)
        // 저장 버튼이 클릭된 경우
        saveButton.setOnClickListener {
            // textField 의 현재 텍스트를 가져온다.
            val text = textField.text.toString()
            when {
                // 텍스트가 비어있는 경우 오류 메세지를 보여준다.
                TextUtils.isEmpty(text) -> {
                    Toast.makeText(applicationContext, "텍스트가 비어있습니다.", Toast.LENGTH_LONG).show()
                }
                else -> {
                    // 내부 저장소 파일에 저장하는 함수 호출
                    saveToInnerStorage(text, filename)
                }
            }
        }
        // 불러오기 버튼이 클릭된 경우
        loadButton.setOnClickListener {
            try {
                // textField 의 텍스트를 불러온 텍스트로 설정한다.
                textField.setText(loadFromInnerStorage(filename))
            } catch (e: FileNotFoundException) {
                // 파일이 없는 경우 에러메세지 보여줌
                Toast.makeText(applicationContext, "저장된 텍스트가 없습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

    // 내부저장소 파일의 텍스트를 저장한다.
    fun saveToInnerStorage(text: String, filename: String) {
        // 내부 저장소의 전달된 파일이름의 파일 출력 스트림을 가져온다.
        val fileOutputStream = openFileOutput(filename, Context.MODE_PRIVATE)
        // 파일 출력 스트림에 text 를 바이트로 변환하여 write 한다
        fileOutputStream.write(text.toByteArray())
        // 파일 출력 스트림을 닫는다
        fileOutputStream.close()
    }

    // 내부저장소 파일의 텍스트를 불러온다
    fun loadFromInnerStorage(filename: String): String {
        // 내부저장소의 전달된 파일이름의 파일 입력 스트림을 가져온다
        val fileInputStream = openFileInput(filename)
        // 파일의 저장된 내용을 읽어 String 형태로 불러온다.
        return fileInputStream.reader().readText()
    }
}