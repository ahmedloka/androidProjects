package demo.clone.uber.com.filebaseexampleproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditTxtTitle, mEditTxtDesc, mEditTxtPriority;
    private Button mBtnAddNotes, mBtnLoadNotes;
    private TextView mTxtViewData;

    public static final String TAG = "MainActivity" ;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference mCollectionRef = db.collection("NoteBook");
    private DocumentSnapshot mDocSnap ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTxtDesc = (EditText) findViewById(R.id.editTxt2);
        mEditTxtTitle = (EditText) findViewById(R.id.editTxt1);
        mEditTxtPriority = (EditText) findViewById(R.id.editTxt3);

        mTxtViewData = (TextView) findViewById(R.id.txtViewData);


        mBtnLoadNotes = (Button) findViewById(R.id.btnLoadNotes);
        mBtnAddNotes = (Button) findViewById(R.id.btnAdd);
        mBtnAddNotes.setOnClickListener(this);
        mBtnLoadNotes.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
/*

        mCollectionRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {

                if (e != null) {
                    return;
                }

                String data = "";

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                    Note note = documentSnapshot.toObject(Note.class);

                    note.setmDocumentID(documentSnapshot.getId());

                    String documentID = note.getmDocumentID();
                    String title = note.getmTitle();
                    String description = note.getmDescription();
                    int priority = note.getmPriority();

                    data += "ID : " + documentID + "\n" + "priority : " + priority + "\n"
                            + "title : " + title + "\n" + "description : " + description + "\n\n";
                }
                mTxtViewData.setText(data);
            }

        });
    */
    }

    @Override
    public void onClick(View v) {



        switch (v.getId()) {
            case R.id.btnAdd:

                String title = mEditTxtTitle.getText().toString().trim();
                String description = mEditTxtDesc.getText().toString().trim();
                final int priority = Integer.parseInt(mEditTxtPriority.getText().toString());

                if (mEditTxtPriority.length() == 0) {
                    mEditTxtPriority.setError("priority required");
                    mEditTxtPriority.requestFocus();
                    return;
                }

                final Note note = new Note(title, description, priority);
                note.setmPriority(priority);

                mCollectionRef.add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "note added successfully", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "failed to add a note", Toast.LENGTH_SHORT).show();
                    }
                });

                break;

            case R.id.btnLoadNotes:


                Query query ;

                if (mDocSnap == null){
                    query = mCollectionRef.orderBy("mPriority").limit(3);
                }else {

                    query = mCollectionRef.orderBy("mPriority").limit(3).startAfter(mDocSnap);
                }


                   query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        String data = "";
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            Note note1 = documentSnapshot.toObject(Note.class);
                            note1.setmDocumentID(documentSnapshot.getId());

                            String id = note1.getmDocumentID();
                            String title = note1.getmTitle();
                            String description = note1.getmDescription();
                            int priority = note1.getmPriority();

                            data += "id : " + id + "\n" + " title : " + title + "\n"
                                    + " description : " + description + "\n" + " priority : " + priority + "\n\n";

                        }
                        if (queryDocumentSnapshots.size() > 0) {
                            data += "________________ \n\n";
                            mTxtViewData.append(data);

                            mDocSnap = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+e.getMessage());
                    }
                });














                break;
                /*

                Task task = mCollectionRef.whereLessThan("mPriority", 3).orderBy("mPriority")
                        .get();
                Task task2 = mCollectionRef.whereGreaterThan("mPriority", 3).orderBy("mPriority")
                        .get();

                Task<List<QuerySnapshot>> tasks = Tasks.whenAllSuccess(task, task2);
                tasks.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
                    @Override
                    public void onSuccess(List<QuerySnapshot> querySnapshots) {

                        String data = "";

                        for (QuerySnapshot documentSnapshot : querySnapshots) {
                            for (QueryDocumentSnapshot documentSnapshot1 : documentSnapshot) {

                                Note note1 = documentSnapshot1.toObject(Note.class);
                                note1.setmDocumentID(documentSnapshot1.getId());

                                String id = note1.getmDocumentID();
                                String title = note1.getmTitle();
                                String description = note1.getmDescription();
                                int priority = note1.getmPriority();

                                data += "id : " + id + "\n" + " title : " + title + "\n"
                                        + " description : " + description + "\n" + " priority : " + priority + "\n\n";
                            }
                            mTxtViewData.setText(data);
                        }
                    }
                });

*/



        }

    }
}
