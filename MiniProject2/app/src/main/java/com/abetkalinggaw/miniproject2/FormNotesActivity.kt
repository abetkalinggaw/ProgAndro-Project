package com.abetkalinggaw.miniproject2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class FormNotesActivity : AppCompatActivity() {
    var note : Notes? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_note)

        val data = intent.getSerializableExtra("notes")
        var edit = true

        val database = Firebase.database
        val myRef = database.getReference("notes")

        val etNoteNameEdit = findViewById<EditText>(R.id.etNoteNameEdit)
        val etNoteDescriptionEdit = findViewById<EditText>(R.id.etNoteDescriptionEdit)
        val btActForm = findViewById<Button>(R.id.btActForm)


        if (data != null) {
            note = data as Notes
            etNoteNameEdit.setText(note?.title)
            etNoteDescriptionEdit.setText(note?.description)
            btActForm.text = "Edit"
        } else {
            btActForm.text = "Tambah"
            edit = false
        }

        btActForm.setOnClickListener {
            if (edit) {
                val changeData = HashMap<String, Any>()
                changeData["title"] = etNoteNameEdit.text.toString()
                changeData["description"] = etNoteDescriptionEdit.text.toString()

                myRef.child(note?.key.toString()).updateChildren(changeData)
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                val key = myRef.push().key

                val newNote = Notes()
                newNote.title = etNoteNameEdit.text.toString()
                newNote.description = etNoteDescriptionEdit.text.toString()

                myRef.child(key.toString()).setValue(newNote)
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}