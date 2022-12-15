package com.abetkalinggaw.miniproject2

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = Firebase.database

        val  myRef = database.getReference("notes")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dataArray = ArrayList<Notes>()
                for (i in dataSnapshot.children){
                    val data = i.getValue(Notes::class.java)
                    data?.key = i.key
                    data?.let { dataArray.add(it) }
                }


                findViewById<RecyclerView>(R.id.rvListNotes).adapter = NotesAdapter(dataArray, object : NotesAdapter.OnClick {
                    override fun edit(note: Notes?) {
                        val intent = Intent(this@MainActivity, FormNotesActivity::class.java)
                        intent.putExtra("note", note)
                        startActivity(intent)
                    }

                    override fun delete(key: String?) {
                        AlertDialog.Builder(this@MainActivity).apply {
                            setTitle("Hapus ?")
                            setPositiveButton("Ya") { dialogInterface: DialogInterface, i: Int ->
                                myRef.child(key.toString()).removeValue()
                                Toast.makeText(this@MainActivity, key, Toast.LENGTH_SHORT).show()
                            }
                            setNegativeButton("Tidak") { dialogInterface: DialogInterface, i: Int -> }
                        }.show()
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("tag", "Failed to read value.", error.toException())
            }
        })

        findViewById<Button>(R.id.btAddNote).setOnClickListener {
            startActivity(Intent(this@MainActivity, FormNotesActivity::class.java))
        }
    }
}